import java.util.Scanner;
import javax.swing.*;
import java.awt.Image;

/**
 *  This class is an implementation of DVDUserInterface
 *  that uses JOptionPane to display the menu of command choices. 
 */

public class DVDGUITest implements DVDUserInterface{
	private DVDCollection dvdlist;
	
	public DVDGUITest(DVDCollection dl)
	{
		dvdlist = dl;
	}
	
	
	public void loadData() {
		// Request the name of data file
		String filename;
		do {
			
			filename = JOptionPane.showInputDialog("Enter data file name: ");
			if (filename == null) {
				System.exit(0); // dialog is closed and exit the program
			}
		
		} while (filename.equals("")); // continue to prompt user if filename is empty
		
		String message = this.dvdlist.loadData(filename);
		JOptionPane.showMessageDialog(null, message);
	}
	
	
	public void processCommands(){

		String[] commands = {"Add/Modify DVD",
				"Remove DVD",
				"Get DVDs By Rating",
				"Get Total Running Time",
				"Select",
				"Random",
				"Exit and Save"};

		int choice;
		String message;

		do {
			message = this.dvdlist.listDVDs();

			choice = JOptionPane.showOptionDialog(null,
					message, 
					"DVD Collection", 
					JOptionPane.YES_NO_CANCEL_OPTION, 
					JOptionPane.QUESTION_MESSAGE, 
					null, 
					commands,
					commands[commands.length - 1]);

			switch (choice) {
			case 0: doAddOrModifyDVD(); break;
			case 1: doRemoveDVD(); break;
			case 2: doGetDVDsByRating(); break;
			case 3: doGetTotalRunningTime(); break;
			case 4: doEditDVD(); break;
			case 5: doRandom(); break;
			case 6: doSave(); break;
			default:  // do nothing
			}

		} while (choice != commands.length-1);
		System.exit(0); // exit the program
	}

	
	
	private void doRandom() {
		String[] commands = {"Next",
		"Back"};
		int choice;
		int dvdIndex;
		
		do {
			dvdIndex = (int) (Math.random() * this.dvdlist.getTotalNumOfDVDs());
			String dvdDetails = "";
			dvdDetails += String.format("%-21s %s\n", "Title:", this.dvdlist.getDVDByIndex(dvdIndex).getTitle());
			dvdDetails += String.format("%-19s %s\n", "Rating:", this.dvdlist.getDVDByIndex(dvdIndex).getRating());
			dvdDetails += String.format("Running Time: %d\n", this.dvdlist.getDVDByIndex(dvdIndex).getRunningTime());
			
			
			// prepare for dvd thumbnail icon
			String filename = "thumbnails/" + (dvdIndex % 5 + 1) + ".png";
			ImageIcon icon = new ImageIcon(filename);
			Image image = icon.getImage();
			Image newImage = image.getScaledInstance(200, 200, Image.SCALE_SMOOTH);
			icon = new ImageIcon(newImage);
			
			// get the user choice
			choice = JOptionPane.showOptionDialog(null,
					dvdDetails, 
					"DVD Details", 
					JOptionPane.YES_NO_CANCEL_OPTION, 
					JOptionPane.QUESTION_MESSAGE, 
					icon,
					commands,
					commands[commands.length - 1]);
			
			switch (choice) {
			case 0: continue;
			case 1: break;// back to previous page
			default: // do nothing
			}
		} while (choice != 1);
	}
	
	
	
	private void doEditDVD() {
		String input = "";
		int num;
		
		// Request the DVD No + sanity check 
		while (true) {
			input = JOptionPane.showInputDialog("Enter DVD No: ");
			// if the input is empty, continue prompting for input
			if (input == null || input.equals("")) {
				continue;
			}
			try {
				num = Integer.parseInt(input);
				// show warning message if input is invalid
				if (num <= 0 || num > this.dvdlist.getTotalNumOfDVDs()) {
					JOptionPane.showMessageDialog(null, "Invalid DVD No. Please try again!", "Warning", JOptionPane.ERROR_MESSAGE);
				} else {
					break;
				}
			} catch (Exception ex) { // if input is not integer, display error message
				JOptionPane.showMessageDialog(null, "Invalid DVD No. Please try again!", "Warning", JOptionPane.ERROR_MESSAGE);
			}
			
		} 
		
		int dvdIndex = num - 1;
		showDVDDetail(dvdIndex);
		
	}
	
	
	
	private void showDVDDetail(int dvdIndex) {
		int choice;
		String dvdDetails = "";
		dvdDetails += String.format("%-21s %s\n", "Title:", this.dvdlist.getDVDByIndex(dvdIndex).getTitle());
		dvdDetails += String.format("%-19s %s\n", "Rating:", this.dvdlist.getDVDByIndex(dvdIndex).getRating());
		dvdDetails += String.format("Running Time: %d\n", this.dvdlist.getDVDByIndex(dvdIndex).getRunningTime());
		
		String[] commands = {"Modify Rating",
				"Modify Running Time",
				"Back"};
		
		// prepare for dvd thumbnail icon
		String filename = "thumbnails/" + (dvdIndex % 5 + 1) + ".png";
		ImageIcon icon = new ImageIcon(filename);
		Image image = icon.getImage();
		Image newImage = image.getScaledInstance(200, 200, Image.SCALE_SMOOTH);
		icon = new ImageIcon(newImage);
		
		// get the user choice
		choice = JOptionPane.showOptionDialog(null,
				dvdDetails, 
				"DVD Details", 
				JOptionPane.YES_NO_CANCEL_OPTION, 
				JOptionPane.QUESTION_MESSAGE, 
				icon,
				commands,
				commands[commands.length - 1]);
		
		switch (choice) {
		case 0: modifyDVDRating(dvdIndex); break;
		case 1: modifyDVDRunningTime(dvdIndex); break;
		case 2: break; // back to previous page
		default: break; // back to previous page
		}
	}

	
	
	private void modifyDVDRating(int dvdIndex) {
		String title = this.dvdlist.getDVDByIndex(dvdIndex).getTitle();
		String rating;
		String time = Integer.toString(dvdlist.getDVDByIndex(dvdIndex).getRunningTime());
		// Request the rating
		while (true) {
			rating = JOptionPane.showInputDialog("Enter rating for " + title + " (G, PG, PG-13, NC-17 or R):");
			if (rating == null || rating.equals("")) {
				return;		// dialog was cancelled
			}
			rating = rating.toUpperCase();
			
			// check if it's correct rating.
			if (this.dvdlist.isValidRating(rating)) {
				break;
			}
		}
		// Add or modify the DVD (assuming the rating and time are valid
		dvdlist.addOrModifyDVD(title, rating, time);
		
		// going back to DVD details page
		showDVDDetail(dvdIndex);
	}
	
	
	
	private void modifyDVDRunningTime(int dvdIndex) {
		String title = this.dvdlist.getDVDByIndex(dvdIndex).getTitle();
		String rating = this.dvdlist.getDVDByIndex(dvdIndex).getRating();
		String time;
		// Request the running time
		while (true) {
			time = JOptionPane.showInputDialog("Enter Running Time for " + title);
			if (time == null || rating.equals("")) {
				return;		// dialog was cancelled
			}
			// check if it's correct time.
			if (this.dvdlist.isValidTime(time)) {
				break;
			}
		}
		// Add or modify the DVD (assuming the rating and time are valid
		dvdlist.addOrModifyDVD(title, rating, time);
		
		// going back to DVD details page
		showDVDDetail(dvdIndex);
	}
	
	
	
	private void doAddOrModifyDVD() {
		String title, rating, time;
		
		// Request the title
		title = JOptionPane.showInputDialog("Enter title");
		if (title == null || title.equals("")) {
			return;		// dialog was cancelled
		}
		title = title.toUpperCase();
		
		// Request the rating
		do {
			rating = JOptionPane.showInputDialog("Enter rating for " + title + " (G, PG, PG-13, NC-17 or R):");
			if (rating == null) {
				return;		// dialog was cancelled
			}
			rating = rating.toUpperCase();
		} while (!this.dvdlist.isValidRating(rating)); // break when the rating is valid

		// Request the running time
		do {
			time = JOptionPane.showInputDialog("Enter running time for " + title);
			if (time == null || time.equals("")) {
				return;
			}
		} while (!this.dvdlist.isValidTime(time)); // break when the time is valid

		// Add or modify the DVD (assuming the rating and time are valid
		dvdlist.addOrModifyDVD(title, rating, time);

	}

	
	
	private void doRemoveDVD() {

		// Request the title
		String title = JOptionPane.showInputDialog("Enter title");
		if (title == null || title.equals("")) {
			return;		// dialog was cancelled
		}
		title = title.toUpperCase();

		// Remove the matching DVD if found
		if (dvdlist.removeDVD(title)) {
			// successfully removed
			JOptionPane.showMessageDialog(null, String.format("Successfully removed %s.", title), "Confirmation", JOptionPane.INFORMATION_MESSAGE);
		} else {
			// dvd not found
			JOptionPane.showMessageDialog(null, String.format("DVD not found! Fail to remove %s.", title), "Error", JOptionPane.ERROR_MESSAGE);
		}

	}

	
	
	private void doGetDVDsByRating() {

		// Request the rating
		String rating = JOptionPane.showInputDialog("Enter rating (G, PG, PG-13, NC-17 or R):");
		if (rating == null || rating.equals("")) {
			return;		// dialog was cancelled
		}
		rating = rating.toUpperCase();
		
		String results = dvdlist.getDVDsByRating(rating);
		if (results.equals("")) { // check if there's no DVD with this rating
			results = "You don't have any DVDs with rating " + rating + ".";
		} else {
			results = "DVDs with rating " + rating + ": \n" + results;
		}

		JOptionPane.showMessageDialog(null, results);

	}

	
	
	private void doGetTotalRunningTime() {

		int total = dvdlist.getTotalRunningTime();
		String results = "Total Running Time of DVDs: " + total + " mins.";
		JOptionPane.showMessageDialog(null, results);

	}

	
	
	private void doSave() {
		
		String results = "Successfully saved DVD collections to " + this.dvdlist.getFilename() + ".";
		int choice = JOptionPane.showConfirmDialog(null, "Do you want to save your DVD collections?", "Exit", JOptionPane.YES_NO_OPTION);
		if (choice == 0) { // Yes option (save collections)
			dvdlist.save();
			JOptionPane.showMessageDialog(null, results);
		} else { // No option (no saving)
			JOptionPane.showMessageDialog(null, "Exiting without saving...");
		}
		
	}
}