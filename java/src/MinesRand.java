import java.util.*;

public class MinesRand {
	private static Random rand;

	MinesRand(){
		rand = new Random();
	}

	public int[] choices(int[] list, final int size){
		int[] result = new int[size];
		int i, j, count = list.length;

		for(i = j = 0; i < size; i++){
			j = rand.nextInt(count);
			result[i] = list[j];
		}

		return result;
	}
	public int[] sample(int[] list, final int size){
		if(size > list.length)
			throw new IllegalArgumentException("Expected return list size has to be smaller than argument list size!");

		List<Integer> list2 = new ArrayList<>();
		int i, count = list.length;
		for(i = 0; i < count; i++)
			list2.add(list[i]);

		Collections.shuffle(list2, rand);

		int[] result = new int[size];
		i = 0;
		ListIterator<Integer> iter = list2.listIterator();
		while(i < size && iter.hasNext() ){
			result[i] = iter.next();
			i++;
		}

		return result;
	}
}
