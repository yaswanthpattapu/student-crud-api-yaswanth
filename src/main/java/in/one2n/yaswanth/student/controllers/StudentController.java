package in.one2n.yaswanth.student.controllers;

import in.one2n.yaswanth.student.entities.Student;
import in.one2n.yaswanth.student.services.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
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
    public void addNewStudent(@RequestBody Student student) {
        student.setCreated_at(new Timestamp(System.currentTimeMillis()));
        student.setUpdated_at(new Timestamp(System.currentTimeMillis()));
        studentService.addNewStudent(student);
    }

    @DeleteMapping("/students/{id}")
    public void deleteStudent(@PathVariable long id) {
        studentService.deleteStudent(id);
    }
}
