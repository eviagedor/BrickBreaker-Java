package game;

import java.awt.Color;
import java.awt.Graphics;

public class Paddle extends GameObject {

	public Paddle(ObjectID id, int x, int y) {
		super(id, x, y);
	}

	@Override
	public void tick() {
		x += velocityX;
		
		if(x <= 0 || x >= 325 - 80) {
			x -= velocityX;
			//System.out.println("NOT MOVING");
		} 
	}

	@Override
	public void render(Graphics g) {
		g.setColor(Color.white);
		g.fillRect(x, y, 64, 10);
	}
}
