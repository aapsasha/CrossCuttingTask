package org.aapsasha.businessLogic.Calculation.WithoutRegEx;

public class StringHolder {
    final String expr;
    int pointer;

    StringHolder(String expr) {
        this.expr = expr;
        pointer = 0;
    }

    Character nextChar(String charset) {
        Character ch = null;
        while ((pointer < expr.length()) && (ch = expr.charAt(pointer++)) == ' ')
            ;
        if (ch != null) {
            if (charset.indexOf(ch) != -1)
                return ch;
            pointer--;
        }
        return null;
    }

    boolean chars_available() {
        return pointer < expr.length();
    }

}