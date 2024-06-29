package src.expressions;

import src.Expression;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Val implements Expression {
    private Boolean value;

    public Val(Boolean value) {
        if (value == null) {
            throw new IllegalArgumentException("Value cannot be null - must be either true or false");
        }
        this.value = value;
    }

    public Boolean evaluate(Map<String, Boolean> assignment) throws Exception {
        return this.evaluate();
    }

    public Boolean evaluate() throws Exception {
        return this.value;
    }

    public List<String> getVariables() {
        return new ArrayList<>(); // No variables in this class so return an empty list
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
//    public boolean equals(Object other) {
//        if (other == null) {
//            return false;
//        }
//        if (other instanceof Val) {
//            // NOT GOOD
//            return this.value == ((Val) other).value;
//        }
//        return false;
//    }

    // Returns a new expression in which all occurrences of the variable
    // var are replaced with the provided expression (Does not modify the
    // current expression). // TODO: Check if this is correct
    public Expression assign(String var, Expression expression) {
        // This class is val meaning it can only be T or F so it can never be var. TODO: check this!!! - I am pretty sure this is correct
        // thus I will simply return this since there is no var to replace ever
        return this; // also breaks recursions (see other implementations)
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
