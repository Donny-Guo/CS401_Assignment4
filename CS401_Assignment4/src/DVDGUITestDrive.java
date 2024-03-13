public class DVDGUITestDrive {

	public static void main(String[] args) {
		
		DVDCollection dl = new DVDCollection();
		DVDGUITest dlInterface = new DVDGUITest(dl);
		dlInterface.loadData();
		dlInterface.processCommands();

		
	}


}
