package xoro.menus;

import processing.core.PApplet;
import processing.core.PFont;
import xoro.XOrO;
import xoro.global.Rect;
import xoro.global.globalButton;

public class OWinMenu {
	private globalButton back;
	private XOrO XOrO;

	public OWinMenu(XOrO XOrO) {
		this.XOrO = XOrO;
		setBack(new globalButton("Back To The Main Menu", new Rect(), XOrO));
	}

	public void setup() {
		getBack().getRect().setHeight(100);
		getBack().getRect().setX(0);
	}

	public void draw() {
		drawO();
		drawButtons();
	}

	private void drawButtons() {
		getBack().getRect().setY(XOrO.height - getBack().getRect().getHeight());
		getBack().getRect().setWidth(XOrO.width);
		getBack().recheckTextPosion();
		drawRects();
		drawTexts();
	}

	private void drawTexts() {
		PFont font;
		font = XOrO.createFont("Arial", 35);
		XOrO.textFont(font);
		XOrO.fill(XOrO.getBackgroundColor());
		XOrO.text(getBack().getText(), getBack().getTextPosion(0), getBack().getTextPosion(1));
	}

	private void drawRects() {
		XOrO.fill(XOrO.getNotBackgroundColor());
		XOrO.noStroke();
		XOrO.rect(getBack().getRect().getX(), getBack().getRect().getY(), getBack().getRect().getWidth(),
				getBack().getRect().getHeight());
	}

	public void drawO() {
		XOrO.fill(XOrO.getBackgroundColor());
		XOrO.stroke(XOrO.getNotBackgroundColor());
		XOrO.strokeWeight(10);
		int size1 = PApplet.min(XOrO.height, XOrO.width) - 125;
		XOrO.ellipse(XOrO.width / 2, (XOrO.height - getBack().getRect().getHeight()) / 2, size1, size1);
	}

	public globalButton getBack() {
		return back;
	}

	public void setBack(globalButton back) {
		this.back = back;
	}
}
