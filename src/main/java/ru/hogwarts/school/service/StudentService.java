package ru.hogwarts.school.service;

import ru.hogwarts.school.model.Student;
import org.springframework.stereotype.Service;
import ru.hogwarts.school.repositories.StudentRepository;

import java.util.Collection;

@Service
public class StudentService{
    private final StudentRepository studentRepository;

    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    public Student add(Student student) {
        student.setId(null);
        return studentRepository.save(student);
    }

    public Student get(long id) {
        return studentRepository.findById(id).orElse(null);
    }

    public Collection<Student> getAll() {
        return studentRepository.findAll();
    }

    public Student edit(Student student) {
        if (get(student.getId()) == null){
            return null;
        }
        return studentRepository.save(student);
    }

    public void delete(long id) {
        studentRepository.deleteById(id);
        }
}
