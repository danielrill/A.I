

import java.util.Arrays;
import java.util.List;
import java.util.LinkedList;
import java.util.Scanner;

/**
 * Klasse KalahBoard
 * 
 * @author Ihr Name Oliver Bittel
 * @since 15.03.2021
 */
public class KalahBoard {

	/**
	 * Problemgröße
	 */
	private static final int NMulden = 6; // Mulden pro Spieler
	private static final int NSteine = 6; // Steine je Mulde

	public static final char APlayer = 'A'; // Spieler A
	public static final char BPlayer = 'B'; // Spieler B

	/*
	 * Board als Feld.
	 * APlayer: 0,1,2,3,4,5 und A-Kalah 6
	 * BPlayer: 7,8,9,10,11,12 und B-Kalah 13
	 */
	private static final int AStart = 0;
	private static final int AKalah = NMulden;
	private static final int BStart = NMulden + 1;
	private static final int BKalah = 2 * NMulden + 1;
	private static final int SIZE = 2 * NMulden + 2;
	private int[] board = new int[SIZE];

	// Aktueller Spieler, ist mit dem nächsten Zug dran
	private char curPlayer; // noch nicht festgelegt

	// Ist nächster Zug ein Bonus-Zug.
	// bonus wird gesetzt, nachdem move(m) ausgeführt wurde
	private boolean bonus = false;

	// Ist Partie beendet.
	// finished wird gesetzt, nachdem move(m) ausgeführt wurde.
	private boolean finished = false;

	// Letzter Zug (Nummer der Mulde, die gespielt wurde).
	// Wird gesetzt, nachdem move(m) ausgeführt wurde.
	private int lastPlay = -1;

	// Konsolen-Ein/Ausgabe:
	private static Scanner in = new Scanner(System.in);
	private static final String ANSI_BLUE = "\u001B[34m";

	/**
	 * Konstruktor.
	 * Legt eine Kalah-Board mit NMulden mit je NSteine an.
	 */
	public KalahBoard() {
		for (int i = 0; i < NMulden; i++) {
			board[AStart + i] = NSteine;
			board[BStart + i] = NSteine;
		}
		board[AKalah] = 0;
		board[BKalah] = 0;
		curPlayer = APlayer;
	}

	/**
	 * Konstruktor.
	 * 
	 * @param b      Board als Feld.
	 * @param player beginnender Spieler.
	 */
	public KalahBoard(int[] b, char player) {
		System.arraycopy(b, 0, board, 0, SIZE);
		curPlayer = player;
	}

	/**
	 * Kopierkonstruktor.
	 * 
	 * @param b Kalah-Board.
	 */
	public KalahBoard(KalahBoard b) {
		board = Arrays.copyOf(b.board, b.board.length);
		curPlayer = b.curPlayer;
		bonus = b.bonus;
		finished = b.finished;
	}

	/**
	 * 
	 * Prüft, ob Partie zu Ende ist.
	 * 
	 * @return true, falls Partie zu Ende ist.
	 */
	public boolean isFinished() {
		return finished;
	}

	/**
	 * 
	 * Prüft, ob nächster Zug ein Bonus-Zug ist.
	 * 
	 * @return true, falls nächster Zug ein Bonus-Zug ist.
	 */
	public boolean isBonus() {
		return bonus;
	}

	/**
	 * 
	 * Aktueller Spieler. Ist mit dem nächsten Zug (move) dran.
	 * 
	 * @return Aktueller Spieler.
	 */
	public char getCurPlayer() {
		return curPlayer;
	}

	/**
	 * 
	 * Liefert letzten Zug zurück (Nummer der Mulde, die gespielt wurde).
	 * Wird gesetzt, nachdem move ausgeführt wurde.
	 * 
	 * @return Letzter Zug.
	 */
	public int getLastPlay() {
		return lastPlay;
	}

	private void changePlayer() {
		if (curPlayer == APlayer)
			curPlayer = BPlayer;
		else
			curPlayer = APlayer;
	}

	/**
	 * Ausgabe des Bretts.
	 */
	public void print() {
		String s1 = "         === Player A ===           " + "\n";

		String s2 = "      --- --- --- --- --- ---       " + "\n" +
				"     | 5 | 4 | 3 | 2 | 1 | 0 |      " + "\n" +
				"      --- --- --- --- --- ---       " + "\n" +
				"     |%2d |%2d |%2d |%2d |%2d |%2d |      " + "\n" +
				"      --- --- --- --- --- ---       " + "\n" +
				" ---                           ---  " + "\n" +
				"| A |                         | B | " + "\n" +
				" ---                           ---  " + "\n" +
				"|%2d |                         |%2d | " + "\n" +
				" ---                           ---  " + "\n" +
				"      --- --- --- --- --- ---       " + "\n" +
				"     |%2d |%2d |%2d |%2d |%2d |%2d |      " + "\n" +
				"      --- --- --- --- --- ---       " + "\n" +
				"     | 7 | 8 | 9 |10 |11 |12 |      " + "\n" +
				"      --- --- --- --- --- ---       " + "\n";
		String s3 = "         === Player B ===           " + "\n";

		if (curPlayer == APlayer)
			s1 = ANSI_BLUE + s1;
		else if (curPlayer == BPlayer)
			s3 = ANSI_BLUE + s3;
		int[] b = board;
		System.out.print(s1);
		System.out.printf(s2, b[5], b[4], b[3], b[2], b[1], b[0],
				b[6], b[13],
				b[7], b[8], b[9], b[10], b[11], b[12]);
		System.out.println(s3);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		final KalahBoard other = (KalahBoard) obj;
		return Arrays.equals(this.board, other.board);
	}

	@Override
	public int hashCode() {
		int hash = 7;
		hash = 67 * hash + Arrays.hashCode(this.board);
		return hash;
	}

	/**
	 * Liefert Kalah von Spieler A.
	 * 
	 * @return Kalah von Spieler A.
	 */
	public int getAKalah() {
		return board[AKalah];
	}

	/**
	 * Liefert Kalah von Spieler B.
	 * 
	 * @return Kalah von Spieler B.
	 */
	public int getBKalah() {
		return board[BKalah];
	}

	/**
	 * Liefert eine Liste der möglichen Aktionen als Liste von Folge-Boards zurück.
	 * 
	 * @return Folge-Boards.
	 */
	public List<KalahBoard> possibleActions() {
		List<KalahBoard> actionList = new LinkedList<>();
		if (finish())
			return actionList; // Keine Folgezüge mehr möglich
		int s = AStart;
		if (curPlayer == BPlayer)
			s = BStart;
		for (int i = s; i < s + NMulden; i++) {
			if (board[i] != 0) {
				KalahBoard next = new KalahBoard(this);
				next.move(i);
				actionList.add(next);
			}
		}
		return actionList;
	}

	/**
	 * Spielt Mulde.
	 * 
	 * @param mulde Mulde, die gespielt wird.
	 * @return true, falls Partie nach diesem Spielzug zu Ende ist.
	 */
	public boolean move(int mulde) {
		int n = board[mulde];
		board[mulde] = 0;
		lastPlay = mulde;
		int j = mulde;
		while (n > 0) {
			j = (j + 1) % SIZE;
			if (curPlayer == APlayer && j == BKalah)
				continue;
			if (curPlayer == BPlayer && j == AKalah)
				continue;
			board[j]++;
			n--;
		}

		// Hat letzer Stein gegnerische Steine erobert:
		if (board[j] == 1 && erorbern(j)) {
			changePlayer();
			bonus = false; // Anderer Spieler an der Reihe
			finished = finish();
			return finished;
		}

		// Ist Partie zu Ende:
		finished = finish();
		if (finished)
			return true;

		if (curPlayer == APlayer && j == AKalah) {
			bonus = true; // Spieler bekommt Extra-Runde
			return false;
		}

		if (curPlayer == BPlayer && j == BKalah) {
			bonus = true; // Spieler bekommt Extra-Runde
			return false;
		}

		changePlayer();
		bonus = false; // Anderer Spieler an der Reihe
		return false;
	}

	private boolean erorbern(int mulde) {
		assert board[mulde] == 1;
		if (curPlayer == APlayer) {
			if (mulde >= AKalah)
				return false;
			int g = AKalah + (AKalah - mulde); // Gegnerische Mulde
			if (board[g] > 0) { // A gegnerische Steine
				board[AKalah] += board[g] + 1;
				board[g] = 0;
				board[mulde] = 0;
				return true;
			} else {
				return false;
			}
		} else { // curPlayer == BPlayer
			if (mulde <= AKalah || mulde == BKalah)
				return false;
			int g = BKalah - mulde - 1; // Gegnerische Mulde
			if (board[g] > 0) { // B erobert gegnerische Steine
				board[BKalah] += board[g] + 1;
				board[g] = 0;
				board[mulde] = 0;
				return true;
			} else {
				return false;
			}
		}
	}

	/**
	 * Prüft, ob ein Spieler nicht mehr spielen kann.
	 * Gegnerische Steine werden dann ins Kalah gelegt.
	 * 
	 * @return true, falls ein Spieler nicht mehr spielen kann.
	 */
	private boolean finish() {
		// Prüfe, ob A-Player nicht mehr spielen kann:
		boolean f = true;
		for (int i = AStart; i < AStart + NMulden; i++) {
			if (board[i] != 0) {
				f = false;
				break;
			}
		}
		if (f) {
			int s = 0;
			for (int i = BStart; i < BStart + NMulden; i++) {
				s += board[i];
				board[i] = 0;
			}
			board[BKalah] += s;
			return true;
		}

		// Prüfe, ob B-Player nicht mehr spielen kann:
		f = true;
		for (int i = BStart; i < BStart + NMulden; i++) {
			if (board[i] != 0) {
				f = false;
				break;
			}
		}
		if (f) {
			int s = 0;
			for (int i = AStart; i < AStart + NMulden; i++) {
				s += board[i];
				board[i] = 0;
			}
			board[AKalah] += s;
			return true;
		}
		return false;
	}

	/**
	 * Liest von der Konsole einen erlaubten Spielzug (Mulden-Nummer) ein.
	 * 
	 * @return Nummer der Mulde, die gespielt werden soll.
	 */
	public int readAction() {
		System.out.print(ANSI_BLUE + curPlayer + " spielt Mulde: ");

		while (in.hasNextLine()) {
			String line = in.nextLine();
			if (line.equals("quit")) {
				System.exit(0); // exit
			}
			int action;
			try {
				action = Integer.parseInt(line);
			} catch (NumberFormatException e) {
				System.out.println("Wrong action. Try again");
				continue;
			}
			if (possibleAction(action)) {
				return action;
			} else {
				System.out.println("Wrong action. Try again");
			}
		}
		return -1;
	}

	/*
		Zählen die Anzahl der Steine im Spielbrett (nicht in Kalah)
	 */
	public int getASeeds() {
		int seed_Counter = 0;
		for (int i = AStart; i < AStart + NMulden; i++) {

			seed_Counter += this.board[i];
		}
		return seed_Counter;
	}
	public int getBSeeds() {
		int seed_Counter = 0;
		for (int i = BStart; i < BStart + NMulden; i++) {

			seed_Counter += this.board[i];
		}
		return seed_Counter;
	}

	/**
	 * Heuristik1 wenn genau ein Stein an letzter stelle
	 */
	public void h1(KalahBoard b) {
		if(b.curPlayer == APlayer) {
			if (this.board[AStart + NMulden - 1] == 1)
				move(AStart + NMulden - 1);
		} else {
			if (this.board[BStart + NMulden - 1] == 1)
					move(BStart + NMulden - 1);
		}
	}

	/**
	 *
	 * Bewerten der SpielSituation
	 */
	public int evaluate_Board(KalahBoard b) {
		int playerA = b.getAKalah() + b.getASeeds();
		int playerB = b.getBKalah() + b.getBSeeds();
		return 0;
	}
	public int evaluatePlayer() {
		int eval = 0;
		int maxSeeds = 0;
		List<Integer> maxList = new LinkedList<>();

		if (this.curPlayer == 'A') {
			eval = this.getAKalah() + this.getASeeds();
			System.out.println(this.possibleActions());

			for (int i = AStart; i < AStart + NMulden; i++) {
				maxSeeds = Math.max(this.board[i],this.board[i+1]);
				maxList.add(maxSeeds);

				System.out.println(maxList);
			}

			return eval;

		} else if (this.curPlayer == 'B') {
			eval = this.getBKalah() + this.getBSeeds();
			System.out.println(this.possibleActions());

			for (int i = BStart; i < BStart + NMulden; i++) {
				maxSeeds = Math.max(this.board[i],this.board[i+1]);
				System.out.println(maxSeeds);
			}

			return eval;

		}
		else
			System.out.println("ooh no");

		return -1;
	}

	int MAX_VAL = Integer.MAX_VALUE;
	int MIN_VAL = Integer.MIN_VALUE;

	public int minimax(int currDepth, KalahBoard board, int player, int alpha, int beta ) throws StackOverflowError {
		if (board.isFinished()) { return 0; }
		if (currDepth <= 6)
			return 0;
		int value = (player == 0) ? MIN_VAL : MAX_VAL;

		if(player == 0) {
			for (int i = AStart; i < AStart + NMulden; i++) {
				if (board.getASeeds() != 0) {
					KalahBoard b = board;

					value = Math.max(value, minimax(currDepth+1, b, 0, alpha, beta));
					alpha = Math.max(alpha, value);

					if (beta <= alpha)
						i = AStart;
				}
			}
		} else {
			for(int i = BStart; i < BStart + NMulden; i++) {
				if (board.getBSeeds() != 0) {
					KalahBoard b = board;

					value = Math.min(value, minimax(currDepth+1, b, 0, alpha, beta));
					beta = Math.min(beta, value);

					if (beta <= alpha)
						i = BStart;
				}
			}
		}
		return value;
	}


	private boolean possibleAction(int mulde) {
		switch (curPlayer) {
			case APlayer:
				return (AStart <= mulde && mulde < AKalah && board[mulde] > 0);
			case BPlayer:
				return (BStart <= mulde && mulde < BKalah && board[mulde] > 0);
			default:
				return false;
		}
	}
}
