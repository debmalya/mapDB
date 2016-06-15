package scrapper;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Date;
import java.util.Vector;

import org.apache.log4j.Logger;

import db.FileMapDb;
import model.StockSymbol;

/**
 * An instance is a stock quote for a given ticker symbol at the time of
 * creation. The static variable record maintains a list of all requested
 * quotes. Requires In.java to compile; download and place it in the same
 * directory. Download site: http://www.cs.princeton.edu/introcs/stdlib/In.java
 * Demo idea due to Sedgewick and Wayne
 */
public class StockQuote {

	private String symbol; // stock ticker symbol
	private Date time; // time the quote was taken;
	private double price; // price of the stock when the quote was recorded

	private static final Logger LOGGER = Logger.getLogger(StockQuote.class);

	private static Vector<StockQuote> record = new Vector<StockQuote>(); // list
																			// of
																			// all
																			// requested
																			// quotes

	private static FileMapDb myDB = FileMapDb.getInstance();

	/**
	 * Print current price of stock with ticker symbol s, and add the new quote
	 * to the record. Print format: <symbol> @ <date>: $<price> Precondition: s
	 * is a valid ticker symbol (case doesn't matter): examples: "goog", "GOOG"
	 * "MsFT"
	 */
	public static void getQuote(String s) {

		double price = 0.0;

		// find URL for data
		URL url = null;
		try {
			url = new URL("http://finance.yahoo.com/q?s=" + s);
			// treat the webpage as a String so we can process it
			String targetText = new In(url).readAll();

			// pull out the substring corresponding to the price
			int beginIndex = targetText.indexOf("After Hours");
			if (beginIndex > -1) {
				targetText = targetText.substring(beginIndex);
				targetText = targetText.substring(0, targetText.indexOf("</span>"));
				targetText = targetText.substring(targetText.lastIndexOf(">") + 1);

				// convert String in targetText to a double price
				price = Double.parseDouble(targetText);

				// record the new quote (by creating it) and print the info out

				StockSymbol stockSymbol = new StockSymbol();
				stockSymbol.setSymbol(s);
				stockSymbol.setPrice(price);
				stockSymbol.setTimeStamp(System.currentTimeMillis());

				boolean isSaved = myDB.save(stockSymbol);
				if (isSaved) {
					if (LOGGER.isDebugEnabled()) {
						LOGGER.debug("SAVED :" + stockSymbol);
					}
				} else {
					LOGGER.error("Not able to save :" + stockSymbol);
				}

			} else {
				LOGGER.error(s + " is not available, please check.");
			}
		} catch (MalformedURLException e) {
			LOGGER.error(e.getMessage(), e);
		}

	}

	/**
	 * = StockQuote at index i. Precondition: i is a valid index into the record
	 */
	public static StockQuote recordAt(int i) {
		return record.get(i);
	}

	/** = <symbol> @ <date>: $<price> */
	// public String toString() {
	// return symbol + " @ " + time + ": $" + price;
	// }

	/** just for private debugging purposes */
	public static void test() {
		// tests of getQuote
		String[] quotes = new String[] { "goog", "msft", "aapl", "pg", "ZNGA" };

		for (String eachSymbol : quotes) {
			getQuote(eachSymbol);			
		}

	}

	public static void main(String[] args) {
		test();
		myDB.closeDB();
	}
}
