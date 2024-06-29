package src.expressions;

import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import src.Expression;

import java.util.List;
import java.util.Map;

import static org.junit.Assert.*;

public class NandTest {

    private Nand nandExpression;
    private Expression left;
    private Expression right;

    @Before
    public void setUp() {
        // Initialize the Expressions before each test
        left = new Var("x");
        right = new Var("y");
        nandExpression = new Nand(left, right);
    }

    @After
    public void tearDown() {
        // Clean up resources after each test if necessary
        nandExpression = null;
        left = null;
        right = null;
    }

    @Test
    public void testEvaluateWithAssignment() throws Exception {
        Map<String, Boolean> assignment = Map.of("x", true, "y", true);
        assertFalse(nandExpression.evaluate(assignment));

        assignment = Map.of("x", true, "y", false);
        assertTrue(nandExpression.evaluate(assignment));

        assignment = Map.of("x", false, "y", true);
        assertTrue(nandExpression.evaluate(assignment));

        assignment = Map.of("x", false, "y", false);
        assertTrue(nandExpression.evaluate(assignment));
    }

    @Test(expected = Exception.class)
    public void testEvaluateWithMissingAssignment() throws Exception {
        Map<String, Boolean> assignment = Map.of("x", true);
        nandExpression.evaluate(assignment);
    }

    @Test(expected = Exception.class)
    public void testEvaluateWithoutAssignment() throws Exception {
        nandExpression.evaluate();
    }

    @Test
    public void testGetVariables() {
        assertEquals(List.of("x", "y"), nandExpression.getVariables());
    }

    @Test
    public void testGetVariablesNegative() {
        assertNotEquals(List.of("x"), nandExpression.getVariables());
        assertNotEquals(List.of("y"), nandExpression.getVariables());
    }

    @Test
    public void testToString() {
        assertEquals("(x A y)", nandExpression.toString());
    }

    @Test
    public void testToStringNegative() {
        assertNotEquals("(x ∧ y)", nandExpression.toString());
        assertNotEquals("x A y", nandExpression.toString());
    }

    @Test
    public void testAssign() {
        Expression newExpression = nandExpression.assign("x", new Val(true));
        assertEquals(new Nand(new Val(true), new Var("y")), newExpression);

        newExpression = nandExpression.assign("y", new Val(false));
        assertEquals(new Nand(new Var("x"), new Val(false)), newExpression);
    }

    @Test
    public void testAssignNegative() {
        Expression newExpression = nandExpression.assign("x", new Val(true));
        assertNotEquals(new Nand(new Val(false), new Var("y")), newExpression);

        newExpression = nandExpression.assign("y", new Val(false));
        assertNotEquals(new Nand(new Var("x"), new Val(true)), newExpression);
    }

    @Test
    public void testEquality() {
        assertEquals(new Nand(new Var("x"), new Var("y")), nandExpression);
        assertNotEquals(new Nand(new Var("x"), new Var("z")), nandExpression);
    }

    @Test
    public void testConsistency() {
        // Check that repeated calls give consistent results
        Map<String, Boolean> assignment = Map.of("x", true, "y", true);
        try {
            assertFalse(nandExpression.evaluate(assignment));
            assertFalse(nandExpression.evaluate(assignment));
        } catch (Exception e) {
            fail("Exception thrown when it shouldn't have been");
        }

        assertEquals(List.of("x", "y"), nandExpression.getVariables());
        assertEquals(List.of("x", "y"), nandExpression.getVariables());

        assertEquals("(x A y)", nandExpression.toString());
        assertEquals("(x A y)", nandExpression.toString());

        Expression newExpression = nandExpression.assign("x", new Val(true));
        assertEquals(new Nand(new Val(true), new Var("y")), newExpression);
        assertEquals(new Nand(new Val(true), new Var("y")), newExpression);
    }
}
