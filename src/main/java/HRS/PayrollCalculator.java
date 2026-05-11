package HRS;

import java.util.List;

/**
 * Calculates payroll for individual employees and totals across a payroll run.
 * PAYE and NASSIT logic is delegated to ComplianceManager.
 */
public class PayrollCalculator {

    private final ComplianceManager complianceManager;

    public PayrollCalculator() {
        this.complianceManager = new ComplianceManager();
    }

    /**
     * Calculates a full payroll record for one employee.
     * Deductions are:
     *   1. NASSIT employee contribution (5% of basic salary)
     *   2. PAYE on (basic + allowances + bonus - NASSIT)
     *
     * @param employee the employee to calculate payroll for
     * @return a Payroll record with all components populated
     */
    public Payroll calculatePayroll(Employee employee) {
        double basicSalary      = employee.getBasicSalary();
        double totalAllowances  = employee.getTotalAllowances();
        double grossIncome      = basicSalary + totalAllowances + employee.getBonus();

        double nassit           = complianceManager.calculateEmployeeNASSIT(basicSalary);
        double taxableIncome    = grossIncome - nassit;
        double paye             = complianceManager.calculatePAYETax(taxableIncome);

        double totalDeductions  = nassit + paye;
        double netSalary        = grossIncome - totalDeductions;

        return new Payroll(employee.getEmployeeId(), basicSalary, totalAllowances, totalDeductions, netSalary);
    }

    /**
     * Sums the net payroll cost across all employees.
     * Returns 0.0 for a null or empty list rather than throwing.
     *
     * @param employees list of employees in the payroll run
     * @return total net pay in SLE
     */
    public double calculateTotalPayroll(List<Employee> employees) {
        if (employees == null || employees.isEmpty()) {
            return 0.0;
        }
        double total = 0.0;
        for (Employee e : employees) {
            total += calculatePayroll(e).getNetSalary();
        }
        return total;
    }

    /**
     * Sums total gross payroll cost to the employer, including the
     * employer NASSIT contribution (10% of each employee's basic salary).
     *
     * @param employees list of employees in the payroll run
     * @return total employer cost in SLE
     */
    public double calculateTotalEmployerCost(List<Employee> employees) {
        if (employees == null || employees.isEmpty()) {
            return 0.0;
        }
        double total = 0.0;
        for (Employee e : employees) {
            total += e.getIncome()
                    + complianceManager.calculateEmployerNASSIT(e.getBasicSalary());
        }
        return total;
    }
}