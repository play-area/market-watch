package com.trading.model;

import java.util.List;

public class DataSetDTO {
	
	private String id;
	
	private List<List<String>> data;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public List<List<String>> getData() {
		return data;
	}

	public void setData(List<List<String>> data) {
		this.data = data;
	}
	
	

}
