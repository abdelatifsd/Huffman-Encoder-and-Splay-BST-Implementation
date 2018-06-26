import java.io.File;
import java.io.IOException;
import java.util.Scanner;



public class Huffman {
	
	// VARIABLES
	
	private int ASCII = 256;
	public CustomMap<Character,String> table = new CustomMap<Character,String>();
	
	
	// MAIN METHODS
	
	public void compress(String text)
	{
		char[] chars = text.toCharArray();
		int[] frequency = freqCounter(chars);
		HuffmanNode root = buildHuffmanTree(frequency);
		buildCode(table,root,"");	
	}
	
	public void encodeUserInput(String input)
	{
		String output = "";
		for(char ch: input.toCharArray()) {
			output+= table.get(ch);
		}
		
		System.out.print(output);
	}
	
	// HELPER METHODS
	
	
	private int[] freqCounter(char[] chars)
	{
		int[] freq = new int[ASCII];
		for(int i = 0; i < chars.length;i++)
		{
			freq[chars[i]]++;
		}
		return freq;
	}
	

	private void buildCode(CustomMap<Character,String> m, HuffmanNode node, String s) {
        if (!node.isLeaf()) {
            buildCode(m,(HuffmanNode)node.left,  s + '0');
            buildCode(m,(HuffmanNode)node.right, s + '1');
        }
        else {
        	
        	m.put(node.character, s);
        }
    }
	
	private HuffmanNode buildHuffmanTree(int[] freq)
	{
		PriorityQueue pq = new PriorityQueue();
		
		for (char i = 0; i < ASCII; i++)
            if (freq[i] > 0)
                pq.insert(new HuffmanNode(i, freq[i], null, null));
		
		
        
        while (pq.size > 1) {
        	HuffmanNode left  = pq.remove();
            HuffmanNode right = pq.remove();
            HuffmanNode parent = new HuffmanNode('\0', left.data + right.data, left, right);
            pq.insert(parent);
        }
        return pq.remove();
		
	}	

	
	// INNER CLASSES
	
	private class PriorityQueue  {

		// VARIABLES
		
		private HuffmanNode[] pq;
	    private int maxsize;
	    public int size;

	    // CONSTURCTOR METHODS
	    
	    public PriorityQueue(int max) 
	    {
		maxsize = max;
		pq = new HuffmanNode[maxsize];
		size = 0 ;
	    }
	    
	    public PriorityQueue() 
	    {
	    	pq = new HuffmanNode[200];
			size = 0 ;
	    }
	    
	    // HELPER METHODS

	    private int leftchild(int i) {
		return 2*i;
	    }
	    private int rightchild(int i) {
		return 2*i + 1;
	    }

	    private int parent(int i) {
		return  (i / 2);
	    }

	    private boolean isleaf(int i) {
		return ((i > size/2) && (i <= size));
	    }

	    private void swap(int pos1, int pos2) {
	    	
	    	HuffmanNode tmp;
		
		tmp = pq[pos1];
		pq[pos1] = pq[pos2];
		pq[pos2] = tmp;
	    }
	    
	    
	    // MAIN METHODS
	    
	    private void insert(HuffmanNode node) {
	    	size++;
	    	pq[size] = node;
	    	int current = size;

	    	while (pq[current] != null &&  pq[parent(current)] != null && pq[current].data < pq[parent(current)].data)  {
	    	    swap(current, parent(current));
	    	    current = parent(current);
	    	}
	        }
	    
	    
	    private HuffmanNode remove() {
	    	swap(1,size);
	    	size--;
	    	if (size != 0)
	    		minHeapify(1);
	    	return pq[size+1];
	        }
	    
	    
	    private void minHeapify(int position) {
	    	
	    	int smallestchild;
	    	
	    	while (!isleaf(position)) {
	    		
	    	    smallestchild = leftchild(position);
	    	    
	    	    if ((smallestchild < size) && (pq[smallestchild].data > pq[smallestchild+1].data))
	    	    	
	    		smallestchild = smallestchild + 1;
	    	    
	    	    if (pq[position].data <= pq[smallestchild].data) return;
	    	    
	    	    swap(position,smallestchild);
	    	    
	    	    position = smallestchild;
	    		}
	   }
	    
	        
	} // End of Priority Queue
	
	
	public class CustomMap<K, V> {
		
		// VARIABLES
		
	    private int size;
	    private int ASCII_CAP = ASCII;
	    
	    @SuppressWarnings("unchecked")
	    private MapNode<K, V>[] nodes = new MapNode[ASCII_CAP];

	    // MAIN METHODS
	    
	    public V get(K key) {
	        for (int i = 0; i < size; i++) {
	            if (nodes[i] != null) {
	                if (nodes[i].key.equals(key)) {
	                    return nodes[i].value;
	                }
	            }
	        }
	        return null;
	    }

	    public void put(K key, V value) {
	        boolean insert = true;
	        for (int i = 0; i < size; i++) {
	            if (nodes[i].key.equals(key)) {
	            	nodes[i].value = value;
	                insert = false;
	            }
	        }
	        if (insert) {
	        	nodes[size++] = new MapNode<K, V>(key, value);
	        }
	    }

	    public void display(){  
	    	int counter = 0;
		for(int i=0;i<256;i++){
		    if(nodes[i]!=null){
		        	 counter++;
		        	 MapNode<K, V> entry=nodes[i];
		         System.out.print("{"+entry.key+"="+entry.value+"}" +" ");
		         if(counter%5==0)
		             System.out.println();
		           }
		       }   
		}
	    
	    // INNER CLASS

	    private class MapNode<K, V> {
	        private K key;
	        private V value;

	        public MapNode(K key, V value) {
	            this.key = key;
	            this.value = value;
	        }  
	    }
	}
	

	private class HuffmanNode extends Tree.Node{

		char character;
		
		public HuffmanNode(char character,int frequency, HuffmanNode l, HuffmanNode r) {
			super(frequency, l, r);
			this.character = character;
			
		}
		
	}	
	
	public static void main(String[] args) throws IOException
	{
		
		String f = args[0];
		String text = "";
		
		File temp = new File(f);
		Scanner sc = new Scanner(temp);
		
		
		
		while(sc.hasNextLine()) {
			 text = text +  sc.nextLine();
		}
		
		
		Huffman HT = new Huffman();
		System.out.println("----------------------------------");
		System.out.println("Huffman encoding for input file:\n");
		HT.compress(text);
		
		HT.table.display();
		
		System.out.println();
		System.out.println("----------------------------------"); 
		
		while(true) {
			
			
			System.out.println("Enter the text you'd like to compress (To exit, enter an empty space) : ");
		
			
			Scanner sc2 = new Scanner(System.in);
			String userInput = sc2.nextLine();
			if(userInput.equals(" "))
			{
				break;
			}
			
			HT.encodeUserInput(userInput);
			System.out.println();
			
			}
		
	}
}