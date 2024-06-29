package src.expressions;

import org.junit.Before;
import org.junit.After;
import org.junit.Ignore;
import org.junit.Test;

import src.Expression;

import java.util.Collections;
import java.util.Map;

import static org.junit.Assert.*;

public class VarTest {

    private Var varX;
    private Var varY;
    private Var nullVar;

    @Before
    public void setUp() {
        // Initialize the Var instances before each test
        varX = new Var("x");
        varY = new Var("y");
    }

    @After
    public void tearDown() {
        // Clean up resources after each test if necessary
        varX = null;
        varY = null;
        nullVar = null;
    }

    @Test(expected = IllegalArgumentException.class)
    public void testConstructorWithNull() {
        nullVar = new Var(null);
    }

    @Test
    public void testEvaluateWithAssignment() throws Exception {
        Map<String, Boolean> assignment = Map.of("x", true, "y", false);

        assertEquals(true, varX.evaluate(assignment));
        assertEquals(false, varY.evaluate(assignment));
    }

    @Test(expected = Exception.class)
    public void testEvaluateWithMissingAssignment() throws Exception {
        Map<String, Boolean> assignment = Map.of("y", false);

        varX.evaluate(assignment);
    }

    @Test(expected = Exception.class)
    public void testEvaluateWithoutAssignment() throws Exception {
        varX.evaluate();
    }

    @Test
    public void testGetVariables() {
        assertEquals(Collections.singletonList("x"), varX.getVariables());
        assertEquals(Collections.singletonList("y"), varY.getVariables());
    }

    @Test
    public void testGetVariablesNegative() {
        assertNotEquals(Collections.singletonList("y"), varX.getVariables());
        assertNotEquals(Collections.singletonList("x"), varY.getVariables());
    }

    @Test
    public void testToString() {
        assertEquals("x", varX.toString());
        assertEquals("y", varY.toString());
    }

    @Test
    public void testToStringNegative() {
        assertNotEquals("y", varX.toString());
        assertNotEquals("x", varY.toString());
    }

    @Test
    public void testAssign() {
        Expression expr = new Val(true);
        assertEquals(expr, varX.assign("x", expr));
        assertEquals(varY, varY.assign("x", expr));
    }

    @Test
    public void testAssignNegative() {
        Expression expr = new Val(true);
        assertNotEquals(varX, varX.assign("y", expr));
        assertNotEquals(expr, varY.assign("y", expr));
    }

    @Test
    public void testEquality() {
        assertEquals(new Var("x"), varX);
        assertEquals(new Var("y"), varY);
    }

    @Test
    public void testInequality() {
        assertNotEquals(new Var("x"), varY);
        assertNotEquals(new Var("y"), varX);
    }

    @Test
    public void testHashCode() {
        assertEquals(new Var("x").hashCode(), varX.hashCode());
        assertEquals(new Var("y").hashCode(), varY.hashCode());
    }

    @Test
    public void testConsistency() {
        // Check that repeated calls give consistent results
        assertEquals(Collections.singletonList("x"), varX.getVariables());
        assertEquals(Collections.singletonList("x"), varX.getVariables());

        assertEquals(Collections.singletonList("y"), varY.getVariables());
        assertEquals(Collections.singletonList("y"), varY.getVariables());

        assertEquals("x", varX.toString());
        assertEquals("x", varX.toString());

        assertEquals("y", varY.toString());
        assertEquals("y", varY.toString());

        Expression expr = new Val(true);
        assertEquals(expr, varX.assign("x", expr));
        assertEquals(expr, varX.assign("x", expr));

        assertEquals(varY, varY.assign("x", expr));
        assertEquals(varY, varY.assign("x", expr));
    }
}
