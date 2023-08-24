package HRS;

public class Main {
    public static void main(String[] args) {
        // Initialize key components
        EmployeeManager employeeManager = new EmployeeManager();
        LeaveManager leaveManager = new LeaveManager();
        PayrollCalculator payrollCalculator = new PayrollCalculator();
        TaxCalculator taxCalculator = new TaxCalculator();
        TaxReportGenerator taxReportGenerator = new TaxReportGenerator();
        StatutoryReportGenerator statutoryReportGenerator = new StatutoryReportGenerator();

        // Create and manage employees
        Employee employee1 = new Employee("20053", "Unisa", "Kamara");
        Employee employee2 = new Employee("11199", "Fatu", "Koroma");
        employeeManager.addEmployee(employee1);
        employeeManager.addEmployee(employee2);

        // Request leave for an employee
        LeaveRequest leaveRequest = new LeaveRequest(employee1, LeaveType.PAID_LEAVE, 5);
        leaveManager.equals(leaveRequest);

        // Calculate and display payroll
        double totalPayroll = payrollCalculator.calculateTotalPayroll(employeeManager.getAllEmployees());
        System.out.println("Total Payroll: SLE" + totalPayroll);

        // Calculate and display total tax
        double totalTax = taxCalculator.calculateTotalTax(employeeManager.getAllEmployees());
        System.out.println("Total Tax: SLE" + totalTax);

        // Generate and display tax report
        var taxReport = taxReportGenerator.generateTaxReport(totalTax);
        System.out.println("Tax Report:\n" + taxReport);

        // Generate and display statutory report
        String statutoryReport = statutoryReportGenerator.generateStatutoryReport(employeeManager);
        System.out.println("Statutory Report:\n" + statutoryReport);

        // Simulate user interaction - for demonstration purposes
        simulateUserInteraction(employeeManager, leaveManager);
    }

    // Simulate user interaction
    private static void simulateUserInteraction(EmployeeManager employeeManager, LeaveManager leaveManager) {
        // Assume user interaction or user interface interactions here
        // For example, allowing users to view employee information, approve leave requests, etc.
        // This is a simplified placeholder for user interactions.

        // Example: Viewing employee information
        Employee employee = employeeManager.getEmployeeByEmail("john@example.com");
        if (employee != null) {
            System.out.println("Employee Information:");
            System.out.println("Name: " + employee.getName());
            System.out.println("Email: " + employee.getEmail());
            System.out.println("Department: " + employee.getDepartment());
        }

        // Example: Approving leave request
        LeaveRequest leaveRequest = leaveManager.getPendingLeaveRequest();
        if (leaveRequest != null) {
            // Assume user approves the leave request
            leaveManager.approveLeave(leaveRequest);
            System.out.println("Leave request approved for employee: " + leaveRequest.getEmployee().getName());
        }
    }
}

