package audiosynth;

import audiosynth.audio.SineWave;
import audiosynth.audio.SquareWave;
import audiosynth.audio.TriangularWave;
import audiosynth.model.Note;
import audiosynth.model.Song;
import audiosynth.model.SongReader;

/**
 * Generates and visualizes music.
 */
public class AudioSynth {
    public static void main(String[] args) {
        // 3-note test song

        Song song = new Song();
        song.addNote(new Note(new SineWave(), 51, 0, 2.0));
        song.addNote(new Note(new TriangularWave(), 58, 0.5, 1.5));
        song.addNote(new Note(new SquareWave(), 67, 1.0, 1.0));

        // Longer test song

//        String fileName = "kondo.csv";
//        Song song = new SongReader().readSong(fileName);
//
//        song.renderAudio().play();
    }
}
