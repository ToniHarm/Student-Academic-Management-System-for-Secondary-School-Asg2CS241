package com.example.Asg2CS241.Service;

import com.example.Asg2CS241.Entity.CourseAdmin;
import com.example.Asg2CS241.Entity.CourseInstructor;
import com.example.Asg2CS241.Entity.Parent;
import com.example.Asg2CS241.Entity.Student;
import com.example.Asg2CS241.Repository.CourseAdminRepository;
import com.example.Asg2CS241.Repository.CourseInstructorRepository;
import com.example.Asg2CS241.Repository.ParentRepository;
import com.example.Asg2CS241.Repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@Transactional
public class UserService {

    @Autowired
    private StudentRepository stuRepo;

    @Autowired
    private ParentRepository parRepo;

    @Autowired
    private CourseAdminRepository courAdminRepo;

    @Autowired
    private CourseInstructorRepository courTeacherRepo;


    public void save(Student Student) {
        stuRepo.save(Student);

    }

    public void save(Parent Parent){
        parRepo.save(Parent);
    }

    public void save(CourseInstructor CourseInstructor){
        courTeacherRepo.save(CourseInstructor);
    }

    public void save(CourseAdmin CourseAdmin){
        courAdminRepo.save(CourseAdmin);
    }








}
