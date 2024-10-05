package com.example.Asg2CS241.Repository;

import com.example.Asg2CS241.Entity.Parent;
import com.example.Asg2CS241.Entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface ParentRepository extends JpaRepository<Parent, Long> {

    @Query("SELECT p FROM Parent p WHERE p.email = ?1")
    Parent findByEmail(String email);


}
