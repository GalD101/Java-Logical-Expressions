package src.expressions;

import src.Expression;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

public class Var implements Expression {
    private String name;

    public Var(String name) {
        if (name == null) {
            throw new IllegalArgumentException("Value cannot be null - must be a String");
        }

        this.name = name;
    }

    @Override
    public Boolean evaluate() throws Exception {
        throw new NoSuchElementException("Variable not found in assignment");
    }

    @Override
    public Boolean evaluate(Map<String, Boolean> assignment) throws Exception {
        if (!assignment.containsKey(this.name)) {
            this.evaluate();
        }

        return assignment.get(this.name);
    }

    // Returns a list of the variables in the expression.
    @Override
    public List<String> getVariables() {
        List<String> variables = new ArrayList<>();
        variables.add(this.name);
        return variables;
    }

    // Returns a nice string representation of the expression.
    @Override
    public String toString() {
        return this.name;
    }

    // Returns a new expression in which all occurrences of the variable
    // var are replaced with the provided expression (Does not modify the
    // current expression).
    @Override
    public Expression assign(String var, Expression expression) {
        List<String> variables = this.getVariables();
        if (!variables.contains(var)) {
            // nothing to replace
            return this;
        }

        return expression;
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
