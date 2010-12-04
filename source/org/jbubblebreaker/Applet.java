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

import javax.swing.JApplet;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.KeyStroke;
import javax.swing.SwingUtilities;

/**
 * GUI for jBubbleBreaker (in applet mode)
 * @author Sven Strickroth
 */
@SuppressWarnings("serial")
public class Applet extends JApplet implements ActionListener, GUIIf, GameLifecycleObserverIf {
	private JPanel infoPanel = new JPanel();
	private Game game;
	private JLabel pointsLabel = new JLabel();
	private JLabel gameModeLabel = new JLabel();
	private JMenuItem menuHelpInfo, menuGameNew, menuGameNewDots, menuGameRedo, menuGameSounds;

	@Override
	public void init() {
		if (getParameter("enablesound") != null && getParameter("enablesound").equalsIgnoreCase("false")) {
			JBubbleBreaker.setUserProperty("enableSound", "false");
		}
		if (getParameter("lastGameMode") != null) {
			JBubbleBreaker.setUserProperty("lastGameMode", getParameter("lastGameMode"));
		}
		if (getParameter("lastBubbleType") != null) {
			JBubbleBreaker.setUserProperty("lastBubbleType", getParameter("lastBubbleType"));
		}
		if (getParameter("lastRows") != null) {
			JBubbleBreaker.setUserProperty("lastRows", getParameter("lastRows"));
		}
		if (getParameter("lastColumns") != null) {
			JBubbleBreaker.setUserProperty("lastColumns", getParameter("lastColumns"));
		}
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
		menuGame.add(menuGameNew);
		menuGameNewDots = new JMenuItem(Localization.getString("MenuNewDots"));
		menuGameNewDots.addActionListener(this);
		menuGameNewDots.setMnemonic(Localization.getChar("MenuNewDotsMnemonic"));
		menuGame.add(menuGameNewDots);
		menuGameRedo = new JMenuItem(Localization.getString("MenuRedo"));
		menuGameRedo.addActionListener(this);
		menuGameRedo.setMnemonic(Localization.getChar("MenuRedoMnemonic"));
		menuGameRedo.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F9, 0));
		menuGame.add(menuGameRedo);
		menuGameSounds = new JCheckBoxMenuItem(Localization.getString("MenuSounds"));
		menuGameSounds.addActionListener(this);
		menuGameSounds.setMnemonic(Localization.getChar("MenuSoundsMnemonic"));
		menuGameSounds.setSelected(JBubbleBreaker.getUserProperty("enableSound", "true").equalsIgnoreCase("true"));
		menuGame.add(menuGameSounds);
		menuHelpInfo = new JMenuItem(Localization.getString("MenuAbout"));
		menuHelpInfo.addActionListener(this);
		menuHelpInfo.setMnemonic(Localization.getChar("MenuAboutMnemonic"));
		menuHelp.add(menuHelpInfo);

		newGameDots();
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
	}

	public void actionPerformed(ActionEvent arg0) {
		if (arg0.getSource() == menuHelpInfo) {
			new AboutBox();
		} else if (arg0.getSource() == menuGameNew) {
			game.newGame();
			pointsLabel.setText(Localization.getString("PointsZero"));
		} else if (arg0.getSource() == menuGameNewDots) {
			newGameDots();
		} else if (arg0.getSource() == menuGameRedo) {
			pointsLabel.setText(Localization.getString("PointsZero"));
			menuGameRedo.setEnabled(false);
			game.redo();
		} else if (arg0.getSource() == menuGameSounds) {
			if (menuGameSounds.isSelected()) {
				JBubbleBreaker.setUserProperty("enableSound", "true");
			} else {
				JBubbleBreaker.setUserProperty("enableSound", "false");
			}
		}
	}

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
		game.setGameLifecycleObserver(this);

		infoPanel.setLayout(new BorderLayout());

		infoPanel.add(pointsLabel, BorderLayout.WEST);

		infoPanel.add(gameModeLabel, BorderLayout.EAST);
		pointsLabel.setText(Localization.getString("PointsZero"));

		menuGameNew.setEnabled(true);
		menuGameNewDots.setEnabled(true);
	}

	public void firstMoveTaken() {
		menuGameRedo.setEnabled(true);
	}

	public void gameOver() {
		menuGameRedo.setEnabled(false);
	}
}
