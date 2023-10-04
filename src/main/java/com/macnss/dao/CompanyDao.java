package com.macnss.dao;

import com.macnss.Libs.Model;
import com.macnss.app.Enums.AgentStatus;
import com.macnss.app.Enums.Gender;
import com.macnss.app.Models.Company;
import com.macnss.app.Models.user.Employee;
import com.macnss.interfaces.Dao.Dao;

import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class CompanyDao extends Model implements Dao<Company> {
    private final Company company;

    public CompanyDao(Company company) {
        super("company", new String[]{"company_id"});
        this.company = company;
    }

    @Override
    public Company read() {
        Map<String, Object> companyData = super.read(new Object[]{company.getCompany_id()});

        if(companyData != null) {
            company.setCompany(
                    (String)  companyData.get("name"),
                    (Integer) companyData.get("company_id")
            );

            return company;
        }else {
            return null;
        }
    }

    @Override
    // String param
    public Optional<Company> get(String name) {
        Map<String, Object> companyData = super.read(new String[]{"company_id"}, new String[]{name});

        if (companyData == null) {
            return Optional.empty();
        }

        company.setCompany(
                (String) companyData.get("name"),
                (Integer) companyData.get("company_id")

        );


        return Optional.of(company);
    }

    // Int param
    public Optional<Company> get(int id) {
        Map<String, Object> companyData = super.read(new String[]{"company_id"}, new Integer[]{id});

        if (companyData == null) {
            return Optional.empty();
        }

        company.setCompany(
                (String) companyData.get("name"),
                (Integer) companyData.get("company_id")

        );


        return Optional.of(company);
    }


    @Override
    public List<Company> getAll() {
        List<Company> companies = new ArrayList<>();
        List<Map<String,Object>> companiesData = super.retrieveAll();

        if(companiesData.isEmpty()) return null;

        companiesData.forEach( (companyData) -> {
            Company company = new Company();

            company.setCompany(

                    (String) companyData.get("name"),
                    (Integer) companyData.get("company_id")

            );
            companies.add(company);

        });
        return companies;
    }

    @Override
    public Optional<Company> save() throws SQLException {
        if (super.create(company.getCompany()) == null) {
            return Optional.empty();
        } else {
            return Optional.of(company);
        }    }

    @Override
    public Optional<Company> create(Company entity) throws SQLException {
        if (super.create(company.getCompany()) == null) {
            return Optional.empty();
        } else {
            return Optional.of(company);
        }    }

    @Override
    public Optional<Company> update(Company entity) {
        if (super.update(company.getCompany(), new String[]{String.valueOf(company.getCompany_id())})) {
            return Optional.of(company);
        } else {
            return Optional.empty();
        }
    }

    @Override
    public List<Company> find(String criteria) {
        List<Company> companies = new ArrayList<>();

        List<Map<String, Object>> companiesData = super.readAll(new String[]{company.getName()});

        if (companiesData.isEmpty()) return companies;

        companiesData.forEach((companyData) -> {
            Company company = new Company();

            company.setCompany(

                    (String) companyData.get("name"),
                    (Integer) companyData.get("company_id")

            );
            companies.add(company);



        });

        return companies;
    }

    @Override
    public boolean delete(Company entity) {
        return super.delete(new String[]{String.valueOf(company.getCompany())});
    }
}
