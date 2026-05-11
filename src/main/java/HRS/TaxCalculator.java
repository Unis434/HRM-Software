package HRS;

import java.util.List;

/**
 * Calculates income tax for individual employees and across a payroll run.
 * All tax logic is delegated to ComplianceManager — this class must not
 * duplicate bracket or rate definitions.
 */
public class TaxCalculator {

    private final ComplianceManager complianceManager;

    public TaxCalculator() {
        this.complianceManager = new ComplianceManager();
    }

    /**
     * Calculates PAYE on a given taxable income.
     * Taxable income should already have the NASSIT deduction removed.
     *
     * @param taxableIncome monthly taxable income in SLE
     * @return PAYE liability in SLE
     */
    public double calculateIncomeTax(double taxableIncome) {
        return complianceManager.calculatePAYETax(taxableIncome);
    }

    /**
     * Sums PAYE liabilities across all employees in a list.
     * Each employee's taxable income is derived as:
     *   gross income - employee NASSIT contribution
     *
     * @param employees list of employees to include in the calculation
     * @return total PAYE liability across all employees in SLE
     */
    public double calculateTotalTax(List<Employee> employees) {
        if (employees == null || employees.isEmpty()) {
            return 0.0;
        }
        double total = 0.0;
        for (Employee e : employees) {
            double nassit        = complianceManager.calculateEmployeeNASSIT(e.getBasicSalary());
            double taxableIncome = e.getIncome() - nassit;
            total               += complianceManager.calculatePAYETax(taxableIncome);
        }
        return total;
    }
}