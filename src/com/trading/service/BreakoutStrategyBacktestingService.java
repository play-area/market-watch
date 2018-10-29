package com.trading.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.trading.constants.ApplicationConstants;
import com.trading.constants.DatabaseConstants;
import com.trading.constants.ExitStrategy;
import com.trading.dao.ManageDataDAO;
import com.trading.model.BackTestingOutputDTO;
import com.trading.model.CandleDTO;
import com.trading.model.TradeDTO;
import com.trading.strategy.BreakoutStrategy;
import com.trading.util.DataUtil;

public class BreakoutStrategyBacktestingService {
	
	private static Logger LOG = LogManager.getLogger();
	
	public static void main(String args[]) {
		BreakoutStrategy breakoutStrategy = new BreakoutStrategy();
		ManageDataDAO manageDataDAO = new ManageDataDAO();
		List<String> symbolList = getWatchList();
		List<List<CandleDTO>> candleList = new ArrayList<List<CandleDTO>>();
		candleList = manageDataDAO.getCandlesData(DatabaseConstants.DATA_QUANDL_DAILY, symbolList);
		for(int i=0;i<candleList.get(1).size();i++) {
			CandleDTO currentCandle = candleList.get(1).get(i);
			//Skipping the first durationOfAverage candle(s) as they are needed for calcualting averages
			if(i>50){
				Map entryMap = breakoutStrategy.getEntryCandle(candleList.get(1),currentCandle,i, 20 , 50,  1.5, 0.3, new BigDecimal(1.5));
				if(entryMap.containsKey(ApplicationConstants.CANDLE)){
					CandleDTO candle = (CandleDTO)entryMap.get(ApplicationConstants.CANDLE);
					LOG.info("ENTRY SIGNAL : "+ candle.getSymbol() + " Date : "+candle.getTime()+" Trade Type : "+entryMap.get(ApplicationConstants.TRADE_TYPE));
				}
			}
		}
		
		List<TradeDTO> listTradeDTO = breakoutStrategy.executeBreakoutStrategy(candleList.get(1), 20, 50, 1.5, 0.3, new BigDecimal(1.5), ExitStrategy.RiskRewardOneTwoWithTrailing, 3000);
		LOG.info("######################### The following trades were taken ##############################");
		double profitLoss = 0;
		for(TradeDTO tradeDTO : listTradeDTO) {
			LOG.info("Trade details : "+ tradeDTO.getStartTime() + " TRADE TYPE "+tradeDTO.getTradeType()+" ENTRY PRICE "+ tradeDTO.getEntryPrice()+" EXIT PRICE "+tradeDTO.getExitPrice());
			if(tradeDTO.getTradeType().equalsIgnoreCase(ApplicationConstants.LONG) && tradeDTO.getStatus().equalsIgnoreCase(ApplicationConstants.CLOSED)) {
				profitLoss = profitLoss + 3000*(tradeDTO.getExitPrice()- tradeDTO.getEntryPrice());
			}else if(tradeDTO.getTradeType().equalsIgnoreCase(ApplicationConstants.SHORT)  && tradeDTO.getStatus().equalsIgnoreCase(ApplicationConstants.CLOSED)) {
				profitLoss = profitLoss + 3000*(tradeDTO.getEntryPrice()-tradeDTO.getExitPrice());
			}
		}
		LOG.info("TOTAL PROFIT LOSS : "+profitLoss);
		//BackTestingOutputDTO backTestingOutputDTO = DataUtil.getBackTestingResults(listTradeDTO);
	}
	
	/**
	 * Function to get data from Quandl
	 * @param List<String> symbolList List of symbols for which data needs to be fetched.
	 * @return List<String>
	 */
	public static List<String> getWatchList(){
		List<String> symbolList = new ArrayList<String>();
		symbolList.add("VEDL");
		symbolList.add("SBIN");
		return symbolList;
	}
}
