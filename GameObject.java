package game;

import java.awt.Graphics;
import java.awt.Rectangle;

public abstract class GameObject {

	protected ObjectID id;
	protected int x;
	protected int y;
	protected int velocityX;
	protected int velocityY;

	public GameObject(ObjectID id, int x, int y) {
		this.id = id;
		this.x = x;
		this.y = y;
	}

	public abstract void tick();

	public abstract void render(Graphics g);

	public abstract Rectangle getBounds();

	public ObjectID getId() {
		return id;
	}

	public void setId(ObjectID id) {
		this.id = id;
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public int getVelocityX() {
		return velocityX;
	}

	public void setVelocityX(int velocityX) {
		this.velocityX = velocityX;
	}

	public int getVelocityY() {
		return velocityY;
	}

	public void setVelocityY(int velocityY) {
		this.velocityY = velocityY;
	}

}
