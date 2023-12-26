package HRS;

import java.util.List;

public class PayrollCalculator {
    private double taxRate;

    public PayrollCalculator() {
        this.taxRate = taxRate;
    }

    public Payroll calculatePayroll(Employee employee) {
        double basicSalary = employee.getBasicSalary();
        double transportAllowance = employee.getTransportAllowance();
        double telephoneSubsidy = employee.getTelephoneSubsidy();
        double utilityAllowance = employee.getUtilityAllowance();
        double entertainment = employee.getEntertainment();
        double domesticHelpAllowance = employee.getDomesticHelpAllowance();
        double lunchAllowance = employee.getLunchAllowance();

        double totalAllowance = transportAllowance + telephoneSubsidy + utilityAllowance +
                entertainment + domesticHelpAllowance + lunchAllowance;

        double totalDeduction = calculateTotalDeductions(basicSalary, totalAllowance);

        double netSalary = basicSalary + totalAllowance - totalDeduction;

        return new Payroll(employee.getEmployeeId(), basicSalary, totalAllowance, totalDeduction, netSalary);
    }

    private double calculateTotalDeductions(double basicSalary, double totalAllowance) {
        double nassit = 0.05 * basicSalary;
        double taxableIncome = basicSalary + totalAllowance - nassit;

        double payeTax = 0;
        if (taxableIncome > 0) {
            if (taxableIncome <= 600) {
                payeTax = 0;
            } else if (taxableIncome <= 1200) {
                payeTax = 0.15 * (taxableIncome - 600);
            } else if (taxableIncome <= 1800) {
                payeTax = 0.15 * 500 + 0.20 * (taxableIncome - 1200);
            } else if (taxableIncome <= 2400) {
                payeTax = 0.15 * 500 + 0.20 * 500 + 0.30 * (taxableIncome - 1800);
            } else {
                payeTax = 0.15 * 600 + 0.20 * 600 + 0.30 * 600 + 0.35 * (taxableIncome - 2400);
            }
        }

        return nassit + payeTax;
    }

    public double calculateTotalPayroll(List<Employee> allEmployees) {
        return 0;
    }
}


