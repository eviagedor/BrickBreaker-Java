package game;

import java.awt.Canvas;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;
import java.awt.Color;

public class Game extends Canvas implements Runnable {

	public static void main(String[] args) {
		new Game();
	}
	
	private static final long serialVersionUID = -8056194281387030261L;
	
	private static final int WIDTH = 640;
	private static final int HEIGHT = 640;
	
	private Thread thread;
	private boolean running = false; // is the thread running?
	
	public Game() {
		new Window(WIDTH, HEIGHT, "This is a game", this);
	}
	
	public synchronized void start() {
		thread = new Thread(this);
		thread.start();
		running = true;
	}
	
	public synchronized void stop() {
		try {
			thread.join();
			running = false;
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void run() {
		long lastTime = System.nanoTime();
		double amountOfTicks = 60.0;
		double ns = 1000000000 / amountOfTicks;
		double delta = 0;
		long timer = System.currentTimeMillis();
		int frames = 0;
		
		while(running) {
			long now = System.nanoTime();
			
			delta += (now - lastTime) / ns;
			lastTime = now;
			
			while(delta >= 1) {
				tick();
				delta--;
			}
			
			if(running) {
				render();
			}
			frames++;
			
			if(System.currentTimeMillis() - timer > 1000) {
				timer += 1000;
				System.out.println("FPS: " + frames);
				frames = 0;
			}
		}
		stop();
	}
	
	private void tick() {
		
	}
	
	private void render() {
		BufferStrategy buff_st = this.getBufferStrategy();
		
		if(buff_st == null) {
			this.createBufferStrategy(3);
			return;
		}
		
		Graphics g = buff_st.getDrawGraphics();
		
		g.setColor(Color.white);
		g.fillRect(0, 0, WIDTH, HEIGHT);
		
		g.dispose();
		buff_st.show();
	}
	
}
