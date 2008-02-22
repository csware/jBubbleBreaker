/*
 * Sven Strickroth
 * Matrikelnummer: 352752
 * eMail: sven.strickroth@tu-clausthal.de
 */
package org.jbubblebreaker.gamemodes;

import org.jbubblebreaker.Game;

import javax.swing.JLabel;

/**
 * Default Rules
 * @author Sven Strickroth
 */
public class GameDefault extends Game {

	/**
	 * Creates a game with default rules
	 * @param rows of the matrix
	 * @param cols of the matrix
	 * @param pointsLabel reference to the points Label in the GUI
	 */
	public GameDefault(int rows, int cols, JLabel pointsLabel) {
		super(rows, cols, pointsLabel);
	}

	@Override
	public String getMode() {
		return "Default";
	};
	
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
