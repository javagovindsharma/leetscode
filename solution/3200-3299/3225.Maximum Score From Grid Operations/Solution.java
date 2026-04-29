class Solution {
    public long maximumScore(int[][] grid) {
        int n = grid.length;
        if (n == 1) return 0;

        // prefix[col][i] = sum of grid[0..i-1][col]
        long[][] prefix = new long[n][n + 1];
        for (int c = 0; c < n; c++) {
            for (int r = 0; r < n; r++) {
                prefix[c][r + 1] = prefix[c][r] + grid[r][c];
            }
        }

        long[][] dp = new long[n + 1][n + 1];

        // initialize states for first 2 columns
        for (int h0 = 0; h0 <= n; h0++) {
            for (int h1 = 0; h1 <= n; h1++) {
                dp[h0][h1] = score(prefix, 0, 0, h0, h1);
            }
        }

        for (int col = 1; col <= n - 2; col++) {
            long[][] next = new long[n + 1][n + 1];
            for (long[] row : next) java.util.Arrays.fill(row, Long.MIN_VALUE);

            for (int cur = 0; cur <= n; cur++) {

                // best[m] = max dp[left][cur] for left <= m
                long[] pref = new long[n + 1];
                java.util.Arrays.fill(pref, Long.MIN_VALUE);

                for (int left = 0; left <= n; left++) {
                    pref[left] = dp[left][cur];
                    if (left > 0) pref[left] = Math.max(pref[left], pref[left - 1]);
                }

                // best[m] = max dp[left][cur] where left >= m
                long[] suff = new long[n + 2];
                java.util.Arrays.fill(suff, Long.MIN_VALUE);

                for (int left = n; left >= 0; left--) {
                    suff[left] = Math.max(suff[left + 1], dp[left][cur]);
                }

                for (int right = 0; right <= n; right++) {

                    long best = Long.MIN_VALUE;

                    // case 1: left <= right  => max(left,right)=right
                    if (pref[right] != Long.MIN_VALUE) {
                        best = Math.max(
                            best,
                            pref[right] + score(prefix, col, right, cur)
                        );
                    }

                    // case 2: left > right => max(left,right)=left
                    for (int m = right + 1; m <= n; m++) {
                        if (suff[m] == Long.MIN_VALUE) continue;
                        best = Math.max(
                            best,
                            suff[m] + score(prefix, col, m, cur)
                        );
                    }

                    next[cur][right] = best;
                }
            }
            dp = next;
        }

        long ans = 0;

        for (int left = 0; left <= n; left++) {
            for (int cur = 0; cur <= n; cur++) {
                ans = Math.max(ans, dp[left][cur] + score(prefix, n - 1, left, cur, 0));
            }
        }

        return ans;
    }

    // contribution using left/current/right heights
    private long score(long[][] prefix, int col, int left, int cur, int right) {
        return score(prefix, col, Math.max(left, right), cur);
    }

    // contribution when maxNeighbor already known
    private long score(long[][] prefix, int col, int mx, int cur) {
        if (mx <= cur) return 0;
        return prefix[col][mx] - prefix[col][cur];
    }
}
