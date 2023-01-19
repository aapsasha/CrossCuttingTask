package org.aapsasha.businessLogic.Calculation.WithoutRegEx;

import jdk.jfr.Name;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ExpressionParserTest {

    @Test
    @Name("basic sum search")
    void calculateDouble1() {
        String expression = "1    + 3+    4+12";
        ExpressionParser parser = new ExpressionParser();
        double answer = parser.calculateDouble(expression);
        double expectedAnswer = 20;
        assertTrue(Math.abs(answer - expectedAnswer) <= 0.00001);
    }

    @Test
    @Name("basic difference search")
    void calculateDouble2() {
        String expression = "1    - 3-    4-12";
        ExpressionParser parser = new ExpressionParser();
        double answer = parser.calculateDouble(expression);
        double expectedAnswer = -18;
        assertTrue(Math.abs(answer - expectedAnswer) <= 0.00001);
    }

    @Test
    @Name("basic multiplication search")
    void calculateDouble3() {
        String expression = "1    * 3*    4*12";
        ExpressionParser parser = new ExpressionParser();
        double answer = parser.calculateDouble(expression);
        double expectedAnswer = 144;
        assertTrue(Math.abs(answer - expectedAnswer) <= 0.00001);
    }

    @Test
    @Name("basic division search")
    void calculateDouble4() {
        String expression = "144/   12  /  4/3";
        ExpressionParser parser = new ExpressionParser();
        double answer = parser.calculateDouble(expression);
        double expectedAnswer = 1;
        assertTrue(Math.abs(answer - expectedAnswer) <= 0.00001);
    }

    @Test
    @Name("real number check")
    void calculateDouble5() {
        String expression = "12.6 + 1.2 / 0.2 * 5.5";
        ExpressionParser parser = new ExpressionParser();
        double answer = parser.calculateDouble(expression);
        double expectedAnswer = 45.6;
        assertTrue(Math.abs(answer - expectedAnswer) <= 0.00001);
    }
    @Test
    @Name("priority check")
    void calculateDouble6() {
        String expression = "2 + 2 * 2";
        ExpressionParser parser = new ExpressionParser();
        double answer = parser.calculateDouble(expression);
        double expectedAnswer = 6;
        assertTrue(Math.abs(answer - expectedAnswer) <= 0.00001);
    }

    @Test
    @Name("bracket priority check")
    void calculateDouble7() {
        String expression = "(2 + 2) * 2";
        ExpressionParser parser = new ExpressionParser();
        double answer = parser.calculateDouble(expression);
        double expectedAnswer = 8;
        assertTrue(Math.abs(answer - expectedAnswer) <= 0.00001);
    }

    @Test
    @Name("complex expression check")
    void calculateDouble8() {
        String expression = "(2 - 4) / (16 -8) + 3*   6";
        ExpressionParser parser = new ExpressionParser();
        double answer = parser.calculateDouble(expression);
        double expectedAnswer = 17.75;
        assertTrue(Math.abs(answer - expectedAnswer) <= 0.00001);
    }
}