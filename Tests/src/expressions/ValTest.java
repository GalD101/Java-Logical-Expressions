package src.expressions;
import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import java.util.Collections;
import java.util.Map;

import static org.junit.Assert.*;

public class ValTest {

    private Val trueVal;
    private Val falseVal;
    private Val nullVal;

    @Before
    public void setUp() {
        // Initialize the Val instances before each test
        trueVal = new Val(true);
        falseVal = new Val(false);
    }

    @After
    public void tearDown() {
        // Clean up resources after each test if necessary
        trueVal = null;
        falseVal = null;
        nullVal = null;
    }

    @Test(expected = IllegalArgumentException.class)
    public void testConstructorWithNull() {
        nullVal = new Val(null);
    }

    @Test
    public void testEvaluateWithAssignment() throws Exception {
        assertEquals(true, trueVal.evaluate(Collections.emptyMap()));
        assertEquals(false, falseVal.evaluate(Collections.emptyMap()));
    }

    @Test
    public void testEvaluateWithoutAssignment() throws Exception {
        assertEquals(true, trueVal.evaluate());
        assertEquals(false, falseVal.evaluate());
    }

    @Test
    public void testEvaluateWithIncorrectAssignment() {
        try {
            trueVal.evaluate(Map.of("x", true));
            falseVal.evaluate(Map.of("x", false));
        } catch (Exception e) {
            fail("evaluate method should not throw an exception");
        }
    }

    @Test
    public void testGetVariables() {
        assertTrue(trueVal.getVariables().isEmpty());
        assertTrue(falseVal.getVariables().isEmpty());
    }

    @Test
    public void testGetVariablesNegative() {
        assertNotEquals(Collections.singletonList("x"), trueVal.getVariables());
        assertNotEquals(Collections.singletonList("x"), falseVal.getVariables());
    }

    @Test
    public void testToString() {
        assertEquals("T", trueVal.toString());
        assertEquals("F", falseVal.toString());
    }

    @Test
    public void testToStringNegative() {
        assertNotEquals("F", trueVal.toString());
        assertNotEquals("T", falseVal.toString());
    }

    @Test
    public void testAssign() {
        assertEquals(trueVal, trueVal.assign("x", new Val(false)));
        assertEquals(falseVal, falseVal.assign("x", new Val(true)));
    }

    @Test
    public void testAssignNegative() {
        assertNotEquals(new Val(false), trueVal.assign("x", new Val(false)));
        assertNotEquals(new Val(true), falseVal.assign("x", new Val(true)));
    }

    @Test
    public void testEquality() {
        assertEquals(new Val(true), trueVal);
        assertEquals(new Val(false), falseVal);
    }

    @Test
    public void testInequality() {
        assertNotEquals(new Val(true), falseVal);
        assertNotEquals(new Val(false), trueVal);
    }

    @Test
    public void testConsistency() {
        // Check that repeated calls give consistent results
        try {
            assertEquals(true, trueVal.evaluate());
            assertEquals(true, trueVal.evaluate());
            assertEquals(false, falseVal.evaluate());
            assertEquals(false, falseVal.evaluate());
        } catch (Exception e) {
            fail("evaluate method should not throw an exception");
        }

        assertEquals("T", trueVal.toString());
        assertEquals("T", trueVal.toString());
        assertEquals("F", falseVal.toString());
        assertEquals("F", falseVal.toString());

        assertEquals(Collections.emptyList(), trueVal.getVariables());
        assertEquals(Collections.emptyList(), trueVal.getVariables());
        assertEquals(Collections.emptyList(), falseVal.getVariables());
        assertEquals(Collections.emptyList(), falseVal.getVariables());

        assertEquals(trueVal, trueVal.assign("x", new Val(false)));
        assertEquals(trueVal, trueVal.assign("x", new Val(false)));
        assertEquals(falseVal, falseVal.assign("x", new Val(true)));
        assertEquals(falseVal, falseVal.assign("x", new Val(true)));
    }
}
