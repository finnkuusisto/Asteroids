/*
* Copyright (C) 2011 by Finn Kuusisto
*
* This program is free software; you can redistribute it and/or modify
* it under the terms of the MIT license.
*
* This program is distributed in the hope that it will be useful,
* but WITHOUT ANY WARRANTY; without even the implied warranty of
* MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
*
* You should have received a copy of the MIT License along with this program.
*/

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.util.List;

public class Bullet implements Entity, Renderable, Collidable {

	private static final String IMG_FNAME = "/shot.gif";
	private static final String ENEMY_IMG_FNAME = "/enemy_shot.gif";
	//these should be static because all bullet objects look the same
	private static BufferedImage IMG = ImageUtils.loadImage(Bullet.IMG_FNAME);
	private static BufferedImage ENEMY_IMG =
		ImageUtils.loadImage(Bullet.ENEMY_IMG_FNAME);
	
	private DoubleVec2D direction;
	private DoubleVec2D position;
	private DoubleVec2D velocity;
	private HitBox hitBox;
	private double speed = 10.0;
	private boolean playerBullet;
	
	public Bullet(DoubleVec2D position, DoubleVec2D direction,
			boolean playerBullet) {
		this.position = position;
		this.direction = direction;
		this.playerBullet = playerBullet;
		//determine velocity, direction should be normalized
		this.velocity = new DoubleVec2D(this.speed * this.direction.getX(),
				this.speed * this.direction.getY());
		this.hitBox = new HitBox();
		//make the hitbox 1x1 for now since I don't want to deal with
		//rotating the hitbox
		List<Rectangle> rects = ImageUtils.getBoundingRectangles(Bullet.IMG,
				1, 1);
		this.hitBox.addRectangles(rects);
		//now shift the hit box to the ship's position
		this.hitBox.move(this.position.getX() - (Bullet.IMG.getWidth() / 2),
				this.position.getY() - (Bullet.IMG.getHeight() / 2));
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
		//move bullet according to velocity
		double dx = this.velocity.getX() * ticksPassed;
		double dy = this.velocity.getY() * ticksPassed;
		this.position.setX(this.position.getX() + dx);
		this.position.setY(this.position.getY() + dy);
		//move the hitbox too
		this.hitBox.move(dx, dy);
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
		BufferedImage img = this.playerBullet ? Bullet.IMG : Bullet.ENEMY_IMG;
		xform.translate(-(img.getWidth() / 2), -(img.getHeight() / 2));
		g2.drawImage(img, xform, null);
		//draw hitbox for now
		//this.hitBox.render(g); //TODO debug
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
