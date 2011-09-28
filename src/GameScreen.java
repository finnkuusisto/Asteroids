import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;

public class GameScreen implements Screen {
	
	private static final int SHOOT_ASTEROID = 100;
	private static final int HIT_BY_ASTEROID = -200;
	
	private Ship player;
	private EnemyShip enemyShip;
	private long score;
	private List<Bullet> bullets;
	private List<Bullet> enemyBullets; //TODO make this appear different
	private List<Asteroid> asteroids;
	private double ticksSinceAsteroid;
	private double ticksPerAsteroid;
	
	public GameScreen() {
		//start in center facing up
		DoubleVec2D pos = new DoubleVec2D((App.WIDTH / 2), (App.HEIGHT / 2));
		DoubleVec2D dir = new DoubleVec2D(0, -1);
		this.player = new Ship(pos, dir);
		DoubleVec2D enemyPos = new DoubleVec2D(0, 0);
		DoubleVec2D enemyDir = new DoubleVec2D(0, -1);
		this.enemyShip = new EnemyShip(enemyPos, enemyDir, this.player);
		this.score = 0;
		this.bullets = new ArrayList<Bullet>(50);
		this.enemyBullets = new ArrayList<Bullet>(50);
		this.asteroids = new ArrayList<Asteroid>(50);
		this.ticksSinceAsteroid = 0;
		this.ticksPerAsteroid = 2 * App.TICKS_PER_SECOND;
	}

	@Override
	public void update(double ticksPassed) {
		//update the player
		this.player.update(ticksPassed);
		//and check if the player fired any bullets
		if (this.player.hasFiredBullet()) {
			this.bullets.add(this.player.getFiredBullet());
		}
		//update the enemy ship
		this.enemyShip.update(ticksPassed);
		if (this.enemyShip.hasFiredBullet()) {
			this.enemyBullets.add(this.enemyShip.getFiredBullet());
		}
		//TODO and check if the enemy ship has fired bullets
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
					this.score += GameScreen.SHOOT_ASTEROID;
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
		//and then update the enemy bullets
		for (int i = 0; i < this.enemyBullets.size(); /*update in loop*/) {
			Bullet bullet = this.enemyBullets.get(i);
			DoubleVec2D position = bullet.getPosition();
			//TODO perhaps also destroys asteroids?
			//check if it's offscreen
			if (position.getX() < -20 || position.getX() > (App.WIDTH + 20) ||
					position.getY() < -20 ||
					position.getY() > (App.HEIGHT + 20)) {
				//remove if so
				this.enemyBullets.remove(i);
			}
			else { //update
				bullet.update(ticksPassed);
				i++;
			}
		}
		//next update the asteroids
		for (int i = 0; i < this.asteroids.size(); /*update in loop*/) {
			Asteroid asteroid = this.asteroids.get(i);
			//check if it hit the player
			if (asteroid.isColliding(this.player)) {
				//TODO maybe count lives or something
				//decrease score and remove asteroid
				this.score += GameScreen.HIT_BY_ASTEROID;
				this.asteroids.remove(i);
			}
			else {
				asteroid.update(ticksPassed);
				i++;
			}
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
		//render bullets first
		for (int i = 0; i < this.bullets.size(); i++) {
			this.bullets.get(i).render(g);
		}
		for (int i = 0; i < this.enemyBullets.size(); i++) {
			this.enemyBullets.get(i).render(g);
		}
		//then the asteroids
		for (int i = 0; i < this.asteroids.size(); i++) {
			this.asteroids.get(i).render(g);
		}
		//render the enemy ship
		this.enemyShip.render(g);
		//render the ship last
		this.player.render(g);
		//render the score on top of everything
		Color tmp = g.getColor();
		g.setColor(Color.WHITE);
		g.drawString("SCORE: " + this.score, 10, 20);
		g.setColor(tmp);
	}
	
	@Override
	public void pause() { /*unused*/ }

	@Override
	public void resume() { /*unused*/ }

}