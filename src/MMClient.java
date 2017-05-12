import java.awt.Point;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class MMClient implements Runnable {

	Socket xSocket;
	int port = 30000;
	String strUserName = "라이온";

	PrintWriter streamOut = null;
	BufferedReader streamIn = null;

	Thread xThreadInput = null;
	boolean qStop = false;
	InputStream is;
	OutputStream os;
	DataInputStream dis;
	
	DataOutputStream dos;

	public void connect(String server) {
		try {
			xSocket = new Socket(server, port);

			is = xSocket.getInputStream();
			dis = new DataInputStream(is);
			os = xSocket.getOutputStream();
			dos = new DataOutputStream(os);
			// streamOut = new PrintWriter(xSocket.getOutputStream(), true);
			// streamIn = new BufferedReader(new
			// InputStreamReader(xSocket.getInputStream()));

			String response = null;

			sendMessage("%C% DO TYERM MMClientv0.1");
			byte b[] = new byte[128];
			dis.read(b);

			response = new String(b);
			response = response.trim();
			// response = streamIn.readLine();

			xThreadInput = new Thread(this);
			xThreadInput.start();

		} catch (Exception ex) {
			System.err.println("연결 에러입니다.");
			System.exit(1);
		}
	}

	public void run() {
		try {
			while (qStop == false) {
				// addMessage(streamIn.readLine());
				byte[] b = new byte[128];
				dis.read(b, 0, 128);
				String msg = new String(b); // + "\n";
				msg = msg.trim();

				addMessage(msg);
			}
		} catch (IOException ex) {
		}
	}

	public synchronized void sendMessage(String msg) {
		// streamOut.println(msg);
		try {
			byte[] bb;
			String s = String.format("%-128s", msg);
			bb = s.getBytes(); // ksc5601
			dos.write(bb, 0, 128); // .writeUTF(str);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public synchronized void addMessage(String msg) {
//		 GameDP.ta.append(msg); //send보는 센텐스
		if (msg.substring(0, 1).equals("/")) {
			if (msg.equals("/play")) {
				GamePlaceCanvas.PLAY_TYPE = 2;
				GameDP.gamePlaceCanvas.repaint();
				msg = "게임을 시작합니다.";
			} else if (msg.equals("/giveup")) {
				GameDP.gamePlaceCanvas.effect = 1;
				GamePlaceCanvas.PLAY_TYPE = 5;
				GameDP.gamePlaceCanvas.repaint();
				GameDP.ta.append(">>>상대방이 기권하였습니다.\n");
				msg = null;
			} else if (msg.equals("/back")) {
//				Judge.subLastStone();
				GameDP.gamePlaceCanvas.repaint();
				GameDP.ta.append(">>>상대방이 무르기를 신청하였습니다.\n");
				int n = GameDP.showDialog();
				if(n==0){
					GameDP.send_value("/back_ok");
					Judge.subLastStone();
				}
				else
					GameDP.send_value("/back_no");
				msg = null;
				return;
			} 
			
			else if (msg.equals("/back_ok")){
				Judge.subLastStone();
				GameDP.gamePlaceCanvas.repaint();
				GameDP.ta.append(">>>상대방이 무르기를 허용 하였습니다.\n");
				msg = null;
			}
			
			else if (msg.equals("/back_no")){
				GameDP.ta.append(">>>상대방이 무르기를 거절 하였습니다.\n");
				msg = null;
				return;
			}
			
			
			else if (msg.substring(0, 5).equals("/spot")) { // 상대방의 좌표를 받아옴.
				String point;
				String[] arrPoint;
				point = msg.substring(6, msg.length());
				arrPoint = point.split(",");
				int r = Integer.parseInt(arrPoint[0]);
				int c = Integer.parseInt(arrPoint[1]);
				int t = Integer.parseInt(arrPoint[2]);
				Judge.turn = t;
				if (Judge.turn % 2 == 1) {
					Judge.Position[r][c] = 1; // 클라가 둔거
					Judge.pointHistory.add(new Point(r, c));
					GameDP.ta.append(">>>어피취가 둘 차례입니다\n");
					GameDP.gamePlaceCanvas.repaint();
				} else {
					Judge.Position[r][c] = 2; // 서버가 둔거
					Judge.pointHistory.add(new Point(r, c));
					GameDP.ta.append(">>>라이온이 둘 차례입니다\n");
					GameDP.gamePlaceCanvas.repaint();
				}
				GameDP.gamePlaceCanvas.repaint();
				msg = null;

			} else if (msg.substring(0, 7).equals("/effect")) {
				String point;
				String[] arrPoint;
				point = msg.substring(8, msg.length());
				if (Integer.parseInt(point) == 1) {
					GameDP.gamePlaceCanvas.effect = -1;
				} else if (Integer.parseInt(point) == -1) {
					GameDP.gamePlaceCanvas.effect = 1;
				}
				GameDP.gamePlaceCanvas.repaint();
				msg = null;
			}
		}
		if (msg != null) {
			GameDP.ta.append("[어피취]>>" + msg + "\n");
		}
	}

}
