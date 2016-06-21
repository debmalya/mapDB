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

import java.io.IOException;
import java.net.URL;

import org.apache.log4j.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 * Mainly 10K like form processor.
 * 
 * @author debmalyajash
 *
 */
public class FormProcessor {

	/**
	 * 
	 */
	private static final String TABLE_OF_CONTENTS = "Table of Contents";
	private static Logger logger = Logger.getLogger(FormProcessor.class);

	public void process(final URL url) throws IOException {
		Document doc = Jsoup.connect(url.toString()).get();
		Elements links = doc.select("a");
		for (Element eachLink : links) {
			String linkText = eachLink.text().trim();
			if (linkText.length() > 0 && !linkText.equalsIgnoreCase(TABLE_OF_CONTENTS))
			logger.debug(eachLink.html()+" "+eachLink.text());
		}
	}
}
