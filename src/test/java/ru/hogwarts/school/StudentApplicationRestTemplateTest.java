package ru.hogwarts.school;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class StudentApplicationRestTemplateTest {
    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void testStudentController() {
        assertThat(this.restTemplate.getForObject(
                "http://localhost:" + port + "/student/forTest", String.class
        ))
                .isEqualTo("All the students here are good!");
    }


    @Test
    public void testStudentGetMapping() {
        assertThat(this.restTemplate.getForObject(
                "http://localhost:" + port + "/student", String.class
        ))
                .isNotNull();
    }
}
