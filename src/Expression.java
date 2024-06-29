package src;

import java.util.List;
import java.util.Map;

/**
 * The Expression interface represents a logical expression.
 * It provides methods for evaluating the expression, getting the variables in the expression,
 * converting the expression to a string, assigning a new expression to a variable,
 * and transforming the expression into different logical operations.
 * It also provides a method for simplifying the expression.
 */
public interface Expression {
    /**
     * Evaluates the expression using the variable values provided in the assignment and returns the result.
     * If the expression contains a variable which is not in the assignment, an exception is thrown.
     *
     * @param assignment A map containing variable names as keys and their corresponding boolean values as values.
     * @return The result of the expression evaluation as a Boolean.
     * @throws Exception If the expression contains a variable not present in the assignment map.
     */
    Boolean evaluate(Map<String, Boolean> assignment) throws Exception;

    /**
     * A convenience method that evaluates the expression without any variable assignment.
     * This method is similar to the `evaluate(assignment)` method, but it uses an empty assignment.
     * If the expression contains a variable, an exception is thrown.
     *
     * @return The result of the expression evaluation as a Boolean.
     * @throws Exception If the expression contains a variable.
     */
    Boolean evaluate() throws Exception;

    /**
     * Returns a list of the variables in the expression.
     * The variables are returned as a list of strings, where each string represents the name of a variable.
     * If the expression does not contain any variables, an empty list is returned.
     *
     * @return A list of strings representing the variables in the expression.
     */
    List<String> getVariables();

    /**
     * Returns a string representation of the expression.
     * This method is used to convert the expression into a string format that can be easily read and understood.
     * The format of the string representation is specific to the implementation of the Expression interface.
     *
     * @return A string representing the expression.
     */
    String toString();

    /**
     * Returns a new expression in which all occurrences of the variable
     * var are replaced with the provided expression. This method does not modify the
     * current expression.
     *
     * @param var        The variable name to be replaced in the expression.
     * @param expression The new expression to replace the variable.
     * @return A new Expression where all occurrences of the variable have been replaced by the provided expression.
     */
    Expression assign(String var, Expression expression);

    /**
     * Returns the expression tree resulting from converting all the operations to the logical Nand operation.
     * This method is used to transform the current expression
     * into a new expression where all operations are replaced by Nand operations.
     * The original expression is not modified.
     *
     * @return A new Expression where all operations have been replaced by Nand operations.
     */
    Expression nandify();

    /**
     * Returns the expression tree resulting from converting all the operations to the logical Nor operation.
     * This method is used to transform the current expression
     * into a new expression where all operations are replaced by Nor operations.
     * The original expression is not modified.
     *
     * @return A new Expression where all operations have been replaced by Nor operations.
     */
    Expression norify();

    /**
     * Returns a simplified version of the current expression.
     * This method is used to simplify the current expression by applying logical simplifications.
     * The original expression is not modified.
     *
     * @return A new Expression that is a simplified version of the current expression.
     */
    Expression simplify();
}