package xoro.menus;

import processing.core.PApplet;
import processing.core.PFont;
import xoro.XOrO;
import xoro.global.Rect;
import xoro.global.globalButton;

public class NoWinerMenu {
	private globalButton back;
	private XOrO XOrO;

	public NoWinerMenu(XOrO XOrO) {
		this.XOrO = XOrO;
		setBack(new globalButton("Back To The Main Menu", new Rect(), XOrO));
	}

	public void draw() {
		drawXAndO();
		drawButtons();
	}

	private void drawButtons() {
		getBack().getRect().setY(XOrO.height - back.getRect().getHeight());
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

	private void drawXAndO() {
		XOrO.fill(XOrO.getBackgroundColor());
		XOrO.stroke(XOrO.getNotBackgroundColor());
		XOrO.strokeWeight(10);
		XOrO.pushMatrix();
		XOrO.translate(XOrO.width / 4, XOrO.height / 2);
		int size1 = PApplet.min(XOrO.height, XOrO.width) / 4 - 15;
		XOrO.line(-size1, -size1, size1, size1);
		XOrO.line(-size1, size1, size1, -size1);
		XOrO.popMatrix();
		size1 = PApplet.min(XOrO.height, XOrO.width) / 2 - 30;
		XOrO.ellipse((XOrO.width / 4) * 3, XOrO.height / 2, size1, size1);
	}

	public void setup() {
		getBack().getRect().setHeight(100);
		getBack().getRect().setX(0);
	}

	public globalButton getBack() {
		return back;
	}

	public void setBack(globalButton back) {
		this.back = back;
	}
}
