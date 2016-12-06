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


	@Override
	public void initialize(UimaContext aContext)
			throws ResourceInitializationException {
		super.initialize(aContext);
		
		String[] lexical = {
				"JJ", "JJR", "JJS", //adj
				"RB", "RBR", "RBS", "WRB", //adv
				"VB", "VBD", "VBG", "VBN", "VBP", "VBZ", //verb
				"NN", "NNS", "NNP", "NNPS" //noun
		};
		Collections.addAll(posList, lexical);

	}

	/* (non-Javadoc)
	 * @see org.apache.uima.analysis_component.JCasAnnotator_ImplBase#process(org.apache.uima.jcas.JCas)
	 */
	@Override
	public void process(JCas aJCas) throws AnalysisEngineProcessException {

		// get annotation indexes and iterator
		Iterator posIter = aJCas.getAnnotationIndex(POS.type).iterator();

		//count number of occurrences 
		int nPOSTypes = 0;
		int nTokens = 0;
		while(posIter.hasNext()) {
			nTokens++;
			POS pos = (POS) posIter.next();
			String tag = pos.getTag();
			if(posList.contains(tag)) {
				nPOSTypes++;
			}
		}

		double density = 0;
		if(nTokens != 0 ) {
			density = (double) nPOSTypes / nTokens;
		}
		
		//output the feature type
		LD annotation = new LD(aJCas);

		annotation.setBegin(0);
		annotation.setEnd(aJCas.getDocumentText().length() - 1);
		//set feature value
		annotation.setValue(density);
		annotation.addToIndexes();

	}
}
