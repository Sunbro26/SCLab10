/* Copyright (c) 2015-2016 MIT 6.005 course staff, all rights reserved.
 * Redistribution of original or derived work requires permission of course staff.
 */
package expressivo;

import static org.junit.Assert.*;
import org.junit.Test;

/**
 * Tests for the Expression abstract data type.
 */
public class ExpressionTest {
	
	@Test(expected=AssertionError.class)
    public void testAssertionsEnabled() {
        assert false; // make sure assertions are enabled with VM argument: -ea
    }
    
    // Number tests
    @Test
    public void testNumberCreation() {
        Number n1 = new Number(42);
        Number n2 = new Number(42.0);
        Number n3 = new Number(3.14);
        
        assertEquals("42", n1.toString());
        assertEquals("42", n2.toString());
        assertEquals("3.14", n3.toString());
    }
    
    @Test
    public void testNumberEquality() {
        Number n1 = new Number(42);
        Number n2 = new Number(42.0);
        Number n3 = new Number(42.1);
        
        assertEquals(n1, n2);
        assertEquals(n1.hashCode(), n2.hashCode());
        assertNotEquals(n1, n3);
    }
    
    @Test(expected=IllegalArgumentException.class)
    public void testNumberInvalidValue() {
        new Number(Double.POSITIVE_INFINITY);
    }
    
    // Variable tests
    @Test
    public void testVariableCreation() {
        Variable v1 = new Variable("x");
        Variable v2 = new Variable("xyz");
        
        assertEquals("x", v1.toString());
        assertEquals("xyz", v2.toString());
    }
    
    @Test(expected=IllegalArgumentException.class)
    public void testVariableInvalidName() {
        new Variable("x1");  // contains number
    }
    
    @Test
    public void testVariableCaseSensitivity() {
        Variable v1 = new Variable("x");
        Variable v2 = new Variable("X");
        
        assertNotEquals(v1, v2);
    }
    
    // Addition tests
    @Test
    public void testAddition() {
        Number n1 = new Number(42);
        Variable v1 = new Variable("x");
        Addition add = new Addition(n1, v1);
        
        assertEquals("(42 + x)", add.toString());
        assertEquals(n1, add.getLeft());
        assertEquals(v1, add.getRight());
    }
    
    @Test
    public void testNestedAddition() {
        Number n1 = new Number(42);
        Variable v1 = new Variable("x");
        Number n2 = new Number(10);
        
        Addition add1 = new Addition(n1, v1);
        Addition add2 = new Addition(add1, n2);
        
        assertEquals("((42 + x) + 10)", add2.toString());
    }
    
    @Test
    public void testAdditionEquality() {
        Number n1 = new Number(42);
        Variable v1 = new Variable("x");
        
        Addition add1 = new Addition(n1, v1);
        Addition add2 = new Addition(n1, v1);
        Addition add3 = new Addition(v1, n1);
        
        assertEquals(add1, add2);
        assertEquals(add1.hashCode(), add2.hashCode());
        assertNotEquals(add1, add3);
    }
    
    // Multiplication tests
    @Test
    public void testMultiplication() {
        Number n1 = new Number(42);
        Variable v1 = new Variable("x");
        Multiplication mult = new Multiplication(n1, v1);
        
        assertEquals("(42 * x)", mult.toString());
        assertEquals(n1, mult.getLeft());
        assertEquals(v1, mult.getRight());
    }
    
    @Test
    public void testNestedMultiplication() {
        Number n1 = new Number(42);
        Variable v1 = new Variable("x");
        Number n2 = new Number(10);
        
        Multiplication mult1 = new Multiplication(n1, v1);
        Multiplication mult2 = new Multiplication(mult1, n2);
        
        assertEquals("((42 * x) * 10)", mult2.toString());
    }
    
    @Test
    public void testMixedOperations() {
        Number n1 = new Number(42);
        Variable v1 = new Variable("x");
        Number n2 = new Number(10);
        
        Addition add = new Addition(n1, v1);
        Multiplication mult = new Multiplication(add, n2);
        
        assertEquals("((42 + x) * 10)", mult.toString());
    }
    
    @Test
    public void testMultiplicationEquality() {
        Number n1 = new Number(42);
        Variable v1 = new Variable("x");
        
        Multiplication mult1 = new Multiplication(n1, v1);
        Multiplication mult2 = new Multiplication(n1, v1);
        Multiplication mult3 = new Multiplication(v1, n1);
        
        assertEquals(mult1, mult2);
        assertEquals(mult1.hashCode(), mult2.hashCode());
        assertNotEquals(mult1, mult3);
    }
    
    // Complex equality and hashCode tests
    @Test
    public void testComplexStructuralEquality() {
        Number n1 = new Number(42);
        Number n2 = new Number(42.0);
        Variable v1 = new Variable("x");
        
        Expression expr1 = new Addition(n1, v1);
        Expression expr2 = new Addition(n2, v1);
        Expression expr3 = new Addition(new Number(42), new Variable("x"));
        
        assertEquals(expr1, expr2);
        assertEquals(expr1.hashCode(), expr2.hashCode());
        assertEquals(expr1, expr3);
        assertEquals(expr1.hashCode(), expr3.hashCode());
    }
}