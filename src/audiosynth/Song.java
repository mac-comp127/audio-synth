package audiosynth;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Song {
    private final List<Note> notes = new ArrayList<>();

    public void addNote(Note note) {
        notes.add(note);
    }

    public List<Note> getNotes() {
        return Collections.unmodifiableList(notes);
    }

    public double getDuration() {
        return notes.stream()
            .mapToDouble(Note::getEndTime)
            .max().orElse(0);
    }

    public AudioBuffer renderAudio() {
        AudioBuffer audio = new AudioBuffer(
            Utils.covertSecondsToSamples(
                getDuration()));

        for (Note note : notes) {
            audio.mix(
                note.getWaveform().createSignal(
                    Utils.convertPitchToWavelength(note.getPitch())),
                Utils.covertSecondsToSamples(note.getStartTime()),
                Utils.covertSecondsToSamples(note.getDuration()));
        }

        audio.normalize();
        return audio;
    }
}
