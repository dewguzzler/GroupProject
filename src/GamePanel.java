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
 * @authors Aida Syrkett, Jesse Perkins, Everett Rucker
 * @version 1.0 Written: Oct 29, 2013
 * 
 * This class - combines all panels together for the game to be played. Paints in ships, backgrounds and shows ship movement
 * 
 * Purpose - So the player can see what they are doing
 * 
 */
public class GamePanel extends JPanel {
 private static final long serialVersionUID = 1L;
 private static final NumberFormat FORMATTER = new DecimalFormat("0000");

 private HeroShip hero; //HeroShip method from HeroShip.java
 private Clip backgroundMusic; //music
 private JPanel pointsPanel; //panel where the # of points are stored
 private JPanel livesPanel; //panel for the # of lives left
 private JPanel levelPanel; //panel for displaying the current level
 private JPanel infoPanel; //panel for showing information
 private Graphics graph; //used for painting images
 private JTextArea pointsTextArea; // describes # of points
 private JTextArea livesTextArea; //describes # of lives
 private JTextArea levelTextArea; //describes what level the player is on
 private int points = 0; //initial amount of points at the beginning of the game
 private int lives; //# of lives
 private int level = 1; //initial level game starts at
 public Missileship mship; //represents the Missileship method in Missileship.java
 public String name;
 private GamePanel gamePanel; //represents GamePanel method from GamePanel.java
 private WelcomeScreenPanel wsp; //represents the WelcomeScreenPanel method from WelcomeScreenPanel.java
 private Clip firingSound; //audio used when laser is fired
 private EnemyShip enemy; //represents EnemyShip method from EnemyShip.java
 private EnemyShip[][] enemyArray; //represents an array of images from the EnemyShip method
 private int enemyShipX = 100; //initial x coordinate of the first enemy ship
 private int enemyShipY = 100; //initial y coordinate of the first enemy ship
 private static Game game; //represents Game method from Game.java
 private EnemyMovement em; //represents EnemyMovemement method from EnemyMovement.java
 private static Thread enm; //thread created for enemy movements
 private static int attempts = 1; //initial # of attempts to complete a level
 int call = 1;

 /** Method: GamePanel* adds the hero, an array of enemies, the infoPanel, pointsPanel, livesPanel
  * and scorePanel to the GamePanel
  * 
  *
  */
 
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
 
 /** Method: initInfoPanel* adds attributes to the infoPanel and places 
  * livesPanel, levelPanel and pointsPanel inside of it
  * 
  *
  */
 private void initInfoPanel() {
  infoPanel = new JPanel();
  infoPanel.setOpaque(false);
  infoPanel.setSize(1000, 100);
  infoPanel.add(livesPanel, BorderLayout.WEST);
  infoPanel.add(levelPanel);
  infoPanel.add(pointsPanel, BorderLayout.EAST);
  add(infoPanel, BorderLayout.NORTH);
 }
 
 /** Method: initPointsPanel* sets up attributes for the pointsPanel and 
  * places pointsTextArea in points Panel. pointsTextArea updates when the number
  * of points change
  * 
  */
 
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

 /** Method: initLivesPanel* sets up attributes for the livesPanel and 
  * places livesTextArea in livesPanel. livesTextArea updates when the number
  * of lives change
  * 
  */
 
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
 
 /** Method: initLevelPanel* sets up attributes for the levelPanel and 
  * places levelTextArea in levelPanel. levelTextArea updates when the level
  * changes
  * 
  */
 
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
 
 /** Method: paintComponent* paints the background and all ships in the game panel
  * @param Graphics g paints 
  */
 
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
 
 /** Method: playBackgoundMusic plays the music while the user plays the game
  * continuously.
  * 
  */
 
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

 /** Method: playingFiringMusic plays the audio sound for firing the missle
  * 
  */
 
 public void playFiringMusic() {
  if (firingSound == null) {
   firingSound = AudioRunnable.getClip("res/audio/firingSound.au");
  }
  if (!firingSound.isRunning()) {
   firingSound.setFramePosition(0);
   firingSound.loop(0);
  }
 }
 
 /** Method: stopBackgroundMusic* stops the audio
  * 
  */
 
 public void stopBackgroundMusic() {
  if (backgroundMusic != null) {
   backgroundMusic.stop();
  }
 }

 /** Method: moveHeroLeft repaints the hero image left
  * 
  */
 
 public void moveHeroLeft() {
  if (hero.getX() > 40) {
   hero.setX(hero.getX() - 10);
   repaint();
  }
 }
 
 /** Method: moveHeroLeft repaints the hero image right
  * 
  */
 
 public void moveHeroRight() {
  if (hero.getX() < 760) {
   hero.setX(hero.getX() + 10);
   repaint();
  }
 }
 
 /** Method: getHeroX returns x coordinate of the hero
  * @return x
  */
 
 public int getHeroX() {
  int x = hero.getX();
  return x;
 }

 /** Method: getHeroX returns y coordinate of the hero
  * @return y
  */
 
 public int getHeroY() {
  int y = hero.getY();
  return y + 30;
 }

 /** Method: startMoving* starts the thread for enemy movement
  * @param Game g is the method Game from Game.java
  * @param WelcomeScreenPanel wp is the method WelcomeScreenPanel from WelcomeScreenPanel.java
  */
 
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
 
 
 /** Method: startMovingAgain* starts the thread for enemy movement after it has stopped
  * @param Game g is the method Game from Game.java
  * @param WelcomeScreenPanel wsp2 is the method WelcomeScreenPanel from WelcomeScreenPanel.java
  */
 
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
 
 /** Method: moveMissle* moves the missile up the panel then fades it out
  * 
  */
 
 public void moveMissile() throws Exception {
  if (mship.getY() > 0) {

   mship.setY(mship.getY() - 30);
   graph.drawImage(mship.getIcon().getImage(), mship.getX(),
     mship.getY(), this);
   mship.setOpaque(false);
  }

 }

 /** Method: moveUpLevel* changes the levelTextArea after every level
  * after all enemy ships are destroyed. When level = 4, the panel is switched 
  * to the welcome screen and player wins
  */
 
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
 
 /** Method: paintMissile* paints the missile and sets it in the gamepanel
  * @param Missileship ms is the method Missileship from Missileship.java
  * @param int y is the y coordinate of the missile
  * @param int x is the x coordinate of the missile
  */
 
 public void paintMissile(Missileship ms, int y, int x) {

  
  mship.setX(x);
  mship.setY(y);
  graph.drawImage(mship.getIcon().getImage(), x, y, 10, 20, this);
 }

 /** Method: paintEnemyExplosion* paints the explosions after an enemy ship is shot
  * It also adds the number of points the ship was worth to the total score
  * @param int enPoints is the number of points an enemy is worth
  * @param int y is the y coordinate of the explosion
  * @param int x is the x coordinate of the explosion
  */
 
 public void paintEnemyExplosion(int y, int x, int enPoints) {
  Explosion ex = new Explosion();
  ex.setY(y);
  ex.setX(x);
  graph.drawImage(ex.getIcon().getImage(), x, y, 50, 50, this);
  points = points + enPoints;
  pointsTextArea.setText("POINTS " + FORMATTER.format(points));
 }

 /** Method: paintPlayerExplosion* paints the explosions after a hero fails
  * Lives are decreases after each failure until game over and switches to game over screen
  * @param int enPoints is the number of points an enemy is worth
  * @param int y is the y coordinate of the explosion
  * @param int x is the x coordinate of the explosion
  */
 
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
 
 /** Method: startLevelOver* enables the player to continue after losing a life
  * @param Game jp is the Game method from Game.java
  */
 
 public void startLevelOver(Game jp) throws Exception {

  Thread.currentThread();
  Thread.sleep(2000);
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
 
 /** Method: startNextLevel* switches the player to the next level panel
  */
 
 public void startNextLevel(Game jp, int level) throws Exception {
  jp.switchToLevelPanel(level);
 }

 /** Method: showGameOverScreen* switches to the game over panel
  * @param Game jp is the Game method from Game.java
  */
 
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

 /** Method: shootMissle* determines if a missile hits an enemy
  * @param Game cp is the Game method from Game.java
  * @param int x is the x coordinate of the missile
  * @param int y is the y coordinate of the missile
  * @param boolean user returns true
  * @param GamePanel g is the GamePanel method from GamePanel.java
  */
 
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
  
  }
 
 /** Method: livesUp* changes screen to the welcome panel
  * 
  *
  */
 
 public void livesUp() {
  game.switchToWelcomeScreenPanel();
 }
 
 /** Method: getBottom* finds the y coordinate of the current bottom row
  * @param EnemyShip[][] es is an array of enemy ships
  *@returns bottom, the y coordinate of the current bottom row
  */
 
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