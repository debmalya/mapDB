package db;

import org.junit.Assert;
import org.junit.Test;
import org.mapdb.BTreeMap;


public class BTreeMapWrapperTest {

	@Test
	public void test() {
		MemoryMapDBWrapper db = new MemoryMapDBWrapper();
		BTreeMapWrapper btreeWrapper = new BTreeMapWrapper("btreeMap");
		BTreeMap<String, Long> btreeMap =  (BTreeMap<String, Long>)btreeWrapper.createMapper(db.getMemoryDB());
		
		Long then = System.currentTimeMillis();
		btreeMap.put("Test", then);
		Assert.assertEquals(then, btreeMap.get("Test"));
		
	}
	
	@Test
	public void testWithNodeSize() {
		MemoryMapDBWrapper db = new MemoryMapDBWrapper();
		BTreeMapWrapper btreeWrapper = new BTreeMapWrapper("btreeMap");
		BTreeMap<String, Long> btreeMap =  (BTreeMap<String, Long>)btreeWrapper.createMapper(db.getMemoryDB(),64);
		
		Long then = System.currentTimeMillis();
		btreeMap.put("Test1", then);
		Assert.assertEquals(then, btreeMap.get("Test1"));
		
	}

}
