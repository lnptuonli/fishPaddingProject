import java.util.*;

/**
 * LeetCode 3607. 电网维护 - Power Grid Maintenance
 * 前置问题：
 * LeetCode 547. 省份数量（并查集入门）
 * LeetCode 200. 岛屿数量（DFS/BFS/并查集）
 * LeetCode 684. 冗余连接（并查集应用）
 * <p>
 * 题目描述：
 * 给你一个整数 c，表示 c 个电站，每个电站有一个唯一标识符 id，从 1 到 c 编号。
 * <p>
 * 这些电站通过 n 条双向电缆互相连接，表示为一个二维数组 connections，
 * 其中每个元素 connections[i] = [ui, vi] 表示电站 ui 和电站 vi 之间的连接。
 * 直接或间接连接的电站组成了一个电网。
 * <p>
 * 最初，所有电站均处于在线（正常运行）状态。
 * <p>
 * 另给你一个二维数组 queries，其中每个查询属于以下两种类型之一：
 * <p>
 * [1, x]：请求对电站 x 进行维护检查。
 * - 如果电站 x 在线，则它自行解决检查。
 * - 如果电站 x 已离线，则检查由与 x 同一电网中编号最小的在线电站解决。
 * - 如果该电网中不存在任何在线电站，则返回 -1。
 * <p>
 * [2, x]：电站 x 离线（即变为非运行状态）。
 * <p>
 * 返回一个整数数组，表示按照查询中出现的顺序，所有类型为 [1, x] 的查询结果。
 * <p>
 * 注意：
 * - 电网的结构是固定的
 * - 离线（非运行）的节点仍然属于其所在的电网
 * - 离线操作不会改变电网的连接性
 * <p>
 * 示例 1：
 * 输入：c = 5, connections = [[1,2],[2,3],[3,4],[4,5]],
 * queries = [[1,3],[2,3],[1,3],[1,5]]
 * 输出：[3, 1, 5]
 * 解释：
 * - 查询 [1,3]：电站 3 在线，返回 3
 * - 查询 [2,3]：电站 3 离线（不返回结果）
 * - 查询 [1,3]：电站 3 离线，同一电网中最小的在线电站是 1，返回 1
 * - 查询 [1,5]：电站 5 在线，返回 5
 * <p>
 * 示例 2：
 * 输入：c = 3, connections = [[1,2]], queries = [[2,1],[2,2],[1,1],[1,2]]
 * 输出：[-1, -1]
 * 解释：
 * - 查询 [2,1]：电站 1 离线
 * - 查询 [2,2]：电站 2 离线
 * - 查询 [1,1]：电站 1 离线，同一电网中只有电站 2，但也离线了，返回 -1
 * - 查询 [1,2]：电站 2 离线，同一电网中只有电站 1，但也离线了，返回 -1
 * <p>
 * 提示：
 * 1 <= c <= 10^5
 * 0 <= connections.length <= min(c * (c - 1) / 2, 10^5)
 * connections[i].length == 2
 * 1 <= ui, vi <= c
 * ui != vi
 * 不存在重复的连接
 * 1 <= queries.length <= 10^5
 * queries[i].length == 2
 * 1 <= queries[i][0] <= 2
 * 1 <= queries[i][1] <= c
 * <p>
 * 核心理解：
 * <p>
 * 1. 这是一道并查集（Union-Find）问题：
 * - 需要快速判断两个电站是否在同一电网中
 * - 需要快速找到某个电网的代表节点
 * - 需要维护每个电网中的最小在线电站
 * <p>
 * 2. 关键数据结构：
 * - 并查集：用于维护电网的连通性
 * - 在线状态数组：记录每个电站是否在线
 * - 最小在线电站映射：记录每个电网中编号最小的在线电站
 * <p>
 * 3. 操作分析：
 * - 查询 [1, x]：
 * * 如果 x 在线，返回 x
 * * 如果 x 离线，找到 x 所在电网的根节点，返回该电网的最小在线电站
 * - 查询 [2, x]：
 * * 标记 x 为离线
 * * 更新 x 所在电网的最小在线电站
 * <p>
 * 4. 难点：
 * - 并查集的实现（路径压缩 + 按秩合并）
 * - 动态维护每个电网的最小在线电站
 * - 处理电站离线后的最小值更新
 * <p>
 * 解题思路：
 * <p>
 * 思路1：并查集 + 哈希表（推荐⭐⭐⭐）
 * <p>
 * 步骤1：初始化
 * - 创建并查集，初始时每个电站是独立的
 * - 根据 connections 建立电网连接
 * - 创建在线状态数组，初始全部在线
 * - 创建哈希表，维护每个电网的最小在线电站
 * <p>
 * 步骤2：处理查询
 * - 查询 [1, x]：
 * * 如果 x 在线，返回 x
 * * 否则，找到 x 的根节点，查询该电网的最小在线电站
 * - 查询 [2, x]：
 * * 标记 x 为离线
 * * 找到 x 的根节点
 * * 如果 x 是该电网的最小在线电站，需要重新计算
 * <p>
 * 步骤3：维护最小在线电站
 * - 方法1：每次离线时，遍历该电网所有节点，找最小在线电站
 * - 方法2：使用优先队列（TreeSet）维护每个电网的在线电站
 * <p>
 * 时间复杂度：
 * - 初始化：O(n * α(c))，α是反阿克曼函数，几乎为常数
 * - 每次查询：O(α(c))
 * - 总体：O((n + q) * α(c))，q 是查询数量
 * <p>
 * 空间复杂度：O(c)
 * <p>
 * 并查集基础知识：
 * <p>
 * 1. 核心操作：
 * - find(x)：找到 x 的根节点（代表元素）
 * - union(x, y)：合并 x 和 y 所在的集合
 * <p>
 * 2. 优化技巧：
 * - 路径压缩：在 find 时，将路径上所有节点直接连到根节点
 * - 按秩合并：合并时，将深度小的树连到深度大的树上
 * <p>
 * 3. 并查集模板：
 * class UnionFind {
 * int[] parent;  // 父节点数组
 * int[] rank;    // 秩（深度）数组
 * <p>
 * public UnionFind(int n) {
 * parent = new int[n];
 * rank = new int[n];
 * for (int i = 0; i < n; i++) {
 * parent[i] = i;  // 初始时每个节点的父节点是自己
 * rank[i] = 1;
 * }
 * }
 * <p>
 * // 查找根节点（带路径压缩）
 * public int find(int x) {
 * if (parent[x] != x) {
 * parent[x] = find(parent[x]);  // 路径压缩
 * }
 * return parent[x];
 * }
 * <p>
 * // 合并两个集合
 * public void union(int x, int y) {
 * int rootX = find(x);
 * int rootY = find(y);
 * if (rootX != rootY) {
 * // 按秩合并
 * if (rank[rootX] < rank[rootY]) {
 * parent[rootX] = rootY;
 * } else if (rank[rootX] > rank[rootY]) {
 * parent[rootY] = rootX;
 * } else {
 * parent[rootY] = rootX;
 * rank[rootX]++;
 * }
 * }
 * }
 * }
 * <p>
 * 易错点：
 * 1. 电站编号从 1 开始，数组下标从 0 开始，需要转换
 * 2. 离线操作不改变电网结构，只改变在线状态
 * 3. 需要动态维护每个电网的最小在线电站
 * 4. 查询 [2, x] 不返回结果，只有 [1, x] 才返回
 */
public class PowerGrid {

    /**
     * 主方法：处理电网维护查询
     *
     * @param c           电站数量
     * @param connections 电缆连接
     * @param queries     查询数组
     * @return 所有类型1查询的结果
     */
    public List<Integer> processQueries(int c, int[][] connections, int[][] queries) {
        // ============================================
        // TODO: 在这里实现你的解法
        // ============================================

        // 提示：并查集 + 哈希表
        // 
        // 步骤1：初始化并查集
        // UnionFind uf = new UnionFind(c + 1);  // 电站编号从1开始，所以需要c+1
        // 
        // 步骤2：根据 connections 建立电网
        // for (int[] conn : connections) {
        //     uf.union(conn[0], conn[1]);
        // }
        // 
        // 步骤3：初始化在线状态和最小在线电站
        // boolean[] online = new boolean[c + 1];
        // Arrays.fill(online, true);  // 初始全部在线
        // online[0] = false;  // 0号不存在
        // 
        // // 用TreeSet维护每个电网的在线电站（自动排序）
        // Map<Integer, TreeSet<Integer>> gridMinOnline = new HashMap<>();
        // for (int i = 1; i <= c; i++) {
        //     int root = uf.find(i);
        //     gridMinOnline.putIfAbsent(root, new TreeSet<>());
        //     gridMinOnline.get(root).add(i);
        // }
        // 
        // 步骤4：处理查询
        // List<Integer> result = new ArrayList<>();
        // for (int[] query : queries) {
        //     int type = query[0];
        //     int x = query[1];
        //     
        //     if (type == 1) {
        //         // 查询类型1：维护检查
        //         if (online[x]) {
        //             // 电站x在线，自行解决
        //             result.add(x);
        //         } else {
        //             // 电站x离线，找同一电网中最小的在线电站
        //             int root = uf.find(x);
        //             TreeSet<Integer> onlineStations = gridMinOnline.get(root);
        //             if (onlineStations.isEmpty()) {
        //                 result.add(-1);  // 该电网没有在线电站
        //             } else {
        //                 result.add(onlineStations.first());  // 返回最小的在线电站
        //             }
        //         }
        //     } else {
        //         // 查询类型2：电站x离线
        //         online[x] = false;
        //         int root = uf.find(x);
        //         gridMinOnline.get(root).remove(x);  // 从在线电站集合中移除
        //     }
        // }
        // 
        // return result;

        // 关键理解：
        // 为什么用并查集？
        // - 快速判断两个电站是否在同一电网：O(α(n))
        // - 快速找到电网的代表节点：O(α(n))
        // 
        // 为什么用TreeSet？
        // - 自动排序，first()就是最小值：O(1)
        // - 添加/删除：O(log n)
        // 
        // 为什么用HashMap<Integer, TreeSet>？
        // - key是电网的根节点
        // - value是该电网中所有在线电站的集合
        // - 这样可以快速找到某个电网的最小在线电站

        // 在这里编写你的实现代码

        //首先实现并查集：
        class Unifind {
            private int[] parent;//父节点
            private int[] rank;//树深
            private int count;//秩

            //构造函数：
            public Unifind(int n) {
                //初始化父节点数组和树深
                parent = new int[n];
                rank = new int[n];
                count = n;
                //将每个元素初始化成一个集合,初始秩为1
                for (int i = 0; i < n; i++) {
                    parent[i] = i;
                    rank[i] = 1;
                }
            }

            //查找根节点函数：
            public int find(int x) {
                //递归查找，找到根节点为止
                if (parent[x] != x) {
                    parent[x] = find(parent[x]);
                }
                return parent[x];
            }

            //集合合并：
            public void union(int x, int y) {
                int rootX = find(x);
                int rootY = find(y);

                if (rootX == rootY)
                    return;
                //按秩合并：
                if (rank[rootX] < rank[rootY]) {
                    parent[rootX] = rootY;
                } else if (rank[rootY] < rank[rootX]) {
                    parent[rootY] = rootX;
                } else {
                    //两者相等，Y合到X，并且X的秩+1
                    parent[rootY] = rootX;
                    rank[rootX]++;
                }
            }

            //测试两颗树的连通性
            public boolean isConnected(int x, int y) {
                return find(x) == find(y);
            }

            public void getSubGrid(ArrayList<Integer> subGrid, int root) {
                for (int i = 0; i < parent.length; i++) {
                    if (parent[i] == root)
                        subGrid.add(i);
                }
            }

        }
        //开始主逻辑：
        // 并查集
        Unifind Grid = new Unifind(c);

        // 连接电网节点对
        for (int[] connection : connections) {
            Grid.union(connection[0] - 1, connection[1] - 1);
        }

        // 在线状态（1-based）
        boolean[] online = new boolean[c + 1];
        Arrays.fill(online, true);
        online[0] = false;  // 0不存在

        List<Integer> result = new ArrayList<>();

        // 处理查询
        for (int[] query : queries) {
            int type = query[0];
            int station = query[1];

            if (type == 1) {
                // 查询类型1：维护检查
                if (online[station]) {
                    // 电站在线，自己处理
                    result.add(station);
                } else {
                    // 电站离线，找同一电网中最小的在线电站
                    int root = Grid.find(station - 1);
                    int minOnline = -1;

                    // 遍历所有电站，找最小的在线电站
                    for (int i = 1; i <= c; i++) {
                        if (online[i] && Grid.find(i - 1) == root) {
                            minOnline = i;
                            break;  // 第一个就是最小的
                        }
                    }

                    result.add(minOnline);
                }
            } else {
                // 查询类型2：电站离线
                online[station] = false;
            }
        }



        int[] listresult=new int[result.size()];
        for (int i = 0; i < result.size(); i++)
            listresult[i]=result.get(i);
        return result;  // 记得返回结果

        // ============================================
        // 实现结束,以下是AI生成的解法：
        // ============================================
        /*Unifind Grid = new Unifind(c);

        // 连接电网
        for (int[] connection : connections) {
            Grid.union(connection[0] - 1, connection[1] - 1);
        }

        // 用TreeSet维护每个电网的在线电站（自动排序）
        Map<Integer, TreeSet<Integer>> gridOnline = new HashMap<>();
        for (int i = 1; i <= c; i++) {
            int root = Grid.find(i - 1);
            gridOnline.putIfAbsent(root, new TreeSet<>());
            gridOnline.get(root).add(i);
        }

        List<Integer> result = new ArrayList<>();

        // 处理查询
        for (int[] query : queries) {
            int type = query[0];
            int station = query[1];

            if (type == 1) {
                int root = Grid.find(station - 1);
                TreeSet<Integer> onlineStations = gridOnline.get(root);

                if (onlineStations.contains(station)) {
                    // 电站在线
                    result.add(station);
                } else {
                    // 电站离线，找最小的在线电站
                    if (onlineStations.isEmpty()) {
                        result.add(-1);
                    } else {
                        result.add(onlineStations.first());
                    }
                }
            } else {
                // 电站离线
                int root = Grid.find(station - 1);
                gridOnline.get(root).remove(station);
            }
        }

        int[] listresult=new int[result.size()];
        for (int i = 0; i < result.size(); i++)
            listresult[i]=result.get(i);
        return listresult;  // 记得返回结果*/

    }


    /**
     * 测试方法
     */
    public static void main(String[] args) {
        PowerGrid solution = new PowerGrid();

        System.out.println("=== LeetCode 3607: 电网维护 ===");
        System.out.println("并查集 + 动态查询问题\n");

        // 测试用例1：官方示例1
        int c1 = 5;
        int[][] connections1 = {{1, 2}, {2, 3}, {3, 4}, {4, 5}};
        int[][] queries1 = {{1, 3}, {2, 3}, {1, 3}, {1, 5}};
        List<Integer> result1 = solution.processQueries(c1, connections1, queries1);
        System.out.println("测试用例1:");
        System.out.println("输入: c = 5");
        System.out.println("connections = [[1,2],[2,3],[3,4],[4,5]]");
        System.out.println("queries = [[1,3],[2,3],[1,3],[1,5]]");
        System.out.println("输出: " + result1);
        System.out.println("预期: [3, 1, 5]");
        System.out.println("通过: " + result1.equals(Arrays.asList(3, 1, 5)));
        System.out.println();

        // 测试用例2：官方示例2
        int c2 = 3;
        int[][] connections2 = {{1, 2}};
        int[][] queries2 = {{2, 1}, {2, 2}, {1, 1}, {1, 2}};
        List<Integer> result2 = solution.processQueries(c2, connections2, queries2);
        System.out.println("测试用例2:");
        System.out.println("输入: c = 3");
        System.out.println("connections = [[1,2]]");
        System.out.println("queries = [[2,1],[2,2],[1,1],[1,2]]");
        System.out.println("输出: " + result2);
        System.out.println("预期: [-1, -1]");
        System.out.println("通过: " + result2.equals(Arrays.asList(-1, -1)));
        System.out.println();

        // 测试用例3：单个电站
        int c3 = 1;
        int[][] connections3 = {};
        int[][] queries3 = {{1, 1}, {2, 1}, {1, 1}};
        List<Integer> result3 = solution.processQueries(c3, connections3, queries3);
        System.out.println("测试用例3:");
        System.out.println("输入: c = 1, connections = [], queries = [[1,1],[2,1],[1,1]]");
        System.out.println("输出: " + result3);
        System.out.println("预期: [1, -1]");
        System.out.println("通过: " + result3.equals(Arrays.asList(1, -1)));
        System.out.println();

        // 测试用例4：多个独立电网
        int c4 = 4;
        int[][] connections4 = {{1, 2}, {3, 4}};
        int[][] queries4 = {{1, 1}, {1, 3}, {2, 1}, {1, 2}, {2, 2}, {1, 1}};
        List<Integer> result4 = solution.processQueries(c4, connections4, queries4);
        System.out.println("测试用例4:");
        System.out.println("输入: c = 4, connections = [[1,2],[3,4]]");
        System.out.println("queries = [[1,1],[1,3],[2,1],[1,2],[2,2],[1,1]]");
        System.out.println("输出: " + result4);
        System.out.println("预期: [1, 3, 2, -1] (两个独立电网)");
        System.out.println();

        // 测试用例5：全部离线
        int c5 = 3;
        int[][] connections5 = {{1, 2}, {2, 3}};
        int[][] queries5 = {{2, 1}, {2, 2}, {2, 3}, {1, 1}, {1, 2}, {1, 3}};
        List<Integer> result5 = solution.processQueries(c5, connections5, queries5);
        System.out.println("测试用例5:");
        System.out.println("输入: c = 3, connections = [[1,2],[2,3]]");
        System.out.println("queries = [[2,1],[2,2],[2,3],[1,1],[1,2],[1,3]]");
        System.out.println("输出: " + result5);
        System.out.println("预期: [-1, -1, -1] (全部离线)");
        System.out.println();

        System.out.println("=== 核心要点 ===");
        System.out.println("1. 并查集：维护电网的连通性");
        System.out.println("2. TreeSet：维护每个电网的最小在线电站");
        System.out.println("3. HashMap：映射根节点到在线电站集合");
        System.out.println("4. 时间复杂度：O((n + q) * α(c))");
        System.out.println("5. 这是一道中等偏难的题目，涉及并查集和动态查询");
        System.out.println("\n提示：如果觉得难，可以先学习并查集的基础知识！");
    }
}





