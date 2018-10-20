package amazon.ood;

import java.util.Stack;

enum ExpTreeNodeType {
	OPERATOR, OPERAND
}

abstract class ExpNode {
	abstract double value();
	abstract String printInfix();
}

// the left and right operands of a BinOpNode are of type ExpNode, not
// BinOpNode. This allows the operand to be either a ConstNode or another
// BinOpNode -- or any other type of ExpNode that we might eventually create.
class BinOpNode extends ExpNode {
	// Represents a node that holds an operator.

	char op; // The operator.
	ExpNode left; // The left operand.
	ExpNode right; // The right operand.

	BinOpNode(char op, ExpNode left, ExpNode right) {
		// Constructor. Create a node to hold the given data.
		this.op = op;
		this.left = left;
		this.right = right;
	}

	double value() {
		// To get the value, compute the value of the left and
		// right operands, and combine them with the operator.
		double leftVal = left.value();
		double rightVal = right.value();
		switch (op) {
		case '+':
			return leftVal + rightVal;
		case '-':
			return leftVal - rightVal;
		case '*':
			return leftVal * rightVal;
		case '/':
			return leftVal / rightVal;
		default:
			return Double.NaN; // Bad operator.
		}
	}
	
	String printInfix() {
		StringBuilder sb = new StringBuilder();
		sb.append("(");
		sb.append(left.printInfix());
		sb.append(op);
		sb.append(right.printInfix());
		sb.append(")");
		return sb.toString();
	}
} // end class BinOpNode

class ConstNode extends ExpNode {
	double number; // The number in the node.

	ConstNode(double val) {
		// Constructor. Create a node to hold val.
		number = val;
	}

	double value() {
		// The value is just the number that the node holds.
		return number;
	}
	
	String printInfix() {
		return String.valueOf(number);
	}
}

class UnaryMinusNode extends ExpNode {
	// An expression node to represent a unary minus operator.
	ExpNode operand; // The operand to which the unary minus applies.

	UnaryMinusNode(ExpNode operand) {
		// Construct a UnaryMinusNode with the specified operand.
		this.operand = operand;
	}

	double value() {
		// The value is the negative of the value of the operand.
		double neg = operand.value();
		return -neg;
	}
	
	String printInfix() {
		return "-";
	}
}

// http://math.hws.edu/eck/cs124/javanotes3/c11/s4.html - evaluate tree
// http://math.hws.edu/eck/cs124/javanotes3/c11/s5.html - build tree

// build tree - infix (2*6-(23+7)/(1+2))
// https://github.com/awangdev/LintCode/blob/master/Java/Expression%20Tree%20Build.java
public class ExpressionTree {
	private ExpNode root;

	public ExpressionTree(String[] postfixExpr) {
		this.root = buildExpTreeByPostFix(postfixExpr);
	}

	// String postfix = "ab+ef*g*-";
	// evalute reverse polish notation
	private ExpNode buildExpTreeByPostFix(String[] postfixExpr) {
		Stack<ExpNode> st = new Stack<>();
		ExpNode t, t1, t2;

		for (int i = 0; i < postfixExpr.length; i++) {

			if (!isOperator(postfixExpr[i])) {
				t = new ConstNode(Double.parseDouble(postfixExpr[i]));
				st.push(t);
			} else {
				t1 = st.pop();
				t2 = st.pop();

				t = new BinOpNode(getOperator(postfixExpr[i]), t1, t2);

				st.push(t);
			}
		}

		t = st.peek();
		st.pop();

		return t;
	}

	private boolean isOperator(String str) {
		return false;
	}

	private char getOperator(String str) {
		return 0;
	}

	public double evaluate() {
		return root.value();
	}

	public double evaluate(ExpNode node) {
		return node.value();
	}

	private double evaluate(ExpTreeNode cur) {
		if (cur == null) {
			return 0;
		} else if (cur.type == ExpTreeNodeType.OPERAND) { // leave nodes
			return Double.parseDouble(cur.val);
		}

		double left = evaluate(cur.left);
		double right = evaluate(cur.right);

		switch (cur.val) {
		case "+":
			return left + right;
		case "-":
			return left - right;
		case "*":
			return left * right;
		case "/":
			return left / right;
		case "^":
			return Math.pow(left, right);
		default:
			return 0;
		}
	}
}

class ExpTreeNode {
	String val;
	ExpTreeNodeType type;
	ExpTreeNode left, right;

	public ExpTreeNode() {
	}
}