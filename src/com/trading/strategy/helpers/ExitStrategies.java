package com.trading.strategy.helpers;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.trading.constants.ApplicationConstants;
import com.trading.constants.ExitStrategy;
import com.trading.model.CandleDTO;
import com.trading.model.TradeDTO;

public class ExitStrategies {
	
	private static Logger LOG = LogManager.getLogger();
	
	
	/**
	 * Function to get Exit Parameters of a Trade
	 * @param tradeDTO
	 * @param currentCandle
	 * @param exitStrategy
	 * @return TradeDTO
	 */
	public static TradeDTO getExitStrategyParmaters(TradeDTO tradeDTO,CandleDTO currentCandle, ExitStrategy exitStrategy) {
		double risk = tradeDTO.getRiskSize();
		switch(exitStrategy) {
			case RiskRewardOneOne :
				if(tradeDTO.getTradeType().equalsIgnoreCase(ApplicationConstants.LONG)) {
					tradeDTO.setExpectedTarget(tradeDTO.getEntryPrice() + risk);
				}else if(tradeDTO.getTradeType().equalsIgnoreCase(ApplicationConstants.SHORT)) {
					tradeDTO.setExpectedTarget(tradeDTO.getEntryPrice() - risk);
				}
				break;
			case RiskRewardOneTwo :
				if(tradeDTO.getTradeType().equalsIgnoreCase(ApplicationConstants.LONG)) {
					tradeDTO.setExpectedTarget(tradeDTO.getEntryPrice() + 2*risk);
				}else if(tradeDTO.getTradeType().equalsIgnoreCase(ApplicationConstants.SHORT)) {
					tradeDTO.setExpectedTarget(tradeDTO.getEntryPrice() - 2*risk);
				}
				break;
			case RiskRewardOneTwoWithTrailing :
				if(tradeDTO.getTradeType().equalsIgnoreCase(ApplicationConstants.LONG)) {
					if(currentCandle.getHigh()> (tradeDTO.getEntryPrice() + risk)) {
						tradeDTO.setExpectedStopLoss(tradeDTO.getEntryPrice());
					}
					tradeDTO.setExpectedTarget(tradeDTO.getEntryPrice() + 2*risk);
				}else if(tradeDTO.getTradeType().equalsIgnoreCase(ApplicationConstants.SHORT)) {
					if(currentCandle.getLow()< (tradeDTO.getEntryPrice() - risk)) {
						tradeDTO.setExpectedStopLoss(tradeDTO.getEntryPrice());
					}
					tradeDTO.setExpectedTarget(tradeDTO.getEntryPrice() - 2*risk);
				}
				break;
			case RiskRewardOneThree :
				if(tradeDTO.getTradeType().equalsIgnoreCase(ApplicationConstants.LONG)) {
					tradeDTO.setExpectedTarget(tradeDTO.getEntryPrice() + 3*risk);
				}else if(tradeDTO.getTradeType().equalsIgnoreCase(ApplicationConstants.SHORT)) {
					tradeDTO.setExpectedTarget(tradeDTO.getEntryPrice() - 3*risk);
				}
				break;
			default:
				LOG.error("Please select an appropriate Exit Strategy, "+exitStrategy+" is not present");
				break;
		}
		return tradeDTO;
	}
}
