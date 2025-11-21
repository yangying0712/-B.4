# 学生管理系统 RESTful API 使用说明

## 项目介绍

这是一个基于Spring Boot开发的学生管理系统RESTful API，实现了对学生信息的增删改查（CRUD）操作。

## 技术栈

- Spring Boot 2.7.14
- Spring Data JPA
- H2 内存数据库
- Maven

## 学生实体属性

- `id`: 学号（Long类型，自动生成）
- `name`: 姓名（String类型）
- `age`: 年龄（Integer类型）
- `grade`: 年级（String类型）
- `major`: 专业（String类型）

## 统一返回格式

所有API都使用统一的Result类返回数据：

```json
{
  "code": 200,
  "message": "Success",
  "data": { ... }
}
```

- `code`: 状态码（200表示成功，404表示未找到，500表示错误）
- `message`: 返回消息
- `data`: 返回的数据（可能为对象、数组或null）

## API端点说明

### 1. 获取所有学生列表

**请求方式**: `GET`

**请求URL**: `http://localhost:8080/students`

**请求示例（Postman）**:
- Method: GET
- URL: http://localhost:8080/students

**返回示例**:
```json
{
  "code": 200,
  "message": "Success",
  "data": [
    {
      "id": 1,
      "name": "张三",
      "age": 20,
      "grade": "大二",
      "major": "计算机科学与技术"
    },
    {
      "id": 2,
      "name": "李四",
      "age": 22,
      "grade": "大四",
      "major": "软件工程"
    }
  ]
}
```

---

### 2. 根据学号获取学生

**请求方式**: `GET`

**请求URL**: `http://localhost:8080/students/{id}`

**路径参数**:
- `id`: 学生ID

**请求示例（Postman）**:
- Method: GET
- URL: http://localhost:8080/students/1

**返回示例**:
```json
{
  "code": 200,
  "message": "Success",
  "data": {
    "id": 1,
    "name": "张三",
    "age": 20,
    "grade": "大二",
    "major": "计算机科学与技术"
  }
}
```

**错误示例（学生不存在）**:
```json
{
  "code": 404,
  "message": "学生不存在，ID: 999",
  "data": null
}
```

---

### 3. 创建新学生

**请求方式**: `POST`

**请求URL**: `http://localhost:8080/students`

**请求头**:
- Content-Type: application/json

**请求Body**:
```json
{
  "name": "张三",
  "age": 20,
  "grade": "大二",
  "major": "计算机科学与技术"
}
```

**请求示例（Postman）**:
- Method: POST
- URL: http://localhost:8080/students
- Headers: Content-Type: application/json
- Body (raw JSON):
  ```json
  {
    "name": "张三",
    "age": 20,
    "grade": "大二",
    "major": "计算机科学与技术"
  }
  ```

**返回示例**:
```json
{
  "code": 200,
  "message": "创建学生成功",
  "data": {
    "id": 1,
    "name": "张三",
    "age": 20,
    "grade": "大二",
    "major": "计算机科学与技术"
  }
}
```

---

### 4. 更新学生信息

**请求方式**: `PUT`

**请求URL**: `http://localhost:8080/students/{id}`

**路径参数**:
- `id`: 要更新的学生ID

**请求头**:
- Content-Type: application/json

**请求Body**:
```json
{
  "name": "李四",
  "age": 22,
  "grade": "大四",
  "major": "软件工程"
}
```

**请求示例（Postman）**:
- Method: PUT
- URL: http://localhost:8080/students/2
- Headers: Content-Type: application/json
- Body (raw JSON):
  ```json
  {
    "name": "李四",
    "age": 22,
    "grade": "大四",
    "major": "软件工程"
  }
  ```

**返回示例**:
```json
{
  "code": 200,
  "message": "更新学生成功",
  "data": {
    "id": 2,
    "name": "李四",
    "age": 22,
    "grade": "大四",
    "major": "软件工程"
  }
}
```

**错误示例（学生不存在）**:
```json
{
  "code": 404,
  "message": "Student not found with id: 999",
  "data": null
}
```

---

### 5. 删除学生

**请求方式**: `DELETE`

**请求URL**: `http://localhost:8080/students/{id}`

**路径参数**:
- `id`: 要删除的学生ID

**请求示例（Postman）**:
- Method: DELETE
- URL: http://localhost:8080/students/3

**返回示例**:
```json
{
  "code": 200,
  "message": "删除学生成功",
  "data": null
}
```

**错误示例（学生不存在）**:
```json
{
  "code": 404,
  "message": "Student not found with id: 999",
  "data": null
}
```

---

## 如何运行项目

### 1. 使用Maven运行

```bash
mvn spring-boot:run
```

### 2. 打包后运行

```bash
# 打包
mvn clean package

# 运行
java -jar target/student-management-1.0.0.jar
```

应用将在 `http://localhost:8080` 启动。

## H2数据库控制台

项目使用H2内存数据库，可以通过以下方式访问数据库控制台：

- URL: http://localhost:8080/h2-console
- JDBC URL: jdbc:h2:mem:studentdb
- 用户名: sa
- 密码: （留空）

## Postman测试步骤

### 完整测试流程

1. **启动应用**
   ```bash
   mvn spring-boot:run
   ```

2. **创建第一个学生**
   - Method: POST
   - URL: http://localhost:8080/students
   - Body:
     ```json
     {
       "name": "张三",
       "age": 20,
       "grade": "大二",
       "major": "计算机科学与技术"
     }
     ```

3. **创建第二个学生**
   - Method: POST
   - URL: http://localhost:8080/students
   - Body:
     ```json
     {
       "name": "李四",
       "age": 21,
       "grade": "大三",
       "major": "软件工程"
     }
     ```

4. **获取所有学生列表**
   - Method: GET
   - URL: http://localhost:8080/students

5. **获取单个学生**
   - Method: GET
   - URL: http://localhost:8080/students/1

6. **更新学生信息**
   - Method: PUT
   - URL: http://localhost:8080/students/2
   - Body:
     ```json
     {
       "name": "李四",
       "age": 22,
       "grade": "大四",
       "major": "软件工程"
     }
     ```

7. **删除学生**
   - Method: DELETE
   - URL: http://localhost:8080/students/2

8. **验证删除（再次获取所有学生）**
   - Method: GET
   - URL: http://localhost:8080/students

9. **测试错误处理**
   - Method: GET
   - URL: http://localhost:8080/students/999
   - 预期返回404错误

## 测试结果说明

所有API端点均已测试通过：

✅ POST /students - 创建学生成功
✅ GET /students - 获取所有学生列表成功
✅ GET /students/{id} - 获取单个学生成功
✅ PUT /students/{id} - 更新学生信息成功
✅ DELETE /students/{id} - 删除学生成功
✅ 错误处理 - 404错误返回正确

所有接口均遵循RESTful规范，使用统一的Result类返回数据。

## 注意事项

1. 本项目使用H2内存数据库，数据在应用重启后会丢失
2. 默认端口为8080，如需修改请编辑 `application.properties` 文件
3. 所有POST和PUT请求的Content-Type必须设置为 `application/json`
4. ID字段由数据库自动生成，创建学生时无需提供ID
