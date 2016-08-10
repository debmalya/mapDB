/**
 * 
 */
package analysis;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

import org.apache.log4j.Logger;

import au.com.bytecode.opencsv.CSVReader;

/**
 * @author debmalyajash
 *
 */
public class DailyPriceMovementAnalysis implements Analyst {

	private static final Logger LOGGER = Logger
			.getLogger(DailyPriceMovementAnalysis.class);

	/*
	 * (non-Javadoc)
	 * 
	 * @see analysis.Analyst#analyze(java.lang.String)
	 */
	@Override
	public void analyze(final String csvFileName) {
		try (CSVReader reader = new CSVReader(new BufferedReader(
				new FileReader(csvFileName)))) {
			List<String[]> records = reader.readAll();
			for (String[] eachRecord:records){
				
				
			}

		} catch (FileNotFoundException e) {
			LOGGER.error(csvFileName + " " + e.getMessage(), e);
		} catch (IOException e) {
			LOGGER.error(csvFileName + " " + e.getMessage(), e);

		}

	}

}
