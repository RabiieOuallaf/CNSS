package com.macnss.dao;

import com.macnss.Libs.Model;
import com.macnss.app.Enums.EmployeeStatus;
import com.macnss.app.Models.Static.BasicSalary;
import com.macnss.app.Models.Static.RetraitSalary;
import com.macnss.app.Models.user.Employee;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.text.DateFormat;
import java.util.*;

public class BasicSalaryDao extends Model {

    private final BasicSalary basicSalary;

    public BasicSalaryDao(BasicSalary basicSalary) {
        super("base_salary", new String[]{"salary_id"});
        this.basicSalary = basicSalary;
    }
    public List<BasicSalary> setSalary(Date birthday,String employeeMatricule, float monthlySalary, int totalWorkingDays) throws SQLException {
        List<BasicSalary> salaryRecords = new ArrayList<>();

        if (totalWorkingDays <= 0) {
            return salaryRecords; // No need to insert records for zero or negative working days.
        }

        int monthsWorked = totalWorkingDays / 26; // Assuming 26 working days per month.

        // Get the current date.
        Date currentDate = new Date();

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(currentDate);

        // Calculate the start and end dates for each month and insert records.
        for (int i = 0; i < monthsWorked; i++) {
            calendar.set(Calendar.DAY_OF_MONTH, 1); // Start of the month.
            Date startDate = calendar.getTime();

            calendar.add(Calendar.MONTH, 1);
            calendar.add(Calendar.DAY_OF_MONTH, -1); // End of the month.
            Date endDate = calendar.getTime();

            // Insert the salary record for this month.
            String setSalaryQuery = "INSERT INTO base_salary(employee_matricule, salary, starting_date, end_date) VALUES (?,?,?,?)";
            PreparedStatement preparedStatement = this.connection.prepareStatement(setSalaryQuery);
            preparedStatement.setString(1, employeeMatricule);
            preparedStatement.setFloat(2, monthlySalary);
            preparedStatement.setDate(3, new java.sql.Date(startDate.getTime()));
            preparedStatement.setDate(4, new java.sql.Date(endDate.getTime()));

            int isSalaryInserted = preparedStatement.executeUpdate();

            if (isSalaryInserted > 0) {
                BasicSalary basicSalary = new BasicSalary(); // Create a BasicSalary object and set its properties.
                basicSalary.setEmployee_matricule(employeeMatricule);
                basicSalary.setSalary(monthlySalary);
                basicSalary.setStarting_date(startDate);
                basicSalary.setEnd_date(endDate);
                salaryRecords.add(basicSalary);



                RetraitSalaryDao retraitSalaryDao = new RetraitSalaryDao();
                System.out.print(calculateAverageSalary(employeeMatricule));
                float retraitSalary = retraitSalaryDao.calculateRetirementSalary(birthday,calculateAverageSalary(employeeMatricule),totalWorkingDays);

                retraitSalaryDao.setRetraitSalary(employeeMatricule,retraitSalary,monthlySalary,totalWorkingDays);

            }

            // Move to the next month.
            calendar.add(Calendar.DAY_OF_MONTH, 1);
        }

        return salaryRecords;
    }



//    public BasicSalary updateSalary(String employeeMatricule, int salary_id ,float salary) throws SQLException{
//        List<Map<String, Object>> salaries = super.search(employeeMatricule,new String[]{"employee_matricule"});
//        if(salaries != null) {
//            String setCurrentSalaryEndDate = "UPDATE base_salary SET end_date = ? WHERE employee_matricule = ? AND salary_id = ?";
//
//            PreparedStatement preparedStatement = connection.prepareStatement(setCurrentSalaryEndDate);
//            preparedStatement.setDate(1,(java.sql.Date) new Date());
//            preparedStatement.setString(2,employeeMatricule);
//            preparedStatement.setInt(3,salary_id);
//
//            ResultSet resultSet = preparedStatement.executeQuery();
//
//
//            if(resultSet != null) {
//
//
//                BasicSalary insertNewSalaryRecord = (BasicSalary) setSalary(employeeMatricule,salary,4);
//
//                if(insertNewSalaryRecord != null) {
//
//                    int employee_salary_id = resultSet.getInt("salary_id");
//                    String employee_matricule = resultSet.getString("employee_matricule");
//                    Float employee_salary = resultSet.getFloat("salary");
//                    Date salary_starting_date = resultSet.getDate("starting_date");
//
//                    basicSalary.setBasicSalary(
//                            (Integer) employee_salary_id,
//                            (String) employee_matricule,
//                            (Float) employee_salary,
//                            (Date) salary_starting_date
//                    );
//                    return basicSalary;
//                }else {
//                    return null;
//                }
//
//
//
//
//            }else {
//                return null;
//            }
//
//        }else {
//            return null;
//        }
//
//    }

    public Float calculateAverageSalary(String employeeMatricule) throws SQLException{
        String averageSalaryQuery = "SELECT AVG(salary) as average_salary FROM base_salary WHERE employee_matricule = ?";
        String average96MonthsSalaryQuery = "SELECT AVG(salary) as average_salary FROM base_salary WHERE employee_matricule = ? ORDER BY salary_id DESC LIMIT 96";

        PreparedStatement preparedStatement;
        if (hasAtLeast96Records(employeeMatricule)) {
            preparedStatement = connection.prepareStatement(average96MonthsSalaryQuery);
        } else {
            preparedStatement = connection.prepareStatement(averageSalaryQuery);
        }

        preparedStatement.setString(1, employeeMatricule);

        ResultSet resultSet = preparedStatement.executeQuery();

        if (resultSet.next()) {
            float averageSalary = resultSet.getFloat("average_salary");
            return averageSalary;
        } else {
            return null;
        }
    }

    private boolean hasAtLeast96Records(String employeeMatricule) throws SQLException {
        String countQuery = "SELECT COUNT(*) as record_count FROM base_salary WHERE employee_matricule = ?";

        PreparedStatement countStatement = connection.prepareStatement(countQuery);
        countStatement.setString(1, employeeMatricule);

        ResultSet countResultSet = countStatement.executeQuery();

        if (countResultSet.next()) {
            int recordCount = countResultSet.getInt("record_count");
            return recordCount >= 96;
        } else {
            return false;
        }
    }

    public Map<Integer, Object> getCurrentSalary(String employeeMatricule) throws SQLException {
        String getCurrentSalaryQuery = "SELECT * FROM base_salary WHERE employee_matricule = ? ORDER BY salary_id DESC LIMIT 1";
        PreparedStatement preparedStatement = connection.prepareStatement(getCurrentSalaryQuery);
        preparedStatement.setString(1,employeeMatricule);

        ResultSet resultSet = preparedStatement.executeQuery();

        if(resultSet.next()) {

            Map<Integer,Object> salary = new HashMap<>();

            int salary_id = resultSet.getInt("salary_id");
            String employee_matricule = resultSet.getString("employee_matricule");
            float employee_salary = resultSet.getFloat("salary");
            Date stating_date = resultSet.getDate("starting_date");

            basicSalary.setBasicSalary(salary_id,employee_matricule,employee_salary,stating_date);

            salary.put(basicSalary.getSalary_id(), basicSalary);

            return salary;
        }else {
            return null;
        }
    }






}
