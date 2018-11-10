package com.trading.constants;

public enum StopLossStrategy {
	
	BeyondEntryCandle,
	OneTimesCandleHeightStop,
	TwoTimesCandleHeightStop,
	ThreeTimesCandleHeightStop,
	FourTimesCandleHeightStop,
	MaxOfCandleBeyondAndOneTimesCandleHeight,
	MaxOfCandleBeyondAndOneHalfTimesCandleHeight,
	MaxOfCandleBeyondAndTwoTimesCandleHeight,
	MaxOfCandleBeyondAndThreeTimesCandleHeight,
	OneTimesATRStop,
	TwoTimesATRStop,
	ThreeTimesATRStop,
}
