package xoro.global;

import processing.core.PApplet;
import xoro.XOrO;

public class globalButton {
	private String text;
	private Rect rect;
	private int[] textPosion = new int[2];
	private PApplet parent;

	public boolean click(Actions actions) {
		if (getRect().pointInShape(parent.mouseX, parent.mouseY)) {
			actions.function();
			return true;
		} else {
			return false;
		}
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public Rect getRect() {
		return rect;
	}

	public globalButton(String text1, Rect rect1, XOrO parent) {
		rect = rect1;
		text = text1;
		this.parent = parent;
		recheckTextPosion();
	}

	public int getTextPosion(int i) {
		return textPosion[i];
	}

	public void recheckTextPosion() {
		textPosion = new int[] { getRect().getX() + getRect().getWidth() / 2 - (int) parent.textWidth(getText()) / 2,
				getRect().getY() + getRect().getHeight() / 2 };
	}
}
