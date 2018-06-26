
import java.io.File;
import java.io.IOException;
import java.util.Scanner;


public class SplayTree extends SplayOperations implements BinarySearchTree{
	
	
	int zig_zig_counter = 0;
	int zig_zag_counter = 0;
	int comparisons = 0;

	public SplayTree() {
		root = null; 
	}

		// MAIN METHODS
	
		public void add(int data) {
				addNewNode(data);
				splay(data);
			}
		
		public boolean find(int data) {
			if (root == null)
			{
				return false;
			}
			splay(data);
			if (root.data == data) {
				return true;
			} else {
				return false;
			}
		}
			
		public void remove(int data) {
			if (root != null) {
				splay(data);
				if (root != null && root.data == data) {
					if (root.left != null) {
						Node tmp = root.right;
						root = root.left;
						splay(data);
						root.right = tmp;
					} else
						root = root.right;
					return;
				}
			}
		}
		
		public void postOrderTraversal(Node node)
		{
			if(node !=null)
			{
				postOrderTraversal(node.left);
				postOrderTraversal(node.right);
				System.out.print(node.data  + " ");
			}
		}
		
		// HELPER METHODS
		
		private void splay(int data) {
			
			boolean operation = true; 
			Node node = root;
			Node grandgrandparent = null; 

			while (true) {
				
				if (node == null || data == node.data)
				{
					break;
				// LEFT-LEFT * LEFT-RIGHT OPERATIONS
				}else if (node.left != null && data < node.data) {

					if (data == node.left.data) {
						comparisons++;
						return;
					}else if (node.left.left != null && data == node.left.left.data) {
						node = leftZigZig(node);
						comparisons++;
						zig_zig_counter++;
						operation = true;
					}else if (node.left.right != null && data == node.left.right.data) {
						node = leftZigZag(node);
						zig_zag_counter++;
						operation = true;
					}else if (data < node.data) {
						comparisons++;
						grandgrandparent = node; 
						node = node.left;
						
					} // RIGHT-RIGHT * RIGHT-LEFT OPERATIONS
				}else if (node.right != null && data > node.data) {
					
					if (data == node.right.data) {
						comparisons++;
						return;
					}else if (node.right.right != null && data == node.right.right.data) {
						node = rightZigZig(node);
						zig_zig_counter++;
						operation = true;
					}else if (node.right.left != null && data == node.right.left.data) {
						node = rightZigZag(node);
						zig_zag_counter++;
						operation = true;
					} else if (data > node.data) {
						comparisons++;
						grandgrandparent = node;
						node = node.right;
					}
				}else if ((node.left == null && data < node.data)|| (node.right == null && data > node.data)) {
					
					data = node.data;
					node = root;
					grandgrandparent = null;
				}

				if (operation && grandgrandparent != null) {
					
					if (node.data < grandgrandparent.data)
					{
						comparisons++;
						grandgrandparent.left = node;
					}else if (node.data > grandgrandparent.data)
					{
						comparisons++;
						grandgrandparent.right = node;
					}
					node = root;
					grandgrandparent = null;
					operation = false;
				}
			}
			
			root = node;
		}

		
		
		// HELPER METHODS
		
		private void addNewNode(int data)
		{
			if (root == null) {
				root = new Node(data);
			}
			else {
				Node t = root;
				Node parent = null;
				while(t!= null)
				{
					parent = t;
					if(data < t.data)
					{
						t = t.left;
						if(t == null)
						{
							parent.left = new Node(data);
						}
					}else {
						t = t.right;
						if(t == null)
						{
							parent.right = new Node(data);
						}
					}
				}
			}
		}
		
		public static void main(String[] args) throws IOException
		{
			
			
			
			int trace_value = Integer.parseInt(args[1]);//Integer.parseInt(args[1]);
	  		int i=0;
	  		
	    	 
	    	 	String filename = args[0] ;
	    	 	File file = new File(filename);
	    	 	Scanner sc = new Scanner(file);
	 		
	 		SplayTree ST = new SplayTree();
	 		
	 		
			
			while(sc.hasNextLine() != false)
			{
				
				String text = sc.nextLine();
				int n;
				
				switch(text.charAt(0)) {
				
				
				case 'a':
					text = text.substring(1);
				    n = Integer.parseInt(text);
					ST.add(n);
					break;
					
				case 'r':
					text = text.substring(1);
					 n = Integer.parseInt(text);
					if(ST.find(n)) 
					{
						ST.remove(n);
					}
					else
						System.out.println("Operation remove: The value " + n + " doesn't exist.");
					break;
				case 'f':
					text = text.substring(1);
				    n = Integer.parseInt(text);
					if(ST.find(n)) 
					{
						System.out.println("Operation find: The value " + n + " exists.");
					}else
						System.out.println("Operation find: The value " + n + " doesn't exist.");
					break;
				
				}
				
				i++;
				
				if(i == trace_value)
				{
					break;
				}
				
			}
			
			System.out.println("----------------------------");
	 		
	 		System.out.print("Traversal = ");
	 		
	 		ST.postOrderTraversal(ST.root);
	 		
	 		System.out.println();
	 		
	 		System.out.println(ST.comparisons + " compares"); 
	 		
	 		System.out.println( ST.zig_zig_counter + " Zig Zigs");
	 		
	 		System.out.println(ST.zig_zag_counter +" Zig Zags" );
	 		
	     }
	}