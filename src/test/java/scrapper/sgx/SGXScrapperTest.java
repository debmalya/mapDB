/**
 * 
 */
package scrapper.sgx;

import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import au.com.bytecode.opencsv.CSVReader;

/**
 * @author debmalyajash
 *
 */
public class SGXScrapperTest {

	private static Map<String, String> symbolMap = new HashMap<>();

	/**
	 * Test method for
	 * {@link scrapper.sgx.SGXScrapper#yahooFinance(java.lang.String)}.
	 */
	@BeforeClass
	public static void setUp() {
		try (CSVReader reader = new CSVReader(new FileReader(
				"./src/main/resources/SGX.csv"))) {
			List<String[]> allValues = reader.readAll();
			for (String[] each : allValues) {
				symbolMap.put(each[0], each[1]);
			}
		} catch (IOException e) {
			System.err.println(e.getMessage());
		}
	}

	@AfterClass
	public static void cleanUp() {
		symbolMap.clear();
	}

	@Test
	public final void testYahooFinance() {
		long startTime = System.currentTimeMillis();
		SGXScrapper scrapper = new SGXScrapper(false);
		try {
//			ExecutorService pool = Executors.newCachedThreadPool();
			if (!symbolMap.isEmpty()) {
				Set<String> keySet = symbolMap.keySet();
				for (String eachSymbol : keySet) {
//					pool.submit(() -> scrapper.yahooFinance(eachSymbol));
					scrapper.yahooFinance(eachSymbol,symbolMap.get(eachSymbol));
				}

			}
//			pool.awaitTermination(5, TimeUnit.MINUTES);
		} catch (Throwable th) {
			System.err.println(th.getMessage());
			th.printStackTrace();
		} finally {
			scrapper.cleanUp();
		}
		System.out.println("Time taken :" + (System.currentTimeMillis() - startTime));
	}
}
