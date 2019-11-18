package audiosynth.instrument;

import audiosynth.Waveform;

public interface Instrument {
    Waveform createWaveform(double wavelength);
}
