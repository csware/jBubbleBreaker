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

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Iterator;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

/**
 * Ask the user to start a new game 
 * @author Sven Strickroth
 */
@SuppressWarnings("serial")
public class NewGameAskUser extends MyModalJFrame implements ActionListener, ChangeListener {
	private JSlider columnsSlider;
	private JSlider rowsSlider;
	private JComboBox comboBox = new JComboBox();
	private JLabel rowsLabel = new JLabel();
	private JLabel columnsLabel = new JLabel();
	private JButton startButton = new JButton();
	
	/**
	 * Create the frame
	 */
	public NewGameAskUser(JFrame parentJFrame) {
		super("New Game...", null, 215, 175, false, parentJFrame);
		getContentPane().setLayout(null);

		final JLabel gameModeLabel = new JLabel();
		gameModeLabel.setBounds(10, 9, 91, 14);
		gameModeLabel.setText("Game mode:");
		getContentPane().add(gameModeLabel);

		rowsLabel.setBounds(10, 42, 91, 14);
		rowsLabel.setText("Rows:");
		getContentPane().add(rowsLabel);


		columnsLabel.setBounds(10, 75, 91, 14);
		columnsLabel.setText("Columns:");
		getContentPane().add(columnsLabel);

		startButton.setBounds(10, 111, 188, 23);
		startButton.setText("Start!");
		getContentPane().add(startButton);
		startButton.addActionListener(this);

		rowsSlider = new JSlider();
		rowsSlider.setBounds(107, 33, 91, 23);
		getContentPane().add(rowsSlider);
		rowsSlider.addChangeListener(this);
		rowsSlider.setValue(12);
		
		columnsSlider = new JSlider();
		columnsSlider.setBounds(107, 71, 91, 23);
		getContentPane().add(columnsSlider);
		columnsSlider.addChangeListener(this);
		columnsSlider.setValue(12);

		comboBox.setBounds(107, 5, 91, 22);
		comboBox.addActionListener(this);
		getContentPane().add(comboBox);
		Iterator iterator = JBubbleBreaker.getModes().iterator();
		while(iterator.hasNext()) {
			comboBox.addItem(((GameMode)iterator.next()).getModiName());
		}

		
		setDefaultCloseOperation(EXIT_ON_CLOSE);

		setVisible(true);
	}
	
	public void actionPerformed(ActionEvent arg0) {
		if (arg0.getSource() == comboBox) {
			GameSize mySize = JBubbleBreaker.getModes().get(comboBox.getSelectedIndex()).getAllowedSize();
			rowsSlider.setMaximum(mySize.getMaxRows());
			rowsSlider.setMinimum(mySize.getMinRows());
			columnsSlider.setMinimum(mySize.getMinColumns());
			columnsSlider.setMaximum(mySize.getMaxColumns());
		} else {
			Game game = null;
			// TODO: JBubbleBreaker.getModes().get(comboBox.getSelectedIndex())
			Iterator<GameMode> iterator = JBubbleBreaker.getModes().iterator();
			while(iterator.hasNext()) {
				GameMode modus = iterator.next();
				try {
					if (modus.getModiName().equals(comboBox.getSelectedItem())) {
						game = (Game)modus.getConstructor().newInstance( new Object[] {rowsSlider.getValue(),columnsSlider.getValue(),((GUI)parentJFrame).pointsLabel} );
					}
				} catch (Exception e) {
					JOptionPane.showMessageDialog(null, "For some reason ("+e.getLocalizedMessage()+") JBubbleBreaker is not able to start the mode "+modus.getModiName()+".", "JBubbleBreaker", JOptionPane.ERROR_MESSAGE);
				}
			}
			if (game != null) {
				((GUI)parentJFrame).startNewGame(game);
				setDefaultCloseOperation(DISPOSE_ON_CLOSE);
				dispose();
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
}
