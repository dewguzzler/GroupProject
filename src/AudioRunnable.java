import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

/**
 * 
 */

/** Class: AudioRunnable.java
 *
 * @author Aida Syrkett
 * @version 1.0
 * Written: Nov 1, 2013
 *
 */
public class AudioRunnable implements Runnable
{
	
	private String filename;
	private int loops;
	
	public AudioRunnable(String filename, int loops)
	{
		this.filename = filename;
		this.loops = loops;
	}
	
	public String getFilename()
	{
		return filename;
	}
	
	public void run()
	{
		Clip clip = getClip(filename);
		clip.loop(loops);

	}
	
	public static Clip getClip(String filename)
	{
		Clip clip = null;
		try
		{
			AudioInputStream audio = AudioSystem.getAudioInputStream(AudioRunnable.class.getResource(filename));
			clip = AudioSystem.getClip();
			clip.open(audio);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return clip;
	}
}
