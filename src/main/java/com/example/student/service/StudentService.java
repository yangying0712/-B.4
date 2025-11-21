package com.example.student.service;

import com.example.student.entity.Student;
import com.example.student.exception.StudentNotFoundException;
import com.example.student.repository.StudentRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * 学生业务逻辑层
 */
@Service
public class StudentService {

    private final StudentRepository studentRepository;

    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    /**
     * 获取所有学生
     */
    public List<Student> getAllStudents() {
        return studentRepository.findAll();
    }

    /**
     * 根据ID获取学生
     */
    public Optional<Student> getStudentById(Long id) {
        return studentRepository.findById(id);
    }

    /**
     * 创建学生
     */
    public Student createStudent(Student student) {
        return studentRepository.save(student);
    }

    /**
     * 更新学生
     */
    public Student updateStudent(Long id, Student studentDetails) {
        Student student = studentRepository.findById(id)
                .orElseThrow(() -> new StudentNotFoundException(id));
        
        student.setName(studentDetails.getName());
        student.setAge(studentDetails.getAge());
        student.setGrade(studentDetails.getGrade());
        student.setMajor(studentDetails.getMajor());
        
        return studentRepository.save(student);
    }

    /**
     * 删除学生
     */
    public void deleteStudent(Long id) {
        Student student = studentRepository.findById(id)
                .orElseThrow(() -> new StudentNotFoundException(id));
        studentRepository.delete(student);
    }
}
