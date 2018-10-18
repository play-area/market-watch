package com.model;

public class TradeDTO {
	
	Double entryPrice;
	Double exitPrice;
	Integer volume;
	String status;
	Double profitLoss;
	Integer tradeDuration;
	
	
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
	public Integer getTradeDuration() {
		return tradeDuration;
	}
	public void setTradeDuration(Integer tradeDuration) {
		this.tradeDuration = tradeDuration;
	}
	
}
