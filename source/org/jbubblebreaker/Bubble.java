package org.jbubblebreaker;
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

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.MouseListener;

import javax.swing.JPanel;

/**
 * @author Sven Strickroth
 */
@SuppressWarnings("serial")
public class Bubble extends JPanel {
	private int height;
	private int width;
	private int row;
	private int col;
	private boolean marked = false;
	private int color;
	private Color[] colors = {Color.BLUE, Color.RED, Color.YELLOW, Color.GREEN, Color.MAGENTA}; 

	public Bubble(int width, int height, int row, int col, MouseListener ml) {
		this.width=width;
		this.height=height;
		this.setSize(width, height);
		this.row = row;
		this.col = col;
		this.setLocation(col*width, row*height);
		color = (int)(Math.random()*colors.length);
		this.addMouseListener(ml);
	}

	public int getColor() {
		return color;
	}
	
	public int getRow() {
		return row;
	}
	
	public int getCol() {
		return col;
	}
	
	public void setMark(boolean what) {
		marked = what;
		this.repaint();
	}
	
	public boolean isMarked() {
		return marked;
	}
	
	public void moveTo(int row, int col) {
		this.row = row;
		this.col = col;
		this.setLocation(col*width, row*height);
	}
	
	public void paint(Graphics g) {
		super.paint(g);
		if (marked == true) {
			g.setColor(Color.GRAY);
			g.fillRect(0, 0, width, height);
		}
		g.setColor(colors[color]);
		g.fillOval(0, 0, width, height);

	}
}
