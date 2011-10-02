import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class GameScreen implements Screen {
	
	private static final int SHOOT_ASTEROID = 100;
	private static final int HIT_BY_ASTEROID = -200;
	private static final int SHOOT_ENEMY = 500;
	private static final int HIT_BY_ENEMY = -800;
	
	private Ship player;
	private EnemyShip enemyShip;
	private long score;
	private List<Bullet> bullets;
	private List<Bullet> enemyBullets; //TODO make this appear different
	private List<Asteroid> asteroids;
	private double ticksSinceAsteroid;
	private double ticksPerAsteroid;
	private Random rand;
	
	public GameScreen() {
		this.rand = new Random();
		//start in random location facing up
		DoubleVec2D pos = new DoubleVec2D(rand.nextInt(App.WIDTH),
				rand.nextInt(App.HEIGHT));
		DoubleVec2D dir = new DoubleVec2D(0, -1);
		this.player = new Ship(pos, dir);
		DoubleVec2D enemyPos = new DoubleVec2D(rand.nextInt(App.WIDTH),
				rand.nextInt(App.HEIGHT));
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
		this.updatePlayer(ticksPassed);
		this.updateEnemy(ticksPassed);
		this.updateBullets(ticksPassed);
		this.updateAsteroids(ticksPassed);
		//spawn asteroids if it is time
		this.ticksSinceAsteroid += ticksPassed;
		if (this.ticksSinceAsteroid > this.ticksPerAsteroid) {
			this.spawnAsteroid();
			this.ticksSinceAsteroid = 0;
		}
	}
	
	private void updatePlayer(double ticksPassed) {
		this.player.update(ticksPassed);
		//and check if the player fired any bullets
		if (this.player.hasFiredBullet()) {
			Sound.SHOT.play();
			this.bullets.add(this.player.getFiredBullet());
		}
	}
	
	private void updateEnemy(double ticksPassed) {
		this.enemyShip.update(ticksPassed);
		//and check if the enemy ship has fired bullets
		if (this.enemyShip.hasFiredBullet()) {
			Sound.SHOT.play();
			this.enemyBullets.add(this.enemyShip.getFiredBullet());
		}
	}
	
	private void updateBullets(double ticksPassed) {
		//first update player bullets
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
			//check if it's offscreen, hit an asteroid, or hit the enemy ship
			if (position.getX() < -20 || position.getX() > (App.WIDTH + 20) ||
					position.getY() < -20 ||
					position.getY() > (App.HEIGHT + 20)) {
				//remove if so
				this.bullets.remove(i);
			}
			else if (hitAsteroid) { //hit asteroid
				this.score += GameScreen.SHOOT_ASTEROID;
				this.bullets.remove(i);
			}
			else if (bullet.isColliding(this.enemyShip)) { // hit enemy
				Sound.HIT.play();
				this.score += GameScreen.SHOOT_ENEMY;
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
			//check if it's offscreen, hit the player or otherwise
			if (position.getX() < -20 || position.getX() > (App.WIDTH + 20) ||
					position.getY() < -20 ||
					position.getY() > (App.HEIGHT + 20)) {
				//remove if so
				this.enemyBullets.remove(i);
			}
			else if (bullet.isColliding(this.player)) { //hit player
				Sound.HIT.play();
				this.score += GameScreen.HIT_BY_ENEMY;
				//remove bullet
				this.enemyBullets.remove(i);
			}
			else { //update
				bullet.update(ticksPassed);
				i++;
			}
		}
	}
	
	private void updateAsteroids(double ticksPassed) {
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
	}
	
	private void spawnAsteroid() {
		DoubleVec2D pos = new DoubleVec2D(0,0);
		DoubleVec2D dir = new DoubleVec2D(0,0);
		//pick random perimeter point and direction
		double posX = 0;
		double posY = 0;
		double dirX = 0;
		double dirY = 0;
		//pick between top/bottom or left/right point
		boolean topBottom = this.rand.nextBoolean();
		if (topBottom) {
			posX = this.rand.nextInt(App.WIDTH);
			dirX = this.rand.nextInt(2 * App.WIDTH) - App.WIDTH;
			//start on top or bottom
			boolean top = this.rand.nextBoolean();
			posY = top ? 0 : (App.HEIGHT - 1);
			int yTmp = this.rand.nextInt(App.HEIGHT);
			dirY = top ? yTmp : -yTmp;
		}
		else {
			posY = this.rand.nextInt(App.HEIGHT);
			dirY = this.rand.nextInt(2 * App.HEIGHT) - App.HEIGHT;
			//start on left or right
			boolean left = this.rand.nextBoolean();
			posX = left ? 0 : (App.WIDTH - 1);
			int xTmp = this.rand.nextInt(App.WIDTH);
			dirX = left ? xTmp : -xTmp;
		}
		pos.setX(posX);
		pos.setY(posY);
		dir.setX(dirX);
		dir.setY(dirY);
		dir = dir.normalized();
		Asteroid asteroid = new Asteroid(pos, dir);
		this.asteroids.add(asteroid);
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