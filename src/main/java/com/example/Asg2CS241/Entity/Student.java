package com.example.Asg2CS241.Entity;


import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name =  "student")
public class Student {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long stuid;
    @Column(name = "email", unique = true)
    private String email;
    private String dob;
    private String fname;
    private String lname;
    private String phone;
    private String address;
    private String password;


    public void setCourses(Set<Course> courses) {
        this.courses = courses;
    }

    public Parent getParent() {
        return parent;
    }

    public void setParent(Parent parent) {
        this.parent = parent;
    }





    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_id", nullable = false)  // Foreign key for Parent
    private Parent parent;


    @ManyToMany(fetch = FetchType.EAGER,cascade = CascadeType.ALL)
    @JoinTable(name = "student_class",joinColumns = @JoinColumn(name = "student_id",referencedColumnName = "stuid"),  // Foreign key for student
            inverseJoinColumns = @JoinColumn(name = "class_id",referencedColumnName = "classid")  // Foreign key for course
    )
    private Set<Course> courses = new HashSet<>();

    public Set<Course> getCourses() {
        return courses;
    }



    public String getPassword() {
        return password;
    }

    public String getDob() {
        return dob;
    }



    public Long getStuid() {
        return stuid;
    }

    public String getEmail() {
        return email;
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

    public void setDob(String dob) {
        this.dob = dob;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setStuid(Long stuid) {
        this.stuid = stuid;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setFname(String fname) {
        this.fname = fname;
    }



    public void setLname(String lname) {
        this.lname = lname;
    }

    public String getAddress() {
        return address;
    }
}