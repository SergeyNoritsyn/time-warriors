/**
 * Sergey Noritsyn
 * Mr.Benum's Period 2 ICS4UE
 * 22/01/2021
 * Serves as the embodiment of the player game component.
 * Moves player in all 4 directions.
 */

import java.awt.Color;
import csta.ibm.pong.GameObject;

public class Player extends GameObject 
{	  
	// Fields
	
	// x and y speed
	private int dx, dy;
	
	// maximum/current HP
	private int hp, curHp;
	
	/**
	 * Creates Player object.
	 * @param speedX
	 * @param speedY
	 * @param health
	 * @param color
	 * @author Sergey Noritsyn
	 */
	public Player(int speedX, int speedY, int health, Color color)
	{
		dx = speedX;
		dy = speedY;
		hp = health;
		curHp = hp;
		setColor(color);
	}
	
	/**
	 * Modifier method.
	 * Resets player's HP to its maximum value.
	 */
	public void setHp()
	{
		curHp = hp;
	}
	
	/**
	 * Modifier method.
	 * Decreases player's health.
	 * @param dmg - damage done by bullet that hit
	 */
	public void setCurHp(int dmg)
	{
		curHp -= dmg;
	}
	
	/**
	 * Accessor method.
	 * @return maximum HP value
	 */
	public int getHp()
	{
		return hp;
	}
	
	/**
	 * Accessor method.
	 * @return current HP value
	 */
	public int getCurHp()
	{
		return curHp;
	}
	
	/**
	 * Modifier method.
	 * Moves player upwards.
	 */
	public void moveUp()
	{
		setY(getY() - getYSpeed());
	}
	
	/**
	 * Modifier method.
	 * Moves player down.
	 */
	public void moveDown()
	{
		setY(getY() + getYSpeed());
	}
	
	/**
	 * Modifier method.
	 * Moves player right.
	 */
	public void moveRight()
	{
		setX(getX() + getXSpeed());
	}
	
	/**
	 * Modifier method.
	 * Moves player left.
	 */
	public void moveLeft()
	{
		setX(getX() - getXSpeed());
	}
	
	/**
	 * Accessor method.
	 * @return x component of player's speed.
	 */
	public int getXSpeed()
	{
		return dx;
	}
	
	/**
	 * Accessor method.
	 * @return y component of player's speed.
	 */
	public int getYSpeed()
	{
		return dy;
	}
	
	/**
	 * Modifier method.
	 * @param dx - new x component of speed
	 */
	public void setXSpeed(int dx)
	{
		this.dx = dx;
	}
	
	/**
	 * Modifier method.
	 * @param dy - new y component of speed
	 */
	public void setYSpeed(int dy)
	{
		this.dy = dy;
	}
	
	@Override
	public void act()
	{
		
	}
}
