package src.expressions;

import org.junit.Test;
import src.Expression;

import static org.junit.Assert.assertEquals;

public class NorifyTest {
    private static final Var A = new Var("A"), B = new Var("B");

    /**
     * nor table:
     * not: A NOR A
     * and: ( A NOR A ) NOR ( B NOR B )
     * or: ( A NOR B ) NOR ( A NOR B )
     * nand: [ ( A NOR A ) NOR ( B NOR B ) ] NOR [ ( A NOR A ) NOR ( B NOR B ) ]
     * nor: A NOR B
     * xor:[ ( A NOR A ) NOR ( B NOR B ) ] NOR ( A NOR B )
     * xnor: [ A NOR ( A NOR B ) ] NOR [ B NOR ( A NOR B ) ]
     */

    @Test
    public void not() {
        Expression expected = new Nor(A, A);
        Expression actual = new Not(A).norify();
        assertEquals(expected.toString(), actual.toString());
    }

    @Test
    public void and() {
        Expression expected = new Nor(new Nor(A, A), new Nor(B, B));
        Expression actual = new And(A, B).norify();
        assertEquals(expected.toString(), actual.toString());
    }

    @Test
    public void or() {
        Expression expected = new Nor(new Nor(A, B), new Nor(A, B));
        Expression actual = new Or(A, B).norify();
        assertEquals(expected.toString(), actual.toString());
    }

    @Test
    public void nand() {
        Expression expected = new Nor(new Nor(new Nor(A, A), new Nor(B, B)),
                new Nor(new Nor(A, A), new Nor(B, B)));
        Expression actual = new Nand(A, B).norify();
        assertEquals(expected.toString(), actual.toString());
    }

    @Test
    public void nor() {
        Expression expected = new Nor(A, B);
        Expression actual = new Nor(A, B).norify();
        assertEquals(expected.toString(), actual.toString());
    }

    @Test
    public void xor() {
        Expression expected = new Nor(new Nor(new Nor(A, A), new Nor(B, B)), new Nor(A, B));
        Expression actual = new Xor(A, B).norify();
        assertEquals(expected.toString(), actual.toString());
    }

    @Test
    public void xnor() {
        Expression expected = new Nor(new Nor(A, new Nor(A, B)), new Nor(B, new Nor(A, B)));
        Expression actual = new Xnor(A, B).norify();
        assertEquals(expected.toString(), actual.toString());
    }
}
