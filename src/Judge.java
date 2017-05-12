import java.awt.Point;
import java.util.ArrayList;

public class Judge {
	final static int OMOK_SIZE = 15; // 열 =16 , 행=16
	static final int WIN = 6;

	int x_pos = 0;
	int y_pos = 0;

	static public ArrayList<Point> pointHistory = new ArrayList<Point>();

	int empty = 0;
	static int Position[][] = new int[OMOK_SIZE][OMOK_SIZE];
	int ai_player1_danger[][] = new int[OMOK_SIZE][OMOK_SIZE];// AI
	int ai_player2_danger[][] = new int[OMOK_SIZE][OMOK_SIZE];// AI

	static int turn = 0;
	int stone = 1;
	int other_stone = 2;

	Judge() {
		for (int i = 0; i < Position.length; i++) {
			for (int j = 0; j < Position[0].length; j++) {
				Position[i][j] = 0;
			}
		}
	}
	
	boolean check33(int x, int y) {
		// int num_dol = 0;
		int cnt = 0;
		int cell_x = x;
		int cell_y = y;

		if (Position[cell_x + 1][cell_y + 1] != 0 && Position[cell_x + 2][cell_y + 2] != 0) {
			cnt++;
		} //1번방향
		if (Position[cell_x - 1][cell_y - 1] != 0 && Position[cell_x - 2][cell_y - 2] != 0) {
			cnt++;
		} //2번방향
		if(Position[cell_x-1][cell_y+1] != 0 && Position[cell_x -2][cell_y+2] != 0){
			cnt++;
		} //3번방향
		if(Position[cell_x+1][cell_y-1] != 0 && Position[cell_x+2][cell_y-2] != 0){
			cnt++;
		} //4번방향
		if(Position[cell_x][cell_y+1] != 0 && Position[cell_x][cell_y+2] != 0){
			cnt++;
		} //5번방향
		if(Position[cell_x][cell_y-1] != 0 && Position[cell_x][cell_y-2] != 0){
			cnt ++;
		} //6번방향
		if(Position[cell_x-1][cell_y] != 0 && Position[cell_x-2][cell_y] != 0){
			cnt ++;
		} // 7번방향
		if(Position[cell_x+1][cell_y] != 0 && Position[cell_x+2][cell_y] != 0){
			cnt ++;
		}//8번방향
		
		if(cnt >= 2){
			Position[cell_x][cell_y]=0;
			GameDP.ta.append("cnt 는" + cnt + "33이다씹썍햐");
			GameDP.showDialog2();
		
			turn++;
			GameDP.setPlaytype(2);
			
			return false;
		}
		else{
			GameDP.ta.append("cnt 는" + cnt + "33아니에요");
			return true;
		}
	
	}

	int check(int x, int y) {
		int num_dol;
		// int num_pos = 0;
		int t_x, t_y;

		// int win = 0;
		// ew check 가로체크
		num_dol = 0;
		t_x = x;
		t_y = y;

		for (int i = t_y; i >= 0; i--) {
			if (Position[t_x][i] == Position[x][y]) {
				num_dol++;
			} else
				break;
		}
		t_x = x;
		t_y = y;

		for (int i = t_y; i < Position.length; i++) {
			if (Position[t_x][i] == Position[x][y]) {
				num_dol++;
			} else
				break;
		}
		if (num_dol >= 6)
			return WIN;

		// ns check 세로체크
		num_dol = 0;
		t_x = x;
		t_y = y;

		for (int i = t_x; i >= 0; i--) {
			if (Position[i][t_y] == Position[x][y]) {
				num_dol++;
			} else
				break;
		}
		t_x = x;
		t_y = y;

		for (int i = t_x; i < Position.length; i++) {
			if (Position[i][t_y] == Position[x][y]) {
				num_dol++;
			} else
				break;
		}
		if (num_dol >= 6)
			return WIN;

		// ne check /방향 체크

		num_dol = 0;
		t_x = x;
		t_y = y;

		for (int i = t_x; i >= 0; i--) {

			if (Position[i][t_y] == Position[x][y]) {
				num_dol++;
			} else
				break;
			t_y--;
			if (t_y < 0)
				break;

		}
		t_x = x;
		t_y = y;

		for (int i = t_x; i < Position.length; i++) {

			if (Position[i][t_y] == Position[x][y]) {
				num_dol++;
			} else
				break;
			t_y++;
			if (t_y >= Position.length)
				break;
		}
		if (num_dol >= 6)
			return WIN;

		// nw check \방향체크

		num_dol = 0;
		t_x = x;
		t_y = y;
		for (int i = t_y; i >= 0; i--) {

			if (Position[t_x++][i] == Position[x][y]) {
				num_dol++;
			} else
				break;
			// t_x--;
			if (t_x >= Position.length)
				break;

		}
		t_x = x;
		t_y = y;

		for (int i = t_y; i < Position.length; i++) {

			if (Position[t_x--][i] == Position[x][y]) {
				num_dol++;
			} else
				break;
			// t_x++;
			if (t_x < 0)
				break;
		}
		if (num_dol >= 6)
			return WIN;

		return num_dol;
	}

	boolean yourMove(int m, int n) {
		pointHistory.add(new Point(m, n)); // arry에 좌표를 넣어줌

		if ((m < 0) || (m > 14) || (n < 0) || (n > 14)) {
			return false;
		}

		if (Position[m][n] != 0)
			return false;

		switch (turn % 2) {
		case 0:     //클라가논둘
			turn++;
			Position[m][n] = 1;   //1로초기화하고
			break;
		case 1:
			turn++;
			Position[m][n] = 2;    //서버가놓은돌은2로초기화
			break;
		}

		return true;
	}

	public static void subLastStone() { //마지막 2개 지우는 함수
		Point lastLocation = pointHistory.remove(pointHistory.size() - 1);
		Point lastLocation2 = pointHistory.remove(pointHistory.size() - 1);

		System.out.println("lastL.x = "+lastLocation.getX() + "lastL.y = " +lastLocation.getY());
		System.out.println("lastL2.x = "+lastLocation2.getX() + "lastL2.y = " +lastLocation2.getY());
		
		Position[(int) lastLocation.getX()][(int) lastLocation.getY()] = 0;
		Position[(int) lastLocation2.getX()][(int) lastLocation2.getY()] = 0;
		
		
	}
	
	public static void rmvLastOne(){ // 마지막 1개 지우는 함수
		Point lastLocation1 = pointHistory.remove(pointHistory.size() - 1);
		Position[(int) lastLocation1.getX()][(int) lastLocation1.getY()] = 0;
	}
	
	
//----------------------------------------------------------------------------
	
	
	// AI = 혼자하기
	void AI() {

		int i, j;
		int max_danger_value = 0, max_attack_value = 0;
		int max_danger_x = 0, max_danger_y = 0;
		int max_attack_x = 0, max_attack_y = 0;

		for (i = 0; i < OMOK_SIZE; i++) {
			for (j = 0; j < OMOK_SIZE; j++) {
				ai_player1_danger[i][j] = 0;
				ai_player2_danger[i][j] = 0;
			}
		}

		for (i = 0; i < OMOK_SIZE; i++) {
			for (j = 0; j < OMOK_SIZE; j++) {
				if (Position[i][j] == stone) {
					if (j > 0)
						if (Position[i][j - 1] == empty)
							ai_player1_danger[i][j - 1]++;
					if (j < (OMOK_SIZE - 1))
						if (Position[i][j + 1] == empty)
							ai_player1_danger[i][j + 1]++;
					if (i > 0)
						if (Position[i - 1][j] == empty)
							ai_player1_danger[i - 1][j]++;
					if (i < (OMOK_SIZE - 1))
						if (Position[i + 1][j] == empty)
							ai_player1_danger[i + 1][j]++;
				}
			}
		}

		AI_Check(4, 10);
		AI_Check(3, 7);
		AI_Check(2, 5);
		Other_Check(4, 10);
		Other_Check(3, 7);
		Other_Check(2, 5);

		for (i = 0; i < OMOK_SIZE; i++) {
			for (j = 0; j < OMOK_SIZE; j++) {
				if (ai_player1_danger[i][j] > max_danger_value) {
					if (Position[i][j] == empty) {
						max_danger_value = ai_player1_danger[i][j];
						max_danger_x = j;
						max_danger_y = i;
					}
				}
			}
		}

		for (i = 0; i < OMOK_SIZE; i++) {
			for (j = 0; j < OMOK_SIZE; j++) {
				if (ai_player2_danger[i][j] > max_attack_value) {
					if (Position[i][j] == empty) {
						max_attack_value = ai_player2_danger[i][j];
						max_attack_x = j;
						max_attack_y = i;
					}
				}
			}
		}
		if (max_danger_value > max_attack_value) {
			x_pos = max_danger_x;
			y_pos = max_danger_y;
		} else {
			x_pos = max_attack_x;
			y_pos = max_attack_y;
		}
	}

	void Other_Check(int n_count, int danger) { // 상대방이 놓인 돌들을 체크하는 함수
		int i, j, k;
		int count = 0; // 돌 갯수 세는 변수
		int count2 = 0; // 양쪽에 모두 비어있는지 검사용

		// 가로검사
		for (i = 0; i < OMOK_SIZE; i++) {
			for (j = 0; j < OMOK_SIZE - n_count; j++) {
				for (k = j; k < j + n_count; k++) {
					if (Position[i][k] == other_stone) {
						count++;
					}
				}
				if (count >= n_count) {
					if (j > 0)
						ai_player2_danger[i][j - 1] += danger;
					if (j + (n_count - 1) < (OMOK_SIZE - 1))
						ai_player2_danger[i][j + (n_count - 1) + 1] += danger; // 위험값을
																				// 추가한다.

					if (Position[i][j - 1] == empty)
						count2++;
					if (Position[i][j + (n_count - 1) + 1] == empty)
						count2++;

					if (count2 >= 2) { // 만약 양쪽이 전부 비어있으면 위험값의 50% 추가
						if (j > 0)
							ai_player2_danger[i][j - 1] += danger / 2;
						if (j + (n_count - 1) < (OMOK_SIZE - 1))
							ai_player2_danger[i][j + (n_count - 1) + 1] += danger / 2;
					} else if (count2 >= 1) { // 만약 양쪽이 전부 비어있으면 위험값의 25% 추가
						if (j > 0)
							ai_player2_danger[i][j - 1] += danger / 4;
						if (j + (n_count - 1) < (OMOK_SIZE - 1))
							ai_player2_danger[i][j + (n_count - 1) + 1] += danger / 4;
					}
					count2 = 0;
				}
				if (n_count == 4 && count == 3) { // 4개를 체크했는데 3개가 있으면
					for (k = j; k < j + (n_count); k++) {
						if (Position[i][k] == empty && k > j && k < j + (n_count) - 1) {
							ai_player2_danger[i][k] += danger / 2; // 위험값의 50%를
																	// 추가
						}
					}
				}
				count = 0;
			}
		}
		count = 0;
		count2 = 0;

		// 세로검사
		for (i = 0; i < OMOK_SIZE - (n_count); i++) {
			for (j = 0; j < OMOK_SIZE; j++) {
				for (k = i; k < i + n_count; k++) {
					if (Position[k][j] == other_stone) {
						count++;
					}
				}
				if (count >= n_count) {

					if (i > 0)
						ai_player2_danger[i - 1][j] += danger;
					if (i + (n_count - 1) < (OMOK_SIZE - 1))
						ai_player2_danger[i + (n_count - 1) + 1][j] += danger; // 위험값을
																				// 추가한다.

					if (Position[i - 1][j] == empty)
						count2++;
					if (Position[i + (n_count - 1) + 1][j] == empty)
						count2++;

					if (count2 >= 2) { // 만약 양쪽이 전부 비어있으면 위험값의 50% 추가
						if (i > 0)
							ai_player2_danger[i - 1][j] += danger / 2;
						if (i + (n_count - 1) < (OMOK_SIZE - 1))
							ai_player2_danger[i + (n_count - 1) + 1][j] += danger / 2; // 위험값을
																						// 추가한다.
					} else if (count2 >= 1) { // 만약 양쪽이 전부 비어있으면 위험값의 25% 추가
						if (i > 0)
							ai_player2_danger[i - 1][j] += danger / 4;
						if (i + (n_count - 1) < (OMOK_SIZE - 1))
							ai_player2_danger[i + (n_count - 1) + 1][j] += danger / 4; // 위험값을
																						// 추가한다.
					}
					count2 = 0;
				}
				if (n_count == 4 && count == 3) { // 4개를 체크했는데 3개가 있으면
					for (k = j; k < i + (n_count); k++) {
						if (Position[k][j] == empty && k > i && k < i + (n_count) - 1) {
							ai_player2_danger[k][j] += danger / 2; // 위험값의 50%를
																	// 추가
						}
					}
				}
				count = 0;
			}
		}
		count = 0;
		count2 = 0;

		// \대각선 검사
		for (i = 0; i < OMOK_SIZE - (n_count); i++) {
			for (j = 0; j < OMOK_SIZE - (n_count); j++) {
				for (k = 0; k < n_count; k++) {
					if (Position[i + k][j + k] == other_stone) {
						count++;
					}
				}
				if (count >= n_count) {
					if (j > 0 && i > 0)
						ai_player2_danger[i - 1][j - 1] += danger;
					if (i + (n_count - 1) < (OMOK_SIZE - 1) && j + (n_count - 1) < 18)
						ai_player2_danger[i + (n_count - 1) + 1][j + (n_count - 1) + 1] += danger; // 위험값을
																									// 추가한다.

					if (Position[i - 1][j - 1] == empty)
						count2++;
					if (Position[i + (n_count - 1) + 1][j + (n_count - 1) + 1] == empty)
						count2++;

					if (count2 >= 2) { // 만약 양쪽이 전부 비어있으면 위험값의 50% 추가
						if (j > 0 && i > 0)
							ai_player2_danger[i - 1][j - 1] += danger / 2;
						if (i + (n_count - 1) < (OMOK_SIZE - 1) && j + (n_count - 1) < 18)
							ai_player2_danger[i + (n_count - 1) + 1][j + (n_count - 1) + 1] += danger / 2; // 위험값을
																											// 추가한다.
					} else if (count2 >= 1) { // 만약 양쪽이 전부 비어있으면 위험값의 25% 추가
						if (j > 0 && i > 0)
							ai_player2_danger[i - 1][j - 1] += danger / 4;
						if (i + (n_count - 1) < (OMOK_SIZE - 1) && j + (n_count - 1) < 18)
							ai_player2_danger[i + (n_count - 1) + 1][j + (n_count - 1) + 1] += danger / 4; // 위험값을
																											// 추가한다.
					}
					count2 = 0;
				}
				if (n_count == 4 && count == 3) { // 4개를 체크했는데 3개가 있으면
					for (k = j; k < (n_count); k++) {
						if (Position[i + k][j + k] == empty && k > 0 && k < (n_count) - 1) {
							ai_player2_danger[i + k][j + k] += danger / 2; // 위험값의
																			// 50%를
																			// 추가
						}
					}
				}
				count = 0;
			}
		}
		count = 0;
		count2 = 0;
		// /대각선 검사
		for (i = 0; i < OMOK_SIZE - (n_count); i++) {
			for (j = (n_count - 1); j < OMOK_SIZE; j++) {
				for (k = 0; k < n_count; k++) {
					if (Position[i + k][j - k] == other_stone) {
						count++;
					}
				}
				if (count >= n_count) {
					if ((j + 1) < (OMOK_SIZE - 1) && i > 0)
						ai_player2_danger[i - 1][j + 1] += danger;
					if (i + (n_count - 1) < (OMOK_SIZE - 1) && j - (n_count - 1) > 0)
						ai_player2_danger[i + (n_count - 1) + 1][j - (n_count - 1) + 1] += danger; // 위험값을
																									// 추가한다.

					if (Position[i - 1][j + 1] == empty)
						count2++;
					if (Position[i + (n_count - 1) + 1][j - (n_count - 1) + 1] == 0)
						count2++;

					if (count2 >= 2) { // 만약 양쪽이 전부 비어있으면 위험값의 50% 추가
						if ((j + 1) < (OMOK_SIZE - 1) && i > 0)
							ai_player2_danger[i - 1][j + 1] += danger / 2;
						if (i + (n_count - 1) < (OMOK_SIZE - 1) && j - (n_count - 1) > 0)
							ai_player2_danger[i + (n_count - 1) + 1][j - (n_count - 1) + 1] += danger / 2; // 위험값을
																											// 추가한다.
					} else if (count2 >= 1) { // 만약 양쪽이 전부 비어있으면 위험값의 25% 추가
						if ((j + 1) < (OMOK_SIZE - 1) && i > 0)
							ai_player2_danger[i - 1][j + 1] += danger / 4;
						if (i + (n_count - 1) < (OMOK_SIZE - 1) && j - (n_count - 1) > 0)
							ai_player2_danger[i + (n_count - 1) + 1][j - (n_count - 1) + 1] += danger / 4; // 위험값을
																											// 추가한다.
					}
					count2 = 0;
				}
				if (n_count == 4 && count == 3) { // 4개를 체크했는데 3개가 있으면
					for (k = 0; k < (n_count); k++) {
						if (Position[i + k][j - k] == empty && k > 0 && k < (n_count) - 1) {
							ai_player2_danger[i + k][j - k] += danger / 2; // 위험값의
																			// 50%를
																			// 추가
						}
					}
				}
				count = 0;
			}
		}
		count = 0;
		count2 = 0;
	}

	void AI_Check(int n_count, int danger) { // 상대방이 놓인 돌들을 체크하는 함수
		int i, j, k;
		int count = 0; // 돌 갯수 세는 변수
		int count2 = 0; // 양쪽에 모두 비어있는지 검사용

		// 가로검사
		for (i = 0; i < OMOK_SIZE; i++) {
			for (j = 0; j < OMOK_SIZE - n_count; j++) {
				for (k = j; k < j + n_count; k++) {
					if (Position[i][k] == stone) {
						count++;
					}
				}
				if (count >= n_count) {

					if (j > 0)
						ai_player1_danger[i][j - 1] += danger;
					if (j + (n_count - 1) < (OMOK_SIZE - 1))
						ai_player1_danger[i][j + (n_count - 1) + 1] += danger; // 위험값을
																				// 추가한다.

					if (Position[i][j - 1] == empty)
						count2++;
					if (Position[i][j + (n_count - 1) + 1] == empty)
						count2++;

					if (count2 >= 2) { // 만약 양쪽이 전부 비어있으면 위험값의 50% 추가
						if (j > 0)
							ai_player1_danger[i][j - 1] += danger / 2;
						if (j + (n_count - 1) < (OMOK_SIZE - 1))
							ai_player1_danger[i][j + (n_count - 1) + 1] += danger / 2;
					} else if (count2 >= 1) { // 만약 양쪽이 전부 비어있으면 위험값의 25% 추가
						if (j > 0)
							ai_player1_danger[i][j - 1] += danger / 4;
						if (j + (n_count - 1) < (OMOK_SIZE - 1))
							ai_player1_danger[i][j + (n_count - 1) + 1] += danger / 4;
					}
					count2 = 0;
				}
				if (n_count == 4 && count == 3) { // 4개를 체크했는데 3개가 있으면
					for (k = j; k < j + (n_count); k++) {
						if (Position[i][k] == empty && k > j && k < j + (n_count) - 1) {
							ai_player1_danger[i][k] += danger / 2; // 위험값의 50%를
																	// 추가
						}
					}
				}
				count = 0;
			}
		}
		count = 0;
		count2 = 0;

		// 세로검사
		for (i = 0; i < OMOK_SIZE - (n_count); i++) {
			for (j = 0; j < OMOK_SIZE; j++) {
				for (k = i; k < i + n_count; k++) {
					if (Position[k][j] == stone) {
						count++;
					}
				}
				if (count >= n_count) {

					if (i > 0)
						ai_player1_danger[i - 1][j] += danger;
					if (i + (n_count - 1) < (OMOK_SIZE - 1))
						ai_player1_danger[i + (n_count - 1) + 1][j] += danger; // 위험값을
																				// 추가한다.

					if (Position[i - 1][j] == empty)
						count2++;
					if (Position[i + (n_count - 1) + 1][j] == empty)
						count2++;

					if (count2 >= 2) { // 만약 양쪽이 전부 비어있으면 위험값의 50% 추가
						if (i > 0)
							ai_player1_danger[i - 1][j] += danger / 2;
						if (i + (n_count - 1) < (OMOK_SIZE - 1))
							ai_player1_danger[i + (n_count - 1) + 1][j] += danger / 2; // 위험값을
																						// 추가한다.
					} else if (count2 >= 1) { // 만약 양쪽이 전부 비어있으면 위험값의 25% 추가
						if (i > 0)
							ai_player1_danger[i - 1][j] += danger / 4;
						if (i + (n_count - 1) < (OMOK_SIZE - 1))
							ai_player1_danger[i + (n_count - 1) + 1][j] += danger / 4; // 위험값을
																						// 추가한다.
					}
					count2 = 0;
				}
				if (n_count == 4 && count == 3) { // 4개를 체크했는데 3개가 있으면
					for (k = j; k < i + (n_count); k++) {
						if (Position[k][j] == empty && k > i && k < i + (n_count) - 1) {
							ai_player1_danger[k][j] += danger / 2; // 위험값의 50%를
																	// 추가
						}
					}
				}
				count = 0;
			}
		}
		count = 0;
		count2 = 0;

		// \대각선 검사
		for (i = 0; i < OMOK_SIZE - (n_count); i++) {
			for (j = 0; j < OMOK_SIZE - (n_count); j++) {
				for (k = 0; k < n_count; k++) {
					if (Position[i + k][j + k] == stone) {
						count++;
					}
				}
				if (count >= n_count) {

					if (j > 0 && i > 0)
						ai_player1_danger[i - 1][j - 1] += danger;
					if (i + (n_count - 1) < (OMOK_SIZE - 1) && j + (n_count - 1) < 18)
						ai_player1_danger[i + (n_count - 1) + 1][j + (n_count - 1) + 1] += danger; // 위험값을
																									// 추가한다.

					if (Position[i - 1][j - 1] == empty)
						count2++;
					if (Position[i + (n_count - 1) + 1][j + (n_count - 1) + 1] == 0)
						count2++;

					if (count2 >= 2) { // 만약 양쪽이 전부 비어있으면 위험값의 50% 추가
						if (j > 0 && i > 0)
							ai_player1_danger[i - 1][j - 1] += danger / 2;
						if (i + (n_count - 1) < (OMOK_SIZE - 1) && j + (n_count - 1) < 18)
							ai_player1_danger[i + (n_count - 1) + 1][j + (n_count - 1) + 1] += danger / 2; // 위험값을
																											// 추가한다.
					} else if (count2 >= 1) { // 만약 양쪽이 전부 비어있으면 위험값의 25% 추가
						if (j > 0 && i > 0)
							ai_player1_danger[i - 1][j - 1] += danger / 4;
						if (i + (n_count - 1) < (OMOK_SIZE - 1) && j + (n_count - 1) < 18)
							ai_player1_danger[i + (n_count - 1) + 1][j + (n_count - 1) + 1] += danger / 4; // 위험값을
																											// 추가한다.
					}
					count2 = 0;
				}
				if (n_count == 4 && count == 3) { // 4개를 체크했는데 3개가 있으면
					for (k = j; k < (n_count); k++) {
						if (Position[i + k][j + k] == empty && k > 0 && k < (n_count) - 1) {
							ai_player1_danger[i + k][j + k] += danger / 2; // 위험값의
																			// 50%를
																			// 추가
						}
					}
				}
				count = 0;
			}
		}
		count = 0;
		count2 = 0;
		// /대각선 검사
		for (i = 0; i < OMOK_SIZE - (n_count); i++) {
			for (j = (n_count - 1); j < OMOK_SIZE; j++) {
				for (k = 0; k < n_count; k++) {
					if (Position[i + k][j - k] == stone) {
						count++;
					}
				}
				if (count >= n_count) {

					if ((j + 1) < (OMOK_SIZE - 1) && i > 0)
						ai_player1_danger[i - 1][j + 1] += danger;
					if (i + (n_count - 1) < (OMOK_SIZE - 1) && j - (n_count - 1) > 0)
						ai_player1_danger[i + (n_count - 1) + 1][j - (n_count - 1) + 1] += danger; // 위험값을
																									// 추가한다.

					if (Position[i - 1][j + 1] == empty)
						count2++;
					if (Position[i + (n_count - 1) + 1][j - (n_count - 1) + 1] == empty)
						count2++;

					if (count2 >= 2) { // 만약 양쪽이 전부 비어있으면 위험값의 50% 추가
						if ((j + 1) < (OMOK_SIZE - 1) && i > 0)
							ai_player1_danger[i - 1][j + 1] += danger / 2;
						if (i + (n_count - 1) < (OMOK_SIZE - 1) && j - (n_count - 1) > 0)
							ai_player1_danger[i + (n_count - 1) + 1][j - (n_count - 1) + 1] += danger / 2; // 위험값을
																											// 추가한다.
					} else if (count2 >= 1) { // 만약 양쪽이 전부 비어있으면 위험값의 25% 추가
						if ((j + 1) < (OMOK_SIZE - 1) && i > 0)
							ai_player1_danger[i - 1][j + 1] += danger / 4;
						if (i + (n_count - 1) < (OMOK_SIZE - 1) && j - (n_count - 1) > 0)
							ai_player1_danger[i + (n_count - 1) + 1][j - (n_count - 1) + 1] += danger / 4; // 위험값을
																											// 추가한다.
					}
					count2 = 0;
				}
				if (n_count == 4 && count == 3) { // 4개를 체크했는데 3개가 있으면
					for (k = 0; k < (n_count); k++) {
						if (Position[i + k][j - k] == empty && k > 0 && k < (n_count) - 1) {
							ai_player1_danger[i + k][j - k] += danger / 2; // 위험값의
																			// 50%를
																			// 추가
						}
					}
				}
				count = 0;
			}
		}
		count = 0;
		count2 = 0;
	}
}
