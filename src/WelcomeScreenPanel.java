import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.sound.sampled.Clip;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextArea;

/** Class: WelcomeScreenPanel.java
 *
 * @author Aida Syrkett
 * @version 1.0
 * Written: Oct 29, 2013
 *
 */
public class WelcomeScreenPanel extends JPanel implements ActionListener
{
 private static final long serialVersionUID = 1L;

 private JButton howToPlayButton; //button clicked to display the HowToPlayPanel
 private JPanel buttonPanel; //Panel where the howToPlayButton is stored
 private JTextArea directionsTextArea; //Stores the text for the directions
 private JTextArea welcomeTextArea; //Stores text to direct players
 private Game gameFrame; //method Game from Game.java
 private Clip backgroundMusic; //audio for the welcome screen music
 private JPanel centerPanel; //The panel everything is stored in
 private JPanel howToPlayPanel; //Panel that stores the howToPlayButton
 private JPanel welcomePanel; //Panel that stores the welcomeTextArea

 /** Method: WelcomeScreenPanel* sets up a panel for the game's welcome screen.
  * It includes the directions.
  * @param Game gameFrame is the Game method from Game.java
  */

 public WelcomeScreenPanel(Game gameFrame)
 {
  super(new BorderLayout());
  this.gameFrame = gameFrame;
  
  welcomePanel = new JPanel();
  welcomePanel.setOpaque(false);
  
  howToPlayPanel = new JPanel();
  howToPlayPanel.setOpaque(false);
  
  centerPanel = new JPanel(new CardLayout());
  centerPanel.setOpaque(false);
  centerPanel.add(welcomePanel, "welcome screen");
  centerPanel.add(howToPlayPanel, "how to play");
  add(centerPanel, BorderLayout.CENTER);
  
  initButtons();
  initWelcomeTextArea();
  initDirectionsTextArea();
 }
 
 /** Method:initButtons* adds a "how to play" button to the buttonPanel
  * 
  */
 
 private void initButtons()
 {
  buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
  buttonPanel.setOpaque(false);
  
  howToPlayButton = new JButton("HOW TO PLAY");
  howToPlayButton.setActionCommand("how to play");
  howToPlayButton.setOpaque(false);
  howToPlayButton.setForeground(Color.WHITE);
  howToPlayButton.setContentAreaFilled(false);
  howToPlayButton.setBorderPainted(false);
  howToPlayButton.addActionListener(this);
  
  Font font = Game.getGameFont();
  howToPlayButton.setFont(font);
  
  buttonPanel.add(howToPlayButton);
  
  add(buttonPanel, BorderLayout.SOUTH);
 }
 
 /** Method: initWelcomeTextArea* adds text to the welcomeTextArea JTextArea
  * and puts it in the welcome panel
  *
  */
 
 private void initWelcomeTextArea()
 {
  welcomeTextArea = new JTextArea();
  welcomeTextArea.setFont(Game.getGameFont().deriveFont((float) 40));
  welcomeTextArea.setEditable(false);
  welcomeTextArea.setForeground(Color.WHITE);
  welcomeTextArea.setOpaque(false);
  
  welcomeTextArea.append("Press P to Play");
  welcomeTextArea.append("\n");
  welcomeTextArea.append("\nPress H to read the How to Play instructions");
  
  welcomePanel.add(welcomeTextArea, BorderLayout.CENTER);
 }
 
 /** Method: initDirectionsTextArea* adds directions to to the directionsTextArea JTextArea
  * and puts it in the howToPlay panel
  *
  */
 
 private void initDirectionsTextArea()
 {
  directionsTextArea = new JTextArea();
  directionsTextArea.setFont(Game.getGameFont().deriveFont((float) 40));
  directionsTextArea.setEditable(false);
  directionsTextArea.setForeground(Color.WHITE);
  directionsTextArea.setOpaque(false);
  directionsTextArea.append("Use Left and Right arrow keys to control the hero (you)");
  directionsTextArea.append("\nPress Space to fire a missile at the enemy fleet");
  directionsTextArea.append("\nWhile dodging enemy fire");
  directionsTextArea.append("\n");
  directionsTextArea.append("\nThe Game will be scored as follows");
  directionsTextArea.append("\n15 points for Yellow-20 points for Purple-30 for Green");
  directionsTextArea.append("\nPhantom hits will not be scored but will clear enemies");
  directionsTextArea.append("\nPress ESC to return to the welcome screen");
  howToPlayPanel.add(directionsTextArea, BorderLayout.CENTER);
 }
 
 /** Method: playBackgroundMusic* loops audio for the welcome panel
  * 
  *
  */
 
 public void playBackgroundMusic()
 {
  if (backgroundMusic == null)
  {
   backgroundMusic = AudioRunnable.getClip("res/audio/GalaxianOpeningTheme.au");
  }
  if (!backgroundMusic.isRunning())
  {
   backgroundMusic.setFramePosition(0);
   backgroundMusic.loop(Clip.LOOP_CONTINUOUSLY);
  }
 }
 
 /** Method:stopBackgroundMusic* stops audio
  * 
  *
  */
 
 public void stopBackgroundMusic()
 {
  if (backgroundMusic != null)
  {
   backgroundMusic.stop();
  }
 }
 
 /** Method: switchToDirections switches the current panel to directions panel
  * 
  *
  */
 
 public void switchToDirections()
 {
  CardLayout cl = (CardLayout) centerPanel.getLayout();
  cl.show(centerPanel, "how to play");
 }
 
 /** Method: switchToWelcomeScreen switches the current panel to the welcome screen
  * 
  *
  */
 
 public void switchToWelcomeScreen()
 {
  CardLayout cl = (CardLayout) centerPanel.getLayout();
  cl.show(centerPanel, "welcome screen");
 }
 
 /** Method: paintComponent* paints images
  *@param Graphics g paints the background 
  *
  */
 
 public void paintComponent(Graphics g)
 {
  super.paintComponent(g);
  ImageIcon background = new ImageIcon(WelcomeScreenPanel.class.getResource("res/images/background.png"));
  g.drawImage(background.getImage(), 0, 0, getWidth(), getHeight(), this);
 }
 
 /** Method:actionPerformed* switches the to game panel or to directions
  *@param ActionEvent e creates action 
  *
  */
 
 @Override
 public void actionPerformed(ActionEvent e)
 {
  if (e.getActionCommand().equals("play"))
  {
   gameFrame.switchToGamePanel();
  }
  else if (e.getActionCommand().equals("how to play"))
  {
   switchToDirections();
  }
 }
}