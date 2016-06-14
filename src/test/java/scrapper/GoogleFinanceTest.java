package scrapper;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import db.FileMapDb;
import model.StockSymbol;

public class GoogleFinanceTest {

	@Test
	public void testParseData() {
		FileMapDb db = new FileMapDb();
		try {
			GoogleFinance gf = new GoogleFinance();
			List<StockSymbol> stockList = gf.parseData();
			
			stockList.forEach(stock -> Assert.assertTrue(db.save(stock)));
		} finally {
			db.closeDB();
		}
	}

}
