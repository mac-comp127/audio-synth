package audiosynth.model;

import org.junit.jupiter.api.Test;

import java.util.List;

import audiosynth.audio.AudioBuffer;
import audiosynth.audio.Waveform;

import static org.junit.jupiter.api.Assertions.*;

class SongTest {
    private static final double CONCERT_A_WAVELENGTH = AudioBuffer.SAMPLE_RATE / 880.0;

    private Song song = new Song();
    private Waveform waveform0 = wavelength -> t -> wavelength;
    private Waveform waveform1 = wavelength -> t -> (t % 2 * 2 - 1) * CONCERT_A_WAVELENGTH * 3;

    @Test
    void startsEmpty() {
        assertEquals(List.of(), song.getNotes());
    }

    @Test
    void notesAreUnmodifiable() {
        assertThrows(UnsupportedOperationException.class, () ->
            song.getNotes().add(new Note(waveform0, 0, 0, 0)));
    }

    @Test
    void getDuration() {
        assertEquals(0, song.getDuration());
        song.addNote(new Note(waveform0, 5, 4.5, 1.5));
        assertEquals(6, song.getDuration());
        song.addNote(new Note(waveform1, 7, 10, 2));
        assertEquals(12, song.getDuration());
        song.addNote(new Note(waveform0, 7, 9, 2.5));
        assertEquals(12, song.getDuration());
    }
}
