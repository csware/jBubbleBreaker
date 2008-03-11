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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with JBubbleBreaker. If not, see <http://www.gnu.org/licenses/>.
 */
package org.jbubblebreaker;

import java.util.MissingResourceException;
import java.util.ResourceBundle;

/**
 * Localization class 
 * @author Sven Strickroth
 */
public class Localization {
	/**
	 * Stores the ResourceBundle
	 */
	final static private ResourceBundle RESOURCE_BUNDLE = ResourceBundle.getBundle("org.jbubblebreaker.i18n.LabelsBundle");

	/**
	 * Returns the localized String, if not found, return the default one (English) or "!key!"
	 * @param key localization key
	 * @return localized string
	 */
	public static String getString(String key) {
		try {
			return RESOURCE_BUNDLE.getString(key);
		} catch (MissingResourceException e) {
			return '!' + key + '!';
		}
	}

	/**
	 * Returns the localized Char, if not found, return the default one (English) or "!"
	 * @param key localization key
	 * @return localized char
	 */
	public static char getChar(String key) {
		try {
			return RESOURCE_BUNDLE.getString(key).charAt(0);
		} catch (MissingResourceException e) {
			return '!';
		}
	}
}
