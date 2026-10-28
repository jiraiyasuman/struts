package com.employeecrudstrut.controller;

import com.employeecrudstrut.dao.EmployeeDAO;
import com.employeecrudstrut.entity.Employee;


import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.Collections;
import java.util.List;
import java.util.regex.Pattern;

import org.apache.struts2.ActionSupport;

public class EmployeeAction extends ActionSupport {

    private static final long serialVersionUID = 1L;

    private final EmployeeDAO dao = new EmployeeDAO();

    // Employee form fields
    private int id;
    private String name;
    private String email;
    private String department;
    private String salary;

    // Data exposed to JSP pages
    private Employee employee;
    private List<Employee> employees;
    private String message;

    private static final Pattern EMAIL_PATTERN =
            Pattern.compile(
                    "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$"
            );

    // --------------------------------------------------
    // LIST ALL EMPLOYEES
    // URL: /employees.action
    // --------------------------------------------------
    public String execute() {
        return employees();
    }

    public String employees() {
        try {
            employees = dao.findAll();
            return SUCCESS;
        } catch (SQLException e) {
            e.printStackTrace();
            addActionError("Unable to retrieve employees from the database.");
            return ERROR;
        }
    }

    // --------------------------------------------------
    // DISPLAY THE ADD EMPLOYEE FORM
    // URL: /input.action
    // --------------------------------------------------
    public String input() {
        clearFormFields();
        return INPUT;
    }

    // --------------------------------------------------
    // CREATE AN EMPLOYEE
    // URL: /create.action
    // --------------------------------------------------
    public String create() {

        // Temporary diagnostic logs: check whether Struts
        // successfully populated the form fields.
        System.out.println("===== CREATE ACTION DEBUG =====");
        System.out.println("ID: [" + id + "]");
        System.out.println("Name: [" + name + "]");
        System.out.println("Email: [" + email + "]");
        System.out.println("Department: [" + department + "]");
        System.out.println("Salary: [" + salary + "]");

        if (!validateEmployee()) {
            System.out.println("Validation failed.");
            return INPUT;
        }

        try {
            if (dao.emailExists(email.trim(), 0)) {
                addFieldError("email", "Email already exists.");
                return INPUT;
            }

            Employee newEmployee = toEmployee();

            dao.insert(newEmployee);

            message = "Employee created successfully.";

            return SUCCESS;

        } catch (SQLException e) {
            e.printStackTrace();
            addActionError(
                    "Unable to save the employee. Please check the database connection and input values."
            );
            return ERROR;
        }
    }

    // --------------------------------------------------
    // LOAD AN EMPLOYEE FOR EDITING
    // URL: /edit.action?id=1
    // --------------------------------------------------
    public String edit() {

        if (id <= 0) {
            addActionError("Invalid employee ID.");
            return ERROR;
        }

        try {
            employee = dao.findById(id);

            if (employee == null) {
                addActionError("Employee not found.");
                return ERROR;
            }

            // Populate the form fields
            name = employee.getName();
            email = employee.getEmail();
            department = employee.getDepartment();

            // Convert the numeric salary to a string
            salary = employee.getSalary() == null
                    ? ""
                    : employee.getSalary().toString();

            return INPUT;

        } catch (SQLException e) {
            e.printStackTrace();
            addActionError("Unable to load the employee.");
            return ERROR;
        }
    }

    // --------------------------------------------------
    // UPDATE AN EMPLOYEE
    // URL: /update.action
    // --------------------------------------------------
    public String update() {

        System.out.println("===== UPDATE ACTION DEBUG =====");
        System.out.println("ID: [" + id + "]");
        System.out.println("Name: [" + name + "]");
        System.out.println("Email: [" + email + "]");
        System.out.println("Department: [" + department + "]");
        System.out.println("Salary: [" + salary + "]");

        if (id <= 0) {
            addActionError("Invalid employee ID.");
            return ERROR;
        }

        if (!validateEmployee()) {
            return INPUT;
        }

        try {
            if (dao.emailExists(email.trim(), id)) {
                addFieldError("email", "Email already exists.");
                return INPUT;
            }

            Employee updatedEmployee = toEmployee();

            dao.update(updatedEmployee);

            message = "Employee updated successfully.";

            return SUCCESS;

        } catch (SQLException e) {
            e.printStackTrace();
            addActionError("Unable to update the employee.");
            return ERROR;
        }
    }

    // --------------------------------------------------
    // DELETE AN EMPLOYEE
    // URL: /delete.action?id=1
    // --------------------------------------------------
    public String delete() {

        if (id <= 0) {
            addActionError("Invalid employee ID.");
            return ERROR;
        }

        try {
            Employee existingEmployee = dao.findById(id);

            if (existingEmployee == null) {
                addActionError("Employee not found.");
                return ERROR;
            }

            dao.delete(id);

            message = "Employee deleted successfully.";

            return SUCCESS;

        } catch (SQLException e) {
            e.printStackTrace();
            addActionError("Unable to delete the employee.");
            return ERROR;
        }
    }

    // --------------------------------------------------
    // VALIDATE FORM FIELDS
    // --------------------------------------------------
    private boolean validateEmployee() {

        boolean valid = true;

        // Name validation
        if (name == null || name.trim().isEmpty()) {

            addFieldError("name", "Name is required.");
            valid = false;

        } else if (name.trim().length() > 100) {

            addFieldError(
                    "name",
                    "Name must not exceed 100 characters."
            );
            valid = false;
        }

        // Email validation
        if (email == null || email.trim().isEmpty()) {

            addFieldError("email", "Email is required.");
            valid = false;

        } else if (!EMAIL_PATTERN.matcher(email.trim()).matches()) {

            addFieldError(
                    "email",
                    "Please enter a valid email address."
            );
            valid = false;

        } else if (email.trim().length() > 150) {

            addFieldError(
                    "email",
                    "Email must not exceed 150 characters."
            );
            valid = false;
        }

        // Department validation
        if (department == null || department.trim().isEmpty()) {

            addFieldError(
                    "department",
                    "Department is required."
            );
            valid = false;

        } else if (department.trim().length() > 100) {

            addFieldError(
                    "department",
                    "Department must not exceed 100 characters."
            );
            valid = false;
        }

        // Salary validation
        if (salary == null || salary.trim().isEmpty()) {

            addFieldError("salary", "Salary is required.");
            valid = false;

        } else {
            try {
                BigDecimal salaryValue =
                        new BigDecimal(salary.trim());

                if (salaryValue.compareTo(BigDecimal.ZERO) < 0) {

                    addFieldError(
                            "salary",
                            "Salary cannot be negative."
                    );
                    valid = false;

                } else if (salaryValue.scale() > 2) {

                    addFieldError(
                            "salary",
                            "Salary can have at most two decimal places."
                    );
                    valid = false;

                } else if (salaryValue.precision() > 12) {

                    addFieldError(
                            "salary",
                            "Salary is too large."
                    );
                    valid = false;
                }

            } catch (NumberFormatException e) {

                addFieldError(
                        "salary",
                        "Salary must be a valid number."
                );
                valid = false;
            }
        }

        return valid;
    }

    // --------------------------------------------------
    // CONVERT FORM DATA INTO EMPLOYEE MODEL
    // --------------------------------------------------
    private Employee toEmployee() {

        Employee e = new Employee();

        e.setId(id);
        e.setName(name.trim());
        e.setEmail(email.trim());
        e.setDepartment(department.trim());
        e.setSalary(new BigDecimal(salary.trim()));

        return e;
    }

    // --------------------------------------------------
    // CLEAR FORM FIELDS
    // --------------------------------------------------
    private void clearFormFields() {

        id = 0;
        name = "";
        email = "";
        department = "";
        salary = "";

        employee = null;
        clearErrorsAndMessages();
    }

    // --------------------------------------------------
    // GETTERS AND SETTERS
    // Struts uses these to bind submitted form parameters.
    // --------------------------------------------------

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getSalary() {
        return salary;
    }

    public void setSalary(String salary) {
        this.salary = salary;
    }

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public List<Employee> getEmployees() {
        return employees == null
                ? Collections.emptyList()
                : employees;
    }

    public void setEmployees(List<Employee> employees) {
        this.employees = employees;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}