package ru.hogwarts.school.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.hogwarts.school.model.Student;
import org.springframework.stereotype.Service;
import ru.hogwarts.school.repositories.StudentRepository;

import java.util.Arrays;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Stream;

@Service
public class StudentService{

    class PrintStudents extends Thread{
        private List<Student>studentList;
        private int[] indexes;
        private boolean isSynchronized;
        public PrintStudents(List<Student> studentList, int[] indexes, boolean isSynchronized){
            this.studentList = studentList;
            this.indexes = indexes;
        }

        @Override
        public void run(){
            if (isSynchronized) {
                Arrays.stream(indexes).forEach(i -> printStudentSynchronized(studentList.get(i)));
            }else{
                Arrays.stream(indexes).forEach(i -> printStudentParallel(studentList.get(i)));
            }
        }
    }

    private static final Logger logger = LoggerFactory.getLogger(StudentService.class);
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

    public Collection<Student>getSortedStudents(){
        logger.info("Was invoked method for getting sorted students");
        return studentRepository
                .findAll()
                .stream()
                .parallel()
                .peek(s->s.setName(s.getName().toUpperCase()))
                .sorted(Comparator.comparing(Student::getName))
                .toList();
    }

    public double getAvgAge(){
        logger.info("Was invoked method #2 for getting avg age of students");
        return studentRepository
                .findAll()
                .stream()
                .mapToInt(Student::getAge)
                .average()
                .orElse(0.0);
    }

    public long getSumOfIterate1(){
        int value = 1_000_000;
        return (1L + value) * value / 2;
    }

    public long getSumOfIterate2(){
        int value = 1_000_000;
        return Stream
                .iterate(1L, a->a + 1)
                .limit(value)
                .parallel()
                .reduce(0L, Long::sum);
    }

    public void printParallel(){
        List<Student>studentList = studentRepository.findAll();

        Stream.of(0, 1).forEach(i-> printStudentParallel(studentList.get(i)));
        new PrintStudents(studentList, new int[]{2, 3}, false).start();
        new PrintStudents(studentList, new int[]{4, 5}, false).start();
    }

    public void printSynchronized(){
        List<Student>studentList = studentRepository.findAll();

        Stream.of(0, 1).forEach(i-> printStudentParallel(studentList.get(i)));
        new PrintStudents(studentList, new int[]{2, 3}, false).start();
        new PrintStudents(studentList, new int[]{4, 5}, false).start();
    }


    private synchronized void printStudentParallel(Student student){
        System.out.println(student + " from thread " + Thread.currentThread().getName());
    }
    private synchronized void printStudentSynchronized(Student student){
        System.out.println(student + " from thread " + Thread.currentThread().getName());
    }
}
