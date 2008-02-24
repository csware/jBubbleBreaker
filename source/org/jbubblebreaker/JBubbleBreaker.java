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

import java.util.LinkedList;
import java.util.List;

/**
 * Main class for JBubbleBreaker
 * @author Sven Strickroth
 */
public class JBubbleBreaker {
	/**
	 * Stores all available modes for JBubbleBreaker
	 */
	static private List<GameMode> gameModes = new LinkedList<GameMode>();
	
	/**
	 * Starts JBubbleBreaker
	 * @param args no parameter evaluation
	 */
	public static void main(String[] args)  {
		addMode("org.jbubblebreaker.gamemodes.GameDefault");
		addMode("org.jbubblebreaker.gamemodes.GameOngoing");
		addMode("org.jbubblebreaker.gamemodes.GameShift");
		addMode("org.jbubblebreaker.gamemodes.GameShiftOngoing");
		addMode("org.jbubblebreaker.gamemodes.GameShiftOngoing2");
		GUI.startGUI();
	}
	
	/**
	 * Registers a new game mode to JBubbleBreaker
	 * @param mode
	 */
	@SuppressWarnings("unchecked")
	public static void addMode(String mode) {
		Class<Game> possibleNewMode = null;
		try {
			possibleNewMode = (Class<Game>) Class.forName(mode);
		} catch (ClassNotFoundException e1) {
			throw(new RuntimeException(mode+" not found!"));
		}
		Class<Game> newMode = possibleNewMode;
		while (possibleNewMode.getSuperclass() != null && ! possibleNewMode.getSuperclass().getName().equals("org.jbubblebreaker.Game")) {
			possibleNewMode = (Class<Game>) possibleNewMode.getSuperclass();
		}
		if (newMode != null && possibleNewMode.getSuperclass() != null && possibleNewMode.getSuperclass().getName().equals("org.jbubblebreaker.Game")) {
			try {
				gameModes.add(new GameMode((String) newMode.getDeclaredField("name").get(null),(GameSize) newMode.getDeclaredField("allowedSize").get(null),newMode.getDeclaredConstructor( new Class[] {int.class, int.class})));
			} catch (IllegalArgumentException e) {
				throw(new RuntimeException(mode+" could not be loaded, because a IllegalArgumentException was thrown: "+e.getLocalizedMessage()));				
			} catch (ClassCastException e) {
				throw(new ClassCastException(mode+" is no JBubbleBreaker extension, it doesn't have the static name-field with type String or the static allowedSize-field with type GameSize."));
			} catch (NoSuchFieldException e) {
				throw(new RuntimeException(mode+" is no JBubbleBreaker extension, it doesn't have the static name- or allowedSize-field."));
			} catch (IllegalAccessException e) {
				throw(new RuntimeException(mode+" could not be loaded, because a IllegalAccessException was thrown: "+e.getLocalizedMessage()));
			} catch (SecurityException e) {
				throw(new RuntimeException(mode+" could not be loaded, because a SecurityException was thrown: "+e.getLocalizedMessage()));
			} catch (NoSuchMethodException e) {
				throw(new RuntimeException(mode+" is no JBubbleBreaker extension, it doesn't have a constructor for (int, int)."));
			}
		} else {
			throw(new RuntimeException(mode+" is no JBubbleBreaker extension, it must extend org.jbubblebreaker.Game."));
		}
	}
	
	/**
	 * Returns the registered JBubbleBreate game modi
	 * @return modi list
	 */
	static List<GameMode> getModes() {
		return gameModes;
	}
}
