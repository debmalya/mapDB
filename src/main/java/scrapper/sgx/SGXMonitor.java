/**
 * 
 */
package scrapper.sgx;

import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.apache.log4j.Logger;

import com.jakewharton.fliptables.FlipTable;

import au.com.bytecode.opencsv.CSVWriter;
import model.StockDetails;

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
	private static CSVWriter writer;

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		monitor();

	}

	public static void monitor() {
		Properties properties = new Properties();
		// InputStream inStream =
		// ClassLoader.getSystemResourceAsStream("monitor_symbol.properties");

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
	 * @param stockMap
	 * @param scrapper
	 * @param symbols
	 * @param balance
	 */
	private static void eachLoop(SGXScrapper scrapper, String[] symbols, float balance) {
		Map<String, String[]> stockMap = new HashMap<>();
		while (true) {
			boolean toBePrinted = false;
			List<String[]> allRows = new ArrayList<String[]>();
			for (String symbol : symbols) {
				try {
					StockDetails details = scrapper.yahooFinance(symbol, null);
					if (details.getCurrentPrice() != null) {
						String[] existingValues = stockMap.get(symbol);
						String[] values = details.abridged();
						if (existingValues == null || !Arrays.equals(existingValues, values)) {
							toBePrinted = true;
							allRows.add(new String[] { symbol, values[1], values[2], values[3], values[4], values[5],
									values[6], values[7],
									Integer.toString((int) (balance / Float.parseFloat(values[1]))) });
							writer.writeNext(values);
							writer.flush();
							stockMap.put(symbol, values);
						}

					}
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

				System.out.println(FlipTable
						.of(new String[] { "Sym", "CP", "CPRT", "dR", "yR", "pe", "eps", "vol", "qty" }, values));
			}
		}
	}

}
