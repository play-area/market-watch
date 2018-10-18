package com.trading.strategy;

import java.io.IOException;
import java.util.List;

import com.model.TradeDTO;

public class BreakoutStrategy {
	
	/**
	 * Function to get Database Properties from Property File
	 * @param breakoutPeriod The last n candle(s) above/below which the current candle is breaking out.
	 * @param candleMultiple Height Multiple of the current candle relative to average of the last n candle height(s).
	 * @param candleWickMultiple Length of the Directional Wick relative to the height of the candle.
	 * @param volumeMultiple Volume multiple of the candle relative to the average volume of the last n candle(s).
	 * @param exitCriteria 
	 * @return List<TradeDTO> Trades Taken
	 */
	public List<TradeDTO> executeBreakoutStrategy(int breakoutPeriod,double candleMultiple,double candleWickMultiple,double volumeMultiple){
		return null;
		
	}

}
