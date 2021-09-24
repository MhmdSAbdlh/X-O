import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Random;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.UIManager;

@SuppressWarnings("serial")
public class GameMP extends JFrame implements ActionListener, KeyListener{
	
	public static JLabel player = new JLabel("X Turn");
	int actionO=0;
	Random random = new Random();
	JButton[] button = new JButton[9];
	String label = "X",gameEnd ="";
	String[] messGO = {"New Game","Restart Game","Exit"};	
	
	public GameMP() {
		
		//Frame
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setSize(640, 800);
		this.setLayout(null);
		this.setResizable(false);
		this.setLocationRelativeTo(null);
		this.getContentPane().setBackground(Game.darkC);
		this.setTitle("X-O Game");
		this.setIconImage(new ImageIcon(getClass().getClassLoader().getResource("xo.png")).getImage());
		
		//Panel
		JPanel panel = new JPanel();
		panel.setBounds(10, 10, 600, 600);
		panel.setLayout(new GridLayout(3,3,0,0));
		this.addKeyListener(this);
		
		//Label
		int pTurn = random.nextInt(2);
		if(pTurn == 1) {
			player.setText("X Turn");
			label = "X";
			player.setForeground(Game.playerX);
		}
		else {
			player.setText("O Turn");
			label = "O";
			player.setForeground(Game.playerY);
		}	
		player.setBounds(0, 620, 640, 100);
		player.setHorizontalAlignment(0);
		player.setFont(Game.introF);
		
		//Button
		for(int i=0;i<9;i++) {
			button[i] = new JButton();
			button[i].setFocusable(false);
			button[i].addActionListener(this);
			button[i].setFont(Game.introF);
			button[i].setBackground(Game.ligthC);
			button[i].setForeground(Game.playerX);
			panel.add(button[i]);
		}	
		
		//Menubar
		JMenuBar mb = new JMenuBar();
		JMenu m1 = new JMenu("File");
		JMenuItem m2 = new JMenuItem("About");
		mb.add(m1);
		mb.add(m2);
		JMenuItem m11 = new JMenuItem("New");
		JMenuItem m12 = new JMenuItem("Restart");
		JMenuItem m13 = new JMenuItem("Exit");
		m1.add(m11);
		m1.add(m12);
		m1.add(m13);
		m11.addActionListener(e -> {
			this.dispose();
			new GameIntro();});
		m12.addActionListener(e -> {
			this.dispose();
			new GameMP();});
		m13.addActionListener(e -> System.exit(0));
		m2.addActionListener(e -> {JOptionPane.showMessageDialog(this,Game.credit.getText());});
		mb.setFocusable(false);
		
		//Add to frame
        this.setJMenuBar(mb);
        this.add(player);
		this.add(panel);

		//Show frame
		this.setVisible(true);
	}

	private boolean gameOver() {
		if(button[0].getText()!=""&&button[1].getText()!=""&&button[2].getText()!=""
				&&button[3].getText()!=""&&button[4].getText()!=""&&button[5].getText()!=""
					&&button[6].getText()!=""&&button[7].getText()!=""&&button[8].getText()!="")
			return true;
		else
			return false;
	}
	
	private void check() {
		int i=0,j=0;
		while(i<9 && j<3){
			if((button[i].getText()==button[i+1].getText())&&(button[i+1].getText()==button[i+2].getText())&&(button[i].getText()!="")) {
					gameEnd = button[i].getText();
					break;
				}
				i+=3;
				if((button[j].getText()==button[j+3].getText())&&(button[j+3].getText()==button[j+6].getText())&&(button[j].getText()!="")) {
					gameEnd = button[j].getText();
					break;
				}					
				j++;
				if(((button[0].getText()==button[4].getText())&&(button[4].getText()==button[8].getText()))
						|| ((button[2].getText()==button[4].getText())&&(button[4].getText()==button[6].getText())))
					{gameEnd = button[4].getText();
					break;}
			}
			if(gameEnd != "") {
				if(gameEnd=="X")
					actionO = JOptionPane.showOptionDialog(this, "Congrats "+GameIntro.userX.toUpperCase()+" aka 'X player', You win!", "Game Over", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.INFORMATION_MESSAGE, null, messGO, null);
				else
					actionO = JOptionPane.showOptionDialog(this, "Congrats "+GameIntro.userO.toUpperCase()+" aka 'O player', You win!", "Game Over", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.INFORMATION_MESSAGE, null, messGO, null);
				if(actionO == 0) {
					this.dispose();
					new GameIntro();
				}
				else
					if(actionO == 1) {
						this.dispose();
						new GameMP();
					}
					else
						System.exit(0);
			}
			else {
				if(gameOver() == true) {
					actionO = JOptionPane.showOptionDialog(this, "It's a draw :|", "Game Over", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.INFORMATION_MESSAGE, null, messGO, null);
					if(actionO == 0) {
						this.dispose();
						new GameIntro();
					}
					else
						if(actionO == 1) {
							this.dispose();
							new GameMP();
						}
						else
							System.exit(0);
				}
			}
		}
	
	private String Change() {	
		if(label == "X")
			label = "O";
		else
			label = "X";
		return label;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		for(int i=0;i<9;i++)
			if(e.getSource()==button[i] && button[i].getText() == "") {
			button[i].setText(label);
			button[i].setEnabled(false);
			if(button[i].getText()=="X") {
				 UIManager.getDefaults().put("Button.disabledText",Game.playerX);
					player.setForeground(Game.playerY);
					player.setText("O Turn");}
			else {
				 UIManager.getDefaults().put("Button.disabledText",Game.playerY);
				 player.setForeground(Game.playerX);
				 player.setText("X Turn");}
			Change();
			}
		check();
		if(gameEnd != "") {
			for(int i=0;i<9;i++)
				button[i].setEnabled(false);
		}
	}

	@Override
	public void keyTyped(KeyEvent e) {}

	@Override
	public void keyPressed(KeyEvent e) {
		
		int[] keyNum = {103,104,105,100,101,102,97,98,99};
		
		for(int i=0;i<9;i++)
			if(e.getKeyCode()==keyNum[i] && button[i].getText()=="") {
				button[i].setText(label);
				button[i].setEnabled(false);
				if(button[i].getText()=="X") {
					UIManager.getDefaults().put("Button.disabledText",Game.playerX);
					player.setForeground(Game.playerY);
					player.setText("O Turn");}
				else {
					UIManager.getDefaults().put("Button.disabledText",Game.playerY);
					player.setForeground(Game.playerX);
					player.setText("X Turn");
				}
				Change();
				check();
				if(gameEnd != "") {
					for(int j=0;j<9;j++)
						button[j].setEnabled(false);
				}
			}
		}

	@Override
	public void keyReleased(KeyEvent e) {}
}