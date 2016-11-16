/**
 * 
 */
package scrapper.sgx;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;

import model.StockDetails;

/**
 * @author debmalyajash
 *
 */
public class ShareJunctionScrapper {

	/**
	 * 
	 */
	private static final String URL = "http://www.sharejunction.com/sharejunction/viewStock.htm?id=";

	private static final Logger LOGGER = Logger.getLogger(ShareJunctionScrapper.class);

	/**
	 * This maps Singapore stock exchange symbol to share junction stock symbol.
	 */
	private static Map<String, String> shareJunctionStockMap = new HashMap<>();

	static {
		shareJunctionStockMap.put("OV8", "24453");
		shareJunctionStockMap.put("5TP", "24716");
		shareJunctionStockMap.put("573","14718");
		shareJunctionStockMap.put("5OT","21149");
	}

	/**
	 * @param args
	 * @throws IOException
	 */
	public static void main(String[] args) throws IOException {
		parse("5TP");
	}

	/**
	 * 
	 * @param symbol
	 *            - Singapore Stock exchange symbol.
	 * @return
	 * @throws IOException
	 */
	public static StockDetails parse(final String symbol) throws IOException {
		String shareJunctionSymbol = shareJunctionStockMap.get(symbol);
		StockDetails stockDetails = new StockDetails();
		stockDetails.setSource("Share Junction");
		if (shareJunctionSymbol != null) {
			String url = URL + shareJunctionSymbol;
			Element doc = Jsoup.connect(url).get();
			Element stockInfo = doc.getElementById("stockinfo");

			String[] entries = stockInfo.text().split(" ");
			String previous = "";
			boolean buyVolume = false;
			boolean sellVolume = false;
			for (String each : entries) {
				switch (previous) {
				case "Open":
					try {
						stockDetails.setOpen(Float.parseFloat(each.trim()));
					} catch (NumberFormatException nfe) {
						LOGGER.error(nfe.getMessage(), nfe);
					}
					break;
				case "Buy":
					if (stockDetails.getBuy() == null) {
						stockDetails.setBuy(each);
					} else {
						buyVolume = true;
					}
					break;
				case "Sell":
					if (stockDetails.getSell() == null) {
						stockDetails.setSell(each);
					} else {
						sellVolume = true;
					}
					break;
				case "High":
					stockDetails.setHigh(each);
					break;
				case "Low":
					stockDetails.setLow(each);
					stockDetails.set52wkRange(each + " - " + stockDetails.getHigh());
					break;
				case "Close":
					// TODO During trading time how to get the current price ?
					stockDetails.setCurrentPrice(each);
					break;
				case "Volume(K)":
					if (stockDetails.getVolume() == null) {
						stockDetails.setVolume(each);
					} else if (buyVolume) {
						stockDetails.setBuyVolume(each);
						buyVolume = false;
					} else if (sellVolume) {
						stockDetails.setSellVolume(each);
						sellVolume = false;
					}
					break;
				case "INFOUpdated":
					stockDetails.setCurrentPriceRecordTime(each);
					break;
				default:
					break;
				}
				previous = each;
//				System.out.println(each);
			}
		} else {
			LOGGER.error(symbol + " not found. Try to find share junction symbol ");
		}
//		System.out.println(stockDetails);
		return stockDetails;
	}

}
