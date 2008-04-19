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

/**
 * Stores the allowed game size for a game mode
 * @author Sven Strickroth
 */
public class GameSize {
	/**
	 * Stores the min. size of a quadratric matrix
	 */
	final static public int minSize = 5;
	/**
	 * Stores the max. size of a quadratric matrix
	 */
	final static public int maxSize = 100;

	private int minRows = minSize;
	private int maxRows = maxSize;
	private int minColumns = minSize;
	private int maxColumns = maxSize;
	
	/**
	 * Sets the allowed size
	 * @param minRows min. count of rows
	 * @param maxRows max. count of rows
	 * @param minColumns min. count of columns
	 * @param maxColumns max. count of columns
	 */
	public GameSize(int minRows, int maxRows, int minColumns, int maxColumns) {
		super();
		if (minRows >= minSize) {
			this.minRows = minRows;
		}
		if (minColumns >= minSize) {
			this.minColumns = minColumns;
		}
		if (maxRows <= maxSize && maxRows >= this.minColumns) {
			this.maxRows = maxRows;
		}
		if (maxColumns <= maxSize && maxColumns >= this.minColumns) {
			this.maxColumns = maxColumns;
		}
	}

	/**
	 * @return the maxColumns
	 */
	public int getMaxColumns() {
		return maxColumns;
	}

	/**
	 * @return the maxRows
	 */
	public int getMaxRows() {
		return maxRows;
	}

	/**
	 * @return the minColumns
	 */
	public int getMinColumns() {
		return minColumns;
	}

	/**
	 * @return the minRows
	 */
	public int getMinRows() {
		return minRows;
	}
}
