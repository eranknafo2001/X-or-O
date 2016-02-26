package xoro.menus;

import processing.core.PApplet;
import processing.core.PFont;
import xoro.XOrO;
import xoro.global.Rect;
import xoro.global.globalButton;

public class MainMenu {
	private globalButton OneVSOne, OneVSComputer, crazyMode;
	private String mainMenu = "Main Menu";
	private PApplet parent;
	private XOrO XOrO;

	

	
	public MainMenu(XOrO XOrO) {
		parent = XOrO;
		this.XOrO = XOrO;
		OneVSOne = new globalButton("1 VS 1", new Rect(), XOrO);
		OneVSComputer = new globalButton("1 VS Computer", new Rect(), XOrO);
		crazyMode = new globalButton("Crazy Mode", new Rect(), XOrO);
	}

	public globalButton getOneVSOne() {
		return OneVSOne;
	}

	public globalButton getOneVSComputer() {
		return OneVSComputer;
	}

	public String getMainMenu() {
		return mainMenu;
	}

	public globalButton getCrazyMode() {
		return crazyMode;
	}

	public void draw() {
		getOneVSOne().getRect().setX(parent.width / 2 - getOneVSOne().getRect().getWidth() / 2);
		getOneVSComputer().getRect().setX(parent.width / 2 - getOneVSComputer().getRect().getWidth() / 2);
		getOneVSOne().getRect().setY(parent.height / 4 * 2 - getOneVSOne().getRect().getHeight() / 2);
		getOneVSComputer().getRect().setY(parent.height / 4 * 3 - getOneVSComputer().getRect().getHeight() / 2);
		getCrazyMode().getRect().setX(0);
		getCrazyMode().getRect().setY(0);
		getOneVSOne().recheckTextPosion();
		getOneVSComputer().recheckTextPosion();
		getCrazyMode().recheckTextPosion();
		drawRects();
		drawTexts();
	}

	private void drawTexts() {
		PFont font;
		font = parent.createFont("Arial", 35);
		parent.textFont(font);
		parent.fill(XOrO.getNotBackgroundColor());
		parent.text(getMainMenu(), parent.width / 2 - parent.textWidth(getMainMenu()) / 2, parent.height / 4);
		parent.fill(XOrO.getBackgroundColor());
		parent.text(getOneVSComputer().getText(), getOneVSComputer().getTextPosion(0),
				getOneVSComputer().getTextPosion(1));
		parent.text(getOneVSOne().getText(), getOneVSOne().getTextPosion(0), getOneVSOne().getTextPosion(1));
		parent.text(getCrazyMode().getText(), getCrazyMode().getTextPosion(0), getCrazyMode().getTextPosion(1));
	}

	private void drawRects() {
		parent.fill(XOrO.getNotBackgroundColor());
		parent.noStroke();
		parent.rect(getOneVSComputer().getRect().getX(), getOneVSComputer().getRect().getY(),
				getOneVSComputer().getRect().getWidth(), getOneVSComputer().getRect().getHeight());
		parent.rect(getOneVSOne().getRect().getX(), getOneVSOne().getRect().getY(), getOneVSOne().getRect().getWidth(),
				getOneVSOne().getRect().getHeight());
		parent.rect(getCrazyMode().getRect().getX(), getCrazyMode().getRect().getY(),
				getCrazyMode().getRect().getWidth(), getCrazyMode().getRect().getHeight());
	}

	public void setup() {
		XOrO.setMenuModeMain(true);
		getOneVSOne().getRect().setHeight(100);
		getOneVSComputer().getRect().setHeight(100);
		getOneVSOne().getRect().setWidth(300);
		getOneVSComputer().getRect().setWidth(300);
		getCrazyMode().getRect().setWidth(400);
		getCrazyMode().getRect().setHeight(100);
		getCrazyMode().setText("Crazy Mode : OFF");
	}

}
