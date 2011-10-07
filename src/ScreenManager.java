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

import java.util.HashMap;
import java.util.Map;

public class ScreenManager {

	private Map<String,Screen> screenMap;
	private Screen activeScreen;
	
	public ScreenManager() {
		this.screenMap = new HashMap<String,Screen>();
	}
	
	public void addScreen(Screen screen, String name) {
		this.screenMap.put(name, screen);
	}
	
	public void setActiveScreen(String name) {
		if (this.activeScreen != null) {
			this.activeScreen.pause();
		}
		this.activeScreen = this.screenMap.get(name);
		this.activeScreen.resume();
	}
	
	public Screen getActiveScreen() {
		return this.activeScreen;
	}
	
}
