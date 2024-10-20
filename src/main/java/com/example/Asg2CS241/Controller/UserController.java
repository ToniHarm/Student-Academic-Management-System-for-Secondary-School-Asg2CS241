package com.example.Asg2CS241.Controller;

import com.example.Asg2CS241.Entity.CourseAdmin;
import com.example.Asg2CS241.Entity.CourseInstructor;
import com.example.Asg2CS241.Entity.Parent;
import com.example.Asg2CS241.Entity.Student;
import com.example.Asg2CS241.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class UserController {

    @Autowired
    private UserService stuRepo;

    @Autowired
    private UserService parRepo;

    @Autowired
    private UserService courTeacherRepo;

    @Autowired
    private UserService courAdminRepo;


    @RequestMapping(value = "/save1", method = RequestMethod.POST)
    public String saveNewStudent(@ModelAttribute("Student") Student Student) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String encodedPassword = passwordEncoder.encode(Student.getPassword());
        Student.setPassword(encodedPassword);

        stuRepo.save(Student);
        return "redirect:/login-student";
    }

    @RequestMapping(value = "/save2", method = RequestMethod.POST)
    public String saveNewParent(@ModelAttribute("Parent") Parent Parent) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String encodedPassword = passwordEncoder.encode(Parent.getPassword());
        Parent.setPassword(encodedPassword);

        parRepo.save(Parent);
        return "redirect:/login-parent";
    }

    @RequestMapping(value = "/save3", method = RequestMethod.POST)
    public String saveNewCourInstructor(@ModelAttribute("CourseInstructor") CourseInstructor CourseInstructor) {

        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String encodedPassword = passwordEncoder.encode(CourseInstructor.getPassword());
        CourseInstructor.setPassword(encodedPassword);


        courTeacherRepo.save(CourseInstructor);
        return "redirect:/login-instructor";
    }

    @RequestMapping(value = "/save4", method = RequestMethod.POST)
    public String saveNewCourAdmin(@ModelAttribute("CourseAdmin") CourseAdmin CourseAdmin) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String encodedPassword = passwordEncoder.encode(CourseAdmin.getPassword());
        CourseAdmin.setPassword(encodedPassword);

        courAdminRepo.save(CourseAdmin);
        return "redirect:/login-admin";
    }
}
