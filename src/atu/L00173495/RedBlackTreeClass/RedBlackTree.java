package atu.L00173495.RedBlackTreeClass;

/**
 * This is a RedBlackTree
 * 
 * NOTE: To allow for our objects to be inserted (and found) properly they have
 * to be COMPARED to the objects in the tree. This is why we have <T extends
 * Comparable<T>> instead of just <T> : We are effectively saying that the
 * objects which can be stored MUST implement the Comparable interface.
 * 
 * NOTE: Our Node class is an inner class in an inner class at the bottom of the
 * code.
 * 
 * Original @author dermot.hegarty Edits and Modifications @author
 * Conor.Callaghan L00173495
 * 
 * @param <T>
 */
public class RedBlackTree<T extends Comparable<T>> {
	/**
	 * Reference to the root of the tree
	 */
	public Node root;

	/**
	 * This is the public insert method, i.e. the one that the outside world will
	 * invoke. It then kicks off a recursive method to "walk" through down through
	 * the tree - this is possible because each sub-tree is itself a tree.
	 * 
	 * @param value Object to insert into the tree
	 */
	public void insert(T value) {
		Node node = new Node(value); // Create the Node to add
		if (root == null) {
			root = node;
			// Remember that new nodes default to Red but
			// the root must always be black
		} else {
			// Initially we start at the root. Each subsequent recursive call will be to a
			// left or right subtree.
			insertRec(root, node);
		}

		// Now that we've inserted we need to make it Red-Black (if necessary)
		handleRedBlack(node);

	}

	/**
	 * This is the insertRec method. This method is recursively called in order to
	 * insert the node at the correct position in the tree
	 * 
	 * @param subTreeRoot The SubTree to insert into
	 * @param node        The Node that we wish to insert
	 */
	protected void insertRec(Node subTreeRoot, Node node) {

		// Note the call to the compareTo() method. This is only possible if our objects
		// implement the Comparable interface.
		if (node.value.compareTo(subTreeRoot.value) < 0) {

			// This is our terminal case for recursion. We should be going left but there is
			// no leaf node there so that is obviously where we must insert
			if (subTreeRoot.left == null) {
				subTreeRoot.left = node;
				node.parent = subTreeRoot;
				return;
			} else { // There is a node to the left of the subTreeRoot
				// Now our new "root" is the left subTree
				insertRec(subTreeRoot.left, node);
			}
		}
		// Same logic for the right subtree
		else {
			// If subTreeRoot does not have a right node then we insert the node at that
			// point
			if (subTreeRoot.right == null) {
				subTreeRoot.right = node;
				node.parent = subTreeRoot;
				return;
			} else { // There is a node to the right of the subTreeRoot
				insertRec(subTreeRoot.right, node);
			}
		}
	}

	/**
	 * Note: This method may be called recursively but only for the case where the
	 * Uncle is red (assuming that the parent node is red - which is a violation, of
	 * course)
	 * 
	 * @param newNode
	 */
	protected void handleRedBlack(Node newNode) {
		// terminating case for "back" recursion - e.g. case 3 (video)
		// If the new Node is the root, it must be set to black
		if (newNode == root) {
			newNode.nodeColourRed = false;
			return;
		}
		// Declare the parent, uncle and grandparent of the newNode
		// Assign the values for parent and grandParent
		Node parent = newNode.parent;
		Node uncle;
		Node grandParent = parent.parent;
		// Now that it's inserted we try to ensure that it's a RedBlack Tree
		// Check if parent is red. This is a violation. I (the new node) am red
		// so my parent cannot also be red!
		if (parent.nodeColourRed) {
			// important that we figure out where the uncle is
			// relative to the current node
			if (uncleOnRightTree(newNode)) {
				uncle = getRightUncle(newNode);
			} else {
				uncle = getLeftUncle(newNode);
			}
			// Now we need to check if x's uncle is RED (Grandparent must
			// have been black)
			// This is case 3 according to the video
			// (https://www.youtube.com/watch?v=g9SaX0yeneU)
			if ((uncle != null) && (uncle.nodeColourRed)) {
				// Recolour parent and uncle.
				// If grandparent is not the root then also recolour it.
				parent.nodeColourRed = false;
				uncle.nodeColourRed = false;
				if (grandParent != root)
					grandParent.nodeColourRed = true;
				// recursively call handleRedBlack with the grandparent
				// To manage the colouring further up the tree
				handleRedBlack(grandParent);
			}
			// If the parent is null or black
			else if ((uncle == null) || !uncle.nodeColourRed) {
				// Identify the location of the parent and the newNode
				// Left-Left Case
				if (parent == grandParent.left && newNode == parent.left) {
					applyLeftLeftCase(parent, grandParent, grandParent.parent);
				}
				// Left-Right Case
				else if (parent == grandParent.left && newNode == parent.right) {
					grandParent.left = rotateSubTreeLeft(parent);
					applyLeftLeftCase(grandParent.left, grandParent, grandParent.parent);

				}
				// Right-Right Case
				else if (parent == grandParent.right && newNode == parent.right) {
					applyRightRightCase(parent, grandParent, grandParent.parent);

				}
				// Right-Left Case
				else {
					grandParent.right = rotateSubTreeRight(parent);
					applyRightRightCase(grandParent.right, grandParent, grandParent.parent);

				}

			}
		}

	}

	/**
	 * Note: This method may be called to conduct apply the left left case This is
	 * done when there are 2 consecutive red nodes which is a violation
	 * 
	 * @param inParent
	 * @param inGrandParent
	 * @param inGreatGrandParent
	 */
	private void applyLeftLeftCase(Node inParent, Node inGrandParent, Node inGreatGrandParent) {

		// Rotate grandparent right and get the new root of the subTree
		// Recolour the parent and grandParent nodes
		Node newSubTreeRoot = rotateSubTreeRight(inGrandParent);
		inParent.nodeColourRed = false;
		inGrandParent.nodeColourRed = true;
		// If greatGrandparent is null
		// Then the newSubTreeRoot is the parent
		// Else assign newSubTreeRoot as one of the greatGrandparent children
		if (inGreatGrandParent == null) {
			root = newSubTreeRoot;
		} else if (inGreatGrandParent.left == inGrandParent) {
			inGreatGrandParent.left = newSubTreeRoot;
		} else {
			inGreatGrandParent.right = newSubTreeRoot;
		}

	}

	/**
	 * Note: This method may be called to conduct apply the right right case This is
	 * done when there are 2 consecutive red nodes which is a violation
	 * 
	 * @param inParent
	 * @param inGrandParent
	 * @param inGreatGrandParent
	 */
	private void applyRightRightCase(Node inParent, Node inGrandParent, Node inGreatGrandParent) {
		// Rotate grandparent left and get the new root
		// Recolour the parent and grandParent nodes
		Node newSubTreeRoot = rotateSubTreeLeft(inGrandParent);
		inParent.nodeColourRed = false;

		inGrandParent.nodeColourRed = true;
		// If greatGrandparent is null
		// Then the newSubTreeRoot is the parent
		// Else assign newSubTreeRoot as one of the greatGrandparent children
		if (inGreatGrandParent == null) {
			root = newSubTreeRoot;
		} else if (inGreatGrandParent.left == inGrandParent) {
			inGreatGrandParent.left = newSubTreeRoot;
		} else {
			inGreatGrandParent.right = newSubTreeRoot;
		}

	}

	/**
	 * Note: This method is called to identify the value of the uncle if it is on
	 * the left
	 * 
	 * @param newNode
	 * @return Node uncle
	 */
	private Node getLeftUncle(Node newNode) {
		// Get the parent then the grandparent and its left which is uncle
		return newNode.parent.parent.left;
	}

	/**
	 * Note: This method is called to identify the value of the uncle if it is on
	 * the right
	 * 
	 * @param newNode
	 * @return Node uncle
	 */
	private Node getRightUncle(Node newNode) {
		return newNode.parent.parent.right;
	}

	/**
	 * Note: This method is called to identify if the newNodes uncle is on the
	 * grandparent's right
	 * 
	 * @param newNode
	 * @return boolean
	 */
	private boolean uncleOnRightTree(Node newNode) {
		// Identify Nodes parent
		Node parent = newNode.parent;
		// identify Nodes grandparent
		Node grandParent = parent.parent;
		// I am checking if my uncle is on the right
		// Therefore if Grandparent.right is equal to parent
		// Uncle will be on left
		if (grandParent.right == parent) {
			return false;
		}
		// Only return true if parent is on the Grandparent.left
		return true;

	}
	
	/**
	 * Note: This method is called to rotate the entire tree to the left
	 * 
	 */
	public void rotateTreeLeft() {
		root.right = rotateSubTreeLeft(root.right);
	}

	/**
	 * Note: This method is called to rotate the entire tree to the right
	 * 
	 */
	public void rotateTreeRight() {
		root.left = rotateSubTreeRight(root.left);
	}

	/**
	 * Note: This method is used to rotate a subTree to the left The root of the
	 * subTree will be moved to the left The subTreeRoot.right will become the root
	 * of the subTree and new parent of the previous root
	 * 
	 * @param Node subTreeRoot
	 * @return Node pivot (which is the new subTreeRoot)
	 */
	public Node rotateSubTreeLeft(Node subTreeRoot) {
		Node pivot = subTreeRoot.right;
		subTreeRoot.right = pivot.left;
		if (pivot.left != null) {
			pivot.left.parent = subTreeRoot;
		}
		pivot.left = subTreeRoot;
		pivot.parent = subTreeRoot.parent;
		subTreeRoot.parent = pivot;

		return pivot;

	}

	/**
	 * Note: This method is used to rotate a subTree to the right The root of the
	 * subTree will be moved to the right The subTreeRoot.left will become the root
	 * of the subTree and new parent of the previous root
	 * 
	 * @param Node subTreeRoot
	 * @return Node pivot (which is the new subTreeRoot)
	 */
	public Node rotateSubTreeRight(Node subTreeRoot) {
		Node pivot = subTreeRoot.left;
		subTreeRoot.left = pivot.right;
		if (pivot.right != null) {
			pivot.right.parent = subTreeRoot;
		}
		pivot.right = subTreeRoot;
		pivot.parent = subTreeRoot.parent;
		subTreeRoot.parent = pivot;
		return pivot;
	}


	/**
	 * Note: This method is called to identify if the maximum node
	 * 
	 * @return T
	 */
	public T findMaximum() {

		Node current = root;
		while (current.right != null) {
			current = current.right;
		}
		return current.value;

	}

	/**
	 * Note: This method is called to identify if the minimum node It calls the
	 * recFindMinimum which recursively passes through the Tree to find the minimum
	 * 
	 * @return T
	 */
	public T findMinimum() {
		return recFindMinimum(root);
	}

	/**
	 * Note: This method calls upon itself to search through the RedBlackTree in
	 * order to identify the minimum value
	 * 
	 * @param Node subTreeRoot
	 * @return T
	 */
	private T recFindMinimum(Node subTreeRoot) {
		if (subTreeRoot.left != null) {
			return recFindMinimum(subTreeRoot.left);
		}
		return subTreeRoot.value;
	}

	/**
	 * Note: This method is called to search through the tree to identify if the
	 * searchVal value is within the tree
	 * 
	 * @param T searchVal
	 * @return T
	 */
	public T find(T searchVal) {
		// Start at the root and recurse
		return recFind(root, searchVal);
	}

	/**
	 * Note: This method calls upon itself to search through the RedBlackTree in
	 * order to identify the position of the searchVal value if it exists
	 * 
	 * @param Node subTreeRoot
	 * @return T
	 */
	private T recFind(Node subTreeRoot, T searchVal) {
		// Base case: node is null
		if (subTreeRoot == null) {
			return null;
		}

		int compareResult = searchVal.compareTo(subTreeRoot.value);

		if (compareResult == 0) {
			// Found the value
			return subTreeRoot.value;
		} else if (compareResult < 0) {
			// Search in the left subtree
			return recFind(subTreeRoot.left, searchVal);
		} else {
			// Search in the right subtree
			return recFind(subTreeRoot.right, searchVal);
		}
	}

	

	//////////////////////////////////////////

	/**
	 * Should traverse the tree "in-order." See the notes
	 */
	public void inOrderTraversal() {
		// start at the root and recurse
		recInOrderTraversal(root);
	}

	public void preOrderTraversal() {
		// start at the root and recurse
		recPreOrderTraversal(root);
	}

	public void postOrderTraversal() {
		// start at the root and recurse
		recPostOrderTraversal(root);
	}
	
	/**
	 * This allows us to recursively process the tree "in-order". Note that it is
	 * private
	 * 
	 * @param subTreeRoot
	 */
	private void recInOrderTraversal(Node subTreeRoot) {
		if (subTreeRoot == null)
			return;

		recInOrderTraversal(subTreeRoot.left);
		processNode(subTreeRoot);
		recInOrderTraversal(subTreeRoot.right);
	}

	/**
	 * This allows us to recursively process the tree "pre-order". Note that it is
	 * private
	 * 
	 * @param subTreeRoot
	 */
	private void recPreOrderTraversal(Node subTreeRoot) {
		if (subTreeRoot == null)
			return;

		processNode(subTreeRoot);
		recPreOrderTraversal(subTreeRoot.left);
		recPreOrderTraversal(subTreeRoot.right);
	}

	/**
	 * This allows us to recursively process the tree "post-order". Note that it is
	 * private
	 * 
	 * @param subTreeRoot
	 */
	private void recPostOrderTraversal(Node subTreeRoot) {
		if (subTreeRoot == null)
			return;

		recPostOrderTraversal(subTreeRoot.left);
		recPostOrderTraversal(subTreeRoot.right);
		processNode(subTreeRoot);
	}

	/**
	 * Do some "work" on the node - here we just print it out
	 * 
	 * @param currNode
	 */
	private void processNode(Node currNode) {
		System.out.println(currNode.toString());
	}

	/**
	 * 
	 * @return The number of nodes in the tree
	 */
	public int countNodes() {
		return recCountNodes(root);
	}

	/**
	 * Note: This is a practical example of a simple usage of pre-order traversal
	 * 
	 * @param subTreeRoot
	 * @return
	 */
	private int recCountNodes(Node subTreeRoot) {
		if (subTreeRoot == null)
			return 0;

		// Look at the pre-order. "Count this node and THEN count the left and right
		// subtrees recursively
		return 1 + recCountNodes(subTreeRoot.left) + recCountNodes(subTreeRoot.right);
	}

	/////////////////////////////////////////////////////////////////
	/**
	 * Our Node contains a value and a reference to the left and right subtrees
	 * also holds a reference to the parent Node and the Nodes current colour
	 * (initially null)
	 * 
	 * Original @author dermot.hegarty 
	 * Edits and Modifications @author Conor.Callaghan L00173495
	 */
	protected class Node {
		protected T value; // value is the actual object that we are storing
		protected Node left; // The left child of the Node
		protected Node right; // The right child of the Node
		protected Node parent; // The parent of the Node
		protected boolean nodeColourRed; // New nodes are always red

		public Node(T value) {
			this.value = value;
			nodeColourRed = true;
		}

		@Override
		public String toString() {
			return "Node [value=" + value + "] : Colour [" + nodeColourRed + "]";
		}

	}

}