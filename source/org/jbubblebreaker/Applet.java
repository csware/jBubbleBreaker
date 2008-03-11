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
package org.jbubblebreaker;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JApplet;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

/**
 * @author Sven Strickroth
 */
@SuppressWarnings("serial")
public class Applet extends JApplet implements ActionListener, GUIIf {
	private JPanel infoPanel = new JPanel();
	private Game game;
	private JLabel pointsLabel = new JLabel();
	private JLabel gameModeLabel = new JLabel();

	private JMenuBar menuBar;
	private JMenuItem menuHelpInfo,menuGameNew,menuGameNewDots;
	
	@Override
	public void init() {
		JBubbleBreaker.registerDefault();
		try {
			SwingUtilities.invokeAndWait(new Runnable() {
				public void run() {
					createGUI();
				}
			});
		} catch (Exception e) {
			System.err.println("createGUI didn't successfully complete");
		}
	}

	/**
	 * Prepares the GUI in a separated thread
	 */
	private void createGUI() {
		// insert Menu
		menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		JMenu menuGame = new JMenu("Game");
		menuGame.setMnemonic('G');
		menuBar.add(menuGame);
		JMenu menuHelp = new JMenu("?");
		menuHelp.setMnemonic('?');
		menuBar.add(menuHelp);
		menuGameNew = new JMenuItem("New");
		menuGameNew.addActionListener(this);
		menuGameNew.setMnemonic('n');
		menuGame.add(menuGameNew);
		menuGameNewDots = new JMenuItem("New...");
		menuGameNewDots.addActionListener(this);
		menuGameNewDots.setMnemonic('e');
		menuGame.add(menuGameNewDots);
		menuHelpInfo = new JMenuItem("About");
		menuHelpInfo.addActionListener(this);
		menuHelpInfo.setMnemonic('a');
		menuHelp.add(menuHelpInfo);
		newGameDots();
	}

	/**
	 * Ask the user for game details for a new game
	 */
	private void newGameDots() {
		game=null;
		menuGameNew.setEnabled(false);
		menuGameNewDots.setEnabled(false);
		NewGameAskUserPanel nGAuP = new NewGameAskUserPanel(this);
		nGAuP.setVisible(false);
		setContentPane(nGAuP);
		nGAuP.setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		if (arg0.getSource() == menuHelpInfo) {
			new AboutBox(null);
		} else if (arg0.getSource() == menuGameNew) {
			game.newGame();
		} else if (arg0.getSource() == menuGameNewDots) {
			newGameDots();
		}
	}

	@Override
	public void startNewGame(Game game) {
		this.game = game;
		JPanel newContentPane = new JPanel();
		newContentPane.setVisible(false);
		setContentPane(newContentPane);
		newContentPane.setVisible(true);
		setLayout(new BorderLayout());
		getContentPane().add(infoPanel, BorderLayout.SOUTH);
		infoPanel.setSize(60, 60);
		if (game != null) {
			getContentPane().remove(game.getPanel());
		}
		gameModeLabel.setText(game.getMode());
		getContentPane().add(game.getPanel(), BorderLayout.CENTER);
		game.setPointsLabel(pointsLabel);

		infoPanel.setLayout(new BorderLayout());

		infoPanel.add(pointsLabel, BorderLayout.WEST);
		
		infoPanel.add(gameModeLabel, BorderLayout.EAST);
		pointsLabel.setText("Points: 0");

		menuGameNew.setEnabled(true);
		menuGameNewDots.setEnabled(true);
	}
}
