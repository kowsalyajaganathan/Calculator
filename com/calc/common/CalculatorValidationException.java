package com.calc.common;

/**
 *CalculatorValidationException.java
 *
 *Version:1.0
 *Date: 24-Mar-2016
 *Author:Kowsalya Jaganathan
 *
 *This class is create a custom Exception for the calculator application
 * 
 *
*/

@SuppressWarnings("serial")
public class CalculatorValidationException extends Exception{
	
	public CalculatorValidationException(String message){
		super(message);
	}
	public CalculatorValidationException(Throwable t){
		super(t);
	}	
	public CalculatorValidationException(String message,Throwable t){
		super(message,t);
	}
	
	
	

}