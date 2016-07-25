/**
 * 
 */
package scrapper.sgx;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

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
		// http://www.businesstimes.com.sg/breaking-news
		Element doc = Jsoup.connect(
				"http://www.businesstimes.com.sg/breaking-news").get();
		Elements links = doc.select("a[href]");

		boolean canStartPrinting = false;
		for (Element link : links) {
			String text = link.text();
			if (canStartPrinting) {
				System.out.printf("<%s>  (%s)\n", link.attr("abs:href"),
						link.text());
			}
			if (text.contains("Banking") && text.contains("Finance")) {
				canStartPrinting = true;
			}
		}
	}

}
