package com.calc.operand;


import org.apache.log4j.Logger;

/**
 *StaticOperand.java
 *
 *Version:1.0
 *Date: 24-Mar-2016	`
 *Author:Kowsalya Jaganathan
 *
 *This class is to handle leaf node which are integer
 *
*/

public class StaticOperand implements OperandFactory{
	final static Logger logger = Logger.getLogger(StaticOperand.class);
    String operand;
    
	
	public StaticOperand(String operand){
		this.operand = operand;
	}

	public String getOperand(){
		return operand;
	}

	public void setOperand(String operand) {
		this.operand = operand;
	}
	



}