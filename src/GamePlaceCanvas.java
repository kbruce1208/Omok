import java.awt.Canvas;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class GamePlaceCanvas extends Canvas implements MouseListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	static int PLAY_TYPE = 0;

	int w_margin = 50;
	int h_margin = 50;
	int image_size = 30;
	int turn = 0;

	Image image;
	Image white, black;
	Image ready;
	Image win, lose;
	Toolkit toolkit = getToolkit();
	Judge jg = null;

	static int effect = 0;

	public GamePlaceCanvas() {
		jg = new Judge();
		this.image = toolkit.getImage("./images/board.jpg");
		this.white = toolkit.getImage("./images/white_1.gif");
		this.black = toolkit.getImage("./images/black_1.gif");
		this.ready = toolkit.getImage("./images/ready.gif");
		this.win = toolkit.getImage("./images/win.gif");
		this.lose = toolkit.getImage("./images/lose.gif");

		addMouseListener(this);
	}

	public void setDefault() {
		jg = new Judge();
		Judge.turn = 0;
		repaint();
	}

	public void paint(Graphics g) {
		g.clearRect(0, 0, getWidth(), getHeight());
		if (image != null)
			g.drawImage(image, 0, 0, this);

		if (PLAY_TYPE == 1) {
			g.drawImage(ready, getWidth() / 2 - 75, getHeight() / 2 - 30, this);
			effect = 0;
		}
		if (effect == 1) {
			g.drawImage(win, getWidth() / 2 - 75, getHeight() / 2 - 30, this);
			PLAY_TYPE = 0;

		} else if (effect == -1) {
			g.drawImage(lose, getWidth() / 2 - 75, getHeight() / 2 - 30, this);
			PLAY_TYPE = 0;
		} else {
			for (int i = 0; i < jg.Position.length; i++) {
				for (int j = 0; j < jg.Position.length; j++) {

					if (jg.Position[i][j] == 1) {
						g.drawImage(black, (33 * j) + w_margin - (image_size / 2),
								(33 * i) + h_margin - (image_size / 2), this);
					} else if (jg.Position[i][j] == 2) {
						g.drawImage(white, (33 * j) + w_margin - (image_size / 2),
								(33 * i) + h_margin - (image_size / 2), this);
					}
				}
			}
		}
	}

	public void mouseClicked(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	public void mousePressed(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub

		int x = e.getX();
		int y = e.getY();
		// String stone = null;
		Dimension d = getSize();

		int c = ((x) * 15) / (d.width - 50) - 1;
		int r = ((y) * 15) / (d.height - 50) - 1;

		if ((PLAY_TYPE == 2 && (jg.turn % 2) == (GameDP.stone % 2))) {
			if (jg.yourMove(r, c)) {
				repaint();
				if(jg.check33(r,c)){
					repaint();
				}
				int i = jg.check(r, c);
				// jg.subLastStone();
				switch (i) {
				case Judge.WIN:
					if ((jg.turn) % 2 == (GameDP.stone % 2)) {
						effect = -1;
						GameDP.ta.append(">>>상대방이 승리하였습니다.\n");
						if (GameDP.IPAddress == null) {
							GameDP.ms.sendMessage("/effect " + effect);
						} else {
							GameDP.mc.sendMessage("/effect " + effect);
						}
					} else {
						effect = 1;
						GameDP.ta.append(">>>내가 승리하였습니다.\n");
						if (GameDP.IPAddress == null) {
							GameDP.ms.sendMessage("/effect " + effect);
						} else {
							GameDP.mc.sendMessage("/effect " + effect);
						}
					}
					break;

				default:
					if ((jg.turn) % 2 == 1) {
						GameDP.ta.append(">>>어피취가 둘 차례입니다\n");

					} else {
						GameDP.ta.append(">>>라이온이 둘 차례입니다\n");
					}
					if (GameDP.IPAddress == null) {
						GameDP.ms.sendMessage("/spot " + r + "," + c + "," + Judge.turn);
					} else {
						GameDP.mc.sendMessage("/spot " + r + "," + c + "," + Judge.turn);
					}
					break;
				}
				repaint();
			}
		
		} else if (PLAY_TYPE == 5) {
			effect = -1;
			GameDP.ta.append(">>>상대방이 승리하였습니다.\n");

			repaint();
		}

		else if (PLAY_TYPE == 4) {
			if (jg.yourMove(r, c)) {
				repaint();
			}
			jg.AI();
			if (jg.yourMove(jg.y_pos, jg.x_pos)) {
				repaint();
			}
		}

		// else if (PLAY_TYPE == 3) {
		//
		// jg.subLastStone();
		//
		// repaint();
		// }

	}

}
