package src;

import java.util.Map;

/**
 * The BaseExpression class is an abstract class that implements the Expression interface.
 * It provides a default implementation for the evaluate() method, which evaluates the expression using an empty assignment.
 * The getOperatorSymbol() method is declared as abstract and must be implemented by any class that extends BaseExpression.
 */
public abstract class BaseExpression implements Expression {

    @Override
    public Boolean evaluate() throws Exception {
        Map<String, Boolean> assignment = new java.util.HashMap<>();
        return this.evaluate(assignment);
    }

    /**
     * This method is an abstract method that must be implemented by any class that extends BaseExpression.
     * It is used to get the symbol of the operator in the expression.
     *
     * @return The symbol of the operator as a String.
     */
    protected abstract String getOperatorSymbol();
}
