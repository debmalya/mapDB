package db;

import org.mapdb.DB;
import org.mapdb.DBMaker;

/**
 * MapDB allows composite keys in the form of Object[]. Interval submaps can be
 * used to fetch tuple subcomponents, or to create a simple form of multimap.
 * Object array is not comparable, so you need to use specialized serializer
 * which provides comparator.
 * 
 * @author debmalyajash
 *
 */
public class CompositeKeyWrapper extends MapWrapper {
	/**
	 * In memory DB.
	 */
	private DB db = DBMaker.memoryDB().make();
	
	
}
