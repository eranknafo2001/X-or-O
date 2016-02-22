package xoro.gameBoard;

public class Board {
	public Rect[][] rect = new Rect[3][3];

	public Board() {
		for (int i = 0; i < rect.length; i++) {
			for (int g = 0; g < rect[0].length; g++) {
				rect[i][g] = new Rect();
			}
		}
	}
}
