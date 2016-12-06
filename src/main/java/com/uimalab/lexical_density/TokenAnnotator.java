/**
 * 
 */
package com.uimalab.lexical_density;

import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;

import org.apache.uima.UimaContext;
import org.apache.uima.analysis_component.JCasAnnotator_ImplBase;
import org.apache.uima.analysis_engine.AnalysisEngineProcessException;
import org.apache.uima.jcas.JCas;
import org.apache.uima.resource.ResourceAccessException;
import org.apache.uima.resource.ResourceInitializationException;

import com.uimalab.type.Sentence;
import com.uimalab.type.Token;

import opennlp.tools.tokenize.Tokenizer;
import opennlp.tools.tokenize.TokenizerME;
import opennlp.tools.tokenize.TokenizerModel;
import opennlp.tools.util.InvalidFormatException;
import opennlp.tools.util.Span;

/**
 * @author xiaobin The AE takes a Sentence as input and tokenize the sentence.
 */
public class TokenAnnotator extends JCasAnnotator_ImplBase {

	private InputStream modelIn;
	private TokenizerModel model;
	private Tokenizer tokenizer;

	public static String RESOURCE_KEY = "TokenModel";

	@Override
	public void initialize(UimaContext aContext) throws ResourceInitializationException {
		super.initialize(aContext);

			try {
				modelIn = aContext.getResourceAsStream(RESOURCE_KEY);
				model = new TokenizerModel(modelIn);
				tokenizer = new TokenizerME(model);
			} catch (ResourceAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (InvalidFormatException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.apache.uima.analysis_component.JCasAnnotator_ImplBase#process(org.
	 * apache.uima.jcas.JCas)
	 */
	@Override
	public void process(JCas aJCas) throws AnalysisEngineProcessException {

		// get annotation indexes and iterator
		Iterator sentIter = aJCas.getAnnotationIndex(Sentence.type).iterator();

		while (sentIter.hasNext()) {
			Sentence sent = (Sentence) sentIter.next();

			// Detect token spans
			Span[] spans = tokenizer.tokenizePos(sent.getCoveredText());

			for (Span span : spans) {
				Token annotation = new Token(aJCas);
				annotation.setBegin(span.getStart() + sent.getBegin()); // the offset is absolute, so adds the sentence begin position.
				annotation.setEnd(span.getEnd() + sent.getBegin());
				annotation.addToIndexes();
			}
		}

	}

	@Override
	public void destroy() {

		if (modelIn != null) {
			try {
				modelIn.close();
			}
			catch (IOException e) {
				e.printStackTrace();
			}
		}
		super.destroy();
	}

}
