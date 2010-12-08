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
	static private Image[] image = { null, null, null, null, null };
	/**
	 * store the last radius
	 */
	static private int oldradius = 0;

	/**
	 * Create a Bubble on a specific position and size with a random color
	 * @param radius radius of Bubble
	 * @param row Row of Bubble
	 * @param col Column of Bubble
	 */
	public Bubble3DCircle(int radius, int row, int col) {
		super(radius, row, col);
	}

	/**
	 * Create a Bubble on a specific position, size and color
	 * @param radius radius of Bubble
	 * @param row Row of Bubble
	 * @param col Column of Bubble
	 * @param colorIndex color index for new Bubble, if this colorIndex is not valid a random color is used
	 */
	public Bubble3DCircle(int radius, int row, int col, int colorIndex) {
		super(radius, row, col, colorIndex);
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public void paint(Graphics g) {
		if (isMarked() == true) {
			g.setColor(Color.GRAY);
			g.fillRect(0, 0, radius, radius);
		}
		if (image[0] == null || radius != oldradius) {
			String[] filenames = { "bubble-red", "bubble-blue", "bubble-yellow", "bubble-magenta", "bubble-green" };
			for (int i = 0; i < 5; i++) {
				try {
					image[i] = ImageIO.read(getClass().getResource("/images/" + filenames[i] + ".png")).getScaledInstance(radius, radius, Image.SCALE_SMOOTH);
				} catch (IOException e) {
					// should not occour
				}
			}
			oldradius = radius;
		}
		g.drawImage(image[getColorIndex()], 0, 0, this);
	}
}
