package game;

import java.awt.Color;
import java.awt.Graphics;

public class Ball extends GameObject {

	public Ball(ObjectID id, int x, int y) {
		super(id, x, y);
		setVelocityX(3);
		setVelocityY(3);
	}

	@Override
	public void tick() {
		x += velocityX;
		y += velocityY;

		if (x <= 0 || x >= 325 - 32) {
			velocityX *= -1; // reverse the direction
		}

		if (y <= 0 || y >= 500 - 64) {
			velocityY *= -1; // reverse the direction

			// System.out.println("Game over!");
		} /*
			 * else if (y <= 0) { velocityY *= -1; }
			 */
	}

	@Override
	public void render(Graphics g) {
		g.setColor(Color.WHITE);
		g.fillOval(x, y, 16, 16);
	}

}
