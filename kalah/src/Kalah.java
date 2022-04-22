

/**
 * Hauptprogramm für KalahMuster.
 * @since 29.3.2021
 * @author oliverbittel
 */
public class Kalah {
	
	private static final String ANSI_BLUE = "\u001B[34m";

	/**
	 *
	 * @param args wird nicht verwendet.
	 */
	public static void main(String[] args) {
		testExample();
		//testHHGame();
	}
	
	/**
	 * Beispiel von https://de.wikipedia.org/wiki/Kalaha
	 */
	public static void testExample() { 
		KalahBoard kalahBd = new KalahBoard(new int[]{5,3,2,1,2,0,0,4,3,0,1,2,2,0}, 'B');
		kalahBd.print();

		System.out.println(kalahBd.minimax(0,kalahBd,1, 0,1));
		/*
		System.out.println("B spielt Mulde 11");
		kalahBd.move(11);
		kalahBd.print();
		
		System.out.println("B darf nochmals ziehen und spielt Mulde 7");
		kalahBd.move(7);
		kalahBd.print();
		System.out.println(kalahBd.getASeeds());
		System.out.println(kalahBd.getBSeeds());

		int a = kalahBd.getBKalah();
		int x = kalahBd.getBSeeds();
		System.out.println(a+x);

		kalahBd.evaluatePlayer();

		 */
	}
	
	/**
	 * Mensch gegen Mensch
	 */
	public static void testHHGame() {
		KalahBoard kalahBd = new KalahBoard();
		kalahBd.print();

		while (!kalahBd.isFinished()) {
			int action = kalahBd.readAction();
			kalahBd.move(action);
			kalahBd.print();
		}

		System.out.println("\n" + ANSI_BLUE + "GAME OVER");
	}

	/**
	 * Mensch gegen Maschine
	 */
	public static void testPVE() {

	}
}
