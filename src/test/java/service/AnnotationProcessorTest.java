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

import java.io.IOException;
import java.net.URL;

import org.junit.Assert;
import org.junit.Test;

import scrapper.FormProcessor;
import util.CommonTestUtil;

/**
 * @author debmalyajash
 *
 */
public class AnnotationProcessorTest {

	/**
	 * Test method for {@link service.AnnotationProcessor#annotate(java.lang.String, java.lang.String)}.
	 */
	@Test
	public void testAnnotate() {
		FormProcessor process = new FormProcessor();
		try {
			process.process(new URL(CommonTestUtil.GOOGLE_10K_URL));
//			AnnotationProcessor.annotate(process.getParsedString(), "parsed.xml");
		} catch (IOException e) {
			e.printStackTrace();
			Assert.assertFalse(e.getMessage(),true);
		}
	}

}
