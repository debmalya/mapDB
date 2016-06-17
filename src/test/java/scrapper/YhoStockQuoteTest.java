package scrapper;

import org.apache.log4j.Logger;
import org.junit.Assert;
import org.junit.Test;

public class YhoStockQuoteTest {
	
	private static Logger LOGGER = Logger.getLogger(YhoStockQuoteTest.class);

	@Test
	public void testParse() {
		YhoStockQuote stockQuote = new YhoStockQuote();
		String[] quotes = new String[] { "goog", "msft", "aapl", "pg", "ZNGA" };
		try {
			for (String eachQuote : quotes) {
				stockQuote.parse(eachQuote);
			}
		} catch (Exception e) {
			LOGGER.error(e.getMessage(),e);
			Assert.assertFalse(e.getMessage(), true);
		}
	}

}
