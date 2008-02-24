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

import java.io.Serializable;

/**
 * Stores statistic informations
 * @author Sven Strickroth
 */
@SuppressWarnings("serial")
public class StatisticData implements Serializable {
	private String mode;
	private int rows = 0;
	private int cols = 0;
	private int countOfGames = 0;
	private int maxPoints = 0;
	private int averagePoints = 0;

	public StatisticData(String mode, int rows, int cols, int points) {
		this.mode = mode;
		this.rows = rows;
		this.cols = cols;
		countOfGames = 1;
		maxPoints = points;
		averagePoints = points;
	}
	
	/**
	 * @param points
	 */
	public void newGame(int points) {
		if (points > maxPoints) {
			maxPoints = points;
		}
		countOfGames++;
		averagePoints = (averagePoints + points)/2;
	}
	
	/**
	 * @return the countOfGames
	 */
	public int getCountOfGames() {
		return countOfGames;
	}
	/**
	 * @param countOfGames the countOfGames to set
	 */
	public void setCountOfGames(int countOfGames) {
		this.countOfGames = countOfGames;
	}
	/**
	 * @return the maxPoints
	 */
	public int getMaxPoints() {
		return maxPoints;
	}
	/**
	 * @param maxPoints the maxPoints to set
	 */
	public void setMaxPoints(int maxPoints) {
		this.maxPoints = maxPoints;
	}
	/**
	 * @return the averagePoints
	 */
	public int getAveragePoints() {
		return averagePoints;
	}
	/**
	 * @return the mode
	 */
	public String getMode() {
		return mode;
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
