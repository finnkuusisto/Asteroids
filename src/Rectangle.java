public class Rectangle {

	//why use java.awt.Rectangle when you can write your own?
	private int[] points;
	
	public Rectangle(int x, int y, int w, int h) {
		this.points = new int[4];
		this.points[0] = x;
		this.points[1] = y;
		this.points[2] = w;
		this.points[3] = h;
	}
	
	public int getX() {
		return this.points[0];
	}
	
	public int getY() {
		return this.points[1];
	}

	public int getWidth() {
		return this.points[2];
	}
	
	public int getHeight() {
		return this.points[3];
	}
	
	public int left() {
		return this.points[0];
	}
	
	public int right() {
		return this.points[0] + this.points[2];
	}
	
	public int top() {
		return this.points[1];
	}
	
	public int bottom() {
		return this.points[1] + this.points[3];
	}
	
	public void setX(int x) {
		this.points[0] = x;
	}
	
	public void setY(int y) {
		this.points[1] = y;
	}
	
	public void setWidth(int w) {
		this.points[2] = w;
	}
	
	public void setHeight(int h) {
		this.points[3] = h;
	}
	
	public boolean intersects(Rectangle other) {
		return !(this.left() > other.right() || this.right() < other.left() ||
				this.top() > other.bottom() || this.bottom() < other.top());
	}
	
}
