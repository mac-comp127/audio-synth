package audiosynth.audio;

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
}
