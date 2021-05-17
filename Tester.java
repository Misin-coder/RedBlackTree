package redBlackTree;

public class Tester {

	public static void main(String[] args) {
		RedBlackTree<Integer, String> tree = new RedBlackTree<>();
		tree.add(50,"");
        tree.add(30, "");
        tree.add(20, "");
        tree.add(40, "");
        tree.add(70, "");
        tree.add(60, "");
        tree.add(80, "");
        tree.inOrder();
		
		
	}

}
