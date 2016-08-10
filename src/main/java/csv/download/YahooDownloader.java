/**
 * 
 */
package csv.download;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.log4j.Logger;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import au.com.bytecode.opencsv.CSVWriter;

/**
 * @author debmalyajash
 *
 */
public class YahooDownloader implements Downloader {

	private static final Logger LOGGER = Logger
			.getLogger(YahooDownloader.class);

	private final String URL = "https://sg.finance.yahoo.com/q/hp?s=";
	private final String CSV_URL = "http://real-chart.finance.yahoo.com/table.csv?s=5TP.SI&a=9&b=28&c=2011&d=7&e=9&f=2016&g=d&ignore=.csv";

	/*
	 * (non-Javadoc)
	 * 
	 * @see csv.download.Downloader#download(java.lang.String)
	 */
	@Override
	public List<String[]> download(String symbol, String suffix) {
		Connection connection = null;
		String downloadURL = "http://real-chart.finance.yahoo.com/table.csv?s="
				+ symbol+"." + suffix
				+ "&a=9&b=28&c=2011&d=7&e=9&f=2016&g=d&ignore=.csv";
		connection = Jsoup.connect(downloadURL);
		Document doc = null;
		List<String[]> allRows = new ArrayList<>();

		createDirectoryIfDoesNotExist("data");
		
		try (CSVWriter writer = new CSVWriter(new PrintWriter("./data/"
				+ symbol + "_" + suffix + ".csv"))) {
			doc = connection.get();
			Element body = doc.body();
			String bodyText = body.text();

			String[] eachRow = new String[] { "Date", "Open", "High", "Low",
					"Close", "Volume", "Adj Close", "Daily Diff",
					"Open - Close" };
			allRows.add(eachRow);
			String[] rows = bodyText.split(" ");
			int rowCount = 0;
			for (String each : rows) {

				rowCount++;
				if (rowCount > 2) {
					eachRow = each.split(",");
					String[] modifiedRow = new String[eachRow.length + 2];
					System.arraycopy(eachRow, 0, modifiedRow, 0, 7);
					modifiedRow[7] = String.valueOf(Float
							.parseFloat(eachRow[2])
							- Float.parseFloat(eachRow[3]));
					modifiedRow[8] = String.valueOf(Float
							.parseFloat(eachRow[1])
							- Float.parseFloat(eachRow[4]));
					allRows.add(modifiedRow);
				}
			}
			writer.writeAll(allRows);
			LOGGER.debug("Downloaded for "+symbol);

		} catch (IOException e) {
			LOGGER.error(downloadURL + " " + e.getMessage(), e);
		} finally {
			
		}

		return allRows;
	}

	/**
	 * @param string
	 */
	private void createDirectoryIfDoesNotExist(String direcotry) {
		File file = new File(direcotry);
		if (!file.exists()){
			file.mkdirs();
		}
		
	}

	public List<String[]> download0(String symbol) {
		Connection connection = null;
		String downloadURL = URL + symbol + ".SI";
		connection = Jsoup.connect(downloadURL);
		Document doc = null;
		List<String[]> allRows = new ArrayList<>();
		try {
			doc = connection.get();

			Elements table = doc.getElementsByClass("yfnc_datamodoutline1");
			Elements rows = table.select("tr");
			Elements ths = rows.select("th");
			String[] eachRow = new String[9];
			for (int i = 0; i < ths.size(); i++) {
				eachRow[i] = ths.get(i).text();
			}
			eachRow[7] = "Daily Diff";
			eachRow[8] = "Open - Close";

			allRows.add(eachRow);

			for (int j = 2; j < rows.size(); j++) {
				Elements tds = rows.get(j).select("td");
				eachRow = new String[tds.size() + 2];
				for (int i = 0; i < tds.size(); i++) {
					eachRow[i] = tds.get(i).text();
				}
				eachRow[eachRow.length - 2] = String.valueOf(Float
						.parseFloat(eachRow[2]) - Float.parseFloat(eachRow[3]));
				eachRow[eachRow.length - 1] = String.valueOf(Float
						.parseFloat(eachRow[1]) - Float.parseFloat(eachRow[4]));
				allRows.add(eachRow);
			}
		} catch (IOException e) {
			LOGGER.error(downloadURL + " " + e.getMessage(), e);
		} finally {
			connection = null;
			doc = null;
		}

		return allRows;
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {

	}

}
