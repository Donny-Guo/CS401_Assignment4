public class DVDGUITestDrive {

	public static void main(String[] args) {
		
		DVDGUI dlInterface;
		DVDCollection dl = new DVDCollection();
		dlInterface = new DVDGUI(dl);
		dlInterface.showMessage();
//		dlInterface.loadData();
//		dlInterface.processCommands();

		
	}


}
