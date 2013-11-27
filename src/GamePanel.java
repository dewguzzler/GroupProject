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
	private JPanel levelPanel;
	private JPanel infoPanel;
	private Graphics graph;
	private JTextArea pointsTextArea;
	private JTextArea livesTextArea;
	private JTextArea levelTextArea;
	private int points = 0;
	private int lives;
	private int level = 1;
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
				if (i == 0) {
					enemyArray[i][j] = new EnemyShip(enemyShipX * j, enemyShipY
							+ 40 * i, 1);
				} else if (i == 1 || i == 2) {
					enemyArray[i][j] = new EnemyShip(enemyShipX * j, enemyShipY
							+ 40 * i, 2);
				} else if (i == 3) {
					enemyArray[i][j] = new EnemyShip(enemyShipX * j, enemyShipY
							+ 40 * i, 3);
				}

			}
		}


		initLevelPanel();
		initPointsPanel();
		initLivesPanel();
		initInfoPanel();
	}
	
	
	private void initInfoPanel() {
		infoPanel = new JPanel();
		infoPanel.setOpaque(false);
		infoPanel.setSize(1000, 100);
		infoPanel.add(livesPanel, BorderLayout.WEST);
		infoPanel.add(levelPanel);
		infoPanel.add(pointsPanel, BorderLayout.EAST);
		add(infoPanel, BorderLayout.NORTH);
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
		//add(pointsPanel, BorderLayout.EAST);
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
		//add(livesPanel, BorderLayout.WEST);
	}
	
	private void initLevelPanel() {
		

		levelPanel = new JPanel();
		levelPanel.setOpaque(false);
		levelPanel.setSize(100, 100);

		levelTextArea = new JTextArea("Level " + Integer.toString(level));
		levelTextArea.setFont(Game.getGameFont().deriveFont((float) 40));
		levelTextArea.setEditable(false);
		levelTextArea.setForeground(Color.WHITE);
		levelTextArea.setOpaque(false);

		levelPanel.add(levelTextArea);
		//add(levelPanel, BorderLayout.NORTH);
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

		if (enm == null) {

			try {
				EnemyMovement em = new EnemyMovement(enemyArray, graph, this,
						hero, g, true, lives, level);
				enm = new Thread(em);
				enm.start();
				if (enm.isInterrupted()) {
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
			em = new EnemyMovement(enemyArray, graph, this, hero, g, true,
					lives, level);
			enm = new Thread(em);
			enm.start();
			if (enm.isInterrupted()) {
				throw new InterruptedException();
			}
		} catch (InterruptedException p) {
		} catch (Exception e) {

			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void moveMissile() throws Exception {
		if (mship.getY() > 0) {

			mship.setY(mship.getY() - 30);
			graph.drawImage(mship.getIcon().getImage(), mship.getX(),
					mship.getY(), this);
			mship.setOpaque(false);
		}

	}

	public void moveUpLevel() {
		level++;
		levelTextArea.setText("Level: " + Integer.toString(level));
		enemyArray = null;
		enemyArray = new EnemyShip[4][5];
		for (int i = 0; i < 4; i++) {
			for (int j = 0; j < 5; j++) {
				if (i == 0) {
					enemyArray[i][j] = new EnemyShip(enemyShipX * j, enemyShipY
							+ 40 * i, 1);
				} else if (i == 1 || i == 2) {
					enemyArray[i][j] = new EnemyShip(enemyShipX * j, enemyShipY
							+ 40 * i, 2);
				} else if (i == 3) {
					enemyArray[i][j] = new EnemyShip(enemyShipX * j, enemyShipY
							+ 40 * i, 3);
				}

			}
		}
		if (level < 4) {
			game.switchToLevelPanel(level);
		}
		if (level == 4) {

			game.switchToWelcomeScreenPanel();
			enemyArray = null;
			enemyArray = new EnemyShip[4][5];
			for (int i = 0; i < 4; i++) {
				for (int j = 0; j < 5; j++) {
					if (i == 0) {
						enemyArray[i][j] = new EnemyShip(enemyShipX * j,
								enemyShipY + 40 * i, 1);
					} else if (i == 1 || i == 2) {
						enemyArray[i][j] = new EnemyShip(enemyShipX * j,
								enemyShipY + 40 * i, 2);
					} else if (i == 3) {
						enemyArray[i][j] = new EnemyShip(enemyShipX * j,
								enemyShipY + 40 * i, 3);
					}

				}
			}
			level = 1;
			levelTextArea.setText("Level: " + Integer.toString(level));
			attempts = 1;
			lives = 3;
			livesTextArea.setText("LIVES " + lives + "/3");
			game.gameOverScreen("Congratulations: You Saved Umaran!", points);
			points = 0;
			pointsTextArea.setText("POINTS " + FORMATTER.format(points));

		}
	}

	public void paintMissile(Missileship ms, int y, int x) {

		
		mship.setX(x);
		mship.setY(y);
		graph.drawImage(mship.getIcon().getImage(), x, y, 10, 20, this);
	}

	public void paintEnemyExplosion(int y, int x, int enPoints) {
		Explosion ex = new Explosion();
		ex.setY(y);
		ex.setX(x);
		graph.drawImage(ex.getIcon().getImage(), x, y, 50, 50, this);
		points = points + enPoints;
		pointsTextArea.setText("POINTS " + FORMATTER.format(points));
	}

	public void paintPlayerExplosion(int y, int x, Game jp) throws Exception {
		
		Explosion ex = new Explosion();
		ex.setY(hero.getY());
		ex.setX(hero.getX());
		graph.drawImage(ex.getIcon().getImage(), hero.getX() - 60,
				hero.getY() - 100, 250, 250, this);

		if (lives - 1 == 0) {
			lives = lives - 1;
			livesTextArea.setText("LIVES " + lives + "/3");
			game.switchToWelcomeScreenPanel();
			game.gameOverScreen(
					"I'm Sorry, Umaran was destroyed.  Please try again",
					points);
			lives = 3;
			livesTextArea.setText("LIVES " + lives + "/3");
			points = 0;
			pointsTextArea.setText("POINTS " + FORMATTER.format(points));
			level = 1;
			levelTextArea.setText("Level: " + Integer.toString(level));
			enemyArray = null;
			enemyArray = new EnemyShip[4][5];
			for (int i = 0; i < 4; i++) {
				for (int j = 0; j < 5; j++) {
					if (i == 0) {
						enemyArray[i][j] = new EnemyShip(enemyShipX * j,
								enemyShipY + 40 * i, 1);
					} else if (i == 1 || i == 2) {
						enemyArray[i][j] = new EnemyShip(enemyShipX * j,
								enemyShipY + 40 * i, 2);
					} else if (i == 3) {
						enemyArray[i][j] = new EnemyShip(enemyShipX * j,
								enemyShipY + 40 * i, 3);
					}

				}
			}
			attempts = 1;
			level = 1;

		} else if (lives - 1 > 0) {
			lives = lives - 1;
			livesTextArea.setText("LIVES " + lives + "/3");
			startLevelOver(jp);
		}
	}

	public void startLevelOver(Game jp) throws Exception {

		Thread.currentThread().sleep(2000);
		attempts++;
		if (attempts < 5) {
			enemyArray = null;
			enemyArray = new EnemyShip[4][5];
			for (int i = 0; i < 4; i++) {
				for (int j = 0; j < 5; j++) {
					if (i == 0) {
						enemyArray[i][j] = new EnemyShip(enemyShipX * j,
								enemyShipY + 40 * i, 1);
					} else if (i == 1 || i == 2) {
						enemyArray[i][j] = new EnemyShip(enemyShipX * j,
								enemyShipY + 40 * i, 2);
					} else if (i == 3) {
						enemyArray[i][j] = new EnemyShip(enemyShipX * j,
								enemyShipY + 40 * i, 3);
					}

				}
			}
			repaint();
			getBottom(enemyArray);
			startMovingAgain(jp, wsp);
		} else {
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

		gamePanel = g;
		name = "mrs" + j;
		if (user) {
			mship = new Missileship();
			MissileRunnable name = new MissileRunnable(graph, x + 15, y - 20,
					mship, g, "up", hero, cp, enemyArray);
			Thread miss = new Thread(name);
			if (!miss.isAlive()) {
				miss.start();
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
	}

	public void shootEnemyMissile(int x, int y, GamePanel g, int j, Game cp)
			throws Exception {

		mship = new Missileship();
		EnemyMissileRunnable names = new EnemyMissileRunnable(graph, x + 15, y,
				mship, g, "down", hero, cp);
		Thread misss = new Thread(names);
		if (!misss.isAlive()) {
			misss.start();
		}
	}

	public void livesUp() {
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