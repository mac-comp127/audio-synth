package audiosynth.instrument;

import audiosynth.Waveform;

public class SineWave implements Instrument {
    @Override
    public Waveform createWaveform(double wavelength) {
        return n -> Math.sin(n * 2 * Math.PI / wavelength);
    }
}
