import javax.sound.sampled.*;
import java.io.IOException;
import java.net.URL;

/**
 * This class is used to handle sound effects in the game. It allows for playing, pausing,
 * resuming, and looping sound clips with customizable volume.
 */
public class SoundEffect {
    private Clip clip;
    private int pausePosition = 0;
    private boolean shouldLoop;

    /**
     * Constructor to initialize and load a sound effect.
     *
     * @param soundFileName The name of the sound file to be loaded (must be placed in the /Sounds/ folder).
     * @param loop A boolean flag indicating whether the sound should loop.
     * @param volume The volume of the sound, where 1.0 is the original volume.
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

            // Set volume
            if (clip.isControlSupported(FloatControl.Type.MASTER_GAIN)) {
                FloatControl gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
                float dB = (float) (Math.log10(volume) * 20.0);
                gainControl.setValue(dB);
            }

            this.shouldLoop = loop;

            // Loop handling
            clip.addLineListener(e -> {
                if (e.getType() == LineEvent.Type.STOP && clip.getFramePosition() >= clip.getFrameLength()) {
                    if (shouldLoop) {
                        clip.setFramePosition(0);
                        clip.start();
                    }
                }
            });

            clip.start();

        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            e.printStackTrace();
        }
    }

    /**
     * Stops the sound effect, saving the current position to resume from later.
     */
    public void stop() {
        if (clip != null && clip.isRunning()) {
            pausePosition = clip.getFramePosition();
            clip.stop();
        }
    }

    /**
     * Resumes playing the sound effect from where it was previously stopped.
     */
    public void play() {
        if (clip != null) {
            clip.setFramePosition(pausePosition);
            clip.start();
        }
    }

    /**
     * Checks if the sound effect is currently playing.
     *
     * @return true if the sound is currently playing, false otherwise.
     */
    public boolean isPlaying() {
        return clip != null && clip.isRunning();
    }
}

