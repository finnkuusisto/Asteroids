import java.awt.Graphics;

public class GameScreen implements Screen {
	
	private Ship player;
	
	public GameScreen() {
		//start in center facing up
		this.player = new Ship(new DoubleVec2D(400,300), new DoubleVec2D(0,-1));
	}

	@Override
	public void update(double ticksPassed) {
		// TODO
		this.player.update(ticksPassed);
	}

	@Override
	public void render(Graphics g) {
		//TODO
		this.player.render(g);
	}
	
	@Override
	public void pause() { /*unused*/ }

	@Override
	public void resume() { /*unused*/ }

}
