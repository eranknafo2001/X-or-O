package xoro.menus;

import xoro.global.Rect;
import xoro.global.globalButton;

public class vsComputerMenu {
	private Rect easyModeRect = new Rect(0, 0, 0, 0);
	private globalButton easyMode = new globalButton("Easy Mode", easyModeRect);
	private Rect normalModeRect = new Rect(0, 0, 0, 0);
	private globalButton normalMode = new globalButton("Normal Mode", normalModeRect);
	private Rect hardModeRect = new Rect(0, 0, 0, 0);
	private globalButton hardMode = new globalButton("Hard Mode", hardModeRect);
	private Rect multiHardModeRect = new Rect(0, 0, 0, 0);
	private globalButton multiHardMode = new globalButton("Multi Hard Mode", multiHardModeRect);

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
}
