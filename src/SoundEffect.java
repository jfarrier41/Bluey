import javax.sound.sampled.*;
import java.io.IOException;
import java.net.URL;

public class SoundEffect {
    private Clip clip;
    private int pausePosition = 0;
    private boolean shouldLoop;

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

    public void stop() {
        if (clip != null && clip.isRunning()) {
            pausePosition = clip.getFramePosition();
            clip.stop();
        }
    }

    public void play() {
        if (clip != null) {
            clip.setFramePosition(pausePosition);
            clip.start();
        }
    }

    /**
     * Returns whether the sound is currently playing.
     */
    public boolean isPlaying() {
        return clip != null && clip.isRunning();
    }
}

