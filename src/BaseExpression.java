package src;

import java.util.List;

// TODO: Maybe also implement a new interface for getOperatorSymbol
public abstract class BaseExpression implements Expression {

//    @Override
//    public Expression assign(String var, Expression expression) {
//        // TODO: Here I need to take the "this" objects (baseExpression)
//        // and replace each occurrence of var in "this" with the expression
//        // Steps:
//        // 1. Get the variables of the current expression (this)
//        // 2. Check if the var is in the variables list
//        // 2.1 if it is not, return the current expression
//        // 2.2 if it is (else), replace the var with the expression and return
//
//        // generics!
//        List<String> variables = this.getVariables();
//        if (!variables.contains(var)) { // nothing to replace
//            return this;
//        }
//        // we need to replace all occurrences of var with the given expression in the current expression (this)
//
//        return this.replace(var, expression);
//    }

    // TODO: Maybe remove this?
    protected abstract String getOperatorSymbol();
}
