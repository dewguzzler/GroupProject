import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;
import java.text.DecimalFormat;
import java.text.NumberFormat;

import javax.sound.sampled.Clip;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;

/**
 * Class: GamePanel.java
 * 
 * @author Aida Syrkett
 * @version 1.0 Written: Oct 29, 2013
 * 
 */
public class GamePanel extends JPanel {
	private static final long serialVersionUID = 1L;
	private static final NumberFormat FORMATTER = new DecimalFormat("0000");

	private HeroShip hero;
	private Clip backgroundMusic;
	private JPanel pointsPanel;
	private JPanel livesPanel;
	private Graphics graph;
	private JTextArea pointsTextArea;
	private JTextArea livesTextArea;
	private int points;
	private int lives;
	private int level;
	public Missileship mship;
	public String name;
	private GamePanel gamePanel;
	private WelcomeScreenPanel wsp;
	private Clip firingSound;
	private EnemyShip enemy;
	private EnemyShip[][] enemyArray;
	private int enemyShipX = 100;
	private int enemyShipY = 100;
	private int firstTime = 0;
	private static Game game;
	private EnemyMovement em;
	private static Thread enm;
	private static int attempts = 1;

	int call = 1;

	public GamePanel(Game game2) {
		super(new BorderLayout());
		hero = new HeroShip();
		graph = super.getGraphics();
		GamePanel.game = game2;
		enemyArray = new EnemyShip[4][5];
		for (int i = 0; i < 4; i++) {
			for (int j = 0; j < 5; j++) {
				enemyArray[i][j] = new EnemyShip(enemyShipX * j, enemyShipY
						+ 40 * i);

			}
		}

		initPointsPanel();
		initLivesPanel();
	}

	private void initPointsPanel() {
		points = 0;

		pointsPanel = new JPanel();
		pointsPanel.setOpaque(false);

		pointsTextArea = new JTextArea("POINTS " + FORMATTER.format(points));
		pointsTextArea.setFont(Game.getGameFont().deriveFont((float) 40));
		pointsTextArea.setEditable(false);
		pointsTextArea.setForeground(Color.WHITE);
		pointsTextArea.setOpaque(false);

		pointsPanel.add(pointsTextArea);
		add(pointsPanel, BorderLayout.EAST);
	}

	private void initLivesPanel() {
		lives = 3;

		livesPanel = new JPanel();
		livesPanel.setOpaque(false);

		livesTextArea = new JTextArea("LIVES " + lives + "/3");
		livesTextArea.setFont(Game.getGameFont().deriveFont((float) 40));
		livesTextArea.setEditable(false);
		livesTextArea.setForeground(Color.WHITE);
		livesTextArea.setOpaque(false);

		livesPanel.add(livesTextArea);
		add(livesPanel, BorderLayout.WEST);
	}

	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		graph = super.getGraphics();
		ImageIcon background = new ImageIcon(
				GamePanel.class.getResource("res/images/background.png"));
		g.drawImage(background.getImage(), 0, 0, getWidth(), getHeight(), this);
		hero.draw(g);
		if (enemyArray != null) {
			for (int i = 0; i < 4; i++) {
				for (int j = 0; j < 5; j++) {
					EnemyShip es = enemyArray[i][j];
					if (es == null) {

					} else {
						es.drawEnemy(g);
					}
				}
			}
		}

	}

	public void playBackgroundMusic() {
		if (backgroundMusic == null) {
			backgroundMusic = AudioRunnable
					.getClip("res/audio/KingGalaxian.au");
		}
		if (!backgroundMusic.isRunning()) {
			backgroundMusic.setFramePosition(0);
			backgroundMusic.loop(Clip.LOOP_CONTINUOUSLY);
		}
	}

	public void playFiringMusic() {
		if (firingSound == null) {
			firingSound = AudioRunnable.getClip("res/audio/firingSound.au");
		}
		if (!firingSound.isRunning()) {
			firingSound.setFramePosition(0);
			firingSound.loop(0);
		}
	}

	public void stopBackgroundMusic() {
		if (backgroundMusic != null) {
			backgroundMusic.stop();
		}
	}

	public void moveHeroLeft() {
		if (hero.getX() > 40) {
			hero.setX(hero.getX() - 10);
			repaint();
		}
	}

	public void moveHeroRight() {
		if (hero.getX() < 760) {
			hero.setX(hero.getX() + 10);
			repaint();
		}
	}

	public int getHeroX() {
		int x = hero.getX();
		return x;
	}

	public int getHeroY() {
		int y = hero.getY();
		return y + 30;
	}

	public void startMoving(Game g, WelcomeScreenPanel wp) {
		this.wsp = wp;
		enm = null;
		// System.out.println("moving");

		if (enm == null) {

			try {
				// System.out.println(call);
				EnemyMovement em = new EnemyMovement(enemyArray, graph, this,
						hero, g, true, lives);
				enm = new Thread(em);
				enm.start();
				call++;
				// int enMissileY = 2;
				// int enmissileX = 1;
				// EnemyShip shipShooting = enemyArray[2][1];
				// shootMissile(shipShooting.getX(), shipShooting.getY(),
				// gamePanel, 5, false, g);
				// System.out.println(enm.isAlive() + " " +
				// enm.isInterrupted());
				if (enm.isInterrupted()) {
					System.out.println("inter");
					throw new InterruptedException();
				}
			} catch (InterruptedException p) {

			} catch (Exception e) {

				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	public void startMovingAgain(Game g, WelcomeScreenPanel wsp2) {

		try {
			System.out.println("movingagain");
			em = new EnemyMovement(enemyArray, graph, this, hero, g, true,
					lives);
			enm = new Thread(em);
			enm.start();
			if(enm.isInterrupted()){
				throw new InterruptedException();
			}
		}catch (InterruptedException p) {
			System.out.println("jjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjj");
		}
			catch (Exception e) {
		
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void moveMissile() throws Exception {
		if (mship.getY() > 0) {

			mship.setY(mship.getY() - 30);
			// System.out.println(mship.getY());
			// mship.setVisible(false);
			// repaint();
			// repaint();
			graph.drawImage(mship.getIcon().getImage(), mship.getX(),
					mship.getY(), this);
			mship.setOpaque(false);
			// mship.draw(graph);
			// mship.update(graph);
			// Thread.sleep(24);
			// moveMissile();
		}

	}

	public void paintMissile(Missileship ms, int y, int x) {

		// mship = new Missileship();
		mship.setX(x);
		mship.setY(y);
		graph.drawImage(mship.getIcon().getImage(), x, y, 10, 20, this);
		// mship.draw(graph);
		// mship.repaint();
		// repaint();
	}

	public void paintEnemyExplosion(int y, int x) {
		// System.out.println("the fuck");
		Explosion ex = new Explosion();
		ex.setY(y);
		ex.setX(x);
		graph.drawImage(ex.getIcon().getImage(), x, y, 50, 50, this);
		points = points + 15;
		pointsTextArea.setText("POINTS " + FORMATTER.format(points));
		// repaint();
		// mship.draw(graph);
		// mship.repaint();
		// repaint();
	}

	public void paintPlayerExplosion(int y, int x, Game jp) throws Exception {
		// System.out.println("the fuck");
		Explosion ex = new Explosion();
		ex.setY(hero.getY());
		ex.setX(hero.getX());
		// mship = new Missileship();
		// mship.setX(x);
		// mship.setY(y);
		graph.drawImage(ex.getIcon().getImage(), hero.getX() - 60,
				hero.getY() - 100, 250, 250, this);
		System.out.println(lives);

		if (lives - 1 == 0) {
			lives = lives - 1;
			livesTextArea.setText("LIVES " + lives + "/3");
			System.out.println("Game Over");

			//showGameOverScreen(jp);
			game.switchToWelcomeScreenPanel();

			lives = 3;
			livesTextArea.setText("LIVES " + lives + "/3");
			points = 0;
			pointsTextArea.setText("POINTS " + FORMATTER.format(points));
			enemyArray = null;
			enemyArray = new EnemyShip[4][5];
			for (int i = 0; i < 4; i++) {
				for (int j = 0; j < 5; j++) {
					enemyArray[i][j] = new EnemyShip(enemyShipX * j, enemyShipY
							+ 40 * i);

				}
			}
			attempts = 1;

		}
		else if (lives - 1 > 0) {
			lives = lives - 1;
			livesTextArea.setText("LIVES " + lives + "/3");
			startLevelOver(jp);
		}

		// repaint();
		// mship.draw(graph);
		// mship.repaint();
		// repaint();
	}

	public void startLevelOver(Game jp) throws Exception {
		
		attempts++;
		if (attempts < 5) {
			if (enm.isInterrupted()) {
				System.out.println("hello");
			}
			enemyArray = null;
			enemyArray = new EnemyShip[4][5];
			for (int i = 0; i < 4; i++) {
				for (int j = 0; j < 5; j++) {
					enemyArray[i][j] = new EnemyShip(enemyShipX * j, enemyShipY
							+ 40 * i);

				}
			}
			repaint();
			getBottom(enemyArray);
			startMovingAgain(jp, wsp);
		}
		else {
			enm.interrupt();
			throw new InterruptedException();
		}

	}

	public void startNextLevel(Game jp, int level) throws Exception {
		jp.switchToLevelPanel(level);
	}

	public void showGameOverScreen(Game jp) {
		jp.switchToWelcomeScreenPanel();
		JFrame gameOver = new JFrame("Game Over");
		gameOver.setLocationRelativeTo(null);
		JPanel go = new JPanel();
		em = null;
		JLabel over = new JLabel("Game Over");
		go.add(over);
		gameOver.add(go);
		gameOver.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		gameOver.pack();
		gameOver.setVisible(true);
		repaint();
		wsp.repaint();

	}

	public void shootMissile(int x, int y, GamePanel g, int j, boolean user,
			Game cp) throws Exception {

		// y = hero.getY();
		// System.out.println(x + " " + y);
		// mship.setX(hero.getX());
		// mship.setY(y-60);
		// mship.draw(graph);

		gamePanel = g;
		// mship.setX(x);
		// mship.setY(ys);
		// mship.draw(graph);
		// mship.setOpaque(false);

		// moveMissile();
		// System.out.println(ms);
		name = "mrs" + j;
		if (user) {
			mship = new Missileship();
			MissileRunnable name = new MissileRunnable(graph, x + 15, y - 20,
					mship, g, "up", hero, cp, enemyArray);
			Thread miss = new Thread(name);
			if (!miss.isAlive()) {
				miss.start();
				// playFiringMusic();
			}
		}
		if (!user) {

			mship = new Missileship();
			EnemyMissileRunnable names = new EnemyMissileRunnable(graph,
					x + 15, 0, mship, g, "down", hero, cp);
			Thread misss = new Thread(names);
			if (!misss.isAlive()) {
				misss.start();
			}
		}

		// //stopBackgroundMusic();
		// if(miss.isInterrupted()) {
		// playBackgroundMusic();
		// }
		// if(miss.isAlive()){
		//
		// System.out.println("interuppted");
		// }

		// graph.drawImage(missile, x+30, y-30, 20, 50, this);
		// System.out.println(x + " " + y);
	}
	
	public void livesUp(){
		System.out.println("ended");
		game.switchToWelcomeScreenPanel();
	}

	public int getBottom(EnemyShip[][] es) {
		int bottom = 0;
		for (int i = 0; i < 4; i++) {
			for (int j = 0; j < 5; j++) {
				EnemyShip ens = es[i][j];
				if (!ens.isHit()) {
					if (ens.getY() > bottom) {
						bottom = ens.getY();
					}
				}
			}
		}
		return bottom;
	}

}