

public class WallHeapMin{
	private NumberWall[] Heap;
	private int maxsize;
	private int size;


	public WallHeapMin(int max) {
		maxsize = max;
		Heap = new NumberWall[maxsize];
		size = 0;
		Heap[0] = new NumberWall();
		Heap[0].uncertain = -5;
	}


	public WallHeapMin(NumberWall[] heap, int maxsize, int size) {
		super();
		this.Heap = heap;
		this.maxsize = maxsize;
		this.size = size;
	}


	private int leftchild(int pos) {
		return 2 * pos;
	}


	private int rightchild(int pos) {
		return 2 * pos + 1;
	}


	private int parent(int pos) {
		return pos / 2;
	}


	private boolean isleaf(int pos) {
		return ((pos > size / 2) && (pos <= size));
	}


	private void swap(int pos1, int pos2) {
		NumberWall tmp;
		tmp = Heap[pos1];
		Heap[pos1] = Heap[pos2];
		Heap[pos2] = tmp;
	}
	
	public int getSize(){
		return size;
	}
	
	public NumberWall[] getHeap(){
		return Heap;
	}
	
	public WallHeapMin copy(){
		NumberWall[] newNumberWalls = new NumberWall[maxsize];
		for(int i=0; i <= size; i++){
			newNumberWalls[i] = Heap[i].copy();
		}
		return new WallHeapMin(newNumberWalls, maxsize, size);
	}

	public void insert(NumberWall elem) {
		size++;
		Heap[size] = elem;
		int current = size;
		while (Heap[current].uncertain < Heap[parent(current)].uncertain) {
			swap(current, parent(current));
			current = parent(current);
		}
	}
	
	public void clean(){
		size = 0;
	}

	public void print() {
		int i;
		for (i = 1; i <= size; i++)
			System.out.println(Heap[i].x + " " + Heap[i].y + " : " + Heap[i].nodeCount + ", " + Heap[i].num + ", "+Heap[i].uncertain);
		System.out.println();
	}

	public NumberWall getMin(){
		return Heap[1];
	}

	public NumberWall removemin() {
		swap(1, size);
		size--;
		if (size != 0)
			pushdown(1);
		return Heap[size + 1];
	}


	private void pushdown(int position) {
		int smallestchild;
		while (!isleaf(position)) {
			smallestchild = leftchild(position);
			if ((smallestchild < size)
					&& (Heap[smallestchild].uncertain == Heap[smallestchild + 1].uncertain))
				smallestchild = smallestchild + 1;
			if (Heap[position].uncertain <= Heap[smallestchild].uncertain)
				return;
			swap(position, smallestchild);
			position = smallestchild;
		}
	}

}
