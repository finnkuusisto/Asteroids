import java.awt.Graphics;
import java.awt.image.BufferedImage;

public class Ship implements Entity, Renderable, Collidable {

	private static final String IMG_FNAME = "/ship.gif";
	//this should be static because all ships look the same
	private static BufferedImage IMG = ImageUtils.loadImage(Ship.IMG_FNAME);
	
	private DoubleVec2D direction;
	private DoubleVec2D position;
	private DoubleVec2D velocity; //TODO
	private HitBox hitBox; //TODO need to be careful of drift due to precision
	private double acceleration = 1;
	
	public Ship(DoubleVec2D position, DoubleVec2D direction) {
		this.position = position;
		this.direction = direction;
		this.hitBox = new HitBox();
		Rectangle rect = ImageUtils.getBoundingRectangle(Ship.IMG);
		//Rectangle rect = new Rectangle(0, 0, Ship.IMG.getWidth(),
		//		Ship.IMG.getHeight());
		rect.setX(this.position.getX() + rect.getX());
		rect.setY(this.position.getY() + rect.getY());
		this.hitBox.addRectangle(rect);
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
		// TODO debug movement
		double dx = 0;
		double dy = 0;
		if (Controller.isKeyDown(Controller.K_W)) {
			dy -= this.acceleration * ticksPassed;
		}
		if (Controller.isKeyDown(Controller.K_S)) {
			dy += this.acceleration * ticksPassed;
		}
		if (Controller.isKeyDown(Controller.K_A)) {
			dx -= this.acceleration * ticksPassed;
		}
		if (Controller.isKeyDown(Controller.K_D)) {
			dx += this.acceleration * ticksPassed;
		}
		this.position.setX(this.position.getX() + dx);
		this.position.setY(this.position.getY() + dy);
		this.hitBox.move(dx, dy);
	}

	@Override
	public void render(Graphics g) {
		//TODO fix for direction vector
		g.drawImage(Ship.IMG, (int)this.position.getX(),
				(int)this.position.getY(), null);
		//draw hitbox for now
		this.hitBox.render(g); //TODO debug
	}

	@Override
	public HitBox getHitBox() {
		return this.hitBox;
	}

	@Override
	public boolean isColliding(Collidable other) {
		// TODO Auto-generated method stub
		return false;
	}

}
