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

public class DoubleVec2D {

	private static final int X = 0;
	private static final int Y = 1;
	
	private double[] vec;
	
	public DoubleVec2D(double x, double y) {
		this.vec = new double[]{x, y};
	}
	
	public double getX() {
		return this.vec[DoubleVec2D.X];
	}
	
	public double getY() {
		return this.vec[DoubleVec2D.Y];
	}
	
	public void setX(double x) {
		this.vec[DoubleVec2D.X] = x;
	}
	
	public void setY(double y) {
		this.vec[DoubleVec2D.Y] = y;
	}
	
	public double magnitude() {
		return Math.sqrt((this.getX() * this.getX()) +
				(this.getY() * this.getY()));
	}
	
	public DoubleVec2D normalized() {
		double length = this.magnitude();
		return new DoubleVec2D((this.getX() / length), (this.getY() / length));
	}
	
	public DoubleVec2D add(DoubleVec2D other) {
		return new DoubleVec2D(this.getX() + other.getX(),
				this.getY() + other.getY());
	}
	
	public DoubleVec2D subtract(DoubleVec2D other) {
		return new DoubleVec2D(this.getX() - other.getX(),
				this.getY() - other.getY());
	}
	
	public double dot(DoubleVec2D other) {
		return (this.getX() * other.getX()) + (this.getY() * other.getY());
	}
	
	public double perpDot(DoubleVec2D other) {
		return (-this.getY() * other.getX()) + (this.getX() * other.getY());
	}
	
	public String toString() {
		return Arrays.toString(this.vec);
	}
	
}
