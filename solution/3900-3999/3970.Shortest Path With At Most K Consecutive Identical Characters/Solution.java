import java.util.*;

class Solution {
    public int shortestPath(
        int n, int[][] edges,
        String labels, int k) {

        List<int[]>[] g = new ArrayList[n];
        for (int i = 0; i < n; i++)
            g[i] = new ArrayList<>();
        for (int[] e : edges)
            g[e[0]].add(new int[]{e[1], e[2]});

        int[][] d = new int[n][k + 1];
        for (int[] a : d)
            Arrays.fill(a, Integer.MAX_VALUE);

        PriorityQueue<int[]> q = new PriorityQueue<>(
            (a, b) -> Integer.compare(a[0], b[0])
        );

        q.offer(new int[]{0, 0, 1});
        d[0][1] = 0;

        while (!q.isEmpty()) {
            int[] a = q.poll();
            int cost = a[0], u = a[1], c = a[2];

            if (cost > d[u][c]) continue;
            if (u == n - 1) return cost;

            for (int[] e : g[u]) {
                int v = e[0], w = e[1];
                int nc = labels.charAt(u) ==
                    labels.charAt(v) ? c + 1 : 1;

                if (nc > k) continue;
                if (cost + w < d[v][nc]) {
                    d[v][nc] = cost + w;
                    q.offer(new int[]{d[v][nc], v, nc});
                }
            }
        }
        return -1;
    }
}
