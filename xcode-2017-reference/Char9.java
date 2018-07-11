import java.util.Stack;

public class TreeTraversal {

	public static void iterativePreOrder(TreeNode root) {
		if (root == null)
			return;
		Stack<TreeNode> s = new Stack<TreeNode>();
		s.push(root);
		TreeNode p = null;
		while (!s.empty()) {
			p = s.pop();
			System.out.println(p.getValue());
			if (p.getRight() != null) {
				s.push(p.getRight());
			}
			if (p.getLeft() != null) {
				s.push(p.getLeft());
			}
		}
	}

	public static void iterativeInOrder(TreeNode root) {
		if (root == null) {
			return;
		}
		Stack<TreeNode> s = new Stack<>();
		TreeNode current = root;
		while (true) {
			if (current != null) {
				s.push(current);
				current = current.left;
				continue;
			}
			if (s.empty()) {
				break;
			}
			current = s.pop();
			System.out.println(current.val);
			current = current.right;
		}
	}

	public static void iterativeInOrder2(TreeNode root) {
		Stack<TreeNode> stack = new Stack<>();
		stack.push(root);
		while (!stack.isEmpty()) {
			// Get the top node
			TreeNode currNode = stack.peek();
			// If it does not have a left child
			// then we pop it and print it.
			if (currNode.getLeft() == null) {
				System.out.println(stack.pop().getValue());
			} else {
				// We need go to the right side of this node
				if (currNode.getRight() != null) {
					stack.push(currNode.getRight());
				} else {
					stack.push(currNode.getLeft());
				}
			}
		}
	}

	// http://www.leetcode.com/2010/10/binary-tree-post-order-traversal.html
	public static void iteractivePostOrder(TreeNode root) {
		if (root == null)
			return;
		Stack<TreeNode> s = new Stack<TreeNode>();
		s.push(root);
		TreeNode current;
		while (!s.empty()) {
			current = s.peek();
			if (current.getLeft() != null
					&& !current.getLeft().isVisited()) {
				s.push(current.getLeft());
			} else if (current.getRight() != null
					&& !current.getRight().isVisited()) {
				s.push(current.getRight());
			} else {
				// both left child and right child are visited
				System.out.println(current.getValue());
				current.setVisited(true);
				s.pop();
			}
		}
	}

	public static void postOrderTraversalIterative(TreeNode root) {
		if (root == null) {
			return;
		}
		Stack<TreeNode> s = new Stack<>();
		s.push(root);
		TreeNode prev = null;
		TreeNode current;
		while (!s.empty()) {
			current = s.peek();
			// we are traversing down the tree
			if (prev == null || prev.getLeft() == current
					|| prev.getRight() == current) {
				if (current.getLeft() != null) {
					s.push(current.getLeft());
				} else if (current.getRight() != null) {
					s.push(current.getRight());
				} else {
					System.out.println(current.getValue());
					s.pop();
				}
			}
			// we are traversing up the tree from the left
			else if (current.getLeft() == prev) {
				if (current.getRight() != null) {
					s.push(current.getRight());
				} else {
					System.out.println(current.getValue());
					s.pop();
				}
			}
			// we are traversing up the tree from the right
			else if (current.getRight() == prev) {
				System.out.println(current.getValue());
				s.pop();
			}

			prev = current;
		}
	}

	/*
	 * The above method is easy to follow, but has some redundant code. We could
	 * refactor out the redundant code, and now it appears to be more concise.
	 * Note how the code section for printing curr��s value get refactored into
	 * one single else block. Don't worry about in an iteration where its value
	 * won��t get printed, as it is guaranteed to enter the else section in the
	 * next iteration.
	 */
	public static void postOrderTraversalIterativeShortversion(TreeNode root) {
		if (root == null)
			return;
		Stack<TreeNode> s = new Stack<TreeNode>();
		s.push(root);
		TreeNode prev = null;
		TreeNode current;
		while (!s.empty()) {
			current = s.peek();
			// we are traversing down the tree
			if (prev == null || prev.getLeft() == current
					|| prev.getRight() == current) {
				if (current.getLeft() != null) {
					s.push(current.getLeft());
				} else if (current.getRight() != null) {
					s.push(current.getRight());
				}
			}
			// we are traversing up the tree from the left
			else if (current.getLeft() == prev) {
				if (current.getRight() != null) {
					s.push(current.getRight());
				}
			} else {
				System.out.println(current.getValue());
				s.pop();
			}

			prev = current;
		}
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {

	}

}
