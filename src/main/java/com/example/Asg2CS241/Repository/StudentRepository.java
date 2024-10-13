package com.example.Asg2CS241.Repository;

import com.example.Asg2CS241.Entity.Attendance;
import com.example.Asg2CS241.Entity.Student;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface StudentRepository extends JpaRepository<Student, Long>{
    @Query("SELECT s FROM Student s WHERE s.email = ?1")
    Student findByEmail(String email);


    Student findByStuid(Long stuid);

    @Query("SELECT s FROM Student s JOIN s.courses c WHERE c.classid = :classid")
    Set<Student> findStudentsByCourseId(@Param("classid") Long classid);





}
