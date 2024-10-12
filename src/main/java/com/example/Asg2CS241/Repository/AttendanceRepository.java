package com.example.Asg2CS241.Repository;

import com.example.Asg2CS241.Entity.Attendance;
import com.example.Asg2CS241.Entity.Course;
import com.example.Asg2CS241.Entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AttendanceRepository extends JpaRepository<Attendance, Long> {
//    // Retrieve attendance records by student and course
//    List<Attendance> findByStudentAndCourse(Student student, Course course);
//
//    // Retrieve attendance records by student, course, and week
//    List<Attendance> findByStudentAndCourseAndWeek(Student student, Course course, int week);
//
//    // Retrieve attendance records by course and week
//    List<Attendance> findByCourseAndWeek(Course course, int week);
//
//    // Retrieve attendance records by course, week, and day of the week
//    List<Attendance> findByCourseAndWeekAndDay_of_week(Course course, int week, String day_of_week);

}
