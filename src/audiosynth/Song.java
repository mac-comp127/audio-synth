package audiosynth;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * A collection of notes that (perhaps) make music together.
 * A newly created song starts out empty — no notes.
 */
public class Song {
    private final List<Note> notes = new ArrayList<>();

    /**
     * Adds the given note to the notes already in the piece.
     */
    public void addNote(Note note) {
        notes.add(note);
    }

    /**
     * Returns an unmodifiable collection of all the notes in the piece.
     */
    public List<Note> getNotes() {
        return Collections.unmodifiableList(notes);
    }
}
