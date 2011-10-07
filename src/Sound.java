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

import java.io.IOException;
import java.net.URL;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

public class Sound {
	
	public static final Sound SHOT = new Sound("/shot.wav", 10);
	public static final Sound HIT = new Sound("/hit.wav", 5);
	public static final Sound EXPLOSION = new Sound("/explosion.wav", 3);

	private Clip[] clips;
	private int position;
	
	public Sound(String filename) {
		this(filename, 1);
	}
	
	public Sound(String filename, int numTracks) {
		this.clips = new Clip[numTracks];
		this.position = 0;
		for (int i = 0; i < this.clips.length; i++) {
			URL url = Sound.class.getResource(filename);
			AudioInputStream inStream;
			try {
				inStream = AudioSystem.getAudioInputStream(url);
				clips[i] = AudioSystem.getClip();
				clips[i].open(inStream);
			} catch (UnsupportedAudioFileException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			} catch (LineUnavailableException e) {
				e.printStackTrace();
			}
		}
	}
	
	public void play() {
		//grab the current clip
		Clip clip = this.clips[this.position];
		//play it from the beginning
		if (clip != null) {
			clip.stop();
			clip.setFramePosition(0);
			clip.start();
		}
		//move to the next available clip
		this.position = (this.position + 1) % this.clips.length;
	}
	
}
