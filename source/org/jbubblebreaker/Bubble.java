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

import java.awt.Graphics;

import javax.swing.JPanel;

/**
 * Provides the Bubbles
 * @author Sven Strickroth
 */
public abstract class Bubble extends JPanel {
	/**
	 * stores the radius of this Bubble
	 */
	protected int radius;
	/**
	 * stores the row of this Bubble-position
	 */
	private int row;
	/**
	 * stores the column of this Bubble-position
	 */
	private int col;
	/**
	 * is this Bubble marked
	 */
	protected boolean marked = false;
	/**
	 * stores the color-index of this Bubble
	 */
	private int color;

	/**
	 * Create a Bubble on a specific position, size and color
	 * @param radius radius of Bubble
	 * @param row Row of Bubble
	 * @param col Column of Bubble
	 * @param colorIndex color index for new Bubble, if this colorIndex is not valid a random color is used
	 */
	public Bubble(int radius, int row, int col, int colorIndex) {
		this.radius = radius;
		this.setSize(radius, radius);
		moveTo(row, col);
		if (colorIndex >= 0 && colorIndex < getColorsCount()) {
			color = colorIndex;
		} else {
			color = (int) (Math.random() * getColorsCount());
		}
	}

	/**
	 * Resize and move optical position of a Bubble if the radius changed
	 * @param radius new radius of the Bubble
	 */
	final public void resized(int radius) {
		if (this.radius != radius) {
			this.radius = radius;
			this.setSize(radius, radius);
			this.setLocation(getCol() * radius, getRow() * radius);
			repaint();
		}
	}

	/**
	 * Returns the bubble type
	 * @return the bubble type name
	 */
	public abstract String getName();

	/**
	 * Returns the count of different colors available
	 * @return color count
	 */
	protected abstract int getColorsCount();

	/**
	 * Returns the colour-index of this bubble
	 * @return color-index
	 */
	final public int getColorIndex() {
		return color;
	}

	/**
	 * Returns the row of the position of this bubble
	 * @return Row
	 */
	final public int getRow() {
		return row;
	}

	/**
	 * Returns the column of the position of this bubble
	 * @return Col
	 */
	final public int getCol() {
		return col;
	}

	/**
	 * Sets or toggles the mark of this Bubble
	 * @param what mark active
	 */
	final public void setMark(boolean what) {
		if (marked != what) {
			marked = what;
			repaint();
		}
	}

	/**
	 * Returns if this bubble is marked
	 * @return is bubble marked
	 */
	final public boolean isMarked() {
		return marked;
	}

	/**
	 * changes the position of this bubble
	 * @param row row-index
	 * @param col column-index
	 */
	final public void moveTo(int row, int col) {
		this.row = row;
		this.col = col;
		this.setLocation(col * radius, row * radius);
	}

	@Override
	public abstract void paint(Graphics g);
}
