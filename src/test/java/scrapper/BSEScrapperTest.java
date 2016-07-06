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

import org.junit.Assert;
import org.junit.Test;


/**
 * @author debmalyajash
 *
 */
public class BSEScrapperTest {

	
	/**
	 * Test method for {@link scrapper.BSEScrapper#parse(java.lang.String)}.
	 */
	@Test
	public void testParse() {
		BSEScrapper bseScrapper = new BSEScrapper();
		try {
			bseScrapper.parse(BSEScrapper.URL);
			Assert.assertNotNull(bseScrapper.getSensexValue());
			System.out.println(bseScrapper.getSensexValue());
			System.out.println(bseScrapper.getChange());
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			Assert.assertFalse(e.getMessage(),true);
		}
	}

}
