
/* First created by JCasGen Mon Dec 05 14:25:28 CET 2016 */
package com.uimalab.type;

import org.apache.uima.jcas.JCas;
import org.apache.uima.jcas.JCasRegistry;
import org.apache.uima.cas.impl.TypeImpl;
import org.apache.uima.cas.Type;
import org.apache.uima.cas.impl.FeatureImpl;
import org.apache.uima.cas.Feature;
import org.apache.uima.jcas.tcas.Annotation_Type;

import org.apache.uima.jcas.cas.AnnotationBase_Type;

/** 
 * Updated by JCasGen Mon Dec 05 14:43:27 CET 2016
 * @generated */
public class DocumentMetaData_Type extends Annotation_Type {
  /** @generated */
  @SuppressWarnings ("hiding")
  public final static int typeIndexID = DocumentMetaData.typeIndexID;
  /** @generated 
     @modifiable */
  @SuppressWarnings ("hiding")
  public final static boolean featOkTst = JCasRegistry.getFeatOkTst("com.uimalab.type.DocumentMetaData");
 
  /** @generated */
  final Feature casFeat_fileName;
  /** @generated */
  final int     casFeatCode_fileName;
  /** @generated
   * @param addr low level Feature Structure reference
   * @return the feature value 
   */ 
  public String getFileName(int addr) {
        if (featOkTst && casFeat_fileName == null)
      jcas.throwFeatMissing("fileName", "com.uimalab.type.DocumentMetaData");
    return ll_cas.ll_getStringValue(addr, casFeatCode_fileName);
  }
  /** @generated
   * @param addr low level Feature Structure reference
   * @param v value to set 
   */    
  public void setFileName(int addr, String v) {
        if (featOkTst && casFeat_fileName == null)
      jcas.throwFeatMissing("fileName", "com.uimalab.type.DocumentMetaData");
    ll_cas.ll_setStringValue(addr, casFeatCode_fileName, v);}
    
  



  /** initialize variables to correspond with Cas Type and Features
	 * @generated
	 * @param jcas JCas
	 * @param casType Type 
	 */
  public DocumentMetaData_Type(JCas jcas, Type casType) {
    super(jcas, casType);
    casImpl.getFSClassRegistry().addGeneratorForType((TypeImpl)this.casType, getFSGenerator());

 
    casFeat_fileName = jcas.getRequiredFeatureDE(casType, "fileName", "uima.cas.String", featOkTst);
    casFeatCode_fileName  = (null == casFeat_fileName) ? JCas.INVALID_FEATURE_CODE : ((FeatureImpl)casFeat_fileName).getCode();

  }
}



    