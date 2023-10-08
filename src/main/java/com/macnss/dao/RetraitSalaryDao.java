package com.macnss.dao;

import com.macnss.Libs.Model;
import com.macnss.app.Models.Static.RetraitSalary;
import com.macnss.app.Models.user.Employee;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

public class RetraitSalaryDao extends Model{

    public RetraitSalaryDao() {
        super("retrait_salary", new String[]{"retrait_id"});
    }

    public float calculateRetirementSalary(Date birthday, float averageSalary , int daysWorked){


        final int INCREMENT_START = 3240;
        final float MAX_SALARY = 6000;
        final float MAX_PERCENTAGE_OF_SALARY = 0.7f;
        final int INCREMENT_DAYS = 216;
        final float INCREMENT_PERCENTAGE = 0.01f;
        float retirementSalary;


        if(daysWorked < INCREMENT_START) {
            retirementSalary = averageSalary * 0.5f;
            if(retirementSalary > MAX_SALARY) {
                retirementSalary = 6000;
            }
            return retirementSalary;
        }else if(daysWorked >= INCREMENT_START ) {
            retirementSalary = averageSalary * 0.5f;
            int increments = (daysWorked - INCREMENT_START) / INCREMENT_DAYS;

            for(int i = 0; i < increments; i++) {
                retirementSalary += (averageSalary * INCREMENT_PERCENTAGE);

                if(retirementSalary > (averageSalary * MAX_PERCENTAGE_OF_SALARY)){
                    retirementSalary = averageSalary * MAX_PERCENTAGE_OF_SALARY;
                }

            }
            if(retirementSalary > MAX_SALARY) {
                retirementSalary = 6000;
            }

            return retirementSalary;
        }else {
            return 0.0f;
        }

    }

    public boolean setRetraitSalary(String employeeMatricule,float retraitSalary,float monthlySalary,int totalWorkingDays) throws SQLException {
        String setRetraitSalary = "INSERT INTO retrait_salary(employee_matricule,retrait_salary,salary,status) VALUES (?,?,?,?)";
        PreparedStatement preparedStatement1 = this.connection.prepareStatement(setRetraitSalary);

        preparedStatement1.setString(1,employeeMatricule);

        preparedStatement1.setFloat(2,retraitSalary);
        preparedStatement1.setFloat(3,monthlySalary);


        if(totalWorkingDays >= 1320) {
            preparedStatement1.setString(4,"RETRAIT");

        }else{
            preparedStatement1.setString(4,"NON-RETRAIT");
        }


        int insertedRetraitSalary = preparedStatement1.executeUpdate();

        if(insertedRetraitSalary > 0){
            return true;
        }else {
            return false;
        }
    }

    public RetraitSalary getCurrentRetraitSalary(String matricule) throws SQLException {
        String getCurrentSalaryQuery = "SELECT * FROM retrait_salary WHERE employee_matricule = ? ORDER BY retrait_id DESC LIMIT 1";
        PreparedStatement preparedStatement = connection.prepareStatement(getCurrentSalaryQuery);
        preparedStatement.setString(1,matricule);

        ResultSet resultSet = preparedStatement.executeQuery();

        if(resultSet.next()) {


            float retrait_salary = resultSet.getFloat("retrait_salary");
            float salary= resultSet.getFloat("salary");
            String status = resultSet.getString("status");


            RetraitSalary retraitSalary = new RetraitSalary();
            retraitSalary.setRetraitSalary(retrait_salary,matricule,salary,status);

            return retraitSalary;


        }else {
            return null;
        }
    }

}
