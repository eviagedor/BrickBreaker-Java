package game;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

public class Brick extends GameObject {

	private boolean isHit = false;
	
	public Brick(int x, int y, int width, int height) {
		super(x, y, width, height);
	}

	public boolean hit(Ball ball) {
		if (ball.getBounds().intersects(getBounds()) && isHit == false) { // cleared brick does not occupy empty space
			isHit = true;
			return isHit;
		} else {
			return false;
		}
	}

	@Override
	public void tick() {
		// Brick does not move so tick just sets velocities to 0
		setVelocityX(0);
		setVelocityY(0);
	}

	@Override
	public void render(Graphics g) {
		if (!isHit) {
			g.setColor(Color.RED);
			g.fillRect(x, y, width, height);
		}
	}

	@Override
	public Rectangle getBounds() {
		return new Rectangle(x, y, width, height);
	}

	public boolean getIsHit() {
		return isHit;
	}

	public void setIsHit(boolean isHit) {
		this.isHit = isHit;
	}

}
