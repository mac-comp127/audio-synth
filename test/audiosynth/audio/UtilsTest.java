package audiosynth.audio;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UtilsTest {

    @Test
    void convertPitchToWavelength() {
        assertEquals(AudioBuffer.SAMPLE_RATE / 440.0, Utils.convertPitchToWavelength(69), 0.001);
        assertEquals(Utils.convertPitchToWavelength(0) / 2, Utils.convertPitchToWavelength(12), 0.001);
    }

    @Test
    void convertSecondsToSamples() {
        assertEquals(AudioBuffer.SAMPLE_RATE, Utils.convertSecondsToSamples(1));
        assertEquals(0, Utils.convertSecondsToSamples(0));
    }
}