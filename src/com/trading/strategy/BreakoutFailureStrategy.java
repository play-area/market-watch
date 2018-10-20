package com.trading.strategy;

import java.util.ArrayList;
import java.util.List;

import com.constants.ExitStrategy;
import com.model.CandleDTO;
import com.model.TradeDTO;

public class BreakoutFailureStrategy {
	
	/**
	 * Function to get Database Properties from Property File
	 * @param candleDTOList Data Input for performing backtesting
	 * @param lookbackPeriod The last n candle(s) above/below which the current candle is breaking out.
	 * @param candleHightMultiple Height Multiple of the current candle relative to average of the last n candle height(s).
	 * @param candleWickMultiple Length of the Directional Wick relative to the height of the candle.
	 * @param volumeMultiple Volume multiple of the candle relative to the average volume of the last n candle(s).
	 * @param exitStrategy The criteria on which exit will take place.
	 * @param tradeSize The size in Rupees of individual trades.
	 * @return List<TradeDTO> Trades Taken
	 */
	public List<TradeDTO> executeBreakoutStrategy(List<CandleDTO>candleDTOList,int lookbackPeriod,double candleHightMultiple,double candleWickMultiple,double volumeMultiple,ExitStrategy exitStrategy, double tradeSize){
		List<TradeDTO> tradeDTOList	=	new ArrayList<TradeDTO>();
		
		return tradeDTOList;
	}
	
	/**
	 * Function to check if the candle is Eligible or not
	 * @param candleDTOList Data Input for performing backtesting
	 * @param lookbackPeriod The last n candle(s) above/below which the current candle is breaking out.
	 * @param candleHightMultiple Height Multiple of the current candle relative to average of the last n candle height(s).
	 * @param candleWickMultiple Length of the Directional Wick relative to the height of the candle.
	 * @param volumeMultiple Volume multiple of the candle relative to the average volume of the last n candle(s).
	 * @return boolean
	 */
	public boolean isValidBreakoutEntry(CandleDTO candleDTO,int lookbackPeriod,double candleHightMultiple,double candleWickMultiple,double volumeMultiple) {
		boolean isCandleElligible;
		
		return false;
		
	}
	
	/**
	 * Function to get exit candle(s)
	 * @return 
	 * @return boolean
	 */
	public void isValidBreakoutExit() {
		
	}


}
