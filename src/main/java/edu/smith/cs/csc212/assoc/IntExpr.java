package edu.smith.cs.csc212.assoc;

import me.jjfoley.adt.errors.TODOErr;

/**
 * This file defines a small extension to the "Math" expression trees discussed
 * in the videos. Can you implement BinaryExpr.evaluate?
 * 
 * @author jfoley.
 */
public abstract class IntExpr {
	public abstract int evaluate();

	/**
	 * This is a literal number, like 5, 0, -7 or 89.
	 */
	public static class Value extends IntExpr {
		int literal;

		public Value(int literal) {
			this.literal = literal;
		}

		@Override
		public int evaluate() {
			return this.literal;
		}

		@Override
		public String toString() {
			return Integer.toString(literal);
		}
	}

	/**
	 * This is an expression with one argument, like !x or -x.
	 */
	public static class UnaryExpr extends IntExpr {
		String op;
		IntExpr child;

		public UnaryExpr(String op, IntExpr child) {
			this.op = op;
			this.child = child;
		}

		@Override
		public String toString() {
			return "( " + op + " " + child.toString() + ")";
		}

		@Override
		public int evaluate() {
			if (op.equals("-")) {
				return -1 * child.evaluate();
			} else if (op.equals("!")) {
				int result = child.evaluate();
				// We consider zero false and anything else to be true.
				if (result == 0) {
					return 1;
				} else {
					return 0;
				}
			}
			return 0;
		}
	}

	/**
	 * This is an "if expression" -- if a result is not zero, it's considered true.
	 * We can encode "equals" by doing a subtraction of two values!
	 */
	public static class IfExpr extends IntExpr {
		IntExpr cond;
		IntExpr thenExpr;
		IntExpr elseExpr;

		public IfExpr(IntExpr cond, IntExpr thenExpr, IntExpr elseExpr) {
			this.cond = cond;
			this.thenExpr = thenExpr;
			this.elseExpr = elseExpr;
		}

		@Override
		public int evaluate() {
			int condition = cond.evaluate();
			if (condition != 0) {
				return this.thenExpr.evaluate();
			} else {
				return this.elseExpr.evaluate();
			}
		}

		@Override
		public String toString() {
			return "(if " + cond + " { " + thenExpr + " } else { " + elseExpr + " })";
		}
	}

	/**
	 * This is the class you're going to be modifying: nodes in the tree that are
	 * BinaryExpr can be one of +, -, /, *, and %.
	 */
	public static class BinaryExpr extends IntExpr {
		/**
		 * The math operator to compute.
		 */
		String op;
		/**
		 * The left-side value of the operator, as an expression of its own.
		 */
		IntExpr left;
		/**
		 * The right-side value of the operator, as an expression of its own.
		 */
		IntExpr right;

		public BinaryExpr(String op, IntExpr left, IntExpr right) {
			this.op = op;
			this.left = left;
			this.right = right;
		}

		/**
		 * Notice how this function is recursive, as well?
		 * 
		 * @return stringified: ( left op right )
		 */
		@Override
		public String toString() {
			return "(" + left.toString() + " " + op + " " + right.toString() + ")";
		}

		//// start:evaluate
		/**
		 * Evaluate a binary expression, recursively. Supports operators
		 * (this.op.equals(...)) -> "+", "-", "/", "*", and "%".
		 *
		 * @return the value that should be computed by this node.
		 */
		@Override
		public int evaluate() {
			if (op.equals("+")) {
				throw new TODOErr();
			}
			throw new RuntimeException("Unsupported operator=" + op);
		}
		//// end
	}
}
