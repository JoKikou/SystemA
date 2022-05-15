import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class GameFrame extends JFrame implements ActionListener{
	
	private JButton[][] boards = new JButton[3][3]; //���У���
	private int flag = 0;//flag=0�ϡ���flag=1�ϩ�
	private int [][]place = new int [3][3];//���Έ��Ϥ�place[i][j]=1,���Έ��Ϥ�-1
	
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
			JOptionPane.showMessageDialog(this,"�����Ϥ⤦���ʤ���������λ�ä��x��Ǥ�������");
			return;
			
		}
		
		play(clickBtn,i,j);
		
		//��ؓ���ж�
		int judge = judge();
		if(judge == 1) {
			JOptionPane.showMessageDialog(this,"���΄٤�");
		}else if(judge == -1) {
			JOptionPane.showMessageDialog(this,"���΄٤�");
		}else if(judge == 0) {
			JOptionPane.showMessageDialog(this,"ͬ��");
		}
	}
	
	private int judge() {
		/**
		 * 1 -1 0
		 * -1 1 0
		 * 0 0 1
		 * 
		 */
		
		//��Έ��Ϥ΄٤�
		int first = place[0][0]+place[0][1]+place[0][2];
		int second = place[1][0]+place[1][1]+place[1][2];
		int third = place[2][0]+place[2][1]+place[2][2];
		
		//�k�Έ��Ϥ΄٤�
		int forth = place[0][0]+place[1][0]+place[2][0];
		int fifth = place[0][1]+place[1][1]+place[2][1];
		int sixth = place[0][2]+place[1][2]+place[2][2];
		
		//б��Έ��Ϥ΄٤�
		int seventh = place[0][0]+place[1][1]+place[2][2];
		int eighth = place[0][2]+place[1][1]+place[2][0];
		
		if(first == 3||second == 3||third == 3||forth == 3||fifth == 3||sixth == 3||seventh == 3||eighth == 3) {
			return 1;//���΄٤�
		}else if(first == -3||second == -3||third == -3||forth == -3||fifth == -3||sixth == -3||seventh == -3||eighth == -3) {
			return -1;//���΄٤�
		}
		
		for(int i=0;i<3;i++) {
			for(int j=0;j<3;j++) {
				if(place[i][j] == 0) {
					return -2;//��ؤΥ��`���A���Ƥ���
				}
			}
		}
		
		return 0;//ͬ��
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
    
	//����å���λ�ä�_������
	public int[] location(JButton clickBtn) {
		int[] location = new int [2];//location[0]��i location[1]��j
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
