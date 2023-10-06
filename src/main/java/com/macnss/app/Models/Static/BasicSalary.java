package com.macnss.app.Models.Static;
import lombok.Data;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Data
public class BasicSalary {
    int salary_id = 0;
    String employee_matricule; // WIch is basically cnie
    float salary;

    Date starting_date;
    Date end_date = null;


    public Map<String, Object> getBasicSalary() {
        Map<String,Object> salaries = new HashMap<>();

        if(this.salary_id != 0) salaries.put("salary_id", this.salary);
        if(this.employee_matricule != null) salaries.put("employee_matricule", this.employee_matricule);
        if(this.salary != 0) salaries.put("salary", this.salary);
        salaries.put("starting_date", this.starting_date);
        salaries.put("end_date", this.end_date);

        return salaries;

    }

    public void setBasicSalary(int salary_id, String employee_matricule, float salary,Date starting_date,Date end_date) {
        this.salary_id = salary_id;
        this.employee_matricule = employee_matricule;
        this.salary = salary;
        this.starting_date = starting_date;
        this.end_date = end_date;
    }
    public void setBasicSalary(int salary_id, String employee_matricule, float salary,Date starting_date) {
        this.salary_id = salary_id;
        this.employee_matricule = employee_matricule;
        this.salary = salary;
        this.starting_date = starting_date;
    }
}
