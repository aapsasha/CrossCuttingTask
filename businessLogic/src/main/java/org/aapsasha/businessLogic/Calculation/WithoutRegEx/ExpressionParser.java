package org.aapsasha.businessLogic.Calculation.WithoutRegEx;

import org.aapsasha.businessLogic.Calculation.CalculationStrategy;

public class ExpressionParser implements CalculationStrategy {

    final static int MaxNumOfDigit = 38;

    @Override
    public String calculate(String expression) {
        double answer = calculateDouble(expression);
        if(answer % 1 == 0) {
            return Integer.toString((int) answer);
        }else {
            return Double.toString(answer);
        }
    }
    public double calculateDouble(String expression){
        ExpressionParser ep = new ExpressionParser();
        Node root = null;
        try {
            root = ep.parseExpression(expression);
        } catch (Exception ex) {
            System.err.println("Exception: " + ex.getMessage());
        }
        return ep.evaluate(root);
    }

    public double evaluate(Node p) {
        if (p != null) {
            switch(p.type) {
                case add:
                    return evaluate(p.left) + evaluate(p.right);
                case subtract:
                    return evaluate(p.left) - evaluate(p.right);
                case multiply:
                    return evaluate(p.left) * evaluate(p.right);
                case divide:
                    return evaluate(p.left) / evaluate(p.right);
                case minus:
                    return -evaluate(p.left);
                case number:
                    return p.num;
                default:
                    throw new RuntimeException("unexpected type: " + p.type);
            }
        }
        return 0;
    }

    public Node parseExpression(String expr) throws Exception {
        StringHolder sh = new StringHolder(expr);
        Node result = expression(sh);
        if (sh.chars_available())
            throw new Exception("unexpected character: " + expr.charAt(sh.pointer));
        return result;
    }

    private Node expression(StringHolder sh) throws Exception {
        Node pt = term(sh);
        Character ch;
        while ((ch = sh.nextChar("+-")) != null) {
            Node p = new Node(ch == '+' ? Type.add : Type.subtract);
            p.left = pt;
            pt = p;
            p.right = term(sh);
        }
        return pt;
    }

    private Node term(StringHolder sh) throws Exception {
        Node pf = factor(sh);
        Character ch;
        while ((ch = sh.nextChar("*/")) != null) {
            Node p = new Node(ch == '*' ? Type.multiply : Type.divide);
            p.left = pf;
            pf = p;
            p.right = factor(sh);
        }
        return pf;
    }

    private Node factor(StringHolder sh) throws Exception {
        Character ch = sh.nextChar("(-0123456789");
        if (ch == null)
            if (sh.chars_available())
                throw new Exception("unexpected character: " + sh.expr.charAt(sh.pointer));
            else throw new Exception("unexpected end of expression");
        if (ch == '(') {
            Node n1 = expression(sh);
            ch = sh.nextChar(")");
            if (ch == null) {
                throw new Exception("missing ) bracket");
            }
            return n1;
        } else if (ch == '-') {
            Node n2 = new Node(Type.minus);
            n2.left = factor(sh);
            return n2;
        } else if (ch >= '0' && ch <= '9') {
            double num = 0; int nDigits = 0;
            while (ch != null) {
                if (nDigits < MaxNumOfDigit) {
                    num = num * 10 + ch - '0';
                    nDigits++;
                } else num = num * 10;
                ch = sh.nextChar("0123456789");
            }
            if (sh.nextChar(".") != null) {
                double m = 1;
                while ((ch = sh.nextChar("0123456789")) != null) {
                    m = m * 0.1;
                    num = num + (ch - '0') * m;
                }
            }
            return new Node(num);
        } else return null;
    }

}