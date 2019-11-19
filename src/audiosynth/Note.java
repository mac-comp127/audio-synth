package audiosynth;

import audiosynth.waveform.Waveform;

import java.util.Objects;

public final class Note {
    private final Waveform waveform;
    private final double pitch, startTime, duration;

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

    @Override
    public boolean equals(Object that) {
        if (this == that) {
            return true;
        }
        if (that == null || getClass() != that.getClass()) {
            return false;
        }
        Note note = (Note) that;
        return Double.compare(note.pitch, pitch) == 0 &&
            Double.compare(note.startTime, startTime) == 0 &&
            Double.compare(note.duration, duration) == 0 &&
            waveform.equals(note.waveform);
    }

    @Override
    public int hashCode() {
        return Objects.hash(waveform, pitch, startTime, duration);
    }
}
