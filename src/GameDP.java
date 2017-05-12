import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class GameDP {

	static JFrame frame = null;
	static String IPAddress = null;
	public String Type = null;
	static GamePlaceCanvas gamePlaceCanvas = null;
	private ImageIcon[] ic = new ImageIcon[6];
	private JButton[] bt = new JButton[6];
	static int stone = 0;

	static MMClient mc = null;
	static MMServer ms = null;

	ImagePanel ipanel1 = new ImagePanel("./images/bar_01.gif");
	ImagePanel ipanel2 = new ImagePanel("./images/bar_02.gif");

	GameDP() {
		stone = 1;
		ic[0] = new ImageIcon("./images/button_01.gif"); // ����ϱ�
		ic[1] = new ImageIcon("./images/button_06.gif"); // ȥ���ϱ�
		ic[2] = new ImageIcon("./images/button_05.gif"); // ������
		ic[3] = new ImageIcon("./images/button_02.gif"); // �����ϱ�
		ic[4] = new ImageIcon("./images/button_03.gif"); // ����ϱ�
		ic[5] = new ImageIcon("./images/button_04.gif"); // �����ϱ�
		frame = new JFrame("Omok Network Game v2.0 <Server>");

		ms = new MMServer();
		ms.connect();

		init();
	}
	public static int showDialog(){
		int n = JOptionPane.showConfirmDialog(frame, "�����ðڽ��ϱ�?", "Confirm",JOptionPane.YES_NO_OPTION);
		return n;
	}
	
	public static void showDialog2(){
		 JOptionPane.showMessageDialog(frame, "33�̴� ��@�߾�");
		
	}

	GameDP(String IPAddress) {
		stone = 2;
		ic[0] = new ImageIcon("./images/button_01.gif"); // ����ϱ�
		ic[1] = new ImageIcon("./images/button_06.gif"); // ȥ���ϱ�
		ic[2] = new ImageIcon("./images/button_05.gif"); // ������
		ic[3] = new ImageIcon("./images/button_02.gif"); // �����ϱ�
		ic[4] = new ImageIcon("./images/button_03.gif"); // ����ϱ�
		ic[5] = new ImageIcon("./images/button_04.gif"); // �����ϱ�
		frame = new JFrame("Omok Network Game v2.0 <Client>");

		this.IPAddress = IPAddress;
		mc = new MMClient();
		mc.connect(IPAddress);
		init();
	}

	private void init() {
		frame.setLayout(null);
		frame.setResizable(false);
		frame.setPreferredSize(new Dimension(800, 600));
		frame.setLocation(50, 50);
		frame.add(getGamePlaceCanvas());
		frame.add(getControlPanel());
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.pack();
		frame.setVisible(true);
	}

	private GamePlaceCanvas getGamePlaceCanvas() {
		if (gamePlaceCanvas == null) {
			gamePlaceCanvas = new GamePlaceCanvas();
			gamePlaceCanvas.setBounds(0, 0, 550, 550);
		}
		return gamePlaceCanvas;
	}

	private JPanel getControlPanel() {
		ipanel2.setName("������");
		ipanel1.setName("���̿�");
		if (controlPanel == null) {
			controlPanel = new JPanel();
			controlPanel.setLayout(null);
			controlPanel.setSize(new Dimension(250, 600));
			controlPanel.setBounds(550, 0, 250, 550);
			JScrollPane sp = new JScrollPane(getTa());
			sp.setBounds(10, 220, 230, 180);
			ipanel1.setBounds(10, 110, 230, 100);
			ipanel2.setBounds(10, 10, 230, 100);

			controlPanel.add(ipanel1);
			controlPanel.add(ipanel2);

			controlPanel.add(sp);
			controlPanel.add(getTf(), null);
			// controlPanel.add(label1);
			getBt();
			for (int i = 0; i < bt.length; i++) {
				if (i == 0 && IPAddress == null) {
				} // server(������)
				else if (i == 3 && IPAddress != null) {
				} // client(���̿�)
					// else if(i == 2) {} //���쳻
				else {
					controlPanel.add(bt[i], null);
				}
			}
		}
		return controlPanel;
	}

	private JPanel controlPanel = null; // @jve:decl-index=0:visual-constraint="19,5"
	static JTextArea ta = null;
	static JTextField tf = null;
	// private JButton[] bt = new JButton[6];

	private JTextArea getTa() {
		if (ta == null) {
			ta = new JTextArea(8, 0);
			ta.setEditable(false);
			ta.setLineWrap(true);
			ta.setWrapStyleWord(true);
		}
		return ta;
	}

	/**
	 * This method initializes tf
	 * 
	 * @return javax.swing.JTextField
	 */
	ActionListener xInputListener = new ActionListener() {
		public void actionPerformed(ActionEvent ev) {
			if (IPAddress != null) {
				if (mc.is != null) {
					mc.sendMessage(tf.getText());
					ta.append("<<[���̿�]" + tf.getText() + "\n");
				}
			} else {
				if (ms.os != null) {
					ms.sendMessage(tf.getText());
					ta.append("<<[������]" + tf.getText() + "\n");
				}
			}
			tf.setText("");
		}
	};

	private JTextField getTf() {
		if (tf == null) {
			tf = new JTextField();
			tf.setBounds(new Rectangle(10, 405, 230, 30));
			tf.addActionListener(xInputListener);
		}
		return tf;
	}

	/**
	 * This method initializes bt1
	 * 
	 * @return javax.swing.JButton
	 */
	private void getBt() {
		if (bt[0] == null) {
			bt[0] = new JButton();
			bt[0].setIcon(ic[0]);
			bt[0].setBounds(new Rectangle(10, 450, 70, 30));
		}
		if (bt[1] == null) {
			bt[1] = new JButton();
			bt[1].setIcon(ic[1]);
			bt[1].setBounds(new Rectangle(90, 450, 70, 30));
		}
		if (bt[2] == null) {
			bt[2] = new JButton();
			bt[2].setIcon(ic[2]);
			bt[2].setBounds(new Rectangle(170, 450, 70, 30));
		}
		if (bt[3] == null) {
			bt[3] = new JButton();
			bt[3].setIcon(ic[3]);
			bt[3].setBounds(new Rectangle(10, 490, 70, 30));
		}
		if (bt[4] == null) {
			bt[4] = new JButton();
			bt[4].setIcon(ic[4]);
			bt[4].setBounds(new Rectangle(90, 490, 70, 30));
		}
		if (bt[5] == null) {
			bt[5] = new JButton();
			bt[5].setIcon(ic[5]);
			bt[5].setBounds(new Rectangle(170, 490, 70, 30));
		}

		bt[0].addActionListener(new Button_Wait());
		bt[1].addActionListener(new Button_Alone());
		bt[2].addActionListener(new Button_Back());
		bt[3].addActionListener(new Button_Start());
		bt[4].addActionListener(new Button_GiveUP());
		bt[5].addActionListener(new Button_End());
	}

	public static void send_value(String str) {
		if (IPAddress != null) {

			mc.sendMessage(str);

		} else {

			ms.sendMessage(str);

		}
	}

	public static void setPlaytype(int val) {
		GamePlaceCanvas.PLAY_TYPE = val;
		gamePlaceCanvas.repaint();
	}

	public int getPlaytype() {
		return GamePlaceCanvas.PLAY_TYPE;
	}

	class Button_Wait implements ActionListener {
		public void actionPerformed(ActionEvent ae) {
			GameDP.gamePlaceCanvas.setDefault();
			setPlaytype(1);
			send_value("/ready");
			ta.append(">>> ���濡�� ��������� �˸��ϴ�.\n");
		}
	}

	class Button_Start implements ActionListener {
		public void actionPerformed(ActionEvent ae) {
			if (getPlaytype() != 1) {
				ta.append(">>> ������ ����� �ƴմϴ�.");
			} else {
				GameDP.gamePlaceCanvas.setDefault();
				setPlaytype(2);
				send_value("/play");
				ta.append(">>> ������ �����մϴ�.\n");
				gamePlaceCanvas.setDefault();
			}
		}
	}

	class Button_GiveUP implements ActionListener {
		public void actionPerformed(ActionEvent ae) {

			setPlaytype(5);
			gamePlaceCanvas.effect = -1;
			gamePlaceCanvas.repaint();
			send_value("/giveup");
			ta.append(">>> ����ϼ̽��ϴ�.\n");
			gamePlaceCanvas.setDefault();
		}
	}

	class Button_End implements ActionListener {
		public void actionPerformed(ActionEvent ae) {
			System.exit(0);
		}
	}

	class Button_Back implements ActionListener {
		public void actionPerformed(ActionEvent ae) {
			// GamePlaceCanvas.PLAY_TYPE=3;
//			Judge.subLastStone();

				if (GameDP.stone % 2 == Judge.turn % 2) {
					send_value("/back");
					setPlaytype(2);
					send_value("/play");
					gamePlaceCanvas.repaint();
			}
		}
	}

	class Button_Alone implements ActionListener {
		public void actionPerformed(ActionEvent ae) {
			if (getPlaytype() > 0) {
				ta.append("���� ���� ���̹Ƿ� ȥ�ڼ� ������ �Ұ��մϴ�.");
			} else {
				setPlaytype(4);
				ta.append(">> ȥ�ڼ� ������ �����մϴ�.\n");
			}
			gamePlaceCanvas.setDefault();
		}
	}

}
