class Solution {
    public long sumAndMultiply(int n) {
        // placeValue tracks the positional multiplier (1, 10, 100, ...)
        // for building the number with zero digits removed
        int placeValue = 1;

        // compactedNumber: n with all zero digits stripped out
        // digitSum: the sum of all digits of n
        int compactedNumber = 0, digitSum = 0;

        // Process each digit from least significant to most significant
        for (; n > 0; n /= 10) {
            int digit = n % 10;

            // Always add the digit to the total digit sum
            digitSum += digit;

            // Only keep non-zero digits when rebuilding the number
            if (digit != 0) {
                compactedNumber += placeValue * digit;
                // Advance to the next place value only for kept digits
                placeValue *= 10;
            }
        }

        // Multiply the compacted number by the digit sum
        // Use 1L to force long arithmetic and avoid integer overflow
        return 1L * compactedNumber * digitSum;
    }
}
