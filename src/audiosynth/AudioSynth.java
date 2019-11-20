package audiosynth;

import audiosynth.waveform.SineWave;

/**
 * Generates and visualizes music.
 */
public class AudioSynth {
    public static void main(String[] args) {
        AudioBuffer audio = new AudioBuffer(20000);
        audio.fill(new SineWave().createSignal(127));
        audio.play();
    }
}
