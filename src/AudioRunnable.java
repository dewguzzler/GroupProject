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
 *AudioRunnable – This class loops the game music audio
 *
 * Purpose: – To fulfill the audio requirement for the game
 */
public class AudioRunnable implements Runnable
{
 
 private String filename; //name of the audio file being used
 private int loops; //number of times the audio file will loop
 
  /** Method: AudioRunnable * defines the filename and loops variables
   * @param String filename name of the file to be played
   * @param int loops number of times the audio file is looped
   */
 
 public AudioRunnable(String filename, int loops)
 {
  this.filename = filename;
  this.loops = loops;
 }
 
  /** Method: getFilename * returns the filename given
   * @returns filename
   * 
   */
 
 public String getFilename()
 {
  return filename;
 }
 
  /** Method: run * accesses the getClip method to find audio file and loop it infinitely
  * 
  * 
  */
 
 public void run()
 {
  Clip clip = getClip(filename);
  clip.loop(loops);

 }
 
  /** Method: getClip * Opens an audio file and plays it
  * @param String filename name of the file to be played
  * @return the audio file to be opened
  */
  
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
