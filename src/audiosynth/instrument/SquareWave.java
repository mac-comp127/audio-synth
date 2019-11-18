package audiosynth.instrument;

import audiosynth.Utils;
import audiosynth.Waveform;

public class SquareWave implements Instrument {
    @Override
    public Waveform createWaveform(double wavelength) {
        return n -> (Math.round(n / wavelength % 1) - 0.5) * 0.7;
    }
}
