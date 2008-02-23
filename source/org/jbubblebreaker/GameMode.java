/*
 * Copyright 2008 Sven Strickroth <email@cs-ware.de>
 * 
 * This file is part of JBubbleBreaker.
 * 
 * JBubbleBreaker is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License version 3 as     
 * published by the Free Software Foundation.                            
 * 
 * JBubbleBreaker is distributed in the hope that it will be useful,     
 * but WITHOUT ANY WARRANTY; without even the implied warranty of        
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the         
 * GNU General Public License for more details.                          
 * 
 * You should have received a copy of the GNU General Public License     
 * along with JBubbleBreaker. If not, see <http://www.gnu.org/licenses/>.
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
	private String modiName;
	/**
	 * Stores the constuctor for this mode
	 */
	private Constructor constructor;

	/**
	 * Creates a datastorage for a game mode
	 * @param modiName Mode name
	 * @param constructor Reference of the constructor
	 */
	public GameMode(String modiName, Constructor constructor) {
		this.modiName = modiName;
		this.constructor = constructor;
	}

	/**
	 * Returns the mode name 
	 * @return the modiName
	 */
	public String getModiName() {
		return modiName;
	}

	/**
	 * Returns the reference to the constructor with (int, int, JPanel) parameters
	 * @return the constructor
	 */
	public Constructor getConstructor() {
		return constructor;
	}
}
