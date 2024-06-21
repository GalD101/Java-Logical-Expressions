package src;

import java.util.ArrayList;
import java.util.List;

public abstract class UnaryExpression extends BaseExpression {
    private Expression expression;

    public UnaryExpression(Expression expression) {
        this.expression = expression;
    }

    // TODO: Check this
    protected Expression getExpression() {
        return this.expression;
    }

    @Override
    public List<String> getVariables() {
        List<String> variables = new ArrayList<>();
        variables.addAll(this.expression.getVariables());
        return variables;
    }
}
