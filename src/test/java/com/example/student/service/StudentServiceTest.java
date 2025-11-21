package com.example.student.service;

import com.example.student.entity.Student;
import com.example.student.exception.StudentNotFoundException;
import com.example.student.repository.StudentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

/**
 * StudentService 单元测试
 */
@ExtendWith(MockitoExtension.class)
class StudentServiceTest {

    @Mock
    private StudentRepository studentRepository;

    @InjectMocks
    private StudentService studentService;

    private Student testStudent;

    @BeforeEach
    void setUp() {
        testStudent = new Student(1L, "张三", 20, "大三", "计算机科学");
    }

    @Test
    void testGetAllStudents() {
        // 准备测试数据
        List<Student> students = Arrays.asList(
                testStudent,
                new Student(2L, "李四", 21, "大四", "软件工程")
        );
        when(studentRepository.findAll()).thenReturn(students);

        // 执行测试
        List<Student> result = studentService.getAllStudents();

        // 验证结果
        assertEquals(2, result.size());
        assertEquals("张三", result.get(0).getName());
        assertEquals("李四", result.get(1).getName());
        verify(studentRepository, times(1)).findAll();
    }

    @Test
    void testGetStudentById_Success() {
        // 准备测试数据
        when(studentRepository.findById(1L)).thenReturn(Optional.of(testStudent));

        // 执行测试
        Optional<Student> result = studentService.getStudentById(1L);

        // 验证结果
        assertTrue(result.isPresent());
        assertEquals("张三", result.get().getName());
        assertEquals(20, result.get().getAge());
        verify(studentRepository, times(1)).findById(1L);
    }

    @Test
    void testGetStudentById_NotFound() {
        // 准备测试数据
        when(studentRepository.findById(999L)).thenReturn(Optional.empty());

        // 执行测试
        Optional<Student> result = studentService.getStudentById(999L);

        // 验证结果
        assertFalse(result.isPresent());
        verify(studentRepository, times(1)).findById(999L);
    }

    @Test
    void testCreateStudent() {
        // 准备测试数据
        Student newStudent = new Student(null, "王五", 19, "大二", "数据科学");
        Student savedStudent = new Student(3L, "王五", 19, "大二", "数据科学");
        when(studentRepository.save(any(Student.class))).thenReturn(savedStudent);

        // 执行测试
        Student result = studentService.createStudent(newStudent);

        // 验证结果
        assertNotNull(result.getId());
        assertEquals("王五", result.getName());
        assertEquals(19, result.getAge());
        assertEquals("大二", result.getGrade());
        assertEquals("数据科学", result.getMajor());
        verify(studentRepository, times(1)).save(any(Student.class));
    }

    @Test
    void testUpdateStudent_Success() {
        // 准备测试数据
        Student updatedDetails = new Student(null, "张三更新", 21, "大四", "人工智能");
        when(studentRepository.findById(1L)).thenReturn(Optional.of(testStudent));
        when(studentRepository.save(any(Student.class))).thenReturn(testStudent);

        // 执行测试
        Student result = studentService.updateStudent(1L, updatedDetails);

        // 验证结果
        assertEquals("张三更新", result.getName());
        assertEquals(21, result.getAge());
        assertEquals("大四", result.getGrade());
        assertEquals("人工智能", result.getMajor());
        verify(studentRepository, times(1)).findById(1L);
        verify(studentRepository, times(1)).save(any(Student.class));
    }

    @Test
    void testUpdateStudent_NotFound() {
        // 准备测试数据
        Student updatedDetails = new Student(null, "张三更新", 21, "大四", "人工智能");
        when(studentRepository.findById(999L)).thenReturn(Optional.empty());

        // 执行测试并验证异常
        assertThrows(StudentNotFoundException.class, () -> {
            studentService.updateStudent(999L, updatedDetails);
        });

        verify(studentRepository, times(1)).findById(999L);
        verify(studentRepository, never()).save(any(Student.class));
    }

    @Test
    void testDeleteStudent_Success() {
        // 准备测试数据
        when(studentRepository.findById(1L)).thenReturn(Optional.of(testStudent));
        doNothing().when(studentRepository).delete(any(Student.class));

        // 执行测试
        assertDoesNotThrow(() -> studentService.deleteStudent(1L));

        // 验证结果
        verify(studentRepository, times(1)).findById(1L);
        verify(studentRepository, times(1)).delete(testStudent);
    }

    @Test
    void testDeleteStudent_NotFound() {
        // 准备测试数据
        when(studentRepository.findById(999L)).thenReturn(Optional.empty());

        // 执行测试并验证异常
        assertThrows(StudentNotFoundException.class, () -> {
            studentService.deleteStudent(999L);
        });

        verify(studentRepository, times(1)).findById(999L);
        verify(studentRepository, never()).delete(any(Student.class));
    }
}
