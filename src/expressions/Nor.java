package src.expressions;

import src.BinaryExpression;
import src.Expression;

/**
 * The Nor class extends the BinaryExpression class and implements the Expression interface.
 * It represents a logical NOR operation in the form of an expression.
 * The symbol for the NOR operation is represented by the "V" character.
 */
public class Nor extends BinaryExpression implements Expression {
    /**
     * Constructs a new Nor object.
     * This constructor initializes a new Nor object with the given left and right Expressions.
     * The Nor object represents a logical NOR operation between the two expressions.
     *
     * @param left  The left operand of the NOR operation.
     * @param right The right operand of the NOR operation.
     */
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
        return "V";
    }

    @Override
    public Expression nandify() {
        // NOR(A, B) = NAND[ NAND( NAND(A, A), NAND(B, B) ) , NAND(NAND(A, A) , NAND(B, B) ) ]
        return new Nand(
                new Nand(
                        new Nand(this.getLeft().nandify(), this.getLeft().nandify()),
                        new Nand(this.getRight().nandify(), this.getRight().nandify())),
                new Nand(
                        new Nand(this.getLeft().nandify(), this.getLeft().nandify()),
                        new Nand(this.getRight().nandify(), this.getRight().nandify())));
    }

    @Override
    public Expression norify() {
        return new Nor(this.getLeft().norify(), this.getRight().norify());
    }

    @Override
    protected Expression performSimplification() {
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
