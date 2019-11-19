package audiosynth.waveform;

import audiosynth.Signal;

public interface Waveform {
    Signal createSignal(double wavelength);
}
