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
	 * width of the playground JPanel
	 */
	private int horizontal;
	/**
	 * height of the playground JPanel
	 */
	private int vertikal;
	/**
	 * stores the matrix
	 */
	private Bubble[][] playground;

	/**
	 * stores the reference to the MouseListener
	 */
	MouseListener mouseListener;
	
	/**
	 * Creates a new empty Playground/Matrix
	 * @param rows row count
	 * @param cols col count
	 * @param ml MouseListener
	 */
	public Playground(int windowWidth, int windowHeight, int rows, int cols, MouseListener ml) {
		this.cols = cols;
		this.rows = rows;
		horizontal = windowWidth / cols;
		vertikal = windowHeight / rows;
		playground = new Bubble[rows][cols];
		mouseListener = ml;
	}

	public void resized(int windowWidth, int windowHeight) {
		horizontal = windowWidth / cols;
		vertikal = windowHeight / rows;
		int row = cols - 1;
		int col = rows - 1;
		while(col >= 0 && isEmpty(row,col) == false) {
			while(row >= 0 && isEmpty(row, col) == false) {
				if (isEmpty(row, col) == false) {
					getBubble(row, col).resized(horizontal, vertikal);
				}
				row--;
			}
			col--;
			row = rows - 1;
		}
	}

	
	/**
	 * Creates a new Bubble on the playground at position x,y
	 * @param x row-index
	 * @param y column-index
	 */
	public void newBubble(int x, int y) {
		if (x >= 0 || y >= 0 || x < rows || y < cols) {
			playground[x][y] = new Bubble(horizontal,vertikal, x, y, mouseListener);
		}
	}

	/**
	 * Creates a new Bubble on the playground at position x,y with a special color
	 * @param x row-index
	 * @param y column-index
	 * @param colorIndex color-index
	 */
	public void newBubble(int x, int y, int colorIndex) {
		if (x >= 0 || y >= 0 || x < rows || y < cols) {
			playground[x][y] = new Bubble(horizontal,vertikal, x, y, mouseListener, colorIndex);
		}
	}
	
	/**
	 * Returns the color-index of a  Bubble at a specific position in the matrix
	 * @param x row-index
	 * @param y column-index
	 * @return Bubble-Color-index, -1 if this Bubble doesn't exist 
	 */
	public int getColor(int x, int y) {
		if (isEmpty(x,y) == true) {
			return -1;
		} else {
			return playground[x][y].getColor();
		}
	}
	
	/**
	 * Returns the Bubble of a  Bubble at a specific position in the matrix
	 * @param x row-index
	 * @param y column-index
	 * @return Bubble-reference, null if this Bubble doesn't exist
	 */
	public Bubble getBubble(int x, int y) {
		if (isEmpty(x,y) == false) {
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
		if (x < 0 || y < 0 || x >= rows || y >= cols) {
			return true;
		}
		return (playground[x][y]==null);
	}

	/**
	 * Checks if at a specific position a Bubble is marked
	 * @param x row-index
	 * @param y column-index
	 * @return is marked?, false if Bubble doesn't exist
	 */
	public boolean isMarked(int x, int y) {
		if (isEmpty(x,y) == true) {
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
			if (isEmpty(toX,toY) == false) {
				playground[toX][toY].moveTo(toX,toY);
			}
			playground[x][y] = null;
		}
	}

	/**
	 * Removes a Bubble on a specific location from the matrix
	 * @param x row-index
	 * @param y column-index
	 */
	public void removeBubble(int x, int y) {
		if (isEmpty(x,y) == false) {
			playground[x][y] = null;
		}
	}
	
	/**
	 * Unmark a specific Bubble 
	 * @param x row-index
	 * @param y column-index
	 */
	public void unMark(int x, int y) {
		if (isEmpty(x,y) == false) {
			playground[x][y].setMark(false);
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
