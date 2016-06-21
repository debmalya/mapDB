/**
 * Copyright 2015-2016 Debmalya Jash
 * <p/>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p/>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p/>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package xodus;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.jetbrains.annotations.NotNull;

import jetbrains.exodus.ByteIterable;
import jetbrains.exodus.bindings.StringBinding;
import jetbrains.exodus.entitystore.Entity;
import jetbrains.exodus.entitystore.EntityIterable;
import jetbrains.exodus.entitystore.PersistentEntityStore;
import jetbrains.exodus.entitystore.PersistentEntityStores;
import jetbrains.exodus.entitystore.StoreTransaction;
import jetbrains.exodus.env.Environment;
import jetbrains.exodus.env.EnvironmentConfig;
import jetbrains.exodus.env.Environments;
import jetbrains.exodus.env.Store;
import jetbrains.exodus.env.StoreConfig;
import jetbrains.exodus.env.Transaction;
import jetbrains.exodus.env.TransactionalComputable;
import jetbrains.exodus.env.TransactionalExecutable;
import model.StockDetails;

/**
 * @author debmalyajash
 *
 */
public class EnvironmentWrapper {
	/**
	 * To log messages.
	 */
	private static final Logger LOGGER = Logger.getLogger(EnvironmentWrapper.class);
	/**
	 * opens existing database or creates the new one in the directory passed as
	 * a parameter. Each environment should have different directories. All
	 * environment data will be stored in ./src/resource/.myAppData This will
	 * open environment with default configuration.
	 */
	private Environment env;

	/**
	 * Lock for initializing environment in thread safe way.
	 */
	private static final Object environmentLock = new Object();

	/**
	 * Lock for initializing persistent store in thread safe way.
	 */
	private static final Object persistentStoreLock = new Object();
	/**
	 * 
	 */
	private Environment envWithoutGC;

	public Environment getEnv() {
		return env;
	}

	/**
	 * With the blessings of PersistentStores we are opening new persistent
	 * entity store.
	 */
	private PersistentEntityStore entityStore;
	// =
	// PersistentEntityStores.newInstance("/src/test/resource/PersistentStore/.myAppData");

	/**
	 * A transactional closure is used as the simplest way to manage
	 * transactions and updates within a transaction.Once you get a Store
	 * object, you can put values by keys in it and get values by keys from it.
	 * On the Environment layer, all data is binary and untyped, and it is
	 * represented by ByteIterable instances. ByteIterable is a kind of byte
	 * array or Iterable<Byte>. Prepare the data and proceed with a closure to
	 * put it into the store:
	 * 
	 * @param storeName
	 *            name of the store where key value pairs will be kept.
	 * @return newly created store.
	 */
	public Store createStore(final String storeName) {
		final Store store = env.computeInTransaction(new TransactionalComputable<Store>() {
			@Override
			public Store compute(@NotNull final Transaction txn) {
				return env.openStore(storeName, StoreConfig.WITHOUT_DUPLICATES, txn);
			}
		});
		return store;
	}

	/**
	 * Default constructor
	 * 
	 * @throws Exception
	 *             if any exception occurs while creating environment instances.
	 */
	public EnvironmentWrapper() throws Exception {
		try {

			if (env == null) {
				synchronized (environmentLock) {
					env = Environments.newInstance("./src/test/resource/environment1/.myAppData");
					LOGGER.debug("Default environment initialized.");
				}
			}
			if (envWithoutGC == null) {
				synchronized (environmentLock) {
					envWithoutGC = Environments.newInstance("./src/test/resource/environment2/.myAppDataWithoutGC",
							new EnvironmentConfig().setGcEnabled(false));
					LOGGER.debug("Environment without garbage collection initialized.");
				}
			}
		} catch (Throwable th) {
			LOGGER.error(th.getMessage(), th);
			throw new Exception(th.getMessage(), th);
		}
	}

	/**
	 * To store key and value both are String.
	 * 
	 * @param key
	 *            to be stored.
	 * @param value
	 *            to be stored.
	 * @return true if stored successfully
	 * @throws Exception
	 *             - if any exception occurs during storing.
	 */
	public boolean storeKeyValue(final String keyToStore, final String valueToStore, final Store store)
			throws Exception {
		try {
			final ByteIterable key = StringBinding.stringToEntry(keyToStore);
			final ByteIterable value = StringBinding.stringToEntry(valueToStore);

			env.executeInTransaction(new TransactionalExecutable() {
				@Override
				public void execute(@NotNull final Transaction txn) {
					store.put(txn, key, value);
				}
			});
			return true;
		} catch (Throwable th) {
			throw new Exception(th.getMessage(), th);
		}
	}

	/**
	 * 
	 * @param directory
	 *            where entity store will be created.
	 * @return PersistentEntityStore
	 * @throws Exception
	 */
	public PersistentEntityStore getPersistentStore(final String directory) throws Exception {
		try {
			if (entityStore == null) {
				synchronized (persistentStoreLock) {
					entityStore = PersistentEntityStores.newInstance(directory);
				}
			}
			return entityStore;
		} catch (Throwable th) {
			LOGGER.error(th.getMessage(), th);
			throw new Exception(th.getMessage(), th);
		}
	}

	/**
	 * Add stock details in persistent store.
	 * 
	 * @param stockDetails
	 *            to be stored.
	 * @return true if stored successfully.
	 * @throws Exception
	 *             if there is any error
	 */
	public boolean addStock(final List<StockDetails> stockList) throws Exception {
		
		final StoreTransaction txn = entityStore.beginTransaction();

		try {
			for (StockDetails stockDetails : stockList) {
				// Setting entity
				final Entity stockDetailsEntity = txn.newEntity("StockDetails");
				stockDetailsEntity.setProperty("symbol", stockDetails.getSymbol());
				stockDetailsEntity.setProperty("exchange", stockDetails.getExchange());
				stockDetailsEntity.setProperty("currentPrice", stockDetails.getCurrentPrice());
				stockDetailsEntity.setProperty("currentPriceRecordTime", stockDetails.getCurrentPriceRecordTime());
				stockDetailsEntity.setProperty("lastPrice", stockDetails.getLastPrice());
				stockDetailsEntity.setProperty("lastPriceRecordTime", stockDetails.getLastPriceRecordTime());
				stockDetailsEntity.setProperty("marketCapital", stockDetails.getMarketCapital());
				stockDetailsEntity.setProperty("change", stockDetails.getChange());
				txn.saveEntity(stockDetailsEntity);
				LOGGER.debug("Saved stock :" + stockDetails);
			}

			txn.flush();
			txn.commit();
			LOGGER.debug("Transaction committed");

			return true;

		} catch (Throwable th) {
			txn.abort();
			throw new Exception(th.getMessage());
		} finally {
			
			entityStore.close();
		}
	}
	
	/**
	 * 
	 * @return List of all the stocks saved.
	 * @throws Exception if any error occurs.
	 */
	public List<StockDetails> getStocks() throws Exception {
		List<StockDetails> retrievedStockList =  new ArrayList<>();
		final StoreTransaction txn = entityStore.beginReadonlyTransaction();
		try {
			final EntityIterable allStocks = txn.getAll("StockDetails");
			for (Entity stock: allStocks) {
				String symbol = (String) stock.getProperty("symbol");
				String exchange = (String)stock.getProperty("exchange");
				float currentPrice = (Float)stock.getProperty("currentPrice");
				String currentPriceRecordTime = (String)stock.getProperty("currentPriceRecordTime");
				float lastPrice = (Float)stock.getProperty("lastPrice"); 
				String lastPriceRecordTime = (String)stock.getProperty("lastPriceRecordTime");
				String marketCapital = (String)stock.getProperty("marketCapital");
				float change = (Float)stock.getProperty("change"); 
				
			    
			    StockDetails stockDetails = new StockDetails(exchange, symbol, currentPrice, lastPrice, change, marketCapital, lastPriceRecordTime, currentPriceRecordTime);
			    retrievedStockList.add(stockDetails);
			}

		}catch(Throwable th) {
			throw new Exception(th.getMessage(),th);
		}finally {
		}
		return retrievedStockList;
	}

	/**
	 * To get the value for a key. Using TransactionalComputable which returns a
	 * value.
	 * 
	 * @param keyInStore
	 *            key we want to retrieve
	 * @param store
	 *            from where we want to retrieve the value.
	 * @return value.
	 */
	public String getValue(final String keyInStore, final Store store) {
		final ByteIterable key = StringBinding.stringToEntry(keyInStore);
		final ByteIterable value = env.computeInReadonlyTransaction(new TransactionalComputable<ByteIterable>() {
			@Override
			public ByteIterable compute(@NotNull final Transaction txn) {
				return store.get(txn, key);
			}
		});
		if (value != null) {
			return StringBinding.entryToString(value);
		} else {
			return null;
		}
	}

	/**
	 * Close environment.
	 * 
	 * @throws Exception
	 */
	public void closeEnvrionment() throws Exception {
		try {
			if (env.isOpen()) {
				env.close();
			}

			if (envWithoutGC.isOpen()) {
				envWithoutGC.close();
			}

		} catch (Throwable th) {
			LOGGER.error(th.getMessage(), th);
			throw new Exception(th);
		}
	}
}
