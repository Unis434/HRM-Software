package HRS;

import java.util.List;

public class TaxReportGenerator {
    private List<Employee> employees;

    public TaxReportGenerator() {
        this.employees = employees;
    }

    public String generateTaxReport(double totalTax) {
        // Generate a tax report header
        System.out.println("Tax Report");
        System.out.println("------------------------------------------------------------------");
        System.out.printf("%-15s %-15s %-15s %-15s %-15s %-15s%n", "Employee ID", "Name", "Income", "PAYE", "NASSIT", "Net Income");
        System.out.println("------------------------------------------------------------------");

        // Calculate and display tax information for each employee
        for (Employee employee : employees) {
            double income = employee.getIncome();
            double paye = calculatePAYE(income);
            double nassit = calculateNASSIT(income);
            double netIncome = income - paye - nassit;

            System.out.printf("%-15s %-15s $%-14.2f $%-14.2f $%-14.2f $%-14.2f%n",
                    employee.getEmployeeId(), employee.getName(), income, paye, nassit, netIncome);
        }
        return null;
    }

    private double calculatePAYE(double income) {
        double payeTax = 0;

        if (income <= 600) {
            payeTax = 0; // Nil tax
        } else if (income <= 1200) {
            payeTax = 0.15 * (income - 600); // 15% tax for the next Le 600
        } else if (income <= 1800) {
            payeTax = 0.15 * 600 + 0.20 * (income - 1200); // 15% tax for the first Le 600, 20% tax for the next Le 600
        } else if (income <= 2400) {
            payeTax = 0.15 * 600 + 0.20 * 600 + 0.30 * (income - 1800); // 15% tax for the first Le 600, 20% tax for the next Le 600, 30% tax for the next Le 600
        } else {
            payeTax = 0.15 * 600 + 0.20 * 600 + 0.30 * 600 + 0.35 * (income - 2400); // 15% tax for the first Le 600, 20% tax for the next Le 600, 30% tax for the next Le 600, 35% tax for the rest
        }

        return payeTax;
    }


    private double calculateNASSIT(double income) {
        // Calculate NASSIT contribution (5% of income) plus employer's contribution (10% of income)
        double employeeContribution = 0.05 * income;
        double employerContribution = 0.10 * income;
        return employeeContribution + employerContribution;
    }
}
