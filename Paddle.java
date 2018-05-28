package game;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

public class Paddle extends GameObject {

	public Paddle(int x, int y, int width, int height) {
		super(x, y, width, height); 
	}
	
	@Override
	public void tick() {
		x += velocityX;
	}

	@Override
	public void render(Graphics g) {
		g.setColor(Color.white);
		g.fillRect(x, y, width, height);
	}

	@Override
	public Rectangle getBounds() {
		return new Rectangle(x, y, width, height);
	}
}
