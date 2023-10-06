package com.macnss.app.Models.Static;

import lombok.Data;

import java.util.HashMap;
import java.util.Map;

@Data
public class RetraitSalary {

    float retrait_salary;
    String employee_matricule;

    public Map<String,Object> getRetraitSalary() {
        Map<String,Object> retraitSalaries = new HashMap<>();

        if(retrait_salary > 0) retraitSalaries.put("retrait_salary", this.retrait_salary);
        if(employee_matricule != null) retraitSalaries.put("employee_matricule", this.employee_matricule);

        return retraitSalaries;

    }

    public void setRetraitSalary(float retrait_salary, String employee_matricule){
        this.retrait_salary = retrait_salary;
        this.employee_matricule = employee_matricule;
    }
}
