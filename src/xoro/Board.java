package xoro;

public class Board {
	Rect[][] rect=new Rect[3][3];
	Board(){
		for(int i=0;i<rect.length;i++){
			for(int g=0;g<rect[0].length;g++){
				rect[i][g]=new Rect();
			}
		}
	}
}
class Rect{
	int x=0;
	int y=0;
	int width=0;
	int heigth=0;
}
