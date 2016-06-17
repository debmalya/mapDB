package service;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import model.StockDetails;
import scrapper.YhoStockQuote;

public class StockDetailsQueryServiceTest {

	@Test
	public void testStockDetailsQueryService() {
		YhoStockQuote stockQuote = new YhoStockQuote();
		List<StockDetails> allStocks = new ArrayList<>();
		String[] quotes = new String[] { "goog", "msft", "aapl", "pg", "ZNGA" };
		try {
			for (String eachQuote : quotes) {
				allStocks.addAll(stockQuote.parse(eachQuote));
			}
		} catch (Exception e) {
			Assert.assertFalse("ERR :" + e.getMessage(), true);
		}
		
		
		StockDetailsQueryService queryService = new StockDetailsQueryService(allStocks);
		queryService.queryBySymbol("goog");
		
	}

}
