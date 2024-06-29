package src.expressions;

import org.junit.Before;
import org.junit.After;
import org.junit.Ignore;
import org.junit.Test;
import src.Expression;

import java.util.List;
import java.util.Map;

import static org.junit.Assert.*;

public class XnorTest {

    private Xnor xnorExpression;
    private Expression left;
    private Expression right;

    @Before
    public void setUp() {
        // Initialize the Expressions before each test
        left = new Var("x");
        right = new Var("y");
        xnorExpression = new Xnor(left, right);
    }

    @After
    public void tearDown() {
        // Clean up resources after each test if necessary
        xnorExpression = null;
        left = null;
        right = null;
    }

    @Test
    public void testEvaluateWithAssignment() {
        try {
            Map<String, Boolean> assignment = Map.of("x", true, "y", true);
            assertTrue(xnorExpression.evaluate(assignment));

            assignment = Map.of("x", true, "y", false);
            assertFalse(xnorExpression.evaluate(assignment));

            assignment = Map.of("x", false, "y", true);
            assertFalse(xnorExpression.evaluate(assignment));

            assignment = Map.of("x", false, "y", false);
            assertTrue(xnorExpression.evaluate(assignment));
        } catch (Exception e) {
            fail("evaluate method threw an exception: " + e.getMessage());
        }
    }

    @Test
    public void testEvaluateWithMissingAssignment() {
        try {
            Map<String, Boolean> assignment = Map.of("x", true);
            xnorExpression.evaluate(assignment);
            fail("evaluate method should have thrown an exception");
        } catch (Exception e) {
            // Expected exception
        }
    }

    @Test
    public void testEvaluateWithoutAssignment() {
        try {
            xnorExpression.evaluate();
            fail("evaluate method should have thrown an exception");
        } catch (Exception e) {
            // Expected exception
        }
    }

    @Test
    public void testGetVariables() {
        assertEquals(List.of("x", "y"), xnorExpression.getVariables());
    }

    @Test
    public void testGetVariablesNegative() {
        assertNotEquals(List.of("x"), xnorExpression.getVariables());
        assertNotEquals(List.of("y"), xnorExpression.getVariables());
    }

    @Test
    public void testToString() {
        assertEquals("(x # y)", xnorExpression.toString());
    }

    @Test
    public void testToStringNegative() {
        assertNotEquals("(x ⊕ y)", xnorExpression.toString());
        assertNotEquals("x # y", xnorExpression.toString());
    }

    @Test
    public void testAssign() {
        Expression newExpression = xnorExpression.assign("x", new Val(true));
        assertEquals(new Xnor(new Val(true), new Var("y")).toString(), newExpression.toString());

        newExpression = xnorExpression.assign("y", new Val(false));
        assertEquals(new Xnor(new Var("x"), new Val(false)).toString(), newExpression.toString());
    }

    @Test
    public void testAssignNegative() {
        Expression newExpression = xnorExpression.assign("x", new Val(true));
        assertNotEquals(new Xnor(new Val(false), new Var("y")), newExpression);

        newExpression = xnorExpression.assign("y", new Val(false));
        assertNotEquals(new Xnor(new Var("x"), new Val(true)), newExpression);
    }

    @Test
    public void testEquality() {
        assertEquals(new Xnor(new Var("x"), new Var("y")).toString(), xnorExpression.toString());
        assertNotEquals(new Xnor(new Var("x"), new Var("z")).toString(), xnorExpression.toString());
    }

    @Test
    public void testConsistency() {
        try {
            // Check that repeated calls give consistent results
            Map<String, Boolean> assignment = Map.of("x", true, "y", true);
            assertTrue(xnorExpression.evaluate(assignment));
            assertTrue(xnorExpression.evaluate(assignment));

            assertEquals(List.of("x", "y"), xnorExpression.getVariables());
            assertEquals(List.of("x", "y"), xnorExpression.getVariables());

            assertEquals("(x # y)", xnorExpression.toString());
            assertEquals("(x # y)", xnorExpression.toString());

            Expression newExpression = xnorExpression.assign("x", new Val(true));
            assertEquals(new Xnor(new Val(true), new Var("y")).toString(), newExpression.toString());
            assertEquals(new Xnor(new Val(true), new Var("y")).toString(), newExpression.toString());
        } catch (Exception e) {
            fail("evaluate method threw an exception: " + e.getMessage());
        }
    }
}
