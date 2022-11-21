package audiosynth.audio;

import java.util.Random;

public class RandomNoise implements Waveform {
    private final Random rand = new Random();

    @Override
    public Signal createSignal(double pitch) {
        return n -> rand.nextFloat() - 0.5;
    }
}
