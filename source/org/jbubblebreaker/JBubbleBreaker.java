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

import java.util.LinkedList;
import java.util.List;

/**
 * Main class for jBubbleBreaker
 * @author Sven Strickroth
 */
public class JBubbleBreaker {
	/**
	 * Stores all available modes for jBubbleBreaker
	 */
	static private List<GameMode> gameModes = new LinkedList<GameMode>();
	/**
	 * Stores all available bubble styles for jBubbleBreaker
	 */
	static private List<BubbleType> bubbleTypes = new LinkedList<BubbleType>();
	
	/**
	 * Starts jBubbleBreaker
	 * @param args no parameter evaluation
	 */
	public static void main(String[] args)  {
		registerDefault();
		GUI.startGUI();
	}

	/**
	 * Returns the version number
	 * @return version numer 
	 */
	static public String getVersion() {
		// check application.properties for all locations where version needs to kept up to date
		return "0.7";
	}

	/**
	 * Returns the URL to the project homepage
	 * @return url
	 */
	static public String getProjectHomepage() {
		return "http://jbubblebreaker.sf.net";
	}
	
	/**
	 * register all default modes and bubble styles
	 */
	public static void registerDefault() {
		addMode("org.jbubblebreaker.gamemodes.GameDefault");
		addMode("org.jbubblebreaker.gamemodes.GameOngoing");
		addMode("org.jbubblebreaker.gamemodes.GameShift");
		addMode("org.jbubblebreaker.gamemodes.GameShiftOngoing");
		addMode("org.jbubblebreaker.gamemodes.GameShiftOngoing2");
		addMode("org.jbubblebreaker.gamemodes.GameSpeed");
		addMode("org.jbubblebreaker.gamemodes.GameOngoingSpeed");
		addMode("org.jbubblebreaker.gamemodes.GameShiftSpeed");
		addBubbleType("org.jbubblebreaker.bubbles.Bubble3DCircle");
		addBubbleType("org.jbubblebreaker.bubbles.BubbleDefault");
		addBubbleType("org.jbubblebreaker.bubbles.BubbleDefaultSix");
		addBubbleType("org.jbubblebreaker.bubbles.BubbleCaro");
		addBubbleType("org.jbubblebreaker.bubbles.Bubble3DRect");
	}
	
	/**
	 * Registers a new game mode to jBubbleBreaker
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
				gameModes.add(new GameMode((String) newMode.getDeclaredField("name").get(null),(GameSize) newMode.getDeclaredField("allowedSize").get(null),newMode.getDeclaredConstructor( new Class[] {int.class, int.class, int.class})));
			} catch (IllegalArgumentException e) {
				throw(new RuntimeException(mode+" could not be loaded, because a IllegalArgumentException was thrown: "+e.getLocalizedMessage()));				
			} catch (ClassCastException e) {
				throw(new ClassCastException(mode+" is no jBubbleBreaker extension, it doesn't have the static name-field with type String or the static allowedSize-field with type GameSize."));
			} catch (NoSuchFieldException e) {
				throw(new RuntimeException(mode+" is no jBubbleBreaker extension, it doesn't have the static name- or allowedSize-field."));
			} catch (IllegalAccessException e) {
				throw(new RuntimeException(mode+" could not be loaded, because a IllegalAccessException was thrown: "+e.getLocalizedMessage()));
			} catch (SecurityException e) {
				throw(new RuntimeException(mode+" could not be loaded, because a SecurityException was thrown: "+e.getLocalizedMessage()));
			} catch (NoSuchMethodException e) {
				throw(new RuntimeException(mode+" is no jBubbleBreaker extension, it doesn't have a constructor for (int, int, int)."));
			}
		} else {
			throw(new RuntimeException(mode+" is no jBubbleBreaker extension, it must extend org.jbubblebreaker.Game."));
		}
	}

	/**
	 * Registers a new game mode to jBubbleBreaker
	 * @param bubbleType
	 */
	@SuppressWarnings("unchecked")
	public static void addBubbleType(String bubbleType) {
		Class<Bubble> possibleNewType = null;
		try {
			possibleNewType = (Class<Bubble>) Class.forName(bubbleType);
		} catch (ClassNotFoundException e1) {
			throw(new RuntimeException(bubbleType+" not found!"));
		}
		Class<Bubble> newType = possibleNewType;
		while (possibleNewType.getSuperclass() != null && ! possibleNewType.getSuperclass().getName().equals("org.jbubblebreaker.Bubble")) {
			possibleNewType = (Class<Bubble>) possibleNewType.getSuperclass();
		}
		if (newType != null && possibleNewType.getSuperclass() != null && possibleNewType.getSuperclass().getName().equals("org.jbubblebreaker.Bubble")) {
			try {
				bubbleTypes.add(new BubbleType((String) newType.getDeclaredField("name").get(null),newType.getDeclaredConstructor( new Class[] {int.class, int.class, int.class, int.class})));
			} catch (IllegalArgumentException e) {
				throw(new RuntimeException(bubbleType+" could not be loaded, because a IllegalArgumentException was thrown: "+e.getLocalizedMessage()));				
			} catch (ClassCastException e) {
				throw(new ClassCastException(bubbleType+" is no jBubbleBreaker bubble type extension, it doesn't have the static name-field with type String."));
			} catch (NoSuchFieldException e) {
				throw(new RuntimeException(bubbleType+" is no jBubbleBreaker bubble type extension, it doesn't have the static name-field."));
			} catch (IllegalAccessException e) {
				throw(new RuntimeException(bubbleType+" could not be loaded, because a IllegalAccessException was thrown: "+e.getLocalizedMessage()));
			} catch (SecurityException e) {
				throw(new RuntimeException(bubbleType+" could not be loaded, because a SecurityException was thrown: "+e.getLocalizedMessage()));
			} catch (NoSuchMethodException e) {
				throw(new RuntimeException(bubbleType+" is no jBubbleBreaker bubble type extension, it doesn't have a constructor for (int, int, int, int)."));
			}
		} else {
			throw(new RuntimeException(bubbleType+" is no jBubbleBreaker bubble type extension, it must extend org.jbubblebreaker.Bubble."));
		}
	}

	/**
	 * Returns the registered jBubbleBreate bubble types
	 * @return modi list
	 */
	static List<BubbleType> getBubbleTypes() {
		return bubbleTypes;
	}

	/**
	 * Returns the registered jBubbleBreate game modi
	 * @return modi list
	 */
	static List<GameMode> getModes() {
		return gameModes;
	}
}
