package src.expressions;

import src.BinaryExpression;
import src.Expression;

import java.util.List;
import java.util.Map;

public class Or extends BinaryExpression implements Expression {
    private final static String symbol = "|";

    public Or(Expression left, Expression right) {
        super(left, right);
    }

    @Override
    public Boolean evaluateOperation(Boolean leftEvaluation, Boolean rightEvaluation) {
        return leftEvaluation || rightEvaluation;
    }

    @Override
    protected BinaryExpression createNewInstance(Expression left, Expression right) {
        return new Or(left, right);
    }

    @Override
    protected String getOperatorSymbol() {
        return symbol;
    }

    @Override
    public Expression nandify() {
        // OR(A, B) = NAND(NAND(A, A), NAND(B, B))
        return new Nand(new Nand(this.getLeft().nandify(), this.getLeft().nandify()), new Nand(this.getRight().nandify(), this.getRight().nandify()));
    }

    @Override
    public Expression norify() {
        // OR(A, B) = NOR(NOR(A, B), NOR(A, B))
        return new Nor
                (new Nor(this.getLeft().norify(), this.getRight().norify())
                        , new Nor(this.getLeft().norify(), this.getRight().norify()));
    }

    @Override
    public Expression simplify() {
        Expression simplifiedLeft = this.getLeft().simplify();
        Expression simplifiedRight = this.getRight().simplify();

        if (simplifiedLeft.toString().equals("T") || simplifiedRight.toString().equals("T")) {
            return new Val(true);
        }
        if (simplifiedLeft.toString().equals("F")) {
            return simplifiedRight;
        }
        if (simplifiedRight.toString().equals("F")) {
            return simplifiedLeft;
        }
        if (simplifiedLeft.toString().equals(simplifiedRight.toString())) {
            return simplifiedLeft;
        }

        return new Or(simplifiedLeft, simplifiedRight);
    }
}
