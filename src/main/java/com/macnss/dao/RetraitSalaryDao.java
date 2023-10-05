package com.macnss.dao;

import com.macnss.Libs.Model;
import com.macnss.app.Models.user.Employee;

import java.util.Date;
import java.util.concurrent.TimeUnit;

public class RetraitSalaryDao {

    public float calculateRetirementSalary(Employee employee, float averageSalary , int daysWorked){


        final int MIN_WORKING_DAYS = 1320;
        final int MIN_AGE = 55;
        final int INCREMENT_START = 3240;
        final int MAX_SALARY = 6000;
        final float MAX_PERCENTAGE_OF_SALARY = 0.7f;
        final int INCREMENT_DAYS = 216;
        final float INCREMENT_PERCENTAGE = 0.01f;
        float retirementSalary;
        int currentAge = calculateAge(employee.getBirthday());


        if(daysWorked >= MIN_WORKING_DAYS && daysWorked < INCREMENT_START && currentAge >= MIN_AGE) {
            retirementSalary =  averageSalary * 0.5f;
            return retirementSalary;
        }else if(daysWorked >= INCREMENT_START && currentAge >= MIN_AGE) {
            retirementSalary = averageSalary * 0.5f;
            int increments = (daysWorked - INCREMENT_START) / INCREMENT_DAYS;

            for(int i = 0; i < increments; i++) {
                retirementSalary += (averageSalary * INCREMENT_PERCENTAGE);

                if(retirementSalary > (averageSalary * MAX_PERCENTAGE_OF_SALARY)){
                    retirementSalary = averageSalary * MAX_PERCENTAGE_OF_SALARY;
                }

                if(retirementSalary > MAX_SALARY) {
                    retirementSalary = 6000;
                }
            }
            return retirementSalary;
        }else {
            return 0.0f;
        }

    }

    public int calculateAge(Date birthday) {
        Date currentDate =  new Date();
        long differenceInMillSeconds = currentDate.getTime() - birthday.getTime();
        return (int) TimeUnit.DAYS.convert(differenceInMillSeconds, TimeUnit.MILLISECONDS) / 365;
    }

}
