package src.expressions;

import src.BinaryExpression;
import src.Expression;

public class Nand extends BinaryExpression implements Expression {
    private final static String symbol = "A";

    public Nand(Expression left, Expression right) {
        super(left, right);
    }

    @Override
    public Boolean evaluateOperation(Boolean leftEvaluation, Boolean rightEvaluation) {
        return !(leftEvaluation && rightEvaluation);
    }

    @Override
    protected BinaryExpression createNewInstance(Expression left, Expression right) {
        return new Nand(left, right);
    }

    @Override
    protected String getOperatorSymbol() {
        return symbol;
    }

    @Override
    public Expression nandify() {
        return new Nand(this.getLeft().nandify(), this.getRight().nandify());
    }

    @Override
    public Expression norify() {
        // NAND(A, B) = NOR[NOR(NOR(A, A), NOR(B, B)), NOR(NOR(A, A), NOR(B, B))]
        return new Nor
                (new Nor
                        (new Nor(this.getLeft().norify(), this.getLeft().norify()), new Nor(this.getRight().norify(), this.getRight().norify()))
                        , new Nor
                        (new Nor(this.getLeft().norify(), this.getLeft().norify()), new Nor(this.getRight().norify(), this.getRight().norify())));
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
        if (simplifiedLeft.toString().equals("F") || simplifiedRight.toString().equals("F")) {
            return new Val(true);
        }
        if (simplifiedLeft.toString().equals(simplifiedRight.toString())) {
            return new Not(simplifiedLeft);
        }

        return new Nand(simplifiedLeft, simplifiedRight);
    }
}
