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

import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.JFrame;

/**
 * Emulates a modal JFrame by disabling the "owner" JFrame as long as the modal JFrame is active.  
 * @author Sven Strickroth
 */
@SuppressWarnings("serial")
public class MyModalJFrame extends MyJFrame implements WindowListener {
	/**
	 * Stores the reference to the owner jFrame
	 */	
	protected JFrame parentJFrame = null;

	/**
	 * Creates a modal JFrame
	 * @param parentJFrame reference of the owner JFrame
	 */
	public MyModalJFrame(JFrame parentJFrame) {
		makeModal(parentJFrame);
	}

	/**
	 * Creates a JFrame with certain parameters
	 * @param title the window-title
	 * @param iconFileName the icon filename, null for no icon
	 * @param width width of the JFrame
	 * @param height height of the JFrame
	 * @param resizeAble create resizebale JFrame?
	 * @param parentJFrame reference of the owner JFrame
	 */
	public MyModalJFrame(String title, String iconFileName, int width, int height, boolean resizeAble,JFrame parentJFrame) {
		super(title, iconFileName, width, height, resizeAble, true);
		makeModal(parentJFrame);
	}

	@Override
	public void dispose() {
		windowClosing(null);
		super.dispose();
	}

	/**
	 * Makes the JFrame modal by disabling the owner JFrame 
	 * @param parentJFrame reference of the owner JFrame
	 */
	private void makeModal(JFrame parentJFrame) {
		if (parentJFrame==null) {
			return;
		}
		addWindowListener(this);
		this.parentJFrame = parentJFrame; 
		parentJFrame.setEnabled(false);
	}

	public void windowActivated(WindowEvent arg0) { }
	public void windowClosed(WindowEvent arg0) { }
	public void windowClosing(WindowEvent arg0) {
		if (parentJFrame != null) {
			parentJFrame.setEnabled(true);
		}
	}
	public void windowDeactivated(WindowEvent arg0) { }
	public void windowDeiconified(WindowEvent arg0) { }
	public void windowIconified(WindowEvent arg0) { }
	public void windowOpened(WindowEvent arg0) { }
}
