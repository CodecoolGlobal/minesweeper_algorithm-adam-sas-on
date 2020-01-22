import java.util.Scanner;

public class Minesweeper {
	private static Mines mines = new Mines();
	private static MinesRand random = new MinesRand();

	private static void mainMenu(int size){
		System.out.println("1) Print example table (3x3)");
		System.out.println("2) Generate random table with size = "+ size);
		System.out.println("3) Change size value");
		System.out.println("4) Calculate and print nearby cells");
		System.out.println("*) Exit.");
	}
	private static int askMenu(int size){
		Scanner in = new Scanner(System.in);
		int command = -1;

		while(command < 0){
			mainMenu(size);
			System.out.print("Type the number of command: ");
			while(!in.hasNextInt() ){
				System.out.print("Wrong value! Type the number of command: ");
				in.next();
			}
			command = in.nextInt();
		}
		return command;
	}
	private static int askChangeSize(int oldSize){
		Scanner in = new Scanner(System.in);
		int newSize = oldSize;
		boolean incorrect = true;

		while(incorrect){
			System.out.println("\tCurrent size = " + oldSize);
			System.out.print("Type new size value for mines > 1 (0 for cancel): ");

			while(!in.hasNextInt() ){
				System.out.println(" Wrong value!");
				System.out.print("Type new size value for mines > 1 (0 for cancel): ");
				in.next();
			}
			newSize = in.nextInt();
			incorrect = false;
			if(newSize == 0)
				newSize = oldSize;
			else if(newSize == 1 || newSize < 0){
				System.out.println("Wrong value for size! Please type again");
				incorrect = true;
			}
		}
		return newSize;
	}

	public static void main(String[] args){
		String minesMap = "";
		int minesSize = 3, command;
		boolean run = true;

		while(run){
			command = askMenu(minesSize);
			switch(command){
				case 1:
					mines.exampleMines();
					minesMap = mines.toString();
					break;
				case 2:
					mines.newMines(random, minesSize, 0);
					minesMap = mines.toString();
					break;
				case 3:
					minesSize = askChangeSize(minesSize);
					mines.newMines(random, minesSize, 0);
					minesMap = mines.toString();
					break;
				case 4:
					mines.solveMinesBoard();
					mines.setBorder();
					minesMap = mines.toString();
					mines.unsetBorder();
					break;
				default:
					run = false;
			}
			System.out.println(minesMap);
		}
	}
}
