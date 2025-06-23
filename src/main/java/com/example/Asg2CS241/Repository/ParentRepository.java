package com.example.Asg2CS241.Repository;

import com.example.Asg2CS241.Entity.Parent;
import com.example.Asg2CS241.Entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Set;

public interface ParentRepository extends JpaRepository<Parent, Long> {

    @Query("SELECT p FROM Parent p WHERE p.email = ?1")
    Parent findByEmail(String email);

    @Query("SELECT s FROM Student s WHERE s.parent.parentid = ?1")
    Set<Student> findStudentsByParentId(Long parentId);  // New method to find students by parent ID

}
