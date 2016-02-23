package xoro.menus;

import xoro.global.Rect;
import xoro.global.globalButton;

public class mainMenu {
	private Rect OneVSOneRect = new Rect(0, 0, 0, 0);
	private globalButton OneVSOne = new globalButton("1 VS 1", OneVSOneRect);
	private Rect OneVSComputerRect = new Rect(0, 0, 0, 0);
	private globalButton OneVSComputer = new globalButton("1 VS Computer", OneVSComputerRect);
	private String mainMenu = "Main Menu";

	public globalButton getOneVSOne() {
		return OneVSOne;
	}

	public globalButton getOneVSComputer() {
		return OneVSComputer;
	}

	public String getMainMenu() {
		return mainMenu;
	}

}
