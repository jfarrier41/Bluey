import javax.sound.sampled.*;
import java.io.IOException;
import java.net.URL;

/**
 * @Author: Jace Claassen
 * @Author: Joseph Farrier
 * Handles loading and playing sound effects for the game.
 * Allows for playback control, looping, and volume adjustment.
 */
public class SoundEffect {
    /** The Clip object used to play the sound. */
    private Clip clip;

    /**
     * Creates and initializes a sound effect.
     *
     * @param soundFileName The name of the sound file located in the "/Sounds/" directory (e.g., "maintheme.wav")
     * @param loop Whether the sound should loop continuously
     * @param volume A float from 0.0 (mute) to 1.0 (full volume)
     */
    public SoundEffect(String soundFileName, boolean loop, float volume) {
        try {
            URL soundURL = SoundEffect.class.getResource("/Sounds/" + soundFileName);
            if (soundURL == null) {
                System.err.println("Sound file not found: " + soundFileName);
                return;
            }

            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(soundURL);
            clip = AudioSystem.getClip();
            clip.open(audioInputStream);

            // Volume control
            if (clip.isControlSupported(FloatControl.Type.MASTER_GAIN)) {
                FloatControl gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);

                // Convert 0.0–1.0 range to decibels
                float min = gainControl.getMinimum();
                float max = gainControl.getMaximum();
                float dB = (float) (Math.log10(Math.max(volume, 0.0001)) * 20.0);
                dB = Math.max(min, Math.min(max, dB));
                gainControl.setValue(dB);
            }

            if (loop) {
                clip.loop(Clip.LOOP_CONTINUOUSLY);
            } else {
                clip.start();
            }

        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            System.err.println("Error playing sound: " + soundFileName);
            e.printStackTrace();
        }
    }
}
