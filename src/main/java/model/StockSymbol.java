package model;

import java.io.Serializable;

public class StockSymbol implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -4471211171911068313L;
	/*
	 * When data is collected.
	 */
	private long timeStamp;
	/**
	 * Stock symbol. Sample values are (Stock exchange (Shanghai, BSE etc.); 3
	 * month, 6 month (for bonds), EUR/USD (currencies))
	 */
	private String symbol;
	
	/**
	 * Price at the time stamp
	 */
	private double price;
	
	/**
	 * Price change amount
	 */
	private double priceChange;
	
	/**
	 * Change percentage.
	 */
	private float changePercentage;

	public double getPriceChange() {
		return priceChange;
	}

	public void setPriceChange(double priceChange) {
		this.priceChange = priceChange;
	}
	
	

	

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((symbol == null) ? 0 : symbol.hashCode());
		result = prime * result + (int) (timeStamp ^ (timeStamp >>> 32));
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		StockSymbol other = (StockSymbol) obj;
		if (symbol == null) {
			if (other.symbol != null)
				return false;
		} else if (!symbol.equals(other.symbol))
			return false;
		if (timeStamp != other.timeStamp)
			return false;
		return true;
	}

	public long getTimeStamp() {
		return timeStamp;
	}

	public void setTimeStamp(long timeStamp) {
		this.timeStamp = timeStamp;
	}

	public String getSymbol() {
		return symbol;
	}

	public void setSymbol(String symbol) {
		this.symbol = symbol;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public float getChangePercentage() {
		return changePercentage;
	}

	public void setChangePercentage(float changePercentage) {
		this.changePercentage = changePercentage;
	}

	@Override
	public String toString() {
		return "StockSymbol [timeStamp=" + timeStamp + ", symbol=" + symbol + ", price=" + price + ", priceChange="
				+ priceChange + ", changePercentage=" + changePercentage + "]";
	}

	
	
}
