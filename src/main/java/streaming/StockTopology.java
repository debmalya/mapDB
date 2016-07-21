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

import com.ibm.streamsx.topology.TStream;
import com.ibm.streamsx.topology.Topology;
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
	Topology topology = new Topology("stockSensor");
	
	SGXScrapper scrapper = new SGXScrapper();
	
	TStream<StockDetails> readings = topology.endlessSource(new Supplier<StockDetails>(){
	    /**
		 * 
		 */
		private static final long serialVersionUID = 9150672403304272372L;

		@Override
	    public StockDetails get() {
	        return scrapper.yahooFinance("5DA", "BreadTalk");
	    }
	});
}
