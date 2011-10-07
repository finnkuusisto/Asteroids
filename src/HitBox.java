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

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;

public class HitBox implements Renderable {
	
	private List<Rectangle> rectangles;
	
	public HitBox() {
		this.rectangles = new ArrayList<Rectangle>(1);
	}
	
	public void addRectangles(List<Rectangle> list) {
		this.rectangles.addAll(list);
	}
	
	public void addRectangle(Rectangle r) {
		this.rectangles.add(r);
	}
	
	public void move(double dx, double dy) {
		for (int i = 0; i < this.rectangles.size(); i++) {
			Rectangle rect = this.rectangles.get(i);
			rect.setX(rect.getX() + dx);
			rect.setY(rect.getY() + dy);
		}
	}
	
	public boolean intersects(HitBox other) {
		for (int i = 0; i < this.rectangles.size(); i++) {
			for (int j = 0; j < other.rectangles.size(); j++) {
				Rectangle mine = this.rectangles.get(i);
				Rectangle his = other.rectangles.get(j);
				if (mine.intersects(his)) {
					return true;
				}
			}
		}
		return false;
	}

	@Override
	public void render(Graphics g) {
		Color old = g.getColor();
		g.setColor(Color.YELLOW);
		for (int i = 0; i < this.rectangles.size(); i++) {
			Rectangle rect = this.rectangles.get(i);
			g.drawRect((int)rect.getX(), (int)rect.getY(),
					(int)rect.getWidth(), (int)rect.getHeight());
		}
		g.setColor(old);
	}

}
