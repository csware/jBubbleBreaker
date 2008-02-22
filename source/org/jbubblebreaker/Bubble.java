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

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.MouseListener;

import javax.swing.JPanel;

/**
 * Provides the Bubbles
 * @author Sven Strickroth
 */
@SuppressWarnings("serial")
public class Bubble extends JPanel {
	/**
	 * stores the radian of the Bubble
	 */
	private int radian;
	/**
	 * stores the row of the Bubble-position
	 */
	private int row;
	/**
	 * stores the column of the Bubble-position
	 */
	private int col;
	/**
	 * is the Bubble marked
	 */
	private boolean marked = false;
	/**
	 * stores the color of this Bubble
	 */
	private int color;
	/**
	 * stores all Possible Colors
	 */
	final private Color[] colors = {Color.BLUE, Color.RED, Color.YELLOW, Color.GREEN, Color.MAGENTA}; 

	/**
	 * Create a Bubble on a specific position and size with a random color
	 * @param radian radian of Bubble
	 * @param row Row of Bubble
	 * @param col Col of Bubble
	 * @param ml MouseListener
	 */
	public Bubble(int radian, int row, int col, MouseListener ml) {
		this.radian=radian;
		this.setSize(radian, radian);
		this.row = row;
		this.col = col;
		this.setLocation(col*radian, row*radian);
		color = (int)(Math.random()*colors.length);
		this.addMouseListener(ml);
	}
	
	/**
	 * Resize and move optical position of a Bubble if the radian changed
	 * @param radian new radian of the Bubble
	 */
	public void resized(int radian) {
		if (this.radian != radian) {
			this.radian=radian;
			this.setSize(radian, radian);
			this.setLocation(col*radian, row*radian);
			repaint();
		}
	}

	/**
	 * Create a Bubble on a specific position, size and color
	 * @param radian radian of Bubble
	 * @param row Row of Bubble
	 * @param col Col of Bubble
	 * @param ml MouseListener
	 * @param colorIndex color index for new Bubble, if this colorIndex is not valid a random color is used
	 */
	public Bubble(int radian, int row, int col, MouseListener ml, int colorIndex) {
		this(radian,row,col,ml);
		if (colorIndex >= 0 && colorIndex < colors.length) {
			color = colorIndex;
		}
	}

	/**
	 * Returns the colour-index of this bubble
	 * @return color-index
	 */
	public int getColor() {
		return color;
	}
	
	/**
	 * Returns the row of the position of this bubble
	 * @return Row
	 */
	public int getRow() {
		return row;
	}
	
	/**
	 * Returns the column of the position of this bubble
	 * @return Col
	 */
	public int getCol() {
		return col;
	}

	/**
	 * Sets or toggles the mark of this Bubble
	 * @param what mark active
	 */
	public void setMark(boolean what) {
		marked = what;
		this.repaint();
	}
	
	/**
	 * Returns if the bubble is marked
	 * @return is bubble marked
	 */
	public boolean isMarked() {
		return marked;
	}
	
	/**
	 * changes the position of this bubble
	 * @param row row-index
	 * @param col column-index
	 */
	public void moveTo(int row, int col) {
		this.row = row;
		this.col = col;
		this.setLocation(col*radian, row*radian);
	}
	
	@Override
	public void paint(Graphics g) {
		super.paint(g);
		if (marked == true) {
			g.setColor(Color.GRAY);
			g.fillRect(0, 0, radian, radian);
		}
		g.setColor(colors[color]);
		g.fillOval(0, 0, radian, radian);

	}
}
