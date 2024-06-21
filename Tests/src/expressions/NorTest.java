package src.expressions;

import org.junit.Before;
import org.junit.After;
import org.junit.Ignore;
import org.junit.Test;
import src.Expression;

import java.util.List;
import java.util.Map;

import static org.junit.Assert.*;

public class NorTest {

    private Nor norExpression;
    private Expression left;
    private Expression right;

    @Before
    public void setUp() {
        // Initialize the Expressions before each test
        left = new Var("x");
        right = new Var("y");
        norExpression = new Nor(left, right);
    }

    @After
    public void tearDown() {
        // Clean up resources after each test if necessary
        norExpression = null;
        left = null;
        right = null;
    }

    @Test
    public void testEvaluateWithAssignment() {
        try {
            Map<String, Boolean> assignment = Map.of("x", true, "y", true);
            assertFalse(norExpression.evaluate(assignment));

            assignment = Map.of("x", true, "y", false);
            assertFalse(norExpression.evaluate(assignment));

            assignment = Map.of("x", false, "y", true);
            assertFalse(norExpression.evaluate(assignment));

            assignment = Map.of("x", false, "y", false);
            assertTrue(norExpression.evaluate(assignment));
        } catch (Exception e) {
            fail("evaluate method threw an exception: " + e.getMessage());
        }
    }

    @Test
    public void testEvaluateWithMissingAssignment() {
        try {
            Map<String, Boolean> assignment = Map.of("x", true);
            norExpression.evaluate(assignment);
            fail("evaluate method should have thrown an exception");
        } catch (Exception e) {
            // Expected exception
        }
    }

    @Test
    public void testEvaluateWithoutAssignment() {
        try {
            norExpression.evaluate();
            fail("evaluate method should have thrown an exception");
        } catch (Exception e) {
            // Expected exception
        }
    }

    @Test
    public void testGetVariables() {
        assertEquals(List.of("x", "y"), norExpression.getVariables());
    }

    @Test
    public void testGetVariablesNegative() {
        assertNotEquals(List.of("x"), norExpression.getVariables());
        assertNotEquals(List.of("y"), norExpression.getVariables());
    }

    @Test
    public void testToString() {
        assertEquals("(x V y)", norExpression.toString());
    }

    @Test
    public void testToStringNegative() {
        assertNotEquals("(x ∧ y)", norExpression.toString());
        assertNotEquals("x V y", norExpression.toString());
    }

    @Test
    @Ignore("Equals method is not implemented")
    public void testAssign() {
        Expression newExpression = norExpression.assign("x", new Val(true));
        assertEquals(new Nor(new Val(true), new Var("y")), newExpression);

        newExpression = norExpression.assign("y", new Val(false));
        assertEquals(new Nor(new Var("x"), new Val(false)), newExpression);
    }

    @Test
    public void testAssignNegative() {
        Expression newExpression = norExpression.assign("x", new Val(true));
        assertNotEquals(new Nor(new Val(false), new Var("y")), newExpression);

        newExpression = norExpression.assign("y", new Val(false));
        assertNotEquals(new Nor(new Var("x"), new Val(true)), newExpression);
    }

    @Test
    @Ignore("Equals method is not implemented")
    public void testEquality() {
        assertEquals(new Nor(new Var("x"), new Var("y")), norExpression);
        assertNotEquals(new Nor(new Var("x"), new Var("z")), norExpression);
    }

    @Test
    @Ignore
    public void testHashCode() {
        assertEquals(new Nor(new Var("x"), new Var("y")).hashCode(), norExpression.hashCode());
        assertNotEquals(new Nor(new Var("x"), new Var("z")).hashCode(), norExpression.hashCode());
    }

    @Test
    @Ignore("Equal implementation needed for this to work")
    public void testConsistency() {
        try {
            // Check that repeated calls give consistent results
            Map<String, Boolean> assignment = Map.of("x", true, "y", true);
            assertFalse(norExpression.evaluate(assignment));
            assertFalse(norExpression.evaluate(assignment));

            assertEquals(List.of("x", "y"), norExpression.getVariables());
            assertEquals(List.of("x", "y"), norExpression.getVariables());

            assertEquals("(x V y)", norExpression.toString());
            assertEquals("(x V y)", norExpression.toString());

            Expression newExpression = norExpression.assign("x", new Val(true));
            assertEquals(new Nor(new Val(true), new Var("y")), newExpression);
            assertEquals(new Nor(new Val(true), new Var("y")), newExpression);
        } catch (Exception e) {
            fail("evaluate method threw an exception: " + e.getMessage());
        }
    }
}
