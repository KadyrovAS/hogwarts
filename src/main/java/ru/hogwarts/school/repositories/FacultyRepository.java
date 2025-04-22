package ru.hogwarts.school.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.hogwarts.school.model.Faculty;

import java.util.Collection;

public interface FacultyRepository extends JpaRepository<Faculty, Long>{
    Collection<Faculty> findFacultyByNameIgnoreCase(String name);

    Collection<Faculty> findFacultyByColorIgnoreCase(String color);
}

