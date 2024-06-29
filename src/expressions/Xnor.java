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
    public Boolean evaluateOperation(Boolean leftEvaluation, Boolean rightEvaluation) {
        return !(leftEvaluation ^ rightEvaluation);
    }

    @Override
    protected BinaryExpression createNewInstance(Expression left, Expression right) {
        return new Xnor(left, right);
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
                        (new Nand(this.getLeft().nandify(), this.getLeft().nandify()), new Nand(this.getRight().nandify(), this.getRight().nandify()))
                        , new Nand(this.getLeft().nandify(), this.getRight().nandify()));
    }

    @Override
    public Expression norify() {
        // NAND(A, B) = NOR[NOR( A, NOR(A, B) ), NOR( B, NOR(A, B) )]
        return new Nor
                (new Nor(this.getLeft().norify(), new Nor(this.getLeft().norify(), this.getRight().norify()))
                        , new Nor(this.getRight().norify(), new Nor(this.getLeft().norify(), this.getRight().norify())));
    }

    @Override
    public Expression simplify() {
        // TODO: This is something all the simplify functions should do
        if (this.getVariables().isEmpty()) {
            try {
                return new Val(this.evaluate());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        Expression simplifiedLeft = this.getLeft().simplify();
        Expression simplifiedRight = this.getRight().simplify();

        if (simplifiedLeft.toString().equals(simplifiedRight.toString())) {
            return simplifiedLeft;
        }

        return new Xnor(simplifiedLeft, simplifiedRight);
    }
}
