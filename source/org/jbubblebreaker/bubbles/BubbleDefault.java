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
import java.awt.Graphics;

import org.jbubblebreaker.Bubble;

/**
 * Provides the Default Bubbles
 * @author Sven Strickroth
 */
@SuppressWarnings("serial")
public class BubbleDefault extends Bubble {
	/**
	 * Stores the name of this bubble set
	 */
	static public String name = "Default";

	/**
	 * Create a Bubble on a specific position and size with a random color
	 * @param radian radian of Bubble
	 * @param row Row of Bubble
	 * @param col Col of Bubble
	 */
	public BubbleDefault(int radian, int row, int col) {
		super(radian, row, col, -1);
	}

	/**
	 * Create a Bubble on a specific position, size and color
	 * @param radian radian of Bubble
	 * @param row Row of Bubble
	 * @param col Col of Bubble
	 * @param colorIndex color index for new Bubble, if this colorIndex is not valid a random color is used
	 */
	public BubbleDefault(int radian, int row, int col, int colorIndex) {
		super(radian, row, col, colorIndex);
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public void paint(Graphics g) {
		if (isMarked() == true) {
			g.setColor(Color.GRAY);
			g.fillRect(0, 0, radian, radian);
		}
		g.setColor(getColor(getColorIndex()));
		g.fillOval(0, 0, radian, radian);

	}

	/**
	 * Stores all real colors
	 * @return color-array
	 */
	protected Color[] getAllColors() {
		return new Color[] {Color.BLUE, Color.RED, Color.YELLOW, Color.GREEN, Color.MAGENTA};
	}

	/**
	 * Returns the color with color index from the getAllColors() array
	 * @param colorIndex
	 * @return the specific real color out of the getAllColors() array
	 */
	protected Color getColor(int colorIndex) {
		return getAllColors()[colorIndex];
	}

	@Override
	protected int getColorsCount() {
		return getAllColors().length;
	}
}
