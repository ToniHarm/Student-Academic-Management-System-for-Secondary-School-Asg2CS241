package com.example.Asg2CS241.Repository;

import com.example.Asg2CS241.Entity.CourseAdmin;
import com.example.Asg2CS241.Entity.CourseInstructor;
import com.example.Asg2CS241.Entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface CourseAdminRepository extends JpaRepository<CourseAdmin, Long> {

    @Query("SELECT c FROM CourseAdmin c WHERE c.email = ?1")
    CourseAdmin findByEmail(String email);
}
