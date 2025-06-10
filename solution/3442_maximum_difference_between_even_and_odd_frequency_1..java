import java.util.HashMap;
import java.util.Map;

public class Solution {
    public int maxDifference(String s) {
        Map<Character, Integer> freqMap = new HashMap<>();
        
        // Count character frequencies
        for (char c : s.toCharArray()) {
            freqMap.put(c, freqMap.getOrDefault(c, 0) + 1);
        }

        int a1 = Integer.MIN_VALUE;
        int a2 = Integer.MAX_VALUE;

        // Process frequencies
        for (Map.Entry<Character, Integer> entry : freqMap.entrySet()) {
            int freq = entry.getValue();
            if (freq % 2 == 1) {
                a1 = Math.max(a1, freq);
            } else {
                a2 = Math.min(a2, freq);
            }
        }

        // Handle case when a1 or a2 was never updated
        if (a1 == Integer.MIN_VALUE || a2 == Integer.MAX_VALUE) {
            return -1; // or some appropriate value for "not found"
        }

        return a1 - a2;
    }
}
