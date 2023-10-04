package com.macnss.app.Models;

import java.util.HashMap;
import java.util.Map;
import lombok.Data;

@Data
public class Company {
    int company_id = 0;
    String name;

    public Map<String,Object> getCompany() {
        Map<String, Object> companies = new HashMap<>();

        if(this.company_id != 0) companies.put("company_id", this.company_id);
        if(this.name != null) companies.put("name", this.name);

        return companies;
    }

    public void setCompany(String name, int company_id) {
        this.name = name;
        this.company_id = company_id;
    }
}
