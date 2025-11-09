/**
 * 
 */
package atu.L00173495.RedBlackTreeTester;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import atu.L00173495.RedBlackTreeClass.RedBlackTree;

/**
 * 
 */
class RedBlackTreeTest extends RedBlackTree<Integer> {
	RedBlackTree<Integer> myTree;

	@BeforeEach
	void setUp() {
		myTree = new RedBlackTree<Integer>();
	}

	/**
	 * Test method for
	 * {@link atu.L00173495.RedBlackTreeClass.RedBlackTree#insert(java.lang.Comparable)}.
	 */
	@Test
	final void testInsert() {
		myTree.insert(10);
		assertEquals(10, myTree.root.getValue());
	}

	/**
	 * Test method for
	 * {@link atu.L00173495.RedBlackTreeClass.RedBlackTree#insertRec(atu.L00173495.RedBlackTreeClass.RedBlackTree.Node, atu.L00173495.RedBlackTreeClass.RedBlackTree.Node)}.
	 */
	@Test
	final void testInsertRec() {
		myTree.insert(10);
		myTree.insert(15);
		assertEquals(15, myTree.root.getRight().getValue());
		// Test testHandleRedBlack
		assertEquals(false, myTree.root.getnodeColourRed());
		assertEquals(true, myTree.root.getRight().getnodeColourRed());

	}

	/**
	 * Test method for
	 * {@link atu.L00173495.RedBlackTreeClass.RedBlackTree#rotateSubTreeLeft(atu.L00173495.RedBlackTreeClass.RedBlackTree.Node)}.
	 */
	@Test
	final void testRotateSubTreeLeft() {
		myTree.insert(100);
		myTree.insert(75);
		myTree.insert(125);
		myTree.insert(110);
		myTree.insert(150);

		Node rotatedSubTree = myTree.rotateSubTreeLeft(myTree.root.getRight());
		assertEquals(150, rotatedSubTree.getValue());
		assertEquals(125, rotatedSubTree.getLeft().getValue());
		assertEquals(110, rotatedSubTree.getLeft().getLeft().getValue());
	}

	/**
	 * Test method for
	 * {@link atu.L00173495.RedBlackTreeClass.RedBlackTree#rotateSubTreeRight(atu.L00173495.RedBlackTreeClass.RedBlackTree.Node)}.
	 */
	@Test
	final void testRotateSubTreeRight() {
		myTree.insert(200);
		myTree.insert(100);
		myTree.insert(250);
		myTree.insert(80);
		myTree.insert(120);

		Node rotatedSubTree = myTree.rotateSubTreeRight(myTree.root.getLeft());
		assertEquals(80, rotatedSubTree.getValue());
		assertEquals(100, rotatedSubTree.getRight().getValue());
		assertEquals(120, rotatedSubTree.getRight().getRight().getValue());
	}

	/**
	 * Test method for
	 * {@link atu.L00173495.RedBlackTreeClass.RedBlackTree#findMaximum()}.
	 */
	@Test
	final void testFindMaximum() {
		myTree.insert(6);
		myTree.insert(5);
		myTree.insert(4);
		myTree.insert(3);
		myTree.insert(2);
		myTree.insert(1);

		assertEquals(6, myTree.findMaximum());
	}

	/**
	 * Test method for
	 * {@link atu.L00173495.RedBlackTreeClass.RedBlackTree#findMinimum()}.
	 */
	@Test
	final void testFindMinimum() {
		myTree.insert(10);
		myTree.insert(11);
		myTree.insert(12);
		myTree.insert(13);
		myTree.insert(14);
		myTree.insert(15);
		myTree.insert(16);
		assertEquals(10, myTree.findMinimum());
	}

	/**
	 * Test method for
	 * {@link atu.L00173495.RedBlackTreeClass.RedBlackTree#find(java.lang.Comparable)}.
	 */
	@Test
	final void testFind() {
		myTree.insert(10);
		myTree.insert(11);
		myTree.insert(12);
		myTree.insert(13);
		myTree.insert(14);
		myTree.insert(15);
		myTree.insert(16);
		assertEquals(null, myTree.find(20));
		assertEquals(14, myTree.find(14));

	}

	/**
	 * Test method for
	 * {@link atu.L00173495.RedBlackTreeClass.RedBlackTree#countNodes()}.
	 */
	@Test
	final void testCountNodes() {
		myTree.insert(10);
		myTree.insert(11);
		myTree.insert(12);
		myTree.insert(13);
		myTree.insert(14);
		myTree.insert(15);
		myTree.insert(16);
		assertEquals(7, myTree.countNodes());
	}

}
