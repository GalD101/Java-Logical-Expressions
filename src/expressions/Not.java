package src.expressions;

import src.Expression;
import src.UnaryExpression;

import java.util.Map;

public class Not extends UnaryExpression implements Expression {
    private final static String symbol = "~";

    public Not(Expression expression) {
        super(expression);
    }

    @Override
    public Boolean evaluate(Map<String, Boolean> assignment) throws Exception {
        return !this.getExpression().evaluate(assignment);
    }

    @Override
    public Expression assign(String var, Expression expression) {
        return new Not(this.getExpression().assign(var, expression));
    }

    @Override
    protected String getOperatorSymbol() {
        return symbol;
    }

    @Override
    public String toString() {
        if ('(' == (this.getExpression().toString().charAt(0))) {
            return symbol + this.getExpression().toString();
        }
        return symbol + "(" + this.getExpression().toString() + ")";
    }

    @Override
    public Expression nandify() {
        // NOT(X) is equivalent to NAND(X, X)
        return new Nand(this.getExpression().nandify(), this.getExpression().nandify());
    }

    @Override
    public Expression norify() {
        // NOT(A) = NOR(A, A)
        return new Nor(this.getExpression().norify(), this.getExpression().norify());
    }

    @Override
    public Expression simplify() {
        Expression simplifiedExpression = this.getExpression().simplify();
        return new Not(simplifiedExpression);
    }

    @Override
    protected Expression createNewInstance(Expression expression) {
        return new Not(expression);
    }
}
