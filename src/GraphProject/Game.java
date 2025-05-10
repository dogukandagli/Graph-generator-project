package GraphProject;

import enigma.core.Enigma;
import enigma.event.TextMouseEvent;
import enigma.event.TextMouseListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class Game {

	public enigma.console.Console cn = Enigma.getConsole("Mouse and Keyboard");
	
	public TextMouseListener tmlis;
	public KeyListener klis;

	public int mousepr, mousex, mousey;
	public int keypr = 0;
	public int rkey = 0;
	public int DrawingMode = 0;
	
	public void clear() {
		for (int i = 0; i < cn.getTextWindow().getRows(); i++) {
			cn.getTextWindow().setCursorPosition(0, i);
			cn.getTextWindow().output(
					"                                                                                                                                                                                                              ");

		}
		cn.getTextWindow().setCursorPosition(0, 0);
	}

	public void showMenu() {
		clear();
		System.out.println("Menu");
		System.out.println("Graph Generation Menu       (Key: Z)");
		System.out.println("Graph Test Menu             (Key: X)");
		System.out.println("Graph Transfer Menu         (Key: C)");
		System.out.println("Sample Isomorphism Screen   (Key: V)");

		System.out.println("Exit                 (Key: Q)");
	}

	public void showGraphTransferMenu() {
		clear();
		System.out.println("Graph Transfer Menu");
		System.out.println("------------------------------");
		System.out.println("1. Copy main graph to secondary graph  (Key: G)");
		System.out.println("2. Copy secondary graph to main graph  (Key: H)");
		System.out.println("3. Load graph file (graph1.txt)        (Key: L)");
		System.out.println("4. Save main graph to file (graph1.txt)(Key: S)");
		System.out.println("5. Copy main graph to depot (Keys: QWE RTY UIO)");
		System.out.println("6. Copy depot graph to main (Keys: 123 456 789)");
		System.out.println("7. Exit                                (Key: A)");
	}

	Game() throws Exception {
		tmlis = new TextMouseListener() {
			public void mouseClicked(TextMouseEvent arg0) {
			}

			public void mousePressed(TextMouseEvent arg0) {
				if (mousepr == 0) {
					mousepr = 1;
					mousex = arg0.getX();
					mousey = arg0.getY();
				}
			}

			public void mouseReleased(TextMouseEvent arg0) {
			}
		};
		cn.getTextWindow().addTextMouseListener(tmlis);

		klis = new KeyListener() {
			public void keyTyped(KeyEvent e) {
			}

			public void keyPressed(KeyEvent e) {
				if (keypr == 0) {
					keypr = 1;
					rkey = e.getKeyCode();
				}
			}

			public void keyReleased(KeyEvent e) {
			}
		};
		cn.getTextWindow().addKeyListener(klis);

		Menu menu = new Menu(cn);
		boolean exit = true;
		showMenu();

		while (exit) {
			if (keypr == 1) {
				if(rkey == KeyEvent.VK_D) {
					DrawingMode++;
					if(DrawingMode == 3) {
						DrawingMode = 0;
					}
					menu.drawingModes(DrawingMode,menu.main_graph);
					if(!menu.secondary_graph.isEmpty()) {
						menu.drawingModes(DrawingMode,menu.secondary_graph);
					}
				}
				if (rkey == KeyEvent.VK_Z) {
					menu.generate();
					
				}
				else if (rkey == KeyEvent.VK_H) {
					
			        menu.improveGraph();
			        clear();
			        menu.main_graph.printEnigma(cn, 0);
			        cn.getTextWindow().setCursorPosition(39, 4 + menu.vertexCount);
			        menu.main_graph.calculatePenalty();
			        cn.getTextWindow().setCursorPosition(39, 5 + menu.vertexCount);
			        System.out.println("Drawing mode: 0");
			        menu.printAdjMatrix(menu.main_graph.matrix, 40, 1);
			        cn.getTextWindow().setCursorPosition(39, 7 + menu.vertexCount);
					System.out.println("---Try:" + menu.trial);
					cn.getTextWindow().setCursorPosition(39, 9 + menu.vertexCount);
					System.out.println("C pen:" + menu.currentScore);
					cn.getTextWindow().setCursorPosition(39, 10 + menu.vertexCount);
					System.out.println("BC pen:" + menu.bestScore);
			        cn.getTextWindow().setCursorPosition(0, 28);
			        System.out.println("==Press H if you want to improve== ");
			        cn.getTextWindow().setCursorPosition(0, 29);
			        System.out.println("Press Q to go MENU");
					
			    }else if (rkey == KeyEvent.VK_X) {
					clear();
					if (menu.adjMatrix != null) {
						menu.TestMenu();
						
					} else {
						clear();
						System.out.println("Please generate a graph first.");
					}
				} else if (rkey == KeyEvent.VK_Q) {
					clear();
					showMenu();
				}else if(rkey == KeyEvent.VK_V) {
					if(!menu.secondary_graph.isEmpty()) {
						if(menu.main_graph.numVertices==menu.secondary_graph.numVertices) {
							menu.displayIsomorphism();
							cn.getTextWindow().setCursorPosition(40, menu.main_graph.numVertices+3);
							System.out.println("C pen:" + menu.currentScore);
							cn.getTextWindow().setCursorPosition(40,menu.main_graph.numVertices+4);
							System.out.println("BC pen:" + menu.bestScore);
							cn.getTextWindow().setCursorPosition(40, menu.main_graph.numVertices+6 );
							System.out.print("---Try : " + menu.trial);
						}else {
							clear();
							System.out.println("Number of vertices of main graph and secondary graph are not equal . ");
						}
					
					
					}else {
							clear();
						  cn.getTextWindow().setCursorPosition(0, 0);
						  System.out.print("Secondary graph empty");
						  cn.getTextWindow().setCursorPosition(0, 2);
						  System.out.println("Press Q to go MENU");
					}
				}
				else if (rkey == KeyEvent.VK_C) {
					clear();
					boolean inTransferMenu = true;
					keypr = 0;
					while (inTransferMenu) {
						if (keypr == 1) {
							showGraphTransferMenu();

							if (rkey == KeyEvent.VK_G) {
								clear();
								cn.getTextWindow().setCursorPosition(0, 4 );
								System.out.println("Press A to go MENU");
								if (menu.main_graph.isEmpty()) {
									System.out.println("Main graph empty");
								} else {
									menu.CopyMainToSecondary();
									cn.getTextWindow().output("Copied main graph to secondary graph.");

									if(!menu.secondary_graph.isEmpty()) {
										menu.secondary_graph.printEnigma(cn, 50+6*menu.main_graph.numVertices);
									}
								}
								cn.getTextWindow().setCursorPosition(0, 4 );
								System.out.println("Press A to go MENU");
							} else if (rkey == KeyEvent.VK_H) {
								clear();
								
								if (menu.secondary_graph.isEmpty()) {
									System.out.println("Secondary graph empty");
								} else {
									menu.CopySecondaryToMain();
									System.out.println("Copied secondary graph to main graph.");
									if(!menu.secondary_graph.isEmpty()) {
										menu.secondary_graph.printEnigma(cn, 50+6*menu.main_graph.numVertices);
									}
								}
								cn.getTextWindow().setCursorPosition(0, 4 );
								System.out.println("Press A to go MENU");
							}else if(rkey == KeyEvent.VK_L) {
								clear();
								if(menu.textIsEmpty) {
									System.out.println("graph.text empty");
								}else {
									menu.textToMain();
									System.out.println("Copied graph.text to main graph");
								}
								
							}
							
							else if(rkey == KeyEvent.VK_S) {
								clear();
								if(!menu.main_graph.isEmpty()) {
									menu.mainToText();
									System.out.println("Copied main graph to graph.text");
								}else {
									System.out.println("Main graph empty");
								}
							}

							else if (rkey == KeyEvent.VK_A) {
								inTransferMenu = false;
								clear();
								showMenu();
							}
							String key = KeyEvent.getKeyText(rkey);
							String[] LetterKeys = { "Q", "W", "E", "R", "T", "Y", "U", "I", "O" };
							String[] NumberKeys = { "1", "2", "3", "4", "5", "6", "7", "8", "9" };

							for (int i = 0; i < LetterKeys.length; i++) {
								
								if (key.equals(LetterKeys[i])) {
									clear();
									
									if(menu.main_graph.isEmpty()) {
										cn.getTextWindow().output("Main graph empty");
									}else if(!menu.depot_graphs[i].isEmpty()) {
										cn.getTextWindow().output(i+". depot graph full");
									}else if(!menu.main_graph.isEmpty() && menu.depot_graphs[i].isEmpty()) {
										menu.CopyMainToDepot(i);
										cn.getTextWindow().output("Copied main graph to "+ (i+1) + ". depot graph.");
									}else {
										cn.getTextWindow().output("Main graph and depot graph empty");
									}
									if(!menu.main_graph.isEmpty()) {
										menu.main_graph.printEnigma(cn, 50+6*menu.main_graph.numVertices);
									}
									
								} else if (key.equals(NumberKeys[i])) {
									clear();
									 if(menu.depot_graphs[i].isEmpty()) {
										 cn.getTextWindow().output(i+". depot graph empty");
									}else if( !menu.depot_graphs[i].isEmpty()) {
										menu.CopyDepotToMain(i);
										cn.getTextWindow().output("Copied " + (i+1) +". depot graph to main graph.");
									}
									 if(!menu.depot_graphs[i].isEmpty()) {
											menu.depot_graphs[i].printEnigma(cn, 50+6*menu.main_graph.numVertices);
										}
									
								}
								cn.getTextWindow().setCursorPosition(0, 10 );
								System.out.println("Press A to go MENU");
							}
							keypr = 0;
						}
						Thread.sleep(20);
					}
				}
				keypr = 0;
			}
			Thread.sleep(20);
		}
	}
}
