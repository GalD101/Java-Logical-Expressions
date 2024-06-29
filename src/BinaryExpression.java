package src;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * The BinaryExpression class is an abstract class that extends BaseExpression and implements the Expression interface.
 * It represents a binary operation in the form of an expression.
 * The binary operation is performed on two Expressions, which are stored as the left and right fields of this class.
 * The BinaryExpression class provides methods to get the left and right Expressions, evaluate the binary operation,
 * assign a value to a variable in the Expressions,
 * get the variables in the Expressions, and convert the operation to a string.
 * It also provides abstract methods to create a new instance of a BinaryExpression and evaluate the binary operation.
 */
public abstract class BinaryExpression extends BaseExpression implements Expression {
    private Expression left;
    private Expression right;

    /**
     * Constructs a new BinaryExpression object.
     * This constructor initializes a new BinaryExpression object with the given left and right Expressions.
     * The left and right Expressions represent the operands of the binary operation.
     *
     * @param left  The left Expression of the binary operation. It is an instance of the Expression interface.
     * @param right The right Expression of the binary operation. It is an instance of the Expression interface.
     */
    public BinaryExpression(Expression left, Expression right) {
        this.left = left;
        this.right = right;
    }

    /**
     * Returns the left Expression of the binary operation.
     * This method is used to retrieve the left operand of the binary operation.
     *
     * @return The left Expression of the binary operation. It is an instance of the Expression interface.
     */
    protected Expression getLeft() {
        return this.left;
    }

    /**
     * Returns the right Expression of the binary operation.
     * This method is used to retrieve the right operand of the binary operation.
     *
     * @return The right Expression of the binary operation. It is an instance of the Expression interface.
     */
    protected Expression getRight() {
        return this.right;
    }

    @Override
    public Boolean evaluate(Map<String, Boolean> assignment) throws Exception {
        // In order to avoid short-circuiting, we need to evaluate both sides
        Boolean leftEvaluation = this.getLeft().evaluate(assignment);
        Boolean rightEvaluation = this.getRight().evaluate(assignment);
        return this.evaluateOperation(leftEvaluation, rightEvaluation);
    }

    @Override
    public Expression assign(String var, Expression expression) {
        List<String> variables = this.getVariables();
        if (!variables.contains(var)) {
            // nothing to replace
            return this.createNewInstance(this.getLeft(), this.getRight());
        }

        // Replace the var with the expression
        return this.createNewInstance(this.getLeft().assign(var, expression), this.getRight().assign(var, expression));
    }

    @Override
    public List<String> getVariables() {
        List<String> variables = new ArrayList<>();
        variables.addAll(this.left.getVariables());
        variables.addAll(this.right.getVariables());
        return variables;
    }

    @Override
    public String toString() {
        return "(" + this.left.toString() + " " + this.getOperatorSymbol() + " " + this.right.toString() + ")";
    }

    /**
     * Creates a new instance of a BinaryExpression.
     * This is an abstract method that must be implemented by subclasses of BinaryExpression.
     * The new instance should be of the same type as the subclass and
     * should have the given left and right Expressions as its operands.
     *
     * @param left  The left Expression of the binary operation. It is an instance of the Expression interface.
     * @param right The right Expression of the binary operation. It is an instance of the Expression interface.
     * @return A new instance of a BinaryExpression with the given left and right Expressions as its operands.
     */
    protected abstract BinaryExpression createNewInstance(Expression left, Expression right);

    /**
     * Evaluates the binary operation with the given left and right evaluations.
     * This is an abstract method that must be implemented by subclasses of BinaryExpression.
     * The left and right evaluations are the results of
     * evaluating the left and right Expressions of the binary operation.
     * The method should return the result of the binary operation as a Boolean.
     *
     * @param leftEvaluation  The result of evaluating the left Expression of the binary operation. It is a Boolean.
     * @param rightEvaluation The result of evaluating the right Expression of the binary operation. It is a Boolean.
     * @return The result of the binary operation as a Boolean.
     */
    protected abstract Boolean evaluateOperation(Boolean leftEvaluation, Boolean rightEvaluation);
}
