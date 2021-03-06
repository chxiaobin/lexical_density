

/* First created by JCasGen Tue Nov 29 22:48:55 CET 2016 */
package com.uimalab.type;

import org.apache.uima.jcas.JCas; 
import org.apache.uima.jcas.JCasRegistry;
import org.apache.uima.jcas.cas.TOP_Type;

import org.apache.uima.jcas.tcas.Annotation;


/** part of speech
 * Updated by JCasGen Mon Dec 05 14:43:27 CET 2016
 * XML source: /home/xiaobin/work/project/pccl-16ws/slide_xiaobin/uima/lab/code/src/main/resources/descriptor/TypeSystem.xml
 * @generated */
public class POS extends Annotation {
  /** @generated
   * @ordered 
   */
  @SuppressWarnings ("hiding")
  public final static int typeIndexID = JCasRegistry.register(POS.class);
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
  protected POS() {/* intentionally empty block */}
    
  /** Internal - constructor used by generator 
   * @generated
   * @param addr low level Feature Structure reference
   * @param type the type of this Feature Structure 
   */
  public POS(int addr, TOP_Type type) {
    super(addr, type);
    readObject();
  }
  
  /** @generated
   * @param jcas JCas to which this Feature Structure belongs 
   */
  public POS(JCas jcas) {
    super(jcas);
    readObject();   
  } 

  /** @generated
   * @param jcas JCas to which this Feature Structure belongs
   * @param begin offset to the begin spot in the SofA
   * @param end offset to the end spot in the SofA 
  */  
  public POS(JCas jcas, int begin, int end) {
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
  //* Feature: tag

  /** getter for tag - gets the POS tag
   * @generated
   * @return value of the feature 
   */
  public String getTag() {
    if (POS_Type.featOkTst && ((POS_Type)jcasType).casFeat_tag == null)
      jcasType.jcas.throwFeatMissing("tag", "com.uimalab.type.POS");
    return jcasType.ll_cas.ll_getStringValue(addr, ((POS_Type)jcasType).casFeatCode_tag);}
    
  /** setter for tag - sets the POS tag 
   * @generated
   * @param v value to set into the feature 
   */
  public void setTag(String v) {
    if (POS_Type.featOkTst && ((POS_Type)jcasType).casFeat_tag == null)
      jcasType.jcas.throwFeatMissing("tag", "com.uimalab.type.POS");
    jcasType.ll_cas.ll_setStringValue(addr, ((POS_Type)jcasType).casFeatCode_tag, v);}    
  }

    