import components.naturalnumber.NaturalNumber;
import components.naturalnumber.NaturalNumber2;
import components.simplereader.SimpleReader;
import components.simplereader.SimpleReader1L;
import components.simplewriter.SimpleWriter;
import components.simplewriter.SimpleWriter1L;
import components.utilities.Reporter;
import components.xmltree.XMLTree;
import components.xmltree.XMLTree1;

/**
 * Program to evaluate XMLTree expressions of {@code int}.
 * 
 * @author David Piatt
 * 
 */
public final class XMLTreeNNExpressionEvaluator {

    /**
     * Private constructor so this utility class cannot be instantiated.
     */
    private XMLTreeNNExpressionEvaluator() {
    }
    
    //This method made to evaluate the expression returned from the xml tree.
    
    /**
     * 
     * The method evaluateExpression will take a string, and two NaturalNumbers
     * It will return the operation that the string represents on the 
     * two numbers.
     * 
     * @requires
     * 	[The result of the operation on the two natural numbers will be a natural number]
     * @ensures
     * 	[The operation of both numbers will be performed on the first natural number. It will be returned.]
     * @param exp
     * 			String that is the operator
     * @param number1
     * 			Integer 1 that the operation will be performed on 
     * @param number2
     * 			Integer 2 that the operation will be performed on.
     * @return
     * 			Operation of the two integers.
     */
    
    
    private static NaturalNumber evaluateExpression(String exp, NaturalNumber number1, NaturalNumber number2){
    	//pull out the value and perform the operation
    	
    	NaturalNumber value = number1.newInstance(); 
    	value.copyFrom(number1);
    	//NaturalNumber zero = new NaturalNumber2(0);
    	if(exp.equals("plus")){
			number1.add(number2);
			
			return number1;
		}else if(exp.equals("minus")){
			if(number2.compareTo(number1) > 0){
				Reporter.fatalErrorToConsole("You cannot have a negative number with Natural Numbers.");
			}else{
			
			number1.subtract(number2);
			return number1;
			}
		}else if(exp.equals("times")){
			number1.multiply(number2);
			return number1;
		}else if(exp.equals("divide")){
			if(number2.isZero()){
				Reporter.fatalErrorToConsole("You cannot divide by zero!");
			}else{
			number1.divide(number2);
			return number1;
			}
		}
	    NaturalNumber zero = new  NaturalNumber2(0);
	    
	    return zero;
    
    }
    
    /**
     * Evaluate the given expression.
     * 
     * @param exp
     *            the {@code XMLTree} representing the expression
     * @return the value of the expression
     * @requires <pre>
     * [exp is a subtree of a well-formed XML arithmetic expression]  and
     *  [the label of the root of exp is not "expression"]
     * </pre>
     * @ensures evaluate = [the value of the expression]
     */
    private static NaturalNumber evaluate(XMLTree exp) {
    	assert exp != null : "Violation of: exp is not null";
    	
    	if(exp.label().equals("number")){
    		// make natural number number equal to the attribute value of "value"
    		//This is the case that the root node is a number.
    		NaturalNumber number = new NaturalNumber2(Integer.parseInt(exp.attributeValue("value")));
    		return number;
    	
    	}else{
    		//otherwise make two numbers to perform an operation on
    		NaturalNumber number1 = new NaturalNumber2(evaluate(exp.child(0)));
    		NaturalNumber number2 = new NaturalNumber2(evaluate(exp.child(1)));
    		
    	return evaluateExpression(exp.label(), number1, number2);
    	}
    	
    }

    /**
     * Main method.
     * 
     * @param args
     *            the command line arguments
     */
    public static void main(String[] args) {
        SimpleReader in = new SimpleReader1L();
        SimpleWriter out = new SimpleWriter1L();

        out.print("Enter the name of an expression XML file: ");
        String file = in.nextLine();
        while (!file.equals("")) {
            XMLTree exp = new XMLTree1(file);
         
            out.println(evaluate(exp.child(0)));
            out.print("Enter the name of an expression XML file: ");
            file = in.nextLine();
        }

        in.close();
        out.close();
    }

}