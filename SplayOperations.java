

public class SplayOperations extends Tree {
	
	
			private Node clockWiseRotation(Node parent) {
				Node child = parent.left;
				parent.left = child.right;
				child.right = parent;
				return child;
			}

			
			private Node counterClockWiseRotation(Node parent) {
				Node child = parent.right;
				parent.right = child.left;
				child.left = parent;
				return child;
			}
			
			public Node leftZigZig(Node node)
			{
				Node grparent,parent;
				grparent = node;
				parent = node.left;
				node = clockWiseRotation(grparent);
				node = clockWiseRotation(parent);
				return node;
			}
			
			public Node rightZigZig(Node node)
			{
				Node grparent,parent;
				grparent = node;
				parent = node.right;
				node = counterClockWiseRotation(grparent);
				node = counterClockWiseRotation(parent);
				return node;
			}
			
			public Node leftZigZag(Node node)
			{
				Node grparent,parent;
				grparent = node;
				parent = node.left; 
				grparent.left = counterClockWiseRotation(parent);
				node = clockWiseRotation(grparent);
				return node;
			}
			
			public Node rightZigZag(Node node)
			{
				Node grparent,parent;
				grparent = node;
				parent = node.right;
				grparent.right = clockWiseRotation(parent);
				node = counterClockWiseRotation(grparent);
				return node;
			}

}
