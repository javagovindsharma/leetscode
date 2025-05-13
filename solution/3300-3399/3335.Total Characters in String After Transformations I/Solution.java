import java.util.*;

public class Solution {
    private static final long MOD = (long) 1e9 + 7;

    private long[][] mul(long[][] A, long[][] B) {
        int rows = A.length;
        int cols = B[0].length;
        long[][] res = new long[rows][cols];

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                for (int k = 0; k < B.length; k++) {
                    res[i][j] = (res[i][j] + A[i][k] * B[k][j] % MOD) % MOD;
                }
            }
        }
        return res;
    }

    private long[][] pow(long[][] A, long k) {
        int size = A.length;
        long[][] res = new long[size][size];
        for (int i = 0; i < size; i++) {
            res[i][i] = 1;
        }

        while (k > 0) {
            if ((k & 1) == 1) {
                res = mul(res, A);
            }
            A = mul(A, A);
            k >>= 1;
        }
        return res;
    }

    public int lengthAfterTransformations(String s, int t) {
        long[][] mat = new long[26][26];
        long[][] cnt = new long[1][26];

        for (int i = 0; i < 26; i++) {
            mat[i][(i + 1) % 26] = 1;
        }
        mat[25][1] = 1;

        for (char ch : s.toCharArray()) {
            cnt[0][ch - 'a']++;
        }

        long[][] po = pow(mat, t);
        long[][] mat2 = mul(cnt, po);

        long res = 0;
        for (int i = 0; i < 26; i++) {
            res = (res + mat2[0][i]) % MOD;
        }
        return (int) res;
    }

    public static void main(String[] args) {
        Solution solution = new Solution();
        String s = "abc";
        int t = 100;
        System.out.println(solution.lengthAfterTransformations(s, t)); // Example usage
    }
}
