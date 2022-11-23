package audiosynth.model;

import audiosynth.audio.Waveform;

import java.util.Objects;

/**
 * A single note in a piece of music: a waveform at a given pitch, starting at a certain time, and
 * lasting for a certain duration.
 */
public final class Note {
    private final Waveform waveform;
    private final double pitch, startTime, duration;

    /**
     * Creates a note.
     *
     * @param waveform  The shape of the wave (must not be null)
     * @param pitch     Pitch in MIDI units
     * @param startTime Start time in seconds
     * @param duration  Duration in seconds
     */
    public Note(Waveform waveform, double pitch, double startTime, double duration) {
        this.waveform = Objects.requireNonNull(waveform, "waveform");
        this.pitch = pitch;
        this.startTime = startTime;
        this.duration = duration;
    }

    public Waveform getWaveform() {
        return waveform;
    }

    public double getPitch() {
        return pitch;
    }

    public double getStartTime() {
        return startTime;
    }

    public double getEndTime() {
        return startTime + duration;
    }

    public double getDuration() {
        return duration;
    }
}
