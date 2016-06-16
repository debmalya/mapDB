package db;

import org.mapdb.DB;
import org.mapdb.DBMaker;

/**
 * Memory mapped files are much faster and should be enabled on 64bit systems
 * for better performance.
 * 
 * @author debmalyajash
 *
 */
public class MemoryMapDBWrapper {
	
	/**
	 * ByteBuffer for memory. User DBMaker.memoryDB
	 */
	private DB memoryDB = DBMaker.memoryDB().make();
	
	/**
	 * DirectByteBuffer for direct memory. Use DBMaker.memoryDirectDB()
	 */
	private DB memoryDirectDB = DBMaker.memoryDirectDB().make();

	/**
	 * Get memory DB.
	 * @return memory DB.
	 */
	public DB getMemoryDB() {
		return memoryDB;
	}

	/**
	 * Get direct memory DB.
	 * @return direct memory DB.
	 */
	public DB getMemoryDirectDB() {
		return memoryDirectDB;
	}

}
