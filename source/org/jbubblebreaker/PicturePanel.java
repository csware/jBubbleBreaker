/*
 * Copyright 2007-2008 Sven Strickroth <email@cs-ware.de>
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

import javax.swing.JPanel;
import java.awt.Graphics;
import java.awt.Toolkit;
import java.net.URL;

/**
 * Provides a JPanel with a picture with "autoredraw" enabled in it
 * @author Sven Strickroth
 */
@SuppressWarnings("serial")
public class PicturePanel extends JPanel {
	/**
	 * stores the filename of the picture of the PicturePanel
	 */
	private URL filename; 
	
	/**
	 * Creates a PicturePanel w/o any location parameters, 0x0 will be used
	 * @param filename path and filename of the picture
	 * @param width width of the picture
	 * @param height height of the picture
	 */
	public PicturePanel(String filename, int width, int height) {
		this(filename,0,0,width,height);
	}
	
	/**
	 * Creates a PicturePanel on a specific location.
	 * @param filename path and filename of the picture
	 * @param x x position of the PicturePanel
	 * @param y y position of the PicturePanel
	 * @param width width of the picture
	 * @param height height of the picture
	 */
	public PicturePanel(String filename, int x, int y, int width, int height) {
		this.filename = getClass().getResource("/images/"+filename);
		this.setBounds(x,y,width,height);
	}
	
	@Override
	public void paint(Graphics g) {
		super.paint(g);
		g.drawImage(Toolkit.getDefaultToolkit().getImage(filename), 0, 0, this);
	}
}
