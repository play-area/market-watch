package com.trading.strategy.helpers;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.trading.constants.PositionSizingStrategy;
import com.trading.model.CandleDTO;
import com.trading.model.TradeDTO;

public class PositionSizingStrategies {
	
private static Logger LOG = LogManager.getLogger();
	
	
	/**
	 * Function to get the Size of the Position
	 * @param tradeDTO  Current TradeDTO
	 * @param positionSizingStrategy The position sizing strategy used
	 * @param tradingCapital  The total trading capital
	 * @return Size of the position
	 */
	public static Integer getTradeSize(TradeDTO tradeDTO,PositionSizingStrategy positionSizingStrategy, Integer tradingCapital) {
		int size = 0;
		double onePercent = 0.01*tradingCapital;
		double risk = tradeDTO.getRiskSize();
		switch(positionSizingStrategy) {
			case RiskOnePercentOfAccount:
				size = (int)Math.round(onePercent/risk);
				break;
			case RiskTwoPercentOfAccount:
				size = (int)Math.round(2*onePercent/risk);
				break;
			case RiskThreePercentOfAccount:
				size = (int)Math.round(3*onePercent/risk);
				break;
			case RiskFourPercentOfAccount:
				size = (int)Math.round(4*onePercent/risk);
				break;
			case RiskFivePercentOfAccount:
				size = (int)Math.round(5*onePercent/risk);
				break;
			default:
				LOG.error("Please select an appropriate Exit Strategy, "+positionSizingStrategy+" is not present");
				break;
		}
		return size;
	}

}
