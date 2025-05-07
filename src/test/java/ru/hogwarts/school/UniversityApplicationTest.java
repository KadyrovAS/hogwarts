package ru.hogwarts.school;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.hogwarts.school.controller.FacultyController;
import ru.hogwarts.school.controller.StudentController;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class UniversityApplicationTest {
    @Autowired
    private FacultyController facultyController;

    @Autowired
    private StudentController studentController;

    @Test
    void contextFacultyLoads() {
        assertThat(facultyController).isNotNull();
    }

    @Test
    void contextStudentLoads() {
        assertThat(studentController).isNotNull();
    }
}
