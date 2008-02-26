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
package org.jbubblebreaker.bubbles;

import java.awt.Color;
import java.awt.Graphics;

/**
 * Provides the 3D Rect Bubbles
 * @author Sven Strickroth
 */
@SuppressWarnings("serial")
public class Bubble3DRect extends BubbleDefault {
	/**
	 * Stores the name of this bubble set
	 */
	static public String name = "3DRect"; 

	/**
	 * Create a Bubble on a specific position and size with a random color
	 * @param radian radian of Bubble
	 * @param row Row of Bubble
	 * @param col Col of Bubble
	 */
	public Bubble3DRect(int radian, int row, int col) {
		super(radian, row, col);
	}
	
	/**
	 * Create a Bubble on a specific position, size and color
	 * @param radian radian of Bubble
	 * @param row Row of Bubble
	 * @param col Col of Bubble
	 * @param colorIndex color index for new Bubble, if this colorIndex is not valid a random color is used
	 */
	public Bubble3DRect(int radian, int row, int col, int colorIndex) {
		super(radian, row, col, colorIndex);
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public void paint(Graphics g) {
		g.setColor(getColor(getColorIndex()));
		g.fill3DRect(0, 0, radian, radian, true);
		if (isMarked() == true) {
			g.setColor(Color.GRAY);
			g.fillOval(radian/4, radian/4, radian/2, radian/2);
		}
	}
}
