package src.expressions;

import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import src.Expression;

import java.util.List;
import java.util.Map;

import static org.junit.Assert.*;

public class XorTest {

    private Xor xorExpression;
    private Expression left;
    private Expression right;

    @Before
    public void setUp() {
        // Initialize the Expressions before each test
        left = new Var("x");
        right = new Var("y");
        xorExpression = new Xor(left, right);
    }

    @After
    public void tearDown() {
        // Clean up resources after each test if necessary
        xorExpression = null;
        left = null;
        right = null;
    }

    @Test
    public void testEvaluateWithAssignment() {
        try {
            Map<String, Boolean> assignment = Map.of("x", true, "y", true);
            assertFalse(xorExpression.evaluate(assignment));

            assignment = Map.of("x", true, "y", false);
            assertTrue(xorExpression.evaluate(assignment));

            assignment = Map.of("x", false, "y", true);
            assertTrue(xorExpression.evaluate(assignment));

            assignment = Map.of("x", false, "y", false);
            assertFalse(xorExpression.evaluate(assignment));
        } catch (Exception e) {
            fail("evaluate method threw an exception: " + e.getMessage());
        }
    }

    @Test
    public void testEvaluateWithMissingAssignment() {
        try {
            Map<String, Boolean> assignment = Map.of("x", true);
            xorExpression.evaluate(assignment);
            fail("evaluate method should have thrown an exception");
        } catch (Exception e) {
            // Expected exception
        }
    }

    @Test
    public void testEvaluateWithoutAssignment() {
        try {
            xorExpression.evaluate();
            fail("evaluate method should have thrown an exception");
        } catch (Exception e) {
            // Expected exception
        }
    }

    @Test
    public void testGetVariables() {
        assertEquals(List.of("x", "y"), xorExpression.getVariables());
    }

    @Test
    public void testGetVariablesNegative() {
        assertNotEquals(List.of("x"), xorExpression.getVariables());
        assertNotEquals(List.of("y"), xorExpression.getVariables());
    }

    @Test
    public void testToString() {
        assertEquals("(x ^ y)", xorExpression.toString());
    }

    @Test
    public void testToStringNegative() {
        assertNotEquals("(x ∧ y)", xorExpression.toString());
        assertNotEquals("x ⊕ y", xorExpression.toString());
    }

    @Test
    public void testAssign() {
        Expression newExpression = xorExpression.assign("x", new Val(true));
        assertEquals(new Xor(new Val(true), new Var("y")), newExpression);

        newExpression = xorExpression.assign("y", new Val(false));
        assertEquals(new Xor(new Var("x"), new Val(false)), newExpression);
    }

    @Test
    public void testAssignNegative() {
        Expression newExpression = xorExpression.assign("x", new Val(true));
        assertNotEquals(new Xor(new Val(false), new Var("y")), newExpression);

        newExpression = xorExpression.assign("y", new Val(false));
        assertNotEquals(new Xor(new Var("x"), new Val(true)), newExpression);
    }

    @Test
    public void testEquality() {
        assertEquals(new Xor(new Var("x"), new Var("y")), xorExpression);
        assertNotEquals(new Xor(new Var("x"), new Var("z")), xorExpression);
    }

    @Test
    public void testConsistency() {
        try {
            // Check that repeated calls give consistent results
            Map<String, Boolean> assignment = Map.of("x", true, "y", true);
            assertFalse(xorExpression.evaluate(assignment));
            assertFalse(xorExpression.evaluate(assignment));

            assertEquals(List.of("x", "y"), xorExpression.getVariables());
            assertEquals(List.of("x", "y"), xorExpression.getVariables());

            assertEquals("(x ⊕ y)", xorExpression.toString());
            assertEquals("(x ⊕ y)", xorExpression.toString());

            Expression newExpression = xorExpression.assign("x", new Val(true));
            assertEquals(new Xor(new Val(true), new Var("y")), newExpression);
            assertEquals(new Xor(new Val(true), new Var("y")), newExpression);
        } catch (Exception e) {
            fail("evaluate method threw an exception: " + e.getMessage());
        }
    }
}
