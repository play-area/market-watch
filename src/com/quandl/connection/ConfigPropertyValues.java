package com.quandl.connection;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ConfigPropertyValues {
	
	public String url = "";
	InputStream inputStream;
 
	public ConfigPropertyValues() throws IOException {
 
		try {
			Properties prop = new Properties();
			String propFileName = "Config.properties";
 
			inputStream = getClass().getClassLoader().getResourceAsStream(propFileName);
 
			if (inputStream != null) {
				prop.load(inputStream);
			} else {
				throw new FileNotFoundException("property file '" + propFileName + "' not found in the classpath");
			}
 
			//Date time = new Date(System.currentTimeMillis());
 
			// get the property value and print it out
			String quandl_base_url = prop.getProperty("quandl_base_url");
			String exchange = prop.getProperty("exchange");
			String stock1 = prop.getProperty("stock1");
			String quandl_api_key = prop.getProperty("quandl_api_key");
			String start_date = prop.getProperty("start_date");
			String end_date = prop.getProperty("end_date");
 
			url = quandl_base_url + "/"+exchange+"/"+stock1+"?api_key="+quandl_api_key+"&start_date="+start_date
					+"&end_date="+end_date;
			//System.out.println(result + "\nProgram Ran on " + time + " by user=" + user);
		} catch (Exception e) {
			System.out.println("Exception: " + e);
		} finally {
			inputStream.close();
		}
		//return url;
	}

}
