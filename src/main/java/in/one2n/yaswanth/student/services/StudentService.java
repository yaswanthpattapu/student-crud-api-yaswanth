package in.one2n.yaswanth.student.services;

import in.one2n.yaswanth.student.entities.Gender;
import in.one2n.yaswanth.student.entities.Student;
import in.one2n.yaswanth.student.repositories.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class StudentService {

    private final StudentRepository studentRepository;

    @Autowired
    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    public List<Student> getAllStudents() {
        return studentRepository.findAll(Sort.by(Sort.Direction.ASC, "name"));
    }

    public Optional<Student> getStudentById(long id) {
        if (!studentRepository.existsById(id))
            throw new IllegalStateException("Student with id " + id + " does not exists");
        return studentRepository.findById(id);
    }

    public Student addNewStudent(Student student) {
        Optional<Student> studentByEmail = studentRepository.findStudentByEmail(student.getEmail());
        if (studentByEmail.isPresent()) throw new IllegalStateException("Email already taken");
        student.setCreated_at(new Timestamp(System.currentTimeMillis()));
        student.setUpdated_at(new Timestamp(System.currentTimeMillis()));
        return studentRepository.save(student);
    }

    public Student updateStudent(Student student) {
        boolean modified = false;
        long student_id = student.getId();
        Student existingStudent = studentRepository.findById(student_id).orElseThrow(
                () -> new IllegalStateException("Student with id " + student_id + " does not exists"));

        String name = student.getName();
        if (name != null && name.length() > 0 && !Objects.equals(name, existingStudent.getName())) {
            existingStudent.setName(name);
            modified = true;
        }

        String email = student.getEmail();
        if (email != null && email.length() > 0 && !Objects.equals(email, existingStudent.getEmail())) {
            Optional<Student> studentByEmail = studentRepository.findStudentByEmail(student.getEmail());
            if (studentByEmail.isPresent()) throw new IllegalStateException("Email already taken");
            existingStudent.setEmail(email);
            modified = true;
        }

        String address = student.getAddress();
        if (address != null && address.length() > 0 && !Objects.equals(address, existingStudent.getAddress())) {
            existingStudent.setAddress(address);
            modified = true;
        }

        Gender gender = student.getGender();
        if (gender != null && gender.toString().length() > 0 && !Objects.equals(gender, existingStudent.getGender())) {
            existingStudent.setGender(gender);
            modified = true;
        }

        LocalDate dob = student.getDob();
        if (dob != null && !Objects.equals(dob, existingStudent.getDob())) {
            existingStudent.setDob(dob);
            modified = true;
        }

        if (modified)
            existingStudent.setUpdated_at(new Timestamp(System.currentTimeMillis()));
        return studentRepository.save(existingStudent);
    }

    public void deleteStudent(long id) {
        boolean exists = studentRepository.existsById(id);
        if (!exists) throw new IllegalStateException("Student with id " + id + " does not exists");
        studentRepository.deleteById(id);
    }

}
