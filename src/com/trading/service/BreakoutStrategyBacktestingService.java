package com.trading.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.trading.constants.ApplicationConstants;
import com.trading.constants.DatabaseConstants;
import com.trading.constants.ExitStrategy;
import com.trading.dao.ManageDataDAO;
import com.trading.model.BackTestingOutputDTO;
import com.trading.model.CandleDTO;
import com.trading.model.TradeDTO;
import com.trading.strategy.BreakoutStrategy;
import com.trading.util.DataUtil;

public class BreakoutStrategyBacktestingService {
	
	private static Logger LOG = LogManager.getLogger();
	
	public static void main(String args[]) {
		BreakoutStrategy breakoutStrategy = new BreakoutStrategy();
		ManageDataDAO manageDataDAO = new ManageDataDAO();
		List<String> symbolList = getWatchList();
		List<List<CandleDTO>> candleList = new ArrayList<List<CandleDTO>>();
		candleList = manageDataDAO.getCandlesData(DatabaseConstants.DATA_QUANDL_DAILY, symbolList);
		for(int i=0;i<candleList.get(1).size();i++) {
			CandleDTO currentCandle = candleList.get(1).get(i);
			//Skipping the first durationOfAverage candle(s) as they are needed for calcualting averages
			if(i>50){
				Map entryMap = breakoutStrategy.getEntryCandle(candleList.get(1),currentCandle,i, 20 , 50,  1.5, 0.3, new BigDecimal(1.5));
				if(entryMap.containsKey(ApplicationConstants.CANDLE)){
					CandleDTO candle = (CandleDTO)entryMap.get(ApplicationConstants.CANDLE);
					LOG.info("ENTRY SIGNAL : "+ candle.getSymbol() + " Date : "+candle.getTime()+" Trade Type : "+entryMap.get(ApplicationConstants.TRADE_TYPE));
				}
			}
		}
		for(List<CandleDTO> candleDTOList : candleList) {
			List<TradeDTO> listTradeDTO = breakoutStrategy.executeBreakoutStrategy(candleDTOList, 20, 50, 1.5, 0.3, new BigDecimal(1.5), ExitStrategy.RiskRewardOneTwo, 3000);
			LOG.info("######################### The following trades were taken ##############################");
			for(TradeDTO tradeDTO : listTradeDTO) {
				LOG.info("Trade details : "+ tradeDTO.getStartTime() + " TRADE TYPE "+tradeDTO.getTradeType()+" ENTRY PRICE "+ tradeDTO.getEntryPrice()+" EXIT PRICE "+tradeDTO.getExitPrice());
			}
			BackTestingOutputDTO backTestingOutputDTO = DataUtil.getBackTestingResults(listTradeDTO);
			LOG.info("#############Backtesting Output##################");
			LOG.info("TOTAL TRADES : "+backTestingOutputDTO.getTotalTrades());
			LOG.info("WINNING TRADES : "+backTestingOutputDTO.getTotalWinnigTrades());
			LOG.info("LOOSING TRADES : "+backTestingOutputDTO.getTotalLoosingTrades());
			LOG.info("WIN PERCENTAGE : "+backTestingOutputDTO.getWinPercentage());
			LOG.info("TOTAL PROFIT LOSS : "+backTestingOutputDTO.getTotalProfitLoss());
		}
		
	}
	
	/**
	 * Function to get data from Quandl
	 * @param List<String> symbolList List of symbols for which data needs to be fetched.
	 * @return List<String>
	 */
	public static List<String> getWatchList(){
		List<String> symbolList = new ArrayList<String>();
		symbolList.add("SBIN");
		symbolList.add("HDFCBANK");
		symbolList.add("VEDL");
		symbolList.add("HINDALCO");
		symbolList.add("MARUTI");
		symbolList.add("TATAMOTORS");
		symbolList.add("TCS");
		symbolList.add("INFY");
		symbolList.add("SUNPHARMA");
		symbolList.add("LUPIN");
		return symbolList;
	}
}
