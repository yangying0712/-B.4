package com.example.student.common;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Result 统一返回结果类测试
 */
class ResultTest {

    @Test
    void testSuccessWithData() {
        // 创建成功的结果对象
        String data = "测试数据";
        Result<String> result = Result.success(data);

        // 验证结果
        assertEquals(200, result.getCode());
        assertEquals("Success", result.getMessage());
        assertEquals("测试数据", result.getData());
    }

    @Test
    void testSuccessWithMessageAndData() {
        // 创建带自定义消息的成功结果对象
        String data = "测试数据";
        String message = "操作成功";
        Result<String> result = Result.success(message, data);

        // 验证结果
        assertEquals(200, result.getCode());
        assertEquals("操作成功", result.getMessage());
        assertEquals("测试数据", result.getData());
    }

    @Test
    void testErrorWithMessage() {
        // 创建错误的结果对象
        Result<String> result = Result.error("操作失败");

        // 验证结果
        assertEquals(500, result.getCode());
        assertEquals("操作失败", result.getMessage());
        assertNull(result.getData());
    }

    @Test
    void testErrorWithCodeAndMessage() {
        // 创建带自定义错误码的错误结果对象
        Result<String> result = Result.error(404, "资源未找到");

        // 验证结果
        assertEquals(404, result.getCode());
        assertEquals("资源未找到", result.getMessage());
        assertNull(result.getData());
    }

    @Test
    void testNoArgsConstructor() {
        // 测试无参构造函数
        Result<String> result = new Result<>();

        // 验证结果
        assertNotNull(result);
        assertNull(result.getCode());
        assertNull(result.getMessage());
        assertNull(result.getData());
    }

    @Test
    void testAllArgsConstructor() {
        // 测试全参构造函数
        Result<String> result = new Result<>(200, "成功", "数据");

        // 验证结果
        assertEquals(200, result.getCode());
        assertEquals("成功", result.getMessage());
        assertEquals("数据", result.getData());
    }

    @Test
    void testSetters() {
        // 创建结果对象并测试 setters
        Result<String> result = new Result<>();
        result.setCode(201);
        result.setMessage("创建成功");
        result.setData("新数据");

        // 验证结果
        assertEquals(201, result.getCode());
        assertEquals("创建成功", result.getMessage());
        assertEquals("新数据", result.getData());
    }

    @Test
    void testGetters() {
        // 创建结果对象并测试 getters
        Result<Integer> result = new Result<>(200, "成功", 100);

        // 验证 getters
        assertEquals(200, result.getCode());
        assertEquals("成功", result.getMessage());
        assertEquals(100, result.getData());
    }

    @Test
    void testSuccessWithNullData() {
        // 测试带 null 数据的成功结果
        Result<String> result = Result.success(null);

        // 验证结果
        assertEquals(200, result.getCode());
        assertEquals("Success", result.getMessage());
        assertNull(result.getData());
    }

    @Test
    void testGenericTypeInteger() {
        // 测试整数类型的泛型
        Result<Integer> result = Result.success(42);

        // 验证结果
        assertEquals(200, result.getCode());
        assertEquals("Success", result.getMessage());
        assertEquals(42, result.getData());
    }

    @Test
    void testGenericTypeObject() {
        // 测试对象类型的泛型
        TestObject obj = new TestObject("测试", 123);
        Result<TestObject> result = Result.success(obj);

        // 验证结果
        assertEquals(200, result.getCode());
        assertEquals("Success", result.getMessage());
        assertNotNull(result.getData());
        assertEquals("测试", result.getData().name);
        assertEquals(123, result.getData().value);
    }

    // 辅助测试类
    private static class TestObject {
        String name;
        int value;

        TestObject(String name, int value) {
            this.name = name;
            this.value = value;
        }
    }
}
