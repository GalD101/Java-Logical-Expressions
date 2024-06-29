package src.expressions;

import src.BinaryExpression;
import src.Expression;

public class And extends BinaryExpression implements Expression {
    private final static String symbol = "&";

    public And(Expression left, Expression right) {
        super(left, right);
    }

    @Override
    public Boolean evaluateOperation(Boolean leftEvaluation, Boolean rightEvaluation) {
        return leftEvaluation && rightEvaluation;
    }

    @Override
    protected BinaryExpression createNewInstance(Expression left, Expression right) {
        return new And(left, right);
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

    @Override
    public Expression simplify() {
        Expression simplifiedLeft = this.getLeft().simplify();
        Expression simplifiedRight = this.getRight().simplify();

        if (simplifiedLeft.toString().equals("F") || simplifiedRight.toString().equals("F")) {
            return new Val(false);
        }
        if (simplifiedLeft.toString().equals("T")) {
            return simplifiedRight;
        }
        if (simplifiedRight.toString().equals("T")) {
            return simplifiedLeft;
        }
        if (simplifiedLeft.toString().equals(simplifiedRight.toString())) {
            return simplifiedLeft;
        }

        return new And(simplifiedLeft, simplifiedRight);
    }
}
