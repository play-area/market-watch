package com.trading.util;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections4.CollectionUtils;
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
		
		
//		backTestingOutputDTO.setTotalTrades(totalTrades);
//		backTestingOutputDTO.setTotalWinnigTrades(totalWinnigTrades);
//		backTestingOutputDTO.setTotalLoosingTrades(totalLoosingTrades);
//		backTestingOutputDTO.setWinPercentage(winPercentage);
		
		return null;
		
	}

}
