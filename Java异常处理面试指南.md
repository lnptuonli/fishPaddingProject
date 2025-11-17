# Java异常处理面试指南

## 目录
1. [异常处理概述](#异常处理概述)
2. [异常体系结构](#异常体系结构)
3. [异常分类详解](#异常分类详解)
4. [常见异常类型](#常见异常类型)
5. [异常处理机制](#异常处理机制)
6. [异常处理最佳实践](#异常处理最佳实践)
7. [面试常见问题](#面试常见问题)
8. [实际代码示例](#实际代码示例)
9. [异常处理陷阱](#异常处理陷阱)
10. [总结](#总结)

## 异常处理概述

### 什么是异常？
异常（Exception）是程序运行时发生的错误或意外情况，它会中断正常的程序执行流程。

### 异常处理的重要性
- **程序健壮性** - 防止程序崩溃
- **用户体验** - 提供友好的错误信息
- **调试便利** - 便于定位和解决问题
- **系统稳定性** - 确保系统持续运行

## 异常体系结构

```
Throwable (可抛出)
├── Error (错误)
│   ├── OutOfMemoryError
│   ├── StackOverflowError
│   ├── NoClassDefFoundError
│   └── ...
└── Exception (异常)
    ├── RuntimeException (运行时异常/非检查异常)
    │   ├── NullPointerException
    │   ├── ArrayIndexOutOfBoundsException
    │   ├── IllegalArgumentException
    │   ├── ClassCastException
    │   ├── NumberFormatException
    │   └── ...
    └── 其他Exception (检查异常)
        ├── IOException
        ├── SQLException
        ├── ClassNotFoundException
        ├── ParseException
        └── ...
```

## 异常分类详解

### 1. Error（错误）
**特点**：
- 严重错误，程序无法处理
- 通常由JVM抛出
- 不需要捕获，也无法恢复
- 程序应该终止

**常见Error类型**：

```java
// OutOfMemoryError - 内存溢出
public class OutOfMemoryErrorExample {
    public static void main(String[] args) {
        List<byte[]> list = new ArrayList<>();
        while (true) {
            list.add(new byte[1024 * 1024]); // 每次分配1MB
        }
    }
}

// StackOverflowError - 栈溢出
public class StackOverflowErrorExample {
    public static void recursiveMethod() {
        recursiveMethod(); // 无限递归
    }
    
    public static void main(String[] args) {
        recursiveMethod();
    }
}

// NoClassDefFoundError - 类定义未找到
// 编译时存在，运行时找不到类文件
```

### 2. Exception（异常）

#### 2.1 RuntimeException（运行时异常/非检查异常）
**特点**：
- 程序运行时可能发生的异常
- 不需要在方法签名中声明
- 可以选择性捕获
- 通常由编程错误引起

**常见RuntimeException类型**：

```java
// NullPointerException - 空指针异常
public class NullPointerExceptionExample {
    public static void main(String[] args) {
        String str = null;
        System.out.println(str.length()); // 抛出NullPointerException
    }
}

// ArrayIndexOutOfBoundsException - 数组越界异常
public class ArrayIndexOutOfBoundsExceptionExample {
    public static void main(String[] args) {
        int[] arr = {1, 2, 3};
        System.out.println(arr[3]); // 抛出ArrayIndexOutOfBoundsException
    }
}

// IllegalArgumentException - 非法参数异常
public class IllegalArgumentExceptionExample {
    public static void setAge(int age) {
        if (age < 0 || age > 150) {
            throw new IllegalArgumentException("年龄必须在0-150之间");
        }
    }
}

// ClassCastException - 类型转换异常
public class ClassCastExceptionExample {
    public static void main(String[] args) {
        Object obj = "Hello";
        Integer num = (Integer) obj; // 抛出ClassCastException
    }
}

// NumberFormatException - 数字格式异常
public class NumberFormatExceptionExample {
    public static void main(String[] args) {
        String str = "abc";
        int num = Integer.parseInt(str); // 抛出NumberFormatException
    }
}

// ArithmeticException - 算术异常
public class ArithmeticExceptionExample {
    public static void main(String[] args) {
        int result = 10 / 0; // 抛出ArithmeticException
    }
}

// IndexOutOfBoundsException - 索引越界异常
public class IndexOutOfBoundsExceptionExample {
    public static void main(String[] args) {
        List<String> list = Arrays.asList("a", "b", "c");
        System.out.println(list.get(3)); // 抛出IndexOutOfBoundsException
    }
}

// ConcurrentModificationException - 并发修改异常
public class ConcurrentModificationExceptionExample {
    public static void main(String[] args) {
        List<String> list = new ArrayList<>(Arrays.asList("a", "b", "c"));
        for (String item : list) {
            if ("b".equals(item)) {
                list.remove(item); // 抛出ConcurrentModificationException
            }
        }
    }
}
```

#### 2.2 检查异常（Checked Exception）
**特点**：
- 必须在方法签名中声明
- 必须被捕获或向上抛出
- 通常由外部因素引起
- 编译时检查

**常见检查异常类型**：

```java
// IOException - 输入输出异常
public class IOExceptionExample {
    public static void readFile(String fileName) throws IOException {
        FileReader reader = new FileReader(fileName);
        // 文件操作
        reader.close();
    }
}

// SQLException - 数据库异常
public class SQLExceptionExample {
    public static void executeQuery(String sql) throws SQLException {
        Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/test");
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery(sql);
        // 数据库操作
    }
}

// ClassNotFoundException - 类未找到异常
public class ClassNotFoundExceptionExample {
    public static void loadClass(String className) throws ClassNotFoundException {
        Class<?> clazz = Class.forName(className);
    }
}

// ParseException - 解析异常
public class ParseExceptionExample {
    public static void parseDate(String dateStr) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date date = sdf.parse(dateStr);
    }
}

// FileNotFoundException - 文件未找到异常
public class FileNotFoundExceptionExample {
    public static void readFile(String fileName) throws FileNotFoundException {
        FileInputStream fis = new FileInputStream(fileName);
        // 文件操作
    }
}

// InterruptedException - 中断异常
public class InterruptedExceptionExample {
    public static void sleepMethod() throws InterruptedException {
        Thread.sleep(1000); // 可能被中断
    }
}
```

## 常见异常类型

### 按使用频率排序的常见异常

```java
public class CommonExceptions {
    
    // 1. NullPointerException - 最常见
    public void nullPointerExample() {
        String str = null;
        // str.length(); // 抛出NullPointerException
        
        // 防御性编程
        if (str != null) {
            System.out.println(str.length());
        }
    }
    
    // 2. ArrayIndexOutOfBoundsException - 数组越界
    public void arrayIndexExample() {
        int[] arr = {1, 2, 3};
        // System.out.println(arr[3]); // 抛出ArrayIndexOutOfBoundsException
        
        // 防御性编程
        if (arr.length > 3) {
            System.out.println(arr[3]);
        }
    }
    
    // 3. IllegalArgumentException - 参数异常
    public void illegalArgumentExample(int age) {
        if (age < 0 || age > 150) {
            throw new IllegalArgumentException("年龄必须在0-150之间");
        }
    }
    
    // 4. NumberFormatException - 数字格式异常
    public void numberFormatExample() {
        String str = "abc";
        try {
            int num = Integer.parseInt(str);
        } catch (NumberFormatException e) {
            System.out.println("数字格式错误: " + e.getMessage());
        }
    }
    
    // 5. ClassCastException - 类型转换异常
    public void classCastExample() {
        Object obj = "Hello";
        if (obj instanceof String) {
            String str = (String) obj;
            System.out.println(str);
        }
    }
    
    // 6. IOException - 输入输出异常
    public void ioExceptionExample() {
        try {
            FileReader reader = new FileReader("test.txt");
            reader.close();
        } catch (IOException e) {
            System.out.println("IO异常: " + e.getMessage());
        }
    }
    
    // 7. SQLException - 数据库异常
    public void sqlExceptionExample() {
        try {
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/test");
            conn.close();
        } catch (SQLException e) {
            System.out.println("数据库异常: " + e.getMessage());
        }
    }
}
```

## 异常处理机制

### 1. try-catch-finally

```java
public class ExceptionHandlingMechanism {
    
    public void basicTryCatch() {
        try {
            // 可能抛出异常的代码
            int result = 10 / 0;
        } catch (ArithmeticException e) {
            // 捕获特定异常
            System.out.println("算术异常: " + e.getMessage());
        } catch (Exception e) {
            // 捕获通用异常（必须放在最后）
            System.out.println("其他异常: " + e.getMessage());
        } finally {
            // 无论是否发生异常都会执行
            System.out.println("finally块执行");
        }
    }
    
    public void tryWithResources() {
        // Java 7+ 自动资源管理
        try (FileReader reader = new FileReader("test.txt");
             BufferedReader bufferedReader = new BufferedReader(reader)) {
            String line = bufferedReader.readLine();
            System.out.println(line);
        } catch (IOException e) {
            System.out.println("IO异常: " + e.getMessage());
        }
        // 资源会自动关闭
    }
    
    public void multipleCatchBlocks() {
        try {
            // 可能抛出多种异常的代码
            String str = null;
            int num = Integer.parseInt(str);
            System.out.println(num);
        } catch (NullPointerException e) {
            System.out.println("空指针异常");
        } catch (NumberFormatException e) {
            System.out.println("数字格式异常");
        } catch (Exception e) {
            System.out.println("其他异常");
        }
    }
}
```

### 2. throws声明

```java
public class ThrowsDeclaration {
    
    // 声明抛出检查异常
    public void methodWithThrows() throws IOException, SQLException {
        // 可能抛出IO异常或SQL异常的代码
        FileReader reader = new FileReader("test.txt");
        Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/test");
    }
    
    // 调用需要处理异常的方法
    public void callMethodWithThrows() {
        try {
            methodWithThrows();
        } catch (IOException e) {
            System.out.println("IO异常: " + e.getMessage());
        } catch (SQLException e) {
            System.out.println("SQL异常: " + e.getMessage());
        }
    }
    
    // 向上抛出异常
    public void propagateException() throws Exception {
        try {
            methodWithThrows();
        } catch (IOException | SQLException e) {
            // 重新包装异常
            throw new Exception("操作失败", e);
        }
    }
}
```

### 3. 自定义异常

```java
// 自定义业务异常
public class BusinessException extends Exception {
    private String errorCode;
    
    public BusinessException(String message) {
        super(message);
    }
    
    public BusinessException(String errorCode, String message) {
        super(message);
        this.errorCode = errorCode;
    }
    
    public BusinessException(String message, Throwable cause) {
        super(message, cause);
    }
    
    public String getErrorCode() {
        return errorCode;
    }
}

// 使用自定义异常
public class CustomExceptionExample {
    
    public void validateUser(String username, String password) throws BusinessException {
        if (username == null || username.trim().isEmpty()) {
            throw new BusinessException("USER001", "用户名不能为空");
        }
        
        if (password == null || password.length() < 6) {
            throw new BusinessException("USER002", "密码长度不能少于6位");
        }
    }
    
    public void handleUserValidation() {
        try {
            validateUser("", "123");
        } catch (BusinessException e) {
            System.out.println("业务异常: " + e.getErrorCode() + " - " + e.getMessage());
        }
    }
}
```

## 异常处理最佳实践

### 1. 异常处理原则

```java
public class ExceptionBestPractices {
    
    // 1. 具体异常优于通用异常
    public void specificException() {
        try {
            int num = Integer.parseInt("abc");
        } catch (NumberFormatException e) {
            // 具体异常，便于处理
            System.out.println("数字格式错误");
        }
        // 避免使用 catch (Exception e)
    }
    
    // 2. 早期抛出，延迟捕获
    public void earlyThrowLateCatch(String input) {
        if (input == null) {
            throw new IllegalArgumentException("输入不能为空");
        }
        
        if (input.trim().isEmpty()) {
            throw new IllegalArgumentException("输入不能为空字符串");
        }
        
        // 业务逻辑
    }
    
    // 3. 异常信息要清晰
    public void clearExceptionMessage(String fileName) {
        try {
            FileReader reader = new FileReader(fileName);
        } catch (FileNotFoundException e) {
            throw new RuntimeException("无法找到文件: " + fileName, e);
        }
    }
    
    // 4. 避免空的catch块
    public void avoidEmptyCatch() {
        try {
            // 可能抛出异常的代码
            int result = 10 / 0;
        } catch (ArithmeticException e) {
            // 不要留空，至少记录日志
            System.out.println("算术异常: " + e.getMessage());
            // 或者重新抛出
            // throw new RuntimeException("计算失败", e);
        }
    }
    
    // 5. 使用finally清理资源
    public void cleanupResources() {
        FileReader reader = null;
        try {
            reader = new FileReader("test.txt");
            // 文件操作
        } catch (IOException e) {
            System.out.println("IO异常: " + e.getMessage());
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    System.out.println("关闭文件失败: " + e.getMessage());
                }
            }
        }
    }
    
    // 6. 使用try-with-resources
    public void tryWithResources() {
        try (FileReader reader = new FileReader("test.txt");
             BufferedReader bufferedReader = new BufferedReader(reader)) {
            String line = bufferedReader.readLine();
            System.out.println(line);
        } catch (IOException e) {
            System.out.println("IO异常: " + e.getMessage());
        }
        // 资源会自动关闭
    }
}
```

### 2. 异常处理模式

```java
public class ExceptionHandlingPatterns {
    
    // 1. 重试模式
    public void retryPattern() {
        int maxRetries = 3;
        int retryCount = 0;
        
        while (retryCount < maxRetries) {
            try {
                // 可能失败的操作
                performOperation();
                break; // 成功则退出循环
            } catch (Exception e) {
                retryCount++;
                if (retryCount >= maxRetries) {
                    throw new RuntimeException("操作失败，已重试" + maxRetries + "次", e);
                }
                System.out.println("操作失败，重试第" + retryCount + "次");
            }
        }
    }
    
    // 2. 降级模式
    public String fallbackPattern() {
        try {
            return getDataFromPrimarySource();
        } catch (Exception e) {
            System.out.println("主数据源失败，使用备用数据源");
            try {
                return getDataFromBackupSource();
            } catch (Exception backupException) {
                return getDefaultData();
            }
        }
    }
    
    // 3. 断路器模式
    private boolean circuitBreakerOpen = false;
    private int failureCount = 0;
    private final int failureThreshold = 5;
    
    public String circuitBreakerPattern() {
        if (circuitBreakerOpen) {
            return getFallbackData();
        }
        
        try {
            String result = performOperation();
            failureCount = 0; // 重置失败计数
            return result;
        } catch (Exception e) {
            failureCount++;
            if (failureCount >= failureThreshold) {
                circuitBreakerOpen = true;
            }
            return getFallbackData();
        }
    }
    
    private void performOperation() throws Exception {
        // 模拟可能失败的操作
        if (Math.random() > 0.7) {
            throw new RuntimeException("操作失败");
        }
    }
    
    private String getDataFromPrimarySource() throws Exception {
        // 模拟主数据源
        throw new Exception("主数据源不可用");
    }
    
    private String getDataFromBackupSource() throws Exception {
        // 模拟备用数据源
        return "备用数据";
    }
    
    private String getDefaultData() {
        return "默认数据";
    }
    
    private String getFallbackData() {
        return "降级数据";
    }
}
```

## 面试常见问题

### 1. 基础概念问题

**Q: Error和Exception的区别？**
```java
// Error: 严重错误，程序无法处理，通常由JVM抛出
// Exception: 程序可以处理的异常

// Error示例
public void errorExample() {
    // OutOfMemoryError, StackOverflowError等
    // 程序应该终止，无法恢复
}

// Exception示例
public void exceptionExample() {
    try {
        int result = 10 / 0;
    } catch (ArithmeticException e) {
        // 可以处理并恢复
        System.out.println("除零异常，使用默认值");
    }
}
```

**Q: 检查异常和非检查异常的区别？**
```java
// 检查异常：必须在方法签名中声明，必须被处理
public void checkedException() throws IOException {
    FileReader reader = new FileReader("test.txt");
}

// 非检查异常：不需要在方法签名中声明，可以选择性处理
public void uncheckedException() {
    String str = null;
    str.length(); // 可能抛出NullPointerException
}
```

### 2. 异常处理机制问题

**Q: try-catch-finally的执行顺序？**
```java
public void executionOrder() {
    try {
        System.out.println("1. try块执行");
        throw new RuntimeException("测试异常");
    } catch (RuntimeException e) {
        System.out.println("2. catch块执行");
    } finally {
        System.out.println("3. finally块执行");
    }
    System.out.println("4. 方法继续执行");
}
// 输出顺序：1 -> 2 -> 3 -> 4
```

**Q: finally块什么时候不执行？**
```java
public void finallyNotExecute() {
    try {
        System.out.println("try块执行");
        System.exit(0); // 程序退出，finally不执行
    } finally {
        System.out.println("finally块不会执行");
    }
}
```

### 3. 实际应用问题

**Q: 如何设计异常处理策略？**
```java
public class ExceptionStrategy {
    
    // 分层异常处理
    public void layeredExceptionHandling() {
        try {
            // 业务逻辑层
            businessLogic();
        } catch (BusinessException e) {
            // 业务异常处理
            handleBusinessException(e);
        } catch (SystemException e) {
            // 系统异常处理
            handleSystemException(e);
        } catch (Exception e) {
            // 通用异常处理
            handleGenericException(e);
        }
    }
    
    // 异常转换
    public void exceptionTranslation() {
        try {
            // 底层操作
            lowLevelOperation();
        } catch (SQLException e) {
            // 转换为业务异常
            throw new BusinessException("数据操作失败", e);
        } catch (IOException e) {
            // 转换为系统异常
            throw new SystemException("文件操作失败", e);
        }
    }
    
    private void businessLogic() throws BusinessException {
        // 业务逻辑
    }
    
    private void lowLevelOperation() throws SQLException, IOException {
        // 底层操作
    }
    
    private void handleBusinessException(BusinessException e) {
        // 业务异常处理逻辑
    }
    
    private void handleSystemException(SystemException e) {
        // 系统异常处理逻辑
    }
    
    private void handleGenericException(Exception e) {
        // 通用异常处理逻辑
    }
}
```

## 实际代码示例

### 1. 文件操作异常处理

```java
public class FileOperationExample {
    
    public String readFileContent(String fileName) {
        StringBuilder content = new StringBuilder();
        
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = reader.readLine()) != null) {
                content.append(line).append("\n");
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException("文件不存在: " + fileName, e);
        } catch (IOException e) {
            throw new RuntimeException("读取文件失败: " + fileName, e);
        }
        
        return content.toString();
    }
    
    public void writeFileContent(String fileName, String content) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
            writer.write(content);
        } catch (IOException e) {
            throw new RuntimeException("写入文件失败: " + fileName, e);
        }
    }
}
```

### 2. 数据库操作异常处理

```java
public class DatabaseOperationExample {
    
    public User findUserById(Long id) {
        String sql = "SELECT * FROM users WHERE id = ?";
        
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setLong(1, id);
            ResultSet rs = stmt.executeQuery();
            
            if (rs.next()) {
                return new User(
                    rs.getLong("id"),
                    rs.getString("name"),
                    rs.getString("email")
                );
            }
            return null;
            
        } catch (SQLException e) {
            throw new DataAccessException("查询用户失败: " + id, e);
        }
    }
    
    public void saveUser(User user) {
        String sql = "INSERT INTO users (name, email) VALUES (?, ?)";
        
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            
            stmt.setString(1, user.getName());
            stmt.setString(2, user.getEmail());
            
            int affectedRows = stmt.executeUpdate();
            if (affectedRows == 0) {
                throw new DataAccessException("保存用户失败");
            }
            
            ResultSet generatedKeys = stmt.getGeneratedKeys();
            if (generatedKeys.next()) {
                user.setId(generatedKeys.getLong(1));
            }
            
        } catch (SQLException e) {
            throw new DataAccessException("保存用户失败", e);
        }
    }
    
    private Connection getConnection() throws SQLException {
        return DriverManager.getConnection("jdbc:mysql://localhost:3306/test");
    }
}
```

### 3. 网络请求异常处理

```java
public class NetworkRequestExample {
    
    public String sendHttpRequest(String url) {
        try {
            URL requestUrl = new URL(url);
            HttpURLConnection connection = (HttpURLConnection) requestUrl.openConnection();
            
            connection.setRequestMethod("GET");
            connection.setConnectTimeout(5000);
            connection.setReadTimeout(10000);
            
            int responseCode = connection.getResponseCode();
            if (responseCode != HttpURLConnection.HTTP_OK) {
                throw new HttpException("HTTP请求失败，状态码: " + responseCode);
            }
            
            try (BufferedReader reader = new BufferedReader(
                    new InputStreamReader(connection.getInputStream()))) {
                StringBuilder response = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    response.append(line);
                }
                return response.toString();
            }
            
        } catch (MalformedURLException e) {
            throw new NetworkException("URL格式错误: " + url, e);
        } catch (IOException e) {
            throw new NetworkException("网络请求失败: " + url, e);
        }
    }
}
```

## 异常处理陷阱

### 1. 常见陷阱

```java
public class ExceptionTraps {
    
    // 陷阱1: 捕获过于宽泛的异常
    public void trap1() {
        try {
            // 业务逻辑
            performOperation();
        } catch (Exception e) {
            // 过于宽泛，可能掩盖重要异常
            System.out.println("发生异常");
        }
    }
    
    // 陷阱2: 空的catch块
    public void trap2() {
        try {
            performOperation();
        } catch (Exception e) {
            // 空的catch块，异常被忽略
        }
    }
    
    // 陷阱3: 在finally块中抛出异常
    public void trap3() {
        try {
            performOperation();
        } finally {
            // 在finally中抛出异常会掩盖try块中的异常
            throw new RuntimeException("finally异常");
        }
    }
    
    // 陷阱4: 异常信息丢失
    public void trap4() {
        try {
            performOperation();
        } catch (Exception e) {
            // 重新抛出时丢失了原始异常信息
            throw new RuntimeException("操作失败");
        }
    }
    
    // 陷阱5: 资源泄漏
    public void trap5() {
        FileReader reader = null;
        try {
            reader = new FileReader("test.txt");
            // 如果这里抛出异常，reader可能不会被关闭
            performOperation();
        } catch (IOException e) {
            System.out.println("IO异常");
        }
        // 忘记关闭reader
    }
    
    private void performOperation() throws Exception {
        // 模拟操作
    }
}
```

### 2. 正确的做法

```java
public class CorrectExceptionHandling {
    
    // 正确做法1: 具体异常处理
    public void correct1() {
        try {
            performOperation();
        } catch (NullPointerException e) {
            System.out.println("空指针异常: " + e.getMessage());
        } catch (IllegalArgumentException e) {
            System.out.println("参数异常: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("其他异常: " + e.getMessage());
        }
    }
    
    // 正确做法2: 记录异常信息
    public void correct2() {
        try {
            performOperation();
        } catch (Exception e) {
            // 记录异常信息
            System.out.println("异常类型: " + e.getClass().getSimpleName());
            System.out.println("异常信息: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    // 正确做法3: 使用try-with-resources
    public void correct3() {
        try (FileReader reader = new FileReader("test.txt")) {
            performOperation();
        } catch (IOException e) {
            System.out.println("IO异常: " + e.getMessage());
        }
        // 资源会自动关闭
    }
    
    // 正确做法4: 保留异常链
    public void correct4() {
        try {
            performOperation();
        } catch (Exception e) {
            // 保留原始异常信息
            throw new RuntimeException("操作失败", e);
        }
    }
    
    // 正确做法5: 异常处理策略
    public void correct5() {
        try {
            performOperation();
        } catch (BusinessException e) {
            // 业务异常，返回默认值
            return getDefaultValue();
        } catch (SystemException e) {
            // 系统异常，记录日志并重新抛出
            logError(e);
            throw e;
        } catch (Exception e) {
            // 未知异常，记录日志并包装
            logError(e);
            throw new SystemException("未知错误", e);
        }
    }
    
    private void performOperation() throws Exception {
        // 模拟操作
    }
    
    private String getDefaultValue() {
        return "默认值";
    }
    
    private void logError(Exception e) {
        System.out.println("记录异常: " + e.getMessage());
    }
}
```

## 总结

### 异常处理核心要点

1. **异常分类**：
   - Error：严重错误，程序无法处理
   - RuntimeException：运行时异常，可选择处理
   - 检查异常：必须处理的异常

2. **处理原则**：
   - 具体异常优于通用异常
   - 早期抛出，延迟捕获
   - 异常信息要清晰
   - 避免空的catch块

3. **最佳实践**：
   - 使用try-with-resources管理资源
   - 保留异常链信息
   - 分层异常处理
   - 异常转换和包装

4. **常见陷阱**：
   - 捕获过于宽泛的异常
   - 空的catch块
   - 在finally中抛出异常
   - 资源泄漏

### 面试准备建议

1. **掌握异常体系结构** - 理解Error、Exception、RuntimeException的区别
2. **熟悉常见异常类型** - 知道什么情况下会抛出什么异常
3. **理解异常处理机制** - try-catch-finally、throws、自定义异常
4. **掌握最佳实践** - 知道如何正确设计异常处理策略
5. **了解常见陷阱** - 避免常见的异常处理错误

通过掌握这些内容，您就能在面试中自信地回答异常处理相关的问题了！



