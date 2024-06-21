package src;

import java.util.ArrayList;
import java.util.List;

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
}
