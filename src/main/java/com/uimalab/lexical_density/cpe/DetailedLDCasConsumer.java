/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 * 
 *   http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

package com.uimalab.lexical_density.cpe;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import org.apache.uima.cas.CAS;
import org.apache.uima.cas.CASException;
import org.apache.uima.cas.FSIterator;
import org.apache.uima.cas.impl.XmiCasSerializer;
import org.apache.uima.collection.CasConsumer_ImplBase;
import org.apache.uima.examples.SourceDocumentInformation;
import org.apache.uima.jcas.JCas;
import org.apache.uima.resource.ResourceInitializationException;
import org.apache.uima.resource.ResourceProcessException;
import org.apache.uima.util.FileUtils;
import org.apache.uima.util.XMLSerializer;
import org.xml.sax.SAXException;

import com.uimalab.type.DocumentMetaData;
import com.uimalab.type.LD;
import com.uimalab.type.POS;

/**
 * A simple CAS consumer that writes the analysis results to a csv file.
 * <p>
 * This CAS Consumer takes one parameter:
 * <ul>
 * <li><code>OutputDirectory</code> - path to directory into which output files will be written</li>
 * </ul>
 */
public class DetailedLDCasConsumer extends CasConsumer_ImplBase {
	/**
	 * Name of configuration parameter that must be set to the path of a directory into which the
	 * output files will be written.
	 */
	public static final String PARAM_OUTPUTDIR = "OutputDirectory";

	FileWriter fout;
	private final String outputFileName = "detail_lexical_info.csv";

	// the string to be written to output file
	String resultStr; 

	//POS tag lists
	List<String> adjTagList = new ArrayList<>();
	List<String> advTagList = new ArrayList<>();
	List<String> verbTagList = new ArrayList<>();
	List<String> nounTagList = new ArrayList<>();

	/**
	 * Initializes the output file in the output directory.
	 */
	public void initialize() throws ResourceInitializationException {
		File mOutputDir;
		File mOutputFile;
		resultStr= "file_name,n_adj,n_adv,n_verb,n_noun,n_token\n"; //this is the header line
		mOutputDir = new File((String) getConfigParameterValue(PARAM_OUTPUTDIR));

		//create the output folder if it does not already exist
		if (!mOutputDir.exists()) {
			mOutputDir.mkdirs();
		}

		//create the output file
		mOutputFile = new File(mOutputDir, outputFileName);
		try {
			fout = new FileWriter(mOutputFile);
		} catch (IOException e) {
			e.printStackTrace();
		}

		//add tags to lexical tag lists
		String[] adjTags = { "JJ", "JJR", "JJS"}; //adj
		String[] advTags = { "RB", "RBR", "RBS", "WRB"}; //adv
		String[] verbTags = { "VB", "VBD", "VBG", "VBN", "VBP", "VBZ"}; //verb
		String[] nounTags = { "NN", "NNS", "NNP", "NNPS"}; //noun

		Collections.addAll(adjTagList, adjTags);
		Collections.addAll(advTagList, advTags);
		Collections.addAll(verbTagList, verbTags);
		Collections.addAll(nounTagList, nounTags);
	}

	/**
	 * Processes the CAS which was populated by the Analysis Engines. <br>
	 * 
	 * @param aCAS
	 *          a CAS which has been populated by the TAEs
	 * 
	 * @throws ResourceProcessException
	 *           if there is an error in processing the Resource
	 * 
	 * @see org.apache.uima.collection.base_cpm.CasObjectProcessor#processCas(org.apache.uima.cas.CAS)
	 */
	public void processCas(CAS aCAS) throws ResourceProcessException {
		//get a jCas object from the CAS object
		JCas jcas;
		try {
			jcas = aCAS.getJCas();
		} catch (CASException e) {
			throw new ResourceProcessException(e);
		}


		//for getting file name from CAS. File name was populated into the CAS by the
		//collection reader.
		FSIterator itDocMeta = jcas.getAnnotationIndex(DocumentMetaData.type).iterator();
		while(itDocMeta.hasNext()) {
			String fileName = "";
			DocumentMetaData docMeta = (DocumentMetaData) itDocMeta.next();
			fileName = docMeta.getFileName();
			resultStr += fileName + ",";
		}

		// get annotation indexes and iterator
		Iterator posIter = jcas.getAnnotationIndex(POS.type).iterator();

		//for counting number of each POS type 
		int nAdj = 0; 
		int nAdv = 0; 
		int nVerb = 0; 
		int nNoun = 0; 
		int nTokens = 0; 

		//interate through all POS annotations
		while(posIter.hasNext()) {
			nTokens++; //increase token count

			//check the POS tag for each token, see if it exists 
			//in the POS tags list
			POS pos = (POS) posIter.next();
			String tag = pos.getTag();
			if(adjTagList.contains(tag)) {
				nAdj++; //if the token is an adjective, increase the adjective count
			} else if(advTagList.contains(tag)) {
				nAdv++;//if the token is an adverb, increase the adverb count
			} else if(verbTagList.contains(tag)) {
				nVerb++;//if the token is a verb, increase the verb count
			} else if(nounTagList.contains(tag)) {
				nNoun++;//if the token is a noun, increase the noun count
			}
		}

		resultStr += nAdj + "," + nAdv + "," + nVerb + "," + nNoun + "," + nTokens + "\n";

	}

	@Override
	public void destroy() {
		super.destroy();
		
		//write the results to the output file
		try {
			fout.write(resultStr);
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		
		if(fout != null) {
			try {
				fout.flush();
				fout.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

}
