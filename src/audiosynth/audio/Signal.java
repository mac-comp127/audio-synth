package audiosynth.audio;

/**
 * A scalar value that varies over time. If this signal represents a sound wave, then the wave has a
 * specific shape, amplitude, and frequency.
 */
public interface Signal {
    /**
     * Returns the amplitude of the wave at the given time in seconds.
     */
    double amplitudeAt(double t);
}
