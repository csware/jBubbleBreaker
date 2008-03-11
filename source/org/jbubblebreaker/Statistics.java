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

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.AbstractTableModel;

/**
 * Statistics handling and Statistics JFrame 
 * @author Sven Strickroth
 */
@SuppressWarnings("serial")
public class Statistics extends MyModalJFrame implements ActionListener {
	private static boolean guestMode = true;

	/**
	 * Create the frame
	 */
	public Statistics(JFrame parentJFrame) {
		super("Statistics", "jbubblebreaker.png", 677, 175, true, parentJFrame);
		getContentPane().setLayout(new BorderLayout());
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);

		JButton startButton = new JButton("Close");
		getContentPane().add(startButton, BorderLayout.SOUTH);
		startButton.addActionListener(this);

		final JScrollPane scrollPane_1 = new JScrollPane();
		getContentPane().add(scrollPane_1, BorderLayout.CENTER);

		final JTable table = new JTable();
		scrollPane_1.setViewportView(table);
		table.setModel(new TableTableModel());
		table.setAutoCreateRowSorter(true);
		
		setVisible(true);
	}

		private class TableTableModel extends AbstractTableModel {
			private final String[] COLUMN_NAMES = new String[] {"Mode", "Colors", "Rows","Columns","Games played","Max. Points","Avg. Points"};
			private List<StatisticData> myData = getStatistics();

			public int getRowCount() {
				return myData.size();
			}

			public int getColumnCount() {
				return COLUMN_NAMES.length;
			}

			public String getColumnName(int columnIndex) {
				return COLUMN_NAMES[columnIndex];
			}

			public Object getValueAt(int rowIndex, int columnIndex) {
				if (columnIndex == 0) {
					return myData.get(rowIndex).getMode();
				} else if (columnIndex == 1) {
					return myData.get(rowIndex).getColors();
				} else if (columnIndex == 2) {
					return myData.get(rowIndex).getRows();
				} else if (columnIndex == 3) {
					return myData.get(rowIndex).getCols();
				} else if (columnIndex == 4) {
					return myData.get(rowIndex).getCountOfGames();
				} else if (columnIndex == 5) {
					return myData.get(rowIndex).getMaxPoints();
				} else {
					return myData.get(rowIndex).getAveragePoints();
				}
			}

			public boolean isCellEditable(int row, int col) {
				return false;
			}

			@SuppressWarnings("unchecked")
			public Class getColumnClass(int c) {
				if (c == 0) {
					return String.class;
				} else {
					return Integer.class;
				}
			}
		}

	public void actionPerformed(ActionEvent arg0) {
		dispose();
	}

	static String updateStatistics(String mode, int colors, int rows, int cols, int points) {
		String returnString = "";
		if (isGuestMode() == false) {
			ObjectOutputStream out = null;
			try {
				Iterator<StatisticData> myIterator = getStatistics().iterator();
				out = new ObjectOutputStream(new BufferedOutputStream(new FileOutputStream(System.getProperty("user.home")+"/.jbubblebreaker-statistics")));
				boolean found = false;
				while(myIterator.hasNext()) {
					StatisticData myItem = myIterator.next(); 
					if (myItem.getMode().equals(mode) && myItem.getColors() == colors && myItem.getRows() == rows && myItem.getCols() == cols) {
						myItem.newGame(points);
						returnString = "\nPlayed games: " + myItem.getCountOfGames()+"\nAverage points: "+myItem.getAveragePoints()+"\nMax points: "+myItem.getMaxPoints();
						found = true;
					}
					out.writeObject(myItem);
				}
				if (found == false) {
					out.writeObject(new StatisticData(mode, colors, rows, cols, points));
					returnString = "\nFirst time you played this mode-, size-combination.";
				}
				out.close();
			} catch (FileNotFoundException e) {
			} catch (IOException e) {
			}
		}
		return returnString;
	}

	public static List<StatisticData> getStatistics() {
		List<StatisticData> myList = new LinkedList<StatisticData>();
		ObjectInputStream in = null;
		try {
			in = new ObjectInputStream(new BufferedInputStream(new FileInputStream(System.getProperty("user.home")+"/.jbubblebreaker-statistics")));
		} catch (FileNotFoundException e) {
			return myList;
		} catch (IOException e) {
			return myList;
		}
		try {
			StatisticData statisticData;
			while ((statisticData = (StatisticData)in.readObject()) != null) {
				myList.add(statisticData);
			}
			in.close();
		} catch (IOException e) {
		} catch (ClassNotFoundException e) {
		}
		return myList;
	}

	/**
	 * @return the guestMode
	 */
	public static boolean isGuestMode() {
		return guestMode;
	}

	/**
	 * @param guestMode the guestMode to set
	 */
	public static void setGuestMode(boolean guestMode) {
		Statistics.guestMode = guestMode;
	}
}
