import java.awt.Graphics;

public class GameScreen implements Screen {
	
	private Ship player;
	
	public GameScreen() {
		//start in center facing up
		DoubleVec2D pos = new DoubleVec2D((App.WIDTH / 2), (App.HEIGHT / 2));
		DoubleVec2D dir = new DoubleVec2D(0, -1);
		this.player = new Ship(pos, dir);
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
