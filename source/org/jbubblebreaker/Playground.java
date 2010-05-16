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

import java.awt.event.MouseListener;

/**
 * Provides the playground/Bubble matrix
 * @author Sven Strickroth
 */
public class Playground {
	/**
	 * stores the width/column-count of the matrix
	 */
	private int cols;
	/**
	 * stores the height/row-count of the matrix
	 */
	private int rows;
	/**
	 * radius of the bubbles
	 */
	private int radius;
	/**
	 * stores the matrix
	 */
	private Bubble[][] playground;
	/**
	 * stores the reference to the MouseListener
	 */
	MouseListener mouseListener;
	/**
	 * stores the current bubble type
	 */
	private int bubbleType = 0;
	/**
	 * Stores the differnet colors the current bubble type provides
	 */
	private int bubbleColors = 0;

	/**
	 * Creates a new empty Playground/Matrix
	 * @param windowWidth width of the windows which contains the playground
	 * @param windowHeight height of the windows which contains the playground
	 * @param rows row count
	 * @param cols column count
	 * @param ml MouseListener
	 * @param bubbleType bubbleType index
	 */
	public Playground(int windowWidth, int windowHeight, int rows, int cols, MouseListener ml, int bubbleType) {
		this.cols = cols;
		this.rows = rows;
		this.bubbleType = bubbleType;

		radius = calulateRadius(windowWidth, windowHeight);
		playground = new Bubble[rows][cols];
		mouseListener = ml;
	}

	/**
	 * Resizes the playground if changed to the new dimension
	 * @param windowWidth new width of the playground
	 * @param windowHeight new height of the playground
	 */
	public void resized(int windowWidth, int windowHeight) {
		if (radius != calulateRadius(windowWidth, windowHeight)) {
			radius = calulateRadius(windowWidth, windowHeight);
			int row = getRows() - 1;
			int col = getCols() - 1;
			while (col >= 0 && isEmpty(row, col) == false) {
				while (row >= 0 && isEmpty(row, col) == false) {
					if (isEmpty(row, col) == false) {
						getBubble(row, col).resized(radius);
					}
					row--;
				}
				col--;
				row = rows - 1;
			}
		}
	}

	/**
	 * Calculates the best radius for the Bubbles from the window-size
	 * @param windowWidth window/jpanel width
	 * @param windowHeight window/jpanel height
	 * @return calculated radius for the Bubbles
	 */
	private int calulateRadius(int windowWidth, int windowHeight) {
		if (windowWidth / getCols() < windowHeight / getRows()) {
			return windowWidth / getCols();
		} else {
			return windowHeight / getRows();
		}
	}

	/**
	 * Creates a new Bubble on the playground at position x,y
	 * @param x row-index
	 * @param y column-index
	 * @return true if Bubble created, false otherwise
	 */
	boolean newBubble(int x, int y) {
		return newBubble(x, y, -1);
	}

	/**
	 * Creates a new Bubble on the playground at position x,y with a special color
	 * @param x row-index
	 * @param y column-index
	 * @param colorIndex color-index
	 * @return true if Bubble created, false otherwise
	 */
	boolean newBubble(int x, int y, int colorIndex) {
		if (x >= 0 && y >= 0 && x < getRows() && y < getCols()) {
			playground[x][y] = null;
			try {
				playground[x][y] = (Bubble) JBubbleBreaker.getBubbleTypes().get(bubbleType).getConstructor().newInstance(new Object[] { radius, x, y, colorIndex });
				playground[x][y].addMouseListener(mouseListener);
			} catch (Exception e) {
				e.printStackTrace();
			}
			if (playground[x][y] != null) {
				if (bubbleColors == 0) {
					bubbleColors = playground[x][y].getColorsCount();
				}
				return true;
			}
		}
		return false;
	}

	/**
	 * Returns the differnet colors the current bubble type provides
	 * @return the count of colors
	 */
	public int getColors() {
		return bubbleColors;
	}

	/**
	 * Returns the color-index of a  Bubble at a specific position in the matrix
	 * @param x row-index
	 * @param y column-index
	 * @return Bubble-Color-index, -1 if this Bubble doesn't exist
	 */
	public int getColor(int x, int y) {
		if (isEmpty(x, y) == true) {
			return -1;
		} else {
			return playground[x][y].getColorIndex();
		}
	}

	/**
	 * Returns the Bubble of a  Bubble at a specific position in the matrix
	 * @param x row-index
	 * @param y column-index
	 * @return Bubble-reference, null if this Bubble doesn't exist
	 */
	Bubble getBubble(int x, int y) {
		if (isEmpty(x, y) == false) {
			return playground[x][y];
		} else {
			return null;
		}
	}

	/**
	 * Checks if at a specific position a Bubble exists
	 * @param x row-index
	 * @param y column-index
	 * @return exists a Bubble at x,y?
	 */
	public boolean isEmpty(int x, int y) {
		if (x < 0 || y < 0 || x >= getRows() || y >= getCols()) {
			return true;
		}
		return (playground[x][y] == null);
	}

	/**
	 * Checks if at a specific position a Bubble is marked
	 * @param x row-index
	 * @param y column-index
	 * @return is marked?, false if Bubble doesn't exist
	 */
	public boolean isMarked(int x, int y) {
		if (isEmpty(x, y) == true) {
			return false;
		}
		return playground[x][y].isMarked();
	}

	/**
	 * Moves a specific Bubble to a new location in the matrix, regardless if on the destination there is a Bubble
	 * @param x old row-index
	 * @param y old column-index
	 * @param toX new row-index 0 <= toX < rows, x != toX
	 * @param toY new column-index 0 <= toY < rows, y != toY
	 */
	public void moveTo(int x, int y, int toX, int toY) {
		if (x != toX || y != toY) {
			playground[toX][toY] = playground[x][y];
			if (isEmpty(toX, toY) == false) {
				playground[toX][toY].moveTo(toX, toY);
			}
			playground[x][y] = null;
		}
	}

	/**
	 * Removes a Bubble on a specific location from the matrix
	 * @param x row-index
	 * @param y column-index
	 */
	void removeBubble(int x, int y) {
		if (isEmpty(x, y) == false) {
			playground[x][y] = null;
		}
	}

	/**
	 * Mark a specific Bubble
	 * @param x row-index
	 * @param y column-index
	 * @param setTo set mark to setTo
	 */
	public void setMark(int x, int y, boolean setTo) {
		if (isEmpty(x, y) == false) {
			playground[x][y].setMark(setTo);
		}
	}

	/**
	 * Unmarks all Bubbles on the playground
	 */
	public void unMarkAll() {
		int row = getRows() - 1;
		int col = getCols() - 1;
		while (col >= 0 && isEmpty(row, col) == false) {
			while (row >= 0 && isEmpty(row, col) == false) {
				setMark(row, col, false);
				row--;
			}
			col--;
			row = rows - 1;
		}
	}

	/**
	 * Returns the column-count of the matrix
	 * @return playground width/column count
	 */
	public int getCols() {
		return cols;
	}

	/**
	 * Returns the row-count of the matrix
	 * @return playground height/row count
	 */
	public int getRows() {
		return rows;
	}
}
