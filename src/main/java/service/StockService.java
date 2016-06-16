package service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

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
	
	

}
