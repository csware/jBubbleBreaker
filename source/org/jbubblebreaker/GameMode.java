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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with jBubbleBreaker. If not, see <http://www.gnu.org/licenses/>.
 */
package org.jbubblebreaker;

import java.lang.reflect.Constructor;

/**
 * Datatype for storing game modes
 * @author Sven Strickroth
 */
public class GameMode {
	/**
	 * Stores the mode name
	 */
	private String modeName;
	/**
	 * Stores the constuctor for this mode
	 */
	private Constructor<Game> constructor;
	/**
	 * Stores the possible matrix-sizes for this mode
	 */
	private GameSize allowedSize;

	/**
	 * Creates a datastorage for a game mode
	 * @param modeName Mode name
	 * @param constructor Reference of the constructor
	 */
	public GameMode(String modeName, GameSize allowedSize, Constructor<Game> constructor) {
		this.modeName = modeName;
		this.constructor = constructor;
		this.allowedSize = allowedSize;
	}

	/**
	 * Returns the mode name 
	 * @return the modeName
	 */
	public String getModeName() {
		return modeName;
	}

	/**
	 * Returns the reference to the constructor with (int, int) parameters
	 * @return the constructor
	 */
	public Constructor<Game> getConstructor() {
		return constructor;
	}
	
	/**
	 * Returns the minimal size for this mode, 0 if there is not limit
	 * @return allowed sizes array
	 */
	public GameSize getAllowedSize() {
		return allowedSize;
	}
}
