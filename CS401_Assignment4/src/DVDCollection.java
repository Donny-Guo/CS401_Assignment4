import java.io.*;
import java.util.Scanner;

public class DVDCollection {

	// Data fields
	
	/** The current number of DVDs in the array */
	private int numdvds;
	
	/** The array to contain the DVDs */
	private DVD[] dvdArray;
	
	/** The name of the data file that contains dvd data */
	private String sourceName;
	
	/** Boolean flag to indicate whether the DVD collection was
	    modified since it was last saved. */
	private boolean modified;
	
	public static final String[] MOVIE_RATINGS= {"G", "PG", "PG-13", "NC-17", "R"}; 
	
	/**
	 *  Constructs an empty directory as an array
	 *  with an initial capacity of 7. When we try to
	 *  insert into a full array, we will double the size of
	 *  the array first.
	 */
	public DVDCollection() {
		numdvds = 0;
		dvdArray = new DVD[7];
	}
	
	public String toString() {
		// Return a string containing all the DVDs in the
		// order they are stored in the array along with
		// the values for numdvds and the length of the array.
		// See homework instructions for proper format.
		// numdvds = 3
		// dvdArray.length = 7
		// dvdArray[0] = ANGELS AND DEMONS/PG-13/138min 
		// dvdArray[1] = STAR TREK/R/127min
		// dvdArray[2] = UP/PG/96min																
		
		String info = "numdvds = " + this.numdvds + "\n"
				+ "dvdArray.length = " + this.dvdArray.length + "\n";
		for (int i = 0; i < this.numdvds; i++) {
			info += String.format("dvdArray[%d] = ", i);
			info += dvdArray[i].getTitle() + "/" + dvdArray[i].getRating()
					+ "/" + dvdArray[i].getRunningTime() + "min\n";
		}
		return info;
	}

	public void addOrModifyDVD(String title, String rating, String runningTime) {
		// NOTE: Be careful. Running time is a string here
		// since the user might enter non-digits when prompted.
		// If the array is full and a new DVD needs to be added,
		// double the size of the array first.
		
		// sanity check for input :
		if (!title.equals("") && isCorrectRating(rating) && isCorrectTime(runningTime)) {

			int index = findDVDIndex(title);
			
			// if title not in dvdArray:
			if (index == -1) {
				// if array is full:
				if (this.numdvds == this.dvdArray.length) {
					// expand array size
					this.dvdArray = expandArray(this.dvdArray);
					
				}
				// add to the end of collections
				DVD newDVD = new DVD(title, rating, Integer.parseInt(runningTime));
				this.dvdArray[numdvds] = newDVD;
				
				// use insertion sort move new dvd to the correct order (lex)
				DVD temp = this.dvdArray[numdvds];
				int i = numdvds - 1;
				while (i >= 0) {
					if (this.dvdArray[i].getTitle().compareToIgnoreCase(temp.getTitle()) > 0) {
						this.dvdArray[i + 1] = this.dvdArray[i];
					} else {
						break;
					}
					i--;
				}
				this.dvdArray[i+1] = temp;
				
				
				// update numdvds
				this.numdvds++;
				// update modified status
				this.modified = true;
				
			} else { // else: (title is in dvdArray)
				String currRating = this.dvdArray[index].getRating();
				int currRunningTime = this.dvdArray[index].getRunningTime();
				int newRunningTime = Integer.parseInt(runningTime);
				
				// if rating is different:
				if (!rating.equals(currRating)) {
					// update rating
					this.dvdArray[index].setRating(rating);
					// update modified status
					this.modified = true;
					
				}
				// if runningTime is different:
				if (newRunningTime != currRunningTime) {
					// update rating
					this.dvdArray[index].setRunningTime(newRunningTime);
					// update modified status
					this.modified = true;
				}
				
			}
			
		} else { // sanity check failed
			System.out.println("Incorrect DVD info provided. No changes made to the collection.");
		}
		

				

	}
	
	public void removeDVD(String title) {
		// given a dvd title, remove it from dvdArray if exists
		
		// get the index of the dvd the user wants to remove
		int index = findDVDIndex(title);
		if (index != -1) { // if dvd is found in the collections
			// set dvd to null
			this.dvdArray[index] = null;
			// move all the dvd after it forward
			for (int i = index + 1; i < this.numdvds; i++) {
				this.dvdArray[i - 1] = this.dvdArray[i];
			}
			// update modified status
			this.modified = true;
			// update numdvds
			this.numdvds--;
			
		} else {
			System.out.println("DVD not found in the collection. Removal failed.");
		}
				
	}
	
	public String getDVDsByRating(String rating) {
		// Return  a string containing all DVDs that match the given rating
		// in the order that they appear in the collection, separated by newlines.
		String ratingInfo = "";
		for (int i = 0; i < this.numdvds; i++) {
			if (this.dvdArray[i].getRating().equals(rating)) {
				String temp = this.dvdArray[i].getTitle() + "\n";
				ratingInfo += temp;
			}
		}
		return ratingInfo;

	}

	public int getTotalRunningTime() {
		// return the total running time of all DVDs in the collection. 
		// if no, return 0
		int totalRunningTime = 0;
		for (int i = 0; i < this.numdvds; i++) {
			totalRunningTime += this.dvdArray[i].getRunningTime();
		}
		return totalRunningTime;

	}

	
	public void loadData(String filename) {
		// if empty: start with empty collections
		// if corrupted: stop initializing the collection at the point of corruption.
		
		// load the file from filename to collections
		this.sourceName = filename;
		File file = new File(filename);
		try {
			if (file.exists()) { // if source file exists
				// load data
				Scanner scanner = new Scanner(file);
				String newDVDInfo = null;
				while (scanner.hasNextLine()) {
					if ((newDVDInfo = scanner.nextLine()) != "") { // make sure line this not empty
						String[] infoList = newDVDInfo.split(",");
						if (infoList.length == 3) {
							String title = infoList[0];
							String rating = infoList[1];
							String runningTime = infoList[2];
							// sanity check for input :
							if (!title.equals("") && isCorrectRating(rating) && isCorrectTime(runningTime)) {
								this.addOrModifyDVD(title, rating, runningTime);
							} else {
								System.out.println("Invalid dvd information found in " + this.sourceName + ". Loading Data Stopped.");
								break; // break the while loop
							}
						} else { // infoList.length != 3
							System.out.println("Encountered invalid dvd format in " + this.sourceName + ". Loading Data Stopped.");
							break; // break the while loop
						}
					}
				} // end while loop
				// set modify status back to false
				this.modified = false;
				scanner.close();
			} else { // if source file does not exist
				System.out.printf("Failed to load data: %s not found.%n", this.sourceName);
			}
		} catch (IOException ex) {
			ex.printStackTrace();
		}
		
		
	}
	
	public void save() {
		// overwrite the sourceName file with curr collection
		if (this.modified) {
			File file = new File(this.sourceName);
			try {
				BufferedWriter writer = new BufferedWriter(new FileWriter(file));
				for (int i = 0; i < this.numdvds; i++) {
					writer.write(this.dvdArray[i].toString());
				}
				writer.close();
				System.out.println("Successfully saved DVD collections to " + this.sourceName + ".");
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		} else {
			System.out.println("Saving...No changes needed to save to " + this.sourceName + ".");
		}

	}

	// Additional private helper methods go here:

	/*
	 * Method: Return an DVD array that doubles the original array size  
	 * 
	 * Precondition: original DVD array
	 * 
	 * Postcondition: 
	 * Return a new array, with double size 
	 * and store all the dvds in the original array
	 */
	private DVD[] expandArray(DVD[] array) {
		DVD[] newArray = new DVD[array.length * 2];
		for (int i = 0; i < array.length; i++) {
			newArray[i] = array[i];
		}
		return newArray; 
	}
	
	
	/*
	 * Method: Return the index of DVD in the array
	 * 
	 * Precondition: DVD title
	 * 
	 * Postcondition: return the index of the input DVD in
	 * the dvdArray. If not exists, return -1
	 */
	private int findDVDIndex(String title) {
		int index = -1;
		for (int i = 0; i < this.numdvds; i++) {
			if (title.equals(this.dvdArray[i].getTitle())) {
				index = i;
				break;
			}
		}
		return index;
	}
	
	private boolean isCorrectRating(String rating) {
		boolean res = false;
		// check if rating is in the list of MOVIE_RATINGS
		for (String s: MOVIE_RATINGS) {
			if (s.equals(rating)) {
				res = true;
				break;
			}
		}
		return res;
	}

	private boolean isCorrectTime(String time) {
		// try to convert time to integer,
		// if failed return false, else return true
		try {
			Integer.parseInt(time);
			return true;
		} catch(Exception e) {
			return false;
		}
	}
}
