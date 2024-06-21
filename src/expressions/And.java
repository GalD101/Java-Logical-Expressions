package src.expressions;

import src.BinaryExpression;
import src.Expression;

import java.util.List;
import java.util.Map;

public class And extends BinaryExpression implements Expression {
    private final static String symbol = "∧";

    public And(Expression left, Expression right) {
        super(left, right);
    }

    @Override
    public Boolean evaluate(Map<String, Boolean> assignment) throws Exception {
        return this.getLeft().evaluate(assignment) && this.getRight().evaluate(assignment);
    }

    @Override
    public Boolean evaluate() throws Exception {
        return this.getLeft().evaluate() && this.getRight().evaluate();
    }

    @Override
    public Expression assign(String var, Expression expression) {
        // TODO: Here I need to take the "this" objects (baseExpression)
        // and replace each occurrence of var in "this" with the expression
        // Steps:
        // 1. Get the variables of the current expression (this)
        // 2. Check if the var is in the variables list
        // 2.1 if it is not, return the current expression
        // 2.2 if it is (else), replace the var with the expression and return

        // generics!
        List<String> variables = this.getVariables();
        if (!variables.contains(var)) { // nothing to replace - breaks the recursion TODO: I think this is redundant so I only need to do it once because I think I already check this in the other implementations
            return new And(this.getLeft(), this.getRight()); // Breaks the recursion
        }
        // we need to replace all occurrences of var with the given expression in the current expression (this)

        // TODO: Maybe needs to change because maybe it will cause infinite recursion
        return new And(this.getLeft().assign(var, expression), this.getRight().assign(var, expression));
    }

    @Override
    protected String getOperatorSymbol() {
        return symbol;
    }

    @Override
    public Expression nandify() {
        // AND(A,B) = NAND(NAND(A,B),NAND(A,B))
        return new Nand(new Nand(this.getLeft().nandify(), this.getRight().nandify()), new Nand(this.getLeft().nandify(), this.getRight().nandify()));
    }

    @Override
    public Expression norify() {
        // AND(A, B) = NOR(NOR(A, A), NOR(B, B))
        return new Nor(
                new Nor(this.getLeft().norify(), this.getLeft().norify()),
                new Nor(this.getRight().norify(), this.getRight().norify()));
    }
}
