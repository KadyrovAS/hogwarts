package ru.hogwarts.school.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.hogwarts.school.model.Student;

import java.util.Collection;

public interface StudentRepository extends JpaRepository<Student, Long>{
    Collection<Student> findByAgeBetween(int age1, int age2);

    Collection<Student> findByAgeGreaterThan(int age1);

    Collection<Student> findByAgeLessThan(int age2);

    @Query(value = "select count(*) from student", nativeQuery = true)
    int getNumberOfStudents();

    @Query(value = "select avg(age) from student", nativeQuery = true)
    float getAvgAgeOfStudents();

    @Query(value = "select * from student order by id desc limit :limit", nativeQuery = true)
    Collection<Student>findByFiveLastStudents(@Param("limit") int limit);
}
