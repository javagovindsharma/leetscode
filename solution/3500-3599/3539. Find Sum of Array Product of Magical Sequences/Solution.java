class Solution {
    public int magicalSum(int m, int k, int[] nums) {
        int MOD = (int)1e9 + 7;
        int n = nums.length;

        long[] fact = new long[m + 1];
        fact[0] = 1;
        for (int i = 1; i <= m; i++) {
            fact[i] = (fact[i - 1] * i) % MOD;
        }
        
        long[] inv_fact = new long[m + 1];
        inv_fact[m] = power(fact[m], MOD - 2, MOD);
        for (int i = m - 1; i >= 0; i--) {
            inv_fact[i] = (inv_fact[i + 1] * (i + 1)) % MOD;
        }

        long[][] powers = new long[n][m + 1];
        for (int i = 0; i < n; i++) {
            powers[i][0] = 1;
            for (int j = 1; j <= m; j++) {
                powers[i][j] = (powers[i][j - 1] * nums[i]) % MOD;
            }
        }
        
        // dp[j][p][q]: j elements used, carry p, q set bits
        int p_bound = m / 2 + 1;
        long[][][] dp = new long[m + 1][p_bound][k + 1];
        dp[0][0][0] = 1;
        
        for (int i = 0; i < n; i++) {
            long[][][] new_dp = new long[m + 1][p_bound][k + 1];
            for (int j = 0; j <= m; j++) {
                // Optimization: p <= j/2
                for (int p = 0; p <= j / 2; p++) {
                    for (int q = 0; q <= k; q++) {
                        if (dp[j][p][q] == 0) {
                            continue;
                        }
                        
                        long val = dp[j][p][q];
                        
                        for (int c_i = 0; c_i <= m - j; c_i++) {
                            int new_j = j + c_i;
                            
                            int total_val_at_i = c_i + p;
                            int bit_i = total_val_at_i % 2;
                            int new_p = total_val_at_i / 2;
                            
                            int new_q = q + bit_i;
                            if (new_q > k) {
                                continue;
                            }
                            
                            long term_for_ci = (powers[i][c_i] * inv_fact[c_i]) % MOD;
                            long contribution = (val * term_for_ci) % MOD;
                            
                            new_dp[new_j][new_p][new_q] = (new_dp[new_j][new_p][new_q] + contribution) % MOD;
                        }
                    }
                }
            }
            dp = new_dp;
        }
        
        long ans = 0;
        // Optimization: final carry p <= m/2
        for (int p = 0; p <= m / 2; p++) {
            int bits_in_carry = Integer.bitCount(p);
            int q_needed = k - bits_in_carry;
            
            if (0 <= q_needed && q_needed <= k) {
                ans = (ans + dp[m][p][q_needed]) % MOD;
            }
        }
        
        return (int)((ans * fact[m]) % MOD);
    }
    
    private long power(long base, long exp, long mod) {
        long res = 1;
        base %= mod;
        while (exp > 0) {
            if (exp % 2 == 1) res = (res * base) % mod;
            exp >>= 1;
            base = (base * base) % mod;
        }
        return res;
    }
}
