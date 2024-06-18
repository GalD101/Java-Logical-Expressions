package src;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Val implements Expression {
    private Boolean value;
    private String name;

    public Val(Boolean value) {
        this.value = value;
        this.name = value.toString();
    }

    // Evaluate the expression using the variable values provided
    // in the assignment, and return the result. If the expression
    // contains a variable which is not in the assignment, an exception
    // is thrown.
    public Boolean evaluate(Map<String, Boolean> assignment) throws Exception {
        if (!assignment.containsKey(this.name)) {
            this.evaluate(); // throw new Exception("Variable not found in assignment");
        }

        return assignment.get(this.name);
    }

    // A convenience method. Like the `evaluate(assignment)` method above,
    // but uses an empty assignment.
    public Boolean evaluate() throws Exception {
        throw new Exception("Variable not found in assignment");
    }

    // Returns a list of the variables in the expression.
    public List<String> getVariables() {
        ArrayList<String> variables = new ArrayList<String>();
        variables.add(this.value.toString());
        return new ArrayList<>();
    }

    // Returns a nice string representation of the expression.
    @Override
    public String toString() {
        if (this.value) {
            return "T";
        } else {
            return "F";
        }
    }

    // Returns a new expression in which all occurrences of the variable
    // var are replaced with the provided expression (Does not modify the
    // current expression).
    public Expression assign(String var, Expression expression) {
        if (var.equals(this.name)) {
            return expression;
        }

        return this;
    }
}
