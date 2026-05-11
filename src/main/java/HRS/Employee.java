package HRS;

import java.util.Date;

/**
 * Represents an employee in the system.
 * All fields are properly initialised to safe defaults.
 * Use the full constructor or the builder-style setters before
 * passing an Employee to any calculation class.
 */
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

    // Allowance fields
    private double transportAllowance;
    private double telephoneSubsidy;
    private double utilityAllowance;
    private double entertainment;
    private double domesticHelpAllowance;
    private double lunchAllowance;
    private double bonus;

    // Tax field: the tax amount this employee reported (used for compliance checks)
    private double reportedTax;

    // ------------------------------------------------------------------
    // Constructors
    // ------------------------------------------------------------------

    /**
     * Minimal constructor — used when only identity is known at creation time.
     * All numeric fields default to 0.0; String fields default to empty string.
     */
    public Employee(String employeeId, String firstName, String lastName) {
        this.employeeId      = employeeId != null ? employeeId : "";
        this.firstName       = firstName  != null ? firstName  : "";
        this.lastName        = lastName   != null ? lastName   : "";
        this.nationalId      = "";
        this.contactNumber   = "";
        this.email           = "";
        this.address         = "";
        this.position        = "";
        this.department      = "";
        this.basicSalary     = 0.0;
        this.bonus           = 0.0;
        this.reportedTax     = 0.0;
        this.transportAllowance   = 0.0;
        this.telephoneSubsidy     = 0.0;
        this.utilityAllowance     = 0.0;
        this.entertainment        = 0.0;
        this.domesticHelpAllowance = 0.0;
        this.lunchAllowance       = 0.0;
    }

    /**
     * Database hydration constructor — used by DatabaseManager when
     * reconstructing employees from a result set.
     */
    public Employee(int id, String name, double basicSalary, String department) {
        this(String.valueOf(id), name != null ? name : "", "");
        this.basicSalary = basicSalary;
        this.department  = department != null ? department : "";
    }

    /**
     * Compliance-check constructor — matches the signature previously
     * used in ComplianceManager. reportedTax is the figure the employee
     * declared; salary and bonus are gross components.
     */
    public Employee(String name, double basicSalary, double bonus, double reportedTax) {
        this("", name != null ? name : "", "");
        this.basicSalary = basicSalary;
        this.bonus       = bonus;
        this.reportedTax = reportedTax;
    }

    /**
     * Full constructor for complete employee records.
     */
    public Employee(String employeeId, String firstName, String lastName,
                    String nationalId, Date dateOfBirth, String contactNumber,
                    String email, String address, Date hireDate,
                    String position, double basicSalary, String department) {
        this(employeeId, firstName, lastName);
        this.nationalId    = nationalId    != null ? nationalId    : "";
        this.dateOfBirth   = dateOfBirth;
        this.contactNumber = contactNumber != null ? contactNumber : "";
        this.email         = email         != null ? email         : "";
        this.address       = address       != null ? address       : "";
        this.hireDate      = hireDate;
        this.position      = position      != null ? position      : "";
        this.basicSalary   = basicSalary;
        this.department    = department    != null ? department    : "";
    }

    // ------------------------------------------------------------------
    // Derived / computed getters
    // ------------------------------------------------------------------

    /** Full name for display purposes. */
    public String getName() {
        String full = (firstName + " " + lastName).trim();
        return full.isEmpty() ? employeeId : full;
    }

    /** Alias for basicSalary — keeps compatibility with older call sites. */
    public double getSalary() {
        return basicSalary;
    }

    /** Gross income = basic salary + all allowances + bonus. */
    public double getIncome() {
        return basicSalary + getTotalAllowances() + bonus;
    }

    /** Sum of all non-salary allowance components. */
    public double getTotalAllowances() {
        return transportAllowance
                + telephoneSubsidy
                + utilityAllowance
                + entertainment
                + domesticHelpAllowance
                + lunchAllowance;
    }

    // ------------------------------------------------------------------
    // Getters and setters
    // ------------------------------------------------------------------

    public String getEmployeeId()              { return employeeId; }
    public void   setEmployeeId(String v)      { this.employeeId = v != null ? v : ""; }

    public String getFirstName()               { return firstName; }
    public void   setFirstName(String v)       { this.firstName = v != null ? v : ""; }

    public String getLastName()                { return lastName; }
    public void   setLastName(String v)        { this.lastName = v != null ? v : ""; }

    public String getNationalId()              { return nationalId; }
    public void   setNationalId(String v)      { this.nationalId = v != null ? v : ""; }

    public Date   getDateOfBirth()             { return dateOfBirth; }
    public void   setDateOfBirth(Date v)       { this.dateOfBirth = v; }

    public String getContactNumber()           { return contactNumber; }
    public void   setContactNumber(String v)   { this.contactNumber = v != null ? v : ""; }

    public String getEmail()                   { return email; }
    public void   setEmail(String v)           { this.email = v != null ? v : ""; }

    public String getAddress()                 { return address; }
    public void   setAddress(String v)         { this.address = v != null ? v : ""; }

    public Date   getHireDate()                { return hireDate; }
    public void   setHireDate(Date v)          { this.hireDate = v; }

    public String getPosition()                { return position; }
    public void   setPosition(String v)        { this.position = v != null ? v : ""; }

    public double getBasicSalary()             { return basicSalary; }
    public void   setBasicSalary(double v)     { this.basicSalary = v; }

    public String getDepartment()              { return department; }
    public void   setDepartment(String v)      { this.department = v != null ? v : ""; }

    public double getBonus()                   { return bonus; }
    public void   setBonus(double v)           { this.bonus = v; }

    public double getReportedTax()             { return reportedTax; }
    public void   setReportedTax(double v)     { this.reportedTax = v; }

    public double getTransportAllowance()      { return transportAllowance; }
    public void   setTransportAllowance(double v) { this.transportAllowance = v; }

    public double getTelephoneSubsidy()        { return telephoneSubsidy; }
    public void   setTelephoneSubsidy(double v){ this.telephoneSubsidy = v; }

    public double getUtilityAllowance()        { return utilityAllowance; }
    public void   setUtilityAllowance(double v){ this.utilityAllowance = v; }

    public double getEntertainment()           { return entertainment; }
    public void   setEntertainment(double v)   { this.entertainment = v; }

    public double getDomesticHelpAllowance()   { return domesticHelpAllowance; }
    public void   setDomesticHelpAllowance(double v) { this.domesticHelpAllowance = v; }

    public double getLunchAllowance()          { return lunchAllowance; }
    public void   setLunchAllowance(double v)  { this.lunchAllowance = v; }

    // ------------------------------------------------------------------
    // Object overrides
    // ------------------------------------------------------------------

    @Override
    public String toString() {
        return "Employee{"
                + "employeeId='"   + employeeId   + '\''
                + ", name='"       + getName()     + '\''
                + ", department='" + department    + '\''
                + ", basicSalary=" + basicSalary
                + '}';
    }
}