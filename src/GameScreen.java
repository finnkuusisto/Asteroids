import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;

public class GameScreen implements Screen {
	
	private Ship player;
	private long score; //TODO
	private List<Bullet> bullets;
	private List<Asteroid> asteroids;
	private double ticksSinceAsteroid;
	private double ticksPerAsteroid;
	
	public GameScreen() {
		//start in center facing up
		DoubleVec2D pos = new DoubleVec2D((App.WIDTH / 2), (App.HEIGHT / 2));
		DoubleVec2D dir = new DoubleVec2D(0, -1);
		this.player = new Ship(pos, dir);
		this.score = 0;
		this.bullets = new ArrayList<Bullet>(50);
		this.asteroids = new ArrayList<Asteroid>(50);
		this.ticksSinceAsteroid = 0;
		this.ticksPerAsteroid = 2 * App.TICKS_PER_SECOND;
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
			//first check if this bullet hit any asteroids
			boolean hitAsteroid = false;
			//only let it destroy 1, thus '&& !hitAsteroid'
			for (int j = 0; j < this.asteroids.size() && !hitAsteroid; j++) {
				Asteroid asteroid = this.asteroids.get(j);
				if (bullet.isColliding(asteroid)) {
					//if so, remove the asteroid
					this.asteroids.remove(j);
					//and mark it as hitting an asteroid
					hitAsteroid = true;
				}
			}
			//check if it's offscreen or was marked as hitting an asteroid
			if (position.getX() < -20 || position.getX() > (App.WIDTH + 20) ||
					position.getY() < -20 ||
					position.getY() > (App.HEIGHT + 20) ||
					hitAsteroid) {
				//remove if so
				this.bullets.remove(i);
			}
			else { //update
				bullet.update(ticksPassed);
				i++;
			}
		}
		//next update the asteroids
		for (int i = 0; i < this.asteroids.size(); i++) {
			this.asteroids.get(i).update(ticksPassed);
			//TODO remove them when offscreen or have them bounce or something
		}
		//spawn asteroids
		//TODO randomize position
		this.ticksSinceAsteroid += ticksPassed;
		while (this.ticksSinceAsteroid > this.ticksPerAsteroid) {
			DoubleVec2D position = new DoubleVec2D(0, 0);
			DoubleVec2D direction = (new DoubleVec2D(1, 1)).normalized();
			this.asteroids.add(new Asteroid(position, direction));
			this.ticksSinceAsteroid = Math.max(0, 
					(this.ticksSinceAsteroid - this.ticksPerAsteroid));
		}
	}

	@Override
	public void render(Graphics g) {
		//TODO
		//render bullets first
		for (int i = 0; i < this.bullets.size(); i++) {
			this.bullets.get(i).render(g);
		}
		//then the asteroids
		for (int i = 0; i < this.asteroids.size(); i++) {
			this.asteroids.get(i).render(g);
		}
		//render the ship last
		this.player.render(g);
	}
	
	@Override
	public void pause() { /*unused*/ }

	@Override
	public void resume() { /*unused*/ }

}