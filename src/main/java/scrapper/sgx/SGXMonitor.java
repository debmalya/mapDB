/**
 * 
 */
package scrapper.sgx;

import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ExecutorService;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;

import com.jakewharton.fliptables.FlipTable;

import au.com.bytecode.opencsv.CSVWriter;
import model.StockDetails;
import scrapper.Bloomberg;

/**
 * @author debmalyajash
 *
 */
public class SGXMonitor {

	/**
	 * 
	 */
	private static final String DEFAULT_BALANCE = "1000.00";
	private static final Logger LOGGER = Logger.getLogger(SGXMonitor.class);
	private static final Level LOG_LEVEL = Level.INFO;
	private static CSVWriter writer;

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		monitor();

	}

	public static void monitor() {
		Properties properties = new Properties();
		LOGGER.setLevel(LOG_LEVEL);

		try {
			FileInputStream fis = new FileInputStream("./target/classes/monitor_symbol.properties");
			writer = new CSVWriter(new FileWriter("sgx_monitor.csv", true));
			SGXScrapper scrapper = new SGXScrapper(false);

			properties.load(fis);
			fis.close();
			String value = properties.getProperty("monitor");
			float balance = Float.parseFloat(properties.getProperty("balance", DEFAULT_BALANCE));
			String[] symbols = value.split(",");

			eachLoop(scrapper, symbols, balance);

		} catch (IOException e) {
			LOGGER.error(e.getMessage(), e);
		} finally {
			if (writer != null) {
				try {
					writer.flush();
					writer.close();
				} catch (IOException e) {
					LOGGER.error(e.getMessage(), e);
				}
			}
		}
	}

	/**
	 * 
	 * @param berg
	 * @param stockMap
	 * @param scrapper
	 * @param symbols
	 * @param balance
	 */
	private static void eachLoop(SGXScrapper scrapper, String[] symbols, float balance) {
		// Stock Details Map from Yahoo Finance.
		Map<String, String[]> stockMap = new HashMap<>();
		// Stock Details Map for Bloomberg
		Map<String, String[]> stockMapBG = new HashMap<>();
		// Stock Details Map for Share Junction
		Map<String, String[]> stockMapShareJunction = new HashMap<>();
//		Stock Details Map for Google
		Map<String,String[]> googleMap = new HashMap<>();
		
		ExecutorService executorService = null;
		
		GoogleScrapper google = new GoogleScrapper();
		while (true) {
			boolean toBePrinted = false;
			List<String[]> allRows = new ArrayList<String[]>();
			for (String symbol : symbols) {
				try {
					StockDetails detailsYahoo = scrapper.yahooFinance(symbol, null);
					toBePrinted = stockPrint(balance, stockMap, toBePrinted, allRows, symbol, detailsYahoo);

					StockDetails bgDetails = Bloomberg.parse(symbol, "SP");
					toBePrinted = stockPrint(balance, stockMapBG, toBePrinted, allRows, symbol, bgDetails);

					StockDetails shareJunctionDetails = ShareJunctionScrapper.parse(symbol);
					toBePrinted = stockPrint(balance, stockMapShareJunction, toBePrinted, allRows, symbol,
							shareJunctionDetails);
					
					StockDetails googleStockDetails = google.scrap(symbol);
					toBePrinted = stockPrint(balance, googleMap, toBePrinted, allRows, symbol,
							googleStockDetails);

				} catch (Throwable neverMind) {
					// Continue, never mind what ever happened
					// just log it. later we will analyze the log using logtash
					LOGGER.error(neverMind.getMessage(), neverMind);
				}
			}

			if (toBePrinted) {
				String[][] values = new String[allRows.size()][2];

				for (int i = 0; i < allRows.size(); i++) {
					values[i] = allRows.get(i);
				}

				String message = FlipTable.of(new String[] { "Sym", "CP", "CPRT", "dR", "yR", "pe", "eps", "vol",
						"qty", "Sell", "Buy", "source" }, values);
				System.out.println(message);
				LOGGER.log(LOGGER.getLevel(), message);
			}
		}
	}

	private static boolean stockPrint(float balance, Map<String, String[]> stockMap, boolean toBePrinted,
			List<String[]> allRows, String symbol, StockDetails detailsYahoo)
			throws NumberFormatException, IOException {
		if (detailsYahoo.getCurrentPrice() != null) {
			String[] existingValues = stockMap.get(symbol);
			String[] values = detailsYahoo.abridged();
			if (existingValues == null || !Arrays.equals(existingValues, values)) {
				toBePrinted = true;
				int qtyToAfford = (int) (balance / Float.parseFloat(values[1]));
				double volume = 0.00D;
				if (values[7] != null) {

					// volume = Double.parseDouble(values[7].replaceAll("\\s+",
					// ""));
					allRows.add(new String[] { symbol, values[1], values[2], values[3], values[4], values[5], values[6],
							values[7], Integer.toString(qtyToAfford), values[9], values[10], values[11], });

				} else {
					LOGGER.error("Volume " + values[7] + " has issuse");
					allRows.add(new String[] { symbol, values[1], values[2], values[3], values[4], values[5], values[6],
							values[7], Integer.toString(qtyToAfford), values[9], values[10], values[11] });
				}
				writer.writeNext(values);
				writer.flush();
				stockMap.put(symbol, values);
			}

		}
		return toBePrinted;
	}

}
