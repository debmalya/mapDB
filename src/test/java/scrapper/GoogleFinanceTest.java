package scrapper;

import java.util.List;

import org.apache.log4j.Logger;
import org.junit.Assert;
import org.junit.Test;

import db.FileMapDb;
import model.StockSymbol;

public class GoogleFinanceTest {
	
	private static final Logger LOGGER = Logger.getLogger(GoogleFinanceTest.class);

	@Test
	public void testParseData() {
		FileMapDb db = FileMapDb.getInstance();
		try {
			GoogleFinance gf = new GoogleFinance();
			List<StockSymbol> stockList = gf.parseData();

			stockList.forEach(stock -> db.save(stock));
		} catch (Throwable th) {
			LOGGER.error(th.getMessage(),th);
			Assert.assertFalse(th.getMessage(),true);
		} finally {
			db.closeDB();
		}
	}

}
