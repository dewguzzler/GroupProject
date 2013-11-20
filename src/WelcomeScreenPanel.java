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
	
	private JButton playButton;
	private JButton howToPlayButton;
	private JPanel buttonPanel;
	private JTextArea directionsTextArea;
	private JTextArea welcomeTextArea;
	private Game gameFrame;
	private Clip backgroundMusic;
	private JPanel centerPanel;
	private JPanel howToPlayPanel;
	private JPanel welcomePanel;
	
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
	
	private void initButtons()
	{
		buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
		buttonPanel.setOpaque(false);
		
		playButton = new JButton("PLAY");
		playButton.setActionCommand("play");
		playButton.setOpaque(false);
		playButton.setForeground(Color.WHITE);
		playButton.setContentAreaFilled(false);
		playButton.setBorderPainted(false);
		playButton.addActionListener(this);
		
		howToPlayButton = new JButton("HOW TO PLAY");
		howToPlayButton.setActionCommand("how to play");
		howToPlayButton.setOpaque(false);
		howToPlayButton.setForeground(Color.WHITE);
		howToPlayButton.setContentAreaFilled(false);
		howToPlayButton.setBorderPainted(false);
		howToPlayButton.addActionListener(this);
		
		Font font = Game.getGameFont();
		playButton.setFont(font);
		howToPlayButton.setFont(font);
		
		buttonPanel.add(playButton);
		buttonPanel.add(howToPlayButton);
		
		add(buttonPanel, BorderLayout.SOUTH);
	}
	
	private void initWelcomeTextArea()
	{
		welcomeTextArea = new JTextArea();
		welcomeTextArea.setFont(Game.getGameFont().deriveFont((float) 40));
		welcomeTextArea.setEditable(false);
		welcomeTextArea.setForeground(Color.WHITE);
		welcomeTextArea.setOpaque(false);
		
		welcomeTextArea.append("Press P to Play");
		welcomeTextArea.append("\nPress H to read the How to Play instructions");
		welcomeTextArea.append("\nPress ESC to return to the welcome screen");
		
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
		ImageIcon background = new ImageIcon(WelcomeScreenPanel.class.getResource("res/images/background.png"));
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