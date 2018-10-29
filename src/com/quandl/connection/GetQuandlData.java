package com.quandl.connection;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import com.trading.model.CandleDTO;
import com.trading.model.QuandlResponseDTO;
import com.trading.util.DailyCandleJSONParser;
import com.trading.util.DataUtil;

public class GetQuandlData {

	private static Logger LOG = LogManager.getLogger();
	
	/**
	 * Function to get data from Quandl
	 * @param List<String> symbolList List of symbols for which data needs to be fetched.
	 * @return List<List<CandleDTO>>
	 */
	public List<List<CandleDTO>> getDataFromQuandl(List<String> symbolList) throws IOException{
		List<List<CandleDTO>> candleList = new ArrayList<List<CandleDTO>>();
		HttpClient client = new DefaultHttpClient();
		List<String> quandlURL = getQuandlURL();
		if(CollectionUtils.isNotEmpty(symbolList)){
			for(String symbol: symbolList) {
				String url = quandlURL.get(0)+symbol+quandlURL.get(1);
				List<CandleDTO> candleDTOList = new ArrayList<CandleDTO>();
				HttpGet request = new HttpGet(url);
				HttpResponse response;
				try {
					response = client.execute(request);
					DailyCandleJSONParser jsonParser = new DailyCandleJSONParser();
					QuandlResponseDTO quandlResponse = jsonParser.fromJSON(EntityUtils.toString(response.getEntity()));
					candleDTOList = DataUtil.getDailyCandleDataList(quandlResponse);
				} catch (Exception e) {
					LOG.error("Error Occured while getting data for symbol : "+ symbol  +" from Quandl "+e);
				}
				candleList.add(candleDTOList);
			}
		}
		return candleList;
	}
	
	/**
	 * Function to get data from Quandl
	 * @return List<String> Quandl URL from properties file
	 */
	public List<String> getQuandlURL() throws IOException {
		String url1 = StringUtils.EMPTY;
		String url2 = StringUtils.EMPTY;
		List<String> urlList = new ArrayList<String>();
		InputStream inputStream = null;
		try {
			Properties prop = new Properties();
			inputStream = getClass().getClassLoader().getResourceAsStream("Config.properties");
 
			if (inputStream != null) {
				prop.load(inputStream);
			} else {
				throw new FileNotFoundException("property file Config.properties not found in the classpath");
			}
 
			String quandl_base_url = prop.getProperty("quandl_base_url");
			String exchange = prop.getProperty("exchange");
			String quandl_api_key = prop.getProperty("quandl_api_key");
			String start_date = prop.getProperty("start_date");
			String end_date = prop.getProperty("end_date");
 
			url1 = quandl_base_url + "/"+exchange+"/";
			url2=".json?api_key="+quandl_api_key+"&start_date="+start_date+"&end_date="+end_date;
			
		} catch (Exception e) {
			LOG.error("Error Occured while getting Quandl URL"+e);
		} finally {
			inputStream.close();
		}
		urlList.add(url1);
		urlList.add(url2);
		return urlList;
	}

}