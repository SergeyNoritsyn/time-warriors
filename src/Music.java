/******************************************************************************
Name: James Zhang
Course: ICS4UE
Date: January 22, 2021
Teacher: Mr. Benum
Music
*******************************************************************************/
import javax.sound.sampled.*;
import java.io.BufferedInputStream;
 
import csta.ibm.pong.GameObject;
/**
 * The class that plays music and sound effects.
 * @author James Zhang
 * @version 2021-01-20
 */
public class Music extends GameObject
{
    private Clip clip;
    private FloatControl floatControl;
    public static boolean paused;
    public static int volume = 0;
    
    /**
     * Creates a music object.
     * @param clipLocation The clip's directory respective to the resource folder.
     */
    public Music(String clipLocation)
    {
        try
        {
            BufferedInputStream buffer = new BufferedInputStream(getClass().getResourceAsStream(clipLocation));
            AudioInputStream audioInput = AudioSystem.getAudioInputStream(buffer);
            clip = AudioSystem.getClip();
            clip.open(audioInput);
            floatControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
    }
    
  
    /**
     * Plays audio in a loop.
     */
    void playMusic()
    {
        floatControl.setValue(volume);
        clip.start();
        clip.loop(Clip.LOOP_CONTINUOUSLY);
    }
    
    /**
     * Plays audio, but only once per call.
     */
    void playEffect()
    {
        floatControl.setValue(volume);
        clip.start();
    }
    
    /**
     * Pauses a clip that is playing.
     */
    void stop()
    {
    	clip.stop();
    }
    
    /**
     * Volume controls.
     * @param direction - specifies if volume should increase or decrease.
     */
    public void volume(String direction)
    {
        if (direction.equals("up") && volume < 5)
        {
            volume += 5;
        }
        else if (direction.equals("down") && volume >= -75)
        {
            volume -= 5;
        }
        floatControl.setValue(volume);
        clip.start();
    }
    
    /**
     * Mutes/unmutes audio and restarts the clip.
     */
    void pause()
    {
        if(paused == false)
        {
            volume = -80;
            paused = true;
        }
        else if(paused == true)
        {
            volume = 0;
            paused = false;
        }
        floatControl.setValue(volume);
        clip.start();
    }
    
    /**
     * Sets volume.
     * @param volume - desired volume level.
     */
    public static void setVolume(int volume)
    {
        Music.volume = volume;
    }
    
    /**
     * Returns volume.
     * @return program's master volume.
     */
    public static int getVolume()
    {
        return volume;
    }
    
    /**
     * Operates actions per millisecond.
     */
    @Override
    public void act()
    {
        if(paused == false)
        {
            floatControl.setValue(volume);
            clip.start();
        }   
    }
}