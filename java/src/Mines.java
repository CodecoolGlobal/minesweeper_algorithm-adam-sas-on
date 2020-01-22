import java.util.Arrays;

public class Mines {
	private char[] mines;
	private int[] countMines;
	private int rows, columns;
	private double mines_percentage = 20;
	private boolean printBorder = false;

	Mines(){

	}

	public void newMines(MinesRand rand, int rows, int cols){
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

		randomMines(rand, true);
	}

	public void randomMines(MinesRand rand, boolean useChoices){
		int count = rows*columns;
		double dCount = mines_percentage/100.0*count;
		count = (int) dCount + 1;

		int i = rows*columns;
		if(i < 4)
			throw new IndexOutOfBoundsException("Too small mines table to use it!");

		int[] indices, list;
		list = new int[i];
		while(i > 0){
			i--;
			list[i] = i;
			mines[i] = '.';
			countMines[i] = 0;
		}

		if(useChoices){
			indices = rand.choices(list, count);
		} else {
			indices = rand.sample(list, count);
		}

		for(int j = 0; j < count; j++){
			i = indices[j];
			mines[i] = '*';
		}
	}

	public void calculate(){

	}

	public void setBorder(){
		printBorder = true;
	}
	public void unsetBorder(){
		printBorder = false;
	}
	@Override
	public String toString(){
		if(rows < 1)
			return "";

		StringBuilder text = new StringBuilder();
		int i, j, k = 0;

		if(printBorder){
			text.append(" /");

			for(i = 0; i < columns; i++)
				text.append("----");
			text.deleteCharAt(text.length() - 1);
			text.append("\\");

			StringBuilder border, values;
			for(i = 0; i < rows; i++){
				border = new StringBuilder(" |");
				values = new StringBuilder(" |");
				for(j = 0; j < columns; j++, k++){
					border.append("----");
					if(mines[k] == '*') {
						values.append(" * |");
					} else {
						values.append(" ").append(countMines[k]).append(" |");
					}
				}
				text.append("\n").append(values).append("\n").append(border);
			}
		} else if(rows > 0){
			for(i = 0; i < rows; i++){
				for(j = 0; j < columns; j++, k++){
					if(mines[k] == '*'){
						text.append(" *");
					} else
						text.append(" .");
				}
				text.append("\n");
			}
			text.deleteCharAt(text.length() - 1);// remove last '\n';
		}

		return text.toString();
	}
}
