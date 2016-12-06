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

/**
 * A simple CAS consumer that writes the analysis results to a csv file.
 * <p>
 * This CAS Consumer takes one parameter:
 * <ul>
 * <li><code>OutputDirectory</code> - path to directory into which output files will be written</li>
 * </ul>
 */
public class CSVOutputCasConsumer extends CasConsumer_ImplBase {
	/**
	 * Name of configuration parameter that must be set to the path of a directory into which the
	 * output files will be written.
	 */
	public static final String PARAM_OUTPUTDIR = "OutputDirectory";

	FileWriter fout;
	private final String outputFileName = "result.csv";

	/**
	 * Initializes the output file in the output directory.
	 */
	public void initialize() throws ResourceInitializationException {
		File mOutputDir;
		File mOutputFile;
		mOutputDir = new File((String) getConfigParameterValue(PARAM_OUTPUTDIR));
		if (!mOutputDir.exists()) {
			mOutputDir.mkdirs();
		}

		mOutputFile = new File(mOutputDir, outputFileName);
		try {
			fout = new FileWriter(mOutputFile);
		} catch (IOException e) {
			e.printStackTrace();
		}
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

		// the string to be written to output file
		String resultStr = "";

		//for getting file name from CAS. File name was populated into the CAS by the
		//collection reader.
		FSIterator itDocMeta = jcas.getAnnotationIndex(DocumentMetaData.type).iterator();
		
		//for getting the Lexical Density result
		FSIterator itLexicalDensity = jcas.getAnnotationIndex(LD.type).iterator();

		while(itDocMeta.hasNext() && itLexicalDensity.hasNext()) {
			String fileName = "";
			double ldValue = 0;

			DocumentMetaData docMeta = (DocumentMetaData) itDocMeta.next();
			LD ld = (LD) itLexicalDensity.next();

			fileName = docMeta.getFileName();
			ldValue = ld.getValue();

			resultStr += fileName + "," + ldValue + "\n";
		}

		try {
			fout.append(resultStr);
		} catch (IOException e1) {
			e1.printStackTrace();
		}

	}
	
	@Override
	public void destroy() {
		super.destroy();
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
