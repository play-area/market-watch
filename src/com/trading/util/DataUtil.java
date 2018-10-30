package com.trading.util;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.MapUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.trading.constants.ApplicationConstants;
import com.trading.model.BackTestingOutputDTO;
import com.trading.model.CandleDTO;
import com.trading.model.QuandlResponseDTO;
import com.trading.model.TradeDTO;

public class DataUtil {

	private static Logger LOG = LogManager.getLogger();
	
	public static List<CandleDTO> getDailyCandleDataList(QuandlResponseDTO responseData) {
		
		List<CandleDTO> dailyCandleList = null;
		
		if(responseData != null) {
			dailyCandleList = new ArrayList<CandleDTO>();
			int i=0;
			SimpleDateFormat df = new SimpleDateFormat(ApplicationConstants.DATE_FORMAT_YYYY_MM_DD);
			try {
				if(responseData.getDataset() !=null && CollectionUtils.isNotEmpty(responseData.getDataset().getData())){
					while(i < responseData.getDataset().getData().size()) {
						List<String> candleData = responseData.getDataset().getData().get(i);
						CandleDTO dailyCandleDTO = new CandleDTO();
						dailyCandleDTO.setTime(df.parse(candleData.get(0)));
						dailyCandleDTO.setSymbol(responseData.getDataset().getDataset_code());
						dailyCandleDTO.setOpen(Double.parseDouble(candleData.get(1)));
						dailyCandleDTO.setHigh(Double.parseDouble(candleData.get(2)));
						dailyCandleDTO.setLow(Double.parseDouble(candleData.get(3)));
						dailyCandleDTO.setClose(Double.parseDouble(candleData.get(5)));
						dailyCandleDTO.setVolume(new BigDecimal(candleData.get(6)));
						dailyCandleList.add(dailyCandleDTO);
						i++;
					}
				}
			}catch (ParseException e) {
				LOG.error("Exception thrown for"+ responseData.getDataset().getDataset_code()+"while extracting response : "+e);
			}
		}
		return dailyCandleList;
	}
	
	public static BackTestingOutputDTO getBackTestingResults(List<TradeDTO> tradeDTOList) {
        BackTestingOutputDTO backTestingOutputDTO = new BackTestingOutputDTO();
        double profitLoss = 0;
        double totalWinSize =0;
        double totalLossSize = 0;
        int totalTrades = 0;
        int winners = 0;
        int loosers = 0;
        List<Map<Object,Object>> maxDrawDownList = new ArrayList<Map<Object,Object>>();
        Map<String,String> tradeOutCome = new HashMap<String,String>();
		for(TradeDTO tradeDTO : tradeDTOList) {
			if(tradeDTO.getTradeType().equalsIgnoreCase(ApplicationConstants.LONG) && tradeDTO.getStatus().equalsIgnoreCase(ApplicationConstants.CLOSED)) {
				totalTrades = totalTrades+1;
		        profitLoss = profitLoss + tradeDTO.getSize()*(tradeDTO.getExitPrice()- tradeDTO.getEntryPrice());
		        if(tradeDTO.getEntryPrice()<tradeDTO.getExitPrice()) {
		        	winners = winners+1;
		        	totalWinSize = totalWinSize+tradeDTO.getSize()*(tradeDTO.getExitPrice()- tradeDTO.getEntryPrice());
		        	tradeOutCome.put(ApplicationConstants.CURRENT,ApplicationConstants.WINNER);
		        }else {
		            loosers = loosers +1;
		            totalLossSize = totalLossSize+tradeDTO.getSize()*(tradeDTO.getExitPrice()-tradeDTO.getEntryPrice());
		            tradeOutCome.put(ApplicationConstants.CURRENT,ApplicationConstants.LOOSER);
                    maxDrawDownList = getMaxDrawDownList(tradeDTO.getSize()*(tradeDTO.getExitPrice()-tradeDTO.getEntryPrice()),tradeOutCome,maxDrawDownList);
                 }
			}else if(tradeDTO.getTradeType().equalsIgnoreCase(ApplicationConstants.SHORT)  && tradeDTO.getStatus().equalsIgnoreCase(ApplicationConstants.CLOSED)) {
                totalTrades = totalTrades+1;
                profitLoss = profitLoss + tradeDTO.getSize()*(tradeDTO.getEntryPrice()-tradeDTO.getExitPrice());
                if(tradeDTO.getEntryPrice()>tradeDTO.getExitPrice()) {
                	winners = winners+1;
                    totalWinSize = totalWinSize+tradeDTO.getSize()*(tradeDTO.getEntryPrice()-tradeDTO.getExitPrice());
                    tradeOutCome.put(ApplicationConstants.CURRENT,ApplicationConstants.WINNER);
                }else {
                	loosers = loosers +1;
                	totalLossSize = totalLossSize+tradeDTO.getSize()*(tradeDTO.getEntryPrice()-tradeDTO.getExitPrice());
                	tradeOutCome.put(ApplicationConstants.CURRENT,ApplicationConstants.LOOSER);
                    maxDrawDownList = getMaxDrawDownList(tradeDTO.getSize()*(tradeDTO.getEntryPrice()-tradeDTO.getExitPrice()),tradeOutCome,maxDrawDownList);
                 }
			}
			tradeOutCome.put(ApplicationConstants.PREVIOUS,tradeOutCome.get(ApplicationConstants.CURRENT));
        }
		Collections.sort(maxDrawDownList,mapComparator);
		backTestingOutputDTO.setTotalTrades(totalTrades);
        backTestingOutputDTO.setTotalWinnigTrades(winners);
        backTestingOutputDTO.setTotalLoosingTrades(loosers);
		backTestingOutputDTO.setWinPercentage((double)(winners*100/totalTrades));
		backTestingOutputDTO.setAverageWinSize(totalWinSize/winners);
		backTestingOutputDTO.setAverageLossSize(totalLossSize/loosers);
		backTestingOutputDTO.setTotalProfitLoss(profitLoss);
		backTestingOutputDTO.setMaxDrawDown((Double)maxDrawDownList.get(0).get("loosingStreakValue"));
		backTestingOutputDTO.setLoosingStreakSize((Integer)maxDrawDownList.get(0).get("loosingStreakSize"));
		return backTestingOutputDTO;
	}


	public static List<Map<Object,Object>> getMaxDrawDownList(double loss,Map<String,String> tradeOutCome,List<Map<Object,Object>> maxDrawDownList){
		Integer loosingStreak = 0;
		Double maxDrawDown = 0.0;
		if(MapUtils.isEmpty(tradeOutCome) || !tradeOutCome.containsKey(ApplicationConstants.PREVIOUS) || tradeOutCome.get(ApplicationConstants.PREVIOUS).equalsIgnoreCase(ApplicationConstants.WINNER)){
			  maxDrawDown = loss;
			  loosingStreak =1;
			  Map<Object,Object> lossingStreakMap = new HashMap<Object,Object>();
			  lossingStreakMap.put("loosingStreakSize", loosingStreak);
			  lossingStreakMap.put("loosingStreakValue", maxDrawDown);
			  maxDrawDownList.add(lossingStreakMap);
		}else if(tradeOutCome.get(ApplicationConstants.PREVIOUS).equalsIgnoreCase(ApplicationConstants.LOOSER)){
              maxDrawDown = (Double) maxDrawDownList.get(maxDrawDownList.size() -1).get("loosingStreakValue");
              loosingStreak = (Integer) maxDrawDownList.get(maxDrawDownList.size() -1).get("loosingStreakSize");
              maxDrawDown = maxDrawDown+loss;
              loosingStreak = loosingStreak+1;
              Map<Object,Object> lossingStreakMap = new HashMap<Object,Object>();
              lossingStreakMap.put("loosingStreakSize", loosingStreak);
              lossingStreakMap.put("loosingStreakValue", maxDrawDown);
              maxDrawDownList.set(maxDrawDownList.size() -1,lossingStreakMap);
		}
		return maxDrawDownList;
	}

	public static Comparator<Map<Object, Object>> mapComparator = new Comparator<Map<Object, Object>>() {
	    public int compare(Map<Object, Object> m1, Map<Object, Object> m2) {
	        return ((Double) m1.get("loosingStreakValue")).compareTo((Double)m2.get("loosingStreakValue"));
	    }
	};





}
