package ru.hogwarts.school.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.service.StudentService;

import java.util.Collection;

@RestController
@RequestMapping("/student")
public class StudentController{
    private final StudentService studentService;

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @GetMapping("{id}")
    public ResponseEntity<Student> get(@PathVariable long id){
        Student student = studentService.get(id);
        if (student == null){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(student);
    }

    @PostMapping
    public Student add(@RequestBody Student student){
        return studentService.add(student);
    }

    @PutMapping
    public ResponseEntity<Student> edit(@RequestBody Student student){
        long id = student.getId();
        if (studentService.get(id) == null){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(studentService.edit(student));
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Student> delete(@PathVariable Long id){
        studentService.delete(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/getAll")
    public Collection<Student> getAll(){
        return studentService.getAll();
    }

}
