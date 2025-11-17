# Java String类型完整指南

## 目录
1. [String基础概念](#string基础概念)
2. [String的特性](#string的特性)
3. [String的创建方式](#string的创建方式)
4. [String常用方法](#string常用方法)
5. [String与字符数组](#string与字符数组)
6. [String的比较](#string的比较)
7. [String的拼接](#string的拼接)
8. [StringBuilder和StringBuffer](#stringbuilder和stringbuffer)
9. [String的不可变性](#string的不可变性)
10. [String常见面试题](#string常见面试题)
11. [最佳实践](#最佳实践)

## String基础概念

### 什么是String？

String是Java中最常用的类之一，用于表示字符串。它是一个**不可变**的字符序列。

```java
public final class String
    implements java.io.Serializable, Comparable<String>, CharSequence {
    // String的内部实现（JDK 9+）
    private final byte[] value;  // 字符数组
    private final byte coder;    // 编码器（LATIN1或UTF16）
}
```

### String的特点

1. **不可变性** - 一旦创建，内容不能被修改
2. **线程安全** - 因为不可变，所以天然线程安全
3. **常量池** - 字符串常量存储在字符串常量池中
4. **final类** - String类是final的，不能被继承

## String的特性

### 1. 不可变性（Immutable）

```java
public class StringImmutability {
    public static void main(String[] args) {
        String str = "Hello";
        str = str + " World";  // 创建了新的String对象，原来的"Hello"没有改变
        
        // 原理解释
        String s1 = "Java";
        String s2 = s1;       // s1和s2指向同一个对象
        s1 = "Python";        // s1指向新对象，s2仍然指向"Java"
        
        System.out.println(s1);  // Python
        System.out.println(s2);  // Java
    }
}
```

### 2. 字符串常量池（String Pool）

```java
public class StringPool {
    public static void main(String[] args) {
        // 字符串字面量存储在常量池中
        String s1 = "Hello";
        String s2 = "Hello";
        System.out.println(s1 == s2);  // true，指向同一个对象
        
        // new创建的String在堆中
        String s3 = new String("Hello");
        String s4 = new String("Hello");
        System.out.println(s3 == s4);  // false，不同对象
        
        // intern()方法将字符串放入常量池
        String s5 = s3.intern();
        System.out.println(s1 == s5);  // true，都指向常量池中的"Hello"
    }
}
```

```
内存结构图：
┌─────────────────────────────────────────┐
│           字符串常量池（方法区）           │
│  ┌──────────────────────────────────┐   │
│  │ "Hello" (s1和s2指向这里)         │   │
│  └──────────────────────────────────┘   │
└─────────────────────────────────────────┘

┌─────────────────────────────────────────┐
│                堆内存                     │
│  ┌──────────────┐   ┌──────────────┐   │
│  │ "Hello" (s3) │   │ "Hello" (s4) │   │
│  └──────────────┘   └──────────────┘   │
└─────────────────────────────────────────┘
```

## String的创建方式

### 1. 字面量创建

```java
public class StringCreation {
    public static void main(String[] args) {
        // 方式1：字面量（推荐）
        String s1 = "Hello";
        
        // 方式2：使用new关键字
        String s2 = new String("Hello");
        
        // 方式3：字符数组
        char[] chars = {'H', 'e', 'l', 'l', 'o'};
        String s3 = new String(chars);
        
        // 方式4：字节数组
        byte[] bytes = {72, 101, 108, 108, 111};
        String s4 = new String(bytes);
        
        // 方式5：StringBuilder/StringBuffer
        StringBuilder sb = new StringBuilder("Hello");
        String s5 = sb.toString();
        
        System.out.println(s1);  // Hello
        System.out.println(s2);  // Hello
        System.out.println(s3);  // Hello
        System.out.println(s4);  // Hello
        System.out.println(s5);  // Hello
    }
}
```

### 2. 创建方式对比

| 创建方式 | 存储位置 | 性能 | 推荐度 |
|---------|---------|------|--------|
| 字面量 `"Hello"` | 常量池 | 高 | ⭐⭐⭐⭐⭐ |
| `new String("Hello")` | 堆 | 低 | ⭐ |
| 字符数组 | 堆 | 中 | ⭐⭐⭐ |
| StringBuilder | 堆 | 高 | ⭐⭐⭐⭐ |

## String常用方法

### 1. 长度和判空

```java
public class StringLength {
    public static void main(String[] args) {
        String str = "Hello World";
        
        // 获取长度
        int length = str.length();
        System.out.println("长度: " + length);  // 11
        
        // 判空
        String empty = "";
        String nullStr = null;
        
        System.out.println(empty.isEmpty());           // true
        System.out.println(empty.length() == 0);       // true
        // System.out.println(nullStr.isEmpty());      // NullPointerException
        
        // 判空最佳实践
        if (str != null && !str.isEmpty()) {
            System.out.println("字符串不为空");
        }
        
        // 判空白（Java 11+）
        String blank = "   ";
        System.out.println(blank.isBlank());           // true
    }
}
```

### 2. 字符访问

```java
public class CharAccess {
    public static void main(String[] args) {
        String str = "Hello";
        
        // 获取指定位置的字符
        char ch = str.charAt(0);
        System.out.println("第一个字符: " + ch);  // H
        
        // 获取字符数组
        char[] chars = str.toCharArray();
        System.out.println("字符数组: " + Arrays.toString(chars));
        
        // 遍历字符串
        for (int i = 0; i < str.length(); i++) {
            System.out.print(str.charAt(i) + " ");
        }
        System.out.println();
        
        // 使用增强for循环
        for (char c : str.toCharArray()) {
            System.out.print(c + " ");
        }
        System.out.println();
    }
}
```

### 3. 子字符串

```java
public class SubString {
    public static void main(String[] args) {
        String str = "Hello World";
        
        // substring(int beginIndex)
        String sub1 = str.substring(6);
        System.out.println(sub1);  // World
        
        // substring(int beginIndex, int endIndex)
        String sub2 = str.substring(0, 5);
        System.out.println(sub2);  // Hello
        
        // 注意：endIndex是不包含的
        String sub3 = str.substring(6, 11);
        System.out.println(sub3);  // World
    }
}
```

### 4. 查找和匹配

```java
public class StringSearch {
    public static void main(String[] args) {
        String str = "Hello World, Hello Java";
        
        // indexOf - 查找字符或子串首次出现的位置
        int index1 = str.indexOf('o');
        System.out.println("首次出现'o'的位置: " + index1);  // 4
        
        int index2 = str.indexOf("Hello");
        System.out.println("首次出现'Hello'的位置: " + index2);  // 0
        
        int index3 = str.indexOf("Hello", 1);  // 从位置1开始查找
        System.out.println("从位置1开始查找'Hello': " + index3);  // 13
        
        // lastIndexOf - 查找字符或子串最后出现的位置
        int index4 = str.lastIndexOf('o');
        System.out.println("最后出现'o'的位置: " + index4);  // 22
        
        // contains - 判断是否包含子串
        boolean contains = str.contains("World");
        System.out.println("包含'World': " + contains);  // true
        
        // startsWith - 判断是否以指定前缀开始
        boolean starts = str.startsWith("Hello");
        System.out.println("以'Hello'开始: " + starts);  // true
        
        // endsWith - 判断是否以指定后缀结束
        boolean ends = str.endsWith("Java");
        System.out.println("以'Java'结束: " + ends);  // true
        
        // matches - 正则表达式匹配
        String email = "test@example.com";
        boolean isEmail = email.matches("^[\\w-\\.]+@[\\w-]+\\.[a-z]{2,}$");
        System.out.println("是否为邮箱: " + isEmail);  // true
    }
}
```

### 5. 大小写转换

```java
public class CaseConversion {
    public static void main(String[] args) {
        String str = "Hello World";
        
        // 转大写
        String upper = str.toUpperCase();
        System.out.println(upper);  // HELLO WORLD
        
        // 转小写
        String lower = str.toLowerCase();
        System.out.println(lower);  // hello world
        
        // 注意：原字符串不变
        System.out.println(str);    // Hello World
    }
}
```

### 6. 去除空格

```java
public class TrimString {
    public static void main(String[] args) {
        String str = "  Hello World  ";
        
        // trim - 去除首尾空格
        String trimmed = str.trim();
        System.out.println("[" + trimmed + "]");  // [Hello World]
        
        // strip - 去除首尾空格（Java 11+，支持Unicode空白字符）
        String stripped = str.strip();
        System.out.println("[" + stripped + "]");  // [Hello World]
        
        // stripLeading - 去除开头空格
        String leading = str.stripLeading();
        System.out.println("[" + leading + "]");  // [Hello World  ]
        
        // stripTrailing - 去除结尾空格
        String trailing = str.stripTrailing();
        System.out.println("[" + trailing + "]");  // [  Hello World]
    }
}
```

### 7. 替换

```java
public class StringReplace {
    public static void main(String[] args) {
        String str = "Hello World, Hello Java";
        
        // replace - 替换所有匹配的字符或子串
        String replaced1 = str.replace('o', '0');
        System.out.println(replaced1);  // Hell0 W0rld, Hell0 Java
        
        String replaced2 = str.replace("Hello", "Hi");
        System.out.println(replaced2);  // Hi World, Hi Java
        
        // replaceFirst - 替换首次匹配的子串
        String replaced3 = str.replaceFirst("Hello", "Hi");
        System.out.println(replaced3);  // Hi World, Hello Java
        
        // replaceAll - 使用正则表达式替换
        String str2 = "Java123Python456Ruby";
        String replaced4 = str2.replaceAll("\\d+", "-");
        System.out.println(replaced4);  // Java-Python-Ruby
    }
}
```

### 8. 分割

```java
public class StringSplit {
    public static void main(String[] args) {
        // split - 分割字符串
        String str1 = "apple,banana,orange";
        String[] fruits = str1.split(",");
        System.out.println(Arrays.toString(fruits));  // [apple, banana, orange]
        
        // 限制分割次数
        String str2 = "a:b:c:d:e";
        String[] parts = str2.split(":", 3);
        System.out.println(Arrays.toString(parts));  // [a, b, c:d:e]
        
        // 正则表达式分割
        String str3 = "a1b2c3d";
        String[] letters = str3.split("\\d+");
        System.out.println(Arrays.toString(letters));  // [a, b, c, d]
        
        // 注意：特殊字符需要转义
        String str4 = "a.b.c";
        String[] parts2 = str4.split("\\.");  // 需要转义
        System.out.println(Arrays.toString(parts2));  // [a, b, c]
    }
}
```

### 9. 连接

```java
public class StringJoin {
    public static void main(String[] args) {
        // join - 用指定分隔符连接字符串
        String joined1 = String.join(", ", "apple", "banana", "orange");
        System.out.println(joined1);  // apple, banana, orange
        
        // 连接数组
        String[] fruits = {"apple", "banana", "orange"};
        String joined2 = String.join(", ", fruits);
        System.out.println(joined2);  // apple, banana, orange
        
        // 连接集合
        List<String> list = Arrays.asList("Java", "Python", "Ruby");
        String joined3 = String.join(" | ", list);
        System.out.println(joined3);  // Java | Python | Ruby
    }
}
```

### 10. 格式化

```java
public class StringFormat {
    public static void main(String[] args) {
        // format - 格式化字符串
        String name = "Alice";
        int age = 25;
        double score = 95.5;
        
        String formatted = String.format("姓名: %s, 年龄: %d, 分数: %.2f", name, age, score);
        System.out.println(formatted);  // 姓名: Alice, 年龄: 25, 分数: 95.50
        
        // 常用格式说明符
        System.out.println(String.format("%d", 123));           // 整数
        System.out.println(String.format("%f", 3.14159));       // 浮点数
        System.out.println(String.format("%.2f", 3.14159));     // 保留2位小数
        System.out.println(String.format("%s", "Hello"));       // 字符串
        System.out.println(String.format("%c", 'A'));           // 字符
        System.out.println(String.format("%b", true));          // 布尔值
        System.out.println(String.format("%x", 255));           // 十六进制
        System.out.println(String.format("%o", 8));             // 八进制
        System.out.println(String.format("%e", 1000.0));        // 科学计数法
        
        // 宽度和对齐
        System.out.println(String.format("|%10s|", "Hello"));   // 右对齐，宽度10
        System.out.println(String.format("|%-10s|", "Hello"));  // 左对齐，宽度10
        System.out.println(String.format("|%010d|", 123));      // 用0填充
    }
}
```

## String与字符数组

### 转换操作

```java
public class StringCharArray {
    public static void main(String[] args) {
        String str = "Hello";
        
        // String转char数组
        char[] chars = str.toCharArray();
        System.out.println(Arrays.toString(chars));  // [H, e, l, l, o]
        
        // char数组转String
        char[] chars2 = {'W', 'o', 'r', 'l', 'd'};
        String str2 = new String(chars2);
        System.out.println(str2);  // World
        
        // 部分转换
        String str3 = new String(chars2, 1, 3);  // 从索引1开始，长度为3
        System.out.println(str3);  // orl
        
        // 获取字符
        char ch = str.charAt(0);
        System.out.println(ch);  // H
        
        // 获取字节数组
        byte[] bytes = str.getBytes();
        System.out.println(Arrays.toString(bytes));  // [72, 101, 108, 108, 111]
        
        // 字节数组转String
        String str4 = new String(bytes);
        System.out.println(str4);  // Hello
    }
}
```

## String的比较

### 1. == vs equals()

```java
public class StringComparison {
    public static void main(String[] args) {
        // == 比较引用（地址）
        String s1 = "Hello";
        String s2 = "Hello";
        String s3 = new String("Hello");
        
        System.out.println(s1 == s2);        // true（都指向常量池）
        System.out.println(s1 == s3);        // false（s3在堆中）
        
        // equals() 比较内容
        System.out.println(s1.equals(s2));   // true
        System.out.println(s1.equals(s3));   // true
        
        // equalsIgnoreCase() 忽略大小写比较
        String s4 = "hello";
        System.out.println(s1.equalsIgnoreCase(s4));  // true
    }
}
```

### 2. compareTo()

```java
public class StringCompareTo {
    public static void main(String[] args) {
        String s1 = "abc";
        String s2 = "abc";
        String s3 = "abd";
        String s4 = "ab";
        
        // compareTo() 按字典序比较
        System.out.println(s1.compareTo(s2));  // 0（相等）
        System.out.println(s1.compareTo(s3));  // -1（s1 < s3）
        System.out.println(s1.compareTo(s4));  // 1（s1 > s4）
        
        // compareToIgnoreCase() 忽略大小写比较
        String s5 = "ABC";
        System.out.println(s1.compareToIgnoreCase(s5));  // 0
    }
}
```

### 3. 比较方法总结

| 方法 | 功能 | 返回值 |
|------|------|--------|
| `==` | 比较引用地址 | boolean |
| `equals()` | 比较内容 | boolean |
| `equalsIgnoreCase()` | 忽略大小写比较内容 | boolean |
| `compareTo()` | 字典序比较 | int（0, >0, <0） |
| `compareToIgnoreCase()` | 忽略大小写字典序比较 | int |

## String的拼接

### 拼接方式对比

```java
public class StringConcatenation {
    public static void main(String[] args) {
        // 方式1：+ 运算符（简单情况）
        String s1 = "Hello" + " " + "World";
        System.out.println(s1);  // Hello World
        
        // 方式2：concat()
        String s2 = "Hello".concat(" ").concat("World");
        System.out.println(s2);  // Hello World
        
        // 方式3：StringBuilder（推荐，循环中使用）
        StringBuilder sb = new StringBuilder();
        sb.append("Hello").append(" ").append("World");
        String s3 = sb.toString();
        System.out.println(s3);  // Hello World
        
        // 方式4：StringBuffer（线程安全）
        StringBuffer sbuf = new StringBuffer();
        sbuf.append("Hello").append(" ").append("World");
        String s4 = sbuf.toString();
        System.out.println(s4);  // Hello World
        
        // 方式5：String.join()（Java 8+）
        String s5 = String.join(" ", "Hello", "World");
        System.out.println(s5);  // Hello World
        
        // 性能对比示例
        performanceTest();
    }
    
    public static void performanceTest() {
        int n = 10000;
        
        // + 运算符（性能最差）
        long start = System.currentTimeMillis();
        String s = "";
        for (int i = 0; i < n; i++) {
            s += "a";
        }
        long end = System.currentTimeMillis();
        System.out.println("+ 运算符耗时: " + (end - start) + "ms");
        
        // StringBuilder（性能最好）
        start = System.currentTimeMillis();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < n; i++) {
            sb.append("a");
        }
        String s2 = sb.toString();
        end = System.currentTimeMillis();
        System.out.println("StringBuilder耗时: " + (end - start) + "ms");
    }
}
```

## StringBuilder和StringBuffer

### 对比分析

```java
public class StringBuilderVsBuffer {
    
    public static void main(String[] args) {
        // StringBuilder - 线程不安全，性能高
        StringBuilder sb = new StringBuilder();
        sb.append("Hello");
        sb.append(" ");
        sb.append("World");
        System.out.println(sb.toString());  // Hello World
        
        // StringBuffer - 线程安全，性能较低
        StringBuffer sbuf = new StringBuffer();
        sbuf.append("Hello");
        sbuf.append(" ");
        sbuf.append("World");
        System.out.println(sbuf.toString());  // Hello World
    }
}
```

### 特点对比

| 特性 | String | StringBuilder | StringBuffer |
|------|--------|---------------|--------------|
| 可变性 | 不可变 | 可变 | 可变 |
| 线程安全 | 安全 | 不安全 | 安全 |
| 性能 | 低（拼接时） | 高 | 中 |
| 使用场景 | 不变字符串 | 单线程拼接 | 多线程拼接 |

### StringBuilder常用方法

```java
public class StringBuilderMethods {
    public static void main(String[] args) {
        StringBuilder sb = new StringBuilder("Hello");
        
        // append - 追加
        sb.append(" World");
        System.out.println(sb);  // Hello World
        
        // insert - 插入
        sb.insert(5, ",");
        System.out.println(sb);  // Hello, World
        
        // delete - 删除
        sb.delete(5, 6);  // 删除逗号
        System.out.println(sb);  // Hello World
        
        // replace - 替换
        sb.replace(6, 11, "Java");
        System.out.println(sb);  // Hello Java
        
        // reverse - 反转
        sb.reverse();
        System.out.println(sb);  // avaJ olleH
        sb.reverse();  // 恢复
        
        // charAt - 获取字符
        char ch = sb.charAt(0);
        System.out.println(ch);  // H
        
        // setCharAt - 设置字符
        sb.setCharAt(0, 'h');
        System.out.println(sb);  // hello Java
        
        // length - 长度
        int len = sb.length();
        System.out.println("长度: " + len);  // 10
        
        // capacity - 容量
        int cap = sb.capacity();
        System.out.println("容量: " + cap);  // 21（初始16 + 扩容）
        
        // substring - 子字符串
        String sub = sb.substring(0, 5);
        System.out.println(sub);  // hello
        
        // toString - 转String
        String str = sb.toString();
        System.out.println(str);  // hello Java
    }
}
```

## String的不可变性

### 为什么String是不可变的？

```java
public class StringImmutability {
    
    public static void main(String[] args) {
        // 1. String内部使用final数组存储
        String str = "Hello";
        // str的value字段是final byte[]，不能被修改
        
        // 2. 修改String实际上创建新对象
        String s1 = "Hello";
        String s2 = s1.concat(" World");  // 创建新对象
        System.out.println(s1);  // Hello（原字符串不变）
        System.out.println(s2);  // Hello World（新对象）
        
        // 3. 字符串常量池重用
        String s3 = "Java";
        String s4 = "Java";
        System.out.println(s3 == s4);  // true（因为不可变，可以安全共享）
    }
}
```

### 不可变性的优势

1. **线程安全** - 多线程环境下无需同步
2. **可以缓存哈希值** - String的hashCode()只需计算一次
3. **字符串常量池** - 节省内存，提高性能
4. **安全性** - 防止被恶意修改（如数据库连接字符串）

## String常见面试题

### 1. String、StringBuilder、StringBuffer的区别

```java
/**
 * 区别总结：
 * 
 * String:
 * - 不可变
 * - 线程安全
 * - 适合不变的字符串
 * 
 * StringBuilder:
 * - 可变
 * - 线程不安全
 * - 适合单线程字符串拼接
 * - 性能最高
 * 
 * StringBuffer:
 * - 可变
 * - 线程安全
 * - 适合多线程字符串拼接
 * - 性能中等
 */
```

### 2. String创建了几个对象？

```java
public class StringObjectCount {
    public static void main(String[] args) {
        // 问题1：创建了几个对象？
        String s1 = "Hello";
        // 答：1个对象（在常量池中）
        
        // 问题2：创建了几个对象？
        String s2 = new String("Hello");
        // 答：2个对象（1个在常量池，1个在堆中）
        
        // 问题3：创建了几个对象？
        String s3 = "Hello";
        String s4 = "World";
        String s5 = "HelloWorld";
        String s6 = s3 + s4;
        // 答：s3、s4、s5各1个对象在常量池
        //     s6创建1个StringBuilder，然后toString()创建1个String对象
        
        // 问题4：创建了几个对象？
        String s7 = "Hello" + "World";
        // 答：1个对象（编译器优化，直接创建"HelloWorld"）
    }
}
```

### 3. String的intern()方法

```java
public class StringIntern {
    public static void main(String[] args) {
        String s1 = new String("Hello");
        String s2 = s1.intern();  // 将s1的值放入常量池，返回常量池中的引用
        String s3 = "Hello";
        
        System.out.println(s1 == s2);  // false（s1在堆，s2在常量池）
        System.out.println(s2 == s3);  // true（都在常量池）
        
        // intern()的作用：
        // 1. 如果常量池中已有该字符串，返回常量池中的引用
        // 2. 如果没有，将该字符串加入常量池，并返回引用
    }
}
```

### 4. String的"+"操作

```java
public class StringPlus {
    public static void main(String[] args) {
        // 编译时常量
        String s1 = "Hello" + "World";
        // 编译器优化为: String s1 = "HelloWorld";
        
        // 运行时拼接
        String s2 = "Hello";
        String s3 = s2 + "World";
        // 编译器转换为:
        // StringBuilder sb = new StringBuilder();
        // sb.append(s2);
        // sb.append("World");
        // String s3 = sb.toString();
        
        // 循环中的拼接
        String s4 = "";
        for (int i = 0; i < 10; i++) {
            s4 += i;  // 每次都创建新的StringBuilder，性能差
        }
        
        // 正确做法
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 10; i++) {
            sb.append(i);  // 只创建一次StringBuilder
        }
        String s5 = sb.toString();
    }
}
```

## 最佳实践

### 1. 字符串创建

```java
// ✅ 推荐：使用字面量
String s1 = "Hello";

// ❌ 避免：不必要的new
String s2 = new String("Hello");  // 浪费内存
```

### 2. 字符串拼接

```java
// ✅ 推荐：简单拼接使用+
String s1 = "Hello" + " " + "World";

// ✅ 推荐：循环中使用StringBuilder
StringBuilder sb = new StringBuilder();
for (int i = 0; i < 100; i++) {
    sb.append(i);
}
String result = sb.toString();

// ❌ 避免：循环中使用+
String s2 = "";
for (int i = 0; i < 100; i++) {
    s2 += i;  // 性能差
}
```

### 3. 字符串比较

```java
// ✅ 推荐：使用equals()比较内容
if (s1.equals(s2)) {
    // ...
}

// ❌ 避免：使用==比较内容
if (s1 == s2) {  // 比较的是引用，不是内容
    // ...
}

// ✅ 推荐：防止空指针
if ("expected".equals(input)) {  // 常量在前
    // ...
}

// ❌ 避免：可能空指针
if (input.equals("expected")) {  // input可能为null
    // ...
}
```

### 4. 空值检查

```java
// ✅ 推荐：完整的空值检查
if (str != null && !str.isEmpty()) {
    // ...
}

// ✅ 推荐：使用工具类（如Apache Commons）
if (StringUtils.isNotBlank(str)) {
    // ...
}

// ❌ 避免：只检查null
if (str != null) {  // 可能是空字符串
    // ...
}
```

### 5. 性能优化

```java
// ✅ 推荐：指定StringBuilder初始容量
StringBuilder sb = new StringBuilder(100);  // 避免扩容

// ✅ 推荐：重用StringBuilder
StringBuilder sb = new StringBuilder();
for (String item : items) {
    sb.setLength(0);  // 清空
    sb.append(item);
    // 使用sb
}

// ✅ 推荐：使用intern()节省内存（大量重复字符串）
String s1 = new String("重复字符串").intern();
```

## 总结

### String核心要点

1. **不可变性** - String对象一旦创建不能修改
2. **常量池** - 字符串字面量存储在常量池中
3. **线程安全** - 因为不可变，所以线程安全
4. **性能考虑** - 大量拼接使用StringBuilder
5. **正确比较** - 使用equals()而不是==

### 选择建议

| 场景 | 推荐使用 |
|------|---------|
| 字符串不变 | String |
| 单线程拼接 | StringBuilder |
| 多线程拼接 | StringBuffer |
| 简单拼接 | + 运算符 |
| 格式化 | String.format() |

通过掌握这些String知识点，您将能够更高效地处理Java中的字符串操作！


