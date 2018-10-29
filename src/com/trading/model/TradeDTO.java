package com.trading.model;

import java.util.Date;

public class TradeDTO {
	
	Double entryPrice;
	Double exitPrice;
	Double expectedStopLoss;
	Double expectedTarget;
	CandleDTO entryCandle;
	Integer size;
	Date startTime;
	Date endTime;
	String status;
	String tradeType;
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
	public Double getExpectedStopLoss() {
		return expectedStopLoss;
	}
	public void setExpectedStopLoss(Double expectedStopLoss) {
		this.expectedStopLoss = expectedStopLoss;
	}
	public Double getExpectedTarget() {
		return expectedTarget;
	}
	public void setExpectedTarget(Double expectedTarget) {
		this.expectedTarget = expectedTarget;
	}
	public CandleDTO getEntryCandle() {
		return entryCandle;
	}
	public void setEntryCandle(CandleDTO entryCandle) {
		this.entryCandle = entryCandle;
	}
	public Integer getSize() {
		return size;
	}
	public void setSize(Integer size) {
		this.size = size;
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
	public String getTradeType() {
		return tradeType;
	}
	public void setTradeType(String tradeType) {
		this.tradeType = tradeType;
	}
	public Double getProfitLoss() {
		return profitLoss;
	}
	public void setProfitLoss(Double profitLoss) {
		this.profitLoss = profitLoss;
	}

}
