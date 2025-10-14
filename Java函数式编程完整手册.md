# Java函数式编程完整手册

## 目录
1. [概述](#概述)
2. [函数式编程基础概念](#函数式编程基础概念)
3. [Lambda表达式](#lambda表达式)
4. [函数式接口](#函数式接口)
5. [Stream API](#stream-api)
6. [Optional类](#optional类)
7. [方法引用](#方法引用)
8. [高阶函数](#高阶函数)
9. [函数组合](#函数组合)
10. [不可变性](#不可变性)
11. [实际项目应用](#实际项目应用)
12. [性能考虑](#性能考虑)
13. [最佳实践](#最佳实践)
14. [常见陷阱](#常见陷阱)

## 概述

函数式编程（Functional Programming）是一种编程范式，它将计算视为数学函数的求值，避免使用可变状态和副作用。

### 函数式编程的核心特征

1. **不可变性** - 数据一旦创建就不能修改
2. **纯函数** - 相同输入总是产生相同输出，无副作用
3. **高阶函数** - 函数可以作为参数传递或返回值
4. **函数组合** - 通过组合简单函数构建复杂功能
5. **声明式编程** - 描述"做什么"而不是"怎么做"

### Java中的函数式编程支持

- **Java 8+**: Lambda表达式、Stream API、Optional
- **Java 9+**: 改进的Stream API
- **Java 10+**: var关键字、局部变量类型推断
- **Java 14+**: 记录类（Record）、模式匹配

## 函数式编程基础概念

### 纯函数 vs 非纯函数

```java
// ❌ 非纯函数 - 有副作用
public class NonPureFunction {
    private int counter = 0;
    
    public int increment() {
        return ++counter; // 修改了外部状态
    }
    
    public void printMessage(String message) {
        System.out.println(message); // 有副作用（IO操作）
    }
}

// ✅ 纯函数 - 无副作用
public class PureFunction {
    
    public int add(int a, int b) {
        return a + b; // 相同输入总是产生相同输出
    }
    
    public String toUpperCase(String input) {
        return input.toUpperCase(); // 不修改原字符串
    }
    
    public List<String> filterEmpty(List<String> list) {
        return list.stream()
            .filter(s -> s != null && !s.isEmpty())
            .collect(Collectors.toList()); // 返回新列表
    }
}
```

### 不可变性

```java
// ❌ 可变对象
public class MutablePerson {
    private String name;
    private int age;
    
    public void setName(String name) {
        this.name = name; // 可以修改
    }
    
    public void setAge(int age) {
        this.age = age; // 可以修改
    }
}

// ✅ 不可变对象
public class ImmutablePerson {
    private final String name;
    private final int age;
    
    public ImmutablePerson(String name, int age) {
        this.name = name;
        this.age = age;
    }
    
    public String getName() {
        return name;
    }
    
    public int getAge() {
        return age;
    }
    
    // 返回新对象而不是修改现有对象
    public ImmutablePerson withAge(int newAge) {
        return new ImmutablePerson(this.name, newAge);
    }
}

// 使用记录类（Java 14+）
public record Person(String name, int age) {
    // 自动生成不可变类
    // 自动生成equals、hashCode、toString方法
}
```

## Lambda表达式

### 基本语法

```java
// 基本语法：(参数列表) -> { 方法体 }
// 简化语法：(参数列表) -> 表达式

public class LambdaExamples {
    
    public void basicLambda() {
        // 传统匿名内部类
        Runnable oldWay = new Runnable() {
            @Override
            public void run() {
                System.out.println("Hello World");
            }
        };
        
        // Lambda表达式
        Runnable newWay = () -> System.out.println("Hello World");
        
        // 带参数的Lambda
        Comparator<String> comparator = (s1, s2) -> s1.compareTo(s2);
        
        // 简化写法
        Comparator<String> simpleComparator = String::compareTo;
    }
    
    public void lambdaWithParameters() {
        // 单个参数可以省略括号
        Function<String, Integer> length = s -> s.length();
        
        // 多个参数需要括号
        BiFunction<String, String, String> concat = (s1, s2) -> s1 + s2;
        
        // 复杂Lambda表达式
        Function<List<String>, List<String>> process = list -> {
            return list.stream()
                .filter(s -> s != null)
                .map(String::toUpperCase)
                .sorted()
                .collect(Collectors.toList());
        };
    }
}
```

### Lambda表达式的类型推断

```java
public class TypeInference {
    
    public void demonstrateTypeInference() {
        // 编译器可以推断类型
        List<String> names = Arrays.asList("Alice", "Bob", "Charlie");
        
        // 显式类型
        names.stream()
            .filter((String name) -> name.length() > 3)
            .forEach((String name) -> System.out.println(name));
        
        // 类型推断
        names.stream()
            .filter(name -> name.length() > 3)
            .forEach(name -> System.out.println(name));
        
        // 方法引用
        names.stream()
            .filter(name -> name.length() > 3)
            .forEach(System.out::println);
    }
}
```

## 函数式接口

### 内置函数式接口

```java
import java.util.function.*;

public class FunctionalInterfaces {
    
    public void demonstrateBuiltInInterfaces() {
        // Predicate<T> - 断言接口
        Predicate<String> isEmpty = s -> s == null || s.isEmpty();
        Predicate<Integer> isEven = n -> n % 2 == 0;
        
        // Function<T, R> - 函数接口
        Function<String, Integer> stringLength = String::length;
        Function<Integer, String> intToString = String::valueOf;
        
        // Consumer<T> - 消费者接口
        Consumer<String> printer = System.out::println;
        Consumer<List<String>> listPrinter = list -> list.forEach(System.out::println);
        
        // Supplier<T> - 供应者接口
        Supplier<String> stringSupplier = () -> "Hello World";
        Supplier<LocalDateTime> timeSupplier = LocalDateTime::now;
        
        // BiFunction<T, U, R> - 双参数函数接口
        BiFunction<String, String, String> concat = (s1, s2) -> s1 + s2;
        BiFunction<Integer, Integer, Integer> add = Integer::sum;
        
        // UnaryOperator<T> - 一元操作符
        UnaryOperator<String> toUpperCase = String::toUpperCase;
        UnaryOperator<Integer> square = n -> n * n;
        
        // BinaryOperator<T> - 二元操作符
        BinaryOperator<Integer> max = Integer::max;
        BinaryOperator<String> longer = (s1, s2) -> s1.length() > s2.length() ? s1 : s2;
    }
}
```

### 自定义函数式接口

```java
// 自定义函数式接口
@FunctionalInterface
public interface TriFunction<T, U, V, R> {
    R apply(T t, U u, V v);
    
    // 可以有默认方法
    default <S> TriFunction<T, U, V, S> andThen(Function<? super R, ? extends S> after) {
        Objects.requireNonNull(after);
        return (T t, U u, V v) -> after.apply(apply(t, u, v));
    }
}

// 使用自定义函数式接口
public class CustomFunctionalInterface {
    
    public void useTriFunction() {
        TriFunction<Integer, Integer, Integer, Integer> addThree = (a, b, c) -> a + b + c;
        
        int result = addThree.apply(1, 2, 3); // 结果: 6
        
        // 链式调用
        TriFunction<Integer, Integer, Integer, String> addAndConvert = addThree.andThen(String::valueOf);
        String resultString = addAndConvert.apply(1, 2, 3); // 结果: "6"
    }
}
```

## Stream API

### 基础Stream操作

```java
import java.util.stream.*;
import java.util.*;

public class StreamBasics {
    
    public void basicStreamOperations() {
        List<String> names = Arrays.asList("Alice", "Bob", "Charlie", "David", "Eve");
        
        // 创建Stream
        Stream<String> stream1 = names.stream();
        Stream<String> stream2 = Stream.of("A", "B", "C");
        Stream<String> stream3 = Stream.generate(() -> "Hello");
        Stream<Integer> stream4 = Stream.iterate(0, n -> n + 1);
        
        // 中间操作（返回Stream）
        List<String> result = names.stream()
            .filter(name -> name.length() > 3)        // 过滤
            .map(String::toUpperCase)                 // 转换
            .sorted()                                 // 排序
            .distinct()                               // 去重
            .limit(3)                                 // 限制数量
            .skip(1)                                  // 跳过元素
            .collect(Collectors.toList());            // 终端操作
        
        System.out.println(result); // [CHARLIE, DAVID]
    }
    
    public void streamTerminalOperations() {
        List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5);
        
        // 收集操作
        List<Integer> evenNumbers = numbers.stream()
            .filter(n -> n % 2 == 0)
            .collect(Collectors.toList());
        
        // 聚合操作
        Optional<Integer> max = numbers.stream().max(Integer::compareTo);
        Optional<Integer> min = numbers.stream().min(Integer::compareTo);
        long count = numbers.stream().count();
        
        // 归约操作
        Optional<Integer> sum = numbers.stream().reduce(Integer::sum);
        Integer product = numbers.stream().reduce(1, (a, b) -> a * b);
        
        // 匹配操作
        boolean anyMatch = numbers.stream().anyMatch(n -> n > 3);
        boolean allMatch = numbers.stream().allMatch(n -> n > 0);
        boolean noneMatch = numbers.stream().noneMatch(n -> n < 0);
        
        // 查找操作
        Optional<Integer> first = numbers.stream().findFirst();
        Optional<Integer> any = numbers.stream().findAny();
    }
}
```

### 高级Stream操作

```java
public class AdvancedStreamOperations {
    
    public void flatMapExample() {
        List<List<String>> listOfLists = Arrays.asList(
            Arrays.asList("a", "b"),
            Arrays.asList("c", "d"),
            Arrays.asList("e", "f")
        );
        
        // 扁平化处理
        List<String> flattened = listOfLists.stream()
            .flatMap(List::stream)
            .collect(Collectors.toList());
        
        System.out.println(flattened); // [a, b, c, d, e, f]
    }
    
    public void groupingAndPartitioning() {
        List<Person> people = Arrays.asList(
            new Person("Alice", 25, "Engineering"),
            new Person("Bob", 30, "Engineering"),
            new Person("Charlie", 25, "Marketing"),
            new Person("David", 35, "Engineering")
        );
        
        // 分组
        Map<String, List<Person>> byDepartment = people.stream()
            .collect(Collectors.groupingBy(Person::getDepartment));
        
        // 分区
        Map<Boolean, List<Person>> byAge = people.stream()
            .collect(Collectors.partitioningBy(p -> p.getAge() > 30));
        
        // 复杂分组
        Map<String, Long> countByDepartment = people.stream()
            .collect(Collectors.groupingBy(
                Person::getDepartment,
                Collectors.counting()
            ));
        
        // 多级分组
        Map<String, Map<Integer, List<Person>>> byDeptAndAge = people.stream()
            .collect(Collectors.groupingBy(
                Person::getDepartment,
                Collectors.groupingBy(Person::getAge)
            ));
    }
    
    public void customCollectors() {
        List<String> words = Arrays.asList("hello", "world", "java", "stream");
        
        // 自定义收集器
        String joined = words.stream()
            .collect(Collectors.joining(", ", "[", "]"));
        
        // 统计信息
        IntSummaryStatistics stats = words.stream()
            .mapToInt(String::length)
            .summaryStatistics();
        
        System.out.println("统计信息: " + stats);
    }
}
```

### 并行Stream

```java
public class ParallelStreams {
    
    public void parallelStreamExample() {
        List<Integer> numbers = IntStream.rangeClosed(1, 1000000)
            .boxed()
            .collect(Collectors.toList());
        
        // 顺序处理
        long startTime = System.currentTimeMillis();
        long sequentialSum = numbers.stream()
            .mapToLong(Integer::longValue)
            .sum();
        long sequentialTime = System.currentTimeMillis() - startTime;
        
        // 并行处理
        startTime = System.currentTimeMillis();
        long parallelSum = numbers.parallelStream()
            .mapToLong(Integer::longValue)
            .sum();
        long parallelTime = System.currentTimeMillis() - startTime;
        
        System.out.println("顺序处理时间: " + sequentialTime + "ms");
        System.out.println("并行处理时间: " + parallelTime + "ms");
        System.out.println("结果相同: " + (sequentialSum == parallelSum));
    }
    
    public void parallelStreamConsiderations() {
        List<String> words = Arrays.asList("hello", "world", "java", "stream", "parallel");
        
        // 注意：并行Stream不总是更快
        // 1. 数据量要足够大
        // 2. 每个元素的处理要足够复杂
        // 3. 避免共享可变状态
        
        // 好的并行使用场景
        List<String> processed = words.parallelStream()
            .map(String::toUpperCase)
            .filter(s -> s.length() > 3)
            .collect(Collectors.toList());
        
        // 避免的并行使用场景
        List<String> badParallel = words.parallelStream()
            .map(s -> {
                // 避免在并行Stream中使用共享状态
                return s.toUpperCase();
            })
            .collect(Collectors.toList());
    }
}
```

## Optional类

### Optional基础使用

```java
import java.util.Optional;

public class OptionalBasics {
    
    public void basicOptionalUsage() {
        // 创建Optional
        Optional<String> empty = Optional.empty();
        Optional<String> ofValue = Optional.of("Hello");
        Optional<String> ofNullable = Optional.ofNullable(getString());
        
        // 检查值是否存在
        if (ofValue.isPresent()) {
            System.out.println("值存在: " + ofValue.get());
        }
        
        // 安全获取值
        String value = ofValue.orElse("默认值");
        String value2 = ofValue.orElseGet(() -> "默认值");
        String value3 = ofValue.orElseThrow(() -> new RuntimeException("值不存在"));
        
        // 链式操作
        Optional<String> result = ofValue
            .map(String::toUpperCase)
            .filter(s -> s.length() > 3)
            .map(s -> s + "!");
    }
    
    private String getString() {
        return Math.random() > 0.5 ? "Hello" : null;
    }
    
    public void optionalChaining() {
        Optional<String> optional = Optional.of("hello world");
        
        // 链式操作
        Optional<String> result = optional
            .map(String::toUpperCase)           // 转换为大写
            .filter(s -> s.length() > 5)        // 过滤长度
            .map(s -> s.replace(" ", "_"));     // 替换空格
        
        // 扁平化处理
        Optional<String> flatResult = optional
            .flatMap(s -> Optional.of(s.toUpperCase()));
        
        // 条件执行
        optional.ifPresent(System.out::println);
        optional.ifPresentOrElse(
            System.out::println,
            () -> System.out.println("值不存在")
        );
    }
}
```

### Optional高级用法

```java
public class OptionalAdvanced {
    
    public void optionalInMethods() {
        // 方法返回Optional
        Optional<User> user = findUserById(123L);
        
        // 安全处理
        String email = user
            .map(User::getProfile)
            .map(Profile::getEmail)
            .orElse("unknown@example.com");
        
        // 复杂链式操作
        Optional<String> result = user
            .filter(u -> u.isActive())
            .map(User::getProfile)
            .filter(p -> p.getEmail() != null)
            .map(Profile::getEmail)
            .filter(e -> e.contains("@"));
    }
    
    public Optional<User> findUserById(Long id) {
        // 模拟数据库查询
        if (id == null || id <= 0) {
            return Optional.empty();
        }
        
        // 假设从数据库查询
        User user = userRepository.findById(id);
        return Optional.ofNullable(user);
    }
    
    public void optionalWithCollections() {
        List<String> names = Arrays.asList("Alice", "Bob", null, "Charlie");
        
        // 过滤空值
        List<String> validNames = names.stream()
            .map(Optional::ofNullable)
            .filter(Optional::isPresent)
            .map(Optional::get)
            .collect(Collectors.toList());
        
        // 更简洁的写法
        List<String> validNames2 = names.stream()
            .filter(Objects::nonNull)
            .collect(Collectors.toList());
        
        // 使用flatMap处理Optional
        List<String> processedNames = names.stream()
            .map(Optional::ofNullable)
            .flatMap(opt -> opt.map(Stream::of).orElse(Stream.empty()))
            .collect(Collectors.toList());
    }
}
```

## 方法引用

### 方法引用类型

```java
import java.util.function.*;

public class MethodReferences {
    
    public void demonstrateMethodReferences() {
        List<String> names = Arrays.asList("Alice", "Bob", "Charlie");
        
        // 1. 静态方法引用
        Function<String, Integer> parseInt = Integer::parseInt;
        BiFunction<String, Integer, String> substring = String::substring;
        
        // 2. 实例方法引用
        Function<String, Integer> length = String::length;
        Function<String, String> toUpperCase = String::toUpperCase;
        
        // 3. 对象实例方法引用
        String prefix = "Hello ";
        Function<String, String> addPrefix = prefix::concat;
        
        // 4. 构造方法引用
        Supplier<List<String>> listSupplier = ArrayList::new;
        Function<Integer, List<String>> listWithCapacity = ArrayList::new;
        
        // 5. 数组构造方法引用
        Function<Integer, int[]> arrayCreator = int[]::new;
    }
    
    public void methodReferenceExamples() {
        List<Person> people = Arrays.asList(
            new Person("Alice", 25),
            new Person("Bob", 30),
            new Person("Charlie", 25)
        );
        
        // 使用实例方法引用
        List<String> names = people.stream()
            .map(Person::getName)
            .collect(Collectors.toList());
        
        // 使用静态方法引用
        List<Integer> ages = people.stream()
            .map(Person::getAge)
            .map(Math::abs)
            .collect(Collectors.toList());
        
        // 使用构造方法引用
        List<String> personStrings = people.stream()
            .map(Person::toString)
            .collect(Collectors.toList());
    }
    
    public void comparingMethodReferences() {
        List<String> words = Arrays.asList("hello", "world", "java", "stream");
        
        // Lambda表达式
        words.sort((s1, s2) -> s1.compareTo(s2));
        
        // 方法引用
        words.sort(String::compareTo);
        
        // 更复杂的例子
        List<Person> people = Arrays.asList(
            new Person("Alice", 25),
            new Person("Bob", 30)
        );
        
        // 按年龄排序
        people.sort(Comparator.comparing(Person::getAge));
        
        // 按姓名排序
        people.sort(Comparator.comparing(Person::getName));
        
        // 多级排序
        people.sort(Comparator.comparing(Person::getAge)
            .thenComparing(Person::getName));
    }
}
```

## 高阶函数

### 函数作为参数

```java
import java.util.function.*;

public class HigherOrderFunctions {
    
    public void functionAsParameter() {
        List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5);
        
        // 传递函数作为参数
        List<Integer> doubled = map(numbers, n -> n * 2);
        List<Integer> squared = map(numbers, n -> n * n);
        List<String> strings = map(numbers, String::valueOf);
        
        // 过滤函数
        List<Integer> evens = filter(numbers, n -> n % 2 == 0);
        List<Integer> greaterThan3 = filter(numbers, n -> n > 3);
        
        // 归约函数
        Integer sum = reduce(numbers, 0, Integer::sum);
        Integer product = reduce(numbers, 1, (a, b) -> a * b);
    }
    
    // 高阶函数实现
    public static <T, R> List<R> map(List<T> list, Function<T, R> mapper) {
        return list.stream()
            .map(mapper)
            .collect(Collectors.toList());
    }
    
    public static <T> List<T> filter(List<T> list, Predicate<T> predicate) {
        return list.stream()
            .filter(predicate)
            .collect(Collectors.toList());
    }
    
    public static <T> T reduce(List<T> list, T identity, BinaryOperator<T> accumulator) {
        return list.stream()
            .reduce(identity, accumulator);
    }
    
    public void functionComposition() {
        // 函数组合
        Function<String, String> addPrefix = s -> "Hello " + s;
        Function<String, String> addSuffix = s -> s + "!";
        Function<String, String> toUpperCase = String::toUpperCase;
        
        // 组合函数
        Function<String, String> composed = addPrefix
            .andThen(toUpperCase)
            .andThen(addSuffix);
        
        String result = composed.apply("world"); // "HELLO WORLD!"
        
        // 使用compose方法
        Function<String, String> composed2 = addSuffix
            .compose(toUpperCase)
            .compose(addPrefix);
        
        String result2 = composed2.apply("world"); // "HELLO WORLD!"
    }
}
```

### 函数作为返回值

```java
public class FunctionAsReturnValue {
    
    public void functionAsReturnValue() {
        // 返回函数
        Function<Integer, Function<Integer, Integer>> add = a -> b -> a + b;
        Function<Integer, Function<Integer, Integer>> multiply = a -> b -> a * b;
        
        // 使用返回的函数
        Function<Integer, Integer> add5 = add.apply(5);
        Function<Integer, Integer> multiplyBy3 = multiply.apply(3);
        
        int result1 = add5.apply(3); // 8
        int result2 = multiplyBy3.apply(4); // 12
        
        // 柯里化
        Function<Integer, Function<Integer, Function<Integer, Integer>>> 
            addThree = a -> b -> c -> a + b + c;
        
        Function<Integer, Function<Integer, Integer>> add5AndB = addThree.apply(5);
        Function<Integer, Integer> add5And3AndC = add5AndB.apply(3);
        int result3 = add5And3AndC.apply(2); // 10
    }
    
    public void partialApplication() {
        // 部分应用
        BiFunction<String, String, String> concat = (s1, s2) -> s1 + s2;
        
        // 部分应用第一个参数
        Function<String, String> addPrefix = s -> concat.apply("Hello ", s);
        Function<String, String> addSuffix = s -> concat.apply(s, "!");
        
        String result1 = addPrefix.apply("World"); // "Hello World"
        String result2 = addSuffix.apply("Hello"); // "Hello!"
    }
    
    public void memoization() {
        // 记忆化（缓存函数结果）
        Function<Integer, Integer> expensiveFunction = this::expensiveCalculation;
        Function<Integer, Integer> memoizedFunction = memoize(expensiveFunction);
        
        // 第一次调用会执行计算
        int result1 = memoizedFunction.apply(5);
        
        // 第二次调用会返回缓存的结果
        int result2 = memoizedFunction.apply(5);
    }
    
    private Integer expensiveCalculation(Integer input) {
        // 模拟昂贵的计算
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        return input * input;
    }
    
    private <T, R> Function<T, R> memoize(Function<T, R> function) {
        Map<T, R> cache = new ConcurrentHashMap<>();
        return input -> cache.computeIfAbsent(input, function);
    }
}
```

## 函数组合

### 函数组合模式

```java
import java.util.function.*;

public class FunctionComposition {
    
    public void basicComposition() {
        // 基础函数
        Function<String, String> trim = String::trim;
        Function<String, String> toUpperCase = String::toUpperCase;
        Function<String, String> addPrefix = s -> "Hello " + s;
        
        // 使用andThen组合
        Function<String, String> composed = trim
            .andThen(toUpperCase)
            .andThen(addPrefix);
        
        String result = composed.apply("  world  "); // "Hello WORLD"
        
        // 使用compose组合
        Function<String, String> composed2 = addPrefix
            .compose(toUpperCase)
            .compose(trim);
        
        String result2 = composed2.apply("  world  "); // "Hello WORLD"
    }
    
    public void pipelinePattern() {
        // 管道模式
        List<String> words = Arrays.asList("  hello  ", "  world  ", "  java  ");
        
        List<String> processed = words.stream()
            .map(String::trim)                    // 去除空格
            .map(String::toUpperCase)             // 转大写
            .map(s -> s + "!")                    // 添加感叹号
            .filter(s -> s.length() > 5)          // 过滤长度
            .collect(Collectors.toList());
        
        System.out.println(processed); // [HELLO!, WORLD!]
    }
    
    public void customComposition() {
        // 自定义组合函数
        Function<String, String> processString = compose(
            String::trim,
            String::toUpperCase,
            s -> s.replace(" ", "_"),
            s -> s + "_PROCESSED"
        );
        
        String result = processString.apply("  hello world  ");
        System.out.println(result); // "HELLO_WORLD_PROCESSED"
    }
    
    @SafeVarargs
    public static <T> Function<T, T> compose(Function<T, T>... functions) {
        return Arrays.stream(functions)
            .reduce(Function.identity(), Function::andThen);
    }
    
    public void conditionalComposition() {
        // 条件组合
        Predicate<String> isNotEmpty = s -> s != null && !s.isEmpty();
        Predicate<String> isLongEnough = s -> s.length() > 3;
        
        Function<String, String> processIfValid = compose(
            s -> isNotEmpty.test(s) ? s : "DEFAULT",
            s -> isLongEnough.test(s) ? s.toUpperCase() : s.toLowerCase(),
            s -> s + "_PROCESSED"
        );
        
        String result1 = processIfValid.apply("hello"); // "HELLO_PROCESSED"
        String result2 = processIfValid.apply("hi");    // "hi_PROCESSED"
        String result3 = processIfValid.apply("");      // "DEFAULT_PROCESSED"
    }
}
```

### 函数式编程模式

```java
public class FunctionalPatterns {
    
    public void mapReducePattern() {
        List<String> words = Arrays.asList("hello", "world", "java", "stream");
        
        // Map-Reduce模式
        int totalLength = words.stream()
            .mapToInt(String::length)              // Map: 转换
            .sum();                                // Reduce: 聚合
        
        // 更复杂的Map-Reduce
        Map<Character, Long> charCount = words.stream()
            .flatMap(word -> word.chars().mapToObj(c -> (char) c))  // Map: 扁平化
            .collect(Collectors.groupingBy(         // Reduce: 分组计数
                Function.identity(),
                Collectors.counting()
            ));
        
        System.out.println("总长度: " + totalLength);
        System.out.println("字符统计: " + charCount);
    }
    
    public void filterMapPattern() {
        List<Person> people = Arrays.asList(
            new Person("Alice", 25, "Engineering"),
            new Person("Bob", 30, "Marketing"),
            new Person("Charlie", 25, "Engineering"),
            new Person("David", 35, "Engineering")
        );
        
        // Filter-Map模式
        List<String> engineerNames = people.stream()
            .filter(p -> "Engineering".equals(p.getDepartment()))  // Filter: 过滤
            .map(Person::getName)                                   // Map: 转换
            .collect(Collectors.toList());
        
        System.out.println("工程师姓名: " + engineerNames);
    }
    
    public void foldPattern() {
        List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5);
        
        // Fold模式（归约）
        int sum = numbers.stream()
            .reduce(0, Integer::sum);
        
        int product = numbers.stream()
            .reduce(1, (a, b) -> a * b);
        
        String concatenated = numbers.stream()
            .map(String::valueOf)
            .reduce("", (a, b) -> a + b);
        
        System.out.println("和: " + sum);
        System.out.println("积: " + product);
        System.out.println("连接: " + concatenated);
    }
}
```

## 不可变性

### 不可变集合

```java
import java.util.*;

public class Immutability {
    
    public void immutableCollections() {
        // 创建不可变集合
        List<String> immutableList = List.of("a", "b", "c");
        Set<String> immutableSet = Set.of("a", "b", "c");
        Map<String, Integer> immutableMap = Map.of("a", 1, "b", 2, "c", 3);
        
        // 尝试修改会抛出异常
        try {
            immutableList.add("d"); // UnsupportedOperationException
        } catch (UnsupportedOperationException e) {
            System.out.println("不可变列表不能修改");
        }
        
        // 从现有集合创建不可变集合
        List<String> mutableList = new ArrayList<>(Arrays.asList("a", "b", "c"));
        List<String> immutableFromMutable = Collections.unmodifiableList(mutableList);
        
        // 使用Stream创建不可变集合
        List<String> immutableFromStream = Arrays.asList("a", "b", "c").stream()
            .map(String::toUpperCase)
            .collect(Collectors.toUnmodifiableList());
    }
    
    public void immutableObjects() {
        // 不可变对象设计
        ImmutablePerson person = new ImmutablePerson("Alice", 25);
        
        // 创建新对象而不是修改现有对象
        ImmutablePerson olderPerson = person.withAge(26);
        
        System.out.println("原年龄: " + person.getAge());
        System.out.println("新年龄: " + olderPerson.getAge());
    }
    
    public void defensiveCopying() {
        // 防御性复制
        List<String> originalList = new ArrayList<>(Arrays.asList("a", "b", "c"));
        
        // 返回防御性副本
        List<String> defensiveCopy = new ArrayList<>(originalList);
        
        // 修改原始列表不会影响副本
        originalList.add("d");
        
        System.out.println("原始列表: " + originalList);
        System.out.println("防御副本: " + defensiveCopy);
    }
}

// 不可变对象示例
class ImmutablePerson {
    private final String name;
    private final int age;
    private final List<String> hobbies;
    
    public ImmutablePerson(String name, int age) {
        this.name = name;
        this.age = age;
        this.hobbies = new ArrayList<>();
    }
    
    public ImmutablePerson(String name, int age, List<String> hobbies) {
        this.name = name;
        this.age = age;
        this.hobbies = new ArrayList<>(hobbies); // 防御性复制
    }
    
    public String getName() {
        return name;
    }
    
    public int getAge() {
        return age;
    }
    
    public List<String> getHobbies() {
        return Collections.unmodifiableList(hobbies); // 返回不可变视图
    }
    
    public ImmutablePerson withAge(int newAge) {
        return new ImmutablePerson(this.name, newAge, this.hobbies);
    }
    
    public ImmutablePerson withHobbies(List<String> newHobbies) {
        return new ImmutablePerson(this.name, this.age, newHobbies);
    }
}
```

## 实际项目应用

### 数据处理管道

```java
@Service
public class DataProcessingService {
    
    public List<ProcessedData> processUserData(List<RawUserData> rawData) {
        return rawData.stream()
            .filter(this::isValidData)                    // 数据验证
            .map(this::normalizeData)                     // 数据标准化
            .filter(this::isBusinessValid)                // 业务规则验证
            .map(this::enrichData)                        // 数据丰富
            .sorted(Comparator.comparing(ProcessedData::getPriority))  // 排序
            .collect(Collectors.toList());
    }
    
    private boolean isValidData(RawUserData data) {
        return data != null 
            && data.getUserId() != null 
            && data.getEmail() != null 
            && data.getEmail().contains("@");
    }
    
    private ProcessedData normalizeData(RawUserData raw) {
        return ProcessedData.builder()
            .userId(raw.getUserId())
            .email(raw.getEmail().toLowerCase().trim())
            .name(raw.getName() != null ? raw.getName().trim() : "Unknown")
            .build();
    }
    
    private boolean isBusinessValid(ProcessedData data) {
        return data.getEmail().length() > 5 
            && data.getName().length() > 1;
    }
    
    private ProcessedData enrichData(ProcessedData data) {
        return data.toBuilder()
            .domain(extractDomain(data.getEmail()))
            .priority(calculatePriority(data))
            .build();
    }
    
    private String extractDomain(String email) {
        return email.substring(email.indexOf("@") + 1);
    }
    
    private int calculatePriority(ProcessedData data) {
        return data.getDomain().equals("company.com") ? 1 : 2;
    }
}
```

### 函数式配置管理

```java
@Component
public class ConfigurationManager {
    
    private final Map<String, String> config;
    
    public ConfigurationManager() {
        this.config = loadConfiguration();
    }
    
    // 函数式配置获取
    public Optional<String> getString(String key) {
        return Optional.ofNullable(config.get(key));
    }
    
    public String getString(String key, String defaultValue) {
        return getString(key).orElse(defaultValue);
    }
    
    public Optional<Integer> getInt(String key) {
        return getString(key)
            .map(this::parseInt)
            .filter(Optional::isPresent)
            .map(Optional::get);
    }
    
    public int getInt(String key, int defaultValue) {
        return getInt(key).orElse(defaultValue);
    }
    
    public Optional<Boolean> getBoolean(String key) {
        return getString(key)
            .map(String::toLowerCase)
            .map(s -> s.equals("true") || s.equals("1") || s.equals("yes"));
    }
    
    public boolean getBoolean(String key, boolean defaultValue) {
        return getBoolean(key).orElse(defaultValue);
    }
    
    // 配置验证
    public void validateRequired(String... keys) {
        Arrays.stream(keys)
            .filter(key -> !config.containsKey(key))
            .findFirst()
            .ifPresent(key -> {
                throw new ConfigurationException("缺少必需的配置项: " + key);
            });
    }
    
    // 配置转换
    public <T> Optional<T> getAs(String key, Function<String, T> converter) {
        return getString(key)
            .map(converter)
            .filter(Objects::nonNull);
    }
    
    private Optional<Integer> parseInt(String value) {
        try {
            return Optional.of(Integer.parseInt(value));
        } catch (NumberFormatException e) {
            return Optional.empty();
        }
    }
    
    private Map<String, String> loadConfiguration() {
        // 加载配置的逻辑
        return new HashMap<>();
    }
}
```

### 函数式错误处理

```java
public class FunctionalErrorHandling {
    
    // 结果类型
    public static class Result<T> {
        private final T value;
        private final String error;
        private final boolean success;
        
        private Result(T value, String error, boolean success) {
            this.value = value;
            this.error = error;
            this.success = success;
        }
        
        public static <T> Result<T> success(T value) {
            return new Result<>(value, null, true);
        }
        
        public static <T> Result<T> failure(String error) {
            return new Result<>(null, error, false);
        }
        
        public boolean isSuccess() {
            return success;
        }
        
        public boolean isFailure() {
            return !success;
        }
        
        public T getValue() {
            if (!success) {
                throw new IllegalStateException("Cannot get value from failed result");
            }
            return value;
        }
        
        public String getError() {
            return error;
        }
        
        public <U> Result<U> map(Function<T, U> mapper) {
            if (success) {
                try {
                    return Result.success(mapper.apply(value));
                } catch (Exception e) {
                    return Result.failure("Mapping failed: " + e.getMessage());
                }
            }
            return Result.failure(error);
        }
        
        public <U> Result<U> flatMap(Function<T, Result<U>> mapper) {
            if (success) {
                try {
                    return mapper.apply(value);
                } catch (Exception e) {
                    return Result.failure("Flat mapping failed: " + e.getMessage());
                }
            }
            return Result.failure(error);
        }
        
        public T orElse(T defaultValue) {
            return success ? value : defaultValue;
        }
        
        public T orElseGet(Supplier<T> supplier) {
            return success ? value : supplier.get();
        }
    }
    
    // 使用示例
    public Result<String> processUserInput(String input) {
        return Result.success(input)
            .map(String::trim)
            .filter(s -> !s.isEmpty(), "输入不能为空")
            .map(String::toLowerCase)
            .filter(s -> s.length() > 3, "输入长度必须大于3")
            .map(s -> "processed_" + s);
    }
    
    public Result<String> processUserInput(String input) {
        return Result.success(input)
            .map(String::trim)
            .flatMap(s -> s.isEmpty() ? 
                Result.failure("输入不能为空") : 
                Result.success(s))
            .map(String::toLowerCase)
            .flatMap(s -> s.length() <= 3 ? 
                Result.failure("输入长度必须大于3") : 
                Result.success(s))
            .map(s -> "processed_" + s);
    }
}
```

## 性能考虑

### Stream性能优化

```java
public class StreamPerformance {
    
    public void performanceConsiderations() {
        List<Integer> numbers = IntStream.rangeClosed(1, 1000000)
            .boxed()
            .collect(Collectors.toList());
        
        // 1. 避免不必要的装箱
        long sum1 = numbers.stream()
            .mapToInt(Integer::intValue)  // 避免装箱
            .sum();
        
        // 2. 使用原始类型Stream
        long sum2 = IntStream.rangeClosed(1, 1000000)
            .sum();
        
        // 3. 避免重复计算
        List<String> words = Arrays.asList("hello", "world", "java", "stream");
        
        // 不好的做法
        List<String> bad = words.stream()
            .filter(s -> s.length() > 3)
            .map(s -> s.toUpperCase())
            .filter(s -> s.length() > 3)  // 重复计算长度
            .collect(Collectors.toList());
        
        // 好的做法
        List<String> good = words.stream()
            .filter(s -> s.length() > 3)
            .map(String::toUpperCase)
            .collect(Collectors.toList());
        
        // 4. 短路操作
        boolean hasLongWord = words.stream()
            .anyMatch(s -> s.length() > 10);  // 找到第一个就停止
        
        // 5. 并行Stream的考虑
        long parallelSum = numbers.parallelStream()
            .mapToLong(Integer::longValue)
            .sum();
    }
    
    public void memoryOptimization() {
        // 使用流式处理避免创建中间集合
        List<String> largeList = generateLargeList();
        
        // 不好的做法 - 创建多个中间集合
        List<String> bad = largeList.stream()
            .filter(s -> s.length() > 5)
            .map(String::toUpperCase)
            .collect(Collectors.toList())
            .stream()
            .filter(s -> s.contains("A"))
            .collect(Collectors.toList());
        
        // 好的做法 - 流式处理
        List<String> good = largeList.stream()
            .filter(s -> s.length() > 5)
            .map(String::toUpperCase)
            .filter(s -> s.contains("A"))
            .collect(Collectors.toList());
    }
    
    private List<String> generateLargeList() {
        return IntStream.rangeClosed(1, 1000000)
            .mapToObj(String::valueOf)
            .collect(Collectors.toList());
    }
}
```

## 最佳实践

### 函数式编程最佳实践

```java
public class FunctionalProgrammingBestPractices {
    
    // 1. 使用有意义的变量名
    public void meaningfulNames() {
        List<String> names = Arrays.asList("Alice", "Bob", "Charlie");
        
        // 好的做法
        List<String> longNames = names.stream()
            .filter(name -> name.length() > 3)
            .collect(Collectors.toList());
        
        // 避免的做法
        List<String> result = names.stream()
            .filter(s -> s.length() > 3)
            .collect(Collectors.toList());
    }
    
    // 2. 保持函数简洁
    public void keepFunctionsSimple() {
        List<Person> people = Arrays.asList(
            new Person("Alice", 25),
            new Person("Bob", 30)
        );
        
        // 好的做法 - 分解复杂操作
        List<String> adultNames = people.stream()
            .filter(this::isAdult)
            .map(Person::getName)
            .collect(Collectors.toList());
        
        // 避免的做法 - 复杂的内联逻辑
        List<String> bad = people.stream()
            .filter(p -> p.getAge() >= 18 && p.getName() != null && !p.getName().isEmpty())
            .map(p -> p.getName().toUpperCase())
            .collect(Collectors.toList());
    }
    
    private boolean isAdult(Person person) {
        return person.getAge() >= 18;
    }
    
    // 3. 使用Optional正确处理空值
    public void properOptionalUsage() {
        List<String> names = Arrays.asList("Alice", null, "Bob", "");
        
        // 好的做法
        List<String> validNames = names.stream()
            .map(Optional::ofNullable)
            .filter(Optional::isPresent)
            .map(Optional::get)
            .filter(name -> !name.isEmpty())
            .collect(Collectors.toList());
        
        // 更好的做法
        List<String> betterNames = names.stream()
            .filter(Objects::nonNull)
            .filter(name -> !name.isEmpty())
            .collect(Collectors.toList());
    }
    
    // 4. 避免副作用
    public void avoidSideEffects() {
        List<String> names = Arrays.asList("Alice", "Bob", "Charlie");
        
        // 不好的做法 - 有副作用
        List<String> bad = new ArrayList<>();
        names.stream()
            .filter(name -> name.length() > 3)
            .forEach(name -> bad.add(name.toUpperCase()));  // 副作用
        
        // 好的做法 - 无副作用
        List<String> good = names.stream()
            .filter(name -> name.length() > 3)
            .map(String::toUpperCase)
            .collect(Collectors.toList());
    }
    
    // 5. 使用不可变对象
    public void useImmutableObjects() {
        // 好的做法 - 不可变对象
        List<String> immutableList = List.of("a", "b", "c");
        
        // 避免的做法 - 可变对象
        List<String> mutableList = new ArrayList<>(Arrays.asList("a", "b", "c"));
    }
}
```

## 常见陷阱

### 函数式编程常见陷阱

```java
public class FunctionalProgrammingPitfalls {
    
    // 1. 过度使用Stream
    public void overuseStream() {
        List<String> names = Arrays.asList("Alice", "Bob");
        
        // 过度使用Stream
        String result = names.stream()
            .filter(name -> name.length() > 0)
            .map(name -> name.toUpperCase())
            .reduce("", (a, b) -> a + b);
        
        // 更简单的做法
        StringBuilder sb = new StringBuilder();
        for (String name : names) {
            sb.append(name.toUpperCase());
        }
        String simpleResult = sb.toString();
    }
    
    // 2. 忽略异常处理
    public void ignoreExceptionHandling() {
        List<String> numbers = Arrays.asList("1", "2", "abc", "4");
        
        // 不好的做法 - 忽略异常
        List<Integer> bad = numbers.stream()
            .map(Integer::parseInt)  // 可能抛出NumberFormatException
            .collect(Collectors.toList());
        
        // 好的做法 - 处理异常
        List<Integer> good = numbers.stream()
            .map(this::safeParseInt)
            .filter(Optional::isPresent)
            .map(Optional::get)
            .collect(Collectors.toList());
    }
    
    private Optional<Integer> safeParseInt(String s) {
        try {
            return Optional.of(Integer.parseInt(s));
        } catch (NumberFormatException e) {
            return Optional.empty();
        }
    }
    
    // 3. 并行Stream的误用
    public void misuseParallelStream() {
        List<String> words = Arrays.asList("hello", "world", "java");
        
        // 不好的做法 - 小数据集使用并行Stream
        List<String> bad = words.parallelStream()
            .map(String::toUpperCase)
            .collect(Collectors.toList());
        
        // 好的做法 - 大数据集使用并行Stream
        List<String> largeList = generateLargeList();
        List<String> good = largeList.parallelStream()
            .map(String::toUpperCase)
            .collect(Collectors.toList());
    }
    
    // 4. 状态共享问题
    public void stateSharingProblem() {
        List<String> words = Arrays.asList("hello", "world", "java");
        
        // 不好的做法 - 共享可变状态
        StringBuilder shared = new StringBuilder();
        words.parallelStream()
            .forEach(word -> shared.append(word));  // 线程不安全
        
        // 好的做法 - 避免共享状态
        String result = words.stream()
            .collect(Collectors.joining());
    }
    
    // 5. 性能问题
    public void performanceIssues() {
        List<String> words = Arrays.asList("hello", "world", "java");
        
        // 不好的做法 - 重复计算
        List<String> bad = words.stream()
            .filter(word -> word.length() > 3)
            .map(word -> word.toUpperCase())
            .filter(word -> word.length() > 3)  // 重复计算
            .collect(Collectors.toList());
        
        // 好的做法 - 避免重复计算
        List<String> good = words.stream()
            .filter(word -> word.length() > 3)
            .map(String::toUpperCase)
            .collect(Collectors.toList());
    }
    
    private List<String> generateLargeList() {
        return IntStream.rangeClosed(1, 1000000)
            .mapToObj(String::valueOf)
            .collect(Collectors.toList());
    }
}
```

## 总结

函数式编程是现代Java开发的重要组成部分，它提供了：

### 核心优势
1. **代码简洁** - 减少样板代码，提高可读性
2. **不可变性** - 减少bug，提高并发安全性
3. **组合性** - 通过组合简单函数构建复杂功能
4. **声明式** - 描述"做什么"而不是"怎么做"
5. **并行友好** - 天然支持并行处理

### 适用场景
- **数据处理** - 集合操作、数据转换
- **异步编程** - CompletableFuture、响应式编程
- **配置管理** - 函数式配置处理
- **错误处理** - 函数式错误处理模式
- **测试** - 纯函数易于测试

### 注意事项
- **性能考虑** - 避免过度使用，注意装箱开销
- **异常处理** - 正确处理函数式编程中的异常
- **状态管理** - 避免共享可变状态
- **可读性** - 保持代码简洁但不过度复杂

通过合理使用函数式编程特性，可以编写出更加简洁、安全、可维护的Java代码。


