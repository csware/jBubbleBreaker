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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with JBubbleBreaker. If not, see <http://www.gnu.org/licenses/>.
 */
package org.jbubblebreaker;

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
	final private static String icon = "jbubblebreaker.png";

	/**
	 * Create and show the AboutBox
	 * @param parentJFrame parent Frame for modal emulation 
	 */
	public AboutBox(JFrame parentJFrame) {
		super("About jBubbleBreaker",icon, 332, 210, false, parentJFrame);

		// we're in an applet
		/* this is only needed if applet is not digitally signed
		if (parentJFrame == null) {
			setSize(332,227);
		}*/
		
		setLayout(null);

		final PicturePanel titlePanel = new PicturePanel("jBubbleBreaker-text.png",219,43);
		titlePanel.setLocation(64,10);
		titlePanel.setToolTipText("jBubbleBreaker");
		getContentPane().add(titlePanel);
		
		PicturePanel panel = new PicturePanel(icon,32,32);
		panel.setBounds(10, 10, 32, 32);
		getContentPane().add(panel);

		JLabel lblVersion = new JLabel("Version "+JBubbleBreaker.getVersion());
		lblVersion.setBounds(64, 56, 252, 14);
		getContentPane().add(lblVersion);

		JButton okButton = new JButton("OK");
		okButton.setBounds(221, 152, 95, 23);
		okButton.addActionListener(this);
		getContentPane().add(okButton);

		JLabel lblCopyright = new JLabel("Coded by Sven Strickroth, 2007 - 2008");
		lblCopyright.setVerticalAlignment(SwingConstants.TOP);
		lblCopyright.setBounds(64, 76, 250, 25);
		getContentPane().add(lblCopyright);

		JLabel lblComment = new JLabel("<html>BubbleBreaker Java clone,<br>Hosted on SourceForge.net:<br>"+JBubbleBreaker.getProjectHomepage()+"</html>");
		lblComment.setVerticalAlignment(SwingConstants.TOP);
		lblComment.setBounds(64, 99, 250, 50);
		getContentPane().add(lblComment);
	
		setVisible(true);
	}
	
	@Override
	public void actionPerformed(ActionEvent arg0) {
		dispose();
	}
}
