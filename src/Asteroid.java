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

public class Asteroid implements Entity, Collidable, Renderable {
	
	private static final String IMG_FNAME[] = {"/asteroid1.gif",
												"/asteroid2.gif",
												"/asteroid3.gif"};
	//this should be static because all bullets look the same
	private static BufferedImage IMG[] = 
		{ImageUtils.loadImage(Asteroid.IMG_FNAME[0]),
		ImageUtils.loadImage(Asteroid.IMG_FNAME[1]),
		ImageUtils.loadImage(Asteroid.IMG_FNAME[2])};
	
	private DoubleVec2D direction;
	private DoubleVec2D position;
	private DoubleVec2D velocity;
	private HitBox hitBox;
	private int imageIndex;
	private double speed = 8.0;
	
	public Asteroid(DoubleVec2D position, DoubleVec2D direction) {
		this.position = position;
		this.direction = direction;
		//determine velocity, direction should be normalized
		this.velocity = new DoubleVec2D(this.speed * this.direction.getX(),
				this.speed * this.direction.getY());
		this.hitBox = new HitBox();
		this.imageIndex = (int)(Math.random() * Asteroid.IMG.length);
		//make the hitbox 1x1 for now since I don't want to deal with
		//rotating the hitbox
		BufferedImage image = Asteroid.IMG[this.imageIndex];
		List<Rectangle> rects = ImageUtils.getBoundingRectangles(image,	1, 1);
		this.hitBox.addRectangles(rects);
		//now shift the hit box to the asteroid's position
		this.hitBox.move(this.position.getX() - (image.getWidth() / 2),
				this.position.getY() - (image.getHeight() / 2));
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
		double dx = this.velocity.getX() * ticksPassed;
		double dy = this.velocity.getY() * ticksPassed;
		//check boundaries first
		if ((this.position.getX() + dx) < 0) { //off left
			dx = this.position.getX();
			this.velocity.setX(-this.velocity.getX());
		}
		else if ((this.position.getX() + dx > App.WIDTH)) { //off right
			dx = (App.WIDTH - this.position.getX());
			this.velocity.setX(-this.velocity.getX());
		}
		if ((this.position.getY() + dy) < 0) { //off top
			dy = this.position.getY();
			this.velocity.setY(-this.velocity.getY());
		}
		else if ((this.position.getY() + dy) > App.HEIGHT) { //off bottom
			dy = (App.HEIGHT - this.position.getY());
			this.velocity.setY(-this.velocity.getY());
		}
		this.position.setX(this.position.getX() + dx);
		this.position.setY(this.position.getY() + dy);
		//move the hitbox too
		this.hitBox.move(dx, dy);
	}

	@Override
	public void render(Graphics g) {
		Graphics2D g2 = (Graphics2D)g;
		//center image on position
		BufferedImage image = Asteroid.IMG[this.imageIndex];
		AffineTransform xform = 
			AffineTransform.getTranslateInstance(
					this.position.getX() - (image.getWidth() / 2),
					this.position.getY() - (image.getHeight() / 2));
		g2.drawImage(image, xform, null);
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
