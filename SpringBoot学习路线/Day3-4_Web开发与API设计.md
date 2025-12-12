# Day 3-4: Web 开发与 API 设计

> **学习目标**：掌握 RESTful API 设计和统一的异常处理
>
> **预计时间**：2天（每天3小时）
>
> **学习方式**：理论 + 实战
>
> **适合人群**：已完成 Day 1-2 学习的开发者

---

## 📚 学习内容

### 1. RESTful API 设计规范
### 2. 请求映射与参数接收
### 3. 统一返回格式封装
### 4. 统一异常处理
### 5. 参数校验

---

## 🔰 前置知识：核心概念扫盲

### 什么是 API？

**API（Application Programming Interface）** = 应用程序编程接口

**通俗理解**：
- API 是"服务的菜单"
- 客户端（浏览器、手机 App）通过 API 向服务器"点菜"
- 服务器处理请求，返回数据

**例子**：
```
客户端：我要查询 ID 为 1 的用户信息
API：GET /users/1
服务器：返回 {"id": 1, "name": "张三", "email": "zhangsan@example.com"}
```

---

### 什么是 RESTful API？

**REST（Representational State Transfer）** = 表述性状态转移

**核心思想**：

1. **资源（Resource）**：万物皆资源，用 URL 表示
2. **表述（Representation）**：资源的表现形式（JSON、XML）
3. **状态转移（State Transfer）**：通过 HTTP 方法改变资源状态

**类比**：
> 把互联网看作一个"图书馆"：
> - **资源**：每本书都是一个资源
> - **URL**：书的编号（如：`/books/123`）
> - **HTTP 方法**：对书的操作（借书、还书、查看）
> - **表述**：书的信息（书名、作者、ISBN）

---

### RESTful API 的 6 大约束

1. **客户端-服务器分离**：前后端分离
2. **无状态**：每次请求独立，不依赖 Session
3. **可缓存**：支持 HTTP 缓存
4. **统一接口**：统一的 URL 和 HTTP 方法
5. **分层系统**：可以有多层代理
6. **按需代码**（可选）：服务器可以返回可执行代码

**记住**：RESTful 是一种"风格"，不是"标准"，灵活运用即可！

---

### HTTP 方法与 CRUD 的对应关系

| HTTP 方法 | CRUD 操作 | 说明 | 是否幂等 |
|-----------|----------|------|---------|
| **GET** | Read（查询） | 获取资源 | ✅ 是 |
| **POST** | Create（创建） | 创建资源 | ❌ 否 |
| **PUT** | Update（更新） | 完整更新资源 | ✅ 是 |
| **PATCH** | Update（更新） | 部分更新资源 | ❌ 否 |
| **DELETE** | Delete（删除） | 删除资源 | ✅ 是 |

**幂等性（Idempotent）**：
- **幂等**：多次请求结果相同（如：`GET /users/1`）
- **非幂等**：多次请求结果不同（如：`POST /users`，每次创建新用户）

---

### 什么是状态码？

**HTTP 状态码** = 服务器告诉客户端"请求处理结果"

**常用状态码**：

| 类别 | 状态码 | 说明 | 使用场景 |
|------|--------|------|---------|
| **2xx 成功** | 200 OK | 请求成功 | 查询、更新成功 |
| | 201 Created | 创建成功 | 创建资源成功 |
| | 204 No Content | 成功但无内容 | 删除成功 |
| **3xx 重定向** | 301 Moved Permanently | 永久重定向 | URL 已永久更改 |
| | 302 Found | 临时重定向 | 临时跳转 |
| **4xx 客户端错误** | 400 Bad Request | 请求参数错误 | 参数校验失败 |
| | 401 Unauthorized | 未认证 | 需要登录 |
| | 403 Forbidden | 无权限 | 权限不足 |
| | 404 Not Found | 资源不存在 | 找不到资源 |
| | 405 Method Not Allowed | 方法不允许 | 用错 HTTP 方法 |
| **5xx 服务器错误** | 500 Internal Server Error | 服务器内部错误 | 代码异常 |
| | 502 Bad Gateway | 网关错误 | 后端服务挂了 |
| | 503 Service Unavailable | 服务不可用 | 服务器过载 |

**记住**：
- **2xx**：成功，客户端可以继续
- **4xx**：客户端的锅（参数错误、权限不足）
- **5xx**：服务器的锅（代码 Bug、数据库挂了）

---

## 🎨 第一部分：RESTful API 设计规范

### 1.1 URL 设计规范

#### 规则1：使用名词，不使用动词

**❌ 错误示例**：
```
GET  /getUser?id=1        # 动词 + 查询参数
POST /createUser          # 动词
POST /deleteUser?id=1     # 动词
```

**✅ 正确示例**：
```
GET    /users/1           # 名词 + ID
POST   /users             # 名词
DELETE /users/1           # 名词 + ID
```

**原因**：HTTP 方法已经表达了"动作"，URL 只需要表示"资源"。

---

#### 规则2：使用复数名词

**❌ 错误示例**：

```
GET /user/1               # 单数
GET /product/123          # 单数
```

**✅ 正确示例**：
```
GET /users/1              # 复数
GET /products/123         # 复数
```

**原因**：保持一致性，避免 `/user` 和 `/users` 混用。

---

#### 规则3：使用小写字母和连字符

**❌ 错误示例**：
```
GET /Users/1              # 大写
GET /user_profiles/1      # 下划线
GET /userProfiles/1       # 驼峰
```

**✅ 正确示例**：
```
GET /users/1              # 小写
GET /user-profiles/1      # 连字符（kebab-case）
```

**原因**：URL 不区分大小写，容易混乱；连字符比下划线更易读。

---

#### 规则4：体现资源的层级关系

**✅ 正确示例**：
```
GET /users/1/orders              # 获取用户 1 的所有订单
GET /users/1/orders/123          # 获取用户 1 的订单 123
POST /users/1/orders             # 为用户 1 创建订单
DELETE /users/1/orders/123       # 删除用户 1 的订单 123
```

**注意**：不要超过 3 层，太深不易理解。

---

#### 规则5：使用查询参数进行过滤、排序、分页

**✅ 正确示例**：
```
# 分页
GET /users?page=1&size=10

# 排序
GET /users?sort=createdAt,desc

# 过滤
GET /users?status=active&role=admin

# 搜索
GET /users?search=张三

# 组合使用
GET /users?status=active&page=1&size=10&sort=createdAt,desc
```

---

### 1.2 完整的 RESTful API 设计示例

以"用户管理"为例：

| 功能 | HTTP 方法 | URL | 说明 |
|------|----------|-----|------|
| 获取用户列表 | GET | `/users` | 返回用户列表 |
| 获取单个用户 | GET | `/users/{id}` | 返回指定用户 |
| 创建用户 | POST | `/users` | 请求体包含用户信息 |
| 更新用户（完整） | PUT | `/users/{id}` | 请求体包含完整用户信息 |
| 更新用户（部分） | PATCH | `/users/{id}` | 请求体包含部分字段 |
| 删除用户 | DELETE | `/users/{id}` | 删除指定用户 |
| 获取用户的订单 | GET | `/users/{id}/orders` | 返回用户的订单列表 |
| 搜索用户 | GET | `/users?search=张三` | 返回匹配的用户 |

---

### 1.3 RESTful API 最佳实践

#### 1. 版本控制

**方式1：URL 路径（推荐）**
```
GET /api/v1/users
GET /api/v2/users
```

**方式2：请求头**
```
GET /api/users
Accept: application/vnd.myapp.v1+json
```

**方式3：查询参数**
```
GET /api/users?version=1
```

**推荐**：使用 URL 路径，直观明了。

---

#### 2. 使用统一的基础路径

**✅ 推荐**：
```
/api/v1/users
/api/v1/products
/api/v1/orders
```

**好处**：
- 区分 API 和页面路由
- 便于网关统一管理
- 方便版本升级

---

#### 3. 返回完整的资源信息

**❌ 不推荐**：
```json
{
  "id": 1,
  "name": "张三"
}
```

**✅ 推荐**：
```json
{
  "id": 1,
  "name": "张三",
  "email": "zhangsan@example.com",
  "createdAt": "2024-01-01T00:00:00Z",
  "updatedAt": "2024-01-01T00:00:00Z"
}
```

**好处**：客户端可以根据需要选择使用哪些字段。

---

#### 4. 使用 HATEOAS（可选）

**HATEOAS（Hypermedia As The Engine Of Application State）**：在返回结果中包含相关资源的链接。

**示例**：

```json
{
  "id": 1,
  "name": "张三",
  "email": "zhangsan@example.com",
  "_links": {
    "self": { "href": "/users/1" },
    "orders": { "href": "/users/1/orders" },
    "friends": { "href": "/users/1/friends" }
  }
}
```

**好处**：客户端可以"顺着链接"访问相关资源，类似网页的超链接。

---

## 🛠️ 第二部分：请求映射与参数接收

### 2.1 @RestController 和 @RequestMapping

**基础用法**：
```java
@RestController
@RequestMapping("/api/v1/users")
public class UserController {
    // 所有方法的 URL 都会加上 /api/v1/users 前缀
}
```

**@RestController 的作用**：

```java
@RestController = @Controller + @ResponseBody
```
- `@Controller`：标记为控制器
- `@ResponseBody`：返回 JSON，而不是视图

---

### 2.2 HTTP 方法映射

#### 1. @GetMapping（查询）

```java
@RestController
@RequestMapping("/api/v1/users")
public class UserController {

    // GET /api/v1/users
    @GetMapping
    public List<User> getUsers() {
        return userService.findAll();
    }

    // GET /api/v1/users/1
    @GetMapping("/{id}")
    public User getUser(@PathVariable Long id) {
        return userService.findById(id);
    }
}
```

---

#### 2. @PostMapping（创建）

```java
@RestController
@RequestMapping("/api/v1/users")
public class UserController {

    // POST /api/v1/users
    @PostMapping
    public User createUser(@RequestBody User user) {//将请求体的json反序列化成User对象
        return userService.save(user);
    }
}
```

---

#### 3. @PutMapping（完整更新）

```java
@RestController
@RequestMapping("/api/v1/users")
public class UserController {

    // PUT /api/v1/users/1
    @PutMapping("/{id}")
    public User updateUser(@PathVariable Long id, @RequestBody User user) {
        user.setId(id);
        return userService.update(user);
    }
}
```

---

#### 4. @PatchMapping（部分更新）

```java
@RestController
@RequestMapping("/api/v1/users")
public class UserController {

    // PATCH /api/v1/users/1
    @PatchMapping("/{id}")
    public User patchUser(@PathVariable Long id, @RequestBody Map<String, Object> updates) {
        return userService.patch(id, updates);
    }
}
```

---

#### 5. @DeleteMapping（删除）

```java
@RestController
@RequestMapping("/api/v1/users")
public class UserController {

    // DELETE /api/v1/users/1
    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable Long id) {
        userService.deleteById(id);
    }
}
```

---

### 2.3 参数接收

#### 1. @PathVariable（路径参数）

**用途**：接收 URL 路径中的参数

**基础用法**：

```java
// GET /users/1
@GetMapping("/users/{id}")
public User getUser(@PathVariable Long id) {
        // id = 1
        }
```

**多个路径参数**：

```java
// GET /users/1/orders/123
@GetMapping("/users/{userId}/orders/{orderId}")
public Order getOrder(
@PathVariable Long userId,
@PathVariable Long orderId
        ) {
        // userId = 1, orderId = 123
        }
```

**参数名不一致时**：
```java
// GET /users/1
@GetMapping("/users/{id}")
public User getUser(@PathVariable("id") Long userId) {
        // 路径参数名为 id，方法参数名为 userId
        }
```

**可选路径参数**：
```java
// GET /users/1 或 GET /users
@GetMapping({"/users/{id}", "/users"})
public Object getUser(@PathVariable(required = false) Long id) {
        if (id == null) {
        return userService.findAll();
        }
        return userService.findById(id);
        }
```

---

#### 2. @RequestParam（查询参数）

**用途**：接收 URL 查询参数（`?key=value`）

**基础用法**：

```java
// GET /users?page=1&size=10
@GetMapping("/users")
public List<User> getUsers(
@RequestParam int page,
@RequestParam int size
        ) {
        // page = 1, size = 10
        }
```

**设置默认值**：
```java
@GetMapping("/users")
public List<User> getUsers(
@RequestParam(defaultValue = "1") int page,
@RequestParam(defaultValue = "10") int size
        ) {
        // 如果不传参数，page = 1, size = 10
        }
```

**可选参数**：
```java
// GET /users?search=张三 或 GET /users
@GetMapping("/users")
public List<User> getUsers(
@RequestParam(required = false) String search
        ) {
        if (search == null) {
        return userService.findAll();
        }
        return userService.search(search);
        }
```

**参数名不一致时**：
```java
// GET /users?q=张三
@GetMapping("/users")
public List<User> getUsers(
@RequestParam("q") String search
        ) {
        // 查询参数名为 q，方法参数名为 search
        }
```

**接收多个同名参数**：
```java
// GET /users?role=admin&role=user
@GetMapping("/users")
public List<User> getUsers(
@RequestParam List<String> role
        ) {
        // role = ["admin", "user"]
        }
```

**接收所有参数**：
```java
@GetMapping("/users")
public List<User> getUsers(@RequestParam Map<String, String> params) {
        // params = {"page": "1", "size": "10", "search": "张三"}
        }
```

---

#### 3. @RequestBody（请求体）

**用途**：接收 JSON 请求体，自动转换为 Java 对象

**基础用法**：
```java
// POST /users
// Content-Type: application/json
// {"name": "张三", "email": "zhangsan@example.com"}
@PostMapping("/users")
public User createUser(@RequestBody User user) {
        // user.getName() = "张三"
        // user.getEmail() = "zhangsan@example.com"
        return userService.save(user);
        }
```

**接收 Map**：
```java
@PostMapping("/users")
public User createUser(@RequestBody Map<String, Object> data) {
        String name = (String) data.get("name");
        String email = (String) data.get("email");
        // ...
        }
```

**接收 List**：
```java
// POST /users/batch
// [{"name": "张三"}, {"name": "李四"}]
@PostMapping("/users/batch")
public List<User> createUsers(@RequestBody List<User> users) {
        return userService.saveAll(users);
        }
```

---

#### 4. @RequestHeader（请求头）

**用途**：接收 HTTP 请求头

**基础用法**：
```java
@GetMapping("/users")
public List<User> getUsers(
@RequestHeader("Authorization") String token
        ) {
        // token = "Bearer xxx"
        }
```

**可选请求头**：
```java
@GetMapping("/users")
public List<User> getUsers(
@RequestHeader(value = "User-Agent", required = false) String userAgent
        ) {
        // 如果没有 User-Agent 请求头，userAgent = null
        }
```

---

#### 5. @CookieValue（Cookie）

**用途**：接收 Cookie 值

**基础用法**：
```java
@GetMapping("/users")
public List<User> getUsers(
@CookieValue("sessionId") String sessionId
        ) {
        // sessionId = Cookie 中的 sessionId 值
        }
```

---

### 2.4 参数接收总结

| 注解 | 用途 | 示例 |
|------|------|------|
| `@PathVariable` | 路径参数 | `/users/{id}` → `@PathVariable Long id` |
| `@RequestParam` | 查询参数 | `/users?page=1` → `@RequestParam int page` |
| `@RequestBody` | 请求体 | JSON → `@RequestBody User user` |
| `@RequestHeader` | 请求头 | `Authorization: Bearer xxx` → `@RequestHeader String token` |
| `@CookieValue` | Cookie | `sessionId=abc` → `@CookieValue String sessionId` |

---

## 📦 第三部分：统一返回格式封装

### 3.1 为什么要统一返回格式？

**❌ 不统一的返回格式**：
```java
// 成功时返回对象
@GetMapping("/users/{id}")
public User getUser(@PathVariable Long id) {
        return userService.findById(id);
        }

// 失败时返回字符串
@GetMapping("/users/{id}")
public String getUser(@PathVariable Long id) {
        return "用户不存在";
        }
```

**问题**：
- 客户端不知道如何处理（是对象还是字符串？）
- 无法统一判断成功或失败
- 错误信息格式不一致

**✅ 统一返回格式**：
```json
{
  "code": 200,
  "message": "success",
  "data": {
    "id": 1,
    "name": "张三"
  }
}
```

**好处**：
- 客户端可以统一处理
- 明确标识成功或失败
- 便于前后端协作

---

### 3.2 设计统一返回格式

**Result.java**：
```java
package com.example.demo.common;

import lombok.Data;

@Data
public class Result<T> {
    private Integer code;      // 状态码
    private String message;    // 提示信息
    private T data;            // 数据
    private Long timestamp;    // 时间戳

    // 私有构造器
    private Result() {
        this.timestamp = System.currentTimeMillis();
    }

    // 成功（有数据）
    public static <T> Result<T> success(T data) {
        Result<T> result = new Result<>();
        result.setCode(200);
        result.setMessage("success");
        result.setData(data);
        return result;
    }

    // 成功（无数据）
    public static <T> Result<T> success() {
        return success(null);
    }

    // 成功（自定义消息）
    public static <T> Result<T> success(String message, T data) {
        Result<T> result = new Result<>();
        result.setCode(200);
        result.setMessage(message);
        result.setData(data);
        return result;
    }

    // 失败
    public static <T> Result<T> error(Integer code, String message) {
        Result<T> result = new Result<>();
        result.setCode(code);
        result.setMessage(message);
        return result;
    }

    // 失败（默认 500）
    public static <T> Result<T> error(String message) {
        return error(500, message);
    }
}
```

---

### 3.3 使用统一返回格式

**UserController.java**：
```java
@RestController
@RequestMapping("/api/v1/users")
public class UserController {

    @Autowired
    private UserService userService;

    // 查询所有用户
    @GetMapping
    public Result<List<User>> getUsers() {
        List<User> users = userService.findAll();
        return Result.success(users);
    }

    // 查询单个用户
    @GetMapping("/{id}")
    public Result<User> getUser(@PathVariable Long id) {
        User user = userService.findById(id);
        if (user == null) {
            return Result.error(404, "用户不存在");
        }
        return Result.success(user);
    }

    // 创建用户
    @PostMapping
    public Result<User> createUser(@RequestBody User user) {
        User savedUser = userService.save(user);
        return Result.success("创建成功", savedUser);
    }

    // 更新用户
    @PutMapping("/{id}")
    public Result<User> updateUser(@PathVariable Long id, @RequestBody User user) {
        user.setId(id);
        User updatedUser = userService.update(user);
        return Result.success("更新成功", updatedUser);
    }

    // 删除用户
    @DeleteMapping("/{id}")
    public Result<Void> deleteUser(@PathVariable Long id) {
        userService.deleteById(id);
        return Result.success("删除成功");
    }
}
```

**返回示例**：
```json
// 成功
{
  "code": 200,
  "message": "success",
  "data": {
    "id": 1,
    "name": "张三",
    "email": "zhangsan@example.com"
  },
  "timestamp": 1704067200000
}

// 失败
{
  "code": 404,
  "message": "用户不存在",
  "data": null,
  "timestamp": 1704067200000
}
```

---

### 3.4 扩展：分页返回格式

**PageResult.java**：
```java
package com.example.demo.common;

import lombok.Data;

import java.util.List;

@Data
public class PageResult<T> {
    private List<T> list;        // 数据列表
    private Long total;          // 总数
    private Integer page;        // 当前页
    private Integer size;        // 每页大小
    private Integer totalPages;  // 总页数

    public PageResult(List<T> list, Long total, Integer page, Integer size) {
        this.list = list;
        this.total = total;
        this.page = page;
        this.size = size;
        this.totalPages = (int) Math.ceil((double) total / size);
    }
}
```

**使用示例**：
```java
@GetMapping
public Result<PageResult<User>> getUsers(
@RequestParam(defaultValue = "1") Integer page,
@RequestParam(defaultValue = "10") Integer size
        ) {
        List<User> users = userService.findAll(page, size);
        Long total = userService.count();
        PageResult<User> pageResult = new PageResult<>(users, total, page, size);
        return Result.success(pageResult);
        }
```

**返回示例**：
```json
{
  "code": 200,
  "message": "success",
  "data": {
    "list": [
      {"id": 1, "name": "张三"},
      {"id": 2, "name": "李四"}
    ],
    "total": 100,
    "page": 1,
    "size": 10,
    "totalPages": 10
  },
  "timestamp": 1704067200000
}
```

---

## ⚠️ 第四部分：统一异常处理

### 4.1 为什么要统一异常处理？

**❌ 不统一的异常处理**：
```java
@GetMapping("/{id}")
public Result<User> getUser(@PathVariable Long id) {
        try {
        User user = userService.findById(id);
        if (user == null) {
        return Result.error(404, "用户不存在");
        }
        return Result.success(user);
        } catch (Exception e) {
        return Result.error(500, "服务器错误");
        }
        }
```

**问题**：
- 每个方法都要 try-catch，代码冗余
- 异常处理逻辑分散，难以维护
- 容易遗漏异常处理

**✅ 统一异常处理**：

```java
@GetMapping("/{id}")
public Result<User> getUser(@PathVariable Long id) {
        // 不需要 try-catch，异常统一处理
        User user = userService.findById(id);
        if (user == null) {
        throw new ResourceNotFoundException("用户不存在");
        }
        return Result.success(user);
        }
```

**好处**：
- 代码简洁，专注业务逻辑
- 异常处理逻辑集中管理
- 统一的错误响应格式

---

### 4.2 自定义业务异常

**BaseException.java（基础异常类）**：
```java
package com.example.demo.exception;

import lombok.Getter;

@Getter
public class BaseException extends RuntimeException {
    private Integer code;

    public BaseException(Integer code, String message) {
        super(message);
        this.code = code;
    }

    public BaseException(String message) {
        super(message);
        this.code = 500;
    }
}
```

**常见业务异常**：
```java
package com.example.demo.exception;

// 资源不存在异常
public class ResourceNotFoundException extends BaseException {
    public ResourceNotFoundException(String message) {
        super(404, message);
    }
}

// 参数校验异常
public class ValidationException extends BaseException {
    public ValidationException(String message) {
        super(400, message);
    }
}

// 业务异常
public class BusinessException extends BaseException {
    public BusinessException(String message) {
        super(400, message);
    }

    public BusinessException(Integer code, String message) {
        super(code, message);
    }
}

// 未授权异常
public class UnauthorizedException extends BaseException {
    public UnauthorizedException(String message) {
        super(401, message);
    }
}

// 无权限异常
public class ForbiddenException extends BaseException {
    public ForbiddenException(String message) {
        super(403, message);
    }
}
```

---

### 4.3 全局异常处理器

**GlobalExceptionHandler.java**：
```java
package com.example.demo.exception;

import com.example.demo.common.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RestControllerAdvice  // 全局异常处理器
public class GlobalExceptionHandler {

    /**
     * 处理自定义业务异常
     */
    @ExceptionHandler(BaseException.class)
    public Result<?> handleBaseException(BaseException e, HttpServletRequest request) {
        log.error("业务异常: {} - {}", request.getRequestURI(), e.getMessage());
        return Result.error(e.getCode(), e.getMessage());
    }

    /**
     * 处理参数校验异常（@Valid）
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Result<?> handleValidationException(MethodArgumentNotValidException e) {
        List<FieldError> fieldErrors = e.getBindingResult().getFieldErrors();
        String message = fieldErrors.stream()
                .map(error -> error.getField() + ": " + error.getDefaultMessage())
                .collect(Collectors.joining(", "));
        log.error("参数校验异常: {}", message);
        return Result.error(400, message);
    }

    /**
     * 处理参数绑定异常
     */
    @ExceptionHandler(BindException.class)
    public Result<?> handleBindException(BindException e) {
        List<FieldError> fieldErrors = e.getBindingResult().getFieldErrors();
        String message = fieldErrors.stream()
                .map(error -> error.getField() + ": " + error.getDefaultMessage())
                .collect(Collectors.joining(", "));
        log.error("参数绑定异常: {}", message);
        return Result.error(400, message);
    }

    /**
     * 处理空指针异常
     */
    @ExceptionHandler(NullPointerException.class)
    public Result<?> handleNullPointerException(NullPointerException e, HttpServletRequest request) {
        log.error("空指针异常: {} - {}", request.getRequestURI(), e.getMessage(), e);
        return Result.error(500, "服务器内部错误");
    }

    /**
     * 处理所有未捕获的异常
     */
    @ExceptionHandler(Exception.class)
    public Result<?> handleException(Exception e, HttpServletRequest request) {
        log.error("未知异常: {} - {}", request.getRequestURI(), e.getMessage(), e);
        return Result.error(500, "服务器内部错误: " + e.getMessage());
    }
}
```

**@RestControllerAdvice 的作用**：
- `@RestControllerAdvice` = `@ControllerAdvice` + `@ResponseBody`
- 全局异常处理器，对所有 Controller 生效
- `@ExceptionHandler` 指定要处理的异常类型

---

### 4.4 使用统一异常处理

**UserService.java**：
```java
@Service
public class UserService {

    @Autowired
    private UserDao userDao;

    public User findById(Long id) {
        User user = userDao.findById(id);
        if (user == null) {
            // 抛出自定义异常，由全局异常处理器处理
            throw new ResourceNotFoundException("用户不存在：ID = " + id);
        }
        return user;
    }

    public User save(User user) {
        // 业务校验
        if (user.getUsername() == null || user.getUsername().trim().isEmpty()) {
            throw new ValidationException("用户名不能为空");
        }

        // 检查用户名是否重复
        User existingUser = userDao.findByUsername(user.getUsername());
        if (existingUser != null) {
            throw new BusinessException("用户名已存在");
        }

        return userDao.save(user);
    }
}
```

**UserController.java**：
```java
@RestController
@RequestMapping("/api/v1/users")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/{id}")
    public Result<User> getUser(@PathVariable Long id) {
        // 不需要 try-catch，异常会被全局异常处理器捕获
        User user = userService.findById(id);
        return Result.success(user);
    }

    @PostMapping
    public Result<User> createUser(@RequestBody User user) {
        // 不需要手动校验，异常会被全局异常处理器捕获
        User savedUser = userService.save(user);
        return Result.success("创建成功", savedUser);
    }
}
```

**错误响应示例**：
```json
// 用户不存在
{
  "code": 404,
  "message": "用户不存在：ID = 999",
  "data": null,
  "timestamp": 1704067200000
}

// 用户名已存在
{
  "code": 400,
  "message": "用户名已存在",
  "data": null,
  "timestamp": 1704067200000
}

// 服务器错误
{
  "code": 500,
  "message": "服务器内部错误: Connection refused",
  "data": null,
  "timestamp": 1704067200000
}
```

---

## ✅ 第五部分：参数校验

### 5.1 为什么要参数校验？

**❌ 手动校验**：
```java
@PostMapping
public Result<User> createUser(@RequestBody User user) {
        if (user.getUsername() == null || user.getUsername().trim().isEmpty()) {
        return Result.error(400, "用户名不能为空");
        }
        if (user.getEmail() == null || !user.getEmail().contains("@")) {
        return Result.error(400, "邮箱格式不正确");
        }
        if (user.getAge() != null && (user.getAge() < 0 || user.getAge() > 150)) {
        return Result.error(400, "年龄必须在 0-150 之间");
        }
        // ... 更多校验

        User savedUser = userService.save(user);
        return Result.success(savedUser);
        }
```

**问题**：
- 代码冗长，可读性差
- 校验逻辑和业务逻辑混在一起
- 每个接口都要重复校验

**✅ 使用 Bean Validation**：
```java
@Data
public class User {
    private Long id;

    @NotBlank(message = "用户名不能为空")
    @Size(min = 2, max = 20, message = "用户名长度必须在 2-20 之间")
    private String username;

    @NotBlank(message = "邮箱不能为空")
    @Email(message = "邮箱格式不正确")
    private String email;

    @Min(value = 0, message = "年龄不能小于 0")
    @Max(value = 150, message = "年龄不能大于 150")
    private Integer age;
}

    @PostMapping
    public Result<User> createUser(@Valid @RequestBody User user) {
        // 参数校验自动完成，校验失败会抛出异常
        User savedUser = userService.save(user);
        return Result.success(savedUser);
    }
```

**好处**：
- 代码简洁，声明式校验
- 校验逻辑和实体类绑定，便于维护
- 统一的校验异常处理

---

### 5.2 常用校验注解

#### 1. 空值校验

| 注解 | 说明 | 示例 |
|------|------|------|
| `@NotNull` | 不能为 null | `@NotNull private String name;` |
| `@NotEmpty` | 不能为 null 且长度 > 0 | `@NotEmpty private String name;` |
| `@NotBlank` | 不能为 null 且去空格后长度 > 0 | `@NotBlank private String name;` |

**区别**：
```java
String str1 = null;        // @NotNull ❌  @NotEmpty ❌  @NotBlank ❌
        String str2 = "";          // @NotNull ✅  @NotEmpty ❌  @NotBlank ❌
        String str3 = "   ";       // @NotNull ✅  @NotEmpty ✅  @NotBlank ❌
        String str4 = "abc";       // @NotNull ✅  @NotEmpty ✅  @NotBlank ✅
```

**推荐**：字符串用 `@NotBlank`，集合用 `@NotEmpty`，其他用 `@NotNull`。

---

#### 2. 长度和大小校验

| 注解 | 说明 | 示例 |
|------|------|------|
| `@Size` | 字符串长度或集合大小 | `@Size(min=2, max=20) private String name;` |
| `@Length` | 字符串长度（Hibernate Validator） | `@Length(min=2, max=20) private String name;` |
| `@Min` | 数字最小值 | `@Min(0) private Integer age;` |
| `@Max` | 数字最大值 | `@Max(150) private Integer age;` |
| `@Range` | 数字范围（Hibernate Validator） | `@Range(min=0, max=150) private Integer age;` |
| `@DecimalMin` | 小数最小值 | `@DecimalMin("0.01") private BigDecimal price;` |
| `@DecimalMax` | 小数最大值 | `@DecimalMax("9999.99") private BigDecimal price;` |

---

#### 3. 格式校验

| 注解 | 说明 | 示例 |
|------|------|------|
| `@Email` | 邮箱格式 | `@Email private String email;` |
| `@Pattern` | 正则表达式 | `@Pattern(regexp="^1[3-9]\\d{9}$") private String phone;` |
| `@URL` | URL 格式 | `@URL private String website;` |

---

#### 4. 时间校验

| 注解 | 说明 | 示例 |
|------|------|------|
| `@Past` | 过去的时间 | `@Past private Date birthday;` |
| `@PastOrPresent` | 过去或现在的时间 | `@PastOrPresent private Date createdAt;` |
| `@Future` | 未来的时间 | `@Future private Date expireDate;` |
| `@FutureOrPresent` | 未来或现在的时间 | `@FutureOrPresent private Date updatedAt;` |

---

#### 5. 其他校验

| 注解 | 说明 | 示例 |
|------|------|------|
| `@Positive` | 正数 | `@Positive private Integer count;` |
| `@PositiveOrZero` | 正数或 0 | `@PositiveOrZero private Integer stock;` |
| `@Negative` | 负数 | `@Negative private Integer deficit;` |
| `@NegativeOrZero` | 负数或 0 | `@NegativeOrZero private Integer balance;` |
| `@Digits` | 数字格式 | `@Digits(integer=3, fraction=2) private BigDecimal price;` |
| `@AssertTrue` | 必须为 true | `@AssertTrue private Boolean agreed;` |
| `@AssertFalse` | 必须为 false | `@AssertFalse private Boolean deleted;` |

---

### 5.3 完整的参数校验示例

**User.java**：
```java
package com.example.demo.entity;

import lombok.Data;

import javax.validation.constraints.*;

@Data
public class User {
    private Long id;

    @NotBlank(message = "用户名不能为空")
    @Size(min = 2, max = 20, message = "用户名长度必须在 2-20 之间")
    private String username;

    @NotBlank(message = "密码不能为空")
    @Size(min = 6, message = "密码长度不能少于 6 位")
    private String password;

    @NotBlank(message = "邮箱不能为空")
    @Email(message = "邮箱格式不正确")
    private String email;

    @Pattern(regexp = "^1[3-9]\\d{9}$", message = "手机号格式不正确")
    private String phone;

    @Min(value = 0, message = "年龄不能小于 0")
    @Max(value = 150, message = "年龄不能大于 150")
    private Integer age;

    @NotNull(message = "性别不能为空")
    private Integer gender;  // 0-女 1-男

    @URL(message = "个人网站格式不正确")
    private String website;
}
```

**UserController.java**：
```java
@RestController
@RequestMapping("/api/v1/users")
public class UserController {

    @Autowired
    private UserService userService;

    /**
     * 创建用户
     * @Valid 触发参数校验
     */
    @PostMapping
    public Result<User> createUser(@Valid @RequestBody User user) {
        User savedUser = userService.save(user);
        return Result.success("创建成功", savedUser);
    }

    /**
     * 更新用户
     */
    @PutMapping("/{id}")
    public Result<User> updateUser(
            @PathVariable Long id,
            @Valid @RequestBody User user
    ) {
        user.setId(id);
        User updatedUser = userService.update(user);
        return Result.success("更新成功", updatedUser);
    }
}
```

**校验失败的响应**（已被全局异常处理器捕获）：
```json
{
  "code": 400,
  "message": "username: 用户名不能为空, email: 邮箱格式不正确, age: 年龄不能小于 0",
  "data": null,
  "timestamp": 1704067200000
}
```

---

### 5.4 分组校验

**场景**：创建和更新时，校验规则不同

**定义分组接口**：
```java
public interface CreateGroup {}
public interface UpdateGroup {}
```

**User.java**：
```java
@Data
public class User {
    // ID 只在更新时需要
    @NotNull(message = "ID 不能为空", groups = UpdateGroup.class)
    private Long id;

    // 用户名在创建和更新时都需要
    @NotBlank(message = "用户名不能为空", groups = {CreateGroup.class, UpdateGroup.class})
    @Size(min = 2, max = 20, message = "用户名长度必须在 2-20 之间")
    private String username;

    // 密码只在创建时需要
    @NotBlank(message = "密码不能为空", groups = CreateGroup.class)
    @Size(min = 6, message = "密码长度不能少于 6 位")
    private String password;

    @NotBlank(message = "邮箱不能为空", groups = {CreateGroup.class, UpdateGroup.class})
    @Email(message = "邮箱格式不正确")
    private String email;
}
```

**UserController.java**：
```java
@RestController
@RequestMapping("/api/v1/users")
public class UserController {

    // 创建时使用 CreateGroup
    @PostMapping
    public Result<User> createUser(@Validated(CreateGroup.class) @RequestBody User user) {
        User savedUser = userService.save(user);
        return Result.success(savedUser);
    }

    // 更新时使用 UpdateGroup
    @PutMapping("/{id}")
    public Result<User> updateUser(
            @PathVariable Long id,
            @Validated(UpdateGroup.class) @RequestBody User user
    ) {
        user.setId(id);
        User updatedUser = userService.update(user);
        return Result.success(updatedUser);
    }
}
```

**注意**：使用分组校验时，要用 `@Validated`，不是 `@Valid`。

---

### 5.5 自定义校验注解

**场景**：校验手机号格式

**1. 定义注解**：
```java
package com.example.demo.validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = PhoneValidator.class)
@Documented
public @interface Phone {
    String message() default "手机号格式不正确";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
```

**2. 实现校验器**：
```java
package com.example.demo.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.regex.Pattern;

public class PhoneValidator implements ConstraintValidator<Phone, String> {

    private static final Pattern PHONE_PATTERN = Pattern.compile("^1[3-9]\\d{9}$");

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        // null 值由 @NotNull 校验
        if (value == null) {
            return true;
        }
        return PHONE_PATTERN.matcher(value).matches();
    }
}
```

**3. 使用自定义注解**：
```java
@Data
public class User {
    @NotBlank(message = "手机号不能为空")
    @Phone  // 使用自定义注解
    private String phone;
}
```

---

## 🎯 完整示例：用户管理 API

### 目录结构
```
com.example.demo/
├── DemoApplication.java
├── common/
│   ├── Result.java              # 统一返回格式
│   └── PageResult.java          # 分页返回格式
├── exception/
│   ├── BaseException.java       # 基础异常类
│   ├── ResourceNotFoundException.java
│   ├── ValidationException.java
│   ├── BusinessException.java
│   └── GlobalExceptionHandler.java  # 全局异常处理器
├── entity/
│   └── User.java                # 用户实体
├── dao/
│   └── UserDao.java             # 数据访问层
├── service/
│   └── UserService.java         # 业务层
└── controller/
    └── UserController.java      # 控制层
```

### UserController.java（完整代码）

```java
package com.example.demo.controller;

import com.example.demo.common.PageResult;
import com.example.demo.common.Result;
import com.example.demo.entity.User;
import com.example.demo.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import java.util.List;

@Slf4j
@Validated  // 开启方法参数校验
@RestController
@RequestMapping("/api/v1/users")
@Api(tags = "用户管理")
public class UserController {

    @Autowired
    private UserService userService;

    /**
     * 获取用户列表（分页）
     */
    @GetMapping
    @ApiOperation("获取用户列表")
    public Result<PageResult<User>> getUsers(
            @ApiParam("页码") @RequestParam(defaultValue = "1") @Min(1) Integer page,
            @ApiParam("每页大小") @RequestParam(defaultValue = "10") @Min(1) Integer size,
            @ApiParam("搜索关键词") @RequestParam(required = false) String search
    ) {
        log.info("查询用户列表: page={}, size={}, search={}", page, size, search);

        List<User> users;
        Long total;

        if (search != null && !search.trim().isEmpty()) {
            users = userService.search(search, page, size);
            total = userService.countBySearch(search);
        } else {
            users = userService.findAll(page, size);
            total = userService.count();
        }

        PageResult<User> pageResult = new PageResult<>(users, total, page, size);
        return Result.success(pageResult);
    }

    /**
     * 获取单个用户
     */
    @GetMapping("/{id}")
    @ApiOperation("获取用户详情")
    public Result<User> getUser(
            @ApiParam("用户ID") @PathVariable @Min(1) Long id
    ) {
        log.info("查询用户: id={}", id);
        User user = userService.findById(id);
        return Result.success(user);
    }

    /**
     * 创建用户
     */
    @PostMapping
    @ApiOperation("创建用户")
    public Result<User> createUser(
            @ApiParam("用户信息") @Valid @RequestBody User user
    ) {
        log.info("创建用户: {}", user);
        User savedUser = userService.save(user);
        return Result.success("创建成功", savedUser);
    }

    /**
     * 更新用户（完整更新）
     */
    @PutMapping("/{id}")
    @ApiOperation("更新用户")
    public Result<User> updateUser(
            @ApiParam("用户ID") @PathVariable @Min(1) Long id,
            @ApiParam("用户信息") @Valid @RequestBody User user
    ) {
        log.info("更新用户: id={}, user={}", id, user);
        user.setId(id);
        User updatedUser = userService.update(user);
        return Result.success("更新成功", updatedUser);
    }

    /**
     * 删除用户
     */
    @DeleteMapping("/{id}")
    @ApiOperation("删除用户")
    public Result<Void> deleteUser(
            @ApiParam("用户ID") @PathVariable @Min(1) Long id
    ) {
        log.info("删除用户: id={}", id);
        userService.deleteById(id);
        return Result.success("删除成功");
    }

    /**
     * 批量删除用户
     */
    @DeleteMapping
    @ApiOperation("批量删除用户")
    public Result<Void> batchDelete(
            @ApiParam("用户ID列表") @RequestParam List<Long> ids
    ) {
        log.info("批量删除用户: ids={}", ids);
        userService.batchDelete(ids);
        return Result.success("批量删除成功");
    }
}
```

---

## 📝 实战练习

### 练习1：完善用户管理 API

**需求**：
1. 实现用户的增删改查（已有数据库）
2. 添加参数校验
3. 添加统一返回格式
4. 添加统一异常处理
5. 实现分页查询
6. 实现模糊搜索

**提示**：
- 使用 `@Valid` 触发参数校验
- Service 层抛出自定义异常
- Controller 层返回统一的 `Result`

---

### 练习2：实现商品管理 API

**需求**：
1. 创建 Product 实体类（id、name、price、stock、description）
2. 实现商品的增删改查
3. 添加价格范围查询（`/products?minPrice=10&maxPrice=100`）
4. 添加库存预警（`/products?lowStock=true`，查询库存 < 10 的商品）
5. 添加分类筛选（`/products?category=电子产品`）

---

### 练习3：实现订单管理 API

**需求**：
1. 创建 Order 实体类（id、userId、productId、quantity、totalPrice、status）
2. 实现订单的创建、查询、取消
3. 查询用户的所有订单（`/users/{userId}/orders`）
4. 按状态筛选订单（`/orders?status=pending`）
5. 按时间范围查询（`/orders?startDate=2024-01-01&endDate=2024-01-31`）

---

## ❓ 常见问题（FAQ）

### Q1: @RequestParam 和 @PathVariable 有什么区别？

**A**：

| 特性 | @RequestParam | @PathVariable |
|------|--------------|---------------|
| 位置 | 查询参数（`?key=value`） | 路径参数（`/users/{id}`） |
| 用途 | 过滤、排序、分页 | 资源标识 |
| 是否必填 | 默认必填，可设置 `required=false` | 必填 |
| 示例 | `/users?page=1` | `/users/1` |

**何时使用**：
- **@PathVariable**：用于标识资源（如：用户 ID、订单 ID）
- **@RequestParam**：用于筛选、排序、分页

---

### Q2: PUT 和 PATCH 有什么区别？

**A**：

- **PUT**：完整更新，需要传递所有字段
- **PATCH**：部分更新，只传递需要修改的字段

**示例**：
```java
// PUT：需要传递所有字段
PUT /users/1
        {
        "name": "张三",
        "email": "zhangsan@example.com",
        "age": 25,
        "phone": "13800138000"
        }

// PATCH：只传递需要修改的字段
        PATCH /users/1
        {
        "email": "newemail@example.com"
        }
```

**推荐**：一般情况下使用 PUT 即可，PATCH 实现较复杂。

---

### Q3: 什么时候应该返回 201 Created？

**A**：创建资源成功时返回 201。

**示例**：
```java
@PostMapping
@ResponseStatus(HttpStatus.CREATED)  // 返回 201
public Result<User> createUser(@Valid @RequestBody User user) {
        User savedUser = userService.save(user);
        return Result.success("创建成功", savedUser);
        }
```

**标准做法**：
- 返回 201 状态码
- 在响应头中包含 `Location`，指向新创建的资源

---

### Q4: 删除成功应该返回什么？

**A**：有两种做法：

**方式1：返回 204 No Content（推荐）**
```java
@DeleteMapping("/{id}")
@ResponseStatus(HttpStatus.NO_CONTENT)
public void deleteUser(@PathVariable Long id) {
        userService.deleteById(id);
        }
```

**方式2：返回 200 + 提示信息**
```java
@DeleteMapping("/{id}")
public Result<Void> deleteUser(@PathVariable Long id) {
        userService.deleteById(id);
        return Result.success("删除成功");
        }
```

**推荐**：根据前端需求选择，如果需要提示信息，用方式2。

---

### Q5: 如何处理并发更新问题？

**A**：使用乐观锁。

**添加版本号字段**：

```java
@Data
public class User {
    private Long id;
    private String name;

    @Version  // JPA 乐观锁
    private Integer version;
}
```
**乐观锁**：
1. **乐观锁（Optimistic Locking） 是一种并发控制策略。**
- 它的核心思想是：**假设并发冲突很少发生**，所以在更新时不加数据库行级锁，而是通过一个版本号字段来检测是否有其他人修改过数据。
2. 工作流程：
- 读取数据时，带上一个 version 字段（比如值为 1）。
- 更新时，**SQL 会自动加条件：WHERE id = ? AND version = ?**。
- 如果更新成功，version 会自动加 1（变成 2）。
- 如果更新失败（因为版本号不匹配），说明数据在你提交前已经被别人修改过，就抛出 OptimisticLockException。

3. 举个例子：
- 用户 A 和用户 B 同时读取了 User(id=1, version=1, name="张三")。
- 用户 A 修改 name → "张三丰"，提交时条件是 WHERE id=1 AND version=1，成功，version 更新为 2。
- 用户 B 修改 name → "张三强"，提交时条件也是 WHERE id=1 AND version=1，但此时数据库里 version 已经是 2，所以更新失败，抛出 OptimisticLockException。
- 👉 这样就避免了 “后提交覆盖前提交” 的问题，保证了数据一致性。
4. 乐观锁适合 **读多写少** 的场景（比如用户资料更新），因为冲突概率低。
- 如果是 高并发频繁写入（比如股票行情数据），乐观锁会导致大量失败重试，性能不佳。此时更适合用 悲观锁 或 队列化写入。
- 乐观锁依赖 @Version 字段，必须保证每次更新都带上它，否则机制不起作用。

**悲观锁**：

- 悲观锁（Pessimistic Locking） 的核心思想是：假设并发冲突经常发生，所以在访问数据时就直接加锁，防止别人同时修改。
- 当一个事务读取数据时，就会对这条记录加上锁（通常是行锁），直到事务结束才释放。
- 其他事务在锁释放之前，不能修改这条记录，有时甚至不能读取。

**工作流程：**
- 事务 A 查询某条记录 → 数据库加锁。
- 事务 B 想修改同一条记录 → 被阻塞，必须等事务 A 提交或回滚。
- 事务 A 更新并提交 → 锁释放。
- 事务 B 才能继续执行。
  👉 这样保证了不会出现“两个事务同时更新，后提交覆盖前提交”的情况。

**适用情况：**
- 高并发写入：比如订单扣库存、银行转账，必须保证强一致性。
- 冲突概率高：比如多个用户同时修改同一条数据。
- 不能容忍失败重试：乐观锁失败后需要重试，而悲观锁直接阻塞等待，保证成功。

**缺点：**
- 性能开销大：锁会阻塞其他事务，降低并发度。
- 容易死锁：多个事务互相等待时可能出现死锁。
- 不适合读多写少的场景：因为大部分时候锁是“白加”的，浪费性能。

**乐观锁更新时检查版本号**：
```java
@PutMapping("/{id}")
public Result<User> updateUser(@PathVariable Long id, @RequestBody User user) {
        user.setId(id);
        try {
        User updatedUser = userService.update(user);
        return Result.success(updatedUser);
        } catch (OptimisticLockException e) {
        return Result.error(409, "数据已被其他用户修改，请刷新后重试");
        }
        }
//需要澄清的是：这似乎涉及每次更新都“先查再更”，但是在一般的业务流程中，更新都是需要先进行查询，查到现状之后再提交更新
//这时更新，复用的是之前查询用到的版本号（即用户预期的结果）。所以更多时候不太涉及增加额外的IO
//此外乐观锁一定需要先查最新的版本号，否则机制不会生效
```

**工作原理**：

1. 查询时带上 `version`
    1. 更新时：`UPDATE user SET name=?, version=version+1 WHERE id=? AND version=?`

2. 如果 `version` 不匹配，更新失败，抛出异常

---

### Q6: 如何实现软删除？

**A**：添加 `deleted` 字段，删除时只更新标记。

**User.java**：
```java
@Data
public class User {
    private Long id;
    private String name;
    private Boolean deleted = false;  // 默认未删除
}
```

**UserService.java**：
```java
@Service
public class UserService {

    // 软删除
    public void deleteById(Long id) {
        User user = findById(id);
        user.setDeleted(true);
        userDao.update(user);
    }

    // 查询时过滤已删除的数据
    public List<User> findAll() {
        return userDao.findByDeleted(false);
    }
}
```

---

### Q7: 如何实现接口限流？

**A**：使用 Guava 的 RateLimiter 或 Redis + Lua。

**简单示例（使用拦截器）**：

```java
@Component
public class RateLimitInterceptor implements HandlerInterceptor {
    //这是 Google Guava 提供的一个令牌桶（Token Bucket）限流器。
    //RateLimiter.create(10.0)：表示限流速率为 每秒 10 个许可（token）。
    private final RateLimiter rateLimiter = RateLimiter.create(10.0);  // 每秒 10 个请求

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        //tryAcquire()：尝试获取一个许可，如果当前桶里没有令牌，就返回 false。
        if (!rateLimiter.tryAcquire()) {
            response.setStatus(429);  // Too Many Requests
            return false;
        }
        return true;
    }
}
//全局限流：这里的 RateLimiter 是单例的，意味着所有请求共享同一个限流器。
//粒度问题：如果你想对不同接口或不同用户分别限流，需要为它们各自创建 RateLimiter。
//分布式场景：这种限流器只在单机有效，如果是多台服务器，需要用 Redis 或其他分布式限流方案。
```

---

### Q8: 如何记录 API 访问日志？

**A**：使用 AOP 拦截所有 Controller 方法。

**LogAspect.java**：

```java
@Aspect
@Component
@Slf4j
public class LogAspect {

    @Around("execution(* com.example.demo.controller.*.*(..))")
    public Object logAround(ProceedingJoinPoint joinPoint) throws Throwable {
        // 请求信息
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();

        String method = request.getMethod();
        String url = request.getRequestURI();
        String ip = request.getRemoteAddr();
        String className = joinPoint.getSignature().getDeclaringTypeName();
        String methodName = joinPoint.getSignature().getName();
        Object[] args = joinPoint.getArgs();

        log.info("请求: {} {} - IP: {} - 方法: {}.{} - 参数: {}",
                method, url, ip, className, methodName, args);

        long start = System.currentTimeMillis();
        Object result = joinPoint.proceed();
        long end = System.currentTimeMillis();

        log.info("响应: {} {} - 耗时: {}ms - 结果: {}",
                method, url, (end - start), result);

        return result;
    }
}
```

---

### Q9: 如何处理跨域问题？

**A**：配置 CORS。

**方式1：全局配置（推荐）**
```java
@Configuration
public class CorsConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/api/**")  // 允许跨域的路径
                .allowedOrigins("http://localhost:3000")  // 允许的域名
                .allowedMethods("GET", "POST", "PUT", "DELETE")  // 允许的方法
                .allowedHeaders("*")  // 允许的请求头
                .allowCredentials(true)  // 允许携带 Cookie
                .maxAge(3600);  // 预检请求的有效期（秒）
    }
}
```

**方式2：注解方式**
```java
@RestController
@RequestMapping("/api/v1/users")
@CrossOrigin(origins = "http://localhost:3000")
public class UserController {
    // ...
}
```

---

### Q10: 如何生成 API 文档？

**A**：使用 Swagger（Springfox 或 Springdoc）。

**添加依赖**：
```xml
<dependency>
    <groupId>io.springfox</groupId>
    <artifactId>springfox-boot-starter</artifactId>
    <version>3.0.0</version>
</dependency>
```

**配置 Swagger**：
```java
@Configuration
@EnableOpenApi
public class SwaggerConfig {

    @Bean
    public Docket api() {
        return new Docket(DocumentationType.OAS_30)
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.example.demo.controller"))
                .paths(PathSelectors.any())
                .build();
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("用户管理 API")
                .description("用户管理系统的 RESTful API 文档")
                .version("1.0.0")
                .build();
    }
}
```

**访问文档**：http://localhost:8080/swagger-ui/index.html

---

## 🤔 面试题

### 1. 什么是 RESTful API？它的核心原则是什么？

**答案**：
RESTful API 是一种 API 设计风格，核心原则：
1. **资源**：万物皆资源，用 URL 表示
2. **表述**：资源的表现形式（JSON、XML）
3. **状态转移**：通过 HTTP 方法改变资源状态
4. **无状态**：每次请求独立，不依赖 Session
5. **统一接口**：统一的 URL 和 HTTP 方法

**示例**：
```
GET    /users          # 查询所有用户
GET    /users/1        # 查询单个用户
POST   /users          # 创建用户
PUT    /users/1        # 更新用户
DELETE /users/1        # 删除用户
```

---

### 2. RESTful API 中，PUT 和 POST 的区别是什么？

**答案**：

| 特性 | POST | PUT |
|------|------|-----|
| 用途 | 创建资源 | 更新资源 |
| 幂等性 | 非幂等（多次创建多个资源） | 幂等（多次更新结果相同） |
| URL | 通常不包含 ID（如 `/users`） | 包含 ID（如 `/users/1`） |
| 返回 | 201 Created | 200 OK |

**示例**：
```
POST /users      # 每次创建一个新用户
PUT /users/1     # 多次更新同一个用户，结果相同
```

---

### 3. 如何设计一个好的 RESTful API？

**答案**：
1. **URL 设计**：
    - 使用名词，不使用动词
    - 使用复数形式
    - 使用小写字母和连字符

2. **HTTP 方法**：
    - GET 查询，POST 创建，PUT 更新，DELETE 删除

3. **状态码**：
    - 200 成功，201 创建成功，404 不存在，500 服务器错误

4. **返回格式**：
    - 统一的返回格式（code、message、data）

5. **错误处理**：
    - 统一的异常处理
    - 明确的错误信息

6. **版本控制**：
    - 使用 URL 路径（`/api/v1/users`）

7. **安全性**：
    - 使用 HTTPS
    - 添加认证和授权
    - 参数校验

---

### 4. 如何处理 API 的版本升级？

**答案**：
1. **URL 路径（推荐）**：
   ```
   /api/v1/users
   /api/v2/users
   ```

2. **请求头**：
   ```
   Accept: application/vnd.myapp.v1+json
   ```

3. **查询参数**：
   ```
   /api/users?version=1
   ```

**最佳实践**：
- 新版本不要破坏旧版本的兼容性
- 提前通知客户端版本废弃时间
- 同时维护多个版本（最多 2-3 个）

---

### 5. @Valid 和 @Validated 的区别是什么？

**答案**：

| 特性 | @Valid | @Validated |
|------|--------|-----------|
| 来源 | JSR-303 标准 | Spring 扩展 |
| 分组校验 | ❌ 不支持 | ✅ 支持 |
| 嵌套校验 | ✅ 支持 | ✅ 支持 |
| 方法参数校验 | ❌ 不支持 | ✅ 支持 |

**使用场景**：
- 普通校验：两者都可以
- 分组校验：必须用 `@Validated`
- 方法参数校验（如 `@PathVariable`）：必须在类上加 `@Validated`

---

## 📚 推荐资源

### 视频教程：
- 尚硅谷 Spring Boot 2（B站）
- 黑马程序员 Spring Boot（B站）

### 官方文档：
- Spring Boot 官方文档：https://spring.io/projects/spring-boot
- RESTful API 设计指南：https://restfulapi.net/

### 书籍：
- 《RESTful Web APIs》
- 《Spring Boot 实战》

---

## ✅ 学习检查清单

- [ ] 理解 RESTful API 的核心原则
- [ ] 掌握 URL 设计规范
- [ ] 掌握 HTTP 方法和状态码
- [ ] 掌握 @PathVariable、@RequestParam、@RequestBody
- [ ] 实现统一返回格式
- [ ] 实现统一异常处理
- [ ] 掌握 Bean Validation
- [ ] 掌握分组校验
- [ ] 实现完整的 CRUD API
- [ ] 完成实战练习

---

## 🎯 下一步

完成 Day 3-4 的学习后，你应该：
- ✅ 能够设计符合 RESTful 规范的 API
- ✅ 能够处理各种参数接收场景
- ✅ 能够实现统一的返回格式和异常处理
- ✅ 能够进行参数校验

**下一步**：Day 5-6 - 数据库访问（MyBatis / JPA）

---

**加油！💪 你已经掌握了 Web 开发的核心技能！**

**记住**：
> 好的 API 设计是前后端协作的基础，要多站在使用者的角度思考。
>
> 统一的返回格式和异常处理，能让代码更优雅、更易维护！

---

**有问题随时问我！我会一直陪伴你的学习之旅！🚀**

