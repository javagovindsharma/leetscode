class Solution {
    private final int MOD = (int) 1e9 + 7;

    public int zigZagArrays(int n, int l, int r) {
        int m = r - l + 1;

        // mat_mul_m function
        java.util.function.BiFunction<int[][], int[][], int[][]> mat_mul_m = (X, Y) -> {
            int[][] res = new int[m][m];
            for (int i = 0; i < m; i++) {
                for (int j = 0; j < m; j++) {
                    for (int k = 0; k < m; k++) {
                        res[i][j] = (int) (((long) res[i][j] + (long) X[i][k] * Y[k][j]) % MOD);
                    }
                }
            }
            return res;
        };

        // mat_vec_mul_m function
        java.util.function.BiFunction<int[][], int[], int[]> mat_vec_mul_m = (M, v) -> {
            int[] res = new int[m];
            for (int i = 0; i < m; i++) {
                for (int j = 0; j < m; j++) {
                    res[i] = (int) (((long) res[i] + (long) M[i][j] * v[j]) % MOD);
                }
            }
            return res;
        };

        // mat_mul_special function
        java.util.function.BiFunction<java.util.List<Object>, java.util.List<Object>, java.util.List<Object>> mat_mul_special = (mat1, mat2) -> {
            int type1 = (int) mat1.get(0);
            int[][] M11 = (int[][]) mat1.get(1);
            int[][] M12 = (int[][]) mat1.get(2);

            int type2 = (int) mat2.get(0);
            int[][] M21 = (int[][]) mat2.get(1);
            int[][] M22 = (int[][]) mat2.get(2);

            if (type1 == 1 && type2 == 1) {
                // [[0, M11], [M12, 0]] * [[0, M21], [M22, 0]] -> [[M11*M22, 0], [0, M12*M21]]
                int[][] new_M1 = mat_mul_m.apply(M11, M22);
                int[][] new_M2 = mat_mul_m.apply(M12, M21);
                return java.util.Arrays.asList(2, new_M1, new_M2);
            } else if (type1 == 1 && type2 == 2) {
                // [[0, M11], [M12, 0]] * [[M21, 0], [0, M22]] -> [[0, M11*M22], [M12*M21, 0]]
                int[][] new_M1 = mat_mul_m.apply(M11, M22);
                int[][] new_M2 = mat_mul_m.apply(M12, M21);
                return java.util.Arrays.asList(1, new_M1, new_M2);
            } else if (type1 == 2 && type2 == 1) {
                // [[M11, 0], [0, M12]] * [[0, M21], [M22, 0]] -> [[0, M11*M21], [M12*M22, 0]]
                int[][] new_M1 = mat_mul_m.apply(M11, M21);
                int[][] new_M2 = mat_mul_m.apply(M12, M22);
                return java.util.Arrays.asList(1, new_M1, new_M2);
            } else { // type1 == 2 && type2 == 2
                // [[M11, 0], [0, M12]] * [[M21, 0], [0, M22]] -> [[M11*M21, 0], [0, M12*M22]]
                int[][] new_M1 = mat_mul_m.apply(M11, M21);
                int[][] new_M2 = mat_mul_m.apply(M12, M22);
                return java.util.Arrays.asList(2, new_M1, new_M2);
            }
        };

        int[][] B = new int[m][m];
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < m; j++) {
                B[i][j] = (j < i) ? 1 : 0;
            }
        }

        int[][] C = new int[m][m];
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < m; j++) {
                C[i][j] = (j > i) ? 1 : 0;
            }
        }

        int power = n - 2;

        java.util.List<Object> base_mat = java.util.Arrays.asList(1, B, C);

        int[][] I_m = new int[m][m];
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < m; j++) {
                I_m[i][j] = (i == j) ? 1 : 0;
            }
        }

        java.util.List<Object> res_mat = java.util.Arrays.asList(2, I_m, I_m);

        while (power > 0) {
            if (power % 2 == 1) {
                res_mat = mat_mul_special.apply(res_mat, base_mat);
            }
            base_mat = mat_mul_special.apply(base_mat, base_mat);
            power /= 2;
        }

        int[] V2_up = new int[m];
        for (int k = 0; k < m; k++) {
            V2_up[k] = k;
        }

        int[] V2_down = new int[m];
        for (int k = 0; k < m; k++) {
            V2_down[k] = m - 1 - k;
        }

        int res_type = (int) res_mat.get(0);
        int[][] M1 = (int[][]) res_mat.get(1);
        int[][] M2 = (int[][]) res_mat.get(2);

        int[] Vn_up;
        int[] Vn_down;
        if (res_type == 1) {
            Vn_up = mat_vec_mul_m.apply(M1, V2_down);
            Vn_down = mat_vec_mul_m.apply(M2, V2_up);
        } else { // res_type == 2
            Vn_up = mat_vec_mul_m.apply(M1, V2_up);
            Vn_down = mat_vec_mul_m.apply(M2, V2_down);
        }

        long total_sum_long = 0;
        for(int val : Vn_up) {
            total_sum_long += val;
        }
        for(int val : Vn_down) {
            total_sum_long += val;
        }

        return (int) (total_sum_long % MOD);
    }
}
