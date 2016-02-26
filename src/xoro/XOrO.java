package xoro;

import processing.core.PApplet;
import xoro.gameBoard.Board;
import xoro.gameBoard.gamePlace;
import xoro.global.Actions;
import xoro.menus.MainMenu;
import xoro.menus.NoWinerMenu;
import xoro.menus.OWinMenu;
import xoro.menus.XWinMenu;
import xoro.menus.vsComputerMenu;

public class XOrO extends PApplet {

	public static void main(String _args[]) {
		PApplet.main(new String[] { xoro.XOrO.class.getName() });
	}

	private MainMenu mainMenu;
	private OWinMenu OWinMenu;
	private XWinMenu XWinMenu;
	private NoWinerMenu noWinerMenu;
	private vsComputerMenu vsComputerMenu;
	private gamePlace[][] place = new gamePlace[3][3];
	private Board board = new Board();
	private boolean ternX = true, isXWiner = true, isThereWiner = false, isOverNoWiner = false, isVSComputerMenu = true,
			way1 = false, menuModeMain = false, menuModeVsComputer = false, playMode = true;
	private int computerMode = 4, ternsPast = 0, backgroundColor, notBackgroundColor, crazyMode = 0;
	private int[][] lastPlaceForPlayer = new int[4][2];

	private Actions oneVsOneAction = new Actions() {
		@Override
		public void function() {
			setVSComputerMenu(false);
			setMenuModeMain(false);
			setPlayMode(true);
			setMenuModeVsComputer(false);
		}
	};
	private Actions oneVsComputerAction = new Actions() {
		@Override
		public void function() {
			setVSComputerMenu(true);
			setMenuModeMain(false);
			setPlayMode(false);
			setMenuModeVsComputer(true);
		}
	};
	private Actions crazyModeAction = new Actions() {
		@Override
		public void function() {
			setCrazyMode(getCrazyMode() + 1);
			switch (getCrazyMode()) {
			case 3:
				setCrazyMode(0);
				mainMenu.getCrazyMode().setText("Crazy Mode : OFF");
				break;
			case 1:
				mainMenu.getCrazyMode().setText("Crazy Mode : Level 1");
				break;
			case 2:
				mainMenu.getCrazyMode().setText("Crazy Mode : Level 2");
				break;
			}
		}
	};
	private Actions easyModeAction = new Actions() {

		@Override
		public void function() {
			setVSComputerMenu(true);
			setMenuModeMain(false);
			setPlayMode(true);
			setMenuModeVsComputer(false);
			computerMode = 1;
		}
	};
	private Actions NormalModeAction = new Actions() {

		@Override
		public void function() {
			setVSComputerMenu(true);
			setMenuModeMain(false);
			setPlayMode(true);
			setMenuModeVsComputer(false);
			computerMode = 2;
		}
	};

	private Actions hardModeAction = new Actions() {

		@Override
		public void function() {
			setVSComputerMenu(true);
			setMenuModeMain(false);
			setPlayMode(true);
			setMenuModeVsComputer(false);
			computerMode = 3;
		}
	};

	private Actions multiHardMode = new Actions() {

		@Override
		public void function() {
			setVSComputerMenu(true);
			setMenuModeMain(false);
			setPlayMode(true);
			setMenuModeVsComputer(false);
			computerMode = 4;
		}
	};

	public void settings() {
		size(600, 600);
	}

	public void setup() {
		mainMenu = new MainMenu(this);
		vsComputerMenu = new vsComputerMenu(this);
		OWinMenu = new OWinMenu(this);
		XWinMenu = new XWinMenu(this);
		noWinerMenu = new NoWinerMenu(this);
		setCrazyMode(0);
		ternX = true;
		isXWiner = true;
		isThereWiner = false;
		isOverNoWiner = false;
		setVSComputerMenu(true);
		way1 = false;
		menuModeMain = false;
		setMenuModeVsComputer(false);
		setPlayMode(true);
		surface.setResizable(true);
		for (int i = 0; i < place.length; i++) {
			for (int g = 0; g < place[0].length; g++) {
				place[i][g] = new gamePlace();
			}
		}
		mainMenu.setup();
		vsComputerMenu.setup();
		XWinMenu.setup();
		noWinerMenu.setup();
		OWinMenu.setup();
		resetBackgroundColor();
	}

	public void draw() {
		if (getCrazyMode() == 2) {
			resetBackgroundColor();
		}
		background(getBackgroundColor());
		if (isMenuModeMain()) {
			background(getBackgroundColor());
			mainMenu.draw();
		} else if (isMenuModeVsComputer()) {
			background(getBackgroundColor());
			vsComputerMenu.draw();
		} else if (isPlayMode()) {
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
					XWinMenu.draw();
				} else {
					OWinMenu.draw();
				}
			} else if (isOverNoWiner) {
				noWinerMenu.draw();
			}
		}
	}

	public void mousePressed() {
		if (getCrazyMode() == 1) {
			resetBackgroundColor();
		}
		if (isMenuModeMain()) {
			if (mainMenu.getCrazyMode().click(crazyModeAction))
				return;
			if (mainMenu.getOneVSComputer().click(oneVsComputerAction))
				return;
			mainMenu.getOneVSOne().click(oneVsOneAction);
		} else if (isMenuModeVsComputer()) {
			if (vsComputerMenu.getEasyMode().click(easyModeAction))
				return;
			if (vsComputerMenu.getNormalMode().click(NormalModeAction))
				return;
			if (vsComputerMenu.getHardMode().click(hardModeAction))
				return;
			vsComputerMenu.getMultiHardMode().click(multiHardMode);
		} else if (isPlayMode()) {
			if (!isThereWiner && !isOverNoWiner) {
				for (int i = 0; i < board.getRect0Length(); i++) {
					for (int g = 0; g < board.getRectLength(); g++) {
						if (board.getRect(g, i).pointInShape(mouseX, mouseY) && place[g][i].getIsThereXOrO() == false) {
							place[g][i].setThereXOrO(true);
							for (int h = 0; h < lastPlaceForPlayer.length; h++) {
								if (h != 3) {
									lastPlaceForPlayer[h][0] = lastPlaceForPlayer[h + 1][0];
									lastPlaceForPlayer[h][1] = lastPlaceForPlayer[h + 1][1];
								} else {
									lastPlaceForPlayer[3][0] = g;
									lastPlaceForPlayer[3][1] = i;
								}
							}
							ternsPast++;
							if (!isVSComputerMenu()) {
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
				if (isOverNoWiner) {
					if (noWinerMenu.getBack().click(new Actions() {
						@Override
						public void function() {
							setup();
						}
					})) {
						return;
					} else {
						restartGame();
					}
				} else if (isXWiner) {
					if (XWinMenu.getBack().click(new Actions() {
						@Override
						public void function() {
							setup();
						}
					})) {
						return;
					} else {
						restartGame();
					}
				} else {
					if (OWinMenu.getBack().click(new Actions() {
						@Override
						public void function() {
							setup();
						}
					})) {
						return;
					} else {
						restartGame();
					}
				}
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
								if (isPlaceToPutInCornerAndMid()) {
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

	private boolean isPlaceToPutInCornerAndMid() {
		int tempNum = 0;
		for (int i = 0; i < board.getRect0Length(); i += 2) {
			for (int g = 0; g < board.getRectLength(); g += 2) {
				if (place[i][g].getIsThereXOrO()) {
					tempNum++;
				}
			}
		}
		if (place[1][1].getIsThereXOrO()) {
			tempNum++;
		}
		if (tempNum >= 5) {
			return false;
		} else {
			return true;
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

	private void drawBoard() {
		fill(getBackgroundColor());
		stroke(getNotBackgroundColor());
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
		fill(getNotBackgroundColor());
		stroke(getNotBackgroundColor());
		strokeWeight(10);
		int size1 = min(board.getRect(0, 0).getHeight(), board.getRect(0, 0).getWidth()) / 2 - 15;
		line(-size1, -size1, size1, size1);
		line(-size1, size1, size1, -size1);
		popMatrix();
	}

	private void drawO(int x1, int y1) {
		fill(getBackgroundColor());
		stroke(getNotBackgroundColor());
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

	private void resetBackgroundColor() {
		int R = (int) random(0f, 255.9999999999999999999999f), B = (int) random(0f, 255.9999999999999999999999f),
				G = (int) random(0f, 255.9999999999999999999999f);
		setBackgroundColor(color(R, G, B));
		setNotBackgroundColor(color(255 - R, 255 - G, 255 - B));
	}

	public int getBackgroundColor() {
		return backgroundColor;
	}

	private void setBackgroundColor(int backgroundColor) {
		this.backgroundColor = backgroundColor;
	}

	public int getNotBackgroundColor() {
		return notBackgroundColor;
	}

	private void setNotBackgroundColor(int notBackgroundColor) {
		this.notBackgroundColor = notBackgroundColor;
	}

	public void setMenuModeMain(boolean menuModeMain) {
		this.menuModeMain = menuModeMain;
	}

	private boolean isMenuModeMain() {
		return menuModeMain;
	}

	public boolean isVSComputerMenu() {
		return isVSComputerMenu;
	}

	public void setVSComputerMenu(boolean isVSComputerMenu) {
		this.isVSComputerMenu = isVSComputerMenu;
	}

	public boolean isPlayMode() {
		return playMode;
	}

	public void setPlayMode(boolean playMode) {
		this.playMode = playMode;
	}

	public boolean isMenuModeVsComputer() {
		return menuModeVsComputer;
	}

	public void setMenuModeVsComputer(boolean menuModeVsComputer) {
		this.menuModeVsComputer = menuModeVsComputer;
	}

	public int getCrazyMode() {
		return crazyMode;
	}

	public void setCrazyMode(int crazyMode) {
		this.crazyMode = crazyMode;
	}
}
