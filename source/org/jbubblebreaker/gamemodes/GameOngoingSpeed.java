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
package org.jbubblebreaker.gamemodes;

import org.jbubblebreaker.GameSize;

/**
 * Ongoing Speed Rules
 * @author Sven Strickroth
 */
public class GameOngoingSpeed extends GameOngoing {
	/**
	 * Stores the name of this Game-Mode
	 */
	static public String name = "OngoingSpeed";
	/**
	 * Stores the possible matrix-sizes for this mode
	 */
	static public GameSize allowedSize = new GameSize(0, 0, 0, 0);
	/**
	 * Stores the timestamp for time differenz calculations
	 */
	private long time;

	/**
	 * Creates a game with rules for an ongoing game
	 * @param rows of the matrix
	 * @param cols of the matrix
	 */
	public GameOngoingSpeed(int rows, int cols, int bubbleType) {
		super(rows, cols, bubbleType);
	}

	@Override
	public String getMode() {
		return name;
	};

	@Override
	protected void removeMarkedBubbles(int row, int col) {
		super.removeMarkedBubbles(row, col);
		time = System.currentTimeMillis();
	}

	@Override
	protected void fillPlayground() {
		super.fillPlayground();
		time = System.currentTimeMillis();
	}

	@Override
	protected Integer getCalculatedPoints() {
		int calculatedTime = 10-(int)((System.currentTimeMillis()-time)/100);
		if (calculatedTime < 0) {
			calculatedTime = 0;
		}
		return (marked*calculatedTime);
	}
}
