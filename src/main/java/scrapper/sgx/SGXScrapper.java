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
package scrapper.sgx;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 * @author debmalyajash
 *
 */
public class SGXScrapper {
	
	private static Map<String,String> companySymbol = new HashMap<>();
	
	static {
		companySymbol.put("BreadTalk Group Ltd", "5DA.SI");
	}

	/**
	 * 
	 */
	private static final int PREFERRED_WIDTH = 35;

	private static final String FOOL = "http://www.sgx.com";
	
	private static final String YAHOO_FINANCE = "https://sg.finance.yahoo.com/q/hp?s=";

	private static final Logger LOGGER = Logger.getLogger(SGXScrapper.class);

	public static void main(String... args) throws IOException {
		fooled();
	}

	/**
	 * @throws IOException
	 * 
	 */
	private static void fooled() throws IOException {
		Element doc = Jsoup.connect(FOOL).get();
		if (LOGGER.isDebugEnabled()) {
			debugLog(doc.text(), 80,"sgx.txt");
		}
		Elements links = doc.select("a[href]");
		Elements media = doc.select("[src]");
		Elements imports = doc.select("link[href]");

		print("\nLinks: (%d)", links.size());
		for (Element link : links) {
//			print(" * a: <%s>  (%s)", link.attr("abs:href"), trim(link.text(), 35));
			if (link.text().equalsIgnoreCase("Gainers")){
				Element subDoc = Jsoup.connect(link.attr("abs:href")).get();
				System.out.println(link.attr("abs:href"));
				debugLog(subDoc.text(), 80,"sgx_gainers.txt");
			}
//			print(" * a: <%s>  (%s)", link.attr("abs:href"), link.text());
		}

	}

	/**
	 * @param text
	 * @param preferredWidth
	 * @throws FileNotFoundException
	 */
	private static void debugLog(String text, int preferredWidth,String fileName) throws FileNotFoundException {
		PrintWriter sgxWriter = null;
		try {
			String[] allWords = text.split(" ");
			StringBuilder sb = new StringBuilder();
			sgxWriter = new PrintWriter(fileName);
			int currentLength = 0;
			for (int i = 0; i < allWords.length; i++) {
				if (currentLength + allWords[i].length() < preferredWidth) {
					sb.append(allWords[i]);
					sb.append(" ");
					currentLength += allWords[i].length() + 1;
				} else {
					sgxWriter.println(sb.toString());
					sb.delete(0, sb.length());
					currentLength = 0;
				}
			}
		} finally {
			if (sgxWriter != null) {
				sgxWriter.close();
			}
		}

	}

	private static void print(String msg, Object... args) {
		System.out.println(String.format(msg, args));
	}

	private static String trim(String s, int width) {
		if (s.length() > width)
			return s.substring(0, width - 1) + ".";
		else
			return s;
	}
}

/*
 * https://www.fool.sg/company/United+Overseas+Bank+Ltd/?ticker=SGX-U11
 */
