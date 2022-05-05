package in.one2n.yaswanth.student.controllers;

import in.one2n.yaswanth.student.entities.Student;
import in.one2n.yaswanth.student.services.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(path = "api/v1")
public class StudentController {

    private final StudentService studentService;

    @Autowired
    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @GetMapping("/students")
    public List<Student> getAllStudents() {
        return studentService.getAllStudents();
    }

    @GetMapping("/students/{id}")
    public Optional<Student> getStudentById(@PathVariable long id) {
        return studentService.getStudentById(id);
    }

    @PostMapping("/students")
    @ResponseBody
    public Student addNewStudent(@RequestBody Student student) {
        return studentService.addNewStudent(student);
    }

    @PutMapping("/students")
    @ResponseBody
    public Student updateStudent(@RequestBody Student student) {
        return studentService.updateStudent(student);
    }

    @DeleteMapping("/students/{id}")
    public void deleteStudent(@PathVariable long id) {
        studentService.deleteStudent(id);
    }
}
