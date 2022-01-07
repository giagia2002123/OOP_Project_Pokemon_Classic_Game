package Controller;

public class Main {
    MainFrame frame;
	class Time extends Thread {
		public void run() {
			while (true) {
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}	
				if(frame.isPause()) {
					if(frame.isResume()) {
						frame.time--;
					}
				} else {
					frame.time--;
				}
				if (frame.time == 0) {
					frame.row = 8;
					frame.col = 8;
					frame.showDialogNewGame("Time up !\nDo you want to play again ?", "Lose", 1);
				}
			}
		}
	}
        
	public Main() {
		frame = new MainFrame();
		Time time = new Time();
		time.start();
		new Thread(frame).start();
	}

	public static void main(String[] args) {
		new Main();
	}
}