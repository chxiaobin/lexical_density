/**
 * 
 */
package com.uimalab.lexical_density;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import org.apache.uima.UimaContext;
import org.apache.uima.analysis_component.JCasAnnotator_ImplBase;
import org.apache.uima.analysis_engine.AnalysisEngineProcessException;
import org.apache.uima.cas.FSIndex;
import org.apache.uima.jcas.JCas;
import org.apache.uima.resource.ResourceInitializationException;

import com.uimalab.type.LD;
import com.uimalab.type.POS;

public class LexicalDensityAE extends JCasAnnotator_ImplBase {

	//list of pos tags to be counted
	List<String> posList = new ArrayList<>();
	private static final String POSTYPES_PARAM_NAME = "POSTypes";

	@Override
	public void initialize(UimaContext aContext)
			throws ResourceInitializationException {
		super.initialize(aContext);

		//get the pos type list and add them to a list
		String[] lexical = (String[]) aContext.getConfigParameterValue(POSTYPES_PARAM_NAME);
		Collections.addAll(posList, lexical);

	}

	/* (non-Javadoc)
	 * @see org.apache.uima.analysis_component.JCasAnnotator_ImplBase#process(org.apache.uima.jcas.JCas)
	 */
	@Override
	public void process(JCas aJCas) throws AnalysisEngineProcessException {

		// get annotation indexes and iterator
		Iterator posIter = aJCas.getAnnotationIndex(POS.type).iterator();

		//for counting number of occurrences 
		int nLexicalTokens = 0; //number of lexical tokens
		int nTokens = 0; //number of tokens
		
		//interate through all POS annotations
		while(posIter.hasNext()) {
			nTokens++;
			
			//check the POS tag for each token, see if it exists 
			//in the lexical POS tags list
			POS pos = (POS) posIter.next();
			String tag = pos.getTag();
			if(posList.contains(tag)) {
				nLexicalTokens++; //if the token is a lexical token, increase the count
			}
		}

		//calculate the lexical density value
		double density = 0;
		if(nTokens != 0 ) {
			density = (double) nLexicalTokens / nTokens;
		}
		
		//store the lexical density value in to the CAS
		LD annotation = new LD(aJCas);

		annotation.setBegin(0);
		annotation.setEnd(aJCas.getDocumentText().length() - 1);
		//set LD value
		annotation.setValue(density);
		annotation.addToIndexes();

	}
}
