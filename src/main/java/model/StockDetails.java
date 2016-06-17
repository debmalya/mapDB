package model;

import java.io.Serializable;

import com.googlecode.cqengine.attribute.Attribute;
import com.googlecode.cqengine.attribute.SimpleAttribute;
import com.googlecode.cqengine.query.option.QueryOptions;

public class StockDetails implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -2031420822267600090L;
	private String exchange;
	private String symbol;
	private float currentPrice;
	private float lastPrice;
	private float change;
	private String marketCapital;
	private String lastPriceRecordTime;
	private String currentPriceRecordTime;

	public String getExchange() {
		return exchange;
	}

	public void setExchange(String exchange) {
		this.exchange = exchange;
	}

	public String getSymbol() {
		return symbol;
	}

	public void setSymbol(String symbol) {
		this.symbol = symbol;
	}

	public float getCurrentPrice() {
		return currentPrice;
	}

	public void setCurrentPrice(float currentPrice) {
		this.currentPrice = currentPrice;
	}

	public float getLastPrice() {
		return lastPrice;
	}

	public void setLastPrice(float lastPrice) {
		this.lastPrice = lastPrice;
	}

	public float getChange() {
		return change;
	}

	public void setChange(float change) {
		this.change = change;
	}

	public String getMarketCapital() {
		return marketCapital;
	}

	public void setMarketCapital(String marketCapital) {
		this.marketCapital = marketCapital;
	}

	public String getLastPriceRecordTime() {
		return lastPriceRecordTime;
	}

	public void setLastPriceRecordTime(String lastPriceRecordTime) {
		this.lastPriceRecordTime = lastPriceRecordTime;
	}

	public String getCurrentPriceRecordTime() {
		return currentPriceRecordTime;
	}

	public void setCurrentPriceRecordTime(String currentPriceRecordTime) {
		this.currentPriceRecordTime = currentPriceRecordTime;
	}

	public StockDetails(String exchange, String symbol, float currentPrice, float lastPrice, float change,
			String marketCapital, String lastPriceRecordTime, String currentPriceRecordTime) {
		super();
		this.exchange = exchange;
		this.symbol = symbol;
		this.currentPrice = currentPrice;
		this.lastPrice = lastPrice;
		this.change = change;
		this.marketCapital = marketCapital;
		this.lastPriceRecordTime = lastPriceRecordTime;
		this.currentPriceRecordTime = currentPriceRecordTime;
	}
	
	public static final Attribute<StockDetails, String> SYMBOL = new SimpleAttribute<StockDetails, String>("symbol") {
        public String getValue(StockDetails stockDetails, QueryOptions queryOptions) { return stockDetails.symbol; }
    };
    
    public static final Attribute<StockDetails, String> EXCHANGE = new SimpleAttribute<StockDetails, String>("exchange") {
        public String getValue(StockDetails stockDetails, QueryOptions queryOptions) { return stockDetails.exchange; }
    };

	@Override
	public String toString() {
		return "StockDetails [exchange=" + exchange + ", symbol=" + symbol + ", currentPrice=" + currentPrice
				+ ", lastPrice=" + lastPrice + ", change=" + change + ", marketCapital=" + marketCapital
				+ ", lastPriceRecordTime=" + lastPriceRecordTime + ", currentPriceRecordTime=" + currentPriceRecordTime
				+ "]";
	}

    
}
