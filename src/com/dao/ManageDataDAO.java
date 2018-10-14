package com.dao;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
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

import com.constants.DatabaseConstants;
import com.model.DailyCandleDTO;
import com.model.SymbolDTO;

public class ManageDataDAO {
	
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
			System.out.println("Could not execute Watchlist Query because "+e);
		}
		return symbolList;	
	} 
	
	/**
	 * Function to insert daily Candlestick(s) data into database
	 * @param List<DailyCandle> List of Daily Candle Data
	 * @return int Count of records updated
	 */
	public int insertDailyCandleData(List<DailyCandleDTO> candleList){ 
		int recordsUpdated = 0;
		try{ 
			Map<String,String> databaseProperties = this.getDBProperties();
			Connection con = CreateDatabaseConnection.getMySQLConnection(databaseProperties.get("ip"),Integer.parseInt(databaseProperties.get("port")),databaseProperties.get("name"),databaseProperties.get("user"),databaseProperties.get("password"));  
			 
			if(con !=null){
				Iterator<DailyCandleDTO> listIterator = candleList.iterator();
				while (listIterator.hasNext()) {
					DailyCandleDTO dailyCandle = listIterator.next();
					PreparedStatement stmt=con.prepareStatement("insert into data_quandl_daily values(?,?,?,?,?,?,?)");  
					stmt.setString(1,dailyCandle.getTime());
					stmt.setString(2,dailyCandle.getSymbol());  
					stmt.setDouble(3, dailyCandle.getOpen());
					stmt.setDouble(4, dailyCandle.getHigh());
					stmt.setDouble(5, dailyCandle.getLow());
					stmt.setDouble(6, dailyCandle.getClose());
					stmt.setBigDecimal(7, dailyCandle.getVolume());
					recordsUpdated = recordsUpdated+stmt.executeUpdate();
				}
				System.out.println(recordsUpdated+" records inserted");  
				  
				con.close();  
			}
		}
		catch(Exception e){ 
				System.out.println("Could not execute Watchlist Query because "+e);
			}
		return recordsUpdated;
		} 
	}

