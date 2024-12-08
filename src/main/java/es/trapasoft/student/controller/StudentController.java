package es.trapasoft.student.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class StudentController {
    
    @GetMapping("/")
    public String showPage(Model model) {
        model.addAttribute("imageName", "http-cat-200.png");
        return "index";
    }
 

//    @GetMapping("/")
//    public String home(Model model) {
//        model.addAttribute("students", studentService.getAllStudents());
//        return "students";
//    }    
//    
//    @GetMapping("/students")
//    public String listStudents(Model model) {
//        model.addAttribute("students", studentService.getAllStudents());
//        return "students";
//    }
//    
//    @GetMapping("/students/new")
//    public String createStudentForm(Model model){
//        
//        // este objeto Student almacenara los valores 
//        Student student = new Student();
//       
//        model.addAttribute("student", student);
//        model.addAttribute("coursesList", coursesList);
//
//        return "create_student";
//    }
//    
//    @PostMapping("/students")
//    public String saveStudent(@ModelAttribute("student") Student student) {
//        studentService.saveStudent(student);
//        return "redirect:/students";
//    }
//    
//    @GetMapping("/students/edit/{id}")
//    public String editStudentForm(@PathVariable Long id, Model model) {
//        Student st = studentService.getStudentById(id);
//        
//        model.addAttribute("student", st);
//        model.addAttribute("coursesList", coursesList);
//        
//        return "edit_student";
//    }
//    
//    @PostMapping("/students/{id}")
//    public String updateStudent(@PathVariable Long id, 
//            @ModelAttribute("student") Student student,
//            Model model) {
//        //sacar el esudiante de la b.d. por el id
//        Student existentStudent = studentService.getStudentById(id);
//        // cargarlo
//        existentStudent.setId(id);
//        existentStudent.setFirstName(student.getFirstName());
//        existentStudent.setLastName(student.getLastName());
//        existentStudent.setEmail(student.getEmail());
//        existentStudent.setCourses(student.getCourses());
//
//        // guardar el estudiante actualizado
//        studentService.updateStudent(existentStudent);
//        
//        return "redirect:/students";
//    }
//    
//    @GetMapping("/students/{id}")
//    public String deleteStudent(@PathVariable Long id) {
//        studentService.deleteStudentById(id);
//        return "redirect:/students";
//    }
//  
}
