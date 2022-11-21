package audiosynth.audio;

/**
 * Utilities to do audio-related unit conversions.
 */
public class Utils {
    /**
     * Converts a pitch in MIDI units to a wavelength in samples (at AudioBuffer.SAMPLE_RATE samples
     * per second).
     *
     * In MIDI units, middle C is 60. A +1 delta is an equal-tempered half step (a.k.a. semitone)
     * higher, so a delta of +12 is an octave higher (i.e. half the wavelength).
     */
    public static double convertPitchToWavelength(double pitch) {
        return AudioBuffer.SAMPLE_RATE / (8.1757989 * Math.pow(2, pitch / 12));
    }

    /**
     * Converts a time in seconds to a time expressed in samples (at AudioBuffer.SAMPLE_RATE samples
     * per second).
     */
    public static int convertSecondsToSamples(double seconds) {
        return (int) Math.round(seconds * AudioBuffer.SAMPLE_RATE);
    }
}
