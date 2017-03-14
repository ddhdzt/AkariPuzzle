
public class BlankNotPlace{
	public int blankCount=0;//�ж���blankλ�ÿ��Դ����
	public BlankNode blankNode = null;//�����洢���������ø��ӵĿɷ��ø���
	public int x;           //��������
	public int y;           //��������
	public BlankNotPlace(int blankCount, BlankNode blankNode) {
		super();
		this.blankCount = blankCount;
		this.blankNode = blankNode;
	}
	public BlankNotPlace() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public BlankNotPlace(int x, int y) {
		super();
		this.x = x;
		this.y = y;
	}
	
	public BlankNotPlace copy(){
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
		return new BlankNotPlace(blankCount, newBlankNode, x, y);
	}
	public BlankNotPlace(int blankCount, BlankNode blankNode, int x, int y) {
		super();
		this.blankCount = blankCount;
		this.blankNode = blankNode;
		this.x = x;
		this.y = y;
	}
	public int getBlankCount() {
		return blankCount;
	}
	public void setBlankCount(int blankCount) {
		this.blankCount = blankCount;
	}
	public BlankNode getBlankNode() {
		return blankNode;
	}
	public void setBlankNode(BlankNode blankNode) {
		this.blankNode = blankNode;
	}
}
