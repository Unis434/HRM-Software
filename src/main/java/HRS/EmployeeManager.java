package HRS;

import java.util.ArrayList;
import java.util.List;

public class EmployeeManager {
    private List<Employee> employees;

    public EmployeeManager() {
        employees = new ArrayList<>();
        // Initialize employees from a database or other data source if needed
    }

    // Add an employee to the list
    public void addEmployee(Employee employee) {
        employees.add(employee);
        // You can also save the employee to a database or other data source here
    }

    // Get a list of all employees
    public List<Employee> getAllEmployees() {
        return employees;
    }

    // Find an employee by their employee ID
    public Employee findEmployeeById(String employeeId) {
        for (Employee employee : employees) {
            if (employee.getEmployeeId().equals(employeeId)) {
                return employee;
            }
        }
        return null; // Employee not found
    }

    // Update employee information
    public boolean updateEmployee(Employee updatedEmployee) {
        for (int i = 0; i < employees.size(); i++) {
            if (employees.get(i).getEmployeeId().equals(updatedEmployee.getEmployeeId())) {
                employees.set(i, updatedEmployee);
                // You can also update the employee in the database here if needed
                return true; // Employee updated successfully
            }
        }
        return false; // Employee not found, update failed
    }

    // Remove an employee
    public boolean removeEmployee(String employeeId) {
        for (Employee employee : employees) {
            if (employee.getEmployeeId().equals(employeeId)) {
                employees.remove(employee);
                // You can also delete the employee from the database here if needed
                return true; // Employee removed successfully
            }
        }
        return false; // Employee not found, removal failed
    }

    // Other methods for managing employees can be added as needed

    // Print a list of all employees (for debugging purposes)
    public void printAllEmployees() {
        for (Employee employee : employees) {
            System.out.println(employee);
        }
    }

    public Employee getEmployeeByEmail(String mail) {
        return null;
    }
}

