package com.employeecrudstrut.dao;

import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import com.employeecrudstrut.connection.DbConnection;
import com.employeecrudstrut.entity.Employee;

public class EmployeeDAO {

    // CREATE
    public boolean insert(Employee e) throws SQLException {
        String sql = """
            INSERT INTO employees
                (name, email, department, salary)
            VALUES (?, ?, ?, ?)
            """;

        try (Connection con = DbConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, e.getName().trim());
            ps.setString(2, e.getEmail().trim());
            ps.setString(3, e.getDepartment().trim());
            ps.setBigDecimal(4, e.getSalary());

            return ps.executeUpdate() == 1;
        }
    }

    // READ ALL
    public List<Employee> findAll() throws SQLException {
        List<Employee> employees = new ArrayList<>();

        String sql = """
            SELECT id, name, email, department, salary
            FROM employees
            ORDER BY id DESC
            """;

        try (Connection con = DbConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                employees.add(mapEmployee(rs));
            }
        }

        return employees;
    }

    // READ ONE
    public Employee findById(int id) throws SQLException {
        String sql = """
            SELECT id, name, email, department, salary
            FROM employees
            WHERE id = ?
            """;

        try (Connection con = DbConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, id);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return mapEmployee(rs);
                }
            }
        }

        return null;
    }

    // UPDATE
    public boolean update(Employee e) throws SQLException {
        String sql = """
            UPDATE employees
            SET name = ?, email = ?, department = ?, salary = ?
            WHERE id = ?
            """;

        try (Connection con = DbConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, e.getName().trim());
            ps.setString(2, e.getEmail().trim());
            ps.setString(3, e.getDepartment().trim());
            ps.setBigDecimal(4, e.getSalary());
            ps.setInt(5, e.getId());

            return ps.executeUpdate() == 1;
        }
    }

    // DELETE
    public boolean delete(int id) throws SQLException {
        String sql = "DELETE FROM employees WHERE id = ?";

        try (Connection con = DbConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, id);
            return ps.executeUpdate() == 1;
        }
    }

    // Check duplicate email, excluding the current employee during update.
    public boolean emailExists(String email, int excludeId)
            throws SQLException {

        String sql = """
            SELECT COUNT(*)
            FROM employees
            WHERE email = ? AND id <> ?
            """;

        try (Connection con = DbConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, email.trim());
            ps.setInt(2, excludeId);

            try (ResultSet rs = ps.executeQuery()) {
                rs.next();
                return rs.getInt(1) > 0;
            }
        }
    }

    private Employee mapEmployee(ResultSet rs) throws SQLException {
        return new Employee(
            rs.getInt("id"),
            rs.getString("name"),
            rs.getString("email"),
            rs.getString("department"),
            rs.getBigDecimal("salary")
        );
    }
}
