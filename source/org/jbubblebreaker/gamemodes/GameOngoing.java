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
package org.jbubblebreaker.gamemodes;

import org.jbubblebreaker.GameSize;

/**
 * Ongoing Rules
 * @author Sven Strickroth
 */
public class GameOngoing extends GameDefault {
	/**
	 * Stores the name of this Game-Mode
	 */
	static public String name = "Ongoing";
	/**
	 * Stores the possible matrix-sizes for this mode
	 */
	static public GameSize allowedSize = new GameSize(0, 0, 0, 0);
	
	/**
	 * Creates a game with rules for an ongoing game
	 * @param rows of the matrix
	 * @param cols of the matrix
	 */
	public GameOngoing(int rows, int cols) {
		super(rows, cols);
	}

	@Override
	public String getMode() {
		return name;
	};
	
	@Override
	protected void removeMarkedBubbles(int row, int col) {
		super.removeMarkedBubbles(row, col);
		// add new Bubbles at the left frontier
		for(int k=0; k < playground.getCols(); k++) {
			if (playground.isEmpty(playground.getRows()-1, k) == true) {
				for(int i=playground.getRows() - 1; i >= Math.random()*(playground.getRows() - 1); i--) {
					playground.newBubble(i, k);
					playgroundPanel.add(playground.getBubble(i, k));
				}
			} else {
				break;
			}
		}
 
	}
}
