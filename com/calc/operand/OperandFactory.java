package com.calc.operand;


import org.apache.log4j.Logger;

/**
 *OperationFactory.java
 *
 *Version:1.0
 *Date: 24-Mar-2016	`
 *Author:Kowsalya Jaganathan
 *
 *This factory class is produce operand objects
 *
*/

public interface OperandFactory {
	final static Logger logger = Logger.getLogger(OperandFactory.class);
	String operand = null;
	
	public String getOperand();
	public void setOperand(String op);
	
}