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

public class FileMapDb  extends MapDB {
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
	@SuppressWarnings({ "unchecked", "deprecation" })
	private void init() {

		stockMap = (BTreeMap<String, List<StockSymbol>>) db.treeMap("stock", Serializer.STRING, Serializer.JAVA)
				.makeOrGet();
		
	}

	public boolean save(StockSymbol stockSymbol) throws Exception {
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
			
			boolean result = false;
			if (!stockList.contains(stockSymbol)) {
				stockList.add(stockSymbol);
				stockMap.put(stockSymbol.getSymbol(), stockList);
				db.commit();
				
				result = true;
			} else {
				LOGGER.debug("Already contains similar record");
				result = false;
			}
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("============================================================");
				LOGGER.debug(stockSymbol.getSymbol()+ " no. of records " + stockList.size());
				LOGGER.debug("============================================================");
			}
			return result;
		} catch (Throwable th) {
			LOGGER.error(th.getMessage(),th);
			db.rollback();
			throw new Exception(th.getMessage());
		}

		
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
	
	public DB createVolumeDB(final String dbFile) {
		return DBMaker.fileDB(dbFile)
		.fileMmapEnable()
		.closeOnJvmShutdown()
		.make();
	}

}
