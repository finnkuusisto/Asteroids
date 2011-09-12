import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Toolkit;
import java.awt.image.BufferStrategy;

public class App {
	
	private Frame frame;
	private Canvas canvas;
	private BufferStrategy canvasBuffer; //on which everything is drawn
	private ScreenManager screenManager;
	
	private final long NANOSEC_PER_SEC = 1000000000L;
	private final long NANOSEC_PER_MS = 1000000L;
	private final long FRAMES_PER_SEC = 60L;
	private final long NANOSECS_PER_FRAME = NANOSEC_PER_SEC / FRAMES_PER_SEC;
	private final long TICKS_PER_SECOND = 30L;
	
	public static final int WIDTH = 800;
	public static final int HEIGHT = 600;
	
	public void init() {
		//init window crap
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		this.frame = new Frame("Asteroids");
		this.frame.setIgnoreRepaint(true);
		this.frame.setResizable(false);
		this.canvas = new Canvas();
		this.canvas.setIgnoreRepaint(true);
		this.canvas.setPreferredSize(new Dimension(App.WIDTH, App.HEIGHT));
		this.canvas.setBackground(Color.BLACK);
		this.frame.add(canvas);
		this.frame.pack();
		this.frame.setLocation((screenSize.width / 2) - (App.WIDTH / 2),
				(screenSize.height / 2) - (App.HEIGHT / 2));
		this.canvas.createBufferStrategy(2);
		this.canvasBuffer = this.canvas.getBufferStrategy();
		//init controller
		this.canvas.addKeyListener(Controller.getListener());
		this.frame.addKeyListener(Controller.getListener());
		this.canvas.addFocusListener(Controller.getListener());
		this.canvas.addMouseListener(Controller.getListener());
		this.canvas.addMouseMotionListener(Controller.getListener());
		//init screens
		this.screenManager = new ScreenManager();
		this.screenManager.addScreen(new GameScreen(), "game");
		this.screenManager.setActiveScreen("game");
		//show the Frame
		this.frame.setVisible(true);
	}
	
	public void stop() {
		this.frame.setVisible(false);
		this.frame.dispose();
	}

	public int run() {
		this.init();
		boolean running = true;
		
		long lastFrame = System.nanoTime();
		long lastUpdate = System.nanoTime();
		long frameCount = 0;
		long nextFrame = 0;
		while (running) {
			Screen screen = this.screenManager.getActiveScreen();
			if (screen == null) {
				return -1; //error
			}
			//compute ticks since last update
			long currTime = System.nanoTime();
			double ticksPassed = (((double)(currTime - lastUpdate) / 
					this.NANOSEC_PER_SEC) * this.TICKS_PER_SECOND);
			lastUpdate = currTime;
			//update
			screen.update(ticksPassed);
			//compute FPS
			currTime = System.nanoTime();
			if (currTime - lastFrame >= this.NANOSEC_PER_SEC) {
				System.out.println("FPS: " + frameCount);
				lastFrame = currTime;
				frameCount = 0;
			}
			else {
				frameCount++;
			}
			nextFrame = currTime + this.NANOSECS_PER_FRAME;
			//render
			Graphics g = null;
			try {
				g = this.canvasBuffer.getDrawGraphics();
				g.fillRect(0, 0, App.WIDTH, App.HEIGHT);
				screen.render(g);
			}
			finally {
				g.dispose();
				this.canvasBuffer.show();
				this.canvas.getToolkit().sync();
				//sleep a little if needed
				long msUntilNextFrame = (nextFrame - System.nanoTime()) /
										this.NANOSEC_PER_MS;
				if (msUntilNextFrame > 0) {
					try {
						Thread.sleep(msUntilNextFrame);
					} catch (InterruptedException e) {}
				}
			}
			//check if quit
			if (Controller.isKeyDown(Controller.K_ESCAPE)) {
				running = false;
			}
		}
		
		this.stop();
		return 0;
	}
	
}
