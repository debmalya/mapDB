package scrapper;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.jsoup.Jsoup;
import org.jsoup.helper.StringUtil;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import model.StockSymbol;

public class GoogleFinance {

	private static final Logger LOGGER = Logger.getLogger(GoogleFinance.class);

	/**
	 * To retrieve price change.
	 */
	public static final String CHANGE = "change";

	/**
	 * To retrieve price.
	 */
	public static final String PRICE = "price";

	/**
	 * To retrieve symbol.
	 */
	public static final String SYMBOL = ".symbol";

	/**
	 * To retrieve each row.
	 */
	public static final String ROW = "tr";

	/**
	 * To retrieve quotes.
	 */
	public static final String QUOTES = ".quotes";

	/**
	 * To retrieve from google finance.
	 */
	public static final String GOOGLE_URL = "https://www.google.com/finance";

	public static void main(String[] args) {

	}

	public List<StockSymbol> parseData() {
		List<StockSymbol> symbolList = new ArrayList<StockSymbol>();
		try {

			Element doc = Jsoup.connect(GOOGLE_URL).get();
			Elements quotes = doc.select(QUOTES);
			quotes.forEach(quote -> {
				Elements trs = quote.getElementsByTag(ROW);

				trs.forEach(tr -> {
					StockSymbol newSymbol = new StockSymbol();
					newSymbol.setTimeStamp(System.currentTimeMillis());
					newSymbol.setSymbol(tr.select(SYMBOL).text());
					getDouble(tr, newSymbol, PRICE);
					getDouble(tr, newSymbol, CHANGE);
					if (!StringUtil.isBlank(newSymbol.getSymbol())) {
						symbolList.add(newSymbol);
					}
				});
			});

			// symbolList.forEach(symbol -> System.out.println(symbol));
		} catch (IOException e) {
			LOGGER.error(e.getMessage(), e);
		}
		return symbolList;
	}

	private void getDouble(Element tr, StockSymbol newSymbol, String tagName) {
		String strPrice = getPriceNChange(tr, tagName);
		try {
			if (strPrice != null && strPrice.trim().length() > 0) {
				if (PRICE.equals(tagName)) {
					newSymbol.setPrice(Double.parseDouble(strPrice));
				} else {
					// first value is change amount
					// second value is change percentage.
					String[] changeNPercentage = strPrice.split(" ");
					newSymbol.setPriceChange(Double.parseDouble(changeNPercentage[0]));
					newSymbol.setChangePercentage(Float.parseFloat(changeNPercentage[1]));
				}
			}
		} catch (Throwable th) {
			LOGGER.error("Not able to convert :" + strPrice, th);
		}
	}

	private String getPriceNChange(Element tr, String tagName) {
		return tr.select("." + tagName).text().replace(",", "").replace("(", "").replace(")", "").replace("%", "");
	}

}
