package org.aapsasha.businessLogic.Calculation.WithoutRegEx;

public class Node {
    final Type type;
    Node left;
    Node right;
    double num;

    Node(Type type) {
        this.type = type;
        left = null;
        right = null;
    }

    Node(double num) {
        type = Type.number;
        left = null;
        right = null;
        this.num = num;
    }
}