import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class GameFrame extends JFrame implements ActionListener{
	
	private JButton[][] boards = new JButton[3][3]; //３行３列
	private int flag = 0;//flag=0は×　flag=1は〇
	private int [][]place = new int [3][3];//×の場合はplace[i][j]=1,〇の場合は-1
	
    public GameFrame(){
    	setBounds(100,100,600,600);
    	setLayout(new GridLayout(3,3));
    	for (int i=0;i<3;i++) {
    		for(int j=0;j<3;j++) {
    			boards[i][j] = new JButton();
    			add(boards[i][j]);
    			boards[i][j].addActionListener(this);
    			
    		}
    	}
    	setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    	setVisible(true);
    }
	
	public void actionPerformed(ActionEvent e) {
		JButton clickBtn = (JButton) e.getSource();
		int[]places = location(clickBtn);
		int i = places[0];
		int j = places[1];
		
		if (place[i][j] == 1 || place[i][j] == -1) {
			JOptionPane.showMessageDialog(this,"ここはもう入れないから他の位置を選んでください");
			return;
			
		}
		
		play(clickBtn,i,j);
		
		//勝負の判断
		int judge = judge();
		if(judge == 1) {
			JOptionPane.showMessageDialog(this,"×の勝ち");
		}else if(judge == -1) {
			JOptionPane.showMessageDialog(this,"〇の勝ち");
		}else if(judge == 0) {
			JOptionPane.showMessageDialog(this,"同点");
		}
	}
	
	private int judge() {
		/**
		 * 1 -1 0
		 * -1 1 0
		 * 0 0 1
		 * 
		 */
		
		//横の場合の勝ち
		int first = place[0][0]+place[0][1]+place[0][2];
		int second = place[1][0]+place[1][1]+place[1][2];
		int third = place[2][0]+place[2][1]+place[2][2];
		
		//縦の場合の勝ち
		int forth = place[0][0]+place[1][0]+place[2][0];
		int fifth = place[0][1]+place[1][1]+place[2][1];
		int sixth = place[0][2]+place[1][2]+place[2][2];
		
		//斜めの場合の勝ち
		int seventh = place[0][0]+place[1][1]+place[2][2];
		int eighth = place[0][2]+place[1][1]+place[2][0];
		
		if(first == 3||second == 3||third == 3||forth == 3||fifth == 3||sixth == 3||seventh == 3||eighth == 3) {
			return 1;//×の勝ち
		}else if(first == -3||second == -3||third == -3||forth == -3||fifth == -3||sixth == -3||seventh == -3||eighth == -3) {
			return -1;//〇の勝ち
		}
		
		for(int i=0;i<3;i++) {
			for(int j=0;j<3;j++) {
				if(place[i][j] == 0) {
					return -2;//今回のゲームを続けていく
				}
			}
		}
		
		return 0;//同点
	}
	
	private void play(JButton clickBtn,int i,int j) {
		if (flag == 0) {
			clickBtn.setText("X");
			flag = 1;
			place[i][j]=1;
		} else if (flag == 1) {
			clickBtn.setText("O");
			flag = 0;
			place[i][j]=-1;
		}
		
	}
    
	//クリックの位置を確定する
	public int[] location(JButton clickBtn) {
		int[] location = new int [2];//location[0]はi location[1]はj
		for(int i=0;i<3;i++) {
			for (int j=0;j<3;j++) {
				if (boards[i][j] == clickBtn) {
					location[0] = i;
					location[1] = j;
					return location;
				}
			}
		}
		return location;
		
	}
	
	public static void main(String[] args) {
		new GameFrame();
	}
}
