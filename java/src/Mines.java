public class Mines {
	private char[] mines;
	private int[] countMines;
	private int rows, columns;
	private double mines_percentage = 20;

	Mines(){

	}

	public void newMines(int rows, int cols){
		if(rows < 2 || cols == 1)
			throw new IllegalArgumentException("Number of rows has to be bigger than 1, and number of columns has to be bigger than 1 or = 0 for the same amount of columns as rows.");

		this.rows = rows;
		columns = (cols > 1)?cols:rows;
		int newLength = rows*columns;
		if(newLength > mines.length){
			char[] minesNew = new char[newLength];
			mines = minesNew;
			int[] newCounts = new int[newLength];
			countMines = newCounts;
		}

		int i = 0;
		for(; i < newLength; i++){
			mines[i] = '.';
			countMines[i] = 0;
		}

	}

	public void randomMines(MinesRand rand, boolean useChoices){
		int count = rows*columns;
		double dCount = mines_percentage/100.0*count;
		count = (int) dCount + 1;

		int i = rows*columns;
		int[] indices, list;
		list = new int[i];
		while(i > 0){
			i--;
			list[i] = i;
		}

		if(useChoices){
			indices = rand.choices(list, count);
		} else {
			indices = rand.sample(list, count);
		}

	}
}
