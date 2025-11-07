package atu.L00173495.RedBlackTreeTester;

import atu.L00173495.RedBlackTreeClass.RedBlackTree;

public class RedBlackTreeConsoleTester {

	// LAB 4
	public static void main(String[] args) {
		// Basic Test for Black Uncle Right-Right case
		RedBlackTree<Integer> myTree = new RedBlackTree<Integer>();
		// Right Right Case
		myTree.insert(10);
		myTree.insert(11);
		myTree.insert(12);
		myTree.insert(13);
		myTree.insert(14);
		myTree.insert(15);
		myTree.insert(16);

//   Left Left 
//	myTree.insert(6);
//	myTree.insert(5);
//	myTree.insert(4);
//	myTree.insert(3);
//	myTree.insert(2);
//	myTree.insert(1);

//   Left Right 
//	myTree.insert(10);
//	myTree.insert(5);
//	myTree.insert(7);

//   Right Left
//	myTree.insert(10);
//	myTree.insert(15);
//	myTree.insert(12);

		
		myTree.preOrderTraversal();
	}
}