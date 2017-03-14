
public class BlankNode {
	public BlankNode next = null;
	public int x;
	public int y;
	
	public BlankNode(int x, int y) {
		super();
		this.x = x;
		this.y = y;
	}
	public BlankNode() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public BlankNode copy(){
		return new BlankNode(x, y);
	}
}
