/**
 * 
 */
package scrapper.sgx;

import model.StockDetails;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author debmalyajash
 *
 */
public class GoogleScrapperTest {

	/**
	 * Test method for {@link scrapper.sgx.GoogleScrapper#scrap(java.lang.String)}.
	 */
	@Test
	public final void testScrap() {
		GoogleScrapper scrapper = new GoogleScrapper();
		StockDetails stockDetails = scrapper.scrap("5TP");
		Assert.assertEquals("5TP", stockDetails.getSymbol());
	}

}
