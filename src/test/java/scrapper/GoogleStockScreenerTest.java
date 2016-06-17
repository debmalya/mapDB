package scrapper;

import org.junit.Assert;
import org.junit.Test;

public class GoogleStockScreenerTest {

	@Test
	public void testParseData() {
		GoogleStockScreener gs = new GoogleStockScreener();
		try {
			gs.parseData();
		} catch (Exception e) {
			Assert.assertFalse(e.getMessage(),true);
		}
	}

}
