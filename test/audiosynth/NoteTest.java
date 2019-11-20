package audiosynth;

import audiosynth.waveform.SineWave;
import audiosynth.waveform.SquareWave;
import audiosynth.waveform.Waveform;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class NoteTest {
    private Waveform waveform = new SineWave();
    private Waveform waveform2 = new SquareWave();

    @Test
    void construction() {
        Note note = new Note(waveform, 32, 88.5, 0.8);
        assertSame(waveform, note.getWaveform());
        assertEquals(32, note.getPitch());
        assertEquals(88.5, note.getStartTime());
        assertEquals(0.8, note.getDuration());
        assertThrows(NullPointerException.class, () -> {
            new Note(null, 32, 88.5, 0.8);
        });
    }

    @Test
    void getEndTime() {
        assertEquals(89.3, new Note(waveform, 6, 88.5, 0.8).getEndTime());
    }

    @Test
    void testEquals() {
        Note note = new Note(waveform, 1, 2, 3);
        assertEquals(new Note(waveform, 1, 2, 3), note);
        assertNotEquals(new Note(waveform2, 1, 2, 3), note);
        assertNotEquals(new Note(waveform, 7, 2, 3), note);
        assertNotEquals(new Note(waveform, 1, 8, 3), note);
        assertNotEquals(new Note(waveform, 1, 2, 9), note);
        assertNotEquals("not a note", note);
        assertNotEquals(null, note);
    }
}
