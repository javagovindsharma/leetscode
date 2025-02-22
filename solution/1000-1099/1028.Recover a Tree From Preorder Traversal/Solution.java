import java.util.Stack;

class TreeNode {
    int val;
    TreeNode left, right;
    
    TreeNode(int val) {
        this.val = val;
    }
}

public class Solution {
    public TreeNode recoverFromPreorder(String traversal) {
        Stack<TreeNode> stack = new Stack<>();
        int i = 0;
        
        while (i < traversal.length()) {
            int depth = 0;
            
            // Count the number of dashes to determine the depth
            while (i < traversal.length() && traversal.charAt(i) == '-') {
                depth++;
                i++;
            }
            
            // Extract the node value
            int num = 0;
            while (i < traversal.length() && Character.isDigit(traversal.charAt(i))) {
                num = num * 10 + (traversal.charAt(i) - '0');
                i++;
            }
            
            TreeNode node = new TreeNode(num);
            
            // Ensure stack has the correct depth
            while (stack.size() > depth) {
                stack.pop();
            }
            
            // Attach the new node as left or right child
            if (!stack.isEmpty()) {
                if (stack.peek().left == null) {
                    stack.peek().left = node;
                } else {
                    stack.peek().right = node;
                }
            }
            
            // Push new node into stack
            stack.push(node);
        }
        
        // Return the root node (bottom-most in stack)
        while (stack.size() > 1) {
            stack.pop();
        }
        
        return stack.peek();
    }
}
