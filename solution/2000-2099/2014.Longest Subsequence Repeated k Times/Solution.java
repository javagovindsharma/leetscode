import java.util.*;

class Solution {
    public String longestSubsequenceRepeatedK(String s, int k) {
        int[] count = new int[26];
        List<Character> possibleChars = new ArrayList<>();
        Queue<String> q = new LinkedList<>(Collections.singletonList(""));

        for (char c : s.toCharArray()) {
            count[c - 'a']++;
        }

        for (char c = 'a'; c <= 'z'; c++) {
            if (count[c - 'a'] >= k) {
                possibleChars.add(c);
            }
        }

        String ans = "";
        while (!q.isEmpty()) {
            String currSubseq = q.poll();
            if (currSubseq.length() * k > s.length()) {
                return ans;
            }
            for (char c : possibleChars) {
                String newSubseq = currSubseq + c;
                if (isSubsequence(newSubseq, s, k)) {
                    q.offer(newSubseq);
                    ans = newSubseq;
                }
            }
        }

        return ans;
    }

    private boolean isSubsequence(String subseq, String s, int k) {
        int i = 0;
        for (char c : s.toCharArray()) {
            if (c == subseq.charAt(i)) {
                i++;
                if (i == subseq.length()) {
                    k--;
                    if (k == 0) {
                        return true;
                    }
                    i = 0;
                }
            }
        }
        return false;
    }
}
