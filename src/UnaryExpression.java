package src;

import java.util.ArrayList;
import java.util.List;

public abstract class UnaryExpression extends BaseExpression {
    private Expression expression;

    public UnaryExpression(Expression expression) {
        this.expression = expression;
    }

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

    protected abstract Expression createNewInstance(Expression expression);
}
