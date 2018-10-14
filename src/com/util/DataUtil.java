package com.util;

import java.util.ArrayList;
import java.util.List;

import com.model.DailyCandleDTO;
import com.model.QuandlResponseDTO;

public class DataUtil {

	public static List<DailyCandleDTO> getDailyCandleDataList(QuandlResponseDTO responseData) {
		
		List<DailyCandleDTO> dailyCandleList = null;
		
		if(responseData != null) {
			
			dailyCandleList = new ArrayList<DailyCandleDTO>();
			
			for(List<String> inputData : responseData.getDataset().getData()) {
				
				DailyCandleDTO dailyCandleDTO = new DailyCandleDTO();
				dailyCandleDTO.setTime(inputData.get(0));
				dailyCandleDTO.setOpen(inputData.get(1));
				dailyCandleDTO.setHigh(inputData.get(2));
				dailyCandleDTO.setLow(inputData.get(3));
				dailyCandleDTO.setClose(inputData.get(5));
				dailyCandleDTO.setVolume(inputData.get(6));
				dailyCandleList.add(dailyCandleDTO);			
				
			}
			
			
		}
		
		return dailyCandleList;
	}

}
