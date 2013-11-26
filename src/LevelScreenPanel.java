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
public class LevelScreenPanel extends JPanel implements ActionListener
{
	private static final long serialVersionUID = 1L;
	
	
	private JTextArea directionsTextArea;
	private JTextArea welcomeTextArea;
	private Game gameFrame;
	private Clip backgroundMusic;
	private JPanel centerPanel;
	private JPanel howToPlayPanel;
	private JPanel welcomePanel;
	private int level;
	
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
	
	private void setLevelText(int level) {
		if(level == 1) {
			welcomeTextArea.append("Level 1");
			welcomeTextArea.append("\nYour planet of Umaran is under attack by");
			welcomeTextArea.append("\nyour enemy, Umacran. Defend your planet soldier!");
			welcomeTextArea.append("\n\n\nHit Enter to Start your defense!");
		}
		else if (level == 2) {
			welcomeTextArea.append("Level 2");
			welcomeTextArea.append("\nYour planet of Umaran is under attack by");
			welcomeTextArea.append("\nyour enemy, Umacran. Defend your planet soldier!");
			welcomeTextArea.append("\n\n\nHit Enter to Start your defense!");
		}
		else if (level == 3) {
			welcomeTextArea.append("Level 3");
			welcomeTextArea.append("\nYour planet of Umaran is under attack by");
			welcomeTextArea.append("\nyour enemy, Umacran. Defend your planet soldier!");
			welcomeTextArea.append("\n\n\nHit Enter to Start your defense!");
		}
	}
	
	
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
	
	private void initDirectionsTextArea()
	{
		directionsTextArea = new JTextArea();
		directionsTextArea.setFont(Game.getGameFont());
		directionsTextArea.setEditable(false);
		directionsTextArea.setForeground(Color.WHITE);
		directionsTextArea.setOpaque(false);
		directionsTextArea.setText("PLACE HOLDER TEXT");
		howToPlayPanel.add(directionsTextArea, BorderLayout.CENTER);
	}
	
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
	
	public void stopBackgroundMusic()
	{
		if (backgroundMusic != null)
		{
			backgroundMusic.stop();
		}
	}
	
	public void switchToDirections()
	{
		CardLayout cl = (CardLayout) centerPanel.getLayout();
		cl.show(centerPanel, "how to play");
	}
	
	public void switchToWelcomeScreen()
	{
		CardLayout cl = (CardLayout) centerPanel.getLayout();
		cl.show(centerPanel, "welcome screen");
	}
	
	public void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		ImageIcon background = new ImageIcon(LevelScreenPanel.class.getResource("res/images/background.png"));
		g.drawImage(background.getImage(), 0, 0, getWidth(), getHeight(), this);
	}
	
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