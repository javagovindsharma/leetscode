#include <string>
#include <vector>
#include <algorithm>

using namespace std;

class Solution {
public:
    string lexPalindromicPermutation(string s, string target) {
        int n = s.length();
        vector<int> counts(26, 0);
        for (char c : s) counts[c - 'a']++;

        // 1. Validate if a valid palindrome permutation is possible
        int odd_count = 0;
        char odd_char = 0;
        for (int i = 0; i < 26; ++i) {
            if (counts[i] % 2 != 0) {
                odd_count++;
                odd_char = 'a' + i;
            }
        }
        if (odd_count > 1) return "";

        // 2. Extract half of the available characters for prefix building
        vector<int> half_counts(26, 0);
        for (int i = 0; i < 26; ++i) {
            half_counts[i] = counts[i] / 2;
        }

        int half_len = n / 2; // Notice: left half length is strictly n/2
        string prefix = "";
        string result = "";

        // 3. Backtracking to build the optimal prefix
        if (backtrack(0, half_len, n, half_counts, odd_char, target, prefix, result)) {
            return result;
        }
        return "";
    }

private:
    bool backtrack(int idx, int half_len, int n, vector<int>& half_counts, 
                   char odd_char, const string& target, string& prefix, string& result) {
        if (idx == half_len) {
            // Construct the full palindrome based on the prefix built so far
            string full = prefix;
            if (n % 2 != 0) {
                full += odd_char;
            }
            string rev = prefix;
            reverse(rev.begin(), rev.end());
            full += rev;

            // Check if it strictly beats the target
            if (full > target) {
                result = full;
                return true;
            }
            return false;
        }

        // Option A: Try to match the target's character to maintain target boundary
        int target_char_idx = target[idx] - 'a';
        if (half_counts[target_char_idx] > 0) {
            half_counts[target_char_idx]--;
            prefix.push_back(target[idx]);
            
            if (backtrack(idx + 1, half_len, n, half_counts, odd_char, target, prefix, result)) {
                return true;
            }
            
            // Backtrack
            prefix.pop_back();
            half_counts[target_char_idx]++;
        }

        // Option B: Choose a strictly greater character, allowing us to fill greedily
        for (int i = target_char_idx + 1; i < 26; ++i) {
            if (half_counts[i] > 0) {
                half_counts[i]--;
                prefix.push_back('a' + i);

                // Greedily append the remaining characters from smallest to largest
                string greedy_prefix = prefix;
                vector<int> temp_counts = half_counts;
                for (int j = 0; j < 26; ++j) {
                    while (temp_counts[j] > 0) {
                        greedy_prefix.push_back('a' + j);
                        temp_counts[j]--;
                    }
                }

                // Complete the string configuration
                if (n % 2 != 0) {
                    greedy_prefix.push_back(odd_char);
                }
                string rev = greedy_prefix.substr(0, half_len);
                reverse(rev.begin(), rev.end());
                greedy_prefix += rev;

                result = greedy_prefix;
                
                // Roll back changes before returning success
                prefix.pop_back();
                half_counts[i]++;
                return true;
            }
        }

        return false;
    }
};
