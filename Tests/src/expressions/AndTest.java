package src.expressions;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import src.Expression;

import java.util.List;
import java.util.Map;

import static org.junit.Assert.*;

public class AndTest {

    private And andExpression;
    private Expression left;
    private Expression right;

    @Before
    public void setUp() {
        // Initialize the Expressions before each test
        left = new Var("x");
        right = new Var("y");
        andExpression = new And(left, right);
    }

    @After
    public void tearDown() {
        // Clean up resources after each test if necessary
        andExpression = null;
        left = null;
        right = null;
    }

    @Test
    public void testEvaluateWithAssignment() throws Exception {
        Map<String, Boolean> assignment = Map.of("x", true, "y", true);
        assertTrue(andExpression.evaluate(assignment));

        assignment = Map.of("x", true, "y", false);
        assertFalse(andExpression.evaluate(assignment));

        assignment = Map.of("x", false, "y", true);
        assertFalse(andExpression.evaluate(assignment));

        assignment = Map.of("x", false, "y", false);
        assertFalse(andExpression.evaluate(assignment));
    }

    @Test(expected = Exception.class)
    public void testEvaluateWithMissingAssignment() throws Exception {
        Map<String, Boolean> assignment = Map.of("x", true);
        andExpression.evaluate(assignment);
    }

    @Test(expected = Exception.class)
    public void testEvaluateWithoutAssignment() throws Exception {
        andExpression.evaluate();
    }

    @Test
    public void testGetVariables() {
        assertEquals(List.of("x", "y"), andExpression.getVariables());
    }

    @Test
    public void testGetVariablesNegative() {
        assertNotEquals(List.of("x"), andExpression.getVariables());
        assertNotEquals(List.of("y"), andExpression.getVariables());
    }

    @Test
    public void testToString() {
        assertEquals("(x & y)", andExpression.toString());
    }

    @Test
    public void testToStringNegative() {
        assertNotEquals("(x ∨ y)", andExpression.toString());
        assertNotEquals("x ∧ y", andExpression.toString());
    }

    @Test
    public void testAssign() {
        Expression newExpression = andExpression.assign("x", new Val(true));
        assertEquals(new And(new Val(true), new Var("y")).toString(), newExpression.toString());

        newExpression = andExpression.assign("y", new Val(false));
        assertEquals(new And(new Var("x"), new Val(false)).toString(), newExpression.toString());
    }

    @Test
    public void testAssignNegative() {
        Expression newExpression = andExpression.assign("x", new Val(true));
        assertNotEquals(new And(new Val(false), new Var("y")), newExpression);

        newExpression = andExpression.assign("y", new Val(false));
        assertNotEquals(new And(new Var("x"), new Val(true)), newExpression);
    }

    @Test
    public void testEquality() {
        assertEquals(new And(new Var("x"), new Var("y")).toString(), andExpression.toString());
        assertNotEquals(new And(new Var("x"), new Var("z")).toString(), andExpression.toString());
    }

    @Test
    public void testConsistency() {
        // Check that repeated calls give consistent results
        Map<String, Boolean> assignment = Map.of("x", true, "y", true);
        // Check that repeated calls give consistent results
        try {
            assertTrue(andExpression.evaluate(assignment));
            assertTrue(andExpression.evaluate(assignment));
        } catch (Exception e) {
            fail("evaluate method should not throw an exception");
        }

        assertEquals(List.of("x", "y"), andExpression.getVariables());
        assertEquals(List.of("x", "y"), andExpression.getVariables());

        assertEquals("(x & y)", andExpression.toString());
        assertEquals("(x & y)", andExpression.toString());

        Expression newExpression = andExpression.assign("x", new Val(true));
        assertEquals(new And(new Val(true), new Var("y")).toString(), newExpression.toString());
        assertEquals(new And(new Val(true), new Var("y")).toString(), newExpression.toString());
    }
}
