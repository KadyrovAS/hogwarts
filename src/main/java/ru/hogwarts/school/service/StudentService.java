package ru.hogwarts.school.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.hogwarts.school.model.Student;
import org.springframework.stereotype.Service;
import ru.hogwarts.school.repositories.StudentRepository;

import java.util.Collection;

@Service
public class StudentService{
    Logger logger = LoggerFactory.getLogger(StudentService.class);
    private final StudentRepository studentRepository;

    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    public Student add(Student student) {
        logger.info("Was invoked method for adding student");
        student.setId(null);
        return studentRepository.save(student);
    }

    public Student get(long id) {
        logger.info("Was invoked method for getting student by id = {}", id);
        Student student = studentRepository.findById(id).orElse(null);
        if (student == null) {
            logger.error("Student with id {} not found", id);
        }
        return student;
    }

    public Collection<Student> getAll() {
        logger.info("Was invoked method for getting all students");
        return studentRepository.findAll();
    }

    public Student edit(Student student) {
        logger.info("Was invoked method for editing student {}", student);
        if (!studentRepository.existsById(student.getId())) {
            return null;
        }
        return studentRepository.save(student);
    }

    public void delete(long id) {
        logger.info("Was invoked method for deleting student by id {}", id);
        studentRepository.deleteById(id);
    }

    public Collection<Student> findByAgeBetween(int age1, int age2) {
        logger.info("Was invoked method for finding by age between {} and {}", age1, age2);
        return studentRepository.findByAgeBetween(age1, age2);
    }

    public Collection<Student> findByAgeGreaterThan(int age1) {
        logger.info("Was invoked method for finding by age greater than {}", age1);
        return studentRepository.findByAgeGreaterThan(age1);
    }

    public Collection<Student> findByAgeLessThan(int age2) {
        logger.info("Was invoked method for finding by age less than {}", age2);
        return studentRepository.findByAgeLessThan(age2);
    }

    public int getNumberOfStudents(){
        logger.info("Was invoked method for getting number of students");
        return studentRepository.getNumberOfStudents();
    }

    public float getAvgAgeOfStudents(){
        logger.info("Was invoked method for getting avg age of students");
        return studentRepository.getAvgAgeOfStudents();
    }

    public Collection<Student>findByLastStudents(int limit){
        logger.info("Was invoked method for finding by last {} students", limit);
        return studentRepository.findByFiveLastStudents(limit);
    }
}
