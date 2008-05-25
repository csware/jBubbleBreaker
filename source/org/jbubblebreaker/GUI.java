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
 * jBubbleBreaker GUI (in application mode)
 * @author Sven Strickroth
 */
@SuppressWarnings("serial")
public class GUI extends MyJFrame implements ActionListener, GUIIf {
	private JPanel infoPanel = new JPanel();
	private Game game;
	private JLabel pointsLabel = new JLabel();
	private JLabel gameModeLabel = new JLabel();
	private static boolean started = false;

	private JMenuItem menuHelpInfo,menuGameNew,menuGameNewDots,menuGameStatistics,menuGameGuestMode,menuGameClose,menuGameSounds;

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
		super("jBubbleBreaker","jbubblebreaker.png",407,470,true,true);
		setDefaultCloseOperation(EXIT_ON_CLOSE);

		// insert Menu
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		JMenu menuGame = new JMenu(Localization.getString("MenuGame"));
		menuGame.setMnemonic(Localization.getChar("MenuGameMnemonic"));
		menuBar.add(menuGame);
		JMenu menuHelp = new JMenu(Localization.getString("MenuHelp"));
		menuHelp.setMnemonic(Localization.getChar("MenuHelpMnemonic"));
		menuBar.add(menuHelp);
		menuGameNew = new JMenuItem(Localization.getString("MenuNew"));
		menuGameNew.addActionListener(this);
		menuGameNew.setMnemonic(Localization.getChar("MenuNewMnemonic"));
		menuGameNew.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F2,0));
		menuGame.add(menuGameNew);
		menuGameNewDots = new JMenuItem(Localization.getString("MenuNewDots"));
		menuGameNewDots.addActionListener(this);
		menuGameNewDots.setMnemonic(Localization.getChar("MenuNewDotsMnemonic"));
		menuGameNewDots.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F3,0));
		menuGame.add(menuGameNewDots);
		menuGameStatistics = new JMenuItem(Localization.getString("MenuStatistics"));
		menuGameStatistics.addActionListener(this);
		menuGameStatistics.setMnemonic(Localization.getChar("MenuGameStatisticsMnemonic"));
		menuGameStatistics.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F4,0));
		menuGame.add(menuGameStatistics);
		menuGameGuestMode = new JCheckBoxMenuItem(Localization.getString("MenuGuestMode"));
		menuGameGuestMode.addActionListener(this);
		menuGameGuestMode.setMnemonic(Localization.getChar("MenuGuestModeMnemonic"));
		menuGameGuestMode.setSelected(JBubbleBreaker.getUserProperty("enableGuestMode","false").equalsIgnoreCase("true"));
		menuGame.add(menuGameGuestMode);
		menuGameSounds = new JCheckBoxMenuItem(Localization.getString("MenuSounds"));
		menuGameSounds.addActionListener(this);
		menuGameSounds.setMnemonic(Localization.getChar("MenuSoundsMnemonic"));
		menuGameSounds.setSelected(JBubbleBreaker.getUserProperty("enableSound","true").equalsIgnoreCase("true"));
		menuGame.add(menuGameSounds);
		menuGameClose = new JMenuItem(Localization.getString("MenuQuit"));
		menuGameClose.addActionListener(this);
		menuGameClose.setMnemonic(Localization.getChar("MenuQuitMnemonic"));
		menuGameClose.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F4,ActionEvent.ALT_MASK));
		menuGame.add(menuGameClose);
		menuHelpInfo = new JMenuItem(Localization.getString("MenuAbout"));
		menuHelpInfo.addActionListener(this);
		menuHelpInfo.setMnemonic(Localization.getChar("MenuAboutMnemonic"));
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
		pointsLabel.setText(Localization.getString("PointsZero"));

		gameModeLabel.setText(game.getMode());
		this.getContentPane().add(game.getPanel(), BorderLayout.CENTER);
		game.setPointsLabel(pointsLabel);
		menuGameNew.setEnabled(true);
		menuGameNewDots.setEnabled(true);
	}

	public void actionPerformed(ActionEvent arg0) {
		if (arg0.getSource() == menuGameClose) {
			dispose();
		} else if (arg0.getSource() == menuHelpInfo) {
			new AboutBox();
		} else if (arg0.getSource() == menuGameNew) {
			game.newGame();
			pointsLabel.setText(Localization.getString("PointsZero"));
		} else if (arg0.getSource() == menuGameStatistics) {
			new Statistics();
		} else if (arg0.getSource() == menuGameNewDots) {
			newGameDots();
		} else if (arg0.getSource() == menuGameGuestMode) {
			if (menuGameGuestMode.isSelected()) {
				JBubbleBreaker.setUserProperty("enableGuestMode","true");
			} else {
				JBubbleBreaker.setUserProperty("enableGuestMode","false");
			}
		} else if (arg0.getSource() == menuGameSounds) {
			if (menuGameSounds.isSelected()) {
				JBubbleBreaker.setUserProperty("enableSound","true");
			} else {
				JBubbleBreaker.setUserProperty("enableSound","false");
			}
		}
	}
}
