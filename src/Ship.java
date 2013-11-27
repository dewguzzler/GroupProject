import java.awt.Graphics;
import java.awt.Point;

import javax.swing.ImageIcon;
import javax.swing.JComponent;

/** Class: Ship.java
 *
 * @author Aida Syrkett
 * @version 1.0
 * Written: Nov 14, 2013
 *
 */
public class Ship extends JComponent
{
	private static final long serialVersionUID = 1L;
	private int x;
	private int y;
	private ImageIcon icon;
	private boolean isHit;
	public int pointsWorth;
	
	public Ship(ImageIcon icon, int x, int y, int points)
	{
		super();
		this.icon = icon;
		this.x = x;
		this.y = y;
		this.pointsWorth = points;
	}

	/**
	 * @return the x
	 */
	public int getX()
	{
		return x;
	}
	
	public int getPoint()
	{
		return pointsWorth;
	}

	/**
	 * @param x the x to set
	 */
	public void setX(int x)
	{
		this.x = x;
	}

	/**
	 * @return the y
	 */
	public int getY()
	{
		return y;
	}

	/**
	 * @param y the y to set
	 */
	public void setY(int y)
	{
		this.y = y;
	}

	/**
	 * @return the icon
	 */
	public ImageIcon getIcon()
	{
		return icon;
	}

	/**
	 * @param icon the icon to set
	 */
	public void setIcon(ImageIcon icon)
	{
		this.icon = icon;
	}

	/**
	 * @return the isHit
	 */
	public boolean isHit()
	{
		return isHit;
	}

	/**
	 * @param isHit the isHit to set
	 */
	public void setHit(boolean isHit)
	{
		this.isHit = isHit;
	}
	
	public void draw(Graphics g)
	{
		g.drawImage(icon.getImage(), x, y, 100, 100, this);
		//g.drawImage(icon.getImage(), x, y, icon.getIconWidth(), icon.getIconHeight(), this);
	}
	
	 public void drawEnemy(Graphics g)
	 {
	  
	  g.drawImage(icon.getImage(), x, y, 100, 38, this);
	  //g.drawImage(icon.getImage(), x, y, icon.getIconWidth(), icon.getIconHeight(), this);
	 }
}