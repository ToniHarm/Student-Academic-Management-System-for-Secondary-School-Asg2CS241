package com.example.Asg2CS241.Service;

import com.example.Asg2CS241.Entity.*;
import com.example.Asg2CS241.Repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.Map;


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

    @Autowired
    private ClassRepository classRepo;


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

    public void save(Course Course){
        classRepo.save(Course);
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


//        Optional<Attendance> existingAttendance = attendanceRepository.findByStudentAndweekAndday_of_week(studentId, week, dayOfWeek);
//        if (existingAttendance.isPresent()) {
//            throw new RuntimeException("Attendance record already exists for this student for the specified week and day.");
//        }

        Attendance attendance = new Attendance();
        attendance.setStudent(student);
        attendance.setCourse(course);
        attendance.setWeek(week);
        attendance.setDay_of_week(dayOfWeek);
        attendance.setStatus(status);

        attendanceRepository.save(attendance);
    }

    public List<Attendance> getAttendanceByClassId(Long classId) {
        return attendanceRepository.findByCourse_Classid(classId);
    }

    // Method to fetch a course by its ID
    public Course getCourseById(Long classId) {
        return courseRepository.findById(classId).orElse(null);  // Assuming you're using JPA or a similar repository pattern
    }

    public Set<Student> getStudentsByCourseId(Long classid) {
        return stuRepo.findStudentsByCourseId(classid);
    }

    // Get paginated attendance records by week and page
    public Page<Attendance> getAttendanceByWeekAndPage(Long classId, int week, int page, int pageSize) {
        Pageable pageable = PageRequest.of(page, pageSize);
        // Fetch attendance records for the given class and specific week
        return attendanceRepository.findByCourse_ClassidAndWeek(classId, week, pageable);
    }

    // Count total number of weeks with attendance records
    public int getTotalWeeksForClass(Long classId) {
        // This assumes you have a "week" field in your Attendance entity
        return attendanceRepository.countDistinctWeeksByClassId(classId);
    }

    public List<Attendance> getStudentAttendanceForCourse(Long studentId, Long classId) {
        return attendanceRepository.findByStudentIdAndClassId(studentId, classId);
    }

    public Set<Student> getStudentsByParentId(Long parentId) {
        return parRepo.findStudentsByParentId(parentId);
    }

    public Map<String, Double> getAttendancePercentages(Long courseId) {
        return attendanceRepository.findAttendancePercentagesByCourseId(courseId);
    }


    @Autowired
    private MessageRepository messageRepository;

    public void sendMessageToParent(Parent parent, String messageContent, String date) {
        Message message = new Message(parent, messageContent, date);
        messageRepository.save(message);
    }

    public Student getStudentById(Long studentId) {
        return stuRepo.findById(studentId)
                .orElseThrow(() -> new RuntimeException("Student not found with ID: " + studentId));
    }

    public void registerStudentToClass(Long studentId, Long classId) {
        Student student = stuRepo.findById(studentId).orElseThrow(() -> new RuntimeException("Student not found"));
        Course course = courseRepository.findById(classId).orElseThrow(() -> new RuntimeException("Course not found"));

        // Add the course to the student's courses set
        student.getCourses().add(course);

        // Save the student back to the repository
        stuRepo.save(student);
    }

}
