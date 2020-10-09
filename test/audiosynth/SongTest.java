package audiosynth;

import audiosynth.waveform.Waveform;
import org.junit.jupiter.api.Test;

import java.util.List;

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
}
