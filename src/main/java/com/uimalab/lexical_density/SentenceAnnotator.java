/**
 * 
 */
package com.uimalab.lexical_density;

import java.io.IOException;
import java.io.InputStream;

import org.apache.uima.UIMAException;
import org.apache.uima.UimaContext;
import org.apache.uima.analysis_component.JCasAnnotator_ImplBase;
import org.apache.uima.analysis_engine.AnalysisEngineProcessException;
import org.apache.uima.jcas.JCas;
import org.apache.uima.resource.ResourceAccessException;
import org.apache.uima.resource.ResourceInitializationException;

import com.uimalab.type.Sentence;

import opennlp.tools.sentdetect.SentenceDetectorME;
import opennlp.tools.sentdetect.SentenceModel;
import opennlp.tools.util.InvalidFormatException;
import opennlp.tools.util.Span;

/**
 * Annotates the sentences in a text.
 * 
 * @author xiaobin
 */
public class SentenceAnnotator extends JCasAnnotator_ImplBase {

	private InputStream modelIn;
	private SentenceDetectorME sentenceDetector;

	private static final String RESOURCE_KEY = "SentenceSegmenterModel";

	/**
	 * Loads the English sentence detector model.
	 */
	@Override
	public void initialize(UimaContext aContext) throws ResourceInitializationException {
		super.initialize(aContext);

		// gets the model resource, which is declared in the annotator xml
			try {
				modelIn = aContext.getResourceAsStream(RESOURCE_KEY);
				sentenceDetector = new SentenceDetectorME(new SentenceModel(modelIn));
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
		// Get document text
		String docText = aJCas.getDocumentText();

		// Detect sentence spans
		Span[] spans = sentenceDetector.sentPosDetect(docText);

		// Store the detected sentence spans in the CAS,
		// Don't forget to add the annotations to the CAS index
		for (Span span : spans) {
			Sentence annotation = new Sentence(aJCas);
			annotation.setBegin(span.getStart());
			annotation.setEnd(span.getEnd());
			annotation.addToIndexes();
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
