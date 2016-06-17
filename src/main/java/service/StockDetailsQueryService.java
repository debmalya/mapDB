/**
 * 
 */
package service;

import java.util.List;

import com.googlecode.cqengine.ConcurrentIndexedCollection;
import com.googlecode.cqengine.IndexedCollection;
import com.googlecode.cqengine.index.navigable.NavigableIndex;
import com.googlecode.cqengine.query.Query;
import static com.googlecode.cqengine.query.QueryFactory.*;



import model.StockDetails;
/**
 * 
 * @author debmalyajash
 *
 */
public class StockDetailsQueryService {
	
	IndexedCollection<StockDetails> stocks = new ConcurrentIndexedCollection<StockDetails>();
	
	public StockDetailsQueryService(final List<StockDetails> stockDetailsList){
		stockDetailsList.forEach(eachStock->stocks.add(eachStock));
		stocks.addIndex(NavigableIndex.onAttribute(StockDetails.SYMBOL));
		
		
		
	}

	/**
	 * TO query all the stocks with stock symbol
	 * @param querySymbol to do the query . (e.g. goog, msft, 
	 */
	public void queryBySymbol(final String querySymbol) {
		Query<StockDetails> query1 = endsWith(StockDetails.SYMBOL, querySymbol);
		  for (StockDetails car : stocks.retrieve(query1)) {
		      System.out.println(car);
		  }
	}

}
