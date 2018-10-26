package com.trading.model;

import java.util.List;

public class QuandlDataSetDTO {
	
	
	private String id;
	private String dataset_code;
	private String database_code;
	private String name;
	private String description;
	private String refreshed_at;
	private String newest_available_date;
	private String oldest_available_date;
	private List<String> column_names;
	private String frequency;
	private String type;
	private String premium;
	private String limit;
	private String transform;
	private String column_index;
	private String start_date;
	private String end_date;
	private List<List<String>> data;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getDataset_code() {
		return dataset_code;
	}
	public void setDataset_code(String dataset_code) {
		this.dataset_code = dataset_code;
	}
	public String getDatabase_code() {
		return database_code;
	}
	public void setDatabase_code(String database_code) {
		this.database_code = database_code;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getRefreshed_at() {
		return refreshed_at;
	}
	public void setRefreshed_at(String refreshed_at) {
		this.refreshed_at = refreshed_at;
	}
	public String getNewest_available_date() {
		return newest_available_date;
	}
	public void setNewest_available_date(String newest_available_date) {
		this.newest_available_date = newest_available_date;
	}
	public String getOldest_available_date() {
		return oldest_available_date;
	}
	public void setOldest_available_date(String oldest_available_date) {
		this.oldest_available_date = oldest_available_date;
	}
	public List<String> getColumn_names() {
		return column_names;
	}
	public void setColumn_names(List<String> column_names) {
		this.column_names = column_names;
	}
	public String getFrequency() {
		return frequency;
	}
	public void setFrequency(String frequency) {
		this.frequency = frequency;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getPremium() {
		return premium;
	}
	public void setPremium(String premium) {
		this.premium = premium;
	}
	public String getLimit() {
		return limit;
	}
	public void setLimit(String limit) {
		this.limit = limit;
	}
	public String getTransform() {
		return transform;
	}
	public void setTransform(String transform) {
		this.transform = transform;
	}
	public String getColumn_index() {
		return column_index;
	}
	public void setColumn_index(String column_index) {
		this.column_index = column_index;
	}
	public String getStart_date() {
		return start_date;
	}
	public void setStart_date(String start_date) {
		this.start_date = start_date;
	}
	public String getEnd_date() {
		return end_date;
	}
	public void setEnd_date(String end_date) {
		this.end_date = end_date;
	}
	public List<List<String>> getData() {
		return data;
	}
	public void setData(List<List<String>> data) {
		this.data = data;
	}
}
