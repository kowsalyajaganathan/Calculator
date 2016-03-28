package com.calc.main;


import java.math.BigInteger;

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import com.calc.common.CalculatorValidationException;
import com.calc.tree.ExpressionTree;

/**
 *Calculator.java
 *
 *Version:1.0
 *Date: 24-Mar-2016	`
 *Author:Kowsalya Jaganathan
 *
 *This class is the main class for the calculator application.
 *It takes input through command line and prints output to the console
 *
 *
*/

public class Calculator {
	final static Logger logger = Logger.getLogger(Calculator.class);
	
	public static void main(String[] args){
		try{
			String input = args[0];
			BasicConfigurator.configure();
			if(args.length >1){
				PropertyConfigurator.configure(args[1]);
			}			
			BigInteger result;
			ExpressionTree tree = new ExpressionTree();
			tree.parseExpression(input);
			result = tree.performOperation();
			logger.info("The result is "+result);
		}catch(CalculatorValidationException e){
			logger.error(e.getMessage());
		}catch(Exception e){
			e.printStackTrace();
			logger.error("An Exception occured"+e);
		}
	}
	
	
}