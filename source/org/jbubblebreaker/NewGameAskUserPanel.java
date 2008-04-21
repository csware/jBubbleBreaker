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

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Iterator;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

/**
 * Provides the panel where the user can choose the game details
 * (game mode and bubble style, matrix size)
 * @author Sven Strickroth
 */
@SuppressWarnings("serial")
public class NewGameAskUserPanel extends JPanel implements ActionListener, ChangeListener {
	private JSlider columnsSlider;
	private JSlider rowsSlider;
	private JComboBox gameTypeComboBox = new JComboBox();
	private JComboBox bubbleTypeComboBox = new JComboBox();;
	private JLabel rowsLabel = new JLabel();
	private JLabel columnsLabel = new JLabel();
	private JButton startButton = new JButton();
	private Component component = null;
	private JPanel backgroundPanel = new JPanel();

	public NewGameAskUserPanel(Component component) {
		super();
		this.component=component;
		setLayout(null);

		add(backgroundPanel);
		backgroundPanel.setSize(238,213);
		backgroundPanel.setLayout(null);

		final PicturePanel titlePanel = new PicturePanel("jBubbleBreaker-text.png",219,43);
		titlePanel.setToolTipText("jBubbleBreaker " + JBubbleBreaker.getVersion());
		titlePanel.setLocation(10,9);
		backgroundPanel.add(titlePanel);

		final JLabel gameModeLabel = new JLabel();
		gameModeLabel.setBounds(10+15, 9+55, 91, 14);
		gameModeLabel.setText(Localization.getString("GameMode"));
		backgroundPanel.add(gameModeLabel);

		final JLabel bubbleTypeLabel = new JLabel();
		bubbleTypeLabel.setText(Localization.getString("BubbleType"));
		bubbleTypeLabel.setBounds(10+15, 104+55, 91, 14);
		backgroundPanel.add(bubbleTypeLabel);

		rowsLabel.setBounds(10+15, 42+55, 91, 14);
		rowsLabel.setText(Localization.getString("Rows"));
		backgroundPanel.add(rowsLabel);

		columnsLabel.setBounds(10+15, 75+55, 91, 14);
		columnsLabel.setText(Localization.getString("Columns"));
		backgroundPanel.add(columnsLabel);

		startButton.setBounds(10+15, 128+55, 188, 23);
		startButton.setText(Localization.getString("Start"));
		backgroundPanel.add(startButton);
		startButton.addActionListener(this);

		rowsSlider = new JSlider();
		rowsSlider.setBounds(107+15, 33+55, 91, 23);
		backgroundPanel.add(rowsSlider);
		rowsSlider.addChangeListener(this);
		rowsSlider.setValue(12);

		columnsSlider = new JSlider();
		columnsSlider.setBounds(107+15, 71+55, 91, 23);
		backgroundPanel.add(columnsSlider);
		columnsSlider.addChangeListener(this);
		columnsSlider.setValue(12);

		gameTypeComboBox.setBounds(107+15, 5+55, 91, 22);
		gameTypeComboBox.addActionListener(this);
		backgroundPanel.add(gameTypeComboBox);
		Iterator<GameMode> gameModesIterator = JBubbleBreaker.getModes().iterator();
		while(gameModesIterator.hasNext()) {
			gameTypeComboBox.addItem((gameModesIterator.next()).getModeName());
		}

		bubbleTypeComboBox.setBounds(107+15, 100+55, 91, 22);
		backgroundPanel.add(bubbleTypeComboBox);
		Iterator<BubbleType> bubbleTypesIterator = JBubbleBreaker.getBubbleTypes().iterator();
		while(bubbleTypesIterator.hasNext()) {
			bubbleTypeComboBox.addItem((bubbleTypesIterator.next()).getTypeName());
		}

		if (bubbleTypeComboBox.getItemCount() == 0 || gameTypeComboBox.getItemCount() == 0) {
			startButton.setEnabled(false);
		}
		gameTypeComboBox.setSelectedItem(JBubbleBreaker.getUserProperty("lastGameMode",""));
		bubbleTypeComboBox.setSelectedItem(JBubbleBreaker.getUserProperty("lastBubbleType",""));
		try {
			rowsSlider.setValue(Integer.parseInt(JBubbleBreaker.getUserProperty("lastRows","")));
			columnsSlider.setValue(Integer.parseInt(JBubbleBreaker.getUserProperty("lastColumns","")));
		} catch (Exception e) {
			// ignore
		}
	}

	/**
	 * Always place this JPanel in the middle of the super component
	 */
	@Override
	public void setBounds(int x, int y, int width, int height) {
		super.setBounds(x,y,width,height);
		if (width < 238+2*x) {
			width=238+2*x;
		}
		if (height < 213+2*y) {
			height=213+2*y;
		}
		backgroundPanel.setLocation((width-238)/2,(height-213)/2);
	}

	public void actionPerformed(ActionEvent arg0) {
		if (arg0.getSource() == gameTypeComboBox) {
			GameSize mySize = JBubbleBreaker.getModes().get(gameTypeComboBox.getSelectedIndex()).getAllowedSize();
			rowsSlider.setMaximum(mySize.getMaxRows());
			rowsSlider.setMinimum(mySize.getMinRows());
			columnsSlider.setMinimum(mySize.getMinColumns());
			columnsSlider.setMaximum(mySize.getMaxColumns());
		} else {
			Game game = null;
			try {
				game = (Game)JBubbleBreaker.getModes().get(gameTypeComboBox.getSelectedIndex()).getConstructor().newInstance( new Object[] {rowsSlider.getValue(),columnsSlider.getValue(), bubbleTypeComboBox.getSelectedIndex()} );
			} catch (Exception e) {
				e.printStackTrace();
				JOptionPane.showMessageDialog(null, "For some reason ("+e+") jBubbleBreaker is not able to start the mode "+gameTypeComboBox.getSelectedItem()+".", "jBubbleBreaker", JOptionPane.ERROR_MESSAGE);
			}
			if (game != null) {
				((GUIIf)component).startNewGame(game);
				JBubbleBreaker.setUserProperty("lastGameMode", (String) gameTypeComboBox.getSelectedItem());
				JBubbleBreaker.setUserProperty("lastBubbleType", (String) bubbleTypeComboBox.getSelectedItem());
				JBubbleBreaker.setUserProperty("lastRows", ((Integer) rowsSlider.getValue()).toString());
				JBubbleBreaker.setUserProperty("lastColumns", ((Integer) columnsSlider.getValue()).toString());
			}
		}
	}

	public void stateChanged(ChangeEvent arg0) {
		if (arg0.getSource() == rowsSlider) {
			rowsLabel.setText(Localization.getString("Rows")+": "+String.valueOf(rowsSlider.getValue()));
		} else if (arg0.getSource() == columnsSlider){
			columnsLabel.setText(Localization.getString("Columns")+": "+String.valueOf(columnsSlider.getValue()));
		}
	}
}
