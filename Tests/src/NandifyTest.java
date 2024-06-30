package src;

import org.junit.Test;
import src.expressions.*;

import static org.junit.Assert.assertEquals;

public class NandifyTest {
    private static final Var A = new Var("A"), B = new Var("B");

    /**
     * nand table:
     * not: A NAND A
     * and: ( A NAND B ) NAND ( A NAND B )
     * or: ( A NAND A ) NAND ( B NAND B )
     * nand: A NAND B
     * nor:  [ ( A NAND A ) NAND ( B NAND B ) ] NAND [ ( A NAND A ) NAND ( B NAND B ) ]
     * xor: [ A NAND ( A NAND B ) ] NAND [ B NAND ( A NAND B ) ]
     * xnor: [ ( A NAND A ) NAND ( B NAND B ) ] NAND ( A NAND B )
     */

    @Test
    public void not() {
        Expression expected = new Nand(A, A);
        Expression actual = new Not(A).nandify();
        assertEquals(expected.toString(), actual.toString());
    }

    @Test
    public void and() {
        Expression expected = new Nand(new Nand(A, B), new Nand(A, B));
        Expression actual = new And(A, B).nandify();
        assertEquals(expected.toString(), actual.toString());
    }

    @Test
    public void or() {
        Expression expected = new Nand(new Nand(A, A), new Nand(B, B));
        Expression actual = new Or(A, B).nandify();
        assertEquals(expected.toString(), actual.toString());
    }

    @Test
    public void nand() {
        Expression expected = new Nand(A, B);
        Expression actual = new Nand(A, B).nandify();
        assertEquals(expected.toString(), actual.toString());
    }

    @Test
    public void nor() {
        Expression expected = new Nand(new Nand(new Nand(A, A), new Nand(B, B)),
                new Nand(new Nand(A, A), new Nand(B, B)));
        Expression actual = new Nor(A, B).nandify();
        assertEquals(expected.toString(), actual.toString());
    }

    @Test
    public void xor() {
        Expression expected = new Nand(new Nand(A, new Nand(A, B)), new Nand(B, new Nand(A, B)));
        Expression actual = new Xor(A, B).nandify();
        assertEquals(expected.toString(), actual.toString());
    }

    @Test
    public void xnor() {
        Expression expected = new Nand(new Nand(new Nand(A, A), new Nand(B, B)), new Nand(A, B));
        Expression actual = new Xnor(A, B).nandify();
        assertEquals(expected.toString(), actual.toString());
    }
}
