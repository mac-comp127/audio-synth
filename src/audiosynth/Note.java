package audiosynth;

import audiosynth.instrument.Instrument;

public final class Note {
    private final Instrument instrument;
    private final double pitch, startTime, duration;

    public Note(Instrument instrument, double pitch, double startTime, double duration) {
        this.instrument = instrument;
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
}
