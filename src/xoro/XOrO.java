package xoro;

import processing.core.*;
import xoro.gameBoard.*;
import xoro.menus.*;

public class XOrO extends PApplet {

	public static void main(String _args[]) {
		PApplet.main(new String[] { xoro.XOrO.class.getName() });
	}

	mainMenu mainMenu = new mainMenu();
	vsComputerMenu vsComputerMenu = new vsComputerMenu();
	gamePlace[][] place = new gamePlace[3][3];
	Board board = new Board();
	boolean ternX = true, isXWiner = true, isThereWiner = false, isOverNoWiner = false, isVSComputer = true,
			way1 = false, menuModeMain = false, menuModeVsComputer = false, playMode = true;
	int computerMode = 4, ternsPast = 0, backgroundColor;
	int[][] lastPlaceForPlayer = new int[4][2];

	public void settings() {
		size(600, 600);
	}

	public void setup() {
		surface.setResizable(true);
		for (int i = 0; i < place.length; i++) {
			for (int g = 0; g < place[0].length; g++) {
				place[i][g] = new gamePlace();
			}
		}
		setupMainMenu();
		setupVsComputerMenu();
		backgroundColor = color((int) random(0f, 255.9999999999999999999999f),
				(int) random(0f, 255.9999999999999999999999f), (int) random(0f, 255.9999999999999999999999f));
	}

	public void draw() {
		background(255);
		if (menuModeMain) {
			background(backgroundColor);
			drawMainMenu();
		} else if (menuModeVsComputer) {
			background(backgroundColor);
			drawVsComputerMenu();
		} else if (playMode) {
			checkXWin();
			checkYWin();
			checkXY1Win();
			checkXY2Win();
			checkOverNoWiner();
			if (!isThereWiner && !isOverNoWiner) {
				boardLocCacoletion();
				drawBoard();
				drawXAndO();
			} else if (isThereWiner) {
				if (isXWiner) {
					pushMatrix();
					translate(width / 2, height / 2);
					fill(0);
					stroke(0);
					strokeWeight(10);
					int size1 = min(height, width) / 2 - 50;
					line(-size1, -size1, size1, size1);
					line(-size1, size1, size1, -size1);
					popMatrix();
				} else {
					fill(255);
					stroke(0);
					strokeWeight(10);
					int size1 = min(height, width) - 100;
					ellipse(width / 2, height / 2, size1, size1);
				}
			} else if (isOverNoWiner) {
				fill(255);
				stroke(0);
				strokeWeight(10);
				pushMatrix();
				translate(width / 4, height / 2);
				int size1 = min(height, width) / 4 - 15;
				line(-size1, -size1, size1, size1);
				line(-size1, size1, size1, -size1);
				popMatrix();
				size1 = min(height, width) / 2 - 30;
				ellipse((width / 4) * 3, height / 2, size1, size1);
			}
		}
	}

	public void mousePressed() {
		if (menuModeMain) {
			if (pointInShape(mouseX, mouseY, mainMenu.getOneVSComputer().getRect().getX(),
					mainMenu.getOneVSComputer().getRect().getY(), mainMenu.getOneVSComputer().getRect().getHeight(),
					mainMenu.getOneVSComputer().getRect().getWidth())) {
				isVSComputer = true;
				menuModeMain = false;
				playMode = false;
				menuModeVsComputer = true;
			} else if (pointInShape(mouseX, mouseY, mainMenu.getOneVSOne().getRect().getX(),
					mainMenu.getOneVSOne().getRect().getY(), mainMenu.getOneVSOne().getRect().getHeight(),
					mainMenu.getOneVSOne().getRect().getWidth())) {
				isVSComputer = false;
				menuModeMain = false;
				playMode = true;
				menuModeVsComputer = false;
			}
		} else if (menuModeVsComputer) {
			if (pointInShape(mouseX, mouseY, vsComputerMenu.getEasyMode().getRect().getX(),
					vsComputerMenu.getEasyMode().getRect().getY(), vsComputerMenu.getEasyMode().getRect().getHeight(),
					vsComputerMenu.getEasyMode().getRect().getWidth())) {
				isVSComputer = true;
				menuModeMain = false;
				playMode = true;
				menuModeVsComputer = false;
				computerMode = 1;
			} else if (pointInShape(mouseX, mouseY, vsComputerMenu.getNormalMode().getRect().getX(),
					vsComputerMenu.getNormalMode().getRect().getY(),
					vsComputerMenu.getNormalMode().getRect().getHeight(),
					vsComputerMenu.getNormalMode().getRect().getWidth())) {
				isVSComputer = true;
				menuModeMain = false;
				playMode = true;
				menuModeVsComputer = false;
				computerMode = 2;
			} else if (pointInShape(mouseX, mouseY, vsComputerMenu.getHardMode().getRect().getX(),
					vsComputerMenu.getHardMode().getRect().getY(), vsComputerMenu.getHardMode().getRect().getHeight(),
					vsComputerMenu.getHardMode().getRect().getWidth())) {
				isVSComputer = true;
				menuModeMain = false;
				playMode = true;
				menuModeVsComputer = false;
				computerMode = 3;
			} else if (pointInShape(mouseX, mouseY, vsComputerMenu.getMultiHardMode().getRect().getX(),
					vsComputerMenu.getMultiHardMode().getRect().getY(),
					vsComputerMenu.getMultiHardMode().getRect().getHeight(),
					vsComputerMenu.getMultiHardMode().getRect().getWidth())) {
				isVSComputer = true;
				menuModeMain = false;
				playMode = true;
				menuModeVsComputer = false;
				computerMode = 4;
			}
		} else if (playMode) {
			if (!isThereWiner && !isOverNoWiner) {
				for (int i = 0; i < board.getRect0Length(); i++) {
					for (int g = 0; g < board.getRectLength(); g++) {
						if (pointInShape(mouseX, mouseY, board.getRect(g, i).getX(), board.getRect(g, i).getY(),
								board.getRect(g, i).getHeight(), board.getRect(g, i).getWidth())
								&& place[g][i].getIsThereXOrO() == false) {
							place[g][i].setThereXOrO(true);
							lastPlaceForPlayer[0][0] = lastPlaceForPlayer[1][0];
							lastPlaceForPlayer[0][1] = lastPlaceForPlayer[1][1];
							lastPlaceForPlayer[1][0] = lastPlaceForPlayer[2][0];
							lastPlaceForPlayer[1][1] = lastPlaceForPlayer[2][1];
							lastPlaceForPlayer[2][0] = lastPlaceForPlayer[3][0];
							lastPlaceForPlayer[2][1] = lastPlaceForPlayer[3][1];
							lastPlaceForPlayer[3][0] = g;
							lastPlaceForPlayer[3][1] = i;
							ternsPast++;
							if (!isVSComputer) {
								place[g][i].setThereX(ternX);
								ternX = !ternX;
							} else {
								place[g][i].setThereX(true);
								switch (computerMode) {
								case 4:
									coputerPlaceCacoletionMultiHard();
									break;
								case 3:
									coputerPlaceCacoletionHard();
									break;
								case 2:
									coputerPlaceCacoletionNormal();
									break;
								case 1:
									coputerPlaceCacoletionEasy();
									break;
								}
							}
						}
					}
				}
			} else {
				restartGame();
			}
		}
	}

	private void coputerPlaceCacoletionEasy() {
		int[] randomNumTemp = { (int) random(0, 2.999999f), (int) random(0, 2.999999f) };
		checkXWin();
		checkYWin();
		checkXY1Win();
		checkXY2Win();
		checkOverNoWiner();
		if (!isOverNoWiner) {
			if (!isThereWiner) {
				if (!place[randomNumTemp[0]][randomNumTemp[1]].getIsThereXOrO()) {
					place[randomNumTemp[0]][randomNumTemp[1]].setThereXOrO(true);
					place[randomNumTemp[0]][randomNumTemp[1]].setThereX(false);
				} else {
					coputerPlaceCacoletionEasy();
				}
			}
		}
	}

	private void coputerPlaceCacoletionNormal() {
		int[] randomNumTemp = { (int) random(0, 2.999999f), (int) random(0, 2.999999f) };
		checkXWin();
		checkYWin();
		checkXY1Win();
		checkXY2Win();
		checkOverNoWiner();
		if (!isOverNoWiner) {
			if (!isThereWiner) {
				if (!blockAndWin(true)) {
					if (!blockAndWin(false)) {
						if (!place[randomNumTemp[0]][randomNumTemp[1]].getIsThereXOrO()) {
							place[randomNumTemp[0]][randomNumTemp[1]].setThereXOrO(true);
							place[randomNumTemp[0]][randomNumTemp[1]].setThereX(false);
						} else {
							coputerPlaceCacoletionEasy();
						}
					}
				}
			}
		}
	}

	private int checkXForBlock() {
		for (int i = 0; i < place.length; i++) {
			int XNum = 0, ONum = 0;
			for (int g = 0; g < place[0].length; g++) {
				if (place[g][i].getIsThereXOrO()) {
					if (place[g][i].isThereX()) {
						XNum++;
					} else {
						ONum++;
					}
				}
			}
			if (XNum == 2 && ONum == 0) {
				return i;
			}
		}
		return -1;
	}

	private int checkYForBlock() {
		for (int i = 0; i < place.length; i++) {
			int XNum = 0, ONum = 0;
			for (int g = 0; g < place[0].length; g++) {
				if (place[i][g].getIsThereXOrO()) {
					if (place[i][g].isThereX()) {
						XNum++;
					} else {
						ONum++;
					}
				}
			}
			if (XNum == 2 && ONum == 0) {
				return i;
			}
		}
		return -1;
	}

	private boolean checkXY1ForBlock() {
		int XNum = 0, ONum = 0;
		for (int i = 0; i < place.length; i++) {
			if (place[i][i].getIsThereXOrO()) {
				if (place[i][i].isThereX()) {
					XNum++;
				} else {
					ONum++;
				}
			}
		}
		if (XNum == 2 && ONum == 0) {
			return true;
		}
		return false;
	}

	private boolean checkXY2ForBlock() {
		int XNum = 0, ONum = 0;
		int g = 2;
		for (int i = 0; i < place.length;) {
			if (place[g][i].getIsThereXOrO()) {
				if (place[g][i].isThereX()) {
					XNum++;
				} else {
					ONum++;
				}
			}
			g--;
			i++;
		}
		if (XNum >= 2 && ONum == 0) {
			return true;
		}
		return false;
	}

	private boolean blockAndWin(boolean WinMode) {
		int onX;
		int onY;
		boolean onXY1;
		boolean onXY2;
		if (WinMode) {
			onX = checkXForOWin();
			onY = checkYForOWin();
			onXY1 = checkXY1ForOWin();
			onXY2 = checkXY2ForOWin();
		} else {
			onX = checkXForBlock();
			onY = checkYForBlock();
			onXY1 = checkXY1ForBlock();
			onXY2 = checkXY2ForBlock();
		}
		if (onX != -1) {
			for (int g = 0; g < place[0].length; g++) {
				if (!place[g][onX].getIsThereXOrO()) {
					place[g][onX].setThereXOrO(true);
					place[g][onX].setThereX(false);
				}
			}
			return true;
		} else if (onY != -1) {
			for (int g = 0; g < place.length; g++) {
				if (!place[onY][g].getIsThereXOrO()) {
					place[onY][g].setThereXOrO(true);
					place[onY][g].setThereX(false);
				}
			}
			return true;
		} else if (onXY1) {
			for (int i = 0; i < place.length; i++) {
				if (!place[i][i].getIsThereXOrO()) {
					place[i][i].setThereXOrO(true);
					place[i][i].setThereX(false);
				}
			}
			return true;
		} else if (onXY2) {
			int g = 2;
			for (int i = 0; i < place.length;) {
				if (!place[g][i].getIsThereXOrO()) {
					place[g][i].setThereXOrO(true);
					place[g][i].setThereX(false);
				}
				g--;
				i++;
			}
			return true;
		} else {
			return false;
		}
	}

	private int checkXForOWin() {
		for (int i = 0; i < place.length; i++) {
			int XNum = 0, ONum = 0;
			for (int g = 0; g < place[0].length; g++) {
				if (place[g][i].getIsThereXOrO()) {
					if (place[g][i].isThereX()) {
						XNum++;
					} else {
						ONum++;
					}
				}
			}
			if (XNum == 0 && ONum == 2) {
				return i;
			}
		}
		return -1;
	}

	private int checkYForOWin() {
		for (int i = 0; i < place.length; i++) {
			int XNum = 0, ONum = 0;
			for (int g = 0; g < place[0].length; g++) {
				if (place[i][g].getIsThereXOrO()) {
					if (place[i][g].isThereX()) {
						XNum++;
					} else {
						ONum++;
					}
				}
			}
			if (XNum == 0 && ONum == 2) {
				return i;
			}
		}
		return -1;
	}

	private boolean checkXY1ForOWin() {
		int XNum = 0, ONum = 0;
		for (int i = 0; i < place.length; i++) {
			if (place[i][i].getIsThereXOrO()) {
				if (place[i][i].isThereX()) {
					XNum++;
				} else {
					ONum++;
				}
			}
		}
		if (XNum == 0 && ONum == 2) {
			return true;
		}
		return false;
	}

	private boolean checkXY2ForOWin() {
		int XNum = 0, ONum = 0;
		int g = 2;
		for (int i = 0; i < place.length;) {
			if (place[g][i].getIsThereXOrO()) {
				if (place[g][i].isThereX()) {
					XNum++;
				} else {
					ONum++;
				}
			}
			g--;
			i++;
		}
		if (XNum == 0 && ONum == 2) {
			return true;
		}
		return false;
	}

	private boolean isPlayerInConer(int place1, int place2) {
		for (int i = 0; i < board.getRect0Length(); i += 2) {
			for (int g = 0; g < board.getRectLength(); g += 2) {
				if (place1 == i && place2 == g) {
					return true;
				}
			}
		}
		return false;
	}

	private void coputerPlaceCacoletionHard() {
		int[] randomNumTemp = { (int) random(0, 2.999999f), (int) random(0, 2.999999f) };
		boolean breakMode = false, isInSide = false;
		checkXWin();
		checkYWin();
		checkXY1Win();
		checkXY2Win();
		checkOverNoWiner();
		if (!isOverNoWiner) {
			if (!isThereWiner) {
				if (!blockAndWin(true)) {
					if (!blockAndWin(false)) {
						if (!way1) {
							if (isPlayerInConer(lastPlaceForPlayer[3][0], lastPlaceForPlayer[3][1]) && ternsPast == 1) {
								place[1][1].setThereXOrO(true);
								place[1][1].setThereX(false);
								way1 = true;
								return;
							} else {
								for (int i = 0; i < board.getRect0Length(); i += 2) {
									for (int g = 0; g < board.getRectLength(); g += 2) {
										if (randomNumTemp[0] == i && randomNumTemp[1] == g) {
											breakMode = true;
											isInSide = true;
											break;
										}
									}
									if (breakMode)
										break;
								}
								breakMode = false;
								if (randomNumTemp[0] == 1 && randomNumTemp[1] == 1) {
									isInSide = true;
								}
								if (!isInSide) {
									coputerPlaceCacoletionHard();
									return;
								}
								if (!place[randomNumTemp[0]][randomNumTemp[1]].getIsThereXOrO()) {
									place[randomNumTemp[0]][randomNumTemp[1]].setThereXOrO(true);
									place[randomNumTemp[0]][randomNumTemp[1]].setThereX(false);
								} else {
									coputerPlaceCacoletionHard();
									return;
								}
							}
						} else {
							if (lastPlaceForPlayer[3][0] != 1 && lastPlaceForPlayer[3][1] != 1
									&& (ternsPast == 1 || ternsPast == 2)) {
								if ((place[0][0].getIsThereXOrO() && place[0][0].isThereX()
										&& place[2][2].getIsThereXOrO() && place[2][2].isThereX())
										|| (place[2][0].getIsThereXOrO() && place[2][0].isThereX()
												&& place[0][2].getIsThereXOrO() && place[0][2].isThereX()
												&& ternsPast == 2)) {
									dontPutInConer(randomNumTemp[0], randomNumTemp[1]);
								}
							} else if (lastPlaceForPlayer[3][0] == 1
									|| lastPlaceForPlayer[3][1] == 1 && (ternsPast == 1 || ternsPast == 2)) {
								int[] placeneed1 = new int[2];
								placeneed1[0] = lastPlaceForPlayer[2][0] + 2;
								placeneed1[1] = lastPlaceForPlayer[2][1] + 2;
								for (int i = 0; i < placeneed1.length; i++) {
									if (placeneed1[i] == 4) {
										placeneed1[i] = 0;
									} else if (placeneed1[i] == 3) {
										placeneed1[i] = 1;
									}
								}
								place[placeneed1[0]][placeneed1[1]].setThereXOrO(true);
								place[placeneed1[0]][placeneed1[1]].setThereX(false);
								return;
							} else {
								way1 = false;
								coputerPlaceCacoletionHard();
								return;
							}
							if (ternsPast > 2) {
								way1 = false;
								coputerPlaceCacoletionHard();
								return;
							}
						}
					}
				}
			}
		}

	}

	private void coputerPlaceCacoletionMultiHard() {
		int[] randomNumTemp = { (int) random(0, 2.999999f), (int) random(0, 2.999999f) };
		boolean breakMode = false, isInSide = false;
		checkXWin();
		checkYWin();
		checkXY1Win();
		checkXY2Win();
		checkOverNoWiner();
		if (!isOverNoWiner) {
			if (!isThereWiner) {
				if (!blockAndWin(true)) {
					if (!blockAndWin(false)) {
						if (!way1) {
							if (ternsPast == 1 && !(lastPlaceForPlayer[3][0] == 1 && lastPlaceForPlayer[3][1] == 1)) {
								place[1][1].setThereXOrO(true);
								place[1][1].setThereX(false);
								way1 = true;
								return;
							} else {
								for (int i = 0; i < board.getRect0Length(); i += 2) {
									for (int g = 0; g < board.getRectLength(); g += 2) {
										if (randomNumTemp[0] == i && randomNumTemp[1] == g) {
											breakMode = true;
											isInSide = true;
											break;
										}
									}
									if (breakMode)
										break;
								}
								breakMode = false;
								if (randomNumTemp[0] == 1 && randomNumTemp[1] == 1) {
									isInSide = true;
								}
								if (!isInSide) {
									coputerPlaceCacoletionMultiHard();
									return;
								}
								if (!place[randomNumTemp[0]][randomNumTemp[1]].getIsThereXOrO()) {
									place[randomNumTemp[0]][randomNumTemp[1]].setThereXOrO(true);
									place[randomNumTemp[0]][randomNumTemp[1]].setThereX(false);
								} else {
									coputerPlaceCacoletionMultiHard();
									return;
								}
							}
						} else {
							if ((lastPlaceForPlayer[3][0] != 1 && lastPlaceForPlayer[3][1] != 1
									&& (ternsPast == 1 || ternsPast == 2))
									&& (place[0][0].getIsThereXOrO() && place[0][0].isThereX()
											&& place[2][2].getIsThereXOrO() && place[2][2].isThereX())
									|| (place[2][0].getIsThereXOrO() && place[2][0].isThereX()
											&& place[0][2].getIsThereXOrO() && place[0][2].isThereX()
											&& ternsPast == 2)) {
								dontPutInConer(randomNumTemp[0], randomNumTemp[1]);
							} else if ((lastPlaceForPlayer[2][0] == 1 || lastPlaceForPlayer[2][1] == 1)
									&& (lastPlaceForPlayer[3][0] != 1 && lastPlaceForPlayer[3][1] != 1)) {
								int[] placeneed1 = new int[2];
								placeneed1[0] = lastPlaceForPlayer[3][0];
								placeneed1[1] = lastPlaceForPlayer[2][1];
								if (placeneed1[1] == 1 || placeneed1[0] == 1) {
									placeneed1[0] = lastPlaceForPlayer[2][0];
									placeneed1[1] = lastPlaceForPlayer[3][1];
								}
								place[placeneed1[0]][placeneed1[1]].setThereXOrO(true);
								place[placeneed1[0]][placeneed1[1]].setThereX(false);
								way1 = false;
								return;
							} else if ((lastPlaceForPlayer[3][0] == 1 || lastPlaceForPlayer[3][1] == 1)
									&& (lastPlaceForPlayer[2][0] != 1 && lastPlaceForPlayer[2][1] != 1)) {
								int[] placeneed1 = new int[2];
								placeneed1[0] = lastPlaceForPlayer[2][0];
								placeneed1[1] = lastPlaceForPlayer[3][1];
								if (placeneed1[1] == 1 || placeneed1[0] == 1) {
									placeneed1[0] = lastPlaceForPlayer[3][0];
									placeneed1[1] = lastPlaceForPlayer[2][1];
								}
								place[placeneed1[0]][placeneed1[1]].setThereXOrO(true);
								place[placeneed1[0]][placeneed1[1]].setThereX(false);
								way1 = false;
								return;
							} else if (lastPlaceForPlayer[3][0] == 1
									|| lastPlaceForPlayer[3][1] == 1 && (ternsPast == 1 || ternsPast == 2)) {
								int[] placeneed1 = new int[2];
								if (lastPlaceForPlayer[3][0] == lastPlaceForPlayer[2][0]
										|| lastPlaceForPlayer[3][1] == lastPlaceForPlayer[2][1]) {
									way1 = false;
									coputerPlaceCacoletionMultiHard();
									return;
								} else {
									placeneed1[0] = lastPlaceForPlayer[2][0];
									placeneed1[1] = lastPlaceForPlayer[3][1];
									if (placeneed1[0] == 1 && placeneed1[1] == 1) {
										placeneed1[0] = lastPlaceForPlayer[3][0];
										placeneed1[1] = lastPlaceForPlayer[2][1];
									}
									place[placeneed1[0]][placeneed1[1]].setThereXOrO(true);
									place[placeneed1[0]][placeneed1[1]].setThereX(false);
									return;
								}
							} else {
								way1 = false;
								coputerPlaceCacoletionMultiHard();
								return;
							}
							if (ternsPast > 2) {
								way1 = false;
								coputerPlaceCacoletionMultiHard();
								return;
							}
						}
					}
				}
			}
		}

	}

	private int[] randomNumTempRestart() {
		int[] i = { (int) random(0, 2.999999f), (int) random(0, 2.999999f) };
		return i;
	}

	private void dontPutInConer(int randomNumTemp1, int randomNumTemp2) {
		if (!isPlayerInConer(randomNumTemp1, randomNumTemp2)) {
			if (!place[randomNumTemp1][randomNumTemp2].getIsThereXOrO()) {
				place[randomNumTemp1][randomNumTemp2].setThereXOrO(true);
				place[randomNumTemp1][randomNumTemp2].setThereX(false);
			} else {
				int[] i = randomNumTempRestart();
				dontPutInConer(i[0], i[1]);
				return;
			}
		} else {
			int[] i = randomNumTempRestart();
			dontPutInConer(i[0], i[1]);
			return;
		}
	}

	// private void PutInConerAndMid(int randomNumTemp1,int randomNumTemp2) {
	// if (isPlayerInConer(randomNumTemp1, randomNumTemp2)) {
	// if (!place[randomNumTemp1][randomNumTemp2].isThereXOrO) {
	// place[randomNumTemp1][randomNumTemp2].isThereXOrO = true;
	// place[randomNumTemp1][randomNumTemp2].isThereX = false;
	// } else {
	// int[] i=randomNumTempRestart();
	// dontPutInConer(i[0],i[1]);
	// return;
	// }
	// }else{
	// int[] i=randomNumTempRestart();
	// dontPutInConer(i[0],i[1]);
	// return;
	// }
	// }

	private void drawBoard() {
		fill(255);
		stroke(0);
		strokeWeight(5);
		for (int i = 0; i < board.getRect0Length(); i++) {
			for (int g = 0; g < board.getRectLength(); g++) {
				rect(board.getRect(g, i).getX(), board.getRect(g, i).getY(), board.getRect(g, i).getWidth(),
						board.getRect(g, i).getHeight());
			}
		}
	}

	private void drawXAndO() {
		for (int i = 0; i < place[0].length; i++) {
			for (int g = 0; g < place.length; g++) {
				if (place[g][i].getIsThereXOrO()) {
					int[] xy = new int[2];
					xy = placeLocCacoletion(g, i);
					if (place[g][i].isThereX()) {
						drawX(xy[0], xy[1]);
					} else {
						drawO(xy[0], xy[1]);
					}
				}
			}
		}
	}

	private void drawX(int x1, int y1) {
		pushMatrix();
		translate(x1, y1);
		fill(0);
		stroke(0);
		strokeWeight(10);
		int size1 = min(board.getRect(0, 0).getHeight(), board.getRect(0, 0).getWidth()) / 2 - 15;
		line(-size1, -size1, size1, size1);
		line(-size1, size1, size1, -size1);
		popMatrix();
	}

	private void drawO(int x1, int y1) {
		fill(255);
		stroke(0);
		strokeWeight(10);
		int size1 = min(board.getRect(0, 0).getHeight(), board.getRect(0, 0).getWidth()) - 30;
		ellipse(x1, y1, size1, size1);
	}

	private int[] placeLocCacoletion(int loc1, int loc2) {
		int[] i = new int[2];
		i[0] = width / 6 + width / 3 * loc1;
		i[1] = height / 6 + height / 3 * loc2;
		return i;
	}

	private boolean pointInShape(int x1, int y1, int x2, int y2, int height, int width) {
		if (x1 >= x2 && x1 <= (x2 + width) && y1 >= y2 && y1 <= (y2 + height)) {
			return true;
		} else {
			return false;
		}
	}

	private void boardLocCacoletion() {
		for (int i = 0; i < board.getRect0Length(); i++) {
			for (int g = 0; g < board.getRectLength(); g++) {
				board.getRect(g, i).setHeight(height / 3);
				board.getRect(g, i).setWidth(width / 3);
				board.getRect(g, i).setX(width / 3 * g);
				board.getRect(g, i).setY(height / 3 * i);
			}
		}
	}

	private void checkOverNoWiner() {
		if (!isThereWiner) {
			int numTemp = 0;
			for (int i = 0; i < place[0].length; i++) {
				for (int g = 0; g < place.length; g++) {
					if (place[i][g].getIsThereXOrO() == true)
						numTemp++;
				}
			}
			if (numTemp >= 9)
				isOverNoWiner = true;
		}
	}

	private void restartGame() {
		for (int i = 0; i < place[0].length; i++) {
			for (int g = 0; g < place.length; g++) {
				place[i][g] = new gamePlace();
			}
		}
		isThereWiner = false;
		isOverNoWiner = false;
		ternsPast = 0;
		way1 = false;
	}

	private void checkXWin() {
		for (int i = 0; i < place.length; i++) {
			int XNum = 0, ONum = 0;
			for (int g = 0; g < place[0].length; g++) {
				if (place[g][i].getIsThereXOrO()) {
					if (place[g][i].isThereX()) {
						XNum++;
					} else {
						ONum++;
					}
				}
			}
			if (XNum >= 3) {
				isThereWiner = true;
				isXWiner = true;
			} else if (ONum >= 3) {
				isThereWiner = true;
				isXWiner = false;
			}
		}

	}

	private void checkYWin() {
		for (int i = 0; i < place.length; i++) {
			int XNum = 0, ONum = 0;
			for (int g = 0; g < place[0].length; g++) {
				if (place[i][g].getIsThereXOrO()) {
					if (place[i][g].isThereX()) {
						XNum++;
					} else {
						ONum++;
					}
				}
			}
			if (XNum >= 3) {
				isThereWiner = true;
				isXWiner = true;
			} else if (ONum >= 3) {
				isThereWiner = true;
				isXWiner = false;
			}
		}
	}

	private void checkXY1Win() {
		int XNum = 0, ONum = 0;
		int g = 0;
		for (int i = 0; i < place.length;) {
			if (place[i][g].getIsThereXOrO()) {
				if (place[i][g].isThereX()) {
					XNum++;
				} else {
					ONum++;
				}
			}
			g++;
			i++;
		}
		if (XNum >= 3) {
			isThereWiner = true;
			isXWiner = true;
		} else if (ONum >= 3) {
			isThereWiner = true;
			isXWiner = false;
		}
	}

	private void checkXY2Win() {
		int XNum = 0, ONum = 0;
		int g = 2;
		for (int i = 0; i < place.length;) {
			if (place[g][i].getIsThereXOrO()) {
				if (place[g][i].isThereX()) {
					XNum++;
				} else {
					ONum++;
				}
			}
			g--;
			i++;
		}
		if (XNum >= 3) {
			isThereWiner = true;
			isXWiner = true;
		} else if (ONum >= 3) {
			isThereWiner = true;
			isXWiner = false;
		}
	}

	private void drawMainMenu() {
		mainMenu.getOneVSOne().getRect().setX(width / 2 - mainMenu.getOneVSOne().getRect().getWidth() / 2);
		mainMenu.getOneVSComputer().getRect().setX(width / 2 - mainMenu.getOneVSComputer().getRect().getWidth() / 2);
		mainMenu.getOneVSOne().getRect().setY(height / 4 * 2 - mainMenu.getOneVSOne().getRect().getHeight() / 2);
		mainMenu.getOneVSComputer().getRect()
				.setY(height / 4 * 3 - mainMenu.getOneVSComputer().getRect().getHeight() / 2);
		drawMainMenuRects();
		drawMainMenuTexts();
	}

	private void drawMainMenuTexts() {
		PFont font;
		font = createFont("Arial", 35);
		textFont(font);
		fill(0);
		text(mainMenu.getMainMenu(), width / 2 - textWidth(mainMenu.getMainMenu()) / 2, height / 4);
		fill(backgroundColor);
		text(mainMenu.getOneVSComputer().getText(),
				mainMenu.getOneVSComputer().getRect().getX() + mainMenu.getOneVSComputer().getRect().getWidth() / 2
						- textWidth(mainMenu.getOneVSComputer().getText()) / 2,
				mainMenu.getOneVSComputer().getRect().getY() + mainMenu.getOneVSComputer().getRect().getHeight() / 2);
		text(mainMenu.getOneVSOne().getText(),
				mainMenu.getOneVSOne().getRect().getX() + mainMenu.getOneVSOne().getRect().getWidth() / 2
						- textWidth(mainMenu.getOneVSOne().getText()) / 2,
				mainMenu.getOneVSOne().getRect().getY() + mainMenu.getOneVSOne().getRect().getHeight() / 2);
	}

	private void drawMainMenuRects() {
		fill(0);
		noStroke();
		rect(mainMenu.getOneVSComputer().getRect().getX(), mainMenu.getOneVSComputer().getRect().getY(),
				mainMenu.getOneVSComputer().getRect().getWidth(), mainMenu.getOneVSComputer().getRect().getHeight());
		rect(mainMenu.getOneVSOne().getRect().getX(), mainMenu.getOneVSOne().getRect().getY(),
				mainMenu.getOneVSOne().getRect().getWidth(), mainMenu.getOneVSOne().getRect().getHeight());
	}

	private void drawVsComputerMenu() {
		vsComputerMenu.getEasyMode().getRect().setX(width / 2 - vsComputerMenu.getEasyMode().getRect().getWidth() / 2);
		vsComputerMenu.getNormalMode().getRect()
				.setX(width / 2 - vsComputerMenu.getNormalMode().getRect().getWidth() / 2);
		vsComputerMenu.getHardMode().getRect().setX(width / 2 - vsComputerMenu.getHardMode().getRect().getWidth() / 2);
		vsComputerMenu.getMultiHardMode().getRect()
				.setX(width / 2 - vsComputerMenu.getMultiHardMode().getRect().getWidth() / 2);
		vsComputerMenu.getEasyMode().getRect()
				.setY(height / 8 - vsComputerMenu.getEasyMode().getRect().getHeight() / 2);
		vsComputerMenu.getNormalMode().getRect()
				.setY(height / 8 * 3 - vsComputerMenu.getNormalMode().getRect().getHeight() / 2);
		vsComputerMenu.getHardMode().getRect()
				.setY(height / 8 * 5 - vsComputerMenu.getHardMode().getRect().getHeight() / 2);
		vsComputerMenu.getMultiHardMode().getRect()
				.setY(height / 8 * 7 - vsComputerMenu.getMultiHardMode().getRect().getHeight() / 2);
		drawVsComputerMenuRects();
		drawVsComputerMenuTexts();
	}

	private void drawVsComputerMenuTexts() {
		PFont font;
		font = createFont("Arial", 35);
		textFont(font);
		fill(backgroundColor);
		text(vsComputerMenu.getEasyMode().getText(),
				vsComputerMenu.getEasyMode().getRect().getX() + vsComputerMenu.getEasyMode().getRect().getWidth() / 2
						- textWidth(vsComputerMenu.getEasyMode().getText()) / 2,
				vsComputerMenu.getEasyMode().getRect().getY() + vsComputerMenu.getEasyMode().getRect().getHeight() / 2);
		text(vsComputerMenu.getNormalMode().getText(),
				vsComputerMenu.getNormalMode().getRect().getX()
						+ vsComputerMenu.getNormalMode().getRect().getWidth() / 2
						- textWidth(vsComputerMenu.getNormalMode().getText()) / 2,
				vsComputerMenu.getNormalMode().getRect().getY()
						+ vsComputerMenu.getNormalMode().getRect().getHeight() / 2);
		text(vsComputerMenu.getHardMode().getText(),
				vsComputerMenu.getHardMode().getRect().getX() + vsComputerMenu.getHardMode().getRect().getWidth() / 2
						- textWidth(vsComputerMenu.getHardMode().getText()) / 2,
				vsComputerMenu.getHardMode().getRect().getY() + vsComputerMenu.getHardMode().getRect().getHeight() / 2);
		text(vsComputerMenu.getMultiHardMode().getText(),
				vsComputerMenu.getMultiHardMode().getRect().getX()
						+ vsComputerMenu.getMultiHardMode().getRect().getWidth() / 2
						- textWidth(vsComputerMenu.getMultiHardMode().getText()) / 2,
				vsComputerMenu.getMultiHardMode().getRect().getY()
						+ vsComputerMenu.getMultiHardMode().getRect().getHeight() / 2);
	}

	private void drawVsComputerMenuRects() {
		fill(0);
		noStroke();
		rect(vsComputerMenu.getEasyMode().getRect().getX(), vsComputerMenu.getEasyMode().getRect().getY(),
				vsComputerMenu.getEasyMode().getRect().getWidth(), vsComputerMenu.getEasyMode().getRect().getHeight());
		rect(vsComputerMenu.getNormalMode().getRect().getX(), vsComputerMenu.getNormalMode().getRect().getY(),
				vsComputerMenu.getNormalMode().getRect().getWidth(),
				vsComputerMenu.getNormalMode().getRect().getHeight());
		rect(vsComputerMenu.getHardMode().getRect().getX(), vsComputerMenu.getHardMode().getRect().getY(),
				vsComputerMenu.getHardMode().getRect().getWidth(), vsComputerMenu.getHardMode().getRect().getHeight());
		rect(vsComputerMenu.getMultiHardMode().getRect().getX(), vsComputerMenu.getMultiHardMode().getRect().getY(),
				vsComputerMenu.getMultiHardMode().getRect().getWidth(),
				vsComputerMenu.getMultiHardMode().getRect().getHeight());
	}

	private void setupVsComputerMenu() {
		vsComputerMenu.getEasyMode().getRect().setHeight(100);
		vsComputerMenu.getNormalMode().getRect().setHeight(100);
		vsComputerMenu.getHardMode().getRect().setHeight(100);
		vsComputerMenu.getMultiHardMode().getRect().setHeight(100);
		vsComputerMenu.getEasyMode().getRect().setWidth(300);
		vsComputerMenu.getNormalMode().getRect().setWidth(300);
		vsComputerMenu.getHardMode().getRect().setWidth(300);
		vsComputerMenu.getMultiHardMode().getRect().setWidth(300);
	}

	private void setupMainMenu() {
		menuModeMain = true;
		mainMenu.getOneVSOne().getRect().setHeight(100);
		mainMenu.getOneVSComputer().getRect().setHeight(100);
		mainMenu.getOneVSOne().getRect().setWidth(300);
		mainMenu.getOneVSComputer().getRect().setWidth(300);
	}
}
