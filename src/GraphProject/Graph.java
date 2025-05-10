package GraphProject;

import java.util.Random;
import enigma.console.Console;

public class Graph {
	public int numVertices;
	public char[][] board;
	public int[][] adjBoard;
	public char[] vertices;
	public int[][] matrix ;  
	Vertex[] vertex;

	int[] degrees;
	int penalty;

	Random rand = new Random();

	public Graph() {
		this.adjBoard = null;
		this.board = null;
	}

	public Graph(int numVertices, int[][] matrix) {
		this.numVertices = numVertices;
		vertices = new char[numVertices];
		board = new char[25][37];
		adjBoard = new int[25][37];
		this.matrix  = new int[numVertices][numVertices]; 
		this.vertex = new Vertex[numVertices];


		for(int i=0; i<numVertices;i++) {
			for(int j=0; j<numVertices;j++) {
				this.matrix[i][j]=matrix[i][j];
			}
		}
		
		generateBoard();
		placeRandom(numVertices);
		printGraph();
		print();
		calculatePenalty();
		

	}public Graph(int numVertices, int[][] matrix , Vertex[] vertex) {
		this.numVertices = numVertices;
		vertices = new char[numVertices];
		board = new char[25][37];
		adjBoard = new int[25][37];
		this.matrix  = new int[numVertices][numVertices];  
		this.vertex = new Vertex[numVertices];
		
		for(int i=0; i<matrix.length;i++) {
			for(int j=0; j<matrix.length;j++) {
				this.matrix[i][j]=matrix[i][j];
			}
		}
		
		for(int i=0; i<vertex.length;i++) {
			this.vertex[i]=vertex[i];
		}

		generateBoard();
		printGraph();
		print();
		calculatePenalty();

	}
	public void generateBoard() {
		for (int i = 0; i < 25; i++) {
			for (int j = 0; j < 37; j++) {
				board[i][j] = ' ';
			}
		}

		for (int i = 0; i < 25; i++) {
			for (int j = 0; j < 37; j++) {
				if (i % 4 == 0 && j % 4 == 0) {
					board[i][j] = '.';
				}
			}
		}
		for (int i = 0; i < 25; i++) {
			for (int j = 0; j < 37; j++) {
				adjBoard[i][j] = 0;
			}
		}
	}
	public void addLetterToBoard() {
		for (int i = 0; i < vertex.length; i++) {
			board[vertex[i].x][vertex[i].y]=vertex[i].name;
		}
	}

	public void print() {
		for (int i = 0; i < 25; i++) {
			for (int j = 0; j < 37; j++) {

				if (adjBoard[i][j] != 0 && (board[i][j] == ' ' || board[i][j] == '.')) {
					char ch = (char) (adjBoard[i][j] + '0');
					board[i][j] = ch;
				}

			}
		}
	}

	public void printEnigma(Console cn , int a ) {
		cn.getTextWindow().setCursorPosition(0, 0);
	      for (int i = 0; i < 25; i++) {
	         for (int j = 0; j < 37; j++) {
	           	char ch = (char) board[i][j];  
	         	cn.getTextWindow().output(a +j, i , ch);

	          }
	       }
	}

	public void placeRandom(int numVertices) {

		int count = 0;

		while (count < numVertices) {
			
			int x = rand.nextInt(7) * 4;
			int y = rand.nextInt(10) * 4;
			if (board[x][y] == '.') {
				char letter = (char) ('A' + count);
				vertices[count] = letter;
				vertex[count] = new Vertex(x, y, letter);
				count++;
				board[x][y]=letter;
			}

		}
	}

	

	public void printGraph() {
		for (int i = 0; i < numVertices; i++) {
			if(vertex[i].name!=0) {
				board[vertex[i].x][vertex[i].y]=vertex[i].name;
			}
		}

		for (int i = 0; i < numVertices; i++) {
			for (int j = i + 1; j < numVertices; j++) {
				if (matrix[i][j] == 1) {
					if(vertex[i].name!=0) {
					printEdge(vertex[i].x, vertex[i].y ,vertex[j].x ,vertex[j].y );
					}
				}
			}
		}

	}

	public void printEdge(int letter1X, int letter1Y,int letter2X ,int letter2Y) {
	
	
		int startX = letter1X;
		int startY = letter1Y;
		int endX = letter2X;
		int endY = letter2Y;
		boolean firstMove = true;

		while (letter1X != letter2X && letter1Y != letter2Y) {
			if (letter1X < letter2X && letter1Y > letter2Y) {
				letter1X++;
				letter1Y--;
			} else if (letter1X > letter2X && letter1Y > letter2Y) {
				letter1X--;
				letter1Y--;
			} else if (letter1X < letter2X && letter1Y < letter2Y) {
				letter1X++;
				letter1Y++;
			} else if (letter1X > letter2X && letter1Y < letter2Y) {
				letter1X--;
				letter1Y++;
			}
			if (!(letter1X == startX && letter1Y == startY) && !(letter1X == endX && letter1Y == endY)) {
				adjBoard[letter1X][letter1Y]++;
			}
		}

		int lastX = letter1X;
		int lastY = letter1Y;

		while (letter1Y != letter2Y) {
			if (letter1Y > letter2Y) {
				letter1Y--;
			} else {
				letter1Y++;
			}
			if (!(letter1X == startX && letter1Y == startY) && !(letter1X == endX && letter1Y == endY)
					&& !(letter1X == lastX && letter1Y == lastY)) {
				adjBoard[letter1X][letter1Y]++;
			}

		}

		while (letter1X != letter2X) {
			if (letter1X > letter2X) {
				letter1X--;
			} else {
				letter1X++;
			}
			if (!(letter1X == startX && letter1Y == startY) && !(letter1X == endX && letter1Y == endY)
					&& !(letter1X == lastX && letter1Y == lastY)) {
				adjBoard[letter1X][letter1Y]++;
			}

		}
	}

	public void calculatePenalty() {
		penalty = 0;
		for (int i = 0; i < 25; i++) {
			for (int j = 0; j < 37; j++) {
				if (adjBoard[i][j] != 0) {
					for (int k = 0; k < vertices.length; k++) {
						if (board[i][j] == vertices[k]) {
							penalty += adjBoard[i][j] * 1000;
						}
					}

				}
				if (adjBoard[i][j] != 0 && adjBoard[i][j] != 1) {

					int a = adjBoard[i][j] - 1;

					penalty += a;
					for (int k = 0; k < vertices.length; k++) {
						if (board[i][j] == vertices[k]) {
							penalty -= a;
						}
					}
				}
			}
		}

	}

	public boolean isEmpty() {

		if (adjBoard == null || board == null) {
			return true;
		}
		return false;
	}
}