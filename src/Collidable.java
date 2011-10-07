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

public interface Collidable {

	public boolean isColliding(Collidable other);
	
	public HitBox getHitBox();
	
}
