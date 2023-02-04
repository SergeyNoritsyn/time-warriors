/**
 * Sergey Noritsyn
 * Mr.Benum's Period 2 ICS4UE
 * 22/01/2021
 * Stores rectangular-shaped obstacles which appear on the game map.
 */

import csta.ibm.pong.GameObject;

public class Obstacle extends GameObject
{
	// Fields
	
	// specifies if obstacle can be shot through
	private boolean shootThrough;
	
	/**
	 * 
	 * @param xTop - top left x coordinate
	 * @param yTop - top left y coordinate
	 * @param xBot - bottom right x coordinate
	 * @param yBot - bottom right y coordinate
	 * @param type - "red or blue" obstacle
	 * @author Sergey Noritsyn
	 */
	public Obstacle(int xTop, int yTop, int xBot, int yBot, String type)
	{
		setBounds(xTop, yTop, xBot - xTop, yBot - yTop);
		// sets boolean
		shootThrough = type.equals("blue") ? true : false;
	}
	
	/**
	 * Accessor method.
	 * @return boolean shootThrough
	 */
	public boolean isThroughShootable()
	{
		return shootThrough;
	}
	
	@Override
	public void act()
	{
		
	}
}
