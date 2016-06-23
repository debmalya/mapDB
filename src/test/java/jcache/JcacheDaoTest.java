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
package jcache;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import model.StockDetails;
import scrapper.YhoStockQuote;

/**
 * @author debmalyajash
 *
 */
public class JcacheDaoTest {

	/**
	 * Test method for {@link jcache.JcacheDao#JcacheDao(java.lang.String)}.
	 */
	@Test
	public void testJcacheDao() {
		try {
			JcacheDao jDao = new JcacheDao("StockDetails");
			Assert.assertNotNull(jDao);
		} catch (Throwable th) {
			th.printStackTrace();
			Assert.assertFalse("Expected there will be no error during cache creation", true);
		}

	}

	/**
	 * Test method for
	 * {@link jcache.JcacheDao#put(java.lang.String, model.StockDetails)}.
	 */
	@Test
	public void testPut() {
		try {
			JcacheDao jDao = new JcacheDao("StockDetails");
			Assert.assertNotNull(jDao);
			YhoStockQuote stockQuote = new YhoStockQuote();
			List<StockDetails> stockDetails = stockQuote.parse("goog");
			for (StockDetails eachStock : stockDetails) {
				jDao.put("goog", eachStock);
			}
		} catch (Throwable th) {
			th.printStackTrace();
			Assert.assertFalse("Expected there will be no error during cache creation", true);
		}
	}

	/**
	 * Test method for {@link jcache.JcacheDao#get(java.lang.String)}.
	 */
	@Test
	public void testGet() {
		try {
			JcacheDao jDao = new JcacheDao("StockDetails");
			Assert.assertNotNull(jDao);
			YhoStockQuote stockQuote = new YhoStockQuote();
			List<StockDetails> stockDetails = stockQuote.parse("goog");
			for (StockDetails eachStock : stockDetails) {
				jDao.put("goog", eachStock);
			}
			StockDetails theLastEntry = jDao.get("goog");
			Assert.assertNotNull("Already entered value, stock must be retrieved",theLastEntry);
			System.out.println(theLastEntry);
		} catch (Throwable th) {
			th.printStackTrace();
			Assert.assertFalse("Expected there will be no error during cache creation", true);
		}
	}

}
