/*
 * Copyright 2008 - 2010 Sven Strickroth <email@cs-ware.de>
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

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.IOException;

import javax.swing.JCheckBoxMenuItem;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.KeyStroke;

/**
 * jBubbleBreaker GUI (in application mode)
 * @author Sven Strickroth
 */
@SuppressWarnings("serial")
public class GUI extends MyJFrame implements ActionListener, GUIIf, GameLifecycleObserverIf {
	private JPanel infoPanel = new JPanel();
	private Game game;
	private JLabel pointsLabel = new JLabel();
	private JLabel gameModeLabel = new JLabel();
	private static boolean started = false;

	private JMenuItem menuHelpUpdate, menuHelpInfo, menuGameNew, menuGameNewDots, menuGameRedo, menuGameStatistics, menuGameGuestMode, menuGameClose, menuGameSounds;

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
	 * Create the JFrame and prepare everything for the game
	 */
	private GUI() {
		super("jBubbleBreaker", "jbubblebreaker.png", 407, 470, true, true);
		setDefaultCloseOperation(EXIT_ON_CLOSE);

		// insert Menu
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		JMenu menuGame = new JMenu();
		Localization.setMemnoricText(menuGame, Localization.getI18n().tr("&Game"));
		menuBar.add(menuGame);
		JMenu menuHelp = new JMenu();
		Localization.setMemnoricText(menuHelp, Localization.getI18n().tr("&Help"));
		menuBar.add(menuHelp);
		menuGameNew = new JMenuItem();
		Localization.setMemnoricText(menuGameNew, Localization.getI18n().tr("&New"));
		menuGameNew.addActionListener(this);
		menuGameNew.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F2, 0));
		menuGame.add(menuGameNew);
		menuGameNewDots = new JMenuItem();
		Localization.setMemnoricText(menuGameNewDots, Localization.getI18n().tr("N&ew..."));
		menuGameNewDots.addActionListener(this);
		menuGameNewDots.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F3, 0));
		menuGame.add(menuGameNewDots);
		menuGameRedo = new JMenuItem();
		Localization.setMemnoricText(menuGameRedo, Localization.getI18n().tr("&Redo"));
		menuGameRedo.addActionListener(this);
		menuGameRedo.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F9, 0));
		menuGame.add(menuGameRedo);
		menuGameStatistics = new JMenuItem();
		Localization.setMemnoricText(menuGameStatistics, Localization.getI18n().tr("&Statistics"));
		menuGameStatistics.addActionListener(this);
		menuGameStatistics.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F4, 0));
		menuGame.add(menuGameStatistics);
		menuGameGuestMode = new JCheckBoxMenuItem();
		Localization.setMemnoricText(menuGameGuestMode, Localization.getI18n().tr("&Guest mode"));
		menuGameGuestMode.addActionListener(this);
		menuGameGuestMode.setSelected(JBubbleBreaker.getUserProperty("enableGuestMode", "false").equalsIgnoreCase("true"));
		menuGame.add(menuGameGuestMode);
		menuGameSounds = new JCheckBoxMenuItem();
		Localization.setMemnoricText(menuGameSounds, Localization.getI18n().tr("S&ound"));
		menuGameSounds.addActionListener(this);
		menuGameSounds.setSelected(JBubbleBreaker.getUserProperty("enableSound", "true").equalsIgnoreCase("true"));
		menuGame.add(menuGameSounds);
		menuGameClose = new JMenuItem();
		Localization.setMemnoricText(menuGameClose, Localization.getI18n().tr("&Quit"));
		menuGameClose.addActionListener(this);
		menuGameClose.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F4, ActionEvent.ALT_MASK));
		menuGame.add(menuGameClose);
		menuHelpUpdate = new JMenuItem();
		Localization.setMemnoricText(menuHelpUpdate, Localization.getI18n().tr("Check for &update"));
		menuHelpUpdate.addActionListener(this);
		menuHelp.add(menuHelpUpdate);
		menuHelpInfo = new JMenuItem();
		Localization.setMemnoricText(menuHelpInfo, Localization.getI18n().tr("&About"));
		menuHelpInfo.addActionListener(this);
		menuHelp.add(menuHelpInfo);

		newGameDots();

		setVisible(true);
	}

	/**
	 * Ask the user for game details for a new game
	 */
	private void newGameDots() {
		game = null;
		menuGameNew.setEnabled(false);
		menuGameNewDots.setEnabled(false);
		menuGameRedo.setEnabled(false);
		NewGameAskUserPanel nGAuP = new NewGameAskUserPanel(this);
		nGAuP.setVisible(false);
		setContentPane(nGAuP);
		nGAuP.setVisible(true);
		repaint();
	}

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
		pointsLabel.setText(Localization.getI18n().tr("Points: {0}", 0));

		gameModeLabel.setText(game.getMode());
		this.getContentPane().add(game.getPanel(), BorderLayout.CENTER);
		game.setPointsLabel(pointsLabel);
		game.setGameLifecycleObserver(this);
		menuGameNew.setEnabled(true);
		menuGameNewDots.setEnabled(true);
	}

	public void actionPerformed(ActionEvent arg0) {
		if (arg0.getSource() == menuGameClose) {
			dispose();
		} else if (arg0.getSource() == menuHelpInfo) {
			new AboutBox();
		} else if (arg0.getSource() == menuHelpUpdate) {
			try {
				if (JBubbleBreaker.checkForUpdate() == true) {
					JOptionPane.showMessageDialog(null, Localization.getI18n().tr("A new version is available.\nCheck out {0}", JBubbleBreaker.getProjectHomepage()), "jBubbleBreaker", JOptionPane.INFORMATION_MESSAGE);
				} else {
					JOptionPane.showMessageDialog(null, Localization.getI18n().tr("No new version is available.\nYou're already running the most current version."), "jBubbleBreaker", JOptionPane.INFORMATION_MESSAGE);
				}
			} catch (IOException e) {
				JOptionPane.showMessageDialog(null, Localization.getI18n().tr("Could not check for version information on the internet."), "jBubbleBreaker", JOptionPane.ERROR_MESSAGE);
			}
		} else if (arg0.getSource() == menuGameNew) {
			game.newGame();
		} else if (arg0.getSource() == menuGameStatistics) {
			new Statistics();
		} else if (arg0.getSource() == menuGameNewDots) {
			newGameDots();
		} else if (arg0.getSource() == menuGameRedo) {
			pointsLabel.setText(Localization.getI18n().tr("Points: {0}", 0));
			menuGameRedo.setEnabled(false);
			game.redo();
		} else if (arg0.getSource() == menuGameGuestMode) {
			if (menuGameGuestMode.isSelected()) {
				JBubbleBreaker.setUserProperty("enableGuestMode", "true");
			} else {
				JBubbleBreaker.setUserProperty("enableGuestMode", "false");
			}
		} else if (arg0.getSource() == menuGameSounds) {
			if (menuGameSounds.isSelected()) {
				JBubbleBreaker.setUserProperty("enableSound", "true");
			} else {
				JBubbleBreaker.setUserProperty("enableSound", "false");
			}
		}
	}

	public void firstMoveTaken() {
		menuGameRedo.setEnabled(true);
	}

	public void gameOver() {
		menuGameRedo.setEnabled(false);
	}
}
