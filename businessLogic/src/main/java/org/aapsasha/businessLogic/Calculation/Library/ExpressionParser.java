package org.aapsasha.businessLogic.Calculation.Library;

import com.fathzer.soft.javaluator.DoubleEvaluator;
import org.aapsasha.businessLogic.Calculation.CalculationStrategy;

public class ExpressionParser implements CalculationStrategy {

    @Override
    public String calculate(String expression) {
        DoubleEvaluator eval = new DoubleEvaluator();
        double answer = eval.evaluate(expression);
        if(answer % 1 == 0) {
            return Integer.toString((int) answer);
        }else {
            return Double.toString(answer);
        }
    }
}
