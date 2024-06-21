package src.expressions;

import src.BinaryExpression;
import src.Expression;

import java.util.List;
import java.util.Map;

public class Xnor extends BinaryExpression implements Expression {
    private final static String symbol = "#";

    public Xnor(Expression left, Expression right) {
        super(left, right);
    }

    @Override
    public Boolean evaluate() throws Exception {
        return !(this.getLeft().evaluate() ^ this.getRight().evaluate());
    }

    @Override
    public Boolean evaluate(Map<String, Boolean> assignment) throws Exception {
        return !(this.getLeft().evaluate(assignment) ^ this.getRight().evaluate(assignment));
    }

    @Override
    public Expression assign(String var, Expression expression) {
        List<String> variables = this.getVariables();
        if (!variables.contains(var)) { // nothing to replace - breaks the recursion TODO: I think this is redundant so I only need to do it once because I think I already check this in the other implementations
            return new Xnor(this.getLeft(), this.getRight()); // Breaks the recursion
        }

        return new Xnor(this.getLeft().assign(var, expression), this.getRight().assign(var, expression));
    }

    @Override
    protected String getOperatorSymbol() {
        return symbol;
    }

    @Override
    public Expression nandify() {
        // XNOR(A, B) = NAND[ NAND( NAND(A, A), NAND(B, B) ), ( NAND(A, B) ) ]
        return new Nand
                (new Nand
                        (new Nand(this.getLeft(), this.getLeft()), new Nand(this.getRight(), this.getRight()))
                        , new Nand(this.getLeft(), this.getRight()));
    }

    @Override
    public Expression norify() {
        // NAND(A, B) = NOR[NOR( A, NOR(A, B) ), NOR( B, NOR(A, B) )]
        return new Nor
                (new Nor(this.getLeft(), new Nor(this.getLeft(), this.getRight()))
                        , new Nor(this.getRight(), new Nor(this.getLeft(), this.getRight())));
    }
}
