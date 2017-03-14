import java.io.File;
import java.io.FileInputStream;
import java.util.*;
public class Main {

	/**
	 * �����ʾ: - + x ���� ! @
	 * -�������Էţ�δ������
	 * +����Ѿ�����
	 * x���ǽ
	 * ���ִ������ǽ
	 * ���������Էţ�δ������
	 * @��ʾ���õ��ݵĵط�
	 *  1 - - - - - - - - 1
		- - - x - - - - - -
		- x - - - 2 - - x -
		- - - - - - - 1 - -
		- - - 4 - - - - - -
		- - - - - - 2 - - -
		- - 2 - - - - - - -
		- x - - 2 - - - x -
		- - - - - - 0 - - -
		1 - - - - - - - - 1
	 * 
	 */
	public static Result doSearch(char[][] chs, WallHeapMin wallQueue,
			BlankHeapMin blankNotPlaceQueue, int height, int width) {
		// TODO Auto-generated method stub
		System.gc();
		while(wallQueue.getSize()>0 || blankNotPlaceQueue.getSize()>0){
			//wallQueue
			int wallMin, blankNotPlaceMin;
			if(wallQueue.getSize() != 0){
				wallMin = wallQueue.getMin().uncertain;
				if(wallMin < 0){
					return new Result(false);
				}
			}else{
				wallMin = 10000;
			}
			if(blankNotPlaceQueue.getSize() != 0){
				blankNotPlaceMin = blankNotPlaceQueue.getMin().blankCount;
				if(blankNotPlaceMin < 1){
					return new Result(false);
				}
			}else{
				blankNotPlaceMin = 10000;
			}
			if(blankNotPlaceMin > wallMin + 1){
				//��wallQueue���д���
				NumberWall numberWall = wallQueue.removemin();
				switch(wallMin){
				case 0: 
					//����һ��chs
					char[][] newChs = copyChs(chs, height, width);
					BlankNode blankNode = numberWall.blankNode;
					while(blankNode != null){
						newChs = lightUp(newChs, height, width, blankNode.x, blankNode.y);
						//System.out.println();
						//printChs(newChs, height, width);
						blankNode = blankNode.next;
					}
					//System.out.println();
					//printChs(newChs, height, width);
					//����wallQueue,�� blankNotPlaceQueue,����һ�ݣ�Ȼ����еݹ鴦��
					WallHeapMin newWallQueue = wallQueue.copy();
					BlankHeapMin newBlankNotPlaceQueue = blankNotPlaceQueue.copy();
					//System.out.println("new copy ....");
					//newWallQueue.print();
					//newBlankNotPlaceQueue.print();
					//������ʧ�ܣ�ֱ��fasle
					if(!update(newChs, newWallQueue, newBlankNotPlaceQueue, height, width)){
						return new Result(false);
					}
//					System.out.println("��������*****");
//					newWallQueue.print();
//					newBlankNotPlaceQueue.print();
	
					//if doSearch() ,true�Ļ����£��������κδ���
					Result result = doSearch(newChs, newWallQueue, newBlankNotPlaceQueue, height, width);
					if(result.flag){
						//����chs, wallQueue, blankNotPlaceQueue
//						chs = result.chs;
//						wallQueue = result.wallQueue;
//						blankNotPlaceQueue = result.blankNotPlaceQueue;
						return result;
					}
					return new Result(false);
				case 1: 
					for(int i=0; i < numberWall.nodeCount; i++){
						int count = 0;
						char[][] newChs2 = copyChs(chs, height, width);
						//����wallQueue,�� numberQueue,����һ�ݣ�Ȼ����еݹ鴦��
						WallHeapMin newWallQueue2 = wallQueue.copy();
						BlankHeapMin newBlankNotPlaceQueue2 = blankNotPlaceQueue.copy();
						BlankNode blankNode1 = numberWall.blankNode;
						while(blankNode1 != null){
							if(count == i){
								newChs2[blankNode1.x][blankNode1.y] = '!';
								//��blankNode1.x, blankNode1.y���д���
								BlankNotPlace tempBlankNotPlace = new BlankNotPlace(blankNode1.x, blankNode1.y);
								updateNotPlace(tempBlankNotPlace, newChs2, height, width);
								newBlankNotPlaceQueue2.insert(tempBlankNotPlace);
							}else{
								newChs2 = lightUp(newChs2, height, width, blankNode1.x, blankNode1.y);
							}
							blankNode1 = blankNode1.next;
							count ++;
						}
//						if(newChs2[1][18] == '2' && newChs2[1][19] == '+' && newChs2[1][20] == '+' && newChs2[1][21] == '@' && newChs2[1][22] == '3' && newChs2[1][23] == '@')
//							System.out.println("�������Ҫ���Եĵط�");
						//System.out.println("����������");
						//printChs(newChs2, height, width);
						//����wallQueue,�� numberQueue,����һ�ݣ�Ȼ����еݹ鴦��
						if(!update(newChs2, newWallQueue2, newBlankNotPlaceQueue2, height, width)){
							continue;
						}
						//if doSearch() ,true�Ļ����£��������κδ���
						Result result2 = doSearch(newChs2, newWallQueue2, newBlankNotPlaceQueue2, height, width);
						if(result2.flag){
							//����chs, wallQueue, blankNotPlaceQueue
//							chs = result2.chs;
//							wallQueue = result2.wallQueue;
//							blankNotPlaceQueue = result2.blankNotPlaceQueue;
							return result2;
						}
					}
					return new Result(false);
				case 2: 
					for(int i=0; i < numberWall.nodeCount -1; i++){
						for(int j=i+1; j < numberWall.nodeCount; j++){
							int count = 0;
							char[][] newChs3 = copyChs(chs, height, width);
							//����wallQueue,�� numberQueue,����һ�ݣ�Ȼ����еݹ鴦��
							WallHeapMin newWallQueue3 = wallQueue.copy();
							BlankHeapMin newBlankNotPlaceQueue3 = blankNotPlaceQueue.copy();
							BlankNode blankNode2 = numberWall.blankNode;
							while (blankNode2 != null) {
								if(i == count || j == count){
									newChs3[blankNode2.x][blankNode2.y] = '!';
									//��blankNode1.x, blankNode1.y���д���
									BlankNotPlace tempBlankNotPlace = new BlankNotPlace(blankNode2.x, blankNode2.y);
									updateNotPlace(tempBlankNotPlace, newChs3, height, width);
									newBlankNotPlaceQueue3.insert(tempBlankNotPlace);
								}else{
									newChs3 = lightUp(newChs3, height, width, blankNode2.x, blankNode2.y);
								}
								blankNode2 = blankNode2.next;
								count ++;
							}
							//����wallQueue,�� numberQueue,����һ�ݣ�Ȼ����еݹ鴦��
//							System.out.println("С����������");
//							printChs(newChs3, height, width);
//							newWallQueue3.print();
//							System.out.println("��ӭС��");
//							newBlankNotPlaceQueue3.print();
							if(!update(newChs3, newWallQueue3, newBlankNotPlaceQueue3, height, width)){
								continue;
							}
//							System.out.println("Ҫ��Ҫ���۾�");
//							printChs(newChs3, height, width);
//							newWallQueue3.print();
//							System.out.println("�����丱�۾�����");
//							newBlankNotPlaceQueue3.print();
							//if doSearch() ,true�Ļ����£��������κδ���
							Result result3 = doSearch(newChs3, newWallQueue3, newBlankNotPlaceQueue3, height, width);
							if(result3.flag){
								//����chs, wallQueue, blankNotPlaceQueue
//								chs = result3.chs;
//								wallQueue = result3.wallQueue;
//								blankNotPlaceQueue = result3.blankNotPlaceQueue;
								return result3;
							}
						}
					}
					return new Result(false);
				case 3: 
					for(int i=0; i < numberWall.nodeCount; i++){
						int count = 0;
						char[][] newChs4 = copyChs(chs, height, width);
						//����wallQueue,�� numberQueue,����һ�ݣ�Ȼ����еݹ鴦��
						WallHeapMin newWallQueue4 = wallQueue.copy();
						BlankHeapMin newBlankNotPlaceQueue4 = blankNotPlaceQueue.copy();
						BlankNode blankNode3 = numberWall.blankNode;
						while(blankNode3 != null){
							if(count == i){
								newChs4 = lightUp(newChs4, height, width, blankNode3.x, blankNode3.y);
							}else{
								newChs4[blankNode3.x][blankNode3.y] = '!';
								//��blankNode1.x, blankNode1.y���д���
								BlankNotPlace tempBlankNotPlace = new BlankNotPlace(blankNode3.x, blankNode3.y);
								updateNotPlace(tempBlankNotPlace, newChs4, height, width);
								newBlankNotPlaceQueue4.insert(tempBlankNotPlace);
							}
							blankNode3 = blankNode3.next;
							count ++;
						}
						//����wallQueue,�� numberQueue,����һ�ݣ�Ȼ����еݹ鴦��
//						System.out.println("�����Լ�����");
//						printChs(newChs4, height, width);
//						newWallQueue4.print();
//						System.out.println("���Ŀ��Ŀ���");
//						newBlankNotPlaceQueue4.print();
						
						if(!update(newChs4, newWallQueue4, newBlankNotPlaceQueue4, height, width)){
							continue;
						}
//						System.out.println("������");
//						printChs(newChs4, height, width);
//						newWallQueue4.print();
//						System.out.println("���������ǿ���Ӣ�ĺ��ķ�");
//						newBlankNotPlaceQueue4.print();
						
						//if doSearch() ,true�Ļ����£��������κδ���
						Result result4 = doSearch(newChs4, newWallQueue4, newBlankNotPlaceQueue4, height, width);
						if(result4.flag){
							//����chs, wallQueue, blankNotPlaceQueue
//							chs = result4.chs;
//							wallQueue = result4.wallQueue;
//							blankNotPlaceQueue = result4.blankNotPlaceQueue;
							return result4;
						}
					}
					return new Result(false);
				}
				
				
			}else{
				//��blankNotPlaceQueue���д���,else
				BlankNotPlace blankNotPlace = blankNotPlaceQueue.removemin();
				for(int i=0; i < blankNotPlace.blankCount; i++){
					int count = 0; 
					BlankNode blankNode = blankNotPlace.blankNode;
					char[][] newChs = copyChs(chs, height, width);
					while(blankNode != null){
						if(i == count){
							newChs = lightUp(newChs, height, width, blankNode.x, blankNode.y);
							break;
						}
						blankNode = blankNode.next;
						count ++;
					}
//					System.out.println("Ц��������!������he");
//					printChs(newChs, height, width);
					//����wallQueue,�� numberQueue,����һ�ݣ�Ȼ����еݹ鴦��
					WallHeapMin newWallQueue = wallQueue.copy();
					BlankHeapMin newBlankNotPlaceQueue = blankNotPlaceQueue.copy();
//					newWallQueue.print();
//					newBlankNotPlaceQueue.print();
					if(!update(newChs, newWallQueue, newBlankNotPlaceQueue, height, width)){
						continue;
					}
//					System.out.println("Цupdate֮�������he");
//					printChs(newChs, height, width);
//					newWallQueue.print();
//					System.out.println("hello hello media");
//					newBlankNotPlaceQueue.print();
					
					
					Result rt = doSearch(newChs, newWallQueue, newBlankNotPlaceQueue, height, width);
					if(rt.flag){
						//����chs, wallQueue, blankNotPlaceQueue
//						chs = rt.chs;
//						wallQueue = rt.wallQueue;
//						blankNotPlaceQueue = rt.blankNotPlaceQueue;
						return rt;
					}
				}
				return new Result(false);
			}
		}
		return new Result(true, chs);
	}
	
	public static void updateNotPlace(BlankNotPlace blankNotPlace, char[][] chs, int height, int width){
		//ˮƽ����
		for(int index=blankNotPlace.y+1; index < width; index++){
			if(chs[blankNotPlace.x][index] == '-'){
				blankNotPlace.blankCount ++;
				BlankNode bn = new BlankNode(blankNotPlace.x, index);
				bn.next = blankNotPlace.blankNode;
				blankNotPlace.blankNode = bn;
			}else if(chs[blankNotPlace.x][index] == '!' || chs[blankNotPlace.x][index] == '+'){
				continue;
			}else{
				break;
			}
		}
		//ˮƽ����
		for(int index=blankNotPlace.y - 1; index >=0; index --){
			if(chs[blankNotPlace.x][index] == '-'){
				blankNotPlace.blankCount ++;
				BlankNode bn = new BlankNode(blankNotPlace.x, index);
				bn.next = blankNotPlace.blankNode;
				blankNotPlace.blankNode = bn;
			}else if(chs[blankNotPlace.x][index] == '!' || chs[blankNotPlace.x][index] == '+'){
				continue;
			}else{
				break;
			}
		}
		//��ֱ����
		for(int index=blankNotPlace.x+1; index < height; index++){
			if(chs[index][blankNotPlace.y] == '-'){
				blankNotPlace.blankCount ++;
				BlankNode bn = new BlankNode(index, blankNotPlace.y);
				bn.next = blankNotPlace.blankNode;
				blankNotPlace.blankNode = bn;
			}else if(chs[index][blankNotPlace.y] == '!' || chs[index][blankNotPlace.y] == '+'){
				continue;
			}else{
				break;
			}
		}
		//��ֱ����
		for(int index=blankNotPlace.x-1; index >= 0; index--){
			if(chs[index][blankNotPlace.y] == '-'){
				blankNotPlace.blankCount ++;
				BlankNode bn = new BlankNode(index, blankNotPlace.y);
				bn.next = blankNotPlace.blankNode;
				blankNotPlace.blankNode = bn;
			}else if(chs[index][blankNotPlace.y] == '!' || chs[index][blankNotPlace.y] == '+'){
				continue;
			}else{
				break;
			}
		}
	}
	
	public static boolean update(char[][] newChs, WallHeapMin newWallQueue, BlankHeapMin newBlankNotPlaceQueue, int height, int width){
		//����wallQueue
		boolean flag = false;
		NumberWall[] numberWalls = newWallQueue.getHeap();
		int numberWallsLen = newWallQueue.getSize();
//		System.out.println("��������������");
		//printChs(newChs, height, width);
		//���newWallQueue�������ٴ����²���
		newWallQueue.clean();
		for(int i=1; i <= numberWallsLen; i++){
			NumberWall numberWall2 = numberWalls[i];
			BlankNode bNode = numberWall2.blankNode;
//			System.out.println("��ʵ����ʵ��"+numberWall2.x+", "+numberWall2.y);
			
			BlankNode p = null;
			while(bNode != null){
				//System.out.println("��������  = " +bNode.x+" , "+bNode.y);
				if(newChs[bNode.x][bNode.y] == '@'){
//					System.out.println("��˹�ٷ�");
					//ɾ��bNode
					if(p == null){
						numberWall2.blankNode = bNode.next;
					}else{
						p.next = bNode.next;
					}
					numberWall2.nodeCount --;
					numberWall2.num --;
				}else if(newChs[bNode.x][bNode.y] == '+' || newChs[bNode.x][bNode.y] == '!'){
//					System.out.println("ż�������");
					//ɾ��bNode
					if(p == null){
						numberWall2.blankNode = bNode.next;
					}else{
						p.next = bNode.next;
					}
					//�ɷ�λ�ü�һ
					numberWall2.nodeCount --;
					numberWall2.uncertain --;
				}else{
					p = bNode;
				}
				bNode = bNode.next;
			}
			
			//����֮��
			//BlankNode qNode = numberWall2.blankNode;
//			System.out.println("����������"+numberWall2.x+", "+numberWall2.y);
			/*while(qNode != null){
				System.out.println("��籩��="+qNode.x+" , "+qNode.y);
				qNode = qNode.next;
			}*/
			//���numberwall2.num����0���ż�����ӣ�����
			if(numberWall2.num > 0){
				//System.out.println("�����ȷ�");
				newWallQueue.insert(numberWall2);
			}else if(numberWall2.num == 0){
				//���num -- ���� 0,��ô˵��ʣ��Ľڵ㶼�ǲ����Էŵ��ˣ�ʣ��Ϊ-�Ľڵ�Ҫ����Ϊ ��
				flag = true;
				BlankNode deleteNode = numberWall2.blankNode;
				//System.out.println("hello world!");
				while(deleteNode != null){
					newChs[deleteNode.x][deleteNode.y] = '!';
					BlankNotPlace tempBlankNotPlace = new BlankNotPlace(deleteNode.x, deleteNode.y);
					updateNotPlace(tempBlankNotPlace, newChs, height, width);
					newBlankNotPlaceQueue.insert(tempBlankNotPlace);
					deleteNode = deleteNode.next;
				}
				//System.out.println("��ӡ��������num = 0ʱ");
//				printChs(newChs, height, width);
			}else{ //.num < 0
				return false;
			}
		}
		//����blankNotPlaceQueue
		BlankNotPlace[] blankNotPlaces= newBlankNotPlaceQueue.getHeap();
		int blankNotPlacesLen = newBlankNotPlaceQueue.getSize();
		newBlankNotPlaceQueue.clean();
		for(int i=1; i <= blankNotPlacesLen; i++){
			BlankNotPlace blankNotPlace = blankNotPlaces[i];
			if(newChs[blankNotPlace.x][blankNotPlace.y] != '+'){
				BlankNode bNode = blankNotPlace.blankNode;
				BlankNode p = null;
				while(bNode != null){
					if(newChs[bNode.x][bNode.y] == '@' || newChs[bNode.x][bNode.y] == '+' || newChs[bNode.x][bNode.y] == '-'){
						//ɾ��bNode
						if(p == null){
							blankNotPlace.blankNode = bNode.next;
						}else{
							p.next = bNode.next;
						}
						blankNotPlace.blankCount --;
					}else{
						p = bNode;
					}
					bNode = bNode.next;
				}
				if(blankNotPlace.blankCount < 1){
					return false;
				}
				newBlankNotPlaceQueue.insert(blankNotPlace);
				
			}
		}
		if(flag){
			if(!update(newChs, newWallQueue, newBlankNotPlaceQueue, height, width)){
				return false;
			}
		}
		return true;
	}
	
	public static char[][] copyChs(char[][] chs, int height, int width){
		char[][] newChs = new char[height][width];
		for(int i=0; i < height; i++){
			for(int j=0; j < width; j++){
				newChs[i][j] = chs[i][j];
			}
		}
		return newChs;
	}
	
	public static char[][] lightUp(char[][] chs, int height, int width, int x, int y){
		//x��y�����ó�@���
		chs[x][y] = '@';
		//��ֱ����
		for(int i=x+1; i < height; i++){
			if(chs[i][y] == '-' || chs[i][y] == '!'){
				chs[i][y] = '+';
			}else if(chs[i][y] == '+'){
				continue;
			}else{
				break;
			}
		}
		//��ֱ����
		for(int i=x-1; i >= 0; i--){
			if(chs[i][y] == '-' || chs[i][y] == '!'){
				chs[i][y] = '+';
			}else if(chs[i][y] == '+'){
				continue;
			}else{
				break;
			}
		}
		//ˮƽ����
		for(int j=y+1; j < width; j++){
			if(chs[x][j] == '-' || chs[x][j] == '!'){
				chs[x][j] = '+';
			}else if(chs[x][j] == '+'){
				continue;
			}else{
				break;
			}
		}
		//ˮƽ����
		for(int j=y-1; j >= 0; j--){
			if(chs[x][j] == '-' || chs[x][j] == '!'){
				chs[x][j] = '+';
			}else if(chs[x][j] == '+'){
				continue;
			}else{
				break;
			}
		}
		return chs;
	}

	public static boolean isNum(char ch){
		if(ch <= '4' && ch > '0')
			return true;
		return false;
	}
	
	public static void printChs(char[][] chs, int height, int width){
		for(int i=0; i < height; i++){
			for(int j=0; j < width; j++){
				System.out.print(chs[i][j] + " ");
			}
			System.out.println();
		}
	}
	
	public static char[][] read(Scanner scanner,int height, int width){
		@SuppressWarnings("resource")
		//Scanner scanner = new Scanner(System.in);
		//int n = scanner.nextInt();
		char[][] chs = new char[height][width];
		for(int i=0; i < height; i++){
			for(int j=0; j < width; j++){
				int number = scanner.nextInt();
				switch(number){
				case 9: chs[i][j] = '-';break;
				case 5: chs[i][j] = 'x';break;
				default: chs[i][j] = (char)(number + 48);
				}
			}
		}
		return chs;
	}
	
	public static void main(String[] args) throws Exception {
		int height = 14;
		int width = 24;
		@SuppressWarnings("resource")
		FileInputStream fis = new FileInputStream(new File("data.txt"));
		long start = System.currentTimeMillis();
		Scanner scanner = new Scanner(new File("data1.txt"));
		//Scanner scanner = new Scanner(System.in);
		//height = width = scanner.nextInt();
		int[][] nums = new int[height][width];
		char[][] chs = read(scanner, height, width);
		/*
		char[][] chs = {
				//1   2   3   4   5   6   7   8   9   10  11  12  13 14  15  16  17  18   19  20  21  22  23 24  25  26  27   28  29  30 31  32  33  34  35  36
				{'-','-','2','-','-','-','-','-','-','-','-','-','-','-','x','-','-','-','-','-','-','-','-','-','-','-','1','-','-','-','-','-','-','-','-','-'},//1
				{'-','-','-','2','-','-','-','-','-','3','-','-','-','-','-','x','-','-','-','-','-','3','-','-','-','-','-','x','-','-','-','-','-','1','-','-'},//2
				{'x','-','-','-','2','-','-','-','3','-','-','-','x','-','-','-','x','-','-','-','1','-','-','-','2','-','-','-','2','-','-','-','1','-','-','-'},//3
				{'-','-','-','-','-','-','1','1','-','-','x','-','-','-','-','-','-','-','1','1','-','-','1','-','-','-','-','-','-','-','1','1','-','-','1','-'},//4
				{'-','x','-','-','2','1','-','-','-','-','-','-','-','x','-','-','2','1','-','-','-','-','-','-','-','1','-','-','1','1','-','-','-','-','-','-'},//5
				{'-','-','-','-','-','-','-','-','-','-','-','-','-','-','-','-','-','-','-','-','-','-','-','-','-','-','-','-','-','-','-','-','-','-','-','-'},//6
				{'-','-','-','-','-','-','-','-','-','-','-','-','-','-','-','-','-','-','-','-','-','-','-','-','-','-','-','-','-','-','-','-','-','-','-','-'},//7
				{'-','-','-','-','-','-','-','-','-','-','-','-','-','-','-','-','-','-','-','-','-','-','-','-','-','-','-','-','-','-','-','-','-','-','-','-'},//8
				{'-','-','-','-','-','-','-','-','-','-','-','-','-','-','-','-','-','-','-','-','-','-','-','-','-','-','-','-','-','-','-','-','-','-','-','-'},//9
				{'-','-','-','-','-','-','-','-','-','-','-','-','-','-','-','-','-','-','-','-','-','-','-','-','-','-','-','-','-','-','-','-','-','-','-','-'},//11
				{'-','-','-','-','-','-','-','-','-','-','-','-','-','-','-','-','-','-','-','-','-','-','-','-','-','-','-','-','-','-','-','-','-','-','-','-'},//12
				{'-','-','-','-','-','-','-','-','-','-','-','-','-','-','-','-','-','-','-','-','-','-','-','-','-','-','-','-','-','-','-','-','-','-','-','-'},//13
				{'-','-','-','-','-','-','-','-','-','-','-','-','-','-','-','-','-','-','-','-','-','-','-','-','-','-','-','-','-','-','-','-','-','-','-','-'},//14
				{'-','-','-','-','-','-','-','-','-','-','-','-','-','-','-','-','-','-','-','-','-','-','-','-','-','-','-','-','-','-','-','-','-','-','-','-'},//15
				{'-','-','-','-','-','-','-','-','-','-','-','-','-','-','-','-','-','-','-','-','-','-','-','-','-','-','-','-','-','-','-','-','-','-','-','-'},//16
				{'-','-','-','-','-','-','-','-','-','-','-','-','-','-','-','-','-','-','-','-','-','-','-','-','-','-','-','-','-','-','-','-','-','-','-','-'},//17
				{'-','-','-','-','-','-','-','-','-','-','-','-','-','-','-','-','-','-','-','-','-','-','-','-','-','-','-','-','-','-','-','-','-','-','-','-'},//18
				{'-','-','-','-','-','-','-','-','-','-','-','-','-','-','-','-','-','-','-','-','-','-','-','-','-','-','-','-','-','-','-','-','-','-','-','-'},//19
				{'-','-','-','-','-','-','-','-','-','-','-','-','-','-','-','-','-','-','-','-','-','-','-','-','-','-','-','-','-','-','-','-','-','-','-','-'}//20
		};
		/*
		char[][] chs = {
				{'-','-','-','-','-'},
				{'0','-','-','0','-'},
				{'-','-','0','-','0'},
				{'-','0','-','-','-'},
				{'x','-','-','0','-'},
				{'-','-','-','-','-'}
		};
		char[][] chs = {
				{'1','-','-','-','-','-','-','-','-','1'},
				{'-','-','-','x','-','-','-','-','-','-'},
				{'-','x','-','-','-','2','-','-','x','-'},
				{'-','-','-','-','-','-','-','1','-','-'},
				{'-','-','-','4','-','-','-','-','-','-'},
				{'-','-','-','-','-','-','2','-','-','-'},
				{'-','-','2','-','-','-','-','-','-','-'},
				{'-','x','-','-','2','-','-','-','x','-'},
				{'-','-','-','-','-','-','0','-','-','-'},
				{'1','-','-','-','-','-','-','-','-','1'}
		};
		
		char[][] chs = {
				{'-','-','-','-','-'},
				{'-','-','-','1','-'},
				{'-','-','1','-','-'},
				{'-','1','-','-','-'},
				{'x','-','-','-','-'}
		};
		
		char[][] chs = {
				{'-','-','-','-','-','1','-','-'},
				{'-','3','x','-','-','-','-','-'},
				{'-','-','-','-','-','-','0','-'},
				{'x','-','-','-','x','-','-','-'},
				{'-','-','-','4','-','-','-','0'},
				{'-','2','-','-','-','-','-','-'},
				{'-','-','-','-','-','1','x','-'},
				{'-','-','x','-','-','-','-','-'}
		};
		
		char[][] chs = {
				{'-','-','-','0'},
				{'-','-','0','-'},
				{'-','0','-','x'},
				{'x','-','x','-'}
		};
		char[][] chs = {
				//1   2   3   4   5   6   7   8   9   10  11  12  13 14  15  16  17  18
				{'-','-','-','-','x','-','-','x','-','-','x','-','-','-','-','-','-','-'},  //1
				{'-','2','-','-','1','-','-','-','-','-','-','-','-','1','-','x','0','-'},  //2
				{'-','x','-','-','-','0','-','-','-','-','-','-','1','-','-','-','-','-'},  //3
				{'-','-','-','-','-','-','-','-','0','-','-','0','-','-','-','-','-','0'},  //4
				{'-','-','-','1','-','-','-','-','-','-','1','-','-','-','-','0','-','-'},  //5
				{'-','-','0','-','-','-','-','1','-','-','-','-','-','-','1','-','-','-'},  //6
				{'0','-','-','-','-','-','1','-','-','0','-','-','-','-','-','-','-','-'},  //7
				{'-','-','-','-','-','1','-','-','-','-','-','-','1','-','-','-','1','-'},  //8
				{'-','1','0','-','x','-','-','-','-','-','-','-','-','x','-','-','0','-'},  //9
				{'-','-','-','-','-','-','-','x','-','-','1','-','-','0','-','-','-','-'}   //10
		};
		
		/*
		char[][] chs = {
				{'1', '-', '0', '-'},
				{'-', '-', '-', '-'},
				{'-', '-', '-', '-'},
				{'1', '-', '-', '-'}
		};
		*/
		
		//Ԥ����
		//����ǽ�����ȶ���
		WallHeapMin wallQueue = new WallHeapMin(1000);
		//������
		//HeapMin<BlankNotPlace> blankQueue = new HeapMin<BlankNotPlace>() 
		BlankHeapMin blankNotPlaceQueue = new BlankHeapMin(1000);
		
		//
		int wallLen =0, blankNotPlaceLen = 0;
		BlankNotPlace[] blankHeap = new BlankNotPlace[1000];
		NumberWall[] wallHeap = new NumberWall[1000];
		
		//Ԥ���?�õ�wallHeap,blankHeap,�����޸�0�Ա߶�Ӧ��4���ַ�Ϊ!
		for(int i=0; i < height; i++){
			for(int j=0; j < width; j++){
				if(chs[i][j] == '0'){
					//blankNotPlaceQueue���1
					//�����������������Ϊ��
					//��,i > 0,
					//!(isNum(chs[i-1][j])) && chs[i-1][j] != '0' && chs[i-1][j] != 'x'
					if(i>0 && chs[i-1][j] == '-'){
						chs[i-1][j] = '!';
						blankHeap[blankNotPlaceLen ++] = new BlankNotPlace(i-1, j);
					}
					//��
					if(i<height-1 && chs[i+1][j] == '-'){
						chs[i+1][j] = '!';
						blankHeap[blankNotPlaceLen ++] = new BlankNotPlace(i+1, j);
					}
					//��
					if(j>0 && chs[i][j-1] == '-'){
						chs[i][j-1] = '!';
						blankHeap[blankNotPlaceLen ++] = new BlankNotPlace(i, j-1);
					}
					//��
					if(j<width-1 && chs[i][j+1] == '-'){
						chs[i][j+1] = '!';
						blankHeap[blankNotPlaceLen ++] = new BlankNotPlace(i, j+1);
					}
				}
				if(isNum(chs[i][j])){
					//wallQueue���1
 					wallHeap[wallLen++] = new NumberWall(i, j, chs[i][j] - '0');
				}
			}
		}
		//printChs(chs, height, width);
		//�ȶ�blankNotQueue���д��?�洢���Դ�ŵĽڵ�
		for(int i=0; i < blankNotPlaceLen; i++){
			updateNotPlace(blankHeap[i], chs, height, width);
			blankNotPlaceQueue.insert(blankHeap[i]);
		}
		//��wallQueue���д���
		for(int i = 0; i < wallLen; i++){
			//��
			if(wallHeap[i].x > 0 && chs[wallHeap[i].x - 1][wallHeap[i].y] == '-'){
				wallHeap[i].nodeCount ++;
				BlankNode bn = new BlankNode(wallHeap[i].x - 1, wallHeap[i].y);
				bn.next = wallHeap[i].blankNode;
				wallHeap[i].blankNode = bn;
			}
			//��
			if(wallHeap[i].x < height -1 && chs[wallHeap[i].x + 1][wallHeap[i].y] == '-'){
				wallHeap[i].nodeCount ++;
				BlankNode bn = new BlankNode(wallHeap[i].x + 1, wallHeap[i].y);
				bn.next = wallHeap[i].blankNode;
				wallHeap[i].blankNode = bn;
			}
			//��
			if(wallHeap[i].y > 0 && chs[wallHeap[i].x][wallHeap[i].y - 1] == '-'){
				wallHeap[i].nodeCount ++;
				BlankNode bn = new BlankNode(wallHeap[i].x, wallHeap[i].y - 1);
				bn.next = wallHeap[i].blankNode;
				wallHeap[i].blankNode = bn;
			}
			//��
			if(wallHeap[i].y < width - 1 && chs[wallHeap[i].x][wallHeap[i].y + 1] == '-'){
				wallHeap[i].nodeCount ++;
				BlankNode bn = new BlankNode(wallHeap[i].x, wallHeap[i].y + 1);
				bn.next = wallHeap[i].blankNode;
				wallHeap[i].blankNode = bn;
			}
			wallHeap[i].uncertain = wallHeap[i].nodeCount - wallHeap[i].num;
			wallQueue.insert(wallHeap[i]);
		}
		//��ӡ����
		//wallQueue.print();
		//blankNotPlaceQueue.print();
		char[][] newChs = copyChs(chs, height, width);
		//doSearch()����ִ��������⹤��
		System.out.println("the original akari puzzle is :");
		printChs(newChs, height, width);
		Result rs = doSearch(newChs, wallQueue, blankNotPlaceQueue, height, width);
		if(rs.flag){
			//forѭ��������
			//System.out.println("after search ..........");
			newChs = rs.chs;
			if(rs.chs == null){
				//System.out.println("null");
			}
			//printChs(rs.chs, height, width);
			//System.out.println("the solution akari puzzle is:");
			for(int i=0; i < height; i++){
				for(int j=0; j < width; j++){
					if(newChs[i][j] == '@'){
						//System.out.print("("+i+", "+j+")  ");
						nums[i][j] = 1;
						if(j == width -1)
							System.out.print(nums[i][j]);
						else
							System.out.print(nums[i][j] + " ");
					}else if(newChs[i][j] == '-'){
						newChs = lightUp(newChs, height, width, i, j);
						//System.out.print("("+i+", "+j+")  ");
						nums[i][j] = 1;
						if(j == width -1)
							System.out.print(nums[i][j]);
						else
							System.out.print(nums[i][j] + " ");
					}else{
						if(j == width -1)
							System.out.print(nums[i][j]);
						else
							System.out.print(nums[i][j] + " ");
					}
				}
				if(i < height-1){
					System.out.println();
				}
			}
			/*
			for(int i=0; i < height; i++){
				for(int j=0; j < width; j++){
					System.out.print(nums[i][j] +"\t");
				}
				System.out.println();
			}
			*/
			
			System.out.println();
			printChs(newChs, height, width);
			//
		}else{
			System.out.println("Sorry, there is not solution~");
		}
		long end = System.currentTimeMillis();
		System.out.println("run time = " + (end - start) +" ms");
	}


	
}

