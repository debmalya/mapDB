package model;

import java.io.Serializable;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.log4j.Logger;

import com.googlecode.cqengine.attribute.Attribute;
import com.googlecode.cqengine.attribute.SimpleAttribute;
import com.googlecode.cqengine.query.option.QueryOptions;

public class StockDetails implements Serializable {

	private static final Logger LOGGER = Logger.getLogger(StockDetails.class);
	/**
	 * 
	 */
	private static final String COMMA = ",";
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
	private float previousClose;
	private float open;
	private String bid;
	private String oneYrTargetEst;
	private String beta;
	private String nextEarningDate;
	private String ask;
	private String daysRange;
	private String yrRange;
	private String volume;
	private String averageVolume;
	private String peRatio;
	private String eps;
	private String divNYield;
	private String exercisePrice;
	private String expirationDate;
	private String expirationPrice;
	private String type;
	private String minimumTradeSize;
	private String share;
	private String issuer;
	private String underlying;

	/**
	 * @return the previousClose
	 */
	public float getPreviousClose() {
		return previousClose;
	}

	/**
	 * @param previousClose
	 *            the previousClose to set
	 */
	public void setPreviousClose(float previousClose) {
		this.previousClose = previousClose;
	}

	/**
	 * @return the beta
	 */
	public String getBeta() {
		return beta;
	}

	/**
	 * @param beta
	 *            the beta to set
	 */
	public void setBeta(String beta) {
		this.beta = beta;
	}

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

	public StockDetails(String exchange, String symbol, float currentPrice,
			float lastPrice, float change, String marketCapital,
			String lastPriceRecordTime, String currentPriceRecordTime) {
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

	/**
	 * Default constructor.
	 */
	public StockDetails() {

	}

	public static final Attribute<StockDetails, String> SYMBOL = new SimpleAttribute<StockDetails, String>(
			"symbol") {
		public String getValue(StockDetails stockDetails,
				QueryOptions queryOptions) {
			return stockDetails.symbol;
		}
	};

	public static final Attribute<StockDetails, String> EXCHANGE = new SimpleAttribute<StockDetails, String>(
			"exchange") {
		public String getValue(StockDetails stockDetails,
				QueryOptions queryOptions) {
			return stockDetails.exchange;
		}
	};

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}

	/**
	 * To get the CSV header.
	 * 
	 * @return CSV header.
	 */
	public static String getCSVHeader() {
		StringBuilder csvHeader = new StringBuilder();
		csvHeader.append("exchange");
		csvHeader.append(COMMA);
		csvHeader.append("symbol");
		csvHeader.append(COMMA);
		csvHeader.append("currentPrice");
		csvHeader.append(COMMA);
		csvHeader.append("lastPrice");
		csvHeader.append(COMMA);
		csvHeader.append("change");
		csvHeader.append(COMMA);
		csvHeader.append("marketCapital");
		csvHeader.append(COMMA);
		csvHeader.append("lastPriceRecordTime");
		csvHeader.append(COMMA);
		csvHeader.append("previousClose");
		csvHeader.append(COMMA);
		csvHeader.append("bid");
		csvHeader.append(COMMA);
		csvHeader.append("open");
		csvHeader.append(COMMA);
		csvHeader.append("1y Target Est");
		csvHeader.append(COMMA);
		csvHeader.append("Beta");
		csvHeader.append(COMMA);
		csvHeader.append("Next Earnings date");
		csvHeader.append(COMMA);
		csvHeader.append("Ask");
		csvHeader.append(COMMA);
		csvHeader.append("Days Range");
		csvHeader.append(COMMA);
		csvHeader.append("52wk Range");
		csvHeader.append(COMMA);
		csvHeader.append("Volume");
		csvHeader.append(COMMA);
		csvHeader.append("Average Volume");
		csvHeader.append(COMMA);
		csvHeader.append("P/E (ttm)");
		csvHeader.append(COMMA);
		csvHeader.append("EPS (ttm)");
		csvHeader.append(COMMA);
		csvHeader.append("Div & Yield");
		csvHeader.append(COMMA);
		csvHeader.append("Exercise Price");
		csvHeader.append(COMMA);
		csvHeader.append("Expiration Date");
		csvHeader.append(COMMA);
		csvHeader.append("Expiration Price");
		csvHeader.append(COMMA);
		csvHeader.append("Type");
		csvHeader.append(COMMA);
		csvHeader.append("Minimum Trade Size");
		csvHeader.append(COMMA);
		csvHeader.append("Share");
		csvHeader.append(COMMA);
		csvHeader.append("Issuer");
		csvHeader.append(COMMA);
		csvHeader.append("Underlying");

		return csvHeader.toString();
	}

	/**
	 * To return result in CSV format.
	 * 
	 * @return return in CSV format.
	 */
	public String toCSV() {
		StringBuilder csvDetail = new StringBuilder();
		csvDetail.append(exchange);
		csvDetail.append(COMMA);
		csvDetail.append(symbol);
		csvDetail.append(COMMA);
		csvDetail.append(currentPrice);
		csvDetail.append(COMMA);
		csvDetail.append(lastPrice);
		csvDetail.append(COMMA);
		csvDetail.append(change);
		csvDetail.append(COMMA);
		csvDetail.append(marketCapital);
		csvDetail.append(COMMA);
		csvDetail.append(lastPriceRecordTime);
		csvDetail.append(COMMA);
		csvDetail.append(previousClose);
		csvDetail.append(COMMA);
		csvDetail.append(bid);
		csvDetail.append(COMMA);
		csvDetail.append(open);
		csvDetail.append(COMMA);
		csvDetail.append(oneYrTargetEst);
		csvDetail.append(COMMA);
		csvDetail.append(beta);
		csvDetail.append(COMMA);
		csvDetail.append(nextEarningDate);
		csvDetail.append(COMMA);
		csvDetail.append(ask);
		csvDetail.append(COMMA);
		csvDetail.append(daysRange);
		csvDetail.append(COMMA);
		csvDetail.append(yrRange);
		csvDetail.append(COMMA);
		csvDetail.append(volume != null ? volume : "NA");
		csvDetail.append(COMMA);
		csvDetail.append(averageVolume != null ? averageVolume : "NA");
		csvDetail.append(COMMA);
		csvDetail.append(peRatio);
		csvDetail.append(COMMA);
		csvDetail.append(eps);
		csvDetail.append(COMMA);
		csvDetail.append(divNYield);
		csvDetail.append(COMMA);
		csvDetail.append(exercisePrice != null ? exercisePrice : "NA");
		csvDetail.append(COMMA);
		csvDetail.append(expirationDate != null ? expirationDate : "NA");
		csvDetail.append(COMMA);
		csvDetail.append(expirationPrice != null ? expirationPrice : "NA");
		csvDetail.append(COMMA);
		csvDetail.append(type != null ? type : "NA");
		csvDetail.append(COMMA);
		csvDetail.append(minimumTradeSize != null ? minimumTradeSize : "NA");
		csvDetail.append(COMMA);
		csvDetail.append(share != null ? share : "NA");
		csvDetail.append(COMMA);
		csvDetail.append(issuer != null ? issuer : "NA");
		csvDetail.append(COMMA);
		csvDetail.append(underlying != null ? underlying : "NA");

		return csvDetail.toString();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime
				* result
				+ ((currentPriceRecordTime == null) ? 0
						: currentPriceRecordTime.hashCode());
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
		StockDetails other = (StockDetails) obj;
		if (currentPriceRecordTime == null) {
			if (other.currentPriceRecordTime != null)
				return false;
		} else if (!currentPriceRecordTime.equals(other.currentPriceRecordTime))
			return false;
		return true;
	}

	/**
	 * @param text
	 */
	public void setPrevClose(String text) {
		previousClose = convertToFloat(text);
	}

	/**
	 * @param text
	 * @return
	 */
	private float convertToFloat(final String text) {
		float value = 0.00f;
		try {
			value = Float.parseFloat(text);
		} catch (NumberFormatException ignore) {
			LOGGER.trace(text + " not converted " + ignore.getMessage(), ignore);
		}
		return value;
	}

	/**
	 * @param text
	 */
	public void setOpen(String text) {
		open = convertToFloat(text);

	}

	/**
	 * @return the bid
	 */
	public String getBid() {
		return bid;
	}

	/**
	 * @param bid
	 *            the bid to set
	 */
	public void setBid(String bid) {
		this.bid = bid;
	}

	/**
	 * @return the oneYrTargetEst
	 */
	public String getOneYrTargetEst() {
		return oneYrTargetEst;
	}

	/**
	 * @param oneYrTargetEst
	 *            the oneYrTargetEst to set
	 */
	public void setOneYrTargetEst(String oneYrTargetEst) {
		this.oneYrTargetEst = oneYrTargetEst;
	}

	/**
	 * @return the open
	 */
	public float getOpen() {
		return open;
	}

	/**
	 * @param open
	 *            the open to set
	 */
	public void setOpen(float open) {
		this.open = open;
	}

	/**
	 * @param value
	 */
	public void setNextEarningsDate(String value) {
		nextEarningDate = value;

	}

	/**
	 * @param value
	 */
	public void setAsk(String value) {
		ask = value;

	}

	/**
	 * @param value
	 */
	public void setDaysRange(String value) {
		daysRange = value;

	}

	/**
	 * @param value
	 */
	public void set52wkRange(String value) {
		yrRange = value;

	}

	/**
	 * @param value
	 */
	public void setVolume(String value) {
		volume = value;

	}

	/**
	 * @param value
	 */
	public void setAverageVolume(String value) {
		averageVolume = value;

	}

	/**
	 * @param value
	 */
	public void setPERatio(String value) {
		peRatio = value;
	}

	/**
	 * @param value
	 */
	public void setEPS(String value) {
		eps = value;

	}

	/**
	 * @param value
	 */
	public void setDivNYield(String value) {
		divNYield = value;

	}

	/**
	 * @param value
	 */
	public void setExercisePrice(String value) {
		exercisePrice = value;

	}

	/**
	 * @param value
	 */
	public void setPreviousClose(String value) {
		try {
			previousClose = Float.parseFloat(value);
		} catch (NumberFormatException nfe) {
			
		}

	}

	/**
	 * @param value
	 */
	public void setExpriationDate(String value) {
		expirationDate = value;
	}

	/**
	 * @param value
	 */
	public void setExpriationPrice(String value) {
		expirationPrice = value;
		
	}

	/**
	 * @param value
	 */
	public void setType(String value) {
		type = value;
		
	}

	/**
	 * @param value
	 */
	public void setMinimumTraceSize(String value) {
		minimumTradeSize = value;
	}

	/**
	 * @param value
	 */
	public void setShare(String value) {
		share = value;
		
	}

	/**
	 * @param value
	 */
	public void setIssuer(String value) {
		issuer = value;	
	}

	/**
	 * @param value
	 */
	public void setUnderlying(String value) {
		underlying = value;
		
	}

}
