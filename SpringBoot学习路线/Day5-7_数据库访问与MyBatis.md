# Day 5-7: 数据库访问与 MyBatis

> **学习目标**：掌握 MyBatis 集成和数据库操作
> 
> **预计时间**：3天（每天3小时）
> 
> **学习方式**：理论 + 实战
> 
> **适合人群**：已完成 Day 1-4 学习的开发者

---

## 📚 学习内容

### 1. MyBatis 基础与集成
### 2. Mapper 接口与 XML 映射
### 3. 动态 SQL
### 4. 分页插件 PageHelper
### 5. 事务管理

---

## 🔰 前置知识：核心概念扫盲

### 什么是 ORM？

**ORM（Object-Relational Mapping）** = 对象关系映射

**通俗理解**：
- ORM 是连接"Java 对象"和"数据库表"的桥梁
- 让你用面向对象的方式操作数据库
- 不需要写繁琐的 JDBC 代码

**类比**：
> **传统 JDBC**：你要自己写 SQL，自己处理结果集，就像手动组装家具
> 
> **ORM 框架**：框架帮你生成 SQL，自动映射结果，就像买成品家具

**Java 对象 ↔ 数据库表的映射**：
```
Java 类         ←→  数据库表
类属性          ←→  表字段
对象            ←→  表记录
```

---

### 什么是 MyBatis？

**MyBatis** 是一个半自动化的 ORM 框架

**特点**：
- ✅ **SQL 可控**：你写 SQL，MyBatis 帮你执行
- ✅ **灵活**：支持复杂查询和动态 SQL
- ✅ **简单**：学习曲线平缓
- ✅ **性能好**：可以手动优化 SQL

**MyBatis vs Hibernate（JPA）**：

| 特性 | MyBatis | Hibernate/JPA |
|------|---------|--------------|
| ORM 类型 | 半自动（需要写 SQL） | 全自动（自动生成 SQL） |
| SQL 控制 | 完全可控 | 不太可控 |
| 学习难度 | 简单 | 较难 |
| 复杂查询 | 方便 | 复杂 |
| 性能优化 | 容易 | 较难 |
| 适用场景 | 复杂业务、需要优化 | 简单 CRUD |

**推荐**：国内项目大多使用 MyBatis，面试也常考 MyBatis。

---

### 什么是 Mapper？

**Mapper** = 数据访问接口 = DAO（Data Access Object）

**作用**：
- 定义数据库操作方法
- MyBatis 根据方法生成 SQL 或执行 XML 中的 SQL

**示例**：

```java
public interface UserMapper {
    User findById(Long id);         // 查询
    void insert(User user);         // 插入
    void update(User user);         // 更新
    void deleteById(Long id);       // 删除
}
```

**两种实现方式**：

1. **注解方式**：在接口方法上写 SQL
2. **XML 方式**：在 XML 文件中写 SQL（推荐）

---

### 什么是数据库连接池？

**连接池（Connection Pool）** = 数据库连接的"缓存池"

**为什么需要连接池？**

**❌ 不使用连接池**：

```java
// 每次查询都创建新连接
Connection conn = DriverManager.getConnection(url, user, password);
// 执行 SQL
// 关闭连接
conn.close();
```
**问题**：
- 创建连接很慢（需要 TCP 握手、认证等）
- 频繁创建销毁连接，浪费资源
- 高并发下数据库连接数耗尽

**✅ 使用连接池**：
```
连接池初始化时创建 N 个连接
应用需要连接时从池中取
用完后放回池中（不是关闭）
连接复用，性能大幅提升
```

**常用连接池**：
- **HikariCP**：Spring Boot 默认，性能最好（推荐）
- **Druid**：阿里开源，功能强大，有监控界面
- **C3P0**：老牌连接池，现在较少使用

---

### 什么是事务（Transaction）？

**事务** = 一组数据库操作，要么全成功，要么全失败

**ACID 特性**：
- **A（Atomicity）原子性**：事务是不可分割的最小单位
- **C（Consistency）一致性**：事务前后数据保持一致
- **I（Isolation）隔离性**：多个事务互不干扰
- **D（Durability）持久性**：事务提交后，数据永久保存

**经典例子：银行转账**
```java
@Transactional
public void transfer(Long fromId, Long toId, BigDecimal amount) {
    // 1. 扣款
    accountDao.deduct(fromId, amount);
    
    // 2. 如果这里出错...
    if (someError) {
        throw new RuntimeException("转账失败");
    }
    
    // 3. 加款
    accountDao.add(toId, amount);
}

// 有 @Transactional：
//   - 如果出错，步骤1会回滚，A 的钱不会被扣
//   - 如果成功，两个操作都生效

// 没有 @Transactional：
//   - 如果出错，A 的钱被扣了，B 没收到，钱丢了！
```

---

## 🚀 第一部分：MyBatis 集成

### 1.1 添加依赖

**pom.xml**：
```xml
<dependencies>
    <!-- Spring Boot Web -->
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-web</artifactId>
    </dependency>
    
    <!-- MyBatis Spring Boot Starter -->
    <dependency>
        <groupId>org.mybatis.spring.boot</groupId>
        <artifactId>mybatis-spring-boot-starter</artifactId>
        <version>2.3.1</version>
    </dependency>
    
    <!-- MySQL 驱动 -->
    <dependency>
        <groupId>mysql</groupId>
        <artifactId>mysql-connector-java</artifactId>
        <version>8.0.33</version>
    </dependency>
    
    <!-- HikariCP 连接池（Spring Boot 已内置） -->
    
    <!-- Lombok -->
    <dependency>
        <groupId>org.projectlombok</groupId>
        <artifactId>lombok</artifactId>
        <optional>true</optional>
    </dependency>
</dependencies>
```

---

### 1.2 配置数据源

**application.yml**：
```yaml
spring:
  datasource:
    # 数据库连接配置
    url: jdbc:mysql://localhost:3306/demo?useUnicode=true&characterEncoding=utf8&useSSL=false&serverTimezone=Asia/Shanghai
    username: root
    password: 123456
    driver-class-name: com.mysql.cj.jdbc.Driver
    
    # HikariCP 连接池配置
    hikari:
      minimum-idle: 5              # 最小空闲连接数
      maximum-pool-size: 20        # 最大连接数
      connection-timeout: 30000    # 连接超时时间（毫秒）
      idle-timeout: 600000         # 空闲连接超时时间（毫秒）
      max-lifetime: 1800000        # 连接最大存活时间（毫秒）

# MyBatis 配置
mybatis:
  # Mapper XML 文件位置
  mapper-locations: classpath:mapper/*.xml
  # 实体类包路径（类型别名）
  type-aliases-package: com.example.demo.entity
  configuration:
    # 下划线转驼峰
    map-underscore-to-camel-case: true
    # 打印 SQL（开发时开启，生产关闭）
    log-impl: org.apache.ibatis.logging.stdout.StdOutImpl
```

**配置项说明**：

**数据源 URL 参数**：
- `useUnicode=true&characterEncoding=utf8`：使用 UTF-8 编码
- `useSSL=false`：关闭 SSL（本地开发）
- `serverTimezone=Asia/Shanghai`：设置时区

**连接池参数**：

- `minimum-idle`：最小空闲连接数，池中始终保持的连接数
- `maximum-pool-size`：最大连接数，池中最多有多少连接
- `connection-timeout`：获取连接的超时时间
- `idle-timeout`：连接空闲多久后被回收
- `max-lifetime`：连接最多存活多久后被回收

---

### 1.3 创建数据库和表

**创建数据库**：
```sql
CREATE DATABASE demo DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

USE demo;
```

**创建用户表**：
```sql
CREATE TABLE `user` (
  `id` BIGINT(20) NOT NULL AUTO_INCREMENT COMMENT '用户ID',
  `username` VARCHAR(50) NOT NULL COMMENT '用户名',
  `password` VARCHAR(100) NOT NULL COMMENT '密码',
  `email` VARCHAR(50) DEFAULT NULL COMMENT '邮箱',
  `phone` VARCHAR(20) DEFAULT NULL COMMENT '手机号',
  `status` TINYINT(1) DEFAULT 1 COMMENT '状态（0-禁用 1-启用）',
  `create_time` DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_username` (`username`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='用户表';
```

**插入测试数据**：
```sql
INSERT INTO `user` (`username`, `password`, `email`, `phone`) VALUES
('admin', '$2a$10$...', 'admin@example.com', '13800138000'),
('zhangsan', '$2a$10$...', 'zhangsan@example.com', '13800138001'),
('lisi', '$2a$10$...', 'lisi@example.com', '13800138002');
```

---

### 1.4 创建实体类

**User.java**：
```java
package com.example.demo.entity;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class User {
    private Long id;
    private String username;
    private String password;
    private String email;
    private String phone;
    private Integer status;          // 0-禁用 1-启用
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
}
```

**注意事项**：

- 类名和表名对应（驼峰 ↔ 下划线）
- 属性名和字段名对应（驼峰 ↔ 下划线）
- MyBatis 会自动转换（配置了 `map-underscore-to-camel-case: true`）

---

### 1.5 测试数据库连接

**DemoApplicationTests.java**：
```java
package com.example.demo;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

@SpringBootTest
public class DemoApplicationTests {
    
    @Autowired
    private DataSource dataSource;
    
    @Test
    public void testConnection() throws SQLException {
        // 获取连接
        Connection connection = dataSource.getConnection();
        
        // 打印连接信息
        System.out.println("数据源类型: " + dataSource.getClass());
        System.out.println("连接对象: " + connection);
        System.out.println("数据库URL: " + connection.getMetaData().getURL());
        System.out.println("数据库用户: " + connection.getMetaData().getUserName());
        
        // 关闭连接（实际上是还回连接池）
        connection.close();
        
        System.out.println("✅ 数据库连接成功！");
    }
}
```

**运行测试**：
```
数据源类型: class com.zaxxer.hikari.HikariDataSource
连接对象: HikariProxyConnection@123456789
数据库URL: jdbc:mysql://localhost:3306/demo
数据库用户: root@localhost
✅ 数据库连接成功！
```

---

## 📝 第二部分：Mapper 接口与 XML 映射

### 2.1 创建 Mapper 接口

**UserMapper.java**：
```java
package com.example.demo.mapper;

import com.example.demo.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper  // 标记为 MyBatis Mapper 接口
public interface UserMapper {
    
    /**
     * 根据 ID 查询用户
     */
    User selectById(Long id);
    
    /**
     * 根据用户名查询用户
     */
    User selectByUsername(String username);
    
    /**
     * 查询所有用户
     */
    List<User> selectAll();
    
    /**
     * 分页查询用户
     */
    List<User> selectPage(@Param("offset") Integer offset, @Param("limit") Integer limit);
    
    /**
     * 查询用户总数
     */
    Long countAll();
    
    /**
     * 插入用户
     */
    int insert(User user);
    
    /**
     * 更新用户
     */
    int update(User user);
    
    /**
     * 删除用户
     */
    int deleteById(Long id);
}
```

**@Mapper 注解的作用**：
- 标记这是一个 MyBatis Mapper 接口
- Spring Boot 会自动扫描并创建代理对象
- 不需要写实现类

**@Param 注解的作用**：
- 给参数命名，在 XML 中可以通过名称引用
- 如果只有一个参数，可以不加 @Param
- 如果有多个参数，建议都加 @Param

---

### 2.2 创建 Mapper XML 文件

**目录结构**：
```
src/main/resources/
└── mapper/
    └── UserMapper.xml
```

**UserMapper.xml**：
```xml
<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE mapper PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN" 
    "http://mybatis.org/dtd/mybatis-3-mapper.dtd">

<!-- namespace 必须对应 Mapper 接口的全限定名 -->
<mapper namespace="com.example.demo.mapper.UserMapper">
    
    <!-- 结果映射：定义如何将数据库字段映射到 Java 对象 -->
    <resultMap id="BaseResultMap" type="User">
        <id property="id" column="id"/>
        <result property="username" column="username"/>
        <result property="password" column="password"/>
        <result property="email" column="email"/>
        <result property="phone" column="phone"/>
        <result property="status" column="status"/>
        <result property="createTime" column="create_time"/>
        <result property="updateTime" column="update_time"/>
    </resultMap>
    
    <!-- SQL 片段：可复用的 SQL 片段 -->
    <sql id="Base_Column_List">
        id, username, password, email, phone, status, create_time, update_time
    </sql>
    
    <!-- 根据 ID 查询用户 -->
    <select id="selectById" resultMap="BaseResultMap">
        SELECT
            <include refid="Base_Column_List"/>
        FROM user
        WHERE id = #{id}
    </select>
    
    <!-- 根据用户名查询用户 -->
    <select id="selectByUsername" resultMap="BaseResultMap">
        SELECT
            <include refid="Base_Column_List"/>
        FROM user
        WHERE username = #{username}
    </select>
    
    <!-- 查询所有用户 -->
    <select id="selectAll" resultMap="BaseResultMap">
        SELECT
            <include refid="Base_Column_List"/>
        FROM user
        ORDER BY create_time DESC
    </select>
    
    <!-- 分页查询用户 -->
    <select id="selectPage" resultMap="BaseResultMap">
        SELECT
            <include refid="Base_Column_List"/>
        FROM user
        ORDER BY create_time DESC
        LIMIT #{offset}, #{limit}
    </select>
    
    <!-- 查询用户总数 -->
    <select id="countAll" resultType="long">
        SELECT COUNT(*) FROM user
    </select>
    
    <!-- 插入用户 -->
    <insert id="insert" useGeneratedKeys="true" keyProperty="id">
        INSERT INTO user (username, password, email, phone, status)
        VALUES (#{username}, #{password}, #{email}, #{phone}, #{status})
    </insert>
    
    <!-- 更新用户 -->
    <update id="update">
        UPDATE user
        SET username = #{username},
            email = #{email},
            phone = #{phone},
            status = #{status}
        WHERE id = #{id}
    </update>
    
    <!-- 删除用户 -->
    <delete id="deleteById">
        DELETE FROM user WHERE id = #{id}
    </delete>
    
</mapper>
```

**XML 标签说明**：

**1. `<mapper>`**：根标签，`namespace` 必须对应 Mapper 接口

**2. `<resultMap>`**：结果映射
```xml
<resultMap id="BaseResultMap" type="User">
    <id property="id" column="id"/>           <!-- 主键 -->
    <result property="username" column="username"/>  <!-- 普通字段 -->
</resultMap>
```
- `property`：Java 对象的属性名
- `column`：数据库表的字段名
- 如果属性名和字段名一致（或驼峰转下划线一致），可以省略

**3. `<sql>` 和 `<include>`**：SQL 片段复用
```xml
<sql id="Base_Column_List">
    id, username, email
</sql>

<select id="selectAll">
    SELECT <include refid="Base_Column_List"/> FROM user
</select>
```

**4. `<select>`**：查询

```xml
<select id="selectById" resultMap="BaseResultMap">
    SELECT * FROM user WHERE id = #{id}
</select>
```
- `resultMap`：使用 resultMap 映射
- `resultType`：直接指定返回类型（如 `resultType="User"`）

**5. `<insert>`**：插入
```xml
<insert id="insert" useGeneratedKeys="true" keyProperty="id">
    INSERT INTO user (username, email) VALUES (#{username}, #{email})
</insert>
```
- `useGeneratedKeys="true"`：返回自增主键
- `keyProperty="id"`：将主键值设置到对象的 id 属性

**6. `<update>` 和 `<delete>`**：更新和删除
```xml
<update id="update">
    UPDATE user SET username = #{username} WHERE id = #{id}
</update>

<delete id="deleteById">
    DELETE FROM user WHERE id = #{id}
</delete>
```

**7. `#{}`  vs `${}`**：
```xml
<!-- #{} 会使用预编译（推荐，防止 SQL 注入） -->
WHERE id = #{id}
<!-- 生成：WHERE id = ? -->

<!-- ${} 直接拼接（不安全，慎用） -->
ORDER BY ${columnName}
<!-- 生成：ORDER BY username -->
```

---

### 2.3 编写 Service 层

**UserService.java**：
```java
package com.example.demo.service;

import com.example.demo.entity.User;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.mapper.UserMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
public class UserService {
    
    @Autowired
    private UserMapper userMapper;
    
    /**
     * 根据 ID 查询用户
     */
    public User findById(Long id) {
        User user = userMapper.selectById(id);
        if (user == null) {
            throw new ResourceNotFoundException("用户不存在：ID = " + id);
        }
        return user;
    }
    
    /**
     * 根据用户名查询用户
     */
    public User findByUsername(String username) {
        return userMapper.selectByUsername(username);
    }
    
    /**
     * 查询所有用户
     */
    public List<User> findAll() {
        return userMapper.selectAll();
    }
    
    /**
     * 分页查询用户
     */
    public List<User> findPage(Integer page, Integer size) {
        Integer offset = (page - 1) * size;
        return userMapper.selectPage(offset, size);
    }
    
    /**
     * 查询用户总数
     */
    public Long count() {
        return userMapper.countAll();
    }
    
    /**
     * 保存用户
     */
    @Transactional(rollbackFor = Exception.class)
    public User save(User user) {
        // 检查用户名是否重复
        User existingUser = userMapper.selectByUsername(user.getUsername());
        if (existingUser != null) {
            throw new RuntimeException("用户名已存在");
        }
        
        // 插入用户
        userMapper.insert(user);
        log.info("用户创建成功：ID = {}", user.getId());
        
        return user;
    }
    
    /**
     * 更新用户
     */
    @Transactional(rollbackFor = Exception.class)
    public User update(User user) {
        // 检查用户是否存在
        User existingUser = findById(user.getId());
        
        // 更新用户
        int rows = userMapper.update(user);
        if (rows == 0) {
            throw new RuntimeException("更新失败");
        }
        
        log.info("用户更新成功：ID = {}", user.getId());
        return findById(user.getId());
    }
    
    /**
     * 删除用户
     */
    @Transactional(rollbackFor = Exception.class)
    public void deleteById(Long id) {
        // 检查用户是否存在
        findById(id);
        
        // 删除用户
        int rows = userMapper.deleteById(id);
        if (rows == 0) {
            throw new RuntimeException("删除失败");
        }
        
        log.info("用户删除成功：ID = {}", id);
    }
}
```

---

### 2.4 编写 Controller 层

**UserController.java**：
```java
package com.example.demo.controller;

import com.example.demo.common.PageResult;
import com.example.demo.common.Result;
import com.example.demo.entity.User;
import com.example.demo.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/v1/users")
public class UserController {
    
    @Autowired
    private UserService userService;
    
    /**
     * 获取用户列表（分页）
     */
    @GetMapping
    public Result<PageResult<User>> getUsers(
        @RequestParam(defaultValue = "1") Integer page,
        @RequestParam(defaultValue = "10") Integer size
    ) {
        List<User> users = userService.findPage(page, size);
        Long total = userService.count();
        
        PageResult<User> pageResult = new PageResult<>(users, total, page, size);
        return Result.success(pageResult);
    }
    
    /**
     * 获取单个用户
     */
    @GetMapping("/{id}")
    public Result<User> getUser(@PathVariable Long id) {
        User user = userService.findById(id);
        return Result.success(user);
    }
    
    /**
     * 创建用户
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
    public Result<User> updateUser(@PathVariable Long id, @Valid @RequestBody User user) {
        user.setId(id);
        User updatedUser = userService.update(user);
        return Result.success("更新成功", updatedUser);
    }
    
    /**
     * 删除用户
     */
    @DeleteMapping("/{id}")
    public Result<Void> deleteUser(@PathVariable Long id) {
        userService.deleteById(id);
        return Result.success("删除成功");
    }
}
```

---

### 2.5 测试

**启动项目**，访问：
```bash
# 查询所有用户
GET http://localhost:8080/api/v1/users?page=1&size=10

# 查询单个用户
GET http://localhost:8080/api/v1/users/1

# 创建用户
POST http://localhost:8080/api/v1/users
Content-Type: application/json

{
  "username": "wangwu",
  "password": "123456",
  "email": "wangwu@example.com",
  "phone": "13800138003",
  "status": 1
}

# 更新用户
PUT http://localhost:8080/api/v1/users/1
Content-Type: application/json

{
  "username": "admin",
  "email": "newemail@example.com",
  "phone": "13900139000",
  "status": 1
}

# 删除用户
DELETE http://localhost:8080/api/v1/users/1
```

---

## 🎯 第三部分：动态 SQL

### 3.1 什么是动态 SQL？

**动态 SQL** = 根据条件动态生成 SQL

**场景**：条件查询
```java
// 用户可能只输入用户名，也可能输入邮箱，也可能都输入
List<User> searchUsers(String username, String email);
```

**❌ 不用动态 SQL（拼接字符串，容易出错）**：
```java
String sql = "SELECT * FROM user WHERE 1=1";
if (username != null) {
    sql += " AND username = ?";
}
if (email != null) {
    sql += " AND email = ?";
}
```

**✅ 使用动态 SQL（MyBatis 自动处理）**：
```xml
<select id="search" resultMap="BaseResultMap">
    SELECT * FROM user
    <where>
        <if test="username != null">
            AND username = #{username}
        </if>
        <if test="email != null">
            AND email = #{email}
        </if>
    </where>
</select>
```

---

### 3.2 常用动态 SQL 标签

#### 1. `<if>`：条件判断

```xml
<select id="search" resultMap="BaseResultMap">
    SELECT * FROM user
    <where>
        <if test="username != null and username != ''">
            AND username LIKE CONCAT('%', #{username}, '%')
        </if>
        <if test="email != null and email != ''">
            AND email = #{email}
        </if>
        <if test="status != null">
            AND status = #{status}
        </if>
    </where>
</select>
```

**test 表达式**：
- `username != null`：username 不为 null
- `username != ''`：username 不为空字符串
- `username != null and username != ''`：两个条件都满足

---

#### 2. `<where>`：智能添加 WHERE 子句

**作用**：
- 自动添加 `WHERE` 关键字
- 自动去掉第一个 `AND` 或 `OR`

**示例**：
```xml
<select id="search" resultMap="BaseResultMap">
    SELECT * FROM user
    <where>
        <if test="username != null">
            AND username = #{username}
        </if>
        <if test="email != null">
            AND email = #{email}
        </if>
    </where>
</select>
```

**生成的 SQL**：
```sql
-- 如果都不为 null
SELECT * FROM user WHERE username = ? AND email = ?

-- 如果只有 username
SELECT * FROM user WHERE username = ?

-- 如果都为 null
SELECT * FROM user
```

---

#### 3. `<set>`：智能生成 SET 子句

**作用**：
- 自动添加 `SET` 关键字
- 自动去掉最后一个逗号

**示例**：
```xml
<update id="updateSelective">
    UPDATE user
    <set>
        <if test="username != null">
            username = #{username},
        </if>
        <if test="email != null">
            email = #{email},
        </if>
        <if test="phone != null">
            phone = #{phone},
        </if>
    </set>
    WHERE id = #{id}
</update>
```

**生成的 SQL**：
```sql
-- 如果都不为 null
UPDATE user SET username = ?, email = ?, phone = ? WHERE id = ?

-- 如果只有 email
UPDATE user SET email = ? WHERE id = ?
```

---

#### 4. `<trim>`：自定义前缀和后缀

**作用**：更灵活的字符串处理

**示例**：
```xml
<select id="search" resultMap="BaseResultMap">
    SELECT * FROM user
    <trim prefix="WHERE" prefixOverrides="AND |OR ">
        <if test="username != null">
            AND username = #{username}
        </if>
        <if test="email != null">
            AND email = #{email}
        </if>
    </trim>
</select>
```

**参数**：
- `prefix`：添加的前缀（如 `WHERE`）
- `suffix`：添加的后缀
- `prefixOverrides`：去掉的前缀（如 `AND` 或 `OR`）
- `suffixOverrides`：去掉的后缀

---

#### 5. `<choose>`、`<when>`、`<otherwise>`：多分支选择

**类似于 Java 的 switch-case**：
```xml
<select id="search" resultMap="BaseResultMap">
    SELECT * FROM user
    <where>
        <choose>
            <when test="username != null">
                AND username = #{username}
            </when>
            <when test="email != null">
                AND email = #{email}
            </when>
            <otherwise>
                AND status = 1
            </otherwise>
        </choose>
    </where>
</select>
```

**逻辑**：
- 如果 `username` 不为 null，按 username 查询
- 否则，如果 `email` 不为 null，按 email 查询
- 否则，查询 status = 1 的用户

---

#### 6. `<foreach>`：遍历集合

**场景1：IN 查询**：
```xml
<select id="selectByIds" resultMap="BaseResultMap">
    SELECT * FROM user
    WHERE id IN
    <foreach collection="ids" item="id" open="(" separator="," close=")">
        #{id}
    </foreach>
</select>
```

**生成的 SQL**：
```sql
SELECT * FROM user WHERE id IN (1, 2, 3, 4, 5)
```

**场景2：批量插入**：
```xml
<insert id="batchInsert">
    INSERT INTO user (username, email, phone)
    VALUES
    <foreach collection="list" item="user" separator=",">
        (#{user.username}, #{user.email}, #{user.phone})
    </foreach>
</insert>
```

**生成的 SQL**：
```sql
INSERT INTO user (username, email, phone)
VALUES
('user1', 'user1@example.com', '13800138001'),
('user2', 'user2@example.com', '13800138002'),
('user3', 'user3@example.com', '13800138003')
```

**参数**：
- `collection`：集合参数名（如 `list`、`array`、`ids`）
- `item`：集合中的元素变量名
- `open`：开始符号（如 `(`）
- `separator`：分隔符（如 `,`）
- `close`：结束符号（如 `)`）

---

### 3.3 完整的动态 SQL 示例

**UserMapper.java**：
```java
@Mapper
public interface UserMapper {
    
    /**
     * 多条件搜索用户
     */
    List<User> search(@Param("username") String username,
                      @Param("email") String email,
                      @Param("status") Integer status);
    
    /**
     * 选择性更新用户
     */
    int updateSelective(User user);
    
    /**
     * 根据 ID 列表查询用户
     */
    List<User> selectByIds(@Param("ids") List<Long> ids);
    
    /**
     * 批量插入用户
     */
    int batchInsert(@Param("list") List<User> users);
}
```

**UserMapper.xml**：
```xml
<!-- 多条件搜索用户 -->
<select id="search" resultMap="BaseResultMap">
    SELECT
        <include refid="Base_Column_List"/>
    FROM user
    <where>
        <if test="username != null and username != ''">
            AND username LIKE CONCAT('%', #{username}, '%')
        </if>
        <if test="email != null and email != ''">
            AND email = #{email}
        </if>
        <if test="status != null">
            AND status = #{status}
        </if>
    </where>
    ORDER BY create_time DESC
</select>

<!-- 选择性更新用户 -->
<update id="updateSelective">
    UPDATE user
    <set>
        <if test="username != null and username != ''">
            username = #{username},
        </if>
        <if test="email != null and email != ''">
            email = #{email},
        </if>
        <if test="phone != null and phone != ''">
            phone = #{phone},
        </if>
        <if test="status != null">
            status = #{status},
        </if>
    </set>
    WHERE id = #{id}
</update>

<!-- 根据 ID 列表查询用户 -->
<select id="selectByIds" resultMap="BaseResultMap">
    SELECT
        <include refid="Base_Column_List"/>
    FROM user
    <where>
        <if test="ids != null and ids.size() > 0">
            id IN
            <foreach collection="ids" item="id" open="(" separator="," close=")">
                #{id}
            </foreach>
        </if>
    </where>
</select>

<!-- 批量插入用户 -->
<insert id="batchInsert">
    INSERT INTO user (username, password, email, phone, status)
    VALUES
    <foreach collection="list" item="user" separator=",">
        (#{user.username}, #{user.password}, #{user.email}, #{user.phone}, #{user.status})
    </foreach>
</insert>
```

---

## 📄 第四部分：分页插件 PageHelper

### 4.1 什么是 PageHelper？

**PageHelper** 是 MyBatis 的分页插件，可以自动为查询添加分页功能。

**不用 PageHelper（手动分页）**：

```java
// 需要自己计算 offset
int offset = (page - 1) * size;
List<User> users = userMapper.selectPage(offset, size);
Long total = userMapper.countAll();
```

**使用 PageHelper（自动分页）**：

```java
// PageHelper 自动添加 LIMIT，自动查询总数
PageHelper.startPage(page, size);
List<User> users = userMapper.selectAll();
PageInfo<User> pageInfo = new PageInfo<>(users);
```

---

### 4.2 添加依赖

**pom.xml**：
```xml
<dependency>
    <groupId>com.github.pagehelper</groupId>
    <artifactId>pagehelper-spring-boot-starter</artifactId>
    <version>1.4.7</version>
</dependency>
```

---

### 4.3 配置 PageHelper

**application.yml**：
```yaml
pagehelper:
  # 数据库方言
  helper-dialect: mysql
  # 启用合理化，如果 pageNum < 1 则查询第一页，如果 pageNum > pages 则查询最后一页
  reasonable: true
  # 支持通过 Mapper 接口参数来传递分页参数
  support-methods-arguments: true
  # 分页参数合理化
  params: count=countSql
```

---

### 4.4 使用 PageHelper

**UserService.java**：
```java
package com.example.demo.service;

import com.example.demo.entity.User;
import com.example.demo.mapper.UserMapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    
    @Autowired
    private UserMapper userMapper;
    
    /**
     * 分页查询用户（使用 PageHelper）
     */
    public PageInfo<User> findPage(Integer page, Integer size) {
        // 1. 开启分页（在查询之前调用）
        PageHelper.startPage(page, size);
        
        // 2. 执行查询（PageHelper 会自动添加 LIMIT）
        List<User> users = userMapper.selectAll();
        
        // 3. 创建 PageInfo 对象（包含分页信息）
        PageInfo<User> pageInfo = new PageInfo<>(users);
        
        return pageInfo;
    }
    
    /**
     * 多条件搜索用户（带分页）
     */
    public PageInfo<User> search(String username, String email, Integer status, 
                                  Integer page, Integer size) {
        PageHelper.startPage(page, size);
        List<User> users = userMapper.search(username, email, status);
        return new PageInfo<>(users);
    }
}
```

**PageInfo 对象**：
```java
PageInfo<User> pageInfo = new PageInfo<>(users);

// 分页信息
pageInfo.getPageNum();      // 当前页
pageInfo.getPageSize();     // 每页大小
pageInfo.getTotal();        // 总记录数
pageInfo.getPages();        // 总页数
pageInfo.getList();         // 当前页数据
pageInfo.isIsFirstPage();   // 是否第一页
pageInfo.isIsLastPage();    // 是否最后一页
pageInfo.isHasPreviousPage();  // 是否有上一页
pageInfo.isHasNextPage();   // 是否有下一页
```

---

### 4.5 Controller 层使用

**UserController.java**：
```java
@RestController
@RequestMapping("/api/v1/users")
public class UserController {
    
    @Autowired
    private UserService userService;
    
    /**
     * 分页查询用户
     */
    @GetMapping
    public Result<PageInfo<User>> getUsers(
        @RequestParam(defaultValue = "1") Integer page,
        @RequestParam(defaultValue = "10") Integer size
    ) {
        PageInfo<User> pageInfo = userService.findPage(page, size);
        return Result.success(pageInfo);
    }
    
    /**
     * 搜索用户（带分页）
     */
    @GetMapping("/search")
    public Result<PageInfo<User>> searchUsers(
        @RequestParam(required = false) String username,
        @RequestParam(required = false) String email,
        @RequestParam(required = false) Integer status,
        @RequestParam(defaultValue = "1") Integer page,
        @RequestParam(defaultValue = "10") Integer size
    ) {
        PageInfo<User> pageInfo = userService.search(username, email, status, page, size);
        return Result.success(pageInfo);
    }
}
```

**返回结果**：
```json
{
  "code": 200,
  "message": "success",
  "data": {
    "pageNum": 1,
    "pageSize": 10,
    "total": 50,
    "pages": 5,
    "list": [
      {"id": 1, "username": "admin", "email": "admin@example.com"},
      {"id": 2, "username": "zhangsan", "email": "zhangsan@example.com"}
    ],
    "isFirstPage": true,
    "isLastPage": false,
    "hasPreviousPage": false,
    "hasNextPage": true
  }
}
```

---

## 🔒 第五部分：事务管理

### 5.1 什么是事务？

**事务（Transaction）** = 一组数据库操作，要么全成功，要么全失败

**为什么需要事务？**

**❌ 没有事务**：
```java
public void transfer(Long fromId, Long toId, BigDecimal amount) {
    // 1. 扣款
    accountDao.deduct(fromId, amount);
    
    // 2. 如果这里出错...
    int result = 1 / 0;  // 抛出异常
    
    // 3. 加款（不会执行）
    accountDao.add(toId, amount);
}

// 结果：A 的钱被扣了，B 没收到，钱丢了！
```

**✅ 有事务**：
```java
@Transactional
public void transfer(Long fromId, Long toId, BigDecimal amount) {
    // 1. 扣款
    accountDao.deduct(fromId, amount);
    
    // 2. 如果这里出错...
    int result = 1 / 0;  // 抛出异常
    
    // 3. 加款（不会执行）
    accountDao.add(toId, amount);
}

// 结果：事务回滚，A 的钱不会被扣，数据一致
```

---

### 5.2 Spring 事务管理

**Spring 提供声明式事务管理**，只需要加 `@Transactional` 注解

**启用事务管理**（Spring Boot 已自动启用）：
```java
@SpringBootApplication
@EnableTransactionManagement  // 可省略，Spring Boot 已自动启用
public class DemoApplication {
    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);
    }
}
```

---

### 5.3 @Transactional 注解

**基础用法**：
```java
@Service
public class UserService {
    
    @Transactional
    public void save(User user) {
        // 所有数据库操作要么全成功，要么全失败
        userMapper.insert(user);
        roleMapper.insertUserRole(user.getId(), 1L);
    }
}
```

**@Transactional 参数**：

**1. rollbackFor**：指定哪些异常回滚

```java
@Transactional(rollbackFor = Exception.class)
public void save(User user) {
    // 所有异常都回滚（推荐）
}

@Transactional(rollbackFor = {RuntimeException.class, IOException.class})
public void save(User user) {
    // 指定异常回滚
}
```

**默认行为**：只有 `RuntimeException` 和 `Error` 才回滚，`Exception` 不回滚
**推荐**：`rollbackFor = Exception.class`，所有异常都回滚

---

**2. propagation**：事务传播行为
```java
@Transactional(propagation = Propagation.REQUIRED)
public void save(User user) {
    // ...
}
```

**传播行为**：

| 传播行为 | 说明 |
|---------|------|
| **REQUIRED**（默认） | 如果当前有事务，加入该事务；如果没有，创建新事务 |
| **REQUIRES_NEW** | 总是创建新事务，如果当前有事务，挂起当前事务 |
| **SUPPORTS** | 如果当前有事务，加入该事务；如果没有，以非事务方式执行 |
| **NOT_SUPPORTED** | 以非事务方式执行，如果当前有事务，挂起当前事务 |
| **MANDATORY** | 必须在事务中执行，如果当前没有事务，抛出异常 |
| **NEVER** | 不能在事务中执行，如果当前有事务，抛出异常 |
| **NESTED** | 如果当前有事务，创建嵌套事务；如果没有，创建新事务 |

**常用场景**：
```java
// 场景1：外层方法和内层方法都需要事务
@Transactional
public void saveUser(User user) {
    userMapper.insert(user);
    saveUserRole(user.getId(), 1L);  // 加入外层事务
}

@Transactional(propagation = Propagation.REQUIRED)  // 默认
public void saveUserRole(Long userId, Long roleId) {
    roleMapper.insertUserRole(userId, roleId);
}

// 场景2：内层方法需要独立事务
@Transactional
public void saveUser(User user) {
    userMapper.insert(user);
    saveLog(user);  // 即使 saveLog 失败，用户仍然保存成功
}

@Transactional(propagation = Propagation.REQUIRES_NEW)
public void saveLog(User user) {
    logMapper.insertLog("创建用户：" + user.getUsername());
}
```

---

**3. isolation**：事务隔离级别

```java
@Transactional(isolation = Isolation.READ_COMMITTED)
public void save(User user) {
    // ...
}
```

#### 隔离级别总览

| 隔离级别 | 脏读 | 不可重复读 | 幻读 | 性能 | 并发度 |
|---------|-----|----------|-----|------|-------|
| **READ_UNCOMMITTED** | ✅ 可能 | ✅ 可能 | ✅ 可能 | 最高 | 最高 |
| **READ_COMMITTED** | ❌ 不会 | ✅ 可能 | ✅ 可能 | 较高 | 较高 |
| **REPEATABLE_READ** | ❌ 不会 | ❌ 不会 | ✅ 可能* | 一般 | 一般 |
| **SERIALIZABLE** | ❌ 不会 | ❌ 不会 | ❌ 不会 | 最低 | 最低 |

> **注**：MySQL InnoDB 的 REPEATABLE_READ 通过 Next-Key Lock 解决了幻读问题

---

#### 核心概念详解

**1. 脏读（Dirty Read）**

**定义**：读到其他事务**未提交**的数据

**问题场景**：
```
时间线          事务 A                       事务 B
T1          开始事务
T2          余额 = 1000
T3                                        开始事务
T4                                        余额 = 1000 - 500 = 500
T5          读取余额 = 500（脏读！）
T6                                        回滚事务（余额恢复 1000）
T7          余额实际是 1000，但 A 读到了 500
```

**代码示例**：
```java
// 事务 A（READ_UNCOMMITTED 隔离级别）
@Transactional(isolation = Isolation.READ_UNCOMMITTED)
public void processOrder() {
    // 读取账户余额
    BigDecimal balance = accountMapper.getBalance(userId);  // 读到 500
    
    // 基于余额进行业务判断
    if (balance.compareTo(new BigDecimal("100")) > 0) {
        // 创建订单（但实际余额是 1000，不是 500）
        orderMapper.createOrder(order);
    }
}

// 事务 B
@Transactional
public void withdraw() {
    accountMapper.updateBalance(userId, -500);  // 扣款 500
    // 还没提交
    throw new RuntimeException("取消扣款");  // 回滚
}
```

**后果**：事务 A 基于错误的数据做决策，导致业务错误

---

**2. 不可重复读（Non-Repeatable Read）**

**定义**：同一事务中，两次读取**同一行**数据，结果不同（因为被其他事务 **UPDATE** 了）

**问题场景**：
```
时间线          事务 A                       事务 B
T1          开始事务
T2          第一次读取：余额 = 1000
T3                                        开始事务
T4                                        UPDATE：余额 = 1000 - 500 = 500
T5                                        提交事务
T6          第二次读取：余额 = 500（不一致！）
T7          提交事务
```

**代码示例**：
```java
// 事务 A（READ_COMMITTED 隔离级别）
@Transactional(isolation = Isolation.READ_COMMITTED)
public void statisticsReport() {
    // 第一次查询
    BigDecimal balance1 = accountMapper.getBalance(userId);  // 1000
    
    // 中间做一些其他操作...
    Thread.sleep(1000);
    
    // 第二次查询（同一行数据）
    BigDecimal balance2 = accountMapper.getBalance(userId);  // 500（不一样了！）
    
    // 生成统计报表（数据不一致）
    if (!balance1.equals(balance2)) {
        // 数据不一致，报表错误
    }
}

// 事务 B
@Transactional
public void updateBalance() {
    accountMapper.updateBalance(userId, -500);  // 扣款 500
    // 提交
}
```

**后果**：同一事务中前后读取的数据不一致，报表、统计结果错误

---

**3. 幻读，只与范围有关（Phantom Read）**

**定义**：同一事务中，两次查询**同一范围**数据，记录数不同（因为被其他事务 **INSERT/DELETE** 了）

**问题场景**：

```
时间线          事务 A                       事务 B
T1          开始事务
T2          第一次查询：COUNT(*) = 10
T3                                        开始事务
T4                                        INSERT 一条新记录
T5                                        提交事务
T6          第二次查询：COUNT(*) = 11（多了一条！）
T7          提交事务
```

**代码示例**：
```java
// 事务 A（REPEATABLE_READ 隔离级别，但仍可能幻读）
@Transactional(isolation = Isolation.REPEATABLE_READ)
public void processOrders() {
    // 第一次查询：查询待处理订单
    List<Order> orders1 = orderMapper.selectPendingOrders();  // 10 条
    log.info("第一次查询：{} 条订单", orders1.size());  // 10
    
    // 中间做一些处理...
    for (Order order : orders1) {
        processOrder(order);
    }
    
    // 第二次查询：再次查询待处理订单
    List<Order> orders2 = orderMapper.selectPendingOrders();  // 11 条（多了一条！）
    log.info("第二次查询：{} 条订单", orders2.size());  // 11
    
    // 问题：第二次查询多了一条，导致业务逻辑错误
}

// 事务 B
@Transactional
public void createOrder() {
    orderMapper.insert(newOrder);  // 插入新订单
    // 提交
}
```

**后果**：统计数据不一致，可能导致重复处理或遗漏处理

**注意**：
- MySQL InnoDB 的 `REPEATABLE_READ` 通过 **Next-Key Lock**（间隙锁）解决了幻读
- 但如果查询条件没有用到索引，仍可能出现幻读

---

#### 四种隔离级别详解

**1. READ_UNCOMMITTED（读未提交）**

**特点**：
- 最低的隔离级别
- 事务可以读取其他事务**未提交**的数据
- 会出现：脏读、不可重复读、幻读

**实现原理**：
- 读不加锁，写加排他锁
- 读操作不会被阻塞

**使用场景**：
- ❌ **几乎不使用**（数据不一致风险太大）
- 可能场景：允许极小误差的实时统计（访问量、点击量）

**示例**：
```java
@Transactional(isolation = Isolation.READ_UNCOMMITTED)
public void countPageViews() {
    // 读取页面访问量（允许误差）
    Long views = statsMapper.getPageViews(pageId);
    // 即使读到未提交的数据，误差也可以接受
}
```

---

**2. READ_COMMITTED（读已提交）**

**特点**：
- 只能读取其他事务**已提交**的数据
- 解决了：脏读
- 仍会出现：不可重复读、幻读

**实现原理**：
- 读操作开始时创建快照（MVCC数据库版本控制机制）
- 每次读取都获取最新的已提交数据
- 写操作加排他锁

**使用场景**：

- ✅ **Oracle 默认隔离级别**
- ✅ 适合**大部分 OLTP（在线事务处理）系统**
- ✅ 对数据一致性要求不高，但要避免脏读

**示例**：
```java
@Transactional(isolation = Isolation.READ_COMMITTED)
public void processPayment(Long orderId) {
    // 查询订单状态（读取最新已提交的数据）
    Order order = orderMapper.selectById(orderId);
    
    if ("PENDING".equals(order.getStatus())) {
        // 处理支付
        paymentService.pay(order);
        
        // 更新订单状态
        orderMapper.updateStatus(orderId, "PAID");
    }
}

// 特点：
// 1. 不会读到未提交的数据（避免脏读）
// 2. 每次读取都是最新的已提交数据
// 3. 但同一事务中，多次读取可能不一致（不可重复读）
```

**优点**：
- 性能较好，并发度高
- 避免了脏读
- 适合大部分业务场景

**缺点**：
- 同一事务中，多次读取可能不一致
- 不适合需要"可重复读"的场景（如报表、统计）

---

**3. REPEATABLE_READ（可重复读）**

**特点**：
- 同一事务中，多次读取**同一行**数据，结果一致
- 解决了：脏读、不可重复读
- 理论上仍会出现：幻读（但 MySQL InnoDB 解决了）

**实现原理**：
- 事务开始时创建一致性快照（MVCC）
- 读操作基于快照，不读取其他事务的修改
- 写操作加排他锁
- MySQL InnoDB 通过 **Next-Key Lock**（行锁 + 间隙锁，即临键锁）防止幻读，通过锁住“记录 + 前间隙”，阻止其他事务在查询范围内插入新记录，从而彻底解决当前读下的幻读问题（当然，这个机制依赖索引，如果查询不用索引导致回落到全表扫描，会导致没有间隙可锁，这个机制就会失效）

**使用场景**：

- ✅ **MySQL InnoDB 默认隔离级别**
- ✅ 需要**可重复读**的场景（报表、统计、对账）
- ✅ 需要**事务内（即一个事务内部进行多次查询）数据一致性**的场景

**示例1：报表统计**
```java
@Transactional(isolation = Isolation.REPEATABLE_READ)
public OrderReport generateReport(LocalDate date) {
    // 查询当天订单总数
    Long totalOrders = orderMapper.countByDate(date);  // 100
    
    // 中间做一些计算...
    
    // 再次查询（结果一致）
    Long totalOrders2 = orderMapper.countByDate(date);  // 仍然是 100
    
    // 查询订单明细（即使其他事务插入了新订单，这里也读不到）
    List<Order> orders = orderMapper.selectByDate(date);
    
    // 生成报表（数据一致）
    return new OrderReport(totalOrders, orders);
}
```

**示例2：库存扣减**
```java
@Transactional(isolation = Isolation.REPEATABLE_READ)
public void decreaseStock(Long productId, Integer quantity) {
    // 第一次查询库存
    Integer stock1 = productMapper.getStock(productId);  // 100
    
    if (stock1 < quantity) {
        throw new RuntimeException("库存不足");
    }
    
    // 中间做一些业务处理...
    
    // 第二次查询库存（仍然是 100，不会读到其他事务的修改）
    Integer stock2 = productMapper.getStock(productId);  // 100
    
    // 扣减库存
    productMapper.updateStock(productId, stock1 - quantity);
}
```

**优点**：
- 同一事务中，读取数据一致
- 适合报表、统计、对账
- MySQL InnoDB 还解决了幻读

**缺点**：
- 性能略低于 READ_COMMITTED
- 可能出现锁等待

---

**4. SERIALIZABLE（串行化）**

**特点**：
- 最高的隔离级别
- 事务串行执行，完全隔离
- 解决了：脏读、不可重复读、幻读

**实现原理**：
- 读操作加**共享锁**（S 锁）
- 写操作加**排他锁**（X 锁）
- 读读可以并发，读写、写写互斥
- 完全阻塞式，几乎无并发

**使用场景**：
- ✅ **对数据一致性要求极高**的场景（金融转账、库存扣减）
- ✅ **并发度极低**的场景
- ❌ 高并发场景不推荐（性能差）

**示例：银行转账**
```java
@Transactional(isolation = Isolation.SERIALIZABLE)
public void transfer(Long fromId, Long toId, BigDecimal amount) {
    // 查询余额（加共享锁，其他事务不能修改）
    BigDecimal fromBalance = accountMapper.getBalance(fromId);
    BigDecimal toBalance = accountMapper.getBalance(toId);
    
    if (fromBalance.compareTo(amount) < 0) {
        throw new RuntimeException("余额不足");
    }
    
    // 扣款（加排他锁）
    accountMapper.updateBalance(fromId, fromBalance.subtract(amount));
    
    // 加款（加排他锁）
    accountMapper.updateBalance(toId, toBalance.add(amount));
    
    // 整个过程完全隔离，不会被其他事务干扰
}
```

**优点**：
- 完全避免并发问题
- 数据一致性最高

**缺点**：
- 性能最差
- 并发度最低
- 容易造成锁等待和死锁

---

#### 隔离级别对比与选择

**性能 vs 一致性 权衡**：

```
隔离级别         性能         一致性        并发度       推荐度
READ_UNCOMMITTED  最高         最低          最高        ❌ 不推荐
READ_COMMITTED    较高         一般          较高        ✅ 推荐（Oracle 默认）
REPEATABLE_READ   一般         较高          一般        ✅ 推荐（MySQL 默认）
SERIALIZABLE      最低         最高          最低        ⚠️ 特殊场景
```

---

**如何选择隔离级别？**

**1. 大部分 Web 应用：使用数据库默认**
```java
@Transactional  // 使用默认隔离级别
public void normalOperation() {
    // MySQL：REPEATABLE_READ
    // Oracle：READ_COMMITTED
}
```

**2. 需要读取最新数据：READ_COMMITTED**
```java
@Transactional(isolation = Isolation.READ_COMMITTED)
public void checkOrderStatus(Long orderId) {
    // 需要读取最新的订单状态
    Order order = orderMapper.selectById(orderId);
}
```

**3. 需要数据一致性（报表、统计）：REPEATABLE_READ**
```java
@Transactional(isolation = Isolation.REPEATABLE_READ)
public SalesReport generateReport(LocalDate date) {
    // 报表数据需要在事务内保持一致
    Long totalSales = orderMapper.sumByDate(date);
    List<Order> orders = orderMapper.selectByDate(date);
    return new SalesReport(totalSales, orders);
}
```

**4. 金融级一致性：SERIALIZABLE**
```java
@Transactional(isolation = Isolation.SERIALIZABLE)
public void criticalOperation() {
    // 金融转账、库存扣减等关键操作
}
```

---

#### 面试高频问题

**Q1: MySQL 的 REPEATABLE_READ 是否完全解决了幻读？**

**A**：不完全。

**场景1：普通查询（解决了幻读）**

```java
@Transactional(isolation = Isolation.REPEATABLE_READ)
public void test() {
    // 第一次查询
    List<User> users1 = userMapper.selectAll();  // 10 条
    
    // 其他事务插入了新数据
    
    // 第二次查询（仍然是 10 条，因为用了 MVCC 快照）
    List<User> users2 = userMapper.selectAll();  // 10 条
}
```

**场景2：当前读（可能出现幻读）**

```java
@Transactional(isolation = Isolation.REPEATABLE_READ)
public void test() {
    // 第一次查询（当前读，加锁）
    List<User> users1 = userMapper.selectForUpdate();  // 10 条
    
    // 其他事务插入了新数据（如果查询条件没用索引，可能插入成功）
    
    // 第二次查询
    List<User> users2 = userMapper.selectForUpdate();  // 11 条（幻读）
}
```

**总结**：
- **快照读（普通 SELECT）**：通过 MVCC 解决幻读
- **当前读（SELECT ... FOR UPDATE）**：通过 Next-Key Lock 解决幻读
- 但如果查询条件没有用到索引，锁范围可能扩大，仍可能出现幻读

---

**Q2: 不同隔离级别如何实现的？**

**A**：

**1. MVCC（多版本并发控制）**
- 适用于：READ_COMMITTED、REPEATABLE_READ
- 原理：每行记录有多个版本，事务读取对自己可见的版本
- 优点：读不加锁，提高并发

**2. 锁机制**
- 适用于：SERIALIZABLE
- 原理：读加共享锁，写加排他锁
- 缺点：并发度低

**3. MySQL InnoDB 的实现**
```
READ_UNCOMMITTED  → 不加锁，直接读最新数据
READ_COMMITTED    → MVCC，每次读取创建新快照
REPEATABLE_READ   → MVCC + Next-Key Lock
SERIALIZABLE      → 读加共享锁，写加排他锁
```

---

**Q3: 为什么 Oracle 默认是 READ_COMMITTED，MySQL 默认是 REPEATABLE_READ？**

**A**：

**Oracle 选择 READ_COMMITTED**：
- OLTP 场景为主（在线事务处理）
- 需要读取最新数据
- 性能优先

**MySQL 选择 REPEATABLE_READ**：
- 兼容性考虑（老版本 MySQL 的主从复制需要）
- 提供更高的一致性保证
- InnoDB 通过 MVCC + Next-Key Lock 优化了性能

**现代应用**：
- 大部分场景使用数据库默认即可
- 根据具体需求调整

---

**推荐**：
- ✅ **一般场景**：使用数据库默认隔离级别
- ✅ **需要读最新数据**：READ_COMMITTED
- ✅ **需要可重复读（报表、统计）**：REPEATABLE_READ
- ⚠️ **金融级操作**：SERIALIZABLE（慎用，性能差）

---

**4. timeout**：超时时间（秒）
```java
@Transactional(timeout = 30)
public void save(User user) {
    // 如果事务执行超过 30 秒，自动回滚
}
```

---

**5. readOnly**：只读事务
```java
@Transactional(readOnly = true)
public User findById(Long id) {
    // 只读事务，不允许修改数据，可以优化性能
    return userMapper.selectById(id);
}
```

---

### 5.4 事务失效的场景

**1. 方法不是 public**
```java
// ❌ 事务不生效
@Transactional
private void save(User user) {
    userMapper.insert(user);
}

// ✅ 事务生效
@Transactional
public void save(User user) {
    userMapper.insert(user);
}
```

---

**2. 同类内部调用**
```java
@Service
public class UserService {
    
    public void save(User user) {
        // ❌ 事务不生效（没走代理）
        this.saveInternal(user);
    }
    
    @Transactional
    private void saveInternal(User user) {
        userMapper.insert(user);
    }
}
```

**原因**：Spring 事务是通过 AOP 代理实现的，同类内部调用不走代理

**解决方法**：
```java
@Service
public class UserService {
    
    @Autowired
    private UserService self;  // 注入自己
    
    public void save(User user) {
        // ✅ 事务生效（走代理）
        self.saveInternal(user);
    }
    
    @Transactional
    public void saveInternal(User user) {
        userMapper.insert(user);
    }
}
```

---

**3. 异常被 catch 了**
```java
@Transactional
public void save(User user) {
    try {
        userMapper.insert(user);
        int result = 1 / 0;  // 抛出异常
    } catch (Exception e) {
        // ❌ 异常被 catch，事务不回滚
        log.error("保存失败", e);
    }
}
```

**解决方法**：
```java
@Transactional
public void save(User user) {
    try {
        userMapper.insert(user);
        int result = 1 / 0;
    } catch (Exception e) {
        log.error("保存失败", e);
        // ✅ 手动回滚
        TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
        // 或者重新抛出异常
        throw e;
    }
}
```

---

**4. 异常类型不对**
```java
@Transactional  // 默认只回滚 RuntimeException
public void save(User user) throws IOException {
    userMapper.insert(user);
    throw new IOException("IO 错误");  // ❌ 不回滚
}
```

**解决方法**：
```java
@Transactional(rollbackFor = Exception.class)  // ✅ 所有异常都回滚
public void save(User user) throws IOException {
    userMapper.insert(user);
    throw new IOException("IO 错误");
}
```

---

**5. 数据库不支持事务**
```sql
-- ❌ MyISAM 不支持事务
CREATE TABLE user (...) ENGINE=MyISAM;

-- ✅ InnoDB 支持事务
CREATE TABLE user (...) ENGINE=InnoDB;
```

---

## 🎯 完整示例：用户管理系统

### 项目结构
```
com.example.demo/
├── DemoApplication.java
├── common/
│   ├── Result.java
│   └── PageResult.java
├── exception/
│   ├── BaseException.java
│   ├── ResourceNotFoundException.java
│   └── GlobalExceptionHandler.java
├── entity/
│   └── User.java
├── mapper/
│   └── UserMapper.java
├── service/
│   └── UserService.java
└── controller/
    └── UserController.java

resources/
├── application.yml
└── mapper/
    └── UserMapper.xml
```

### 完整代码已在前面章节给出

---

## 📝 实战练习

### 练习1：实现用户注册和登录

**需求**：
1. 用户注册：
   - 检查用户名是否重复
   - 密码加密（使用 BCrypt）
   - 插入用户数据
   - 使用事务

2. 用户登录：
   - 根据用户名查询用户
   - 验证密码
   - 返回用户信息（不返回密码）

**提示**：
```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-security</artifactId>
</dependency>
```

```java
// 密码加密
String encodedPassword = new BCryptPasswordEncoder().encode(rawPassword);

// 密码验证
boolean matches = new BCryptPasswordEncoder().matches(rawPassword, encodedPassword);
```

---

### 练习2：实现用户权限管理

**需求**：
1. 创建角色表（role）和用户角色关联表（user_role）
2. 为用户分配角色（事务）
3. 查询用户的所有角色
4. 查询某角色的所有用户

**提示**：
- 使用 MyBatis 的一对多、多对多映射
- 使用 `<collection>` 标签

---

### 练习3：实现批量操作

**需求**：
1. 批量插入用户
2. 批量更新用户状态
3. 批量删除用户
4. 使用事务

**提示**：
- 使用 `<foreach>` 标签
- 注意批量操作的性能

---

## ❓ 常见问题（FAQ）

### Q1: #{}  和 ${} 有什么区别？

**A**：

| 特性 | #{} | ${} |
|------|-----|-----|
| 方式 | 预编译（PreparedStatement） | 字符串拼接 |
| SQL 注入 | ✅ 防止 | ❌ 不防止 |
| 用途 | 参数值 | SQL 片段（表名、列名） |
| 示例 | `WHERE id = #{id}` | `ORDER BY ${columnName}` |

**示例**：
```xml
<!-- ✅ 推荐：使用 #{} -->
<select id="selectById">
    SELECT * FROM user WHERE id = #{id}
</select>
<!-- 生成：SELECT * FROM user WHERE id = ? -->

<!-- ⚠️ 慎用：${} 有 SQL 注入风险 -->
<select id="selectAll">
    SELECT * FROM user ORDER BY ${columnName}
</select>
<!-- 生成：SELECT * FROM user ORDER BY username -->

<!-- ❌ 危险：${} 可能被注入 -->
<select id="selectByUsername">
    SELECT * FROM user WHERE username = '${username}'
</select>
<!-- 如果 username = "admin' OR '1'='1" -->
<!-- 生成：SELECT * FROM user WHERE username = 'admin' OR '1'='1' -->
```

**总结**：
- **优先使用 `#{}`**
- **`${}` 只用于 SQL 片段（表名、列名、ORDER BY）**
- **永远不要把用户输入用 `${}`**

---

### Q2: resultMap 和 resultType 有什么区别？

**A**：

**resultType**：直接指定返回类型
```xml
<select id="selectById" resultType="User">
    SELECT * FROM user WHERE id = #{id}
</select>
```
- 简单，自动映射
- 要求字段名和属性名一致（或开启驼峰转换）

**resultMap**：自定义映射规则
```xml
<resultMap id="UserResultMap" type="User">
    <id property="id" column="id"/>
    <result property="username" column="user_name"/>
    <result property="email" column="email"/>
</resultMap>

<select id="selectById" resultMap="UserResultMap">
    SELECT * FROM user WHERE id = #{id}
</select>
```
- 灵活，可以自定义映射
- 支持复杂映射（一对多、多对多）

**何时使用**：
- **简单查询**：用 `resultType`
- **字段名和属性名不一致**：用 `resultMap`
- **复杂映射（关联查询）**：用 `resultMap`

---

### Q3: MyBatis 一级缓存和二级缓存是什么？

**A**：

**一级缓存（Session 级别）**：

- 默认开启
- 同一个 SqlSession 中，相同查询会使用缓存
- 执行 INSERT、UPDATE、DELETE 会清空缓存

```java
SqlSession session = sqlSessionFactory.openSession();
UserMapper mapper = session.getMapper(UserMapper.class);

// 第一次查询，查数据库
User user1 = mapper.selectById(1L);

// 第二次查询，使用缓存
User user2 = mapper.selectById(1L);

System.out.println(user1 == user2);  // true

session.close();
```

**二级缓存（Mapper 级别）**：
- 默认关闭，需要手动开启
- 同一个 Mapper 的不同 SqlSession 可以共享缓存
- 需要实体类实现 Serializable

```xml
<!-- 开启二级缓存 -->
<cache/>

<select id="selectById" useCache="true">
    SELECT * FROM user WHERE id = #{id}
</select>
```

**推荐**：
- 一级缓存保持开启
- 二级缓存慎用（可能导致数据不一致）
- 生产环境建议使用 Redis 缓存

---

### Q4: 如何调试 MyBatis SQL？

**A**：

**方式1：开启 SQL 日志（开发环境）**
```yaml
mybatis:
  configuration:
    log-impl: org.apache.ibatis.logging.stdout.StdOutImpl
```

**输出**：
```
==>  Preparing: SELECT * FROM user WHERE id = ?
==> Parameters: 1(Long)
<==    Columns: id, username, email
<==        Row: 1, admin, admin@example.com
<==      Total: 1
```

---

**方式2：使用 Logback 配置**
```xml
<!-- logback-spring.xml -->
<logger name="com.example.demo.mapper" level="DEBUG"/>
```

---

**方式3：使用 p6spy 打印完整 SQL**

**1. 添加依赖**：
```xml
<dependency>
    <groupId>p6spy</groupId>
    <artifactId>p6spy</artifactId>
    <version>3.9.1</version>
</dependency>
```

**2. 修改配置**：
```yaml
spring:
  datasource:
    driver-class-name: com.p6spy.engine.spy.P6SpyDriver
    url: jdbc:p6spy:mysql://localhost:3306/demo
```

**3. 添加 spy.properties**：
```properties
# 使用日志系统记录SQL
appender=com.p6spy.engine.spy.appender.Slf4JLogger
# 自定义日志打印
logMessageFormat=com.p6spy.engine.spy.appender.CustomLineFormat
customLogMessageFormat=执行时间: %(executionTime)ms | SQL: %(sql)
```

**输出**：
```
执行时间: 5ms | SQL: SELECT * FROM user WHERE id = 1
```

---

### Q5: 如何处理 MyBatis 的 N+1 问题？

**A**：

**N+1 问题**：查询 N 条记录，每条记录又查询关联数据，总共执行 N+1 次查询

**❌ 有 N+1 问题**：
```xml
<!-- 查询所有用户 -->
<select id="selectAll" resultMap="UserResultMap">
    SELECT * FROM user
</select>

<resultMap id="UserResultMap" type="User">
    <id property="id" column="id"/>
    <result property="username" column="username"/>
    <!-- 每个用户都会再查询一次角色 -->
    <collection property="roles" select="selectRolesByUserId" column="id"/>
</resultMap>

<select id="selectRolesByUserId">
    SELECT * FROM role WHERE user_id = #{id}
</select>
```

**如果有 100 个用户，会执行 101 次查询（1 + 100）！**

**✅ 解决方法1：使用 JOIN**
```xml
<select id="selectAllWithRoles" resultMap="UserResultMap">
    SELECT u.*, r.id as role_id, r.name as role_name
    FROM user u
    LEFT JOIN user_role ur ON u.id = ur.user_id
    LEFT JOIN role r ON ur.role_id = r.id
</select>

<resultMap id="UserResultMap" type="User">
    <id property="id" column="id"/>
    <result property="username" column="username"/>
    <collection property="roles" ofType="Role">
        <id property="id" column="role_id"/>
        <result property="name" column="role_name"/>
    </collection>
</resultMap>
```

**✅ 解决方法2：使用 fetchType="lazy"（懒加载）**
```xml
<!-- 开启懒加载 -->
<settings>
    <setting name="lazyLoadingEnabled" value="true"/>
</settings>

<resultMap id="UserResultMap" type="User">
    <id property="id" column="id"/>
    <result property="username" column="username"/>
    <!-- 只有访问 roles 时才查询 -->
    <collection property="roles" select="selectRolesByUserId" column="id" fetchType="lazy"/>
</resultMap>
```

---

### Q6: MyBatis 如何防止 SQL 注入？

**A**：

**使用 `#{}` 而不是 `${}`**

**✅ 安全**：
```xml
<select id="selectByUsername">
    SELECT * FROM user WHERE username = #{username}
</select>
```

**生成**：
```sql
SELECT * FROM user WHERE username = ?
```

**MyBatis 会使用 PreparedStatement，自动转义特殊字符**

---

**❌ 不安全**：
```xml
<select id="selectByUsername">
    SELECT * FROM user WHERE username = '${username}'
</select>
```

**如果 `username = "admin' OR '1'='1"`**：
```sql
SELECT * FROM user WHERE username = 'admin' OR '1'='1'
```

**所有用户都会被查出来！**

---

### Q7: 如何在 MyBatis 中执行批量操作？

**A**：

**方式1：foreach 标签**（推荐）
```xml
<insert id="batchInsert">
    INSERT INTO user (username, email)
    VALUES
    <foreach collection="list" item="user" separator=",">
        (#{user.username}, #{user.email})
    </foreach>
</insert>
```

**方式2：使用 BATCH 模式**
```java
// 获取 BATCH 模式的 SqlSession
SqlSession session = sqlSessionFactory.openSession(ExecutorType.BATCH);
UserMapper mapper = session.getMapper(UserMapper.class);

// 批量插入
for (User user : users) {
    mapper.insert(user);
}

// 提交
session.commit();
session.close();
```

---

### Q8: 事务传播行为 REQUIRED 和 REQUIRES_NEW 的区别？

**A**：

**REQUIRED（默认）**：
```java
@Transactional
public void methodA() {
    // 开启事务 A
    userMapper.insert(user1);
    methodB();  // 加入事务 A
}

@Transactional(propagation = Propagation.REQUIRED)
public void methodB() {
    userMapper.insert(user2);
}

// 如果 methodB 失败，user1 和 user2 都不会插入
```

**REQUIRES_NEW**：
```java
@Transactional
public void methodA() {
    // 开启事务 A
    userMapper.insert(user1);
    methodB();  // 挂起事务 A，开启新事务 B
}

@Transactional(propagation = Propagation.REQUIRES_NEW)
public void methodB() {
    userMapper.insert(user2);
}

// 如果 methodB 失败，user1 仍然会插入，user2 不会插入
```

**使用场景**：
- **REQUIRED**：业务操作需要整体成功或失败
- **REQUIRES_NEW**：日志记录、审计，即使主业务失败也要保存

---

### Q9: 如何优化 MyBatis 性能？

**A**：

**1. 使用连接池**（HikariCP）
```yaml
spring:
  datasource:
    hikari:
      minimum-idle: 5
      maximum-pool-size: 20
```

**2. 使用批量操作**
```xml
<!-- 批量插入 -->
<insert id="batchInsert">
    INSERT INTO user (username, email)
    VALUES
    <foreach collection="list" item="user" separator=",">
        (#{user.username}, #{user.email})
    </foreach>
</insert>
```

**3. 避免 N+1 问题**
```xml
<!-- 使用 JOIN 而不是嵌套查询 -->
<select id="selectAllWithRoles">
    SELECT u.*, r.id as role_id, r.name as role_name
    FROM user u
    LEFT JOIN role r ON u.id = r.user_id
</select>
```

**4. 使用缓存**（Redis）
```java
@Service
public class UserService {
    
    @Autowired
    private RedisTemplate<String, User> redisTemplate;
    
    @Cacheable(value = "user", key = "#id")
    public User findById(Long id) {
        return userMapper.selectById(id);
    }
}
```

**5. 合理使用索引**
```sql
-- 为常查询字段添加索引
CREATE INDEX idx_username ON user(username);
CREATE INDEX idx_email ON user(email);
```

**6. 避免 SELECT ***
```xml
<!-- ❌ 不推荐 -->
<select id="selectById">
    SELECT * FROM user WHERE id = #{id}
</select>

<!-- ✅ 推荐 -->
<select id="selectById">
    SELECT id, username, email FROM user WHERE id = #{id}
</select>
```

**7. 分页查询**
```java
// 使用 PageHelper
PageHelper.startPage(page, size);
List<User> users = userMapper.selectAll();
```

---

### Q10: 如何在 MyBatis 中使用枚举？

**A**：

**1. 定义枚举**：
```java
public enum UserStatus {
    DISABLED(0, "禁用"),
    ENABLED(1, "启用");
    
    private Integer code;
    private String desc;
    
    UserStatus(Integer code, String desc) {
        this.code = code;
        this.desc = desc;
    }
    
    // getter
}
```

**2. 实体类使用枚举**：
```java
@Data
public class User {
    private Long id;
    private String username;
    private UserStatus status;
}
```

**3. 配置类型处理器**：
```yaml
mybatis:
  type-handlers-package: com.example.demo.handler
```

```java
@MappedTypes(UserStatus.class)
public class UserStatusTypeHandler extends BaseTypeHandler<UserStatus> {
    
    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, UserStatus parameter, JdbcType jdbcType) throws SQLException {
        ps.setInt(i, parameter.getCode());
    }
    
    @Override
    public UserStatus getNullableResult(ResultSet rs, String columnName) throws SQLException {
        int code = rs.getInt(columnName);
        return Arrays.stream(UserStatus.values())
            .filter(status -> status.getCode() == code)
            .findFirst()
            .orElse(null);
    }
    
    // ... 其他方法
}
```

---

## 🤔 面试题

### 1. MyBatis 的工作原理是什么？

**答案**：

**执行流程**：
```
1. 读取 MyBatis 配置文件（application.yml、Mapper XML）
   ↓
2. 创建 SqlSessionFactory（单例，负责创建 SqlSession）
   ↓
3. 创建 SqlSession（线程不安全，每次请求创建新的）
   ↓
4. 通过 SqlSession 获取 Mapper 代理对象
   ↓
5. 调用 Mapper 方法
   ↓
6. 找到对应的 SQL 语句（根据 namespace + id）
   ↓
7. 执行 SQL（使用 JDBC）
   ↓
8. 映射结果集（ResultMap 或 ResultType）
   ↓
9. 返回结果
```

**核心组件**：
- **SqlSessionFactory**：创建 SqlSession
- **SqlSession**：执行 SQL 的主要接口
- **Executor**：执行器，负责执行 SQL
- **StatementHandler**：处理 SQL 语句
- **ParameterHandler**：处理参数
- **ResultSetHandler**：处理结果集
- **TypeHandler**：类型转换

---

### 2. MyBatis 和 Hibernate 的区别？

**答案**：

| 特性 | MyBatis | Hibernate |
|------|---------|----------|
| ORM 类型 | 半自动（需要写 SQL） | 全自动（自动生成 SQL） |
| SQL 控制 | 完全可控 | 不太可控 |
| 学习难度 | 简单 | 较难 |
| 复杂查询 | 方便（直接写 SQL） | 复杂（HQL 或 Criteria） |
| 性能优化 | 容易（优化 SQL） | 较难（调优配置） |
| 缓存 | 一级缓存、二级缓存 | 一级缓存、二级缓存 |
| 适用场景 | 复杂业务、需要优化 | 简单 CRUD |
| 国内流行度 | 非常高 | 一般 |

**总结**：
- MyBatis 更灵活，适合国内项目
- Hibernate 更规范，适合简单 CRUD

---

### 3. MyBatis 的一级缓存和二级缓存？

**答案**：

**一级缓存（Session 级别）**：
- **作用域**：SqlSession
- **生命周期**：SqlSession 创建到关闭
- **默认**：开启
- **清空时机**：执行 INSERT、UPDATE、DELETE 或手动调用 clearCache()

**二级缓存（Mapper 级别）**：
- **作用域**：Mapper（namespace）
- **生命周期**：应用运行期间
- **默认**：关闭
- **开启方式**：在 Mapper XML 中添加 `<cache/>`
- **要求**：实体类实现 Serializable

**缓存查询顺序**：
```
1. 查询二级缓存
2. 如果未命中，查询一级缓存
3. 如果未命中，查询数据库
4. 结果放入一级缓存
5. SqlSession 提交后，结果放入二级缓存
```

**注意**：
- 二级缓存可能导致数据不一致
- 生产环境建议使用 Redis 缓存

---

### 4. #{} 和 ${} 的区别？

**答案**：

| 特性 | #{} | ${} |
|------|-----|-----|
| 方式 | 预编译（PreparedStatement） | 字符串拼接（Statement） |
| SQL 注入 | ✅ 防止 | ❌ 不防止 |
| 性能 | 更好（预编译缓存） | 较差 |
| 用途 | 参数值 | SQL 片段（表名、列名） |
| 类型转换 | 自动转换 | 不转换 |

**示例**：
```xml
<!-- #{} 预编译 -->
WHERE id = #{id}
<!-- 生成：WHERE id = ? -->

<!-- ${} 字符串拼接 -->
WHERE id = ${id}
<!-- 生成：WHERE id = 1 -->
```

**总结**：
- 优先使用 `#{}`
- `${}` 只用于 SQL 片段（表名、列名、ORDER BY）

---

### 5. MyBatis 如何执行批量操作？

**答案**：

**方式1：foreach 标签（推荐）**
```xml
<insert id="batchInsert">
    INSERT INTO user (username, email)
    VALUES
    <foreach collection="list" item="user" separator=",">
        (#{user.username}, #{user.email})
    </foreach>
</insert>
```

**方式2：BATCH 模式**
```java
SqlSession session = sqlSessionFactory.openSession(ExecutorType.BATCH);
UserMapper mapper = session.getMapper(UserMapper.class);

for (User user : users) {
    mapper.insert(user);
}

session.commit();
session.close();
```

**对比**：
- **foreach**：生成一条 SQL，性能更好
- **BATCH**：多条 SQL，批量提交，适合大批量

---

### 6. @Transactional 的传播行为有哪些？

**答案**：

| 传播行为 | 说明 |
|---------|------|
| **REQUIRED**（默认） | 有事务加入，无事务创建 |
| **REQUIRES_NEW** | 总是创建新事务，挂起当前事务 |
| **SUPPORTS** | 有事务加入，无事务以非事务执行 |
| **NOT_SUPPORTED** | 以非事务执行，挂起当前事务 |
| **MANDATORY** | 必须在事务中执行，否则抛异常 |
| **NEVER** | 不能在事务中执行，否则抛异常 |
| **NESTED** | 嵌套事务 |

**常用场景**：
- **REQUIRED**：默认，大部分场景
- **REQUIRES_NEW**：日志记录、审计

---

### 7. 事务失效的场景有哪些？

**答案**：

1. **方法不是 public**
2. **同类内部调用**（不走代理）
3. **异常被 catch 了**
4. **异常类型不对**（默认只回滚 RuntimeException）
5. **数据库不支持事务**（MyISAM）

**解决方法**：
- 方法改为 public
- 通过注入的 Bean 调用
- catch 后重新抛出异常
- 配置 `rollbackFor = Exception.class`
- 使用 InnoDB 引擎

---

### 8. MyBatis 如何防止 SQL 注入？

**答案**：

**使用 `#{}` 而不是 `${}`**

**`#{}` 原理**：
```java
// 使用 PreparedStatement
String sql = "SELECT * FROM user WHERE id = ?";
PreparedStatement ps = conn.prepareStatement(sql);
ps.setLong(1, id);  // 自动转义特殊字符
```

**`${}` 问题**：
```java
// 直接字符串拼接
String sql = "SELECT * FROM user WHERE username = '" + username + "'";
// 如果 username = "admin' OR '1'='1"
// 生成：SELECT * FROM user WHERE username = 'admin' OR '1'='1'
```

**总结**：
- 优先使用 `#{}`
- `${}` 只用于不可注入的场景（表名、列名）

---

### 9. MyBatis 如何处理一对多、多对多关系？

**答案**：

**一对多（用户 → 角色）**：
```xml
<resultMap id="UserResultMap" type="User">
    <id property="id" column="id"/>
    <result property="username" column="username"/>
    <!-- collection：一对多 -->
    <collection property="roles" ofType="Role">
        <id property="id" column="role_id"/>
        <result property="name" column="role_name"/>
    </collection>
</resultMap>

<select id="selectWithRoles" resultMap="UserResultMap">
    SELECT u.*, r.id as role_id, r.name as role_name
    FROM user u
    LEFT JOIN user_role ur ON u.id = ur.user_id
    LEFT JOIN role r ON ur.role_id = r.id
    WHERE u.id = #{id}
</select>
```

**多对一（订单 → 用户）**：
```xml
<resultMap id="OrderResultMap" type="Order">
    <id property="id" column="id"/>
    <result property="orderNo" column="order_no"/>
    <!-- association：多对一 -->
    <association property="user" javaType="User">
        <id property="id" column="user_id"/>
        <result property="username" column="username"/>
    </association>
</resultMap>

<select id="selectWithUser" resultMap="OrderResultMap">
    SELECT o.*, u.id as user_id, u.username
    FROM `order` o
    LEFT JOIN user u ON o.user_id = u.id
    WHERE o.id = #{id}
</select>
```

---

### 10. 如何优化 MyBatis 性能？

**答案**：

1. **使用连接池**（HikariCP）
2. **使用批量操作**（foreach、BATCH 模式）
3. **避免 N+1 问题**（使用 JOIN）
4. **使用缓存**（Redis）
5. **合理使用索引**
6. **避免 SELECT ***
7. **分页查询**（PageHelper）
8. **懒加载**（fetchType="lazy"）
9. **SQL 优化**（EXPLAIN 分析）
10. **监控 SQL**（p6spy、慢查询日志）

---

## 📚 推荐资源

### 视频教程：
- 尚硅谷 MyBatis（B站）
- 黑马程序员 MyBatis（B站）

### 官方文档：
- MyBatis 官方文档：https://mybatis.org/mybatis-3/zh/index.html
- MyBatis Spring Boot Starter：https://mybatis.org/spring-boot-starter/mybatis-spring-boot-autoconfigure/

### 书籍：
- 《MyBatis 从入门到精通》
- 《Spring Boot 实战》

---

## ✅ 学习检查清单

- [ ] 理解 ORM 和 MyBatis 的概念
- [ ] 掌握 MyBatis 集成到 Spring Boot
- [ ] 掌握 Mapper 接口和 XML 映射
- [ ] 掌握动态 SQL（if、where、set、foreach）
- [ ] 掌握 PageHelper 分页插件
- [ ] 掌握 @Transactional 事务管理
- [ ] 理解事务传播行为
- [ ] 理解事务失效的场景
- [ ] 完成用户管理系统实战
- [ ] 完成实战练习

---

## 🎯 下一步

完成 Day 5-7 的学习后，你应该：
- ✅ 能够集成 MyBatis 到 Spring Boot
- ✅ 能够编写 Mapper 接口和 XML 映射
- ✅ 能够使用动态 SQL
- ✅ 能够使用分页插件
- ✅ 能够管理事务

**下一步**：Week 2 - Spring Boot 进阶（AOP、拦截器、缓存、定时任务、安全）

---

**加油！💪 你已经掌握了数据库访问的核心技能！**

**记住**：
> MyBatis 是半自动 ORM，SQL 可控，适合复杂业务。
> 
> 事务管理是保证数据一致性的关键，一定要理解透彻！

---

**有问题随时问我！我会一直陪伴你的学习之旅！🚀**

