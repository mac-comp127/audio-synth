package audiosynth;

import javax.sound.sampled.*;
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

    private static final double LATENCY_COMPENSATION = 1.0 / 30;

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

    public float getPeak() {
        float max = 0;
        for (float sample : samples) {
            if (Math.abs(sample) > max) {
                max = Math.abs(sample);
            }
        }
        return max;
    }

    public void normalize() {
        float peak = getPeak();
        for (int n = 0; n < samples.length; n++) {
            samples[n] /= peak;
        }
    }

    public void mix(Waveform waveform, int offset, int duration) {
        for(int n = 0; n < duration && n + offset < samples.length; n++) {
            samples[n + offset] += waveform.amplitudeAt(n);
        }
    }

    public void play() {
        play(() -> {});
    }

    public void play(Runnable completionCallback) {
        play(t -> { }, completionCallback);
    }

    public void play(PlayingCallback playingCallback, Runnable completionCallback) {
        playAudioData(
            convertSamplesToRawData(),
            playingCallback,
            keepJVMAliveUntilCompletion(completionCallback));
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

    private void playAudioData(byte[] audioData, PlayingCallback playingCallback, Runnable completionCallback) {
        try {
            Clip clip = AudioSystem.getClip();
            clip.open(new AudioFormat(SAMPLE_RATE, 16, 1, true, true), audioData, 0, audioData.length);
            clip.start();
            Timer playbackPoller = new Timer(33, event -> {
                playingCallback.updateTime((double) clip.getLongFramePosition() / SAMPLE_RATE + LATENCY_COMPENSATION);
            });
            playbackPoller.setInitialDelay(0);
            playbackPoller.start();
            clip.addLineListener(event -> {
                if (event.getType() == LineEvent.Type.STOP) {
                    completionCallback.run();
                    playbackPoller.stop();
                }
            });
        } catch (LineUnavailableException e) {
            throw new RuntimeException("Unable to play audio", e);
        }
    }

    private Runnable keepJVMAliveUntilCompletion(Runnable completionCallback) {
        Object completionLock = new Object();

        // Non-daemon Thread keeps JVM alive while audio is playing, even if main() exits
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
                try {
                    completionCallback.run();
                } catch(Exception e) {
                    System.err.println("Exception in audio completion callback");
                    e.printStackTrace();
                }
                completionLock.notifyAll();
            }
        };
    }

    public static interface PlayingCallback {
        void updateTime(double currentTime);
    }
}
