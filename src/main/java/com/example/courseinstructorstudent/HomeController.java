package com.example.courseinstructorstudent;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;

@Controller
public class HomeController {
    @Autowired
    CourseRepository courseRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    StudentRepository studentRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    UserService userService;

   @GetMapping("/register")
    public String showRegistration(Model model){
       model.addAttribute("user", new User());
       return "register";
   }

    @PostMapping("/register")
    public String processRegistrationPage(@Valid @ModelAttribute("user") User user, BindingResult result, Model model, @RequestParam("password") String pw){
        System.out.println("pw: " + pw);
        if(result.hasErrors()){
//            model.addAttribute("user", user);
            return "register";
        } else {
            user.encode(pw);
            userService.saveUser(user);
            model.addAttribute("message", "New User Account Created");
        }
        return "login";
    }

    @RequestMapping("/login")
    public String login(){
        return "login";
    }

    //ASK DAVE!!!
    @RequestMapping("/admin")
    public String admin(){
        return "admin";
    }

    //ASK DAVE!!!
    @RequestMapping("/secure")
    public String secure(Principal principal, Model model){
        User myuser = ((CustomerUserDetails)
                ((UsernamePasswordAuthenticationToken) principal)
                        .getPrincipal())
                .getUser();
        model.addAttribute("myuser", myuser);
        return "secure";
    }

    @RequestMapping("/")
    public String listCourses(Model model) {
        model.addAttribute("courses", courseRepository.findAll()); //generate select * statement
        if(userService.getUser() != null){
            model.addAttribute("user_id", userService.getUser().getId());
        }
        return "list";
    }

    @GetMapping("/addcourse")
    public String courseForm(Model model) {
        model.addAttribute("course", new Course());
        return "courseform";
    }

    @PostMapping("/process")
    public String processForm(@Valid Course course, BindingResult result) {
        if (result.hasErrors()) {
            return "courseform";
        }

        course.setUser(userService.getUser());
        courseRepository.save(course);//generate SQL statement and insert into database
        return "redirect:/";
    }


    @RequestMapping("/addstudent/{id}")
    public String addStudent(@PathVariable("id") long id, Model model){
       model.addAttribute("course", courseRepository.findById(id).get());
    return "redirect:/addstudent";
    }

    @GetMapping("/addstudent")
    public String addStudent(Model model, @ModelAttribute Course course){
       model.addAttribute("student", new Student());
       model.addAttribute("courses", courseRepository.findAll());
       return "studentform";
    }

    @PostMapping("/processstudent")
    public String processstudentform(@Valid Student student, BindingResult result){
        if(result.hasErrors()){
            return "studentform";
        }
        studentRepository.save(student);
        return "redirect:/";
    }

    @RequestMapping("/detail/{id}")
    public String showCourse(@PathVariable("id") long id, Model model) {
       Course course = courseRepository.findById(id).get();
        model.addAttribute("course", course);
        model.addAttribute("students", studentRepository.findAllByCourse(course));
        return "show";
    }
    @RequestMapping("/enroll/{id}")
    public String enrollCourse(@PathVariable("id") long id, Model model){
        model.addAttribute("student", studentRepository.findById(id).get());
        return "studentenrolled";
    }
    @RequestMapping("/student")
    public String studentPage(Model model){
        model.addAttribute("courses", courseRepository.findAll());
        model.addAttribute("user", userService.getUser());

        return "studentenrolled";
    }

    @RequestMapping("/update/{id}")
    public String updateCourse(@PathVariable("id") long id, Model model) {
        model.addAttribute("course", courseRepository.findById(id).get());
        return "courseform";
    }

    @RequestMapping("/delete/{id}")
    public String delCourse(@PathVariable("id") long id){
        courseRepository.deleteById(id);
        return "redirect:/";
    }

    @GetMapping("/termsandconditions")
    public String getTermsAndCondition(){
        return "termsandconditions";
    }

    @PostMapping("/forgot-password")
    public String forgetPassword(){
        return "/";
    }

    @GetMapping("/about")
    public String getAbout(){
        return "about";
    }
}


