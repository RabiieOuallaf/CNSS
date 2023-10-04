package com.macnss.dao;

import com.macnss.Libs.Model;
import com.macnss.app.Enums.Gender;
import com.macnss.app.Models.user.Employee;
import com.macnss.interfaces.Dao.Dao;

import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class EmployeeDao extends Model implements Dao<Employee> {
    private final Employee employee;

    public EmployeeDao() {
        super("company_employee" , new String[]{"cnie"});
        employee = new Employee();
    }

    @Override
    public Employee read() {
        Map<String,Object> employeeData = super.read(new Object[]{employee.getCnie()});

        if(employeeData != null) {
            employee.setEmployee(

                    (String) employeeData.get("cnie"),
                    (String) employeeData.get("first_name"),
                    (String) employeeData.get("last_name"),
                    (Date) employeeData.get("birthday"),
                    Gender.valueOf((String) employeeData.get("gender")),
                    (String) employeeData.get("email"),
                    (String) employeeData.get("phone"),
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

                    (String) employeeData.get("cnie"),
                    (String) employeeData.get("first_name"),
                    (String) employeeData.get("last_name"),
                    (Date) employeeData.get("birthday"),
                    Gender.valueOf((String) employeeData.get("gender")),
                    (String) employeeData.get("email"),
                    (String) employeeData.get("phone"),
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
        Map<String, Object> employeeData = super.read(new String[]{"cnie"}, new String[]{cnie});

        if(employeeData == null) {
            return Optional.empty();
        }

        employee.setEmployee(

                (String) employeeData.get("cnie"),
                (String) employeeData.get("first_name"),
                (String) employeeData.get("last_name"),
                (Date) employeeData.get("birthday"),
                Gender.valueOf((String) employeeData.get("gender")),
                (String) employeeData.get("email"),
                (String) employeeData.get("phone"),
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

                    (String) employeeData.get("cnie"),
                    (String) employeeData.get("first_name"),
                    (String) employeeData.get("last_name"),
                    (Date) employeeData.get("birthday"),
                    Gender.valueOf((String) employeeData.get("gender")),
                    (String) employeeData.get("email"),
                    (String) employeeData.get("phone"),
                    (String) employeeData.get("password"),
                    (Integer) employeeData.get("working_days"),
                    (Integer) employeeData.get("company_id")


            );
            employees.add(employee);

        });
        return employees;
    }
}
