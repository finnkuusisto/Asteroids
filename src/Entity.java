public interface Entity {

	public DoubleVec2D getPosition();
	
	public void setPosition(DoubleVec2D position);
	
	public DoubleVec2D getDirection();
	
	public void setDirection(DoubleVec2D direction);
	
	public void update(double ticksPassed);
	
}
