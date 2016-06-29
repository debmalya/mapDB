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

import model.SecDocument;

/**
 * Mainly 10K like form processor.
 * 
 * @author debmalyajash
 *
 */
public class FormProcessor {
	
	private String parsedString;

	/**
	 * 
	 */
	private static final String TABLE_OF_CONTENTS = "Table of Contents";
	private static final Logger LOGGER = Logger.getLogger(FormProcessor.class);

	public void process(final URL url) throws IOException {
		Document doc = Jsoup.connect(url.toString()).get();
		SecDocument secDocument = new SecDocument();
		setHeader(doc, secDocument);
		setPart(doc, secDocument);
	}

	/**
	 * 
	 * @param url
	 * @return
	 * @throws IOException
	 */
	public String processHTML(final URL url) throws IOException {
		return Jsoup.connect(url.toString()).get().text();
	}
	/**
	 * @param doc
	 * @param secDocument
	 */
	private void setPart(Document doc, SecDocument secDocument) {
		// TODO Auto-generated method stub

	}

	/**
	 * @param doc
	 * @return
	 */
	private void setHeader(Document doc, SecDocument secDocument) {

		secDocument.setDescription(doc.select("title").text());

		Elements descriptionElements = doc.select("div");
		StringBuilder sb = new StringBuilder();
		
		int count = 1;
		for (Element eachDescriptionElement : descriptionElements) {
			LOGGER.debug(count + " " + eachDescriptionElement.text());
			sb.append(eachDescriptionElement.text());
			count++;
		}
		setParsedString(sb.toString());
	}

	/**
	 * Process all the links of the document.
	 * 
	 * @param doc
	 * @throws IOException
	 */
	private void processLinks(final Document doc) throws IOException {
		Elements links = doc.select("a");
		for (Element eachLink : links) {
			String linkText = eachLink.text().trim();
			if (linkText.length() > 0 && !linkText.equalsIgnoreCase(TABLE_OF_CONTENTS))
				LOGGER.debug(eachLink.text());
		}
	}

	/**
	 * Processes all the font from the document.
	 * 
	 * @param doc
	 *            document to be processed.
	 */
	public void processFonts(Document doc) {
		Elements fontList = doc.select("font");
		for (Element eachELement : fontList) {
			String txt = eachELement.text();
			if (txt.contains("ITEM")) {
				LOGGER.debug(eachELement.text());
			}
		}
	}

	/**
	 * @return the parsedString
	 */
	public String getParsedString() {
		return parsedString;
	}

	/**
	 * @param parsedString the parsedString to set
	 */
	private void setParsedString(String parsedString) {
		this.parsedString = parsedString;
	}
}
