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

import javax.swing.JLabel;

/**
 * Ongoing Rules
 * @author Sven Strickroth
 */
public class GameOngoing extends GameDefault {

	/**
	 * Creates a game with rules for an ongoing game
	 * @param rows of the matrix
	 * @param cols of the matrix
	 * @param pointsLabel reference to the points Label in the GUI
	 */
	public GameOngoing(int windowWidth, int windowHeight, int rows, int cols, JLabel pointsLabel) {
		super(windowWidth, windowHeight, rows, cols, pointsLabel);
	}

	@Override
	public String getMode() {
		return "Ongoing";
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
