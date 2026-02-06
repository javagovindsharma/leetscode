class Solution {
   int[] smallestDiff(int a[], int b[], int c[]) {
    Arrays.sort(a);
    Arrays.sort(b);
    Arrays.sort(c);

    int i = 0, j = 0, k = 0;
    int bestDiff = Integer.MAX_VALUE;
    int bestSum = Integer.MAX_VALUE;

    int x = 0, y = 0, z = 0;

    while (i < a.length && j < b.length && k < c.length) {
        int A = a[i], B = b[j], C = c[k];

        int maxVal = Math.max(A, Math.max(B, C));
        int minVal = Math.min(A, Math.min(B, C));
        int diff = maxVal - minVal;
        int sum = A + B + C;

        if (diff < bestDiff || (diff == bestDiff && sum < bestSum)) {
            bestDiff = diff;
            bestSum = sum;
            x = A;
            y = B;
            z = C;
        }

        // Move pointer of the minimum element
        if (minVal == A) i++;
        else if (minVal == B) j++;
        else k++;
    }

    int[] ans = new int[]{x, y, z};
    Arrays.sort(ans);              // increasing
    return new int[]{ans[2], ans[1], ans[0]};  // decreasing order
}
}
