package org.jbubblebreaker;
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

import java.awt.BorderLayout;
import java.awt.Toolkit;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * @author Sven Strickroth
 */
@SuppressWarnings("serial")
public class GUI extends JFrame implements MouseListener {

	private JPanel infoPanel = new JPanel();
	private JPanel playgroundPanel = new JPanel();
	private int rows = 12;
	private int cols = 12;
	private int marked = 0;
	private int points = 0;
	private JLabel possiblePoints = new JLabel("2000");
	
	private Bubble[][] playground; 
	/**
	 * Launch the application
	 * @param args
	 */
	public static void main(String args[]) {
		try {
			GUI frame = new GUI();
			frame.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the frame
	 */
	private GUI() {
		super("JBubbleBreaker");
		setLayout(null);
		setSize(407,427);
		setResizable(false);
		setLocation((Toolkit.getDefaultToolkit().getScreenSize().width-getWidth())/2,(Toolkit.getDefaultToolkit().getScreenSize().height-getHeight())/2);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		getContentPane().add(infoPanel, BorderLayout.SOUTH);
		getContentPane().add(playgroundPanel, BorderLayout.CENTER);
		playgroundPanel.setSize(500,600);
		playgroundPanel.setLayout(null);
		playgroundPanel.repaint();

		possiblePoints.setSize(50,50);
		playgroundPanel.add(possiblePoints);
		
		possiblePoints.setVisible(false);
		
		int horizontal = 400 / cols;
		int vertikal = 400 / rows;
		playground = new Bubble[rows][cols];
		for(int i=0; i < rows; i++) {
			for(int j=0; j < cols; j++) {
				playground[i][j] = new Bubble(horizontal,vertikal,i,j,this);
				playgroundPanel.add(playground[i][j]);
			}
		}
	}

	private void findsame(int row, int col) {
		Bubble circle = playground[row][col];
		if (circle == null) { return; }
		marked++;
		circle.setMark(true);
		int color = circle.getColor();
		
		if (col+1 < cols) {
			if (playground[row][col+1] != null && playground[row][col+1].getColor() == color && playground[row][col+1].isMarked()==false) {
				findsame(row,col+1);
			}
		}
		if (col >= 1) {
			if (playground[row][col-1] != null && playground[row][col-1].getColor() == color && playground[row][col-1].isMarked()==false) {
				findsame(row,col-1);
			}
		}

		if (row+1 < rows) {
			if (playground[row+1][col] != null && playground[row+1][col].getColor() == color && playground[row+1][col].isMarked()==false) {
				findsame(row+1,col);
			}
		}
		if (row >= 1) {
			if (playground[row-1][col] != null && playground[row-1][col].getColor() == color && playground[row-1][col].isMarked()==false) {
				findsame(row-1,col);
			}
		}
		
	}
	
	private void unmarkAll() {
		marked=0;
		for(int i=0; i < rows; i++) {
			for(int j=0; j < cols; j++) {
				if (playground[i][j] != null) {
					playground[i][j].setMark(false);
				}
			}
		}
		possiblePoints.setVisible(false);
	}
	
	private void removeBubbles(int row, int col) {
		// erstmal nur in der Spalte löschen
		for(int k=0; k < cols; k++) {
			for(int i=0; i < rows; i++) {
				//System.out.println(i);
				if (playground[i][k] != null && playground[i][k].isMarked() == true) {
					//System.out.println("wech:"+i);
					playgroundPanel.remove(playground[i][k]);
					playground[i][k] = null;
					for(int j=i; j > 0; j--) {
						playground[j][k] = playground[j-1][k];
						if (playground[j][k] != null) {
							playground[j][k].moveTo(j,k);
						}
						//System.out.println("wandern "+(j-1)+" nach "+j);
					}
					playground[0][k] = null;
				}
			}
		}
		int firstEmpty = -1;
		for(int k=0; k < cols; k++) {
			if (playground[rows-1][k] == null && firstEmpty == -1) {
				firstEmpty = k;
			}
		}
		for(int k=cols-1; k > 0; k--) {
			while (playground[rows-1][k] == null && firstEmpty <= k) {
				for(int j=k; j > 0; j--) {
					//System.out.println("wandern "+(j-1)+" nach "+j);
					for(int i=0; i < rows; i++) {
						playground[i][j] = playground[i][j-1];
						if (playground[i][j] != null) {
							playground[i][j].moveTo(i,j);
						}
					}
				}
				for(int i=0; i < rows; i++) {
					playground[i][0] = null;					
				}
				firstEmpty++;
			}
		}
		possiblePoints.setVisible(false);
		marked=0;
		this.setTitle("JBubbleBreaker Punkte: "+ points);
		playgroundPanel.repaint();
		if (isSolveable() == false) {
			System.out.println("ENDE!!!");
			this.setTitle("JBubbleBreaker Punkte: "+ points+"ENDE!!!");
		}
	}

	private boolean isSolveable() {
		int row = rows - 1;
		int col = cols - 1;
		while(col > 0 && playground[row][col] != null) {
			while(row > 0 && playground[row][col] != null) {
				if (playground[row-1][col] != null && playground[row][col].getColor() == playground[row-1][col].getColor()) {
					return true;
				}
				if (playground[row][col-1] != null && playground[row][col].getColor() == playground[row][col-1].getColor()) {
					return true;
				}
				row--;
			}
			col--;
			row = rows - 1;
		}
		return false;
	}

	public void mouseClicked(MouseEvent arg0) {
		this.setTitle("JBubbleBreaker Punkte: "+ points);
		Bubble my = (Bubble)(arg0.getSource());
		if (my==null) { return; }
		//System.out.println(my.getRow() +"x"+ my.getCol());
		if (marked != 0) {
			if (my.isMarked() == false) {
				unmarkAll();
			} else {
				points += (marked*(marked-1));
				removeBubbles(my.getRow(),my.getCol());
			}
			return;
		}
		marked=0;
		findsame(my.getRow(),my.getCol());
		possiblePoints.setText(""+(marked*(marked-1)));
		possiblePoints.setLocation((int)my.getLocation().getX()+10,(int)my.getLocation().getY()-12);
		possiblePoints.setVisible(true);
		if (marked==1) {
			unmarkAll();
		}
	}

	public void mouseEntered(MouseEvent arg0) { }
	public void mouseExited(MouseEvent arg0) { }
	public void mousePressed(MouseEvent arg0) { }
	public void mouseReleased(MouseEvent arg0) { }
}
