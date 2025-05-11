package ru.hogwarts.school.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.hogwarts.school.model.Faculty;
import org.springframework.stereotype.Service;
import ru.hogwarts.school.repositories.FacultyRepository;

import java.util.Collection;
import java.util.Comparator;

@Service
public class FacultyService{
    private final FacultyRepository facultyRepository;

    public FacultyService(FacultyRepository facultyRepository) {
        this.facultyRepository = facultyRepository;
    }
    Logger logger = LoggerFactory.getLogger(FacultyService.class);

    public Faculty add(Faculty faculty) {
        logger.info("Was invoked method for adding a faculty of {}", faculty.getName());
        faculty.setId(null);
        return facultyRepository.save(faculty);
    }

    public void delete(long id) {
        logger.info("Was invoked method for deleting a faculty  of {}", id);
        facultyRepository.deleteById(id);
    }

    public Faculty get(long id) {
        logger.info("Was invoked method for getting a faculty of {}", id);
        Faculty faculty = facultyRepository.findById(id).orElse(null);
        if (faculty == null) {
            logger.error("Faculty with id {} not found", id);
        }
        return faculty;
    }

    public Collection<Faculty> getAll() {
        logger.info("Was invoked method for getting all faculties");
        return facultyRepository.findAll();
    }

    public Faculty edit(Faculty faculty) {
        logger.info("Was invoked method for editing faculty {}", faculty.getName());
        if (!facultyRepository.existsById(faculty.getId())) {
            return null;
        }
        return facultyRepository.save(faculty);
    }

    public Collection<Faculty> findByName(String name) {
        logger.info("Was invoked method for finding a faculty by name {}", name);
        return facultyRepository.findFacultyByNameIgnoreCase(name);
    }

    public Collection<Faculty> findByColor(String color) {
        logger.info("Was invoked method for finding a faculty by color  {}", color);
        return facultyRepository.findFacultyByColorIgnoreCase(color);
    }

    public String getLongestName(){
        return facultyRepository
                .findAll()
                .stream()
                .map(Faculty::getName)
                .max(Comparator.comparing(String::length))
                .orElse(null);
    }
}
