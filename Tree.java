public  class Tree {

	protected Node root;
	
	public static class Node {
		
		int data;
		Node left;
		Node right;
	
		
		protected Node(int d)
		{
			data = d;
		}
		
		protected Node(int d , Node l, Node r)
		{
			data = d;
			left = l;
			right = r;
		}
		
		
		protected boolean isLeaf()
		{
			return this.left == null && this.right == null;
		}
		
	}
	
	
}
