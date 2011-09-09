public class Rectangle {

	//why use java.awt.Rectangle when you can write your own?
	private double[] points;
	
	public Rectangle(double x, double y, double w, double h) {
		this.points = new double[4];
		this.points[0] = x;
		this.points[1] = y;
		this.points[2] = w;
		this.points[3] = h;
	}
	
	public double getX() {
		return this.points[0];
	}
	
	public double getY() {
		return this.points[1];
	}

	public double getWidth() {
		return this.points[2];
	}
	
	public double getHeight() {
		return this.points[3];
	}
	
	public double left() {
		return this.points[0];
	}
	
	public double right() {
		return this.points[0] + this.points[2];
	}
	
	public double top() {
		return this.points[1];
	}
	
	public double bottom() {
		return this.points[1] + this.points[3];
	}
	
	public void setX(double x) {
		this.points[0] = x;
	}
	
	public void setY(double y) {
		this.points[1] = y;
	}
	
	public void setWidth(double w) {
		this.points[2] = w;
	}
	
	public void setHeight(double h) {
		this.points[3] = h;
	}
	
	public boolean intersects(Rectangle other) {
		return !(this.left() > other.right() || this.right() < other.left() ||
				this.top() > other.bottom() || this.bottom() < other.top());
	}
	
}
