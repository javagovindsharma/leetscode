class Solution {
   public int sortableIntegers(int[] nums) {
    int n = nums.length;
    int ans = 0;

    for (int k = 1; k <= n; k++) {
        if (n % k != 0) {
            continue;
        }

        boolean valid = true;
        int prevMax = Integer.MIN_VALUE;

        for (int start = 0; start < n; start += k) {
            int end = start + k;
            int min = Integer.MAX_VALUE;
            int max = Integer.MIN_VALUE;
            int descents = 0;

            for (int i = start; i < end; i++) {
                min = Math.min(min, nums[i]);
                max = Math.max(max, nums[i]);

                int next = (i + 1 == end) ? start : i + 1;
                if (nums[i] > nums[next]) {
                    descents++;
                }
            }

            // This block must be sortable by cyclic rotation.
            if (descents > 1) {
                valid = false;
                break;
            }

            // The sorted blocks must also be globally ordered.
            if (start > 0 && prevMax > min) {
                valid = false;
                break;
            }

            prevMax = max;
        }

        if (valid) {
            ans += k;
        }
    }

    return ans;
}
}
