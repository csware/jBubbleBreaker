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

import java.lang.reflect.Constructor;

/**
 * Datatype for storing bubble types
 * @author Sven Strickroth
 */
public class BubbleType {
	/**
	 * Stores the type name
	 */
	private String typeName;
	/**
	 * Stores the constuctor for this type
	 */
	private Constructor constructor;

	/**
	 * Creates a datastorage for a bubble type
	 * @param typeName bubble type name
	 * @param constructor Reference of the constructor
	 */
	public BubbleType(String typeName, Constructor constructor) {
		this.typeName = typeName;
		this.constructor = constructor;
	}

	/**
	 * Returns the type name 
	 * @return the modiName
	 */
	public String getTypeName() {
		return typeName;
	}

	/**
	 * Returns the reference to the constructor with (int, int, int, int) parameters
	 * @return the constructor
	 */
	public Constructor getConstructor() {
		return constructor;
	}
}
