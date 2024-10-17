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
import java.util.*;

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

        int totalWeeks = userService.getTotalWeeksForClass(classId);

        if (currentWeek < 1 || currentWeek > totalWeeks) {
            currentWeek = 1;
        }

        List<WeeklyAttendanceDTO> attendanceRecords = userService.getGroupedAttendance(classId, currentWeek);

        Course course = userService.getCourseById(classId);
        Map<String, Double> attendancePercentages = userService.getAttendancePercentages(classId);

        model.addAttribute("attendancePercentages", attendancePercentages);
        model.addAttribute("course", course);
        model.addAttribute("courseInstructorId", instructorId);
        model.addAttribute("attendanceRecords", attendanceRecords);
        model.addAttribute("currentWeek", currentWeek);
        model.addAttribute("currentPage", currentPage);
        model.addAttribute("totalPages", totalWeeks);

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

        try {
            // Fetch course and student
            Course course = userService.getCourseById(classId);
            Student student = userService.getStudentById(studentId);
            Parent parent = student.getParent();  // Get the parent of the student

            // Add course to model
            model.addAttribute("course", course);

            // Save attendance and get the result message
            String resultMessage = userService.saveAttendance(studentId, classId, week, dayOfWeek, status);
            model.addAttribute("message", resultMessage);

            // If the student is marked absent, notify the parent
            if (status.equalsIgnoreCase("absent")) {
                String messageContent = "Your child, " + student.getFname() + " " + student.getLname() +
                        " (ID: " + student.getStuid() + "), was marked absent from the course " +
                        course.getClassname() + " (Class ID: " + classId + ") on " + dayOfWeek +
                        " during Week " + week + ".";
                String currentDate = LocalDate.now().toString();

                // Save the message to the parent's inbox
                userService.sendMessageToParent(parent, messageContent, currentDate);
            }

            // Redirect back to attendance page
            return "redirect:/CourseInstructorDashboard/{courseinstructorid}/attendance/{classid}";

        } catch (Exception e) {
            // Handle any unexpected exceptions
            model.addAttribute("message", "An error occurred while saving attendance: Student Attendance already exist");
            return "save_attendance";  // Redirect to an error page or show the error in the same form
        }
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

        // Create a map to store weekly attendance records
        Map<Integer, WeeklyAttendanceDTO> weeklyAttendanceMap = new HashMap<>();

        for (Attendance record : attendanceRecords) {
            int week = record.getWeek();
            WeeklyAttendanceDTO dto = weeklyAttendanceMap.getOrDefault(week, new WeeklyAttendanceDTO());

            dto.setWeek(week);
            dto.setStudentName(record.getStudent().getFname() + " " + record.getStudent().getLname());
            dto.getDailyAttendance().put(record.getDay_of_week(), record.getStatus());

            weeklyAttendanceMap.put(week, dto);
        }

        // Convert the map to a list of WeeklyAttendanceDTO
        List<WeeklyAttendanceDTO> weeklyAttendanceList = new ArrayList<>(weeklyAttendanceMap.values());

        // Add attributes to the model
        model.addAttribute("course", course);
        model.addAttribute("studentId", studentId);
        model.addAttribute("weeklyAttendanceList", weeklyAttendanceList);

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
    public String getStudentCourseAttendanceForParent(
            @PathVariable("stuid") Long studentId,
            @PathVariable("classid") Long classId,
            @PathVariable("parentid") Long parentId,
            Model model) {

        // Fetch all attendance records for the student in the given class
        List<Attendance> attendanceRecords = userService.getStudentAttendanceForCourse(studentId, classId);

        // Create a map to store weekly attendance records
        Map<Integer, WeeklyAttendanceDTO> weeklyAttendanceMap = new HashMap<>();

        for (Attendance record : attendanceRecords) {
            int week = record.getWeek();
            WeeklyAttendanceDTO dto = weeklyAttendanceMap.getOrDefault(week, new WeeklyAttendanceDTO());

            dto.setWeek(week);
            dto.setStudentName(record.getStudent().getFname() + " " + record.getStudent().getLname());
            dto.getDailyAttendance().put(record.getDay_of_week(), record.getStatus());

            weeklyAttendanceMap.put(week, dto);
        }

        // Convert the map to a list of WeeklyAttendanceDTO
        List<WeeklyAttendanceDTO> weeklyAttendanceList = new ArrayList<>(weeklyAttendanceMap.values());

        // Add attributes to the model
        model.addAttribute("studentId", studentId);
        model.addAttribute("classId", classId);
        model.addAttribute("parentId", parentId);
        model.addAttribute("weeklyAttendanceList", weeklyAttendanceList);

        return "parent_student_courses_attendance";  // Thymeleaf template name
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
