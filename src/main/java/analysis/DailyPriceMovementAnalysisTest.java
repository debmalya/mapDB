/**
 * 
 */
package analysis;

import static org.junit.Assert.*;

import org.junit.Test;

/**
 * @author debmalyajash
 *
 */
public class DailyPriceMovementAnalysisTest {

	/**
	 * Test method for {@link analysis.DailyPriceMovementAnalysis#analyze(java.lang.String)}.
	 */
	@Test
	public final void testAnalyze() {
		DailyPriceMovementAnalysis ana = new DailyPriceMovementAnalysis();
		ana.analyze("./src/test/resource/5TP.csv");
	}

}
