package xoro;

public class MenuMain {
	OneVsOneButton oneVsOneButton = new OneVsOneButton();
	OneVsComputerButton oneVsComputerButton = new OneVsComputerButton();
	
}

class OneVsComputerButton {
	MenuRect rect = new MenuRect();
	MenuText text = new MenuText();

	public OneVsComputerButton() {
		text.text = "1 VS Computer";
		text.font = "Arial-BoldMT-48";
		rect.width = 200;
		rect.height = 60;
		rect.penThickness = 2;
	}
}

class OneVsOneButton {
	MenuRect rect = new MenuRect();
	MenuText text = new MenuText();

	public OneVsOneButton() {
		text.text = "1 VS 1";
		text.font = "Arial-BoldMT-48";
		rect.width = 200;
		rect.height = 60;
		rect.penThickness = 2;
	}
}

class MenuRect {
	public int x;
	public int y;
	public int width;
	public int height;
	public int brush;
	public int pen;
	public int penThickness;
}

class MenuText {
	public int x;
	public int y;
	public int brush;
	public String text;
	public String font;
}
