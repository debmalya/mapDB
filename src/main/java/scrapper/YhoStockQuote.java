package scrapper;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import model.StockDetails;

public class YhoStockQuote {

	private static final Logger LOGGER = Logger.getLogger(YhoStockQuote.class);

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

	public List<StockDetails> parse(final String symbol) throws Exception {
		List<StockDetails> stockDetalsList = new ArrayList<StockDetails>();
		try {
			Element doc = Jsoup.connect(ScrapperConstants.YAHOO_FINANCE + symbol).get();
			Elements quoteSummaryElement = doc.select(".yfi_rt_quote_summary");
			String stockExchange = quoteSummaryElement.select(".rtq_exch").text();
			String strPrice = quoteSummaryElement.select(".time_rtq_ticker").text();
			float currentPrice = 0.00f;
			try {
				currentPrice = Float.parseFloat(strPrice);
			} catch (NumberFormatException nfe) {
				LOGGER.error(nfe.getMessage());
			}

			String[] time = quoteSummaryElement.select(".time_rtq").text().split(" ");
			StringBuilder currentTime = new StringBuilder();
			StringBuilder lastTime = new StringBuilder();
			int l = time.length;
			if (l > 6) {
				// both current time and last time were there
				for (int i = 0; i < time.length / 2; i++) {
					currentTime.append(time[i]);
					currentTime.append(" ");
					lastTime.append(time[time.length / 2 + i]);
					lastTime.append(" ");
				}
			} else {
				for (int i = 0; i < time.length; i++) {
					currentTime.append(time[i]);
					currentTime.append(" ");
				}
			}

			String clossingPrice = quoteSummaryElement.select(".yfs_rtq_quote").text();
			float lastPrice = 0.00f;
			try {
				lastPrice = Float.parseFloat(clossingPrice);

			} catch (NumberFormatException nfe) {
				LOGGER.error(nfe.getMessage());
			}

			float change = 0.00f;
			if (currentPrice != 0.00f && lastPrice != 0.00f) {
				change = currentPrice - lastPrice;
			}

			StockDetails stockDetails = new StockDetails(stockExchange, symbol, currentPrice, lastPrice, change, "",
					lastTime.toString(), currentTime.toString());
			getSummaryData(doc, stockDetails);

			LOGGER.debug(stockDetails);
			stockDetalsList.add(stockDetails);

		} catch (IOException e) {
			LOGGER.error(e.getMessage(), e);
			throw new Exception(e);
		}
		return stockDetalsList;

	}

	/**
	 * @param doc
	 * @param stockDetails
	 */
	private void getSummaryData(Element doc, StockDetails stockDetails) {
		Elements summaryElement = doc.select("#yfi_quote_summary_data");
		Elements allSummaries = summaryElement.select(".yfnc_tabledata1");
		int count = 0;
		for (Element each : allSummaries) {
			String text = each.text();
			switch (count) {
			case 0:
				stockDetails.setPrevClose(text);
				break;
			case 1:
				stockDetails.setOpen(text);
				break;
			default:
				break;
			}
			count++;
		}

	}

}
