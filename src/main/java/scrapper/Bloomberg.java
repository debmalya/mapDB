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

import org.apache.log4j.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import model.StockDetails;

/**
 * @author debmalyajash
 *
 */
public class Bloomberg implements Runnable {

	private static final Logger LOGGER = Logger.getLogger(Bloomberg.class);

	private static String URL = "http://www.bloomberg.com/quote/";

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		parse("5TP", "SP");
		// parse("504","SP");
		// parse("OV8","SP");
	}

	public static StockDetails parse(final String symbol, final String suffix) {
		StockDetails details = new StockDetails();
		StringBuilder sb = new StringBuilder(URL);
		sb.append(symbol);
		sb.append(":");
		sb.append(suffix);

		try {
			Document doc = Jsoup.connect(sb.toString()).get();
			String[] prices = doc.getElementsByClass("change-container").text().split(" ");
			details.setSymbol(symbol);
			details.setChange(Float.parseFloat(prices[1].replace("%", "")));
			details.setCurrentPriceRecordTime(doc.getElementsByClass("price-datetime").text());
			details.setSource("bloomberg");

			Elements elements = doc.getAllElements();
			boolean start = false;
			int count = 0;
			String previous = "";
			for (Element each : elements) {
				String current = each.text().trim();
				if (start) {

					if (count == 7) {
						String[] values = each.text().split(" ");
						details.setCurrentPrice(values[0]);
					}
					switch (previous) {
					case "+ Watchlist":
						details.setExchange(suffix);
						details.setStockName(each.text());
						break;
					case "Open":
						details.setOpen(Float.parseFloat(current));
						break;
					case "Day Range":
						details.setDaysRange(current);
						break;
					case "Previous Close":
						details.setLastPrice(Float.parseFloat(current));
						details.setPrevClose(current);
						break;
					case "52Wk Range":
						details.set52wkRange(current);
						break;
					case "1 Yr Return":
						details.set1YrReturn(current);
						break;
					case "YTD Return":
						details.setYtdReturnMkt(current);
						break;
					case "Current P/E Ratio (TTM)":
						details.setPERatio(current);
						break;
					case "Earnings per Share (USD) (TTM)":
						details.setEPS(current);
						break;
					case "Market Cap":
						details.setMarketCapital(current);
						break;
					case "Shares Outstanding (m)":
						details.setVolume(current);
						break;
					case "Price/Sales (TTM)":

						break;
					case "Dividend Indicated Gross Yield":
						details.setDivNYield(current);
						break;
					case "Sector":
						details.setSector(current);
						break;
					case "Industry":
						details.setIndustry(current);
						break;
					case "Sub-Industry":
						details.setSubIndustry(current);
						start = false;
						break;
					default:
						break;
					}
					count++;
				}
				if (start) {
//					System.out.println(count + " " + each.text());
					previous = current;
				}

				if (each.text().trim().equals("+ Watchlist")) {
					start = true;
					previous = current;
				}

			}
//			System.out.println(details);
		} catch (IOException e) {
			LOGGER.error(sb.toString() + " " + e.getMessage(), e);
		}
		return details;
	}

	/* (non-Javadoc)
	 * @see java.lang.Runnable#run()
	 */
	@Override
	public void run() {
		// TODO Auto-generated method stub
		
	}

}
