package db;

import java.util.concurrent.ConcurrentMap;

import org.mapdb.DB;

/**
 * 
 * @author debmalyajash
 *
 */
public class ConcurrentMapWrapper extends MapWrapper{
	
	
	
	/**
	 * From the passed DB it will create a map and return it.
	 * @param db
	 * @return
	 */
	@SuppressWarnings("deprecation")
	public ConcurrentMap<?,?> getConcurrentHashMap(final DB db){
		if (getMapName() != null) {
			return db.hashMap(getMapName()).make();
		}
		return db.hashMap("map").make();
	}
	
	/**
	 * It will create a map with the passed name.
	 * @param mapName map name.
	 */
	public ConcurrentMapWrapper(final String mapName) {
		setMapName(mapName);
	}

}
