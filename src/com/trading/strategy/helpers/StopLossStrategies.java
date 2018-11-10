package com.trading.strategy.helpers;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.trading.constants.ApplicationConstants;
import com.trading.constants.ExitStrategy;
import com.trading.constants.StopLossStrategy;
import com.trading.model.CandleDTO;
import com.trading.model.TradeDTO;
import com.trading.util.IndicatorsUtil;

public class StopLossStrategies {
	
	private static Logger LOG = LogManager.getLogger();

	/**
	 * Function to get Exit Parameters of a Trade
	 * @param tradeDTO
	 * @param currentCandle
	 * @param exitStrategy
	 * @return TradeDTO
	 */
	public static double getStopLoss(TradeDTO tradeDTO,CandleDTO currentCandle,Double averageCandleHeight,StopLossStrategy stopLossStrategy){
		double stopLossAmount = 0;
		double smallestTickSize = 0.05;
		switch(stopLossStrategy){
			case BeyondEntryCandle :
				if(tradeDTO.getTradeType().equalsIgnoreCase(ApplicationConstants.LONG)){
					stopLossAmount =  currentCandle.getLow()-smallestTickSize*2;
				}else if(tradeDTO.getTradeType().equalsIgnoreCase(ApplicationConstants.SHORT)){
					stopLossAmount =  currentCandle.getHigh()+smallestTickSize*2;
				}
				break;
			case OneTimesCandleHeightStop :
				if(tradeDTO.getTradeType().equalsIgnoreCase(ApplicationConstants.LONG)){
					stopLossAmount = currentCandle.getClose()-averageCandleHeight;
				}else if(tradeDTO.getTradeType().equalsIgnoreCase(ApplicationConstants.SHORT)){
					stopLossAmount = currentCandle.getHigh()+averageCandleHeight; 
				}
				break;
			case TwoTimesCandleHeightStop :
				if(tradeDTO.getTradeType().equalsIgnoreCase(ApplicationConstants.LONG)){
					stopLossAmount = currentCandle.getClose()-2*averageCandleHeight;
				}else if(tradeDTO.getTradeType().equalsIgnoreCase(ApplicationConstants.SHORT)){
					stopLossAmount =  currentCandle.getHigh()+2*averageCandleHeight; 
				}
				break;
			case ThreeTimesCandleHeightStop :
				if(tradeDTO.getTradeType().equalsIgnoreCase(ApplicationConstants.LONG)){
					stopLossAmount = currentCandle.getClose()-3*averageCandleHeight;
				}else if(tradeDTO.getTradeType().equalsIgnoreCase(ApplicationConstants.SHORT)){
					stopLossAmount = currentCandle.getHigh()+3*averageCandleHeight; 
				}
				break;
			case FourTimesCandleHeightStop :
				if(tradeDTO.getTradeType().equalsIgnoreCase(ApplicationConstants.LONG)){
					stopLossAmount = currentCandle.getClose()-4*averageCandleHeight;
				}else if(tradeDTO.getTradeType().equalsIgnoreCase(ApplicationConstants.SHORT)){
					stopLossAmount = currentCandle.getHigh()+4*averageCandleHeight; 
				}
				break;
			case MaxOfCandleBeyondAndOneTimesCandleHeight :
				if(tradeDTO.getTradeType().equalsIgnoreCase(ApplicationConstants.LONG)){
					if((currentCandle.getClose()-currentCandle.getLow()) < averageCandleHeight){
						stopLossAmount = currentCandle.getClose()-averageCandleHeight;
					}else{
						stopLossAmount = currentCandle.getLow()-smallestTickSize*2;
					}
				}else if(tradeDTO.getTradeType().equalsIgnoreCase(ApplicationConstants.SHORT)){
					if((currentCandle.getHigh()-currentCandle.getClose()) < averageCandleHeight){
						stopLossAmount = currentCandle.getClose()+averageCandleHeight;
					}else{
						stopLossAmount = currentCandle.getHigh()+smallestTickSize*2;
					}
				}
				break;
			case MaxOfCandleBeyondAndOneHalfTimesCandleHeight :
				if(tradeDTO.getTradeType().equalsIgnoreCase(ApplicationConstants.LONG)){
					if((currentCandle.getClose()-currentCandle.getLow()) < 1.5*averageCandleHeight){
						stopLossAmount = currentCandle.getClose()-1.5*averageCandleHeight;
					}else{
						stopLossAmount = currentCandle.getLow()-smallestTickSize*2;
					}
				}else if(tradeDTO.getTradeType().equalsIgnoreCase(ApplicationConstants.SHORT)){
					if((currentCandle.getHigh()-currentCandle.getClose()) < 1.5*averageCandleHeight){
						stopLossAmount = currentCandle.getClose()+1.5*averageCandleHeight;
					}else{
						stopLossAmount = currentCandle.getHigh()+smallestTickSize*2;
					}
				}
				break;
			case MaxOfCandleBeyondAndTwoTimesCandleHeight :
				if(tradeDTO.getTradeType().equalsIgnoreCase(ApplicationConstants.LONG)){
					if((currentCandle.getClose()-currentCandle.getLow()) < 2*averageCandleHeight){
						stopLossAmount = currentCandle.getClose()-2*averageCandleHeight;
					}else{
						stopLossAmount = currentCandle.getLow()-smallestTickSize*2;
					}
				}else if(tradeDTO.getTradeType().equalsIgnoreCase(ApplicationConstants.SHORT)){
					if((currentCandle.getHigh()-currentCandle.getClose()) < 2*averageCandleHeight){
						stopLossAmount = currentCandle.getClose()+2*averageCandleHeight;
					}else{
						stopLossAmount = currentCandle.getHigh()+smallestTickSize*2;
					}
				}
				break;
			case MaxOfCandleBeyondAndThreeTimesCandleHeight :
				if(tradeDTO.getTradeType().equalsIgnoreCase(ApplicationConstants.LONG)){
					if((currentCandle.getClose()-currentCandle.getLow()) < 3*averageCandleHeight){
						stopLossAmount = currentCandle.getClose()-3*averageCandleHeight;
					}else{
						stopLossAmount = currentCandle.getLow()-smallestTickSize*2;
					}

				}else if(tradeDTO.getTradeType().equalsIgnoreCase(ApplicationConstants.SHORT)){
					if((currentCandle.getHigh()-currentCandle.getClose()) < 3*averageCandleHeight){
						stopLossAmount = currentCandle.getClose()+3*averageCandleHeight;
					}else{
						stopLossAmount = currentCandle.getHigh()+smallestTickSize*2;
					}
				}
				break;
			default:
				LOG.error("Please select an appropriate Stop Loss Strategy, "+stopLossStrategy+" is not present");
				break;
		}
		return stopLossAmount;
	}
}
