package src.expressions;

import org.junit.Test;
import src.Expression;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.*;

public class OrTest {

    @Test
    public void testEvaluateTrue() throws Exception {
        Map<String, Boolean> assignment = new HashMap<>();
        assignment.put("x", true);
        assignment.put("y", false);

        Or orExpr = new Or(new Var("x"), new Var("y"));
        assertTrue(orExpr.evaluate(assignment));
    }

    @Test
    public void testEvaluateFalse() throws Exception {
        Map<String, Boolean> assignment = new HashMap<>();
        assignment.put("x", false);
        assignment.put("y", false);

        Or orExpr = new Or(new Var("x"), new Var("y"));
        assertFalse(orExpr.evaluate(assignment));
    }

    @Test
    public void testSimplifyWithTrue() {
        Or orExpr = new Or(new Val(true), new Var("x"));
        assertEquals(new Val(true).toString(), orExpr.simplify().toString());
    }

    @Test
    public void testSimplifyWithFalse() {
        Or orExpr = new Or(new Val(false), new Var("x"));
        assertEquals(new Var("x").toString(), orExpr.simplify().toString());
    }

    @Test
    public void testSimplifySameExpressions() {
        Or orExpr = new Or(new Var("x"), new Var("x"));
        assertEquals(new Var("x").toString(), orExpr.simplify().toString());
    }

    @Test
    public void testSimplifyNested() {
        Or orExpr = new Or(new Or(new Var("x"), new Val(false)), new Var("y"));
        assertEquals(new Or(new Var("x"), new Var("y")).toString(), orExpr.simplify().toString());
    }

    @Test
    public void testEvaluateThrowsException() {
        Or orExpr = new Or(new Var("x"), new Var("y"));
        Map<String, Boolean> assignment = new HashMap<>();

        assertThrows(Exception.class, () -> {
            orExpr.evaluate(assignment);
        });
    }

    @Test
    public void testToString() {
        Or orExpr = new Or(new Var("x"), new Var("y"));
        assertEquals("(x | y)", orExpr.toString());
    }

    @Test
    public void testToStringNested() {
        Or orExpr = new Or(new And(new Var("x"), new Var("y")), new Var("z"));
        assertEquals("((x & y) | z)", orExpr.toString());
    }

    @Test
    public void testEvaluateNested() throws Exception {
        Map<String, Boolean> assignment = new HashMap<>();
        assignment.put("x", true);
        assignment.put("y", false);
        assignment.put("z", false);

        Or orExpr = new Or(new And(new Var("x"), new Var("y")), new Var("z"));
        assertFalse(orExpr.evaluate(assignment));
    }

    @Test
    public void testSimplifyComplexExpression() {
        Or orExpr = new Or(new And(new Val(true), new Var("x")), new Or(new Val(false), new Var("y")));
        assertEquals(new Or(new Var("x"), new Var("y")).toString(), orExpr.simplify().toString());
    }

    @Test
    public void testOrSimplify() {
        Expression expr = new Or(new Or(new Var("x"), new Var("y")), new Or(new Var("x"), new Var("y")));
        assertEquals("(x | y)", expr.simplify().toString());

        Expression expr2 = new Or(new Or(new Var("x"), new Var("y")), new Val(false));
        assertEquals("(x | y)", expr2.simplify().toString());
    }
}
