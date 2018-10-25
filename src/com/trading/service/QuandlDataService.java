package com.trading.service;

import java.util.ArrayList;
import java.util.List;

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
		List<String> symbolList = new ArrayList<String>();
		symbolList.add("VEDL");
		symbolList.add("SBIN");
		LOG.info("Symbol List for getting data from Quandl"+symbolList);
		try {
			//Getting data from Quandl API
			candleList = getQuandlData.getDataFromQuandl(symbolList);
			//Inserting Quandl data into Database
			manageDataDao.insertDailyCandleData(candleList);
			
			
		} catch (Exception e) {
			System.out.println("Exception thrown :"+e);
		}
	}

}
