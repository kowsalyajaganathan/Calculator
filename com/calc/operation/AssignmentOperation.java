package com.calc.operation;


import java.math.BigInteger;
import java.util.ArrayList;
import org.apache.log4j.Logger;

/**
 *OperationFactory.java
 *
 *Version:1.0
 *Date: 24-Mar-2016	`
 *Author:Kowsalya Jaganathan
 *
 *This class is to handle assignment operation
 *
 *
*/

public class AssignmentOperation extends OperationFactory {
	final static Logger logger = Logger.getLogger(OperationFactory.class);
    BigInteger result;
	String operator;
	String assignmentVariable = "";
	ArrayList<String> assignmentList = new ArrayList<>();
	
	 public BigInteger performOperation(){
    	String leftValue = super.leftOperand.getOperand();
    	assignmentList.add(assignmentVariable);
    	assignmentList.add(leftValue);
    	super.leftOperand.setOperand(assignmentVariable+"="+leftValue);
    	return result;
	 }
	 
	 public  String toString(){
		StringBuilder str = new StringBuilder();
		str.append("let(")
		   .append(assignmentVariable)
		   .append(",")
		   .append(super.leftOperand.getOperand())
		   .append(",")
		   .append(super.rightOperand.getOperand())
		   .append(")");
		
		return str.toString();
	}
	 
	public String getAssignmentVariable() {
		return assignmentVariable;
	}

	public void setAssignmentVariable(String assignmentVariable) {
		this.assignmentVariable = assignmentVariable;
	}

	public ArrayList<String> getAssignmentList() {
		return assignmentList;
	}

	public void setAssignmentList(ArrayList<String> assignmentList) {
		this.assignmentList = assignmentList;
	}

	
   
	  


}