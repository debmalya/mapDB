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

import org.apache.commons.lang.builder.ToStringBuilder;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;

/**
 * @author debmalyajash
 *
 */
public class BSEScrapper {
	
	/**
	 * URL to retrieve BSE stock index value.
	 */
	public static final String URL = "https://www.google.com/finance?q=INDEXBOM%3ASENSEX&ei=x_t8V-nFFZbZuATb2oCwCw";


	private String sensexValue;
	
	private String change;

	public void parse(String url) throws Exception {
		Element doc = Jsoup.connect(url).get();
		setSensexValue(doc.select("#ref_15173681_l").text());
		setChange(doc.select("#ref_15173681_c").text());

	}

	/**
	 * @return the sensexValue
	 */
	public String getSensexValue() {
		return sensexValue;
	}

	/**
	 * @param sensexValue
	 *            the sensexValue to set
	 */
	private void setSensexValue(String sensexValue) {
		this.sensexValue = sensexValue;
	}

	/**
	 * @return the change
	 */
	public String getChange() {
		return change;
	}

	
	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}

	/**
	 * @param change the change to set
	 */
	private void setChange(String change) {
		this.change = change;
	}
	
	public static void main(String... args) throws Exception {
		BSEScrapper scrapper = new BSEScrapper();
		scrapper.parse(URL);
		System.out.println(scrapper);
	}

}
