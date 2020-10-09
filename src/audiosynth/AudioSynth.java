package audiosynth;

import audiosynth.waveform.SineWave;
import audiosynth.waveform.SquareWave;
import audiosynth.waveform.TriangularWave;

/**
 * Generates and visualizes music.
 */
public class AudioSynth {
    public static void main(String[] args) {
        Song song = new Song();
        song.addNote(new Note(new SineWave(), 51, 0, 2.0));
        song.addNote(new Note(new TriangularWave(), 58, 0.5, 1.5));
        song.addNote(new Note(new SquareWave(), 67, 1.0, 1.0));
    }
}
