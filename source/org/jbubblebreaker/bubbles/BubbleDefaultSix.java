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
package org.jbubblebreaker.bubbles;

import java.awt.Color;

/**
 * Provides the default Bubbles with 6 different colors
 * @author Sven Strickroth
 */
@SuppressWarnings("serial")
public class BubbleDefaultSix extends BubbleDefault {
	/**
	 * Stores the name of this bubble set
	 */
	static public String name = "Default 6 colors";

	/**
	 * Create a Bubble on a specific position and size with a random color
	 * @param radius radius of Bubble
	 * @param row Row of Bubble
	 * @param col Column of Bubble
	 */
	public BubbleDefaultSix(int radius, int row, int col) {
		super(radius, row, col);
	}

	/**
	 * Create a Bubble on a specific position, size and color
	 * @param radius radius of Bubble
	 * @param row Row of Bubble
	 * @param col Column of Bubble
	 * @param colorIndex color index for new Bubble, if this colorIndex is not valid a random color is used
	 */
	public BubbleDefaultSix(int radius, int row, int col, int colorIndex) {
		super(radius, row, col, colorIndex);
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	protected Color[] getAllColors() {
		return new Color[] {Color.BLUE, Color.RED, Color.YELLOW, Color.GREEN, Color.MAGENTA, Color.CYAN};
	}
}
