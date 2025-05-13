package ru.hogwarts.school.controller;

import ru.hogwarts.school.model.Faculty;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.hogwarts.school.service.FacultyService;

import java.util.Collection;

@RestController
@RequestMapping("/faculty")
public class FacultyController{
    private final FacultyService facultyService;

    public FacultyController(FacultyService facultyService) {
        this.facultyService = facultyService;
    }

    @GetMapping("/forTest")
    public ResponseEntity getInfoAboutFaculty(){
        return ResponseEntity.ok("This is a good faculty!");
    }


    @GetMapping("{id}")
    public ResponseEntity<Faculty> get(@PathVariable long id) {
        Faculty faculty = facultyService.get(id);
        if (faculty == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(faculty);
    }

    @PostMapping
    public Faculty add(@RequestBody Faculty faculty) {
        return facultyService.add(faculty);
    }

    @PutMapping
    public ResponseEntity<Faculty> edit(@RequestBody Faculty faculty) {
        long id = faculty.getId();
        if (facultyService.get(id) == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(facultyService.edit(faculty));
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Faculty> delete(@PathVariable Long id) {
        facultyService.delete(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping
    public ResponseEntity<Collection<Faculty>> getAll(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String color
    ) {
        Collection<Faculty>collection;
        if (name != null && !name.isBlank()) {
            collection = facultyService.findByName(name);
        } else if (color != null && !color.isBlank()) {
            collection = facultyService.findByColor(color);
        } else {
            collection = facultyService.getAll();
        }
        if (collection.isEmpty()){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(collection);
    }

    @GetMapping("/longestName")
    public String getLongestName(){
        return facultyService.getLongestName();
    }
}
