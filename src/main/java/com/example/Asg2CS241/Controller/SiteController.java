package com.example.Asg2CS241.Controller;

import com.example.Asg2CS241.Entity.CourseAdmin;
import com.example.Asg2CS241.Entity.CourseInstructor;
import com.example.Asg2CS241.Entity.Parent;
import com.example.Asg2CS241.Entity.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import com.example.Asg2CS241.Service.UserService;


@Controller
public class SiteController {


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



    @GetMapping("/register-student")
    public String showRegisterStudent(Model model) {
        model.addAttribute("Student", new Student());
        return "RegisterStudent";  // This returns the 'register.html' template
    }

    @GetMapping("/register-parent")
    public String showRegisterParent(Model model) {
        model.addAttribute("Parent", new Parent());
        return "RegisterParent";  // This returns the 'register.html' template
    }

    @GetMapping("/register-admin")
    public String showRegisterAdmin(Model model) {
        model.addAttribute("CourseAdmin", new CourseAdmin());
        return "RegisterAdmin";  // This returns the 'register.html' template
    }

    @GetMapping("/register-instructor")
    public String showRegisterInstructor(Model model) {
        model.addAttribute("CourseInstructor", new CourseInstructor());
        return "RegisterInstructor";  // This returns the 'register.html' template
    }

    @GetMapping("/StudentDashboard")
    public String showStudentDashboard(){
        return "StudentDashboard";
    }

    @GetMapping("/CourseAdminDashboard")
    public String showAdminDashboard() {
        return "CourseAdminDashboard";  // Return the admin dashboard view
    }

    @GetMapping("/CourseInstructorDashboard")
    public String showInstructorDashboard() {
        return "CourseInstructorDashboard";  // Return the student dashboard view
    }

    @GetMapping("/ParentDashboard")
    public String showParentDashboard() {
        return "ParentDashboard";  // Return the student dashboard view
    }

}
