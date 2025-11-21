package com.example.student.exception;

/**
 * 学生未找到异常
 */
public class StudentNotFoundException extends RuntimeException {
    
    public StudentNotFoundException(Long id) {
        super("Student not found with id: " + id);
    }
    
    public StudentNotFoundException(String message) {
        super(message);
    }
}
