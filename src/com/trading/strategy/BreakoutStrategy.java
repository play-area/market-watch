package com.trading.strategy;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.trading.constants.ApplicationConstants;
import com.trading.constants.ExitStrategy;
import com.trading.constants.PositionSizingStrategy;
import com.trading.constants.StopLossStrategy;
import com.trading.model.CandleDTO;
import com.trading.model.TradeDTO;
import com.trading.strategy.helpers.ExitStrategies;
import com.trading.strategy.helpers.PositionSizingStrategies;
import com.trading.strategy.helpers.StopLossStrategies;
import com.trading.util.IndicatorsUtil;

public class BreakoutStrategy {
	
	private static Logger LOG = LogManager.getLogger();
	
	/**
	 * Function to get Database Properties from Property File
	 * @param candleDTOList Data Input for performing backtesting
	 * @param lookbackPeriod The last n candle(s) above/below which the current candle is breaking out.
	 * @param durationOfAverage The duration for calculating the averages of Volume, Candle Height etc
	 * @param candleHightMultiple Height Multiple of the current candle relative to average of the last n candle height(s).
	 * @param candleWickMultiple Length of the Directional Wick relative to the height of the candle.
	 * @param volumeMultiple Volume multiple of the candle relative to the average volume of the last n candle(s).
	 * @param exitStrategy The criteria on which exit will take place.
	 * @param positionSizingStrategy The position sizing Strategy used.
	 * @param stopLossStrategy The Stop Loss Strategy implemented.
	 * @param tradeSize The size in Rupees of individual trades.
	 * @return List<TradeDTO> Trades Taken
	 */
	public List<TradeDTO> executeBreakoutStrategy(List<CandleDTO>candleDTOList,int lookbackPeriod, int durationOfAverage , double candleHightMultiple,double candleWickMultiple,BigDecimal volumeMultiple,ExitStrategy exitStrategy, PositionSizingStrategy positionSizingStrategy , StopLossStrategy stopLossStrategy ,Integer tradingCapital){
		List<TradeDTO> tradeDTOList	=	new ArrayList<TradeDTO>();
		CandleDTO currentCandle = new CandleDTO();
		if(CollectionUtils.isNotEmpty(candleDTOList)) {
			for(int i=0;i<candleDTOList.size();i++) {
				currentCandle = candleDTOList.get(i);
				//Skipping the first durationOfAverage candle(s) as they are needed for calcualting averages
				if(i>durationOfAverage){
					if(CollectionUtils.isEmpty(tradeDTOList) || (CollectionUtils.isNotEmpty(tradeDTOList) && tradeDTOList.get(tradeDTOList.size()-1).getStatus().equalsIgnoreCase(ApplicationConstants.CLOSED))) {
						Map entryMap = getEntryCandle(candleDTOList,currentCandle,i, lookbackPeriod, durationOfAverage,  candleHightMultiple, candleWickMultiple, volumeMultiple);
						if(entryMap.containsKey(ApplicationConstants.CANDLE)){
							double averageCandleHeight = IndicatorsUtil.calcualteAverageCandleHeight(durationOfAverage, candleDTOList,i);
							TradeDTO tradeDTO = new TradeDTO();
							tradeDTO.setEntryCandle(currentCandle);
							tradeDTO.setEntryPrice(currentCandle.getClose());
							tradeDTO.setStartTime(currentCandle.getTime());
							tradeDTO.setStatus(ApplicationConstants.OPENED);
							tradeDTO.setTradeType((String)entryMap.get(ApplicationConstants.TRADE_TYPE));
							tradeDTO.setExpectedStopLoss(StopLossStrategies.getStopLoss(tradeDTO, currentCandle,averageCandleHeight, stopLossStrategy));
							tradeDTO.setRiskSize(Math.abs(tradeDTO.getEntryPrice()-tradeDTO.getExpectedStopLoss()));
							tradeDTO = ExitStrategies.getExitStrategyParmaters(tradeDTO,currentCandle, exitStrategy);
							tradeDTO.setSize(PositionSizingStrategies.getTradeSize(tradeDTO, positionSizingStrategy , tradingCapital) );
							tradeDTOList.add(tradeDTO);
						}
					}else if(CollectionUtils.isNotEmpty(tradeDTOList) && tradeDTOList.get(tradeDTOList.size()-1).getStatus().equalsIgnoreCase(ApplicationConstants.OPENED)) {
						int currentTradeIndex = tradeDTOList.size()-1;
						TradeDTO currentTrade = tradeDTOList.get(currentTradeIndex);
						currentTrade = verifyExitParametersSatisfied(tradeDTOList.get(tradeDTOList.size()-1),currentCandle, exitStrategy);
						if(currentTrade.getStatus().equalsIgnoreCase(ApplicationConstants.CLOSED)){
							tradeDTOList.set(currentTradeIndex,currentTrade );
						}
					}
				}
			}
		}
		return tradeDTOList;
	}
	
	/**
	 * Function to check if the candle is Eligible or not
	 * @param candleDTOList Data Input for performing backtesting
	 * @param lookbackPeriod The last n candle(s) above/below which the current candle is breaking out.
	 * @param avaerageDuration The duration for calculating the averages of Volume, height etc
	 * @param candleHightMultiple Height Multiple of the current candle relative to average of the last n candle height(s).
	 * @param candleWickMultiple Length of the Directional Wick relative to the height of the candle.
	 * @param volumeMultiple Volume multiple of the candle relative to the average volume of the last n candle(s).
	 * @return Map<Object,Object> entryDetails containing details of the Entry Candle and the direction of the trade
	 */
	public Map<Object,Object> getEntryCandle(List<CandleDTO> candleDTOList, CandleDTO candleDTO,int index,int lookbackPeriod,int durationOfAverage, double candleHightMultiple,double candleWickMultiple,BigDecimal volumeMultiple) {
		Map<Object,Object> entryDetails = new HashMap<Object,Object>();
		BigDecimal averageVolume = IndicatorsUtil.calcuateAverageVolume(durationOfAverage, candleDTOList,index);
		double averageCandleHeight = IndicatorsUtil.calcualteAverageCandleHeight(durationOfAverage, candleDTOList,index);
		double high = IndicatorsUtil.getHighLow(lookbackPeriod, candleDTOList, ApplicationConstants.HIGHEST,index);
		double low  = IndicatorsUtil.getHighLow(lookbackPeriod, candleDTOList, ApplicationConstants.LOWEST,index);
		double candleWick = 0;
		SimpleDateFormat dateFormatter = new SimpleDateFormat(ApplicationConstants.DATE_FORMAT_DD_MM_YYYY);
		DecimalFormat decimalFormatter = new DecimalFormat("##.00");
		if(dateFormatter.format(candleDTO.getTime()).equalsIgnoreCase("DD-MM-YYYY")){
			LOG.info("Candle Info for date : "+dateFormatter.format(candleDTO.getTime()));
			LOG.info("Average Candle Volume : "+averageVolume+" Candle Volume : "+candleDTO.getVolume());
			LOG.info("Average Candle Height : "+decimalFormatter.format(averageCandleHeight)+" Candle Height : "+decimalFormatter.format(Math.abs(candleDTO.getHigh()-candleDTO.getLow())));
			LOG.info("Candle Wick Long : "+decimalFormatter.format(candleDTO.getHigh()-candleDTO.getClose())+" Candle Wick Short : "+decimalFormatter.format(candleDTO.getClose()-candleDTO.getLow())+" Candle Height "+decimalFormatter.format(candleDTO.getHigh()-candleDTO.getLow()));
			LOG.info("Period High : "+high+" Period Low : "+low);
		}
		if(candleDTO.getVolume().compareTo(averageVolume.multiply(volumeMultiple))==1 && Math.abs(candleDTO.getHigh()-candleDTO.getLow()) > candleHightMultiple*averageCandleHeight ) {
			if(candleDTO.getClose()>high && candleDTO.getClose() > candleDTO.getOpen()) {
				candleWick =candleDTO.getHigh()-candleDTO.getClose();
				if(candleWick<=0.3*(candleDTO.getHigh()-candleDTO.getLow())) {
					entryDetails.put(ApplicationConstants.CANDLE,candleDTO);
					entryDetails.put(ApplicationConstants.TRADE_TYPE,ApplicationConstants.LONG);
//					LOG.info("Candle Info for date : "+dateFormatter.format(candleDTO.getTime()));
//					LOG.info("Average Candle Volume : "+averageVolume+" Candle Volume : "+candleDTO.getVolume());
//					LOG.info("Average Candle Height : "+decimalFormatter.format(averageCandleHeight)+" Candle Height : "+decimalFormatter.format(Math.abs(candleDTO.getHigh()-candleDTO.getLow())));
//					LOG.info("Candle Wick : "+decimalFormatter.format(candleWick)+" Candle Height "+decimalFormatter.format(candleDTO.getHigh()-candleDTO.getLow()));
				}
			}else if(candleDTO.getClose()<low && candleDTO.getClose() < candleDTO.getOpen()) {
				candleWick =candleDTO.getClose()-candleDTO.getLow();
				if(candleWick<=0.3*(candleDTO.getHigh()-candleDTO.getLow())) {
					entryDetails.put(ApplicationConstants.CANDLE,candleDTO);
					entryDetails.put(ApplicationConstants.TRADE_TYPE,ApplicationConstants.SHORT);
//					LOG.info("Candle Info for date : "+dateFormatter.format(candleDTO.getTime()));
//					LOG.info("Average Candle Volume : "+averageVolume+" Candle Volume : "+candleDTO.getVolume());
//					LOG.info("Average Candle Height : "+decimalFormatter.format(averageCandleHeight)+" Candle Height : "+decimalFormatter.format(Math.abs(candleDTO.getHigh()-candleDTO.getLow())));
//					LOG.info("Candle Wick : "+decimalFormatter.format(candleWick)+" Candle Height "+decimalFormatter.format(candleDTO.getHigh()-candleDTO.getLow()));
				}
			}
		}
		return entryDetails;
	}
	

	
	 /**
	 * Function to check if the trade has been exited.
	 * @param tradeDTO  TradeDTO containing details of the current trade.
	 * @param currentCandle The most recent candle.
	 * @param exitStrategy Exit strategy for the trade.
	 * @return tradeDTO
	 */
	public TradeDTO verifyExitParametersSatisfied(TradeDTO tradeDTO,CandleDTO currentCandle , ExitStrategy exitStrategy){
		// Checking if the trade has hit the Stop Loss or the Target
		if(tradeDTO.getTradeType().equalsIgnoreCase(ApplicationConstants.LONG)) {
			if(currentCandle.getLow() < tradeDTO.getExpectedStopLoss()) {
				tradeDTO.setExitPrice(tradeDTO.getExpectedStopLoss());
				tradeDTO.setEndTime(currentCandle.getTime());
				tradeDTO.setStatus(ApplicationConstants.CLOSED);
			}else if(currentCandle.getHigh() > tradeDTO.getExpectedTarget()){
				tradeDTO.setExitPrice(tradeDTO.getExpectedTarget());
				tradeDTO.setEndTime(currentCandle.getTime());
				tradeDTO.setStatus(ApplicationConstants.CLOSED);
			}
		}else if(tradeDTO.getTradeType().equalsIgnoreCase(ApplicationConstants.SHORT)) {
			if(currentCandle.getHigh() > tradeDTO.getExpectedStopLoss()) {
				tradeDTO.setExitPrice(tradeDTO.getExpectedStopLoss());
				tradeDTO.setEndTime(currentCandle.getTime());
				tradeDTO.setStatus(ApplicationConstants.CLOSED);
			}else if(currentCandle.getLow() < tradeDTO.getExpectedTarget()){
				tradeDTO.setExitPrice(tradeDTO.getExpectedTarget());
				tradeDTO.setEndTime(currentCandle.getTime());
				tradeDTO.setStatus(ApplicationConstants.CLOSED);
			}
		}
		// If trade is still open then recalculating the Stop Loss and the Target based on the choosen exit strategy
		if(tradeDTO.getStatus().equalsIgnoreCase(ApplicationConstants.OPENED)){
			ExitStrategies.getExitStrategyParmaters(tradeDTO,currentCandle, exitStrategy);
		}
		return tradeDTO;
	  }

	
	
}
