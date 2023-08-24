package HRS;

import java.util.List;

public class StatutoryReportGenerator {
    private List<Employee> employees;

    public StatutoryReportGenerator() {
        this.employees = employees;
    }

    public String generateStatutoryReport(EmployeeManager employeeManager) {
        // Generate a statutory report header
        System.out.println("Statutory Report");
        System.out.println("--------------------------------------------------------");
        System.out.printf("%-15s %-15s %-15s %-15s %-15s%n", "Employee ID", "Name", "Income", "PAYE", "NASSIT");
        System.out.println("--------------------------------------------------------");

        // Calculate and display statutory information for each employee
        for (Employee employee : employees) {
            double income = employee.getIncome();
            double paye = calculatePAYE(income);
            double nassit = calculateNASSIT(income);

            System.out.printf("%-15s %-15s $%-14.2f $%-14.2f $%-14.2f%n",
                    employee.getEmployeeId(), employee.getName(), income, paye, nassit);
        }
        return null;
    }

    private double calculatePAYE(double income) {
        double payeTax = 0;

        if (income <= 500) {
            payeTax = 0; // Nil tax
        } else if (income <= 1000) {
            payeTax = 0.15 * (income - 500); // 15% tax for the next Le 500
        } else if (income <= 1500) {
            payeTax = 0.15 * 500 + 0.20 * (income - 1000); // 15% tax for the first Le 500, 20% tax for the next Le 500
        } else if (income <= 2000) {
            payeTax = 0.15 * 500 + 0.20 * 500 + 0.30 * (income - 1500); // 15% tax for the first Le 500, 20% tax for the next Le 500, 30% tax for the next Le 500
        } else {
            payeTax = 0.15 * 500 + 0.20 * 500 + 0.30 * 500 + 0.35 * (income - 2000); // 15% tax for the first Le 500, 20% tax for the next Le 500, 30% tax for the next Le 500, 35% tax for the rest
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

