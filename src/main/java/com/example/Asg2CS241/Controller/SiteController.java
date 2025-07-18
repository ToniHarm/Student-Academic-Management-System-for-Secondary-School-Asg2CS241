package com.example.Asg2CS241.Controller;

import com.example.Asg2CS241.Entity.*;
import com.example.Asg2CS241.Security.CustomCourseAdminDetails;
import com.example.Asg2CS241.Security.CustomCourseInstructorDetails;
import com.example.Asg2CS241.Security.CustomParentDetails;
import com.example.Asg2CS241.Security.CustomStudentDetails;
import com.example.Asg2CS241.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Set;


@Controller
public class SiteController {
    @Autowired
    private UserService userService;

    @GetMapping("/")
    public String restingPage() {
        return "LoginStudent";
    }
    @GetMapping("login-student")
    public String loginStudent() {
        return "LoginStudent";
    }
    @GetMapping("/login-admin")
    public String loginAdmin() {
        return "LoginAdmin";
    }
    @GetMapping("/login-parent")
    public String loginParent() {
        return "LoginParent";
    }
    @GetMapping("login-instructor")
    public String loginInstructor() {
        return "LoginInstructor";
    }
    @PostMapping("/login-student")
    public String loginStudent(@RequestParam("email") String email, @RequestParam("password") String password, Model model) {
        return "redirect:/StudentDashboard";
    }
    @PostMapping("/login-admin")
    public String loginAdmin(@RequestParam("email") String email, @RequestParam("password") String password, Model model) {
            return "redirect:/CourseAdminDashboard";
    }
    @PostMapping("/login-parent")
    public String loginParent(@RequestParam("email") String email, @RequestParam("password") String password, Model model) {
        return "redirect:/ParentDashboard";
    }
    @PostMapping("/login-instructor")
    public String loginInstructor(@RequestParam("email") String email, @RequestParam("password") String password, Model model) {
        return "redirect:/CourseInstructorDashboard";
    }



    @GetMapping("/CourseAdminDashboard/register-student")
    public String showRegisterStudent(Model model) {
        model.addAttribute("Student", new Student());
        return "RegisterStudent";  // This returns the 'register.html' template
    }

    @GetMapping("/CourseAdminDashboard/register-parent")
    public String showRegisterParent(Model model) {
        model.addAttribute("Parent", new Parent());
        return "RegisterParent";  // This returns the 'register.html' template
    }

    @GetMapping("/CourseAdminDashboard/register-admin")
    public String showRegisterAdmin(Model model) {
        model.addAttribute("CourseAdmin", new CourseAdmin());
        return "RegisterAdmin";  // This returns the 'register.html' template
    }

    @GetMapping("/CourseAdminDashboard/register-instructor")
    public String showRegisterInstructor(Model model) {
        model.addAttribute("CourseInstructor", new CourseInstructor());
        return "RegisterInstructor";  // This returns the 'register.html' template
    }

    @Autowired
    private UserService stuRepo;

    @GetMapping("/StudentDashboard")
    public String showStudentDashboard(Model model) {
        // Get the currently authenticated user
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        CustomStudentDetails userDetails = (CustomStudentDetails) authentication.getPrincipal();

        // Fetch the student entity using the student ID from the user details
        Long studentId = userDetails.getId();  // Assuming CustomStudentDetails has getId()

        // Add the studentId to the model so it can be accessed in the view
        model.addAttribute("studentId", studentId);

        return "StudentDashboard";
    }

    @GetMapping("/CourseAdminDashboard")
    public String showAdminDashboard(Model model) {

        // Get the currently authenticated user
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        CustomCourseAdminDetails userDetails = (CustomCourseAdminDetails) authentication.getPrincipal();

        // Fetch the student entity using the student ID from the user details
        Long courseadminId = userDetails.getId();  // Assuming CustomStudentDetails has getId()

        // Add the studentId to the model so it can be accessed in the view
        model.addAttribute("courseAdminId", courseadminId);


        return "CourseAdminDashboard";  // Return the admin dashboard view
    }

    @GetMapping("/CourseInstructorDashboard")
    public String showInstructorDashboard(Model model) {
        // Get the currently authenticated user
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        CustomCourseInstructorDetails userDetails = (CustomCourseInstructorDetails) authentication.getPrincipal();

        // Fetch the course instructor ID from the user details
        Long courseinstructorId = userDetails.getId();

        // Add the instructor ID to the model so it can be used in the view
        model.addAttribute("courseInstructorId", courseinstructorId);

        return "CourseInstructorDashboard";  // This is your Thymeleaf template for the dashboard
    }

    @GetMapping("/ParentDashboard")
    public String showParentDashboard(Model model) {

        // Get the currently authenticated user
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        CustomParentDetails userDetails = (CustomParentDetails) authentication.getPrincipal();

        // Fetch the student entity using the student ID from the user details
        Long parentId = userDetails.getId();  // Assuming CustomStudentDetails has getId()


        // Fetch all students linked to the parent
        Set<Student> students = userService.getStudentsByParentId(parentId);
        model.addAttribute("students", students);
        model.addAttribute("parentId", parentId);



        // Add the studentId to the model so it can be accessed in the view
        model.addAttribute("parentId", parentId);





        return "ParentDashboard";  // Return the student dashboard view
    }

    @Autowired
    private UserService courseRepo;

    @GetMapping("/CourseAdminDashboard/register-course")
    public String showCourseCreatePage(Model model){
        model.addAttribute("Course", new Course());
        return "create_course";
    }

    @RequestMapping(value = "/save-course", method = RequestMethod.POST)
    public String saveCourse(@ModelAttribute("Course") Course course) {

        courseRepo.save(course);

        return "redirect:/CourseAdminDashboard";
    }

    @GetMapping("/CourseAdminDashboard/registerStudentToClass")
    public String showRegistrationForm(Model model) {
        // Add the DTO object to the model
        model.addAttribute("studentCourseRegistrationDTO", new StudentCourseRegistrationDTO());
        return "RegisterStudentToCourse";
    }

    @PostMapping("/CourseAdminDashboard/registerStudentToClass/save")
    public String registerStudentToClass(@ModelAttribute StudentCourseRegistrationDTO registrationDTO, Model model) {
        Long studentId = registrationDTO.getStudentId();
        Long classId = registrationDTO.getClassId();

        // Add the student to the class via UserService
        userService.registerStudentToClass(studentId, classId);

        return "redirect:/CourseAdminDashboard";  // Redirect to a success or confirmation page
    }

    @GetMapping("/CourseAdminDashboard/registerInstructorToClass")
    public String showRegistrationFormInstructor(Model model) {
        // Add the DTO object to the model
        model.addAttribute("instructorCourseRegistrationDTO", new InstructorCourseRegistrationDTO());
        return "RegisterInstructorToCourse";
    }

    @PostMapping("/CourseAdminDashboard/registerInstructorToClass/save")
    public String registerInstructorToClass(@ModelAttribute InstructorCourseRegistrationDTO registrationDTO, Model model) {
        Long instructorId = registrationDTO.getCourseInstructorId();
        Long classId = registrationDTO.getClassId();

        // Add the student to the class via UserService
        userService.registerInstructorToClass(instructorId, classId);

        return "redirect:/CourseAdminDashboard";  // Redirect to a success or confirmation page
    }






}
