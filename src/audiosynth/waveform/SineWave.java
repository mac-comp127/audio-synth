package audiosynth.waveform;

import audiosynth.Signal;

public class SineWave implements Waveform {
    @Override
    public Signal createSignal(double wavelength) {
        return n -> Math.sin(n * 2 * Math.PI / wavelength);
    }
}
