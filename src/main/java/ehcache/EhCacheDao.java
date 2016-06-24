/**
 * Copyright 2015-2016 Debmalya Jash
 *
 */
package ehcache;

import javax.json.JsonObject;

import org.ehcache.Cache;
import org.ehcache.CacheManager;
import org.ehcache.config.builders.CacheConfigurationBuilder;
import org.ehcache.config.builders.CacheManagerBuilder;
import org.ehcache.config.builders.ResourcePoolsBuilder;

import model.StockDetails;



/**
 * @author debmalyajash
 *
 */
public class EhCacheDao {

	/**
	 * Cache Manager.
	 */
	private CacheManager cacheManager = null;

	/**
	 * This will hold ID/MSISDN as key and JsonObject as value.
	 */
	private Cache<String, StockDetails> cache;

	/**
	 * To initiate EhCacheDao with cacheManager and cache.
	 * @param cacheName name of the cache.
	 * @param cacheSize
	 *            cache size.
	 */
	public EhCacheDao(final String cacheName, final long cacheSize) {
		setCacheManager(CacheManagerBuilder.newCacheManagerBuilder().withCache(cacheName, CacheConfigurationBuilder
				.newCacheConfigurationBuilder(String.class, StockDetails.class, ResourcePoolsBuilder.heap(cacheSize))
				.build()).build(true));

		setCache(cacheManager.getCache(cacheName, String.class, StockDetails.class));
	}

	/**
	 * This may need to close the cache manager.
	 * @return Cache Manager.
	 */
	public CacheManager getCacheManager() {
		return cacheManager;
	}

	/**
	 * 
	 * @param cacheManager setting cache manager.
	 */
	private void setCacheManager(CacheManager cacheManager) {
		this.cacheManager = cacheManager;
	}

	/**
	 * 
	 * @return Cache which will work with String as Key and JsonObject as value.
	 */
	public Cache<String, StockDetails> getCache() {
		return cache;
	}

	/**
	 * 
	 * @param cache
	 *            to set.
	 */
	private void setCache(Cache<String, StockDetails> cache) {
		this.cache = cache;
	}

	/**
	 * To stored key (id, MSISDN) and related indicator JsonObject.
	 * 
	 * @param key
	 *            to be stored.
	 * @param value
	 *            with associated value.
	 */
	public void put(final String key, final StockDetails value) {
		cache.put(key, value);
	}

	/**
	 * @param key
	 *            whose JsonObject will be retrieved.
	 * @return JsonObject if available in the cache, otherwise null.
	 */
	public StockDetails get(final String key) {
		return cache.get(key);
	}

}
