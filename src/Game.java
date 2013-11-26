import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.KeyEventDispatcher;
import java.awt.KeyboardFocusManager;
import java.awt.event.KeyEvent;
import javax.swing.JFrame;
import javax.swing.JPanel;

/** Class: Game.java
 *
 * @author Aida Syrkett
 * @version 1.0
 * Written: Oct 29, 2013
 *
 */
public class Game extends JFrame implements KeyEventDispatcher
{	
	private static final long serialVersionUID = 1L;
	private static final Dimension DEFAULT_SIZE = new Dimension(900, 600);
	
	private JPanel centerPanel;
	private WelcomeScreenPanel welcomeScreenPanel;
	private GamePanel gamePanel;
	public int j = 0;
	private boolean user;
	private static Game game;
	
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
	
		centerPanel.add(welcomeScreenPanel, "Welcome Screen Panel");
		centerPanel.add(gamePanel, "Game Panel");
		
		add(centerPanel, BorderLayout.CENTER);
		switchToWelcomeScreenPanel();
		
		setVisible(true);
	}
	
	public CardLayout getCenterPanelLayout()
	{
		CardLayout cl = (CardLayout) centerPanel.getLayout();
		return cl;
	}
	
	public void switchToWelcomeScreenPanel()
	{
		getCenterPanelLayout().show(centerPanel, "Welcome Screen Panel");
		gamePanel.stopBackgroundMusic();
		welcomeScreenPanel.playBackgroundMusic();
	}
	
	public void switchToGamePanel()
	{
		getCenterPanelLayout().show(centerPanel, "Game Panel");
		welcomeScreenPanel.stopBackgroundMusic();
		gamePanel.playBackgroundMusic();
	}
	
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
        		switchToGamePanel();
        		gamePanel.startMoving(game);
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
        	else if (e.getKeyCode() == KeyEvent.VK_SPACE)
        	{
        		int x = gamePanel.getHeroX() + 30;
        		
        		int y = gamePanel.getHeroY();
        		System.out.println(y);
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
	
	/**
	 * @param args
	 */
	public static void main(String[] args)
	{
		new Game();
	}
}