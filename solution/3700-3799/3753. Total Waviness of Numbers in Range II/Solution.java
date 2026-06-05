class Solution {
    private char[] digits;
    private Map<Long, long[]> memo;

    public long totalWaviness(long num1, long num2) {
        return calc(num2) - calc(num1 - 1);
    }

    private long calc(long n) {
        if (n <= 0) return 0;

        digits = Long.toString(n).toCharArray();
        memo = new HashMap<>();

        return dfs(0, 10, 10, 0, true)[1];
    }

    // returns {countNumbers, totalWaviness}
    private long[] dfs(int pos, int prev2, int prev1,
                       int len, boolean tight) {

        if (pos == digits.length) {
            return new long[]{1, 0};
        }

        long key = 0;

        if (!tight) {
            key = encode(pos, prev2, prev1, len);
            long[] cached = memo.get(key);
            if (cached != null) return cached;
        }

        int limit = tight ? digits[pos] - '0' : 9;

        long totalCount = 0;
        long totalWave = 0;

        for (int d = 0; d <= limit; d++) {

            boolean nextTight = tight && (d == limit);

            if (len == 0 && d == 0) {
                long[] child = dfs(pos + 1, 10, 10, 0, nextTight);

                totalCount += child[0];
                totalWave += child[1];
                continue;
            }

            int add = 0;

            // Once we already have at least 2 previous digits,
            // the middle digit (prev1) can now be evaluated.
            if (len >= 2) {
                if ((prev2 < prev1 && prev1 > d) ||
                    (prev2 > prev1 && prev1 < d)) {
                    add = 1;
                }
            }

            int nPrev2, nPrev1;

            if (len == 0) {
                nPrev2 = 10;
                nPrev1 = d;
            } else if (len == 1) {
                nPrev2 = prev1;
                nPrev1 = d;
            } else {
                nPrev2 = prev1;
                nPrev1 = d;
            }

            long[] child = dfs(
                pos + 1,
                nPrev2,
                nPrev1,
                Math.min(3, len + 1),
                nextTight
            );

            totalCount += child[0];
            totalWave += child[1] + child[0] * add;
        }

        long[] res = new long[]{totalCount, totalWave};

        if (!tight) {
            memo.put(key, res);
        }

        return res;
    }

    private long encode(int pos, int prev2, int prev1, int len) {
        long key = pos;
        key = key * 11 + prev2;
        key = key * 11 + prev1;
        key = key * 4 + len;
        return key;
    }
}
