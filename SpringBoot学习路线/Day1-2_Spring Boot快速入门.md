# Day 1-2: Spring Boot 快速入门

> **学习目标**：掌握 Spring Boot 项目搭建和核心概念
> 
> **预计时间**：2天（每天3小时）
> 
> **学习方式**：理论 + 实战
> 
> **适合人群**：零基础或基础薄弱的初学者

---

## 📚 学习内容

### 1. Spring Boot 项目搭建（Spring Initializr）
### 2. 依赖注入（DI）、控制反转（IOC）
### 3. 常用注解
### 4. 配置文件

---

## 🔰 前置知识：核心概念扫盲

在开始学习之前，我们先理解一些基础概念，这些概念会在后续学习中反复出现。

### 什么是 Tomcat？

**Tomcat** 是一个 **Web 服务器**（也叫 Servlet 容器）。

**通俗理解**：

- 你写的 Java Web 程序不能直接运行，需要一个"容器"来运行它
- Tomcat 就是这个容器，它负责：
  - 监听端口（比如 8080）
  - 接收浏览器的 HTTP 请求
  - 把请求交给你的 Java 代码处理
  - 把处理结果返回给浏览器
- 即：
  - 浏览器：发起 HTTP 请求（点菜）。
  - Tomcat：接收请求，解析 HTTP 协议，找到对应的 Servlet，把请求交给它（服务员传菜单）。
  - Servlet/你的代码：执行业务逻辑，生成响应（厨师做菜）。
  - Tomcat：把响应封装成 HTTP 格式，返回给浏览器（服务员端菜）。

**类比**：

> Tomcat 就像一个"餐厅服务员"：
> - 客人（浏览器）点菜（发送请求）
> - 服务员（Tomcat）把订单交给厨师（你的代码）
> - 厨师做好菜（处理请求）
> - 服务员把菜端给客人（返回响应）

**其他类似的服务器**：
- Jetty
- Undertow
- WebLogic（企业级）
- WebSphere（企业级）

---

### 什么是 WAR 包和 JAR 包？

**JAR（Java ARchive）**：
- Java 归档文件，就是把很多 `.class` 文件打包成一个文件
- 类似于 Windows 的 `.zip` 压缩包
- 用途：打包 Java 类库、工具类

**WAR（Web Application aRchive）**：

- Web 应用归档文件，专门用于打包 Web 应用
- 包含：
  - Java 类文件（`.class`）
  - JSP 页面
  - HTML、CSS、JavaScript
  - 配置文件（`web.xml`）
  - 依赖的 JAR 包

**传统部署方式**：

```
1. 开发 Web 应用
2. 打包成 WAR 文件（例如：myapp.war）
3. 安装 Tomcat 服务器
4. 把 WAR 文件放到 Tomcat 的 webapps 目录
5. 启动 Tomcat
6. 访问：http://localhost:8080/myapp
```

**Spring Boot 的方式**：
```
1. 开发 Web 应用
2. 打包成 JAR 文件（例如：myapp.jar）
3. 直接运行：java -jar myapp.jar
4. 访问：http://localhost:8080
```

**为什么 Spring Boot 不需要 WAR？**

- Spring Boot 内嵌了 Tomcat
- JAR 包里已经包含了 Tomcat
- 不需要单独安装服务器

**类比**：
> **传统方式**：你要吃火锅，需要先买火锅炉、买燃料、再买食材
> 
> **Spring Boot 方式**：你买了自热火锅，打开就能吃（自带加热包）

---

### 什么是 Bean？

**Bean** 是 Spring 框架中最核心的概念，简单来说：

**Bean = 由 Spring 容器管理的 Java 对象**

**传统方式创建对象**：
```java
// 你自己 new 对象
UserService userService = new UserService();
```

**Spring 方式创建对象**：
```java
// Spring 容器帮你创建和管理对象
@Service
public class UserService {
    // 这个类的对象就是一个 Bean
}
```

**Bean 的好处**：
1. **自动管理生命周期**：Spring 负责创建、初始化、销毁
2. **自动注入依赖**：需要什么对象，Spring 自动给你
3. **单例模式**：默认整个应用只创建一个实例（节省内存）
4. **解耦**：对象之间不直接依赖

**类比**：
> **传统方式**：你要用什么工具，自己去买、自己保管、自己维护
> 
> **Bean 方式**：你把工具放在工具箱（Spring 容器）里，需要时从工具箱拿，用完放回去

**Bean 的命名**：
- 默认：类名首字母小写（`UserService` → `userService`）
- 自定义：`@Service("myUserService")`

---

### 什么是 Maven？

**Maven** 是一个 **项目管理工具**，主要用于：

**1. 依赖管理**：
```xml
<!-- 传统方式：手动下载 jar 包，放到项目里 -->
<!-- Maven 方式：在 pom.xml 中声明，自动下载 -->
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-web</artifactId>
    <version>2.7.14</version>
</dependency>
```

**2. 项目构建**：
```bash
mvn clean      # 清理编译结果
mvn compile    # 编译代码
mvn test       # 运行测试
mvn package    # 打包（生成 jar 或 war）
mvn install    # 安装到本地仓库
```

**3. 统一项目结构**：
```
src/main/java       # Java 源代码
src/main/resources  # 配置文件、静态资源
src/test/java       # 测试代码
pom.xml             # Maven 配置文件
```

**Maven 仓库**：

- **本地仓库**：你电脑上的 jar 包缓存（默认：`~/.m2/repository`）
- **中央仓库**：Maven 官方仓库（https://repo.maven.apache.org）
- **私服**：公司内部的仓库（如 Nexus）

**类比**：

> Maven 就像"应用商店"：
> - 你需要什么库（jar 包），在 pom.xml 中"下单"
> - Maven 自动从"仓库"下载
> - 还会自动下载这个库依赖的其他库（传递依赖）

---

### 什么是注解（Annotation）？

**注解** 是 Java 提供的一种"标签"机制，用于给代码添加元数据。

**语法**：

```java
@注解名
@注解名(参数)
@注解名(参数1 = 值1, 参数2 = 值2)
```

**常见注解**：
```java
// Java 内置注解
@Override           // 表示重写父类方法
@Deprecated         // 表示已过时
@SuppressWarnings   // 抑制警告

// Spring 注解
@Component          // 标记为 Spring 组件
@Service            // 标记为业务层组件
@Controller         // 标记为控制层组件
@Autowired          // 自动注入依赖
```

**注解的作用**：
1. **给编译器看**：`@Override` 让编译器检查是否真的重写了，本质上就是一个“告诉编译器帮我检查”的标签
2. **给框架看**：`@Service` 让 Spring 知道这是一个 Bean
3. **给工具看**：`@Deprecated` 让 IDE 显示删除线

**类比**：
> 注解就像"标签"：
> - 你在文件夹上贴标签"重要"、"待办"
> - 别人（框架）看到标签，就知道该怎么处理这个文件夹

---

### 什么是依赖（Dependency）？

**依赖** = 你的代码需要用到的外部库（jar 包）

**例子**：
```java
// 你想用 MySQL 数据库
// 就需要依赖 MySQL 的驱动包
<dependency>
    <groupId>mysql</groupId>
    <artifactId>mysql-connector-java</artifactId>
    <version>8.0.33</version>
</dependency>
```

**依赖的坐标（GAV）**：
- **G**roupId：组织名（如：`org.springframework.boot`）
- **A**rtifactId：项目名（如：`spring-boot-starter-web`）
- **V**ersion：版本号（如：`2.7.14`）

**依赖的作用域（Scope）**：
```xml
<scope>compile</scope>   <!-- 默认，编译和运行都需要 -->
<scope>test</scope>      <!-- 只在测试时需要 -->
<scope>runtime</scope>   <!-- 只在运行时需要 -->
<scope>provided</scope>  <!-- 编译需要，运行时由容器提供 -->
```

---

### 什么是 Starter？

**Starter** 是 Spring Boot 提供的"一站式依赖包"。

**传统方式**：
```xml
<!-- 要用 Spring MVC，需要添加很多依赖 -->
<dependency>
    <groupId>org.springframework</groupId>
    <artifactId>spring-web</artifactId>
</dependency>
<dependency>
    <groupId>org.springframework</groupId>
    <artifactId>spring-webmvc</artifactId>
</dependency>
<dependency>
    <groupId>com.fasterxml.jackson.core</groupId>
    <artifactId>jackson-databind</artifactId>
</dependency>
<!-- 还有很多... -->
```

**Spring Boot 方式**：
```xml
<!-- 一个 starter 搞定所有 -->
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-web</artifactId>
</dependency>
```

**常用 Starter**：
- `spring-boot-starter-web`：Web 开发（包含 Tomcat、Spring MVC、JSON）
- `spring-boot-starter-data-jpa`：JPA 数据库访问
- `spring-boot-starter-redis`：Redis 缓存
- `spring-boot-starter-test`：测试框架

**类比**：
> **传统方式**：你要做蛋糕，需要分别买面粉、鸡蛋、糖、黄油...
> 
> **Starter 方式**：你买了"蛋糕套餐"，所有材料都配好了

---

### 什么是 HTTP 请求？

**HTTP（HyperText Transfer Protocol）** 是浏览器和服务器之间通信的协议。

**HTTP 请求的组成**：
```
GET /user/123 HTTP/1.1              # 请求行（方法 + 路径 + 协议版本）
Host: localhost:8080                # 请求头
Content-Type: application/json      # 请求头
                                    # 空行
{"name": "张三"}                    # 请求体（可选）
```

**常见 HTTP 方法**：
- **GET**：查询数据（如：获取用户信息）
- **POST**：创建数据（如：注册新用户）
- **PUT**：更新数据（如：修改用户信息）
- **DELETE**：删除数据（如：删除用户）

**HTTP 响应的组成**：
```
HTTP/1.1 200 OK                     # 状态行（协议版本 + 状态码 + 状态描述）
Content-Type: application/json      # 响应头
                                    # 空行
{"id": 123, "name": "张三"}         # 响应体
```

**常见状态码**：
- **2xx**：成功
  - 200 OK：请求成功
  - 201 Created：创建成功
- **3xx**：重定向
  - 301 Moved Permanently：永久重定向
  - 302 Found：临时重定向
- **4xx**：客户端错误
  - 400 Bad Request：请求参数错误
  - 401 Unauthorized：未认证
  - 403 Forbidden：无权限
  - 404 Not Found：资源不存在
- **5xx**：服务器错误
  - 500 Internal Server Error：服务器内部错误
  - 503 Service Unavailable：服务不可用

---

### 什么是 RESTful API？

**REST（Representational State Transfer）** 是一种 API 设计风格。

**核心原则**：
1. **资源（Resource）**：用 URL 表示
2. **动作（Action）**：用 HTTP 方法表示
3. **无状态（Stateless）**：每次请求都是独立的

**RESTful API 示例**：
```
GET    /users          # 获取所有用户
GET    /users/123      # 获取 ID 为 123 的用户
POST   /users          # 创建新用户
PUT    /users/123      # 更新 ID 为 123 的用户
DELETE /users/123      # 删除 ID 为 123 的用户
```

**非 RESTful 风格**（不推荐）：
```
/getUserList           # 获取所有用户
/getUserById?id=123    # 获取 ID 为 123 的用户
/createUser            # 创建新用户
/updateUser            # 更新用户
/deleteUser?id=123     # 删除用户
```

**类比**：
> RESTful API 就像"标准化的菜单"：
> - 所有餐厅都用同样的格式
> - 看到 "GET /users" 就知道是"查询用户"
> - 看到 "POST /users" 就知道是"创建用户"

---

### 什么是 JSON？

**JSON（JavaScript Object Notation）** 是一种轻量级的数据交换格式。

**语法**：
```json
{
  "name": "张三",
  "age": 25,
  "email": "zhangsan@example.com",
  "hobbies": ["读书", "旅游"],
  "address": {
    "city": "北京",
    "street": "长安街"
  }
}
```

**为什么用 JSON？**
- ✅ 易读易写
- ✅ 体积小
- ✅ 所有编程语言都支持
- ✅ 浏览器原生支持

**Java 对象 ↔ JSON**：
```java
// Java 对象
User user = new User();
user.setName("张三");
user.setAge(25);

// 转成 JSON（序列化）
String json = "{\"name\":\"张三\",\"age\":25}";

// JSON 转成 Java 对象（反序列化）
User user = objectMapper.readValue(json, User.class);
```

**Spring Boot 自动处理 JSON**：
```java
@RestController
public class UserController {
    @GetMapping("/user")
    public User getUser() {
        User user = new User();
        user.setName("张三");
        // Spring Boot 自动把 User 对象转成 JSON 返回
        return user;
    }
}
```

---

## 🚀 第一部分：Spring Boot 项目搭建

### 1.1 什么是 Spring Boot？

**Spring Boot** 是基于 Spring 框架的快速开发脚手架，它简化了 Spring 应用的配置和部署。

**通俗理解**：
- **Spring**：一个强大但复杂的 Java 框架（需要大量配置）
- **Spring Boot**：Spring 的"简化版"（自动配置，开箱即用）

**核心特点**：
- ✅ **约定优于配置**：默认配置开箱即用（不需要写一堆 XML）
- ✅ **自动配置**：根据依赖自动配置 Bean（添加了 MySQL 依赖，自动配置数据源）
- ✅ **内嵌服务器**：无需部署 WAR 包（不需要单独安装 Tomcat）
- ✅ **starter 依赖**：一站式依赖管理（一个依赖搞定所有相关库）
- ✅ **生产就绪**：提供健康检查、监控等功能（开箱即用的运维功能）

**Spring vs Spring Boot**：

| 特性 | Spring | Spring Boot |
|------|--------|-------------|
| 配置方式 | XML 或 Java 配置 | 自动配置 |
| 服务器 | 需要外部 Tomcat | 内嵌 Tomcat |
| 依赖管理 | 手动管理版本 | starter 统一管理 |
| 开发效率 | 配置繁琐 | 开箱即用 |

---

### 1.2 使用 Spring Initializr 创建项目

**方法一：使用 IDEA（推荐）**

1. 打开 IDEA，选择 `File` → `New` → `Project`
2. 选择 `Spring Initializr`
3. 配置项目信息：
   ```
   Name: demo
   Language: Java
   Type: Maven
   Group: com.example
   Artifact: demo
   Package name: com.example.demo
   Packaging: Jar
   Java: 8 或 11 或 17
   ```
4. 选择依赖：
   - `Spring Web`（Web 开发）
   - `Spring Boot DevTools`（热部署）
   - `Lombok`（简化代码）
5. 点击 `Create`

**方法二：使用官网**

1. 访问：https://start.spring.io/
2. 配置项目信息（同上）
3. 点击 `Generate` 下载项目
4. 解压后用 IDEA 打开

---

### 1.3 项目结构

```
demo/
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── com/example/demo/
│   │   │       └── DemoApplication.java    # 启动类
│   │   └── resources/
│   │       ├── application.properties      # 配置文件
│   │       ├── static/                     # 静态资源
│   │       └── templates/                  # 模板文件
│   └── test/                               # 测试代码
├── pom.xml                                 # Maven 配置
└── README.md
```

**核心文件说明**：

**1. `pom.xml`（Maven 配置）**
```xml
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0">
    <modelVersion>4.0.0</modelVersion>
    
    <!-- Spring Boot 父依赖 -->
    <parent>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-parent</artifactId>
        <version>2.7.14</version>
    </parent>
    
    <groupId>com.example</groupId>
    <artifactId>demo</artifactId>
    <version>0.0.1-SNAPSHOT</version>
    <name>demo</name>
    
    <properties>
        <java.version>8</java.version>
    </properties>
    
    <dependencies>
        <!-- Web 开发 -->
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-web</artifactId>
        </dependency>
        
        <!-- 热部署 -->
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-devtools</artifactId>
            <scope>runtime</scope>
            <optional>true</optional>
        </dependency>
        
        <!-- Lombok -->
        <dependency>
            <groupId>org.projectlombok</groupId>
            <artifactId>lombok</artifactId>
            <optional>true</optional>
        </dependency>
        
        <!-- 测试 -->
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-test</artifactId>
            <scope>test</scope>
        </dependency>
    </dependencies>
    
    <build>
        <plugins>
            <plugin>
                <groupId>org.springframework.boot</groupId>
                <artifactId>spring-boot-maven-plugin</artifactId>
            </plugin>
        </plugins>
    </build>
</project>
```

**2. `DemoApplication.java`（启动类）**
```java
package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class DemoApplication {
    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);
    }
}
```

**`@SpringBootApplication` 注解解析**：
```java
@SpringBootApplication = 
    @SpringBootConfiguration +      // 标记为配置类
    @EnableAutoConfiguration +      // 启用自动配置
    @ComponentScan                  // 组件扫描
```

---

### 1.4 第一个 Controller

创建 `HelloController.java`：

```java
package com.example.demo.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {
    
    @GetMapping("/hello")
    public String hello() {
        return "Hello, Spring Boot!";
    }
}
```

**运行项目**：
1. 运行 `DemoApplication.main()`
2. 访问：http://localhost:8080/hello
3. 看到：`Hello, Spring Boot!`

**恭喜！🎉 你的第一个 Spring Boot 应用成功运行了！**

---

## 🧩 第二部分：依赖注入（DI）与控制反转（IOC）

### 2.1 什么是 IOC（控制反转）？

**传统方式**：
```java
public class UserService {
    // 自己创建依赖对象，写死了依赖，换实现必须改源码。
    // 你没法在测试时替换掉 UserDao，只能用真实的数据库访问逻辑。
    private UserDao userDao = new UserDao();
    
    public void saveUser() {
        userDao.save();
    }
}
```
❌ **问题**：
- 对象之间耦合度高
- 难以测试（无法 mock）
- 难以替换实现

**IOC 方式**：

```java
public class UserService {
    // 由容器注入依赖对象
    // Spring 容器负责创建和管理 UserDao，并在需要时注入到 UserService。
    // UserService 不关心 UserDao 的具体实现，只要有一个符合接口的 Bean 就能用。
    // 易于替换：你可以在容器里注册不同的 UserDao 实现（比如 JdbcUserDao、MockUserDao），Spring 会帮你注入。
    // 易于测试：在单元测试里，你可以用 @MockBean 或者直接传入一个假的 UserDao，而不用改 UserService 的代码。
    @Autowired
    private UserDao userDao;//不用自己new
    
    public void saveUser() {
        userDao.save();
    }
}
```
✅ **优点**：
- 对象之间解耦
- 易于测试
- 易于替换实现

**IOC 的核心思想**：
> 对象的创建和管理权交给容器（Spring），而不是由程序员手动创建。

---

### 2.2 什么是 DI（依赖注入）？

**注入：**是把“已经被 Spring 容器创建和管理的对象（Bean）”按需“塞进”另一个对象的过程。本质是控制权的转移：对象的创建、生命周期和依赖关系由容器负责，而不是类自己 new。

**定义：**注入是“把依赖交给你”。某个类声明“我需要 UserDao”，Spring 容器在启动时创建好 UserDao，然后在合适的时机把它放到 UserService 的属性里（构造器、Setter 或字段）。

**注入的两个阶段：**

  ***1.注册/创建：***容器发现可作为 Bean 的类（比如带 @Component/@Service/@Repository 的类，或配置类里的 @Bean 方法），**实例化**并放进容器。

  ***2.注入/装配：***容器解析每个 Bean 的依赖（@Autowired、构造器参数、方法参数），找到匹配的 Bean，并“装配进去”。

#### 注册 vs 注入

- **注册（Bean 定义）：**告诉容器“这些类/方法产出的对象要被管理”。来源包括：
  - **注解扫描：**@Component/@Service/@Controller/@Repository
  - **显式定义：**@Configuration 类中的 @Bean 方法
- **注入（依赖装配）：**在某个 Bean 中标注“我需要 X”，容器把已注册的 X 的实例放进去。
  - **匹配规则：**默认按类型匹配；有多个候选时用 @Qualifier 指定名称，或 @Primary 设定默认。

**依赖注入** 是 IOC 的实现方式，Spring 通过以下方式注入依赖：

**1. 构造器注入（推荐）**
```java
@Service
public class UserService {
    private final UserDao userDao;
    
    // 构造器注入
    public UserService(UserDao userDao) {
        this.userDao = userDao;
    }
}
```
✅ **优点**：
- 依赖不可变（final）
- 保证依赖不为 null
- 便于测试

**2. Setter 注入**
```java
@Service
public class UserService {
    private UserDao userDao;
    
    // Setter 注入
    @Autowired
    public void setUserDao(UserDao userDao) {
        this.userDao = userDao;
    }
}
```

**3. 字段注入（最常用，但不推荐）**
```java
@Service
public class UserService {
    // 字段注入
    @Autowired
    private UserDao userDao;
}
```
⚠️ **缺点**：
- 无法使用 final
- 难以测试
- 隐藏了依赖关系

**最佳实践**：优先使用构造器注入！

---

### 2.3 IOC 容器

**Spring IOC 容器** 负责管理 Bean 的生命周期。

**什么是容器？**
> 容器就像一个"对象仓库"：
> - 你把对象（Bean）放进去
> - 需要时从容器里拿出来
> - 容器负责创建、管理、销毁对象

**两种容器**：
1. **BeanFactory**：基础容器，延迟加载（用到时才创建 Bean）
2. **ApplicationContext**：高级容器，立即加载（启动时就创建所有 Bean，推荐）

**ApplicationContext 的实现**：
- `ClassPathXmlApplicationContext`：从 classpath 加载 XML 配置
- `FileSystemXmlApplicationContext`：从文件系统加载 XML 配置
- `AnnotationConfigApplicationContext`：从 Java 配置类加载
- `WebApplicationContext`：Web 应用专用

**Spring Boot 中的容器**：
```java
@SpringBootApplication//即启动类
public class DemoApplication {
    public static void main(String[] args) {
        // 启动 Spring Boot，返回 ApplicationContext（容器）
        ApplicationContext context = SpringApplication.run(DemoApplication.class, args);
        
        // 从容器中获取 Bean（就像从仓库里拿东西）
        UserService userService = context.getBean(UserService.class);
        userService.saveUser();
    }
}
```

**容器的工作流程**：
```
1. 启动 Spring Boot
   ↓
2. 扫描所有带 @Component、@Service 等注解的类
   ↓
3. 创建这些类的对象（Bean）
   ↓
4. 把 Bean 放到容器（ApplicationContext）里
   ↓
5. 处理 @Autowired，注入依赖
   ↓
6. 应用运行，随时可以从容器获取 Bean
   ↓
7. 应用关闭，容器销毁所有 Bean
```

---

### 2.4 Bean 的生命周期

```
1. 实例化（Instantiation）
   ↓
2. 属性赋值（Populate）
   ↓
3. 初始化（Initialization）
   - BeanNameAware.setBeanName()
   - BeanFactoryAware.setBeanFactory()
   - ApplicationContextAware.setApplicationContext()
   - @PostConstruct
   - InitializingBean.afterPropertiesSet()
   - init-method
   ↓
4. 使用（In Use）
   ↓
5. 销毁（Destruction）
   - @PreDestroy
   - DisposableBean.destroy()
   - destroy-method
```

**示例代码**：
```java
@Component
public class MyBean {
    
    public MyBean() {
        System.out.println("1. 构造器执行");
    }
    
    @PostConstruct//手工定义的初始化逻辑，执行时由Spring自动控制
    public void init() {
        System.out.println("2. @PostConstruct 执行");
    }
    
    @PreDestroy
    public void destroy() {
        System.out.println("3. @PreDestroy 执行");
    }
}
```

---

## 📝 第三部分：常用注解

### 3.1 组件注解

**1. `@Component`（通用组件）**
```java
@Component
public class MyComponent {
    public void doSomething() {
        System.out.println("Component");
    }
}
```
- 标记一个类为 Spring 组件
- 由 Spring 容器管理

**2. `@Service`（业务层）**
```java
@Service
public class UserService {
    public void saveUser() {
        System.out.println("Save user");
    }
}
```
- 标记业务层组件
- 本质上和 `@Component` 一样，但语义更清晰

**3. `@Repository`（数据访问层）**
```java
@Repository
public class UserDao {
    public void save() {
        System.out.println("Save to database");
    }
}
```
- 标记数据访问层组件
- 会自动转换数据库异常为 Spring 异常

**4. `@Controller`（控制层）**
```java
@Controller
public class UserController {
    @GetMapping("/user")
    @ResponseBody
    public String getUser() {
        return "User";
    }
}
```
- 标记控制层组件
- 通常配合 `@ResponseBody` 使用

**5. `@RestController`（RESTful 控制层）**
```java
@RestController
public class UserController {
    @GetMapping("/user")
    public String getUser() {
        return "User";
    }
}
```
- `@RestController` = `@Controller` + `@ResponseBody`
- 所有方法默认返回 JSON

**注解层次关系**：
```
@Component (通用)
    ├── @Service (业务层)
    ├── @Repository (数据访问层)
    └── @Controller (控制层)
            └── @RestController (RESTful 控制层)
```

---

### 3.2 依赖注入注解

**1. `@Autowired`（Spring 注解）**
```java
@Service
public class UserService {
    @Autowired
    private UserDao userDao;
}
```
- 按类型（byType）自动注入
- 可以用在字段、构造器、Setter 上
- 默认必须注入（required=true）

**2. `@Resource`（JDK 注解）**
```java
@Service
public class UserService {
    @Resource
    private UserDao userDao;
}
```
- 默认按名称（byName）注入
- 找不到再按类型（byType）注入

**3. `@Qualifier`（指定注入的 Bean）**
```java
@Service
public class UserService {
    @Autowired
    @Qualifier("userDaoImpl")
    private UserDao userDao;
}
```
- 配合 `@Autowired` 使用
- 当有多个实现时，指定注入哪个

**4. `@Primary`（设置默认 Bean）**
```java
@Repository
@Primary//（通常情况下只能有一个，多个会报错）
public class UserDaoImpl implements UserDao {
    // ...
}
```
- 当有多个实现时，优先注入标记了 `@Primary` 的 Bean

---

### 3.3 配置注解

**1. `@Configuration`（配置类）**
```java
@Configuration
public class AppConfig {
    @Bean
    public UserService userService() {
        return new UserService();
    }
}
```
- 标记配置类
- 替代 XML 配置，比方说
- <beans>
    -<bean id="userService" class="com.example.UserService"/>
    -</beans>

**2. `@Bean`（定义 Bean）**
```java
@Configuration
public class DataSourceConfig {
    @Bean
    public DataSource dataSource() {
        BasicDataSource ds = new BasicDataSource();
        ds.setUrl("jdbc:mysql://localhost:3306/test");
        ds.setUsername("root");
        ds.setPassword("123456");
        return ds;
    }
}
```
- 在配置类中定义 Bean
- 方法名默认为 Bean 的名称
- 好处：
  - **类型安全**：IDE 能检查方法返回类型，避免拼写错误。
  - **更灵活**：可以在方法里写逻辑，比如根据条件返回不同实现。
  - **统一风格**：不用在 XML 和 Java 之间来回切换，所有配置都在代码里。

**3. `@ComponentScan`（组件扫描）**
```java
@Configuration
@ComponentScan("com.example.demo")
public class AppConfig {
}
```
- 指定扫描的包路径
- Spring Boot 默认扫描启动类所在包及子包

**4. `@Value`（注入配置值）**
```java
@Component
public class MyComponent {
    @Value("${server.port}")
    private int port;
}
```
- 从配置文件注入值
- 支持 SpEL 表达式

---

### 3.4 完整示例

**目录结构**：
```
com.example.demo/
├── DemoApplication.java          # 启动类
├── controller/                   # 控制层（接收请求）
│   └── UserController.java
├── service/                      # 业务层（处理业务逻辑）
│   ├── UserService.java
│   └── impl/
│       └── UserServiceImpl.java
├── dao/                          # 数据访问层（操作数据库）
│   ├── UserDao.java
│   └── impl/
│       └── UserDaoImpl.java
└── entity/                       # 实体类（对应数据库表）
    └── User.java
```

**三层架构说明**：
```
浏览器
  ↓ HTTP 请求
Controller（控制层）
  - 接收请求
  - 调用 Service
  - 返回响应
  ↓
Service（业务层）
  - 处理业务逻辑
  - 调用 Dao
  - 事务管理
  ↓
Dao（数据访问层）
  - 操作数据库
  - 增删改查
  ↓
数据库
```

**为什么要分层？**
- ✅ **职责清晰**：每层只做自己的事
- ✅ **易于维护**：修改数据库不影响业务逻辑
- ✅ **易于测试**：可以单独测试每一层
- ✅ **易于复用**：Service 可以被多个 Controller 调用

**1. Entity（实体类）**
```java
package com.example.demo.entity;

import lombok.Data;

// @Data 是 Lombok 注解，自动生成 getter、setter、toString、equals、hashCode
// 等价于手写：
// public Long getId() { return id; }
// public void setId(Long id) { this.id = id; }
// ...
@Data
public class User {
    private Long id;           // 用户ID
    private String username;   // 用户名
    private String email;      // 邮箱
}
```

**Lombok 注解说明**：
- `@Data`：自动生成 getter、setter、toString、equals、hashCode
- `@Getter`：只生成 getter
- `@Setter`：只生成 setter
- `@NoArgsConstructor`：生成无参构造器
- `@AllArgsConstructor`：生成全参构造器

**如果不用 Lombok，需要手写**：
```java
public class User {
    private Long id;
    private String username;
    private String email;
    
    // 无参构造器
    public User() {}
    
    // 全参构造器
    public User(Long id, String username, String email) {
        this.id = id;
        this.username = username;
        this.email = email;
    }
    
    // getter 和 setter
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    
    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }
    
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    
    // toString
    @Override
    public String toString() {
        return "User{id=" + id + ", username='" + username + "', email='" + email + "'}";
    }
    
    // equals 和 hashCode
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(id, user.id);
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
```

**可以看到，Lombok 帮我们节省了大量重复代码！**

**2. Dao（数据访问层）**

**接口定义**：
```java
package com.example.demo.dao;

import com.example.demo.entity.User;

// Dao 接口：定义数据访问的方法（增删改查）
public interface UserDao {
    void save(User user);      // 保存用户
    User findById(Long id);    // 根据 ID 查询用户
}
```

**为什么要定义接口？**
- ✅ **面向接口编程**：调用方只需要知道接口，不需要知道具体实现
- ✅ **易于替换实现**：可以有多个实现（MySQL、MongoDB、内存）
- ✅ **易于测试**：可以 mock 接口

**实现类**：
```java
package com.example.demo.dao.impl;

import com.example.demo.dao.UserDao;
import com.example.demo.entity.User;
import org.springframework.stereotype.Repository;

// @Repository：标记为数据访问层组件，Spring 会创建这个类的 Bean
@Repository
public class UserDaoImpl implements UserDao {
    
    @Override
    public void save(User user) {
        // 这里应该是数据库操作，为了演示，我们只打印
        // 实际项目中，这里会用 JdbcTemplate、MyBatis 或 JPA
        System.out.println("保存用户：" + user.getUsername());
        // 例如：jdbcTemplate.update("INSERT INTO user VALUES (?, ?)", user.getId(), user.getUsername());
    }
    
    @Override
    public User findById(Long id) {
        // 这里应该是数据库查询，为了演示，我们返回模拟数据
        // 实际项目中：User user = jdbcTemplate.queryForObject("SELECT * FROM user WHERE id = ?", new BeanPropertyRowMapper<>(User.class), id);
        User user = new User();
        user.setId(id);
        user.setUsername("张三");
        user.setEmail("zhangsan@example.com");
        return user;
    }
}
```

**实际项目中的 Dao**：
```java
// 使用 MyBatis
@Mapper
public interface UserDao {
    @Insert("INSERT INTO user(username, email) VALUES(#{username}, #{email})")
    void save(User user);
    
    @Select("SELECT * FROM user WHERE id = #{id}")
    User findById(Long id);
}

// 使用 JPA
public interface UserDao extends JpaRepository<User, Long> {
    // 不需要写实现，Spring Data JPA 自动生成
}
```

**3. Service（业务层）**

**接口定义**：
```java
package com.example.demo.service;

import com.example.demo.entity.User;

// Service 接口：定义业务方法
public interface UserService {
    void saveUser(User user);      // 保存用户
    User getUserById(Long id);     // 获取用户
}
```

**实现类**：
```java
package com.example.demo.service.impl;

import com.example.demo.dao.UserDao;
import com.example.demo.entity.User;
import com.example.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

// @Service：标记为业务层组件，Spring 会创建这个类的 Bean
@Service
public class UserServiceImpl implements UserService {
    
    // 依赖注入：Service 需要用到 Dao
    private final UserDao userDao;
    
    // 构造器注入（推荐）
    // 当只有一个构造器时，@Autowired 可以省略
    @Autowired
    public UserServiceImpl(UserDao userDao) {
        this.userDao = userDao;
    }
    
    @Override
    public void saveUser(User user) {
        // 这里可以添加业务逻辑
        // 例如：
        // 1. 校验用户名是否重复
        // 2. 加密密码
        // 3. 发送欢迎邮件
        // 4. 记录日志
        
        // 调用 Dao 保存用户
        userDao.save(user);
    }
    
    @Override
    public User getUserById(Long id) {
        // 这里可以添加业务逻辑
        // 例如：
        // 1. 检查用户是否存在
        // 2. 检查用户是否被禁用
        // 3. 记录访问日志
        
        // 调用 Dao 查询用户
        return userDao.findById(id);
    }
}
```

**Service 层的职责**：
1. **业务逻辑处理**：
   - 数据校验（用户名是否重复）
   - 数据转换（密码加密）
   - 业务规则（VIP 用户打折）

2. **事务管理**：
   ```java
   @Transactional  // 这个方法中的所有数据库操作要么全成功，要么全失败
   public void transferMoney(Long fromId, Long toId, BigDecimal amount) {
       accountDao.deduct(fromId, amount);  // 扣款
       accountDao.add(toId, amount);       // 加款
       // 如果中间出错，两个操作都会回滚
   }
   ```

3. **调用其他 Service**：
   ```java
   @Service
   public class OrderService {
       @Autowired
       private UserService userService;      // 调用用户服务
       @Autowired
       private ProductService productService; // 调用商品服务
       
       public void createOrder(Long userId, Long productId) {
           User user = userService.getUserById(userId);
           Product product = productService.getProductById(productId);
           // 创建订单...
       }
   }
   ```

**为什么不直接在 Controller 里调用 Dao？**
- ❌ **违反单一职责原则**：Controller 应该只负责接收请求和返回响应
- ❌ **业务逻辑分散**：如果多个 Controller 都需要保存用户，业务逻辑会重复
- ❌ **难以测试**：无法单独测试业务逻辑
- ❌ **难以维护**：修改业务逻辑需要改很多地方

**4. Controller（控制层）**
```java
package com.example.demo.controller;

import com.example.demo.entity.User;
import com.example.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

// @RestController = @Controller + @ResponseBody
// 表示这个类是一个控制器，所有方法返回的都是数据（JSON），而不是页面
@RestController
// @RequestMapping：指定这个 Controller 的基础路径
// 所有方法的路径都会加上 /user 前缀
@RequestMapping("/user")
public class UserController {
    
    // 依赖注入：Controller 需要用到 Service
    private final UserService userService;
    
    // 构造器注入
    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }
    
    // @GetMapping：处理 GET 请求
    // /{id}：路径参数，例如 /user/123，id = 123
    // @PathVariable：把路径参数绑定到方法参数
    @GetMapping("/{id}")
    public User getUser(@PathVariable Long id) {
        // 调用 Service 获取用户
        return userService.getUserById(id);
        // Spring Boot 会自动把 User 对象转成 JSON 返回
    }
    
    // @PostMapping：处理 POST 请求
    // @RequestBody：把请求体（JSON）转成 User 对象
    @PostMapping
    public String saveUser(@RequestBody User user) {
        // 调用 Service 保存用户
        userService.saveUser(user);
        return "保存成功";
    }
}
```

**注解详解**：

**1. `@RestController` vs `@Controller`**：
```java
// @Controller：返回页面（HTML）
@Controller
public class PageController {
    @GetMapping("/index")
    public String index() {
        return "index";  // 返回 index.html 页面
    }
}

// @RestController：返回数据（JSON）
@RestController
public class ApiController {
    @GetMapping("/user")
    public User getUser() {
        return new User();  // 返回 JSON 数据
    }
}
```

**2. `@RequestMapping` 及其变体**：
```java
@RequestMapping(value = "/user", method = RequestMethod.GET)   // 原始写法
@GetMapping("/user")                                           // 简化写法（推荐）

@RequestMapping(value = "/user", method = RequestMethod.POST)
@PostMapping("/user")

@RequestMapping(value = "/user", method = RequestMethod.PUT)
@PutMapping("/user")

@RequestMapping(value = "/user", method = RequestMethod.DELETE)
@DeleteMapping("/user")
```

**3. `@PathVariable`（路径参数）**：
```java
// URL: /user/123
@GetMapping("/user/{id}")
public User getUser(@PathVariable Long id) {
    // id = 123
}

// URL: /user/123/orders/456
@GetMapping("/user/{userId}/orders/{orderId}")
public Order getOrder(@PathVariable Long userId, @PathVariable Long orderId) {
    // userId = 123, orderId = 456
}
```

**4. `@RequestParam`（查询参数）**：
```java
// URL: /user?id=123&name=张三
@GetMapping("/user")
public User getUser(@RequestParam Long id, @RequestParam String name) {
    // id = 123, name = "张三"
}

// 可选参数（带默认值）
@GetMapping("/user")
public List<User> getUsers(
    @RequestParam(defaultValue = "1") int page,
    @RequestParam(defaultValue = "10") int size
) {
    // 如果不传参数，page = 1, size = 10
}
```

**5. `@RequestBody`（请求体）**：
```java
// POST /user
// Content-Type: application/json
// {"username": "张三", "email": "zhangsan@example.com"}
@PostMapping("/user")
public String saveUser(@RequestBody User user) {
    // user.getUsername() = "张三"
    // user.getEmail() = "zhangsan@example.com"
}
```

**完整的请求处理流程**：
```
1. 浏览器发送请求：GET http://localhost:8080/user/123
   ↓
2. Tomcat 接收请求
   ↓
3. Spring MVC 根据 URL 找到对应的 Controller 方法
   ↓
4. 调用 UserController.getUser(123)
   ↓
5. Controller 调用 UserService.getUserById(123)
   ↓
6. Service 调用 UserDao.findById(123)
   ↓
7. Dao 从数据库查询用户
   ↓
8. 返回 User 对象
   ↓
9. Spring Boot 把 User 对象转成 JSON
   ↓
10. Tomcat 把 JSON 返回给浏览器
```

**测试**：

**方式1：使用浏览器**
```
直接在浏览器输入：http://localhost:8080/user/1
```

**方式2：使用 curl（命令行工具）**
```bash
# 查询用户（GET 请求）
curl http://localhost:8080/user/1

# 保存用户（POST 请求）
curl -X POST http://localhost:8080/user \
  -H "Content-Type: application/json" \
  -d '{"username":"李四","email":"lisi@example.com"}'
```

**方式3：使用 Postman（图形化工具，推荐）**
```
1. 下载安装 Postman
2. 新建请求
3. 选择 GET 方法
4. 输入 URL：http://localhost:8080/user/1
5. 点击 Send
```

**方式4：使用 IDEA 自带的 HTTP Client**
```http
### 查询用户
GET http://localhost:8080/user/1

### 保存用户
POST http://localhost:8080/user
Content-Type: application/json

{
  "username": "李四",
  "email": "lisi@example.com"
}
```

---

## ⚙️ 第四部分：配置文件

### 4.1 application.properties vs application.yml

**Spring Boot 支持两种配置文件格式**：

**1. application.properties（传统格式）**
```properties
# 服务器配置
server.port=8080
server.servlet.context-path=/api

# 数据源配置
spring.datasource.url=jdbc:mysql://localhost:3306/demo
spring.datasource.username=root
spring.datasource.password=123456
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver

# 日志配置
logging.level.root=INFO
logging.level.com.example.demo=DEBUG
```

**2. application.yml（推荐，更简洁）**
```yaml
# 服务器配置
server:
  port: 8080
  servlet:
    context-path: /api

# 数据源配置
spring:
  datasource:
    url: jdbc:mysql://localhost:3306/demo
    username: root
    password: 123456
    driver-class-name: com.mysql.cj.jdbc.Driver

# 日志配置
logging:
  level:
    root: INFO
    com.example.demo: DEBUG
```

**对比**：

| 特性 | properties | yml |
|------|-----------|-----|
| 格式 | key=value | key: value |
| 层级 | 用 `.` 分隔 | 用缩进表示 |
| 可读性 | 一般 | 好 |
| 复杂配置 | 冗长 | 简洁 |
| 推荐度 | ⭐⭐⭐ | ⭐⭐⭐⭐⭐ |

**注意**：
- ✅ YAML 使用缩进表示层级（2个空格或4个空格）
- ❌ YAML 不能使用 Tab 缩进
- ✅ 冒号后面必须有空格：`key: value`

---

### 4.2 常用配置

**1. 服务器配置**
```yaml
server:
  port: 8080                    # 端口号
  servlet:
    context-path: /api          # 上下文路径
  tomcat:
    max-threads: 200            # 最大线程数
    max-connections: 10000      # 最大连接数
```

**2. 应用配置**
```yaml
spring:
  application:
    name: demo                  # 应用名称
  profiles:
    active: dev                 # 激活的环境
```

**3. 日志配置**
```yaml
logging:
  level:
    root: INFO                  # 根日志级别
    com.example.demo: DEBUG     # 指定包的日志级别
  file:
    name: logs/app.log          # 日志文件路径
  pattern:
    console: "%d{yyyy-MM-dd HH:mm:ss} [%thread] %-5level %logger{36} - %msg%n"
```

**4. 自定义配置**
```yaml
# 自定义配置
my:
  name: 张三
  age: 25
  email: zhangsan@example.com
```

**读取自定义配置**：
```java
@Component
public class MyConfig {
    
    @Value("${my.name}")
    private String name;
    
    @Value("${my.age}")
    private int age;
    
    @Value("${my.email}")
    private String email;
}
```

---

### 4.3 多环境配置

**创建多个配置文件**：
```
resources/
├── application.yml              # 主配置
├── application-dev.yml          # 开发环境
├── application-test.yml         # 测试环境
└── application-prod.yml         # 生产环境
```

**application.yml（主配置）**
```yaml
spring:
  profiles:
    active: dev                  # 激活开发环境
```

**application-dev.yml（开发环境）**
```yaml
server:
  port: 8080

spring:
  datasource:
    url: jdbc:mysql://localhost:3306/demo_dev
    username: root
    password: 123456

logging:
  level:
    root: DEBUG
```

**application-prod.yml（生产环境）**
```yaml
server:
  port: 80

spring:
  datasource:
    url: jdbc:mysql://prod-server:3306/demo_prod
    username: admin
    password: prod_password

logging:
  level:
    root: INFO
```

**切换环境**：
```bash
# 方式1：修改 application.yml
spring.profiles.active=prod

# 方式2：启动时指定
java -jar demo.jar --spring.profiles.active=prod

# 方式3：环境变量
export SPRING_PROFILES_ACTIVE=prod
```

---

### 4.4 配置文件优先级

**Spring Boot 配置文件加载顺序（优先级从高到低）**：

1. `命令行参数`
2. `java:comp/env` 的 JNDI 属性
3. `System.getProperties()` 系统属性
4. `操作系统环境变量`
5. `jar 包外的 application-{profile}.properties/yml`
6. `jar 包内的 application-{profile}.properties/yml`
7. `jar 包外的 application.properties/yml`
8. `jar 包内的 application.properties/yml`
9. `@Configuration 类上的 @PropertySource`
10. `SpringApplication.setDefaultProperties()` 默认属性

**示例**：
```bash
# 命令行参数优先级最高
java -jar demo.jar --server.port=9090
```

---

## 🎯 学习要点总结

### 1. 理解 IOC 容器的概念

**核心思想**：
- 对象的创建和管理权交给 Spring 容器
- 程序员只需要声明依赖，由容器负责注入

**好处**：
- ✅ 解耦：对象之间不直接依赖
- ✅ 易测试：可以轻松 mock 依赖
- ✅ 易维护：修改实现不影响调用方

**记住**：
> **IOC（控制反转）** = 对象创建的控制权反转给容器
> 
> **DI（依赖注入）** = IOC 的实现方式

---

### 2. 掌握 Bean 的创建和注入

**Bean 的创建方式**：
1. `@Component` 及其衍生注解（`@Service`, `@Repository`, `@Controller`）
2. `@Configuration` + `@Bean`

**Bean 的注入方式**：
1. **构造器注入**（推荐）
2. Setter 注入
3. 字段注入

**最佳实践**：
```java
@Service
public class UserService {
    // 1. 使用 final 保证不可变
    private final UserDao userDao;
    
    // 2. 构造器注入（只有一个构造器时，@Autowired 可省略）
    public UserService(UserDao userDao) {
        this.userDao = userDao;
    }
}
```

---

### 3. 学会使用 YAML 配置文件

**YAML 语法要点**：
```yaml
# 1. 键值对（冒号后必须有空格）
key: value

# 2. 层级关系（使用缩进）
parent:
  child: value

# 3. 数组
list:
  - item1
  - item2
  - item3

# 或者
list: [item1, item2, item3]

# 4. 对象
object:
  name: 张三
  age: 25

# 5. 多行字符串
description: |
  这是第一行
  这是第二行
  这是第三行
```

**常见错误**：
```yaml
# ❌ 错误：冒号后没有空格
key:value

# ✅ 正确
key: value

# ❌ 错误：使用 Tab 缩进
server:
	port: 8080

# ✅ 正确：使用空格缩进
server:
  port: 8080
```

---

## 📝 实战练习

### 练习1：创建一个简单的用户管理系统

**需求**：
1. 创建 User 实体类（包含 id、username、email）
2. 创建 UserDao、UserService、UserController
3. 实现用户的增删改查（模拟，不连数据库，用 Map 存储）
4. 使用构造器注入
5. 使用 YAML 配置文件

**提示**：
- 使用 `@RestController` 创建 RESTful API
- 使用 `@GetMapping`, `@PostMapping`, `@PutMapping`, `@DeleteMapping`
- 使用 `@PathVariable` 接收路径参数
- 使用 `@RequestBody` 接收请求体

**参考实现**：

**UserDao.java**：
```java
@Repository
public class UserDao {
    // 用 Map 模拟数据库
    private Map<Long, User> database = new HashMap<>();
    private AtomicLong idGenerator = new AtomicLong(1);
    
    public User save(User user) {
        if (user.getId() == null) {
            user.setId(idGenerator.getAndIncrement());
        }
        database.put(user.getId(), user);
        return user;
    }
    
    public User findById(Long id) {
        return database.get(id);
    }
    
    public List<User> findAll() {
        return new ArrayList<>(database.values());
    }
    
    public void deleteById(Long id) {
        database.remove(id);
    }
}
```

**API 设计**：
```
GET    /users          # 获取所有用户
GET    /users/{id}     # 获取指定用户
POST   /users          # 创建用户
PUT    /users/{id}     # 更新用户
DELETE /users/{id}     # 删除用户
```

---

### 练习2：配置多环境

**需求**：
1. 创建 dev、test、prod 三个环境的配置文件
2. dev 环境端口 8080，日志级别 DEBUG
3. test 环境端口 8081，日志级别 INFO
4. prod 环境端口 80，日志级别 WARN
5. 尝试切换不同环境启动

---

### 练习3：自定义配置

**需求**：
1. 在 application.yml 中添加自定义配置：
   ```yaml
   app:
     name: 用户管理系统
     version: 1.0.0
     author: 张三
   ```
2. 创建一个配置类读取这些值
3. 在 Controller 中使用这些配置

---

## ❓ 常见问题（FAQ）

### Q1: 为什么要用 Spring Boot，不能直接写 Java 代码吗？

**A**: 可以，但是：
- ❌ **传统方式**：你需要手动创建对象、管理依赖、配置服务器、处理 HTTP 请求...
- ✅ **Spring Boot**：这些都自动帮你做好了，你只需要写业务逻辑

**类比**：
> 你可以自己造车，但买一辆成品车更方便

---

### Q2: Bean 和普通对象有什么区别？

**A**：
| 特性 | 普通对象 | Bean |
|------|---------|------|
| 创建方式 | `new User()` | Spring 容器创建 |
| 管理方式 | 手动管理 | Spring 自动管理 |
| 生命周期 | 手动控制 | Spring 控制 |
| 依赖注入 | 手动传入 | 自动注入 |
| 作用域 | 每次 new 都是新对象 | 默认单例 |

**例子**：
```java
// 普通对象
UserService service1 = new UserService();
UserService service2 = new UserService();
// service1 和 service2 是两个不同的对象

// Bean
@Autowired
private UserService service1;
@Autowired
private UserService service2;
// service1 和 service2 是同一个对象（单例）
```

---

### Q3: 为什么要用接口？直接写实现类不行吗？

**A**: 可以，但是用接口有很多好处：

**1. 易于替换实现**：
```java
// 接口
public interface UserDao {
    void save(User user);
}

// MySQL 实现
@Repository("mysqlDao")
public class MySQLUserDao implements UserDao {
    public void save(User user) {
        // 保存到 MySQL
    }
}

// MongoDB 实现
@Repository("mongoDao")
public class MongoUserDao implements UserDao {
    public void save(User user) {
        // 保存到 MongoDB
    }
}

// Service 只依赖接口，不关心具体实现
@Service
public class UserService {
    @Autowired
    @Qualifier("mysqlDao")  // 可以轻松切换实现
    private UserDao userDao;
}
```

**2. 易于测试**：
```java
// 测试时可以 mock 接口
@Test
public void testSaveUser() {
    UserDao mockDao = Mockito.mock(UserDao.class);
    UserService service = new UserService(mockDao);
    // 测试...
}
```

**3. 符合设计原则**：
- 依赖倒置原则（DIP）：依赖抽象，不依赖具体
- 开闭原则（OCP）：对扩展开放，对修改关闭

---

### Q4: @Autowired 是怎么工作的？

**A**: Spring 容器启动时：

```
1. 扫描所有类，找到带 @Component、@Service 等注解的类
   ↓
2. 创建这些类的对象（Bean），放到容器里
   ↓
3. 扫描所有 Bean，找到 @Autowired 注解
   ↓
4. 根据类型从容器里找到对应的 Bean
   ↓
5. 注入到 @Autowired 标记的字段/构造器/Setter
```

**例子**：
```java
@Service
public class UserService {
    @Autowired
    private UserDao userDao;  // Spring 会自动注入 UserDao 的 Bean
}

// 等价于手动注入：
UserService service = new UserService();
UserDao dao = container.getBean(UserDao.class);
service.setUserDao(dao);
```

---

### Q5: 为什么构造器注入比字段注入好？

**A**：

**字段注入的问题**：
```java
@Service
public class UserService {
    @Autowired
    private UserDao userDao;  // 不能用 final
    
    // 测试时无法注入
    // 依赖关系不明显
}
```

**构造器注入的优点**：
```java
@Service
public class UserService {
    private final UserDao userDao;  // 可以用 final，保证不可变
    
    public UserService(UserDao userDao) {
        this.userDao = userDao;
    }
    
    // 测试时可以直接 new
    // UserService service = new UserService(mockDao);
    
    // 依赖关系一目了然
}
```

---

### Q6: application.yml 和 application.properties 可以同时存在吗？

**A**: 可以，但不推荐。

- 如果同时存在，`application.properties` 优先级更高
- 建议只用一种，推荐用 `application.yml`（更简洁）

---

### Q7: 启动时报错 "Consider defining a bean of type..."，怎么办？

**A**: 这个错误表示 Spring 找不到需要注入的 Bean。

**可能原因**：
1. **忘记加注解**：
   ```java
   // ❌ 没有 @Service 注解
   public class UserService {
   }
   
   // ✅ 加上注解
   @Service
   public class UserService {
   }
   ```

2. **类不在扫描范围内**：
   ```java
   // 启动类在 com.example.demo
   // UserService 在 com.example.other
   // Spring 默认只扫描启动类所在包及子包
   
   // 解决方法：
   @SpringBootApplication
   @ComponentScan(basePackages = {"com.example.demo", "com.example.other"})
   public class DemoApplication {
   }
   ```

3. **有多个实现，不知道注入哪个**：
   ```java
   // 有两个 UserDao 实现
   @Repository
   public class MySQLUserDao implements UserDao {}
   
   @Repository
   public class MongoUserDao implements UserDao {}
   
   // 解决方法1：用 @Primary 指定默认实现
   @Repository
   @Primary
   public class MySQLUserDao implements UserDao {}
   
   // 解决方法2：用 @Qualifier 指定注入哪个
   @Autowired
   @Qualifier("mySQLUserDao")
   private UserDao userDao;
   ```

---

### Q8: 如何查看 Spring 容器里有哪些 Bean？

**A**：
```java
@SpringBootApplication
public class DemoApplication {
    public static void main(String[] args) {
        ApplicationContext context = SpringApplication.run(DemoApplication.class, args);
        
        // 获取所有 Bean 的名称
        String[] beanNames = context.getBeanDefinitionNames();
        for (String beanName : beanNames) {
            System.out.println(beanName);
        }
    }
}
```

---

### Q9: 为什么访问 http://localhost:8080 显示 404？

**A**: 可能原因：

1. **没有定义根路径的 Controller**：
   ```java
   // 定义一个根路径
   @RestController
   public class HomeController {
       @GetMapping("/")
       public String home() {
           return "Welcome!";
       }
   }
   ```

2. **端口被占用**：
   ```yaml
   # 修改端口
   server:
     port: 8081
   ```

3. **Controller 路径写错了**：
   ```java
   // 如果你定义的是 /user/1
   // 那就要访问 http://localhost:8080/user/1
   ```

---

### Q10: 如何调试 Spring Boot 应用？

**A**：

**1. 查看日志**：
```yaml
# application.yml
logging:
  level:
    root: INFO
    com.example.demo: DEBUG  # 你的包名
```

**2. 使用断点**：
- 在 IDEA 中点击行号左侧，设置断点
- 点击 Debug 按钮启动（而不是 Run）
- 发送请求，程序会在断点处暂停

**3. 打印日志**：
```java
@Service
public class UserService {
    private static final Logger log = LoggerFactory.getLogger(UserService.class);
    
    public User getUserById(Long id) {
        log.info("查询用户，ID: {}", id);
        User user = userDao.findById(id);
        log.debug("查询结果: {}", user);
        return user;
    }
}
```

**4. 使用 Spring Boot Actuator**：
```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-actuator</artifactId>
</dependency>
```
访问：http://localhost:8080/actuator/health

---

## 🤔 面试题

### 1. Spring Boot 的自动配置原理是什么？

**答案**：
1. `@SpringBootApplication` 包含 `@EnableAutoConfiguration`
2. `@EnableAutoConfiguration` 通过 `@Import` 导入 `AutoConfigurationImportSelector`
3. `AutoConfigurationImportSelector` 通过 `SpringFactoriesLoader` 加载 `META-INF/spring.factories`
4. 根据条件注解（`@ConditionalOnClass`, `@ConditionalOnMissingBean`）决定是否生效

---

### 2. IOC 和 DI 的区别？

**答案**：
- **IOC（控制反转）**：是一种设计思想，将对象创建的控制权交给容器
- **DI（依赖注入）**：是 IOC 的实现方式，通过注入的方式提供依赖

**关系**：DI 是实现 IOC 的一种手段

---

### 3. `@Autowired` 和 `@Resource` 的区别？

**答案**：

| 特性 | @Autowired | @Resource |
|------|-----------|-----------|
| 来源 | Spring 注解 | JDK 注解 |
| 注入方式 | 按类型（byType） | 按名称（byName） |
| 找不到时 | 按类型查找 | 按类型查找 |
| 配合注解 | @Qualifier | @Named |
| 推荐度 | ⭐⭐⭐⭐⭐ | ⭐⭐⭐ |

---

### 4. Bean 的作用域有哪些？

**答案**：
1. **singleton**（默认）：单例，整个容器只有一个实例
2. **prototype**：原型，每次获取都创建新实例
3. **request**：每个 HTTP 请求一个实例（Web 环境）
4. **session**：每个 Session 一个实例（Web 环境）
5. **application**：每个 ServletContext 一个实例（Web 环境）

```java
@Service
@Scope("prototype")
public class UserService {
}
```

---

### 5. Spring Boot 配置文件的加载顺序？

**答案**：
1. 命令行参数（优先级最高）
2. 系统属性
3. 操作系统环境变量
4. jar 包外的 application-{profile}.yml
5. jar 包内的 application-{profile}.yml
6. jar 包外的 application.yml
7. jar 包内的 application.yml（优先级最低）

---

## 📚 推荐资源

### 视频教程：
- 尚硅谷 Spring Boot 2（B站）
- 黑马程序员 Spring Boot（B站）

### 官方文档：
- Spring Boot 官方文档：https://spring.io/projects/spring-boot
- Spring Framework 官方文档：https://spring.io/projects/spring-framework

### 书籍：
- 《Spring Boot 实战》
- 《Spring Boot 编程思想》

---

## ✅ 学习检查清单

- [ ] 使用 Spring Initializr 创建项目
- [ ] 理解 `@SpringBootApplication` 注解
- [ ] 创建第一个 Controller
- [ ] 理解 IOC 和 DI 的概念
- [ ] 掌握 `@Component`, `@Service`, `@Repository`, `@Controller` 注解
- [ ] 掌握 `@Autowired` 注解
- [ ] 理解 Bean 的生命周期
- [ ] 掌握构造器注入、Setter 注入、字段注入
- [ ] 学会使用 YAML 配置文件
- [ ] 掌握多环境配置
- [ ] 完成实战练习

---

## 🎯 下一步

完成 Day 1-2 的学习后，你应该：
- ✅ 能够独立创建 Spring Boot 项目
- ✅ 理解 IOC 和 DI 的核心概念
- ✅ 掌握常用注解的使用
- ✅ 会使用 YAML 配置文件

**下一步**：Day 3-4 - Web 开发

---

## 🗺️ 学习路径建议

### 第1天：理论学习（3小时）

**上午（1.5小时）**：
1. ✅ 阅读"前置知识：核心概念扫盲"（30分钟）
2. ✅ 理解 IOC 和 DI 的概念（30分钟）
3. ✅ 学习常用注解（30分钟）

**下午（1.5小时）**：
1. ✅ 使用 Spring Initializr 创建项目（15分钟）
2. ✅ 跟着文档敲一遍完整示例（45分钟）
3. ✅ 运行项目，测试 API（30分钟）

---

### 第2天：实战练习（3小时）

**上午（1.5小时）**：
1. ✅ 完成练习1：用户管理系统（1小时）
2. ✅ 完成练习2：多环境配置（30分钟）

**下午（1.5小时）**：
1. ✅ 完成练习3：自定义配置（30分钟）
2. ✅ 阅读常见问题（FAQ）（30分钟）
3. ✅ 复习面试题（30分钟）

---

### 学习建议

**1. 动手实践**：
- ⚠️ 不要只看不做
- ✅ 每个示例代码都要敲一遍
- ✅ 尝试修改代码，看看会发生什么

**2. 理解原理**：
- ⚠️ 不要死记硬背
- ✅ 理解为什么要这样做
- ✅ 多问"为什么"

**3. 遇到问题**：
- ✅ 先看错误信息（Error、Exception）
- ✅ 查看日志（控制台输出）
- ✅ 搜索错误信息（Google、百度）
- ✅ 查看官方文档
- ✅ 问 AI 助手（ChatGPT、Claude）

**4. 记笔记**：
- ✅ 记录重要概念
- ✅ 记录遇到的问题和解决方法
- ✅ 整理自己的代码片段

**5. 循序渐进**：
- ⚠️ 不要跳过基础
- ⚠️ 不要急于求成
- ✅ 一步一个脚印
- ✅ 基础打牢了，后面会很轻松

---

## 📌 重点回顾

### 核心概念（必须理解）

1. **Bean**：Spring 容器管理的对象
2. **IOC**：对象创建的控制权交给容器
3. **DI**：容器自动注入依赖
4. **注解**：给代码添加元数据，让框架知道该怎么处理
5. **三层架构**：Controller → Service → Dao

### 核心注解（必须掌握）

| 注解 | 作用 | 用在哪里 |
|------|------|---------|
| `@SpringBootApplication` | 启动类 | 主类 |
| `@RestController` | RESTful 控制器 | Controller 类 |
| `@Service` | 业务层组件 | Service 类 |
| `@Repository` | 数据访问层组件 | Dao 类 |
| `@Autowired` | 自动注入依赖 | 字段/构造器/Setter |
| `@GetMapping` | 处理 GET 请求 | Controller 方法 |
| `@PostMapping` | 处理 POST 请求 | Controller 方法 |
| `@PathVariable` | 路径参数 | 方法参数 |
| `@RequestBody` | 请求体 | 方法参数 |

### 最佳实践（必须遵守）

1. ✅ 使用构造器注入，而不是字段注入
2. ✅ 使用接口，而不是直接用实现类
3. ✅ 使用 YAML 配置文件，而不是 properties
4. ✅ 遵循三层架构，不要跨层调用
5. ✅ 使用 RESTful API 风格

---

## 🎓 学习成果检验

完成学习后，你应该能够：

- [ ] 独立创建一个 Spring Boot 项目
- [ ] 解释什么是 IOC 和 DI
- [ ] 说出至少 5 个常用注解及其作用
- [ ] 创建一个完整的三层架构项目
- [ ] 实现用户的增删改查 API
- [ ] 配置多环境（dev、test、prod）
- [ ] 使用 Postman 测试 API
- [ ] 调试 Spring Boot 应用
- [ ] 解决常见的启动错误
- [ ] 回答本文档中的所有面试题

**如果以上都能做到，恭喜你！🎉 你已经掌握了 Spring Boot 的基础知识！**

---

**加油！💪 你已经迈出了 Spring Boot 学习的第一步！**

**记住**：
> 编程是一门实践的艺术，不要只看不做。
> 
> 每天进步一点点，坚持下去，你一定能成为优秀的 Java 工程师！

---

**有问题随时问我！我会一直陪伴你的学习之旅！🚀**

