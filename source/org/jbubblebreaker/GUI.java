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

import java.awt.BorderLayout;
import java.awt.Toolkit;

import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 * @author Sven Strickroth
 */
@SuppressWarnings("serial")
public class GUI extends JFrame {

	private JPanel infoPanel = new JPanel();
	private Game playground;

	/**
	 * Launch the application
	 * @param args
	 */
	public static void main(String args[]) {
		try {
			GUI frame = new GUI();
			frame.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the frame
	 */
	private GUI() {
		super("JBubbleBreaker");
		setLayout(null);
		setSize(407,427);
		setResizable(false);
		setLocation((Toolkit.getDefaultToolkit().getScreenSize().width-getWidth())/2,(Toolkit.getDefaultToolkit().getScreenSize().height-getHeight())/2);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		getContentPane().add(infoPanel, BorderLayout.SOUTH);
		playground = new Game(12,12);
		getContentPane().add(playground.getPanel(), BorderLayout.CENTER);

		playground.getPanel().setSize(500,600);
	}
}
