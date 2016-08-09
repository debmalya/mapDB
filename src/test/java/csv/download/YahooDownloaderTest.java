/**
 * 
 */
package csv.download;

import java.io.File;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author debmalyajash
 *
 */
public class YahooDownloaderTest {

	/**
	 * Test method for {@link csv.download.YahooDownloader#download(java.lang.String)}.
	 */
//	@Test
	public final void testDownload5TP() {
		YahooDownloader loader = new YahooDownloader();
		List<String[]> rows = loader.download("5TP","SI");
		Assert.assertNotNull(rows);
		Assert.assertTrue(rows.size() > 0);
		File file = new File("data");
		Assert.assertTrue(file.exists());
		file = new File("./data/"+"5TP_SI.csv");
		Assert.assertTrue(file.exists());
	}
	
	
//	@Test
	public final void testDownloadADQU() {
		YahooDownloader loader = new YahooDownloader();
		List<String[]> rows = loader.download("ADQU","SI");
		Assert.assertNotNull(rows);
		Assert.assertTrue(rows.size() > 0);
		File file = new File("data");
		Assert.assertTrue(file.exists());
		file = new File("./data/"+"ADQU_SI.csv");
		Assert.assertTrue(file.exists());
	}
	
//	@Test
	public final void testDownloadO5RU() {
		YahooDownloader loader = new YahooDownloader();
		List<String[]> rows = loader.download("O5RU","SI");
		Assert.assertNotNull(rows);
		Assert.assertTrue(rows.size() > 0);
		File file = new File("data");
		Assert.assertTrue(file.exists());
		file = new File("./data/"+"O5RU_SI.csv");
		Assert.assertTrue(file.exists());
	}


//	@Test
	public final void testDownloadChallenger() {
		String symbol = "573";
		String suffix = "SI";
		YahooDownloader loader = new YahooDownloader();
		List<String[]> rows = loader.download(symbol,suffix);
		Assert.assertNotNull(rows);
		Assert.assertTrue(rows.size() > 0);
		File file = new File("data");
		Assert.assertTrue(file.exists());
		file = new File("./data/"+symbol+"_"+suffix+".csv");
		Assert.assertTrue(file.exists());
	}
	
	@Test
	public final void testDownloadLJ3() {
		String symbol = "LJ3";
		String suffix = "SI";
		YahooDownloader loader = new YahooDownloader();
		List<String[]> rows = loader.download(symbol,suffix);
		Assert.assertNotNull(rows);
		Assert.assertTrue(rows.size() > 0);
		File file = new File("data");
		Assert.assertTrue(file.exists());
		file = new File("./data/"+symbol+"_"+suffix+".csv");
		Assert.assertTrue(file.exists());
	}
	
//	@Test
	public final void testDownloadLupin() {
		String symbol = "LUPIN";
		String suffix = "NS";
		YahooDownloader loader = new YahooDownloader();
		List<String[]> rows = loader.download(symbol,suffix);
		Assert.assertNotNull(rows);
		Assert.assertTrue(rows.size() > 0);
		File file = new File("data");
		Assert.assertTrue(file.exists());
		file = new File("./data/"+symbol+"_"+suffix+".csv");
		Assert.assertTrue(file.exists());
	}
}
