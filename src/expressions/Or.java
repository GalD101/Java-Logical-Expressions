package src.expressions;

import src.BinaryExpression;
import src.Expression;

/**
 * The Or class extends the BinaryExpression class and implements the Expression interface.
 * It represents a logical OR operation in the form of an expression.
 * The symbol for the OR operation is represented by the "|" character.
 */
public class Or extends BinaryExpression implements Expression {
    /**
     * Constructs a new Or object.
     * This constructor initializes a new Or object with the given left and right Expressions.
     * The Or object represents a logical OR operation between the two expressions.
     *
     * @param left  The left operand of the OR operation.
     * @param right The right operand of the OR operation.
     */
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
        return "|";
    }

    @Override
    public Expression nandify() {
        // OR(A, B) = NAND(NAND(A, A), NAND(B, B))
        return new Nand(
                new Nand(this.getLeft().nandify(), this.getLeft().nandify()),
                new Nand(this.getRight().nandify(), this.getRight().nandify()));
    }

    @Override
    public Expression norify() {
        // OR(A, B) = NOR(NOR(A, B), NOR(A, B))
        return new Nor(
                new Nor(this.getLeft().norify(), this.getRight().norify()),
                new Nor(this.getLeft().norify(), this.getRight().norify()));
    }

    @Override
    protected Expression performSimplification() {
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
