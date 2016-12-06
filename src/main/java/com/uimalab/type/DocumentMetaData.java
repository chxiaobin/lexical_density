

/* First created by JCasGen Mon Dec 05 14:25:28 CET 2016 */
package com.uimalab.type;

import org.apache.uima.jcas.JCas; 
import org.apache.uima.jcas.JCasRegistry;
import org.apache.uima.jcas.cas.TOP_Type;

import org.apache.uima.jcas.tcas.Annotation;


import org.apache.uima.jcas.cas.AnnotationBase;


/** 
 * Updated by JCasGen Mon Dec 05 14:43:27 CET 2016
 * XML source: /home/xiaobin/work/project/pccl-16ws/slide_xiaobin/uima/lab/code/src/main/resources/descriptor/TypeSystem.xml
 * @generated */
public class DocumentMetaData extends Annotation {
  /** @generated
   * @ordered 
   */
  @SuppressWarnings ("hiding")
  public final static int typeIndexID = JCasRegistry.register(DocumentMetaData.class);
  /** @generated
   * @ordered 
   */
  @SuppressWarnings ("hiding")
  public final static int type = typeIndexID;
  /** @generated
   * @return index of the type  
   */
  @Override
  public              int getTypeIndexID() {return typeIndexID;}
 
  /** Never called.  Disable default constructor
   * @generated */
  protected DocumentMetaData() {/* intentionally empty block */}
    
  /** Internal - constructor used by generator 
   * @generated
   * @param addr low level Feature Structure reference
   * @param type the type of this Feature Structure 
   */
  public DocumentMetaData(int addr, TOP_Type type) {
    super(addr, type);
    readObject();
  }
  
  /** @generated
   * @param jcas JCas to which this Feature Structure belongs 
   */
  public DocumentMetaData(JCas jcas) {
    super(jcas);
    readObject();   
  } 

  /** @generated
   * @param jcas JCas to which this Feature Structure belongs
   * @param begin offset to the begin spot in the SofA
   * @param end offset to the end spot in the SofA 
  */  
  public DocumentMetaData(JCas jcas, int begin, int end) {
    super(jcas);
    setBegin(begin);
    setEnd(end);
    readObject();
  }   

  /** 
   * <!-- begin-user-doc -->
   * Write your own initialization here
   * <!-- end-user-doc -->
   *
   * @generated modifiable 
   */
  private void readObject() {/*default - does nothing empty block */}
     
 
    
  //*--------------*
  //* Feature: fileName

  /** getter for fileName - gets 
   * @generated
   * @return value of the feature 
   */
  public String getFileName() {
    if (DocumentMetaData_Type.featOkTst && ((DocumentMetaData_Type)jcasType).casFeat_fileName == null)
      jcasType.jcas.throwFeatMissing("fileName", "com.uimalab.type.DocumentMetaData");
    return jcasType.ll_cas.ll_getStringValue(addr, ((DocumentMetaData_Type)jcasType).casFeatCode_fileName);}
    
  /** setter for fileName - sets  
   * @generated
   * @param v value to set into the feature 
   */
  public void setFileName(String v) {
    if (DocumentMetaData_Type.featOkTst && ((DocumentMetaData_Type)jcasType).casFeat_fileName == null)
      jcasType.jcas.throwFeatMissing("fileName", "com.uimalab.type.DocumentMetaData");
    jcasType.ll_cas.ll_setStringValue(addr, ((DocumentMetaData_Type)jcasType).casFeatCode_fileName, v);}    
  }

    