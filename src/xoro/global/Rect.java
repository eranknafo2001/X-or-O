package xoro.global;

public class Rect {
	private int x = 0;
	private int y = 0;
	private int width = 0;
	private int height = 0;

	public Rect(int x1, int y1, int width1, int height1) {
		x = x1;
		y = y1;
		width = width1;
		height = height1;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}

	public void setX(int x) {
		this.x = x;
	}

	public void setY(int y) {
		this.y = y;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public void setHeight(int height) {
		this.height = height;
	}
}
