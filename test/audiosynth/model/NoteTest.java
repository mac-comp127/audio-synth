package audiosynth.model;

import org.junit.jupiter.api.Test;

import audiosynth.audio.SineWave;
import audiosynth.audio.Waveform;

import static org.junit.jupiter.api.Assertions.*;

class NoteTest {
    private Waveform waveform = new SineWave();

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
}
