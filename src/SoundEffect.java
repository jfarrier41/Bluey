import javax.sound.sampled.*;
import java.io.IOException;
import java.net.URL;

public class SoundEffect {
    private Clip clip;

    /**
     * @param soundFileName The name of the sound file (e.g., "maintheme.wav")
     * @param loop Whether the sound should loop
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

                // Convert 0.0–1.0 to decibels
                float min = gainControl.getMinimum();
                float max = gainControl.getMaximum();
                float gain = (max - min) * volume + min;
                gainControl.setValue(gain);
            }

            if (loop) {
                clip.loop(Clip.LOOP_CONTINUOUSLY);
            } else {
                clip.start();
            }
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            e.printStackTrace();
        }
    }

    /**
     * Start the clip if not already running.
     */
    public void start() {
        if (clip != null && !clip.isRunning()) {
            clip.start();
        }
    }

    /**
     * Stop the clip if it is running.
     */
    public void stop() {
        if (clip != null && clip.isRunning()) {
            clip.stop();
        }
    }

    /**
     * Adjust the volume (loudness) of the clip.
     * @param volume A float from 0.0 (mute) to 1.0 (full volume)
     */
    public void adjustVolume(float volume) {
        if (clip != null && clip.isControlSupported(FloatControl.Type.MASTER_GAIN)) {
            FloatControl gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);

            // Convert 0.0–1.0 to decibels
            float min = gainControl.getMinimum();
            float max = gainControl.getMaximum();
            float gain = (max - min) * volume + min;
            gainControl.setValue(gain);
        }
    }
}
