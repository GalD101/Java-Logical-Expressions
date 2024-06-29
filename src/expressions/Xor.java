package src.expressions;

import src.BinaryExpression;
import src.Expression;

import java.util.Map;

public class Xor extends BinaryExpression implements Expression {
    private final static String symbol = "^";

    public Xor(Expression left, Expression right) {
        super(left, right);
    }

    @Override
    public Boolean evaluateOperation(Boolean leftEvaluation, Boolean rightEvaluation) {
        return leftEvaluation ^ rightEvaluation;
    }

    @Override
    protected BinaryExpression createNewInstance(Expression left, Expression right) {
        return new Xor(left, right);
    }

    @Override
    protected String getOperatorSymbol() {
        return symbol;
    }

    @Override
    public Expression nandify() {
        // XOR(A, B) = NAND(NAND(A, NAND(A, B)), NAND(B, NAND(A, B))
        return new Nand
                (new Nand(this.getLeft().nandify(), new Nand(this.getLeft().nandify(), this.getRight().nandify()))
                        , new Nand(this.getRight().nandify(), new Nand(this.getLeft().nandify(), this.getRight().nandify())));
    }

    @Override
    public Expression norify() {
        // XOR(A, B) = NOR[NOR( NOR(A, A), NOR(B, B) ), NOR(A, B) ]
        return new Nor
                (new Nor
                        (new Nor(this.getLeft().norify(), this.getLeft().norify()), new Nor(this.getRight().norify(), this.getRight().norify()))
                        , new Nor(this.getLeft().norify(), this.getRight().norify()));
    }

    @Override
    public Expression simplify() {
        Expression simplifiedLeft = this.getLeft().simplify();
        Expression simplifiedRight = this.getRight().simplify();

        if (simplifiedLeft.toString().equals("T")) {
            return new Not(simplifiedRight);
        }
        if (simplifiedRight.toString().equals("T")) {
            return new Not(simplifiedLeft);
        }
        if (simplifiedLeft.toString().equals("F")) {
            return simplifiedRight;
        }
        if (simplifiedRight.toString().equals("F")) {
            return simplifiedLeft;
        }
        if (simplifiedLeft.toString().equals(simplifiedRight.toString())) {
            return new Val(false);
        }

        return new Xor(simplifiedLeft, simplifiedRight);
    }
}
