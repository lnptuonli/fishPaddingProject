# Java HashMap完整指南

## 目录
1. [HashMap基础概念](#hashmap基础概念)
2. [HashMap的结构](#hashmap的结构)
3. [HashMap的创建](#hashmap的创建)
4. [HashMap的基本操作](#hashmap的基本操作)
5. [HashMap的遍历](#hashmap的遍历)
6. [HashMap的原理](#hashmap的原理)
7. [HashMap的扩容机制](#hashmap的扩容机制)
8. [HashMap线程安全问题](#hashmap线程安全问题)
9. [HashMap常见面试题](#hashmap常见面试题)
10. [最佳实践](#最佳实践)

## HashMap基础概念

### 什么是HashMap？

HashMap是Java集合框架中最常用的Map实现，它基于**哈希表**实现，存储键值对（key-value），允许null键和null值。

```java
public class HashMap<K,V> extends AbstractMap<K,V>
    implements Map<K,V>, Cloneable, Serializable {
    // HashMap的实现
}
```

### HashMap的特点

1. **无序性** - 不保证元素的顺序
2. **键唯一** - key不能重复，重复会覆盖
3. **允许null** - 允许一个null键和多个null值
4. **非线程安全** - 多线程环境需要外部同步
5. **高效性** - 查找、插入、删除的平均时间复杂度为O(1)

### HashMap vs Hashtable vs ConcurrentHashMap

| 特性 | HashMap | Hashtable | ConcurrentHashMap |
|------|---------|-----------|-------------------|
| 线程安全 | 否 | 是 | 是 |
| null键 | 允许 | 不允许 | 不允许 |
| null值 | 允许 | 不允许 | 不允许 |
| 性能 | 高 | 低 | 高 |
| 推荐使用 | 单线程 | 已过时 | 多线程 |

## HashMap的结构

### 内部结构（JDK 1.8+）

```
HashMap内部结构：
┌─────────────────────────────────────────┐
│          HashMap                         │
│                                          │
│  ┌────────────────────────────────┐    │
│  │     Node<K,V>[] table          │    │
│  │  (哈希表/数组)                  │    │
│  │                                 │    │
│  │  [0] → Node → Node → Node      │    │
│  │  [1] → null                     │    │
│  │  [2] → Node → TreeNode (红黑树) │    │
│  │  [3] → Node                     │    │
│  │  ...                            │    │
│  └────────────────────────────────┘    │
└─────────────────────────────────────────┘
```

### Node节点结构

```java
static class Node<K,V> implements Map.Entry<K,V> {
    final int hash;    // 键的哈希值
    final K key;       // 键
    V value;           // 值
    Node<K,V> next;    // 下一个节点（链表）
    
    Node(int hash, K key, V value, Node<K,V> next) {
        this.hash = hash;
        this.key = key;
        this.value = value;
        this.next = next;
    }
}
```

### 关键参数

```java
public class HashMapParameters {
    public static void main(String[] args) {
        // 默认初始容量：16
        static final int DEFAULT_INITIAL_CAPACITY = 1 << 4;
        
        // 最大容量：2^30
        static final int MAXIMUM_CAPACITY = 1 << 30;
        
        // 默认加载因子：0.75
        static final float DEFAULT_LOAD_FACTOR = 0.75f;
        
        // 链表转红黑树阈值：8
        static final int TREEIFY_THRESHOLD = 8;
        
        // 红黑树转链表阈值：6
        static final int UNTREEIFY_THRESHOLD = 6;
        
        // 最小树化容量：64
        static final int MIN_TREEIFY_CAPACITY = 64;
    }
}
```

## HashMap的创建

### 创建方式

```java
import java.util.*;

public class HashMapCreation {
    public static void main(String[] args) {
        // 方式1：默认构造（初始容量16，加载因子0.75）
        HashMap<String, Integer> map1 = new HashMap<>();
        
        // 方式2：指定初始容量
        HashMap<String, Integer> map2 = new HashMap<>(32);
        
        // 方式3：指定初始容量和加载因子
        HashMap<String, Integer> map3 = new HashMap<>(32, 0.8f);
        
        // 方式4：从其他Map创建
        Map<String, Integer> sourceMap = new HashMap<>();
        sourceMap.put("A", 1);
        sourceMap.put("B", 2);
        HashMap<String, Integer> map4 = new HashMap<>(sourceMap);
        
        // 方式5：使用静态工厂方法（Java 9+）
        Map<String, Integer> map5 = Map.of("A", 1, "B", 2, "C", 3);
        
        // 方式6：使用ofEntries（Java 9+）
        Map<String, Integer> map6 = Map.ofEntries(
            Map.entry("A", 1),
            Map.entry("B", 2),
            Map.entry("C", 3)
        );
        
        System.out.println("map1: " + map1);
        System.out.println("map4: " + map4);
        System.out.println("map5: " + map5);
    }
}
```

### 初始容量计算

```java
public class InitialCapacity {
    public static void main(String[] args) {
        // 如果知道要存储的元素数量，建议指定初始容量
        // 初始容量 = 预期元素数量 / 加载因子 + 1
        
        int expectedSize = 100;
        float loadFactor = 0.75f;
        int initialCapacity = (int) (expectedSize / loadFactor) + 1;
        
        HashMap<String, Integer> map = new HashMap<>(initialCapacity);
        
        // 避免扩容，提高性能
        for (int i = 0; i < 100; i++) {
            map.put("key" + i, i);
        }
    }
}
```

## HashMap的基本操作

### 1. 添加元素

```java
public class HashMapPut {
    public static void main(String[] args) {
        HashMap<String, Integer> map = new HashMap<>();
        
        // put - 添加键值对
        map.put("Apple", 10);
        map.put("Banana", 20);
        map.put("Orange", 30);
        System.out.println(map);  // {Apple=10, Orange=30, Banana=20}
        
        // 如果key已存在，value会被覆盖
        Integer oldValue = map.put("Apple", 15);
        System.out.println("旧值: " + oldValue);  // 10
        System.out.println(map);  // {Apple=15, Orange=30, Banana=20}
        
        // putIfAbsent - 只在key不存在时添加
        map.putIfAbsent("Apple", 20);   // 不会添加，key已存在
        map.putIfAbsent("Grape", 40);   // 会添加
        System.out.println(map);
        
        // putAll - 批量添加
        Map<String, Integer> newMap = new HashMap<>();
        newMap.put("Peach", 50);
        newMap.put("Mango", 60);
        map.putAll(newMap);
        System.out.println(map);
    }
}
```

### 2. 获取元素

```java
public class HashMapGet {
    public static void main(String[] args) {
        HashMap<String, Integer> map = new HashMap<>();
        map.put("Apple", 10);
        map.put("Banana", 20);
        map.put("Orange", 30);
        
        // get - 获取值
        Integer value1 = map.get("Apple");
        System.out.println("Apple: " + value1);  // 10
        
        // 如果key不存在，返回null
        Integer value2 = map.get("Grape");
        System.out.println("Grape: " + value2);  // null
        
        // getOrDefault - 获取值，不存在返回默认值
        Integer value3 = map.getOrDefault("Grape", 0);
        System.out.println("Grape: " + value3);  // 0
        
        // containsKey - 判断key是否存在
        boolean hasApple = map.containsKey("Apple");
        System.out.println("包含Apple: " + hasApple);  // true
        
        // containsValue - 判断value是否存在
        boolean has20 = map.containsValue(20);
        System.out.println("包含值20: " + has20);  // true
    }
}
```

### 3. 删除元素

```java
public class HashMapRemove {
    public static void main(String[] args) {
        HashMap<String, Integer> map = new HashMap<>();
        map.put("Apple", 10);
        map.put("Banana", 20);
        map.put("Orange", 30);
        
        // remove - 删除指定key
        Integer removedValue = map.remove("Banana");
        System.out.println("删除的值: " + removedValue);  // 20
        System.out.println(map);  // {Apple=10, Orange=30}
        
        // remove - 删除指定key和value（只有都匹配才删除）
        boolean removed = map.remove("Apple", 15);
        System.out.println("是否删除: " + removed);  // false（value不匹配）
        
        removed = map.remove("Apple", 10);
        System.out.println("是否删除: " + removed);  // true
        System.out.println(map);  // {Orange=30}
        
        // clear - 清空所有元素
        map.clear();
        System.out.println("清空后: " + map);  // {}
    }
}
```

### 4. 更新元素

```java
public class HashMapUpdate {
    public static void main(String[] args) {
        HashMap<String, Integer> map = new HashMap<>();
        map.put("Apple", 10);
        map.put("Banana", 20);
        
        // replace - 替换value
        Integer oldValue = map.replace("Apple", 15);
        System.out.println("旧值: " + oldValue);  // 10
        System.out.println(map);  // {Apple=15, Banana=20}
        
        // replace - 只有value匹配才替换
        boolean replaced = map.replace("Banana", 25, 30);
        System.out.println("是否替换: " + replaced);  // false（value不匹配）
        
        replaced = map.replace("Banana", 20, 30);
        System.out.println("是否替换: " + replaced);  // true
        System.out.println(map);  // {Apple=15, Banana=30}
        
        // compute - 根据key计算新value
        map.compute("Apple", (k, v) -> v == null ? 1 : v + 5);
        System.out.println(map);  // {Apple=20, Banana=30}
        
        // computeIfPresent - key存在时计算
        map.computeIfPresent("Banana", (k, v) -> v + 10);
        System.out.println(map);  // {Apple=20, Banana=40}
        
        // computeIfAbsent - key不存在时计算
        map.computeIfAbsent("Orange", k -> 50);
        System.out.println(map);  // {Apple=20, Orange=50, Banana=40}
        
        // merge - 合并值
        map.merge("Apple", 5, Integer::sum);  // 如果存在，执行sum操作
        System.out.println(map);  // {Apple=25, Orange=50, Banana=40}
    }
}
```

### 5. 其他操作

```java
public class HashMapOther {
    public static void main(String[] args) {
        HashMap<String, Integer> map = new HashMap<>();
        map.put("Apple", 10);
        map.put("Banana", 20);
        map.put("Orange", 30);
        
        // size - 获取元素数量
        int size = map.size();
        System.out.println("元素数量: " + size);  // 3
        
        // isEmpty - 判断是否为空
        boolean empty = map.isEmpty();
        System.out.println("是否为空: " + empty);  // false
        
        // keySet - 获取所有key的集合
        Set<String> keys = map.keySet();
        System.out.println("所有key: " + keys);  // [Apple, Orange, Banana]
        
        // values - 获取所有value的集合
        Collection<Integer> values = map.values();
        System.out.println("所有value: " + values);  // [10, 30, 20]
        
        // entrySet - 获取所有键值对的集合
        Set<Map.Entry<String, Integer>> entries = map.entrySet();
        System.out.println("所有entry: " + entries);
    }
}
```

## HashMap的遍历

### 遍历方式对比

```java
import java.util.*;

public class HashMapIteration {
    public static void main(String[] args) {
        HashMap<String, Integer> map = new HashMap<>();
        map.put("Apple", 10);
        map.put("Banana", 20);
        map.put("Orange", 30);
        
        // 方式1：遍历entrySet（推荐）
        System.out.println("=== 方式1：entrySet ===");
        for (Map.Entry<String, Integer> entry : map.entrySet()) {
            System.out.println(entry.getKey() + " = " + entry.getValue());
        }
        
        // 方式2：遍历keySet
        System.out.println("\n=== 方式2：keySet ===");
        for (String key : map.keySet()) {
            Integer value = map.get(key);
            System.out.println(key + " = " + value);
        }
        
        // 方式3：遍历values（只需要值）
        System.out.println("\n=== 方式3：values ===");
        for (Integer value : map.values()) {
            System.out.println(value);
        }
        
        // 方式4：使用Iterator
        System.out.println("\n=== 方式4：Iterator ===");
        Iterator<Map.Entry<String, Integer>> iterator = map.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<String, Integer> entry = iterator.next();
            System.out.println(entry.getKey() + " = " + entry.getValue());
        }
        
        // 方式5：Lambda表达式（Java 8+）
        System.out.println("\n=== 方式5：forEach ===");
        map.forEach((key, value) -> {
            System.out.println(key + " = " + value);
        });
        
        // 方式6：Stream API（Java 8+）
        System.out.println("\n=== 方式6：Stream ===");
        map.entrySet().stream()
            .forEach(entry -> System.out.println(entry.getKey() + " = " + entry.getValue()));
    }
}
```

### 遍历时删除元素

```java
public class HashMapIterationRemove {
    public static void main(String[] args) {
        HashMap<String, Integer> map = new HashMap<>();
        map.put("Apple", 10);
        map.put("Banana", 20);
        map.put("Orange", 30);
        map.put("Grape", 40);
        
        // ❌ 错误：直接在foreach中删除（会抛ConcurrentModificationException）
        try {
            for (String key : map.keySet()) {
                if (map.get(key) == 20) {
                    map.remove(key);  // 错误！
                }
            }
        } catch (ConcurrentModificationException e) {
            System.out.println("错误：不能在foreach中直接删除");
        }
        
        // ✅ 正确：使用Iterator删除
        Iterator<Map.Entry<String, Integer>> iterator = map.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<String, Integer> entry = iterator.next();
            if (entry.getValue() == 30) {
                iterator.remove();  // 正确
            }
        }
        System.out.println("使用Iterator删除后: " + map);
        
        // ✅ 正确：使用removeIf（Java 8+）
        map.entrySet().removeIf(entry -> entry.getValue() == 40);
        System.out.println("使用removeIf删除后: " + map);
    }
}
```

## HashMap的原理

### 哈希函数

```java
public class HashMapHash {
    
    // HashMap的hash方法
    static final int hash(Object key) {
        int h;
        // 1. 获取key的hashCode
        // 2. 高16位异或低16位（减少碰撞）
        return (key == null) ? 0 : (h = key.hashCode()) ^ (h >>> 16);
    }
    
    public static void main(String[] args) {
        String key = "Apple";
        int hashCode = key.hashCode();
        int hash = hash(key);
        
        System.out.println("hashCode: " + hashCode);
        System.out.println("hash: " + hash);
        
        // 计算数组索引
        int capacity = 16;
        int index = hash & (capacity - 1);  // 等价于 hash % capacity
        System.out.println("索引位置: " + index);
    }
}
```

### put操作流程

```java
/**
 * HashMap的put操作流程：
 * 
 * 1. 计算key的hash值
 *    hash = (key == null) ? 0 : (h = key.hashCode()) ^ (h >>> 16)
 * 
 * 2. 计算数组索引
 *    index = hash & (capacity - 1)
 * 
 * 3. 判断table[index]位置
 *    a. 如果为空，直接插入
 *    b. 如果不为空，发生哈希碰撞
 *       i. 如果key相同，覆盖value
 *       ii. 如果key不同，添加到链表或红黑树
 * 
 * 4. 判断是否需要扩容
 *    if (++size > threshold) resize()
 * 
 * 5. 判断是否需要树化
 *    if (链表长度 >= 8 && 容量 >= 64) treeifyBin()
 */

public class HashMapPutProcess {
    public static void main(String[] args) {
        HashMap<String, Integer> map = new HashMap<>();
        
        // 第一次put
        map.put("Apple", 10);
        // 1. hash("Apple") = ...
        // 2. index = hash & 15 = ...
        // 3. table[index] = new Node("Apple", 10)
        
        // 发生碰撞的put
        map.put("Banana", 20);
        // 如果hash("Banana")的索引与"Apple"相同
        // 则添加到链表: table[index] -> Node("Apple") -> Node("Banana")
    }
}
```

### get操作流程

```java
/**
 * HashMap的get操作流程：
 * 
 * 1. 计算key的hash值
 * 2. 计算数组索引 index = hash & (capacity - 1)
 * 3. 在table[index]位置查找
 *    a. 如果第一个节点就匹配，直接返回
 *    b. 如果是链表，遍历链表查找
 *    c. 如果是红黑树，在树中查找
 * 4. 返回value或null
 */

public class HashMapGetProcess {
    public static void main(String[] args) {
        HashMap<String, Integer> map = new HashMap<>();
        map.put("Apple", 10);
        map.put("Banana", 20);
        
        // get操作
        Integer value = map.get("Apple");
        // 1. hash("Apple") = ...
        // 2. index = hash & 15 = ...
        // 3. 在table[index]位置查找
        // 4. 返回10
    }
}
```

## HashMap的扩容机制

### 扩容原理

```java
public class HashMapResize {
    
    /**
     * HashMap扩容机制：
     * 
     * 触发条件：
     * size > threshold （threshold = capacity * loadFactor）
     * 
     * 扩容过程：
     * 1. 创建新数组，容量为原来的2倍
     * 2. 重新计算每个元素的位置
     * 3. 将元素移动到新数组
     * 
     * 优化（JDK 1.8+）：
     * - 不需要重新计算hash值
     * - 元素要么在原位置，要么在"原位置+旧容量"位置
     */
    
    public static void main(String[] args) {
        // 初始容量16，加载因子0.75
        HashMap<String, Integer> map = new HashMap<>();
        
        // threshold = 16 * 0.75 = 12
        // 当添加第13个元素时触发扩容
        
        for (int i = 0; i < 20; i++) {
            map.put("key" + i, i);
            
            if (i == 12) {
                System.out.println("触发扩容：size=" + map.size());
                // 新容量 = 16 * 2 = 32
                // 新threshold = 32 * 0.75 = 24
            }
        }
    }
}
```

### 扩容示例

```
扩容前（容量16）：
index  元素
[0]  → A
[1]  → B → C
[2]  → null
[3]  → D
...

扩容后（容量32）：
index  元素
[0]  → A        （原位置）
[1]  → B        （原位置）
[17] → C        （原位置 + 16）
[3]  → D        （原位置）
...

规律：
hash & oldCapacity == 0  → 保持原位置
hash & oldCapacity == 1  → 移动到（原位置 + oldCapacity）
```

## HashMap线程安全问题

### 多线程问题

```java
import java.util.concurrent.*;

public class HashMapThreadSafety {
    
    public static void main(String[] args) throws InterruptedException {
        // ❌ 不安全：HashMap在多线程环境下不安全
        HashMap<Integer, Integer> unsafeMap = new HashMap<>();
        
        // 创建多个线程同时操作HashMap
        ExecutorService executor = Executors.newFixedThreadPool(10);
        
        for (int i = 0; i < 10; i++) {
            final int threadNum = i;
            executor.execute(() -> {
                for (int j = 0; j < 1000; j++) {
                    unsafeMap.put(threadNum * 1000 + j, j);
                }
            });
        }
        
        executor.shutdown();
        executor.awaitTermination(1, TimeUnit.MINUTES);
        
        System.out.println("预期大小: 10000");
        System.out.println("实际大小: " + unsafeMap.size());
        // 实际大小可能小于10000（数据丢失）
        // 甚至可能死循环（JDK 1.7）
    }
}
```

### 线程安全的解决方案

```java
import java.util.*;
import java.util.concurrent.*;

public class ThreadSafeSolutions {
    
    public static void main(String[] args) {
        // 方案1：使用Collections.synchronizedMap
        Map<String, Integer> map1 = Collections.synchronizedMap(new HashMap<>());
        
        // 方案2：使用ConcurrentHashMap（推荐）
        ConcurrentHashMap<String, Integer> map2 = new ConcurrentHashMap<>();
        
        // 方案3：使用Hashtable（不推荐，已过时）
        Hashtable<String, Integer> map3 = new Hashtable<>();
        
        // 性能对比
        System.out.println("=== 性能对比 ===");
        testPerformance("SynchronizedMap", map1);
        testPerformance("ConcurrentHashMap", map2);
        testPerformance("Hashtable", map3);
    }
    
    private static void testPerformance(String name, Map<String, Integer> map) {
        long start = System.nanoTime();
        
        ExecutorService executor = Executors.newFixedThreadPool(10);
        for (int i = 0; i < 10; i++) {
            final int threadNum = i;
            executor.execute(() -> {
                for (int j = 0; j < 1000; j++) {
                    map.put("key" + (threadNum * 1000 + j), j);
                }
            });
        }
        
        executor.shutdown();
        try {
            executor.awaitTermination(1, TimeUnit.MINUTES);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        
        long end = System.nanoTime();
        System.out.println(name + " 耗时: " + (end - start) / 1000000 + "ms, 大小: " + map.size());
    }
}
```

## HashMap常见面试题

### 1. HashMap的底层实现

```java
/**
 * HashMap底层实现（JDK 1.8+）：
 * 
 * 数据结构：数组 + 链表 + 红黑树
 * 
 * - 数组：Node<K,V>[] table
 * - 链表：解决哈希冲突（链地址法）
 * - 红黑树：当链表长度>=8且容量>=64时，链表转红黑树
 * 
 * 关键参数：
 * - 初始容量：16
 * - 加载因子：0.75
 * - 树化阈值：8
 * - 退化阈值：6
 */
```

### 2. HashMap的put流程

```java
/**
 * put操作详细流程：
 * 
 * 1. 计算hash值: hash(key)
 * 2. 如果table为空，先扩容
 * 3. 计算索引: i = (n-1) & hash
 * 4. 如果table[i]为空，直接插入
 * 5. 如果table[i]不为空：
 *    a. 如果key相同，覆盖value
 *    b. 如果是树节点，在树中插入
 *    c. 如果是链表节点，遍历链表
 *       - 如果找到相同key，覆盖value
 *       - 如果没找到，插入到链表尾部
 *       - 插入后判断是否需要树化
 * 6. 判断是否需要扩容
 */
```

### 3. HashMap为什么线程不安全？

```java
/**
 * HashMap线程不安全的原因：
 * 
 * 1. 数据丢失
 *    两个线程同时put，可能覆盖对方的数据
 * 
 * 2. 死循环（JDK 1.7）
 *    扩容时形成环形链表，导致get操作死循环
 *    JDK 1.8已解决此问题
 * 
 * 3. 数据不一致
 *    size计数不准确
 * 
 * 解决方案：
 * - 使用ConcurrentHashMap
 * - 使用Collections.synchronizedMap
 * - 外部加锁
 */
```

### 4. HashMap和Hashtable的区别

```java
/**
 * HashMap vs Hashtable:
 * 
 * 1. 线程安全：
 *    HashMap：非线程安全
 *    Hashtable：线程安全（所有方法都synchronized）
 * 
 * 2. null键值：
 *    HashMap：允许一个null键和多个null值
 *    Hashtable：不允许null键和null值
 * 
 * 3. 性能：
 *    HashMap：性能高
 *    Hashtable：性能低（同步开销）
 * 
 * 4. 初始容量和扩容：
 *    HashMap：初始16，扩容2倍
 *    Hashtable：初始11，扩容2倍+1
 * 
 * 5. 迭代器：
 *    HashMap：fail-fast迭代器
 *    Hashtable：fail-fast迭代器
 * 
 * 推荐：
 * - 单线程：HashMap
 * - 多线程：ConcurrentHashMap
 */
```

### 5. HashMap的容量为什么是2的幂次？

```java
/**
 * HashMap容量为2的幂次的原因：
 * 
 * 1. 提高计算效率
 *    index = hash & (n-1)  // 等价于 hash % n，但更快
 *    当n是2的幂次时，n-1的二进制都是1，可以充分利用hash值
 * 
 * 2. 减少碰撞
 *    当n是2的幂次时，hash & (n-1) 可以保证索引均匀分布
 * 
 * 3. 扩容时的优化
 *    扩容后，元素要么在原位置，要么在"原位置+旧容量"位置
 *    不需要重新计算hash值
 * 
 * 示例：
 * n = 16 (10000)
 * n-1 = 15 (01111)
 * hash & (n-1) 可以取到 0-15 的所有值
 */

public class CapacityPowerOf2 {
    public static void main(String[] args) {
        int n = 16;
        int hash1 = 13;
        int hash2 = 29;
        
        System.out.println("n=" + n + ", n-1=" + (n-1));
        System.out.println("hash1=" + hash1 + ", index=" + (hash1 & (n-1)));
        System.out.println("hash2=" + hash2 + ", index=" + (hash2 & (n-1)));
        
        // 扩容后
        int newN = 32;
        System.out.println("\n扩容后 n=" + newN);
        System.out.println("hash1新index=" + (hash1 & (newN-1)));
        System.out.println("hash2新index=" + (hash2 & (newN-1)));
    }
}
```

### 6. HashMap的加载因子为什么是0.75？

```java
/**
 * 加载因子0.75的权衡：
 * 
 * 1. 空间利用率
 *    加载因子越大，空间利用率越高，但碰撞概率增加
 * 
 * 2. 时间效率
 *    加载因子越小，碰撞概率降低，但浪费空间
 * 
 * 3. 泊松分布
 *    根据统计学，0.75是时间和空间的最佳权衡点
 *    当加载因子为0.75时，链表长度为8的概率非常小（0.00000006）
 * 
 * 4. 实际应用
 *    - 如果内存紧张，可以增大加载因子（如0.9）
 *    - 如果追求性能，可以减小加载因子（如0.5）
 */
```

## 最佳实践

### 1. 指定初始容量

```java
// ✅ 推荐：预估元素数量，指定初始容量
int expectedSize = 100;
HashMap<String, Integer> map = new HashMap<>((int) (expectedSize / 0.75) + 1);

// ❌ 避免：使用默认容量，频繁扩容
HashMap<String, Integer> map2 = new HashMap<>();
for (int i = 0; i < 1000; i++) {
    map2.put("key" + i, i);  // 会触发多次扩容
}
```

### 2. 合理选择key类型

```java
// ✅ 推荐：使用不可变类作为key
HashMap<String, Integer> map1 = new HashMap<>();
map1.put("key", 1);  // String是不可变的

// ✅ 推荐：重写equals()和hashCode()
class Person {
    private String name;
    private int age;
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Person person = (Person) o;
        return age == person.age && Objects.equals(name, person.name);
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(name, age);
    }
}

// ❌ 避免：使用可变对象作为key
HashMap<List<String>, Integer> map2 = new HashMap<>();
List<String> key = new ArrayList<>();
key.add("a");
map2.put(key, 1);
key.add("b");  // 修改key后，无法找到对应的value
```

### 3. 选择合适的遍历方式

```java
HashMap<String, Integer> map = new HashMap<>();

// ✅ 推荐：需要key和value时使用entrySet
for (Map.Entry<String, Integer> entry : map.entrySet()) {
    String key = entry.getKey();
    Integer value = entry.getValue();
}

// ❌ 避免：需要key和value时使用keySet（效率低）
for (String key : map.keySet()) {
    Integer value = map.get(key);  // 额外的查找操作
}

// ✅ 推荐：只需要value时使用values
for (Integer value : map.values()) {
    // ...
}
```

### 4. 多线程环境使用ConcurrentHashMap

```java
// ✅ 推荐：多线程环境使用ConcurrentHashMap
ConcurrentHashMap<String, Integer> map1 = new ConcurrentHashMap<>();

// ❌ 避免：多线程环境使用HashMap
HashMap<String, Integer> map2 = new HashMap<>();
```

### 5. 避免在遍历时修改

```java
HashMap<String, Integer> map = new HashMap<>();

// ❌ 避免：直接在foreach中删除
for (String key : map.keySet()) {
    if (condition) {
        map.remove(key);  // ConcurrentModificationException
    }
}

// ✅ 推荐：使用Iterator删除
Iterator<Map.Entry<String, Integer>> iterator = map.entrySet().iterator();
while (iterator.hasNext()) {
    Map.Entry<String, Integer> entry = iterator.next();
    if (condition) {
        iterator.remove();  // 正确
    }
}

// ✅ 推荐：使用removeIf
map.entrySet().removeIf(entry -> condition);
```

## 总结

### HashMap核心要点

1. **数据结构** - 数组 + 链表 + 红黑树
2. **哈希函数** - 高16位异或低16位
3. **解决冲突** - 链地址法（链表或红黑树）
4. **扩容机制** - 2倍扩容，元素重新分布
5. **线程安全** - 非线程安全，多线程用ConcurrentHashMap

### 性能特点

| 操作 | 平均时间复杂度 | 最坏时间复杂度 |
|------|---------------|---------------|
| get | O(1) | O(log n) |
| put | O(1) | O(log n) |
| remove | O(1) | O(log n) |
| containsKey | O(1) | O(log n) |

### 选择建议

| 场景 | 推荐使用 |
|------|---------|
| 单线程 | HashMap |
| 多线程 | ConcurrentHashMap |
| 有序遍历 | LinkedHashMap |
| 排序 | TreeMap |
| 线程安全+有序 | Collections.synchronizedMap(LinkedHashMap) |

通过掌握这些HashMap知识点，您将能够更高效地使用Java集合框架！




