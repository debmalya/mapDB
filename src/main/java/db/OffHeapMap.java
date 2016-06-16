package db;

import java.util.Map;

import org.mapdb.DB;

public class OffHeapMap {
	
	/**
	 * 
	 * @param db - MapDB.
	 * @param maxSize - maximum size of off-heap cache.
	 * @return Map of specified size.
	 */
	public Map createFixedSieCache(DB db,long maxSize){
		return db.hashMap("off-heap-map").expireMaxSize(maxSize).expireAfterGet().create();
	}
	
	

}
