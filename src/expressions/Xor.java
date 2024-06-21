package src.expressions;

import src.BinaryExpression;
import src.Expression;

import java.util.List;
import java.util.Map;

public class Xor extends BinaryExpression implements Expression {
    private final static String symbol = "⊕";

    public Xor(Expression left, Expression right) {
        super(left, right);
    }

    @Override
    public Boolean evaluate() throws Exception {
        return this.getLeft().evaluate() ^ this.getRight().evaluate();
    }

    @Override
    public Boolean evaluate(Map<String, Boolean> assignment) throws Exception {
        return this.getLeft().evaluate(assignment) ^ this.getRight().evaluate(assignment);
    }

    @Override
    public Expression assign(String var, Expression expression) {
        List<String> variables = this.getVariables();
        if (!variables.contains(var)) { // nothing to replace - breaks the recursion TODO: I think this is redundant so I only need to do it once because I think I already check this in the other implementations
            return new Xor(this.getLeft(), this.getRight()); // Breaks the recursion
        }

        return new Xor(this.getLeft().assign(var, expression), this.getRight().assign(var, expression));
    }

    @Override
    protected String getOperatorSymbol() {
        return symbol;
    }

    @Override
    public Expression nandify() {
        // XOR(A, B) = NAND(NAND(A, NAND(A, B)), NAND(B, NAND(A, B))
        return new Nand
                (new Nand(this.getLeft(), new Nand(this.getLeft(), this.getRight()))
                        , new Nand(this.getRight(), new Nand(this.getLeft(), this.getRight())));
    }

    @Override
    public Expression norify() {
        // XOR(A, B) = NOR[NOR( NOR(A, A), NOR(B, B) ), NOR(A, B) ]
        return new Nor
                (new Nor
                        (new Nor(this.getLeft(), this.getLeft()), new Nor(this.getRight(), this.getRight()))
                        , new Nor(this.getLeft(), this.getRight()));
    }
}
