package audiosynth;

import audiosynth.instrument.Instrument;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

class SongTest {
    private static final double CONCERT_A_WAVELENGTH = AudioBuffer.SAMPLE_RATE / 880.0;

    private Song song = new Song();
    private Instrument instrument0 = wavelength -> t -> wavelength;
    private Instrument instrument1 = wavelength -> t -> (t % 2 * 2 - 1) * CONCERT_A_WAVELENGTH * 3;

    @Test
    void startsEmpty() {
        assertEquals(List.of(), song.getNotes());
    }

    @Test
    void getDuration() {
        song.addNote(new Note(instrument0, 5, 4.5, 1.5));
        assertEquals(6, song.getDuration());
        song.addNote(new Note(instrument1, 7, 10, 2));
        assertEquals(12, song.getDuration());
        song.addNote(new Note(instrument0, 7, 9, 2.5));
        assertEquals(12, song.getDuration());
    }

    @Test
    void renderAudio() {
        double sampleLen = 1.0 / AudioBuffer.SAMPLE_RATE;
        song.addNote(new Note(instrument0, 81, 2 * sampleLen, 4 * sampleLen));
        song.addNote(new Note(instrument1, 33, 4 * sampleLen, 5 * sampleLen));

        AudioBuffer audio = song.renderAudio();
        assertArrayEquals(
            new float[] { 0, 0, 0.25f, 0.25f, -0.5f, 1, -0.75f, 0.75f, -0.75f },
            audio.getSamples(),
            0.0001f);
    }
}
