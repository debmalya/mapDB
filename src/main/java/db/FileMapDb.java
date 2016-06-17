package db;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.log4j.Logger;
import org.mapdb.BTreeMap;
import org.mapdb.DB;
import org.mapdb.DBMaker;
import org.mapdb.HTreeMap;
import org.mapdb.Serializer;

import model.StockDetails;
import model.StockSymbol;

public class FileMapDb extends MapDB {
	private DB db;
	private BTreeMap<String, List<StockSymbol>> stockMap;

	private HTreeMap<String, List<StockDetails>> stockDetailsMap;

	private static final Logger LOGGER = Logger.getLogger(FileMapDb.class);

	/**
	 * Initializes the database.
	 */
	@SuppressWarnings({ "unchecked", "deprecation" })
	private void init() {

		stockMap = (BTreeMap<String, List<StockSymbol>>) db.treeMap("stock", Serializer.STRING, Serializer.JAVA)
				.makeOrGet();

		stockDetailsMap = (HTreeMap<String, List<StockDetails>>) db.hashMap("stockDetails")
				.keySerializer(Serializer.STRING).valueSerializer(Serializer.JAVA).makeOrGet();
	}

	/**
	 * Saves stock symbol
	 * 
	 * @param stockSymbol
	 *            to store
	 * @return true if saved successfully, false if record already exists no
	 *         need to save.
	 * @throws Exception
	 *             if any exception occurs.
	 */
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
				LOGGER.debug(stockSymbol.getSymbol() + " no. of records " + stockList.size());
				LOGGER.debug("============================================================");
			}
			return result;
		} catch (Throwable th) {
			LOGGER.error(th.getMessage(), th);
			db.rollback();
			throw new Exception(th.getMessage());
		}

	}

	/**
	 * Saves stock symbol
	 * 
	 * @param stockDetails
	 *            to store
	 * @return true if saved successfully, false if record already exists no
	 *         need to save.
	 * @throws Exception
	 *             if any exception occurs.
	 */
	public boolean save(StockDetails stockDetails) throws Exception {
		try {
			List<StockDetails> stockDetailsList = stockDetailsMap.get(stockDetails.getSymbol());

			if (stockDetailsList == null) {
				stockDetailsList = new ArrayList<StockDetails>();
			} 

			boolean result = false;

			stockDetailsList.add(stockDetails);
			stockDetailsMap.put(stockDetails.getSymbol(), stockDetailsList);
			db.commit();

			result = true;

			if (LOGGER.isDebugEnabled()) {
				stockDetailsList.forEach(stock -> {
					LOGGER.debug(stock);
				});
				LOGGER.debug("============================================================");
				LOGGER.debug(stockDetails.getSymbol() + " no. of records " + stockDetailsList.size());
				LOGGER.debug("============================================================");
			}
			return result;
		} catch (Throwable th) {
			LOGGER.error(th.getMessage(), th);
			db.rollback();
			throw new Exception(th.getMessage());
		}

	}

	/**
	 * 
	 * @param dbName
	 *            database name. (e.g. test.db)
	 */
	public FileMapDb(final String dbName) {
		db = DBMaker.fileDB(dbName).make();
		init();
	}

	public void closeDB() {
		if (db != null) {
			db.close();
		}
	}

	public DB createVolumeDB(final String dbFile) {
		return DBMaker.fileDB(dbFile).fileMmapEnable().closeOnJvmShutdown().make();
	}

}
