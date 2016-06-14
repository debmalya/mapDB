package db;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.locks.ReentrantReadWriteLock.ReadLock;

import org.mapdb.BTreeMap;
import org.mapdb.DB;
import org.mapdb.DBMaker;
import org.mapdb.Serializer;

import model.StockSymbol;

public class FileMapDb {
	private DB db = DBMaker.fileDB("test.db").make();
	private BTreeMap<String, List<StockSymbol>> stockMap;

	/**
	 * Initializes the database.
	 */
	private void init() {

		stockMap = (BTreeMap<String, List<StockSymbol>>) db.treeMap("stock", Serializer.STRING, Serializer.JAVA)
				.makeOrGet();
//		ReadLock readLock = db.getLock$mapdb().readLock();
//		readLock.lock();
//		Map<String, Object> allEntries = db.getAll();
//		readLock.unlock();

//		System.out.println(allEntries);
	}

	public boolean save(StockSymbol stockSymbol) {
		try {
			List<StockSymbol> stockList = stockMap.get(stockSymbol.getSymbol());
			
			if (stockList == null) {
				stockList = new ArrayList<StockSymbol>();
			} else {
				System.out.println(stockSymbol.getSymbol()+ " no. of entries " + stockList.size());
			}
			if (!stockList.contains(stockSymbol)) {
				stockList.add(stockSymbol);
				stockMap.put(stockSymbol.getSymbol(), stockList);
				db.commit();
				return true;
			}
		} catch (Throwable th) {
			db.rollback();
		}

		return false;
	}

	public FileMapDb() {
		init();
	}

	public void closeDB() {
		if (db != null) {
			db.close();
		}
	}

}
