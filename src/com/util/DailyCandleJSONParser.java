package com.util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.model.QuandlResponseDTO;

public class DailyCandleJSONParser {
	
	
	
	public  QuandlResponseDTO fromJSON(String json) {
		
		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		
		QuandlResponseDTO responseDTO =  (QuandlResponseDTO) gson.fromJson(json, QuandlResponseDTO.class);
		return responseDTO;
		
	}

}
