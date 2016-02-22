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
	int computerMode = 4, ternsPast = 0;
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
	}

	public void draw() {
		background(255);
		if (menuModeMain) {
			drawMainMenu();
		} else if (menuModeVsComputer) {
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
					int size1 = min(height, width)/2 - 50;
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
				int size1 = min(height, width)/4 - 15;
				line(-size1, -size1, size1, size1);
				line(-size1, size1, size1, -size1);
				popMatrix();
				size1 = min(height, width)/2 - 30;
				ellipse((width / 4) * 3, height / 2, size1, size1);
			}
		}
	}

	public void mousePressed() {
		if (menuModeMain) {
			if (pointInShape(mouseX, mouseY, mainMenu.OneVSComputer.rect.x, mainMenu.OneVSComputer.rect.y,
					mainMenu.OneVSComputer.rect.height, mainMenu.OneVSComputer.rect.width)) {
				isVSComputer = true;
				menuModeMain = false;
				playMode = false;
				menuModeVsComputer = true;
			} else if (pointInShape(mouseX, mouseY, mainMenu.OneVSOne.rect.x, mainMenu.OneVSOne.rect.y,
					mainMenu.OneVSOne.rect.height, mainMenu.OneVSOne.rect.width)) {
				isVSComputer = false;
				menuModeMain = false;
				playMode = true;
				menuModeVsComputer = false;
			}
		} else if (menuModeVsComputer) {
			if (pointInShape(mouseX, mouseY, vsComputerMenu.easyMode.rect.x, vsComputerMenu.easyMode.rect.y,
					vsComputerMenu.easyMode.rect.height, vsComputerMenu.easyMode.rect.width)) {
				isVSComputer = true;
				menuModeMain = false;
				playMode = true;
				menuModeVsComputer = false;
				computerMode = 1;
			} else if (pointInShape(mouseX, mouseY, vsComputerMenu.normalMode.rect.x, vsComputerMenu.normalMode.rect.y,
					vsComputerMenu.normalMode.rect.height, vsComputerMenu.normalMode.rect.width)) {
				isVSComputer = true;
				menuModeMain = false;
				playMode = true;
				menuModeVsComputer = false;
				computerMode = 2;
			} else if (pointInShape(mouseX, mouseY, vsComputerMenu.hardMode.rect.x, vsComputerMenu.hardMode.rect.y,
					vsComputerMenu.hardMode.rect.height, vsComputerMenu.hardMode.rect.width)) {
				isVSComputer = true;
				menuModeMain = false;
				playMode = true;
				menuModeVsComputer = false;
				computerMode = 3;
			} else if (pointInShape(mouseX, mouseY, vsComputerMenu.multiHardMode.rect.x,
					vsComputerMenu.multiHardMode.rect.y, vsComputerMenu.multiHardMode.rect.height,
					vsComputerMenu.multiHardMode.rect.width)) {
				isVSComputer = true;
				menuModeMain = false;
				playMode = true;
				menuModeVsComputer = false;
				computerMode = 4;
			}
		} else if (playMode) {
			if (!isThereWiner && !isOverNoWiner) {
				for (int i = 0; i < board.rect[0].length; i++) {
					for (int g = 0; g < board.rect.length; g++) {
						if (pointInShape(mouseX, mouseY, board.rect[g][i].x, board.rect[g][i].y,
								board.rect[g][i].height, board.rect[g][i].width) && place[g][i].isThereXOrO == false) {
							place[g][i].isThereXOrO = true;
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
								place[g][i].isThereX = ternX;
								ternX = !ternX;
							} else {
								place[g][i].isThereX = true;
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
		int tempNum = 0;
		for (int i = 0; i < board.rect[0].length; i++) {
			for (int g = 0; g < board.rect.length; g++) {
				if (place[g][i].isThereXOrO) {
					tempNum++;
				}
			}
		}
		if (tempNum < 9) {
			if (!place[randomNumTemp[0]][randomNumTemp[1]].isThereXOrO) {
				place[randomNumTemp[0]][randomNumTemp[1]].isThereXOrO = true;
				place[randomNumTemp[0]][randomNumTemp[1]].isThereX = false;
			} else {
				coputerPlaceCacoletionEasy();
			}
		}
	}

	private void coputerPlaceCacoletionNormal() {
		int[] randomNumTemp = { (int) random(0, 2.999999f), (int) random(0, 2.999999f) };
		int tempNum = 0;
		for (int i = 0; i < board.rect[0].length; i++) {
			for (int g = 0; g < board.rect.length; g++) {
				if (place[g][i].isThereXOrO) {
					tempNum++;
				}
			}
		}
		if (tempNum < 9) {
			if (!blockAndWin(true)) {
				if (!blockAndWin(false)) {
					if (!place[randomNumTemp[0]][randomNumTemp[1]].isThereXOrO) {
						place[randomNumTemp[0]][randomNumTemp[1]].isThereXOrO = true;
						place[randomNumTemp[0]][randomNumTemp[1]].isThereX = false;
					} else {
						coputerPlaceCacoletionEasy();
					}
				}
			}
		}
	}

	private int checkXForBlock() {
		for (int i = 0; i < place.length; i++) {
			int XNum = 0, ONum = 0;
			for (int g = 0; g < place[0].length; g++) {
				if (place[g][i].isThereXOrO) {
					if (place[g][i].isThereX) {
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
				if (place[i][g].isThereXOrO) {
					if (place[i][g].isThereX) {
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
			if (place[i][i].isThereXOrO) {
				if (place[i][i].isThereX) {
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
			if (place[g][i].isThereXOrO) {
				if (place[g][i].isThereX) {
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
				if (!place[g][onX].isThereXOrO) {
					place[g][onX].isThereXOrO = true;
					place[g][onX].isThereX = false;
				}
			}
			return true;
		} else if (onY != -1) {
			for (int g = 0; g < place.length; g++) {
				if (!place[onY][g].isThereXOrO) {
					place[onY][g].isThereXOrO = true;
					place[onY][g].isThereX = false;
				}
			}
			return true;
		} else if (onXY1) {
			for (int i = 0; i < place.length; i++) {
				if (!place[i][i].isThereXOrO) {
					place[i][i].isThereXOrO = true;
					place[i][i].isThereX = false;
				}
			}
			return true;
		} else if (onXY2) {
			int g = 2;
			for (int i = 0; i < place.length;) {
				if (!place[g][i].isThereXOrO) {
					place[g][i].isThereXOrO = true;
					place[g][i].isThereX = false;
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
				if (place[g][i].isThereXOrO) {
					if (place[g][i].isThereX) {
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
				if (place[i][g].isThereXOrO) {
					if (place[i][g].isThereX) {
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
			if (place[i][i].isThereXOrO) {
				if (place[i][i].isThereX) {
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
			if (place[g][i].isThereXOrO) {
				if (place[g][i].isThereX) {
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
		for (int i = 0; i < board.rect[0].length; i += 2) {
			for (int g = 0; g < board.rect.length; g += 2) {
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
								place[1][1].isThereXOrO = true;
								place[1][1].isThereX = false;
								way1 = true;
								return;
							} else {
								for (int i = 0; i < board.rect[0].length; i += 2) {
									for (int g = 0; g < board.rect.length; g += 2) {
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
								if (!place[randomNumTemp[0]][randomNumTemp[1]].isThereXOrO) {
									place[randomNumTemp[0]][randomNumTemp[1]].isThereXOrO = true;
									place[randomNumTemp[0]][randomNumTemp[1]].isThereX = false;
								} else {
									coputerPlaceCacoletionHard();
									return;
								}
							}
						} else {
							if (lastPlaceForPlayer[3][0] != 1 && lastPlaceForPlayer[3][1] != 1
									&& (ternsPast == 1 || ternsPast == 2)) {
								if ((place[0][0].isThereXOrO && place[0][0].isThereX && place[2][2].isThereXOrO
										&& place[2][2].isThereX)
										|| (place[2][0].isThereXOrO && place[2][0].isThereX && place[0][2].isThereXOrO
												&& place[0][2].isThereX && ternsPast == 2)) {
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
								place[placeneed1[0]][placeneed1[1]].isThereXOrO = true;
								place[placeneed1[0]][placeneed1[1]].isThereX = false;
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
								place[1][1].isThereXOrO = true;
								place[1][1].isThereX = false;
								way1 = true;
								return;
							} else {
								for (int i = 0; i < board.rect[0].length; i += 2) {
									for (int g = 0; g < board.rect.length; g += 2) {
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
								if (!place[randomNumTemp[0]][randomNumTemp[1]].isThereXOrO) {
									place[randomNumTemp[0]][randomNumTemp[1]].isThereXOrO = true;
									place[randomNumTemp[0]][randomNumTemp[1]].isThereX = false;
								} else {
									coputerPlaceCacoletionMultiHard();
									return;
								}
							}
						} else {
							if ((lastPlaceForPlayer[3][0] != 1 && lastPlaceForPlayer[3][1] != 1
									&& (ternsPast == 1 || ternsPast == 2))
									&& (place[0][0].isThereXOrO && place[0][0].isThereX && place[2][2].isThereXOrO
											&& place[2][2].isThereX)
									|| (place[2][0].isThereXOrO && place[2][0].isThereX && place[0][2].isThereXOrO
											&& place[0][2].isThereX && ternsPast == 2)) {
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
								place[placeneed1[0]][placeneed1[1]].isThereXOrO = true;
								place[placeneed1[0]][placeneed1[1]].isThereX = false;
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
								place[placeneed1[0]][placeneed1[1]].isThereXOrO = true;
								place[placeneed1[0]][placeneed1[1]].isThereX = false;
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
									place[placeneed1[0]][placeneed1[1]].isThereXOrO = true;
									place[placeneed1[0]][placeneed1[1]].isThereX = false;
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
			if (!place[randomNumTemp1][randomNumTemp2].isThereXOrO) {
				place[randomNumTemp1][randomNumTemp2].isThereXOrO = true;
				place[randomNumTemp1][randomNumTemp2].isThereX = false;
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
		for (int i = 0; i < board.rect[0].length; i++) {
			for (int g = 0; g < board.rect.length; g++) {
				rect(board.rect[g][i].x, board.rect[g][i].y, board.rect[g][i].width, board.rect[g][i].height);
			}
		}
	}

	private void drawXAndO() {
		for (int i = 0; i < place[0].length; i++) {
			for (int g = 0; g < place.length; g++) {
				if (place[g][i].isThereXOrO) {
					int[] xy = new int[2];
					xy = placeLocCacoletion(g, i);
					if (place[g][i].isThereX) {
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
		int size1 = min(board.rect[0][0].height, board.rect[0][0].width) / 2 - 15;
		line(-size1, -size1, size1, size1);
		line(-size1, size1, size1, -size1);
		popMatrix();
	}

	private void drawO(int x1, int y1) {
		fill(255);
		stroke(0);
		strokeWeight(10);
		int size1 = min(board.rect[0][0].height, board.rect[0][0].width) - 30;
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
		for (int i = 0; i < board.rect[0].length; i++) {
			for (int g = 0; g < board.rect.length; g++) {
				board.rect[g][i].height = height / 3;
				board.rect[g][i].width = width / 3;
				board.rect[g][i].x = width / 3 * g;
				board.rect[g][i].y = height / 3 * i;
			}
		}
	}

	private void checkOverNoWiner() {
		if (!isThereWiner) {
			int numTemp = 0;
			for (int i = 0; i < place[0].length; i++) {
				for (int g = 0; g < place.length; g++) {
					if (place[i][g].isThereXOrO == true)
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
				if (place[g][i].isThereXOrO) {
					if (place[g][i].isThereX) {
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
				if (place[i][g].isThereXOrO) {
					if (place[i][g].isThereX) {
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
			if (place[i][g].isThereXOrO) {
				if (place[i][g].isThereX) {
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
			if (place[g][i].isThereXOrO) {
				if (place[g][i].isThereX) {
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
		mainMenu.OneVSOne.rect.x = width / 2 - mainMenu.OneVSOne.rect.width / 2;
		mainMenu.OneVSComputer.rect.x = width / 2 - mainMenu.OneVSComputer.rect.width / 2;
		mainMenu.OneVSOne.rect.y = height / 4 - mainMenu.OneVSOne.rect.height / 2;
		mainMenu.OneVSComputer.rect.y = height / 4 * 3 - mainMenu.OneVSComputer.rect.height / 2;
		drawMainMenuRects();
		drawMainMenuTexts();
	}

	private void drawMainMenuTexts() {
		PFont font;
		font = createFont("Arial", 35);
		textFont(font);
		fill(255);
		text(mainMenu.OneVSComputer.text,
				mainMenu.OneVSComputer.rect.x + mainMenu.OneVSComputer.rect.width / 2
						- textWidth(mainMenu.OneVSComputer.text) / 2,
				mainMenu.OneVSComputer.rect.y + mainMenu.OneVSComputer.rect.height / 2);
		text(mainMenu.OneVSOne.text,
				mainMenu.OneVSOne.rect.x + mainMenu.OneVSOne.rect.width / 2 - textWidth(mainMenu.OneVSOne.text) / 2,
				mainMenu.OneVSOne.rect.y + mainMenu.OneVSOne.rect.height / 2);
	}

	private void drawMainMenuRects() {
		fill(0);
		noStroke();
		rect(mainMenu.OneVSComputer.rect.x, mainMenu.OneVSComputer.rect.y, mainMenu.OneVSComputer.rect.width,
				mainMenu.OneVSComputer.rect.height);
		rect(mainMenu.OneVSOne.rect.x, mainMenu.OneVSOne.rect.y, mainMenu.OneVSOne.rect.width,
				mainMenu.OneVSOne.rect.height);
	}

	private void drawVsComputerMenu() {
		vsComputerMenu.easyMode.rect.x = width / 2 - vsComputerMenu.easyMode.rect.width / 2;
		vsComputerMenu.normalMode.rect.x = width / 2 - vsComputerMenu.normalMode.rect.width / 2;
		vsComputerMenu.hardMode.rect.x = width / 2 - vsComputerMenu.hardMode.rect.width / 2;
		vsComputerMenu.multiHardMode.rect.x = width / 2 - vsComputerMenu.multiHardMode.rect.width / 2;
		vsComputerMenu.easyMode.rect.y = height / 8 - vsComputerMenu.easyMode.rect.height / 2;
		vsComputerMenu.normalMode.rect.y = height / 8 * 3 - vsComputerMenu.normalMode.rect.height / 2;
		vsComputerMenu.hardMode.rect.y = height / 8 * 5 - vsComputerMenu.hardMode.rect.height / 2;
		vsComputerMenu.multiHardMode.rect.y = height / 8 * 7 - vsComputerMenu.multiHardMode.rect.height / 2;
		drawVsComputerMenuRects();
		drawVsComputerMenuTexts();
	}

	private void drawVsComputerMenuTexts() {
		PFont font;
		font = createFont("Arial", 35);
		textFont(font);
		fill(255);
		text(vsComputerMenu.easyMode.text,
				vsComputerMenu.easyMode.rect.x + vsComputerMenu.easyMode.rect.width / 2
						- textWidth(vsComputerMenu.easyMode.text) / 2,
				vsComputerMenu.easyMode.rect.y + vsComputerMenu.easyMode.rect.height / 2);
		text(vsComputerMenu.normalMode.text,
				vsComputerMenu.normalMode.rect.x + vsComputerMenu.normalMode.rect.width / 2
						- textWidth(vsComputerMenu.normalMode.text) / 2,
				vsComputerMenu.normalMode.rect.y + vsComputerMenu.normalMode.rect.height / 2);
		text(vsComputerMenu.hardMode.text,
				vsComputerMenu.hardMode.rect.x + vsComputerMenu.hardMode.rect.width / 2
						- textWidth(vsComputerMenu.hardMode.text) / 2,
				vsComputerMenu.hardMode.rect.y + vsComputerMenu.hardMode.rect.height / 2);
		text(vsComputerMenu.multiHardMode.text,
				vsComputerMenu.multiHardMode.rect.x + vsComputerMenu.multiHardMode.rect.width / 2
						- textWidth(vsComputerMenu.multiHardMode.text) / 2,
				vsComputerMenu.multiHardMode.rect.y + vsComputerMenu.multiHardMode.rect.height / 2);
	}

	private void drawVsComputerMenuRects() {
		fill(0);
		noStroke();
		rect(vsComputerMenu.easyMode.rect.x, vsComputerMenu.easyMode.rect.y, vsComputerMenu.easyMode.rect.width,
				vsComputerMenu.easyMode.rect.height);
		rect(vsComputerMenu.normalMode.rect.x, vsComputerMenu.normalMode.rect.y, vsComputerMenu.normalMode.rect.width,
				vsComputerMenu.normalMode.rect.height);
		rect(vsComputerMenu.hardMode.rect.x, vsComputerMenu.hardMode.rect.y, vsComputerMenu.hardMode.rect.width,
				vsComputerMenu.hardMode.rect.height);
		rect(vsComputerMenu.multiHardMode.rect.x, vsComputerMenu.multiHardMode.rect.y,
				vsComputerMenu.multiHardMode.rect.width, vsComputerMenu.multiHardMode.rect.height);
	}

	private void setupVsComputerMenu() {
		vsComputerMenu.easyMode.rect.height = 100;
		vsComputerMenu.normalMode.rect.height = 100;
		vsComputerMenu.hardMode.rect.height = 100;
		vsComputerMenu.multiHardMode.rect.height = 100;
		vsComputerMenu.easyMode.rect.width = 300;
		vsComputerMenu.normalMode.rect.width = 300;
		vsComputerMenu.hardMode.rect.width = 300;
		vsComputerMenu.multiHardMode.rect.width = 300;
		vsComputerMenu.easyMode.rect.x = width / 2 - vsComputerMenu.easyMode.rect.width / 2;
		vsComputerMenu.normalMode.rect.x = width / 2 - vsComputerMenu.normalMode.rect.width / 2;
		vsComputerMenu.hardMode.rect.x = width / 2 - vsComputerMenu.hardMode.rect.width / 2;
		vsComputerMenu.multiHardMode.rect.x = width / 2 - vsComputerMenu.multiHardMode.rect.width / 2;
		vsComputerMenu.easyMode.rect.y = height / 8 - vsComputerMenu.easyMode.rect.height / 2;
		vsComputerMenu.normalMode.rect.y = height / 8 * 2 - vsComputerMenu.normalMode.rect.height / 2;
		vsComputerMenu.hardMode.rect.y = height / 8 * 4 - vsComputerMenu.hardMode.rect.height / 2;
		vsComputerMenu.multiHardMode.rect.y = height / 8 * 6 - vsComputerMenu.multiHardMode.rect.height / 2;
	}

	private void setupMainMenu() {
		menuModeMain = true;
		mainMenu.OneVSOne.rect.height = 100;
		mainMenu.OneVSComputer.rect.height = 100;
		mainMenu.OneVSOne.rect.width = 300;
		mainMenu.OneVSComputer.rect.width = 300;
		mainMenu.OneVSOne.rect.x = width / 2 - mainMenu.OneVSOne.rect.width / 2;
		mainMenu.OneVSComputer.rect.x = width / 2 - mainMenu.OneVSComputer.rect.width / 2;
		mainMenu.OneVSOne.rect.x = width / 2 - mainMenu.OneVSOne.rect.width / 2;
		mainMenu.OneVSComputer.rect.x = width / 2 - mainMenu.OneVSComputer.rect.width / 2;
		mainMenu.OneVSOne.rect.y = height / 4 - mainMenu.OneVSOne.rect.height / 2;
		mainMenu.OneVSComputer.rect.y = height / 4 * 3 - mainMenu.OneVSComputer.rect.height / 2;
	}
}
