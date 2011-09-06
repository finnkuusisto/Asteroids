import java.util.ArrayList;
import java.util.List;

public class HitBox {
	
	private List<Rectangle> rectangles;
	
	public HitBox() {
		this.rectangles = new ArrayList<Rectangle>(1);
	}
	
	public void addRectangle(Rectangle r) {
		this.rectangles.add(r);
	}
	
	public boolean intersects(HitBox other) {
		for (int i = 0; i < this.rectangles.size(); i++) {
			for (int j = 0; j < other.rectangles.size(); j++) {
				Rectangle mine = this.rectangles.get(i);
				Rectangle his = other.rectangles.get(j);
				if (mine.intersects(his)) {
					return true;
				}
			}
		}
		return false;
	}

}
