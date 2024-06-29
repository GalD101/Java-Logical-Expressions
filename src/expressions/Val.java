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
