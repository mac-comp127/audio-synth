# Song object model specification

Your goal: create an object model to represent a song made of many notes.

## `Note` class

Here is a sketch of the new class. Again, copy and paste this in (including the javadoc comments!) as your starting point. This only shows the signature of public methods. What instance variables does the class need?

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
        // hint: to make sure that waveform is not null, use:
        // Objects.requireNonNull(waveform, "waveform")

    public Waveform getWaveform()

    public double getPitch()

    public double getStartTime()

    public double getEndTime()  // computed from start time and duration

    public double getDuration()
```

Here are some tests that will pass if you implemented `Note` correctly:

```java
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
        // Hint: use Collections.unmodifiableList(...)

```

Tests:

```java
class SongTest {
    private static final double CONCERT_A_WAVELENGTH = AudioBuffer.SAMPLE_RATE / 880.0;

    private Song song = new Song();
    private Waveform waveform0 = wavelength -> t -> wavelength;

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
