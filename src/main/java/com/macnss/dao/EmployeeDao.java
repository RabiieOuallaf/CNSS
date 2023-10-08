package com.macnss.dao;

import com.macnss.Libs.Model;
import com.macnss.app.Enums.EmployeeStatus;
import com.macnss.app.Enums.Gender;
import com.macnss.app.Models.user.Employee;
import com.macnss.interfaces.Dao.Dao;
import org.mindrot.jbcrypt.BCrypt;


import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class EmployeeDao extends Model implements Dao<Employee> {
    private final Employee employee;

    public EmployeeDao(Employee employee) {
        super("company_employee" , new String[]{"employee_matricule"});
        System.out.println(employee);

        this.employee = employee;
        System.out.println(employee);


    }

    @Override
    public Employee read() {
        Map<String,Object> employeeData = super.read(new Object[]{employee.getCnie()});

        if(employeeData != null) {
            employee.setEmployee(
                    (String) employeeData.get("employee_matricule"),
                    (String) employeeData.get("employee_firstname"),
                    (String) employeeData.get("employee_lastname"),
                    (EmployeeStatus) employeeData.get("status"),
                    (Date) employeeData.get("birthday"),
                    Gender.valueOf((String) employeeData.get("gender")),
                    (String) employeeData.get("employee_email"),
                    (String) employeeData.get("employee_phone"),
                    (String) employeeData.get("password"),
                    (Integer) employeeData.get("working_days"),
                    (Integer) employeeData.get("company_id")

            );

            return employee;
        }else {
            return null;
        }
    }

    @Override
    public Optional<Employee> save() throws SQLException {
        if(super.create(employee.getEmployee()) == null) {
            return Optional.empty();
        }else {
            return Optional.of(employee);
        }
    }

    @Override
    public Optional<Employee> create(Employee employee) throws SQLException {
        if (super.create(employee.getEmployee()) == null) {
            return Optional.empty();
        } else {
            return Optional.of(employee);
        }
    }

    public Optional<Employee> createEmployee(
            String employeeMatricule,
            String employeeFirstname,
            String employeeLastname,
            int companyId,
            EmployeeStatus status,
            int workingDays,
            Date birthday,
            String employeeEmail,
            String employeePhone,
            Gender gender,
            String password
    )throws SQLException {
        String hashPassword = BCrypt.hashpw(password,BCrypt.gensalt());

        String insertCompany = "INSERT INTO " + _table + " (employee_matricule, employee_firstname,employee_lastname, company_id,status, working_days, birthday, employee_email, employee_phone, gender, password) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        PreparedStatement preparedStatement = connection.prepareStatement(insertCompany);

        preparedStatement.setString(1, employeeMatricule);
        preparedStatement.setString(2, employeeFirstname);
        preparedStatement.setString(3, employeeLastname);
        preparedStatement.setInt(4, companyId);
        preparedStatement.setObject(5, status, Types.OTHER);
        preparedStatement.setInt(6, workingDays);
        preparedStatement.setDate(7, birthday); // Assuming birthday is a java.sql.Date
        preparedStatement.setString(8, employeeEmail);
        preparedStatement.setString(9, employeePhone);
        preparedStatement.setObject(10, gender,Types.OTHER);
        preparedStatement.setString(11, hashPassword);

        int rowsAffected = preparedStatement.executeUpdate();

        if(rowsAffected > 0) {
            employee.setEmployee(
                    employeeMatricule,
                    employeeFirstname,
                    employeeLastname,
                    status,
                    birthday,
                    gender,
                    employeeEmail,
                    employeePhone,
                    password,
                    workingDays,
                    companyId

                    );
            return Optional.of(employee);
        }
        return Optional.empty();
    }

    @Override
    public Optional<Employee> update(Employee employee) {
        if (super.update(employee.getEmployee(), new String[]{String.valueOf(employee.getCnie())})) {
            return Optional.of(employee);
        } else {
            return Optional.empty();
        }
    }

    @Override
    public List<Employee> find(String cnie) {
        List<Employee> employees = new ArrayList<>();

        List<Map<String, Object>> employeesData = super.readAll(new String[]{cnie});

        if (employeesData.isEmpty()) return employees;

        employeesData.forEach((employeeData) -> {
            Employee employee = new Employee();

            employee.setEmployee(
                    (String) employeeData.get("employee_matricule"),
                    (String) employeeData.get("employee_firstname"),
                    (String) employeeData.get("employee_lastname"),
                    (EmployeeStatus) employeeData.get("status"),
                    (Date) employeeData.get("birthday"),
                    Gender.valueOf((String) employeeData.get("gender")),
                    (String) employeeData.get("employee_email"),
                    (String) employeeData.get("employee_phone"),
                    (String) employeeData.get("password"),
                    (Integer) employeeData.get("working_days"),
                    (Integer) employeeData.get("company_id")
            );

            employees.add(employee);
        });

        return employees;
    }

    @Override
    public boolean delete(Employee employee) {
        return super.delete(new String[]{String.valueOf(employee.getEmployee())});
    }



    public Optional<Employee> get(String cnie) {
        Map<String, Object> employeeData = super.read(new String[]{"employee_matricule"}, new String[]{cnie});
        if(employeeData == null) {
            return Optional.empty();
        }

        employee.setEmployee(

                (String) employeeData.get("employee_matricule"),
                (String) employeeData.get("employee_firstname"),
                (String) employeeData.get("employee_lastname"),
                (EmployeeStatus) employeeData.get("status"),
                (Date) employeeData.get("birthday"),
                Gender.valueOf((String) employeeData.get("gender")),
                (String) employeeData.get("employee_email"),
                (String) employeeData.get("employee_phone"),
                (String) employeeData.get("password"),
                (Integer) employeeData.get("working_days"),
                (Integer) employeeData.get("company_id")


        );

        return Optional.of(employee);
    }

    public List<Employee> getAll() {
        List<Employee> employees = new ArrayList<>();
        List<Map<String,Object>> employeesData = super.retrieveAll();
        if(employeesData.isEmpty()) return null;

        employeesData.forEach( (employeeData) -> {
            Employee employee = new Employee();

            employee.setEmployee(

                    (String) employeeData.get("employee_matricule"),
                    (String) employeeData.get("employee_firstname"),
                    (String) employeeData.get("employee_lastname"),
                    (EmployeeStatus) employeeData.get("status"),
                    (Date) employeeData.get("birthday"),
                    Gender.valueOf((String) employeeData.get("gender")),
                    (String) employeeData.get("employee_email"),
                    (String) employeeData.get("employee_phone"),
                    (String) employeeData.get("password"),
                    (Integer) employeeData.get("working_days"),
                    (Integer) employeeData.get("company_id")

            );

            employees.add(employee);


        });
        return employees;
    }

    public List<Employee> getEmployeesByCompany(int company_id) throws SQLException {
        List<Employee> employees = new ArrayList<>();
        String selectQuery = "SELECT * FROM company_employee WHERE company_id = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(selectQuery);
        preparedStatement.setInt(1, company_id);

        ResultSet resultSet = preparedStatement.executeQuery();

        while (resultSet.next()) {
            Employee employee = new Employee();
            employee.setEmployee(
                    resultSet.getString("employee_matricule"),
                    resultSet.getString("employee_firstname"),
                    resultSet.getString("employee_lastname"),
                    EmployeeStatus.valueOf(resultSet.getString("status")),
                    resultSet.getDate("birthday"),
                    Gender.valueOf(resultSet.getString("gender")),
                    resultSet.getString("employee_email"),
                    resultSet.getString("employee_phone"),
                    resultSet.getString("password"),
                    resultSet.getInt("working_days"),
                    resultSet.getInt("company_id")
            );
            employees.add(employee);
        }

        resultSet.close();
        preparedStatement.close();

        return employees;
    }
}
