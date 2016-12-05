

/* First created by JCasGen Tue Nov 29 23:36:05 CET 2016 */
package com.uimalab.type;

import org.apache.uima.jcas.JCas; 
import org.apache.uima.jcas.JCasRegistry;
import org.apache.uima.jcas.cas.TOP_Type;

import org.apache.uima.jcas.tcas.Annotation;


import org.apache.uima.jcas.cas.AnnotationBase;


/** lexical density
 * Updated by JCasGen Tue Nov 29 23:42:05 CET 2016
 * XML source: /home/xiaobin/work/project/pccl-16ws/slide_xiaobin/uima/lab/code/src/main/resources/descriptor/TypeSystem.xml
 * @generated */
public class LD extends Annotation {
  /** @generated
   * @ordered 
   */
  @SuppressWarnings ("hiding")
  public final static int typeIndexID = JCasRegistry.register(LD.class);
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
  protected LD() {/* intentionally empty block */}
    
  /** Internal - constructor used by generator 
   * @generated
   * @param addr low level Feature Structure reference
   * @param type the type of this Feature Structure 
   */
  public LD(int addr, TOP_Type type) {
    super(addr, type);
    readObject();
  }
  
  /** @generated
   * @param jcas JCas to which this Feature Structure belongs 
   */
  public LD(JCas jcas) {
    super(jcas);
    readObject();   
  } 

  /** @generated
   * @param jcas JCas to which this Feature Structure belongs
   * @param begin offset to the begin spot in the SofA
   * @param end offset to the end spot in the SofA 
  */  
  public LD(JCas jcas, int begin, int end) {
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
  //* Feature: value

  /** getter for value - gets lexical density value
   * @generated
   * @return value of the feature 
   */
  public double getValue() {
    if (LD_Type.featOkTst && ((LD_Type)jcasType).casFeat_value == null)
      jcasType.jcas.throwFeatMissing("value", "com.uimalab.type.LD");
    return jcasType.ll_cas.ll_getDoubleValue(addr, ((LD_Type)jcasType).casFeatCode_value);}
    
  /** setter for value - sets lexical density value 
   * @generated
   * @param v value to set into the feature 
   */
  public void setValue(double v) {
    if (LD_Type.featOkTst && ((LD_Type)jcasType).casFeat_value == null)
      jcasType.jcas.throwFeatMissing("value", "com.uimalab.type.LD");
    jcasType.ll_cas.ll_setDoubleValue(addr, ((LD_Type)jcasType).casFeatCode_value, v);}    
  }

    