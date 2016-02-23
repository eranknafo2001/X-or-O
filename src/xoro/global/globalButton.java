package xoro.global;

public class globalButton {
	private String text;
	private Rect rect;

	public String getText() {
		return text;
	}

	public Rect getRect() {
		return rect;
	}

	public globalButton(String text1, Rect rect1) {
		rect = rect1;
		text = text1;
	}
}
