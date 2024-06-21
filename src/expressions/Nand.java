package src.expressions;

import src.BinaryExpression;
import src.Expression;

import java.util.List;
import java.util.Map;

public class Nand extends BinaryExpression implements Expression {
    private final static String symbol = "A";

    public Nand(Expression left, Expression right) {
        super(left, right);
    }

    @Override
    public Boolean evaluate(Map<String, Boolean> assignment) throws Exception {
        return new Not(new And(this.getLeft(), this.getRight())).evaluate(assignment);
    }

    @Override
    public Boolean evaluate() throws Exception {
        return new Not(new And(this.getLeft(), this.getRight())).evaluate();
    }

    @Override
    public Expression assign(String var, Expression expression) {
        List<String> variables = this.getVariables();
        if (!variables.contains(var)) { // nothing to replace - breaks the recursion TODO: I think this is redundant so I only need to do it once because I think I already check this in the other implementations
            return new Not(new And(this.getLeft(), this.getRight())); // Breaks the recursion
        }

        return new Not(new And(this.getLeft().assign(var, expression), this.getRight().assign(var, expression)));
    }

    @Override
    protected String getOperatorSymbol() {
        return symbol;
    }

    @Override
    public Expression nandify() {
        return new Nand(this.getLeft().nandify(), this.getRight().nandify());
    }

    @Override
    public Expression norify() {
        // NAND(A, B) = NOR[NOR(NOR(A, A), NOR(B, B)), NOR(NOR(A, A), NOR(B, B))]
        return new Nor
                (new Nor
                        (new Nor(this.getLeft().norify(), this.getLeft().norify()), new Nor(this.getRight().norify(), this.getRight().norify()))
                        , new Nor
                        (new Nor(this.getLeft().norify(), this.getLeft().norify()), new Nor(this.getRight().norify(), this.getRight().norify())));
    }
}
