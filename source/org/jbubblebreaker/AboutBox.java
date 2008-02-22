/*
 * Copyright 2007-2008 Sven Strickroth <email@cs-ware.de>
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

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

/**
 * AboutBox
 * @author Sven Strickroth
 */
@SuppressWarnings("serial")
public class AboutBox extends MyModalJFrame implements ActionListener {
	final private static String icon = "mainicon.png";
	
	public AboutBox(JFrame parentJFrame) {
		super("About JBubbleBreaker",null, 332, 175, false, parentJFrame);

		setLayout(null);
		
		PicturePanel panel = new PicturePanel(icon,32,32);
		panel.setBounds(10, 10, 32, 32);
		getContentPane().add(panel);

		JLabel lblTitle = new JLabel("JBubbleBreaker");
		lblTitle.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 16));
		lblTitle.setBounds(64, 10, 250, 25);
		getContentPane().add(lblTitle);

		JLabel lblVersion = new JLabel("Version "+getVersion());
		lblVersion.setBounds(64, 41, 252, 14);
		getContentPane().add(lblVersion);

		JButton okButton = new JButton("OK");
		okButton.setBounds(221, 117, 95, 23);
		okButton.addActionListener(this);
		getContentPane().add(okButton);

		JLabel lblCopyright = new JLabel("Coded by Sven Strickroth, 2008");
		lblCopyright.setVerticalAlignment(SwingConstants.TOP);
		lblCopyright.setBounds(64, 61, 250, 25);
		getContentPane().add(lblCopyright);

		JLabel lblComment = new JLabel("BubbleBreaker Java clone");
		lblComment.setVerticalAlignment(SwingConstants.TOP);
		lblComment.setBounds(64, 84, 250, 27);
		getContentPane().add(lblComment);
	
		setVisible(true);
	}
	
	static public String getVersion() {
		return "1.0";
	}
	
	public void actionPerformed(ActionEvent arg0) {
		dispose();
	}
}
