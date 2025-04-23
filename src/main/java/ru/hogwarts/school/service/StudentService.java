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
        if (!studentRepository.existsById(student.getId())) {
            return null;
        }
        return studentRepository.save(student);
    }

    public void delete(long id) {
        studentRepository.deleteById(id);
    }

    public Collection<Student> findByAgeBetween(int age1, int age2) {
        return studentRepository.findByAgeBetween(age1, age2);
    }

    public Collection<Student> findByAgeGreaterThan(int age1) {
        return studentRepository.findByAgeGreaterThan(age1);
    }

    public Collection<Student> findByAgeLessThan(int age2) {
        return studentRepository.findByAgeLessThan(age2);
    }
}
