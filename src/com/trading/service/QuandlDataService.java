package com.trading.service;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.quandl.connection.GetQuandlData;
import com.trading.dao.ManageDataDAO;
import com.trading.model.CandleDTO;

public class QuandlDataService {
	private static Logger LOG = LogManager.getLogger();
	
	public static void main(String[] args) {
		GetQuandlData getQuandlData = new GetQuandlData();
		ManageDataDAO manageDataDao = new ManageDataDAO();
		List<List<CandleDTO>> candleList = new ArrayList<List<CandleDTO>>();
		List<String> symbolList = getWatchList();
		LOG.info("Fetching data from Quandl for : "+symbolList);
		try {
			//Getting data from Quandl API
			candleList = getQuandlData.getDataFromQuandl(symbolList);
			//Inserting Quandl data into Database
			for(List<CandleDTO> candleDTOList : candleList) {
				if(CollectionUtils.isNotEmpty(candleDTOList)){
					LOG.info("Data received from Quandl for Symbol "+ candleDTOList.get(0).getSymbol() +" has records :"+candleDTOList.size());
					LOG.info("Records inserted into database : "+manageDataDao.insertDailyCandleData(candleDTOList));
				}
			}
		} catch (Exception e) {
			LOG.error("Exception thrown :"+e);
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

