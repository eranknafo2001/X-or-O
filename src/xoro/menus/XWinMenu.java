package xoro.menus;

import processing.core.PApplet;
import processing.core.PFont;
import xoro.XOrO;
import xoro.global.Rect;
import xoro.global.globalButton;

public class XWinMenu {
	private globalButton back;
	private XOrO XOrO;
	private PApplet parent;

	public XWinMenu(XOrO XOrO) {
		this.XOrO = XOrO;
		parent = XOrO;
		setBack(new globalButton("Back To The Main Menu", new Rect(), XOrO));
	}

	public void setup() {
		getBack().getRect().setHeight(100);
		getBack().getRect().setX(0);
	}

	public void draw() {
		drawX();
		drawButtons();
	}

	private void drawButtons() {
		getBack().getRect().setY(parent.height - getBack().getRect().getHeight());
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

	public void drawX() {
		parent.pushMatrix();
		parent.translate(parent.width / 2, (parent.height - getBack().getRect().getHeight()) / 2);
		parent.fill(XOrO.getNotBackgroundColor());
		parent.stroke(XOrO.getNotBackgroundColor());
		parent.strokeWeight(10);
		int size1 = PApplet.min(parent.height, parent.width) / 2 - 75;
		parent.line(-size1, -size1, size1, size1);
		parent.line(-size1, size1, size1, -size1);
		parent.popMatrix();
	}

	public globalButton getBack() {
		return back;
	}

	public void setBack(globalButton back) {
		this.back = back;
	}
}
