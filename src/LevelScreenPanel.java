import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Graphics;
import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.JTextArea;

/** Class: WelcomeScreenPanel.java
 *
 * @author Jesse Perkins
 * @version 1.0
 * Written: Oct 29, 2013
 * 
 * This class - displays story text before each level
 * 
 * Purpose - to give a limited story why the hero fights
 *
 */
public class LevelScreenPanel extends JPanel 
{
 private static final long serialVersionUID = 1L;
 
 
 private JTextArea directionsTextArea; //the directions are stored in this variable
 private JTextArea welcomeTextArea; //stores the welcome text
 private Game gameFrame; //represents the Game method in Game.java
 private JPanel centerPanel; //Panel which holds all the HowToPlayPanel and the welcomePanel
 private JPanel howToPlayPanel; //panel which stores directionsTextArea
 private JPanel welcomePanel; //panel which stores WelcomeTextArea
 private int level; //the level represented (1, 2 or 3)
 
 /** Method:LevelScreenPanel* adds welcomePanel and howToPlayPanel to the centerPanel
  * @param Game gameFrame is the Game method from Game.java
  * @param int level is the level the user is on
  *
  */
 
 public LevelScreenPanel(Game gameFrame, int level)
 {
  super(new BorderLayout());
  this.gameFrame = gameFrame;
  this.level = level;
  
  welcomePanel = new JPanel(); 
  welcomePanel.setOpaque(false);
  
  howToPlayPanel = new JPanel();
  howToPlayPanel.setOpaque(false);
  
  centerPanel = new JPanel(new CardLayout());
  centerPanel.setOpaque(false);
  centerPanel.add(welcomePanel, "welcome screen");
  centerPanel.add(howToPlayPanel, "how to play");
  add(centerPanel, BorderLayout.CENTER);
  
  
  initWelcomeTextArea();
  initDirectionsTextArea();
 }
 
 /** Method:setLevelText* Displays story text for each level
  *@param int level is what level the player is on
  */
 
 private void setLevelText(int level) {
  if(level == 1) {
   welcomeTextArea.append("Level 1");
   welcomeTextArea.append("\nYour planet of Umaran is under attack by");
   welcomeTextArea.append("\nyour enemy, Umacran. Defend your planet soldier!");
   welcomeTextArea.append("\n\n\nHit Enter to Start your defense!");
  }
  else if (level == 2) {
   welcomeTextArea.append("Level 2");
   welcomeTextArea.append("\nYou saved Umaran from the Umacrans!");
   welcomeTextArea.append("\nNow is the time to go on the offensive!");
   welcomeTextArea.append("\nYou catch up to another group while pursuing");
   welcomeTextArea.append("\nThese are special and are faster than before");
   welcomeTextArea.append("\n\n\nHit Enter to hunt them down!");
  }
  else if (level == 3) {
   welcomeTextArea.append("Level 3");
   welcomeTextArea.append("\nYes, Yes! keep them on the run!");
   welcomeTextArea.append("\nNow you head home to lick your wounds");
   welcomeTextArea.append("\nAs you turn Your back, they attack your rear flank");
   welcomeTextArea.append("\nTheir fastest ships are here");
   welcomeTextArea.append("\n\n\nHit Enter to destroy them for good");
  }
 }
 
 /** Method:initWelcomeTextArea* gives attributes to the welcomeTextArea JTextArea.
  * Adds the welcometoTextArea to welcomePanel
  *
  */
 
 private void initWelcomeTextArea()
 {
  welcomeTextArea = new JTextArea();
  welcomeTextArea.setFont(Game.getGameFont().deriveFont((float) 40));
  welcomeTextArea.setEditable(false);
  welcomeTextArea.setForeground(Color.WHITE);
  welcomeTextArea.setOpaque(false);
  
  setLevelText(level);
  
  welcomePanel.add(welcomeTextArea, BorderLayout.CENTER);
 }
 
 
 /** Method:initDrectionsTextAre* changes attributes of the directions
  * and adds it to the howToPlayPanel
  *
  */
 
 private void initDirectionsTextArea()
 {
  directionsTextArea = new JTextArea();
  directionsTextArea.setFont(Game.getGameFont());
  directionsTextArea.setEditable(false);
  directionsTextArea.setForeground(Color.WHITE);
  directionsTextArea.setOpaque(false);
  howToPlayPanel.add(directionsTextArea, BorderLayout.CENTER);
 }
 
 
 
 
 /** Method:paintComponent* sets the background and paints it
  * @param Graphics g paints images
  */
 
 public void paintComponent(Graphics g)
 {
  super.paintComponent(g);
  ImageIcon background = new ImageIcon(LevelScreenPanel.class.getResource("res/images/background.png"));
  g.drawImage(background.getImage(), 0, 0, getWidth(), getHeight(), this);
 }
 
 /** Method:actionPerformed* switches to the game panel or to the directions panel with a key stroke
  * @param ActionEvent e makes things happen!
  */
 
}