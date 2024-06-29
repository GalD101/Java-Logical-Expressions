package src;

import src.expressions.Val;

import java.util.Map;

// TODO: Maybe also implement a new interface for getOperatorSymbol
public abstract class BaseExpression implements Expression {

    @Override
    public Boolean evaluate() throws Exception {
        Map<String, Boolean> assignment = new java.util.HashMap<>();
        return this.evaluate(assignment);
    }

    protected abstract String getOperatorSymbol();
}
