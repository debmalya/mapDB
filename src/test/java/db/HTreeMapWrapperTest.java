package db;

import java.io.File;

import org.junit.Assert;
import org.junit.Test;
import org.mapdb.HTreeMap;

public class HTreeMapWrapperTest {

	@Test
	public void testCreateMapperDB() {
		MemoryMapDBWrapper memoryDb = new MemoryMapDBWrapper();
		HTreeMapWrapper htTreeMapWrapper = new HTreeMapWrapper("htreeMap");
		Assert.assertNotNull(htTreeMapWrapper);
		HTreeMap<String, Long> map = (HTreeMap<String, Long>) htTreeMapWrapper.createMapper(memoryDb.getMemoryDB(),
				1024);
		Assert.assertNotNull(map);
		Long timeThen = System.currentTimeMillis();
		map.put("Test", timeThen);
		Assert.assertEquals(timeThen, map.get("Test"));
	}

	@Test
	public void testCreateMapperDBInt() {
		MemoryMapDBWrapper memoryDb = new MemoryMapDBWrapper();
		HTreeMapWrapper htTreeMapWrapper = new HTreeMapWrapper("htreeMap1");
		Assert.assertNotNull(htTreeMapWrapper);
		HTreeMap<String, Long> map = (HTreeMap<String, Long>) htTreeMapWrapper.createMapper(memoryDb.getMemoryDB());
		Assert.assertNotNull(map);
		Long timeThen = System.currentTimeMillis();
		map.put("Test", timeThen);
		Assert.assertEquals(timeThen, map.get("Test"));
	}

	@Test
	public void testCreateMapperDBWithExpiry() {
		File f = new File(HTreeMapWrapper.EXPIRATION_DB_FILE_NAME);
//		if (f.exists()){
//			Assert.assertTrue("File deleted successfully",f.delete());
//		}
		MemoryMapDBWrapper memoryDb = new MemoryMapDBWrapper();
		HTreeMapWrapper htTreeMapWrapper = new HTreeMapWrapper("htreeMap2");
		HTreeMap<String, Long> map2 = (HTreeMap<String, Long>) htTreeMapWrapper
				.createExpirationEnabledMapper(memoryDb.getMemoryDB(), 1000L, 1000L, 1000L, 2);
		Assert.assertNotNull(map2);
		Long timeThen = System.currentTimeMillis();
		map2.put("Test", timeThen);
		Assert.assertEquals(timeThen, map2.get("Test"));

		long startTime = System.currentTimeMillis();
		// Now wait 2 seconds.
		try {
			Thread.sleep(2000L);
		} catch (Throwable th) {
			Assert.assertFalse(th.getMessage(), true);
		}
		// Wait is over does it update expiration.db ?
		
		if(f.exists() && !f.isDirectory()) { 
		    // do something
			Assert.assertTrue("Expiration db created",true);
		} else {
			Assert.assertFalse("Expiration db not created",true);
			Assert.assertTrue("There must be some modification after execution of test program",f.lastModified() > startTime);
		}

		Assert.assertEquals(timeThen, map2.get("Test"));
	}

}
