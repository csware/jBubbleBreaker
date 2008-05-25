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
 * Ongoing with Shift Rules 2
 * @author Sven Strickroth
 */
public class GameShiftOngoing2 extends GameShift {
	/**
	 * Stores the name of this Game-Mode
	 */
	static public String name = "ShiftOngoing2";
	/**
	 * Stores the possible matrix-sizes for this mode
	 */
	static public GameSize allowedSize = new GameSize(0, 0, 0, 0);

	/**
	 * Creates a game with default rules
	 * @param rows of the matrix
	 * @param cols of the matrix
	 */
	public GameShiftOngoing2(int rows, int cols, int bubbleType) {
		super(rows, cols, bubbleType);
	}

	@Override
	public String getMode() {
		return name;
	};

	@Override
	protected boolean isSolvable() {
		return false;
	}

	@Override
	protected void removeMarkedBubbles(int row, int col) {
		super.removeMarkedBubbles(row, col);
		int i = playground.getRows() - 1;
		while (i > 0 && playground.isEmpty(i, 0) == false) {
			i--;
		}
		newBubble(i, 0);
		doShift();
	}
}
