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
import java.util.ArrayList;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import com.jakewharton.fliptables.FlipTable;

/**
 * @author debmalyajash
 * 
 */
public class TextBrower {

	/**
	 * Get the text content of the browser.
	 * @param args URL to be parsed.
	 */
	public static void main(String[] args) {
		if (args.length >= 1){
			for (String eachURL: args) {
				 try {
					 if (!eachURL.startsWith("http") && !eachURL.startsWith("https")){
						 eachURL = "http://" + eachURL;
					 }
					Document doc = Jsoup.connect(eachURL).get();
					String docText = doc.text();
					String[] allWords = docText.split(" ");
					List<String> lines = new ArrayList<>();
					StringBuilder eachLine = new StringBuilder();
					int length = 0;
					for (String eachWord:allWords) {
						eachLine.append(eachWord.trim());
						eachLine.append(" ");
						length += eachWord.length() + 1;
						if (length > 80) {
							eachLine.append(System.getProperty("line.separator"));
							length = 0;
						}
					}
					lines.add(eachLine.toString());
					
					System.out
					.println(FlipTable
							.of(new String[] { eachURL },
									new String[][] {lines.toArray(new String[0])}));
					
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			} 
		}else {
			System.err.println("Ussage : TextBrowser <URL>");
		}

	}

}
