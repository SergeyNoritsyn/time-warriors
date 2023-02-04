/**
 * Sergey Noritsyn, Egor Gorelyy, James Zhang
 * Mr.Benum's Period 2 ICS4UE
 * January 22nd, 2021
 * This is the main class of the game, which accesses other class's
 * methods through instances of them. It contains all of the main game
 * logic, including win conditions and in-game object movement.
 * 
 * Here are some links that proved useful to us 
 * throughout the duration of this project:
 * 
 * https://www.youtube.com/watch?v=CmK1nObLxiw 
 * A tutorial on how to use JLayeredPane class.
 * 
 * https://docs.oracle.com/javase/tutorial/uiswing/components/layeredpane.html
 * Additional resource for understanding JLayeredPane.
 * 
 * https://wordtohtml.net
 * An online text-to-html converter.
 * 
 * https://css-tricks.com/snippets/javascript/javascript-keycodes/
 * A guide to keyCodes that includes a list of indexes for each key.
 * 
 * https://www.youtube.com/watch?v=QiLeau29fOQ
 * A guide to adding images to a JFrame, which we used in our menu page.
 * 
 * https://stackoverflow.com/questions/33954698/jbutton-change-default-border
 * Guide for customizing button layouts.
 * 
 * https://stackoverflow.com/questions/8214958/the-getsource-and-getactioncommand
 * https://www.dreamincode.net/forums/topic/234827-multiple-buttons-to-same-action-listener/
 * https://docs.oracle.com/javase/tutorial/uiswing/events/actionlistener.html
 * Guides to understanding how JButtons work and respond to clicks.
 * 
 * The rest of our project was created with the power of friendship, 
 * as well as a lot of professional debugging, also known as failing 
 * a million times before finally getting it right.
 */
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.time.Instant;
import javax.imageio.ImageIO;
import javax.swing.*;
import java.util.ArrayList;
import java.util.StringTokenizer;
import csta.ibm.pong.Game;

public class TimeWarriors extends Game
{
	// Fields
	
	// Game behaviour
	private static int gameTheme;
	private static int pointsGoal;
	private static int weaponType[] = new int[2];
	private int score1 = 0, score2 = 0;
	private ArrayList<Character> curKeyPresses = new ArrayList<Character>();
	
	// Game layout
	private static JLayeredPane panel = new JLayeredPane();
	private JLabel mapPic;
	
	// Player parameters
	private Player p1, p2;
	private JLabel p1Score, p2Score;
	private JLabel p1Ammo, p2Ammo;
	private JLabel p1HpBar, p2HpBar;
	private JLabel p1shot, p2shot;
	private String shotDir[] = {"down", "left", "up", "right"};
	private int p1ShotDir = 3, p2ShotDir = 1;
	private int [] parr = {140, 255, 720};
	
	// Music class objects
	private static Music introMusic = new Music("soundtracks/Black Vortex.wav");
	private static Music endMusic;

	// Imported GameObjects
	private Weapon p1Weapon, p2Weapon;
	private ArrayList<Obstacle> obstacles = new ArrayList<Obstacle>();
	
	private static String[][] weaponChoices = 
	{
		{"Barrett M82", "SCAR-H", "IWI Tavor-21"},
		{"1891 Mosin-Nagant", "Maxim Machinegun", "Winchester Shotgun Model 1897"},
		{"Charleville Musket", "Baker Rifle", "Twelve-Pounder Cannon"}
	};
	
	private static String[][] weaponFiles = 
	{
		{"text_docs/info docs/barret.txt", "text_docs/info docs/SCar.txt", "text_docs/info docs/tavor.txt"},
		{"text_docs/info docs/nagant.txt", "text_docs/info docs/maxim.txt", "text_docs/info docs/winchester.txt"},
		{"text_docs/info docs/musket.txt", "text_docs/info docs/baker.txt", "text_docs/info docs/cannon.txt"}
	};
	
	private String obstacleDocs[] = {"text_docs/obstacles/modern.txt", "text_docs/obstacles/ww1.txt", "text_docs/obstacles/napoleon.txt"};
	
	private static Weapon[][] weapons = new Weapon[3][3];
	{
		weapons[0][0] = new Weapon(false, true, 800, 80, 10, 10, 4000, 500, 5, 0, "sounds/Barrett Fire.wav", "sounds/Barrett Reload.wav");
        weapons[0][1] = new Weapon(false, false, 400, 30, 20, 20, 2000, 50, 4, 0, "sounds/SCAR Fire.wav", "sounds/SCAR Reload.wav"); 
        weapons[0][2] = new Weapon(false, false, 225, 30, 30, 30, 1400, 40, 5, 0, "sounds/Tavor Fire.wav", "sounds/Tavor Reload.wav");
        weapons[1][0] = new Weapon(false, true, 600, 25, 5, 5, 2300, 800, 5, 0, "sounds/Nagant Fire.wav", "sounds/Nagant Reload.wav");
        weapons[1][1] = new Weapon(false, false, 275, 7, 200, 200, 11000, 40, 4, 0, "sounds/Maxim Fire.wav", "sounds/Maxim Reload.wav");
        weapons[1][2] = new Weapon(false, true, 225, 40, 5, 5, 3000, 600, 3, 0, "sounds/Shotgun Fire.wav", "sounds/Shotgun Reload.wav");
        weapons[2][0] = new Weapon(false, false, 225, 75, 1, 1, 6000, 0, 2, 0, "sounds/Musket Fire.wav", "sounds/Musket Reload.wav");
        weapons[2][1] = new Weapon(false, false, 350, 75, 1, 1, 8000, 0, 3, 0, "sounds/Baker Fire.wav", "sounds/Baker Reload.wav");
        weapons[2][2] = new Weapon(false, false, 500, 100, 1, 1, 10000, 0, 2, 0, "sounds/Cannon Fire.wav", "sounds/Cannon Reload.wav");
	}
	
	private static String[][] endTunes = 
    {
    	{
    		"soundtracks/Star Spangled Banner.wav", "soundtracks/Land of the Two Rivers.wav"
        },
        {
            "soundtracks/De Brabanconne.wav", "soundtracks/Heil dir im Siegerkranz.wav"
        },
        {
            "soundtracks/La Marseillaise.wav", "soundtracks/God Save the King.wav"
        }
    };
	
	private JLabel themeMaps[] = new JLabel[3];
	{
		try 
		{
			themeMaps[0] = new JLabel(new ImageIcon(ImageIO.read(getClass().getResourceAsStream("images/Desert.png")).getScaledInstance(800, 450, Image.SCALE_DEFAULT)));
		} 
		catch (IOException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try 
		{
			themeMaps[1] = new JLabel(new ImageIcon(ImageIO.read(getClass().getResourceAsStream("images/WWI.png")).getScaledInstance(800, 450, Image.SCALE_DEFAULT)));
		} 
		catch (IOException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try 
		{
			themeMaps[2] = new JLabel(new ImageIcon(ImageIO.read(getClass().getResourceAsStream("images/Waterloo.png")).getScaledInstance(800, 450, Image.SCALE_DEFAULT)));
		} 
		catch (IOException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * Internal class that takes parameters to delay specific 
	 * actions a set amount of time.
	 * @author Sergey Noritsyn
	 */
	private class PauseThread implements Runnable
	{
		int length;
		String name;
		Thread t;
		Music reload1, reload2;
		PauseThread (String name, int length)
		{
			this.length = length;
			this.name = name;
			t = new Thread(this, name);
			reload1 = new Music(p1Weapon.getReloadSound());
            reload2 = new Music(p2Weapon.getReloadSound());
            t.start();
		}
		
		public void run() 
		{
			long start = Instant.now().toEpochMilli();
			long end = Instant.now().toEpochMilli();
			
			if(this.name.equals("reload 1"))
            {
                reload1.playEffect();
            }
            else if(this.name.equals("reload 2"))
            {
                reload2.playEffect();
            }
			
			// runs for specified amount of time
			while (end - start < this.length)
			{
				end = Instant.now().toEpochMilli();
			}
			
			if (this.name.equals("new bullet 1"))
			{
				p1Weapon.setReloading(false);
			}
			else if (this.name.equals("reload 1"))
			{
				p1Weapon.setReloading(false);
				p1Weapon.setMagCur(p1Weapon.getMagCime());
				p1Ammo.setText(p1Weapon.getMagCur() + "/" + p1Weapon.getMagCime());
			}
			
			else if(this.name.equals("new bullet 2"))
			{
				p2Weapon.setReloading(false);
			}
			else if(this.name.equals("reload 2"))
			{
				p2Weapon.setReloading(false);
				p2Weapon.setMagCur(p2Weapon.getMagCime());
				p2Ammo.setText(p2Weapon.getMagCur() + "/" + p2Weapon.getMagCime());
			}
			
			else if(this.name.equals("turn 1"))
			{
				p1Weapon.setIfTurned(false);
			}
			else if(this.name.equals("turn 2"))
			{
				p2Weapon.setIfTurned(false);
			}
			
			// resets game for next point
			else if(this.name.equals("reset"))
			{
				resetGame();
				startGame();
			}
		}
	}
	
	/**
	 * Creates a PauseThread instance - makes a new thread.
	 * @author Sergey Noritsyn
	 * @param action - defines action done in PauseThread
	 * @param time - duration
	 */
	private void delayTimer(String action, int time)
	{
		new PauseThread(action, time);
	}
	
	/**
	 * Method which runs all actions that constantly occur,
	 * including checking for all sorts of collisions and
	 * user key presses.
	 * @author Sergey Noritsyn
	 */
	@Override
	public void act()
	{
		// update firing position of weapons
		p1Weapon.setLocation(p1.getX() + p1.getWidth() / 2, p1.getY() + p1.getHeight() / 2);
		p2Weapon.setLocation(p2.getX() + p2.getWidth() / 2, p2.getY() + p2.getHeight() / 2);
		
		// Checks if player 1 shoots weapon
		if (ZKeyPressed() && p1Weapon.isReloading() == false)
		{
			p1Weapon.fire();
			
			// updates screen labels and components
			p1Ammo.setText(p1Weapon.getMagCur() + "/" + p1Weapon.getMagCime());
			panel.add(p1Weapon.getBullet(p1Weapon.numBullets() - 1), Integer.valueOf(5));
			
			// checks for reload/delay between shots
			if (p1Weapon.getMagCur() > 0)
			{
				delayTimer("new bullet 1", p1Weapon.getShotDelay());
			}
			else 
			{
				delayTimer("reload 1", p1Weapon.getReloadTime());
			}
		}
		
		// Checks if player 2 shoots weapon
		if (NKeyPressed() && p2Weapon.isReloading() == false)
		{
			p2Weapon.fire();
			
			// updates screen labels and components
			p2Ammo.setText(p2Weapon.getMagCur() + "/" + p2Weapon.getMagCime());
			panel.add(p2Weapon.getBullet(p2Weapon.numBullets() - 1), Integer.valueOf(5));
			
			// checks for reload/delay between shots
			if (p2Weapon.getMagCur() > 0)
			{
				delayTimer("new bullet 2", p2Weapon.getShotDelay());
			}
			else 
			{
				delayTimer("reload 2", p2Weapon.getReloadTime());
			}
		}
		
		// Checks if player 1 turns character (changes shooting direction)
		if (XKeyPressed() && p1Weapon.wasTurned() == false)
		{
			p1Weapon.changeShotDir();
			
			// runs delay on turning again (0.5s)
			p1Weapon.setIfTurned(true);
			delayTimer("turn 1", 500);
			
			// updates screen labels
			p1ShotDir = (p1ShotDir + 1) % 4;
			p1shot.setText("<html><div style='text-align: center;'>Player 1 Direction:<br> " + shotDir[p1ShotDir] + "</div></html>");
		}
		
		// Checks if player 2 turns character (changes shooting direction)
		if (MKeyPressed() && p2Weapon.wasTurned() == false)
		{
			p2Weapon.changeShotDir();
			
			// runs delay on turning again (0.5s)
			p2Weapon.setIfTurned(true);
			delayTimer("turn 2", 500);
			
			// updates screen labels
			p2ShotDir = (p2ShotDir + 1) % 4;
			p2shot.setText("<html><div style='text-align: center;'>Player 2 Direction:<br> " + shotDir[p2ShotDir] + "</div></html>");
		}
		
		checkKeyPresses();
		
		// checks for collisions
		checkBulletWallHits();
		checkBulletPlayerHits();
	}
	
	/**
	 * Allows players to switch their weapons.
	 * Resets and updates all game components and labels on the screen.
	 * @author Sergey Noritsyn
	 */
	private void resetGame()
	{
		// offers to change weapons
		String options[] = weaponChoices[gameTheme];
    	for (int i = 0; i < 2; i++)
        {
            Object choice;
            if (i == 0)
            {
                choice = JOptionPane.showInputDialog(null, "Select your Weapon (Player 1)", "Weapon Selection", JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
            }
            else
            {
                choice = JOptionPane.showInputDialog(null, "Select your Weapon (Player 2)", "Weapon Selection", JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
            }
            for (int j = 0; j < 3; j++)
            {
                if (weaponChoices[gameTheme][j] == choice)
                {
                    weaponType[i] = j;
                    break;
                }
            }
        }
    	
    	// updates weapon selection
		p1Weapon = new Weapon(weapons[gameTheme][weaponType[0]]);
        add(p1Weapon);
        p2Weapon = new Weapon(weapons[gameTheme][weaponType[1]]);
        p2Weapon.setXSpeed(p2Weapon.getXSpeed() * -1);
        add(p2Weapon);
        
    	// reset player 1 components - HP, magazine capacity, location
		p1Weapon.setMagCur(p1Weapon.getMagCime());
		p1Ammo.setText(p1Weapon.getMagCur() + "/" + p1Weapon.getMagCime());
		p1.setHp();
		p1.setLocation(parr[0], parr[1]);
		p1HpBar.setBounds(150, 55, 200, 10);
		
    	// reset player w components - HP, magazine capacity, location
		p2Weapon.setMagCur(p2Weapon.getMagCime());
		p2Ammo.setText(p2Weapon.getMagCur() + "/" + p2Weapon.getMagCime());
		p2.setHp();
		p2.setLocation(parr[2], parr[1]);
		p2HpBar.setBounds(550, 55, 200, 10);
		
		// removes all bullets shot by Player 1 in previous game
		for (int i = 0; i < p1Weapon.numBullets(); i++)
		{
			p1Weapon.removeBullet(i);
			i--;
		}
		
		// removes all bullets shot by Player 2 in previous game
		for (int i = 0; i < p2Weapon.numBullets(); i++)
		{
			p2Weapon.removeBullet(i);
			i--;
		}
        
		// remove all key presses still active from previous game
        for (int i = curKeyPresses.size() - 1; i >= 0; i--)
        {
        	curKeyPresses.remove(i);
        }
        
        // reset player 1 direction
        p1ShotDir = 3;
		p1shot.setText("<html><div style='text-align: center;'>Player 1 Direction:<br> " + shotDir[p1ShotDir] + "</div></html>");
		
		// reset player 2 direction
		p2ShotDir = 1;
		p2shot.setText("<html><div style='text-align: center;'>Player 2 Direction:<br> " + shotDir[p2ShotDir] + "</div></html>");
	}
	
	/**
	 * Checks for collisions between bullets and players.
	 * @author Sergey Noritsyn
	 */
	private void checkBulletPlayerHits()
	{
		// checks every bullet from Player 1
		for (int i = 0; i < p1Weapon.numBullets(); i++)
		{
			if (p1Weapon.getBullet(i).collides(p2))
			{
				// updates HP
				p2.setCurHp(p1Weapon.getDamage());
				if (p2.getCurHp() <= 0)
				{
					// updates score, checks win condition
					score1++;
					p1Score.setText(score1 + "");
					p2HpBar.setBounds(550, 55, 0, 10);
					if (score1 < pointsGoal)
					{
						stopGame();
						delayTimer("reset", 2000);
						return;
					}
					else
					{
						playerWins(1);
					}
				}
				// updates HP bar, removes bullet from magazine
				p2HpBar.setBounds(550, 55, p2.getCurHp() * 2, 10);
				p1Weapon.removeBullet(i);
				i--;
			}
		}
		
		// checks every bullet from Player 2
		for (int i = 0; i < p2Weapon.numBullets(); i++)
		{
			if (p2Weapon.getBullet(i).collides(p1))
			{
				// updates HP
				p1.setCurHp(p2Weapon.getDamage());
				if (p1.getCurHp() <= 0)
				{
					// updates score, checks win condition
					score2++;
					p2Score.setText(score2 + "");
					p1HpBar.setBounds(550, 55, 0, 10);
					if (score2 < pointsGoal)
					{
						stopGame();
						delayTimer("reset", 2000);
						return;
					}
					else
					{
						playerWins(2);
					}
				}
				// updates HP bar, removes bullet from magazine
				p1HpBar.setBounds(150, 55, p1.getCurHp() * 2, 10);
				p2Weapon.removeBullet(i);
				i--;
			}
		}
	}
	
	/**
	 * Ends the game.
	 * @param player - player that won
	 * @author Sergey Noritsyn
	 */
	private void playerWins(int player) 
    {
		// changes background music
		introMusic.stop();
		endMusic = new Music(endTunes[gameTheme][player - 1]);
		endMusic.playMusic();
		
		// displays win message on screen
        if(player == 1)
        {
        	JOptionPane.showMessageDialog(null, "Player I has won this round!", "Winner", JOptionPane.PLAIN_MESSAGE);
        }    
        else
        {
        	JOptionPane.showMessageDialog(null, "Player II has won this round!", "Winner", JOptionPane.PLAIN_MESSAGE);
        } 
        System.exit(0);
    }
	
	/**
	 * Checks collisions between bullets and obstacles on the map.
	 * @author Sergey Noritsyn
	 */
	private void checkBulletWallHits()
	{
		// checks every bullet from Player 1
		for (int i = 0; i < p1Weapon.numBullets(); i++)
		{
			for (Obstacle o : obstacles)
			{
				// checks if obstacle can be shot through
				if (o.isThroughShootable())
				{
					continue;
				}
				
				// checks for collision
				if (p1Weapon.getBullet(i).collides(o))
				{
					p1Weapon.removeBullet(i);
					i--;
					break;
				}
			}
		}
		
		// checks every bullet from Player 2
		for (int i = 0; i < p2Weapon.numBullets(); i++)
		{
			for (Obstacle o : obstacles)
			{
				// checks if obstacle can be shot through
				if (o.isThroughShootable())
				{
					continue;
				}
				
				// checks for collision
				if (p2Weapon.getBullet(i).collides(o))
				{
					p2Weapon.removeBullet(i);
					i--;
					break;
				}
			}
		}
	}
	
	/**
	 * Checks for collision of player with an obstacle directly above.
	 * @param p - player being checked
	 * @return whether there is an obstacle
	 * @author Sergey Noritsyn
	 */
	private boolean upperBoundCheck(Player p)
	{
		for (Obstacle o : obstacles)
		{
			if (p.getY() - p.getYSpeed() < o.getY() + o.getHeight() && p.getY() > o.getY())
			{
				if (p.getX() >= o.getX() && p.getX() + p.getWidth() <= o.getX() + o.getWidth())
				{
					return true;
				}
				if (p.getX() < o.getX() && p.getX() + p.getWidth() > o.getX())
				{
					return true;
				}
				if (p.getX() < o.getX() + o.getWidth() && p.getX() + p.getWidth() > o.getX() + o.getWidth())
				{
					return true;
				}
			}
		}
		return false;
	}
	
	/**
	 * Checks for collision of player with an obstacle directly to the left.
	 * @param p - player being checked
	 * @return whether there is an obstacle
	 * @author Sergey Noritsyn
	 */
	private boolean leftBoundCheck(Player p)
	{
		for (Obstacle o : obstacles)
		{
			if (p.getX() - p.getXSpeed() < o.getX() + o.getWidth() && p.getX() > o.getX())
			{
				if (p.getY() >= o.getY() && p.getY() + p.getHeight() <= o.getY() + o.getHeight())
				{
					return true;
				}
				if (p.getY() < o.getY() && p.getY() + p.getHeight() > o.getY())
				{
					return true;
				}
				if (p.getY() < o.getY() + o.getHeight() && p.getY() + p.getHeight() > o.getY() + o.getHeight())
				{
					return true;
				}
			}
		}
		return false;
	}
	
	/**
	 * Checks for collision of player with an obstacle directly to the right.
	 * @param p - player being checked
	 * @return whether there is an obstacle
	 * @author Sergey Noritsyn
	 */
	private boolean lowerBoundCheck(Player p)
	{
		for (Obstacle o : obstacles)
		{
			if (p.getY() + p.getYSpeed() + p.getHeight() > o.getY() && p.getY() < o.getY() + o.getHeight())
			{
				if (p.getX() >= o.getX() && p.getX() <= o.getX() + o.getWidth() - p.getWidth())
				{
					return true;
				}
				if (p.getX() < o.getX() && p.getX() + p.getWidth() > o.getX())
				{
					return true;
				}
				if (p.getX() < o.getX() + o.getWidth() && p.getX() + p.getWidth() > o.getX() + o.getWidth())
				{
					return true;
				}
			}
		}
		return false;
	}
	
	/**
	 * Checks for collision of player with an obstacle directly below.
	 * @param p - player being checked
	 * @return whether there is an obstacle
	 * @author Sergey Noritsyn
	 */
	private boolean rightBoundCheck(Player p)
	{
		for (Obstacle o : obstacles)
		{
			if (p.getX() + p.getXSpeed() + p.getWidth() > o.getX() && p.getX() + p.getWidth() < o.getX() + o.getWidth())
			{
				if (p.getY() >= o.getY() && p.getY() + p.getHeight() <= o.getY() + o.getHeight())
				{
					return true;
				}
				if (p.getY() < o.getY() && p.getY() + p.getHeight() > o.getY())
				{
					return true;
				}
				if (p.getY() < o.getY() + o.getHeight() && p.getY() + p.getHeight() > o.getY() + o.getHeight())
				{
					return true;
				}
			}
		}
		return false;
	}
	
	/**
	 * Checks all key presses for both players.
	 * @author Sergey Noritsyn
	 */
	private void checkKeyPresses()
	{
		if (curKeyPresses.contains('W') && upperBoundCheck(p1) == false)
		{
			if (p1.getY() - p1.getYSpeed()>= mapPic.getY())
			{
				p1.moveUp();
			}
		}
		if (curKeyPresses.contains('A') && leftBoundCheck(p1) == false)
		{
			if (p1.getX() - p1.getXSpeed() >= mapPic.getX())
			{
				p1.moveLeft();
			}
		}
		if (curKeyPresses.contains('S') && lowerBoundCheck(p1) == false)
		{
			if (p1.getY() + p1.getHeight() + p1.getYSpeed() <= mapPic.getY() + mapPic.getHeight())
			{
				p1.moveDown();
			}
		}
		if (curKeyPresses.contains('D') && rightBoundCheck(p1) == false)
		{
			if (p1.getX() + p1.getWidth() + p1.getXSpeed() <= mapPic.getX() + mapPic.getWidth())
			{
				p1.moveRight();
			}
		}
		if (curKeyPresses.contains('U') && upperBoundCheck(p2) == false)
		{
			if (p2.getY() - p2.getYSpeed() >= mapPic.getY())
			{
				p2.moveUp();
			}
		}
		if (curKeyPresses.contains('H') && leftBoundCheck(p2) == false)
		{
			if (p2.getX() - p2.getXSpeed() >= mapPic.getX())
			{
				p2.moveLeft();
			}
		}
		if (curKeyPresses.contains('J') && lowerBoundCheck(p2) == false)
		{
			if (p2.getY() + p2.getHeight() + p2.getYSpeed() <= mapPic.getY() + mapPic.getHeight())
			{
				p2.moveDown();
			}
		}
		if (curKeyPresses.contains('K') && rightBoundCheck(p2) == false)
		{
			if (p2.getX() + p2.getWidth() + p2.getXSpeed() <= mapPic.getX() + mapPic.getWidth())
			{
				p2.moveRight();
			}
		}
	}
	
	/**
	 * Processes keyboard inputs.
	 * @author Sergey Noritsyn
	 */
	private void addKeyPresses()
	{
		addKeyListener(new KeyListener()
		{
			/**
			 * Checks using switch statement which key is 
			 * pressed and responds accordingly.
			 * @param KeyEvent (user key presses)
			 */
			public void keyPressed(KeyEvent e)
			{
				char key = Character.toUpperCase(e.getKeyChar());
				int keyCode = e.getKeyCode();
				if (keyCode == 38)
				{
					introMusic.volume("up");
				}
				else if (keyCode == 40)
				{
					introMusic.volume("down");
				}
				else if (curKeyPresses.contains(key) == false && key != 'P')
				{
					curKeyPresses.add(key);
				}
			}
			
			/**
			 * Checks for key releases that may result in
			 * game behavior changes.
			 */
			public void keyReleased(KeyEvent e)
			{
				char key = Character.toUpperCase(e.getKeyChar());
				if (key == 'P')
				{
					introMusic.pause();
				}
				else if (curKeyPresses.contains(key))
				{
					curKeyPresses.remove(curKeyPresses.indexOf(key));
				}
			}

			@Override
			public void keyTyped(KeyEvent arg0) {
				// TODO Auto-generated method stub
				
			}
		});
	}
	
	/**
	 * Sets up playing area.
	 * @author Sergey Noritsyn
	 */
	@Override
	public void setup()
	{
		setTitle("Time Warriors");
		ClassLoader loader = Thread.currentThread().getContextClassLoader();
		
		// Frame parameters
		setLayout(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBackground(new Color(153, 102, 0));
		setSize(914, 660);
		setResizable(false);
		
		// Specifies JLayeredPane parameters
		panel.setBounds(0, 0, 914, 660);
		add(panel);
		
		// Create player 1
		p1 = new Player(1, 1, 100, Color.BLUE);
		p1.setOpaque(true);
		p1.setBounds(parr[0], parr[1], 15, 15);
    	panel.add(p1, Integer.valueOf(3));
		
		// Create player 2
		p2 = new Player(1, 1, 100, Color.RED);
		p2.setOpaque(true);
		p2.setBounds(parr[2], parr[1], 15, 15);
		panel.add(p2, Integer.valueOf(3));
		
		// Create weapons
		p1Weapon = new Weapon(weapons[gameTheme][weaponType[0]]);
		add(p1Weapon);
		p2Weapon = new Weapon(weapons[gameTheme][weaponType[1]]);
		p2Weapon.setXSpeed(p2Weapon.getXSpeed() * -1);
		add(p2Weapon);
		
		// Set score labels
		p1Score = new JLabel(score1 + "");
		p1Score.setFont(new Font("Arial", Font.BOLD, 24));
		p1Score.setBounds(250, 10, 40, 40);
		p1Score.setForeground(Color.WHITE);
		panel.add(p1Score, Integer.valueOf(1));
		
		p2Score = new JLabel(score2 + "");
		p2Score.setFont(new Font("Arial", Font.BOLD, 24));
		p2Score.setBounds(610, 10, 40, 40);
		p2Score.setForeground(Color.WHITE);
		panel.add(p2Score, Integer.valueOf(1));
		
		// Set ammo labels
		p1Ammo = new JLabel(p1Weapon.getMagCur() + "/" +p1Weapon.getMagCime());
		p1Ammo.setFont(new Font("Arial", Font.PLAIN, 20));
		p1Ammo.setBounds(210, 540, 80, 40);
		p1Ammo.setForeground(Color.WHITE);
		panel.add(p1Ammo, Integer.valueOf(1));
		
		p2Ammo = new JLabel(p2Weapon.getMagCur() + "/" +p2Weapon.getMagCime());
		p2Ammo.setFont(new Font("Arial", Font.PLAIN, 20));
		p2Ammo.setBounds(570, 540, 80, 40);
		p2Ammo.setForeground(Color.WHITE);
		panel.add(p2Ammo, Integer.valueOf(1));
		
		// Set shooting direction labels
		p1shot = new JLabel("<html><div style='text-align: center;'>Player 1 Direction:<br> " + shotDir[p1ShotDir] + "</div></html>");
		p1shot.setFont(new Font("Arial", Font.BOLD, 14));
		p1shot.setBounds(50, 10, 80, 60);
		p1shot.setForeground(Color.WHITE);
		panel.add(p1shot, Integer.valueOf(1));
		
		p2shot = new JLabel("<html><div style='text-align: center;'>Player 2 Direction:<br> " + shotDir[p2ShotDir] + "</div></html>");
		p2shot.setFont(new Font("Arial", Font.BOLD, 14));
		p2shot.setBounds(770, 10, 80, 60);
		p2shot.setForeground(Color.WHITE);
		panel.add(p2shot, Integer.valueOf(1));
		
		// Set HP bars
		JLabel redBar1 = new JLabel("");
		redBar1.setBounds(150, 55, 200, 10);
		redBar1.setOpaque(true);
		redBar1.setBackground(Color.RED);
		panel.add(redBar1, Integer.valueOf(1));
		
		JLabel redBar2 = new JLabel("");
		redBar2.setBounds(550, 55, 200, 10);
		redBar2.setOpaque(true);
		redBar2.setBackground(Color.RED);
		panel.add(redBar2, Integer.valueOf(1));
		
		p1HpBar = new JLabel();
		p1HpBar.setOpaque(true);
		p1HpBar.setBackground(Color.GREEN);
		p1HpBar.setBounds(150, 55, 200, 10);
		panel.add(p1HpBar, Integer.valueOf(2));
		
		p2HpBar = new JLabel();
		p2HpBar.setOpaque(true);
		p2HpBar.setBackground(Color.GREEN);
		p2HpBar.setBounds(550, 55, 200, 10);
		panel.add(p2HpBar, Integer.valueOf(2));
		
		// Set background image
		InputStream inputStream = loader.getResourceAsStream(obstacleDocs[gameTheme]);
		mapPic = themeMaps[gameTheme];
		mapPic.setOpaque(true);
		mapPic.setBounds(50, 75, 800, 450);
		panel.add(mapPic, Integer.valueOf(0));
		
		// Set obstacles for map
		try 
		{
			readObstacles(inputStream);
		}
		catch (IOException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		addKeyPresses();
	}
	
	/**
	 * Reads parameters for creating Obstacle objects, 
	 * which are stored to an ArrayList field.
	 * @param inputStream - stream for file location
	 * @throws IOException
	 * @author Sergey Noritsyn
	 */
	private void readObstacles(InputStream inputStream) throws IOException
	{
		StringTokenizer st;
		try (BufferedReader br = new BufferedReader(new InputStreamReader(inputStream))) 
		{
			String line;
			while ((line = br.readLine()) != null) 
			{
				st = new StringTokenizer(line);
				obstacles.add(new Obstacle(Integer.parseInt(st.nextToken()) + mapPic.getX(), Integer.parseInt(st.nextToken()) + mapPic.getY(), Integer.parseInt(st.nextToken()) + mapPic.getX(), Integer.parseInt(st.nextToken()) + mapPic.getY(), st.nextToken()));
			}
		}
	}
	
	/**
	 * Processes HTML text from a text file.
	 * @param inputStream
	 * @return HTML-formatted String
	 * @throws IOException
	 * @author Sergey Noritsyn
	 */
	private static String readFromInputStream(InputStream inputStream) throws IOException 
	{
		String result = "";
		try (BufferedReader br = new BufferedReader(new InputStreamReader(inputStream))) 
		{
			String line;
			while ((line = br.readLine()) != null) 
			{
				result += line;
			}
		}
		return result;
	}
	
	/**
	 * Internal class that creates menu and displays
	 * user options on screen.
	 * @author Egor Gorelyy
	 *
	 */
	private static class ActionListenerButtons implements ActionListener //class for menu screen with buttons
    {
    	/**
    	 * Egor Gorelyy
    	 * 1/19/2021
    	 * This class is responsible for the main menu and all the buttons in it.
    	 * Teacher: Peter Benum
    	 */
    	private boolean run = false;  //whether menu is running
    	private JFrame frame = new JFrame("Main Menu"); //main frame
    	private JButton start = new JButton("New Game");   //new game button
    	private JButton controls = new JButton("Controls");  //controls button
    	private JButton credits = new JButton("Credits");  //credits button
    	private JButton sand = new JButton("Sand, Oil and Blood");  //1st game mode button
    	private JButton ww1 = new JButton("Crawling in the Trenches"); //2nd game mode button
    	private JButton napo = new JButton("Marching with Napoleon");  //3rd game mode button
    	private JButton dis = new JButton("Back");  //Back button 
    	private String img0 = "images/menu/timelogo2.png";  //all the various image screens and their addresses
    	private String img3 = "images/menu/controls.png";
    	private String img1 = "images/menu/credits.png";
    	private String img2 = "images/menu/start.png";
    	ImageIcon icon = new ImageIcon();
    	{
    		try 
 			{
 				icon = new ImageIcon(ImageIO.read(getClass().getResourceAsStream(img0)));
 			} 
 			catch (IOException e) 
 			{
 				// TODO Auto-generated catch block
 				e.printStackTrace();
 			}
    	}
        private JLabel label = new JLabel(icon, JLabel.CENTER);
        /**
         * action listener buttons
         */
        private ActionListenerButtons()
        {
    		 dis.setBounds(849, 550, 195, 60); //back button which goes back
         	 buttonColor(dis);               //sets button colors and styles
         	 sand.setBounds(100, 430, 400, 60);  //sets bounds for the 3 themed buttons
     	     buttonColor(sand);
     	     ww1.setBounds(100, 490, 400, 60);
    	     buttonColor(ww1);
    	     napo.setBounds(100, 550, 400, 60);
     	     buttonColor(napo);
     	    
    	     frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);  //sets the frame and its respective size
    	     frame.setSize(1297, 768);
    	     frame.setLocationRelativeTo(null);
    	     frame.setVisible(true);  //sets the frame visible
    	        
    	     start.setBounds(50, 50, 195, 60); //sets the dimensions of the start, controls, credits buttons. their styles as well as adding them to the frame
    	     buttonColor(start);
    	     frame.add(start); 
    	     start.setActionCommand("new game");

    	     controls.setBounds(50, 150, 195, 60);
    	     buttonColor(controls);
    	     frame.add(controls); 
    	     controls.setActionCommand("controls");

    	     credits.setBounds(50, 250, 195, 60);
    	     buttonColor(credits);
    	     frame.add(credits); 
    	     credits.setActionCommand("credits");
    		    
    	     //ajouter les deux JLabel a JFrame
    	     frame.getContentPane().add(label);  //adds the image label to the pane
    	     frame.validate();  //validates it

    	     start.addActionListener(this); //adds action listeners to all the buttons
    	     controls.addActionListener(this);
    	     credits.addActionListener(this);
    	     frame.setLayout(new BorderLayout());
    	     introMusic.playMusic();
        }
        

        /**
         * action performer; performs actions when buttons are clicked
         */
        @Override
        public void actionPerformed(ActionEvent e) 
        {
        	if (e.getSource() == dis) //if back button is clicked
            {
        		start.setVisible(true); //sets certain buttons visible and other invisible
        		controls.setVisible(true);  //sets the new image icon and revalides the whole panel
        		credits.setVisible(true);
        		dis.setVisible(false);
        		napo.setVisible(false);
        		ww1.setVisible(false);
        		sand.setVisible(false);
        		label.setIcon(null);
        		ImageIcon icon2 = icon;
         	    label.setIcon(icon2);  
         	    label.revalidate();	
            }
        	else if (e.getSource() == napo) //updates gameTheme with chosen game mode and closes the menu - proceeding to game setup
            {
        		gameTheme = 2;
        		run = true;
        		frame.setVisible(false);
        		
        		frame.dispose();
            } 
        	else if (e.getSource() == sand)  //updates gameTheme with chosen game mode and closes the menu - proceeding to game stup
            {
        		gameTheme = 0;
        		run = true;
        		frame.setVisible(false);

        		frame.dispose();
            }
        	else if (e.getSource() == ww1) //updates gameTheme with chosen game mode and closes the menu - proceeding to game stup
            {
        		gameTheme = 1;
        		run = true;
        		frame.setVisible(false);

        		frame.dispose();
            }
        	else if (e.getSource() == start) //if start button is clicked it shows all the appropriate option buttons i.e. game modes and sets the icon
            {
        		dis.setVisible(true); //sets different buttons visible and invisible depending on the situation
        		napo.setVisible(true);
        		ww1.setVisible(true);
        		sand.setVisible(true);
        		start.setVisible(false);
        		controls.setVisible(false);
        		credits.setVisible(false);
        		label.setIcon(null);
        		ImageIcon icon1 = new ImageIcon();
				try 
				{
					icon1 = new ImageIcon(ImageIO.read(getClass().getResourceAsStream(img2)));
				} catch (IOException e1) 
				{
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
         	    label.setIcon(icon1);  
         	    
         	    frame.add(sand); 
         	    sand.setActionCommand("Sand, Oil and Blood");
         	    sand.addActionListener(this); //adds all the action listeners
         	    
         	    frame.add(ww1); 
        	    ww1.setActionCommand("Crawling in the Trenches");
        	    ww1.addActionListener(this);
        	    
        	    frame.add(napo); 
         	    napo.setActionCommand("Marching with Napoleon");
         	    napo.addActionListener(this);
         	    
         	    
         	    frame.add(dis); 
    		    dis.setActionCommand("Back");
    		    dis.addActionListener(this);
         	   	frame.getContentPane().add(label);
        	    label.revalidate(); //revalidates the frame
            }
            else if (e.getSource() == controls) //if controls button is clicked sets certain buttons visible and others not. updates iamge. adds it to frame and revalidates
            {
            	dis.setVisible(true);
        		start.setVisible(false);
        		controls.setVisible(false);
        		credits.setVisible(false);
        		label.setIcon(null);
        		ImageIcon icon3 = new ImageIcon();
				try {
					icon3 = new ImageIcon(ImageIO.read(getClass().getResourceAsStream(img3)));
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
         	    label.setIcon(icon3);  
         	    
         	    frame.add(dis); 
    		    dis.setActionCommand("Back");
    		    dis.addActionListener(this);
         	   	frame.getContentPane().add(label);
        	    label.revalidate();
            }
            else if (e.getSource() == credits) //if credits button is clicked sets certain buttons visible and others not. updates iamge. adds it to frame and revalidates
            {
            	dis.setVisible(true);
        		start.setVisible(false);
        		controls.setVisible(false);
        		credits.setVisible(false);
        		label.setIcon(null);
        		ImageIcon icon4 = new ImageIcon();
				try {
					icon4 = new ImageIcon(ImageIO.read(getClass().getResourceAsStream(img1)));
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
         	    label.setIcon(icon4);  
         	    
         	    frame.add(dis); 
    		    dis.setActionCommand("Back");
    		    dis.addActionListener(this);
         	    frame.getContentPane().add(label);
        	    label.revalidate();        	 
            }	
        }
        
        /**
         * customizes the button with the appropriate style
         * @param bt is the passed button
         */
        public void buttonColor(JButton bt) 
    	{
    		bt.setBorder(BorderFactory.createBevelBorder(1, Color.BLUE, Color.BLUE));//sets bevel border;
    		bt.setFont(new Font("Arial", Font.BOLD, 30)); //sets font and size
    	    bt.setBackground(new Color(32, 32, 32)); //background color
    	    bt.setForeground(Color.RED); //sets foreground color of text
    	}
        
        /**
         * accessor method for whether the setup can be run (true or false)
         * @return run and if its true or false - determing to run setup or not run it
         */
        public boolean getRun()
    	{
    		return run;
    	}  
    }
	
	public static void main(String[] args)
	{
		ClassLoader loader = Thread.currentThread().getContextClassLoader();
		ActionListenerButtons test = new ActionListenerButtons();  //new instance of ActionListener to run menu
		while(test.getRun() == false) //while menu is running the rest of the game is not being run
    	{
    		System.out.println("");
    	}
        String options[] = weaponChoices[gameTheme];  //appropriate weapon options are presented based on theme chosen	      
        
		for (int i = 0; i < 2; i++)
		{
			Object choice;
			if (i == 0)
			{
				choice = JOptionPane.showInputDialog(null, "Select your Weapon (Player 1)", "Weapon Selection", JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
			}
			else
			{
				choice = JOptionPane.showInputDialog(null, "Select your Weapon (Player 2)", "Weapon Selection", JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
			}
			for (int j = 0; j < 3; j++)
			{
				if (weaponChoices[gameTheme][j] == choice)
				{
					weaponType[i] = j;
					break;
				}
			}
			String displayWeapon = "";
			InputStream inputStream = loader.getResourceAsStream(weaponFiles[gameTheme][weaponType[i]]);
			// reads info about weapon from file to a MessageDialog JOptionPane
			try 
			{
				displayWeapon = readFromInputStream(inputStream);
			} 
			catch (IOException e) 
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			JOptionPane.showMessageDialog(null, displayWeapon, "Your Weapon Choice", JOptionPane.PLAIN_MESSAGE);
		}
        
		// Take input for number of points to which match is played 
		String pointNumString = JOptionPane.showInputDialog("Enter Amount of Points Needed to Win.", "10");     //prompts user to enter point limit
        do
        {
            try
            {
                   pointsGoal = Integer.parseInt(pointNumString);      
                   if (pointsGoal < 1)
                   {
                       JOptionPane.showMessageDialog(null, "Enter a number more than 0", "Error!", JOptionPane.PLAIN_MESSAGE);     //error message is shown
                       pointNumString = JOptionPane.showInputDialog("Enter Amount of Points Needed to Win", "10"); 
                   }
            }
            catch (NumberFormatException exception)            //catches number format exception
            {
                JOptionPane.showMessageDialog(null, "Enter a number", "Error!", JOptionPane.PLAIN_MESSAGE);     //error message is shown
                  pointNumString = JOptionPane.showInputDialog("Enter Amount of Points Needed to Win", "10"); 
            }
        }
        while (pointsGoal < 1);
        
		TimeWarriors t = new TimeWarriors();
		t.setVisible(true);
		t.initComponents();
	}
}