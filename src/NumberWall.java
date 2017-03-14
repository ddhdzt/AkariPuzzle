
public class NumberWall{
	public int num; //旁边需要安放多少盏灯
	public int uncertain; //-1,直接终止，0，表示完全确定，1表示右3盏灯不确定
	public int nodeCount = 0;
	public BlankNode blankNode = null;
	public int x;
	public int y;

	
	@Override
	public String toString() {
		return "NumberWall [num=" + num + ", uncertain=" + uncertain
				+ ", nodeCount=" + nodeCount + ", blankNode=" + blankNode
				+ ", x=" + x + ", y=" + y + "]";
	}



	public NumberWall() {
		super();
		// TODO Auto-generated constructor stub
	}

	public NumberWall(int x, int y, int num) {
		super();
		this.x = x;
		this.y = y;
		this.num = num;
	}
	
	

	public NumberWall(int num, int uncertain, int nodeCount,
			BlankNode blankNode, int x, int y) {
		super();
		this.num = num;
		this.uncertain = uncertain;
		this.nodeCount = nodeCount;
		this.blankNode = blankNode;
		this.x = x;
		this.y = y;
	}

	public NumberWall(int num, int uncertain, int nodeCount, BlankNode blankNode) {
		super();
		this.num = num;
		this.uncertain = uncertain;
		this.nodeCount = nodeCount;
		this.blankNode = blankNode;
	}
	
	public NumberWall copy(){
		BlankNode newBlankNode = null;
		BlankNode p = blankNode;
		while(p != null){
			BlankNode q = p.copy();
			if(newBlankNode == null){
				newBlankNode = q;
			}else{
				q.next = newBlankNode.next;
				newBlankNode.next = q;
			}
			p = p.next;
		}
		return new NumberWall(num, uncertain, nodeCount, newBlankNode, x, y);
	}


	public int getNum() {
		return num;
	}


	public void setNum(int num) {
		this.num = num;
	}


	public int getUncertain() {
		return uncertain;
	}

	public void setUncertain(int uncertain) {
		this.uncertain = uncertain;
	}

	public int getNodeCount() {
		return nodeCount;
	}

	public void setNodeCount(int nodeCount) {
		this.nodeCount = nodeCount;
	}

	public BlankNode getBlankNode() {
		return blankNode;
	}

	public void setBlankNode(BlankNode blankNode) {
		this.blankNode = blankNode;
	}
	
}
