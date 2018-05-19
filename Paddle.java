package game;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

public class Paddle extends GameObject {
	
	public Paddle(int x, int y) {
		super(ObjectID.PADDLE, x, y); // we know this is paddle, so assign Paddle ID
	}
	
	@Override
	public void tick() {
		x += velocityX;
	}

	@Override
	public void render(Graphics g) {
		g.setColor(Color.white);
		g.fillRect(x, y, 64, 10);
	}

	@Override
	public Rectangle getBounds() {
		return new Rectangle(x, y, 64, 10);
	}
}
