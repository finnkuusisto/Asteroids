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

public interface Entity {

	public DoubleVec2D getPosition();
	
	public void setPosition(DoubleVec2D position);
	
	public DoubleVec2D getDirection();
	
	public void setDirection(DoubleVec2D direction);
	
	public void update(double ticksPassed);
	
}
