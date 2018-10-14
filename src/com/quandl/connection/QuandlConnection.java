package com.quandl.connection;

import java.io.*;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import com.dao.ManageDataDAO;

public class QuandlConnection {

	public static void main(String[] args) {
		HttpClient client = new DefaultHttpClient();
		//api URL
		HttpGet request = new HttpGet("https://www.quandl.com/api/v3/datasets/NSE/VEDL.json?api_key=UXsVDnMsYhtX_qVmVVLR");
		HttpResponse response;
		try {
			response = client.execute(request);
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