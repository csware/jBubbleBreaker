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
 * Level Breaker Rules
 * @author Sven Strickroth
 */
public class GameLevelBreaker extends GameDefault {
	/**
	 * Stores the name of this Game-Mode
	 */
	static public String name = "Level Breaker";
	/**
	 * Stores the possible matrix-sizes for this mode
	 */
	static public GameSize allowedSize = new GameSize(0, 0, 0, 0);
	/**
	 * store the gameLevel of the current game
	 */
	private int gameLevel;

	/**
	 * Creates a game with rules for an ongoing game
	 * @param rows of the matrix
	 * @param cols of the matrix
	 */
	public GameLevelBreaker(int rows, int cols, int bubbleType) {
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
	protected void prepareNewGame() {
		gameLevel = 2;
	}

	@Override
	protected int solvedAction() {
		if (gameLevel < playground.getColors()) {
			gameLevel++;
			fillPlayground();
			playgroundPanel.repaint();
			playgroundPanel.setEnabled(true);
		}
		return -1;
	}

	@Override
	protected void fillPlayground() {
		for (int i = 0; i < playground.getRows(); i++) {
			for (int j = 0; j < playground.getCols(); j++) {
				newBubble(i, j, (int) (Math.random() * gameLevel));
			}
		}
	}
}
