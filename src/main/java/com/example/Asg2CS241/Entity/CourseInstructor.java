package com.example.Asg2CS241.Entity;


import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name =  "courseteacher")
public class CourseInstructor {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private Long courseteacherid;
    private String email;
    private String dob;
    private String fname;
    private String lname;
    private String phone;
    private String address;
    private String password;

    @ManyToMany(fetch = FetchType.EAGER,cascade = CascadeType.ALL)
    @JoinTable(name = "teacher_class",joinColumns = @JoinColumn(name = "courseteacher_id",referencedColumnName = "courseteacherid"),  // Foreign key for student
            inverseJoinColumns = @JoinColumn(name = "class_id",referencedColumnName = "classid")  // Foreign key for course
    )
    private Set<Course> courses = new HashSet<>();

    public Set<Course> getCourses() {
        return courses;
    }

    public Long getCourseteacherid() {
        return courseteacherid;
    }

    public void setCourseteacherid(Long courteaherid) {
        this.courseteacherid = courteaherid;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public String getFname() {
        return fname;
    }

    public void setFname(String fname) {
        this.fname = fname;
    }

    public String getLname() {
        return lname;
    }

    public void setLname(String lname) {
        this.lname = lname;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }


}
