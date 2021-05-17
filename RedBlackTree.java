package redBlackTree;

public class RedBlackTree<K, V> {

	Node<K, V> root;
	int size;

	private class Node<K, V> {

		K key;
		V value;
		Node<K, V> left, right, parent;
		boolean black, isLeftChild;

		public Node(K key, V value) {
			this.key = key;
			this.value = value;
			left = right = parent = null;
			black = isLeftChild = false;
		}

	}

	public RedBlackTree() {
		root = null;
		size = 0;
	}

	public void add(K key, V value) {
		Node<K, V> newNode = new Node<K, V>(key, value);

		if (root == null) {
			root = newNode;
			root.black = true;
			size++;
			return;
		}
		add(root, newNode);
		size++;
		checkColor(newNode);
	}

	private void add(Node<K, V> parent, Node<K, V> newNode) {

		if (((Comparable<K>) newNode.key).compareTo(parent.key) > 0) {
			// go to right
			if (parent.right == null) {
				parent.right = newNode;
				newNode.parent = parent;
				newNode.isLeftChild = false;
				newNode.black = false;
				return;
			}
			add(parent.right, newNode);
		} else {
			if (parent.left == null) {
				parent.left = newNode;
				newNode.parent = parent;
				newNode.isLeftChild = true;
				newNode.black = false;
				return;
			}
			add(parent.left, newNode);
		}

	}

	private void checkColor(Node<K, V> node) {

		if (node == root)
			return;
		if (!node.black && !node.parent.black) {
			correctTree(node);
		}
		checkColor(node.parent);

	}

	private void correctTree(Node<K, V> node) {

		if (node.parent.isLeftChild) {
			// aunt is rightChild of grandparent;
			if (node.parent.parent.right == null) {
				rotate(node);
			} else {
				if (node.parent.parent.right.black)
					rotate(node);
				if (!node.parent.parent.right.black) {
					// color flip
					node.parent.black = true;
					if (node.parent.parent != root) {
						node.parent.parent.black = false;
					}
					node.parent.parent.right.black = true;
				}
			}

		} else {
			// aunt is to left
			if (node.parent.parent.left == null) {
				rotate(node);
			} else {
				if (node.parent.parent.left.black)
					rotate(node);
				if (!node.parent.parent.left.black) {
					// color flip
					node.parent.black = true;
					if (node.parent.parent != root) {
						node.parent.parent.black = false;
					}
					node.parent.parent.left.black = true;
				}
			}

		}
	}

	private void rotate(Node<K, V> node) {

		if (node.isLeftChild) {
			if (node.parent.isLeftChild) {
				rightRotate(node.parent.parent);
				// rememember to change color after rotate
				node.black = false;
				node.parent.black = true;
				node.parent.right.black = false;
			} else {
				rightLeftRotate(node.parent.parent);
				node.black = true;
				node.left.black = false;
				node.right.black = false;
			}
		} else {
			if (node.parent.isLeftChild) {
				leftRightRotate(node.parent.parent);
				node.black = true;
				node.left.black = false;
				node.right.black = false;
			} else {
				leftRotate(node.parent.parent);
				node.black = false;
				node.parent.black = true;
				node.parent.left.black = false;
			}
		}

	}

	private void rightRotate(Node<K, V> node) {
		Node<K, V> temp = node.left;
		node.left = temp.right;

		if (temp.right != null) {
			temp.right.parent = node;
			temp.right.isLeftChild = true;
		}

		temp.parent = node.parent;

		if (node.parent == null) {
			root = temp;
		} else if (node.isLeftChild) {
			node.parent.left = temp;
			temp.isLeftChild = true;
		} else {
			node.parent.right = temp;
			temp.isLeftChild = false;
		}

		temp.right = node;
		node.parent = temp;
		node.isLeftChild = false;

	}

	private void rightLeftRotate(Node<K, V> node) {
		rightRotate(node.right);
		leftRotate(node);

	}

	private void leftRightRotate(Node<K, V> node) {
		leftRotate(node.left);
		rightRotate(node);

	}

	private void leftRotate(Node<K, V> node) {

		Node<K, V> temp = node.right;
		node.right = temp.left;

		if (temp.left != null) {
			temp.left.parent = node;
			temp.left.isLeftChild = false;
		}

		temp.parent = node.parent;

		if (node.parent == null) {
			root = temp;
		} else if (node.isLeftChild) {
			node.parent.left = temp;
			temp.isLeftChild = true;
		} else {
			node.parent.right = temp;
			temp.isLeftChild = false;
		}

		temp.left = node;
		node.parent = temp;
		node.isLeftChild = true;

	}

	public int blackNodes(Node<K, V> node) throws WrongRedBlackTreeException {
		if (node == null) {
			return 1;
		}

		int leftBlackNode = blackNodes(node.left);
		int rightBlackNode = blackNodes(node.right);

		if (leftBlackNode != rightBlackNode) {
			throw new WrongRedBlackTreeException();
		}
		if (node.black) {
			rightBlackNode++;
		}
		return rightBlackNode;
	}
	
	public void inOrder() {
		inOrder(root);
	}

	private void inOrder(Node<K, V> node) {
		if(node == null) {
			return;
		}
		
		inOrder(node.left);
		System.out.println(node.key);
		inOrder(node.right);
		
	}

}
