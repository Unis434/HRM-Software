package HRS;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DatabaseManager {
    private Connection connection;

    // Constructor to initialize the database connection
    public DatabaseManager(String databaseUrl) {
        try {
            // Load the SQLite JDBC driver
            Class.forName("org.sqlite.JDBC");

            // Connect to the SQLite database
            connection = DriverManager.getConnection("jdbc:sqlite:" + databaseUrl);

            // Create tables if they don't exist
            createEmployeeTable();
            createPayrollTable();
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }

    // Create the Employee table if it doesn't exist
    private void createEmployeeTable() {
        String createTableSQL = "CREATE TABLE IF NOT EXISTS employee (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "name TEXT NOT NULL," +
                "salary DOUBLE NOT NULL," +
                "department TEXT NOT NULL)";

        try (PreparedStatement statement = connection.prepareStatement(createTableSQL)) {
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Create the Payroll table if it doesn't exist
    private void createPayrollTable() {
        String createTableSQL = "CREATE TABLE IF NOT EXISTS payroll (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "employee_id INTEGER NOT NULL," +
                "month TEXT NOT NULL," +
                "year INTEGER NOT NULL," +
                "total_salary DOUBLE NOT NULL)";

        try (PreparedStatement statement = connection.prepareStatement(createTableSQL)) {
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Insert a new employee into the database
    public void insertEmployee(Employee employee) {
        String insertSQL = "INSERT INTO employee (name, salary, department) VALUES (?, ?, ?)";

        try (PreparedStatement statement = connection.prepareStatement(insertSQL)) {
            statement.setString(1, employee.getName());
            statement.setDouble(2, employee.getSalary());
            statement.setString(3, employee.getDepartment());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Retrieve all employees from the database
    public List<Employee> getAllEmployees() {
        List<Employee> employees = new ArrayList<>();
        String selectSQL = "SELECT * FROM employee";

        try (PreparedStatement statement = connection.prepareStatement(selectSQL);
             ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                double salary = resultSet.getDouble("salary");
                String department = resultSet.getString("department");
                employees.add(new Employee(id, name, salary, department));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return employees;
    }}

// Insert a new payroll record into

