package com.trading.dao;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Array;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.trading.constants.DatabaseConstants;
import com.trading.model.CandleDTO;
import com.trading.model.SymbolDTO;

public class ManageDataDAO {
	
	private static Logger LOG = LogManager.getLogger();
	
	/**
	 * Function to get Database Properties from Property File
	 * @return List<String>
	 * @throws IOException 
	 */
	public Map<String,String> getDBProperties() throws IOException{
		Map<String,String> databaseProperties = new HashMap<String,String>();
		
		Properties prop = new Properties();
		String propFileName = "Config.properties";

		InputStream inputStream = getClass().getClassLoader().getResourceAsStream(propFileName);

		if (inputStream != null) {
			prop.load(inputStream);
		} else {
			throw new FileNotFoundException("Property file " + propFileName + " Not found in the classpath");
		}
		databaseProperties.put("ip",prop.getProperty(DatabaseConstants.MYSQL_REMOTE_IP));
		databaseProperties.put("port",prop.getProperty(DatabaseConstants.MYSQL_REMOTE_PORT));
		databaseProperties.put("name",prop.getProperty(DatabaseConstants.MYSQL_REMOTE_DB_NAME));
		databaseProperties.put("user",prop.getProperty(DatabaseConstants.MYSQL_REMOTE_DB_USER));
		databaseProperties.put("password",prop.getProperty(DatabaseConstants.MYSQL_REMOTE_DB_PASSWORD));
		return databaseProperties;
	}
	
	/**
	 * Function to get the symbols details for a particular watchlist
	 * @param marketWatch Marketwatch name
	 * @return List<Symbol>
	 */
	public List<SymbolDTO> getWatchListSymbols(String marketWatch){ 
		List<SymbolDTO> symbolList = new ArrayList<SymbolDTO>();
		
		try{ 
		Map<String,String> databaseProperties = this.getDBProperties();
		
		Connection con = CreateDatabaseConnection.getMySQLConnection(databaseProperties.get("ip"),Integer.parseInt(databaseProperties.get("port")),databaseProperties.get("name"),databaseProperties.get("user"),databaseProperties.get("password"));  
		 
		if(con !=null){
			Statement stmt=con.createStatement();  
			ResultSet rs=stmt.executeQuery(DatabaseConstants.WATCHLIST_QUERY);
			while (rs.next()) {
	            SymbolDTO symbol = new SymbolDTO();
	            symbol.setSymbol(rs.getString(DatabaseConstants.WATCHLIST_COLUMN_SYMBOL));
	            symbol.setCompanyName(rs.getString(DatabaseConstants.WATCHLIST_COLUMN_COMPANY_NAME));
	            symbol.setSector(rs.getString(DatabaseConstants.WATCHLIST_COLUMN_SECTOR));
	            symbolList.add(symbol);
	        }
			con.close();  
			}
		}
		catch(Exception e){ 
			LOG.error("Could not execute Watchlist Query because "+e);
		}
		return symbolList;	
	} 
	
	/**
	 * Function to get the the Candle(s) from a particular table
	 * @param tableName table name
	 * @param List<String> symbols
	 * @return List<CandleDTO>
	 */
	public List<List<CandleDTO>> getCandlesData(String tableName, List<String> symbols){ 
		List<List<CandleDTO>> candleList = new ArrayList<List<CandleDTO>>();
		
		try{ 
			Map<String,String> databaseProperties = this.getDBProperties();
			Connection con = CreateDatabaseConnection.getMySQLConnection(databaseProperties.get("ip"),Integer.parseInt(databaseProperties.get("port")),databaseProperties.get("name"),databaseProperties.get("user"),databaseProperties.get("password"));  
			 
			if(con !=null && CollectionUtils.isNotEmpty(symbols)){
				for(String symbol:symbols) {
					List<CandleDTO> candleDTOList = new ArrayList<CandleDTO>();
					PreparedStatement stmt=con.prepareStatement(DatabaseConstants.CANDLES_QUERY_1+tableName+DatabaseConstants.CANDLES_QUERY_2); 
					stmt.setString(1,symbol); 
					ResultSet rs = stmt.executeQuery();
					while(rs.next()) {
						CandleDTO candle = new CandleDTO();
						candle.setTime(new java.util.Date(rs.getDate(1).getTime()));
						candle.setSymbol(rs.getString(2));
						candle.setOpen(rs.getDouble(3));
						candle.setHigh(rs.getDouble(4));
						candle.setLow(rs.getDouble(5));
						candle.setClose(rs.getDouble(6));
						candle.setVolume(rs.getBigDecimal(7));
						candleDTOList.add(candle);
						}
					candleList.add(candleDTOList);
				}
				con.close();  
			}
		}catch(Exception e){ 
			LOG.error("Could not execute Get Candle(s) Query because "+e);
		}
		return candleList;	
	} 
	
	/**
	 * Function to insert daily Candlestick(s) data into database
	 * @param List<DailyCandle> List of Daily Candle Data
	 * @return int Count of records updated
	 */
	public int insertDailyCandleData(List<List<CandleDTO>> candleList){ 
		int recordsUpdated = 0;
		try{ 
			Map<String,String> databaseProperties = this.getDBProperties();
			Connection con = CreateDatabaseConnection.getMySQLConnection(databaseProperties.get("ip"),Integer.parseInt(databaseProperties.get("port")),databaseProperties.get("name"),databaseProperties.get("user"),databaseProperties.get("password"));  
			
			if(con !=null && CollectionUtils.isNotEmpty(candleList) ){ 
				for(List<CandleDTO> candleDTOList: candleList) {
					Iterator<CandleDTO> listIterator = candleDTOList.iterator();
					while (listIterator.hasNext()) {
						CandleDTO dailyCandle = listIterator.next();
						PreparedStatement stmt=con.prepareStatement("insert into data_quandl_daily values(?,?,?,?,?,?,?)");  
						stmt.setDate(1,java.sql.Date.valueOf(dailyCandle.getTime().toString()));
						stmt.setString(2,dailyCandle.getSymbol());  
						stmt.setDouble(3, dailyCandle.getOpen());
						stmt.setDouble(4, dailyCandle.getHigh());
						stmt.setDouble(5, dailyCandle.getLow());
						stmt.setDouble(6, dailyCandle.getClose());
						stmt.setBigDecimal(7, dailyCandle.getVolume());
						recordsUpdated = recordsUpdated+stmt.executeUpdate();
					}
					LOG.info(recordsUpdated+" records inserted");
					System.out.println(recordsUpdated+" records inserted");  
					con.close();  
				}
			}
		}catch(Exception e){ 
			LOG.error("Could not insert Candle(s) records because "+e);
		}
		return recordsUpdated;
		} 
	}

