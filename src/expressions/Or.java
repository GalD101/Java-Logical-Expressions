package src.expressions;

import src.BinaryExpression;
import src.Expression;

import java.util.List;
import java.util.Map;

public class Or extends BinaryExpression implements Expression {
    private final static String symbol = "∨";

    public Or(Expression left, Expression right) {
        super(left, right);
    }

    @Override
    public Boolean evaluate(Map<String, Boolean> assignment) throws Exception {
        if (assignment == null) throw new Exception("Null assignment");
//        if (!assignment.containsKey(this.getLeft().toString()) || !assignment.containsKey(this.getRight().toString())) throw new Exception("Missing assignment");
        // In order to avoid short-circuiting, we need to evaluate both sides
        Boolean leftEvaluation = this.getLeft().evaluate(assignment);
        Boolean rightEvaluation = this.getRight().evaluate(assignment);
        return leftEvaluation || rightEvaluation;
    }

    @Override
    public Boolean evaluate() throws Exception {
        return this.getLeft().evaluate() || this.getRight().evaluate();
    }

    @Override
    public Expression assign(String var, Expression expression) {
        List<String> variables = this.getVariables();
        if (!variables.contains(var)) { // nothing to replace - breaks the recursion TODO: I think this is redundant so I only need to do it once because I think I already check this in the other implementations
            return new Or(this.getLeft(), this.getRight()); // Breaks the recursion
        }

        // TODO: Maybe needs to change because maybe it will cause infinite recursion
        return new Or(this.getLeft().assign(var, expression), this.getRight().assign(var, expression));
    }

    @Override
    protected String getOperatorSymbol() {
        return symbol;
    }
}
