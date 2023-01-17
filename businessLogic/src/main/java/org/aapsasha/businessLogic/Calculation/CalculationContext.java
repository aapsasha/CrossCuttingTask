package org.aapsasha.businessLogic.Calculation;

public class CalculationContext {
    private CalculationStrategy strategy;

    public void setStrategy(CalculationStrategy strategy){
        this.strategy = strategy;
    }

    public String calculateExpression(String expression){
        return strategy.calculate(expression);
    }

}
