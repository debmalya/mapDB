/**
 * Copyright 2015-2016 Debmalya Jash
 * <p/>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p/>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p/>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package scrapper;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 * @author debmalyajash
 *
 */
public class GoodReadsQuotes {

	private static String URL_FIRST_PART = "https://www.goodreads.com/quotes/search?commit=Search&page=";

	private static String URL_SECOND_PART = "&q=Vivekananda&utf8=%E2%9C%93";

	private static PrintWriter quoteWriter;

	/**
	 * @param args
	 * @throws FileNotFoundException
	 */
	public static void main(String[] args) {
		try {
			quoteWriter = new PrintWriter("vivekananda.txt");
			quoteWriter.println("$motivational_quotes = array(");
			for (int i = 1; i < 33; i++) {
				try {
					List<String> quoteList = getQuote(URL_FIRST_PART + i + URL_SECOND_PART, quoteWriter);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			quoteWriter.println(");");
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} finally {
			if (quoteWriter != null) {
				quoteWriter.flush();
				quoteWriter.close();
			}
		}

	}

	private static List<String> getQuote(String url, PrintWriter writer) throws IOException {
		List<String> quoteList = new ArrayList<>();
		Element doc = Jsoup.connect(url).get();
		Elements contents = doc.getElementsByClass("quoteText");
		for (Element eachContent : contents) {
			String quotation_text = eachContent.text();
			int lastIndex = quotation_text.lastIndexOf("\"");
			if (lastIndex > -1) {
				quotation_text = quotation_text.substring(0, lastIndex);
			}
			quotation_text = quotation_text.replace("\"", "'");
			if (quotation_text.length() < 4500) {
				quoteWriter.println(quotation_text + ",");
			}
		}
		return null;
	}
}
