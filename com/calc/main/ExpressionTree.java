package com.calc.main;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collections;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.log4j.Logger;

import com.calc.common.CalculatorValidationException;
import com.calc.operand.DynamicOperand;
import com.calc.operand.OperandFactory;
import com.calc.operand.StaticOperand;
import com.calc.operation.AdditionOperation;
import com.calc.operation.AssignmentOperation;
import com.calc.operation.DivisionOperation;
import com.calc.operation.MultiplicationOperation;
import com.calc.operation.OperationFactory;
import com.calc.operation.SubtractionOperation;
import com.calc.common.Constants;
/**
 *ExpressionTree.java
 *
 *Version:1.0
 *Date: 24-Mar-2016	`
 *Author:Kowsalya Jaganathan
 *
 *This  class is to process the input arithmetic expression and store it in a tree structure.
 *The tree structure is arranged in infix order. 
 *The arithmetic expressions in the tree are executed in bottom-up structure to get the result
 * 
 *
*/

public class ExpressionTree {
	final static Logger logger = Logger.getLogger(ExpressionTree.class);
    OperationFactory operation = null;
    ArrayList<OperationFactory> operationList = new ArrayList<>();
	BigInteger result = null;
	
	/*
	 * This  method parses the input arithmetic expression and builds a tree structure
	 */
	public void parseExpression(String arithmeticOperation)throws CalculatorValidationException{
		String leftOp = null;
		String rightOp = null;
		int nodeCount =0;
		String parentNode = null;
		OperandFactory leftOperand = null;
		OperandFactory rightOperand = null;
		
		try{
			if(getNodeCount(arithmeticOperation,"\\(")!=getNodeCount(arithmeticOperation,"\\)")){
				throw new CalculatorValidationException(Constants.MSG_INVALID_INPUT);
			}
			parentNode = "0";
			buildObject(arithmeticOperation.trim(),parentNode); //Building root node			
			nodeCount = getNodeCount(arithmeticOperation,"\\(")-1;
			
			if(nodeCount>=1){
				for(int i=0;i<=nodeCount;i++){
					leftOperand = operationList.get(i).getLeftOperand();
					rightOperand = operationList.get(i).getRightOperand();					
					leftOp = leftOperand.getOperand();
					rightOp = rightOperand.getOperand();
					
					if((leftOperand) instanceof DynamicOperand){
						parentNode = i+"L";
						buildObject(leftOp,parentNode); //Building left child nodes
					}
					if((rightOperand) instanceof DynamicOperand){
						parentNode = i+"R";
						buildObject(rightOp,parentNode);//Building right child nodes
					}
					
				}
			}
			Collections.sort(operationList); //Sorting the arrayList based on the parent node. The tree structure is set to infix order
			//print();
		}
		catch(CalculatorValidationException e){
			throw new CalculatorValidationException(e.getMessage());
		}catch(Exception e){
			logger.debug("An Exception has occurred"+e.getStackTrace());
			throw new CalculatorValidationException(Constants.MSG_PARSE_ERROR);
		}
	}
	
		/*
		 * This method is to parse the input to form left and right operand  and build the Operation object.
		 */
	private void buildObject(String arithmeticOperation, String parentNode) throws CalculatorValidationException{
		String operator;
		String leftOp;
		String rightOp;
		int count=0;
		int index = 0;
		String temp ="";
		try{
			operator = arithmeticOperation.substring(0,arithmeticOperation.indexOf("(")); //Type of arithmetic operation
			operation = getOperation(operator.trim());									//instantiating appropriate arithmetic operation object
			leftOp = arithmeticOperation.substring(arithmeticOperation.indexOf("(")+1,arithmeticOperation.indexOf(","));
			if(Constants.ASSIGNMENT.equalsIgnoreCase(operator)){
				((AssignmentOperation)operation).setAssignmentVariable(leftOp);     // saving the assignment variable say,a,b for assignment operator
				arithmeticOperation = arithmeticOperation.substring(arithmeticOperation.indexOf(",")+1,arithmeticOperation.length());
				leftOp = arithmeticOperation.substring(0,arithmeticOperation.indexOf(","));
			}
			if( !leftOp.contains("(")){
				operation.setLeftOperand(new StaticOperand(leftOp.trim()));					//setting the left operand if it is a simple integer
				rightOp = arithmeticOperation.substring(arithmeticOperation.indexOf(",")+1,arithmeticOperation.length()-1);
				if( rightOp.contains(",")){													//checking if right operand is a simple integer or an expression
					operation.setRightOperand(new DynamicOperand(rightOp.trim()));
				} else {
					operation.setRightOperand(new StaticOperand(rightOp.trim()));
				}				
			} else {																	// if left operand is an expression, following logic is used to get the expression
				if(Constants.ASSIGNMENT.equalsIgnoreCase(operator)){
					
					count = getNodeCount(arithmeticOperation.substring(0,arithmeticOperation.indexOf(")")+1),"\\(");					
				} else{
					count = getNodeCount(arithmeticOperation.substring(arithmeticOperation.indexOf("(")+1,arithmeticOperation.indexOf("),")+1),"\\(");
				}
				temp = arithmeticOperation;
				while(count>0){
					index = index+temp.indexOf("),")+1;
					temp = temp.substring(temp.indexOf("),")+1,temp.length());
					count--;
				}
				if(Constants.ASSIGNMENT.equalsIgnoreCase(operator)){
					leftOp = arithmeticOperation.substring(0,index);
				}else{
					leftOp = arithmeticOperation.substring(arithmeticOperation.indexOf("(")+1,index);
				}
				rightOp = arithmeticOperation.substring(index+1,arithmeticOperation.length()-1);
				operation.setLeftOperand(new DynamicOperand(leftOp.trim()));
				if( rightOp.contains(",")){
					operation.setRightOperand(new DynamicOperand(rightOp.trim()));
				} else {
					operation.setRightOperand(new StaticOperand(rightOp.trim()));
				}
			}
			if(parentNode !=null && !parentNode.isEmpty() ){
				this.operation.setParentNode(parentNode);
			}
			operationList.add(this.operation);					
		}catch(Exception e){
			logger.debug("An Exception has occurred"+e.getStackTrace());
			throw new CalculatorValidationException(Constants.MSG_PARSE_ERROR);
		}
	}
	
	
	/*
	 * This method is to calculate the result of the input expression
	 */
	public BigInteger performOperation()throws CalculatorValidationException{
		int node;
		String parentNode;
		String parentOperandSide;
		String rightOperand;
		String leftOperand;
		int outerLoopMax ;
		try{
			int assignmentOperationCount = getOperationCount(Constants.ASSIGNMENT);
			if(assignmentOperationCount>0){
				outerLoopMax = assignmentOperationCount+1;
			}else{
				outerLoopMax = 1;
			}
			for(int j=0; j<outerLoopMax;j++){
				for( int i=operationList.size()-1;i>=0;i--){
					operation = operationList.get(i);
					parentNode = operation.getParentNode();
					rightOperand = operation.getRightOperand().getOperand();
					leftOperand = operation.getLeftOperand().getOperand();
					if(operation instanceof AssignmentOperation ){
						if(operation.getLeftOperand() instanceof StaticOperand && !leftOperand.contains("=")
								&& ((AssignmentOperation)operation).getAssignmentList().size()==0){					
							operation.performOperation();												//Resolving the assignment variable
							replaceConstants(((AssignmentOperation)operation).getAssignmentList(),i+"R");    //applying the assignment variable value to the input expression
						}
					}else{
						if(leftOperand.matches(".*[a-zA-Z].*") || rightOperand.matches(".*[a-zA-Z].*") ){ 
							continue;								//the operation is not executed,if the left or right operand has unresolved assignment variable in its expression 
						}
						if(operation.getResult()== null){
							result = operation.performOperation();		//Executing the arithmetic operation
							operation.setResult(result);
							if(!parentNode.equals("0")){				//updating parent nodes
								parentOperandSide = parentNode.substring(1,2);
								node = Integer.parseInt(parentNode.substring(0, 1));
								if(Constants.RIGHT.equals(parentOperandSide)){
									operationList.get(node).setRightOperand(new StaticOperand(result.toString()));																									
								}else{
									operationList.get(node).setLeftOperand(new StaticOperand(result.toString()));									
								}
								if(operationList.get(node) instanceof AssignmentOperation && !(operationList.get(node).getParentNode().equals("0"))){
									parentOperandSide = (operationList.get(node).getParentNode()).substring(1,2);
									node = Integer.parseInt((operationList.get(node).getParentNode()).substring(0, 1));										
									if(Constants.RIGHT.equals(parentOperandSide)){
										operationList.get(node).setRightOperand(new StaticOperand(result.toString()));
									}else{
										operationList.get(node).setLeftOperand(new StaticOperand(result.toString()));
									}
							   }	
							}else{										//processing root node
								if(operation instanceof AssignmentOperation){
									result = new BigInteger(operation.getRightOperand().getOperand());
								}else{
									result = operationList.get(0).performOperation();
								}
							}
						}
					}
					
				}
			}
		}catch(Exception e){
			logger.debug("An Exception has occurred"+e.getStackTrace());
			throw new CalculatorValidationException(Constants.MSG_PARSE_ERROR);
		}
		return result;
	}
	
		
	/*
	 * This method is to replace the variables in expression with integer value
	 */
	private void replaceConstants(ArrayList<String> assigmentList, String index){
		String rightOperand;
		String leftOperand;
		OperationFactory op;
		String assgnVariable = assigmentList.get(0).trim();
	    String assgnValue = assigmentList.get(1).trim();	
	    String parentNode = index;
	    for(int i=0; i<operationList.size();i++){
	    	op = operationList.get(i);
	    	if(op.getParentNode().equals(parentNode)){
	    		leftOperand = op.getLeftOperand().getOperand();			
		    	rightOperand = op.getRightOperand().getOperand();
				
		    	if(leftOperand.contains(assgnVariable)){
			    	if(op.getLeftOperand() instanceof StaticOperand){
			    		op.getLeftOperand().setOperand(assgnValue);
			    	}else{
			    		replaceConstants(assigmentList,i+"L");
			    	}
		    	}
		    	if(rightOperand.contains(assgnVariable)){
			    	if(op.getRightOperand() instanceof StaticOperand){
			    		op.getRightOperand().setOperand(assgnValue);
			    		
			    	}else{
			    		replaceConstants(assigmentList,i+"R");
			    	}
		    	}			    	
	    	}
	    }
	}
	
	
	/*
	 * This method is to find the number of child expressions in an expression
	 */
	private int getNodeCount(String expression,String searchStr){
		int nodeCount=0;
		Pattern pattern = Pattern.compile(searchStr);
		Matcher matcher = pattern.matcher(expression);
	
		while(matcher.find()){
			nodeCount++;
		}
		return nodeCount;		
	}
	
	
	/*
	 * This method is to instantiate arithmetic Operation object based on the input
	 */
    private OperationFactory getOperation(String op) throws CalculatorValidationException{
    	OperationFactory operation; 
    	switch(op){
	    	case Constants.ADDITION :
	    		operation = new AdditionOperation();
	    		break;
	    	case Constants.SUBTRACTION :
	    		operation = new SubtractionOperation();
	    		break;
	    	case Constants.MULTIPLICATION :
	    		operation = new MultiplicationOperation();
	    		break;
	    	case Constants.DIVISION :
	    		operation = new DivisionOperation();
	    		break;
	    	case Constants.ASSIGNMENT:
	    		operation = new AssignmentOperation();
	    		break;
	    	default :
	    		throw new CalculatorValidationException(Constants.MSG_INVALID_OPERATOR);
		}
    	return operation;
    }
    
    
	/*
	 * This  method is to count the occurrence of specified arithmetic operation in the given expression
	 */
    public int getOperationCount(String op){
    	int count=0;
    	for(int i=0;i<operationList.size();i++){
			operation = operationList.get(i);
			if(Constants.ASSIGNMENT.equals(op)){
				if(operation instanceof AssignmentOperation){
					count++;
				}
			}else if(Constants.ADDITION.equals(op)){
				if(operation instanceof AdditionOperation){
					count++;
				}
			}else if(Constants.SUBTRACTION.equals(op)){
				if(operation instanceof SubtractionOperation){
					count++;
				}
			}else if(Constants.DIVISION.equals(op)){
				if(operation instanceof DivisionOperation){
					count++;
				}
			}
    	}
    	return count;
    }
    
    
    /*
     * This method is to print the tree of dynamic expressions 
     */
    public void print(){
    	for(int i=0;i<operationList.size();i++){
			logger.info(operationList.get(i));
    	}
    }	
}