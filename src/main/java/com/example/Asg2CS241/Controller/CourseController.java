package com.example.Asg2CS241.Controller;

import com.example.Asg2CS241.Entity.*;

import com.example.Asg2CS241.Repository.MessageRepository;
import com.example.Asg2CS241.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Set;
@Controller
public class CourseController {
    @Autowired
    private UserService userService;

    // Endpoint for Student to view their courses
    @GetMapping("/StudentDashboard/{stuid}")
    public String getCoursesForStudent(@PathVariable("stuid") Long studentId, Model model) {
        // Fetch the courses associated with the student ID
        Set<Course> courses = userService.getCoursesForStudent(studentId);

        // Add the list of courses to the model
        model.addAttribute("listCourses", courses);
        model.addAttribute("studentId", studentId);

        // Return the name of the HTML template for displaying the courses
        return "student_courses"; // This should be the name of your Thymeleaf template (student_courses.html)
    }

    // Endpoint for Instructor to view their courses
    @GetMapping("/CourseInstructorDashboard/{courseinstructorid}")
    public String getCoursesForInstructor(@PathVariable("courseinstructorid") Long instructorId, Model model) {
        Set<Course> courses = userService.getCoursesForInstructor(instructorId);
        model.addAttribute("listCourses", courses);
        model.addAttribute("courseInstructorId", instructorId);
        return "instructor_courses"; // Return the name of the HTML template
    }


    @GetMapping("/CourseInstructorDashboard/{courseinstructorid}/attendance/{classid}")
    public String getAttendancePageInstructor(
            @PathVariable("classid") Long classId,
            @PathVariable("courseinstructorid") Long instructorId,
            @RequestParam(value = "week", defaultValue = "1") int currentWeek,
            @RequestParam(value = "page", defaultValue = "0") int currentPage,
            Model model) {

        // Fetch the number of unique weeks (used for pagination)
        int totalWeeks = userService.getTotalWeeksForClass(classId);

        // Ensure that currentWeek is within valid bounds
        if (currentWeek < 1 || currentWeek > totalWeeks) {
            currentWeek = 1;  // Set default to week 1 if out of bounds
        }

        // Fetch attendance records for the specific week and page
        Page<Attendance> attendanceRecords = userService.getAttendanceByWeekAndPage(classId, currentWeek, currentPage, 50);

        // Fetch course details
        Course course = userService.getCourseById(classId);

        // Fetch attendance percentages
        Map<String, Double> attendancePercentages = userService.getAttendancePercentages(classId);

        // Add attributes to the model
        model.addAttribute("attendancePercentages", attendancePercentages);

        // Add data to the model
        model.addAttribute("course", course);
        model.addAttribute("courseInstructorId", instructorId);
        model.addAttribute("attendanceRecords", attendanceRecords.getContent());
        model.addAttribute("currentWeek", currentWeek);
        model.addAttribute("currentPage", currentPage);
        model.addAttribute("totalPages", totalWeeks);  // Total weeks represent total pages

        return "courses_attendance_instructor";
    }



    @RequestMapping("/CourseInstructorDashboard/{courseinstructorid}/attendance/{classid}/setAttendance")
    public String showAttendancePage(@PathVariable("classid") Long classid,
                                     @PathVariable("courseinstructorid") Long instructorId,
                                     Model model) {
        // Retrieve the course
        Course course = userService.getCourseById(classid);

        // Fetch students enrolled in this course
        Set<Student> students = userService.getStudentsByCourseId(classid);

        // Create a new Attendance object
        Attendance attendance = new Attendance();



        // Add attributes to the model
        model.addAttribute("attendance", attendance);
        model.addAttribute("course", course);
        model.addAttribute("students", students);
        model.addAttribute("courseInstructorId", instructorId);

        return "save_attendance";  // Return the Thymeleaf template name
    }





    @PostMapping("/CourseInstructorDashboard/{courseinstructorid}/attendance/{classid}/save")
    public String saveAttendance(@PathVariable("classid") Long classId,
                                 @RequestParam("studentId") Long studentId,
                                 @RequestParam("week") int week,
                                 @RequestParam("dayOfWeek") String dayOfWeek,
                                 @RequestParam("status") String status,
                                 @PathVariable("courseinstructorid") Long courseInstructorId,
                                 Model model) {
        Course course = userService.getCourseById(classId);
        Student student = userService.getStudentById(studentId);
        Parent parent = student.getParent();  // Get the parent of the student
        model.addAttribute("course", course);

        userService.saveAttendance(studentId, classId, week, dayOfWeek, status);

        // If student is marked absent, send a message to the parent
        if (status.equalsIgnoreCase("absent")) {
            String messageContent = "Your child, " + student.getFname() + " " + student.getLname() +
                    " (ID: " + student.getStuid() + "), was marked absent from the course " +
                    course.getClassname() + " (Class ID: " + classId + ") on " + dayOfWeek +
                    " during Week " + week + ".";
            String currentDate = LocalDate.now().toString();  // Current date as a string

            // Save the message to the parent’s inbox
            userService.sendMessageToParent(parent, messageContent, currentDate);
        }

        // Redirect back to attendance page
        return "redirect:/CourseInstructorDashboard/{courseinstructorid}/attendance/{classid}";
    }


    @GetMapping("/StudentDashboard/{stuid}/attendance/{classid}")
    public String getStudentAttendance(
            @PathVariable("stuid") Long studentId,
            @PathVariable("classid") Long classId,
            Model model) {

        // Fetch the course details
        Course course = userService.getCourseById(classId);

        // Fetch all attendance records for the student in the given class
        List<Attendance> attendanceRecords = userService.getStudentAttendanceForCourse(studentId, classId);

        // Add attributes to the model
        model.addAttribute("course", course);
        model.addAttribute("studentId", studentId);
        model.addAttribute("attendanceRecords", attendanceRecords);

        return "courses_attendance_student";  // Thymeleaf template name
    }

    @GetMapping("/ParentDashboard/{parentid}/studentCourses/{stuid}")
    public String getstudentCourseForParent(@PathVariable("parentid") Long parentId,
                                            @PathVariable("stuid") Long studentId,
                                            Model model) {

        Set<Course> courses = userService.getCoursesForStudent(studentId);
        Student student = userService.findByStuid(studentId); // Fetch the student entity

        model.addAttribute("listCourses", courses);
        model.addAttribute("parentId", parentId);
        model.addAttribute("studentId", studentId);
        model.addAttribute("student", student); // Add the student to the model

        return "parent_student_courses";
    }

    @GetMapping("/ParentDashboard/{parentid}/studentCourses/{stuid}/attendance/{classid}")
    public String getStudentCourseAttendanceForParent(@PathVariable("stuid") Long studentId,
                                                      @PathVariable("classid") Long classId,
                                                      @PathVariable("parentid") Long parentId,
                                                      Model model){

        List<Attendance> attendanceRecords = userService.getStudentAttendanceForCourse(studentId, classId);
        model.addAttribute("studentId", studentId);
        model.addAttribute("classId", classId);
        model.addAttribute("parentId", parentId);
        model.addAttribute("attendanceRecords", attendanceRecords);

        return "parent_student_courses_attendance";
    }
    @Autowired
    MessageRepository messageRepository;

    @GetMapping("/ParentDashboard/{parentId}/messages")
    public String getParentMessages(@PathVariable("parentId") Long parentId, Model model) {
        List<Message> messages = messageRepository.findByParent_Parentid(parentId);
        model.addAttribute("messages", messages);
        return "parents_message";  // Thymeleaf template for displaying messages
    }









}
