package game;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

public class Ball extends GameObject {

	public Ball(int x, int y) {
		super(ObjectID.BALL, x, y);
		setVelocityX(3);
		setVelocityY(3);
	}

	@Override
	public void tick() {
		x += velocityX;
		y += velocityY;
	}

	@Override
	public void render(Graphics g) {
		g.setColor(Color.WHITE);
		g.fillOval(x, y, 16, 16);
	}

	@Override
	public Rectangle getBounds() {
		return new Rectangle(x, y, 16, 16);
	}
}
