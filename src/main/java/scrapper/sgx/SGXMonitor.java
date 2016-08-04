/**
 * 
 */
package scrapper.sgx;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.apache.log4j.Logger;

/**
 * @author debmalyajash
 *
 */
public class SGXMonitor {
	
	private static final Logger LOGGER = Logger.getLogger(SGXMonitor.class);

	/**
	 * @param args
	 */
	public static void main(String[] args) {
			monitor();

	}
	
	public static void monitor(){
		Properties propertis = new Properties();
		InputStream inStream = ClassLoader.getSystemResourceAsStream("monitor_symbol.properties");
		try {
			SGXScrapper scrapper = new SGXScrapper();
			propertis.load(inStream);
			inStream.close();
			String value = propertis.getProperty("monitor");
			String[] symbols = value.split(",");
			for (String each:symbols) {
				System.out.println(each);
			}
		} catch (IOException e) {
			LOGGER.error(e.getMessage(),e);
		}
	}

}
