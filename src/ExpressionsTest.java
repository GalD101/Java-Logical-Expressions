package src;

import src.expressions.And;
import src.expressions.Nand;
import src.expressions.Nor;
import src.expressions.Not;
import src.expressions.Or;
import src.expressions.Val;
import src.expressions.Var;
import src.expressions.Xnor;
import src.expressions.Xor;

import java.util.HashMap;

/**
 * The ExpressionsTest class is the main class of the application.
 * It contains a main method which creates a complex logical expression using various logical operators and variables.
 * The expression is then evaluated, nandified, norified, and simplified, with the results printed to the console.
 */
public class ExpressionsTest {
    /**
     * The main method of the ExpressionsTest class.
     * This method creates a complex logical expression using various logical operators and variables.
     * It then evaluates, nandifies, norifies, and simplifies the expression, printing the results to the console.
     *
     * @param args The command line arguments. This parameter is not used in this method.
     */
    public static void main(String[] args) {
        // Create an expression with 3 variables
        Expression x = new Var("x");
        Expression y = new Var("y");
        Expression z = new Var("z");

        // Create a complex expression with the 3 variables
        // and all the expression types
        Expression complexExpression =
                new Not(new And(new Nand(new Nor(new Or(new Xnor(new Xor(x, y), z), x), y),
                        new Nor(new Or(new Xnor(new Xor(x, y), z), x), y)), new Val(true)));

        // Print the complex expression
        System.out.println(complexExpression);

        // Create a map with the values of the variables
        HashMap<String, Boolean> map = new HashMap<>();
        map.put("x", true);
        map.put("y", false);
        map.put("z", true);

        // Evaluate the complex expression with the map
        try {
            System.out.println(complexExpression.evaluate(map));
        } catch (Exception e) {
            System.out.println(complexExpression);
        }

        // Nandify the complex expression and print it
        System.out.println(complexExpression.nandify());

        // Norify the complex expression and print it
        System.out.println(complexExpression.norify());

        // Simplify the complex expression and print it
        System.out.println(complexExpression.simplify());
    }
}
