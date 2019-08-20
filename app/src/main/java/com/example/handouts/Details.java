package com.example.handouts;

public class Details {

    String college;
    String dob;
    String phone;
    String email;

    public Details(){

    }

    public  Details(String college, String dob, String phone, String email){
        this.college=college;
        this.dob=dob;
        this.phone=phone;
        this.email=email;
    }

    public String getCollege() {
        return college;
    }

    public String getDob() {
        return dob;
    }

    public String getPhone() {
        return phone;
    }

    public String getEmail() {
        return email;
    }
}
