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
    public Boolean evaluate(Map<String, Boolean> assignment) throws Exception {
        if (!assignment.containsKey(this.name)) {
            throw new NoSuchElementException("Variable not found in assignment");// this.evaluate(); // TODO: Check this
        }

        return assignment.get(this.name); // Breaks recursion
    }

    @Override
    public Boolean evaluate() throws Exception {
        throw new Exception("Variable not found in assignment");
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
        // TODO: Here I need to take the "this" objects (baseExpression)
        // and replace each occurrence of var in "this" with the expression
        // Steps:
        // 1. Get the variables of the current expression (this)
        // 2. Check if the var is in the variables list
        // 2.1 if it is not, return the current expression
        // 2.2 if it is (else), replace the var with the expression and return

        List<String> variables = this.getVariables();
        if (!variables.contains(var)) { // nothing to replace
            return this;
        }
        // we need to replace all occurrences of var with the given expression in the current expression (this)
        // In this class, we only have one var so in order to replace this one var by the expression I will simply return the expression
        // You can think of it as one of the base cases of the recursion
        return expression;
    }
//
//    public Expression replace(String var, Expression expression) {
//        // Here I need to take the "this" object which is a variable
//        // and then replace the var with the expression
//        // Here I just create a new var since, let's suppose
//        // The current (this) var is "x" and the var is "y"
//        // So in order to replace I just delete x and create y
//        // and return it - this is the var class and I already checked in the base class if the var is in the list
//        // I can just return the new var. TODO: check if I need to check this again to not be dependent of base class (I think not, that is the point of inheritance)
//        return new Var(var);
//    }
}
