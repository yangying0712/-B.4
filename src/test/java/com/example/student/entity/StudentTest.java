package com.example.student.entity;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Student 实体类验证测试
 */
class StudentTest {

    private Validator validator;

    @BeforeEach
    void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    void testValidStudent() {
        // 创建有效的学生对象
        Student student = new Student(1L, "张三", 20, "大三", "计算机科学");

        // 验证
        Set<ConstraintViolation<Student>> violations = validator.validate(student);

        // 断言没有验证错误
        assertTrue(violations.isEmpty());
    }

    @Test
    void testNameNotBlank() {
        // 创建姓名为空的学生对象
        Student student = new Student(1L, "", 20, "大三", "计算机科学");

        // 验证
        Set<ConstraintViolation<Student>> violations = validator.validate(student);

        // 断言有验证错误
        assertFalse(violations.isEmpty());
        assertEquals(1, violations.size());
        assertEquals("姓名不能为空", violations.iterator().next().getMessage());
    }

    @Test
    void testNameNotNull() {
        // 创建姓名为null的学生对象
        Student student = new Student(1L, null, 20, "大三", "计算机科学");

        // 验证
        Set<ConstraintViolation<Student>> violations = validator.validate(student);

        // 断言有验证错误
        assertFalse(violations.isEmpty());
    }

    @Test
    void testAgeNotNull() {
        // 创建年龄为null的学生对象
        Student student = new Student(1L, "张三", null, "大三", "计算机科学");

        // 验证
        Set<ConstraintViolation<Student>> violations = validator.validate(student);

        // 断言有验证错误
        assertFalse(violations.isEmpty());
        assertEquals("年龄不能为空", violations.iterator().next().getMessage());
    }

    @Test
    void testAgeMin() {
        // 创建年龄小于1的学生对象
        Student student = new Student(1L, "张三", 0, "大三", "计算机科学");

        // 验证
        Set<ConstraintViolation<Student>> violations = validator.validate(student);

        // 断言有验证错误
        assertFalse(violations.isEmpty());
        assertEquals("年龄必须大于0", violations.iterator().next().getMessage());
    }

    @Test
    void testAgeMax() {
        // 创建年龄大于150的学生对象
        Student student = new Student(1L, "张三", 151, "大三", "计算机科学");

        // 验证
        Set<ConstraintViolation<Student>> violations = validator.validate(student);

        // 断言有验证错误
        assertFalse(violations.isEmpty());
        assertEquals("年龄必须小于150", violations.iterator().next().getMessage());
    }

    @Test
    void testGradeNotBlank() {
        // 创建年级为空的学生对象
        Student student = new Student(1L, "张三", 20, "", "计算机科学");

        // 验证
        Set<ConstraintViolation<Student>> violations = validator.validate(student);

        // 断言有验证错误
        assertFalse(violations.isEmpty());
        assertEquals("年级不能为空", violations.iterator().next().getMessage());
    }

    @Test
    void testMajorNotBlank() {
        // 创建专业为空的学生对象
        Student student = new Student(1L, "张三", 20, "大三", "");

        // 验证
        Set<ConstraintViolation<Student>> violations = validator.validate(student);

        // 断言有验证错误
        assertFalse(violations.isEmpty());
        assertEquals("专业不能为空", violations.iterator().next().getMessage());
    }

    @Test
    void testGettersAndSetters() {
        // 创建学生对象
        Student student = new Student();

        // 测试 setters
        student.setId(1L);
        student.setName("李四");
        student.setAge(21);
        student.setGrade("大四");
        student.setMajor("软件工程");

        // 测试 getters
        assertEquals(1L, student.getId());
        assertEquals("李四", student.getName());
        assertEquals(21, student.getAge());
        assertEquals("大四", student.getGrade());
        assertEquals("软件工程", student.getMajor());
    }

    @Test
    void testToString() {
        // 创建学生对象
        Student student = new Student(1L, "王五", 19, "大二", "数据科学");

        // 测试 toString
        String expected = "Student{id=1, name='王五', age=19, grade='大二', major='数据科学'}";
        assertEquals(expected, student.toString());
    }

    @Test
    void testConstructorWithAllParameters() {
        // 测试带参数的构造函数
        Student student = new Student(1L, "赵六", 22, "研一", "人工智能");

        assertEquals(1L, student.getId());
        assertEquals("赵六", student.getName());
        assertEquals(22, student.getAge());
        assertEquals("研一", student.getGrade());
        assertEquals("人工智能", student.getMajor());
    }

    @Test
    void testNoArgsConstructor() {
        // 测试无参构造函数
        Student student = new Student();

        assertNotNull(student);
        assertNull(student.getId());
        assertNull(student.getName());
        assertNull(student.getAge());
        assertNull(student.getGrade());
        assertNull(student.getMajor());
    }
}
