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
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.KeyEvent;

import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.KeyStroke;

import org.jbubblebreaker.gamemodes.GameOngoing;

/**
 * JBubbleBreaker GUI
 * @author Sven Strickroth
 */
@SuppressWarnings("serial")
public class GUI extends MyJFrame implements ActionListener {
	private JPanel infoPanel = new JPanel();
	private Game game;
	private JLabel pointsLabel = new JLabel();
	private JLabel gameModeLabel = new JLabel();
	
	private JMenuItem menuHelpInfo,menuFileNew,menuFileNewDots,menuFileStatistics,menuFileClose;
	
	/**
	 * Launch JBubbleBreaker
	 * @param args
	 */
	public static void main(String args[]) {
		new GUI();
	}

	/**
	 * Create the JFrame
	 */
	private GUI() {
		super("JBubbleBreaker",null,407,470,true,true);
		setDefaultCloseOperation(EXIT_ON_CLOSE);	
		addComponentListener(new ComponentAdapter() {
			public void componentResized(ComponentEvent arg0) {
				game.resized(size().width,size().height);
			}
		});
		setLayout(new BorderLayout());

		getContentPane().add(infoPanel, BorderLayout.SOUTH);
		infoPanel.setSize(60, 60);
		newOtherGame();

		infoPanel.setLayout(new BorderLayout());

		infoPanel.add(pointsLabel, BorderLayout.WEST);
		
		infoPanel.add(gameModeLabel, BorderLayout.EAST);
		pointsLabel.setText("Points: 0");
		
		// insert Menu
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		JMenu menuFile = new JMenu("File");
		menuBar.add(menuFile);
		JMenu menuHelp = new JMenu("?");
		menuBar.add(menuHelp);
		menuFileNew = new JMenuItem("New");
		menuFileNew.addActionListener(this);
		menuFileNew.setMnemonic('n');
		menuFileNew.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F2,0));
		menuFile.add(menuFileNew);
		menuFileNewDots = new JMenuItem("New...");
		menuFileNewDots.addActionListener(this);
		menuFileNewDots.setMnemonic('e');
		menuFileNewDots.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F3,0));
		menuFile.add(menuFileNewDots);
		menuFileStatistics = new JMenuItem("Statistics");
		menuFileStatistics.addActionListener(this);
		menuFileStatistics.setMnemonic('s');
		menuFile.add(menuFileStatistics);
		menuFileClose = new JMenuItem("Quit");
		menuFileClose.addActionListener(this);
		menuFileClose.setMnemonic('q');
		menuFileClose.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F4,ActionEvent.ALT_MASK));
		menuFile.add(menuFileClose);
		menuHelpInfo = new JMenuItem("About");
		menuHelpInfo.addActionListener(this);
		menuHelpInfo.setMnemonic('a');
		menuHelp.add(menuHelpInfo);
		setVisible(true);
	}
	
	/**
	 * starts a new game and ask the user for details
	 */
	public void newOtherGame() {
		if (game != null) {
			getContentPane().remove(game.getPanel());
		}
		
		game = new GameOngoing(getSize().width, getSize().height, 12, 12, pointsLabel);
		getContentPane().add(game.getPanel(), BorderLayout.CENTER);
		gameModeLabel.setText(game.getMode());
	}

	public void actionPerformed(ActionEvent arg0) {
		if (arg0.getSource() == menuFileClose) {
			dispose();
		} else if (arg0.getSource() == menuHelpInfo) {
			new AboutBox(this);
		} else if (arg0.getSource() == menuFileNew) {
			game.newGame();
		} else if (arg0.getSource() == menuFileNewDots) {
			newOtherGame();
		}
	}
}
