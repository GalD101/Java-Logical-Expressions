package src;

import java.util.ArrayList;
import java.util.List;

/**
 * The UnaryExpression abstract class that extends the BaseExpression class.
 * This class represents a unary operation in a logical expression.
 * A unary operation is an operation with only one operand.
 * The operand is an instance of the Expression interface.
 * The UnaryExpression class provides methods for getting the variables of the expression,
 * assigning a value to a variable in the expression,
 * and creating a new instance of the expression with a given operand.
 */
public abstract class UnaryExpression extends BaseExpression {
    private Expression expression;

    /**
     * Constructor for the UnaryExpression class.
     * This constructor initializes a new instance of UnaryExpression with the given Expression as its operand.
     *
     * @param expression The Expression that is the operand of the unary operation.
     *                   It is an instance of the Expression interface.
     */
    public UnaryExpression(Expression expression) {
        this.expression = expression;
    }

    /**
     * Retrieves the Expression that is the operand of the unary operation.
     * This method returns the Expression that was passed to the constructor when the UnaryExpression was created.
     *
     * @return The Expression that is the operand of the unary operation. It is an instance of the Expression interface.
     */
    protected Expression getExpression() {
        return this.expression;
    }

    @Override
    public List<String> getVariables() {
        List<String> variables = new ArrayList<>();
        variables.addAll(this.expression.getVariables());
        return variables;
    }

    @Override
    public Expression assign(String var, Expression expression) {
        List<String> variables = this.getVariables();
        if (!variables.contains(var)) {
            // nothing to replace
            return this.createNewInstance(this.getExpression());
        }

        // Replace the var with the expression
        return this.createNewInstance(this.getExpression().assign(var, expression));
    }

    /**
     * Abstract method to create a new instance of the UnaryExpression.
     * This method is intended to be overridden by subclasses of UnaryExpression.
     * It is used in the assign method to create a new instance of the UnaryExpression with a given operand.
     *
     * @param expression The Expression that is the operand of the unary operation.
     *                   It is an instance of the Expression interface.
     * @return A new instance of the UnaryExpression with the given operand.
     */
    protected abstract Expression createNewInstance(Expression expression);
}
