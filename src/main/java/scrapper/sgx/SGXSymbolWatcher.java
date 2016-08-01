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

import model.StockDetails;

/**
 * @author debmalyajash
 *
 */
public class SGXSymbolWatcher {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		if (args.length > 0) {
			SGXScrapper scrapper = new SGXScrapper();
			StockDetails details = null;
			StockDetails previous = null;
			while (true) {
				if (args.length == 1) {
					details = scrapper.yahooFinance(args[0], "");
				} else {
					details = scrapper.yahooFinance(args[0], args[1]);
				}
				if (!details.equals(previous)) {
					previous = details;
					if (args.length != 3) {
						System.out.println(details);
					} else {
						float differnceWithTarget = Float.parseFloat(args[2])
								- Float.parseFloat(details.getCurrentPrice());

						System.out.println(details.getCurrentPrice() + " , "
								+ details.getVolume() + " , "
								+ differnceWithTarget + " , "
								+ details.getCurrentPriceRecordTime());
					}
				}
			}
		} else {
			System.err
					.println("Usage: SGXSymbolWatcher <Symbol> (e.g. SGXSymbolWatcher 5DA)");
		}

	}

}
