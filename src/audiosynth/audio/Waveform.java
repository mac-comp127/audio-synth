package audiosynth.audio;

/**
 * A general shape of sound wave, independent of amplitude and frequency. A waveform acts like an
 * instrument: it can generate signals for different pitches (wavelengths).
 */
public interface Waveform {
    /**
     * Returns a signal that produces this waveform at the given wavelength.
     */
    Signal createSignal(double wavelength);
}
