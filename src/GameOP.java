import java.awt.GridLayout;
import java.awt.event.*;
import java.util.Random;
import javax.swing.*;
import java.awt.Color;
import javax.swing.plaf.metal.MetalButtonUI;

@SuppressWarnings("serial")
public class GameOP extends JFrame implements KeyListener{
	
	int actionO=0;
	static int difLevel;
	JButton[] button = new JButton[9];
	Random random = new Random();
	String[] messGO = {"New Game","Restart Game","Exit"};
	String label = "X",gameEnd ="";	
	
	public GameOP() {
		
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
		JLabel diff = new JLabel(difLevel==1?"Hard":"Easy");
		diff.setBounds(0, 630, 640, 100);
		diff.setHorizontalAlignment(0);
		diff.setForeground(Game.ligthC);
		diff.setFont(Game.introF);
		
		//Button
		for(int i=0;i<9;i++) {
			button[i] = new JButton();
			button[i].setFocusable(false);
			button[i].setFont(Game.introF);
			button[i].setBackground(Game.ligthC);
			button[i].setForeground(Game.playerX);
			button[i].addActionListener(e -> {
				for(int j=0;j<9;j++)
					if(e.getSource()==button[j] && button[j].getText() == "") {
						button[j].setText(label);
						button[j].setEnabled(false);
						button[j].setUI(new MetalButtonUI() {
							protected Color getDisabledTextColor() {
								return Game.playerX;
							}
						});
						Change();
					}
				check();
				if(gameEnd != "")
					for(int k=0;k<9;k++)
						button[k].setEnabled(false);
				if(gameEnd=="" && !gameOver()) {
					pcTurn();
					check();
					if(gameEnd != "")
						for(int j=0;j<9;j++)
							button[j].setEnabled(false);
					}
			});
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
		m11.addActionListener((event) -> {
			this.dispose();
			new GameIntro();});
		m12.addActionListener((event) -> {
			this.dispose();
			new GameOP();});
		m13.addActionListener((event) -> System.exit(0));
		m2.addActionListener((event) -> {JOptionPane.showMessageDialog(this,Game.credit.getText());});
		mb.setFocusable(false);
		
		//Add to frame
        this.setJMenuBar(mb);
		this.add(panel);
		this.add(diff);

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
				if(gameEnd == "X")
					actionO = JOptionPane.showOptionDialog(this, "Congrats, You win!", "Game Over", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.INFORMATION_MESSAGE, null, messGO, null);
				else
					actionO = JOptionPane.showOptionDialog(this, "Hard Luck, You loss!", "Game Over", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.INFORMATION_MESSAGE, null, messGO, null);
				if(actionO == 0) {
					this.dispose();
					new GameIntro();
				}
				else
					if(actionO == 1) {
						this.dispose();
						new GameOP();
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
							new GameOP();
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

	private void pcTurn() {
		
		if(difLevel==0) {
			easyPC();
			Change();
		}
		else {//Hard Level
			hardPC();
			Change();
			}
		}

	private void hardPC() {
		if(button[4].getText()=="") {
			button[4].setText(label);
			button[4].setEnabled(false);
			button[4].setUI(new MetalButtonUI() {
				protected Color getDisabledTextColor() {
					return Game.playerY;
				}
			});
		}
		else {
			int i=0;
			boolean isFind =false;
			while(i<9) {//Check rows
				if(button[i].getText()=="O"&&button[i].getText()==button[i+1].getText()&&button[i+2].getText()=="") {
					button[i+2].setText(label);
					button[i+2].setEnabled(false);
					button[i+2].setUI(new MetalButtonUI() {
						protected Color getDisabledTextColor() {
							return Game.playerY;
						}
					});
					isFind=true;
					break;
				}
				else
					if(button[i].getText()=="O"&&button[i].getText()==button[i+2].getText()&&button[i+1].getText()=="") {
						button[i+1].setText(label);
						button[i+1].setEnabled(false);
						button[i+1].setUI(new MetalButtonUI() {
							protected Color getDisabledTextColor() {
								return Game.playerY;
							}
						});
						isFind=true;
						break;
					}
					else
						if(button[i+1].getText()=="O"&&button[i+1].getText()==button[i+2].getText()&&button[i].getText()=="") {
							button[i].setText(label);
							button[i].setEnabled(false);
							button[i].setUI(new MetalButtonUI() {
								protected Color getDisabledTextColor() {
									return Game.playerY;
								}
							});
							isFind=true;
							break;
						}
						else
							if(button[i].getText()=="X"&&button[i].getText()==button[i+1].getText()&&button[i+2].getText()=="") {
								button[i+2].setText(label);
								button[i+2].setEnabled(false);
								button[i+2].setUI(new MetalButtonUI() {
									protected Color getDisabledTextColor() {
										return Game.playerY;
									}
								});
								isFind=true;
								break;
							}
							else
								if(button[i].getText()=="X"&&button[i].getText()==button[i+2].getText()&&button[i+1].getText()=="") {
									button[i+1].setText(label);
									button[i+1].setEnabled(false);
									button[i+1].setUI(new MetalButtonUI() {
										protected Color getDisabledTextColor() {
											return Game.playerY;
										}
									});
									isFind=true;
									break;
								}
								else
									if(button[i+1].getText()=="X"&&button[i+1].getText()==button[i+2].getText()&&button[i].getText()=="") {
										button[i].setText(label);
										button[i].setEnabled(false);
										button[i].setUI(new MetalButtonUI() {
											protected Color getDisabledTextColor() {
												return Game.playerY;
											}
										});
										isFind=true;
										break;
										}
				i+=3;
			}//end of check rows
			i=0;
			if(isFind==false)
				while(i<3) {//Check Columns	
					if(button[i].getText()=="O"&&button[i].getText()==button[i+3].getText()&&button[i+6].getText()=="") {
						button[i+6].setText(label);
						button[i+6].setEnabled(false);
						button[i+6].setUI(new MetalButtonUI() {
							protected Color getDisabledTextColor() {
								return Game.playerY;
							}
						});
						isFind=true;
						break;
					}
					else
						if(button[i].getText()=="O"&&button[i].getText()==button[i+6].getText()&&button[i+3].getText()=="") {
							button[i+3].setText(label);
							button[i+3].setEnabled(false);
							button[i+3].setUI(new MetalButtonUI() {
								protected Color getDisabledTextColor() {
									return Game.playerY;
								}
							});
							isFind=true;
							break;
						}
						else
							if(button[i+3].getText()=="O"&&button[i+3].getText()==button[i+6].getText()&&button[i].getText()=="") {
								button[i].setText(label);
								button[i].setEnabled(false);
								button[i].setUI(new MetalButtonUI() {
									protected Color getDisabledTextColor() {
										return Game.playerY;
									}
								});
								isFind=true;
								break;
							}
							else
								if(button[i].getText()=="X"&&button[i].getText()==button[i+3].getText()&&button[i+6].getText()=="") {
									button[i+6].setText(label);
									button[i+6].setEnabled(false);
									button[i+6].setUI(new MetalButtonUI() {
										protected Color getDisabledTextColor() {
											return Game.playerY;
										}
									});
									isFind=true;
									break;
								}
								else
									if(button[i].getText()=="X"&&button[i].getText()==button[i+6].getText()&&button[i+3].getText()=="") {
										button[i+3].setText(label);
										button[i+3].setEnabled(false);
										button[i+3].setUI(new MetalButtonUI() {
											protected Color getDisabledTextColor() {
												return Game.playerY;
											}
										});
										isFind=true;
										break;
									}
									else
										if(button[i+3].getText()=="X"&&button[i+3].getText()==button[i+6].getText()&&button[i].getText()=="") {
											button[i].setText(label);
											button[i].setEnabled(false);
											button[i].setUI(new MetalButtonUI() {
												protected Color getDisabledTextColor() {
													return Game.playerY;
												}
											});
											isFind=true;
											break;
										}
						i++;
				}//End of check columns
			if(isFind==false) {//Diagonal Check
				if(button[0].getText()=="O"&&button[0].getText()==button[4].getText()&&button[8].getText()=="") {
					button[8].setText(label);
					button[8].setEnabled(false);
					button[8].setUI(new MetalButtonUI() {
						protected Color getDisabledTextColor() {
							return Game.playerY;
						}
					});
					isFind=true;
				}
				else
					if(button[0].getText()=="O"&&button[0].getText()==button[8].getText()&&button[4].getText()=="") {
						button[4].setText(label);
						button[4].setEnabled(false);
						button[4].setUI(new MetalButtonUI() {
							protected Color getDisabledTextColor() {
								return Game.playerY;
							}
						});
						isFind=true;
					}
					else
						if(button[4].getText()=="O"&&button[4].getText()==button[8].getText()&&button[0].getText()=="") {
							button[0].setText(label);
							button[0].setEnabled(false);
							button[0].setUI(new MetalButtonUI() {
								protected Color getDisabledTextColor() {
									return Game.playerY;
								}
							});
							isFind=true;
						}
						else
							if(button[2].getText()=="O"&&button[2].getText()==button[4].getText()&&button[6].getText()=="") {
								button[6].setText(label);
								button[6].setEnabled(false);
								button[6].setUI(new MetalButtonUI() {
									protected Color getDisabledTextColor() {
										return Game.playerY;
									}
								});
								isFind=true;
							}
							else
								if(button[2].getText()=="O"&&button[2].getText()==button[6].getText()&&button[4].getText()=="") {
									button[4].setText(label);
									button[4].setEnabled(false);
									button[4].setUI(new MetalButtonUI() {
										protected Color getDisabledTextColor() {
											return Game.playerY;
										}
									});
									isFind=true;
								}
								else
									if(button[4].getText()=="O"&&button[4].getText()==button[6].getText()&&button[2].getText()=="") {
										button[2].setText(label);
										button[2].setEnabled(false);
										button[2].setUI(new MetalButtonUI() {
											protected Color getDisabledTextColor() {
												return Game.playerY;
											}
										});
										isFind=true;
									}
									else
										if(button[0].getText()=="X"&&button[0].getText()==button[4].getText()&&button[8].getText()=="") {
											button[8].setText(label);
											button[8].setEnabled(false);
											button[8].setUI(new MetalButtonUI() {
												protected Color getDisabledTextColor() {
													return Game.playerY;
												}
											});
											isFind=true;
										}
										else
											if(button[0].getText()=="X"&&button[0].getText()==button[8].getText()&&button[4].getText()=="") {
												button[4].setText(label);
												button[4].setEnabled(false);
												button[4].setUI(new MetalButtonUI() {
													protected Color getDisabledTextColor() {
														return Game.playerY;
													}
												});
												isFind=true;
											}
											else
												if(button[4].getText()=="X"&&button[4].getText()==button[8].getText()&&button[0].getText()=="") {
													button[0].setText(label);
													button[0].setEnabled(false);
													button[0].setUI(new MetalButtonUI() {
														protected Color getDisabledTextColor() {
															return Game.playerY;
														}
													});
													isFind=true;
												}
												else
													if(button[2].getText()=="X"&&button[2].getText()==button[4].getText()&&button[6].getText()=="") {
														button[6].setText(label);
														button[6].setEnabled(false);
														button[6].setUI(new MetalButtonUI() {
															protected Color getDisabledTextColor() {
																return Game.playerY;
															}
														});
														isFind=true;
													}
													else
														if(button[2].getText()=="X"&&button[2].getText()==button[6].getText()&&button[4].getText()=="") {
															button[4].setText(label);
															button[4].setEnabled(false);
															button[4].setUI(new MetalButtonUI() {
																protected Color getDisabledTextColor() {
																	return Game.playerY;
																}
															});
															isFind=true;
														}
														else
															if(button[4].getText()=="X"&&button[4].getText()==button[6].getText()&&button[2].getText()=="") {
																button[2].setText(label);
																button[2].setEnabled(false);
																button[2].setUI(new MetalButtonUI() {
																	protected Color getDisabledTextColor() {
																		return Game.playerY;
																	}
																});
																isFind=true;
															}
				}//End of diagonal check
			if(isFind==false)
				easyPC();//random put
			}
	}

	private void easyPC() {
		int i = random.nextInt(9);
		while(button[i].getText()!="")
			i = random.nextInt(9);
		button[i].setText(label);
		button[i].setEnabled(false);
		button[i].setUI(new MetalButtonUI() {
			protected Color getDisabledTextColor() {
				return Game.playerY;
			}
		});
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
				if(button[i].getText()=="X")
					UIManager.getDefaults().put("Button.disabledText",Game.playerX);
				else
					UIManager.getDefaults().put("Button.disabledText",Game.playerY);
				Change();
				check();
				if(gameEnd != "")
					for(int k=0;k<9;k++)
						button[k].setEnabled(false);
				if(gameEnd=="" && !gameOver()) {
					pcTurn();
					check();
					if(gameEnd != "")
						for(int j=0;j<9;j++)
							button[j].setEnabled(false);
					}
			}
	}

	@Override
	public void keyReleased(KeyEvent e) {}
}