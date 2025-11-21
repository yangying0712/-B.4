package com.example.student.controller;

import com.example.student.entity.Student;
import com.example.student.exception.StudentNotFoundException;
import com.example.student.service.StudentService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * StudentController 集成测试
 */
@WebMvcTest(StudentController.class)
class StudentControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private StudentService studentService;

    private Student testStudent;

    @BeforeEach
    void setUp() {
        testStudent = new Student(1L, "张三", 20, "大三", "计算机科学");
    }

    @Test
    void testGetAllStudents() throws Exception {
        // 准备测试数据
        List<Student> students = Arrays.asList(
                testStudent,
                new Student(2L, "李四", 21, "大四", "软件工程")
        );
        when(studentService.getAllStudents()).thenReturn(students);

        // 执行测试并验证结果
        mockMvc.perform(get("/students"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(200))
                .andExpect(jsonPath("$.message").value("Success"))
                .andExpect(jsonPath("$.data", hasSize(2)))
                .andExpect(jsonPath("$.data[0].name").value("张三"))
                .andExpect(jsonPath("$.data[1].name").value("李四"));

        verify(studentService, times(1)).getAllStudents();
    }

    @Test
    void testGetStudentById_Success() throws Exception {
        // 准备测试数据
        when(studentService.getStudentById(1L)).thenReturn(Optional.of(testStudent));

        // 执行测试并验证结果
        mockMvc.perform(get("/students/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(200))
                .andExpect(jsonPath("$.message").value("Success"))
                .andExpect(jsonPath("$.data.id").value(1))
                .andExpect(jsonPath("$.data.name").value("张三"))
                .andExpect(jsonPath("$.data.age").value(20))
                .andExpect(jsonPath("$.data.grade").value("大三"))
                .andExpect(jsonPath("$.data.major").value("计算机科学"));

        verify(studentService, times(1)).getStudentById(1L);
    }

    @Test
    void testGetStudentById_NotFound() throws Exception {
        // 准备测试数据
        when(studentService.getStudentById(999L)).thenReturn(Optional.empty());

        // 执行测试并验证结果
        mockMvc.perform(get("/students/999"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(404))
                .andExpect(jsonPath("$.message").value("学生不存在，ID: 999"));

        verify(studentService, times(1)).getStudentById(999L);
    }

    @Test
    void testCreateStudent_Success() throws Exception {
        // 准备测试数据
        Student newStudent = new Student(null, "王五", 19, "大二", "数据科学");
        Student savedStudent = new Student(3L, "王五", 19, "大二", "数据科学");
        when(studentService.createStudent(any(Student.class))).thenReturn(savedStudent);

        // 执行测试并验证结果
        mockMvc.perform(post("/students")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(newStudent)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(200))
                .andExpect(jsonPath("$.message").value("创建学生成功"))
                .andExpect(jsonPath("$.data.id").value(3))
                .andExpect(jsonPath("$.data.name").value("王五"));

        verify(studentService, times(1)).createStudent(any(Student.class));
    }

    @Test
    void testCreateStudent_ValidationError() throws Exception {
        // 准备测试数据 - 缺少必填字段
        Student invalidStudent = new Student(null, "", null, "", "");

        // 执行测试并验证结果
        mockMvc.perform(post("/students")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalidStudent)))
                .andExpect(status().isBadRequest());

        verify(studentService, never()).createStudent(any(Student.class));
    }

    @Test
    void testUpdateStudent_Success() throws Exception {
        // 准备测试数据
        Student updatedStudent = new Student(1L, "张三更新", 21, "大四", "人工智能");
        when(studentService.updateStudent(eq(1L), any(Student.class))).thenReturn(updatedStudent);

        // 执行测试并验证结果
        mockMvc.perform(put("/students/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updatedStudent)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(200))
                .andExpect(jsonPath("$.message").value("更新学生成功"))
                .andExpect(jsonPath("$.data.name").value("张三更新"))
                .andExpect(jsonPath("$.data.age").value(21));

        verify(studentService, times(1)).updateStudent(eq(1L), any(Student.class));
    }

    @Test
    void testUpdateStudent_NotFound() throws Exception {
        // 准备测试数据
        Student updatedStudent = new Student(999L, "张三更新", 21, "大四", "人工智能");
        when(studentService.updateStudent(eq(999L), any(Student.class)))
                .thenThrow(new StudentNotFoundException(999L));

        // 执行测试并验证结果
        mockMvc.perform(put("/students/999")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updatedStudent)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(404));

        verify(studentService, times(1)).updateStudent(eq(999L), any(Student.class));
    }

    @Test
    void testDeleteStudent_Success() throws Exception {
        // 准备测试数据
        doNothing().when(studentService).deleteStudent(1L);

        // 执行测试并验证结果
        mockMvc.perform(delete("/students/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(200))
                .andExpect(jsonPath("$.message").value("删除学生成功"));

        verify(studentService, times(1)).deleteStudent(1L);
    }

    @Test
    void testDeleteStudent_NotFound() throws Exception {
        // 准备测试数据
        doThrow(new StudentNotFoundException(999L)).when(studentService).deleteStudent(999L);

        // 执行测试并验证结果
        mockMvc.perform(delete("/students/999"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(404));

        verify(studentService, times(1)).deleteStudent(999L);
    }
}
