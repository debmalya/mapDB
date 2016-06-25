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
package pdfconverter;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.allcolor.yahp.converter.CYaHPConverter;
import org.allcolor.yahp.converter.IHtmlToPdfTransformer;
import org.allcolor.yahp.converter.IHtmlToPdfTransformer.CConvertException;

/**
 * @author debmalyajash
 *
 */
public class YahpConverter {

	/**
	 * @param args
	 * @throws CConvertException 
	 * @throws IOException 
	 */
	public static void main(String[] args) throws CConvertException, IOException {
		// new converter
		CYaHPConverter converter = new CYaHPConverter();
		// save pdf in outfile
		File fout = new File("Google_Yahp.pdf");
		FileOutputStream out = new FileOutputStream(fout);
		// contains configuration properties
		Map properties = new HashMap();
		// list containing header/footer
		List			 headerFooterList = new ArrayList();
		// add header/footer
		headerFooterList.add(new IHtmlToPdfTransformer.CHeaderFooter(
				"<table width=\"100%\"><tbody><tr><td align=\"left\">"+
				"Generated with YaHPConverter.</td><td align=\"right\">Page <pagenumber>/<"+
				"pagecount></td></tr></tbody></table>",
				IHtmlToPdfTransformer.CHeaderFooter.HEADER));
		headerFooterList.add(new IHtmlToPdfTransformer.CHeaderFooter(
				"Â© 2009 Quentin Anciaux",
				IHtmlToPdfTransformer.CHeaderFooter.FOOTER));
		properties.put(IHtmlToPdfTransformer.PDF_RENDERER_CLASS,
				IHtmlToPdfTransformer.FLYINGSAUCER_PDF_RENDERER);
//		properties.put(IHtmlToPdfTransformer.FOP_TTF_FONT_PATH, fontPath);
		converter.convertToPdf(new URL("https://www.sec.gov/Archives/edgar/data/1288776/000165204416000012/goog10-k2015.htm"),
			IHtmlToPdfTransformer.A4P, headerFooterList, out,
			properties);
		out.flush();
		out.close();

	}

}
