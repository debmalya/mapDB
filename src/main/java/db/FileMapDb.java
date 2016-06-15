package db;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.log4j.Logger;
import org.mapdb.BTreeMap;
import org.mapdb.DB;
import org.mapdb.DBMaker;
import org.mapdb.Serializer;

import model.StockSymbol;

public class FileMapDb {
	private DB db = DBMaker.fileDB("test.db").make();
	private BTreeMap<String, List<StockSymbol>> stockMap;

	private static final Logger LOGGER = Logger.getLogger(FileMapDb.class);

	private static FileMapDb myDB;
	static {
		myDB = new FileMapDb();
	}

	/**
	 * Initializes the database.
	 */
	private void init() {

		stockMap = (BTreeMap<String, List<StockSymbol>>) db.treeMap("stock", Serializer.STRING, Serializer.JAVA)
				.makeOrGet();
	}

	public boolean save(StockSymbol stockSymbol) {
		try {
			List<StockSymbol> stockList = stockMap.get(stockSymbol.getSymbol());

			if (stockList == null) {
				stockList = new ArrayList<StockSymbol>();
			} else {
				Collections.sort(stockList);
				stockList.forEach(stock -> {
					if (LOGGER.isDebugEnabled()) {
						LOGGER.debug(stock);
					}
				});
			}
			if (!stockList.contains(stockSymbol)) {
				stockList.add(stockSymbol);
				stockMap.put(stockSymbol.getSymbol(), stockList);
				db.commit();
				return true;
			} else {
				LOGGER.debug("Already contains similar record");
				return false;
			}
		} catch (Throwable th) {
			LOGGER.error(th.getMessage(),th);
			db.rollback();
		}

		return false;
	}

	private FileMapDb() {
		init();
	}

	public void closeDB() {
		if (db != null) {
			db.close();
		}
	}
	
	public static FileMapDb getInstance() {
		return myDB;
	}

}
