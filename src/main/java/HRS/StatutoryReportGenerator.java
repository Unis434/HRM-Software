package HRS;

import java.util.Collections;
import java.util.List;

/**
 * Generates the statutory report required for NRA and NASSIT submissions.
 * Shows each employee's income, PAYE, and both employee and employer
 * NASSIT contributions.
 *
 * All statutory rates are sourced from ComplianceManager.
 */
public class StatutoryReportGenerator {

    private final List<Employee>    employees;
    private final ComplianceManager complianceManager;

    /**
     * Constructs the generator with a live employee list.
     *
     * @param employees non-null list of employees to include in the report
     */
    public StatutoryReportGenerator(List<Employee> employees) {
        if (employees == null) {
            throw new IllegalArgumentException("Employee list must not be null.");
        }
        this.employees         = employees;
        this.complianceManager = new ComplianceManager();
    }

    /**
     * No-argument constructor that initialises with an empty employee list.
     */
    public StatutoryReportGenerator() {
        this(Collections.emptyList());
    }

    /**
     * Generates and prints a statutory report including PAYE and NASSIT
     * for each employee, and returns the report as a String.
     *
     * @param employeeManager the EmployeeManager whose employee list is used
     *                        if this generator was constructed with no-arg constructor;
     *                        pass null to use the list injected at construction time
     * @return formatted statutory report string
     */
    public String generateStatutoryReport(EmployeeManager employeeManager) {
        List<Employee> source = (employeeManager != null)
                ? employeeManager.getAllEmployees()
                : employees;

        StringBuilder sb = new StringBuilder();

        String header = String.format(
                "%-14s %-20s %16s %16s %16s %16s%n",
                "Employee ID", "Name", "Income (SLE)", "PAYE (SLE)",
                "NASSIT Emp (SLE)", "NASSIT Empr (SLE)"
        );
        String divider = "-".repeat(110);

        sb.append("Statutory Report\n").append(divider).append("\n")
                .append(header).append(divider).append("\n");

        double totalPAYE        = 0.0;
        double totalNassitEmp   = 0.0;
        double totalNassitEmpr  = 0.0;

        for (Employee e : source) {
            double income       = e.getIncome();
            double nassitEmp    = complianceManager.calculateEmployeeNASSIT(e.getBasicSalary());
            double nassitEmpr   = complianceManager.calculateEmployerNASSIT(e.getBasicSalary());
            double taxable      = income - nassitEmp;
            double paye         = complianceManager.calculatePAYETax(taxable);

            totalPAYE       += paye;
            totalNassitEmp  += nassitEmp;
            totalNassitEmpr += nassitEmpr;

            sb.append(String.format(
                    "%-14s %-20s %16.2f %16.2f %16.2f %16.2f%n",
                    e.getEmployeeId(), e.getName(), income, paye, nassitEmp, nassitEmpr
            ));
        }

        sb.append(divider).append("\n");
        sb.append(String.format("Totals:%54s %16.2f %16.2f %16.2f%n",
                "", totalPAYE, totalNassitEmp, totalNassitEmpr));

        String report = sb.toString();
        System.out.print(report);
        return report;
    }
}