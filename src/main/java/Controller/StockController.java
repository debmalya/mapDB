package Controller;

import form.StockForm;
import service.StockService;

public class StockController {
	private StockForm listener;
	private StockService stockService;
	
	public StockController(StockService stockService){
		this.stockService = stockService;
	}
	
	public void registerChangeListener(StockForm listener) {
		this.listener = listener;
	}
	
	
}
