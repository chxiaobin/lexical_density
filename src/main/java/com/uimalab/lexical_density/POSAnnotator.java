package com.uimalab.lexical_density;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

import org.apache.uima.UimaContext;
import org.apache.uima.analysis_component.JCasAnnotator_ImplBase;
import org.apache.uima.analysis_engine.AnalysisEngineProcessException;
import org.apache.uima.jcas.JCas;
import org.apache.uima.resource.ResourceAccessException;
import org.apache.uima.resource.ResourceInitializationException;

import com.uimalab.type.POS;
import com.uimalab.type.Sentence;
import com.uimalab.type.Token;

import opennlp.tools.postag.POSModel;
import opennlp.tools.postag.POSTaggerME;
import opennlp.tools.tokenize.TokenizerME;
import opennlp.tools.tokenize.TokenizerModel;
import opennlp.tools.util.InvalidFormatException;
import opennlp.tools.util.Span;

public class POSAnnotator extends JCasAnnotator_ImplBase {

	//for pos tagger
	private InputStream posModelIn;
	private POSModel POSmodel;
	private POSTaggerME posTagger;
	public static String POS_RESOURCE_KEY = "POSModel";

	@Override
	public void initialize(UimaContext aContext) throws ResourceInitializationException {
		super.initialize(aContext);

		//init pos tagger
			try {
				posModelIn = aContext.getResourceAsStream(POS_RESOURCE_KEY);
				POSmodel = new POSModel(posModelIn);
				posTagger = new POSTaggerME(POSmodel);
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
		//iterate through all sentences
		Iterator sentIter = aJCas.getAnnotationIndex(Sentence.type).iterator();
		while (sentIter.hasNext()) {
			Sentence sent = (Sentence) sentIter.next();
			int sentStart = sent.getBegin();
			int sentEnd = sent.getEnd();
			List<Token> sentTokens = new ArrayList<>();
			
			//iterate through all tokens
			Iterator tokenIter = aJCas.getAnnotationIndex(Token.type).iterator(false);
			while(tokenIter.hasNext()) {
				Token token = (Token) tokenIter.next();

				if(token.getBegin() >= sentStart && token.getEnd() <= sentEnd) {
					sentTokens.add(token);
				}
			}
			
			//convert the list of tokens in the sentence to a String array
			//alternatively, use:
			//	String[] tokenStrings = sentTokens.toArray(new String[0]);
			int size = sentTokens.size();
			String[] tokenStrings = new String[size];
			for(int i = 0; i < size; i++) {
				tokenStrings[i] = sentTokens.get(i).getCoveredText();
			}

			//get POS tags
			String tags[] = posTagger.tag(tokenStrings);

			//populate the CAS
			for(int i = 0; i < size; i++) {
				Token token = sentTokens.get(i);
				POS annotation = new POS(aJCas);
				annotation.setBegin(token.getBegin()); 
				annotation.setEnd(token.getEnd());
				annotation.setTag(tags[i]);
				annotation.addToIndexes();
			}
		}

	}

	@Override
	public void destroy() {

		if (posModelIn != null) {
			try {
				posModelIn.close();
			}
			catch (IOException e) {
				e.printStackTrace();
			}
		}
		super.destroy();
	}
}
