package game;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class KeyInput extends KeyAdapter {

	private GameObject object;

	public KeyInput(GameObject object) {
		this.object = object;
	}

	public void keyPressed(KeyEvent e) {
		if (object.getId() == ObjectID.PADDLE) {
			if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
				object.setVelocityX(5);
			} else if (e.getKeyCode() == KeyEvent.VK_LEFT) {
				object.setVelocityX(-5);
			}
		}
	}

	public void keyReleased(KeyEvent e) {
		if (object.getId() == ObjectID.PADDLE) {
			if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
				object.setVelocityX(0);
			} else if (e.getKeyCode() == KeyEvent.VK_LEFT) {
				object.setVelocityX(0);
			}
		}
	}

}
