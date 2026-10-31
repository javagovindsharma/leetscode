class Solution {
    public int uniquePaths(int[][] grid) {
        int MOD = 1_000_000_007;

        int m = grid.length;
        int n = grid[0].length;

        int[] up = new int[n];

        // Initial path at (0,0)
        up[0] = 1;

        int ans = 0;

        for (int i = 0; i < m; i++) {
            int left = 0;

            for (int j = 0; j < n; j++) {

                int fromLeft = left;
                int fromUp = up[j];

                if (grid[i][j] == 1) {
                    // Mirror: swap incoming directions
                    left = fromUp;
                    up[j] = fromLeft;
                } else {
                    // Empty cell: paths can move right and down
                    int ways = (fromLeft + fromUp) % MOD;

                    left = ways;
                    up[j] = ways;

                    if (i == m - 1 && j == n - 1) {
                        ans = ways;
                    }
                }
            }
        }

        return ans;
    }
}
