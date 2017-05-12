import java.awt.Frame;
import java.awt.Rectangle;

import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.UIManager;

public class OmokForm extends Frame {

	private static final long serialVersionUID = 1L;
	private JTextField InputIPAddress = null;
	private JButton join = null;
	private JButton wait = null;

	/**
	 * This method initializes InputIPAddress
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getInputIPAddress() {
		if (InputIPAddress == null) {
			InputIPAddress = new JTextField();
			InputIPAddress.setBounds(new Rectangle(10, 43, 233, 25));
			InputIPAddress.setText("127.0.0.1");
		}
		return InputIPAddress;
	}

	/**
	 * This method initializes join
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getJoin() {
		if (join == null) {
			join = new JButton();
			join.setBounds(new Rectangle(252, 40, 70, 30));
			join.setText("Join");
			join.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					GameDP gdp = new GameDP(InputIPAddress.getText()); // TODO
																		// Auto-generated
																		// Event
																		// stub
																		// actionPerformed()
					// gdp.init();
					// System.exit(0);
				}
			});
		}
		return join;
	}

	/**
	 * This method initializes wait
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getWait() {
		if (wait == null) {
			wait = new JButton();
			wait.setBounds(new Rectangle(326, 40, 70, 30));
			wait.setText("Wait");
			wait.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					GameDP gdp = new GameDP(); // TODO Auto-generated Event stub
												// actionPerformed()
					// gdp.init();
					// System.exit(0);
				}
			});
		}
		return wait;
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Frame f = new OmokForm();
		f.setVisible(true);
	}

	/**
	 * This is the default constructor
	 */
	public OmokForm() {
		super();
		try {
			UIManager.setLookAndFeel("com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel");
		} catch (Exception e) {
			e.printStackTrace();
		}

		initialize();

	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		this.setLayout(null);
		this.setSize(406, 87);
		this.setTitle("S.P 네트워크 오목 게임 ");

		this.add(getInputIPAddress(), null);
		this.add(getJoin(), null);
		this.add(getWait(), null);
		this.addWindowListener(new java.awt.event.WindowAdapter() {
			public void windowClosing(java.awt.event.WindowEvent e) {
				System.out.println("windowClosing()"); // TODO Auto-generated
														// Event stub
														// windowClosing()
				System.exit(0);
			}
		});
	}

} // @jve:decl-index=0:visual-constraint="12,7"
