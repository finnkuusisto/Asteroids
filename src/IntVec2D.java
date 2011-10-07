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

import java.util.Arrays;

public class IntVec2D {

	private static final int X = 0;
	private static final int Y = 1;
	
	private int[] vec;
	
	public IntVec2D(int x, int y) {
		this.vec = new int[]{x, y};
	}
	
	public int getX() {
		return this.vec[IntVec2D.X];
	}
	
	public int getY() {
		return this.vec[IntVec2D.Y];
	}
	
	public void setX(int x) {
		this.vec[IntVec2D.X] = x;
	}
	
	public void setY(int y) {
		this.vec[IntVec2D.Y] = y;
	}
	
	public double magnitude() {
		return Math.sqrt((this.getX() * this.getX()) +
				(this.getY() * this.getY()));
	}
	
	public IntVec2D add(IntVec2D other) {
		return new IntVec2D(this.getX() + other.getX(),
				this.getY() + other.getY());
	}
	
	public IntVec2D subtract(IntVec2D other) {
		return new IntVec2D(this.getX() - other.getX(),
				this.getY() - other.getY());
	}
	
	public String toString() {
		return Arrays.toString(this.vec);
	}
	
}
