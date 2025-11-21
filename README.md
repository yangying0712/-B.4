# 学生管理系统 RESTful API

程序实训B.4 - Spring Boot 学生信息管理系统

## 项目简介

这是一个基于Spring Boot开发的学生管理系统RESTful API，实现了对学生信息的增删改查（CRUD）操作。

## 功能特性

- ✅ RESTful API设计规范
- ✅ 统一的返回格式（Result类）
- ✅ 完整的CRUD操作
- ✅ 输入验证
- ✅ 错误处理
- ✅ H2内存数据库

## 技术栈

- Spring Boot 2.7.14
- Spring Data JPA
- H2 Database
- Bean Validation
- Maven

## API端点

| 方法 | 端点 | 功能 |
|------|------|------|
| GET | /students | 获取所有学生列表 |
| GET | /students/{id} | 根据学号获取学生 |
| POST | /students | 创建新学生 |
| PUT | /students/{id} | 更新学生信息 |
| DELETE | /students/{id} | 删除学生 |

## 快速开始

### 运行项目

```bash
mvn spring-boot:run
```

应用将在 http://localhost:8080 启动

### 访问H2控制台

- URL: http://localhost:8080/h2-console
- JDBC URL: jdbc:h2:mem:studentdb
- 用户名: sa
- 密码: （留空）

## 文档

- [API使用说明](API使用说明.md) - 详细的API使用指南和Postman测试说明
- [项目总结](项目总结.md) - 项目完成情况和测试结果

## 项目结构

```
src/main/java/com/example/student/
├── StudentManagementApplication.java    # 主应用入口
├── common/
│   └── Result.java                      # 统一返回结果类
├── controller/
│   └── StudentController.java           # RESTful API控制器
├── entity/
│   └── Student.java                     # 学生实体类
├── exception/
│   └── StudentNotFoundException.java    # 自定义异常
├── repository/
│   └── StudentRepository.java           # 数据访问接口
└── service/
    └── StudentService.java              # 业务逻辑层
```

## 验收标准

✅ 使用Spring Boot开发RESTful API  
✅ 实现增删改查功能  
✅ 正常访问数据库  
✅ 按照RESTful格式发送和返回报文  
✅ 可使用Postman展示功能
