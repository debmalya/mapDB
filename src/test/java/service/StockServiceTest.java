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
package service;

import java.util.stream.Stream;

import org.junit.Assert;
import org.junit.Test;

import model.StockSymbol;

/**
 * @author debmalyajash
 *
 */
public class StockServiceTest {

	/**
	 * Test method for {@link service.StockService#select(java.lang.String)}.
	 */
	@Test
	public void testSelect() {
		StockService service = new StockService();
		Stream<StockSymbol> selectedValues = service.select("BSE Sensex");
		Assert.assertNotNull(selectedValues);
//		Assert.assertTrue(selectedValues.count() > 0);
		selectedValues.forEach(each-> System.out.println(each));
	}

}
