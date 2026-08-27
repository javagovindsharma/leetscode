class Solution {
    boolean flag = false;

    public String lexGreaterPermutation(String s, String target) {
        int[] freq = new int[26];
        for (char ch : s.toCharArray()) freq[ch - 'a']++;

        StringBuilder current = new StringBuilder();
        backtrack(current, target, freq, 0);
        return flag ? current.toString() : "";
    }

    public void backtrack(StringBuilder current, String target, int[] freq, int idx) {
        if (flag) return;
        if (idx == target.length()) {
            if (current.toString().compareTo(target) > 0) flag = true;
            return;
        }

        for (int i = 0; i < 26; i++) {
            if (freq[i] == 0) continue;
            char ch = (char) ('a' + i);
            if (current.toString().equals(target.substring(0, idx)) && ch < target.charAt(idx)) continue;

            current.append(ch);
            freq[i]--;
            backtrack(current, target, freq, idx + 1);
            if (flag) return;
            current.deleteCharAt(current.length() - 1);
            freq[i]++;
        }
    }
}
