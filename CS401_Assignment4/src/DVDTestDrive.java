/*
 * Driver class for DVD class (DVD.java)
 */
public class DVDTestDrive {
	public static void main(String[] args) {
		String inputTitle = "DVDTitle";
		String inputRating = "DVDRating";
		int inputRunningTime = 120;
		String inputInfo = "DVDTitle,DVDRating,120\n";
		// create one DVD
		DVD d = new DVD(inputTitle, inputRating, inputRunningTime);
		// verify all getter methods and toString
		String title = d.getTitle();
		String rating = d.getRating();
		int runningTime = d.getRunningTime();
		String info = d.toString();
		// Test1 - verify getter methods
		boolean test1IsPassed = false;
		if (title.equals(inputTitle) 
			&& rating.equals(inputRating) 
			&& runningTime == inputRunningTime
			&& info.equals(inputInfo)) {
			test1IsPassed = true;
		}
		System.out.println("Test1 is passed? " + test1IsPassed);
		
		
		// use all setting methods
		String newTitle = "ANGELS AND DEMONS";
		String newRating = "PG-13";
		int newRunningTime = 138;
		String newInfo = "ANGELS AND DEMONS,PG-13,138\n";
		d.setTitle(newTitle);
		d.setRating(newRating);
		d.setRunningTime(newRunningTime);
		// Test2 - verify setter methods by calling getter methods
		title = d.getTitle();
		rating = d.getRating();
		runningTime = d.getRunningTime();
		info = d.toString();
		// verify
		boolean test2IsPassed = false;
		if (title.equals(newTitle) 
			&& rating.equals(newRating) 
			&& runningTime == newRunningTime
			&& info.equals(newInfo)) {
			test2IsPassed = true;
		}
		System.out.println("Test2 is passed? " + test2IsPassed);
		
	}
}
