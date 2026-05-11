package HRS;

/**
 * Application entry point.
 * Demonstrates correct wiring of all HRS components after bug fixes.
 */
public class Main {

    public static void main(String[] args) {

        // Initialise core managers
        EmployeeManager    employeeManager    = new EmployeeManager();
        LeaveManager       leaveManager       = new LeaveManager();
        PayrollCalculator  payrollCalculator  = new PayrollCalculator();
        TaxCalculator      taxCalculator      = new TaxCalculator();

        // Create employees using the corrected minimal constructor
        Employee employee1 = new Employee("20053", "Unisa", "Kamara");
        employee1.setDepartment("Finance");
        employee1.setPosition("Finance Officer");
        employee1.setBasicSalary(3_500_000.0);
        employee1.setTransportAllowance(300_000.0);
        employee1.setLunchAllowance(150_000.0);

        Employee employee2 = new Employee("11199", "Fatu", "Koroma");
        employee2.setDepartment("Administration");
        employee2.setPosition("Admin Officer");
        employee2.setBasicSalary(2_200_000.0);
        employee2.setTransportAllowance(200_000.0);

        employeeManager.addEmployee(employee1);
        employeeManager.addEmployee(employee2);

        // Request leave — using the corrected LeaveRequest constructor
        LeaveRequest leaveRequest = new LeaveRequest("REQ001", employee1.getEmployeeId(),
                new java.util.Date(), new java.util.Date());
        leaveManager.addLeaveRequest(leaveRequest);

        // Calculate and display payroll for each employee
        System.out.println("=== Payroll Run ===");
        for (Employee e : employeeManager.getAllEmployees()) {
            Payroll payroll = payrollCalculator.calculatePayroll(e);
            System.out.println(payroll);
            System.out.println();
        }

        // Total payroll and tax
        double totalPayroll = payrollCalculator.calculateTotalPayroll(employeeManager.getAllEmployees());
        double totalTax     = taxCalculator.calculateTotalTax(employeeManager.getAllEmployees());
        System.out.printf("Total net payroll: SLE %,.2f%n", totalPayroll);
        System.out.printf("Total PAYE:        SLE %,.2f%n", totalTax);
        System.out.println();

        // Generate reports — employee list injected at construction time
        TaxReportGenerator taxReportGenerator =
                new TaxReportGenerator(employeeManager.getAllEmployees());
        taxReportGenerator.generateTaxReport();

        StatutoryReportGenerator statutoryReportGenerator =
                new StatutoryReportGenerator(employeeManager.getAllEmployees());
        statutoryReportGenerator.generateStatutoryReport(null);
    }
}