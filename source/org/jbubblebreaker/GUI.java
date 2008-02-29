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
import java.awt.event.KeyEvent;

import javax.swing.JCheckBoxMenuItem;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.KeyStroke;

/**
 * JBubbleBreaker GUI
 * @author Sven Strickroth
 */
@SuppressWarnings("serial")
public class GUI extends MyJFrame implements ActionListener, AbstractGUI {
	private JPanel infoPanel = new JPanel();
	private Game game;
	private JLabel pointsLabel = new JLabel();
	private JLabel gameModeLabel = new JLabel();
	private static boolean started = false;
	
	private JMenuItem menuHelpInfo,menuFileNew,menuFileNewDots,menuFileStatistics,menuFileGuestMode,menuFileClose;

	/**
	 * Start GUI, but only once 
	 */
	public static void startGUI() {
		if (started == false) {
			new GUI();
			started = true;
		}
	}

	/**
	 * Create the JFrame
	 */
	private GUI() {
		super("JBubbleBreaker","jbubblebreaker.png",407,470,true,true);
		setDefaultCloseOperation(EXIT_ON_CLOSE);	

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
		menuFileGuestMode = new JCheckBoxMenuItem("Guest Mode");
		menuFileGuestMode.addActionListener(this);
		menuFileGuestMode.setMnemonic('g');
		menuFile.add(menuFileGuestMode);
		menuFileClose = new JMenuItem("Quit");
		menuFileClose.addActionListener(this);
		menuFileClose.setMnemonic('q');
		menuFileClose.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F4,ActionEvent.ALT_MASK));
		menuFile.add(menuFileClose);
		menuHelpInfo = new JMenuItem("About");
		menuHelpInfo.addActionListener(this);
		menuHelpInfo.setMnemonic('a');
		menuHelp.add(menuHelpInfo);

		newGameDots();

		setVisible(true);
	}

	private void newGameDots() {
		game = null;
		NewGameAskUserPanel nGAuP = new NewGameAskUserPanel(this);
		nGAuP.setVisible(false);
		setContentPane(nGAuP);
		nGAuP.setVisible(true);
	}
	
	/* (non-Javadoc)
	 * @see org.jbubblebreaker.AbstractGUI#startNewGame(org.jbubblebreaker.Game)
	 */
	public void startNewGame(Game game) {
		this.game = game;

		JPanel newContentPane = new JPanel();
		newContentPane.setVisible(false);
		this.setContentPane(newContentPane);
		newContentPane.setVisible(true);
		
		setLayout(new BorderLayout());

		getContentPane().add(infoPanel, BorderLayout.SOUTH);
		infoPanel.setSize(60, 60);

		infoPanel.setLayout(new BorderLayout());

		infoPanel.add(pointsLabel, BorderLayout.WEST);
		
		infoPanel.add(gameModeLabel, BorderLayout.EAST);
		pointsLabel.setText("Points: 0");

		
		gameModeLabel.setText(game.getMode());
		this.getContentPane().add(game.getPanel(), BorderLayout.CENTER);
		game.setPointsLabel(pointsLabel);
	}

	public void actionPerformed(ActionEvent arg0) {
		if (arg0.getSource() == menuFileClose) {
			dispose();
		} else if (arg0.getSource() == menuHelpInfo) {
			new AboutBox(this);
		} else if (arg0.getSource() == menuFileNew) {
			game.newGame();
		} else if (arg0.getSource() == menuFileStatistics) {
			new Statistics(this);
		} else if (arg0.getSource() == menuFileNewDots) {
			newGameDots();
		} else if (arg0.getSource() == menuFileGuestMode) {
			Statistics.setGuestMode(!Statistics.isGuestMode());
			menuFileGuestMode.setSelected(Statistics.isGuestMode());
		}
	}
}
