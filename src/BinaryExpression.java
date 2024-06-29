package src;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public abstract class BinaryExpression extends BaseExpression implements Expression {
    private Expression left;
    private Expression right;

    public BinaryExpression(Expression left, Expression right) {
        this.left = left;
        this.right = right;
    }

    public Expression getLeft() {
        return this.left;
    }

    public Expression getRight() {
        return this.right;
    }

    @Override
    public Boolean evaluate(Map<String, Boolean> assignment) throws Exception {
        // In order to avoid short-circuiting, we need to evaluate both sides
        Boolean leftEvaluation = this.getLeft().evaluate(assignment);
        Boolean rightEvaluation = this.getRight().evaluate(assignment);
        return this.evaluateOperation(leftEvaluation, rightEvaluation);
    }

    @Override
    public Expression assign(String var, Expression expression) {
        List<String> variables = this.getVariables();
        if (!variables.contains(var)) {
            // nothing to replace
            return this.createNewInstance(this.getLeft(), this.getRight());
        }

        // Replace the var with the expression
        return this.createNewInstance(this.getLeft().assign(var, expression), this.getRight().assign(var, expression));
    }

    @Override
    public List<String> getVariables() {
        List<String> variables = new ArrayList<>();
        variables.addAll(this.left.getVariables());
        variables.addAll(this.right.getVariables());
        return variables;
    }

    @Override
    public String toString() {
        return "(" + this.left.toString() + " " + this.getOperatorSymbol() + " " + this.right.toString() + ")";
    }

    protected abstract BinaryExpression createNewInstance(Expression left, Expression right);

    protected abstract Boolean evaluateOperation(Boolean leftEvaluation, Boolean rightEvaluation);
}
