public interface Screen extends Renderable {

	public void update(double ticksPassed);
	
	public void pause();
	
	public void resume();
	
}
