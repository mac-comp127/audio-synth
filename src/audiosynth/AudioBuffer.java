package audiosynth;

import javax.sound.sampled.*;
import javax.swing.SwingUtilities;
import javax.swing.Timer;

/**
 * A playable, modifiable, fixed-length buffer of audio samples. Only supports monophonic (i.e. not
 * stereo) audio. All AudioBuffers shared a single fixed sample rate, set with the SAMPLE_RATE
 * constant.
 *
 * Samples are 32-bit floats in the range [-1,1]. Samples outside that range are clipped during
 * playback.
 */
public class AudioBuffer {
    /**
     * The sample rate of all AudioBuffer instances, in samples / second.
     */
    public static final int SAMPLE_RATE = 48000;

    // Compensation for latency cause by using a Swing timer when sending playback events.
    private static final double LATENCY_COMPENSATION = 1.0 / 60;

    private final float[] samples;

    /**
     * Creates a new audio buffer filled with zeros.
     *
     * @param size The number of samples in the buffer (see SAMPLE_RATE).
     */
    public AudioBuffer(int size) {
        samples = new float[size];
    }

    /**
     * Returns the raw audio data. Callers may modify the contents of this array.
     */
    public float[] getSamples() {
        return samples;
    }

    /**
     * Fills the entire buffer by sampling the given signal, overwriting any data already present.
     */
    public void fill(Signal signal) {
        for (int n = 0; n < samples.length; n++) {
            samples[n] = (float) signal.amplitudeAt(n);
        }
    }

    /**
     * Returns the peak amplitude, i.e. the largest absolute value of any individual sample.
     */
    public float getPeak() {
        float max = 0;
        for (float sample : samples) {
            if (Math.abs(sample) > max) {
                max = Math.abs(sample);
            }
        }
        return max;
    }

    /**
     * Uniformly scales all samples so that the peak amplitude is 1.
     */
    public void normalize() {
        float peak = getPeak();
        for (int n = 0; n < samples.length; n++) {
            samples[n] /= peak;
        }
    }

    /**
     * Plays this buffer to the system's default sound output. This method is _asynchronous_: it
     * does not wait for playback to finish.
     */
    public void play() {
        play((time, done) -> {});
    }

    /**
     * Asynchronously plays this buffer to the system's default sound output.
     *
     * @param playingCallback Called periodically during audio playback, indicating the current
     *                        playback position and whether the audio is done playing.
     */
    public void play(PlayingCallback playingCallback) {
        playAudioData(
            convertSamplesToRawData(),
            playingCallback);
    }

    /**
     * Converts the float audio to 16-bit samples for playback.
     */
    private byte[] convertSamplesToRawData() {
        byte[] audioData = new byte[samples.length * 2];
        for (int i = 0, out = 0; i < samples.length; i++) {
            short sample16 = (short) (samples[i] * Short.MAX_VALUE);
            audioData[out++] = (byte) (sample16 >> 8);
            audioData[out++] = (byte) sample16;
        }
        return audioData;
    }

    private void playAudioData(byte[] audioData, PlayingCallback playingCallback) {
        try {
            Clip clip = AudioSystem.getClip();
            clip.open(new AudioFormat(SAMPLE_RATE, 16, 1, true, true), audioData, 0, audioData.length);
            clip.start();

            Timer playbackPoller = new Timer(33, event -> {
                playbackUpdated(playingCallback, clip.getLongFramePosition(), false);
            });
            playbackPoller.setInitialDelay(0);
            playbackPoller.start();

            Runnable keepAliveCompletion = keepJVMAlive();
            clip.addLineListener(event -> {
                if (event.getType() == LineEvent.Type.STOP) {
                    SwingUtilities.invokeLater(() -> {
                        playbackUpdated(playingCallback, Long.MAX_VALUE, true);
                        playbackPoller.stop();
                        keepAliveCompletion.run();
                    });
                }
            });
        } catch (LineUnavailableException e) {
            throw new RuntimeException("Unable to play audio", e);
        }
    }

    private void playbackUpdated(PlayingCallback playingCallback, long samplePosition, boolean isComplete) {
        double endTime = (double) samples.length / SAMPLE_RATE;
        try {
            playingCallback.playbackUpdated(
                Math.min(endTime, (double) samplePosition / SAMPLE_RATE + LATENCY_COMPENSATION),
                isComplete);
        } catch(Exception e) {
            System.err.println("Exception in audio completion callback");
            e.printStackTrace();
        }
    }

    // Creates a non-daemon Thread to keep JVM alive while audio is playing, even if main() exits
    private Runnable keepJVMAlive() {
        Object completionLock = new Object();

        Thread completionThread = new Thread(() -> {
            synchronized(completionLock) {
                try {
                    completionLock.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    // intentionally ignored
                }
            }
        });
        completionThread.setDaemon(false);
        completionThread.start();

        return () -> {
            synchronized(completionLock) {
                completionLock.notifyAll();
            }
        };
    }

    /**
     * A functional interface for receiving updates about audio playback.
     *
     * See AudioBuffer.play(PlayingCallback).
     */
    public static interface PlayingCallback {
        /**
         * Called periodically during audio playback.
         *
         * @param currentTime The current offset in seconds from the beginning of the audio buffer.
         * @param isComplete True if the audio is done playing.
         */
        void playbackUpdated(double currentTime, boolean isComplete);
    }
}
