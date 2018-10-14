package com.quandl.connection;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import com.model.DailyCandleDTO;
import com.model.QuandlResponseDTO;
import com.util.DailyCandleJSONParser;
import com.util.DataUtil;

public class QuandlConnection {

	public static void main(String[] args) throws IOException {
		HttpClient client = new DefaultHttpClient();
		//api URL
		ConfigPropertyValues configProperty = new ConfigPropertyValues();
		String url = configProperty.url;
		HttpGet request = new HttpGet(url);
		HttpResponse response;
		try {
			response = client.execute(request);
			
			DailyCandleJSONParser jsonParser = new DailyCandleJSONParser();
			
			
			
			QuandlResponseDTO data = jsonParser.fromJSON(EntityUtils.toString(response.getEntity()));
			
			List<DailyCandleDTO> dailyCandleData = DataUtil.getDailyCandleDataList(data);
		
			// Get the response
			BufferedReader br;
			
			br = new BufferedReader(new InputStreamReader(response
						.getEntity().getContent()));
			
			String line = "";
			while ((line = br.readLine()) != null) {
				System.out.println(line);
			}
		} catch (IOException e) {
			e.printStackTrace();
		} catch (UnsupportedOperationException e) {
			e.printStackTrace();
		}
	}

}