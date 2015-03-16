import java.util.ArrayList;
import java.util.List;
import java.util.Random;


public class Test {

	private static final int MAX_LIMIT = 50;

	public static void main(String[] args) {
		List list = new ArrayList();
		Random random = new Random();
		for (int index = 0; index < 24; index++) {
			//list.add(index);
			list.add(random.nextInt(MAX_LIMIT));
		}
		System.out.println(list);
	}
}
