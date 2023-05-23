package assignment4;

import javax.swing.*;

/**
 * Main entry for assignment 4.
 *
 * @author Jagtej Sidhu
 */
public class Controller {
	private MainPanel mainPanel;
	private Thread reader;
	private Thread writer;
	private Thread modifier;
	private BoundedBuffer buffer;


	public static void main(String[] args) {
		try {
			for (UIManager.LookAndFeelInfo lnf : UIManager.getInstalledLookAndFeels()) {
				if (lnf.getName().contains("Nimbus")) {
					UIManager.setLookAndFeel(lnf.getClassName());
					break;
				}
			}
		} catch (Exception e) {
		}

		Controller controller = new Controller();
		controller.showFrame();
	}

	private void showFrame() {
		mainPanel = new MainPanel(this);

		JFrame frame = new JFrame("Text File Editor by Jagtej Sidhu");
		frame.add(mainPanel);
		frame.pack();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
	}

	/**
	 * Creates the bounded buffer and reader/writer/modifier threads.
	 *
	 * @param target where to store final result (GUI)
	 * @param lines source lines
	 * @param find string to find
	 * @param replace replace with this string
	 */
	public void execute(JTextArea target, String[] lines, String find, String replace)
	{
		buffer = new BoundedBuffer(15, find, replace);

		reader = new Thread(new Reader(buffer, lines.length, target), "Reader");
		writer = new Thread(new Writer(buffer, lines), "Writer");
		modifier = new Thread(new Modifier(buffer, lines.length), "Modifier");

		reader.start();
		writer.start();
		modifier.start();


		try {

			modifier.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
