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

import jetbrains.exodus.ByteIterable;
import jetbrains.exodus.bindings.StringBinding;
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
	private static final String GOOGLE = "Google";
	/**
	 * 
	 */
	private static final String GOOGLE_STOCK_SYMBOL = "goog";
	private static final Logger LOGGER = Logger.getLogger(EnvironmentWrapperTest.class);

	/**
	 * Test method for {@link xodus.EnvironmentWrapper#getEnv()}.
	 * 
	 * Objective is to test creation of store. Add some values into it and
	 * retrieve and check.
	 * 
	 * Environment wrapper is a wrapper over environment. It can give a store to
	 * me to play with.
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
			if (wrapper != null) {
				try {
					wrapper.closeEnvrionment();
				} catch (Exception e) {
					LOGGER.error(e.getMessage(), e);
				}
			}
		}

	}

	@Test

	public void testStore() {
		EnvironmentWrapper wrapper = null;
		try {
			wrapper = new EnvironmentWrapper();
			Assert.assertNotNull(wrapper.getEnv());
			Store myStore = wrapper.createStore("Xodus");
			Assert.assertNotNull(myStore);

			
			wrapper.storeKeyValue(GOOGLE, GOOGLE_STOCK_SYMBOL, myStore);
			
		} catch (Throwable th) {
			LOGGER.error(th.getMessage(), th);
			Assert.assertFalse(th.getMessage(), true);
		} finally {
			if (wrapper != null) {
				try {
					wrapper.closeEnvrionment();
				} catch (Exception e) {
					LOGGER.error(e.getMessage(), e);
				}
			}
		}
	}
}
