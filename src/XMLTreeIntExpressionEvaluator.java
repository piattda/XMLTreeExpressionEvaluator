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
public final class XMLTreeIntExpressionEvaluator {

    /**
     * Private constructor so this utility class cannot be instantiated.
     */
    private XMLTreeIntExpressionEvaluator() {
    }

    //Evaluate the expression returned from the xml
    /**
     * The method evaluateExpression will take a string, and two ints
     * It will return the operation that the string represents on the 
     * two numbers.
     * 
     * @requires
     * 	[The result of the operation on the two numbers will be a number]
     * @ensures
     * 	[The operation of both numbers will be returned.]
     * @param expression
     * 			String that is the operator
     * @param number1
     * 			Integer 1 that the operation will be performed on 
     * @param number2
     * 			Integer 2 that the operation will be performed on.
     * @return
     * 			Operation of the two integers.
     */
    private static int evaluateExpression(String expression, int number1, int number2){
    if(expression.equals("plus")){
    	return (number1 + number2);
    }else if(expression.equals("minus")){
    	return (number1 - number2);
    }else if(expression.equals("times")){
    	return(number1 * number2);
    }else if(expression.equals("divide")){
    	return(number1 / number2);
    }
    return 0;
    
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
    private static int evaluate(XMLTree exp) {
    	
    	assert exp != null : "Violation of: exp is not null";

        //If the root is a number we're done.
       if(exp.label().equals("number")){
    	   return Integer.parseInt(exp.attributeValue("value"));
        //else we have to do some math and recursive calls. I know right.	   
       } else {
    	   /*The root is an operator. We need to keep looking through it until we find a number
    	    * Numbers are stored as attribute values*/
    	   int number1 = evaluate(exp.child(0));  
    	   int number2 = evaluate(exp.child(1));
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