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
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;

/**
 * @author debmalyajash
 *
 */
public class ThoughtOfTheDay {

	private static String URL = "http://www.radiosai.org/pages/thought.asp";

	private static String TEXT_URL = "http://www.radiosai.org/pages/ThoughtText.asp?mydate=";

	/**
	 * @param args
	 * @throws IOException
	 */
	public static void main(String[] args) throws IOException {

		LocalDate dateNow = LocalDate.now();
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		PrintWriter thoughtOfTheDay = null;
	

		try {
			thoughtOfTheDay = new PrintWriter(new File("ThoughtOfTheDay.txt"));
			while (true) {
				
				dateNow = dateNow.minusDays(1);
				String quote = getQuote(TEXT_URL + formatter.format(dateNow));
				if ("".equals(quote)) {
					break;
				}
				thoughtOfTheDay.println(getQuote(TEXT_URL + formatter.format(dateNow)));
				thoughtOfTheDay.println("------------------------------------------------");
			}
			thoughtOfTheDay.println(getQuote(URL));
			thoughtOfTheDay.println("------------------------------------------------");

		} finally {
			System.out.println("Till :" + formatter.format(dateNow));
			if (thoughtOfTheDay != null) {
				thoughtOfTheDay.flush();
				thoughtOfTheDay.close();
			}

		}

	}

	private static String getQuote(String url) throws IOException {
		Element doc = Jsoup.connect(url).get();
		Element content = doc.getElementById("content");
		if (content == null) {
			content = doc.getElementById("Content");
		}

		if (content != null)
			return content.text();
		return "";
	}

}
