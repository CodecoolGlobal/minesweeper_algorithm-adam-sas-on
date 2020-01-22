import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MinesSolveTest {
	private Mines mines = new Mines();
	private MinesRand rand = new MinesRand();

	@BeforeEach
	void setUp(){
		mines.newMines(rand, 10, 10);

	}

	@Test
	void solveMinesBoard(){
	}
}