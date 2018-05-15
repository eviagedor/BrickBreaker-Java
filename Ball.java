package game;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

public class Ball extends GameObject {

	ObjectHandler handler;
	
	public Ball(int x, int y, ObjectHandler handler) {
		super(ObjectID.BALL, x, y);
		this.handler = handler;
		setVelocityX(3);
		setVelocityY(3);
	}
	
	private void collisionDetection() {
		GameObject paddle = handler.getObject(0);
		
		if(getBounds().intersects(paddle.getBounds())) {
			velocityX *= -1;
			velocityY *= -1;
		}
	}

	@Override
	public void tick() {
		x += velocityX;
		y += velocityY;
		
		if (x <= 0 || x >= Game.WIDTH - 32) {
			velocityX *= -1; // reverse the direction
			//System.out.println("Hit left or right");
		}

		if (y <= 0) {
			velocityY *= -1; // reverse the direction
			//System.out.println("Hit top");
		} 
		
		if(y >= Game.HEIGHT) {
			velocityX = 0;
			velocityY = 0;
		} 
		// did the ball hit the paddle?
		collisionDetection();
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
