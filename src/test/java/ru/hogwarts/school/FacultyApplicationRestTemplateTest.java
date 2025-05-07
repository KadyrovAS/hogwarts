package ru.hogwarts.school;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class FacultyApplicationRestTemplateTest {
    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void testFacultyController() {
        assertThat(this.restTemplate.getForObject(
                "http://localhost:" + port + "/faculty/forTest", String.class
        ))
                .isEqualTo("This is a good faculty!");
    }


    @Test
    public void testFacultyGetMapping() {
        assertThat(this.restTemplate.getForObject(
                "http://localhost:" + port + "/faculty", String.class
        ))
                .isNotNull();
    }
}
