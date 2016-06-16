/**
 * 
 */
package db;

import java.util.concurrent.Executors;

import org.mapdb.DB;
import org.mapdb.DBMaker;
import org.mapdb.HTreeMap;

/**
 * HTreeMap provides HashMap and HashSet collections for MapDB. It optionally
 * supports entry expiration and can be used as a cache. It is thread-safe and
 * scales under parallel updates.
 * 
 * It is thread safe, and supports parallel writes by using multiple segments,
 * each with separate ReadWriteLock. ConcurrentHashMap in JDK 7 works in a
 * similar way. The number of segments (also called concurrency factor) is
 * configurable.
 * 
 * HTreeMap is a segmented Hash Tree. Unlike other HashMaps it does not use
 * fixed size Hash Table, and does not rehash all data when Hash Table grows.
 * HTreeMap uses auto-expanding Index Tree, so it never needs resize. It also
 * occupies less space, since empty hash slots do not consume any space. On the
 * other hand, the tree structure requires more seeks and is slower on access.
 * 
 * HTreeMap optionally supports entry expiration based on four criteria: maximal
 * map size, maximal storage size, time-to-live since last modification and
 * time-to-live since last access. Expired entries are automatically removed.
 * This feature uses FIFO queue and each segment has independent expiration
 * queue.
 * 
 * @author debmalyajash
 *
 */
public class HTreeMapWrapper extends MapWrapper {

	/**
	 * Name of the expiration DB.
	 */
	public static final String EXPIRATION_DB_FILE_NAME = "expiration.db";

	/**
	 * To deal with expiration overflow. After an entry expires from in-memory,
	 * it is automatically moved to on-disk by Modification Listener. And Value
	 * Loader will load values back to in-memory map, if those are not found by
	 * map.get() operation.
	 */
	// private

	// Big map populated with data expired from cache
	// private

	/**
	 * 
	 * @param db
	 *            MapDB instance.
	 * @param expiryAfterGet
	 *            expiration after get.
	 * @param expiryAfterCreate
	 *            expiration after create.
	 * @param expiryAferUpdate
	 *            expiration after update.
	 * @param numberOfHandlerThreads
	 *            number of threads to handle expiry.
	 * @return HTreeMap
	 */
	public HTreeMap<?, ?> createExpirationEnabledMapper(final DB db, final long expiryAfterGet,
			final long expiryAfterCreate, final long expiryAferUpdate, int numberOfHandlerThreads) {
		DB expirationDB = null;
		try {
			expirationDB = DBMaker.fileDB(EXPIRATION_DB_FILE_NAME).make();

			HTreeMap onDisk = expirationDB.hashMap("onDisk").createOrOpen();

			return DBMaker.memoryDB().make().hashMap(getMapName()).expireAfterGet(expiryAfterGet)
					.expireAfterCreate(expiryAfterCreate).expireAfterUpdate(expiryAferUpdate).expireOverflow(onDisk)
					.expireExecutor(Executors.newScheduledThreadPool(numberOfHandlerThreads)).createOrOpen();
		} finally {
			if (expirationDB != null) {
				expirationDB.close();
			}
		}

	}

	/**
	 * Creates HTreeMap from MapDB instance.
	 * 
	 * @param db
	 *            - MapDB instance
	 * @return HTreeMap.
	 */
	public HTreeMap<?, ?> createMapper(final DB db) {
		return db.hashMap(getMapName()).createOrOpen();
	}

	/**
	 * Creates HTreeMap from MapDB instance. Passed hashSeed will be used of
	 * hashing. This is is to avoid hash collision. Proposed hash seed is 1024.
	 * 
	 * 
	 * 
	 * @param db
	 *            MapDB instance
	 * @param hashSeed
	 *            Hash seed value.
	 * @return HTreeMap instance.
	 */
	public HTreeMap<?, ?> createMapper(final DB db, final int hashSeed) {
		// TODO : serializers are not added it will use default serializer.
		// Which will be slower.
		return db.hashMap(getMapName()).hashSeed(hashSeed).createOrOpen();
	}

	/**
	 * By default HTreeMap does not keep track of its size and map.size()
	 * performs a linear scan to count all entries. You can enable size counter
	 * and in that case map.size() is instant, but there is some overhead on
	 * inserts.
	 * 
	 * @param db
	 *            MapDB instance.
	 * @param hashSeed
	 *            to be used for hashing.
	 * @param counterEnabled
	 *            whether counter enabled or not.
	 * @return HTreeMap instance.
	 */
	public HTreeMap<?, ?> createMapper(final DB db, final int hashSeed, boolean counterEnabled) {
		if (counterEnabled) {
			return db.hashMap(getMapName()).hashSeed(hashSeed).counterEnable().createOrOpen();

		} else {
			return db.hashMap(getMapName()).hashSeed(hashSeed).createOrOpen();
		}
	}

	/**
	 * HTreeMap is split into separate segments. Each segment is independent and
	 * does not share any state with other segments. However they still share
	 * underlying Store and that affects performance under concurrent load. It
	 * is possible to make segments truly independent, by using separate Store
	 * for each segment.
	 * 
	 * Sharded HTreeMap has similar configurations options as HTreeMap created
	 * by DB. But there is no DB object associated with this HTreeMap. So in
	 * order to close Sharded HTreeMap, one has to invoke HTreeMap.close()
	 * method directly.
	 * 
	 * @param numberOfStores
	 *            - number of stores.
	 * @return HTreeMap.
	 */
	public HTreeMap<?, ?> createMapper(final int numberOfStores) {
		return DBMaker.memoryShardedHashMap(numberOfStores).createOrOpen();
	}

	// public HTreeMap>?,?> createMapper

	/**
	 * 
	 * @param mapName
	 *            name of the map.
	 */
	public HTreeMapWrapper(final String mapName) {
		setMapName(mapName);
	}
}
