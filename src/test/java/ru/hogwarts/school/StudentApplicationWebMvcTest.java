package ru.hogwarts.school;

import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.context.bean.override.mockito.MockitoSpyBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import ru.hogwarts.school.controller.StudentController;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repositories.StudentRepository;
import ru.hogwarts.school.service.StudentService;


import java.util.Collection;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(StudentController.class)
@Import(StudentService.class)
public class StudentApplicationWebMvcTest {
    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private StudentRepository studentRepository;

    @MockitoSpyBean
    private StudentService studentService;

    private Student student;
    private Faculty faculty;
    private JSONObject studentObject = new JSONObject();

    @BeforeEach
    public void initValue() throws Exception {
        Long id = 1L;
        String name = "Ivan";
        int age = 19;

        studentObject.put("name", name);
        studentObject.put("age", age);

        student = new Student();
        student.setId(id);
        student.setName(name);
        student.setAge(age);

        faculty = new Faculty();
        faculty.setId(1L);
        faculty.setColor("yellow");
        faculty.setName("Java Faculty");
        student.setFaculty(faculty);

        when(studentRepository.findById(any(Long.class))).thenReturn(Optional.ofNullable(student));
        when(studentRepository.save(any(Student.class))).thenReturn(student);
        when(studentRepository.existsById(any(Long.class))).thenReturn(true);
        when(studentRepository.findAll())
                .thenReturn((List<Student>) List.of(student));
        when(studentRepository.findByAgeGreaterThan(any(Integer.class)))
                .thenReturn((Collection<Student>) List.of(student));
        when(studentRepository.findByAgeLessThan(any(Integer.class)))
                .thenReturn((Collection<Student>) List.of(student));
        when(studentRepository.findByAgeBetween(any(Integer.class), any(Integer.class)))
                .thenReturn((Collection<Student>) List.of(student));
    }

    @Test
    public void postStudentTest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                        .post("/student")
                        .content(studentObject.toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(student.getId()))
                .andExpect(jsonPath("$.name").value(student.getName()))
                .andExpect(jsonPath("$.age").value(student.getAge()));
    }

    @Test
    public void getByIdStudentTest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/student/{id}", student.getId())
                        .content(studentObject.toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(student.getId()))
                .andExpect(jsonPath("$.name").value(student.getName()))
                .andExpect(jsonPath("$.age").value(student.getAge()));
    }

    @Test
    public void putStudentTest() throws Exception {
        studentObject.put("id", student.getId());
        mockMvc.perform(MockMvcRequestBuilders
                        .put("/student")
                        .content(studentObject.toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(student.getId()))
                .andExpect(jsonPath("$.name").value(student.getName()))
                .andExpect(jsonPath("$.age").value(student.getAge()));
    }

    @Test
    public void deleteStudentTest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                        .delete("/student/{id}", student.getId())
                        .content(studentObject.toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void findByAgeBetweenStudentTest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/student?ageMin=10&ageMax=20")
                        .content(studentObject.toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(student.getId()))
                .andExpect(jsonPath("$[0].name").value(student.getName()))
                .andExpect(jsonPath("$[0].age").value(student.getAge()));
    }

    @Test
    public void findByAgeGreaterThanStudentTest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/student?ageMin=10")
                        .content(studentObject.toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(student.getId()))
                .andExpect(jsonPath("$[0].name").value(student.getName()))
                .andExpect(jsonPath("$[0].age").value(student.getAge()));
    }

    @Test
    public void findByAgeLessThanStudentTest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/student?ageMax=20")
                        .content(studentObject.toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(student.getId()))
                .andExpect(jsonPath("$[0].name").value(student.getName()))
                .andExpect(jsonPath("$[0].age").value(student.getAge()));
    }

    @Test
    public void findAllStudentTest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/student")
                        .content(studentObject.toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(student.getId()))
                .andExpect(jsonPath("$[0].name").value(student.getName()))
                .andExpect(jsonPath("$[0].age").value(student.getAge()));
    }
}
