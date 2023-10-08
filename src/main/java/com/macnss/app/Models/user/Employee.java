    package com.macnss.app.Models.user;

    import com.macnss.app.Enums.EmployeeStatus;
    import com.macnss.app.Enums.Gender;
    import com.macnss.app.Models.Abstract.employee;

    import java.sql.Date;
    import java.util.HashMap;
    import java.util.Map;

    public class Employee extends employee {
        EmployeeStatus status;
        int working_days;
        int company_id;

        public Map<String,Object> getEmployee() {

            Map<String, Object> employee = new HashMap<>();

            if (this.cnie != null) employee.put("cnie", this.cnie);
            if (this.firstName != null) employee.put("first_name", this.firstName);
            if (this.lastName != null) employee.put("last_name", this.lastName);
            if (this.email != null) employee.put("email", this.email);
            if (this.birthday != null) employee.put("birthday", this.birthday);
            if (this.phone != null) employee.put("phone", this.phone);
            if (this.gender != null) employee.put("gender", this.gender);
            if (this.password != null) employee.put("password", this.password);
            if (this.status != null) employee.put("Status", this.status);
            if (this.working_days > 0) employee.put("working_days", this.working_days);
            if (this.company_id > 0) employee.put("company_id", this.company_id);


            return employee;

        }
        public void setEmployee(String cnie, String firstName, String lastName, EmployeeStatus status,Date birthday, Gender gender, String email, String phone, String password,int working_days,int company_id) {
            this.cnie = cnie;
            this.firstName = firstName;
            this.lastName = lastName;
            this.birthday = birthday;
            this.gender = Gender.valueOf(String.valueOf(gender));
            this.email = email;
            this.phone = phone;
            this.password = password;
            this.working_days = working_days;
            this.company_id = company_id;
            this.status = status;


        }




    }
