package com.example.Asg2CS241.Entity;

import jakarta.persistence.*;


@Entity
@Table(name =  "courseadmin")
public class CourseAdmin {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private Long courseadminid;
    private String email;
    private String dob;
    private String fname;
    private String lname;
    private String phone;
    private String address;
    private String password;

    public Long getCourseadminid() {
        return courseadminid;
    }

    public void setCourseadminid(Long couradminid) {
        this.courseadminid = couradminid;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public void setFname(String fname) {
        this.fname = fname;
    }

    public void setLname(String lname) {
        this.lname = lname;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public String getDob() {
        return dob;
    }

    public String getFname() {
        return fname;
    }

    public String getLname() {
        return lname;
    }

    public String getPhone() {
        return phone;
    }

    public String getAddress() {
        return address;
    }

    public String getPassword() {
        return password;
    }
}
