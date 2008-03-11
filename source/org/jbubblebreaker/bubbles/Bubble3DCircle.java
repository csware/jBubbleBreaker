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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with JBubbleBreaker. If not, see <http://www.gnu.org/licenses/>.
 */
package org.jbubblebreaker.bubbles;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.io.IOException;
import javax.imageio.ImageIO;

/**
 * Provides 3D Circle Bubbles
 * @author Sven Strickroth
 */
@SuppressWarnings("serial")
public class Bubble3DCircle extends BubbleDefault {
	/**
	 * Stores the name of this bubble set
	 */
	static public String name = "3DCircle"; 
	/**
	 * Buffer the image 
	 */
	private Image image = null;
	/**
	 * store the last radian
	 */
	private int oldradian = 0;
	
	/**
	 * Create a Bubble on a specific position and size with a random color
	 * @param radian radian of Bubble
	 * @param row Row of Bubble
	 * @param col Col of Bubble
	 */
	public Bubble3DCircle(int radian, int row, int col) {
		super(radian, row, col);
	}
	
	/**
	 * Create a Bubble on a specific position, size and color
	 * @param radian radian of Bubble
	 * @param row Row of Bubble
	 * @param col Col of Bubble
	 * @param colorIndex color index for new Bubble, if this colorIndex is not valid a random color is used
	 */
	public Bubble3DCircle(int radian, int row, int col, int colorIndex) {
		super(radian, row, col, colorIndex);
	}
	
	@Override
	public String getName() {
		return name;
	}

	@Override
	public void paint(Graphics g) {
		if (isMarked() == true) {
			g.setColor(Color.GRAY);
			g.fillRect(0, 0, radian, radian);
		}
		if (image == null || radian != oldradian) {
			String filename = "";
			switch (getColorIndex()) {
				case 0: filename = "bubble-red"; break;
				case 1: filename = "bubble-blue"; break;
				case 2: filename = "bubble-yellow"; break;
				case 3: filename = "bubble-magenta"; break;
				case 4: filename = "bubble-green"; break;
			}
			try {
				image = ImageIO.read(getClass().getResource("/images/"+filename+".png")).getScaledInstance(radian, radian, Image.SCALE_SMOOTH);
			} catch (IOException e) {
				// should not occour
			}
			oldradian = radian;
		}
		g.drawImage(image, 0, 0, this);
	}
}
