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
import org.junit.Assert;
import org.junit.Test;

import util.CommonTestUtil;




/**
 * @author debmalyajash
 *
 */
public class FormProcessorTest {
	
	private static Logger LOGGER = Logger.getLogger(FormProcessorTest.class);

	/**
	 * Test method for {@link scrapper.FormProcessor#process(java.lang.String)}.
	 */
	@Test
	public void testProcess() {
		FormProcessor processor = new FormProcessor();
		try {
			processor.process(new URL(CommonTestUtil.GOOGLE_10K_URL));
			LOGGER.debug("-------------------------------------------------------------------------------------------------");
			processor.process(new URL("https://www.sec.gov/Archives/edgar/data/789019/000119312515272806/d918813d10k.htm"));
		} catch (IOException e) {
			LOGGER.error(e.getMessage(),e);
			Assert.assertFalse(e.getMessage(),true);
		}
	}

}
