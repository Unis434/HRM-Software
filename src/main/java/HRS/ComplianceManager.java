package HRS;

/**
 * Single canonical source of truth for Sierra Leone statutory deduction logic.
 *
 * PAYE brackets — National Revenue Authority (NRA), current rates:
 *   First Le 600,000        0%   (exempt threshold)
 *   Le 600,001 - 1,200,000  15%
 *   Le 1,200,001 - 1,800,000 20%
 *   Le 1,800,001 - 2,400,000 30%
 *   Above Le 2,400,000       35%
 *
 * NASSIT contributions (National Social Security and Insurance Trust):
 *   Employee: 5% of basic salary
 *   Employer: 10% of basic salary
 *
 * All monetary values are in Sierra Leonean Leones (SLE).
 * TaxReportGenerator and StatutoryReportGenerator must delegate
 * to this class — they must NOT implement their own tax logic.
 */
public class ComplianceManager {

    // ------------------------------------------------------------------
    // NRA PAYE constants
    // ------------------------------------------------------------------

    private static final double EXEMPT_THRESHOLD = 600_000.0;
    private static final double BAND_1_LIMIT     = 1_200_000.0;
    private static final double BAND_2_LIMIT     = 1_800_000.0;
    private static final double BAND_3_LIMIT     = 2_400_000.0;

    private static final double RATE_BAND_1 = 0.15;
    private static final double RATE_BAND_2 = 0.20;
    private static final double RATE_BAND_3 = 0.30;
    private static final double RATE_BAND_4 = 0.35;

    // ------------------------------------------------------------------
    // NASSIT constants
    // ------------------------------------------------------------------

    public static final double NASSIT_EMPLOYEE_RATE = 0.05;
    public static final double NASSIT_EMPLOYER_RATE = 0.10;

    // ------------------------------------------------------------------
    // PAYE calculation
    // ------------------------------------------------------------------

    /**
     * Calculates the monthly PAYE liability on a given taxable income.
     * Taxable income = basic salary + allowances - NASSIT employee contribution.
     *
     * @param taxableIncome monthly taxable income in SLE
     * @return PAYE tax due in SLE
     */
    public double calculatePAYETax(double taxableIncome) {
        if (taxableIncome <= EXEMPT_THRESHOLD) {
            return 0.0;
        }

        double tax            = 0.0;
        double remaining      = taxableIncome - EXEMPT_THRESHOLD;

        // Band 1: Le 600,001 - 1,200,000 (width = 600,000)
        double band1Width = BAND_1_LIMIT - EXEMPT_THRESHOLD;
        if (remaining <= band1Width) {
            return remaining * RATE_BAND_1;
        }
        tax      += band1Width * RATE_BAND_1;
        remaining -= band1Width;

        // Band 2: Le 1,200,001 - 1,800,000 (width = 600,000)
        double band2Width = BAND_2_LIMIT - BAND_1_LIMIT;
        if (remaining <= band2Width) {
            return tax + remaining * RATE_BAND_2;
        }
        tax      += band2Width * RATE_BAND_2;
        remaining -= band2Width;

        // Band 3: Le 1,800,001 - 2,400,000 (width = 600,000)
        double band3Width = BAND_3_LIMIT - BAND_2_LIMIT;
        if (remaining <= band3Width) {
            return tax + remaining * RATE_BAND_3;
        }
        tax      += band3Width * RATE_BAND_3;
        remaining -= band3Width;

        // Band 4: above Le 2,400,000
        tax += remaining * RATE_BAND_4;
        return tax;
    }

    // ------------------------------------------------------------------
    // NASSIT calculation
    // ------------------------------------------------------------------

    /**
     * Calculates the employee NASSIT deduction (5% of basic salary).
     *
     * @param basicSalary monthly basic salary in SLE
     * @return employee NASSIT contribution in SLE
     */
    public double calculateEmployeeNASSIT(double basicSalary) {
        return basicSalary * NASSIT_EMPLOYEE_RATE;
    }

    /**
     * Calculates the employer NASSIT contribution (10% of basic salary).
     * This is a cost to the organisation, not a deduction from the employee.
     *
     * @param basicSalary monthly basic salary in SLE
     * @return employer NASSIT contribution in SLE
     */
    public double calculateEmployerNASSIT(double basicSalary) {
        return basicSalary * NASSIT_EMPLOYER_RATE;
    }

    /**
     * Calculates total NASSIT (employee + employer) for reporting purposes.
     *
     * @param basicSalary monthly basic salary in SLE
     * @return combined NASSIT contribution in SLE
     */
    public double calculateTotalNASSIT(double basicSalary) {
        return calculateEmployeeNASSIT(basicSalary) + calculateEmployerNASSIT(basicSalary);
    }

    // ------------------------------------------------------------------
    // Compliance check
    // ------------------------------------------------------------------

    /**
     * Checks whether the PAYE tax reported by an employee matches the
     * amount calculated under current NRA rules.
     * Uses a small tolerance to account for rounding differences.
     *
     * @param employee    the employee whose reported tax is checked
     * @param totalIncome gross monthly income (basic + allowances + bonus)
     * @return true if the employee's reported tax is within Le 1.00 of the calculated amount
     */
    public boolean isTaxCompliant(Employee employee, double totalIncome) {
        double nassit          = calculateEmployeeNASSIT(employee.getBasicSalary());
        double taxableIncome   = totalIncome - nassit;
        double calculatedTax   = calculatePAYETax(taxableIncome);
        return Math.abs(employee.getReportedTax() - calculatedTax) <= 1.0;
    }

    // ------------------------------------------------------------------
    // Demo
    // ------------------------------------------------------------------

    public static void main(String[] args) {
        ComplianceManager cm = new ComplianceManager();

        // Employee earning Le 2,000,000 basic, no allowances
        double basicSalary   = 2_000_000.0;
        double nassit        = cm.calculateEmployeeNASSIT(basicSalary);
        double taxableIncome = basicSalary - nassit;
        double paye          = cm.calculatePAYETax(taxableIncome);

        System.out.printf("Basic salary:     SLE %,.2f%n", basicSalary);
        System.out.printf("NASSIT (employee):SLE %,.2f%n", nassit);
        System.out.printf("Taxable income:   SLE %,.2f%n", taxableIncome);
        System.out.printf("PAYE:             SLE %,.2f%n", paye);
        System.out.printf("Net pay:          SLE %,.2f%n", basicSalary - nassit - paye);
    }
}