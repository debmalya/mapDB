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
import java.io.PrintWriter;
import java.util.List;
import java.util.Properties;

import edu.stanford.nlp.ling.CoreAnnotations;
import edu.stanford.nlp.pipeline.Annotation;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;
import edu.stanford.nlp.trees.Tree;
import edu.stanford.nlp.trees.TreeCoreAnnotations.TreeAnnotation;
import edu.stanford.nlp.util.CoreMap;

/**
 * @author debmalyajash
 *
 */
public class AnnotationProcessor {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			annotate("This is a short sentence", "sample.xml");
		} catch (IOException e) {
			System.err.println(e.getMessage());
		}
	}

	public static void annotate(final String textInput, final String outputFileName) throws IOException {
		PrintWriter xmlOut = new PrintWriter(outputFileName);
		Properties props = new Properties();
		// props.setProperty("annotators", "tokenize, ssplit, pos, lemma, ner,
		// parse, cleanxml");
		props.setProperty("annotators", "tokenize, ssplit, pos, lemma, ner, parse");
		StanfordCoreNLP pipeline = new StanfordCoreNLP(props);
		Annotation annotation = new Annotation(textInput);
		pipeline.annotate(annotation);
		pipeline.xmlPrint(annotation, xmlOut);
		// An Annotation is a Map and you can get and use the
		// various analyses individually. For instance, this
		// gets the parse tree of the 1st sentence in the text.
		List<CoreMap> sentences = annotation.get(CoreAnnotations.SentencesAnnotation.class);
		if (sentences != null && sentences.size() > 0) {
			CoreMap sentence = sentences.get(0);
			Tree tree = sentence.get(TreeAnnotation.class);
			PrintWriter out = new PrintWriter(System.out);
			out.println("The first sentence parsed is:");
			if (tree != null) {
				tree.pennPrint(out);
			}
		}
	}

}
