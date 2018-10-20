package com.model;

import java.util.Date;

public class TradeDTO {
	
	Double entryPrice;
	Double exitPrice;
	Integer volume;
	Date startTime;
	Date endTime;
	String status;
	Double profitLoss;
	
	public Double getEntryPrice() {
		return entryPrice;
	}
	public void setEntryPrice(Double entryPrice) {
		this.entryPrice = entryPrice;
	}
	public Double getExitPrice() {
		return exitPrice;
	}
	public void setExitPrice(Double exitPrice) {
		this.exitPrice = exitPrice;
	}
	public Integer getVolume() {
		return volume;
	}
	public void setVolume(Integer volume) {
		this.volume = volume;
	}
	public Date getStartTime() {
		return startTime;
	}
	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}
	public Date getEndTime() {
		return endTime;
	}
	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public Double getProfitLoss() {
		return profitLoss;
	}
	public void setProfitLoss(Double profitLoss) {
		this.profitLoss = profitLoss;
	}
	
}
