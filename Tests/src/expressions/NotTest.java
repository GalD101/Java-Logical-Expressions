package src.expressions;

import org.junit.Test;
import src.Expression;
import src.expressions.Not;
import src.expressions.And;
import src.expressions.Or;
import src.expressions.Val;
import src.expressions.Var;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.*;

public class NotTest {

    @Test
    public void testEvaluateTrue() throws Exception {
        Map<String, Boolean> assignment = new HashMap<>();
        assignment.put("x", false);

        Not notExpr = new Not(new Var("x"));
        assertTrue(notExpr.evaluate(assignment));
    }

    @Test
    public void testEvaluateFalse() throws Exception {
        Map<String, Boolean> assignment = new HashMap<>();
        assignment.put("x", true);

        Not notExpr = new Not(new Var("x"));
        assertFalse(notExpr.evaluate(assignment));
    }

    @Test
    public void testSimplifyTrue() {
        Not notExpr = new Not(new Val(false));
        assertEquals(new Val(true).toString(), notExpr.simplify().toString());
    }

    @Test
    public void testSimplifyFalse() {
        Not notExpr = new Not(new Val(true));
        assertEquals(new Val(false).toString(), notExpr.simplify().toString());
    }

    @Test
    public void testSimplifyVar() {
        Not notExpr = new Not(new Var("x"));
        assertEquals(new Not(new Var("x")).toString(), notExpr.simplify().toString());
    }

    @Test
    public void testSimplifyNested() {
        Not notExpr = new Not(new And(new Val(true), new Var("x")));
        assertEquals(new Not(new Var("x")).toString(), notExpr.simplify().toString());
    }

    @Test
    public void testToString() {
        Not notExpr = new Not(new Var("x"));
        assertEquals("~(x)", notExpr.toString().toString());

        Not nestedNotExpr = new Not(new And(new Var("x"), new Var("y")));
        assertEquals("~((x & y))", nestedNotExpr.toString());
    }

    @Test
    public void testEvaluateThrowsException() {
        Not notExpr = new Not(new Var("x"));
        Map<String, Boolean> assignment = new HashMap<>();

        assertThrows(Exception.class, () -> {
            notExpr.evaluate(assignment);
        });
    }

    @Test
    public void testEvaluateNested() throws Exception {
        Map<String, Boolean> assignment = new HashMap<>();
        assignment.put("x", true);
        assignment.put("y", false);

        Not notExpr = new Not(new Or(new Var("x"), new Var("y")));
        assertFalse(notExpr.evaluate(assignment));
    }

    @Test
    public void testSimplifyDoubleNegation() {
        Not notExpr = new Not(new Not(new Var("x")));
        notExpr.simplify();
        assertEquals(new Not(new Not(new Var("x"))).toString(), notExpr.simplify().toString());

        Not notVal = new Not(new Not(new Val(true)));
        notVal.simplify();
        assertEquals("T", notVal.simplify().toString());
    }

    @Test
    public void testSimplifyComplexExpression() {
        Not notExpr = new Not(new And(new Val(false), new Or(new Val(true), new Var("x"))));
        notExpr.simplify();
        assertEquals(new Val(true).toString(), notExpr.simplify().toString());
    }

    @Test
    public void testToStringDoubleNegation() {
        Not notExpr = new Not(new Not(new Var("x")));
        assertEquals("~(~(x))", notExpr.toString());
    }

    @Test
    public void testToStringComplexExpression() {
        Not notExpr = new Not(new Or(new And(new Var("x"), new Var("y")), new Var("z")));
        assertEquals("~(((x & y) | z))", notExpr.toString());
    }

    @Test
    public void testSimplifyWithConstantExpressions() {
        Not notExpr = new Not(new And(new Val(true), new Val(false)));
        assertEquals(new Val(true).toString(), notExpr.simplify().toString());
    }

    @Test
    public void testNotWithTrue() throws Exception {
        Expression expr = new Not(new Val(true));
        assertFalse(expr.evaluate());
    }

    @Test
    public void testNotWithFalse() throws Exception {
        Expression expr = new Not(new Val(false));
        assertTrue(expr.evaluate());
    }

    @Test
    public void testNotWithVariable() throws Exception {
        Expression expr = new Not(new Var("x"));
        Map<String, Boolean> assignment = new HashMap<>();
        assignment.put("x", true);
        assertFalse(expr.evaluate(assignment));
        assignment.put("x", false);
        assertTrue(expr.evaluate(assignment));
    }

    @Test
    public void testNotWithNull() {
        Expression expr = new Not(null);
        assertThrows(NullPointerException.class, () -> {
            expr.evaluate();
        });
    }

    @Test
    public void testNotToString() {
        Expression expr = new Not(new Val(true));
        assertEquals("~(T)", expr.toString());
    }
}
