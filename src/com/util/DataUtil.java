package com.util;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import com.model.BackTestingOutputDTO;
import com.model.DailyCandleDTO;
import com.model.QuandlResponseDTO;
import com.model.TradeDTO;

public class DataUtil {

	public static List<DailyCandleDTO> getDailyCandleDataList(QuandlResponseDTO responseData) {
		
		List<DailyCandleDTO> dailyCandleList = null;
		
		if(responseData != null) {
			
			dailyCandleList = new ArrayList<DailyCandleDTO>();
			int i=0;
			while(i < responseData.getDataset().getData().size()) {
				List<String> candleData = responseData.getDataset().getData().get(i);
				DailyCandleDTO dailyCandleDTO = new DailyCandleDTO();
				dailyCandleDTO.setTime(candleData.get(0));
				dailyCandleDTO.setSymbol("symbol");
				dailyCandleDTO.setOpen(Double.parseDouble(candleData.get(1)));
				dailyCandleDTO.setHigh(Double.parseDouble(candleData.get(2)));
				dailyCandleDTO.setLow(Double.parseDouble(candleData.get(3)));
				dailyCandleDTO.setClose(Double.parseDouble(candleData.get(5)));
				dailyCandleDTO.setVolume(new BigDecimal(candleData.get(6)));
				dailyCandleList.add(dailyCandleDTO);
				i++;
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
