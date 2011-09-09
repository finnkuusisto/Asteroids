public class DoubleVec2D {

	private static final int X = 0;
	private static final int Y = 1;
	
	private double[] vec;
	
	public DoubleVec2D(double x, double y) {
		this.vec = new double[]{x, y};
	}
	
	public double getX() {
		return this.vec[DoubleVec2D.X];
	}
	
	public double getY() {
		return this.vec[DoubleVec2D.Y];
	}
	
	public void setX(double x) {
		this.vec[DoubleVec2D.X] = x;
	}
	
	public void setY(double y) {
		this.vec[DoubleVec2D.Y] = y;
	}
	
	public DoubleVec2D add(DoubleVec2D other) {
		return new DoubleVec2D(this.getX() + other.getX(),
				this.getY() + other.getY());
	}
	
	public DoubleVec2D subtract(DoubleVec2D other) {
		return new DoubleVec2D(this.getX() - other.getX(),
				this.getY() - other.getY());
	}
	
}
