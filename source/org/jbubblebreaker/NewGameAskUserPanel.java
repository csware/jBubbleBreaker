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
 * @author Sven Strickroth
 */
@SuppressWarnings("serial")
public class NewGameAskUserPanel extends JPanel implements ActionListener, ChangeListener, AbstractGUI {
	private JSlider columnsSlider;
	private JSlider rowsSlider;
	private JComboBox gameTypeComboBox = new JComboBox();
	private JComboBox bubbleTypeComboBox = new JComboBox();;
	private JLabel rowsLabel = new JLabel();
	private JLabel columnsLabel = new JLabel();
	private JButton startButton = new JButton();
	private Component component = null;
	
	public NewGameAskUserPanel(Component component) {
		super();
		this.component=component;
		setLayout(null);

		final JLabel gameModeLabel = new JLabel();
		gameModeLabel.setBounds(10, 9, 91, 14);
		gameModeLabel.setText("Game mode:");
		add(gameModeLabel);

		final JLabel bubbleTypeLabel = new JLabel();
		bubbleTypeLabel.setText("Bubble type:");
		bubbleTypeLabel.setBounds(10, 104, 91, 14);
		add(bubbleTypeLabel);
		
		rowsLabel.setBounds(10, 42, 91, 14);
		rowsLabel.setText("Rows:");
		add(rowsLabel);

		columnsLabel.setBounds(10, 75, 91, 14);
		columnsLabel.setText("Columns:");
		add(columnsLabel);

		startButton.setBounds(10, 128, 188, 23);
		startButton.setText("Start!");
		add(startButton);
		startButton.addActionListener(this);

		rowsSlider = new JSlider();
		rowsSlider.setBounds(107, 33, 91, 23);
		add(rowsSlider);
		rowsSlider.addChangeListener(this);
		rowsSlider.setValue(12);
		
		columnsSlider = new JSlider();
		columnsSlider.setBounds(107, 71, 91, 23);
		add(columnsSlider);
		columnsSlider.addChangeListener(this);
		columnsSlider.setValue(12);

		gameTypeComboBox.setBounds(107, 5, 91, 22);
		gameTypeComboBox.addActionListener(this);
		add(gameTypeComboBox);
		Iterator<GameMode> gameModesIterator = JBubbleBreaker.getModes().iterator();
		while(gameModesIterator.hasNext()) {
			gameTypeComboBox.addItem((gameModesIterator.next()).getModeName());
		}

		bubbleTypeComboBox.setBounds(107, 100, 91, 22);
		add(bubbleTypeComboBox);
		Iterator<BubbleType> bubbleTypesIterator = JBubbleBreaker.getBubbleTypes().iterator();
		while(bubbleTypesIterator.hasNext()) {
			bubbleTypeComboBox.addItem((bubbleTypesIterator.next()).getTypeName());
		}

		if (bubbleTypeComboBox.getItemCount() == 0 || gameTypeComboBox.getItemCount() == 0) {
			startButton.setEnabled(false);
		}
		setAutoscrolls(true);
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
				JOptionPane.showMessageDialog(null, "For some reason ("+e+") JBubbleBreaker is not able to start the mode "+gameTypeComboBox.getSelectedItem()+".", "JBubbleBreaker", JOptionPane.ERROR_MESSAGE);
			}
			if (game != null) {
				((AbstractGUI)component).startNewGame(game);
			}
		}
	}

	public void stateChanged(ChangeEvent arg0) {
		if (arg0.getSource() == rowsSlider) {
			rowsLabel.setText("Rows: "+String.valueOf(rowsSlider.getValue()));
		} else if (arg0.getSource() == columnsSlider){
			columnsLabel.setText("Columns: "+String.valueOf(columnsSlider.getValue()));
		}
	}

	public void startNewGame(Game game) {
		// TODO Auto-generated method stub
		
	}
}
