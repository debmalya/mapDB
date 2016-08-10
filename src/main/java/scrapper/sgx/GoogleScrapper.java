/**
 * 
 */
package scrapper.sgx;

import model.StockDetails;

import org.apache.log4j.Logger;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 * @author debmalyajash
 *
 */
public class GoogleScrapper implements Scrapper {

	private static final Logger LOGGER = Logger.getLogger(GoogleScrapper.class);

	private static final String URL = "https://www.google.com/finance?q=SGX%3A";

	private static Connection connection;

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see scrapper.sgx.Scrapper#scrap(java.lang.String)
	 */
	@Override
	public StockDetails scrap(String symbol) {

		StockDetails details = new StockDetails();
		details.setSymbol(symbol);
		details.setExchange("SGX");
		details.setSource("Google");
		try {

			connection = Jsoup.connect(URL + symbol);

			Document doc = connection.get();
			Element pricePanel = doc.getElementById("price-panel");
			String[] values = pricePanel.text().split(" ");
			details.setCurrentPrice(values[0]);
			details.setChange(Float.parseFloat(values[1]));
			details.setCurrentPriceRecordTime(values[3] + " " + values[4]);
			details.setLastPriceRecordTime(values[3] + " " + values[4]);

			Elements keyElements = doc.getElementsByClass("key");
			Elements valueElements = doc.getElementsByClass("val");
			for (int i = 0; i < keyElements.size(); i++) {
				
				String key = keyElements.get(i).ownText();
				String value = valueElements.get(i).ownText();

				switch (key) {
				case "Range":
					details.setDaysRange(value);
					break;
				case "52 week":
					details.set52wkRange(value);
					break;
				case "Open":
					details.setOpen(Float.parseFloat(value));
					break;
				case "Vol / Avg.":
					String[] vols = value.split("/");
					details.setVolume(vols[0]);
					details.setAverageVolume(vols[1]);
					break;
				case "Mkt cap":
					details.setMarketCapital(value);
					break;
				case "P/E":
					details.setPERatio(value);
					break;
				case "Div/yield":
					details.setDivNYield(value);
					break;
				case "EPS":
					details.setEPS(value);
					break;
				case "Shares":
					details.setShare(value);
					break;
				case "Beta":
					details.setBeta(value);
					break;
				case "Inst. own":
					details.setInstOwn(value);
					break;
				default:
					break;
				}

			}

		} catch (Throwable e) {
			LOGGER.error("ERR:" + URL + symbol + " " + e.getMessage(), e);
		}
		return details;
	}

}
