package audiosynth.instrument;

import audiosynth.Waveform;

public class TriangularWave implements Instrument {
    @Override
    public Waveform createWaveform(double wavelength) {
        return n -> Math.abs(n / wavelength % 1 - 0.5) * 4 - 1;
    }
}
