package audiosynth;

import audiosynth.instrument.Instrument;

import java.util.Objects;

public final class Note {
    private final Instrument instrument;
    private final double pitch, startTime, duration;

    public Note(Instrument instrument, double pitch, double startTime, double duration) {
        this.instrument = Objects.requireNonNull(instrument, "instrument");
        this.pitch = pitch;
        this.startTime = startTime;
        this.duration = duration;
    }

    public Instrument getInstrument() {
        return instrument;
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
            instrument.equals(note.instrument);
    }

    @Override
    public int hashCode() {
        return Objects.hash(instrument, pitch, startTime, duration);
    }
}
