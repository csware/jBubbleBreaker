package org.jbubblebreaker;


import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

import javax.swing.JApplet;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.KeyStroke;
import javax.swing.SwingUtilities;

/**
 * @author Sven Strickroth
 */
@SuppressWarnings("serial")
public class Applet extends JApplet implements ActionListener, AbstractGUI {
    private JPanel infoPanel = new JPanel();
    private Game game;
    private JLabel pointsLabel = new JLabel();
    private JLabel gameModeLabel = new JLabel();

	private JMenuBar menuBar;
	private JMenuItem menuHelpInfo,menuFileNew,menuFileNewDots;
	
    public void init() {
        try {
            SwingUtilities.invokeAndWait(new Runnable() {
                public void run() {
                	JBubbleBreaker.registerDefault();
                	createGUI();
                }
            });
        } catch (Exception e) {
            System.err.println("createGUI didn't successfully complete");
        }
    }

    private void createGUI() {        
    	// insert Menu
    	menuBar = new JMenuBar();
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
		menuHelpInfo = new JMenuItem("About");
		menuHelpInfo.addActionListener(this);
		menuHelpInfo.setMnemonic('a');
		menuHelp.add(menuHelpInfo);
		newGameDots();
    }

    private void newGameDots() {
    	game=null;
    	NewGameAskUserPanel nGAuP = new NewGameAskUserPanel(this);
    	nGAuP.setVisible(false);
    	setContentPane(nGAuP);
    	nGAuP.setVisible(true);
    }

	public void actionPerformed(ActionEvent arg0) {
		if (arg0.getSource() == menuHelpInfo) {
//			new AboutBox(this);
		} else if (arg0.getSource() == menuFileNew) {
			game.newGame();
		} else if (arg0.getSource() == menuFileNewDots) {
			newGameDots();
		}
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
		if (game != null) {
			getContentPane().remove(game.getPanel());
		}
		gameModeLabel.setText(game.getMode());
		this.getContentPane().add(game.getPanel(), BorderLayout.CENTER);
		game.setPointsLabel(pointsLabel);

		infoPanel.setLayout(new BorderLayout());

		infoPanel.add(pointsLabel, BorderLayout.WEST);
		
		infoPanel.add(gameModeLabel, BorderLayout.EAST);
		pointsLabel.setText("Points: 0");
	}
}
