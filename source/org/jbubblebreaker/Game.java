/*
 * Copyright 2008 - 2010 Sven Strickroth <email@cs-ware.de>
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

import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

/**
 * Provides the jBubbleBreaker core (main game logic)
 * @author Sven Strickroth
 */
public abstract class Game extends MouseAdapter {
	/**
	 * Stores the number of marked Bubbles
	 */
	protected int marked = 0;
	/**
	 * Stores the JPanel with the Bubble-matrix
	 */
	protected JPanel playgroundPanel = new JPanel();
	/**
	 * Stores a JLabel where the possible points are shown
	 */
	private JLabel possiblePoints = new JLabel("0");
	/**
	 * Stores the playground, the matrix of Bubbles
	 */
	protected Playground playground;
	private Integer[][] playgroundCopy;
	/**
	 * Stores a reference of the points label in the GUI, instantiate on creation with a JPanel to avoid null-PounterExceptions
	 */
	private JLabel pointsLabel = new JLabel();
	/**
	 * Stores the points the user gained
	 */
	private int points = 0;
	private int oldPoints = 0;

	private boolean firstMove = true;
	private GameLifecycleObserverIf gameLifecycleObserver;
	
	/**
	 * Prepares a new playground
	 * @param rows of the matrix
	 * @param cols of the matrix
	 * @param bubbleType bubbleType index
	 */
	public Game(int rows, int cols, int bubbleType) {
		playgroundPanel.setLayout(null);

		playground = new Playground(playgroundPanel.getWidth(), playgroundPanel.getHeight(), rows, cols, this, bubbleType);
		playgroundCopy = new Integer[rows][cols];

		possiblePoints.setSize(50, 50);
		playgroundPanel.add(possiblePoints);
		possiblePoints.setVisible(false);

		newGame();

		playgroundPanel.addComponentListener(new ComponentAdapter() {
			public void componentResized(ComponentEvent arg0) {
				playground.resized(playgroundPanel.getWidth(), playgroundPanel.getHeight());
				playgroundPanel.repaint();
			}
		});
		playgroundPanel.addMouseListener(this);
	}

	/**
	 * sets the label where to show the points
	 * @param pointsLabel reference to the points Label in the GUI
	 */
	final void setPointsLabel(JLabel pointsLabel) {
		this.pointsLabel = pointsLabel;
		pointsLabel.setText(Localization.getString("Points") + points);
	}

	/**
	 * Creates a new game.
	 * Resets all values and creates a new Bubble matrix.
	 */
	final public void newGame() {
		points = 0;
		marked = 0;
		firstMove = true;
		possiblePoints.setVisible(false);
		playgroundPanel.setVisible(false);
		prepareNewGame();

		removeAllBubblesFromPlayground();
		fillPlayground();
		playgroundPanel.setVisible(true);
		playgroundPanel.setEnabled(true);
		if (isPlaygroundSolvable() == false) {
			gameOver(0);
		}
	}

	/**
	 * Removes all bubbles from playgroundPanel
	 */
	private void removeAllBubblesFromPlayground() {
		int row = playground.getRows() - 1;
		int col = playground.getCols() - 1;
		while (col >= 0 && playground.isEmpty(row, col) == false) {
			while (row >= 0 && playground.isEmpty(row, col) == false) {
				removeBubble(row, col);
				row--;
			}
			col--;
			row = playground.getRows() - 1;
		}
	}

	/**
	 * Game modes can add specific code which gets executed when a new Game is started.
	 */
	protected void prepareNewGame() {}

	/**
	 * fills the playground/matrix with Bubbles
	 */
	protected abstract void fillPlayground();

	/**
	 * Returns the JPanel with the Bubbles inside
	 * @return playground-JPanel
	 */
	final public JPanel getPanel() {
		return playgroundPanel;
	}

	/**
	 * Find (recursively) all Bubbles with the same color-index which are next to each other
	 * @param row row-index to start the search
	 * @param col column-index to start the search
	 */
	protected abstract void findsame(int row, int col);

	/**
	 * This method is called, when game is over (solved or not solvable any more)
	 * @param solvedPoints the number of points the user got for solving the game, not used if solvedPoints < 0 or game mode is not solvable
	 * @see #isSolvable()
	 */
	final private void gameOver(int solvedPoints) {
		gameLifecycleObserver.gameOver();
		playgroundPanel.setEnabled(false);
		if (solvedPoints >= 0 && isSolvable()) {
			JOptionPane.showMessageDialog(null, Localization.getString("GameOver") + "\n" + Localization.getString("Points") + getPoints() + "\n" + Localization.getString("BreakerBonus") + ": " + solvedPoints + Statistics.updateStatistics(getMode(), playground.getColors(), playground.getRows(), playground.getCols(), getPoints() + solvedPoints), "jBubbleBreaker", JOptionPane.INFORMATION_MESSAGE);
		} else {
			JOptionPane.showMessageDialog(null, Localization.getString("GameOver") + "\n" + Localization.getString("Points") + getPoints() + Statistics.updateStatistics(getMode(), playground.getColors(), playground.getRows(), playground.getCols(), getPoints()), "jBubbleBreaker", JOptionPane.INFORMATION_MESSAGE);
		}
	}

	/**
	 * Checks if the current game is solvable
	 * @return playground solvable?
	 */
	protected abstract boolean isPlaygroundSolvable();

	/**
	 * Returns if the current game mode is solvable (i.e. not endless)
	 * @return game mode solvable?
	 */
	protected abstract boolean isSolvable();

	/**
	 * Action to perform when playground is solved (i.e. all bubbles are removed by the user)
	 * @return points the user gets if he solved the game (i.e. cleared all bubbles). If -1 then gameOver(X) isn't called.
	 * @see #isSolvable()
	 * @see #gameOver(int solvedPoints)
	 */
	protected int solvedAction() {
		return 0;
	}

	/**
	 * Remove all marked Bubbles
	 * @param row row-index of a marked Bubble, unused atm
	 * @param col column-index of a marked Bubble, unused atm
	 */
	protected abstract void removeMarkedBubbles(int row, int col);

	/**
	 * Creates a new Bubble on the playground at position x,y if the position is empty
	 * @param row row-index
	 * @param col column-index
	 */
	final protected void newBubble(int row, int col) {
		if (playground.isEmpty(row, col) == true) {
			if (playground.newBubble(row, col) == true) {
				playgroundPanel.add(playground.getBubble(row, col));
			}
		}
	}

	/**
	 * Creates a new Bubble on the playground at position x,y with a special color if the position is empty
	 * @param row row-index
	 * @param col column-index
	 * @param colorIndex color-index
	 */
	final protected void newBubble(int row, int col, int colorIndex) {
		if (playground.isEmpty(row, col) == true) {
			playground.newBubble(row, col, colorIndex);
			playgroundPanel.add(playground.getBubble(row, col));
		}
	}

	/**
	 * Removes a special Bubble
	 * @param row row-index
	 * @param col column-index
	 */
	final protected void removeBubble(int row, int col) {
		if (playground.isEmpty(row, col) == false) {
			playgroundPanel.remove(playground.getBubble(row, col));
			playground.removeBubble(row, col);
		}
	}

	/**
	 * Returns the game-Mode
	 * @return the gameMode name
	 */
	public abstract String getMode();

	/**
	 * Unmark all Bubbles
	 */
	private void unMarkAll() {
		marked = 0;
		playground.unMarkAll();
		possiblePoints.setVisible(false);
	}

	@Override
	final public void mouseClicked(MouseEvent arg0) {
		pointsLabel.setText(Localization.getString("Points") + points);
		if (arg0.getSource() == playgroundPanel || playgroundPanel.isEnabled() == false) {
			unMarkAll();
			return;
		}
		Bubble my = (Bubble) (arg0.getSource());
		if (my == null) {
			return;
		}
		//System.out.println(my.getRow() +"x"+ my.getCol());
		if (marked != 0) {
			if (my.isMarked() == false) {
				unMarkAll();
			} else {
				if (JBubbleBreaker.getUserProperty("enableSound", "true").equalsIgnoreCase("true")) {
					new PlaySound(Sounds.REMOVE_BUBBLES);
				}
				if (firstMove) {
					gameLifecycleObserver.firstMoveTaken();
					firstMove = false;
				}
				makeBackup();
				points += getCalculatedPoints();
				removeMarkedBubbles(my.getRow(), my.getCol());
				pointsLabel.setText(Localization.getString("Points") + points);
				playgroundPanel.repaint();
				marked = 0;
				possiblePoints.setVisible(false);
				if (playground.isEmpty(playground.getRows() - 1, playground.getCols() - 1) == true) {
					int solvedPoints = solvedAction();
					if (solvedPoints >= 0) {
						pointsLabel.setText(Localization.getString("Points") + points);
						gameOver(solvedPoints);
						return;
					}
				}
				if (isPlaygroundSolvable() == false) {
					gameOver(0);
				}
			}
			return;
		}
		marked = 0;
		findsame(my.getRow(), my.getCol());
		possiblePoints.setText(getCalculatedPoints().toString());
		possiblePoints.setLocation((int) my.getLocation().getX() + 10, (int) my.getLocation().getY() - 12);
		possiblePoints.setVisible(true);
		if (marked == 1) {
			unMarkAll();
		}
	}

	/**
	 * Calculates the points
	 * @return calculated points
	 */
	protected abstract Integer getCalculatedPoints();

	/**
	 * Return the points of the user in the current game
	 * @return the points
	 */
	final public int getPoints() {
		return points;
	}

	public void makeBackup() {
		oldPoints = points;
		int row = playground.getRows() - 1;
		int col = playground.getCols() - 1;
		while (col >= 0) {
			while (row >= 0) {
				if (playground.isEmpty(row, col) == false) {
					playgroundCopy[row][col] = playground.getBubble(row, col).getColorIndex();
				} else {
					playgroundCopy[row][col] = null;
				}
				row--;
			}
			col--;
			row = playground.getRows() - 1;
		}
	}

	public void restoreBackup() {
		int row = playground.getRows() - 1;
		int col = playground.getCols() - 1;
		while (col >= 0) {
			while (row >= 0) {
				if (playgroundCopy[row][col] != null) {
					newBubble(row, col, playgroundCopy[row][col]);
				}
				row--;
			}
			col--;
			row = playground.getRows() - 1;
		}
		points = oldPoints;
	}

	public void redo() {
		playgroundPanel.setVisible(false);
		this.removeAllBubblesFromPlayground();
		restoreBackup();
		playgroundPanel.setVisible(true);
		marked = 0;
		firstMove = true;
		possiblePoints.setVisible(false);
		pointsLabel.setText(Localization.getString("Points") + points);
	}

	/**
	 * @param gameLifecycleObserver the gameLifecycleObserver to set
	 */
	public void setGameLifecycleObserver(GameLifecycleObserverIf gameLifecycleObserver) {
		this.gameLifecycleObserver = gameLifecycleObserver;
	}
}
