package ru.hogwarts.school;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import ru.hogwarts.school.controller.FacultyController;
import ru.hogwarts.school.controller.StudentController;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class UniversityApplicationRestTemplateTest {
    @LocalServerPort
    private int port;

    @Autowired
    private FacultyController facultyController;

    @Autowired
    private StudentController studentController;

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    void contextFacultyLoads() throws Exception {
        assertThat(facultyController).isNotNull();
    }

    @Test
    void contextStudentLoads() throws Exception {
        assertThat(studentController).isNotNull();
    }

    @Test
    public void testStudentController() throws Exception {
        assertThat(this.restTemplate.getForObject(
                "http://localhost:" + port + "/student/forTest", String.class
        ))
                .isEqualTo("All the students here are good!");
    }

    @Test
    public void testFacultyController() throws Exception {
        assertThat(this.restTemplate.getForObject(
                "http://localhost:" + port + "/faculty/forTest", String.class
        ))
                .isEqualTo("This is a good faculty!");
    }

    @Test
    public void testStudentGetMapping() throws Exception {
        assertThat(this.restTemplate.getForObject(
                "http://localhost:" + port + "/student", String.class
        ))
                .isNotNull();
    }


    @Test
    public void testFacultyGetMapping() throws Exception {
        assertThat(this.restTemplate.getForObject(
                "http://localhost:" + port + "/faculty", String.class
        ))
                .isNotNull();
    }

}
