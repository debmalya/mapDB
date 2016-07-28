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

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.jakewharton.fliptables.FlipTable;

/**
 * @author debmalyajash
 * 
 */
public class TextBrowser {
	private static String mainURL;

	private static Set<String> parsedURL = new HashSet<>();

	private static final Logger LOGGER = Logger.getLogger(TextBrowser.class);

	private static PrintWriter writer = null;

	/**
	 * Get the text content of the browser.
	 * 
	 * @param args
	 *            URL to be parsed.
	 * @throws FileNotFoundException
	 */
	public static void main(String[] args)  {
		try {
			if (args.length >= 1) {
				writer = new PrintWriter(new PrintWriter(new File(
						"TextBrowser.txt")));
				for (int i = 0; i < args.length; i++) {
					mainURL = args[i];
					processEachURL(args[i]);
				}
			} else {
				System.err.println("Ussage : TextBrowser <URL>");
			}
		} catch (Throwable th) {
			LOGGER.error(th.getMessage(),th);
		} finally {
			if (writer != null) {
				writer.flush();
				writer.close();
			}
		}

	}

	public static void processEachURL(String eachURL) {
		if (eachURL == null || StringUtils.isEmpty(eachURL)) {
			return;
		}
		try {
			if (!eachURL.startsWith("http") && !eachURL.startsWith("https")) {
				eachURL = "http://" + eachURL;
			} else if (!eachURL.startsWith(mainURL) && !eachURL.contains("www")) {
				eachURL = mainURL + eachURL;
			}

			Document doc = Jsoup.connect(eachURL).get();
			String docText = doc.text();
			String[] allWords = docText.split(" ");
			List<String> lines = new ArrayList<>();
			StringBuilder eachLine = new StringBuilder();
			int length = 0;
			for (String eachWord : allWords) {
				eachLine.append(eachWord.trim());
				eachLine.append(" ");
				length += eachWord.length() + 1;
				if (length > 80) {
					eachLine.append(System.getProperty("line.separator"));
					length = 0;
				}
			}
			lines.add(eachLine.toString());

			System.out.println(FlipTable.of(new String[] { eachURL },
					new String[][] { lines.toArray(new String[0]) }));

			Elements elts = doc.select("a");
			for (Element each : elts) {
				try {
					String url = each.attr("href");
					if (!url.startsWith(mainURL) && !url.contains(mainURL)) {
						url = mainURL + url;
					}

					if (parsedURL.add(url)) {
						processEachURL(url);
					}
				} catch (Throwable ignore) {
					LOGGER.error(ignore.getMessage(), ignore);
				}
			}

		} catch (IOException ignore) {
			LOGGER.error(ignore.getMessage(), ignore);
			// System.err.println(ignore.getMessage());
		}
		return;
	}

}
