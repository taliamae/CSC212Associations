package edu.smith.cs.csc212.assoc;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import javax.annotation.Nullable;

import me.jjfoley.adt.errors.TODOErr;

/**
 * A binary search tree (BST) of integers!
 * 
 * @author jfoley.
 */
public class IntBST {
	@Nullable
	BSTNode root;

	/**
	 * Construct an empty tree:
	 */
	public IntBST() {
		this.root = null;
	}

	/**
	 * Construct a tree with a given root:
	 * 
	 * @param root - a reference to a BSTNode; or null.
	 */
	IntBST(@Nullable BSTNode root) {
		this.root = root;
	}

	/**
	 * A tree is empty if it's root is null.
	 * @return - compare this root to null.
	 */
	public boolean isEmpty() {
		return this.root == null;
	}

	/**
	 * Ask root to compute its height, if available.
	 * @return 0 if empty, the height of the tree otherwise.
	 */
	public int getHeight() {
		if (this.root == null) {
			return 0;
		}
		return this.root.getHeight();
	}

	/**
	 * Ask root to insert this value into the tree if possible.
	 * @param value - the value to insert.
	 */
	public void insert(int value) {
		if (this.root == null) {
			this.root = new BSTNode(value);
		} else {
			this.root.insert(value);
		}
	}

	/**
	 * Ask root if this value is contained in the tree.
	 * @param value - the value to look for.
	 * @return true if it is found, false if not.
	 */
	public boolean contains(int value) {
		if (this.root == null) {
			return false;
		} else {
			return this.root.contains(value);
		}
	}

	/**
	 * Start off an in-order traversal; create a list to use with {@link BSTNode#addToSortedList}
	 * @return a sorted list of nodes.
	 */
	public List<Integer> toSortedList() {
		ArrayList<Integer> output = new ArrayList<>();
		if (this.root != null) {
			this.root.addToSortedList(output);
		}
		return output;
	}

	public HashSet<Integer> toSet() {
		HashSet<Integer> values = new HashSet<>();
		if (this.root != null) {
			this.root.addToSet(values);
		}
		return values;
	}

	@Override
	public String toString() {
		if (this.root == null) {
			return "Empty IntBST";
		}
		return "IntBST: " + this.root.toString();
	}

	@Override
	public boolean equals(Object other) {
		if (other instanceof IntBST) {
			return Objects.equals(this.root, ((IntBST) other).root);
		}
		return false;
	}

	public void remove(int value) {
		if (this.root == null) {
			return;
		}

		// Here's the deal, root doesn't have a valid parent, so let's make one,
		// temporarily!
		// That way BSTNode#remove can assume that every node has a parent!
		BSTNode rootsFakeParent = new BSTNode(-42);
		rootsFakeParent.left = this.root;
		this.root.remove(value, rootsFakeParent);
		// Now, let's grab the new root (if it's changed)!
		this.root = rootsFakeParent.left;
	}

	static class BSTNode {
		int value;
		@Nullable
		BSTNode left;
		@Nullable
		BSTNode right;

		public BSTNode(int value) {
			this.value = value;
			this.left = null;
			this.right = null;
		}

		public BSTNode(int value, @Nullable BSTNode left, @Nullable BSTNode right) {
			this.value = value;
			this.left = left;
			this.right = right;
		}

		public boolean equals(Object other) {
			if (other == null) {
				return false;
			}
			if (other instanceof BSTNode) {
				BSTNode bst = (BSTNode) other;
				// Check values first, this is easy.
				if (this.value != bst.value) {
					return false;
				}
				// This will check for nulls and recurse.
				return Objects.equals(this.left, bst.left) && Objects.equals(this.right, bst.right);
			}
			return false;
		}

		@Override
		public String toString() {
			return "(" + this.value + " L:" + this.left + " R:" + this.right + ")";
		}

		//// start:getHeight
		/**
		 * Recursively compute the height of the subtree starting at this node. The
		 * height of a node is 1 + the maximum height of it's left and right branches.
		 *
		 * @return an integer greater than or equal to 1.
		 */
		public int getHeight() {
			throw new TODOErr();
		}
		//// end

		/**
		 * Recursively find the position in this tree of BSTNodes to insert the value.
		 * @param value - an integer to insert.
		 * @return true if we changed the tree.
		 */
		public boolean insert(int value) {
			if (value == this.value) {
				return false;
			} else if (value < this.value) {
				// try-insert left:
				if (this.left == null) {
					this.left = new BSTNode(value);
					return true;
				} else {
					return this.left.insert(value);
				}
			} else {
				// try-insert right:
				if (this.right == null) {
					this.right = new BSTNode(value);
					return true;
				} else {
					return this.right.insert(value);
				}
			}
		}

		/**
		 * Add all items to the output set. Order does not matter because it is a set
		 * that we're building.
		 * 
		 * @param output - a set of integers.
		 */
		public void addToSet(Set<Integer> output) {
			output.add(this.value);
			if (this.left != null) {
				this.left.addToSet(output);
			}
			if (this.right != null) {
				this.right.addToSet(output);
			}
		}

		//// start:addToSortedList
		/**
		 * Call add (Java's addBack) on the output list to create an in-order traversal.
		 * 
		 * @param output - a list.
		 */
		public void addToSortedList(List<Integer> output) {
			throw new TODOErr();
		}
		//// end

		public int getMinimum() {
			if (this.left != null) {
				return this.left.getMinimum();
			}
			return this.value;
		}

		public int getMaximum() {
			if (this.right != null) {
				return this.right.getMaximum();
			}
			return this.value;
		}

		public void remove(int value, BSTNode parent) {
			if (value < this.value) {
				if (left != null) {
					left.remove(value, this);
				}
			} else if (value > this.value) {
				if (right != null) {
					right.remove(value, this);
				}
			} else {
				assert (value == this.value);
				if (this.left != null && this.right != null) {
					// find the minimum of the right subtree, and swap it up.
					this.value = this.right.getMinimum();
					// now chase down and remove the duplicate, recursively.
					this.right.remove(this.value, this);
				} else {
					// One or both of our links is null, so we can just keep it around:
					BSTNode child = this.left;
					if (child == null) {
						child = this.right;
					}
					// Update the link that brought us here:
					if (parent.left == this) {
						parent.left = child;
					} else {
						parent.right = child;
					}
				}
			}
		}

		//// start: contains
		/**
		 * Search through this BST and return ``true`` if it contains ``value``, false
		 * otherwise; starting at this node.
		 *
		 * Cases: 1. This node has the value we're looking for. 2. We need to go left or
		 * right. 2.a. The direction we need to go exists; call ``contains`` on that
		 * node. 2.b. The direction we need to go does not exist. Return false. It's not
		 * in the tree.
		 *
		 * @param value - the number we are searching for in this tree.
		 * @return true if found, false if not found.
		 */
		public boolean contains(int value) {
			throw new TODOErr();
		}
		//// end

	}
}
