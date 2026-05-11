package HRS;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

/**
 * Manages all database interactions for the HRS system.
 *
 * The connection URL, username, and password are obtained from
 * ConfigurationManager (which reads from environment variables).
 * No credentials are hardcoded here.
 *
 * Default database: SQLite (suitable for single-user desktop deployment).
 * For multi-user environments, set HRS_DB_URL to a PostgreSQL JDBC URL.
 */
public class DatabaseManager {

    private static final Logger LOGGER = Logger.getLogger(DatabaseManager.class.getName());

    private Connection connection;

    // ------------------------------------------------------------------
    // Construction and connection
    // ------------------------------------------------------------------

    /**
     * Opens a database connection using settings from ConfigurationManager.
     */
    public DatabaseManager() {
        ConfigurationManager config = new ConfigurationManager();
        String url = config.getDatabaseUrl();
        connect(url, null, null);
    }

    /**
     * Opens a database connection with an explicit JDBC URL.
     * Username and password are loaded from environment variables.
     *
     * @param databaseUrl JDBC URL (e.g. "jdbc:sqlite:hrs.db")
     */
    public DatabaseManager(String databaseUrl) {
        connect(databaseUrl, null, null);
    }

    private void connect(String url, String username, String password) {
        try {
            if (url.startsWith("jdbc:sqlite:")) {
                Class.forName("org.sqlite.JDBC");
                connection = DriverManager.getConnection(url);
            } else {
                // PostgreSQL or other JDBC driver
                connection = (username != null)
                        ? DriverManager.getConnection(url, username, password)
                        : DriverManager.getConnection(url);
            }
            LOGGER.info("Database connection established: " + url);
            createTables();
        } catch (ClassNotFoundException e) {
            LOGGER.severe("JDBC driver not found: " + e.getMessage());
        } catch (SQLException e) {
            LOGGER.severe("Failed to connect to database: " + e.getMessage());
        }
    }

    // ------------------------------------------------------------------
    // Schema creation
    // ------------------------------------------------------------------

    private void createTables() {
        createEmployeeTable();
        createPayrollTable();
    }

    private void createEmployeeTable() {
        String sql = "CREATE TABLE IF NOT EXISTS employee ("
                + "id         INTEGER PRIMARY KEY AUTOINCREMENT,"
                + "emp_id     TEXT NOT NULL UNIQUE,"
                + "first_name TEXT NOT NULL,"
                + "last_name  TEXT NOT NULL,"
                + "email      TEXT,"
                + "department TEXT,"
                + "position   TEXT,"
                + "basic_salary REAL NOT NULL DEFAULT 0)";
        executeUpdate(sql, "employee table");
    }

    private void createPayrollTable() {
        String sql = "CREATE TABLE IF NOT EXISTS payroll ("
                + "id             INTEGER PRIMARY KEY AUTOINCREMENT,"
                + "employee_id    TEXT    NOT NULL,"
                + "period_month   TEXT    NOT NULL,"
                + "period_year    INTEGER NOT NULL,"
                + "basic_salary   REAL    NOT NULL,"
                + "total_allowances REAL  NOT NULL DEFAULT 0,"
                + "total_deductions REAL  NOT NULL DEFAULT 0,"
                + "net_salary     REAL    NOT NULL,"
                + "created_at     TEXT    DEFAULT (datetime('now')),"
                + "FOREIGN KEY (employee_id) REFERENCES employee(emp_id))";
        executeUpdate(sql, "payroll table");
    }

    private void executeUpdate(String sql, String context) {
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.executeUpdate();
        } catch (SQLException e) {
            LOGGER.severe("Failed to create " + context + ": " + e.getMessage());
        }
    }

    // ------------------------------------------------------------------
    // Employee operations
    // ------------------------------------------------------------------

    /**
     * Inserts a new employee record into the database.
     *
     * @param employee the employee to persist
     */
    public void insertEmployee(Employee employee) {
        String sql = "INSERT INTO employee (emp_id, first_name, last_name, email, department, position, basic_salary) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, employee.getEmployeeId());
            stmt.setString(2, employee.getFirstName());
            stmt.setString(3, employee.getLastName());
            stmt.setString(4, employee.getEmail());
            stmt.setString(5, employee.getDepartment());
            stmt.setString(6, employee.getPosition());
            stmt.setDouble(7, employee.getBasicSalary());
            stmt.executeUpdate();
        } catch (SQLException e) {
            LOGGER.severe("insertEmployee failed: " + e.getMessage());
        }
    }

    /**
     * Returns all employees currently in the database.
     *
     * @return list of Employee objects; empty list if none found or on error
     */
    public List<Employee> getAllEmployees() {
        List<Employee> employees = new ArrayList<>();
        String sql = "SELECT emp_id, first_name, last_name, email, department, position, basic_salary FROM employee";
        try (PreparedStatement stmt = connection.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                Employee e = new Employee(
                        rs.getString("emp_id"),
                        rs.getString("first_name"),
                        rs.getString("last_name")
                );
                e.setEmail(rs.getString("email"));
                e.setDepartment(rs.getString("department"));
                e.setPosition(rs.getString("position"));
                e.setBasicSalary(rs.getDouble("basic_salary"));
                employees.add(e);
            }
        } catch (SQLException e) {
            LOGGER.severe("getAllEmployees failed: " + e.getMessage());
        }
        return employees;
    }

    // ------------------------------------------------------------------
    // Payroll operations
    // ------------------------------------------------------------------

    /**
     * Inserts a completed payroll record for audit and reporting purposes.
     *
     * @param payroll     the calculated payroll object
     * @param periodMonth month label (e.g. "January")
     * @param periodYear  four-digit year (e.g. 2025)
     */
    public void insertPayrollRecord(Payroll payroll, String periodMonth, int periodYear) {
        String sql = "INSERT INTO payroll "
                + "(employee_id, period_month, period_year, basic_salary, total_allowances, total_deductions, net_salary) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, payroll.getEmployeeId());
            stmt.setString(2, periodMonth);
            stmt.setInt(3,    periodYear);
            stmt.setDouble(4, payroll.getBasicSalary());
            stmt.setDouble(5, payroll.getAllowances());
            stmt.setDouble(6, payroll.getDeductions());
            stmt.setDouble(7, payroll.getNetSalary());
            stmt.executeUpdate();
        } catch (SQLException e) {
            LOGGER.severe("insertPayrollRecord failed: " + e.getMessage());
        }
    }

    // ------------------------------------------------------------------
    // Connection management
    // ------------------------------------------------------------------

    /**
     * Closes the database connection. Call this when the application shuts down.
     */
    public void close() {
        if (connection != null) {
            try {
                connection.close();
                LOGGER.info("Database connection closed.");
            } catch (SQLException e) {
                LOGGER.warning("Error closing database connection: " + e.getMessage());
            }
        }
    }
}