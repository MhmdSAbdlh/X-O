import java.awt.Color;
import java.awt.Font;
import javax.swing.JLabel;

public class Game {
	
	public static Color darkC = new Color(34, 40, 49);
	public static Color ligthC = new Color(238, 238, 238);
	public static Color playerX = new Color(123, 17, 58);
	public static Color playerY = new Color(21, 151, 187);
	public static Font introF = new Font("Tahoma",Font.BOLD,75);
	public static Font introSF = new Font("Tahoma",Font.BOLD,35);
	public static JLabel credit = new JLabel("Created with Love by MhmdSAbdlh");
	
	public static void main(String[] args) {
		new GameIntro();	
	}
}
