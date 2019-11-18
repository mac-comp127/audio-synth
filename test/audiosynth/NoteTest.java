package audiosynth;

import audiosynth.instrument.Instrument;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class NoteTest {
    private Instrument instrument = x -> y -> 0;
    private Instrument instrument2 = x -> y -> 0;

    @Test
    void construction() {
        Note note = new Note(instrument, 32, 88.5, 0.8);
        assertSame(instrument, note.getInstrument());
        assertEquals(32, note.getPitch());
        assertEquals(88.5, note.getStartTime());
        assertEquals(0.8, note.getDuration());
        assertThrows(NullPointerException.class, () -> {
            new Note(null, 32, 88.5, 0.8);
        });
    }

    @Test
    void getEndTime() {
        assertEquals(89.3, new Note(instrument, 6, 88.5, 0.8).getEndTime());
    }

    @Test
    void testEquals() {
        Note note = new Note(instrument, 1, 2, 3);
        assertEquals(new Note(instrument, 1, 2, 3), note);
        assertNotEquals(new Note(instrument2, 1, 2, 3), note);
        assertNotEquals(new Note(instrument, 7, 2, 3), note);
        assertNotEquals(new Note(instrument, 1, 8, 3), note);
        assertNotEquals(new Note(instrument, 1, 2, 9), note);
        assertNotEquals("not a note", note);
        assertNotEquals(null, note);
    }
}
