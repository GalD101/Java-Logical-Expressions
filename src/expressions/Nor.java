package src.expressions;

import src.BinaryExpression;
import src.Expression;

import java.util.List;
import java.util.Map;

public class Nor extends BinaryExpression implements Expression {
    private final static String symbol = "V";

    public Nor(Expression left, Expression right) {
        super(left, right);
    }

    @Override
    public Boolean evaluate(Map<String, Boolean> assignment) throws Exception {
        return new Not(new Or(this.getLeft(), this.getRight())).evaluate(assignment);
    }

    @Override
    public Boolean evaluate() throws Exception {
        return new Not(new Or(this.getLeft(), this.getRight())).evaluate();
    }

    @Override
    public Expression assign(String var, Expression expression) {
        List<String> variables = this.getVariables();
        if (!variables.contains(var)) { // nothing to replace - breaks the recursion TODO: I think this is redundant so I only need to do it once because I think I already check this in the other implementations
            return new Not(new Or(this.getLeft(), this.getRight())); // Breaks the recursion
        }

        return new Not(new Or(this.getLeft().assign(var, expression), this.getRight().assign(var, expression)));
    }

    @Override
    protected String getOperatorSymbol() {
        return symbol;
    }

    @Override
    public Expression nandify() {
        // NOR(A, B) = NAND[ NAND( NAND(A, A), NAND(B, B) ) , NAND(NAND(A, A) , NAND(B, B) ) ]
        return new Nand
                (new Nand
                        (new Nand(this.getLeft(), this.getLeft()), new Nand(this.getRight(), this.getRight()))
                        , new Nand
                        (new Nand(this.getLeft(), this.getLeft()), new Nand(this.getRight(), this.getRight())));
    }

    @Override
    public Expression norify() {
        return new Nor(this.getLeft().norify(), this.getRight().norify());
    }
}
