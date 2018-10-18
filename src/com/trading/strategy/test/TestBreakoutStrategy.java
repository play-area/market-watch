package com.trading.strategy.test;

import java.util.ArrayList;
import java.util.List;

import com.constants.ExitStrategy;
import com.model.BackTestingOutputDTO;
import com.model.DailyCandleDTO;
import com.model.TradeDTO;
import com.trading.strategy.BreakoutStrategy;
import com.util.DataUtil;

public class TestBreakoutStrategy {
	
	public static void main(String args[]) {
		BreakoutStrategy breakoutStrategy = new BreakoutStrategy();
		List<DailyCandleDTO> candleDTOList = new ArrayList<DailyCandleDTO>();
		List<TradeDTO> listTradeDTO = breakoutStrategy.executeBreakoutStrategy(candleDTOList,20, 1, 0.25, 1.5, ExitStrategy.RiskRewardOneOne, 50000);
		BackTestingOutputDTO backTestingOutputDTO = DataUtil.getBackTestingResults(listTradeDTO);
	}
	
}
