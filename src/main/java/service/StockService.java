package service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Stream;

import model.StockSymbol;
import scrapper.GoogleFinance;

public class StockService {
	List<StockSymbol> stockSymbols = new ArrayList<StockSymbol>();
	
	public List<StockSymbol> getStockSymbols() {
		return stockSymbols;
	}

	public StockService() {
		stockSymbols = new GoogleFinance().parseData();
	}

	public List<StockSymbol> update() {
		stockSymbols = new GoogleFinance().parseData();
		Collections.sort(stockSymbols);
		return stockSymbols;
	}
	
	
	public Stream<StockSymbol> select(final String stockSymbol) {
		stockSymbols = new GoogleFinance().parseData();
		return stockSymbols.stream().filter(stock -> stock.getSymbol().equalsIgnoreCase(stockSymbol));
	}
	

}
