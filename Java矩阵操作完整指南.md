# Java矩阵操作完整指南

## 目录
1. [矩阵基础概念](#矩阵基础概念)
2. [矩阵的创建](#矩阵的创建)
3. [矩阵的遍历](#矩阵的遍历)
4. [矩阵的基本操作](#矩阵的基本操作)
5. [矩阵的常见算法](#矩阵的常见算法)
6. [特殊矩阵处理](#特殊矩阵处理)
7. [矩阵旋转与翻转](#矩阵旋转与翻转)
8. [矩阵搜索算法](#矩阵搜索算法)
9. [矩阵动态规划](#矩阵动态规划)
10. [最佳实践与优化](#最佳实践与优化)

## 矩阵基础概念

### 什么是矩阵？

在Java中，矩阵通常用**二维数组**表示：

```java
int[][] matrix = {
    {1, 2, 3},
    {4, 5, 6},
    {7, 8, 9}
};
```

### 矩阵的表示方式

```
矩阵结构：
        列0  列1  列2
行0     1    2    3
行1     4    5    6
行2     7    8    9

访问方式：matrix[行][列]
例如：matrix[1][2] = 6
```

### 矩阵的基本术语

| 术语 | 说明 | 示例 |
|------|------|------|
| **行数** (rows) | 矩阵的行数量 | m = 3 |
| **列数** (cols) | 矩阵的列数量 | n = 3 |
| **维度** | m × n | 3 × 3 矩阵 |
| **方阵** | 行数等于列数 | 3 × 3 |
| **元素** | matrix[i][j] | matrix[1][2] = 6 |
| **主对角线** | i == j 的元素 | 1, 5, 9 |
| **副对角线** | i + j == n - 1 | 3, 5, 7 |

### 常见矩阵类型

```java
// 1. 零矩阵 - 所有元素都是0
int[][] zero = {
    {0, 0, 0},
    {0, 0, 0},
    {0, 0, 0}
};

// 2. 单位矩阵 - 主对角线为1，其他为0
int[][] identity = {
    {1, 0, 0},
    {0, 1, 0},
    {0, 0, 1}
};

// 3. 上三角矩阵 - 主对角线下方都是0
int[][] upper = {
    {1, 2, 3},
    {0, 5, 6},
    {0, 0, 9}
};

// 4. 下三角矩阵 - 主对角线上方都是0
int[][] lower = {
    {1, 0, 0},
    {4, 5, 0},
    {7, 8, 9}
};

// 5. 对称矩阵 - matrix[i][j] == matrix[j][i]
int[][] symmetric = {
    {1, 2, 3},
    {2, 5, 6},
    {3, 6, 9}
};
```

## 矩阵的创建

### 1. 直接初始化

```java
public class MatrixCreation {
    public static void main(String[] args) {
        // 方式1：直接赋值（推荐用于已知数据）
        int[][] matrix1 = {
            {1, 2, 3},
            {4, 5, 6},
            {7, 8, 9}
        };
        
        // 方式2：声明后赋值
        int[][] matrix2 = new int[3][3];
        matrix2[0][0] = 1;
        matrix2[0][1] = 2;
        // ...
        
        // 方式3：指定行数，列数可变（锯齿数组）
        int[][] jagged = new int[3][];
        jagged[0] = new int[2];  // 第一行2列
        jagged[1] = new int[3];  // 第二行3列
        jagged[2] = new int[4];  // 第三行4列
    }
}
```

### 2. 动态创建

```java
public class DynamicMatrix {
    
    /**
     * 创建指定大小的零矩阵
     */
    public static int[][] createZeroMatrix(int rows, int cols) {
        return new int[rows][cols];  // 默认填充0
    }
    
    /**
     * 创建指定大小并填充指定值的矩阵
     */
    public static int[][] createFilledMatrix(int rows, int cols, int value) {
        int[][] matrix = new int[rows][cols];
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                matrix[i][j] = value;
            }
        }
        return matrix;
    }
    
    /**
     * 创建单位矩阵
     */
    public static int[][] createIdentityMatrix(int size) {
        int[][] matrix = new int[size][size];
        for (int i = 0; i < size; i++) {
            matrix[i][i] = 1;
        }
        return matrix;
    }
    
    /**
     * 创建随机矩阵
     */
    public static int[][] createRandomMatrix(int rows, int cols, int min, int max) {
        int[][] matrix = new int[rows][cols];
        Random random = new Random();
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                matrix[i][j] = random.nextInt(max - min + 1) + min;
            }
        }
        return matrix;
    }
    
    /**
     * 从一维数组创建矩阵
     */
    public static int[][] createFromArray(int[] arr, int rows, int cols) {
        if (arr.length != rows * cols) {
            throw new IllegalArgumentException("数组长度不匹配");
        }
        int[][] matrix = new int[rows][cols];
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                matrix[i][j] = arr[i * cols + j];
            }
        }
        return matrix;
    }
    
    public static void main(String[] args) {
        // 创建3x3的零矩阵
        int[][] zero = createZeroMatrix(3, 3);
        
        // 创建3x3的矩阵，所有元素填充为5
        int[][] filled = createFilledMatrix(3, 3, 5);
        
        // 创建3x3的单位矩阵
        int[][] identity = createIdentityMatrix(3);
        
        // 创建3x3的随机矩阵（值范围1-10）
        int[][] random = createRandomMatrix(3, 3, 1, 10);
        
        // 从一维数组创建矩阵
        int[] arr = {1, 2, 3, 4, 5, 6, 7, 8, 9};
        int[][] fromArray = createFromArray(arr, 3, 3);
    }
}
```

### 3. 复制矩阵

```java
public class MatrixCopy {
    
    /**
     * 浅拷贝（不推荐）
     */
    public static int[][] shallowCopy(int[][] matrix) {
        // ❌ 只复制引用，修改会影响原矩阵
        return matrix;
    }
    
    /**
     * 深拷贝方式1：手动复制
     */
    public static int[][] deepCopy1(int[][] matrix) {
        if (matrix == null) return null;
        int rows = matrix.length;
        int cols = matrix[0].length;
        int[][] copy = new int[rows][cols];
        
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                copy[i][j] = matrix[i][j];
            }
        }
        return copy;
    }
    
    /**
     * 深拷贝方式2：使用System.arraycopy
     */
    public static int[][] deepCopy2(int[][] matrix) {
        if (matrix == null) return null;
        int rows = matrix.length;
        int[][] copy = new int[rows][];
        
        for (int i = 0; i < rows; i++) {
            copy[i] = new int[matrix[i].length];
            System.arraycopy(matrix[i], 0, copy[i], 0, matrix[i].length);
        }
        return copy;
    }
    
    /**
     * 深拷贝方式3：使用Arrays.copyOf
     */
    public static int[][] deepCopy3(int[][] matrix) {
        if (matrix == null) return null;
        int rows = matrix.length;
        int[][] copy = new int[rows][];
        
        for (int i = 0; i < rows; i++) {
            copy[i] = Arrays.copyOf(matrix[i], matrix[i].length);
        }
        return copy;
    }
    
    /**
     * 深拷贝方式4：使用clone()
     */
    public static int[][] deepCopy4(int[][] matrix) {
        if (matrix == null) return null;
        int rows = matrix.length;
        int[][] copy = matrix.clone();
        
        for (int i = 0; i < rows; i++) {
            copy[i] = matrix[i].clone();
        }
        return copy;
    }
}
```

## 矩阵的遍历

### 1. 基本遍历方式

```java
public class MatrixTraversal {
    
    /**
     * 方式1：标准双层循环（最常用）
     */
    public static void traverse1(int[][] matrix) {
        int rows = matrix.length;
        int cols = matrix[0].length;
        
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                System.out.print(matrix[i][j] + " ");
            }
            System.out.println();
        }
    }
    
    /**
     * 方式2：增强for循环
     */
    public static void traverse2(int[][] matrix) {
        for (int[] row : matrix) {
            for (int element : row) {
                System.out.print(element + " ");
            }
            System.out.println();
        }
    }
    
    /**
     * 方式3：Stream API（Java 8+）
     */
    public static void traverse3(int[][] matrix) {
        Arrays.stream(matrix)
            .forEach(row -> {
                Arrays.stream(row)
                    .forEach(element -> System.out.print(element + " "));
                System.out.println();
            });
    }
    
    /**
     * 方式4：按列遍历
     */
    public static void traverseByColumn(int[][] matrix) {
        int rows = matrix.length;
        int cols = matrix[0].length;
        
        for (int j = 0; j < cols; j++) {
            for (int i = 0; i < rows; i++) {
                System.out.print(matrix[i][j] + " ");
            }
            System.out.println();
        }
    }
}
```

### 2. 特殊遍历方式

```java
public class SpecialTraversal {
    
    /**
     * 对角线遍历 - 主对角线
     */
    public static void traverseMainDiagonal(int[][] matrix) {
        int n = Math.min(matrix.length, matrix[0].length);
        System.out.print("主对角线: ");
        for (int i = 0; i < n; i++) {
            System.out.print(matrix[i][i] + " ");
        }
        System.out.println();
    }
    
    /**
     * 对角线遍历 - 副对角线
     */
    public static void traverseAntiDiagonal(int[][] matrix) {
        int rows = matrix.length;
        int cols = matrix[0].length;
        int n = Math.min(rows, cols);
        
        System.out.print("副对角线: ");
        for (int i = 0; i < n; i++) {
            System.out.print(matrix[i][cols - 1 - i] + " ");
        }
        System.out.println();
    }
    
    /**
     * 螺旋遍历（顺时针）
     */
    public static List<Integer> spiralTraversal(int[][] matrix) {
        List<Integer> result = new ArrayList<>();
        if (matrix == null || matrix.length == 0) return result;
        
        int top = 0, bottom = matrix.length - 1;
        int left = 0, right = matrix[0].length - 1;
        
        while (top <= bottom && left <= right) {
            // 从左到右
            for (int j = left; j <= right; j++) {
                result.add(matrix[top][j]);
            }
            top++;
            
            // 从上到下
            for (int i = top; i <= bottom; i++) {
                result.add(matrix[i][right]);
            }
            right--;
            
            // 从右到左
            if (top <= bottom) {
                for (int j = right; j >= left; j--) {
                    result.add(matrix[bottom][j]);
                }
                bottom--;
            }
            
            // 从下到上
            if (left <= right) {
                for (int i = bottom; i >= top; i--) {
                    result.add(matrix[i][left]);
                }
                left++;
            }
        }
        
        return result;
    }
    
    /**
     * Z字形遍历（对角线）
     */
    public static List<Integer> zigzagTraversal(int[][] matrix) {
        List<Integer> result = new ArrayList<>();
        if (matrix == null || matrix.length == 0) return result;
        
        int rows = matrix.length;
        int cols = matrix[0].length;
        int[] row = new int[rows + cols - 1];
        
        // 按对角线分组
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                int diag = i + j;
                // 根据对角线编号决定顺序
                if (diag % 2 == 0) {
                    result.add(matrix[i][j]);
                } else {
                    result.add(0, matrix[i][j]);
                }
            }
        }
        
        return result;
    }
    
    /**
     * 环形遍历（层次遍历）
     */
    public static void layerTraversal(int[][] matrix) {
        int rows = matrix.length;
        int cols = matrix[0].length;
        int layers = (Math.min(rows, cols) + 1) / 2;
        
        for (int layer = 0; layer < layers; layer++) {
            System.out.print("第" + (layer + 1) + "层: ");
            
            // 上边
            for (int j = layer; j < cols - layer; j++) {
                System.out.print(matrix[layer][j] + " ");
            }
            
            // 右边
            for (int i = layer + 1; i < rows - layer; i++) {
                System.out.print(matrix[i][cols - layer - 1] + " ");
            }
            
            // 下边
            if (rows - layer - 1 > layer) {
                for (int j = cols - layer - 2; j >= layer; j--) {
                    System.out.print(matrix[rows - layer - 1][j] + " ");
                }
            }
            
            // 左边
            if (cols - layer - 1 > layer) {
                for (int i = rows - layer - 2; i > layer; i--) {
                    System.out.print(matrix[i][layer] + " ");
                }
            }
            
            System.out.println();
        }
    }
}
```

### 遍历方向图解

```
标准遍历（行优先）：
1  → 2  → 3
↓    ↓    ↓
4  → 5  → 6
↓    ↓    ↓
7  → 8  → 9

列优先遍历：
1  4  7
↓  ↓  ↓
2  5  8
↓  ↓  ↓
3  6  9

螺旋遍历：
1  → 2  → 3
          ↓
8  → 9    4
↑         ↓
7  ← 6  ← 5

对角线遍历：
1      
↓ ↘    
2  5    
↓ ↘ ↘  
3  6  8
↓ ↘ ↘ ↘
4  7  9
```

## 矩阵的基本操作

### 1. 矩阵的打印

```java
public class MatrixPrint {
    
    /**
     * 标准打印
     */
    public static void printMatrix(int[][] matrix) {
        for (int[] row : matrix) {
            for (int element : row) {
                System.out.print(element + " ");
            }
            System.out.println();
        }
    }
    
    /**
     * 格式化打印（对齐）
     */
    public static void printFormatted(int[][] matrix) {
        // 找出最大宽度
        int maxWidth = 0;
        for (int[] row : matrix) {
            for (int element : row) {
                maxWidth = Math.max(maxWidth, String.valueOf(element).length());
            }
        }
        
        // 格式化打印
        for (int[] row : matrix) {
            for (int element : row) {
                System.out.printf("%" + maxWidth + "d ", element);
            }
            System.out.println();
        }
    }
    
    /**
     * 带边框打印
     */
    public static void printWithBorder(int[][] matrix) {
        int rows = matrix.length;
        int cols = matrix[0].length;
        
        // 上边框
        System.out.print("┌");
        for (int j = 0; j < cols; j++) {
            System.out.print("────");
        }
        System.out.println("┐");
        
        // 内容
        for (int i = 0; i < rows; i++) {
            System.out.print("│");
            for (int j = 0; j < cols; j++) {
                System.out.printf(" %2d ", matrix[i][j]);
            }
            System.out.println("│");
        }
        
        // 下边框
        System.out.print("└");
        for (int j = 0; j < cols; j++) {
            System.out.print("────");
        }
        System.out.println("┘");
    }
    
    /**
     * 转换为字符串
     */
    public static String matrixToString(int[][] matrix) {
        StringBuilder sb = new StringBuilder();
        sb.append("[\n");
        for (int i = 0; i < matrix.length; i++) {
            sb.append("  [");
            for (int j = 0; j < matrix[i].length; j++) {
                sb.append(matrix[i][j]);
                if (j < matrix[i].length - 1) sb.append(", ");
            }
            sb.append("]");
            if (i < matrix.length - 1) sb.append(",");
            sb.append("\n");
        }
        sb.append("]");
        return sb.toString();
    }
}
```

### 2. 矩阵的转置

```java
public class MatrixTranspose {
    
    /**
     * 转置（创建新矩阵）
     */
    public static int[][] transpose(int[][] matrix) {
        int rows = matrix.length;
        int cols = matrix[0].length;
        int[][] result = new int[cols][rows];
        
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                result[j][i] = matrix[i][j];
            }
        }
        
        return result;
    }
    
    /**
     * 原地转置（仅适用于方阵）
     */
    public static void transposeInPlace(int[][] matrix) {
        int n = matrix.length;
        
        for (int i = 0; i < n; i++) {
            for (int j = i + 1; j < n; j++) {
                // 交换matrix[i][j]和matrix[j][i]
                int temp = matrix[i][j];
                matrix[i][j] = matrix[j][i];
                matrix[j][i] = temp;
            }
        }
    }
    
    public static void main(String[] args) {
        int[][] matrix = {
            {1, 2, 3},
            {4, 5, 6},
            {7, 8, 9}
        };
        
        System.out.println("原矩阵:");
        MatrixPrint.printMatrix(matrix);
        
        int[][] transposed = transpose(matrix);
        System.out.println("\n转置后:");
        MatrixPrint.printMatrix(transposed);
        
        // 输出:
        // 原矩阵:      转置后:
        // 1 2 3        1 4 7
        // 4 5 6   →    2 5 8
        // 7 8 9        3 6 9
    }
}
```

### 3. 矩阵的加法和减法

```java
public class MatrixArithmetic {
    
    /**
     * 矩阵加法
     */
    public static int[][] add(int[][] a, int[][] b) {
        if (a.length != b.length || a[0].length != b[0].length) {
            throw new IllegalArgumentException("矩阵维度不匹配");
        }
        
        int rows = a.length;
        int cols = a[0].length;
        int[][] result = new int[rows][cols];
        
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                result[i][j] = a[i][j] + b[i][j];
            }
        }
        
        return result;
    }
    
    /**
     * 矩阵减法
     */
    public static int[][] subtract(int[][] a, int[][] b) {
        if (a.length != b.length || a[0].length != b[0].length) {
            throw new IllegalArgumentException("矩阵维度不匹配");
        }
        
        int rows = a.length;
        int cols = a[0].length;
        int[][] result = new int[rows][cols];
        
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                result[i][j] = a[i][j] - b[i][j];
            }
        }
        
        return result;
    }
    
    /**
     * 标量乘法
     */
    public static int[][] scalarMultiply(int[][] matrix, int scalar) {
        int rows = matrix.length;
        int cols = matrix[0].length;
        int[][] result = new int[rows][cols];
        
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                result[i][j] = matrix[i][j] * scalar;
            }
        }
        
        return result;
    }
}
```

### 4. 矩阵乘法

```java
public class MatrixMultiplication {
    
    /**
     * 标准矩阵乘法
     * A(m×n) × B(n×p) = C(m×p)
     */
    public static int[][] multiply(int[][] a, int[][] b) {
        int m = a.length;
        int n = a[0].length;
        int p = b[0].length;
        
        if (n != b.length) {
            throw new IllegalArgumentException("矩阵维度不匹配：A的列数必须等于B的行数");
        }
        
        int[][] result = new int[m][p];
        
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < p; j++) {
                for (int k = 0; k < n; k++) {
                    result[i][j] += a[i][k] * b[k][j];
                }
            }
        }
        
        return result;
    }
    
    /**
     * 优化的矩阵乘法（缓存友好）
     */
    public static int[][] multiplyOptimized(int[][] a, int[][] b) {
        int m = a.length;
        int n = a[0].length;
        int p = b[0].length;
        
        int[][] result = new int[m][p];
        
        // 转置B矩阵以提高缓存命中率
        int[][] bT = MatrixTranspose.transpose(b);
        
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < p; j++) {
                int sum = 0;
                for (int k = 0; k < n; k++) {
                    sum += a[i][k] * bT[j][k];
                }
                result[i][j] = sum;
            }
        }
        
        return result;
    }
    
    public static void main(String[] args) {
        int[][] a = {
            {1, 2, 3},
            {4, 5, 6}
        };  // 2×3
        
        int[][] b = {
            {7, 8},
            {9, 10},
            {11, 12}
        };  // 3×2
        
        int[][] c = multiply(a, b);  // 结果: 2×2
        
        // c[0][0] = 1*7 + 2*9 + 3*11 = 58
        // c[0][1] = 1*8 + 2*10 + 3*12 = 64
        // c[1][0] = 4*7 + 5*9 + 6*11 = 139
        // c[1][1] = 4*8 + 5*10 + 6*12 = 154
    }
}
```

## 矩阵的常见算法

### 1. 矩阵搜索

```java
public class MatrixSearch {
    
    /**
     * 线性搜索（无序矩阵）
     * 时间复杂度：O(m*n)
     */
    public static boolean linearSearch(int[][] matrix, int target) {
        for (int[] row : matrix) {
            for (int element : row) {
                if (element == target) return true;
            }
        }
        return false;
    }
    
    /**
     * 搜索有序矩阵（每行递增，每列递增）
     * 时间复杂度：O(m+n)
     */
    public static boolean searchSortedMatrix(int[][] matrix, int target) {
        if (matrix == null || matrix.length == 0) return false;
        
        int rows = matrix.length;
        int cols = matrix[0].length;
        
        // 从右上角开始搜索
        int i = 0;
        int j = cols - 1;
        
        while (i < rows && j >= 0) {
            if (matrix[i][j] == target) {
                return true;
            } else if (matrix[i][j] > target) {
                j--;  // 向左移动
            } else {
                i++;  // 向下移动
            }
        }
        
        return false;
    }
    
    /**
     * 二分搜索（完全有序矩阵）
     * 时间复杂度：O(log(m*n))
     */
    public static boolean binarySearch(int[][] matrix, int target) {
        if (matrix == null || matrix.length == 0) return false;
        
        int rows = matrix.length;
        int cols = matrix[0].length;
        int left = 0;
        int right = rows * cols - 1;
        
        while (left <= right) {
            int mid = left + (right - left) / 2;
            int midValue = matrix[mid / cols][mid % cols];
            
            if (midValue == target) {
                return true;
            } else if (midValue < target) {
                left = mid + 1;
            } else {
                right = mid - 1;
            }
        }
        
        return false;
    }
}
```

### 2. 找出矩阵中的最大/最小值

```java
public class MatrixMinMax {
    
    /**
     * 找出最大值
     */
    public static int findMax(int[][] matrix) {
        int max = Integer.MIN_VALUE;
        for (int[] row : matrix) {
            for (int element : row) {
                max = Math.max(max, element);
            }
        }
        return max;
    }
    
    /**
     * 找出最小值
     */
    public static int findMin(int[][] matrix) {
        int min = Integer.MAX_VALUE;
        for (int[] row : matrix) {
            for (int element : row) {
                min = Math.min(min, element);
            }
        }
        return min;
    }
    
    /**
     * 找出最大值的位置
     */
    public static int[] findMaxPosition(int[][] matrix) {
        int max = Integer.MIN_VALUE;
        int[] position = new int[2];
        
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[i].length; j++) {
                if (matrix[i][j] > max) {
                    max = matrix[i][j];
                    position[0] = i;
                    position[1] = j;
                }
            }
        }
        
        return position;
    }
    
    /**
     * 找出每行的最大值
     */
    public static int[] findRowMaximums(int[][] matrix) {
        int[] maxs = new int[matrix.length];
        
        for (int i = 0; i < matrix.length; i++) {
            int max = Integer.MIN_VALUE;
            for (int j = 0; j < matrix[i].length; j++) {
                max = Math.max(max, matrix[i][j]);
            }
            maxs[i] = max;
        }
        
        return maxs;
    }
    
    /**
     * 找出每列的最大值
     */
    public static int[] findColMaximums(int[][] matrix) {
        int cols = matrix[0].length;
        int[] maxs = new int[cols];
        Arrays.fill(maxs, Integer.MIN_VALUE);
        
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < cols; j++) {
                maxs[j] = Math.max(maxs[j], matrix[i][j]);
            }
        }
        
        return maxs;
    }
}
```

### 3. 矩阵求和

```java
public class MatrixSum {
    
    /**
     * 求所有元素的和
     */
    public static int sumAll(int[][] matrix) {
        int sum = 0;
        for (int[] row : matrix) {
            for (int element : row) {
                sum += element;
            }
        }
        return sum;
    }
    
    /**
     * 求主对角线的和
     */
    public static int sumMainDiagonal(int[][] matrix) {
        int sum = 0;
        int n = Math.min(matrix.length, matrix[0].length);
        for (int i = 0; i < n; i++) {
            sum += matrix[i][i];
        }
        return sum;
    }
    
    /**
     * 求副对角线的和
     */
    public static int sumAntiDiagonal(int[][] matrix) {
        int sum = 0;
        int rows = matrix.length;
        int cols = matrix[0].length;
        int n = Math.min(rows, cols);
        
        for (int i = 0; i < n; i++) {
            sum += matrix[i][cols - 1 - i];
        }
        return sum;
    }
    
    /**
     * 求每行的和
     */
    public static int[] sumRows(int[][] matrix) {
        int[] sums = new int[matrix.length];
        
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[i].length; j++) {
                sums[i] += matrix[i][j];
            }
        }
        
        return sums;
    }
    
    /**
     * 求每列的和
     */
    public static int[] sumCols(int[][] matrix) {
        int cols = matrix[0].length;
        int[] sums = new int[cols];
        
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < cols; j++) {
                sums[j] += matrix[i][j];
            }
        }
        
        return sums;
    }
    
    /**
     * 区域和查询（前缀和优化）
     * 预处理：O(m*n)，查询：O(1)
     */
    static class MatrixPrefixSum {
        private int[][] prefixSum;
        
        public MatrixPrefixSum(int[][] matrix) {
            int rows = matrix.length;
            int cols = matrix[0].length;
            prefixSum = new int[rows + 1][cols + 1];
            
            // 构建前缀和矩阵
            for (int i = 1; i <= rows; i++) {
                for (int j = 1; j <= cols; j++) {
                    prefixSum[i][j] = matrix[i-1][j-1]
                                    + prefixSum[i-1][j]
                                    + prefixSum[i][j-1]
                                    - prefixSum[i-1][j-1];
                }
            }
        }
        
        /**
         * 查询子矩阵和 [row1, col1] 到 [row2, col2]
         */
        public int query(int row1, int col1, int row2, int col2) {
            return prefixSum[row2+1][col2+1]
                 - prefixSum[row1][col2+1]
                 - prefixSum[row2+1][col1]
                 + prefixSum[row1][col1];
        }
    }
}
```

## 特殊矩阵处理

### 1. 零矩阵设置

```java
/**
 * LeetCode 73: 矩阵置零
 * 给定一个矩阵，如果一个元素为0，则将其所在行和列的所有元素都设为0
 */
public class SetMatrixZeroes {
    
    /**
     * 方法1：使用额外空间
     * 空间复杂度：O(m+n)
     */
    public void setZeroes1(int[][] matrix) {
        int rows = matrix.length;
        int cols = matrix[0].length;
        Set<Integer> zeroRows = new HashSet<>();
        Set<Integer> zeroCols = new HashSet<>();
        
        // 记录哪些行和列需要置零
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                if (matrix[i][j] == 0) {
                    zeroRows.add(i);
                    zeroCols.add(j);
                }
            }
        }
        
        // 置零
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                if (zeroRows.contains(i) || zeroCols.contains(j)) {
                    matrix[i][j] = 0;
                }
            }
        }
    }
    
    /**
     * 方法2：原地算法
     * 空间复杂度：O(1)
     */
    public void setZeroes2(int[][] matrix) {
        int rows = matrix.length;
        int cols = matrix[0].length;
        boolean firstRowZero = false;
        boolean firstColZero = false;
        
        // 检查第一行是否需要置零
        for (int j = 0; j < cols; j++) {
            if (matrix[0][j] == 0) {
                firstRowZero = true;
                break;
            }
        }
        
        // 检查第一列是否需要置零
        for (int i = 0; i < rows; i++) {
            if (matrix[i][0] == 0) {
                firstColZero = true;
                break;
            }
        }
        
        // 使用第一行和第一列作为标记
        for (int i = 1; i < rows; i++) {
            for (int j = 1; j < cols; j++) {
                if (matrix[i][j] == 0) {
                    matrix[i][0] = 0;
                    matrix[0][j] = 0;
                }
            }
        }
        
        // 根据标记置零
        for (int i = 1; i < rows; i++) {
            for (int j = 1; j < cols; j++) {
                if (matrix[i][0] == 0 || matrix[0][j] == 0) {
                    matrix[i][j] = 0;
                }
            }
        }
        
        // 处理第一行
        if (firstRowZero) {
            for (int j = 0; j < cols; j++) {
                matrix[0][j] = 0;
            }
        }
        
        // 处理第一列
        if (firstColZero) {
            for (int i = 0; i < rows; i++) {
                matrix[i][0] = 0;
            }
        }
    }
}
```

### 2. 判断特殊矩阵

```java
public class SpecialMatrixCheck {
    
    /**
     * 判断是否为对称矩阵
     */
    public static boolean isSymmetric(int[][] matrix) {
        int n = matrix.length;
        if (n != matrix[0].length) return false;
        
        for (int i = 0; i < n; i++) {
            for (int j = i + 1; j < n; j++) {
                if (matrix[i][j] != matrix[j][i]) {
                    return false;
                }
            }
        }
        return true;
    }
    
    /**
     * 判断是否为单位矩阵
     */
    public static boolean isIdentity(int[][] matrix) {
        int n = matrix.length;
        if (n != matrix[0].length) return false;
        
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (i == j) {
                    if (matrix[i][j] != 1) return false;
                } else {
                    if (matrix[i][j] != 0) return false;
                }
            }
        }
        return true;
    }
    
    /**
     * 判断是否为上三角矩阵
     */
    public static boolean isUpperTriangular(int[][] matrix) {
        int n = matrix.length;
        if (n != matrix[0].length) return false;
        
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < i; j++) {
                if (matrix[i][j] != 0) return false;
            }
        }
        return true;
    }
    
    /**
     * 判断是否为下三角矩阵
     */
    public static boolean isLowerTriangular(int[][] matrix) {
        int n = matrix.length;
        if (n != matrix[0].length) return false;
        
        for (int i = 0; i < n; i++) {
            for (int j = i + 1; j < n; j++) {
                if (matrix[i][j] != 0) return false;
            }
        }
        return true;
    }
    
    /**
     * 判断是否为对角矩阵
     */
    public static boolean isDiagonal(int[][] matrix) {
        int n = matrix.length;
        if (n != matrix[0].length) return false;
        
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (i != j && matrix[i][j] != 0) {
                    return false;
                }
            }
        }
        return true;
    }
    
    /**
     * 判断是否为托普利茨矩阵
     * 每条对角线上的元素都相同
     */
    public static boolean isToeplitz(int[][] matrix) {
        int rows = matrix.length;
        int cols = matrix[0].length;
        
        for (int i = 0; i < rows - 1; i++) {
            for (int j = 0; j < cols - 1; j++) {
                if (matrix[i][j] != matrix[i+1][j+1]) {
                    return false;
                }
            }
        }
        return true;
    }
}
```

## 矩阵旋转与翻转

### 1. 矩阵旋转

```java
public class MatrixRotation {
    
    /**
     * 顺时针旋转90度（创建新矩阵）
     */
    public static int[][] rotateClockwise90(int[][] matrix) {
        int rows = matrix.length;
        int cols = matrix[0].length;
        int[][] result = new int[cols][rows];
        
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                result[j][rows - 1 - i] = matrix[i][j];
            }
        }
        
        return result;
    }
    
    /**
     * 原地顺时针旋转90度（方阵）
     * 方法：先转置，再水平翻转
     */
    public static void rotateClockwise90InPlace(int[][] matrix) {
        int n = matrix.length;
        
        // 步骤1：转置
        for (int i = 0; i < n; i++) {
            for (int j = i + 1; j < n; j++) {
                int temp = matrix[i][j];
                matrix[i][j] = matrix[j][i];
                matrix[j][i] = temp;
            }
        }
        
        // 步骤2：水平翻转（每行反转）
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n / 2; j++) {
                int temp = matrix[i][j];
                matrix[i][j] = matrix[i][n - 1 - j];
                matrix[i][n - 1 - j] = temp;
            }
        }
    }
    
    /**
     * 逆时针旋转90度
     * 方法：先转置，再垂直翻转
     */
    public static void rotateCounterClockwise90(int[][] matrix) {
        int n = matrix.length;
        
        // 步骤1：转置
        for (int i = 0; i < n; i++) {
            for (int j = i + 1; j < n; j++) {
                int temp = matrix[i][j];
                matrix[i][j] = matrix[j][i];
                matrix[j][i] = temp;
            }
        }
        
        // 步骤2：垂直翻转（每列反转）
        for (int i = 0; i < n / 2; i++) {
            for (int j = 0; j < n; j++) {
                int temp = matrix[i][j];
                matrix[i][j] = matrix[n - 1 - i][j];
                matrix[n - 1 - i][j] = temp;
            }
        }
    }
    
    /**
     * 旋转180度
     */
    public static void rotate180(int[][] matrix) {
        int rows = matrix.length;
        int cols = matrix[0].length;
        
        for (int i = 0; i < rows / 2; i++) {
            for (int j = 0; j < cols; j++) {
                int temp = matrix[i][j];
                matrix[i][j] = matrix[rows - 1 - i][cols - 1 - j];
                matrix[rows - 1 - i][cols - 1 - j] = temp;
            }
        }
        
        // 如果行数是奇数，处理中间行
        if (rows % 2 == 1) {
            int mid = rows / 2;
            for (int j = 0; j < cols / 2; j++) {
                int temp = matrix[mid][j];
                matrix[mid][j] = matrix[mid][cols - 1 - j];
                matrix[mid][cols - 1 - j] = temp;
            }
        }
    }
}
```

### 旋转示意图

```
原始矩阵:           顺时针90°:        逆时针90°:        180°:
1 2 3              7 4 1            3 6 9            9 8 7
4 5 6    →         8 5 2    或      2 5 8    或      6 5 4
7 8 9              9 6 3            1 4 7            3 2 1
```

### 2. 矩阵翻转

```java
public class MatrixFlip {
    
    /**
     * 水平翻转（左右翻转）
     */
    public static void flipHorizontal(int[][] matrix) {
        int rows = matrix.length;
        int cols = matrix[0].length;
        
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols / 2; j++) {
                int temp = matrix[i][j];
                matrix[i][j] = matrix[i][cols - 1 - j];
                matrix[i][cols - 1 - j] = temp;
            }
        }
    }
    
    /**
     * 垂直翻转（上下翻转）
     */
    public static void flipVertical(int[][] matrix) {
        int rows = matrix.length;
        int cols = matrix[0].length;
        
        for (int i = 0; i < rows / 2; i++) {
            for (int j = 0; j < cols; j++) {
                int temp = matrix[i][j];
                matrix[i][j] = matrix[rows - 1 - i][j];
                matrix[rows - 1 - i][j] = temp;
            }
        }
    }
    
    /**
     * 主对角线翻转（转置）
     */
    public static void flipMainDiagonal(int[][] matrix) {
        int n = matrix.length;
        for (int i = 0; i < n; i++) {
            for (int j = i + 1; j < n; j++) {
                int temp = matrix[i][j];
                matrix[i][j] = matrix[j][i];
                matrix[j][i] = temp;
            }
        }
    }
    
    /**
     * 副对角线翻转
     */
    public static void flipAntiDiagonal(int[][] matrix) {
        int n = matrix.length;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n - i; j++) {
                int temp = matrix[i][j];
                matrix[i][j] = matrix[n - 1 - j][n - 1 - i];
                matrix[n - 1 - j][n - 1 - i] = temp;
            }
        }
    }
}
```

## 矩阵搜索算法

### 1. DFS（深度优先搜索）

```java
/**
 * LeetCode 79: 单词搜索
 * 在矩阵中搜索单词
 */
public class WordSearch {
    
    public boolean exist(char[][] board, String word) {
        int rows = board.length;
        int cols = board[0].length;
        
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                if (dfs(board, word, i, j, 0)) {
                    return true;
                }
            }
        }
        
        return false;
    }
    
    private boolean dfs(char[][] board, String word, int i, int j, int index) {
        // 边界检查
        if (i < 0 || i >= board.length || j < 0 || j >= board[0].length) {
            return false;
        }
        
        // 字符不匹配或已访问
        if (board[i][j] != word.charAt(index)) {
            return false;
        }
        
        // 找到完整单词
        if (index == word.length() - 1) {
            return true;
        }
        
        // 标记为已访问
        char temp = board[i][j];
        board[i][j] = '#';
        
        // 四个方向搜索
        boolean found = dfs(board, word, i + 1, j, index + 1) ||
                       dfs(board, word, i - 1, j, index + 1) ||
                       dfs(board, word, i, j + 1, index + 1) ||
                       dfs(board, word, i, j - 1, index + 1);
        
        // 恢复
        board[i][j] = temp;
        
        return found;
    }
}
```

### 2. BFS（广度优先搜索）

```java
/**
 * LeetCode 542: 01矩阵
 * 找到每个单元格到最近0的距离
 */
public class Matrix01 {
    
    public int[][] updateMatrix(int[][] mat) {
        int rows = mat.length;
        int cols = mat[0].length;
        int[][] result = new int[rows][cols];
        Queue<int[]> queue = new LinkedList<>();
        
        // 初始化：将所有0加入队列
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                if (mat[i][j] == 0) {
                    queue.offer(new int[]{i, j});
                } else {
                    result[i][j] = Integer.MAX_VALUE;
                }
            }
        }
        
        // 四个方向
        int[][] directions = {{-1, 0}, {1, 0}, {0, -1}, {0, 1}};
        
        // BFS
        while (!queue.isEmpty()) {
            int[] cell = queue.poll();
            int i = cell[0], j = cell[1];
            
            for (int[] dir : directions) {
                int ni = i + dir[0];
                int nj = j + dir[1];
                
                if (ni >= 0 && ni < rows && nj >= 0 && nj < cols) {
                    if (result[ni][nj] > result[i][j] + 1) {
                        result[ni][nj] = result[i][j] + 1;
                        queue.offer(new int[]{ni, nj});
                    }
                }
            }
        }
        
        return result;
    }
}
```

### 3. 岛屿问题

```java
/**
 * LeetCode 200: 岛屿数量
 * 计算矩阵中岛屿的数量
 */
public class NumberOfIslands {
    
    public int numIslands(char[][] grid) {
        if (grid == null || grid.length == 0) return 0;
        
        int count = 0;
        int rows = grid.length;
        int cols = grid[0].length;
        
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                if (grid[i][j] == '1') {
                    count++;
                    dfs(grid, i, j);
                }
            }
        }
        
        return count;
    }
    
    private void dfs(char[][] grid, int i, int j) {
        int rows = grid.length;
        int cols = grid[0].length;
        
        if (i < 0 || i >= rows || j < 0 || j >= cols || grid[i][j] != '1') {
            return;
        }
        
        grid[i][j] = '0';  // 标记为已访问
        
        // 四个方向
        dfs(grid, i + 1, j);
        dfs(grid, i - 1, j);
        dfs(grid, i, j + 1);
        dfs(grid, i, j - 1);
    }
}

/**
 * LeetCode 695: 岛屿的最大面积
 */
public class MaxAreaOfIsland {
    
    public int maxAreaOfIsland(int[][] grid) {
        int maxArea = 0;
        int rows = grid.length;
        int cols = grid[0].length;
        
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                if (grid[i][j] == 1) {
                    maxArea = Math.max(maxArea, dfs(grid, i, j));
                }
            }
        }
        
        return maxArea;
    }
    
    private int dfs(int[][] grid, int i, int j) {
        int rows = grid.length;
        int cols = grid[0].length;
        
        if (i < 0 || i >= rows || j < 0 || j >= cols || grid[i][j] != 1) {
            return 0;
        }
        
        grid[i][j] = 0;  // 标记为已访问
        
        int area = 1;
        area += dfs(grid, i + 1, j);
        area += dfs(grid, i - 1, j);
        area += dfs(grid, i, j + 1);
        area += dfs(grid, i, j - 1);
        
        return area;
    }
}
```

## 矩阵动态规划

### 1. 最小路径和

```java
/**
 * LeetCode 64: 最小路径和
 * 找到从左上角到右下角的最小路径和
 */
public class MinimumPathSum {
    
    /**
     * 方法1：原地DP
     * 空间复杂度：O(1)
     */
    public int minPathSum(int[][] grid) {
        int rows = grid.length;
        int cols = grid[0].length;
        
        // 初始化第一行
        for (int j = 1; j < cols; j++) {
            grid[0][j] += grid[0][j-1];
        }
        
        // 初始化第一列
        for (int i = 1; i < rows; i++) {
            grid[i][0] += grid[i-1][0];
        }
        
        // 填充其余格子
        for (int i = 1; i < rows; i++) {
            for (int j = 1; j < cols; j++) {
                grid[i][j] += Math.min(grid[i-1][j], grid[i][j-1]);
            }
        }
        
        return grid[rows-1][cols-1];
    }
    
    /**
     * 方法2：使用额外DP数组
     */
    public int minPathSum2(int[][] grid) {
        int rows = grid.length;
        int cols = grid[0].length;
        int[][] dp = new int[rows][cols];
        
        dp[0][0] = grid[0][0];
        
        // 初始化第一行
        for (int j = 1; j < cols; j++) {
            dp[0][j] = dp[0][j-1] + grid[0][j];
        }
        
        // 初始化第一列
        for (int i = 1; i < rows; i++) {
            dp[i][0] = dp[i-1][0] + grid[i][0];
        }
        
        // DP
        for (int i = 1; i < rows; i++) {
            for (int j = 1; j < cols; j++) {
                dp[i][j] = grid[i][j] + Math.min(dp[i-1][j], dp[i][j-1]);
            }
        }
        
        return dp[rows-1][cols-1];
    }
}
```

### 2. 不同路径

```java
/**
 * LeetCode 62: 不同路径
 * 计算从左上角到右下角的路径数量
 */
public class UniquePaths {
    
    public int uniquePaths(int m, int n) {
        int[][] dp = new int[m][n];
        
        // 第一行和第一列都是1
        for (int i = 0; i < m; i++) dp[i][0] = 1;
        for (int j = 0; j < n; j++) dp[0][j] = 1;
        
        // DP
        for (int i = 1; i < m; i++) {
            for (int j = 1; j < n; j++) {
                dp[i][j] = dp[i-1][j] + dp[i][j-1];
            }
        }
        
        return dp[m-1][n-1];
    }
    
    /**
     * 空间优化版本
     * 空间复杂度：O(n)
     */
    public int uniquePathsOptimized(int m, int n) {
        int[] dp = new int[n];
        Arrays.fill(dp, 1);
        
        for (int i = 1; i < m; i++) {
            for (int j = 1; j < n; j++) {
                dp[j] += dp[j-1];
            }
        }
        
        return dp[n-1];
    }
}
```

### 3. 最大正方形

```java
/**
 * LeetCode 221: 最大正方形
 * 找到由1组成的最大正方形
 */
public class MaximalSquare {
    
    public int maximalSquare(char[][] matrix) {
        if (matrix == null || matrix.length == 0) return 0;
        
        int rows = matrix.length;
        int cols = matrix[0].length;
        int[][] dp = new int[rows + 1][cols + 1];
        int maxSide = 0;
        
        for (int i = 1; i <= rows; i++) {
            for (int j = 1; j <= cols; j++) {
                if (matrix[i-1][j-1] == '1') {
                    dp[i][j] = Math.min(Math.min(dp[i-1][j], dp[i][j-1]), dp[i-1][j-1]) + 1;
                    maxSide = Math.max(maxSide, dp[i][j]);
                }
            }
        }
        
        return maxSide * maxSide;
    }
}
```

## 最佳实践与优化

### 1. 性能优化技巧

```java
public class PerformanceOptimization {
    
    /**
     * 技巧1：缓存行列数
     */
    public void goodPractice1(int[][] matrix) {
        int rows = matrix.length;
        int cols = matrix[0].length;
        
        // ✅ 好：缓存长度
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                // 处理matrix[i][j]
            }
        }
        
        // ❌ 坏：重复计算长度
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[0].length; j++) {
                // 处理matrix[i][j]
            }
        }
    }
    
    /**
     * 技巧2：选择合适的遍历顺序（行优先）
     */
    public void goodPractice2(int[][] matrix) {
        int rows = matrix.length;
        int cols = matrix[0].length;
        
        // ✅ 好：行优先（缓存友好）
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                // 处理matrix[i][j]
            }
        }
        
        // ❌ 坏：列优先（缓存不友好）
        for (int j = 0; j < cols; j++) {
            for (int i = 0; i < rows; i++) {
                // 处理matrix[i][j]
            }
        }
    }
    
    /**
     * 技巧3：原地修改vs创建新矩阵
     */
    // ✅ 空间优化：原地修改
    public void modifyInPlace(int[][] matrix) {
        // 直接修改matrix
    }
    
    // ❌ 额外空间：创建新矩阵
    public int[][] createNew(int[][] matrix) {
        int[][] result = new int[matrix.length][matrix[0].length];
        // 填充result
        return result;
    }
    
    /**
     * 技巧4：避免重复计算
     */
    public int sumWithMemoization(int[][] matrix) {
        int rows = matrix.length;
        int cols = matrix[0].length;
        
        // 使用前缀和避免重复计算
        int[][] prefixSum = new int[rows + 1][cols + 1];
        
        for (int i = 1; i <= rows; i++) {
            for (int j = 1; j <= cols; j++) {
                prefixSum[i][j] = matrix[i-1][j-1]
                                + prefixSum[i-1][j]
                                + prefixSum[i][j-1]
                                - prefixSum[i-1][j-1];
            }
        }
        
        return prefixSum[rows][cols];
    }
}
```

### 2. 边界处理

```java
public class BoundaryHandling {
    
    /**
     * 技巧1：安全访问
     */
    public boolean safeAccess(int[][] matrix, int i, int j) {
        if (matrix == null || matrix.length == 0) return false;
        if (i < 0 || i >= matrix.length) return false;
        if (j < 0 || j >= matrix[0].length) return false;
        return true;
    }
    
    /**
     * 技巧2：四方向遍历
     */
    public void traverseFourDirections(int[][] matrix, int i, int j) {
        int rows = matrix.length;
        int cols = matrix[0].length;
        
        // 方式1：分别检查
        if (i > 0) process(matrix, i-1, j);           // 上
        if (i < rows-1) process(matrix, i+1, j);      // 下
        if (j > 0) process(matrix, i, j-1);           // 左
        if (j < cols-1) process(matrix, i, j+1);      // 右
        
        // 方式2：使用方向数组
        int[][] directions = {{-1,0}, {1,0}, {0,-1}, {0,1}};
        for (int[] dir : directions) {
            int ni = i + dir[0];
            int nj = j + dir[1];
            if (ni >= 0 && ni < rows && nj >= 0 && nj < cols) {
                process(matrix, ni, nj);
            }
        }
    }
    
    /**
     * 技巧3：八方向遍历
     */
    public void traverseEightDirections(int[][] matrix, int i, int j) {
        int[][] directions = {
            {-1,-1}, {-1,0}, {-1,1},  // 上排
            {0,-1},          {0,1},    // 中排
            {1,-1},  {1,0},  {1,1}     // 下排
        };
        
        int rows = matrix.length;
        int cols = matrix[0].length;
        
        for (int[] dir : directions) {
            int ni = i + dir[0];
            int nj = j + dir[1];
            if (ni >= 0 && ni < rows && nj >= 0 && nj < cols) {
                process(matrix, ni, nj);
            }
        }
    }
    
    private void process(int[][] matrix, int i, int j) {
        // 处理逻辑
    }
}
```

### 3. 常见陷阱与注意事项

```java
public class CommonPitfalls {
    
    /**
     * 陷阱1：空矩阵检查
     */
    public void pitfall1(int[][] matrix) {
        // ❌ 坏：没有检查null
        int rows = matrix.length;
        
        // ✅ 好：完整检查
        if (matrix == null || matrix.length == 0 || matrix[0].length == 0) {
            return;
        }
        int safeRows = matrix.length;
    }
    
    /**
     * 陷阱2：锯齿数组
     */
    public void pitfall2(int[][] matrix) {
        // ❌ 坏：假设所有行长度相同
        int cols = matrix[0].length;
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < cols; j++) {  // 可能越界
                // ...
            }
        }
        
        // ✅ 好：每行单独检查
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[i].length; j++) {
                // ...
            }
        }
    }
    
    /**
     * 陷阱3：索引混淆
     */
    public void pitfall3(int[][] matrix) {
        // ❌ 坏：行列颠倒
        int value1 = matrix[2][1];  // 第3行，第2列
        
        // ✅ 好：清晰命名
        int row = 2, col = 1;
        int value2 = matrix[row][col];
    }
    
    /**
     * 陷阱4：浅拷贝
     */
    public void pitfall4(int[][] original) {
        // ❌ 坏：浅拷贝
        int[][] shallow = original;
        shallow[0][0] = 99;  // 会修改original
        
        // ✅ 好：深拷贝
        int[][] deep = new int[original.length][];
        for (int i = 0; i < original.length; i++) {
            deep[i] = original[i].clone();
        }
        deep[0][0] = 99;  // 不会修改original
    }
}
```

## 总结

### 核心要点

1. **矩阵表示** - 使用二维数组 `int[][]`
2. **访问方式** - `matrix[行][列]`
3. **遍历顺序** - 行优先（缓存友好）
4. **空间优化** - 原地修改 vs 创建新矩阵
5. **边界检查** - 防止数组越界

### 时间复杂度总结

| 操作 | 时间复杂度 | 空间复杂度 |
|------|-----------|-----------|
| 创建矩阵 | O(m×n) | O(m×n) |
| 遍历矩阵 | O(m×n) | O(1) |
| 转置 | O(m×n) | O(m×n) |
| 矩阵加法 | O(m×n) | O(m×n) |
| 矩阵乘法 | O(m×n×p) | O(m×p) |
| 螺旋遍历 | O(m×n) | O(1) |
| 旋转90° | O(n²) | O(1) |
| DFS搜索 | O(m×n) | O(m×n) |
| BFS搜索 | O(m×n) | O(m×n) |

### 算法题常见模式

| 模式 | 典型题目 | 关键技巧 |
|------|---------|---------|
| **遍历** | 螺旋矩阵 | 四边界控制 |
| **搜索** | 单词搜索 | DFS + 回溯 |
| **DP** | 最小路径和 | 状态转移 |
| **旋转** | 旋转图像 | 转置+翻转 |
| **岛屿** | 岛屿数量 | DFS/BFS |
| **区域和** | 子矩阵和 | 前缀和 |

### 学习建议

1. **掌握基础** - 创建、遍历、打印
2. **理解操作** - 转置、旋转、翻转
3. **熟悉算法** - DFS、BFS、DP
4. **练习题目** - LeetCode矩阵专题
5. **优化技巧** - 空间、时间优化

通过系统地学习和练习矩阵操作，您将能够轻松应对各类算法题中的矩阵问题！


