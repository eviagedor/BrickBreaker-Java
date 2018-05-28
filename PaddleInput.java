package game;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class PaddleInput extends KeyAdapter {

	private Paddle paddle;

	public PaddleInput(Paddle paddle) {
		this.paddle = paddle;
	}

	public void keyPressed(KeyEvent e) {
		int key = e.getKeyCode();
		
		if (key == KeyEvent.VK_RIGHT) {
			paddle.setVelocityX(5);
		} else if (key == KeyEvent.VK_LEFT) {
			paddle.setVelocityX(-5);
		}
	}

	public void keyReleased(KeyEvent e) {
		int key = e.getKeyCode();
		
		if (key == KeyEvent.VK_RIGHT) {
			paddle.setVelocityX(0);
		} else if (key == KeyEvent.VK_LEFT) {
			paddle.setVelocityX(0);
		}
	}
}
