package xoro;

public class VsComputerMenu {
	easyButton easyButton=new easyButton();
	normalButton normalButton=new normalButton();
	hardButton hardButton=new hardButton();
}

class easyButton{
	MenuRect rect=new MenuRect();
	MenuText text=new MenuText();
	public easyButton() {
		text.text = "Easy";
		text.font = "Arial-BoldMT-48";
		rect.width = 200;
		rect.height = 60;
		rect.penThickness = 2;
	}
}

class normalButton{
	MenuRect rect=new MenuRect();
	MenuText text=new MenuText();
	public normalButton() {
		text.text = "Normal";
		text.font = "Arial-BoldMT-48";
		rect.width = 200;
		rect.height = 60;
		rect.penThickness = 2;
	}
}

class hardButton{
	MenuRect rect=new MenuRect();
	MenuText text=new MenuText();
	public hardButton() {
		text.text = "Hard";
		text.font = "Arial-BoldMT-48";
		rect.width = 200;
		rect.height = 60;
		rect.penThickness = 2;
	}
}
