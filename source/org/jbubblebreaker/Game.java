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

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

/**
 * Provides the JBubbleBreaker logic
 * @author Sven Strickroth
 */
public class Game extends MouseAdapter {
	private int marked = 0;
	private JPanel playgroundPanel = new JPanel();
	private JLabel possiblePoints = new JLabel("2000");
	private Playground playground;
	JLabel pointsLabel;
	private int points = 0;
	
	public Game(int rows, int cols, JLabel pointsLabel) {
		playground = new Playground(rows, cols, this);

		this.pointsLabel = pointsLabel;
		possiblePoints.setSize(50,50);
		playgroundPanel.add(possiblePoints);
		possiblePoints.setVisible(false);
		
		for(int i=0; i < rows; i++) {
			for(int j=0; j < cols; j++) {
				playgroundPanel.add(playground.getBubble(i, j));
			}
		}
		playgroundPanel.addMouseListener(this);
		playgroundPanel.setLayout(null);
		playgroundPanel.repaint();
	}

	/**
	 * Returns the JPanel with the Bubbles inside
	 * @return playground-JPanel
	 */
	public JPanel getPanel() {
		return playgroundPanel;
	}

	/**
	 * Find reculsively all Bubbles with the same color-index which are next to each other 
	 * @param row row-index to start the search
	 * @param col column-index to start the search
	 */
	private void findsame(int row, int col) {
		Bubble circle = playground.getBubble(row, col);
		if (circle == null) { return; }
		marked++;
		circle.setMark(true);
		int color = circle.getColor();

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

	/**
	 * Checks if the current game is solveable
	 * @return playground solveable?
	 */
	private boolean isSolveable() {
		int row = playground.getRows() - 1;
		int col = playground.getCols() - 1;
		while(col > 0 && playground.isEmpty(row,col) == false) {
			while(row > 0 && playground.isEmpty(row, col) == false) {
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

	/**
	 * Remove all marked Bubbles, starting on row, col
	 * @param row row-index
	 * @param col column-index
	 */
	private void removeBubbles(int row, int col) {
		// first of all delete bubbles in col
		for(int k=0; k < playground.getCols(); k++) {
			for(int i=0; i < playground.getRows(); i++) {
				//System.out.println(i);
				if (playground.isMarked(i,k) == true) {
					//System.out.println("wech:"+i);
					playgroundPanel.remove(playground.getBubble(i,k));
					for(int j=i; j > 0; j--) {
						playground.moveTo(j-1, k, j, k);
						//System.out.println("wandern "+(j-1)+" nach "+j);
					}
				}
			}
		}
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
		
		marked=0;
		possiblePoints.setVisible(false);
		pointsLabel.setText("Points: "+ points);
		playgroundPanel.repaint();
		if (isSolveable() == false) {
			JOptionPane.showMessageDialog(null, "Spielende. Punkte: "+points, "JBubbleBreaker", JOptionPane.INFORMATION_MESSAGE);
		}
	}

	/**
	 * Unmarks all Bubbles
	 */
	private void unmarkAll() {
		marked=0;
		for(int i=0; i < playground.getRows(); i++) {
			for(int j=0; j < playground.getCols(); j++) {
				playground.unMark(i, j);
			}
		}
		possiblePoints.setVisible(false);
	}

	@Override
	public void mouseClicked(MouseEvent arg0) {
		pointsLabel.setText("Points: "+ points);
		if (arg0.getSource() == playgroundPanel) {
			unmarkAll();
			return;
		}
		Bubble my = (Bubble)(arg0.getSource());
		if (my == null) { return; }
		//System.out.println(my.getRow() +"x"+ my.getCol());
		if (marked != 0) {
			if (my.isMarked() == false) {
				unmarkAll();
			} else {
				points += (marked*(marked-1));
				removeBubbles(my.getRow(),my.getCol());
			}
			return;
		}
		marked=0;
		findsame(my.getRow(),my.getCol());
		possiblePoints.setText(""+(marked*(marked-1)));
		possiblePoints.setLocation((int)my.getLocation().getX()+10,(int)my.getLocation().getY()-12);
		possiblePoints.setVisible(true);
		if (marked==1) {
			unmarkAll();
		}
	}
}
