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
package org.jbubblebreaker.gamemodes;

import org.jbubblebreaker.GameSize;

/**
 * Default with Shift Rules
 * @author Sven Strickroth
 */
public class GameShift extends GameDefault {
	/**
	 * Stores the name of this Game-Mode
	 */
	static public String name = "Shift";
	/**
	 * Stores the possible matrix-sizes for this mode
	 */
	static public GameSize allowedSize = new GameSize(0, 0, 0, 0);

	/**
	 * Creates a game with default rules
	 * @param rows of the matrix
	 * @param cols of the matrix
	 */
	public GameShift(int rows, int cols, int bubbleType) {
		super(rows, cols, bubbleType);
	}

	@Override
	public String getMode() {
		return name;
	};

	protected void doShift() {
		// shift empty columns
		for(int l=0; l < playground.getRows(); l++) {
			for(int k=0; k < playground.getCols(); k++) {
				if (playground.isEmpty(l, k) == true) {
					for (int i = k; i > 0; i--)  {
						playground.moveTo(l, i-1, l, i);
					}
				}
			}
		}

	}

	@Override
	protected void removeMarkedBubbles(int row, int col) {
		// first of all delete bubbles in col
		for(int k=0; k < playground.getCols(); k++) {
			for(int i=0; i < playground.getRows(); i++) {
				if (playground.isMarked(i,k) == true) {
					removeBubble(i, k);
					for(int j=i; j > 0; j--) {
						playground.moveTo(j-1, k, j, k);
					}
				}
			}
		}
		doShift();
	}
}
