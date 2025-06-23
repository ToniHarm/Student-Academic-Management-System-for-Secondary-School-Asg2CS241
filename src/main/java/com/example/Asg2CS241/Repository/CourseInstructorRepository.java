package com.example.Asg2CS241.Repository;

import com.example.Asg2CS241.Entity.CourseInstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface CourseInstructorRepository extends JpaRepository<CourseInstructor, Long> {
    @Query("SELECT c FROM CourseInstructor c WHERE c.email = ?1")
    CourseInstructor findByEmail(String email);

    CourseInstructor findBycourseteacherid(Long teacherId);
}
