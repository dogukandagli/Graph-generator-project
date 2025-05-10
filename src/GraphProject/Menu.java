package GraphProject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;
import java.util.Scanner;

import enigma.console.Console;

public class Menu {

	public int vertexCount;
	public int[] degreeSequence;
	public int[] degreeSequenceCopy;
	public int[] degreeSequenceCopy2;
	public int[][] adjMatrix;
	public int[][] secadjMatrix;
	Console cn;
	public Random rand = new Random();
	public boolean textIsEmpty = true;
	public int trial=0;
	public int trial2=0;
	public Graph main_graph;
	public Graph secondary_graph;
	public Graph[] depot_graphs = new Graph[9];
	public int bestScore;
	public int currentScore;

	public void clear() {
		for (int i = 0; i < cn.getTextWindow().getRows(); i++) {
			cn.getTextWindow().setCursorPosition(0, i);
			cn.getTextWindow().output(                   
					"                                                                                                                                                                        ");

		}
		cn.getTextWindow().setCursorPosition(0, 0);
	}

	public void vertexAndDegree() {

		Random rand = new Random();
		System.out.print("Enter vertex count: ");
		Scanner sc = new Scanner(System.in);
		vertexCount = sc.nextInt();
		sc.nextLine();

		System.out.print("Press 1 to enter degree sequence, 2 to enter degree interval: ");
		int option = sc.nextInt();
		switch (option) {
		case 1:
			System.out.print("Enter degree sequence (put spaces between): ");
			degreeSequence = new int[vertexCount];
			degreeSequenceCopy = new int[vertexCount];
			for (int i = 0; i < vertexCount; i++) {
				int input = sc.nextInt();
				degreeSequence[i] = input;
				degreeSequenceCopy[i] = input;
			}
			break;
		case 2:

			boolean flag = true;
			while (flag) {
				System.out.print("Enter minimum degree: ");
				int min = sc.nextInt();
				System.out.print("Enter maximum degree: ");
				int max = sc.nextInt();
				degreeSequence = new int[vertexCount];
				degreeSequenceCopy = new int[vertexCount];
				degreeSequenceCopy2 = new int[vertexCount];
				for (int i = 0; i < vertexCount; i++) {
					int a = rand.nextInt(max - min + 1) + min;
					degreeSequence[i] = a;
					degreeSequenceCopy[i] = a;
					degreeSequenceCopy2[i] = a;
				}
				  if (canItBeGraph(degreeSequenceCopy) && isSimpleGraph(degreeSequenceCopy2)) {
				        flag = false;
				    }
			}
			break;
		default:
			break;
		}
	}

	public boolean canItBeGraph(int[] d) {
		int sum = 0;

		for (int j : d) {
			sum += j;
		}
		return sum % 2 == 0;
	}

	public boolean isSimpleGraph(int[] d) {
		int temp;

		int[] degrees = new int[d.length];
		for (int i = 0; i < d.length; i++) {
			degrees[i] = d[i];
		}

		if (degrees.length < 2) {
			return false;
		}

		if (allZero(degrees)) {
			return false;
		}
		if (lowerThanZero(degrees)) {
			return false;
		} else {
			while (true) {
				bubbleSort(degrees);

				temp = degrees[0];
				degrees[0] = 0;
				if (temp >= degrees.length) {
					return false;
				}

				for (int i = 1; i <= temp; i++) {
					degrees[i]--;
				}
				if (allZero(degrees)) {
					return true;
				}
				if (lowerThanZero(degrees)) {
					return false;
				}
			}

		}

	}

	public boolean allZero(int[] d) {
		for (int degree : d) {
			if (degree != 0) {
				return false;
			}
		}
		return true;
	}

	public boolean lowerThanZero(int[] d) {
		for (int degree : d) {
			if (degree < 0) {
				return true;
			}
		}
		return false;
	}

	public void bubbleSort(int[] d) {
	    boolean swapped;
	    for (int i = 0; i < d.length - 1; i++) {
	        swapped = false;
	        for (int j = 0; j < d.length - 1 - i; j++) {
	            if (d[j] < d[j + 1]) {
	                int temp = d[j];
	                d[j] = d[j + 1];
	                d[j + 1] = temp;
	                swapped = true;
	            }
	        }
	        if (!swapped) {
	            break;
	        }
	    }
	}

	public void graphMatrix(int v, int[] d) {
	    boolean flag = false;

	    while (!flag) {
	        adjMatrix = new int[v][v];
	        int[] currentDegree = new int[v];
	        boolean failed = false;

	        for (int i = 0; i < v; i++) {
	            int attempt = 0;

	            while (currentDegree[i] < d[i]) {
	                if (attempt > v * v) {
	                    failed = true;
	                    break;
	                }
	                int j = rand.nextInt(v);

	                if (i != j && adjMatrix[i][j] == 0 && currentDegree[j] < d[j]) {
	                    adjMatrix[i][j] = adjMatrix[j][i] = 1;
	                    currentDegree[i]++;
	                    currentDegree[j]++;
	                }
	                attempt++;
	            }

	            if (failed==true) {
	                break;
	            }
	        }

	        flag = true;
	        for (int i = 0; i < v; i++) {
	            if (currentDegree[i] != d[i]) {
	            	flag = false;
	                break;
	            }
	        }
	    }
	}


	public void printAdjMatrix(int[][] matrix, int x, int y) {
		cn.getTextWindow().setCursorPosition(x, y);
		cn.getTextWindow().output("  ");
		for (int i = 0; i < matrix.length; i++) {
			char letter = (char) ('A' + i);
			cn.getTextWindow().output(letter + " ");
		}
		for (int i = 0; i < matrix.length; i++) {
			int toplam = 0;
			char letter = (char) ('A' + i);
			cn.getTextWindow().setCursorPosition(x, y + 1 + i);
			cn.getTextWindow().output(letter);
			cn.getTextWindow().setCursorPosition(x + 2, y + 1 + i);
			for (int j = 0; j < matrix.length; j++) {

				toplam += matrix[i][j];
				System.out.print(matrix[i][j] + " ");

			}
			cn.getTextWindow().setCursorPosition(x + 2 + 2 * matrix.length + 1, y + 1 + i);
			cn.getTextWindow().output(String.valueOf(toplam));
		}

	}
	public void printAdjMatrix(int[][] matrix, String permutation, int x, int y) {
	    cn.getTextWindow().setCursorPosition(x, y);
	    cn.getTextWindow().output("  ");
	    
	   
	    String[] permNumbers = permutation.split(" ");

	    for (int i = 0; i < matrix.length; i++) {
	        char newLetter = (char) ('A' + (Integer.parseInt(permNumbers[i]) - 1)); 
	        cn.getTextWindow().output(newLetter + " ");
	    }

	    for (int i = 0; i < matrix.length; i++) {
	        int toplam = 0;
	        char newLetter = (char) ('A' + (Integer.parseInt(permNumbers[i]) - 1)); 

	        cn.getTextWindow().setCursorPosition(x, y + 1 + i);
	        cn.getTextWindow().output(String.valueOf(newLetter));

	        cn.getTextWindow().setCursorPosition(x + 2, y + 1 + i);
	        for (int j = 0; j < matrix.length; j++) {
	            int rowIndex = Integer.parseInt(permNumbers[i]) - 1;  
	            int colIndex = Integer.parseInt(permNumbers[j]) - 1; 

	            cn.getTextWindow().output(matrix[rowIndex][colIndex] + " ");
	            toplam += matrix[rowIndex][colIndex];
	        }

	        cn.getTextWindow().setCursorPosition(x + 2 + 2 * matrix.length + 1, y + 1 + i);
	        cn.getTextWindow().output(String.valueOf(toplam));
	    }
	}


	public void generate() {
		

		clear();
		cn.getTextWindow().setCursorPosition(0, 0);
		vertexAndDegree();
		if (canItBeGraph(degreeSequenceCopy)) {

			if (isSimpleGraph(degreeSequenceCopy)) {
				graphMatrix(vertexCount, degreeSequence);
				if (adjMatrix != null) {
					main_graph = new Graph(vertexCount, adjMatrix);
					bestScore=main_graph.penalty;
					currentScore=main_graph.penalty;
					clear();
					main_graph.printEnigma(cn, 0);
					cn.getTextWindow().setCursorPosition(39, 4 + vertexCount);
					System.out.print("Penalty : ");
					main_graph.calculatePenalty();
					System.out.println(main_graph.penalty);
					cn.getTextWindow().setCursorPosition(39, 5 + vertexCount);
					System.out.println("Drawing mode: 0");
					printAdjMatrix(main_graph.matrix, 40, 1);
					cn.getTextWindow().setCursorPosition(0, 28);
					System.out.println("==Press H if you want to improve== ");
					cn.getTextWindow().setCursorPosition(0, 29);
					System.out.println("Press Q to go MENU");
					cn.getTextWindow().setCursorPosition(39, 17);
					System.out.println("C pen:" + currentScore);
					cn.getTextWindow().setCursorPosition(39, 18);
					System.out.println("BC pen:" + bestScore);
					cn.getTextWindow().setCursorPosition(39, 20);
					System.out.println("---Try:" + trial);
					

				} else {
					System.out.println("could not be generated.");
					System.out.println();
					System.out.println("Press Z to try again");
				}
			} else {
				System.out.println("Not simple");
			}

		} else {
			System.out.println("Can be genereated ");
		}

	}

	public boolean completeGraph() {
		for (int i = 0; i < vertexCount; i++) {
			int toplam = 0;
			for (int j = 0; j < vertexCount; j++) {
				toplam += adjMatrix[i][j];
			}
			if (toplam != vertexCount - 1) {
				return false;
			}
		}
		return true;
	}

	public String isConnected(int[][] matrix) {
		for (int i = 0; i < matrix[0].length; i++) {
			Boolean connected_flag = false;
			for (int j = 0; j < matrix[1].length; j++) {
				if(matrix[i][j] == 1) {
					connected_flag = true;
				}     
			}
			if(connected_flag == false) {
				return "No";
			}
		}
		return "Yes";
	}
	
	public void containsC3() {
		String result2 = "";
		for (int i = 0; i < vertexCount; i++) {
			for (int j = i + 1; j < vertexCount; j++) {
				if (adjMatrix[i][j] == 1) {
					for (int k = j + 1; k < vertexCount; k++)
						if (adjMatrix[j][k] == 1 && adjMatrix[k][i] == 1) {
							result2 += Character.toString((char) ('A' + i));
							result2 += ",";
							result2 += Character.toString((char) ('A' + j));
							result2 += ",";
							result2 += Character.toString((char) ('A' + k));
							result2 += ",";
						}
				}
			}

		}

	}
	private  int findnoncenter(int[][] adjMatrix, int centerNode) {
        for (int i = 0; i < adjMatrix.length; i++) {
            if (i != centerNode) return i; // return noncenter node
        }
        return -1;
    }

    private  void dfs(int[][] matrix, boolean[] visited, int centernode, int current, int parent) {
        visited[current] = true;
        for (int neighbor = 0; neighbor < matrix.length; neighbor++) {
            if (neighbor == centernode) continue; // pass center node
            if (matrix[current][neighbor] == 1 && neighbor != parent) { // neighbour control
                if (!visited[neighbor]) {
                    dfs(matrix, visited, centernode, neighbor, current);
                }
            }
        }
    }

    public   void wheelgraph(int[][] adjMatrix, int vertexCount) {
        int n = adjMatrix.length;
        int centernode = -1;
        boolean flag = true;

        if (n < 4) {
            flag = false;
        }

        // find center node
        for (int i = 0; i < n; i++) {
            int degree = 0;
            for (int j = 0; j < n; j++) {
                degree += adjMatrix[i][j];
            }
            if (degree == n - 1) {
                if (centernode != -1) {
                    flag = false; // more than center node case
                }
                centernode = i;
            }
        }

        // no center case
        if (centernode == -1) {
            flag = false;
        }

        // other nodes degree control
        for (int i = 0; i < n; i++) {
            if (i == centernode) continue;
            int degree = 0;
            for (int j = 0; j < n; j++) {
                degree += adjMatrix[i][j];
            }
            if (degree != 3) { // other degrees should be 3
                flag = false;
            }
        }

        /* bu kısım işi bozuyo gibi geldi command içine aldım ama yine de silmeyelim bi sorun çıkarsa tekrardan içeri alır
        deneriz şimdilik çıkmadı ama
        for (int i = 0; i < n; i++) {
            if (i == centernode) continue;
            for (int j = i + 1; j < n; j++) {
                if (j == centernode) continue;
                if (adjMatrix[i][j] == 1) {
                    flag = false;
                }
            }
        }*/

        //(DFS)
        boolean[] visited = new boolean[n];
        dfs(adjMatrix, visited, centernode, findnoncenter(adjMatrix, centernode), -1);

        for (int i = 0; i < n; i++) {
            if (i != centernode && !visited[i]) {
                flag = false;
            }
        }

        //  is wheel
        if (!flag) {
            System.out.print("No.");
        } else {
            System.out.print("Yes. Center: " + centernode);
        }
    }

	public void displayIsomorphism() {
		 cn.getTextWindow().setCursorPosition(39, 5 + vertexCount);
	        System.out.println("Drawing mode: 0");

	    
		boolean flag = false;
		clear();
		String permVaraible="";
		main_graph.printEnigma(cn, 0);

		secondary_graph.printEnigma(cn, 50+6*main_graph.numVertices);

		int perm = factorial(main_graph.matrix.length);
		String[] permutations = new String[perm];

		for (int i = 0; i < perm; i++) {
			int count = 0;
			int fakeI = i;

			int[] digitArr = new int[main_graph.matrix.length];

			for (int j = main_graph.matrix.length - 1; j > 0; j--) {
				int digit = fakeI / factorial(j);
				fakeI = fakeI - digit * factorial(j);
				digitArr[count] = digit;
				count++;
			}
			digitArr[main_graph.matrix.length - 1] = 0;

			int[] facBase = new int[main_graph.matrix.length];

			for (int k = 0; k < main_graph.matrix.length; k++) {
				facBase[k] = k + 1;
			}

			int[] permDigit = new int[main_graph.matrix.length];
			int[] tempFacBase = facBase.clone();

			int count3 = 0;
			for (int k = 0; k < digitArr.length; k++) {

				int digit = digitArr[k];
				permDigit[count3] = tempFacBase[digit];
				int a = tempFacBase[digit];

				tempFacBase = removeElement(tempFacBase, a);
				count3++;
			}

			StringBuilder sb = new StringBuilder();
			for (int j = 0; j < permDigit.length;j++) {
			    sb.append(permDigit[j]);
			    if (j < permDigit.length - 1) {
			        sb.append(" ");  			    }
			}
			permutations[i] = sb.toString();
		}
		
		

	
		for (int i = 0; i < permutations.length; i++) {
			trial2=0;
			trial2++;
			int[][] alteredMatrix = new int[main_graph.matrix.length][main_graph.matrix.length];
			String[] permNumbers = permutations[i].split(" ");
			for (int j = 0; j < permNumbers.length; j++) {
				 int a = Integer.parseInt(permNumbers[j]) - 1; 

				for (int k = j + 1; k < permNumbers.length; k++) {
					 int b = Integer.parseInt(permNumbers[k]) - 1;

					alteredMatrix[j][k] = main_graph.matrix[a][b];
					alteredMatrix[k][j] = alteredMatrix[j][k];
					
					permVaraible=permutations[i];
					
				}
			}

			if (areMatricesEqual(alteredMatrix, secondary_graph.matrix)) {
				flag = true;
				cn.getTextWindow().setCursorPosition(40, 20+main_graph.numVertices);
				cn.getTextWindow().output(" Isomorphic");
				printAdjMatrix(main_graph.matrix, 40, 1);
				printAdjMatrix(alteredMatrix,permVaraible, 40, 9+main_graph.numVertices);
				printAdjMatrix(secondary_graph.matrix, 56+main_graph.numVertices, 9+main_graph.numVertices);
				break;
			}
			
		}

		if (flag == false) {

			cn.getTextWindow().setCursorPosition(40, 20+main_graph.numVertices);
			cn.getTextWindow().output("Not Isomorphic");
			printAdjMatrix(main_graph.matrix, 40, 1);
			printAdjMatrix(secondary_graph.matrix, 50+main_graph.numVertices, 9+main_graph.numVertices);

		} 
		
	
			

	}


	public int factorial(int fac) {
		int faktör = 1;
		for (int i = fac; i > 0; i--) {
			faktör *= i;
		}
		return faktör;
	}

	public boolean areMatricesEqual(int[][] matrix1, int[][] matrix2) {
		if (matrix1.length != matrix2.length || matrix1[0].length != matrix2[0].length) {
			return false;
		}

		for (int i = 0; i < matrix1.length; i++) {
			for (int j = 0; j < matrix1[i].length; j++) {
				if (matrix1[i][j] != matrix2[i][j]) {
					return false;
				}
			}
		}

		return true;
	}

	public int[] removeElement(int[] array, int element) {
		int[] newArray = new int[array.length - 1];
		int index = 0;
		for (int i = 0; i < array.length; i++) {
			if (array[i] != element) {
				newArray[index++] = array[i];
			}
		}
		return newArray;
	}

	public void TestMenu() {
		clear();

		main_graph.printEnigma(cn, 0);
		cn.getTextWindow().setCursorPosition(39, 6 + main_graph.numVertices);
		System.out.println("---Try:" + trial);
		cn.getTextWindow().setCursorPosition(39, 3 + main_graph.numVertices);
		System.out.println("C pen:" + currentScore);
		cn.getTextWindow().setCursorPosition(39, 4 + main_graph.numVertices);
		System.out.println("BC pen:" + bestScore);
		printAdjMatrix(main_graph.matrix, 40, 1);

		cn.getTextWindow().setCursorPosition(39, 10 + main_graph.numVertices);
		System.out.println("1. Connected? -------> " + isConnected(main_graph.matrix));

		cn.getTextWindow().setCursorPosition(39, 11 + main_graph.numVertices);
		System.out.println("2. Contains C3?");

		String result2 = "";
		for (int i = 0; i < main_graph.numVertices; i++) {
			for (int j = i + 1; j < main_graph.numVertices; j++) {
				if (main_graph.matrix[i][j] == 1) {
					for (int k = j + 1; k < main_graph.numVertices; k++)
						if (main_graph.matrix[j][k] == 1 && main_graph.matrix[k][i] == 1) {
							result2 += Character.toString((char) ('A' + i));
							result2 += ",";
							result2 += Character.toString((char) ('A' + j));
							result2 += ",";
							result2 += Character.toString((char) ('A' + k));
							result2 += ",";

							result2 += "   /   ";

						}
				}
			}

		}
		cn.getTextWindow().setCursorPosition(60, 11 + main_graph.numVertices);
		if (result2 != "") {
			System.out.println("------->   " + result2);
		} else {
			System.out.println("------->   None");
		}
		cn.getTextWindow().setCursorPosition(39, 12 + main_graph.numVertices);
		System.out.println("3.Isolated vertices?");
		String result = "";
		for (int i = 0; i < main_graph.numVertices; i++) {
			int count = main_graph.numVertices;
			for (int j = 0; j < main_graph.numVertices; j++) {
				if (main_graph.matrix[i][j] == 0)
					count--;
				if (count == 0) {
					result += Character.toString((char) ('A' + i));
					result += ",";
				}
			}
		}
		cn.getTextWindow().setCursorPosition(60, 12 + main_graph.numVertices);
		if (result != "") {
			System.out.println("------->   " + result);
		} else {
			System.out.println("------->   None");
		}
		cn.getTextWindow().setCursorPosition(39, 13 + main_graph.numVertices);
		System.out.println("4.Complete graph (Kn)?");
		cn.getTextWindow().setCursorPosition(60, 13 + main_graph.numVertices);
		System.out.println("------->   " + completeGraph());
		cn.getTextWindow().setCursorPosition(39, 14 + main_graph.numVertices);
		System.out.println("5. Bipartite?");
		cn.getTextWindow().setCursorPosition(60, 14 + main_graph.numVertices);
		System.out.print("------->   " + isBipartite(main_graph.matrix,main_graph.numVertices));
		cn.getTextWindow().setCursorPosition(39, 15 + main_graph.numVertices);
		System.out.println("6. Complete bipartite (Km,n)? -----> " + isCompleteBipartite(main_graph.matrix,main_graph.numVertices));
		cn.getTextWindow().setCursorPosition(39, 16 + main_graph.numVertices);
		System.out.println("7.Cycle graph (Cn)?");
		cn.getTextWindow().setCursorPosition(60, 16 + main_graph.numVertices);
		System.out.print("------->   " + isCycleGraph(main_graph.matrix,main_graph.numVertices));
		cn.getTextWindow().setCursorPosition(39, 17 + main_graph.numVertices);
		System.out.println("8. Wheel graph (Wn)?");
		cn.getTextWindow().setCursorPosition(60, 17 + main_graph.numVertices);
		System.out.print("------->   " );
		wheelgraph(main_graph.matrix,main_graph.numVertices);
		cn.getTextWindow().setCursorPosition(39, 18 + main_graph.numVertices);
		System.out.println("9. Star graph (Sn)?");
		cn.getTextWindow().setCursorPosition(60, 18 + main_graph.numVertices);
		System.out.print("------->   "  +isStarGraph(main_graph.matrix,main_graph.numVertices) );
		cn.getTextWindow().setCursorPosition(39, 19 + main_graph.numVertices);
		System.out.println("10. Isomorphic? (main graph and secondary graph)  (Key: V ) ");

		printRN();

		cn.getTextWindow().setCursorPosition(0, 29);
		System.out.println("Press Q to go MENU");

	}
	public String isBipartite(int[][] matrix, int n) {
		boolean flag = true;
		int[] vertexColor = new int[n];
		char[] v1 = new char[n];
		char[] v2 = new char[n];

		vertexColor[0] = 1;
		for(int i = 0; i < n; i++){
			for(int j = 0; j < n; j++){
				if(matrix[i][j] == 1 && vertexColor[j] == 0){
					if(vertexColor[i] == 1){
						vertexColor[j] = 2;
					}
					else if(vertexColor[i] == 2){
						vertexColor[j] = 1;
					}
					else flag = false;
				}
				if(matrix[i][j] == 1 && (vertexColor[j] == 1 || vertexColor[j] == 2)){
					if(vertexColor[i] == 1 && vertexColor[j] == 1){
						flag = false;
					}
					if(vertexColor[i] == 2 && vertexColor[j] == 2){
						flag = false;
					}
				}
			}
		}
		int v1j = 0;
		int v2j = 0;
		for(int i = 0; i < n; i++) {
			if(vertexColor[i] == 1) {
				v1[v1j] = (char) (65 + i);
				v1j++;
			}
			if(vertexColor[i] == 2) {
				v2[v2j] = (char) (65 + i);
				v2j++;
			}
		}

		String ifTrue = "V1={";
		for(int i = 0; i < v1j; i++){
			ifTrue += v1[i];
			if(i != v1j-1) ifTrue += ",";
		}
		ifTrue += "} V2={";
		for(int i = 0; i < v2j; i++){
			ifTrue += v2[i];
			if(i != v2j-1) ifTrue += ",";
		}
		ifTrue += "}";

		if(flag) return "Yes, " + ifTrue;
		else return "No";
    }

	public String isCompleteBipartite(int[][] matrix, int vertexCount) {
	    String result = null;
	    boolean[] visited = new boolean[vertexCount];

	    if (isBipartite(matrix, vertexCount).equals("No")) {
	        return result = "No. ";
	    } else {
	        int[] vertexColor = new int[vertexCount];
	        int[] set1 = new int[vertexCount];
	        int[] set2 = new int[vertexCount];

	        for (int i = 0; i < vertexCount; i++) {
	            if (!dfsForColoring(matrix, vertexCount, vertexColor, i, 1, visited) && !visited[i]) {
	                return "No. ";
	            }
	        }

	        int s1Counter = 0, s2Counter = 0;
	        for (int i = 0; i < vertexCount; i++) {
	            if (vertexColor[i] == 1) {
	                set1[s1Counter++] = i;
	            } else if (vertexColor[i] == 2) {
	                set2[s2Counter++] = i;
	            }
	        }

	        int edgeCounter = 0;
	        for (int i = 0; i < s1Counter; i++) {
	            for (int j = 0; j < s2Counter; j++) {
	                if (matrix[set1[i]][set2[j]] == 1) {
	                    edgeCounter++;
	                }
	            }
	        }

	        if (edgeCounter == s1Counter * s2Counter) {
	            result = "Yes, V1={";
	            for (int i = 0; i < s1Counter; i++) {
	                result += (char) (65 + set1[i]);
	                if (i != s1Counter - 1) {
	                    result += ",";
	                }
	            }

	            result += "} V2={";
	            for (int i = 0; i < s2Counter; i++) {
	                result += (char) (65 + set2[i]);
	                if (i != s2Counter - 1) {
	                    result += ",";
	                }
	            }

	            return result += "}";
	        }
	    }

	    return result = "No.";
	}

	public boolean dfsForColoring(int[][] matrix, int vertexCount, int[] vertexColor, int node, int color, boolean[] visited) {
	    if (vertexColor[node] != 0) {
	        return vertexColor[node] == color;
	    }

	    else {
		    vertexColor[node] = color; visited[node] = true;

		    for (int i = 0; i < vertexCount; i++) {
		        if (matrix[node][i] == 1 && !dfsForColoring(matrix, vertexCount, vertexColor, i, 3 - color, visited)) {
		            return false;
		        }
		    }

		    return true;
	    }
	}
	
	public String isStarGraph(int[][] matrix, int n){
		boolean flag = true;
		int[] degreeSums = new int[n];

		for(int i = 0; i < n; i++){
			for(int j = 0; j < n; j++){
				if(matrix[i][j] == 1){
					degreeSums[i]++;
				}
			}
		}
		int[] cloneDegreeSums = degreeSums.clone();
		bubbleSort(cloneDegreeSums);
		boolean flag2 = true;
		for(int i = 1; i < n-1; i++){
			if(degreeSums[i] != 1){
				flag2 = false;
				break;
			}
		}
		if(flag2 && cloneDegreeSums[0] == n - 1) flag = true;
		else flag = false;
		
		int center = 0;
		for(int i = 0; i < n; i++){
			if(degreeSums[i] == cloneDegreeSums[0]) center = i;
		}

		if(flag) return "Yes, center is " + (char) (65 + center);
		else return "No";
	}
	
	public void CopyMainToSecondary() {
		secondary_graph = new Graph(main_graph.numVertices, main_graph.matrix, main_graph.vertex);

	}

	public void CopySecondaryToMain() {
		main_graph = new Graph(secondary_graph.numVertices, secondary_graph.matrix, secondary_graph.vertex);

	}

	public void CopyMainToDepot(int queue) {
		depot_graphs[queue] = new Graph(main_graph.numVertices, main_graph.matrix, main_graph.vertex);

	}

	public void CopyDepotToMain(int queue) {
		main_graph = new Graph(depot_graphs[queue].numVertices, depot_graphs[queue].matrix, depot_graphs[queue].vertex);

	}

	public void drawingModes(int mod , Graph graph) {
		if (mod == 0) {
			cn.getTextWindow().setCursorPosition(0, 0);
			for (int i = 0; i < 25; i++) {
				for (int j = 0; j < 37; j++) {
					System.out.print(graph.board[i][j]);
				}
				System.out.println();
			}
			cn.getTextWindow().setCursorPosition(39, 5 + graph.numVertices);
			System.out.println("Drawing mode: " + mod);
		} else if (mod == 1) {
			cn.getTextWindow().setCursorPosition(0, 0);
			for (int i = 0; i < 25; i++) {
				for (int j = 0; j < 37; j++) {
					if (graph.board[i][j] == '1') {
						cn.getTextWindow().setCursorPosition(j, i);
						System.out.print(" ");
						cn.getTextWindow().setCursorPosition(j, i);
						System.out.print("+");
					} else if (graph.board[i][j] == '2') {
						cn.getTextWindow().setCursorPosition(j, i);
						System.out.print(" ");
						cn.getTextWindow().setCursorPosition(j, i);
						System.out.print("o");
					} else if (graph.board[i][j] == '3') {
						cn.getTextWindow().setCursorPosition(j, i);
						System.out.print(" ");
						cn.getTextWindow().setCursorPosition(j, i);
						System.out.print("#");
					} else if (graph.board[i][j] != ' ' && graph.board[i][j] != '.') {
						cn.getTextWindow().setCursorPosition(j, i);
						System.out.print(" ");
						cn.getTextWindow().setCursorPosition(j, i);
						System.out.print("+");
					}
				}
			}
			for (int i = 0; i < 25; i++) {
				for (int j = 0; j < 37; j++) {
					for (int k = 0; k < graph.numVertices; k++) {
						if (graph.board[i][j] == graph.vertices[k]) {
							cn.getTextWindow().setCursorPosition(j, i);
							System.out.print(" ");
							cn.getTextWindow().setCursorPosition(j, i);
							System.out.print(main_graph.board[i][j]);
						}
					}
				}
			}
			cn.getTextWindow().setCursorPosition(39, 5 + graph.numVertices);
			System.out.println("Drawing mode: " + mod);
		} else if (mod == 2) {
			cn.getTextWindow().setCursorPosition(0, 0);
			for (int i = 0; i < 25; i++) {
				for (int j = 0; j < 37; j++) {
					if (graph.board[i][j] != ' ' && graph.board[i][j] != '.') {
						cn.getTextWindow().setCursorPosition(j, i);
						System.out.print(" ");
						cn.getTextWindow().setCursorPosition(j, i);
						System.out.print("+");
					}
				}
			}
			for (int i = 0; i < 25; i++) {
				for (int j = 0; j < 37; j++) {
					for (int k = 0; k < graph.numVertices; k++) {
						if (graph.board[i][j] == graph.vertices[k]) {
							cn.getTextWindow().setCursorPosition(j, i);
							System.out.print(" ");
							cn.getTextWindow().setCursorPosition(j, i);
							System.out.print(graph.board[i][j]);
						}
					}
				}
			}
			cn.getTextWindow().setCursorPosition(39, 5 + graph.numVertices);
			System.out.println("Drawing mode: " + mod);
		}
	}

	public void printRN(){
		int graphsPerRow = 0;
		int x = 120;
		int y = 1;
		int spaceBetweenRows = 0;
		int spaceBetweenColumns = main_graph.numVertices*3;
		int tmp = 0;

		for(int i = 2; i <= main_graph.numVertices + 1; i++) {
			if(i % 3 == 2){
				cn.getTextWindow().setCursorPosition(x, y+spaceBetweenRows);
				System.out.print("R^" + i + " : ");
				cn.getTextWindow().setCursorPosition(x, y+1+spaceBetweenRows);
				printMatrix(rn(main_graph.matrix, main_graph.numVertices, i), x, y+2+spaceBetweenRows);
			}
			if(i % 3 == 0){
				cn.getTextWindow().setCursorPosition(x+spaceBetweenColumns, y+spaceBetweenRows);
				System.out.print("R^" + i + " : ");
				cn.getTextWindow().setCursorPosition(x+spaceBetweenColumns, y+1+spaceBetweenRows);
				printMatrix(rn(main_graph.matrix, main_graph.numVertices, i), x+spaceBetweenColumns, y+2+spaceBetweenRows);
			}
			if(i % 3 == 1){
				cn.getTextWindow().setCursorPosition(x+spaceBetweenColumns*2, y+spaceBetweenRows);
				System.out.print("R^" + i + " : ");
				cn.getTextWindow().setCursorPosition(x+spaceBetweenColumns*2, y+1+spaceBetweenRows);
				printMatrix(rn(main_graph.matrix, main_graph.numVertices, i), x+spaceBetweenColumns*2, y+2+spaceBetweenRows);
				spaceBetweenRows += main_graph.numVertices*2;
			}
			tmp = i;
		}

		switch(tmp % 3){
			case 1:
				cn.getTextWindow().setCursorPosition(x, y+spaceBetweenRows);
				System.out.print("R^* : ");
				cn.getTextWindow().setCursorPosition(x, y+1+spaceBetweenRows);
				printMatrix(TransitiveClosure(main_graph.matrix,main_graph.numVertices), x, y+2+spaceBetweenRows);

				cn.getTextWindow().setCursorPosition(x+spaceBetweenColumns, y+spaceBetweenRows);
				System.out.print("R^min : ");
				cn.getTextWindow().setCursorPosition(x+spaceBetweenColumns, y+1+spaceBetweenRows);
				printMatrix(findRminMatrix(main_graph.matrix), x+spaceBetweenColumns, y+2+spaceBetweenRows);
				break;
			case 2:
				cn.getTextWindow().setCursorPosition(x+spaceBetweenColumns, y+spaceBetweenRows);
				System.out.print("R^* : ");
				cn.getTextWindow().setCursorPosition(x+spaceBetweenColumns, y+1+spaceBetweenRows);
				printMatrix(TransitiveClosure(main_graph.matrix,main_graph.numVertices), x+spaceBetweenColumns, y+2+spaceBetweenRows);

				cn.getTextWindow().setCursorPosition(x+spaceBetweenColumns*2, y+spaceBetweenRows);
				System.out.print("R^min : ");
				cn.getTextWindow().setCursorPosition(x+spaceBetweenColumns*2, y+1+spaceBetweenRows);
				printMatrix(findRminMatrix(main_graph.matrix), x+spaceBetweenColumns*2, y+2+spaceBetweenRows);
				break;
			case 0:
				cn.getTextWindow().setCursorPosition(x+spaceBetweenColumns*2, y+spaceBetweenRows);
				System.out.print("R^* : ");
				cn.getTextWindow().setCursorPosition(x+spaceBetweenColumns*2, y+1+spaceBetweenRows);
				printMatrix(TransitiveClosure(main_graph.matrix,main_graph.numVertices), x+spaceBetweenColumns*2, y+2+spaceBetweenRows);

				spaceBetweenRows += main_graph.numVertices*2;
				cn.getTextWindow().setCursorPosition(x, y+spaceBetweenRows);
				System.out.print("R^min : ");
				cn.getTextWindow().setCursorPosition(x, y+1+spaceBetweenRows);
				printMatrix(findRminMatrix(main_graph.matrix), x, y+2+spaceBetweenRows);
				break;
		}
	} 

	public int[][] rn(int[][] matrix, int n, int power) {
		int[][] result = new int[main_graph.numVertices][main_graph.numVertices];
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < n; j++) {
				result[i][j] = matrix[i][j];
			}
		}

		int[][] temp = new int[n][n];

		for (int p = 1; p < power; p++) { // R^2, R^3, ..., R^n hesaplama
			// Geçici matris sıfırlanıyor
			for (int i = 0; i < n; i++) {
				for (int j = 0; j < n; j++) {
					temp[i][j] = 0;
				}
			}

			// Matrix çarpımı result * matrix
			for (int i = 0; i < n; i++) {
				for (int j = 0; j < n; j++) {
					for (int k = 0; k < n; k++) {
						temp[i][j] += result[i][k] * matrix[k][j]; // Graph teorisine uygun hesaplama
					}
				}
			}

			// Sonucu result'a kopyala ve 0 ya da 1 yap
			for (int i = 0; i < n; i++) {
				for (int j = 0; j < n; j++) {
					result[i][j] = (temp[i][j] > 0) ? 1 : 0; // 0'dan büyükse 1, değilse 0
				}
			}
		}

		return result;
	}

	public void printMatrix(int[][] matrix, int x, int y) {
		for (int i = 0; i < main_graph.numVertices; i++) {
			for (int j = 0; j < main_graph.numVertices; j++) {
				System.out.print(matrix[i][j] + " ");
			}
			cn.getTextWindow().setCursorPosition(x, y);
			y++;
		}
	}

	public int[][] TransitiveClosure(int[][] matrix, int n) {
	    int[][] result = new int[n][];
	    
	    for (int i = 0; i < n; i++) {
	        result[i] = new int[n];
	        for (int j = 0; j < n; j++) {
	            result[i][j] = matrix[i][j]; 
	        }
	    }

	    for (int k = 0; k < n; k++) {
	        for (int i = 0; i < n; i++) {
	            for (int j = 0; j < n; j++) {
	                result[i][j] = (result[i][j] == 1 || (result[i][k] == 1 && result[k][j] == 1)) ? 1 : 0;
	            }
	        }
	    }

	    return result;
	}
	
    private int dfsForRmin(int[][] matrix, int i, int j, boolean[] visited, int stepCounter) {
        if (i == j) {
            return stepCounter;
        }

        visited[i] = true;  
        int tempStepCounter = Integer.MAX_VALUE;

        for (int i_copy = 0; i_copy < matrix.length; i_copy++) {
            if (!visited[i_copy] && matrix[i][i_copy] == 1) {
                int result = dfsForRmin(matrix, i_copy, j, visited, stepCounter + 1);
                if (result < tempStepCounter) {
                	tempStepCounter = result;
                }
            }
        }

        visited[i] = false;  
        return tempStepCounter;
    }

	public int[][] findRminMatrix(int[][] matrix) {
		int[][] resultMatrix = new int[matrix[0].length][matrix[1].length];
		for(int i = 0; i < matrix[0].length; i++) {
			for(int j = 0; j < matrix[1].length; j++) {
				if(i == j) {
					resultMatrix[i][j] = 2;
				}
				else if(i != j && matrix[i][j] == 1) {
					resultMatrix[i][j] = 1;
				}
				else if(i != j && matrix[i][j] == 0) {          
					boolean[] visited = new boolean[matrix.length];
                    resultMatrix[i][j] = dfsForRmin(matrix, i, j, visited, 0);;
				}
			}
		}
		return resultMatrix;
	}
	
	public boolean isCycleGraph(int[][] matrix, int n) {
	    
	    for (int i = 0; i < n; i++) {
	        int degree = 0;
	        for (int j = 0; j < n; j++) {
	            degree += matrix[i][j];
	        }
	        if (degree != 2) {
	            return false;
	        }
	    }

	    boolean[] visited = new boolean[n];
	    int current = 0;
	    int parent = -1;

	    for (int step = 0; step < n; step++) {
	        visited[current] = true;
	        int next = -1;

	        for (int i = 0; i < n; i++) {
	            if (matrix[current][i] == 1 && i != parent) {
	                next = i;
	                break;
	            }
	        }

	        if (next == -1) {
	            return false; 
	        }

	        parent = current;
	        current = next;
	    }

	    
	    return current == 0 && allVisited(visited);
	}

	private boolean allVisited(boolean[] visited) {
	    for (boolean v : visited) {
	        if (!v) return false;
	    }
	    return true;
	}

	public void mainToText() {
		try {
			BufferedWriter writer = new BufferedWriter(new FileWriter("graph.txt"));
			writer.write(String.valueOf(main_graph.numVertices));
			for (int i = 0; i < main_graph.numVertices; i++) {
				writer.newLine();
				writer.write(main_graph.vertex[i].name + " ");
				writer.write(String.valueOf(main_graph.vertex[i].x) + " ");
				writer.write(String.valueOf(main_graph.vertex[i].y));
			}
			writer.newLine();
			for (int i = 0; i < main_graph.numVertices; i++) {
				for (int j = 0; j < main_graph.numVertices; j++) {
					writer.write(String.valueOf(main_graph.matrix[i][j] + " "));
				}
				writer.newLine();
			}
			writer.close();

			textIsEmpty = false;

		} catch (IOException e) {

			e.printStackTrace();
		}
	}

	public void textToMain() {
		String graphText = "graph.txt";
		Vertex[] vertex;
		int numVertices = 0;
		try {
			BufferedReader reader = new BufferedReader(new FileReader(graphText));
			try {
				numVertices = Integer.parseInt(reader.readLine().trim());
				vertex = new Vertex[numVertices];
				int i = 0;
				String lastLine = "";
				while (true) {
					for (int k = 0; k < main_graph.vertex.length; k++) {
						main_graph.vertex[i] = new Vertex();
					}
					String line = reader.readLine();
					String[] vertexs = line.split(" ");

					String temp = vertexs[0];
					char letter = temp.charAt(0);
					if (letter == '1' || letter == '0') {
						lastLine = line;
						break;
					}
					int x = Integer.parseInt(vertexs[1]);
					int y = Integer.parseInt(vertexs[2]);
					vertex[i] = new Vertex(x, y, letter);
					i++;
				}

				int[][] matrix = new int[i][i];

				String[] row = lastLine.split(" ");
				for (int k = 0; k < i; k++) {
					matrix[0][k] = Integer.parseInt(row[k]);
				}

				for (int j = 1; j < i; j++) {
					row = reader.readLine().split(" ");
					for (int k = 0; k < i; k++) {
						matrix[j][k] = Integer.parseInt(row[k]);
					}
				}

				main_graph = new Graph(numVertices, matrix, vertex);

			} catch (NumberFormatException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

	public void improveGraph() {
		
		trial++;
		//int attempt=10000;
		 main_graph = new Graph(main_graph.numVertices, main_graph.matrix);
		main_graph.calculatePenalty();
		currentScore = main_graph.penalty;
		if(currentScore<bestScore) {
			bestScore=currentScore;
		}
		/*
		int a=main_graph.penalty;
		while (a > 0 && attempt > 0) {
			trial++;
			Graph main2Graph = new Graph(main_graph.numVertices, main_graph.matrix);

			if (main2Graph.penalty < a) {
				a = main2Graph.penalty;
				main_graph = new Graph(main2Graph.numVertices, main2Graph.matrix, main2Graph.vertex);
			}

			attempt--;
		}*/
	}

	Menu(Console cn) {
		secondary_graph = new Graph();
		main_graph = new Graph();
		for (int i = 0; i < 9; i++) {
			depot_graphs[i] = new Graph();
		}
		this.cn = cn;
	}

}



