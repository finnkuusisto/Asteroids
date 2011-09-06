public class Vector2D {

	private static final int X = 0;
	private static final int Y = 1;
	
	private int[] vec;
	
	public Vector2D(int x, int y) {
		this.vec = new int[]{x, y};
	}
	
	public int getX() {
		return this.vec[Vector2D.X];
	}
	
	public int getY() {
		return this.vec[Vector2D.Y];
	}
	
	public void setX(int x) {
		this.vec[Vector2D.X] = x;
	}
	
	public void setY(int y) {
		this.vec[Vector2D.Y] = y;
	}
	
	public Vector2D add(Vector2D other) {
		return new Vector2D(this.getX() + other.getX(),
				this.getY() + other.getY());
	}
	
	public Vector2D subtract(Vector2D other) {
		return new Vector2D(this.getX() - other.getX(),
				this.getY() - other.getY());
	}
	
}
