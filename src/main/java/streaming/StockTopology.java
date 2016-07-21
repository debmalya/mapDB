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
package streaming;

import java.util.Random;

import com.ibm.streams.flow.javaprimitives.JavaTestableGraph;
import com.ibm.streamsx.topology.TStream;
import com.ibm.streamsx.topology.Topology;
import com.ibm.streamsx.topology.context.StreamsContext;
import com.ibm.streamsx.topology.context.StreamsContextFactory;
import com.ibm.streamsx.topology.function.Supplier;

import model.StockDetails;
import scrapper.sgx.SGXScrapper;

/**
 * @author debmalyajash
 *
 */
public class StockTopology {
	/**
	 * The topology object contains information about the structure of our graph
	 * (that is, our application), including how the data is generated and
	 * processed.
	 */
	private static Topology topology = new Topology("stockSensor");

	private SGXScrapper scrapper = new SGXScrapper();

	TStream<String> symbols = topology.strings(new String[] { "BFK" });

	String[] filteredSymbols = new String[] { "C52", "E8Z", "D07", "U19", "U05", "F34", "T14", "A0P", "T8B", "F9D",
			"5FW", "K3PD", "Q1P", "BFU", "GU5", "AYL", "42Z", "AWC", "BBW", "BEC", "C2PU", "ACV", "BLS", "V03", "K3DD",
			"C33", "BKX", "E5H", "BTM", "S9B", "IX2", "5MM", "C6L", "S21", "P8A", "5SR", "P9D", "CC3", "B66", "5CP",
			"U9E", "BKY", "F17", "BDF", "C2J", "S05", "BLT", "B10", "K29", "S7OU", "S29", "40S", "K3KD", "S91", "AWG",
			"A7RU", "K22", "DM0", "C13", "BKW", "5VS", "BJW", "S35", "S6NU", "D38", "AZI", "BDX", "C70", "Q0F", "S61",
			"S63", "Q5T", "C09", "AJ2", "M11", "S23", "588", "K3SD", "A75", "O5RU", "H20", "SK6U", "T82U", "5VJ", "S56",
			"AGS", "I85", "T24", "S69", "5UX", "561", "5JS", "F25U", "SV3U", "BUOU", "B07", "BFI", "U14", "D6U", "AXL",
			"T4B", "558", "NC9", "MC0", "AWZ", "C14", "AFC", "C41", "EH5", "S7P", "N33", "T42", "D5IU", "C31", "B61",
			"A34", "591", "KJ9", "RQ1", "AVV", "F99", "AWX", "P15", "J91U", "Y35", "D4N", "5NG", "BLR", "i06", "MV4",
			"TQ5", "H07", "C61U", "S08", "U04", "AWM", "RC5", "J2T", "N01", "AWE", "D03", "543", "H18", "BTE", "U09",
			"AUE", "M03", "BEW", "F03", "LJ3", "J85", "U13", "S71", "U06", "M04", "J17", "O10", "AZA", "S58", "N32",
			"D8DU", "BRD", "BHK", "CH8", "B0Z", "M01", "P8Z", "H15", "547", "K71U", "BLZ", "5EN", "S59", "BHQ", "593",
			"BFK", "ME8U", "BLH", "H78", "TS0U", "P40U", "K75", "5HJ", "i07", "EB5", "H12", "533", "QC7", "O23", "X06",
			"A30", "5OI", "5PC", "L09", "5DM", "D1R", "BAI", "BDU", "P36", "G07", "F31", "S20", "BJZ", "G41", "Z25",
			"BEV", "C92", "C38U", "D11", "5GD", "S68", "E16", "5JK", "AYF", "NC2", "H02", "BEZ", "B7K", "B73", "566",
			"5DA", "ADQU", "BEI", "500", "BJV", "F11", "N02", "S3N", "5CN", "BR9", "G13", "BTOU", "L17", "E3B", "CY6U",
			"5AU", "RE2", "D01", "BHU", "C9Q", "BOL", "S10", "SK7", "T18", "5EC", "ADJ", "MR7", "M30", "CZ4", "A05",
			"RF7", "5OQ", "K3OD", "G20", "M1Z", "573", "T15", "P52", "J69U", "AWI", "BPF", "BSL", "544", "BDV", "570",
			"D05", "5DL", "BN2", "C50", "D5N", "5CH", "BMA", "E9L", "BQP", "AW9U", "U77", "BVA", "5MZ", "M35", "N08",
			"BTX", "564", "NR7", "P19", "Y03", "C22", "T6I", "UD2", "BDA", "L03", "B16", "43Q", "OU8", "5OC", "U96",
			"AZG", "G0I", "NS8U", "K6S", "BRS", "K11", "BHO", "M44U", "G92", "S53", "L46", "AU8U", "N03", "Y92", "5KT",
			"K2N", "ER0", "ACW", "A26", "A17U", "C04", "N0Z", "H64", "J36", "ADP", "N2IU", "C07", "c29", "42R", "T39",
			"B26", "40W", "F1E", "BQM", "P9J", "5ER", "5IC", "K1Q", "AJBU", "528", "F13", "MU7", "L38", "C05", "T41",
			"AVM", "H19", "Q01", "BIX", "S19", "B49", "BCY", "5NF", "T12", "BIP", "S49", "T43", "H13", "43A", "A68U",
			"510", "Z74", "ND8U", "5DD", "B2F", "H27", "A04", "H30", "AIY", "569", "42G", "AP4", "P01", "E28", "B28",
			"G10", "5IF", "K3GD", "ADN", "G08", "BS6", "O39", "S41", "BCZ", "K03", "UD1U", "BQF", "S85", "FO8", "T55",
			"MF6", "RW0U", "D50", "5DP", "AWV", "P11", "RF1U", "U10", "OV8", "T8JU", "B08", "5CF", "BN4", "L19", "AYN",
			"BTF", "S07", "5FL", "5AB", "J37", "R07", "W05", "5KI", "Z59", "BESU", "BKK", "U11", };
	
	Random random = new Random(filteredSymbols.length);

	/**
	 * Randomly returns any stock details according to passed symbol.
	 */
	TStream<StockDetails> readings = topology.endlessSource(new Supplier<StockDetails>() {
		/**
		 * 
		 */
		private static final long serialVersionUID = 9150672403304272372L;

		@Override
		public StockDetails get() {
			return scrapper.yahooFinance(filteredSymbols[random.nextInt()], null);
		}
	});
	
	public static void main(String[] args){
//		Blocked , Caused by: java.lang.ClassNotFoundException: com.ibm.json.java.JSONObject
		 StreamsContext<JavaTestableGraph> context = StreamsContextFactory.getEmbedded();
//		 System.out.println(context.isSupported(topology));
//		 context.submit(topology);
	}
}
