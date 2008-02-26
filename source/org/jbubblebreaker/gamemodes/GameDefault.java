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

import org.jbubblebreaker.Game;
import org.jbubblebreaker.GameSize;

/**
 * Default Rules
 * @author Sven Strickroth
 */
public class GameDefault extends Game {
	/**
	 * Stores the name of this game mode
	 */
	static public String name = "Default";
	/**
	 * Stores the possible matrix-sizes for this mode
	 */
	static public GameSize allowedSize = new GameSize(0, 0, 0, 0);
	
	/**
	 * Creates a game with default rules
	 * @param rows of the matrix
	 * @param cols of the matrix
	 */
	public GameDefault(int rows, int cols, int bubbleType) {
		super(rows, cols, bubbleType);
	}
	
	@Override
	public String getMode() {
		return name;
	};

	@Override
	protected void fillPlayground() {
		for(int i=0; i < playground.getRows(); i++) {
			for(int j=0; j < playground.getCols(); j++) {
				newBubble(i, j);
			}
		}
	}
	
	@Override
	protected void findsame(int row, int col) {
		if (playground.isEmpty(row, col) == true) { return; }
		marked++;
		playground.setMark(row, col, true);
		int color = playground.getColor(row, col);

		if (playground.getColor(row, col + 1) == color && playground.isMarked(row, col + 1) == false) {
			findsame(row,col + 1);
		}
		if (playground.getColor(row, col - 1) == color && playground.isMarked(row, col - 1) == false) {
			findsame(row,col - 1);
		}
		if (playground.getColor(row + 1, col) == color && playground.isMarked(row+1, col) == false) {
			findsame(row + 1,col);
		}
		if (playground.getColor(row - 1, col) == color && playground.isMarked(row - 1, col) == false) {
			findsame(row - 1,col);
		}
	}

	@Override
	protected boolean isSolveable() {
		int row = playground.getRows() - 1;
		int col = playground.getCols() - 1;
		while(col >= 0 && playground.isEmpty(row,col) == false) {
			while(row >= 0 && playground.isEmpty(row, col) == false) {
				if (playground.isEmpty(row - 1, col) == false && playground.getColor(row, col) == playground.getColor(row - 1, col)) {
					return true;
				}
				if (playground.isEmpty(row, col - 1) == false && playground.getColor(row, col) == playground.getColor(row, col - 1)) {
					return true;
				}
				row--;
			}
			col--;
			row = playground.getRows() - 1;
		}
		return false;
	}

	@Override
	protected Integer getCalculatedPoints() {
		return (marked*(marked-1));
	}

	@Override
	protected void removeMarkedBubbles(int row, int col) {
		// first of all delete bubbles in col
		for(int k=0; k < playground.getCols(); k++) {
			for(int i=0; i < playground.getRows(); i++) {
				//System.out.println(i);
				if (playground.isMarked(i,k) == true) {
					//System.out.println("wech:"+i);
					removeBubble(i, k);
					for(int j=i; j > 0; j--) {
						playground.moveTo(j-1, k, j, k);
						//System.out.println("wandern "+(j-1)+" nach "+j);
					}
				}
			}
		}
		// now remove empty columns
		int firstEmpty = -1;
		for(int k=0; k < playground.getCols(); k++) {
			if (playground.isEmpty(playground.getRows()-1, k) == true && firstEmpty == -1) {
				firstEmpty = k;
			}
		}
		for(int k=playground.getCols()-1; k > 0; k--) {
			while (playground.isEmpty(playground.getRows()-1, k) == true && firstEmpty <= k) {
				for(int j=k; j > 0; j--) {
					//System.out.println("wandern "+(j-1)+" nach "+j);
					for(int i=0; i < playground.getRows(); i++) {
						playground.moveTo(i, j-1, i, j);
					}
				}
				firstEmpty++;
			}
		}
	}
}
