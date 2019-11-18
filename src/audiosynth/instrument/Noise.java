package audiosynth.instrument;

import audiosynth.Waveform;

import java.util.Random;

public class Noise implements Instrument {
    private Random rand = new Random();

    @Override
    public Waveform createWaveform(double pitch) {
        return n -> rand.nextFloat() - 0.5;
    }
}
