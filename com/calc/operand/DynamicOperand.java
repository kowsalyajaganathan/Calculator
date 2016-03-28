package com.calc.operand;


import org.apache.log4j.Logger;

/**
 *DynamicOperand.java
 *
 *Version:1.0
 *Date: 24-Mar-2016	`
 *Author:Kowsalya Jaganathan
 *
 *This class is to handle child node which are arithmetic expressions
 *
 *
*/

public class DynamicOperand implements OperandFactory{
	final static Logger logger = Logger.getLogger(DynamicOperand.class);
	String operand;
	
	public DynamicOperand(String operand){
		this.operand = operand;
	}

	public String getOperand(){
		return operand;
	}
    
	public void setOperand(String operand) {
		this.operand = operand;
	}
	
	
}
