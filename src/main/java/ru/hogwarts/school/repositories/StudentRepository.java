package ru.hogwarts.school.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.hogwarts.school.model.Student;

import java.util.Collection;

public interface StudentRepository extends JpaRepository<Student, Long>{
    Collection<Student> findByAgeBetween(int age1, int age2);

    Collection<Student> findByAgeGreaterThan(int age1);

    Collection<Student> findByAgeLessThan(int age2);
}
