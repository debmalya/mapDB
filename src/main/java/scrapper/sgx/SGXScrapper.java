/**
 * Copyright 2015-2016 Debmalya Jash
 * <p/>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p/>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p/>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package scrapper.sgx;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import model.StockDetails;

import org.apache.log4j.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import au.com.bytecode.opencsv.CSVWriter;

/**
 * @author debmalyajash
 * 
 */
public class SGXScrapper {

	private static Map<String, String> stockSites = new HashMap<>();

	static {

		stockSites.put("Australia", "www.asx.com.au");
		stockSites.put("Japan", "www.tse.or.jp");
		stockSites.put("Singapore", "www.sgx.com");
		stockSites.put("Hongkong", "www.hkex.com.hk");
		stockSites.put("France", "www.euronext.com");
		stockSites.put("Switzerland", "www.swxeurope.com");
		stockSites.put("Germany", "www.deutsche-boerse.com");
		stockSites.put("London", "www.londonstockexchange.com");
		stockSites.put("Newyork Stock Exchange", "www.nyse.com");
		stockSites.put("Nasdaq", "www.nasdaq.com");
		stockSites.put("India", "www.nseindia.com");
	}

	/**
	 * 
	 */
	private static final int PREFERRED_WIDTH = 35;

	private static final String FOOL = "http://www.sgx.com";

	private static final String YAHOO_FINANCE = "https://sg.finance.yahoo.com/q?s=";

	private static final Logger LOGGER = Logger.getLogger(SGXScrapper.class);

	private static CSVWriter stockWriter = null;

	public static void main(String... args) throws IOException {
		// fooled();
		// yahooFinanceMultiple(args);

		Element doc = Jsoup
				.connect(
						"http://download.finance.yahoo.com/d/quotes.csv?s=5HT.SI&amp;f=sl1d1t1c1ohgv&amp;e=.csv")
				.get();
		System.out.println(doc);
	}

	public SGXScrapper() {
		try {
			String pathname =  "sgx_yahoo.csv";
			boolean existing = false;
			
				File stockFile = new File(pathname);
				if (stockFile.exists()) {
					existing = true;
				}
			
			FileWriter file = new FileWriter(pathname, true);

			stockWriter = new CSVWriter(new PrintWriter(file));
			if (!existing) {
				stockWriter.writeNext(StockDetails.getCSVHeader().split(","));
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			LOGGER.error(e.getMessage(), e);
		}
	}

	public void yahooFinanceMultiple(String... args) {
		if (args.length > 0) {
			for (String each : args) {
				yahooFinance(each);
			}
		}
	}

	/**
	 * @param each
	 */
	public void yahooFinance(String each) {
		String symbol = each + ".SI";
		String url = YAHOO_FINANCE + symbol;

		LOGGER.debug(url);

		try {
			Element doc = Jsoup.connect(url).get();
			Element quoteSummary = doc.getElementById("yfi_rt_quote_summary");
			StockDetails details = new StockDetails();
			details.setExchange("SGX");
			details.setSymbol(symbol);
			if (quoteSummary != null) {
				details.setCurrentPrice(Float.valueOf(getValueByClass(
						quoteSummary, "time_rtq_ticker")));
				details.setChange(Float.valueOf(getValueByClass(quoteSummary,
						"time_rtq_ticker")));
				details.setCurrentPriceRecordTime(getValueByClass(quoteSummary,
						"time_rtq"));
			}

			parseTable(doc, "table1", details);
			parseTable(doc, "table2", details);

			System.out.println("----------------------------" + symbol);

			if (stockWriter != null) {
				stockWriter.writeNext(details.toCSV().split(","));
			}
		} catch (Throwable e) {
			System.err.println(e.getMessage());
		} finally {

		}

	}

	public static void parseTable(Element doc, String tableid,
			StockDetails details) {
		Element tableElement = doc.getElementById(tableid);

		tableElement.select("tr").iterator()
				.forEachRemaining(s -> processEachTableRow(s, details));

	}

	public static void processEachTableRow(Element s, StockDetails details) {
		String key = s.select("th").text().trim();
		String value = s.select("td").text().trim().replace(","," ");
//		System.out.println(key);
		switch (key) {
		case "Prev Close:":
			try {
				details.setLastPrice(Float.parseFloat(value));
				details.setPrevClose(value);
			} catch (NumberFormatException nfe) {
				LOGGER.error(nfe.getMessage(), nfe);
			}
			break;
		case "Open:":
			details.setOpen(value);
			break;
		case "Bid:":
			details.setBid(value);
			break;
		case "1y Target Est:":
			details.setBid(value);
			break;
		case "Ask:":
			details.setAsk(value);
			break;
		case "Next Earnings Date:":
			details.setNextEarningsDate(value);
			break;
		case "Beta:":
			details.setBeta(value);
			break;
		case "Day's Range:":
			details.setDaysRange(value);
			break;
		case "52wk Range:":
			details.set52wkRange(value);
			break;
		case "Volume:":
			details.setVolume(value);
			break;
		case "Avg Vol (3m)":
			details.setAverageVolume(value);
			break;
		case "Market Cap:":
			details.setMarketCapital(value);
			break;
		case "P/E (ttm):":
			details.setPERatio(value);
			break;
		case "EPS (ttm):":
			details.setEPS(value);
			break;
		case "Div & Yield:":
			details.setDivNYield(value);
			break;
		case "Exercise Price:":
			details.setExercisePrice(value);
			break;
		case "Previous Close:":
			details.setPreviousClose(value);
			break;
		case "Expiration Date:":
			details.setExpriationDate(value);
			break;
		case "Expiration Price:":
			details.setExpriationPrice(value);
			break;
		case "Type:":
			details.setType(value);
			break;
		case "Minimum Trade Size:":
			details.setMinimumTraceSize(value);
			break;
		case "Share:":
			details.setShare(value);
			break;
		case "Issuer:":
			details.setIssuer(value);
			break;
		case "Underlying:":
			details.setUnderlying(value);
			break;
		default:
			if (key.contains("Avg Vol")) {
				details.setAverageVolume(value);
			} else {
				System.err.println("Not handled " + key);
			}
			break;
		}
	}

	/**
	 * @param quoteDetails
	 *            Prev Close: Open: Bid Ask
	 */
	private static void parseQuoteDetals(Element quoteDetails) {
		// Elements details =
		// quoteDetails.getElementsByClass("yfnc_tabledata1");
		Elements details = quoteDetails.getAllElements();
		for (Element each : details) {
			System.out.println(each.text());
			String[] values = each.text().split(":");
			System.out.println(Arrays.toString(values));
			break;
		}

	}

	public static String getValueByClass(Element quoteSummary, String filter) {
		Elements lastPriceElement = quoteSummary.getElementsByClass(filter);
		if (lastPriceElement != null) {
			return lastPriceElement.text();

		}
		return "";
	}

	/**
	 * @throws IOException
	 * 
	 */
	private static void fooled() throws IOException {
		Element doc = Jsoup.connect(FOOL).get();
		if (LOGGER.isDebugEnabled()) {
			debugLog(doc.text(), 80, "sgx.txt");
		}
		Elements links = doc.select("a[href]");
		Elements media = doc.select("[src]");
		Elements imports = doc.select("link[href]");

		print("\nLinks: (%d)", links.size());
		for (Element link : links) {
			// print(" * a: <%s>  (%s)", link.attr("abs:href"),
			// trim(link.text(), 35));
			if (link.text().equalsIgnoreCase("Gainers")) {
				Element subDoc = Jsoup.connect(link.attr("abs:href")).get();
				System.out.println(link.attr("abs:href"));
				debugLog(subDoc.text(), 80, "sgx_gainers.txt");
			}
			// print(" * a: <%s>  (%s)", link.attr("abs:href"), link.text());
		}

	}

	/**
	 * @param text
	 * @param preferredWidth
	 * @throws FileNotFoundException
	 */
	private static void debugLog(String text, int preferredWidth,
			String fileName) throws FileNotFoundException {
		PrintWriter sgxWriter = null;
		try {
			String[] allWords = text.split(" ");
			StringBuilder sb = new StringBuilder();
			sgxWriter = new PrintWriter(fileName);
			int currentLength = 0;
			for (int i = 0; i < allWords.length; i++) {
				if (currentLength + allWords[i].length() < preferredWidth) {
					sb.append(allWords[i]);
					sb.append(" ");
					currentLength += allWords[i].length() + 1;
				} else {
					sgxWriter.println(sb.toString());
					sb.delete(0, sb.length());
					currentLength = 0;
				}
			}
		} finally {
			if (sgxWriter != null) {
				sgxWriter.close();
			}
		}

	}

	private static void print(String msg, Object... args) {
		System.out.println(String.format(msg, args));
	}

	private static String trim(String s, int width) {
		if (s.length() > width)
			return s.substring(0, width - 1) + ".";
		else
			return s;
	}

	public void cleanUp() {
		if (stockWriter != null) {
			try {
				stockWriter.flush();
				stockWriter.close();
			} catch (IOException e) {
				e.printStackTrace();
				LOGGER.error(e.getMessage(), e);
			}
		}
	}
}

/*
 * https://www.fool.sg/company/United+Overseas+Bank+Ltd/?ticker=SGX-U11
 */
