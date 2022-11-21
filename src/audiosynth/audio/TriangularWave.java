package audiosynth.audio;

public class TriangularWave implements Waveform {
    @Override
    public Signal createSignal(double wavelength) {
        return n -> Math.abs(n / wavelength % 1 - 0.5) * 4 - 1;
    }
}
