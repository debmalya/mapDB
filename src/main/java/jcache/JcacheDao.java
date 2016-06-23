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

import java.util.concurrent.TimeUnit;

import javax.cache.Cache;
import javax.cache.CacheException;
import javax.cache.CacheManager;
import javax.cache.Caching;
import javax.cache.configuration.MutableConfiguration;
import javax.cache.expiry.AccessedExpiryPolicy;
import javax.cache.expiry.Duration;
import javax.cache.spi.CachingProvider;

import model.StockDetails;

/**
 * @author debmalyajash
 *
 */
public class JcacheDao {
	private CachingProvider jcacheProvider = Caching.getCachingProvider();

	private CacheManager cacheManager = jcacheProvider.getCacheManager();

	private Duration expiryDuration = new Duration(TimeUnit.HOURS, 1);
//	private MutableConfiguration<String, StockDetails> configuration = new MutableConfiguration<String, StockDetails>()
//			.setTypes(String.class, StockDetails.class)
//			.setExpiryPolicyFactory(AccessedExpiryPolicy.factoryOf(expiryDuration)).setStatisticsEnabled(true);
	
	private MutableConfiguration<String, StockDetails> configuration = new MutableConfiguration<String, StockDetails>()
			.setTypes(String.class, StockDetails.class);

	private Cache<String, StockDetails> cache;

	public JcacheDao(final String cacheName) {
		if (cache == null) {
			try {
				cache = cacheManager.createCache(cacheName, configuration);
			} catch (CacheException cacheExpiration) {
				if (cacheExpiration.getMessage().contains("already exists")) {
					cache = cacheManager.getCache(cacheName, String.class, StockDetails.class);
				}
			}
		}
	}

	/**
	 * 
	 * @param stockSymbol
	 *            to store
	 * @param stockDetails
	 *            stock details related to the stock symbol.
	 */
	public void put(final String stockSymbol, final StockDetails stockDetails) {
		if (cache != null) {
			cache.put(stockSymbol, stockDetails);
		} else {
			System.err.println("Cache is null");
		}
	}

	/**
	 * Return stock details.
	 * 
	 * @param stockSymbol
	 * @return
	 */
	public StockDetails get(final String stockSymbol) {
		return cache.get(stockSymbol);
	}
}
