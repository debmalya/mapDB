/**
 * 
 */
package db;

import org.mapdb.DB;
import org.mapdb.HTreeMap;

/**
 * @author debmalyajash
 *
 */
public class HTreeMapWrapper extends MapWrapper {
	
	public HTreeMap<?,?> createMapper(final DB db) {
		return db.hashMap(getMapName()).createOrOpen();
	}

	public HTreeMapWrapper(final String mapName) {
		setMapName(mapName);
	}
}
