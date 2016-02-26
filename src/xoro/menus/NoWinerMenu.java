package xoro.menus;

import processing.core.PApplet;
import processing.core.PFont;
import xoro.XOrO;
import xoro.global.Rect;
import xoro.global.globalButton;

public class NoWinerMenu {
	private globalButton back;
	private XOrO XOrO;
	private PApplet parent;

	public NoWinerMenu(XOrO XOrO) {
		this.XOrO = XOrO;
		parent = XOrO;
		setBack(new globalButton("Back To The Main Menu", new Rect(), XOrO));
	}

	public void draw() {
		drawXAndO();
		drawButtons();
	}

	private void drawButtons() {
		getBack().getRect().setY(parent.height - back.getRect().getHeight());
		getBack().getRect().setWidth(parent.width);
		getBack().recheckTextPosion();
		drawRects();
		drawTexts();
	}

	private void drawTexts() {
		PFont font;
		font = parent.createFont("Arial", 35);
		parent.textFont(font);
		parent.fill(XOrO.getBackgroundColor());
		parent.text(getBack().getText(), getBack().getTextPosion(0), getBack().getTextPosion(1));
	}

	private void drawRects() {
		parent.fill(XOrO.getNotBackgroundColor());
		parent.noStroke();
		parent.rect(getBack().getRect().getX(), getBack().getRect().getY(), getBack().getRect().getWidth(),
				getBack().getRect().getHeight());
	}

	private void drawXAndO() {
		parent.fill(XOrO.getBackgroundColor());
		parent.stroke(XOrO.getNotBackgroundColor());
		parent.strokeWeight(10);
		parent.pushMatrix();
		parent.translate(parent.width / 4, parent.height / 2);
		int size1 = PApplet.min(parent.height, parent.width) / 4 - 15;
		parent.line(-size1, -size1, size1, size1);
		parent.line(-size1, size1, size1, -size1);
		parent.popMatrix();
		size1 = PApplet.min(parent.height, parent.width) / 2 - 30;
		parent.ellipse((parent.width / 4) * 3, parent.height / 2, size1, size1);
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
