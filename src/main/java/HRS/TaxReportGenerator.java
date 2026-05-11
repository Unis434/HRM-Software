package HRS;

import java.util.Collections;
import java.util.List;

/**
 * Generates the monthly tax report showing each employee's gross income,
 * PAYE liability, NASSIT contribution, and net income.
 *
 * Tax figures are sourced exclusively from ComplianceManager.
 * This class contains no bracket or rate definitions of its own.
 */
public class TaxReportGenerator {

    private final List<Employee>      employees;
    private final ComplianceManager   complianceManager;

    /**
     * Constructs a report generator with a live employee list.
     * The list must not be null; pass an empty list if no employees
     * are available yet.
     *
     * @param employees non-null list of employees to report on
     */
    public TaxReportGenerator(List<Employee> employees) {
        if (employees == null) {
            throw new IllegalArgumentException("Employee list must not be null.");
        }
        this.employees         = employees;
        this.complianceManager = new ComplianceManager();
    }

    /**
     * No-argument constructor that initialises with an empty employee list.
     * Intended for use in Main.java before employees are loaded;
     * call generateTaxReport() after populating via EmployeeManager.
     */
    public TaxReportGenerator() {
        this(Collections.emptyList());
    }

    /**
     * Generates and prints a formatted tax report for all employees.
     * Also returns the report as a String for logging or export.
     *
     * @param totalTax pre-calculated total PAYE (used only for the summary footer)
     * @return formatted report string
     */
    public String generateTaxReport(double totalTax) {
        StringBuilder sb = new StringBuilder();

        String header = String.format(
                "%-14s %-20s %16s %16s %16s %16s%n",
                "Employee ID", "Name", "Income (SLE)", "PAYE (SLE)", "NASSIT (SLE)", "Net Income (SLE)"
        );
        String divider = "-".repeat(102);

        sb.append("Tax Report\n").append(divider).append("\n").append(header).append(divider).append("\n");

        for (Employee e : employees) {
            double income      = e.getIncome();
            double nassitEmp   = complianceManager.calculateEmployeeNASSIT(e.getBasicSalary());
            double taxable     = income - nassitEmp;
            double paye        = complianceManager.calculatePAYETax(taxable);
            double net         = income - nassitEmp - paye;

            sb.append(String.format(
                    "%-14s %-20s %16.2f %16.2f %16.2f %16.2f%n",
                    e.getEmployeeId(), e.getName(), income, paye, nassitEmp, net
            ));
        }

        sb.append(divider).append("\n");
        sb.append(String.format("Total PAYE liability: SLE %,.2f%n", totalTax));

        String report = sb.toString();
        System.out.print(report);
        return report;
    }

    /**
     * Convenience method: calculates the total PAYE across all employees
     * and generates the report in one call.
     *
     * @return formatted report string
     */
    public String generateTaxReport() {
        double totalTax = 0.0;
        for (Employee e : employees) {
            double nassit  = complianceManager.calculateEmployeeNASSIT(e.getBasicSalary());
            double taxable = e.getIncome() - nassit;
            totalTax      += complianceManager.calculatePAYETax(taxable);
        }
        return generateTaxReport(totalTax);
    }
}