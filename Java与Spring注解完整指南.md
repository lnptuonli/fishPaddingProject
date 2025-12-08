# Java与Spring注解完整指南

## 目录
1. [注解基础概念](#注解基础概念)
2. [注解原理](#注解原理)
3. [Java内置注解](#java内置注解)
4. [自定义注解](#自定义注解)
5. [Spring核心注解](#spring核心注解)
6. [Spring Boot注解](#spring-boot注解)
7. [Spring Cloud注解](#spring-cloud注解)
8. [Spring MVC注解](#spring-mvc注解)
9. [Spring Data JPA注解](#spring-data-jpa注解)
10. [事务注解](#事务注解)
11. [AOP注解](#aop注解)
12. [面试高频问题](#面试高频问题)
13. [最佳实践](#最佳实践)

## 注解基础概念

### 什么是注解？
注解（Annotation）是Java 5引入的一种元数据机制，用于为代码提供额外的信息。注解本身不包含业务逻辑，但可以被编译器、解释器或框架读取并处理。

### 注解的作用
1. **编译时检查** - 如 @Override、@Deprecated
2. **代码生成** - 如 Lombok的 @Data、@Getter
3. **运行时处理** - 如 Spring的 @Component、@Autowired
4. **配置替代** - 替代XML配置
5. **文档生成** - 如 @param、@return

### 注解的分类

```java
// 1. 按来源分类
// - 标准注解（Java内置）
@Override
@Deprecated
@SuppressWarnings

// - 元注解（用于定义注解的注解）
@Target
@Retention
@Documented
@Inherited

// - 自定义注解
@MyCustomAnnotation

// 2. 按作用时期分类
// - 源码注解（SOURCE）：只在源码中保留
// - 编译时注解（CLASS）：在class文件中保留
// - 运行时注解（RUNTIME）：在运行时可通过反射读取
```

## 注解原理

### 注解的本质

```java
// 注解本质上是一个继承了Annotation接口的接口
public @interface MyAnnotation {
    String value();
}

// 编译后等价于：
public interface MyAnnotation extends Annotation {
    String value();
}
```

### 注解的处理机制

```java
/**
 * 注解处理的三个阶段
 */
public class AnnotationProcessing {
    
    // 1. 编译时处理（APT - Annotation Processing Tool）
    // Lombok等工具在编译时处理注解，生成代码
    
    // 2. 类加载时处理
    // 字节码增强技术（如AspectJ）
    
    // 3. 运行时处理（反射）
    public void runtimeProcessing() {
        Class<?> clazz = MyClass.class;
        
        // 获取类上的注解
        if (clazz.isAnnotationPresent(MyAnnotation.class)) {
            MyAnnotation annotation = clazz.getAnnotation(MyAnnotation.class);
            String value = annotation.value();
        }
        
        // 获取方法上的注解
        for (Method method : clazz.getDeclaredMethods()) {
            if (method.isAnnotationPresent(MyAnnotation.class)) {
                MyAnnotation annotation = method.getAnnotation(MyAnnotation.class);
                // 处理注解
            }
        }
    }
}
```

### 元注解详解

```java
import java.lang.annotation.*;

// 1. @Target - 指定注解可以用在哪些地方
//用法：
@Target({
    ElementType.TYPE,           // 类、接口、枚举
    ElementType.FIELD,          // 字段
    ElementType.METHOD,         // 方法
    ElementType.PARAMETER,      // 参数
    ElementType.CONSTRUCTOR,    // 构造函数
    ElementType.LOCAL_VARIABLE, // 局部变量
    ElementType.ANNOTATION_TYPE,// 注解
    ElementType.PACKAGE         // 包
})
//比方说，要定义一个自己的注解MyAnnotation，通过上面的Target就能指定MyAnnotation这个注解可以用在什么地方
public @interface MyAnnotation {
}

// 2. @Retention - 指定注解保留的时期
@Retention(RetentionPolicy.SOURCE)   // 源码期：只在源码中保留，编译时丢弃
@Retention(RetentionPolicy.CLASS)    // 编译期：在class文件中保留，运行时丢弃（默认）
@Retention(RetentionPolicy.RUNTIME)  // 运行期：运行时保留，可通过反射读取
public @interface MyAnnotation {
}

// 3. @Documented - 注解是否包含在JavaDoc中，JavaDoc是官方提供的文档生成工具，如果在编写内部类之类的建议加上，会让团队成员更清楚
@Documented
public @interface MyAnnotation {
}

// 4. @Inherited - 子类是否继承父类的注解
//@Inherited 只对 类级别的注解有效（@Target(ElementType.TYPE)）。
//它不会作用于方法、字段、构造器等。
//只有直接继承的子类会继承，接口实现或多级继承不会自动传递
//如果子类自己也加了同样的注解，会覆盖父类的注解。
import java.lang.annotation.*;
import java.lang.reflect.*;

// 定义一个可继承的注解
@Inherited//表示如果MyAnnotation注解修饰的类Parent被继承
@Retention(RetentionPolicy.RUNTIME)// 运行期：运行时保留，可通过反射读取
@Target(ElementType.TYPE)
@interface MyAnnotation {
    String value() default "来自父类的注解";
}

// 父类上使用注解
@MyAnnotation("父类注解")
class Parent {
}

// 子类没有显式使用MyAnnotation注解
class Child extends Parent {
}

// 测试类
public class InheritedDemo {
    public static void main(String[] args) {
        Class<?> clazz = Child.class;// 运行期：运行时保留，可通过反射读取
        MyAnnotation annotation = clazz.getAnnotation(MyAnnotation.class);

        if (annotation != null) {
            System.out.println("子类继承了注解，值为：" + annotation.value());
        } else {
            System.out.println("子类没有继承注解");
        }
    }
}
// 输出结果：
子类继承了注解，值为：父类注解

// 5. @Repeatable - 注解是否可重复使用（Java 8+）
@Repeatable(MyAnnotations.class)
public @interface MyAnnotation {
    String value();
}

@interface MyAnnotations {
    MyAnnotation[] value();
}
```

## Java内置注解

### 基础注解

```java
public class JavaBuiltInAnnotations {
    
    // 1. @Override - 标记方法覆盖父类方法
    @Override
    public String toString() {
        return "JavaBuiltInAnnotations";
    }
    
    // 2. @Deprecated - 标记过时的元素
    @Deprecated
    public void oldMethod() {
        System.out.println("这是一个过时的方法");
    }
    
    @Deprecated(since = "1.5", forRemoval = true)
    public void veryOldMethod() {
        System.out.println("这个方法将在未来版本移除");
    }
    
    // 3. @SuppressWarnings - 抑制编译警告
    @SuppressWarnings("unchecked")
    public void uncheckedMethod() {
        List list = new ArrayList();
        list.add("test");
    }
    
    @SuppressWarnings({"unchecked", "deprecation"})
    public void multipleWarnings() {
        // 抑制多种警告
    }
    // 常见警告类型：
    // "unchecked"	使用了原始类型或类型转换不安全
	// "deprecation"	使用了已过时的方法或类
	// "rawtypes"	使用了没有泛型参数的原始类型
	// "unused"	有变量或方法没有被使用
	// "null"	可能存在空指针问题（取决于 IDE）
    
    // 4. @SafeVarargs - 抑制可变参数的警告（Java 7+）
    @SafeVarargs
    public final <T> void safeVarargs(T... args) {
        for (T arg : args) {
            System.out.println(arg);
        }
    }
}
```


### @FunctionalInterface - 函数式接口（Java 8+）

函数式接口是只包含一个抽象方法的接口。这个"函数"指的是"行为"，也就是你可以把它当作一个函数来传递、执行。虽然接口可以有多个默认方法或静态方法，但只要抽象方法（即只有函数声明，没有函数体的方法）只有一个，它就是函数式接口。

#### 函数式接口的优势

1. **简洁** - 用 Lambda 表达式代替匿名类，代码更清爽
2. **可读性强** - 逻辑更集中，行为更明确（因为它只能有一个抽象方法）
3. **更强的抽象能力** - 可以把行为作为参数传递，提高灵活性
4. **支持并行和流式处理** - 与 Stream API 配合使用，提升性能和表达力

#### 函数式接口定义

```java
@FunctionalInterface
// @FunctionalInterface是函数式接口的标记，编译器会检查其是否符合规范
public interface MyFunction {
    // 接口中的方法默认是 public abstract
    void apply();
    
    // 函数式接口只能有一个抽象方法
    default void defaultMethod() {
        System.out.println("默认方法");
    }
}
```

#### 常见函数式接口

来自 `java.util.function` 包的常用函数式接口：

| 接口 | 功能描述 |
|------|----------|
| `Function<T, R>` | 接收一个参数，返回一个结果 |
| `Predicate<T>` | 接收一个参数，返回布尔值（用于判断） |
| `Consumer<T>` | 接收一个参数，无返回值（用于消费） |
| `Supplier<T>` | 无参数，返回一个结果（用于生产） |
| `UnaryOperator<T>` | 接收一个参数，返回相同类型的结果 |

#### 使用示例

```java
@FunctionalInterface
interface GreetingService {
    void sayMessage(String message);
}

public class Demo {
    public static void main(String[] args) {
        GreetingService greet = msg -> System.out.println("Hello " + msg);
        greet.sayMessage("凝");
    }
}
```


## 自定义注解

### 注解定义

```java
import java.lang.annotation.*;

// 1. 基础注解定义
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface MyMethod {
    String value() default "";
}

// 2. 带多个属性的注解
@Target({ElementType.TYPE, ElementType.METHOD})//限定这个注解只能用于类或方法上
@Retention(RetentionPolicy.RUNTIME)//注解信息在运行时仍然可见，可以通过反射读取
public @interface ApiInfo {
    String name();                          // 必填属性，使用注解时必须指定
    String description() default "";        // 选填，有默认值
    String version() default "1.0";
    String author() default "Unknown";
    String[] tags() default {};			   // 选填，默认空数组
}

// 3. 权限注解示例
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface RequirePermission {
    String[] value();                      // 需要的权限
    LogicalOperator operator() default LogicalOperator.AND;  // 逻辑操作符
    
    enum LogicalOperator {
        AND, OR
    }
}

// 4. 日志注解示例
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Log {
    String operation();                    // 操作名称
    LogLevel level() default LogLevel.INFO;
    boolean recordParams() default true;   // 是否记录参数
    boolean recordResult() default true;   // 是否记录返回值
    
    enum LogLevel {
        DEBUG, INFO, WARN, ERROR
    }
}
```

### 注解使用

```java
public class CustomAnnotationUsage {
    
    @MyMethod("测试方法")
    public void testMethod() {
        System.out.println("测试方法");
    }
    
    @ApiInfo(
        name = "用户服务",
        description = "提供用户相关的API",
        version = "2.0",
        author = "张三",
        tags = {"user", "api"}
    )
    public class UserService {
    }
    
    @RequirePermission(value = {"user:read", "user:write"}, operator = RequirePermission.LogicalOperator.OR)
    public void sensitiveOperation() {
        // 需要读或写权限
    }
    
    @Log(operation = "创建用户", level = Log.LogLevel.INFO, recordParams = true)
    public User createUser(String name, String email) {
        return new User(name, email);
    }
}
```

### 注解处理器

```java
import java.lang.reflect.*;

public class AnnotationProcessor {
    
    // 处理类上的注解
    public void processClassAnnotations(Class<?> clazz) {
        if (clazz.isAnnotationPresent(ApiInfo.class)) {
            ApiInfo apiInfo = clazz.getAnnotation(ApiInfo.class);
            System.out.println("API名称: " + apiInfo.name());
            System.out.println("API描述: " + apiInfo.description());
            System.out.println("API版本: " + apiInfo.version());
            System.out.println("API作者: " + apiInfo.author());
            System.out.println("API标签: " + Arrays.toString(apiInfo.tags()));
        }
    }
    
    // 处理方法上的注解
    public void processMethodAnnotations(Class<?> clazz) {
        for (Method method : clazz.getDeclaredMethods()) {
            // 处理权限注解
            if (method.isAnnotationPresent(RequirePermission.class)) {
                RequirePermission permission = method.getAnnotation(RequirePermission.class);
                System.out.println("方法: " + method.getName());
                System.out.println("需要权限: " + Arrays.toString(permission.value()));
                System.out.println("逻辑操作: " + permission.operator());
            }
            
            // 处理日志注解
            if (method.isAnnotationPresent(Log.class)) {
                Log log = method.getAnnotation(Log.class);
                System.out.println("操作: " + log.operation());
                System.out.println("日志级别: " + log.level());
                System.out.println("记录参数: " + log.recordParams());
                System.out.println("记录结果: " + log.recordResult());
            }
        }
    }
    
    // AOP方式处理注解（伪代码）
    public Object processLogAnnotation(ProceedingJoinPoint joinPoint) throws Throwable {
        Method method = getMethod(joinPoint);
        Log log = method.getAnnotation(Log.class);
        
        if (log != null) {
            // 记录日志前
            System.out.println("操作: " + log.operation());
            
            if (log.recordParams()) {
                Object[] args = joinPoint.getArgs();
                System.out.println("参数: " + Arrays.toString(args));
            }
            
            long startTime = System.currentTimeMillis();
            Object result = joinPoint.proceed();
            long endTime = System.currentTimeMillis();
            
            if (log.recordResult()) {
                System.out.println("返回值: " + result);
            }
            
            System.out.println("耗时: " + (endTime - startTime) + "ms");
            
            return result;
        }
        
        return joinPoint.proceed();
    }
    
    private Method getMethod(ProceedingJoinPoint joinPoint) {
        // 获取方法的实现逻辑
        return null;
    }
}
```

## Spring核心注解

### IoC容器注解

```java
import org.springframework.stereotype.*;
import org.springframework.context.annotation.*;
import org.springframework.beans.factory.annotation.*;

// 1. 组件注册注解
@Component              // 通用组件
public class MyComponent {
}

@Service               // 业务逻辑层
public class UserService {
}

@Repository            // 数据访问层
public class UserRepository {
}

@Controller            // 表现层（Spring MVC）
public class UserController {
}

@RestController        // RESTful控制器 = @Controller + @ResponseBody
public class UserRestController {
}

// 2. 配置类注解
@Configuration         // 标记配置类
public class AppConfig {
    
    @Bean              // 定义Bean
    public UserService userService() {
        return new UserService();
    }
    
    @Bean(name = "myBean", initMethod = "init", destroyMethod = "destroy")
    public MyBean myBean() {
        return new MyBean();
    }
}

// 3. 依赖注入注解
public class DependencyInjectionExample {
    
    // 字段注入
    @Autowired
    private UserService userService;
    
    // 字段注入 + 可选
    @Autowired(required = false)
    private OptionalService optionalService;
    
    // 构造器注入（推荐）
    private final UserRepository userRepository;
    
    @Autowired
    public DependencyInjectionExample(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
    
    // Setter注入
    private EmailService emailService;
    
    @Autowired
    public void setEmailService(EmailService emailService) {
        this.emailService = emailService;
    }
    
    // 使用@Qualifier指定Bean
    @Autowired
    @Qualifier("userServiceImpl")
    private UserService specificUserService;
    
    // 使用@Resource（JSR-250）
    @Resource(name = "userService")
    private UserService userService2;
    
    // 注入配置值
    @Value("${app.name}")
    private String appName;
    
    @Value("${app.version:1.0}")  // 带默认值
    private String appVersion;
    
    @Value("#{systemProperties['user.name']}")
    private String userName;
}

// 4. 组件扫描注解
@ComponentScan(basePackages = "com.example")
@ComponentScan(basePackages = {"com.example.service", "com.example.repository"})
public class AppConfig {
}

// 5. 导入配置
@Configuration
@Import({DatabaseConfig.class, CacheConfig.class})
@ImportResource("classpath:applicationContext.xml")  // 导入XML配置
public class AppConfig {
}

// 6. 属性源注解
@Configuration
@PropertySource("classpath:application.properties")
@PropertySource(value = "classpath:config.properties", ignoreResourceNotFound = true)
public class PropertyConfig {
}

// 7. 条件注解
@Configuration
public class ConditionalConfig {
    
    @Bean
    @ConditionalOnProperty(name = "feature.enabled", havingValue = "true")
    public FeatureService featureService() {
        return new FeatureService();
    }
    
    @Bean
    @ConditionalOnMissingBean(DataSource.class)
    public DataSource dataSource() {
        return new DataSource();
    }
    
    @Bean
    @ConditionalOnClass(name = "com.mysql.cj.jdbc.Driver")
    public MySQLConfig mySQLConfig() {
        return new MySQLConfig();
    }
}

// 8. Profile注解
@Configuration
@Profile("dev")
public class DevConfig {
    @Bean
    public DataSource devDataSource() {
        return new DataSource("localhost");
    }
}

@Configuration
@Profile("prod")
public class ProdConfig {
    @Bean
    public DataSource prodDataSource() {
        return new DataSource("production-server");
    }
}

// 9. Scope注解
@Service
@Scope("singleton")    // 单例（默认）
public class SingletonService {
}

@Service
@Scope("prototype")    // 原型（每次请求创建新实例）
public class PrototypeService {
}

@Service
@Scope("request")      // 请求作用域
public class RequestService {
}

@Service
@Scope("session")      // 会话作用域
public class SessionService {
}

// 10. Lazy初始化
@Service
@Lazy                  // 延迟初始化
public class LazyService {
}
```

## Spring Boot注解

### 核心注解

```java
import org.springframework.boot.autoconfigure.*;
import org.springframework.boot.context.properties.*;

// 1. 启动类注解
@SpringBootApplication  // 等价于 @Configuration + @EnableAutoConfiguration + @ComponentScan
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}

// 自定义配置
@SpringBootApplication(
    scanBasePackages = "com.example",
    exclude = {DataSourceAutoConfiguration.class}
)
public class CustomApplication {
}

// 2. 配置属性注解
@ConfigurationProperties(prefix = "app")
@Component
public class AppProperties {
    private String name;
    private String version;
    private DatabaseConfig database;
    
    // Getters and Setters
    
    public static class DatabaseConfig {
        private String url;
        private String username;
        private String password;
        
        // Getters and Setters
    }
}

// 使用配置属性
@Service
public class ConfigService {
    @Autowired
    private AppProperties appProperties;
    
    public void printConfig() {
        System.out.println("应用名称: " + appProperties.getName());
        System.out.println("数据库URL: " + appProperties.getDatabase().getUrl());
    }
}

// 3. 启用配置属性
@Configuration
@EnableConfigurationProperties(AppProperties.class)
public class Config {
}

// 4. 条件注解（扩展）
@Configuration
public class ConditionalBeans {
    
    @Bean
    @ConditionalOnBean(DataSource.class)
    public JdbcTemplate jdbcTemplate(DataSource dataSource) {
        return new JdbcTemplate(dataSource);
    }
    
    @Bean
    @ConditionalOnMissingBean
    public DefaultService defaultService() {
        return new DefaultService();
    }
    
    @Bean
    @ConditionalOnProperty(prefix = "cache", name = "enabled", havingValue = "true")
    public CacheManager cacheManager() {
        return new CacheManager();
    }
    
    @Bean
    @ConditionalOnClass(RedisTemplate.class)
    public RedisConfig redisConfig() {
        return new RedisConfig();
    }
    
    @Bean
    @ConditionalOnExpression("${feature.enabled:false}")
    public FeatureService featureService() {
        return new FeatureService();
    }
}

// 5. 自动配置注解
@Configuration
@ConditionalOnClass(DataSource.class)
@EnableConfigurationProperties(DataSourceProperties.class)
public class DataSourceAutoConfiguration {
    
    @Bean
    @ConditionalOnMissingBean
    public DataSource dataSource(DataSourceProperties properties) {
        return new DataSource(properties);
    }
}
```

## Spring Cloud注解

### 服务注册与发现

```java
import org.springframework.cloud.client.discovery.*;
import org.springframework.cloud.netflix.eureka.*;

// 1. Eureka Server
@SpringBootApplication
@EnableEurekaServer
public class EurekaServerApplication {
    public static void main(String[] args) {
        SpringApplication.run(EurekaServerApplication.class, args);
    }
}

// 2. Eureka Client
@SpringBootApplication
@EnableEurekaClient        // 或 @EnableDiscoveryClient（更通用）
public class ServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(ServiceApplication.class, args);
    }
}

// 3. 服务调用
@Service
public class RemoteServiceCaller {
    
    @Autowired
    private DiscoveryClient discoveryClient;
    
    @Autowired
    private RestTemplate restTemplate;
    
    public void callRemoteService() {
        // 通过服务名调用
        String result = restTemplate.getForObject(
            "http://user-service/api/users/1",
            String.class
        );
    }
}

// 配置负载均衡的RestTemplate
@Configuration
public class RestTemplateConfig {
    
    @Bean
    @LoadBalanced      // 启用负载均衡
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}
```

### Feign声明式HTTP客户端

```java
import org.springframework.cloud.openfeign.*;

// 1. 启用Feign
@SpringBootApplication
@EnableFeignClients(basePackages = "com.example.client")
public class Application {
}

// 2. 定义Feign客户端
@FeignClient(name = "user-service", path = "/api/users")
public interface UserServiceClient {
    
    @GetMapping("/{id}")
    User getUserById(@PathVariable("id") Long id);
    
    @PostMapping
    User createUser(@RequestBody User user);
    
    @PutMapping("/{id}")
    User updateUser(@PathVariable("id") Long id, @RequestBody User user);
    
    @DeleteMapping("/{id}")
    void deleteUser(@PathVariable("id") Long id);
}

// 3. Feign配置
@FeignClient(
    name = "user-service",
    url = "${user-service.url:}",      // 可选的固定URL
    fallback = UserServiceFallback.class,  // 降级处理
    configuration = FeignConfig.class     // 自定义配置
)
public interface UserServiceClient {
    // ...
}

// 4. Feign降级处理
@Component
public class UserServiceFallback implements UserServiceClient {
    
    @Override
    public User getUserById(Long id) {
        return new User(id, "默认用户", "default@example.com");
    }
    
    @Override
    public User createUser(User user) {
        throw new RuntimeException("服务不可用");
    }
}

// 5. Feign自定义配置
@Configuration
public class FeignConfig {
    
    @Bean
    public Logger.Level feignLoggerLevel() {
        return Logger.Level.FULL;
    }
    
    @Bean
    public RequestInterceptor requestInterceptor() {
        return template -> {
            template.header("Authorization", "Bearer token");
        };
    }
}
```

### Hystrix断路器

```java
import org.springframework.cloud.netflix.hystrix.*;
import com.netflix.hystrix.contrib.javanica.annotation.*;

// 1. 启用Hystrix
@SpringBootApplication
@EnableHystrix        // 或 @EnableCircuitBreaker
public class Application {
}

// 2. 使用Hystrix命令
@Service
public class UserService {
    
    @HystrixCommand(
        fallbackMethod = "getUserByIdFallback",
        commandProperties = {
            @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "3000"),
            @HystrixProperty(name = "circuitBreaker.requestVolumeThreshold", value = "10"),
            @HystrixProperty(name = "circuitBreaker.errorThresholdPercentage", value = "50")
        }
    )
    public User getUserById(Long id) {
        // 可能失败的远程调用
        return restTemplate.getForObject("http://user-service/users/" + id, User.class);
    }
    
    // 降级方法
    public User getUserByIdFallback(Long id) {
        return new User(id, "默认用户", "default@example.com");
    }
    
    // 忽略特定异常
    @HystrixCommand(
        fallbackMethod = "fallback",
        ignoreExceptions = {IllegalArgumentException.class}
    )
    public String process(String input) {
        if (input == null) {
            throw new IllegalArgumentException("输入不能为空");
        }
        return remoteService.call(input);
    }
}

// 3. Hystrix Dashboard
@SpringBootApplication
@EnableHystrixDashboard
public class HystrixDashboardApplication {
}
```

### 配置中心

```java
import org.springframework.cloud.config.server.*;
import org.springframework.cloud.context.config.annotation.*;

// 1. Config Server
@SpringBootApplication
@EnableConfigServer
public class ConfigServerApplication {
}

// 2. Config Client
@SpringBootApplication
public class ConfigClientApplication {
    
    @Value("${config.property}")
    private String configProperty;
}

// 3. 刷新配置
@RestController
@RefreshScope      // 支持动态刷新配置
public class ConfigController {
    
    @Value("${config.property}")
    private String configProperty;
    
    @GetMapping("/config")
    public String getConfig() {
        return configProperty;
    }
}
```

### 网关

```java
import org.springframework.cloud.gateway.*;

// 1. Spring Cloud Gateway
@SpringBootApplication
public class GatewayApplication {
    
    @Bean
    public RouteLocator customRouteLocator(RouteLocatorBuilder builder) {
        return builder.routes()
            .route("user-service", r -> r
                .path("/api/users/**")
                .filters(f -> f
                    .stripPrefix(1)
                    .addRequestHeader("X-Gateway", "SpringCloudGateway"))
                .uri("lb://user-service"))
            .route("order-service", r -> r
                .path("/api/orders/**")
                .uri("lb://order-service"))
            .build();
    }
}

// 2. Zuul（旧版）
@SpringBootApplication
@EnableZuulProxy
public class ZuulGatewayApplication {
}

// Zuul过滤器
@Component
public class PreFilter extends ZuulFilter {
    
    @Override
    public String filterType() {
        return "pre";
    }
    
    @Override
    public int filterOrder() {
        return 1;
    }
    
    @Override
    public boolean shouldFilter() {
        return true;
    }
    
    @Override
    public Object run() {
        RequestContext ctx = RequestContext.getCurrentContext();
        HttpServletRequest request = ctx.getRequest();
        
        String token = request.getHeader("Authorization");
        if (token == null) {
            ctx.setSendZuulResponse(false);
            ctx.setResponseStatusCode(401);
            return null;
        }
        
        return null;
    }
}
```

### 链路追踪

```java
import org.springframework.cloud.sleuth.*;

// 1. 启用Sleuth（自动配置）
@SpringBootApplication
public class Application {
}

// 2. 自定义追踪
@Service
public class TraceService {
    
    @Autowired
    private Tracer tracer;
    
    public void customTrace() {
        Span newSpan = tracer.nextSpan().name("customOperation");
        try (Tracer.SpanInScope ws = tracer.withSpanInScope(newSpan.start())) {
            // 业务逻辑
            newSpan.tag("custom.tag", "value");
            newSpan.annotate("custom.event");
        } finally {
            newSpan.finish();
        }
    }
}
```

## Spring MVC注解

### 控制器注解

```java
import org.springframework.web.bind.annotation.*;

// 1. 基础控制器
@RestController
@RequestMapping("/api/users")
@CrossOrigin(origins = "*")    // 跨域配置
public class UserController {
    
    // 2. 请求映射注解
    @GetMapping                 // GET /api/users
    public List<User> getAllUsers() {
        return userService.findAll();
    }
    
    @GetMapping("/{id}")        // GET /api/users/1
    public User getUserById(@PathVariable Long id) {
        return userService.findById(id);
    }
    
    @GetMapping("/search")      // GET /api/users/search?name=John&age=25
    public List<User> searchUsers(
        @RequestParam String name,
        @RequestParam(required = false) Integer age) {
        return userService.search(name, age);
    }
    
    @PostMapping                // POST /api/users
    public User createUser(@RequestBody User user) {
        return userService.create(user);
    }
    
    @PutMapping("/{id}")        // PUT /api/users/1
    public User updateUser(
        @PathVariable Long id,
        @RequestBody User user) {
        return userService.update(id, user);
    }
    
    @DeleteMapping("/{id}")     // DELETE /api/users/1
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteUser(@PathVariable Long id) {
        userService.delete(id);
    }
    
    // 3. 参数绑定
    @PostMapping("/register")
    public User register(
        @RequestParam String username,
        @RequestParam String password,
        @RequestParam String email,
        @RequestHeader("User-Agent") String userAgent,
        @CookieValue(value = "sessionId", required = false) String sessionId,
        HttpServletRequest request) {
        
        String ip = request.getRemoteAddr();
        return userService.register(username, password, email);
    }
    
    // 4. 表单数据绑定
    @PostMapping("/form")
    public User createUserFromForm(@ModelAttribute User user) {
        return userService.create(user);
    }
    
    // 5. 响应状态码
    @GetMapping("/{id}/status")
    @ResponseStatus(HttpStatus.OK)
    public User getUserStatus(@PathVariable Long id) {
        return userService.findById(id);
    }
    
    // 6. 文件上传
    @PostMapping("/upload")
    public String uploadFile(@RequestParam("file") MultipartFile file) {
        if (file.isEmpty()) {
            return "文件为空";
        }
        
        String fileName = file.getOriginalFilename();
        // 保存文件逻辑
        return "上传成功: " + fileName;
    }
    
    // 7. 矩阵变量（不常用）
    @GetMapping("/matrix/{id}")
    public User getByMatrixVariables(
        @PathVariable Long id,
        @MatrixVariable String name) {
        return userService.findByIdAndName(id, name);
    }
}

// 8. 异常处理
@RestControllerAdvice
public class GlobalExceptionHandler {
    
    @ExceptionHandler(UserNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponse handleUserNotFound(UserNotFoundException ex) {
        return new ErrorResponse("USER_NOT_FOUND", ex.getMessage());
    }
    
    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleIllegalArgument(IllegalArgumentException ex) {
        return new ErrorResponse("INVALID_ARGUMENT", ex.getMessage());
    }
    
    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorResponse handleGenericException(Exception ex) {
        return new ErrorResponse("INTERNAL_ERROR", "服务器内部错误");
    }
}

// 9. 拦截器
@Component
public class AuthenticationInterceptor implements HandlerInterceptor {
    
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        String token = request.getHeader("Authorization");
        if (token == null || !validateToken(token)) {
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
            return false;
        }
        return true;
    }
    
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) {
        // 请求处理后执行
    }
    
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
        // 请求完成后执行
    }
    
    private boolean validateToken(String token) {
        return true;
    }
}

// 10. 拦截器配置
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {
    
    @Autowired
    private AuthenticationInterceptor authenticationInterceptor;
    
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(authenticationInterceptor)
            .addPathPatterns("/api/**")
            .excludePathPatterns("/api/public/**");
    }
}
```

### 验证注解

```java
import javax.validation.constraints.*;
import org.springframework.validation.annotation.*;

// 1. Bean Validation注解
public class UserDTO {
    
    @NotNull(message = "ID不能为空")
    private Long id;
    
    @NotBlank(message = "用户名不能为空")
    @Size(min = 3, max = 20, message = "用户名长度必须在3-20之间")
    private String username;
    
    @Email(message = "邮箱格式不正确")
    private String email;
    
    @Min(value = 18, message = "年龄不能小于18")
    @Max(value = 100, message = "年龄不能大于100")
    private Integer age;
    
    @Pattern(regexp = "^1[3-9]\\d{9}$", message = "手机号格式不正确")
    private String phone;
    
    @Past(message = "生日必须是过去的日期")
    private Date birthDate;
    
    @Future(message = "过期时间必须是未来的日期")
    private Date expiryDate;
    
    @AssertTrue(message = "必须同意用户协议")
    private Boolean agreedToTerms;
    
    @Valid
    @NotNull(message = "地址不能为空")
    private Address address;
    
    // Getters and Setters
}

// 2. 嵌套验证
public class Address {
    @NotBlank(message = "城市不能为空")
    private String city;
    
    @NotBlank(message = "街道不能为空")
    private String street;
    
    // Getters and Setters
}

// 3. 控制器中使用验证
@RestController
@Validated
public class UserController {
    
    @PostMapping("/users")
    public User createUser(@Valid @RequestBody UserDTO userDTO, BindingResult result) {
        if (result.hasErrors()) {
            throw new ValidationException(result.getAllErrors());
        }
        return userService.create(userDTO);
    }
    
    // 方法级别验证
    @GetMapping("/users/{id}")
    public User getUserById(@PathVariable @Min(1) Long id) {
        return userService.findById(id);
    }
}

// 4. 自定义验证注解
@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = PhoneNumberValidator.class)
public @interface ValidPhoneNumber {
    String message() default "手机号格式不正确";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}

// 5. 自定义验证器
public class PhoneNumberValidator implements ConstraintValidator<ValidPhoneNumber, String> {
    
    private static final Pattern PHONE_PATTERN = Pattern.compile("^1[3-9]\\d{9}$");
    
    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null) {
            return true;
        }
        return PHONE_PATTERN.matcher(value).matches();
    }
}
```

## Spring Data JPA注解

### 实体注解

```java
import javax.persistence.*;
import org.springframework.data.jpa.repository.*;

// 1. 实体类注解
@Entity
@Table(name = "users", indexes = {
    @Index(name = "idx_username", columnList = "username"),
    @Index(name = "idx_email", columnList = "email")
})
public class User {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "username", nullable = false, unique = true, length = 50)
    private String username;
    
    @Column(name = "email", nullable = false, unique = true)
    private String email;
    
    @Column(name = "age")
    private Integer age;
    
    @Column(name = "created_at", updatable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;
    
    @Column(name = "updated_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date updatedAt;
    
    @Transient  // 不映射到数据库
    private String tempField;
    
    @Lob        // 大对象
    private String description;
    
    @Enumerated(EnumType.STRING)
    private UserStatus status;
    
    // 关联关系
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "profile_id")
    private UserProfile profile;
    
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Order> orders;
    
    @ManyToOne
    @JoinColumn(name = "department_id")
    private Department department;
    
    @ManyToMany
    @JoinTable(
        name = "user_roles",
        joinColumns = @JoinColumn(name = "user_id"),
        inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    private Set<Role> roles;
    
    // 生命周期回调
    @PrePersist
    public void prePersist() {
        this.createdAt = new Date();
        this.updatedAt = new Date();
    }
    
    @PreUpdate
    public void preUpdate() {
        this.updatedAt = new Date();
    }
    
    // Getters and Setters
}

// 2. Repository接口
@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    
    // 方法名查询
    User findByUsername(String username);
    
    List<User> findByAgeGreaterThan(Integer age);
    
    List<User> findByUsernameAndEmail(String username, String email);
    
    List<User> findByAgeBetween(Integer minAge, Integer maxAge);
    
    List<User> findByUsernameContaining(String keyword);
    
    List<User> findByOrdersIsNotEmpty();
    
    // @Query注解查询
    @Query("SELECT u FROM User u WHERE u.email = ?1")
    User findByEmail(String email);
    
    @Query("SELECT u FROM User u WHERE u.username LIKE %:keyword%")
    List<User> searchByUsername(@Param("keyword") String keyword);
    
    @Query(value = "SELECT * FROM users WHERE age > ?1", nativeQuery = true)
    List<User> findByAgeNative(Integer age);
    
    // 更新查询
    @Modifying
    @Query("UPDATE User u SET u.status = :status WHERE u.id = :id")
    int updateStatus(@Param("id") Long id, @Param("status") UserStatus status);
    
    // 删除查询
    @Modifying
    @Query("DELETE FROM User u WHERE u.status = :status")
    int deleteByStatus(@Param("status") UserStatus status);
    
    // 分页查询
    Page<User> findByStatus(UserStatus status, Pageable pageable);
    
    // 排序查询
    List<User> findByStatus(UserStatus status, Sort sort);
}
```

## 事务注解

### @Transactional详解

```java
import org.springframework.transaction.annotation.*;

@Service
public class TransactionService {
    
    // 1. 基础事务
    @Transactional
    public void basicTransaction() {
        // 事务方法
    }
    
    // 2. 只读事务（优化性能）
    @Transactional(readOnly = true)
    public User findUser(Long id) {
        return userRepository.findById(id).orElse(null);
    }
    
    // 3. 事务传播行为
    @Transactional(propagation = Propagation.REQUIRED)      // 默认：如果当前有事务，则加入；否则新建
    public void required() {}
    
    @Transactional(propagation = Propagation.REQUIRES_NEW)  // 总是新建事务
    public void requiresNew() {}
    
    @Transactional(propagation = Propagation.SUPPORTS)      // 支持当前事务，没有则以非事务方式执行
    public void supports() {}
    
    @Transactional(propagation = Propagation.NOT_SUPPORTED) // 以非事务方式执行
    public void notSupported() {}
    
    @Transactional(propagation = Propagation.MANDATORY)     // 必须在事务中执行
    public void mandatory() {}
    
    @Transactional(propagation = Propagation.NEVER)         // 不能在事务中执行
    public void never() {}
    
    @Transactional(propagation = Propagation.NESTED)        // 嵌套事务
    public void nested() {}
    
    // 4. 事务隔离级别
    @Transactional(isolation = Isolation.READ_UNCOMMITTED)  // 读未提交
    public void readUncommitted() {}
    
    @Transactional(isolation = Isolation.READ_COMMITTED)    // 读已提交（MySQL默认）
    public void readCommitted() {}
    
    @Transactional(isolation = Isolation.REPEATABLE_READ)   // 可重复读（MySQL默认）
    public void repeatableRead() {}
    
    @Transactional(isolation = Isolation.SERIALIZABLE)      // 串行化
    public void serializable() {}
    
    // 5. 异常回滚配置
    @Transactional(rollbackFor = Exception.class)           // 所有异常都回滚
    public void rollbackForAllExceptions() {}
    
    @Transactional(noRollbackFor = IllegalArgumentException.class)  // 指定异常不回滚
    public void noRollbackForSpecificException() {}
    
    @Transactional(
        rollbackFor = {SQLException.class, IOException.class},
        noRollbackFor = {ValidationException.class}
    )
    public void customRollbackRules() {}
    
    // 6. 超时设置
    @Transactional(timeout = 30)  // 30秒超时
    public void timeoutTransaction() {}
    
    // 7. 完整配置示例
    @Transactional(
        propagation = Propagation.REQUIRED,
        isolation = Isolation.READ_COMMITTED,
        timeout = 30,
        readOnly = false,
        rollbackFor = Exception.class
    )
    public void fullConfigTransaction() {
        // 业务逻辑
    }
}

// 8. 启用事务管理
@Configuration
@EnableTransactionManagement
public class TransactionConfig {
    
    @Bean
    public PlatformTransactionManager transactionManager(EntityManagerFactory emf) {
        return new JpaTransactionManager(emf);
    }
}
```

## AOP注解

### 面向切面编程

```java
import org.aspectj.lang.annotation.*;
import org.aspectj.lang.*;

// 1. 启用AOP
@Configuration
@EnableAspectJAutoProxy
public class AopConfig {
}

// 2. 切面定义
@Aspect
@Component
public class LoggingAspect {
    
    // 3. 切点定义
    @Pointcut("execution(* com.example.service.*.*(..))")
    public void serviceMethods() {}
    
    @Pointcut("@annotation(com.example.annotation.Log)")
    public void logAnnotation() {}
    
    @Pointcut("within(com.example.controller..*)")
    public void controllerLayer() {}
    
    // 4. 前置通知
    @Before("serviceMethods()")
    public void logBefore(JoinPoint joinPoint) {
        String methodName = joinPoint.getSignature().getName();
        Object[] args = joinPoint.getArgs();
        System.out.println("方法执行前: " + methodName);
        System.out.println("参数: " + Arrays.toString(args));
    }
    
    // 5. 后置通知（返回后）
    @AfterReturning(pointcut = "serviceMethods()", returning = "result")
    public void logAfterReturning(JoinPoint joinPoint, Object result) {
        String methodName = joinPoint.getSignature().getName();
        System.out.println("方法执行后: " + methodName);
        System.out.println("返回值: " + result);
    }
    
    // 6. 后置通知（异常后）
    @AfterThrowing(pointcut = "serviceMethods()", throwing = "ex")
    public void logAfterThrowing(JoinPoint joinPoint, Exception ex) {
        String methodName = joinPoint.getSignature().getName();
        System.out.println("方法抛出异常: " + methodName);
        System.out.println("异常: " + ex.getMessage());
    }
    
    // 7. 后置通知（最终）
    @After("serviceMethods()")
    public void logAfter(JoinPoint joinPoint) {
        String methodName = joinPoint.getSignature().getName();
        System.out.println("方法执行完毕: " + methodName);
    }
    
    // 8. 环绕通知
    @Around("serviceMethods()")
    public Object logAround(ProceedingJoinPoint joinPoint) throws Throwable {
        String methodName = joinPoint.getSignature().getName();
        
        // 方法执行前
        System.out.println("环绕通知 - 方法执行前: " + methodName);
        long startTime = System.currentTimeMillis();
        
        Object result = null;
        try {
            // 执行目标方法
            result = joinPoint.proceed();
            
            // 方法执行后
            long endTime = System.currentTimeMillis();
            System.out.println("环绕通知 - 方法执行后: " + methodName);
            System.out.println("执行时间: " + (endTime - startTime) + "ms");
            
        } catch (Exception e) {
            // 异常处理
            System.out.println("环绕通知 - 方法异常: " + methodName);
            throw e;
        }
        
        return result;
    }
    
    // 9. 获取注解信息
    @Around("@annotation(log)")
    public Object logWithAnnotation(ProceedingJoinPoint joinPoint, Log log) throws Throwable {
        System.out.println("操作: " + log.operation());
        System.out.println("日志级别: " + log.level());
        
        return joinPoint.proceed();
    }
}

// 10. 切点表达式详解
@Aspect
@Component
public class PointcutExpressions {
    
    // 执行表达式
    @Pointcut("execution(public * com.example.service.*.*(..))")
    public void publicServiceMethods() {}
    
    // 指定返回类型
    @Pointcut("execution(com.example.entity.User com.example.service.*.*(..))")
    public void returnsUser() {}
    
    // 指定参数类型
    @Pointcut("execution(* com.example.service.*.*(Long, ..))")
    public void firstParamLong() {}
    
    // within表达式（类型范围）
    @Pointcut("within(com.example.service..*)")
    public void inServicePackage() {}
    
    // this表达式（代理对象类型）
    @Pointcut("this(com.example.service.UserService)")
    public void proxyIsUserService() {}
    
    // target表达式（目标对象类型）
    @Pointcut("target(com.example.service.UserService)")
    public void targetIsUserService() {}
    
    // args表达式（参数类型）
    @Pointcut("args(Long, String, ..)")
    public void argsMatch() {}
    
    // @within表达式（类上有注解）
    @Pointcut("@within(org.springframework.stereotype.Service)")
    public void classWithServiceAnnotation() {}
    
    // @target表达式（目标对象类上有注解）
    @Pointcut("@target(org.springframework.stereotype.Repository)")
    public void targetWithRepositoryAnnotation() {}
    
    // @args表达式（参数对象类上有注解）
    @Pointcut("@args(com.example.annotation.Valid)")
    public void argsWithValidAnnotation() {}
    
    // @annotation表达式（方法上有注解）
    @Pointcut("@annotation(org.springframework.transaction.annotation.Transactional)")
    public void methodWithTransactionalAnnotation() {}
    
    // 组合切点
    @Pointcut("publicServiceMethods() && inServicePackage()")
    public void combinedPointcut() {}
    
    @Pointcut("publicServiceMethods() || inServicePackage()")
    public void orPointcut() {}
    
    @Pointcut("publicServiceMethods() && !inServicePackage()")
    public void notPointcut() {}
}
```

## 面试高频问题

### 1. 注解的本质是什么？

```java
/**
 * 注解的本质
 * 
 * 注解本质上是一个继承了Annotation接口的接口
 */
public @interface MyAnnotation {
    String value();
}

// 编译后等价于：
public interface MyAnnotation extends java.lang.annotation.Annotation {
    String value();
}

/**
 * 面试回答要点：
 * 1. 注解是一种元数据，为代码提供额外信息
 * 2. 本质是继承了Annotation接口的接口
 * 3. 通过反射或APT处理
 * 4. 不包含业务逻辑，只提供元信息
 */
```

### 2. @Autowired和@Resource的区别？

```java
/**
 * @Autowired vs @Resource
 */
public class DifferenceBetweenAnnotations {
    
    // @Autowired - Spring注解
    // 按类型注入（by type）
    // 可配合@Qualifier指定bean名称
    @Autowired
    @Qualifier("userServiceImpl")
    private UserService userService;
    
    // @Resource - Java标准注解（JSR-250）
    // 按名称注入（by name）
    // name属性指定bean名称
    @Resource(name = "userServiceImpl")
    private UserService userService2;
    
    /**
     * 面试回答要点：
     * 
     * @Autowired:
     * - Spring框架注解
     * - 按类型自动注入
     * - 可用于构造器、方法、参数、字段
     * - 配合@Qualifier按名称注入
     * - required属性控制是否必须
     * 
     * @Resource:
     * - JDK标准注解（JSR-250）
     * - 按名称自动注入
     * - 只能用于字段和setter方法
     * - name属性指定bean名称
     * - 如果name未指定，按字段名查找
     * 
     * 推荐：优先使用@Autowired（Spring标准）
     */
}
```

### 3. @Component、@Service、@Repository、@Controller的区别？

```java
/**
 * 四大组件注解的区别
 */

// @Component - 通用组件
@Component
public class MyComponent {
}

// @Service - 业务逻辑层
@Service
public class UserService {
}

// @Repository - 数据访问层
@Repository
public class UserRepository {
}

// @Controller - 表现层
@Controller
public class UserController {
}

/**
 * 面试回答要点：
 * 
 * 1. 功能上：都是用于Spring Bean的注册
 * 2. 语义上：
 *    - @Component：通用组件
 *    - @Service：业务逻辑层
 *    - @Repository：数据访问层（有异常转换功能）
 *    - @Controller：表现层（MVC控制器）
 * 
 * 3. @Service、@Repository、@Controller都是@Component的特化
 * 4. @Repository会自动进行异常转换（将持久层异常转换为Spring的DataAccessException）
 * 5. 使用具体的注解有助于AOP切面的精确匹配
 * 
 * 推荐：按层次使用对应的注解，增强代码可读性
 */
```

### 4. @Transactional什么时候失效？

```java
/**
 * @Transactional失效场景
 */
@Service
public class TransactionFailureScenarios {
    
    // 场景1：方法不是public（会失效）
    @Transactional
    private void privateMethod() {
        // 事务不会生效
    }
    
    // 场景2：同类方法调用（会失效）
    public void outerMethod() {
        this.innerMethod();  // 事务不会生效
    }
    
    @Transactional
    public void innerMethod() {
        // 同类调用，事务失效
    }
    
    // 场景3：异常被捕获（会失效）
    @Transactional
    public void catchException() {
        try {
            // 业务逻辑
            throw new RuntimeException();
        } catch (Exception e) {
            // 异常被捕获，事务不会回滚
        }
    }
    
    // 场景4：抛出检查异常但未配置rollbackFor（会失效）
    @Transactional  // 默认只回滚RuntimeException
    public void checkedExceptionNotRollback() throws Exception {
        throw new Exception();  // 不会回滚
    }
    
    // 正确做法
    @Transactional(rollbackFor = Exception.class)
    public void checkedExceptionRollback() throws Exception {
        throw new Exception();  // 会回滚
    }
    
    /**
     * 面试回答要点：
     * 
     * @Transactional失效场景：
     * 1. 方法不是public
     * 2. 同类方法直接调用（this.method()）
     * 3. 异常被捕获未抛出
     * 4. 抛出检查异常但未配置rollbackFor
     * 5. 数据库不支持事务（如MyISAM）
     * 6. 没有被Spring管理（如new对象）
     * 7. propagation设置为NOT_SUPPORTED
     * 
     * 解决方案：
     * 1. 确保方法是public
     * 2. 通过注入的bean调用（不是this）
     * 3. 异常要抛出到Spring事务管理器
     * 4. 配置rollbackFor = Exception.class
     * 5. 使用InnoDB等支持事务的引擎
     */
}
```

### 5. Spring Boot启动过程中注解的作用？

```java
/**
 * Spring Boot启动注解详解
 */

// @SpringBootApplication详解
@SpringBootApplication  // 组合注解
// 等价于：
@SpringBootConfiguration  // = @Configuration
@EnableAutoConfiguration  // 自动配置
@ComponentScan           // 组件扫描

public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}

/**
 * 面试回答要点：
 * 
 * @SpringBootApplication启动流程：
 * 
 * 1. @SpringBootConfiguration
 *    - 标记为配置类
 *    - 等同于@Configuration
 * 
 * 2. @EnableAutoConfiguration
 *    - 启用自动配置机制
 *    - 读取META-INF/spring.factories
 *    - 根据条件注解决定是否加载配置
 * 
 * 3. @ComponentScan
 *    - 扫描@Component、@Service等注解
 *    - 默认扫描主类所在包及子包
 *    - 注册Bean到容器
 * 
 * 启动流程：
 * 1. 创建SpringApplication对象
 * 2. 准备Environment
 * 3. 创建ApplicationContext
 * 4. 刷新ApplicationContext（加载Bean）
 * 5. 执行Runners
 */
```

## 最佳实践

### 1. 注解使用规范

```java
public class AnnotationBestPractices {
    
    // 1. 优先使用标准注解
    @Autowired           // Spring标准
    private UserService userService;
    
    // 2. 构造器注入优于字段注入
    private final UserRepository userRepository;
    
    @Autowired
    public AnnotationBestPractices(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
    
    // 3. 使用语义化的注解
    @Service             // 而不是@Component
    public class UserService {
    }
    
    // 4. 合理使用@Transactional
    @Transactional(rollbackFor = Exception.class)
    public void businessMethod() {
        // 业务逻辑
    }
    
    // 5. 避免过度使用注解
    // 不要在一个类上堆砌太多注解
    
    // 6. 自定义注解要有文档
    /**
     * 日志注解
     * 用于记录方法执行日志
     */
    @Target(ElementType.METHOD)
    @Retention(RetentionPolicy.RUNTIME)
    @Documented
    public @interface Log {
        String operation();
    }
}
```

### 2. 性能优化

```java
@Service
public class PerformanceOptimization {
    
    // 1. 使用@Lazy延迟加载
    @Autowired
    @Lazy
    private HeavyService heavyService;
    
    // 2. 合理设置@Transactional的readOnly
    @Transactional(readOnly = true)
    public User findUser(Long id) {
        return userRepository.findById(id).orElse(null);
    }
    
    // 3. 使用@Cacheable缓存
    @Cacheable(value = "users", key = "#id")
    public User getUserById(Long id) {
        return userRepository.findById(id).orElse(null);
    }
    
    // 4. 使用@Async异步执行
    @Async
    public CompletableFuture<String> asyncMethod() {
        // 异步任务
        return CompletableFuture.completedFuture("result");
    }
}
```

## 总结

### 注解核心知识点

1. **注解本质**：继承Annotation接口的接口
2. **处理机制**：编译时（APT）、类加载时（字节码增强）、运行时（反射）
3. **元注解**：@Target、@Retention、@Documented、@Inherited
4. **常用注解**：
   - IoC：@Component、@Service、@Repository、@Autowired
   - MVC：@Controller、@RestController、@RequestMapping
   - 配置：@Configuration、@Bean、@Value
   - 事务：@Transactional
   - AOP：@Aspect、@Before、@After、@Around

### 面试准备建议

1. **理解原理**：掌握注解的本质和处理机制
2. **熟悉分类**：了解Java标准注解和Spring注解
3. **掌握用法**：知道各注解的使用场景和参数
4. **注意陷阱**：了解常见的失效场景（如@Transactional）
5. **实践经验**：能够举例说明在项目中的应用

通过掌握这些内容，您就能在面试中自信地回答注解相关的问题了！





