package src.expressions;

import src.BinaryExpression;
import src.Expression;

/**
 * The And class extends the BinaryExpression class and implements the Expression interface.
 * It represents a logical AND operation in the form of an expression.
 * The symbol for the AND operation is represented by the "&" character.
 */
public class And extends BinaryExpression implements Expression {

    /**
     * Constructs a new And object.
     * This constructor initializes a new And object with the given left and right Expressions.
     * The And object represents a logical AND operation between the two expressions.
     *
     * @param left  The left operand of the AND operation.
     * @param right The right operand of the AND operation.
     */
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
        return "&";
    }

    @Override
    public Expression nandify() {
        // AND(A,B) = NAND(NAND(A,B),NAND(A,B))
        return new Nand(new Nand(this.getLeft().nandify(), this.getRight().nandify()),
                new Nand(this.getLeft().nandify(), this.getRight().nandify()));
    }

    @Override
    public Expression norify() {
        // AND(A, B) = NOR(NOR(A, A), NOR(B, B))
        return new Nor(
                new Nor(this.getLeft().norify(), this.getLeft().norify()),
                new Nor(this.getRight().norify(), this.getRight().norify()));
    }

    @Override
    protected Expression performSimplification() {
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
