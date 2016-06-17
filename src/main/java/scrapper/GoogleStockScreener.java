package scrapper;

import java.io.IOException;

import org.apache.log4j.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;

public class GoogleStockScreener {

	private static Logger LOGGER = Logger.getLogger(GoogleStockScreener.class);

	public void parseData() throws Exception {
		try {
			Element doc = Jsoup.connect(ScrapperConstants.GOOGLE_STOCK_SCREENER).get();
			LOGGER.debug(doc);
		} catch (IOException e) {
			LOGGER.error(e.getMessage(), e);
			throw new Exception(e);
		}
	}
}
