package game;

import java.awt.Canvas;
import java.awt.Dimension;
import javax.swing.JFrame;

public class Window extends Canvas {

	private static final long serialVersionUID = 318126280877336490L;

	public Window(int width, int height, String title, Game game) {
		JFrame frame = new JFrame(title);
		
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		// set the sizes for the window and location
		frame.setPreferredSize(new Dimension(width, height));
		frame.setMaximumSize(new Dimension(width, height));
		frame.setMinimumSize(new Dimension(width, height));
		frame.setPreferredSize(new Dimension(width, height));
		frame.setResizable(false);
		frame.setLocationRelativeTo(null);
		
		// add the game to the JFrame container and display
		frame.add(game);
		frame.setVisible(true);
		
		// run
		game.start();
	}

}
