package HRS;

import java.util.Map;
import java.util.TreeMap;

public class ComplianceManager {
    private static final Map<Double, Double> PAYE_BRACKETS = new TreeMap<>();

    static {
        // Define PAYE tax brackets and rates
        PAYE_BRACKETS.put(500.0, 0.0);     // First Le 500 is exempt
        PAYE_BRACKETS.put(1000.0, 0.15);   // Next Le 1000 at 15%
        PAYE_BRACKETS.put(1500.0, 0.20);   // Next Le 1500 at 20%
        PAYE_BRACKETS.put(2000.0, 0.30);   // Next Le 2000 at 30%
    }

    public double calculatePAYETax(double totalIncome) {
        double remainingIncome = totalIncome;
        double tax = 0.0;

        for (Map.Entry<Double, Double> entry : PAYE_BRACKETS.entrySet()) {
            double bracketLimit = entry.getKey();
            double taxRate = entry.getValue();

            if (remainingIncome <= 0) {
                break;  // No more income to tax
            }

            if (remainingIncome <= bracketLimit) {
                tax += remainingIncome * taxRate;
                break;
            } else {
                tax += bracketLimit * taxRate;
                remainingIncome -= bracketLimit;
            }
        }

        return tax;
    }

    public boolean isTaxCompliant(Employee employee, double totalIncome) {
        double calculatedTax = calculatePAYETax(totalIncome);

        // Check if the calculated tax matches the employee's reported tax
        return employee.getReportedTax() == calculatedTax;
    }

    public static void main(String[] args) {
        ComplianceManager complianceManager = new ComplianceManager();

        // Example usage: Check PAYE tax compliance for an employee
        Employee employee = new Employee("John Doe", 50000.0, 10000.0, 7500.0); // Reported tax: Le 7500
        double totalIncome = employee.getSalary() + employee.getBonus();

        double calculatedTax = complianceManager.calculatePAYETax(totalIncome);

        System.out.println("Total Income: Le " + totalIncome);
        System.out.println("Calculated PAYE Tax: Le " + calculatedTax);

        boolean isCompliant = complianceManager.isTaxCompliant(employee, totalIncome);
        if (isCompliant) {
            System.out.println("Tax compliance: Employee is compliant with PAYE tax regulations.");
        } else {
            System.out.println("Tax compliance: Employee is not compliant with PAYE tax regulations.");
        }
    }

}

