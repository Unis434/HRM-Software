package HRS;

import java.util.List;

public class TaxCalculator {
    private double[] taxBrackets;
    private double[] taxRates;

    public TaxCalculator() {
        this.taxBrackets = taxBrackets;
        this.taxRates = taxRates;
    }

    public double calculateIncomeTax(double taxableIncome) {
        double incomeTax = 0;

        for (int i = 0; i < taxBrackets.length; i++) {
            if (taxableIncome <= taxBrackets[i]) {
                incomeTax += taxableIncome * taxRates[i];
                break;
            } else {
                incomeTax += taxBrackets[i] * taxRates[i];
                taxableIncome -= taxBrackets[i];
            }
        }

        return incomeTax;
    }

    public double calculateTotalTax(List<Employee> allEmployees) {
        return 0;
    }
}

