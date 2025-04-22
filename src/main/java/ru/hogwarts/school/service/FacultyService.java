package ru.hogwarts.school.service;

import ru.hogwarts.school.model.Faculty;
import org.springframework.stereotype.Service;
import ru.hogwarts.school.repositories.FacultyRepository;

import java.util.Collection;

@Service
public class FacultyService{
    private final FacultyRepository facultyRepository;

    public FacultyService(FacultyRepository facultyRepository) {
        this.facultyRepository = facultyRepository;
    }

    public Faculty add(Faculty faculty){
        faculty.setId(null);
        return facultyRepository.save(faculty);
    }

    public void delete(long id){
        facultyRepository.deleteById(id);
    }

    public Faculty get(long id){
        return facultyRepository.findById(id).orElse(null);
    }

    public Collection<Faculty> getAll(){
        return facultyRepository.findAll();
    }

    public Faculty edit(Faculty faculty){
        if (get(faculty.getId()) == null){
            return null;
        }
        return facultyRepository.save(faculty);
    }
}
