package com.example.Asg2CS241.Controller;

import com.example.Asg2CS241.Entity.Attendance;
import com.example.Asg2CS241.Entity.Course;

import com.example.Asg2CS241.Entity.CourseInstructor;
import com.example.Asg2CS241.Entity.Student;
import com.example.Asg2CS241.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
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
    public String getAttendancePageInstructor(@PathVariable("courseinstructorid") Long instructorId,@PathVariable("classid") Long classId, Model model) {
        Course course = userService.getCourseById(classId);  // Use the new method to fetch the course
        model.addAttribute("course", course);
        model.addAttribute("courseInstructorId", instructorId);
        return "courses_attendance_instructor";  // Return the Thymeleaf template
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
                                 @PathVariable("courseinstructorid") Long courseInstructorId, // Assuming instructor ID is passed
                                 Model model) {
        Course course = userService.getCourseById(classId);
        model.addAttribute("course", course);
        userService.saveAttendance(studentId, classId, week, dayOfWeek, status);

        // Correct redirect with properly resolved variables
        return "redirect:/CourseInstructorDashboard/{courseinstructorid}/attendance/{classid}";
    }

    // Endpoint to view attendance for a specific course and week
//    @GetMapping("/CourseInstructorDashboard/{courseinstructorid}/attendance/{classid}/week/{week}")
//    public String viewAttendanceForCourse(@PathVariable("classid") Long classId,
//                                          @PathVariable("week") int week,
//                                          Model model) {
//        List<Attendance> attendanceRecords = userService.getAttendanceForCourseInWeek(classId, week);
//        model.addAttribute("attendanceRecords", attendanceRecords);
//        return "attendance_week";  // This should be the name of your Thymeleaf template (attendance_week.html)
//    }
//
//    // Endpoint to view attendance for a specific course, week, and day
//    @GetMapping("/CourseInstructorDashboard/{courseinstructorid}/attendance/{classid}/week/{week}/day/{dayOfWeek}")
//    public String viewAttendanceForCourseInDay(@PathVariable("classid") Long classId,
//                                               @PathVariable("week") int week,
//                                               @PathVariable("dayOfWeek") String dayOfWeek,
//                                               Model model) {
//        List<Attendance> attendanceRecords = userService.getAttendanceForCourseInWeekAndDay(classId, week, dayOfWeek);
//        model.addAttribute("attendanceRecords", attendanceRecords);
//        return "attendance_day";  // This should be the name of your Thymeleaf template (attendance_day.html)
//    }






}
