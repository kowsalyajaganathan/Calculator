package com.calc.operation;


import java.math.BigInteger;

import org.apache.log4j.Logger;

import com.calc.operand.OperandFactory;

/**
 *OperationFactory.java
 *
 *Version:1.0
 *Date: 24-Mar-2016	`
 *Author:Kowsalya Jaganathan
 *
 *This factory class is produce different arithmetic operation objects
 * 
 *
*/

public abstract class OperationFactory implements Comparable<OperationFactory>{
	final static Logger logger = Logger.getLogger(OperationFactory.class);
	OperandFactory leftOperand = null;
	OperandFactory rightOperand = null;
    BigInteger result = null;
	String parentNode = "";
	
	public abstract BigInteger  performOperation();
	//public abstract String toString();
	
	public   int compareTo(OperationFactory opFactory)
	{
		 String compareParent=((OperationFactory)opFactory).getParentNode();
		    return this.parentNode.compareTo(compareParent);
	}

	public OperandFactory getLeftOperand() {
		return leftOperand;
	}

	public void setLeftOperand(OperandFactory leftOperand) {
		this.leftOperand = leftOperand;
	}

	public OperandFactory getRightOperand() {
		return rightOperand;
	}

	public void setRightOperand(OperandFactory rightOperand) {
		this.rightOperand = rightOperand;
	}

	public BigInteger getResult() {
		return result;
	}

	public void setResult(BigInteger result) {
		this.result = result;
	}

	public String getParentNode() {
		return parentNode;
	}

	public void setParentNode(String parentNode) {
		this.parentNode = parentNode;
	}
	
	
}