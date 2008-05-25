/*
 * Copyright 2008 Sven Strickroth <email@cs-ware.de>
 * 
 * This file is part of jBubbleBreaker.
 * 
 * jBubbleBreaker is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License version 3 as
 * published by the Free Software Foundation.
 * 
 * jBubbleBreaker is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with jBubbleBreaker. If not, see <http://www.gnu.org/licenses/>.
 */
package org.jbubblebreaker;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

/**
 * Play sounds
 * @author Sven Strickroth
 */
public class PlaySound extends Thread {
	/**
	 * Cache for the sound-clips
	 */
	private static Clip[] clip = new Clip[Sounds.values().length];
	private int soundID;

	/**
	 *Plays the sound(file) sound
	 * @param sound to play
	 */
	public PlaySound(Sounds sound) {
		super();
		if (JBubbleBreaker.getUserProperty("enableSound", "true").equalsIgnoreCase("true")) {
			soundID = sound.ordinal();
			start();
		}
	}

	@Override
	public void run() {
		try {
			if (clip[soundID] == null) {
				AudioInputStream stream = AudioSystem.getAudioInputStream(getClass().getResource(Sounds.values()[soundID].getFilename()));
				//Info info = new Info(Clip.class, stream.getFormat());
				clip[soundID] = AudioSystem.getClip();
				//clip[soundID] = (Clip) AudioSystem.getLine(info);
				clip[soundID].open(stream);
				clip[soundID].loop(0);
			}
			clip[soundID].setMicrosecondPosition(0);
			clip[soundID].start();
			clip[soundID].drain();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
