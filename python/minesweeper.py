import curses, getopt, sys
from random import choices, sample

#  - - - - - - - - - - - - - - - - - - - - UI functions- - - - - - - - - - - - - - - - - - - - -
def usage():
	print("\tScript to print Minesweeper random tables with size set by the user. It also prints the one from the content of this task.\n")

def getWork():
	try:
		opts, args = getopt.getopt(sys.argv[1:], "h", ["help"])
	except getopt.GetoptError:
		return 0

	job=1
	for opt, arg in opts:
		if opt in ("-h", "--help"):
			usage()
			job=0

	return job
# end
#  - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -

def new_table(size):
	# another option: import numpy; board=numpy.zeros((size, size));
	board = [['.' for x in range(size)] for y in range(size)]
	return board
#

#  - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -

def indices_table_by_choices(ind_range, size):
	if ind_range < 1:
		return [0]

	int_list = [x for x in range(ind_range)]
	indices = choices(int_list, k=size*2)
	result = []

	i = 0
	while i < len(indices):
		result.append( (indices[i], indices[i+1]) )
		i += 2

	return result
#

def indices_table_by_sample(ind_range, size):
	if ind_range < 1:
		return [0]

	int_list = []
	i = 0
	while i < ind_range:
		j = 0
		while j < ind_range:
			int_list.append( (i, j) )
			j += 1
		i += 1

	result = sample(int_list, size)
	return result
#

def random_mines(table, mines_in_percentage):
	count = len(table)*len(table[0])
	count = mines_in_percentage/100.0*count
	count = int(count) + 1

	indices = indices_table_by_choices(len(table), count)
	# indices = indices_table_by_sample(len(table), count)

	for (i, j) in indices:
		table[i][j] = '*'
#

def mines_from_example(table):
	table[0][2] = table[2][0] = '*'
#

#  - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -

def draw_board(stdscr, board, row_bg):
	stdscr.move(row_bg, 0)
	stdscr.clrtobot()

	i=0
	while i < len(board):
		stdscr.addstr(row_bg + i, 2, '.')
		j=0
		for col in board[i]:
			stdscr.addstr(row_bg + i, 2 + j*2, " {}".format(col) )
			j+=1

		i+=1

#

def solve_mines_board(table):
	i=0
	while i < len(table):
		j=0
		while j < len(table[i]):
			if table[i][j] != '*':
				table[i][j] = 0
			j+=1
		i+=1


#

def run(stdscr):
	stdscr = curses.initscr()

	curses.noecho()
	curses.cbreak()
	stdscr.keypad(True)


	run = True
	cmd = 0

	MINE_PERCENTAGE = 20
	size = 3
	table = new_table(size)
	random_mines(table, MINE_PERCENTAGE)

	numbers = {ord('0'), ord('1'), ord('2'), ord('3'), ord('4'), ord('5'), ord('6'), ord('7'), ord('8'), ord('9')}
	stdscr.addstr(0, 4, "Minesweeper")

	while run:
		stdscr.addstr(2, 1, "Print example table (3x3)")
		stdscr.addstr(3, 1, "Generate random table with size = {} (number for size modification, ENTER to print) ".format(size) )
		stdscr.addstr(4, 2, "Click ENTER to calculate and print nearby cells: ")
		stdscr.addstr(5, 2, "Click ENTER to exit: ")

		if cmd==0:
			stdscr.addstr(2, 1, "Print example table (3x3)", curses.A_BOLD)
		elif cmd==1:
			stdscr.addstr(3, 1, "Generate random table with size = {}".format(size), curses.A_BOLD)
		elif cmd==2:
			stdscr.addstr(4, 2, "Click ENTER to calculate and print nearby cells: ", curses.A_BOLD)
		elif cmd==3:
			stdscr.addstr(5, 2, "Click ENTER to exit:", curses.A_BOLD)

		c = stdscr.getch()

		if c == curses.KEY_UP and cmd > 0:
			cmd -= 1
		elif c == curses.KEY_DOWN and cmd < 3:
			cmd += 1
		elif ( (c == curses.KEY_ENTER or c == 10) and cmd == 3) or c == curses.KEY_EXIT:
			run = False
		elif c == curses.KEY_BACKSPACE and cmd == 1:
			size = int(size/10)
		elif cmd == 1 and c in numbers:
			nr = int(c-ord('0'))
			size = size*10 + nr
			if size > 20:
				size = 20
		elif (c == curses.KEY_ENTER or c == 10) and cmd == 0:
			size = 3
			table = new_table(size)
			mines_from_example(table)

			draw_board(stdscr, table, 7)
		elif (c == curses.KEY_ENTER or c == 10) and cmd == 1 and size > 1:
			table = new_table(size)
			random_mines(table, MINE_PERCENTAGE)

			draw_board(stdscr, table, 7)
		elif (c == curses.KEY_ENTER or c == 10) and cmd == 2:
			solve_mines_board(table)

			draw_board(stdscr, table, 7)
# end run

	curses.nocbreak()
	stdscr.keypad(False)
	curses.echo()

	curses.endwin()
#

if __name__ == '__main__':
	wrk=getWork()

	if wrk==1:
		curses.wrapper(run)
#

