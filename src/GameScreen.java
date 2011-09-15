import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;

public class GameScreen implements Screen {
	
	private Ship player;
	private List<Bullet> bullets;
	
	public GameScreen() {
		//start in center facing up
		DoubleVec2D pos = new DoubleVec2D((App.WIDTH / 2), (App.HEIGHT / 2));
		DoubleVec2D dir = new DoubleVec2D(0, -1);
		this.player = new Ship(pos, dir);
		this.bullets = new ArrayList<Bullet>();
	}

	@Override
	public void update(double ticksPassed) {
		// TODO
		//update the player
		this.player.update(ticksPassed);
		//and check if the player fired any bullets
		if (this.player.hasFiredBullets()) {
			this.bullets.addAll(this.player.getFiredBullets());
		}
		//update bullets next
		for (int i = 0; i < this.bullets.size(); /*update in loop*/) {
			Bullet bullet = this.bullets.get(i);
			DoubleVec2D position = bullet.getPosition();
			//first check if it's offscreen
			if (position.getX() < -20 || position.getX() > (App.WIDTH + 20) ||
					position.getY() < -20 ||
					position.getY() > (App.HEIGHT + 20)) {
				//remove if so
				this.bullets.remove(i);
			}
			else { //update
				this.bullets.get(i).update(ticksPassed);
				i++;
			}
		}
		
	}

	@Override
	public void render(Graphics g) {
		//TODO
		//render bullets first
		for (int i = 0; i < this.bullets.size(); i++) {
			this.bullets.get(i).render(g);
		}
		//render the ship last
		this.player.render(g);
	}
	
	@Override
	public void pause() { /*unused*/ }

	@Override
	public void resume() { /*unused*/ }

}
