package src;
import org.junit.Test;
import src.expressions.*;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.*;

public class ExpressionsGPTTest {

    @Test
    public void testAndOperation() throws Exception {
        Expression expr = new And(new Val(true), new Val(false));
        assertFalse(expr.evaluate());
    }

    @Test
    public void testOrOperation() throws Exception {
        Expression expr = new Or(new Val(false), new Val(false));
        assertFalse(expr.evaluate());
        expr = new Or(new Val(false), new Val(true));
        assertTrue(expr.evaluate());
    }

    @Test
    public void testNotOperation() throws Exception {
        Expression expr = new Not(new Val(true));
        assertFalse(expr.evaluate());
        expr = new Not(new Val(false));
        assertTrue(expr.evaluate());
    }

    @Test
    public void testComplexExpression() throws Exception {
        Expression expr = new And(new Or(new Val(true), new Val(false)), new Not(new Val(false)));
        assertTrue(expr.evaluate());
    }

    @Test
    public void testVariableExpression() throws Exception {
        Expression expr = new Var("x");
        Map<String, Boolean> assignment = new HashMap<>();
        assignment.put("x", true);
        assertTrue(expr.evaluate(assignment));
        assignment.put("x", false);
        assertFalse(expr.evaluate(assignment));
    }

    @Test
    public void testNestedExpression() throws Exception {
        Expression expr = new And(
                new Or(new Val(true), new Not(new Var("x"))),
                new Xor(new Val(true), new Var("y"))
        );
        Map<String, Boolean> assignment = new HashMap<>();
        assignment.put("x", false);
        assignment.put("y", false);
        assertTrue(expr.evaluate(assignment));
        assignment.put("x", true);
        assignment.put("y", true);
        assertFalse(expr.evaluate(assignment));
    }

    @Test
    public void testInvalidExpression() {
        Expression expr = new And(new Val(true), null);
        assertThrows(NullPointerException.class, () -> {
            expr.evaluate();
        });
    }
}
