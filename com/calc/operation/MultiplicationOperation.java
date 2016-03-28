package com.calc.operation;


import java.math.BigInteger;

import org.apache.log4j.Logger;

/**
 *OperationFactory.java
 *
 *Version:1.0
 *Date: 24-Mar-2016	`
 *Author:Kowsalya Jaganathan
 *
 *This class is to handle multiplication operation
 *
 *
*/

public class MultiplicationOperation extends OperationFactory{
	final static Logger logger = Logger.getLogger(OperationFactory.class);
    BigInteger result;
	String operator;
	
	 public BigInteger performOperation(){
		BigInteger lOp = new BigInteger(super.leftOperand.getOperand());
    	BigInteger rOp = new BigInteger(super.rightOperand.getOperand());
    	
    	result = lOp.multiply(rOp);
    	return result;
	}
	 
	 public  String toString(){
		StringBuilder str = new StringBuilder();
		str.append("mult(")
	   .append(super.leftOperand.getOperand())
	   .append(",")
	   .append(super.rightOperand.getOperand())
	   .append(")");
	
		return str.toString();
	}
}