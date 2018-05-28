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

	private final int WINDOW_WIDTH = 265;
	private final int WINDOW_HEIGHT = 500;
	
	private final int PADDLE_WIDTH = 64;
	private final int PADDLE_HEIGHT = 10;
	
	private final int BALL_WIDTH = 16;
	private final int BALL_HEIGHT = 16;
	
	private final int BRICK_WIDTH = 45;
	private final int BRICK_HEIGHT = 20;
	
	private final int ROW = 5;
	private final int COL = 8;
	
	private Thread thread;
	private boolean running = false; // is the thread running?
	
	private int score = 0;
	
	private Paddle player;
	private Ball ball;
	private Brick[][] brick = new Brick[ROW][COL];
	
	//private Brick brick = new Brick(1, 1);
	
	private ObjectHandler handler = ObjectHandler.getInstance(); // handles paddle & ball 

	public Game() {
		generateBricks();
		player = new Paddle(WINDOW_WIDTH / 2, WINDOW_HEIGHT - 75, PADDLE_WIDTH, PADDLE_HEIGHT);
		ball = new Ball(WINDOW_WIDTH / 2, WINDOW_HEIGHT / 2, BALL_WIDTH, BALL_HEIGHT);
		
		this.addKeyListener(new PaddleInput(player));

		new Window(WINDOW_WIDTH, WINDOW_HEIGHT, "This is a game", this);

		addToHandler(player);
		addToHandler(ball);
	}
	
	public void generateBricks() {
		for(int i = 0; i < ROW; i++) {
			for(int j = 0; j < COL; j++) {
				brick[i][j] = new Brick(i * 50, (j * 25) + (25 / 2), BRICK_WIDTH, BRICK_HEIGHT);
				addToHandler(brick[i][j]);
			}
		}
	}
	
	public void addToHandler(GameObject object) {
		handler.addObject(object);
	}
	
	public void collisionWall() {
		if (ball.x < 0 || ball.x > WINDOW_WIDTH - 32) {
			ball.velocityX *= -1; // reverse the direction
			// System.out.println("Hit left or right");
		}

		if (ball.y < 0 || ball.y > WINDOW_HEIGHT - 64) {
			ball.velocityY *= -1; // reverse the direction
			// System.out.println("Hit top");
		}
		
		if(player.x < 0 || player.x > WINDOW_WIDTH - 80) {
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
				if(brick[i][j].hit(ball) == true) {
					ball.velocityX *= -1;
					ball.velocityY *= -1;
					System.out.println(++score);
				}
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
	 * Game-loop algorithm. 
	 * Removed logging of FPS.
	 */
	@Override
	public void run() {
		// Initialization phase
		long lastTime = System.nanoTime();
		double amountOfTicks = 60.0;
		double ns = 1000000000 / amountOfTicks;
		double delta = 0;

		// Can the game keep up with our (human) time?
		while (running) {
			
			/**
			 * Update phase
			 */
			long now = System.nanoTime(); // our time

			// ensure that the game maintains steady frames
			delta += (now - lastTime) / ns;
			lastTime = now; // game time is our time

			while (delta >= 1) {
				tick(); // update the objects
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
		g.fillRect(0, 0, WINDOW_WIDTH, WINDOW_HEIGHT);

		handler.render(g);

		g.dispose();
		buff_st.show();
	}

	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}
}
