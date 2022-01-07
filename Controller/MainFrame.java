package Controller;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;

public class MainFrame extends JFrame implements ActionListener, Runnable {
    protected int row = 8; // Create a matrix 8 x 8
	protected int col = 8;
	private int width = 1500; // Size of panel (width, height)
	private int height = 1000;
	private ButtonEvent graphicsPanel;
	private JPanel mainPanel;
	protected int MAX_TIME = 300;
	public int time = MAX_TIME;
	public JLabel lbScore;
	private JProgressBar progressTime; // Create a bar to set time for the game
	private JButton btnNewGame;
	private JButton btnReset;
	private boolean pause = false;
	private boolean resume = false;
      
	public MainFrame() {
		add(mainPanel = createMainPanel());
		setTitle("Pokemon Game");
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(width, height);
		setLocationRelativeTo(null);
		setVisible(true);
                
	}

	public boolean isPause() {
		return pause;
	}

	public void setPause(boolean pause) {
		this.pause = pause;
	}

	public boolean isResume() {
		return resume;
	}

	public void setResume(boolean resume) {
		this.resume = resume;
	}

	private JPanel createMainPanel() {
		JPanel panel = new JPanel(new BorderLayout());
		panel.add(createGraphicsPanel(), BorderLayout.CENTER);
		panel.add(createControlPanel(), BorderLayout.EAST);
		return panel;
	}

	private JPanel createGraphicsPanel() {
		graphicsPanel = new ButtonEvent(this, row, col);
		JPanel panel = new JPanel(new GridBagLayout());
		panel.setBackground(Color.gray);
		panel.add(graphicsPanel);
		return panel;
	}

	private JButton createButton(String buttonName) {
		JButton btn = new JButton(buttonName);
		btn.addActionListener(this);
		return btn;
	}

	private JPanel createControlPanel() {
		// Create JLabel and set lbScore equal 0
		lbScore = new JLabel("0");
		progressTime = new JProgressBar(0, 100);
		progressTime.setValue(100);

		// Create Panel to store Score and Time

		JPanel panelLeft = new JPanel(new GridLayout(2, 1, 5, 5));
		panelLeft.add(new JLabel("Score:"));
		panelLeft.add(new JLabel("Time:"));

		JPanel panelCenter = new JPanel(new GridLayout(2, 1, 5, 5));
		panelCenter.add(lbScore);
		panelCenter.add(progressTime);

		JPanel panelScoreAndTime = new JPanel(new BorderLayout(5, 0));
		panelScoreAndTime.add(panelLeft, BorderLayout.WEST);
		panelScoreAndTime.add(panelCenter, BorderLayout.CENTER);

		// Create Panel include panelScoreAndTime and New Game button
		JPanel panelControl = new JPanel(new BorderLayout(10, 10));
		panelControl.setBorder(new EmptyBorder(10, 3, 5, 3));
		panelControl.add(panelScoreAndTime, BorderLayout.CENTER);
		panelControl.add(btnNewGame = createButton("New Game"), 
				BorderLayout.AFTER_LAST_LINE);
		panelControl.add(btnReset = createButton("Reset Level"),
				BorderLayout.AFTER_LINE_ENDS);


		// Set BorderLayout to display panelControl
		JPanel panel = new JPanel(new BorderLayout());
		panel.setBorder(new TitledBorder("Good luck"));
		panel.add(panelControl, BorderLayout.PAGE_START);
		
		return panel;
	}

	public void newGame() {
		time = MAX_TIME;
		graphicsPanel.removeAll();
		mainPanel.add(createGraphicsPanel(), BorderLayout.CENTER);
		mainPanel.validate();
		mainPanel.setVisible(true);
		lbScore.setText("0");
	}

	public void showDialogNewGame(String message, String title, int t) {
		pause = true;
		resume = false;

		int select = JOptionPane.showOptionDialog(null, message, title,
		JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null,
					null, null);
		if (select == 0) {
			pause = false;
			newGame();
		} else {
			if(t==1) {
				System.exit(0);
			} else {
				resume = true;
			}
		}
	}

    @Override
	public void actionPerformed(ActionEvent e) {   
		if(e.getSource() == btnNewGame) {
			row = 8;
			col = 8;
			showDialogNewGame("Your game hasn't done yet. Do you want to start a new game ?", "Warning", 0);
		}

		if(e.getSource() == btnReset) {
			showDialogNewGame("You haven't clear this level yet. Do you want to reset this level ?", "Warning", 0);
		}
	}


	@Override
	public void run() {
		while (true) {
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			progressTime.setValue((int) ((double) time / MAX_TIME * 100));
		}
	}
}
