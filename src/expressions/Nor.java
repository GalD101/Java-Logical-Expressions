package src.expressions;

import src.BinaryExpression;
import src.Expression;

import java.util.Map;

public class Nor extends BinaryExpression implements Expression {
    private final static String symbol = "V";

    public Nor(Expression left, Expression right) {
        super(left, right);
    }

    @Override
    public Boolean evaluateOperation(Boolean leftEvaluation, Boolean rightEvaluation) {
        return !(leftEvaluation || rightEvaluation);
    }

    @Override
    protected BinaryExpression createNewInstance(Expression left, Expression right) {
        return new Nor(left, right);
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
                        (new Nand(this.getLeft().nandify(), this.getLeft().nandify()), new Nand(this.getRight().nandify(), this.getRight().nandify()))
                        , new Nand
                        (new Nand(this.getLeft().nandify(), this.getLeft().nandify()), new Nand(this.getRight().nandify(), this.getRight().nandify())));
    }

    @Override
    public Expression norify() {
        return new Nor(this.getLeft().norify(), this.getRight().norify());
    }

    @Override
    public Expression simplify() {
        Expression simplifiedLeft = this.getLeft().simplify();
        Expression simplifiedRight = this.getRight().simplify();

        if (simplifiedLeft.toString().equals("T") || simplifiedRight.toString().equals("T")) {
            return new Val(false);
        }
        if (simplifiedLeft.toString().equals("F")) {
            return new Not(simplifiedRight);
        }
        if (simplifiedRight.toString().equals("F")) {
            return new Not(simplifiedLeft);
        }
        if (simplifiedLeft.toString().equals(simplifiedRight.toString())) {
            return new Not(simplifiedLeft);
        }

        return new Nor(simplifiedLeft, simplifiedRight);
    }
}
