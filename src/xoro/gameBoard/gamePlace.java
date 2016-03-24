package xoro.gameBoard;

public class gamePlace {
	private boolean isThereXOrO = false;
	private boolean isThereX = true;

	public gamePlace() {
		setThereXOrO(false);
		setThereX(true);
	}

	public boolean isThereX() {
		return isThereX;
	}

	public void setThereX(boolean isThereX) {
		this.isThereX = isThereX;		
	}

	public boolean getIsThereXOrO() {
		return isThereXOrO;
	}

	public void setThereXOrO(boolean isThereXOrO) {
		this.isThereXOrO = isThereXOrO;
	}
}
