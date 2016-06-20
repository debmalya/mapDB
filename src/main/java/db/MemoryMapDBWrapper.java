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
	 * 
	 * @return memory DB.
	 */
	public DB getMemoryDB() {
		return memoryDB;
	}

	/**
	 * Get direct memory DB.
	 * 
	 * @return direct memory DB.
	 */
	public DB getMemoryDirectDB() {
		return memoryDirectDB;
	}

	/**
	 * Memory mapped files are activated with DBMaker.mmapFileEnable() setting.
	 * One can also activate mmap files only if a 64bit platform is detected:
	 * DBMaker.mmapFileEnableIfSupported(). Mmap files are highly dependent on
	 * the operating system. For example, on Windows you cannot delete a mmap
	 * file while it is locked by JVM. If Windows JVM dies without closing the
	 * mmap file, you have to restart Windows to release the file lock.
	 * 
	 * There is also bug in JVM. Mmaped file handles are not released until
	 * DirectByteBuffer is GCed. That means that mmap file remains open even
	 * after db.close() is called. On Windows it prevents file to be reopened or
	 * deleted. On Linux it consumes file descriptors, and could lead to errors
	 * once all descriptors are used. There is a workaround for this bug using
	 * undocumented API. But it was linked to JVM crashes in rare cases and is
	 * disabled by default. Use DBMaker.cleanerHackEnable() to enable it.
	 * 
	 * 
	 * @param dbName
	 *            database name.
	 * @return instance of DB.
	 */
	public DB getMemoryMappedDB(final String dbName) {
		DB db = DBMaker.fileDB(dbName).fileMmapEnable().fileMmapEnableIfSupported().cleanerHackEnable().make();
		db.getStore().preallocate();
		return db;
	}

	/**
	 * By default MapDB tries minimize space usage and allocates space in 1MB
	 * increments. This additional allocations might be slower than single large
	 * allocation. There are two options to control storage initial size and
	 * size increment. This example will allocate <starterSizeinGB> GB initially and then
	 * increment size in 1MB chunks:
	 * 
	 * @param dbName
	 * @param starterSizeinGB
	 * @param incrementSizeInMB
	 * @return
	 */
	public DB getMemoryMappedDB(final String dbName, final int starterSizeinGB) {
		return DBMaker
			    .fileDB(dbName)
			    .fileMmapEnable()
			    .allocateStartSize( starterSizeinGB * 1024*1024*1024)
			    .make();
		
	}

}
