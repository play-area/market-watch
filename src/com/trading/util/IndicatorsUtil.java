package com.trading.util;

import java.math.BigDecimal;
import java.util.List;

import org.apache.commons.collections4.CollectionUtils;

import com.trading.constants.ApplicationConstants;
import com.trading.model.CandleDTO;

public class IndicatorsUtil {
	
	/**
	 * Function to calculate the Simple Moving Average
	 * @param durationOfAverage The duration for calculating the average.
	 * @param candleDTOList List of values for which the average needs to be calculated.
	 * @param fieldName The fieldName for which the average needs to be calculated
	 * @return average of the values
	 */
	public static Double calcuateSimpleMovingAverage(int durationOfAverage,List<CandleDTO> candleDTOList, String fieldName) {
		double total = 0;
		if(CollectionUtils.isNotEmpty(candleDTOList)) {
			for(int i=candleDTOList.size()-1;i>=candleDTOList.size()-durationOfAverage;i--) {
				if(fieldName.equalsIgnoreCase(ApplicationConstants.CANDLE_CLOSE)){
					total = total + candleDTOList.get(i).getClose();
				}
			}
		}
		if(total!=0) {
			return total/durationOfAverage;
		}else {
			return 0.0;
		}
	}
	
	/**
	 * Function to calculate the Exponential Moving Average
	 * @param List List of values for which the average needs to be calculated.
	 * @return exponential average of the values
	 */
	public static Double calcuateExponentialMovingAverage(List inputArray) {
		int size = inputArray.size();
		return null;
	}
	
	/**
	 * Function to calculate the Average of Volume
	 * @param durationOfAverage The duration for calculating the average.
	 * @param candleDTOList List of values for which the average needs to be calculated.
	 * @param index Index of the current candle
	 * @return average of the Volume
	 */
	public static BigDecimal calcuateAverageVolume(int durationOfAverage,List<CandleDTO> candleDTOList,int index) {
		BigDecimal totalVolume = new BigDecimal(0);
		if(CollectionUtils.isNotEmpty(candleDTOList)) {
			for(int i=index-durationOfAverage;i<index;i++) {
				totalVolume = totalVolume.add(candleDTOList.get(i).getVolume());
			}
		}
		return totalVolume.divide(new BigDecimal(durationOfAverage));
	}
	
	/**
	 * Function to calculate the Average of Candle Height
	 * @param durationOfAverage The duration for calculating the average.
	 * @param candleDTOList List of values for which the average needs to be calculated.
	 * @param index Index of the current candle
	 * @return average of the Candle Height
	 */
	public static Double calcualteAverageCandleHeight(int durationOfAverage,List<CandleDTO> candleDTOList,int index) {
		double totalHeight = 0;
		if(CollectionUtils.isNotEmpty(candleDTOList)) {
			for(int i=index-durationOfAverage;i<index;i++) {
				totalHeight = totalHeight+Math.abs(candleDTOList.get(i).getHigh()-candleDTOList.get(i).getLow());
			}
		}
		if(totalHeight!=0) {
			return totalHeight/durationOfAverage;
		}else {
			return 0.0;
		}
	}
	
	/**
	 * Function to calculate the Highest or Lowest of the last n candles
	 * @param lookbackPeriod The duration for looking back
	 * @param candleDTOList List of values for which the average needs to be calculated.
	 * @param fieldName The fieldName for which the average needs to be calculated
	 * @return average of the values
	 */
	public static Double getHighLow(int lookbackPeriod,List<CandleDTO> candleDTOList, String fieldName,int index) {
		double high = candleDTOList.get(index-lookbackPeriod).getHigh();
		double low = candleDTOList.get(index-lookbackPeriod).getLow();
		if(CollectionUtils.isNotEmpty(candleDTOList)) {
			for(int i=index-lookbackPeriod;i<index;i++) {
				if(fieldName.equalsIgnoreCase(ApplicationConstants.HIGHEST)){
					if(candleDTOList.get(i).getHigh()>high) {
						high = candleDTOList.get(i).getHigh();
					}
				}else if(fieldName.equalsIgnoreCase(ApplicationConstants.LOWEST)){
					if(candleDTOList.get(i).getLow()<low) {
						low = candleDTOList.get(i).getLow();
					}
				}
			}
		}
		if(fieldName.equalsIgnoreCase(ApplicationConstants.HIGHEST)){
			return high;
		}else{
			return low;
		}
	}

}
