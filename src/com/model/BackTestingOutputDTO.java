package com.model;

public class BackTestingOutputDTO {
	
	Double initialCapital;
	Double finalCapital;
	Double totalProfitLoss;
	Double profitLossPercentage;
	Integer totalTrades;
	Integer totalWinnigTrades;
	Integer totalLoosingTrades;
	Double winPercentage;
	Double averageWinSize;
	Double averageLossSize;
	Integer winningStreakSize;
	Integer loosingStreakSize;
	Double maxDrawDown;
	Integer averageTradeDuration;
	Double expectancy;
	
	public Double getInitialCapital() {
		return initialCapital;
	}
	public void setInitialCapital(Double initialCapital) {
		this.initialCapital = initialCapital;
	}
	public Double getFinalCapital() {
		return finalCapital;
	}
	public void setFinalCapital(Double finalCapital) {
		this.finalCapital = finalCapital;
	}
	public Double getTotalProfitLoss() {
		return totalProfitLoss;
	}
	public void setTotalProfitLoss(Double totalProfitLoss) {
		this.totalProfitLoss = totalProfitLoss;
	}
	public Double getProfitLossPercentage() {
		return profitLossPercentage;
	}
	public void setProfitLossPercentage(Double profitLossPercentage) {
		this.profitLossPercentage = profitLossPercentage;
	}
	public Integer getTotalTrades() {
		return totalTrades;
	}
	public void setTotalTrades(Integer totalTrades) {
		this.totalTrades = totalTrades;
	}
	public Integer getTotalWinnigTrades() {
		return totalWinnigTrades;
	}
	public void setTotalWinnigTrades(Integer totalWinnigTrades) {
		this.totalWinnigTrades = totalWinnigTrades;
	}
	public Integer getTotalLoosingTrades() {
		return totalLoosingTrades;
	}
	public void setTotalLoosingTrades(Integer totalLoosingTrades) {
		this.totalLoosingTrades = totalLoosingTrades;
	}
	public Double getWinPercentage() {
		return winPercentage;
	}
	public void setWinPercentage(Double winPercentage) {
		this.winPercentage = winPercentage;
	}
	public Double getAverageWinSize() {
		return averageWinSize;
	}
	public void setAverageWinSize(Double averageWinSize) {
		this.averageWinSize = averageWinSize;
	}
	public Double getAverageLossSize() {
		return averageLossSize;
	}
	public void setAverageLossSize(Double averageLossSize) {
		this.averageLossSize = averageLossSize;
	}
	public Integer getWinningStreakSize() {
		return winningStreakSize;
	}
	public void setWinningStreakSize(Integer winningStreakSize) {
		this.winningStreakSize = winningStreakSize;
	}
	public Integer getLoosingStreakSize() {
		return loosingStreakSize;
	}
	public void setLoosingStreakSize(Integer loosingStreakSize) {
		this.loosingStreakSize = loosingStreakSize;
	}
	public Double getMaxDrawDown() {
		return maxDrawDown;
	}
	public void setMaxDrawDown(Double maxDrawDown) {
		this.maxDrawDown = maxDrawDown;
	}
	public Integer getAverageTradeDuration() {
		return averageTradeDuration;
	}
	public void setAverageTradeDuration(Integer averageTradeDuration) {
		this.averageTradeDuration = averageTradeDuration;
	}
	public Double getExpectancy() {
		return expectancy;
	}
	public void setExpectancy(Double expectancy) {
		this.expectancy = expectancy;
	}
	
}
