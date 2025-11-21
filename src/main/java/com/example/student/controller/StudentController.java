package com.example.student.controller;

import com.example.student.common.Result;
import com.example.student.entity.Student;
import com.example.student.exception.StudentNotFoundException;
import com.example.student.service.StudentService;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

/**
 * 学生控制器 - RESTful API
 */
@RestController
@RequestMapping("/students")
public class StudentController {

    private final StudentService studentService;

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    /**
     * 获取所有学生列表
     * GET /students
     */
    @GetMapping
    public Result<List<Student>> getAllStudents() {
        try {
            List<Student> students = studentService.getAllStudents();
            return Result.success(students);
        } catch (Exception e) {
            return Result.error("获取学生列表失败: " + e.getMessage());
        }
    }

    /**
     * 根据学号获取学生
     * GET /students/{id}
     */
    @GetMapping("/{id}")
    public Result<Student> getStudentById(@PathVariable Long id) {
        try {
            Optional<Student> student = studentService.getStudentById(id);
            if (student.isPresent()) {
                return Result.success(student.get());
            } else {
                return Result.error(404, "学生不存在，ID: " + id);
            }
        } catch (Exception e) {
            return Result.error("获取学生信息失败: " + e.getMessage());
        }
    }

    /**
     * 创建一个新学生
     * POST /students
     */
    @PostMapping
    public Result<Student> createStudent(@Valid @RequestBody Student student) {
        try {
            Student createdStudent = studentService.createStudent(student);
            return Result.success("创建学生成功", createdStudent);
        } catch (Exception e) {
            return Result.error("创建学生失败: " + e.getMessage());
        }
    }

    /**
     * 更新一个已存在的学生信息
     * PUT /students/{id}
     */
    @PutMapping("/{id}")
    public Result<Student> updateStudent(@PathVariable Long id, @Valid @RequestBody Student student) {
        try {
            Student updatedStudent = studentService.updateStudent(id, student);
            return Result.success("更新学生成功", updatedStudent);
        } catch (StudentNotFoundException e) {
            return Result.error(404, e.getMessage());
        } catch (Exception e) {
            return Result.error("更新学生失败: " + e.getMessage());
        }
    }

    /**
     * 删除一个学生
     * DELETE /students/{id}
     */
    @DeleteMapping("/{id}")
    public Result<Void> deleteStudent(@PathVariable Long id) {
        try {
            studentService.deleteStudent(id);
            return Result.success("删除学生成功", null);
        } catch (StudentNotFoundException e) {
            return Result.error(404, e.getMessage());
        } catch (Exception e) {
            return Result.error("删除学生失败: " + e.getMessage());
        }
    }
}
