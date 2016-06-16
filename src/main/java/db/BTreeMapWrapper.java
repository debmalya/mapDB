package db;

import org.mapdb.BTreeMap;
import org.mapdb.DB;

/**
 * This is related to BTreeMap operations.
 * 
 * BTreeMap provides TreeMap and TreeSet for MapDB. It is based on lock-free
 * concurrent B-Linked-Tree. It offers great performance for small keys and has
 * good vertical scalability.
 * 
 * @author debmalyajash
 *
 */
public class BTreeMapWrapper extends MapWrapper {

	/**
	 * 
	 * @param db
	 *            - MapDB instance.
	 * @return BTreeMap.
	 */
	public BTreeMap<?, ?> createMapper(final DB db) {
		return db.treeMap(getMapName()).createOrOpen();
	}

	/**
	 * BTrees store all their keys and values as part of a btree node. Node size
	 * affects the performance a lot. A large node means that many keys have to
	 * be deserialized on lookup. A smaller node loads faster, but makes large
	 * BTrees deeper and requires more operations. The default maximal node size
	 * is 32 entries and it can be changed in this way.
	 * 
	 * @param db
	 *            - MapDB instance.
	 * @param nodeSize - size of the node.           
	 * @return BTreeMap.
	 */
	public BTreeMap<?, ?> createMapper(final DB db, final int nodeSize) {
		return db.treeMap(getMapName()).maxNodeSize(nodeSize).createOrOpen();
	}

	/**
	 * 
	 * @param mapName
	 *            to set the map name.
	 */
	public BTreeMapWrapper(final String mapName) {
		setMapName(mapName);
	}
}
