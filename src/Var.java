package src;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

public class Var implements Expression {
    private String name;

    public Var(String name) {
        this.name = name;
    }

    // Evaluate the expression using the variable values provided
    // in the assignment, and return the result. If the expression
    // contains a variable which is not in the assignment, an exception
    // is thrown.
    @Override
    public Boolean evaluate(Map<String, Boolean> assignment) throws Exception {
        if (!assignment.containsKey(this.name)) {
            throw new NoSuchElementException("Variable not found in assignment");// this.evaluate(); // TODO: Check this
        }

        return assignment.get(this.name);
    }

    // A convenience method. Like the `evaluate(assignment)` method above,
    // but uses an empty assignment.
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
        if (var.equals(this.name)) {
            return expression;
        }

        return this;
    }
}
