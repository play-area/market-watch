package com.trading.service;

import java.util.ArrayList;
import java.util.List;

import com.trading.constants.ExitStrategy;
import com.trading.model.BackTestingOutputDTO;
import com.trading.model.CandleDTO;
import com.trading.model.TradeDTO;
import com.trading.strategy.BreakoutStrategy;
import com.trading.util.DataUtil;

public class BreakoutStrategyBacktestingService {
	
	public static void main(String args[]) {
		BreakoutStrategy breakoutStrategy = new BreakoutStrategy();
		List<CandleDTO> candleDTOList = new ArrayList<CandleDTO>();
		List<TradeDTO> listTradeDTO = breakoutStrategy.executeBreakoutStrategy(candleDTOList,20, 1, 0.25, 1.5, ExitStrategy.RiskRewardOneOne, 50000);
		BackTestingOutputDTO backTestingOutputDTO = DataUtil.getBackTestingResults(listTradeDTO);
	}
	
}
