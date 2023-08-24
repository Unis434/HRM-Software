package HRS;

public class Payroll {
    private String employeeId;
    private double basicSalary;
    private double allowances;
    private double deductions;
    private double netSalary;

    public Payroll(String employeeId, double basicSalary, double allowances, double deductions, double netSalary) {
        this.employeeId = employeeId;
        this.basicSalary = basicSalary;
        this.allowances = allowances;
        this.deductions = deductions;
        calculateNetSalary(); // Calculate net salary upon creation
    }

    public String getEmployeeId() {
        return employeeId;
    }

    public double getBasicSalary() {
        return basicSalary;
    }

    public void setBasicSalary(double basicSalary) {
        this.basicSalary = basicSalary;
        calculateNetSalary();
    }

    public double getAllowances() {
        return allowances;
    }

    public void setAllowances(double allowances) {
        this.allowances = allowances;
        calculateNetSalary();
    }

    public double getDeductions() {
        return deductions;
    }

    public void setDeductions(double deductions) {
        this.deductions = deductions;
        calculateNetSalary();
    }

    public double getNetSalary() {
        return netSalary;
    }

    private void calculateNetSalary() {
        // Calculate net salary by subtracting deductions from the sum of basic salary and allowances
        netSalary = basicSalary + allowances - deductions;
    }

    @Override
    public String toString() {
        return "Payroll for Employee ID: " + employeeId +
                "\nBasic Salary: $" + basicSalary +
                "\nAllowances: $" + allowances +
                "\nDeductions: $" + deductions +
                "\nNet Salary: $" + netSalary;
    }
}


