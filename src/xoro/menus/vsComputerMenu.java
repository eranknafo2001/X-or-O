package xoro.menus;

import processing.core.PFont;
import xoro.XOrO;
import xoro.global.Rect;
import xoro.global.globalButton;

public class vsComputerMenu {
	private globalButton easyMode, normalMode, hardMode, multiHardMode;
	private XOrO XOrO;

	public vsComputerMenu(XOrO XOrO) {
		this.XOrO = XOrO;
		easyMode = new globalButton("Easy Mode", new Rect(), XOrO);
		normalMode = new globalButton("Normal Mode", new Rect(), XOrO);
		hardMode = new globalButton("Hard Mode", new Rect(), XOrO);
		multiHardMode = new globalButton("Multi Hard Mode", new Rect(), XOrO);
	}

	public globalButton getEasyMode() {
		return easyMode;
	}

	public globalButton getNormalMode() {
		return normalMode;
	}

	public globalButton getHardMode() {
		return hardMode;
	}

	public globalButton getMultiHardMode() {
		return multiHardMode;
	}

	public void draw() {
		getEasyMode().getRect().setX(XOrO.width / 2 - getEasyMode().getRect().getWidth() / 2);
		getNormalMode().getRect().setX(XOrO.width / 2 - getNormalMode().getRect().getWidth() / 2);
		getHardMode().getRect().setX(XOrO.width / 2 - getHardMode().getRect().getWidth() / 2);
		getMultiHardMode().getRect().setX(XOrO.width / 2 - getMultiHardMode().getRect().getWidth() / 2);
		getEasyMode().getRect().setY(XOrO.height / 8 - getEasyMode().getRect().getHeight() / 2);
		getNormalMode().getRect().setY(XOrO.height / 8 * 3 - getNormalMode().getRect().getHeight() / 2);
		getHardMode().getRect().setY(XOrO.height / 8 * 5 - getHardMode().getRect().getHeight() / 2);
		getMultiHardMode().getRect().setY(XOrO.height / 8 * 7 - getMultiHardMode().getRect().getHeight() / 2);
		getEasyMode().recheckTextPosion();
		getNormalMode().recheckTextPosion();
		getHardMode().recheckTextPosion();
		getMultiHardMode().recheckTextPosion();
		drawRects();
		drawTexts();
	}

	private void drawTexts() {
		PFont font;
		font = XOrO.createFont("Arial", 35);
		XOrO.textFont(font);
		XOrO.fill(XOrO.getBackgroundColor());
		XOrO.text(getEasyMode().getText(), getEasyMode().getTextPosion(0), getEasyMode().getTextPosion(1));
		XOrO.text(getNormalMode().getText(), getNormalMode().getTextPosion(0), getNormalMode().getTextPosion(1));
		XOrO.text(getHardMode().getText(), getHardMode().getTextPosion(0), getHardMode().getTextPosion(1));
		XOrO.text(getMultiHardMode().getText(), getMultiHardMode().getTextPosion(0),
				getMultiHardMode().getTextPosion(1));
	}

	private void drawRects() {
		XOrO.fill(XOrO.getNotBackgroundColor());
		XOrO.noStroke();
		XOrO.rect(getEasyMode().getRect().getX(), getEasyMode().getRect().getY(), getEasyMode().getRect().getWidth(),
				getEasyMode().getRect().getHeight());
		XOrO.rect(getNormalMode().getRect().getX(), getNormalMode().getRect().getY(),
				getNormalMode().getRect().getWidth(), getNormalMode().getRect().getHeight());
		XOrO.rect(getHardMode().getRect().getX(), getHardMode().getRect().getY(), getHardMode().getRect().getWidth(),
				getHardMode().getRect().getHeight());
		XOrO.rect(getMultiHardMode().getRect().getX(), getMultiHardMode().getRect().getY(),
				getMultiHardMode().getRect().getWidth(), getMultiHardMode().getRect().getHeight());
	}

	public void setup() {
		getEasyMode().getRect().setHeight(100);
		getNormalMode().getRect().setHeight(100);
		getHardMode().getRect().setHeight(100);
		getMultiHardMode().getRect().setHeight(100);
		getEasyMode().getRect().setWidth(300);
		getNormalMode().getRect().setWidth(300);
		getHardMode().getRect().setWidth(300);
		getMultiHardMode().getRect().setWidth(300);
	}
}
