

public class BlankHeapMin{
	private BlankNotPlace[] Heap;
	private int maxsize;
	private int size;


	public BlankHeapMin(int max) {
		maxsize = max;
		Heap = new BlankNotPlace[maxsize];
		size = 0;
		Heap[0] = new BlankNotPlace();
		Heap[0].blankCount = -1;
	}
	
	

	public BlankHeapMin(BlankNotPlace[] heap, int maxsize, int size) {
		super();
		Heap = heap;
		this.maxsize = maxsize;
		this.size = size;
	}

	public void clean(){
		size = 0;
	}


	public BlankHeapMin copy(){
		BlankNotPlace[] newHeap = new BlankNotPlace[maxsize];
		for(int i=0; i <= size; i++){
			newHeap[i] = Heap[i].copy();
		}
		return new BlankHeapMin(newHeap, maxsize, size);
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
		BlankNotPlace tmp;
		tmp = Heap[pos1];
		Heap[pos1] = Heap[pos2];
		Heap[pos2] = tmp;
	}
	
	public int getSize(){
		return size;
	}
	
	public BlankNotPlace[] getHeap(){
		return Heap;
	}
	
	public BlankNotPlace getMin(){
		return Heap[1];
	}

	public void insert(BlankNotPlace elem) {
		size++;
		Heap[size] = elem;
		int current = size;
		while (Heap[current].blankCount < Heap[parent(current)].blankCount) {
			swap(current, parent(current));
			current = parent(current);
		}
	}


	public void print() {
		int i;
		for (i = 1; i <= size; i++)
			System.out.println(Heap[i].x + " " + Heap[i].y +" : " + Heap[i].blankCount);
		System.out.println();
	}


	public BlankNotPlace removemin() {
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
					&& (Heap[smallestchild].blankCount == Heap[smallestchild + 1].blankCount))
				smallestchild = smallestchild + 1;
			if (Heap[position].blankCount <= Heap[smallestchild].blankCount)
				return;
			swap(position, smallestchild);
			position = smallestchild;
		}
	}

}
