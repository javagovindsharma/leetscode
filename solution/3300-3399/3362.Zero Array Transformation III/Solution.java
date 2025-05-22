import java.util.*;

class Solution {
    public int maxRemoval(int[] nums, int[][] queries) {
        int n = nums.length;
        PriorityQueue<Integer> usedQuery = new PriorityQueue<>(); // Min-heap (stores end points)
        PriorityQueue<Integer> availableQuery = new PriorityQueue<>(Collections.reverseOrder()); // Max-heap (stores end points)
        
        // Sort queries by start point
        Arrays.sort(queries, (a, b) -> Integer.compare(a[0], b[0]));
        
        int queryPos = 0;
        int appliedCount = 0;
        
        for (int i = 0; i < n; i++) {
            // Push all queries starting at `i` into availableQuery
            while (queryPos < queries.length && queries[queryPos][0] == i) {
                availableQuery.offer(queries[queryPos][1]);
                queryPos++;
            }
            
            // Adjust nums[i] by subtracting the number of active queries covering it
            nums[i] -= usedQuery.size();
            
            // Apply queries if nums[i] > 0
            while (nums[i] > 0 && !availableQuery.isEmpty() && availableQuery.peek() >= i) {
                int end = availableQuery.poll();
                usedQuery.offer(end);
                nums[i]--;
                appliedCount++;
            }
            
            // If nums[i] couldn't be reduced to zero
            if (nums[i] > 0) {
                return -1;
            }
            
            // Remove queries that end at `i` from usedQuery
            while (!usedQuery.isEmpty() && usedQuery.peek() == i) {
                usedQuery.poll();
            }
        }
        
        return queries.length - appliedCount;
    }
}
