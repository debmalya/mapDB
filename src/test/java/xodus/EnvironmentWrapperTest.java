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

import java.util.List;

import org.apache.log4j.Logger;
import org.junit.Assert;
import org.junit.Test;

import jetbrains.exodus.entitystore.PersistentEntityStore;
import jetbrains.exodus.env.Store;
import model.StockDetails;
import scrapper.YhoStockQuote;

/**
 * @author debmalyajash
 *
 */
public class EnvironmentWrapperTest {
	/**
	 * 
	 */
	private static final String MICROSOFT_STOCK_SYMBOL = "msft";
	/**
	 * 
	 */
	private static final String MICROSOFT = "Microsoft";
	/**
	 * 
	 */
	private static final String GOOGLE = "Google";
	/**
	 * 
	 */
	private static final String GOOGLE_STOCK_SYMBOL = "goog";
	private static final Logger LOGGER = Logger.getLogger(EnvironmentWrapperTest.class);

	/**
	 * Test method for {@link xodus.EnvironmentWrapper#getEnv()}.
	 * 
	 * 
	 * 
	 */
	@Test
	public void testGetEnv() {
		EnvironmentWrapper wrapper = null;
		try {
			wrapper = new EnvironmentWrapper();
			Assert.assertNotNull(wrapper.getEnv());

			wrapper.closeEnvrionment();

		} catch (Throwable th) {
			LOGGER.error(th.getMessage(), th);
			Assert.assertFalse(th.getMessage(), true);
		} finally {
			closeEnvironment(wrapper);
		}

	}

	/**
	 * Objective is to test creation of store. Add some values into it and
	 * retrieve and check.
	 * 
	 * Environment wrapper is a wrapper over environment. It can give a store to
	 * me to play with.
	 */
	@Test
	public void testStore() {
		EnvironmentWrapper wrapper = null;
		try {
			wrapper = new EnvironmentWrapper();
			Assert.assertNotNull(wrapper.getEnv());
			Store myStore = wrapper.createStore("Xodus");
			Assert.assertNotNull(myStore);

			// Testing with existing key
			wrapper.storeKeyValue(GOOGLE, GOOGLE_STOCK_SYMBOL, myStore);
			String actual = wrapper.getValue(GOOGLE, myStore);
			Assert.assertEquals("Expected '" + GOOGLE_STOCK_SYMBOL + "' but found '" + actual + "'",
					GOOGLE_STOCK_SYMBOL, actual);

			// Testing with non existing key
			actual = wrapper.getValue(MICROSOFT, myStore);
			Assert.assertNull("this value is not in store, got '" + actual + "'", actual);

		} catch (Throwable th) {
			LOGGER.error(th.getMessage(), th);
			Assert.assertFalse(th.getMessage(), true);
		} finally {
			closeEnvironment(wrapper);
		}
	}

	private void closeEnvironment(EnvironmentWrapper wrapper) {
		if (wrapper != null) {
			try {
				wrapper.closeEnvrionment();
			} catch (Exception e) {
				LOGGER.error(e.getMessage(), e);
				Assert.assertFalse(e.getMessage(), true);
			}
		}
	}

	/**
	 * To test entity store creation.
	 * Storing of entity.
	 */
	@Test
	public void testPersistentStore() {
		EnvironmentWrapper wrapper = null;
		try {
			wrapper = new EnvironmentWrapper();
			PersistentEntityStore persistentStore = wrapper
					.getPersistentStore("./src/test/resource/PersistentStore/.stockStore");
			Assert.assertNotNull("Persistent store should be created", persistentStore);
			
			YhoStockQuote stockExchange = new YhoStockQuote();
			List<StockDetails> stockList = stockExchange.parse(GOOGLE_STOCK_SYMBOL);
			stockList.addAll(stockExchange.parse(MICROSOFT_STOCK_SYMBOL));
			
			wrapper.addStock(stockList);
			
		} catch (Throwable th) {
			LOGGER.error(th.getMessage(), th);
			Assert.assertFalse("ERR:" + th.getMessage(), true);

		} finally {
			closeEnvironment(wrapper);
		}
	}
	
	/**
	 * To test entity store retrieval.
	 * Retrieval of entity.
	 */
//	@Test
	public void testPersistentRetrieval() {
		EnvironmentWrapper wrapper = null;
		try {
			wrapper = new EnvironmentWrapper();
			PersistentEntityStore persistentStore = wrapper
					.getPersistentStore("./src/test/resource/PersistentStore/.stockStore");
			Assert.assertNotNull("Persistent store should be created", persistentStore);
			
		
			List<StockDetails> stockList = wrapper.getStocks();
			Assert.assertTrue("There must be entries in the stock list",!stockList.isEmpty());
			for (StockDetails stockDetails : stockList){
				Assert.assertNotNull(stockDetails);
				LOGGER.debug(stockDetails);
				Assert.assertTrue("Symbol must be either '" + GOOGLE_STOCK_SYMBOL+"' or '" + MICROSOFT_STOCK_SYMBOL+"'", stockDetails.getSymbol().equals(GOOGLE_STOCK_SYMBOL) ||  stockDetails.getSymbol().equals(MICROSOFT_STOCK_SYMBOL));
			}
		} catch (Throwable th) {
			LOGGER.error(th.getMessage(), th);
			Assert.assertFalse("ERR:" + th.getMessage(), true);

		} finally {
			closeEnvironment(wrapper);
		}
	}
}
