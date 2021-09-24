import java.awt.*;
import javax.swing.*;

@SuppressWarnings("serial")
public class GameIntro extends JFrame{
	
	JLabel gameName = new JLabel("X-O Game");
	JButton onePlayer = new JButton("SINGLE");
	JButton twoPlayer = new JButton("MULTIPLAYER");
	static String userX;
	static String userO;
	
	GameIntro(){
		//Frame
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setSize(640, 800);
		this.setLayout(null);
		this.setResizable(false);
		this.setLocationRelativeTo(null);
		this.setTitle("X-O Game");
		this.getContentPane().setBackground(Game.darkC);
		this.getRootPane().setDefaultButton(onePlayer);
		this.setIconImage(new ImageIcon(getClass().getClassLoader().getResource("xo.png")).getImage());
		
		//Labels
		gameName.setBounds(0, 100, 640, 100);
		gameName.setHorizontalAlignment(0);
		gameName.setFont(Game.introF);
		gameName.setForeground(Game.ligthC);
		Game.credit.setHorizontalAlignment(0);
		Game.credit.setBounds(0, 730, 640, 20);
		Game.credit.setForeground(Color.white);
		
		//Buttons
		onePlayer.setFont(Game.introSF);
		onePlayer.setBounds(120, 250, 400, 50);
		onePlayer.setFocusable(false);
		onePlayer.setBackground(Game.ligthC);
		onePlayer.setForeground(Game.darkC);
		onePlayer.addActionListener((event) -> {
			String[] difSelect = {"Easy","Hard"};
			int actionO = JOptionPane.showOptionDialog(this, "DIFFICULTY", "PC LEVEL", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.INFORMATION_MESSAGE, null, difSelect, null);
			GameOP.difLevel =actionO;
			this.dispose();
			new GameOP();
			});
		twoPlayer.setFont(Game.introSF);
		twoPlayer.setBounds(120, 350, 400, 50);
		twoPlayer.setFocusable(false);
		twoPlayer.setBackground(Game.ligthC);
		twoPlayer.setForeground(Game.darkC);
		twoPlayer.addActionListener((event) -> {
			userX = JOptionPane.showInputDialog("X-Player, Please enter your name:");
			userO = JOptionPane.showInputDialog("O-Player, Please enter your name:");
			this.dispose();
			new GameMP();
			});
		JButton Exit = new JButton("EXIT GAME");
		Exit.setFont(Game.introSF);
		Exit.setBounds(120, 450, 400, 50);
		Exit.setFocusable(false);
		Exit.setBackground(Game.ligthC);
		Exit.setForeground(Game.darkC);
		Exit.addActionListener((event) -> System.exit(0));
				
		//Add to frame
		this.add(gameName);
		this.add(onePlayer);
		this.add(twoPlayer);
		this.add(Exit);
		this.add(Game.credit);
		
		//Show frame
		this.setVisible(true);
	}	
}
