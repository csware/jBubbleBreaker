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

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.InvalidPropertiesFormatException;
import java.util.LinkedList;
import java.util.List;
import java.util.Properties;

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
	 * Stores the user properties
	 */
	static private Properties userProperties = null;
	/**
	 * Stores if we are in applicationMode
	 */
	static private Boolean applicationMode = false;

	/**
	 * Starts jBubbleBreaker
	 * @param args no parameter evaluation
	 */
	public static void main(String[] args) {
		// move old files
		File a = new File(System.getProperty("user.home") + "/.jbubblebreaker");
		if (!a.exists()) {
			a.mkdir();
		}
		a = new File(System.getProperty("user.home") + "/.jbubblebreaker-statistics");
		if (a.exists() && a.isFile()) {
			a.renameTo(new File(System.getProperty("user.home") + "/.jbubblebreaker/statistics"));
		}
		a = new File(System.getProperty("user.home") + "/.jbubblebreaker-properties");
		if (a.exists() && a.isFile()) {
			a.renameTo(new File(System.getProperty("user.home") + "/.jbubblebreaker/properties"));
		}
		applicationMode = true;
		registerDefault();
		GUI.startGUI();
	}

	/**
	 * Returns the version number
	 * @return version number
	 */
	static public String getVersion() {
		// check application.properties for all locations where version needs to kept up to date
		return "0.8";
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
		addMode("org.jbubblebreaker.gamemodes.GameLevelBreaker");
		addMode("org.jbubblebreaker.gamemodes.GameLevelBreakerShift");
		addBubbleType("org.jbubblebreaker.bubbles.Bubble3DCircle");
		addBubbleType("org.jbubblebreaker.bubbles.Bubble3DCircleSix");
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
			throw (new RuntimeException(mode + " not found!"));
		}
		Class<Game> newMode = possibleNewMode;
		while (possibleNewMode.getSuperclass() != null && !possibleNewMode.getSuperclass().getName().equals("org.jbubblebreaker.Game")) {
			possibleNewMode = (Class<Game>) possibleNewMode.getSuperclass();
		}
		if (newMode != null && possibleNewMode.getSuperclass() != null && possibleNewMode.getSuperclass().getName().equals("org.jbubblebreaker.Game")) {
			try {
				gameModes.add(new GameMode((String) newMode.getDeclaredField("name").get(null), (GameSize) newMode.getDeclaredField("allowedSize").get(null), newMode.getDeclaredConstructor(new Class[] { int.class, int.class, int.class })));
			} catch (IllegalArgumentException e) {
				throw (new RuntimeException(mode + " could not be loaded, because a IllegalArgumentException was thrown: " + e.getLocalizedMessage()));
			} catch (ClassCastException e) {
				throw (new ClassCastException(mode + " is no jBubbleBreaker extension, it doesn't have the static name-field with type String or the static allowedSize-field with type GameSize."));
			} catch (NoSuchFieldException e) {
				throw (new RuntimeException(mode + " is no jBubbleBreaker extension, it doesn't have the static name- or allowedSize-field."));
			} catch (IllegalAccessException e) {
				throw (new RuntimeException(mode + " could not be loaded, because a IllegalAccessException was thrown: " + e.getLocalizedMessage()));
			} catch (SecurityException e) {
				throw (new RuntimeException(mode + " could not be loaded, because a SecurityException was thrown: " + e.getLocalizedMessage()));
			} catch (NoSuchMethodException e) {
				throw (new RuntimeException(mode + " is no jBubbleBreaker extension, it doesn't have a constructor for (int, int, int)."));
			}
		} else {
			throw (new RuntimeException(mode + " is no jBubbleBreaker extension, it must extend org.jbubblebreaker.Game."));
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
			throw (new RuntimeException(bubbleType + " not found!"));
		}
		Class<Bubble> newType = possibleNewType;
		while (possibleNewType.getSuperclass() != null && !possibleNewType.getSuperclass().getName().equals("org.jbubblebreaker.Bubble")) {
			possibleNewType = (Class<Bubble>) possibleNewType.getSuperclass();
		}
		if (newType != null && possibleNewType.getSuperclass() != null && possibleNewType.getSuperclass().getName().equals("org.jbubblebreaker.Bubble")) {
			try {
				bubbleTypes.add(new BubbleType((String) newType.getDeclaredField("name").get(null), newType.getDeclaredConstructor(new Class[] { int.class, int.class, int.class, int.class })));
			} catch (IllegalArgumentException e) {
				throw (new RuntimeException(bubbleType + " could not be loaded, because a IllegalArgumentException was thrown: " + e.getLocalizedMessage()));
			} catch (ClassCastException e) {
				throw (new ClassCastException(bubbleType + " is no jBubbleBreaker bubble type extension, it doesn't have the static name-field with type String."));
			} catch (NoSuchFieldException e) {
				throw (new RuntimeException(bubbleType + " is no jBubbleBreaker bubble type extension, it doesn't have the static name-field."));
			} catch (IllegalAccessException e) {
				throw (new RuntimeException(bubbleType + " could not be loaded, because a IllegalAccessException was thrown: " + e.getLocalizedMessage()));
			} catch (SecurityException e) {
				throw (new RuntimeException(bubbleType + " could not be loaded, because a SecurityException was thrown: " + e.getLocalizedMessage()));
			} catch (NoSuchMethodException e) {
				throw (new RuntimeException(bubbleType + " is no jBubbleBreaker bubble type extension, it doesn't have a constructor for (int, int, int, int)."));
			}
		} else {
			throw (new RuntimeException(bubbleType + " is no jBubbleBreaker bubble type extension, it must extend org.jbubblebreaker.Bubble."));
		}
	}

	/**
	 * Returns the registered jBubbleBreate bubble types
	 * @return mode list
	 */
	static List<BubbleType> getBubbleTypes() {
		return bubbleTypes;
	}

	/**
	 * Returns the registered jBubbleBreate game modi
	 * @return mode list
	 */
	static List<GameMode> getModes() {
		return gameModes;
	}

	/**
	 * Loads the user properties from the user property file
	 */
	private static void loadUserProperties() {
		if (userProperties == null) {
			userProperties = new Properties();
			if (isApplicationMode()) {
				try {
					userProperties.loadFromXML(new FileInputStream(System.getProperty("user.home") + "/.jbubblebreaker/properties"));
				} catch (Exception e) {
					// ignore
				}
			}
		}
	}

	/**
	 * Returns the user property value for a specific property
	 * @param property name of the property
	 * @param defaultValue default value for property
	 * @return property value
	 */
	static String getUserProperty(String property, String defaultValue) {
		loadUserProperties();
		return userProperties.getProperty(property, defaultValue);
	}

	/**
	 * Sets a user property to a specific value
	 * @param property name of the property
	 * @param value value to set the property to
	 */
	static void setUserProperty(String property, String value) {
		loadUserProperties();
		userProperties.setProperty(property, value);
		try {
			userProperties.storeToXML(new FileOutputStream(System.getProperty("user.home") + "/.jbubblebreaker/properties"), "");
		} catch (Exception e) {
			// ignore
		}
	}

	/**
	 * Are we in applicationMode (i.e. not in an applet)
	 * @return if we are in application mode
	 */
	public static Boolean isApplicationMode() {
		return applicationMode;
	}

	/**
	 * Checks on the WebSite if a new version is available.
	 * @return true if an update is available, false otherwise
	 * @throws IOException
	 */
	public static boolean checkForUpdate() throws IOException {
		Properties versionProperties = new Properties();
		try {
			versionProperties.loadFromXML(new URL(getProjectHomepage() + "/jbubblebreaker.xml").openStream());
		} catch (MalformedURLException e) {
			// ignore
		}
		if (getVersion().equalsIgnoreCase(versionProperties.getProperty("latestVersion"))) {
			return false;
		} else {
			return true;
		}
	}
}
