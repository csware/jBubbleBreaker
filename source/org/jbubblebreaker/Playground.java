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
 * @author Sven Strickroth
 */
public class Playground {
	private int cols = 12;
	private int rows = 12;
	private int horizontal = 0;
	private int vertikal = 0;
	private Bubble[][] playground;

	public Playground(int rows, int cols, MouseListener ml) {
		this.cols = cols;
		this.rows = rows;
		horizontal = 400 / cols;
		vertikal = 400 / rows;
		playground = new Bubble[rows][cols];
		for(int i=0; i < rows; i++) {
			for(int j=0; j < cols; j++) {
				playground[i][j] = new Bubble(horizontal,vertikal, i, j, ml);
			}
		}
	}

	public int getColor(int x, int y) {
		if (isEmpty(x,y) == true) {
			return -1;
		} else {
			return playground[x][y].getColor();
		}
	}
	
	public Bubble getBubble(int x, int y) {
		if (isEmpty(x,y) == false) {
			return playground[x][y];
		} else {
			return null;
		}
	}

	public boolean isEmpty(int x, int y) {
		if (x < 0 || y < 0 || x >= rows || y >= cols) {
			return true;
		}
		return (playground[x][y]==null);
	}

	public boolean isMarked(int x, int y) {
		if (isEmpty(x,y) == true) {
			return false;
		}
		return playground[x][y].isMarked();
	}

	
	public void moveTo(int x, int y, int toX, int toY) {
		if (x != toX || y != toY) {
			playground[toX][toY] = playground[x][y];
			if (isEmpty(toX,toY) == false) {
				playground[toX][toY].moveTo(toX,toY);
				playground[x][y] = null;
			}
		}
	}

	public void unMark(int x, int y) {
		if (isEmpty(x,y) == false) {
			playground[x][y].setMark(false);
		}
	}

	/**
	 * @return the cols
	 */
	public int getCols() {
		return cols;
	}

	/**
	 * @return the rows
	 */
	public int getRows() {
		return rows;
	}
}
