package com.example.Asg2CS241.Service;

import com.example.Asg2CS241.Entity.*;
import com.example.Asg2CS241.Repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;


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



    public Set<Course> getCoursesForStudent(Long studentId) {
        Student student = stuRepo.findById(studentId)
                .orElseThrow(() -> new RuntimeException("Student not found"));
        return student.getCourses();
    }

    public Set<Course> getCoursesForInstructor(Long instructorId) {
        CourseInstructor instructor = courTeacherRepo.findById(instructorId)
                .orElseThrow(() -> new RuntimeException("Instructor not found"));
        return instructor.getCourses();
    }


    public Student findByStuid(Long studentId) {
        return stuRepo.findById(studentId).get();
    }

    @Autowired
    private AttendanceRepository attendanceRepository;
    @Autowired
    private ClassRepository courseRepository;
    // Save an attendance record
    public void saveAttendance(Long studentId, Long courseId, int week, String dayOfWeek, String status) {
        Student student = stuRepo.findById(studentId)
                .orElseThrow(() -> new RuntimeException("Student not found"));
        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new RuntimeException("Course not found"));

        Attendance attendance = new Attendance();
        attendance.setStudent(student);
        attendance.setCourse(course);
        attendance.setWeek(week);
        attendance.setDay_of_week(dayOfWeek);
        attendance.setStatus(status);

        attendanceRepository.save(attendance);
    }

    // Method to fetch a course by its ID
    public Course getCourseById(Long classId) {
        return courseRepository.findById(classId).orElse(null);  // Assuming you're using JPA or a similar repository pattern
    }
}
