package src.expressions;

import src.BinaryExpression;
import src.Expression;

/**
 * The Xnor class extends the BinaryExpression class and implements the Expression interface.
 * It represents an XNOR operation in the form of an expression.
 * The XNOR operation is performed on two Expressions, which are stored in the BinaryExpression superclass.
 * The Xnor class provides methods to evaluate the XNOR operation, create a new instance of the Xnor class,
 * get the operator symbol, and convert the operation to NAND, NOR, and simplified forms.
 * The operator symbol for the XNOR operation is "#".
 */
public class Xnor extends BinaryExpression implements Expression {

    /**
     * Constructs a new Xnor object.
     * This constructor initializes a new Xnor object with the given left and right Expressions.
     * It uses the super constructor of the BinaryExpression class to initialize the left and right Expressions.
     *
     * @param left  The left Expression of the Xnor operation. It is an instance of the Expression interface.
     * @param right The right Expression of the Xnor operation. It is an instance of the Expression interface.
     */
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
        return "#";
    }

    @Override
    public Expression nandify() {
        // XNOR(A, B) = NAND[ NAND( NAND(A, A), NAND(B, B) ), ( NAND(A, B) ) ]
        return new Nand(
                new Nand(
                        new Nand(this.getLeft().nandify(), this.getLeft().nandify()),
                        new Nand(this.getRight().nandify(), this.getRight().nandify())),
                new Nand(this.getLeft().nandify(), this.getRight().nandify()));
    }

    @Override
    public Expression norify() {
        // NAND(A, B) = NOR[NOR( A, NOR(A, B) ), NOR( B, NOR(A, B) )]
        return new Nor(
                new Nor(
                        this.getLeft().norify(), new Nor(this.getLeft().norify(), this.getRight().norify())),
                new Nor(
                        this.getRight().norify(), new Nor(this.getLeft().norify(), this.getRight().norify())));
    }

    @Override
    protected Expression performSimplification() {
        Expression simplifiedLeft = this.getLeft().simplify();
        Expression simplifiedRight = this.getRight().simplify();

        if (simplifiedLeft.toString().equals(simplifiedRight.toString())) {
            return simplifiedLeft;
        }

        return new Xnor(simplifiedLeft, simplifiedRight);
    }
}
