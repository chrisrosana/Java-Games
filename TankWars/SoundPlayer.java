package TankGameMaster;


import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import java.net.URL;

public class SoundPlayer {

    private static URL soundStream;
    public String soundFile;
    private static Clip clip;

    public static void SoundPlayer(boolean looping, String soundFile)
    {
        try{
            soundStream = TankWorld.getTankWorld().getClass().getClassLoader().getResource(soundFile);
            clip = AudioSystem.getClip();


            //bool check if continous loop (background)
            if(looping)
            {
                AudioInputStream audioInput = AudioSystem.getAudioInputStream(soundStream);
                Clip looper = AudioSystem.getClip();
                looper.open(audioInput);
                looper.loop(Clip.LOOP_CONTINUOUSLY);
            }

            //else play once (bullet, explosions, PowerUP)
            else
            {
                clip.start();
                clip.stop();
            }

        }

        //check for file
        catch(Exception e)
        {
            System.out.println(e.getMessage() + "No sound documents are Found");
        }
    }
}