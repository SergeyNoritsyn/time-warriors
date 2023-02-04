/**
 * Sergey Noritsyn, James Zhang
 * Mr.Benum's Period 2 ICS4UE
 * 22/01/2021
 * Represents bullets shot by player's weapons.
 */

import java.awt.Color;
import csta.ibm.pong.GameObject;

public class Bullet extends GameObject
{
	// Fields
	
	// speed, damage done, initial position, maximum final position
	private int dx, dy, damage, startX, startY, range;
	
	// constant
	private final int BULLET_SIZE = 5;
	
	/**
	 * Constructor for a Bullet.
	 * @param x - x coordinate from where bullet is shot
	 * @param y - y coordinate from where bullet is shot
	 * @param dx - x speed component
	 * @param dy - y speed component
	 * @param dmg
	 * @param r - bullet's maximum range (distance in travels)
	 * @author Sergey Noritsyn
	 */
	public Bullet(int x, int y, int dx, int dy, int dmg, int r)
	{
		startX = x;
		startY = y;
		this.dx = dx;
		this.dy = dy;
		this.damage = dmg;
		range = r;
		setColor(Color.BLACK);
		setBounds(startX, startY, BULLET_SIZE, BULLET_SIZE);
	}
	
	/**
	 * Accessor method.
	 * @return x component of speed
	 */
	public int getXSpeed()
	{
		return dx;
	}
	
	/**
	 * Accessor method.
	 * @return y component of speed
	 */
	public int getYSpeed()
	{	
		return dy;
	}
	
	/**
	 * Accessor method.
	 * @return damage done by bullet
	 */
	public int getDamage()
	{
		return damage;
	}
	
	/**
	 * Modifier method.
	 * @param dx - new x speed component
	 */
	public void setXSpeed(int dx)
	{
		this.dx = dx;
	}
	
	/**
	 * Modifier method.
	 * @param dy - new y speed component
	 */
	public void setYSpeed(int dy)
	{
		this.dy = dy;
	}
	
	/**
	 * Checks if bullet has travelled its maximum distance
	 * or reached a boundary of the game playing area.
	 * @return boolean 
	 */
	public boolean isOutOfRange()
	{
		if (Math.abs(getX() - startX) >= range || getX() <= 50 + dx || getX() >= 850 - BULLET_SIZE - dx) 
		{
			return true;
		}
		else if (Math.abs(getY() - startY) >= range || getY() <= 75 + dy || getY() >= 525 - BULLET_SIZE - dy)
		{
			return true;
		}
		return false;
	}
	
	@Override
	public void act()
	{
		
	}
}