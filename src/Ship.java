import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

public class Ship implements Entity, Renderable, Collidable {

	private static final String IMG_FNAME = "/ship.gif";
	//this should be static because all ships look the same
	private static BufferedImage IMG = ImageUtils.loadImage(Ship.IMG_FNAME);
	
	private DoubleVec2D direction;
	private DoubleVec2D position;
	private DoubleVec2D velocity;
	private HitBox hitBox; //TODO need to be careful of drift due to precision?
	private double acceleration = 0.2;
	private double drag = 0.05;
	private double bulletCooldown; //in ticks
	private double ticksSinceBullet;
	private List<Bullet> firedBullets;
	
	public Ship(DoubleVec2D position, DoubleVec2D direction) {
		this.position = position;
		this.direction = direction;
		this.velocity = new DoubleVec2D(0,0);
		this.hitBox = new HitBox();
		//make the hitbox 1x1 for now since I don't want to deal with
		//rotating the hitbox
		List<Rectangle> rects = ImageUtils.getBoundingRectangles(Ship.IMG,
				1, 1);
		this.hitBox.addRectangles(rects);
		//now shift the hit box to the ship's position
		this.hitBox.move(this.position.getX() - (Ship.IMG.getWidth() / 2),
				this.position.getY() - (Ship.IMG.getHeight() / 2));
		//TODO maybe this should be configurable
		this.bulletCooldown = 4;
		this.ticksSinceBullet = 0;
		this.firedBullets = new ArrayList<Bullet>(5);
	}
	
	public boolean hasFiredBullets() {
		return this.firedBullets.size() > 0;
	}
	
	public List<Bullet> getFiredBullets() {
		//perhaps this isn't the best way to handle it
		return this.firedBullets;
	}
	
	@Override
	public DoubleVec2D getDirection() {
		return this.direction;
	}

	@Override
	public DoubleVec2D getPosition() {
		return this.position;
	}

	@Override
	public void setDirection(DoubleVec2D direction) {
		this.direction = direction;

	}

	@Override
	public void setPosition(DoubleVec2D position) {
		this.position = position;

	}

	@Override
	public void update(double ticksPassed) {
		//move ship according to velocity
		double dx = this.velocity.getX() * ticksPassed;
		double dy = this.velocity.getY() * ticksPassed;
		//check boundaries first
		if ((this.position.getX() + dx) < 0) { //off left
			dx = this.position.getX();
			this.velocity.setX(0);
		}
		else if ((this.position.getX() + dx > App.WIDTH)) { //off right
			dx = (App.WIDTH - this.position.getX());
			this.velocity.setX(0);
		}
		if ((this.position.getY() + dy) < 0) { //off top
			dy = this.position.getY();
			this.velocity.setY(0);
		}
		else if ((this.position.getY() + dy) > App.HEIGHT) { //off bottom
			dy = (App.HEIGHT - this.position.getY());
			this.velocity.setY(0);
		}
		this.position.setX(this.position.getX() + dx);
		this.position.setY(this.position.getY() + dy);
		//move the hitbox too
		this.hitBox.move(dx, dy);
		//slow velocity due to drag
		double xDrag = this.drag * ticksPassed *
			Math.abs(this.direction.getX());
		double yDrag = this.drag * ticksPassed *
			Math.abs(this.direction.getY());
		dx = (this.velocity.getX() > 0) ?
			-Math.min(xDrag, this.velocity.getX()) :
			Math.min(xDrag, -this.velocity.getX());
		dy = (this.velocity.getY() > 0) ?
			-Math.min(yDrag, this.velocity.getY()) :
			Math.min(yDrag, -this.velocity.getY());
		this.velocity.setX(this.velocity.getX() + dx);
		this.velocity.setY(this.velocity.getY() + dy);
		//face the mouse
		this.direction =
			Controller.getMousePosition().subtract(this.position).normalized();
		//modify velocity for controls
		dx = this.direction.getX() * this.acceleration * ticksPassed;
		dy = this.direction.getY() * this.acceleration * ticksPassed;
		if (Controller.isKeyDown(Controller.K_W)) {
			this.velocity.setX(this.velocity.getX() + dx);
			this.velocity.setY(this.velocity.getY() + dy);
		}
		if (Controller.isKeyDown(Controller.K_S)) {
			this.velocity.setX(this.velocity.getX() - dx);
			this.velocity.setY(this.velocity.getY() - dy);
		}
		//spawn bullets, considering cooldown
		this.firedBullets.clear();
		this.ticksSinceBullet += ticksPassed;
		while ((Controller.isKeyDown(Controller.K_SPACE) ||
				Controller.isMouseDown()) &&
				(this.ticksSinceBullet > this.bulletCooldown)) {
			DoubleVec2D position = new DoubleVec2D(this.position.getX(),
					this.position.getY());
			DoubleVec2D direction = new DoubleVec2D(this.direction.getX(),
					this.direction.getY());
			this.firedBullets.add(new Bullet(position, direction));
			this.ticksSinceBullet = Math.max(0, 
					(this.ticksSinceBullet - this.bulletCooldown));
		}
	}

	@Override
	public void render(Graphics g) {
		Graphics2D g2 = (Graphics2D)g;
		//the direction vector should always be normalized and
		//calculate from (1,0), but add pi/2 since the image is vertical and
		//positive y is downward
		double rot = Math.atan2(this.direction.getY(), this.direction.getX()) +
			(Math.PI / 2);
		AffineTransform xform = 
			AffineTransform.getTranslateInstance(this.position.getX(),
					this.position.getY());
		xform.rotate(rot);
		//center image on position
		xform.translate(-(Ship.IMG.getWidth() / 2),
				-(Ship.IMG.getHeight() / 2));
		g2.drawImage(Ship.IMG, xform, null);
		//draw hitbox for now
		this.hitBox.render(g); //TODO debug
	}

	@Override
	public HitBox getHitBox() {
		return this.hitBox;
	}

	@Override
	public boolean isColliding(Collidable other) {
		return this.hitBox.intersects(other.getHitBox());
	}

}
