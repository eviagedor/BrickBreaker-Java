package game;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;

public class Game extends Canvas implements Runnable {

	public static void main(String[] args) {
		new Game();
	}

	private static final long serialVersionUID = -8056194281387030261L;

	private final int WIDTH = 265;
	private final int HEIGHT = 500;
	
	private final int ROW = 5;
	private final int COL = 8;
	
	private Thread thread;
	private boolean running = false; // is the thread running?

	private Paddle player;
	private Ball ball;
	private Brick[][] brick = new Brick[ROW][COL];
	
	//private Brick brick = new Brick(1, 1);
	
	private ObjectHandler handler = ObjectHandler.getInstance(); // handles paddle & ball 

	public Game() {
		generateBricks();
		player = new Paddle(WIDTH / 2, HEIGHT - 75);
		ball = new Ball(WIDTH / 2, HEIGHT / 3);
		
		this.addKeyListener(new KeyInput(player));

		new Window(WIDTH, HEIGHT, "This is a game", this);

		addToHandler(player);
		addToHandler(ball);
	}
	
	public void generateBricks() {
		for(int i = 0; i < ROW; i++) {
			for(int j = 0; j < COL; j++) {
				brick[i][j] = new Brick((i * 50), ((j * 25) + (25 / 2)));
				addToHandler(brick[i][j]);
			}
		}
	}
	
	public void addToHandler(GameObject object) {
		handler.addObject(object);
	}
	
	public void collisionWall() {
		if (ball.x < 0 || ball.x > WIDTH - 32) {
			ball.velocityX *= -1; // reverse the direction
			// System.out.println("Hit left or right");
		}

		if (ball.y < 0 || ball.y > HEIGHT - 64) {
			ball.velocityY *= -1; // reverse the direction
			// System.out.println("Hit top");
		}
		
		if(player.x < 0 || player.x > WIDTH - 80) {
			player.x -= player.velocityX;
		} 
	}
	
	public void collisionPaddle() {
		if(ball.getBounds().intersects(player.getBounds())) {
			ball.velocityX *= -1;
			ball.velocityY *= -1;
		}
	}
	
	public void collisionBrick() {
		for(int i = 0; i < ROW; i++) {
			for(int j = 0; j < COL; j++) {
				brick[i][j].hit(ball);
			}
		}
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
	 * Removed logging of FPS.
	 */
	@Override
	public void run() {
		// Initialization phase
		long lastTime = System.nanoTime();
		double amountOfTicks = 60.0;
		double ns = 1000000000 / amountOfTicks;
		double delta = 0;

		// loop will update the frames and draw objects onto the canvas
		while (running) {
			
			// Update phase
			long now = System.nanoTime();

			delta += (now - lastTime) / ns;
			lastTime = now;

			while (delta >= 1) {
				tick();
				collisionWall();
				collisionPaddle();
				collisionBrick();
				delta--;
			}

			// Draw phase
			if (running) {
				render();
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
