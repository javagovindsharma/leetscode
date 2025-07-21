class Solution {
   static final int MAX = 100001;
    static int[] mobius = new int[MAX];

    static void computeMobius() {
        Arrays.fill(mobius, 1);
        boolean[] isPrime = new boolean[MAX];
        Arrays.fill(isPrime, true);

        for (int i = 2; i < MAX; i++) {
            if (isPrime[i]) {
                for (int j = i; j < MAX; j += i) {
                    isPrime[j] = false;
                    mobius[j] *= -1;
                }
                for (long j = 1L * i * i; j < MAX; j += i * i) {
                    mobius[(int)j] = 0;
                }
            }
        }
    }

    public int cntCoprime(int[] arr) {
        computeMobius();
        int n = arr.length;
        int[] freq = new int[MAX];

        int maxVal = 0;
        for (int val : arr) {
            freq[val]++;
            maxVal = Math.max(maxVal, val);
        }

        int[] divisibleCount = new int[MAX];
        for (int i = 1; i <= maxVal; i++) {
            for (int j = i; j <= maxVal; j += i) {
                divisibleCount[i] += freq[j];
            }
        }

        long totalCoprimePairs = 0;
        for (int i = 1; i <= maxVal; i++) {
            if (mobius[i] == 0) continue;
            long c = divisibleCount[i];
            totalCoprimePairs += mobius[i] * (c * (c - 1) / 2);
        }

        return (int) totalCoprimePairs;
    }

    
}
