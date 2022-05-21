//SystemA made by 1CES2205 JoKikou

package Othello1;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
 
import javax.swing.*;
 
public class Reversi extends JFrame implements ActionListener{
 
	JButton jbStart = new JButton("スタート");
	JButton jbStop = new JButton("オーバー");
	MyPanel panel = new MyPanel();
	Container c = this.getContentPane();
	
	int width = Toolkit.getDefaultToolkit().getScreenSize().width;
	int height = Toolkit.getDefaultToolkit().getScreenSize().height;
	boolean canPlay = false;
	boolean isBlack = false;
	int count = 0;
	int f = 1;
	int cntBlack = 0;
	int cntWhite = 0;
	int reversi[][] = new int[9][9];
	
	public Reversi() {
		Init();
		addListener();
	}
	
	public void Init() {
		this.setTitle("Reversi");
		this.setSize(500, 500);
		this.setResizable(false);
		this.setLayout(null);
		this.setVisible(true);
		this.setDefaultCloseOperation(this.EXIT_ON_CLOSE);
		this.setLocation((width - 500)/2, (height - 500)/2);
		jbStart.setBounds(135, 430, 100, 30);
		jbStop.setBounds(265, 430, 100, 30);
		panel.add(jbStart);
		panel.add(jbStop);
		
		panel.setLayout(null);
		panel.setSize(500, 500);
		c.add(panel);
	}
	
	public void addListener() {
		this.jbStart.addActionListener(this);
		this.jbStop.addActionListener(this);
		this.addMouseListener(new MouseListener() {
			
			public void mouseReleased(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			public void mousePressed(MouseEvent e) {
				// TODO Auto-generated method stub
				
				if(canPlay) {
					if(e.getX() >= 50 && e.getX() <= 450 && e.getY() >= 50 && e.getY() <= 450) {
						//System.out.println(e.getX() + "---" + e.getY());
						int x = e.getX();
						int y = e.getY();
						x = (x - 50) / 50;
						y = (y - 50) / 50;
//						System.out.println(x + " - " + y);
						if(reversi[x][y] == 0) {
							if(isBlack) {
								if(!isOK(x, y, 1))
									return;
								reversi[x][y] = 1;
								//System.out.println(x + "-" + y + reversi[x][y]);
								//System.out.println(reversi[4][3]);
								isEated(x, y);
								repaint();
								//System.out.println(reversi[4][3]);
								isBlack = false;
								if(Check(2)) {
									if(cntBlack > cntWhite)
										JOptionPane.showMessageDialog(null, "黒石の方が多い、黒石の勝ち");
									else if(cntBlack < cntWhite)
										JOptionPane.showMessageDialog(null, "白石の方が多い、白石の勝ち");
									else
										JOptionPane.showMessageDialog(null, "同点！");
								}
								System.out.println(cntBlack + "---" + cntWhite);
							}
							else {
								if(!isOK(x, y, 2))
									return;
								reversi[x][y] = 2;
								isEated(x, y);
								repaint();
								
								isBlack = true;
								if(Check(1)) {
									if(cntBlack > cntWhite)
										JOptionPane.showMessageDialog(null, "白石の方が多い、白石の勝ち");
									else if(cntBlack < cntWhite)
										JOptionPane.showMessageDialog(null, "黒石の方が多い、黒石の勝ち");
									else
										JOptionPane.showMessageDialog(null, "同点！");
								}
								System.out.println(cntBlack + "---" + cntWhite);
							}
						}
						
						
						repaint();
					}
				}
			}
			
			public void mouseExited(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			public void mouseEntered(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			public void mouseClicked(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
		});
	}
	
	public void actionPerformed(ActionEvent e) {
		
		if(e.getSource() == jbStart) {
			canPlay = true;
			jbStart.setEnabled(false);
			InitFrame();
			repaint();
		}
		if(e.getSource() == jbStop) {
			System.exit(0);
		}
	}
	
	public class MyPanel extends JPanel {
		public void paintComponent(Graphics scr){
			
			BufferedImage bi = new BufferedImage(500, 500, BufferedImage.TYPE_INT_ARGB);
			Graphics g = bi.createGraphics();
			
			g.setColor(Color.BLACK);
			
			for(int i = 1; i <= 9; i++) {
				g.drawLine(i*50, 20, i*50, 420);
			}
			for(int i = 1; i <= 9; i++) {
				g.drawLine(50, i*50-30, 450, i*50-30);
			}
			
			for(int i = 0; i <= 7; i++) {
				for(int j = 0; j <= 7; j++) {
					int x;
					int y;
					x = i * 50 + 50;
					y = j * 50 + 20;
					if(reversi[i][j] == 2) {
						g.fillOval(x, y, 50, 50);
					}
					else if(reversi[i][j] == 1) {
						g.setColor(Color.WHITE);
						g.fillOval(x, y, 50, 50);
						g.setColor(Color.BLACK);
						g.drawOval(x, y, 50, 50);
					}
				}
			}
			scr.drawImage(bi, 0, 0, this);
		}
	}
	
	public void isEated(int x, int y) {
		
		int color;
		
		if(isBlack)
			color = 1;
		else
			color = 2;
		
		int flag = 0;
		int tempx = x;
		int tempy = y;
		while(tempx > 0) { //左
			tempx--;
			if(reversi[tempx][tempy] == color) {
				for(int i = tempx + 1; i <= x; i++) {
					if(reversi[i][tempy] == 0)
						flag = 1;
				}
				if(flag == 1)
					break;
				for(int i = tempx + 1; i <= x; i++) {
					reversi[i][tempy] = color;
				}
				break;
			}
		}
		flag = 0;
		tempx = x;
		tempy = y;
		while(tempx < 7) { //右
			tempx++;
			if(reversi[tempx][tempy] == color) {
				for(int i = x + 1; i <= tempx; i++) {
					if(reversi[i][tempy] == 0)
						flag = 1;
				}
				if(flag == 1)
					break;
				for(int i = x + 1; i <= tempx; i++) {
					reversi[i][tempy] = color;
				}
				break;
			}
		}
		flag = 0;
		tempx = x;
		tempy = y;
		while(tempy > 0) { //上
			tempy--;
			if(reversi[tempx][tempy] == color) {
				for(int i = tempy + 1; i <= y; i++) {
					if(reversi[tempx][i] == 0) 
						flag = 1;
				}
				if(flag == 1)
					break;
				for(int i = tempy + 1; i <= y; i++) {
					reversi[tempx][i] = color;
				}
				break;
			}
		}
		flag = 0;
		tempx = x;
		tempy = y;
		while(tempy < 7) { //下
			tempy++;
			if(reversi[tempx][tempy] == color) {
				for(int i = y + 1; i <= tempy; i++) {
					if(reversi[tempx][i] == 0)
						flag = 1;
				}
				if(flag == 1)
					break;
				for(int i = y + 1; i <= tempy; i++) {
					reversi[tempx][i] = color;
				}
				break;
			}
		}
		flag = 0;
		tempx = x;
		tempy = y;
		while(tempx > 0 && tempy > 0) { //左上
			tempx--;
			tempy--;
			if(reversi[tempx][tempy] == color) {
				for(int i = tempx + 1, j  = tempy + 1; i <= x && j <= y; i++, j++) {
					if(reversi[i][j] == 0)
						flag = 1;
				}
				if(flag == 1)
					break;
				for(int i = tempx + 1, j  = tempy + 1; i <= x && j <= y; i++, j++) {
					reversi[i][j] = color;
				}
				break;
			}
		}
		flag = 0;
		tempx = x;
		tempy = y;
		while(tempx > 0 && tempy < 7) { //左下
			tempx--;
			tempy++;
			if(reversi[tempx][tempy] == color) {
				for(int i = x - 1, j = y + 1; j < tempy; i--, j++) {
					if(reversi[i][j] == 0)
						flag = 1;
				}
				if(flag == 1)
					break;
				for(int i = x - 1, j = y + 1; j < tempy; i--, j++) {
					reversi[i][j] = color;
				}
				break;
			}
		}
		flag = 0;
		tempx = x;
		tempy = y;
		while(tempx < 7 && tempy > 0) { //右上
			tempx++;
			tempy--;
			if(reversi[tempx][tempy] == color) {
				//System.out.println("11");
				for(int i = x + 1, j  = y - 1; i < tempx; i++, j--) {
					//System.out.println(i + "--" + j + reversi[i][j]);
					if(reversi[i][j] == 0) {
						flag = 1;
						System.out.println(i + "----" + j + reversi[i][j]);
					}
				}
				if(flag == 1)
					break;
				for(int i = x + 1, j  = y - 1; i < tempx; i++, j--) {
					reversi[i][j] = color;
				}
				break;
			}
		}
		flag = 0;
		tempx = x;
		tempy = y;
		while(tempx < 7 && tempy < 7) { //右下
			tempx++;
			tempy++;
			if(reversi[tempx][tempy] == color) {
				for(int i = x + 1, j  = y + 1; i <= tempx && j <= tempy; i++, j++) {
					if(reversi[i][j] == 0)
						flag = 1;
				}
				if(flag == 1)
					break;
				for(int i = x + 1, j  = y + 1; i <= tempx && j <= tempy; i++, j++) {
					reversi[i][j] = color;
				}
				break;
			}
		}
	}
	
public boolean isOK(int x, int y, int color) {
		
		f = 0;
		int flag = 0;
		int tempx = x;
		int tempy = y;
		while(tempx > 0) { //左
			tempx--;
			if(reversi[tempx][tempy] == color) {
				for(int i = tempx + 1; i < x; i++) {
					if(reversi[i][tempy] == 0)
						flag = 1;
				}
				if(flag == 1)
					break;
				for(int i = tempx + 1; i < x; i++) {
					//reversi[i][tempy] = color;
					f = 1;
				}
				
				break;
			}
		}
		flag = 0;
		tempx = x;
		tempy = y;
		while(tempx < 7) { //右
			tempx++;
			if(reversi[tempx][tempy] == color) {
				for(int i = x + 1; i < tempx; i++) {
					if(reversi[i][tempy] == 0)
						flag = 1;
				}
				if(flag == 1)
					break;
				for(int i = x + 1; i < tempx; i++) {
					//reversi[i][tempy] = color;
					f = 1;
				}
				break;
			}
		}
		flag = 0;
		tempx = x;
		tempy = y;
		while(tempy > 0) { //上
			tempy--;
			if(reversi[tempx][tempy] == color) {
				for(int i = tempy + 1; i < y; i++) {
					if(reversi[tempx][i] == 0) 
						flag = 1;
				}
				if(flag == 1)
					break;
				for(int i = tempy + 1; i < y; i++) {
					//reversi[tempx][i] = color;
					f = 1;
				}
				break;
			}
		}
		flag = 0;
		tempx = x;
		tempy = y;
		while(tempy < 7) { //下
			tempy++;
			if(reversi[tempx][tempy] == color) {
				for(int i = y + 1; i < tempy; i++) {
					if(reversi[tempx][i] == 0)
						flag = 1;
				}
				if(flag == 1)
					break;
				for(int i = y + 1; i < tempy; i++) {
					//reversi[tempx][i] = color;
					f = 1;
				}
				break;
			}
		}
		flag = 0;
		tempx = x;
		tempy = y;
		while(tempx > 0 && tempy > 0) { //左上
			tempx--;
			tempy--;
			if(reversi[tempx][tempy] == color) {
				for(int i = tempx + 1, j  = tempy + 1; i < x && j < y; i++, j++) {
					if(reversi[i][j] == 0)
						flag = 1;
				}
				if(flag == 1)
					break;
				for(int i = tempx + 1, j  = tempy + 1; i < x && j < y; i++, j++) {
					//reversi[i][j] = color;
					f = 1;
				}
				
				break;
			}
		}
		flag = 0;
		tempx = x;
		tempy = y;
		while(tempx > 0 && tempy < 7) { //左下
			tempx--;
			tempy++;
			if(reversi[tempx][tempy] == color) {
				for(int i = x - 1, j = y + 1; j < tempy; i--, j++) {
					if(reversi[i][j] == 0)
						flag = 1;
				}
				if(flag == 1)
					break;
				for(int i = x - 1, j = y + 1; j < tempy; i--, j++) {
				//	reversi[i][j] = color;
					f = 1;
				}
				
				break;
			}
		}
		flag = 0;
		tempx = x;
		tempy = y;
		while(tempx < 7 && tempy > 0) { //右上
			tempx++;
			tempy--;
			if(reversi[tempx][tempy] == color) {
				//System.out.println("11");
				for(int i = x + 1, j  = y - 1; i < tempx; i++, j--) {
					//System.out.println(i + "--" + j + reversi[i][j]);
					if(reversi[i][j] == 0) {
						flag = 1;
						//System.out.println(i + "----" + j + reversi[i][j]);
					}
				}
				if(flag == 1)
					break;
				for(int i = x + 1, j  = y - 1; i < tempx; i++, j--) {
					//reversi[i][j] = color;
					f = 1;
				}
				
				break;
			}
		}
		flag = 0;
		tempx = x;
		tempy = y;
		while(tempx < 7 && tempy < 7) { //右下
			tempx++;
			tempy++;
			if(reversi[tempx][tempy] == color) {
				for(int i = x + 1, j  = y + 1; i < tempx && j < tempy; i++, j++) {
					if(reversi[i][j] == 0)
						flag = 1;
				}
				if(flag == 1)
					break;
				for(int i = x + 1, j  = y + 1; i < tempx && j < tempy; i++, j++) {
					//reversi[i][j] = color;
					f = 1;
				}
				
				break;
			}
		}
		if(f == 1)
			return true;
		else
			return false;
	}
	
	public boolean Check(int color) {
		
//		for(int i = 0; i <= 7; i++) {
//			for(int j = 0; j <= 7; j++) {
//				if(reversi[i][j] == color) 
//					return false;
//				else if(reversi[i][j] == color % 2 + 1)
//					count++;
//			}
//		}
//		return true;
		cntBlack = 0;
		cntWhite = 0;
		int cnt = 0;
		for(int i = 0; i <= 7; i++) {
			for(int j = 0; j <= 7; j++) {
				
				
				if(reversi[i][j] == 1) {
					cntBlack++;
				}
				else if(reversi[i][j] == 2) {
					cntWhite++;
				}
				else {
					cnt++;
				}
			}
		}
		
		for(int i = 0; i <= 7; i++) {
			for(int j = 0; j <= 7; j++) {
				if(reversi[i][j] == 0 && isOK(i, j, color))
					return false;
			}
		}
		JOptionPane.showMessageDialog(null, color == 1?"黒石が打てる場所がないので、白石に変わる":"白石が打てる場所がないので、黒石に変わる");
		color = color % 2 + 1;
		
		for(int i = 0; i <= 7; i++) {
			for(int j = 0; j <= 7; j++) {
				if(reversi[i][j] == 0 && isOK(i, j, color)) {
					if(color == 1) {
						isBlack = true;
					}
					else {
						isBlack = false;
					}
					return false;
				}
					
			}
		}
		JOptionPane.showMessageDialog(null, "打てる場所がないので、ゲーム終了");
		return true;
	}
	
	public void InitFrame() {
		reversi[3][4] = reversi[4][3] = 2;
		reversi[3][3] = reversi[4][4] = 1;
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		new Reversi();
	}
}