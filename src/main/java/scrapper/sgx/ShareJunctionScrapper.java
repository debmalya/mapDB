/**
 * 
 */
package scrapper.sgx;

import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;

/**
 * @author debmalyajash
 *
 */
public class ShareJunctionScrapper {

	/**
	 * @param args
	 * @throws IOException 
	 */
	public static void main(String[] args) throws IOException {
		parse();

	}
	
	public static void parse() throws IOException{
		String url = "http://www.sharejunction.com/sharejunction/viewStock.htm?id=24453";
		Element doc = Jsoup.connect(url).get();
		Element stockInfo = doc.getElementById("stockinfo");
		System.out.println(stockInfo.text());
		String[] entries = stockInfo.text().split(" ");
		for (String each:entries){
			System.out.println(each);
		}
	}

}
