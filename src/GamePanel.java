import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.NumberFormat;

import javax.imageio.ImageIO;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.JTextArea;

/** Class: GamePanel.java
 *
 * @author Aida Syrkett
 * @version 1.0
 * Written: Oct 29, 2013
 *
 */
public class GamePanel extends JPanel
{
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
	private Clip firingSound;

	public GamePanel()
	{
		super(new BorderLayout());
		hero = new HeroShip();
		initPointsPanel();
		initLivesPanel();
	}
	
	private void initPointsPanel()
	{
		points = 0;
		
		pointsPanel = new JPanel();
		pointsPanel.setOpaque(false);
		
		pointsTextArea = new JTextArea("POINTS " + FORMATTER.format(points));
		pointsTextArea.setFont(Game.getGameFont().deriveFont((float)40));
		pointsTextArea.setEditable(false);
		pointsTextArea.setForeground(Color.WHITE);
		pointsTextArea.setOpaque(false);
		
		pointsPanel.add(pointsTextArea);
		add(pointsPanel, BorderLayout.EAST);
	}
	
	private void initLivesPanel()
	{
		lives = 3;
		
		livesPanel = new JPanel();
		livesPanel.setOpaque(false);
		
		livesTextArea = new JTextArea("LIVES " + lives + "/3");
		livesTextArea.setFont(Game.getGameFont().deriveFont((float)40));
		livesTextArea.setEditable(false);
		livesTextArea.setForeground(Color.WHITE);
		livesTextArea.setOpaque(false);
		
		livesPanel.add(livesTextArea);
		add(livesPanel, BorderLayout.WEST);
	}
	
	public void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		graph = super.getGraphics();
		ImageIcon background = new ImageIcon(GamePanel.class.getResource("res/images/background.png"));
		g.drawImage(background.getImage(), 0, 0, getWidth(), getHeight(), this);
		hero.draw(g);
	}
	
	public void playBackgroundMusic ()
	{
		if (backgroundMusic == null)
		{
			backgroundMusic = AudioRunnable.getClip("res/audio/KingGalaxian.au");
		}
		if (!backgroundMusic.isRunning())
		{
			backgroundMusic.setFramePosition(0);
			backgroundMusic.loop(Clip.LOOP_CONTINUOUSLY);
		}
	}
	
	public void playFiringMusic ()
	{
		if (firingSound == null)
		{
			firingSound = AudioRunnable.getClip("res/audio/firingSound.au");
		}
		if (!firingSound.isRunning())
		{
			firingSound.setFramePosition(0);
			firingSound.loop(0);
		}
	}
	
	public void stopBackgroundMusic()
	{
		if (backgroundMusic != null)
		{
			backgroundMusic.stop();
		}
	}
	
	public void moveHeroLeft()
	{
		if (hero.getX() > 40)
		{
			hero.setX(hero.getX() - 10);
			repaint();
		}
	}
	
	public void moveHeroRight()
	{
		if (hero.getX() < 760)
		{
			hero.setX(hero.getX() + 10);
			repaint();
		}
	}
	
	
	public int getHeroX() {
		int x = hero.getX();
		return x;
	}
	
	public void moveMissile() throws Exception
	{
		if(mship.getY() > 0) {
			
			mship.setY(mship.getY() - 30);
			System.out.println(mship.getY());
			//mship.setVisible(false);
			//repaint();
			//repaint();
			
			mship.setOpaque(false);
			mship.draw(graph);
			//mship.update(graph);
//			Thread.sleep(24);
//			moveMissile();
		}
			
	}
	
	public void paintMissile(Missileship ms, int y) {
		System.out.println("the fuck");
		//mship = new Missileship();
		mship.setX(hero.getX());
		mship.setY(y);
		
		mship.draw(graph);
		//mship.repaint();
		//repaint();
	}
	
	
	public void shootMissile(int x, int y, GamePanel g, int j) throws Exception {
		mship = new Missileship();
		y = hero.getY();
		System.out.println(x + " " + y);
//		mship.setX(hero.getX());
//		mship.setY(y-60);
//		mship.draw(graph);

		gamePanel = g;
//		mship.setX(x);
//		mship.setY(ys);
//		mship.draw(graph);
//		mship.setOpaque(false);
		playFiringMusic();
		//moveMissile();
		//System.out.println(ms);
		name = "mrs" + j;
		MissileRunnable name = new MissileRunnable(graph, x, y - 50, mship, g, "up");
		Thread miss = new Thread(name);
		miss.start();
//		//stopBackgroundMusic();
//		if(miss.isInterrupted()) {
//			playBackgroundMusic();
//		}
//		if(miss.isAlive()){
//			
//			System.out.println("interuppted");
//		}
		
//		graph.drawImage(missile, x+30, y-30, 20, 50, this);
//		System.out.println(x + " " + y);
	}
	
}