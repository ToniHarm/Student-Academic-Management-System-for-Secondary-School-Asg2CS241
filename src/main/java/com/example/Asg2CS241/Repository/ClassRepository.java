package com.example.Asg2CS241.Repository;

import com.example.Asg2CS241.Entity.Course;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClassRepository extends JpaRepository<Course, Long> {

}
