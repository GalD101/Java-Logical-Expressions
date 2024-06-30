package src.expressions;

import src.Expression;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * The Val class implements the Expression interface.
 * It represents a Boolean value in the form of an expression.
 * The Boolean value can be either true or false, and is stored in the Val object.
 * The Val class provides methods to evaluate the Boolean value, get variables, convert the value to a string,
 * assign an expression to a variable, and simplify the expression.
 */
public class Val implements Expression {
    private final Boolean value;

    /**
     * Constructs a new Val object.
     * This constructor initializes a new Val object with the given Boolean value.
     * If the provided value is null, an IllegalArgumentException is thrown.
     *
     * @param value The Boolean value to be stored in the Val object. Must be either true or false, not null.
     * @throws IllegalArgumentException if the provided value is null.
     */
    public Val(Boolean value) {
        if (value == null) {
            throw new IllegalArgumentException("Value cannot be null - must be either true or false");
        }

        this.value = value;
    }

    @Override
    public Boolean evaluate(Map<String, Boolean> assignment) throws Exception {
        return this.evaluate();
    }

    @Override
    public Boolean evaluate() throws Exception {
        return this.value;
    }

    @Override
    public List<String> getVariables() {
        // No variables in this class so return an empty list
        return new ArrayList<>();
    }

    @Override
    public String toString() {
        if (this.value) {
            return "T";
        } else {
            return "F";
        }
    }

    @Override
    public Expression assign(String var, Expression expression) {
        return this;
    }

    @Override
    public Expression nandify() {
        return this;
    }

    @Override
    public Expression norify() {
        return this;
    }

    @Override
    public Expression simplify() {
        return this;
    }
}
