# Song object model specification

## `Note` class

```java
/**
 * A single note in a piece of music: a waveform at a given pitch, starting at a certain time, and
 * lasting for a certain duration.
 */
public final class Note

    /**
     * Creates a note.
     *
     * @param waveform  The shape of the wave (must not be null)
     * @param pitch     Pitch in MIDI units
     * @param startTime Start time in seconds
     * @param duration  Duration in seconds
     */
    public Note(Waveform waveform, double pitch, double startTime, double duration)

    public Waveform getWaveform()

    public double getPitch()

    public double getStartTime()

    public double getEndTime()  // computed from start time and duration

    public double getDuration()
```

## `Song` class

```java
/**
 * A collection of notes that (perhaps) make music together.
 * A newly created song starts out empty — no notes.
 */
public class Song

    /**
     * Adds the given note to the notes already in the piece.
     */
    public void addNote(Note note)

    /**
     * Returns an unmodifiable collection of all the notes in the piece.
     */
    public List<Note> getNotes()

```




```java
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
```