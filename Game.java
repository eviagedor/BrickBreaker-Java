package game;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;

import game_objects.Ball;
import game_objects.GameObject;
import game_objects.ObjectID;
import game_objects.Paddle;

public class Game extends Canvas implements Runnable {

	public static void main(String[] args) {
		new Game();
	}

	private static final long serialVersionUID = -8056194281387030261L;

	private static final int WIDTH = 325;
	private static final int HEIGHT = 500;

	private Thread thread;
	private boolean running = false; // is the thread running?

	private ObjectHandler handler = new ObjectHandler(); // create a singleton for handling all game objects

	public Game() {
		GameObject player = new Paddle(ObjectID.PADDLE, WIDTH / 2, HEIGHT - 100);
		GameObject ball = new Ball(ObjectID.BALL, WIDTH / 2, HEIGHT / 2);

		this.addKeyListener(new KeyInput(player));

		new Window(WIDTH, HEIGHT, "This is a game", this);

		handler.addObject(player);
		handler.addObject(ball);
	}

	/**
	 * Start a new thread for this instance of Game. Aids in performance.
	 */
	public synchronized void start() {
		thread = new Thread(this);
		thread.start(); // call the run method
		running = true;
	}

	/**
	 * Kill the running thread.
	 */
	public synchronized void stop() {
		try {
			thread.join();
			running = false;
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Game-loop algorithm used by Notch, creator of Minecraft
	 */
	@Override
	public void run() {
		// Initialization phase
		long lastTime = System.nanoTime();
		double amountOfTicks = 60.0;
		double ns = 1000000000 / amountOfTicks;
		double delta = 0;
		long timer = System.currentTimeMillis();
		int frames = 0;

		// loop will update the frames and draw objects onto the canvas
		while (running) {
			// Update phase
			long now = System.nanoTime();

			delta += (now - lastTime) / ns;
			lastTime = now;

			while (delta >= 1) {
				tick();
				delta--;
			}

			// Draw phase
			if (running) {
				render();
			}

			// Back to updating the frames
			frames++;

			if (System.currentTimeMillis() - timer > 1000) {
				timer += 1000;
				// System.out.println("FPS: " + frames);
				frames = 0;
			}
		}
		stop(); // kill the thread when game is no longer running
	}

	private void tick() {
		handler.tick();
	}

	private void render() {
		BufferStrategy buff_st = this.getBufferStrategy();

		if (buff_st == null) {
			this.createBufferStrategy(3);
			return;
		}

		Graphics g = buff_st.getDrawGraphics();

		g.setColor(Color.black);
		g.fillRect(0, 0, WIDTH, HEIGHT);

		handler.render(g);

		g.dispose();
		buff_st.show();
	}
}
