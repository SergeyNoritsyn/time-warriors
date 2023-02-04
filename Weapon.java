/**
 * Sergey Noritsyn
 * Mr.Benum's Period 2 ICS4UE
 * 22/01/2021
 * Represents player's weapons.
 */

import csta.ibm.pong.GameObject;
import java.util.ArrayList;

public class Weapon extends GameObject
{
	// Fields
	
	// booleans on weapon's reloading state
    private boolean semiAuto, reloading, justTurned = false; 
    
    // numerical characteristics
    private int range, damage;
    private int magCap, magCur;
    private int reloadTime, shotDelay;
    private int dx, dy;
    
    // Imported instances for/from other classes
    private ArrayList<Bullet> magazine;
    private String fireSound, reloadSound;
    private Music music;
    
    /**
     * Creates a Weapon object with specified unique fields.
     * @param rel indicates if weapon is currently reloading
     * @param semi indicates if weapon is semi-automatic
     * @param r indicates range of weapon's bullets
     * @param dmg indicates damage done
     * @param mag indicates magazine capacity
     * @param cur indicates current number of bullets in magazine
     * @param rTime indicates reload time
     * @param sTime indicates time between shots
     * @param x indicates horizontal speed
     * @param y indicates vertical speed
     * @param sound indicates the sound associates with the firing of the weapon
     * @author Sergey Noritsyn
     */
    public Weapon(boolean rel, boolean semi, int r, int dmg, int mag, int cur, int rTime, int sTime, int x, int y, String fireSound, String reloadSound)
    {
        range = r;
        damage = dmg;
        magCap = mag;
        magCur = cur;
        reloadTime = rTime;
        shotDelay = sTime;
        dx = x;
        dy = y;
        semiAuto = semi;
        reloading = rel;
        magazine = new ArrayList<Bullet>();
        this.fireSound = fireSound;
        this.reloadSound = reloadSound;
    }
    
    /**
     * Copy constructor to avoid objects sharing same reference
     * @param w passes an instance of a pre-defined 
     * Weapon object to the constructor
     * @author Sergey Noritsyn
     */
    public Weapon(Weapon w)
    {
    	range = w.getRange();
        damage = w.getDamage();
        magCap = w.getMagCime();
        magCur = w.getMagCur();
        reloadTime = w.getReloadTime();
        shotDelay = w.getShotDelay();
        dx = w.getXSpeed();
        dy = w.getYSpeed();
        semiAuto = w.isSemiAuto();
        reloading = w.isReloading();
        magazine = new ArrayList<Bullet>();
        fireSound = w.getShotSound();
        reloadSound = w.getReloadSound();
    }
    
    /**
     * Updates location of each bullet that has been fired.
     */
    @Override
	public void act()
	{
		for (int i = 0; i < magazine.size(); i++)
		{
			// checks that bullet can keep moving
			if (magazine.get(i).isOutOfRange() == false)
			{
				magazine.get(i).setX(magazine.get(i).getX() + magazine.get(i).getXSpeed());
				magazine.get(i).setY(magazine.get(i).getY() + magazine.get(i).getYSpeed());
			}
			// removes bullet
			else
			{
				removeBullet(i);
				i--;
			}
		}
	}
	
    /**
     * Removes a bullet from the weapon's list of currently
     * existing bullets.
     * @param index - specifies index of bullet that is to be deleted.
     */
    public void removeBullet(int index)
    {
    	magazine.get(index).setVisible(false);
		magazine.get(index).setOpaque(false);
		magazine.remove(index);
    }
    
	/**
	 * Fires the bullet and plays a sound.
	 */
	public void fire()
	{
		magazine.add(new Bullet(getX(), getY(), dx, dy, damage, range));
		magazine.get(numBullets() - 1).setOpaque(true);
		reloading = true;
		music = new Music(fireSound);
        music.playEffect();
		magCur--;
	}
	
	/**
	 * Returns a Bullet instance.
	 * @param index - specifies index of Bullet being returned
	 * @return a Bullet object
	 */
	public Bullet getBullet(int index)
	{
		return magazine.get(index);
	}
	
	/**
	 * Changes firing direction based on 
	 * direction where player is turned.
	 */
	public void changeShotDir()
	{
		if (getXSpeed() == 0)
		{
			setXSpeed(getYSpeed() * -1);
			setYSpeed(0);
		}
		else
		{
			setYSpeed(getXSpeed());
			setXSpeed(0);
		}
	}
	
	/**
	 * Accessor method.
	 * @return weapon range.
	 */
    public int getRange()                
    {
        return range;
    }
    
    /**
     * Accessor method.
     * @return x-component of speed.
     */
    public int getXSpeed()
    {
        return dx;
    }
    
    /**
     * Accessor method.
     * @return y-component of speed.
     */
    public int getYSpeed()
    {
        return dy;
    }
    
    /**
     * Accessor method.
     * @return weapon's reload time.
     */
    public int getReloadTime()
    {
        return reloadTime;
    }
    
    /**
     * Accessor method.
     * @return weapon's maximum magazine size.
     */
    public int getMagCime()
    {
        return magCap;
    }
    
    /**
     * Accessor method.
     * @return weapon's damage.
     */
    public int getDamage()
    {
        return damage;
    }
    
    /**
     * Modifier method.
     * @param dx - changes x-component of speed.
     */
    public void setXSpeed(int dx)
    {
        this.dx = dx;
    }
    
    /**
     * Modifier method.
     * @param dy - changes y-component of speed.
     */
    public void setYSpeed(int dy)
    {
        this.dy = dy;
    }
    
    /**
     * Modifier method.
     * @param rel - specifies if weapon currently reloading.
     */
    public void setReloading(boolean rel)
    {
        this.reloading = rel;
    }
    
    /**
     * Modifier method.
     * @param mag - changes current magazine size.
     */
    public void setMagCur(int mag)
    {
        magCur = mag;
    }
    
    /**
     * Accessor method.
     * @return magazine's current capacity.
     */
    public int getMagCur()
    {
    	return magCur;
    }
    
    /**
     * Accessor method.
     * @return weapon's delay between shots.
     */
    public int getShotDelay()
    {
    	return shotDelay;
    }
    
    /**
     * Accessor method.
     * @return whether weapon is currently reloading.
     */
    public boolean isReloading()
    {
    	return reloading;
    }
    
    /**
     * Accessor method.
     * @return whether weapon is semiauto.
     */
    public boolean isSemiAuto()
    {
    	return semiAuto;
    }
    
    /**
     * Accessor method.
     * @return file address for weapon's shot sound.
     */
    public String getShotSound()
    {
    	return fireSound;
    }
    
    /**
     * Accessor method.
     * @return file address for weapon's reload sound.
     */
    public String getReloadSound()
    {
        return reloadSound;
    }
    
    /**
     * Accessor method.
     * @return whether cooldown on turning character still enabled.
     */
    public boolean wasTurned()
    {
    	return justTurned;
    }
    
    /**
     * Modifier method.
     * @param turnOrNot - specifies if turning cooldown should be enabled.
     */
    public void setIfTurned(boolean turnOrNot)
    {
    	justTurned = turnOrNot;
    }
    
    /**
     * Accessor method.
     * @return number of bullets currently shot by weapon.
     */
    public int numBullets()
    {
    	return magazine.size();
    }
}
