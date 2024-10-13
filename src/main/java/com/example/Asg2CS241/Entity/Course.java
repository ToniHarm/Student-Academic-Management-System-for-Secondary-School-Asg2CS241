package com.example.Asg2CS241.Entity;


import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Entity
@Table(name =  "class")
public class Course {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private Long classid;
    private String classname;
    private String classdescription;



    @OneToMany(mappedBy = "course")
    private List<Attendance> attendanceRecords = new ArrayList<>();



    public Long getClassid() {
        return classid;
    }

    public void setClassid(Long classid) {
        this.classid = classid;
    }

    public String getClassname() {
        return classname;
    }

    public void setClassname(String classname) {
        this.classname = classname;
    }

    public String getClassdescription() {
        return classdescription;
    }

    public void setClassdescription(String classdescription) {
        this.classdescription = classdescription;
    }
}
