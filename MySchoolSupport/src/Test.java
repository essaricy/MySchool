import java.text.DateFormatSymbols;
import java.util.Calendar;


public class Test {

	private static final int MAX_LIMIT = 50;

	public static void main(String[] args) {
		int startValue = 0;
		int endValue = 0;
		String[] names = null;

		Calendar calendar = Calendar.getInstance();
		names = DateFormatSymbols.getInstance().getShortWeekdays();
		for (int i = 0; i < names.length; i++) {
			System.out.println(i + " = " + names[i]);
		}
		System.out.println("---------------------");
		startValue = 2;
		int type = Calendar.DAY_OF_WEEK;
		endValue = calendar.getActualMaximum(type) + 2;
		System.out.println("endValue " + endValue);

		for (int loopCount = startValue; loopCount < endValue; loopCount++) {
			System.out.println("loopCount= " + loopCount);
			double value = 0;
			System.out.println("Number=" + loopCount);
			if (names == null) {
				System.out.println("Name=" + loopCount);
			} else {
				System.out.println("Name=" + names[loopCount-1]);
			}
		}
	}
}
