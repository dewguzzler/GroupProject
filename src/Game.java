import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.KeyEventDispatcher;
import java.awt.KeyboardFocusManager;
import java.awt.event.KeyEvent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

/** Class: Game.java
 *
 * @author Aida Syrkett
 * @version 1.0
 * Written: Oct 29, 2013
 * 
 * This class - creates the frame that holds the whole game. Also controls all key actions for hero.
 * It allows the user to switch between each panel with key strokes
 * 
 * Purpose - to store the game in a frame and allow the players to make movements
 *
 */
public class Game extends JFrame implements KeyEventDispatcher
{ 
 private static final long serialVersionUID = 1L;
 private static final Dimension DEFAULT_SIZE = new Dimension(900, 600); //frame dimension
 
 private JPanel centerPanel; //center panel that stores everything
 private WelcomeScreenPanel welcomeScreenPanel; //stores the text for the welcome screen
 private GamePanel gamePanel; //where the game is played
 private LevelScreenPanel levelOnePanel; //panel for the first level
 private LevelScreenPanel levelTwoPanel; //panel for the second level
 private LevelScreenPanel levelThreePanel; //panel for the 3rd level
 public int j = 0; //
 private boolean user; //boolean to shoot
 private static Game game; //returns the Game method from Game.java

  /** Method: Game* creates a gui that holds all the game's panels together
   * 
   */
 
 public Game()
 {
  super("Galaxian");
  game = this;
  setSize(DEFAULT_SIZE);
  setResizable(false);
  setLocationRelativeTo(null);
  setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
  
        KeyboardFocusManager manager = KeyboardFocusManager.getCurrentKeyboardFocusManager();
        manager.addKeyEventDispatcher(this);
        
        centerPanel = new JPanel(new CardLayout());
  welcomeScreenPanel = new WelcomeScreenPanel(this);
  gamePanel = new GamePanel(game);
  

  createLevelPanels();
 
  centerPanel.add(welcomeScreenPanel, "Welcome Screen Panel");
  centerPanel.add(levelOnePanel, "Welcome to Level :1");
  centerPanel.add(levelTwoPanel, "Welcome to Level :2");
  centerPanel.add(levelThreePanel, "Welcome to Level :3");
  centerPanel.add(gamePanel, "Game Panel");
  
  add(centerPanel, BorderLayout.CENTER);
  switchToWelcomeScreenPanel();
  
  setVisible(true);
 }
 
  /** Method: getCenterPanelLayout returns the centerPanel layout
   * @return c1, which is the layout for centerPanel
   */
 
 public CardLayout getCenterPanelLayout()
 {
  CardLayout cl = (CardLayout) centerPanel.getLayout();
  return cl;
 }
 
  /** Method: switchToWelcomeScreenPanel switches the current panel to the welcome screen
  * 
  */
 
 public void switchToWelcomeScreenPanel()
 {
  getCenterPanelLayout().show(centerPanel, "Welcome Screen Panel");
  gamePanel.stopBackgroundMusic();
  welcomeScreenPanel.playBackgroundMusic();
 }
 
  /** Method: createLevelPanels makes the panel for each level visible
  * 
  */
 
 public void createLevelPanels() {
  levelOnePanel = new LevelScreenPanel(this, 1);
  levelOnePanel.setOpaque(false);
  levelTwoPanel = new LevelScreenPanel(this, 2);
  levelTwoPanel.setOpaque(false);
  levelThreePanel = new LevelScreenPanel(this, 3);
  levelThreePanel.setOpaque(false);
 }
 
  /** Method:switchToGamePenl switches the current panel shown to the Game Panel
  * 
  */
 
 public void switchToGamePanel()
 {
  getCenterPanelLayout().show(centerPanel, "Game Panel");
  welcomeScreenPanel.stopBackgroundMusic();
  gamePanel.playBackgroundMusic();
 }
 
  /** Method: switchToLevelPanel switches current panel to the Level Panel
  * @param int r is the level number
  */
 
 public void switchToLevelPanel(int r)
 {
  
  getCenterPanelLayout().show(centerPanel, "Welcome to Level :" + r);
 }
 
  /** Method: getGameFont returns a font type
  * @return font, which gives the font type
  */
 
 public static Font getGameFont()
 {
  Font font = null;
  try
  {
   font = Font.createFont(Font.PLAIN, WelcomeScreenPanel.class.getResourceAsStream("res/font/small_pixel_7.ttf"));
   font = font.deriveFont((float) 60);
  }
  catch (Exception e)
  {
   e.printStackTrace();
  }
  return font;
 }
 
  /** Method: dispatchKeyEvent returns user to the Welcome Screen if Esc is pressed, the Level 1 panel if "p" is pressed.
  * It also returns level directions if "h" is pressed. It uses the left and right keys to move the hero ship
  * and spacebar to shoot missiles 
  * @param KeyEvent e describes a key being pressed
  * @return true or false
  */
 
 @Override
    public boolean dispatchKeyEvent(KeyEvent e)
    {
        if (e.getID() == KeyEvent.KEY_PRESSED)
        {
         if (e.getKeyCode() == KeyEvent.VK_ESCAPE)
         {
          switchToWelcomeScreenPanel();
          welcomeScreenPanel.switchToWelcomeScreen();
         }
         else if (e.getKeyCode() == KeyEvent.VK_P)
         {
    switchToLevelPanel(1);
          
         }
         else if (e.getKeyCode() == KeyEvent.VK_H)
         {
          welcomeScreenPanel.switchToDirections();
         }
         else if (e.getKeyCode() == KeyEvent.VK_LEFT)
         {
          gamePanel.moveHeroLeft();
         }
         else if (e.getKeyCode() == KeyEvent.VK_RIGHT)
         {
          gamePanel.moveHeroRight();
         }
         else if (e.getKeyCode() == KeyEvent.VK_ENTER)
         {
          
          switchToGamePanel(); 
          gamePanel.startMoving(game, welcomeScreenPanel);
           
         }
         else if (e.getKeyCode() == KeyEvent.VK_SPACE)
         {
          int x = gamePanel.getHeroX() + 30;
          
          int y = gamePanel.getHeroY();
          try {
           user = true;
     gamePanel.shootMissile(x, y, gamePanel, j, user, game);
     j++;
    } catch (Exception e1) {
     // TODO Auto-generated catch block
     e1.printStackTrace();
    }
         }
        }
        return false;
    }
 
  /** Method: gameOverScreen creates a screen after the game has ended and shows the total score
  * @param String wonOrNot tells the user if the game is won
  * @param int score number of points scored
  */
 
 public void gameOverScreen(String wonOrNot, int score){
  JFrame gameOver = new JFrame("Game Over");
  gameOver.setLocationRelativeTo(null);
  gameOver.setResizable(false);
  JPanel go = new JPanel();
  JLabel over = new JLabel(wonOrNot);
  JLabel scores = new JLabel("You Scored: " + score + " points");
  go.add(over);
  go.add(scores);
  gameOver.add(go);
  gameOver.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
  gameOver.pack();
  gameOver.setVisible(true);
 }
 
 
 public static void main(String[] args)
 {
  new Game();
 }
}