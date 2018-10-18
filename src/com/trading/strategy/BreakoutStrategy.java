package com.trading.strategy;

import java.util.ArrayList;
import java.util.List;

import com.constants.ExitStrategy;
import com.model.DailyCandleDTO;
import com.model.TradeDTO;

public class BreakoutStrategy {
	
	/**
	 * Function to get Database Properties from Property File
	 * @param candleDTOList Data Input for performing backtesting
	 * @param breakoutPeriod The last n candle(s) above/below which the current candle is breaking out.
	 * @param candleMultiple Height Multiple of the current candle relative to average of the last n candle height(s).
	 * @param candleWickMultiple Length of the Directional Wick relative to the height of the candle.
	 * @param volumeMultiple Volume multiple of the candle relative to the average volume of the last n candle(s).
	 * @param exitCriteria The criteria on which exit will take place.
	 * @param tradeSize The size in Rupees of individual trades.
	 * @return List<TradeDTO> Trades Taken
	 */
	public List<TradeDTO> executeBreakoutStrategy(List<DailyCandleDTO>candleDTOList,int breakoutPeriod,double candleMultiple,double candleWickMultiple,double volumeMultiple,ExitStrategy exitStratefy, double tradeSize){
		List<TradeDTO> tradeDTOList	=	new ArrayList<TradeDTO>();
		
		return tradeDTOList;
	}

}
