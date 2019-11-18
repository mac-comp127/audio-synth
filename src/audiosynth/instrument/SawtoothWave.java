package audiosynth.instrument;

import audiosynth.Utils;
import audiosynth.Waveform;

public class SawtoothWave implements Instrument {
    @Override
    public Waveform createWaveform(double wavelength) {
        return n -> n / wavelength % 1 - 0.5;
    }
}
