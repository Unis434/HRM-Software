package HRS;

import java.util.Date; // Import necessary libraries for Date

public class Employee {
    private String employeeId;
    private String firstName;
    private String lastName;
    private String nationalId;
    private Date dateOfBirth;
    private String contactNumber;
    private String email;
    private String address;
    private Date hireDate;
    private String position;
    private double basicSalary;
    private String department;
    // Add other relevant attributes as needed

    // Constructors
    public Employee(String johnDoe, double v, double salary, double v1) {
        // Default constructor
    }

    public Employee(String employeeId, String firstName, String lastName) {
        this.employeeId = employeeId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.nationalId = nationalId;
        this.dateOfBirth = dateOfBirth;
        this.contactNumber = contactNumber;
        this.email = email;
        this.address = address;
        this.hireDate = hireDate;
        this.position = position;
        this.basicSalary = basicSalary;
        this.department = department;
    }

    public Employee(int id, String name, double salary, String department) {
    }

    // Getter and Setter methods for all attributes
    public String getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(String employeeId) {
        this.employeeId = employeeId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getNationalId() {
        return nationalId;
    }

    public void setNationalId(String nationalId) {
        this.nationalId = nationalId;
    }

    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getContactNumber() {
        return contactNumber;
    }

    public void setContactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Date getHireDate() {
        return hireDate;
    }

    public void setHireDate(Date hireDate) {
        this.hireDate = hireDate;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public double getBasicSalary() {
        return basicSalary;
    }

    public void setBasicSalary(double basicSalary) {
        this.basicSalary = basicSalary;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    // Additional methods specific to employee-related operations can be added here

    @Override
    public String toString() {
        return "Employee{" +
                "employeeId='" + employeeId + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", nationalId='" + nationalId + '\'' +
                ", dateOfBirth=" + dateOfBirth +
                ", contactNumber='" + contactNumber + '\'' +
                ", email='" + email + '\'' +
                ", address='" + address + '\'' +
                ", hireDate=" + hireDate +
                ", position='" + position + '\'' +
                ", basicSalary=" + basicSalary +
                ", department='" + department + '\'' +
                '}';
    }

    public double getTotalAllowances() {
        return  0;
    }

    public double getTransportAllowance() {
        return  0;
    }

    public double getTelephoneSubsidy() {
        return 0;
    }

    public double getUtilityAllowance() {
        return  0;
    }

    public double getEntertainment() {
        return 0;
    }

    public double getDomesticHelpAllowance() {
        return  0;
    }

    public double getLunchAllowance() {
        return 0;
    }

    public double getIncome() {
        return 0;
    }

    public String getName() {
        return null;
    }

    public double getSalary() {
        return 0;
    }

    public double getBonus() {
        return 0;
    }

    public double getReportedTax() {
        return 0;
    }
}

