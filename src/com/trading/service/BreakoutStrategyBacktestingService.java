package com.trading.service;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.trading.constants.ApplicationConstants;
import com.trading.constants.DatabaseConstants;
import com.trading.constants.ExitStrategy;
import com.trading.constants.PositionSizingStrategy;
import com.trading.constants.StopLossStrategy;
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
		List<TradeDTO> listTradeDTO = new ArrayList<TradeDTO>();
		SimpleDateFormat dateFormatter = new SimpleDateFormat(ApplicationConstants.DATE_FORMAT_DD_MM_YYYY);
		DecimalFormat decimalFormatter = new DecimalFormat("##.00");
		candleList = manageDataDAO.getCandlesData(DatabaseConstants.DATA_QUANDL_DAILY, symbolList);
		for(int i=0;i<candleList.size();i++) {
			for(int j=0;j<candleList.get(i).size();j++) {
				//Skipping the first durationOfAverage candle(s) as they are needed for calcualting averages
				if(j>50){
					CandleDTO currentCandle = candleList.get(i).get(j);
					Map entryMap = breakoutStrategy.getEntryCandle(candleList.get(i),currentCandle,j, 20 , 50,  1.5, 0.3, new BigDecimal(1.5));
					if(entryMap.containsKey(ApplicationConstants.CANDLE)){
						CandleDTO candle = (CandleDTO)entryMap.get(ApplicationConstants.CANDLE);
						LOG.info("ENTRY SIGNAL : "+ candle.getSymbol() + " Date : "+candle.getTime()+" Trade Type : "+entryMap.get(ApplicationConstants.TRADE_TYPE));
					}
				}
			}
			listTradeDTO.addAll(breakoutStrategy.executeBreakoutStrategy(candleList.get(i), 20, 50, 1.5, 0.3, new BigDecimal(1.5), ExitStrategy.RiskRewardOneTwo,PositionSizingStrategy.RiskThreePercentOfAccount, StopLossStrategy.beyondEntryCandle ,500000));
		}
		LOG.info("######################### The following trades were taken ##############################");
		for(TradeDTO tradeDTO : listTradeDTO) {
			LOG.info("\t"+tradeDTO.getEntryCandle().getSymbol()+"\t"+tradeDTO.getTradeType()+"\t"+tradeDTO.getSize()+" Shares\tENTRY PRICE : "+decimalFormatter.format(tradeDTO.getEntryPrice())+"\tEXIT PRICE : "+decimalFormatter.format(tradeDTO.getExitPrice()!=null?tradeDTO.getExitPrice():0.0)+"\tSTART TIME : "+dateFormatter.format(tradeDTO.getStartTime())+"\tEND TIME :"+dateFormatter.format(tradeDTO.getEndTime()!=null?tradeDTO.getEndTime():new Date()));
		}
		//Getting the Backtesting results of the overall strategy performance
		BackTestingOutputDTO backTestingOutputDTO = DataUtil.getBackTestingResults(listTradeDTO);
		LOG.info("#############Backtesting Output##################");
		LOG.info("TOTAL TRADES : "+backTestingOutputDTO.getTotalTrades());
		LOG.info("WINNING TRADES : "+backTestingOutputDTO.getTotalWinnigTrades());
		LOG.info("LOOSING TRADES : "+backTestingOutputDTO.getTotalLoosingTrades());
		LOG.info("WIN PERCENTAGE : "+backTestingOutputDTO.getWinPercentage());
		LOG.info("AVERAGE LOSS SIZE : "+backTestingOutputDTO.getAverageLossSize());
		LOG.info("AVERAGE WIN SIZE : "+backTestingOutputDTO.getAverageWinSize());
		LOG.info("MAX DRAWDOWN : "+backTestingOutputDTO.getMaxDrawDown());
		LOG.info("MAX No. of loosing trades in a row : "+backTestingOutputDTO.getLoosingStreakSize());
		LOG.info("TOTAL PROFIT LOSS : "+backTestingOutputDTO.getTotalProfitLoss());
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
