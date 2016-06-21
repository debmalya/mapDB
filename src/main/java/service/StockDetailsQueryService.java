/**
 * 
 */
package service;

import static com.googlecode.cqengine.query.QueryFactory.endsWith;
import static com.googlecode.cqengine.query.QueryFactory.or;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.googlecode.cqengine.ConcurrentIndexedCollection;
import com.googlecode.cqengine.IndexedCollection;
import com.googlecode.cqengine.index.navigable.NavigableIndex;
import com.googlecode.cqengine.query.Query;

import db.FileMapDb;
import model.StockDetails;

/**
 * 
 * @author debmalyajash
 *
 */
public class StockDetailsQueryService {

	IndexedCollection<StockDetails> stocks = new ConcurrentIndexedCollection<StockDetails>();

	private FileMapDb fileDB;

	private static final Logger LOGGER = Logger.getLogger(StockDetailsQueryService.class);

	/**
	 * Constructor get all the stock lists.
	 * 
	 * @param stockDetailsList
	 *            to be added in the index and query later.
	 */
	public StockDetailsQueryService(final List<StockDetails> stockDetailsList) {
		
		try {
			fileDB = new FileMapDb("stock.db");
			stockDetailsList.forEach(eachStock -> {
				stocks.add(eachStock);
				try {
					if (fileDB != null) {
						fileDB.save(eachStock);
					} else {
						LOGGER.error("fileDB is null");
					}
				} catch (Exception e) {
					LOGGER.error(e.getMessage(), e);
				}
			});
			stocks.addIndex(NavigableIndex.onAttribute(StockDetails.SYMBOL));
			stocks.addIndex(NavigableIndex.onAttribute(StockDetails.EXCHANGE));
		} catch (Throwable th) {
			LOGGER.error(th.getMessage(), th);
		} finally {
			if (fileDB != null) {
				fileDB.closeDB();
			}
		}

	}

	/**
	 * TO query all the stocks with stock symbol
	 * 
	 * @param querySymbol
	 *            to do the query . (e.g. goog, msft,
	 */
	public void queryBySymbol(final String querySymbol) {
		Query<StockDetails> query1 = endsWith(StockDetails.SYMBOL, querySymbol);
		for (StockDetails stockDetails : stocks.retrieve(query1)) {
			System.out.println(stockDetails);
		}
	}
	
	/**
	 * TO query all the stocks with stock symbol
	 * 
	 * @param querySymbol
	 *            to do the query . (e.g. goog, msft,)
	 * @param exchange stock exchange.           
	 */
	public List<StockDetails> queryBySymbolOrExchange(final String querySymbol,final String exchange) {
		Query<StockDetails> query1 = or(endsWith(StockDetails.SYMBOL, querySymbol),endsWith(StockDetails.EXCHANGE, exchange));
		List<StockDetails> results = new ArrayList<StockDetails>();
		for (StockDetails stockDetails : stocks.retrieve(query1)) {
			results.add(stockDetails);
		}
		return results;
	}

	

}
