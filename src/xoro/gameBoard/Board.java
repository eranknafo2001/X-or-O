package xoro.gameBoard;

import xoro.global.Rect;

public class Board {
	private Rect[][] rect = new Rect[3][3];

	public Rect getRect(int x, int y) {
		return rect[x][y];
	}

	public int getRect0Length() {
		return rect[0].length;
	}

	public int getRectLength() {
		return rect.length;
	}

	public Board() {
		for (int i = 0; i < getRectLength(); i++) {
			for (int g = 0; g < getRect0Length(); g++) {
				rect[i][g] = new Rect(0, 0, 0, 0);
			}
		}
	}
}
