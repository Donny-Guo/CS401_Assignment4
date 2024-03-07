
public class DVDCollectionTestDrive {
	public static void main(String[] args) {
		// test data (total 15 movies)
		String[] titles = {
	            "The Shawshank Redemption", "The Godfather", "The Dark Knight", 
	            "Forrest Gump", "Inception", "The Matrix", 
	            "Pulp Fiction", "Fight Club", "Interstellar", 
	            "Goodfellas", "The Silence of the Lambs", "Saving Private Ryan", 
	            "Spirited Away", "The Green Mile", "Parasite"
	        };

        String[] ratings = {
            "R", "R", "PG-13", "PG-13", "PG-13", "R", 
            "R", "R", "PG-13", "R", "R", "R", 
            "PG", "R", "R"
        };

        String[] duration = {
            "142", "175", "152", "142", "148", "136", 
            "154", "139", "169", "146", "118", "169", 
            "125", "189", "132"
        };
        
        DVD[] myDVD = new DVD[15];
        for (int i = 0; i < 15; i++) {
        	int runningTime = Integer.parseInt(duration[i]);
        	myDVD[i] = new DVD(titles[i], ratings[i], runningTime);
        }
        
		/*
		 * Test 1: everything except loading and saving data
		 */
		// initialize DVDCollection
		DVDCollection dvdCollection = new DVDCollection();
		String expectedInfo = "numdvds = 0\ndvdArray.length = 7\n";
		// toString()
		String actualInfo = dvdCollection.toString();
		// verify toString()
		if (expectedInfo.equals(actualInfo)) {
			System.out.println("Initialized toString test passed");
		} else {
			System.out.println("Initialized toString test failed.");
			System.out.printf("Expected: %s%n; Actual: %s%n", expectedInfo, actualInfo);
		}
		
		
		// add 4 new dvds
		for (int i = 0; i < 4; i++) {
			dvdCollection.addOrModifyDVD(titles[i], ratings[i], duration[i]);
		}
		// verify add by toString()
		expectedInfo = "numdvds = 4\n"
				+ "dvdArray.length = 7\n"
				+ "dvdArray[0] = Forrest Gump/PG-13/142min\n"
				+ "dvdArray[1] = The Dark Knight/PG-13/152min\n"
				+ "dvdArray[2] = The Godfather/R/175min\n"
				+ "dvdArray[3] = The Shawshank Redemption/R/142min\n";
		// verify
		actualInfo = dvdCollection.toString();
		if (expectedInfo.equals(actualInfo)) {
			System.out.println("toString test (4 dvds) passed");
		} else {
			System.out.println("toString test (4 dvds) failed.");
			System.out.printf("Expected: %s%n; Actual: %s%n", expectedInfo, actualInfo);
		}
		
		// add 6 new dvds
		for (int i = 4; i < 10; i++) {
			dvdCollection.addOrModifyDVD(titles[i], ratings[i], duration[i]);
		}

		expectedInfo = "numdvds = 10\n"
				+ "dvdArray.length = 14\n"
				+ "dvdArray[0] = Fight Club/R/139min\n"
				+ "dvdArray[1] = Forrest Gump/PG-13/142min\n"
				+ "dvdArray[2] = Goodfellas/R/146min\n"
				+ "dvdArray[3] = Inception/PG-13/148min\n"
				+ "dvdArray[4] = Interstellar/PG-13/169min\n"
				+ "dvdArray[5] = Pulp Fiction/R/154min\n"
				+ "dvdArray[6] = The Dark Knight/PG-13/152min\n"
				+ "dvdArray[7] = The Godfather/R/175min\n"
				+ "dvdArray[8] = The Matrix/R/136min\n"
				+ "dvdArray[9] = The Shawshank Redemption/R/142min\n";
		actualInfo = dvdCollection.toString();
		// verify expand array size
		if (expectedInfo.equals(actualInfo)) {
			System.out.println("Expand size test (10 dvds total) passed");
		} else {
			System.out.println("Expand size test (10 dvds total) failed.");
			System.out.printf("Expected: %s%n; Actual: %s%n", expectedInfo, actualInfo);
		}
		
		
		
		// modify 3 dvds (test modify)
		DVD[] modifedDVDs = new DVD[3];
		modifedDVDs[0] = new DVD("Fight Club", "PG-13", 139);
		modifedDVDs[1] = new DVD("Goodfellas", "R", 139);
		modifedDVDs[2] = new DVD("Interstellar", "R", 139);
		for (int i = 0; i < 3; i++) {
			dvdCollection.addOrModifyDVD(modifedDVDs[i].getTitle(), modifedDVDs[i].getRating(), Integer.toString(modifedDVDs[i].getRunningTime()));
		}
		// case 1: change runningTime or rating
		expectedInfo = "numdvds = 10\n"
				+ "dvdArray.length = 14\n"
				+ "dvdArray[0] = Fight Club/PG-13/139min\n"
				+ "dvdArray[1] = Forrest Gump/PG-13/142min\n"
				+ "dvdArray[2] = Goodfellas/R/139min\n"
				+ "dvdArray[3] = Inception/PG-13/148min\n"
				+ "dvdArray[4] = Interstellar/R/139min\n"
				+ "dvdArray[5] = Pulp Fiction/R/154min\n"
				+ "dvdArray[6] = The Dark Knight/PG-13/152min\n"
				+ "dvdArray[7] = The Godfather/R/175min\n"
				+ "dvdArray[8] = The Matrix/R/136min\n"
				+ "dvdArray[9] = The Shawshank Redemption/R/142min\n";
		actualInfo = dvdCollection.toString();
		// verify modification
		if (expectedInfo.equals(actualInfo)) {
			System.out.println("Modification test (10 dvds total) passed");
		} else {
			System.out.println("Modification test (10 dvds total) failed.");
			System.out.printf("Expected: %s;%nActual: %s%n", expectedInfo, actualInfo);
		}
		
		
		// case 2: invalid film 
		// Adding/Modify invalid dvd test ###################
		dvdCollection.addOrModifyDVD("Fight Club", "R-13", "139");
		dvdCollection.addOrModifyDVD("Forrest Gump", "PG-13", "142min");
		dvdCollection.addOrModifyDVD("", "R", "999");
		dvdCollection.addOrModifyDVD("ABC", "R-99", "999");
		dvdCollection.addOrModifyDVD("ABC", "R", "999min");
		actualInfo = dvdCollection.toString();
		// verify
		if (expectedInfo.equals(actualInfo)) {
			System.out.println("Modification test (invalid info) passed");
		} else {
			System.out.println("Modification test (invalid info) failed.");
			System.out.printf("Expected: %s;%nActual: %s%n", expectedInfo, actualInfo);
		}
		
		
		// test remove: ####################
		// remove 1 dvd
		dvdCollection.removeDVD("Forrest Gump");
		dvdCollection.removeDVD("Unknown Movie");
		dvdCollection.removeDVD("The Shawshank Redemption");
		
		expectedInfo = "numdvds = 8\n"
				+ "dvdArray.length = 14\n"
				+ "dvdArray[0] = Fight Club/PG-13/139min\n"
				+ "dvdArray[1] = Goodfellas/R/139min\n"
				+ "dvdArray[2] = Inception/PG-13/148min\n"
				+ "dvdArray[3] = Interstellar/R/139min\n"
				+ "dvdArray[4] = Pulp Fiction/R/154min\n"
				+ "dvdArray[5] = The Dark Knight/PG-13/152min\n"
				+ "dvdArray[6] = The Godfather/R/175min\n"
				+ "dvdArray[7] = The Matrix/R/136min\n";
		actualInfo = dvdCollection.toString();
		// verify removal
		if (expectedInfo.equals(actualInfo)) {
			System.out.println("Removal test (8 dvds total) passed");
		} else {
			System.out.println("Removal test (8 dvds total) failed.");
			System.out.printf("Expected: %s%n Actual: %s%n", expectedInfo, actualInfo);
		}
		
		
		// getDVD by rating test: ######################
		String expectedRating = "Goodfellas\n"
				+ "Interstellar\n"
				+ "Pulp Fiction\n"
				+ "The Godfather\n"
				+ "The Matrix\n";
		String actualRating = dvdCollection.getDVDsByRating("R");
		if (actualRating.equals(expectedRating)) {
			System.out.println("getDVDsByRating test passed");
		} else {
			System.out.println("getDVDsByRating test failed");
			System.out.printf("Expected: %s%n Actual: %s%n", expectedRating, actualRating);
		}						

		
		
		// get total running time test ##############
		int expectedTotalRunningTime = 0;
		for (int i = 0; i < 10; i++) {
			if (i == 1 || i == 9) {
				continue;
			}
			expectedTotalRunningTime += myDVD[i].getRunningTime();
		}
		int actualTotalRunningTime = dvdCollection.getTotalRunningTime();
		// verify
		if (expectedTotalRunningTime == actualTotalRunningTime) {
			System.out.println("Total running time test passed");
		} else {
			System.out.println("Total running time test failed");
			System.out.printf("Expected: %d%n; Actual: %d%n", expectedTotalRunningTime, actualTotalRunningTime);
		}
	
		
		test2();
//		test3();
		
	} // end main method
	
	public static boolean test2() {
		/*
		 * Test 2: load data
		 */
		// Case 1: empty file/file not found
		DVDCollection dvdCollection1 = new DVDCollection();
		String filename1 = "dvddataNotFound.txt";
		dvdCollection1.loadData(filename1);
		String expectedInfo = "numdvds = 0\n"
				+ "dvdArray.length = 7\n";
		// verify
		String actualInfo = dvdCollection1.toString();
		if (expectedInfo.equals(actualInfo)) {
			System.out.println("Load data case 1 empty file/file not found passed");
		} else {
			System.out.println("Load data case 1 empty file/file not found failed.");
			System.out.printf("Expected: %s%n Actual: %s%n", expectedInfo, actualInfo);
		}
		
		
		// Case 2: file exists but corrupt in the middle way
		DVDCollection dvdCollection2 = new DVDCollection();
		String filename2 = "dvddatatest_open_corrupted_file.txt";
		dvdCollection2.loadData(filename2);
		expectedInfo = "numdvds = 2\n"
				+ "dvdArray.length = 7\n"
				+ "dvdArray[0] = Forrest Gump/PG-13/142min\n"
				+ "dvdArray[1] = The Dark Knight/PG-13/152min\n";
		actualInfo = dvdCollection2.toString();
		if (expectedInfo.equals(actualInfo)) {
			System.out.println("Load data case 2 open corrupted file passed");
		} else {
			System.out.println("Load data case 2 open corrupted file failed.");
			System.out.printf("Expected: %s%n Actual: %s%n", expectedInfo, actualInfo);
		}
		
		return true;
		
//		// Case 3: file exists and no format problem
//		DVDCollection dvdCollection = new DVDCollection();
//		String filename = "dvddata.txt";
//		dvdCollection.loadData(filename);
//		// verify
//		expectedInfo = "numdvds = 3\n"
//				+ "dvdArray.length = 7\n"
//				+ "dvdArray[0] = ANGELS AND DEMONS/PG-13/138min\n"
//				+ "dvdArray[1] = STAR TREK/R/127min\n"
//				+ "dvdArray[2] = UP/PG/96min\n";
//		actualInfo = dvdCollection.toString();
//		if (expectedInfo.equals(actualInfo)) {
//			System.out.println("Load data test case 3 passed");
//		} else {
//			System.out.println("Load data test case 3 failed.");
//			System.out.printf("Expected: %s%n Actual: %s%n", expectedInfo, actualInfo);
//		}
//		
//		dvdCollection.save();
//		return false; // STUB
	}
	
	public static boolean test3() {
		// test data (total 15 movies)
		String[] titles = {
	            "The Shawshank Redemption", "The Godfather", "The Dark Knight", 
	            "Forrest Gump", "Inception", "The Matrix", 
	            "Pulp Fiction", "Fight Club", "Interstellar", 
	            "Goodfellas", "The Silence of the Lambs", "Saving Private Ryan", 
	            "Spirited Away", "The Green Mile", "Parasite"
	        };

        String[] ratings = {
            "R", "R", "PG-13", "PG-13", "PG-13", "R", 
            "R", "R", "PG-13", "R", "R", "R", 
            "PG", "R", "R"
        };

        String[] duration = {
            "142", "175", "152", "142", "148", "136", 
            "154", "139", "169", "146", "118", "169", 
            "125", "189", "132"
        };
        
        DVD[] myDVD = new DVD[15];
        for (int i = 0; i < 15; i++) {
        	int runningTime = Integer.parseInt(duration[i]);
        	myDVD[i] = new DVD(titles[i], ratings[i], runningTime);
        }
        
        // case 1: write to empty file
        DVDCollection dvdCollection = new DVDCollection();
		String filename = "dvddatatest.txt";
		dvdCollection.loadData(filename);
		
		// add 4 new dvds
		for (int i = 0; i < 4; i++) {
			dvdCollection.addOrModifyDVD(titles[i], ratings[i], duration[i]);
		}
		// verify
		dvdCollection.save();
		// Forrest Gump,PG-13,142
		// The Dark Knight,PG-13,152
		// The Godfather,R,175
		// The Shawshank Redemption,R,142
        
		return false; // STUB
	}
}
