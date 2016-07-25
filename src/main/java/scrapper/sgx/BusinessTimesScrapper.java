/**
 * 
 */
package scrapper.sgx;

import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 * @author debmalyajash
 *
 */
public class BusinessTimesScrapper {

	/**
	 * @param args
	 * @throws IOException 
	 */
	public static void main(String[] args) throws IOException {
		parse();

	}
	
	public static void parse() throws IOException {
		Element doc = Jsoup.connect("http://www.businesstimes.com.sg").get();
//		getText(doc);
		Elements allElements = doc.getAllElements();
		for (Element each:allElements) {
			System.out.println(each);
//			getText(each);
		}
	}

	/**
	 * @param doc
	 */
	private static void getText(Element doc) {
		System.out.println(doc.text());
		
	}
	

}
