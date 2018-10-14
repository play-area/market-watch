package com.dao;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import com.constants.DatabaseConstants;
import com.model.Symbol;

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
	public List<Symbol> getWatchListSymbols(String marketWatch){ 
		List<Symbol> symbolList = new ArrayList<Symbol>();
		
		try{ 
		Map<String,String> databaseProperties = this.getDBProperties();
		
		Connection con = CreateDatabaseConnection.getMySQLConnection(databaseProperties.get("ip"),Integer.parseInt(databaseProperties.get("port")),databaseProperties.get("name"),databaseProperties.get("user"),databaseProperties.get("password"));  
		 
		if(con !=null){
			Statement stmt=con.createStatement();  
			ResultSet rs=stmt.executeQuery(DatabaseConstants.WATCHLIST_QUERY);
			while (rs.next()) {
	            Symbol symbol = new Symbol();
	            symbol.setSymbol(rs.getString(DatabaseConstants.WATCHLIST_COLUMN_SYMBOL));
	            symbol.setCompanyName(rs.getString(DatabaseConstants.WATCHLIST_COLUMN_COMPANY_NAME));
	            symbol.setSector(rs.getString(DatabaseConstants.WATCHLIST_COLUMN_SECTOR));
	            symbolList.add(symbol);
	        }
			con.close();  
			}
		}
		catch(Exception e)
		{ 
			System.out.println("Could not execute Watchlist Query because "+e);
		}
		return symbolList;	
	} 
}
