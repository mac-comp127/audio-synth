package audiosynth;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

class AudioBufferTest {
    private AudioBuffer audio = new AudioBuffer(8);

    @Test
    void getPeak() {
        assertEquals(0, audio.getPeak());
        audio.getSamples()[0] = 3;
        assertEquals(3, audio.getPeak());
        audio.getSamples()[3] = -17;
        audio.getSamples()[6] = 12;
        assertEquals(17, audio.getPeak());
    }

    @Test
    void normalize() {
        audio.getSamples()[0] = -3;
        audio.getSamples()[2] = 10;
        audio.getSamples()[5] = -20;
        audio.getSamples()[7] = 2;
        audio.normalize();
        assertArrayEquals(
            new float[] { -0.15f, 0, 0.5f, 0, 0, -1, 0, 0.1f },
            audio.getSamples());
    }

    @Test
    void mixStartsSignalAtTime0() {
        audio.mix(n -> n + 10, 4, 3);
        assertArrayEquals(
            new float[] { 0, 0, 0, 0, 10, 11, 12, 0 },
            audio.getSamples());
    }

    @Test
    void mixAddsToExistingSignal() {
        audio.getSamples()[4] = 100;
        audio.mix(n -> 1, 2, 4);
        assertArrayEquals(
            new float[] { 0, 0, 1, 1, 101, 1, 0, 0 },
            audio.getSamples());
    }

    @Test
    void mixReachesBothEnds() {
        audio.mix(n -> 1, 0, 8);
        audio.mix(n -> 10, 0, 3);
        audio.mix(n -> 100, 5, 3);
        assertArrayEquals(
            new float[] { 11, 11, 11, 1, 1, 101, 101, 101 },
            audio.getSamples());
    }

    @Test
    void mixMultiple() {
        audio.mix(n -> n * 0.5 + 1, 4, 3);
        assertArrayEquals(
            new float[] { 0, 0, 0, 0, 1.0f, 1.5f, 2.0f, 0 },
            audio.getSamples());

        audio.mix(n -> n - 6, 1, 5);
        assertArrayEquals(
            new float[] { 0, -6, -5, -4, -2, -0.5f, 2.0f, 0 },
            audio.getSamples());
    }
}