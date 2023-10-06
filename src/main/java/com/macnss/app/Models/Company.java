package com.macnss.app.Models;

import java.util.HashMap;
import java.util.Map;
import lombok.Data;

@Data
public class Company {
    int company_id = 0;

    String name;

    String email;

    String password;

    public Map<String,Object> getCompany() {
        Map<String, Object> companies = new HashMap<>();

        if(this.company_id != 0) companies.put("company_id", this.company_id);
        if(this.name != null) companies.put("name", this.name);
        if(this.email != null) companies.put("email", this.email);
        if(this.password != null) companies.put("password", this.password);

        return companies;
    }

    public void setCompany(String name, int company_id,String email, String password) {
        this.name = name;
        this.company_id = company_id;
        this.email = email;
        this.password = password;
    }
}
