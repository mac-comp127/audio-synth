package audiosynth.waveform;

import audiosynth.Signal;

public class SawtoothWave implements Waveform {
    @Override
    public Signal createSignal(double wavelength) {
        return n -> n / wavelength % 1 - 0.5;
    }
}
