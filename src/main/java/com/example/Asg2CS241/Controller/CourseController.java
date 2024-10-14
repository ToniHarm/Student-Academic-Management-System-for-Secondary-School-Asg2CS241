package com.example.Asg2CS241.Controller;

import com.example.Asg2CS241.Entity.Attendance;
import com.example.Asg2CS241.Entity.Course;

import com.example.Asg2CS241.Entity.CourseInstructor;
import com.example.Asg2CS241.Entity.Student;
import com.example.Asg2CS241.Service.AttendanceService;
import com.example.Asg2CS241.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.Set;
@Controller
public class CourseController {
    @Autowired
    private UserService userService;
    @Autowired
    private AttendanceService attendanceService;

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
                                 @PathVariable("courseinstructorid") Long courseInstructorId, // Assuming instructor ID is passed
                                 Model model) {
        Course course = userService.getCourseById(classId);
        model.addAttribute("course", course);
        userService.saveAttendance(studentId, classId, week, dayOfWeek, status);

        // Correct redirect with properly resolved variables
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

    @GetMapping("/parentDashboard/{parentid}")
    public String getstudentForParent(@PathVariable("parentid") Long parentId, Model model) {


        model.addAttribute("parentId", parentId);

        return null;
    }

    // Search attendance
    @GetMapping("/CourseInstructorDashboard/{courseInstructorId}/attendance/search")
    public String searchAttendance(@PathVariable Long courseInstructorId,
                                   @RequestParam int week,
                                   @RequestParam String dayOfWeek,
                                   @RequestParam Long studentId,
                                   Model model) {
        Optional<Attendance> searchedRecord = attendanceService.findByStudentIdAndWeekAndDayOfWeek(studentId, week, dayOfWeek);

        if (searchedRecord.isPresent()) {
            model.addAttribute("searchedAttendanceRecord", searchedRecord.get());
            model.addAttribute("courseInstructorId", courseInstructorId);
            return "courses_attendance_instructor"; // Return a detail view for the attendance record
        } else {
            model.addAttribute("error", "No records found for the provided attendance ID and day.");
            return "courses_attendance_instructor"; // Return to attendance instructor page with error
        }
    }

    // Update attendance record
    @PostMapping("/CourseInstructorDashboard/{courseInstructorId}/attendance/edit")
    public String updateAttendance(@PathVariable Long courseInstructorId,
                                   @RequestParam Long studentId,
                                   @RequestParam int week,
                                   @RequestParam String dayOfWeek,
                                   @RequestParam String status,
                                   Model model) {
        // Find the existing attendance record
        Optional<Attendance> existingRecord = attendanceService.findByStudentIdAndWeekAndDayOfWeek(studentId, week, dayOfWeek);

        if (existingRecord.isPresent()) {
            // Update the record with the new status
            Attendance attendance = existingRecord.get();
            attendance.setStatus(status);
            attendanceService.saveAttendance(attendance); // Save updated record

            // Redirect to the updated attendance page
//            return "redirect:/CourseInstructorDashboard/" + courseInstructorId + "/attendance/{classid}";
            return "courses_attendance_instructor";
        } else {
            model.addAttribute("error", "No record found for the specified student, week, and day.");
            return "courses_attendance_instructor"; // Return with error
        }
    }

    // Delete attendance record
    @PostMapping("/CourseInstructorDashboard/{courseInstructorId}/attendance/delete")
    public String deleteAttendance(@PathVariable Long courseInstructorId,
                                   @RequestParam Long studentId,
                                   @RequestParam int week,
                                   @RequestParam String dayOfWeek,
                                   Model model) {
        // Find the existing attendance record
        Optional<Attendance> existingRecord = attendanceService.findByStudentIdAndWeekAndDayOfWeek(studentId, week, dayOfWeek);

        if (existingRecord.isPresent()) {
            // Delete the record
            attendanceService.deleteAttendance(existingRecord.get());

            // Redirect to the attendance page after deletion
            return "courses_attendance_instructor";
//            return "redirect:/CourseInstructorDashboard/" + courseInstructorId + "/attendance/{classid}";
        } else {
            model.addAttribute("error", "No record found to delete.");
            return "courses_attendance_instructor"; // Return with error
        }
    }












}
