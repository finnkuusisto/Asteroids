public class IntVec2D {

	private static final int X = 0;
	private static final int Y = 1;
	
	private int[] vec;
	
	public IntVec2D(int x, int y) {
		this.vec = new int[]{x, y};
	}
	
	public int getX() {
		return this.vec[IntVec2D.X];
	}
	
	public int getY() {
		return this.vec[IntVec2D.Y];
	}
	
	public void setX(int x) {
		this.vec[IntVec2D.X] = x;
	}
	
	public void setY(int y) {
		this.vec[IntVec2D.Y] = y;
	}
	
	public IntVec2D add(IntVec2D other) {
		return new IntVec2D(this.getX() + other.getX(),
				this.getY() + other.getY());
	}
	
	public IntVec2D subtract(IntVec2D other) {
		return new IntVec2D(this.getX() - other.getX(),
				this.getY() - other.getY());
	}
	
}
